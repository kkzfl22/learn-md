# ClickHouse笔记

## 1. ClickHouse介绍

官网地址：

```json
https://clickhouse.com/
```

ClickHouse® is a high-performance, column-oriented SQL database management system (DBMS) for online analytical processing (OLAP). It is available as both an [open-source software](https://github.com/ClickHouse/ClickHouse) and a [cloud offering](https://clickhouse.com/cloud).

## [What is OLAP?](https://clickhouse.com/docs/en/intro#what-is-olap)

OLAP scenarios require real-time responses on top of large datasets for complex analytical queries with the following characteristics:

- Datasets can be massive - billions or trillions of rows
- Data is organized in tables that contain many columns
- Only a few columns are selected to answer any particular query
- Results must be returned in milliseconds or seconds

## [Column-Oriented vs Row-Oriented Databases](https://clickhouse.com/docs/en/intro#column-oriented-vs-row-oriented-databases)

In a row-oriented DBMS, data is stored in rows, with all the values related to a row physically stored next to each other.

In a column-oriented DBMS, data is stored in columns, with values from the same columns stored together.

## [Why Column-Oriented Databases Work Better in the OLAP Scenario](https://clickhouse.com/docs/en/intro#why-column-oriented-databases-work-better-in-the-olap-scenario)

Column-oriented databases are better suited to OLAP scenarios: they are at least 100 times faster in processing most queries. The reasons are explained in detail below, but the fact is easier to demonstrate visually:

**Row-oriented DBMS**

![Row-oriented](D:\work\learn\learn-md\clickhouse\row-oriented-3e6fd5aa48e3075202d242b4799da8fa.gif)

**Column-oriented DBMS**

![Column-oriented](D:\work\learn\learn-md\clickhouse\column-oriented-d082e49b7743d4ded32c7952bfdb028f.gif)

See the difference?

The rest of this article explains why column-oriented databases work well for these scenarios, and why ClickHouse in particular [outperforms](https://clickhouse.com/docs/en/concepts/why-clickhouse-is-so-fast#performance-when-inserting-data) others in this category.





## 2. Clickhouse的安装

```javascript
https://clickhouse.com/docs/en/install
```

官网提供了多种的安装方式

### [在线RPM包安装](https://clickhouse.com/docs/en/install#from-rpm-packages)：

It is recommended to use official pre-compiled `rpm` packages for CentOS, RedHat, and all other rpm-based Linux distributions.

#### Setup the RPM repository[](https://clickhouse.com/docs/en/install#setup-the-rpm-repository)

First, you need to add the official repository:

```bash
sudo yum install -y yum-utils
sudo yum-config-manager --add-repo https://packages.clickhouse.com/rpm/clickhouse.repo
```



For systems with `zypper` package manager (openSUSE, SLES):

```bash
sudo zypper addrepo -r https://packages.clickhouse.com/rpm/clickhouse.repo -g
sudo zypper --gpg-auto-import-keys refresh clickhouse-stable
```



Later any `yum install` can be replaced by `zypper install`. To specify a particular version, add `-$VERSION` to the end of the package name, e.g. `clickhouse-client-22.2.2.22`.

#### Install ClickHouse server and client[](https://clickhouse.com/docs/en/install#install-clickhouse-server-and-client-1)

```bash
sudo yum install -y clickhouse-server clickhouse-client
```



#### Start ClickHouse server[](https://clickhouse.com/docs/en/install#start-clickhouse-server-1)

```bash
sudo systemctl enable clickhouse-server
sudo systemctl start clickhouse-server
sudo systemctl status clickhouse-server
clickhouse-client # or "clickhouse-client --password" if you set up a password.
```



#### Install standalone ClickHouse Keeper[](https://clickhouse.com/docs/en/install#install-standalone-clickhouse-keeper-1)

tip

In production environment we [strongly recommend](https://clickhouse.com/docs/en/operations/tips#L143-L144) running ClickHouse Keeper on dedicated nodes. In test environments, if you decide to run ClickHouse Server and ClickHouse Keeper on the same server, you do not need to install ClickHouse Keeper as it is included with ClickHouse server. This command is only needed on standalone ClickHouse Keeper servers.

```bash
sudo yum install -y clickhouse-keeper
```



#### Enable and start ClickHouse Keeper[](https://clickhouse.com/docs/en/install#enable-and-start-clickhouse-keeper-1)

```bash
sudo systemctl enable clickhouse-keeper
sudo systemctl start clickhouse-keeper
sudo systemctl status clickhouse-keeper
```

You can replace `stable` with `lts` to use different [release kinds](https://clickhouse.com/docs/knowledgebase/production) based on your needs.

Then run these commands to install packages:

```bash
sudo yum install clickhouse-server clickhouse-client
```

You can also download and install packages manually from [here](https://packages.clickhouse.com/rpm/stable).

### 使用TGZ包安装

首先从`https://packages.clickhouse.com/tgz/`去下载包,选择`stable`稳定版本，选择`clickhouse-client-22.1.4.30`版本

需要拉取包:

```sh
https://packages.clickhouse.com/tgz/stable/clickhouse-common-static-22.12.6.22-amd64.tgz
https://packages.clickhouse.com/tgz/stable/clickhouse-common-static-dbg-22.12.6.22-amd64.tgz
https://packages.clickhouse.com/tgz/stable/clickhouse-server-22.12.6.22-amd64.tgz
https://packages.clickhouse.com/tgz/stable/clickhouse-client-22.12.6.22-amd64.tgz
https://packages.clickhouse.com/tgz/stable/clickhouse-keeper-22.12.6.22-amd64.tgz

export ARCH="amd64"
export LATEST_VERSION="22.12.6.22"

tar -xzvf "clickhouse-common-static-$LATEST_VERSION-${ARCH}.tgz"
sudo "clickhouse-common-static-$LATEST_VERSION/install/doinst.sh"

tar -xzvf "clickhouse-common-static-dbg-$LATEST_VERSION-${ARCH}.tgz"
sudo "clickhouse-common-static-dbg-$LATEST_VERSION/install/doinst.sh"

tar -xzvf "clickhouse-server-$LATEST_VERSION-${ARCH}.tgz"
sudo "clickhouse-server-$LATEST_VERSION/install/doinst.sh" configure
sudo /etc/init.d/clickhouse-server start

tar -xzvf "clickhouse-client-$LATEST_VERSION-${ARCH}.tgz"
sudo "clickhouse-client-$LATEST_VERSION/install/doinst.sh"


# 启动服务
sudo clickhouse start

# 连接数据库 
clickhouse-client --password

```





It is recommended to use official pre-compiled `tgz` archives for all Linux distributions, where installation of `deb` or `rpm` packages is not possible.

The required version can be downloaded with `curl` or `wget` from repository https://packages.clickhouse.com/tgz/. After that downloaded archives should be unpacked and installed with installation scripts. Example for the latest stable version:

```bash
LATEST_VERSION=$(curl -s https://raw.githubusercontent.com/ClickHouse/ClickHouse/master/utils/list-versions/version_date.tsv | \
    grep -Eo '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+' | sort -V -r | head -n 1)
export LATEST_VERSION

case $(uname -m) in
  x86_64) ARCH=amd64 ;;
  aarch64) ARCH=arm64 ;;
  *) echo "Unknown architecture $(uname -m)"; exit 1 ;;
esac

for PKG in clickhouse-common-static clickhouse-common-static-dbg clickhouse-server clickhouse-client clickhouse-keeper
do
  curl -fO "https://packages.clickhouse.com/tgz/stable/$PKG-$LATEST_VERSION-${ARCH}.tgz" \
    || curl -fO "https://packages.clickhouse.com/tgz/stable/$PKG-$LATEST_VERSION.tgz"
done

tar -xzvf "clickhouse-common-static-$LATEST_VERSION-${ARCH}.tgz" \
  || tar -xzvf "clickhouse-common-static-$LATEST_VERSION.tgz"
sudo "clickhouse-common-static-$LATEST_VERSION/install/doinst.sh"

tar -xzvf "clickhouse-common-static-dbg-$LATEST_VERSION-${ARCH}.tgz" \
  || tar -xzvf "clickhouse-common-static-dbg-$LATEST_VERSION.tgz"
sudo "clickhouse-common-static-dbg-$LATEST_VERSION/install/doinst.sh"

tar -xzvf "clickhouse-server-$LATEST_VERSION-${ARCH}.tgz" \
  || tar -xzvf "clickhouse-server-$LATEST_VERSION.tgz"
sudo "clickhouse-server-$LATEST_VERSION/install/doinst.sh" configure
sudo /etc/init.d/clickhouse-server start

tar -xzvf "clickhouse-client-$LATEST_VERSION-${ARCH}.tgz" \
  || tar -xzvf "clickhouse-client-$LATEST_VERSION.tgz"
sudo "clickhouse-client-$LATEST_VERSION/install/doinst.sh"
```



For production environments, it’s recommended to use the latest `stable`-version. You can find its number on GitHub page https://github.com/ClickHouse/ClickHouse/tags with postfix `-stable`.





### docker运行 

```sh
# $(realpath ./ch_data) 即获取当前目录下ch_data的完整目录
# 运行容器并指定相关的配制与日志目录 
mkdir -p /opt/nullnull/clickhouse/data
mkdir -p /opt/nullnull/clickhouse/log
mkdir -p /opt/nullnull/clickhouse/config/config.d
mkdir -p /opt/nullnull/clickhouse/config/users.d


docker run -d \
	-p 18123:8123 -p19000:9000  \
    -v /opt/nullnull/clickhouse/data:/var/lib/clickhouse/:z \
    -v /opt/nullnull/clickhouse/log:/var/log/clickhouse-server/:z \
    -v /opt/nullnull/clickhouse/config/config.d:/etc/clickhouse-server/config.d/:z \
    -v /opt/nullnull/clickhouse/config/users.d:/etc/clickhouse-server/users.d/:z \
    --name some-clickhouse-server  \
    --ulimit nofile=262144:262144 \
    clickhouse/clickhouse-server


docker exec -it some-clickhouse-server clickhouse-client


docker stop some-clickhouse-server && docker rm some-clickhouse-server

```

样例：

```sh
docker pull dockerpull.com/clickhouse/clickhouse-server:22.6.3.35

docker run -d -p 18123:8123 -p19000:9000  \
     -v /opt/nullnull/clickhouse/data:/var/lib/clickhouse/:z \
     -v /opt/nullnull/clickhouse/log:/var/log/clickhouse-server/:z \
     -v /opt/nullnull/clickhouse/config/config.d:/etc/clickhouse-server/config.d/:z \
     -v /opt/nullnull/clickhouse/config/users.d:/etc/clickhouse-server/users.d/:z \
     --name some-clickhouse-server  \
     --ulimit nofile=262144:262144 \
     dockerpull.com/clickhouse/clickhouse-server:22.6.3.35


[root@os21 config.d]# docker run -d \
> -p 18123:8123 -p19000:9000  \
>     -v /opt/nullnull/clickhouse/data:/var/lib/clickhouse/:z \
>     -v /opt/nullnull/clickhouse/log:/var/log/clickhouse-server/:z \
>     -v /opt/nullnull/clickhouse/config/config.d:/etc/clickhouse-server/config.d/:z \
>     -v /opt/nullnull/clickhouse/config/users.d:/etc/clickhouse-server/users.d/:z \
>     --name some-clickhouse-server  \
>     --ulimit nofile=262144:262144 \
>     clickhouse/clickhouse-server
b701e64d074286db06a8974185c51b9a79c9d37a668cea6ba37286d5b9830bb8
[root@os21 data]# docker exec -it some-clickhouse-server clickhouse-client
ClickHouse client version 21.12.3.32 (official build).
Connecting to localhost:9000 as user default.
Connected to ClickHouse server version 21.12.3 revision 54452.

85aeb1bde429 :) show databases;

SHOW DATABASES

Query id: e0a97ff5-2c76-4a2c-98dd-6f2040dcecb4

┌─name───────────────┐
│ INFORMATION_SCHEMA │
│ default            │
│ information_schema │
│ system             │
└────────────────────┘

4 rows in set. Elapsed: 0.001 sec.
```



## 3. ClickHouse的核心目录结构

```sh
# 配制目录
[root@os21 clickhouse-server]# pwd
/etc/clickhouse-server
[root@os21 clickhouse-server]# tree .
.
├── config.d
│   └── listen.xml
├── config.xml
├── users.d
│   └── default-password.xml
└── users.xml

2 directories, 4 files

# 数据目录
[root@os21 clickhouse-server]# cd /var/lib/clickhouse
[root@os21 clickhouse]# tree -L 3 .
.
├── access
│   ├── quotas.list
│   ├── roles.list
│   ├── row_policies.list
│   ├── settings_profiles.list
│   └── users.list
├── cores
├── data(存放数据)
│   ├── default
│   └── system
│       ├── asynchronous_metric_log -> /var/lib/clickhouse/store/85d/85d0f9e5-73ca-430a-a7bf-8b84c18619d8/
│       ├── metric_log -> /var/lib/clickhouse/store/b68/b68920da-efdb-4523-8666-1e91e5e262be/
│       ├── query_log -> /var/lib/clickhouse/store/5ca/5ca490e8-33ba-4463-b6a8-67bd92fe1a7e/
│       └── trace_log -> /var/lib/clickhouse/store/417/417dbbc7-9d1b-4fab-bc08-15e75825e658/
├── dictionaries_lib
├── flags
├── format_schemas
├── metadata(元数据)
│   ├── default -> /var/lib/clickhouse/store/080/080794c6-0a20-4fc7-8b1b-eab09585e33d/
│   ├── default.sql
│   ├── information_schema
│   ├── INFORMATION_SCHEMA
│   ├── information_schema.sql
│   ├── INFORMATION_SCHEMA.sql
│   ├── system -> /var/lib/clickhouse/store/9a3/9a3b33ec-78a0-4a04-9c6f-8f148cdc7237/
│   └── system.sql
├── metadata_dropped
├── named_collections
├── preprocessed_configs
│   ├── config.xml
│   └── users.xml
├── status
├── store
│   ├── 080
│   │   └── 080794c6-0a20-4fc7-8b1b-eab09585e33d
│   ├── 417
│   │   └── 417dbbc7-9d1b-4fab-bc08-15e75825e658
│   ├── 5ca
│   │   └── 5ca490e8-33ba-4463-b6a8-67bd92fe1a7e
│   ├── 85d
│   │   └── 85d0f9e5-73ca-430a-a7bf-8b84c18619d8
│   ├── 9a3
│   │   └── 9a3b33ec-78a0-4a04-9c6f-8f148cdc7237
│   └── b68
│       └── b68920da-efdb-4523-8666-1e91e5e262be
├── tmp
├── user_defined
├── user_files
├── user_scripts
└── uuid

37 directories, 13 files

# 运行的日志目录
[root@os21 clickhouse-server]# cd /var/log/clickhouse-server/
[root@os21 clickhouse-server]# tree .
.
├── clickhouse-server.err.log
├── clickhouse-server.log
├── stderr.log
└── stdout.log

# 运行的脚本目录
[root@os21 clickhouse-server]# cd /usr/bin/
[root@os21 bin]# ls -l | grep click
-r-xr-xr-x. 1 root              root              451311184 Jul 27 16:15 clickhouse
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-benchmark -> clickhouse
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-client -> clickhouse
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-compressor -> clickhouse
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-copier -> /usr/bin/clickhouse
-rwxr-xr-x. 1 root              root               19643059 Jul 27 16:15 clickhouse-diagnostics
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-disks -> /usr/bin/clickhouse
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-extract-from-config -> /usr/bin/clickhouse
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-format -> clickhouse
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-git-import -> /usr/bin/clickhouse
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-keeper -> /usr/bin/clickhouse
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-keeper-converter -> /usr/bin/clickhouse
-rwxr-xr-x. 1 clickhouse-bridge clickhouse-bridge 141491712 Jul 27 16:15 clickhouse-library-bridge
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-local -> clickhouse
lrwxrwxrwx. 1 root              root                     10 Jul 27 16:18 clickhouse-obfuscator -> clickhouse
-rwxr-xr-x. 1 clickhouse-bridge clickhouse-bridge 142156296 Jul 27 16:15 clickhouse-odbc-bridge
-rwxr-xr-x. 1 root              root                   2014 Jul 27 16:15 clickhouse-report
lrwxrwxrwx. 1 root              root                     19 Jul 27 16:15 clickhouse-server -> /usr/bin/clickhouse
```

指定数据目录的位置：

```sh
在config.xml文件中指定:
/etc/clickhouse-server/config.xml
    <!-- Path to data directory, with trailing slash. -->
    <path>/var/lib/clickhouse/</path>
```

建库建表后查看数据目录

```sh
create database nullnull;

use nullnull;

create table user(id UInt8,name String)engine=TinyLog;

insert into user(id,name) values(1,'nullnull'),(2,'feifei');

select * from user;
```

样例：

```sh
[root@os21 data]# clickhouse-client --password
ClickHouse client version 22.12.6.22 (official build).
Password for user (default): 
Connecting to localhost:9000 as user default.
Connected to ClickHouse server version 22.12.6 revision 54461.

Warnings:
 * Linux transparent hugepages are set to "always". Check /sys/kernel/mm/transparent_hugepage/enabled

os21 :) create database nullnull;

CREATE DATABASE nullnull

Query id: f8f6c6eb-a2c2-4172-a10d-559aa57d1ade

Ok.

0 rows in set. Elapsed: 0.276 sec. 

os21 :) use nullnull;

USE nullnull

Query id: 5d33816b-d99d-4372-a08d-04bb50073dba

Ok.

0 rows in set. Elapsed: 0.001 sec. 

os21 :) create table user(id UInt8,name String)engine=TinyLog;

CREATE TABLE user
(
    `id` UInt8,
    `name` String
)
ENGINE = TinyLog

Query id: 6be4e0b9-f53f-47c3-8e84-ada395542da7

Ok.

0 rows in set. Elapsed: 0.011 sec. 

os21 :) insert into user(id,name) values(1,'nullnull'),(2,'feifei');

INSERT INTO user (id, name) FORMAT Values

Query id: 059230ff-1c12-41fc-85a4-99aa51d1f4e7

Ok.

2 rows in set. Elapsed: 0.002 sec. 

os21 :) select * from user;

SELECT *
FROM user

Query id: d4a77c75-decc-487b-bb15-45c4c9349207

┌─id─┬─name─────┐
│  1 │ nullnull │
│  2 │ feifei   │
└────┴──────────┘

2 rows in set. Elapsed: 0.001 sec. 

os21 :) 

```

查看数据目录结构：

```sh
[root@os21 data]# pwd
/var/lib/clickhouse/data
[root@os21 data]# tree .
.
├── default
├── nullnull(数据库名)
│   └── user -> /var/lib/clickhouse/store/210/2104d6ea-ec7a-4eda-b9f1-ce7b2aee52ae/ (表名)
└── system
    ├── asynchronous_metric_log -> /var/lib/clickhouse/store/85d/85d0f9e5-73ca-430a-a7bf-8b84c18619d8/
    ├── metric_log -> /var/lib/clickhouse/store/b68/b68920da-efdb-4523-8666-1e91e5e262be/
    ├── query_log -> /var/lib/clickhouse/store/5ca/5ca490e8-33ba-4463-b6a8-67bd92fe1a7e/
    └── trace_log -> /var/lib/clickhouse/store/417/417dbbc7-9d1b-4fab-bc08-15e75825e658/

8 directories, 0 files
[root@os21 data]# cd nullnull/user/
[root@os21 user]# ll
total 12
-rw-r-----. 1 clickhouse clickhouse 28 Jul 27 17:29 id.bin
-rw-r-----. 1 clickhouse clickhouse 43 Jul 27 17:29 name.bin
-rw-r-----. 1 clickhouse clickhouse 68 Jul 27 17:29 sizes.json

[root@os21 user]# pwd
/var/lib/clickhouse/data/nullnull/user
[root@os21 user]# tree .
.
├── id.bin
├── name.bin
└── sizes.json

0 directories, 3 files
```

查看元数据目录结构

```sh
/var/lib/clickhouse/metadata
----------------------------/nullnull(库名)
-------------------------------------/user.sql (建表语句)
----------------------------/nullnull.sql(建库脚本)
```

样例

```sh
[root@os21 metadata]# pwd
/var/lib/clickhouse/metadata
[root@os21 metadata]# tree .
.
├── default -> /var/lib/clickhouse/store/080/080794c6-0a20-4fc7-8b1b-eab09585e33d/
├── default.sql
├── information_schema
├── INFORMATION_SCHEMA
├── information_schema.sql
├── INFORMATION_SCHEMA.sql
├── nullnull -> /var/lib/clickhouse/store/941/94131bf4-a3d3-4991-9b55-410c25678228/ ()
├── nullnull.sql
├── system -> /var/lib/clickhouse/store/9a3/9a3b33ec-78a0-4a04-9c6f-8f148cdc7237/
└── system.sql

5 directories, 5 files
[root@os21 metadata]# ll
total 20
lrwxrwxrwx. 1 clickhouse clickhouse 67 Jul 27 16:19 default -> /var/lib/clickhouse/store/080/080794c6-0a20-4fc7-8b1b-eab09585e33d/
-rw-r-----. 1 clickhouse clickhouse 78 Jul 27 16:19 default.sql
drwxr-x---. 2 clickhouse clickhouse  6 Jul 27 16:19 information_schema
drwxr-x---. 2 clickhouse clickhouse  6 Jul 27 16:19 INFORMATION_SCHEMA
-rw-r-----. 1 clickhouse clickhouse 51 Jul 27 16:19 information_schema.sql
-rw-r-----. 1 clickhouse clickhouse 51 Jul 27 16:19 INFORMATION_SCHEMA.sql
lrwxrwxrwx. 1 clickhouse clickhouse 67 Jul 27 17:23 nullnull -> /var/lib/clickhouse/store/941/94131bf4-a3d3-4991-9b55-410c25678228/
-rw-r-----. 1 clickhouse clickhouse 78 Jul 27 17:23 nullnull.sql
lrwxrwxrwx. 1 clickhouse clickhouse 67 Jul 27 16:19 system -> /var/lib/clickhouse/store/9a3/9a3b33ec-78a0-4a04-9c6f-8f148cdc7237/
-rw-r-----. 1 clickhouse clickhouse 78 Jul 27 16:19 system.sql
[root@os21 metadata]# cd nullnull/
[root@os21 nullnull]# ls
user.sql
[root@os21 nullnull]# cat user.sql 
ATTACH TABLE _ UUID '2104d6ea-ec7a-4eda-b9f1-ce7b2aee52ae'
(
    `id` UInt8,
    `name` String
)
ENGINE = TinyLog
[root@os21 nullnull]# cd ..
[root@os21 metadata]# ls
default      information_schema  information_schema.sql  nullnull      system
default.sql  INFORMATION_SCHEMA  INFORMATION_SCHEMA.sql  nullnull.sql  system.sql
[root@os21 metadata]# cat nullnull.sql 
ATTACH DATABASE _ UUID '94131bf4-a3d3-4991-9b55-410c25678228'
ENGINE = Atomic
[root@os21 metadata]# pwd
/var/lib/clickhouse/metadata
[root@os21 metadata]#
```



## 4. Clickhouse-client命令

```sh
# 查看相关参数的命令
clickhouse-client --help

# 样例-指定IP和端口
clickhouse-client --host 127.0.0.1 --port 9000

# 多行SQL查询 -m不加，默认为一行。
clickhouse-client --host 127.0.0.1 --port 9000 -m

# 直接执行SQL -q 跟上SQL语句
clickhouse-client --host 127.0.0.1 --port 9000 -m -q  'select * from nullnull.user' 

# 使用机器名
clickhouse-client --host os21 --port 9000 -m -q 'select * from nullnull.user' 
/etc/clickhouse-server/config.xml 
此需要将：<!-- <listen_host>::</listen_host> --> 注释打开，需重启服务
```

样例 ：

```sh
# 指定端口和IP登录
[root@os21 metadata]# clickhouse-client --host 127.0.0.1 --port 9000 --password
ClickHouse client version 22.12.6.22 (official build).
Password for user (default): 
Connecting to 127.0.0.1:9000 as user default.
Connected to ClickHouse server version 22.12.6 revision 54461.

Warnings:
 * Linux transparent hugepages are set to "always". Check /sys/kernel/mm/transparent_hugepage/enabled

os21 :) 

# 指定为多行SQL
[root@os21 metadata]# clickhouse-client --host 127.0.0.1 --port 9000  --password -m
ClickHouse client version 22.12.6.22 (official build).
Password for user (default): 
Connecting to 127.0.0.1:9000 as user default.
Connected to ClickHouse server version 22.12.6 revision 54461.

Warnings:
 * Linux transparent hugepages are set to "always". Check /sys/kernel/mm/transparent_hugepage/enabled

os21 :) select
        * from 
        nullnull.user;

SELECT *
FROM nullnull.user

Query id: 4cc2dfbc-44a4-4bca-8ac4-09ca8cffafb5

┌─id─┬─name─────┐
│  1 │ nullnull │
│  2 │ feifei   │
└────┴──────────┘

2 rows in set. Elapsed: 0.001 sec. 

os21 :) 

# 直接执行SQ查询
[root@os21 metadata]# clickhouse-client --host 127.0.0.1 --port 9000 -m -q 'select * from nullnull.user' --password
Password for user (default): 
1       nullnull
2       feifei
```



## 5. 使用官方提供的数据集学习

```sh
# 地址
# 使用Anonymized Web Analytics Data
https://clickhouse.com/docs/en/getting-started/example-datasets/metrica

# 相关的数据地址 可使用工具将这两文件下载至本地，然后上传至CK机器
https://datasets.clickhouse.com/hits/tsv/hits_v1.tsv.xz (802MB)
https://datasets.clickhouse.com/visits/tsv/visits_v1.tsv.xz (405MB)

```

装载hits_v1表

```sh
cd /opt/nullnull/example/webAnalyticsData
# 解压数据 
unxz --threads=`nproc` -c hits_v1.tsv.xz   > hits_v1.tsv

 # 创建数据库
clickhouse-client --query "CREATE DATABASE IF NOT EXISTS datasets" --password
 
 # 创建表
clickhouse-client --query "CREATE TABLE datasets.hits_v1 ( WatchID UInt64,  JavaEnable UInt8,  Title String,  GoodEvent Int16,  EventTime DateTime,  EventDate Date,  CounterID UInt32,  ClientIP UInt32,  ClientIP6 FixedString(16),  RegionID UInt32,  UserID UInt64,  CounterClass Int8,  OS UInt8,  UserAgent UInt8,  URL String,  Referer String,  URLDomain String,  RefererDomain String,  Refresh UInt8,  IsRobot UInt8,  RefererCategories Array(UInt16),  URLCategories Array(UInt16), URLRegions Array(UInt32),  RefererRegions Array(UInt32),  ResolutionWidth UInt16,  ResolutionHeight UInt16,  ResolutionDepth UInt8,  FlashMajor UInt8, FlashMinor UInt8,  FlashMinor2 String,  NetMajor UInt8,  NetMinor UInt8, UserAgentMajor UInt16,  UserAgentMinor FixedString(2),  CookieEnable UInt8, JavascriptEnable UInt8,  IsMobile UInt8,  MobilePhone UInt8,  MobilePhoneModel String,  Params String,  IPNetworkID UInt32,  TraficSourceID Int8, SearchEngineID UInt16,  SearchPhrase String,  AdvEngineID UInt8,  IsArtifical UInt8,  WindowClientWidth UInt16,  WindowClientHeight UInt16,  ClientTimeZone Int16,  ClientEventTime DateTime,  SilverlightVersion1 UInt8, SilverlightVersion2 UInt8,  SilverlightVersion3 UInt32,  SilverlightVersion4 UInt16,  PageCharset String,  CodeVersion UInt32,  IsLink UInt8,  IsDownload UInt8,  IsNotBounce UInt8,  FUniqID UInt64,  HID UInt32,  IsOldCounter UInt8, IsEvent UInt8,  IsParameter UInt8,  DontCountHits UInt8,  WithHash UInt8, HitColor FixedString(1),  UTCEventTime DateTime,  Age UInt8,  Sex UInt8,  Income UInt8,  Interests UInt16,  Robotness UInt8,  GeneralInterests Array(UInt16), RemoteIP UInt32,  RemoteIP6 FixedString(16),  WindowName Int32,  OpenerName Int32,  HistoryLength Int16,  BrowserLanguage FixedString(2),  BrowserCountry FixedString(2),  SocialNetwork String,  SocialAction String,  HTTPError UInt16, SendTiming Int32,  DNSTiming Int32,  ConnectTiming Int32,  ResponseStartTiming Int32,  ResponseEndTiming Int32,  FetchTiming Int32,  RedirectTiming Int32, DOMInteractiveTiming Int32,  DOMContentLoadedTiming Int32,  DOMCompleteTiming Int32,  LoadEventStartTiming Int32,  LoadEventEndTiming Int32, NSToDOMContentLoadedTiming Int32,  FirstPaintTiming Int32,  RedirectCount Int8, SocialSourceNetworkID UInt8,  SocialSourcePage String,  ParamPrice Int64, ParamOrderID String,  ParamCurrency FixedString(3),  ParamCurrencyID UInt16, GoalsReached Array(UInt32),  OpenstatServiceName String,  OpenstatCampaignID String,  OpenstatAdID String,  OpenstatSourceID String,  UTMSource String, UTMMedium String,  UTMCampaign String,  UTMContent String,  UTMTerm String, FromTag String,  HasGCLID UInt8,  RefererHash UInt64,  URLHash UInt64,  CLID UInt32,  YCLID UInt64,  ShareService String,  ShareURL String,  ShareTitle String,  ParsedParams Nested(Key1 String,  Key2 String, Key3 String, Key4 String, Key5 String,  ValueDouble Float64),  IslandID FixedString(16),  RequestNum UInt32,  RequestTry UInt8) ENGINE = MergeTree() PARTITION BY toYYYYMM(EventDate) ORDER BY (CounterID, EventDate, intHash32(UserID)) SAMPLE BY intHash32(UserID) SETTINGS index_granularity = 8192" --password
 
 
 # 导入数据
cat hits_v1.tsv | clickhouse-client --query "INSERT INTO datasets.hits_v1 FORMAT TSV" --max_insert_block_size=100000 --password

# 检查数据行数
clickhouse-client --query "SELECT COUNT(*) FROM datasets.hits_v1" --password
# 行数 8873898
 
 
```



装载visits表

```sh
# 解压
unxz --threads=`nproc` -c visits_v1.tsv.xz   > visits_v1.tsv


# 创建表
clickhouse-client --query "CREATE TABLE datasets.visits_v1 ( CounterID UInt32,  StartDate Date,  Sign Int8,  IsNew UInt8,  VisitID UInt64,  UserID UInt64,  StartTime DateTime,  Duration UInt32,  UTCStartTime DateTime,  PageViews Int32,  Hits Int32,  IsBounce UInt8,  Referer String,  StartURL String,  RefererDomain String,  StartURLDomain String,  EndURL String,  LinkURL String,  IsDownload UInt8,  TraficSourceID Int8,  SearchEngineID UInt16,  SearchPhrase String,  AdvEngineID UInt8,  PlaceID Int32,  RefererCategories Array(UInt16),  URLCategories Array(UInt16),  URLRegions Array(UInt32),  RefererRegions Array(UInt32),  IsYandex UInt8,  GoalReachesDepth Int32,  GoalReachesURL Int32,  GoalReachesAny Int32,  SocialSourceNetworkID UInt8,  SocialSourcePage String,  MobilePhoneModel String,  ClientEventTime DateTime,  RegionID UInt32,  ClientIP UInt32,  ClientIP6 FixedString(16),  RemoteIP UInt32,  RemoteIP6 FixedString(16),  IPNetworkID UInt32,  SilverlightVersion3 UInt32,  CodeVersion UInt32,  ResolutionWidth UInt16,  ResolutionHeight UInt16,  UserAgentMajor UInt16,  UserAgentMinor UInt16,  WindowClientWidth UInt16,  WindowClientHeight UInt16,  SilverlightVersion2 UInt8,  SilverlightVersion4 UInt16,  FlashVersion3 UInt16,  FlashVersion4 UInt16,  ClientTimeZone Int16,  OS UInt8,  UserAgent UInt8,  ResolutionDepth UInt8,  FlashMajor UInt8,  FlashMinor UInt8,  NetMajor UInt8,  NetMinor UInt8,  MobilePhone UInt8,  SilverlightVersion1 UInt8,  Age UInt8,  Sex UInt8,  Income UInt8,  JavaEnable UInt8,  CookieEnable UInt8,  JavascriptEnable UInt8,  IsMobile UInt8,  BrowserLanguage UInt16,  BrowserCountry UInt16,  Interests UInt16,  Robotness UInt8,  GeneralInterests Array(UInt16),  Params Array(String),  Goals Nested(ID UInt32, Serial UInt32, EventTime DateTime,  Price Int64,  OrderID String, CurrencyID UInt32),  WatchIDs Array(UInt64),  ParamSumPrice Int64,  ParamCurrency FixedString(3),  ParamCurrencyID UInt16,  ClickLogID UInt64,  ClickEventID Int32,  ClickGoodEvent Int32,  ClickEventTime DateTime,  ClickPriorityID Int32,  ClickPhraseID Int32,  ClickPageID Int32,  ClickPlaceID Int32,  ClickTypeID Int32,  ClickResourceID Int32,  ClickCost UInt32,  ClickClientIP UInt32,  ClickDomainID UInt32,  ClickURL String,  ClickAttempt UInt8,  ClickOrderID UInt32,  ClickBannerID UInt32,  ClickMarketCategoryID UInt32,  ClickMarketPP UInt32,  ClickMarketCategoryName String,  ClickMarketPPName String,  ClickAWAPSCampaignName String,  ClickPageName String,  ClickTargetType UInt16,  ClickTargetPhraseID UInt64,  ClickContextType UInt8,  ClickSelectType Int8,  ClickOptions String,  ClickGroupBannerID Int32,  OpenstatServiceName String,  OpenstatCampaignID String,  OpenstatAdID String,  OpenstatSourceID String,  UTMSource String,  UTMMedium String,  UTMCampaign String,  UTMContent String,  UTMTerm String,  FromTag String,  HasGCLID UInt8,  FirstVisit DateTime,  PredLastVisit Date,  LastVisit Date,  TotalVisits UInt32,  TraficSource    Nested(ID Int8,  SearchEngineID UInt16, AdvEngineID UInt8, PlaceID UInt16, SocialSourceNetworkID UInt8, Domain String, SearchPhrase String, SocialSourcePage String),  Attendance FixedString(16),  CLID UInt32,  YCLID UInt64,  NormalizedRefererHash UInt64,  SearchPhraseHash UInt64,  RefererDomainHash UInt64,  NormalizedStartURLHash UInt64,  StartURLDomainHash UInt64,  NormalizedEndURLHash UInt64,  TopLevelDomain UInt64,  URLScheme UInt64,  OpenstatServiceNameHash UInt64,  OpenstatCampaignIDHash UInt64,  OpenstatAdIDHash UInt64,  OpenstatSourceIDHash UInt64,  UTMSourceHash UInt64,  UTMMediumHash UInt64,  UTMCampaignHash UInt64,  UTMContentHash UInt64,  UTMTermHash UInt64,  FromHash UInt64,  WebVisorEnabled UInt8,  WebVisorActivity UInt32,  ParsedParams    Nested(Key1 String,  Key2 String,  Key3 String,  Key4 String, Key5 String, ValueDouble    Float64),  Market Nested(Type UInt8, GoalID UInt32, OrderID String,  OrderPrice Int64,  PP UInt32,  DirectPlaceID UInt32,  DirectOrderID  UInt32,  DirectBannerID UInt32,  GoodID String, GoodName String, GoodQuantity Int32,  GoodPrice Int64),  IslandID FixedString(16)) ENGINE = CollapsingMergeTree(Sign) PARTITION BY toYYYYMM(StartDate) ORDER BY (CounterID, StartDate, intHash32(UserID), VisitID) SAMPLE BY intHash32(UserID) SETTINGS index_granularity = 8192" --password


# 导入数据 
cat visits_v1.tsv | clickhouse-client --query "INSERT INTO datasets.visits_v1 FORMAT TSV" --max_insert_block_size=100000 --password


# 检查数据行数
clickhouse-client --query "SELECT COUNT(*) FROM datasets.visits_v1" --password
# 数据行数：1680609
```

## 6. ClickHouse的beanchmark的信息

```sh
https://benchmark.clickhouse.com/
```



## 7. ClickHouse的数据类型

官网文档地址：

```sh
https://clickhouse.com/docs/en/sql-reference/data-types
```



```markdown
Data Types in ClickHouse
ClickHouse can store various kinds of data in table cells. 
This section describes the supported data types and special considerations for using and/or implementing them if any.

note
You can check whether a data type name is case-sensitive in the system.data_type_families table.
关于CK支持的所有数据类型可以到system.data_type_families表中去查看

ClickHouse data types include:

Integer types: signed and unsigned integers (UInt8, UInt16, UInt32, UInt64, UInt128, UInt256, Int8, Int16, Int32, Int64, Int128, Int256)
Floating-point numbers: floats(Float32 and Float64) and Decimal values
Boolean: ClickHouse has a Boolean type
Strings: String and FixedString
Dates: use Date and Date32 for days, and DateTime and DateTime64 for instances in time
JSON: the JSON object stores a JSON document in a single column
UUID: a performant option for storing UUID values
Low cardinality types: use an Enum when you have a handful of unique values, or use LowCardinality when you have up to 10,000 unique values of a column
Arrays: any column can be defined as an Array of values
Maps: use Map for storing key/value pairs
Aggregation function types: use SimpleAggregateFunction and AggregateFunction for storing the intermediate status of aggregate function results
Nested data structures: A Nested data structure is like a table inside a cell
Tuples: A Tuple of elements, each having an individual type.
Nullable: Nullable allows you to store a value as NULL when a value is "missing" (instead of the column settings its default value for the data type)
IP addresses: use IPv4 and IPv6 to efficiently store IP addresses
Geo types: for geographical data, including Point, Ring, Polygon and MultiPolygon
Special data types: including Expression, Set, Nothing and Interval
```

至system.data_type_families查看类型

```sh
┌─name────────────────────────────┬─case_insensitive─┬─alias_to────┐
│ JSON                            │                1 │             │
│ Polygon                         │                0 │             │
│ Ring                            │                0 │             │
│ Point                           │                0 │             │
│ SimpleAggregateFunction         │                0 │             │
│ MultiPolygon                    │                0 │             │
│ IPv6                            │                0 │             │
│ UInt32                          │                0 │             │
│ IntervalYear                    │                0 │             │
│ IntervalQuarter                 │                0 │             │
│ IntervalMonth                   │                0 │             │
│ Int64                           │                0 │             │
│ IntervalDay                     │                0 │             │
│ IntervalHour                    │                0 │             │
│ IPv4                            │                0 │             │
│ IntervalSecond                  │                0 │             │
│ LowCardinality                  │                0 │             │
│ Int16                           │                0 │             │
│ UInt256                         │                0 │             │
│ AggregateFunction               │                0 │             │
│ Nothing                         │                0 │             │
│ Decimal256                      │                1 │             │
│ Tuple                           │                0 │             │
│ Array                           │                0 │             │
│ IntervalMicrosecond             │                0 │             │
│ Bool                            │                1 │             │
│ Enum16                          │                0 │             │
│ IntervalMinute                  │                0 │             │
│ FixedString                     │                0 │             │
│ String                          │                0 │             │
│ DateTime                        │                1 │             │
│ Object                          │                0 │             │
│ Map                             │                0 │             │
│ UUID                            │                0 │             │
│ Decimal64                       │                1 │             │
│ Nullable                        │                0 │             │
│ Enum                            │                1 │             │
│ Int32                           │                0 │             │
│ UInt8                           │                0 │             │
│ Date                            │                1 │             │
│ Decimal32                       │                1 │             │
│ UInt128                         │                0 │             │
│ Float64                         │                0 │             │
│ Nested                          │                0 │             │
│ UInt16                          │                0 │             │
│ IntervalMillisecond             │                0 │             │
│ Int128                          │                0 │             │
│ Decimal128                      │                1 │             │
│ Int8                            │                0 │             │
│ Decimal                         │                1 │             │
│ Int256                          │                0 │             │
│ DateTime64                      │                1 │             │
│ Enum8                           │                0 │             │
│ DateTime32                      │                1 │             │
│ Date32                          │                1 │             │
│ IntervalWeek                    │                0 │             │
│ UInt64                          │                0 │             │
│ IntervalNanosecond              │                0 │             │
│ Float32                         │                0 │             │
│ bool                            │                1 │ Bool        │
│ INET6                           │                1 │ IPv6        │
│ INET4                           │                1 │ IPv4        │
│ ENUM                            │                1 │ Enum        │
│ BINARY                          │                1 │ FixedString │
│ GEOMETRY                        │                1 │ String      │
│ NATIONAL CHAR VARYING           │                1 │ String      │
│ BINARY VARYING                  │                1 │ String      │
│ NCHAR LARGE OBJECT              │                1 │ String      │
│ NATIONAL CHARACTER VARYING      │                1 │ String      │
│ boolean                         │                1 │ Bool        │
│ NATIONAL CHARACTER LARGE OBJECT │                1 │ String      │
│ NATIONAL CHARACTER              │                1 │ String      │
│ NATIONAL CHAR                   │                1 │ String      │
│ CHARACTER VARYING               │                1 │ String      │
│ LONGBLOB                        │                1 │ String      │
│ TINYBLOB                        │                1 │ String      │
│ MEDIUMTEXT                      │                1 │ String      │
│ TEXT                            │                1 │ String      │
│ VARCHAR2                        │                1 │ String      │
│ CHARACTER LARGE OBJECT          │                1 │ String      │
│ DOUBLE PRECISION                │                1 │ Float64     │
│ LONGTEXT                        │                1 │ String      │
│ NVARCHAR                        │                1 │ String      │
│ INT1 UNSIGNED                   │                1 │ UInt8       │
│ VARCHAR                         │                1 │ String      │
│ CHAR VARYING                    │                1 │ String      │
│ MEDIUMBLOB                      │                1 │ String      │
│ NCHAR                           │                1 │ String      │
│ VARBINARY                       │                1 │ String      │
│ CHAR                            │                1 │ String      │
│ SMALLINT UNSIGNED               │                1 │ UInt16      │
│ TIMESTAMP                       │                1 │ DateTime    │
│ FIXED                           │                1 │ Decimal     │
│ TINYTEXT                        │                1 │ String      │
│ NUMERIC                         │                1 │ Decimal     │
│ DEC                             │                1 │ Decimal     │
│ TIME                            │                1 │ Int64       │
│ FLOAT                           │                1 │ Float32     │
│ SET                             │                1 │ UInt64      │
│ TINYINT UNSIGNED                │                1 │ UInt8       │
│ INTEGER UNSIGNED                │                1 │ UInt32      │
│ INT UNSIGNED                    │                1 │ UInt32      │
│ CLOB                            │                1 │ String      │
│ MEDIUMINT UNSIGNED              │                1 │ UInt32      │
│ BLOB                            │                1 │ String      │
│ REAL                            │                1 │ Float32     │
│ SMALLINT                        │                1 │ Int16       │
│ INTEGER SIGNED                  │                1 │ Int32       │
│ NCHAR VARYING                   │                1 │ String      │
│ INT SIGNED                      │                1 │ Int32       │
│ TINYINT SIGNED                  │                1 │ Int8        │
│ BIGINT SIGNED                   │                1 │ Int64       │
│ BINARY LARGE OBJECT             │                1 │ String      │
│ SMALLINT SIGNED                 │                1 │ Int16       │
│ YEAR                            │                1 │ UInt16      │
│ MEDIUMINT                       │                1 │ Int32       │
│ INTEGER                         │                1 │ Int32       │
│ INT1 SIGNED                     │                1 │ Int8        │
│ BIT                             │                1 │ UInt64      │
│ BIGINT UNSIGNED                 │                1 │ UInt64      │
│ BYTEA                           │                1 │ String      │
│ INT                             │                1 │ Int32       │
│ SINGLE                          │                1 │ Float32     │
│ MEDIUMINT SIGNED                │                1 │ Int32       │
│ DOUBLE                          │                1 │ Float64     │
│ INT1                            │                1 │ Int8        │
│ CHAR LARGE OBJECT               │                1 │ String      │
│ TINYINT                         │                1 │ Int8        │
│ BIGINT                          │                1 │ Int64       │
│ CHARACTER                       │                1 │ String      │
│ BYTE                            │                1 │ Int8        │
└─────────────────────────────────┴──────────────────┴─────────────┘

```

### 7.1.  数值类型

整数

```markdown
# 整数
UInt8, UInt16, UInt32, UInt64, UInt128, UInt256, Int8, Int16, Int32, Int64, Int128, Int256
Fixed-length integers, with or without a sign.

When creating tables, numeric parameters for integer numbers can be set (e.g. TINYINT(8), SMALLINT(16), INT(32), BIGINT(64)), but ClickHouse ignores them.

Int Ranges 支持负数
Int8 — [-128 : 127]
Int16 — [-32768 : 32767]
Int32 — [-2147483648 : 2147483647]
Int64 — [-9223372036854775808 : 9223372036854775807]
Int128 — [-170141183460469231731687303715884105728 : 170141183460469231731687303715884105727]
Int256 — [-57896044618658097711785492504343953926634992332820282019728792003956564819968 : 57896044618658097711785492504343953926634992332820282019728792003956564819967]

Aliases:

Int8 — TINYINT, INT1, BYTE, TINYINT SIGNED, INT1 SIGNED.
Int16 — SMALLINT, SMALLINT SIGNED.
Int32 — INT, INTEGER, MEDIUMINT, MEDIUMINT SIGNED, INT SIGNED, INTEGER SIGNED.
Int64 — BIGINT, SIGNED, BIGINT SIGNED, TIME.

UInt Ranges 不支持负数
UInt8 — [0 : 255]
UInt16 — [0 : 65535]
UInt32 — [0 : 4294967295]
UInt64 — [0 : 18446744073709551615]
UInt128 — [0 : 340282366920938463463374607431768211455]
UInt256 — [0 : 115792089237316195423570985008687907853269984665640564039457584007913129639935]

Aliases:

UInt8 — TINYINT UNSIGNED, INT1 UNSIGNED.
UInt16 — SMALLINT UNSIGNED.
UInt32 — MEDIUMINT UNSIGNED, INT UNSIGNED, INTEGER UNSIGNED
UInt64 — UNSIGNED, BIGINT UNSIGNED, BIT, SET


The equivalent types in ClickHouse and in C are given below:

# 注意，如果向UInt中存入负数，那么，数据并不是原来的负数，会改变值。
例如：
select toUInt8(-8);
248
```

浮点数

```markdown
# 浮点数
Float32, Float64
对于小数点后面的位数是有严格要求的，否则数据结果准确性就成问题。

note
If you need accurate calculations, in particular if you work with financial or business data requiring a high precision, you should consider using Decimal instead.

Floating Point Numbers might lead to inaccurate results as illustrated below:

CREATE TABLE IF NOT EXISTS float_vs_decimal
(
   my_float Float64,
   my_decimal Decimal64(3)
)
Engine=MergeTree
ORDER BY tuple();

# Generate 1 000 000 random numbers with 2 decimal places and store them as a float and as a decimal
INSERT INTO float_vs_decimal SELECT round(randCanonical(), 3) AS res, res FROM system.numbers LIMIT 1000000;

SELECT sum(my_float), sum(my_decimal) FROM float_vs_decimal;

┌──────sum(my_float)─┬─sum(my_decimal)─┐
│ 499693.60500000004 │      499693.605 │
└────────────────────┴─────────────────┘

SELECT sumKahan(my_float), sumKahan(my_decimal) FROM float_vs_decimal;

┌─sumKahan(my_float)─┬─sumKahan(my_decimal)─┐
│         499693.605 │           499693.605 │
└────────────────────┴──────────────────────┘

The equivalent types in ClickHouse and in C are given below:
Float32 — float.
Float64 — double.
Float types in ClickHouse have the following aliases:

Float32 — FLOAT, REAL, SINGLE.
Float64 — DOUBLE, DOUBLE PRECISION.
When creating tables, numeric parameters for floating point numbers can be set (e.g. FLOAT(12), FLOAT(15, 22), DOUBLE(12), DOUBLE(4, 18)), but ClickHouse ignores them.



# 样例
select toFloat32('0.1234567890123456789') as f32, toTypeName(f32);
┌────────f32─┬─toTypeName(toFloat32('0.1234567890123456789'))─┐
│ 0.12345679 │ Float32                                        │
└────────────┴────────────────────────────────────────────────┘
小数点位数中7位，第8位溢出

select toFloat64('0.1234567890123456789') as f64, toTypeName(f64);
┌─────────────────f64─┬─toTypeName(toFloat64('0.1234567890123456789'))─┐
│ 0.12345678901234568 │ Float64                                        │
└─────────────────────┴────────────────────────────────────────────────┘
小数点位数为16位，第17位溢出

Using Floating-point Numbers
Computations with floating-point numbers might produce a rounding error.
SELECT 1 - 0.9

┌───────minus(1, 0.9)─┐
│ 0.09999999999999998 │
└─────────────────────┘

The result of the calculation depends on the calculation method (the processor type and architecture of the computer system).
Floating-point calculations might result in numbers such as infinity (Inf) and “not-a-number” (NaN). This should be taken into account when processing the results of calculations.
When parsing floating-point numbers from text, the result might not be the nearest machine-representable number.



## NaN and Inf
In contrast to standard SQL, ClickHouse supports the following categories of floating-point numbers:

Inf – Infinity.
SELECT 0.5 / 0

┌─divide(0.5, 0)─┐
│            inf │
└────────────────┘
正无穷

-Inf — Negative infinity.
SELECT -0.5 / 0

┌─divide(-0.5, 0)─┐
│            -inf │
└─────────────────┘
负无穷

NaN — Not a number.
SELECT 0 / 0

┌─divide(0, 0)─┐
│          nan │
└──────────────┘
不是一个数字
See the rules for NaN sorting in the section ORDER BY clause.
```

货币类型-decimal

```markdown
Decimal, Decimal(P), Decimal(P, S), Decimal32(S), Decimal64(S), Decimal128(S), Decimal256(S)

Signed fixed-point numbers that keep precision during add, subtract and multiply operations. For division least significant digits are discarded (not rounded).

Parameters
P - precision. Valid range: [ 1 : 76 ]. Determines how many decimal digits number can have (including fraction). By default, the precision is 10.
S - scale. Valid range: [ 0 : P ]. Determines how many decimal digits fraction can have.
Decimal(P) is equivalent to Decimal(P, 0). Similarly, the syntax Decimal is equivalent to Decimal(10, 0).

Depending on P parameter value Decimal(P, S) is a synonym for:

P from [ 1 : 9 ] - for Decimal32(S)
P from [ 10 : 18 ] - for Decimal64(S)
P from [ 19 : 38 ] - for Decimal128(S)
P from [ 39 : 76 ] - for Decimal256(S)
Decimal Value Ranges
Decimal32(S) - ( -1 * 10^(9 - S), 1 * 10^(9 - S) )
Decimal64(S) - ( -1 * 10^(18 - S), 1 * 10^(18 - S) )
Decimal128(S) - ( -1 * 10^(38 - S), 1 * 10^(38 - S) )
Decimal256(S) - ( -1 * 10^(76 - S), 1 * 10^(76 - S) )
For example, Decimal32(4) can contain numbers from -99999.9999 to 99999.9999 with 0.0001 step.

Internal Representation
Internally data is represented as normal signed integers with respective bit width. Real value ranges that can be stored in memory are a bit larger than specified above, which are checked only on conversion from a string.

Because modern CPUs do not support 128-bit and 256-bit integers natively, operations on Decimal128 and Decimal256 are emulated. Thus, Decimal128 and Decimal256 work significantly slower than Decimal32/Decimal64.

Operations and Result Type
Binary operations on Decimal result in wider result type (with any order of arguments).

Decimal64(S1) <op> Decimal32(S2) -> Decimal64(S)
Decimal128(S1) <op> Decimal32(S2) -> Decimal128(S)
Decimal128(S1) <op> Decimal64(S2) -> Decimal128(S)
Decimal256(S1) <op> Decimal<32|64|128>(S2) -> Decimal256(S)
Rules for scale:

add, subtract: S = max(S1, S2).
multiply: S = S1 + S2.
divide: S = S1.
For similar operations between Decimal and integers, the result is Decimal of the same size as an argument.

Operations between Decimal and Float32/Float64 are not defined. If you need them, you can explicitly cast one of argument using toDecimal32, toDecimal64, toDecimal128 or toFloat32, toFloat64 builtins. Keep in mind that the result will lose precision and type conversion is a computationally expensive operation.

Some functions on Decimal return result as Float64 (for example, var or stddev). Intermediate calculations might still be performed in Decimal, which might lead to different results between Float64 and Decimal inputs with the same values.
```

使用样例：

加减法

```sh
select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x+y as z,toTypeName(z) as zType;
# 通过观察发现，当P输入不足时，默认为类型的最大长度。
# add, subtract: S = max(S1, S2).
# 加减法时，取两加数的最大位数
```

输出：

```sh

os21 :) select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x+y as z,toTypeName(z) as zType;

SELECT
    toDecimal64(2, 3) AS x,
    toTypeName(x) AS xType,
    toDecimal32(1, 2) AS y,
    toTypeName(y) AS yType,
    x + y AS z,
    toTypeName(z) AS zType

Query id: bbf60cf8-2392-4e13-8b1b-84b776b5bcd9

┌─x─┬─xType──────────┬─y─┬─yType─────────┬─z─┬─zType──────────┐
│ 2 │ Decimal(18, 3) │ 1 │ Decimal(9, 2) │ 3 │ Decimal(18, 3) │
└───┴────────────────┴───┴───────────────┴───┴────────────────┘

1 row in set. Elapsed: 0.001 sec. 
```

乘法

```sh
select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x*y as z,toTypeName(z) as zType;
# 通过观察发现，当P输入不足时，默认为类型的最大长度。
# multiply: S = S1 + S2.
# 乘法，取两个数据位数相加
```

输出:

```sh
os21 :) select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x*y as z,toTypeName(z) as zType;

SELECT
    toDecimal64(2, 3) AS x,
    toTypeName(x) AS xType,
    toDecimal32(1, 2) AS y,
    toTypeName(y) AS yType,
    x * y AS z,
    toTypeName(z) AS zType

Query id: 3942817f-f6e2-4906-bf86-4b7fe0f7c55f

┌─x─┬─xType──────────┬─y─┬─yType─────────┬─z─┬─zType──────────┐
│ 2 │ Decimal(18, 3) │ 1 │ Decimal(9, 2) │ 2 │ Decimal(18, 5) │
└───┴────────────────┴───┴───────────────┴───┴────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) 
```

除法

```sh
select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x/y as z,toTypeName(z) as zType;
# 通过观察发现，当P输入不足时，默认为类型的最大长度。
# divide: S = S1.
# 除法，使用被除数
```

输出

```sh
os21 :) select toDecimal64(2,3) as x,toTypeName(x) as xType,toDecimal32(1,2) as y,toTypeName(y) as yType,x/y as z,toTypeName(z) as zType;

SELECT
    toDecimal64(2, 3) AS x,
    toTypeName(x) AS xType,
    toDecimal32(1, 2) AS y,
    toTypeName(y) AS yType,
    x / y AS z,
    toTypeName(z) AS zType

Query id: d1cde916-18d3-4f9d-b634-6cc968b81d8b

┌─x─┬─xType──────────┬─y─┬─yType─────────┬─z─┬─zType──────────┐
│ 2 │ Decimal(18, 3) │ 1 │ Decimal(9, 2) │ 2 │ Decimal(18, 3) │
└───┴────────────────┴───┴───────────────┴───┴────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) 
```



### 7.2  布尔类型

```markdown
Bool
Type bool is internally stored as UInt8. Possible values are true (1), false (0).

select true as col, toTypeName(col);
┌─col──┬─toTypeName(true)─┐
│ true │ Bool             │
└──────┴──────────────────┘

select true == 1 as col, toTypeName(col);
┌─col─┬─toTypeName(equals(true, 1))─┐
│   1 │ UInt8                       │
└─────┴─────────────────────────────┘

CREATE TABLE test_bool
(
    `A` Int64,
    `B` Bool
)
ENGINE = Memory;

INSERT INTO test_bool VALUES (1, true),(2,0);

SELECT * FROM test_bool;
┌─A─┬─B─────┐
│ 1 │ true  │
│ 2 │ false │
└───┴───────┘
```

### 7.3 UUID

```markdown
UUID
A Universally Unique Identifier (UUID) is a 16-byte value used to identify records. For detailed information about UUIDs, see Wikipedia.

While different UUID variants exist (see here), ClickHouse does not validate that inserted UUIDs conform to a particular variant. UUIDs are internally treated as a sequence of 16 random bytes with 8-4-4-4-12 representation at SQL level.

Example UUID value:

61f0c404-5cb3-11e7-907b-a6006ad3dba0

The default UUID is all-zero. It is used, for example, when a new record is inserted but no value for a UUID column is specified:

00000000-0000-0000-0000-000000000000

Due to historical reasons, UUIDs are sorted by their second half. UUIDs should therefore not be used directly in a primary key, sorting key, or partition key of a table.
```

样例

```sh
create table data_type_uuid(uuid UUID,name String) ENGINE = Memory();
desc data_type_uuid;
insert into data_type_uuid(uuid,name) select generateUUIDv4() ,'nullnull';
select * from data_type_uuid;

# 如果不设置则使用00000000-0000-0000-0000-000000000000来填充
insert into data_type_uuid(name) select 'nullnull2';
select * from data_type_uuid;
```

输出：

```sh
os21 :) create table data_type_uuid(uuid UUID,name String) ENGINE = Memory();

CREATE TABLE data_type_uuid
(
    `uuid` UUID,
    `name` String
)
ENGINE = Memory

Query id: e80f5d28-84b8-413a-9a61-4a8c0fd9a5c7

Ok.

0 rows in set. Elapsed: 0.004 sec. 

os21 :) desc data_type_uuid;

DESCRIBE TABLE data_type_uuid

Query id: b9283419-df46-40ca-a3df-f03ab9f63e99

┌─name─┬─type───┬─default_type─┬─default_expression─┬─comment─┬─codec_expression─┬─ttl_expression─┐
│ uuid │ UUID   │              │                    │         │                  │                │
│ name │ String │              │                    │         │                  │                │
└──────┴────────┴──────────────┴────────────────────┴─────────┴──────────────────┴────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

os21 :) insert into data_type_uuid(uuid,name) select generateUUIDv4() ,'nullnull';

INSERT INTO data_type_uuid (uuid, name) SELECT
    generateUUIDv4(),
    'nullnull'

Query id: 25a80683-4ee6-489b-a70b-569541bc636d

Ok.

0 rows in set. Elapsed: 0.001 sec. 

os21 :) select * from data_type_uuid;

SELECT *
FROM data_type_uuid

Query id: cbd73b8d-5cce-4753-90e2-a1f822bdd710

┌─uuid─────────────────────────────────┬─name─────┐
│ c54ba77a-e845-413a-ad9e-a4d48343e9f4 │ nullnull │
└──────────────────────────────────────┴──────────┘

1 row in set. Elapsed: 0.001 sec. 
os21 :) insert into data_type_uuid(name) select 'nullnull2';

INSERT INTO data_type_uuid (name) SELECT 'nullnull2'

Query id: f4199d1d-6dbc-4de6-8756-ef72b7698533

Ok.

0 rows in set. Elapsed: 0.001 sec. 

os21 :) select * from data_type_uuid;

SELECT *
FROM data_type_uuid

Query id: c9611e50-c762-4fac-8937-3dbc5062361d

┌─uuid─────────────────────────────────┬─name─────┐
│ c54ba77a-e845-413a-ad9e-a4d48343e9f4 │ nullnull │
└──────────────────────────────────────┴──────────┘
┌─uuid─────────────────────────────────┬─name──────┐
│ 00000000-0000-0000-0000-000000000000 │ nullnull2 │
└──────────────────────────────────────┴───────────┘

2 rows in set. Elapsed: 0.001 sec. 
```

### 7.4 数组类型

```markdown
Array(T)
An array of T-type items, with the starting array index as 1. T can be any data type, including an array.
下标从1开始 .
```

使用数组

```sh
# 创建数组
select array(1,2) as x,toTypeName(x);
# 通过观察可以发现，数组的类型经过推导为UInt8

# 第二种定义方式
select [1,2] as x,toTypeName(x);

# 在array中存在null的情况
select array(1,2,null) as x ,toTypeName(x);


# 建表测试
create table datatype_array(id UInt8,bobby Array(String)) engine=Memory;
desc datatype_array;

insert into datatype_array values(1,['drink','eat','run']),(2,['sleep','game']);
select * from datatype_array;
# 数据下标从1开始，使用0会报错
select id,bobby[0] from datatype_array;
# 正确的获取方式
select id,bobby[1] from datatype_array;
# 获取数组元素个数
select id,bobby.size0 from datatype_array;
```

```sh
# 创建数组
os21 :) select array(1,2) as x,toTypeName(x);

SELECT
    [1, 2] AS x,
    toTypeName(x)

Query id: 91907fa4-89d0-4af7-beda-310a68c1b847

┌─x─────┬─toTypeName([1, 2])─┐
│ [1,2] │ Array(UInt8)       │
└───────┴────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 第二种定义方式
os21 :) select [1,2] as x,toTypeName(x);

SELECT
    [1, 2] AS x,
    toTypeName(x)

Query id: b06d0d7c-24a6-4340-92be-5c76c6c994a8

┌─x─────┬─toTypeName([1, 2])─┐
│ [1,2] │ Array(UInt8)       │
└───────┴────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 在array中存在null的情况
os21 :) select array(1,2,null) as x ,toTypeName(x);

SELECT
    [1, 2, NULL] AS x,
    toTypeName(x)

Query id: ba4c52a0-eb4e-421e-b579-67af0b3a87aa

┌─x──────────┬─toTypeName([1, 2, NULL])─┐
│ [1,2,NULL] │ Array(Nullable(UInt8))   │
└────────────┴──────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 建表测试
os21 :) create table datatype_array(id UInt8,bobby Array(String)) engine=Memory;

CREATE TABLE datatype_array
(
    `id` UInt8,
    `bobby` Array(String)
)
ENGINE = Memory

Query id: c71009b8-57bc-4ded-9c89-67500c6d0233

Ok.

0 rows in set. Elapsed: 0.002 sec. 

os21 :) desc datatype_array;

DESCRIBE TABLE datatype_array

Query id: 371d21c0-ac17-4035-90ce-4f8064c86812

┌─name──┬─type──────────┬─default_type─┬─default_expression─┬─comment─┬─codec_expression─┬─ttl_expression─┐
│ id    │ UInt8         │              │                    │         │                  │                │
│ bobby │ Array(String) │              │                    │         │                  │                │
└───────┴───────────────┴──────────────┴────────────────────┴─────────┴──────────────────┴────────────────┘

2 rows in set. Elapsed: 0.000 sec. 

os21 :) select * from datatype_array;

SELECT *
FROM datatype_array

Query id: 1b118634-363a-492d-822b-a120d6101731

┌─id─┬─bobby─────────────────┐
│  1 │ ['drink','eat','run'] │
│  2 │ ['sleep','game']      │
└────┴───────────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

os21 :) 
os21 :) select id,bobby[0] from datatype_array;

SELECT
    id,
    bobby[0]
FROM datatype_array

Query id: 11723a58-6b56-43ea-ac74-5dc7a71c1496


0 rows in set. Elapsed: 0.026 sec. 

Received exception from server (version 22.12.6):
Code: 135. DB::Exception: Received from localhost:9000. DB::Exception: Array indices are 1-based. (ZERO_ARRAY_OR_TUPLE_INDEX)

# 正确的获取方式
os21 :) select id,bobby[1] from datatype_array;

SELECT
    id,
    bobby[1]
FROM datatype_array

Query id: eb43d8e2-f495-45e1-8a43-4658f7fcc763

┌─id─┬─arrayElement(bobby, 1)─┐
│  1 │ drink                  │
│  2 │ sleep                  │
└────┴────────────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

# 获取数组元素个数
os21 :) select id,bobby.size0 from datatype_array;

SELECT
    id,
    bobby.size0
FROM datatype_array

Query id: aba57554-4248-4771-80bf-e3e9f2f68a24

┌─id─┬─bobby.size0─┐
│  1 │           3 │
│  2 │           2 │
└────┴─────────────┘

2 rows in set. Elapsed: 0.001 sec. 
```



### 7.5 Nullable类型

```markdown
Nullable(T)
Allows to store special marker (NULL) that denotes “missing value” alongside normal values allowed by T. For example, a Nullable(Int8) type column can store Int8 type values, and the rows that do not have a value will store NULL.

T can’t be any of the composite data types Array, Map and Tuple but composite data types can contain Nullable type values, e.g. Array(Nullable(Int8)).

A Nullable type field can’t be included in table indexes.

NULL is the default value for any Nullable type, unless specified otherwise in the ClickHouse server configuration.

Storage Features
To store Nullable type values in a table column, ClickHouse uses a separate file with NULL masks in addition to normal file with values. Entries in masks file allow ClickHouse to distinguish between NULL and a default value of corresponding data type for each table row. Because of an additional file, Nullable column consumes additional storage space compared to a similar normal one.

note
Using Nullable almost always negatively affects performance, keep this in mind when designing your databases.
使用NUll会对性能产生影响，特别注意。
```



### 7.6 Map类型

```markdown
Data type Map(K, V) stores key-value pairs.

Unlike other databases, maps are not unique in ClickHouse, i.e. a map can contain two elements with the same key. (The reason for that is that maps are internally implemented as Array(Tuple(K, V)).)

You can use use syntax m[k] to obtain the value for key k in map m. Also, m[k] scans the map, i.e. the runtime of the operation is linear in the size of the map.

Parameters

K — The type of the Map keys. Arbitrary type except Nullable and LowCardinality nested with Nullable types.
V — The type of the Map values. Arbitrary type.
```

样例

```sql
# 创建表
create table datatype_map(data Map(String,UInt64)) engine=Memory;

# 插入数据
insert into datatype_map values({'key1':1,'key1':20}),({'key1':2,'key2':30}),({'key1':3,'key2':40});

# 查询数据
select data['key1'] from datatype_map;

```

样例：

```sh
# 创建表
os21 :) create table datatype_map(data Map(String,UInt64)) engine=Memory;

CREATE TABLE datatype_map
(
    `data` Map(String, UInt64)
)
ENGINE = Memory

Query id: 9910343c-ae72-4b09-8a9c-81e26a21185d

Ok.

0 rows in set. Elapsed: 0.002 sec. 

# 插入数据
os21 :) insert into datatype_map values({'key1':1,'key1':20}),({'key1':2,'key2':30}),({'key1':3,'key2':40});

INSERT INTO datatype_map FORMAT Values

Query id: c18985a5-00dc-471f-a5a7-4e42bb459331

Ok.

3 rows in set. Elapsed: 0.001 sec. 

# 查询一个不存在的KEY
s21 :) select data['key'] from datatype_map;

SELECT data['key']
FROM datatype_map

Query id: d584d685-5412-4fb6-a374-345ea6afc835

┌─arrayElement(data, 'key')─┐
│                         0 │
│                         0 │
│                         0 │
└───────────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 

# 查询存在的数据
os21 :) select data['key1'] from datatype_map;

SELECT data['key1']
FROM datatype_map

Query id: 098e88a8-3aa4-477e-935b-3f51669fe55b

┌─arrayElement(data, 'key1')─┐
│                          1 │
│                          2 │
│                          3 │
└────────────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 

os21 :) 
```

### 7.7 String类型与FixedString(N)

String

```markdown
String
Strings of an arbitrary length. The length is not limited. The value can contain an arbitrary set of bytes, including null bytes. The String type replaces the types VARCHAR, BLOB, CLOB, and others from other DBMSs.
任意长度

When creating tables, numeric parameters for string fields can be set (e.g. VARCHAR(255)), but ClickHouse ignores them.

Aliases:

String — LONGTEXT, MEDIUMTEXT, TINYTEXT, TEXT, LONGBLOB, MEDIUMBLOB, TINYBLOB, BLOB, VARCHAR, CHAR, CHAR LARGE OBJECT, CHAR VARYING, CHARACTER LARGE OBJECT, CHARACTER VARYING, NCHAR LARGE OBJECT, NCHAR VARYING, NATIONAL CHARACTER LARGE OBJECT, NATIONAL CHARACTER VARYING, NATIONAL CHAR VARYING, NATIONAL CHARACTER, NATIONAL CHAR, BINARY LARGE OBJECT, BINARY VARYING,
Encodings
ClickHouse does not have the concept of encodings. Strings can contain an arbitrary set of bytes, which are stored and output as-is. If you need to store texts, we recommend using UTF-8 encoding. At the very least, if your terminal uses UTF-8 (as recommended), you can read and write your values without making conversions. Similarly, certain functions for working with strings have separate variations that work under the assumption that the string contains a set of bytes representing a UTF-8 encoded text. For example, the length function calculates the string length in bytes, while the lengthUTF8 function calculates the string length in Unicode code points, assuming that the value is UTF-8 encoded.
```

FixedString

```markdown
FixedString(N)
A fixed-length string of N bytes (neither characters nor code points).

To declare a column of FixedString type, use the following syntax:

<column_name> FixedString(N)

Where N is a natural number.

The FixedString type is efficient when data has the length of precisely N bytes. In all other cases, it is likely to reduce efficiency.

Examples of the values that can be efficiently stored in FixedString-typed columns:

The binary representation of IP addresses (FixedString(16) for IPv6).
Language codes (ru_RU, en_US ... ).
Currency codes (USD, RUB ... ).
Binary representation of hashes (FixedString(16) for MD5, FixedString(32) for SHA256).
To store UUID values, use the UUID data type.

When inserting the data, ClickHouse:

Complements a string with null bytes if the string contains fewer than N bytes.
Throws the Too large value for FixedString(N) exception if the string contains more than N bytes.
```

样例:

```sql
# 创建一个内存表
create table datatype_string(id UInt8,name String)engine=Memory;

# 查看表结构
desc datatype_string;

# 插入数据
insert into datatype_string values(1,'null1'),(2,'null2'),(3,'null3');

# 查询数据
select * from datatype_string;



# FixedString

# FixedString长度检查
select toFixedString('1',5) name,length(name) len;
#可以发现此固定占用5位.

# 长度检查对比
select toFixedString('null',6) name, name='null', length(name) len;
#可以发现长度不一样，但值相同，但是在有些片下，此结果是0，所有需要注意.



create table datatype_fixstring(id UInt8,name FixedString(5))engine=Memory;

desc datatype_fixstring;

# 插入数据测试
insert into datatype_fixstring values(1,'null'),(2,'null1');

select * from  datatype_fixstring;


insert into datatype_fixstring values(1,'1234567');

# 中文
insert into datatype_fixstring values(1,'中文');

# 检查长度
select length('中文');


```

样例:

```sh
# String创建表
os21 :) create table datatype_string(id UInt8,name String)engine=Memory;

CREATE TABLE datatype_string
(
    `id` UInt8,
    `name` String
)
ENGINE = Memory

Query id: 2288989c-a8f7-47db-b13c-f7c26688b425

Ok.

0 rows in set. Elapsed: 0.004 sec. 

# 查看表结构
os21 :) desc datatype_string;

DESCRIBE TABLE datatype_string

Query id: 01df6c2d-3f47-4d90-baa3-b52f604c7632

┌─name─┬─type───┬─default_type─┬─default_expression─┬─comment─┬─codec_expression─┬─ttl_expression─┐
│ id   │ UInt8  │              │                    │         │                  │                │
│ name │ String │              │                    │         │                  │                │
└──────┴────────┴──────────────┴────────────────────┴─────────┴──────────────────┴────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

# 插入数据
os21 :) insert into datatype_string values(1,'null1'),(2,'null2'),(3,'null3');

INSERT INTO datatype_string FORMAT Values

Query id: fc36f98c-dbb8-47f8-9da3-efc8354a3309

Ok.

3 rows in set. Elapsed: 0.001 sec. 

# 查询数据
os21 :) select * from datatype_string;

SELECT *
FROM datatype_string

Query id: 278f8f45-9793-4aa1-8706-b9194a72ceea

┌─id─┬─name──┐
│  1 │ null1 │
│  2 │ null2 │
│  3 │ null3 │
└────┴───────┘

3 rows in set. Elapsed: 0.001 sec. 

# FixedString长度检查
os21 :) select toFixedString('1',5) name,length(name) len;

SELECT
    toFixedString('1', 5) AS name,
    length(name) AS len

Query id: c2347102-54ba-4e1a-90c6-fd01271a60bf

┌─name─┬─len─┐
│ 1    │   5 │
└──────┴─────┘

1 row in set. Elapsed: 0.001 sec.

# FixedString长度检查及对比
os21 :) select toFixedString('null',6) name, name='null', length(name) len;

SELECT
    toFixedString('null', 6) AS name,
    name = 'null',
    length(name) AS len

Query id: c3f4c4a9-dbf4-4dd9-97ec-3ecdb26e9027

┌─name─┬─equals(toFixedString('null', 6), 'null')─┬─len─┐
│ null │                                        1 │   6 │
└──────┴──────────────────────────────────────────┴─────┘

1 row in set. Elapsed: 0.001 sec. 




# 固定长度建表
os21 :) create table datatype_fixstring(id UInt8,name FixedString(5))engine=Memory;

CREATE TABLE datatype_fixstring
(
    `id` UInt8,
    `name` FixedString(5)
)
ENGINE = Memory

Query id: 106299fc-1cac-44ab-b922-cfa9f5462a42

Ok.

0 rows in set. Elapsed: 0.002 sec. 

# 查看描述
os21 :) desc datatype_fixstring;

DESCRIBE TABLE datatype_fixstring

Query id: 1c19534b-9926-4b8f-a2c8-28bd8f961310

┌─name─┬─type───────────┬─default_type─┬─default_expression─┬─comment─┬─codec_expression─┬─ttl_expression─┐
│ id   │ UInt8          │              │                    │         │                  │                │
│ name │ FixedString(5) │              │                    │         │                  │                │
└──────┴────────────────┴──────────────┴────────────────────┴─────────┴──────────────────┴────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

# 插入数据
os21 :) insert into datatype_fixstring values(1,'null'),(2,'null1');

INSERT INTO datatype_fixstring FORMAT Values

Query id: f9711a1d-5b1d-4564-a646-7f6a38f4efc9

Ok.

2 rows in set. Elapsed: 0.001 sec. 

# 查询数据
os21 :) select * from  datatype_fixstring;

SELECT *
FROM datatype_fixstring

Query id: 9c603c2e-e72d-4ae1-b0b7-7bb15c9d3cc2

┌─id─┬─name──┐
│  1 │ null  │
│  2 │ null1 │
└────┴───────┘

2 rows in set. Elapsed: 0.001 sec. 

# 异常数据检查
os21 :) insert into datatype_fixstring values(1,'1234567');

INSERT INTO datatype_fixstring FORMAT Values

Query id: ef2ba8f3-e4f7-4455-8751-18fc338dc52c

Ok.
Exception on client:
Code: 131. DB::Exception: String too long for type FixedString(5): while executing 'FUNCTION if(isNull(_dummy_0) : 3, defaultValueOfTypeName('FixedString(5)') :: 2, _CAST(_dummy_0, 'FixedString(5)') :: 4) -> if(isNull(_dummy_0), defaultValueOfTypeName('FixedString(5)'), _CAST(_dummy_0, 'FixedString(5)')) FixedString(5) : 1': While executing ValuesBlockInputFormat: data for INSERT was parsed from query. (TOO_LARGE_STRING_SIZE)

os21 :) insert into datatype_fixstring values(1,'中文');

INSERT INTO datatype_fixstring FORMAT Values

Query id: b7911809-b1ea-46d6-b1b2-76dd5fb1c92d

Ok.
Exception on client:
Code: 131. DB::Exception: String too long for type FixedString(5): while executing 'FUNCTION if(isNull(_dummy_0) : 3, defaultValueOfTypeName('FixedString(5)') :: 2, _CAST(_dummy_0, 'FixedString(5)') :: 4) -> if(isNull(_dummy_0), defaultValueOfTypeName('FixedString(5)'), _CAST(_dummy_0, 'FixedString(5)')) FixedString(5) : 1': While executing ValuesBlockInputFormat: data for INSERT was parsed from query. (TOO_LARGE_STRING_SIZE)

os21 :) select length('中文');

SELECT length('中文')

Query id: 58032d0b-aef7-494b-a496-041faa31bedd

┌─length('中文')─┐
│              6 │
└────────────────┘

1 row in set. Elapsed: 0.001 sec. 
```



## 8. ClickHouse内置函数



### 8.1 算术函数

```sh
https://clickhouse.com/docs/en/sql-reference/functions/arithmetic-functions
```

描述

```sh
Arithmetic Functions
Arithmetic functions work for any two operands of type UInt8, UInt16, UInt32, UInt64, Int8, Int16, Int32, Int64, Float32, or Float64.

Before performing the operation, both operands are casted to the result type. The result type is determined as follows (unless specified differently in the function documentation below):

If both operands are up to 32 bits wide, the size of the result type will be the size of the next bigger type following the bigger of the two operands (integer size promotion). For example, UInt8 + UInt16 = UInt32 or Float32 * Float32 = Float64.
If one of the operands has 64 or more bits, the size of the result type will be the same size as the bigger of the two operands. For example, UInt32 + UInt128 = UInt128 or Float32 * Float64 = Float64.
If one of the operands is signed, the result type will also be signed, otherwise it will be signed. For example, UInt32 * Int32 = Int64.
These rules make sure that the result type will be the smallest type which can represent all possible results. While this introduces a risk of overflows around the value range boundary, it ensures that calculations are performed quickly using the maximum native integer width of 64 bit. This behavior also guarantees compatibility with many other databases which provide 64 bit integers (BIGINT) as the biggest integer type.

```

样例

```sh
select toTypeName(0),toTypeName(0+0),toTypeName(0+0+0),toTypeName(0+0+0+0);
```

输出:

```sh
os21 :) select toTypeName(0),toTypeName(0+0),toTypeName(0+0+0),toTypeName(0+0+0+0);

SELECT
    toTypeName(0),
    toTypeName(0 + 0),
    toTypeName((0 + 0) + 0),
    toTypeName(((0 + 0) + 0) + 0)

Query id: ac35f387-e080-4785-8d0a-838b1eccfcf5

┌─toTypeName(0)─┬─toTypeName(plus(0, 0))─┬─toTypeName(plus(plus(0, 0), 0))─┬─toTypeName(plus(plus(plus(0, 0), 0), 0))─┐
│ UInt8         │ UInt16                 │ UInt32                          │ UInt64                                   │
└───────────────┴────────────────────────┴─────────────────────────────────┴──────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 
```

样例

```sh
# 加法运算
select plus(12,21),plus(10,-10),plus(-10,-10);

# 减法运算
select minus(12,2),minus(10,-10),minus(-10,-10);

# 乘法运算
select multiply(12,2),multiply(12,-2),multiply(-12,-3);

# 除法运算
select divide(12,4),divide(10,3),divide(2,4);
select divide(-4,-2),divide(-4,2),divide(-4.5,3);

# 除0操作
select divide(0,0);
select divide(10,0), divide(-10,0);

# intDivOrZero
select intDivOrZero(1,0);
select intDivOrZero(1,0.01);
```

输出:

```sh
# 加法运算
os21 :) select plus(12,21),plus(10,-10),plus(-10,-10);

SELECT
    12 + 21,
    10 + -10,
    -10 + -10

Query id: becc2fed-e597-4998-8d12-b3e6e2e2da4d

┌─plus(12, 21)─┬─plus(10, -10)─┬─plus(-10, -10)─┐
│           33 │             0 │            -20 │
└──────────────┴───────────────┴────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 减法运算
os21 :) select minus(12,2),minus(10,-10),minus(-10,-10);

SELECT
    12 - 2,
    10 - -10,
    -10 - -10

Query id: 6165ad3f-56b9-4819-8b02-51cebdafe412

┌─minus(12, 2)─┬─minus(10, -10)─┬─minus(-10, -10)─┐
│           10 │             20 │               0 │
└──────────────┴────────────────┴─────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 乘法运算
os21 :) select multiply(12,2),multiply(12,-2),multiply(-12,-3);

SELECT
    12 * 2,
    12 * -2,
    -12 * -3

Query id: 75d4913d-b466-40b9-995d-774311abf68e

┌─multiply(12, 2)─┬─multiply(12, -2)─┬─multiply(-12, -3)─┐
│              24 │              -24 │                36 │
└─────────────────┴──────────────────┴───────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 除法运算
os21 :) select divide(12,4),divide(10,3),divide(2,4);

SELECT
    12 / 4,
    10 / 3,
    2 / 4

Query id: 7165df27-8f1f-4516-b2be-d7dab7d19fad

┌─divide(12, 4)─┬──────divide(10, 3)─┬─divide(2, 4)─┐
│             3 │ 3.3333333333333335 │          0.5 │
└───────────────┴────────────────────┴──────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) select divide(-4,-2),divide(-4,2),divide(-4.5,3);

SELECT
    -4 / -2,
    -4 / 2,
    -4.5 / 3

Query id: 776afc4d-ae48-43db-acb3-aa51de9e561e

┌─divide(-4, -2)─┬─divide(-4, 2)─┬─divide(-4.5, 3)─┐
│              2 │            -2 │            -1.5 │
└────────────────┴───────────────┴─────────────────┘

1 row in set. Elapsed: 0.001 sec.

# 除0操作
os21 :) select divide(0,0);

SELECT 0 / 0

Query id: e582862b-3956-412b-ae27-38e601e97894

┌─divide(0, 0)─┐
│          nan │
└──────────────┘

1 row in set. Elapsed: 0.001 sec.


os21 :) select divide(10,0), divide(-10,0);

SELECT
    10 / 0,
    -10 / 0

Query id: dece3e76-2012-47a1-a622-64657ab24f60

┌─divide(10, 0)─┬─divide(-10, 0)─┐
│           inf │           -inf │
└───────────────┴────────────────┘

1 row in set. Elapsed: 0.001 sec.

# 除法结果为0或者其他正常值
os21 :) select intDivOrZero(1,0);

SELECT intDivOrZero(1, 0)

Query id: 7319cfe0-70ac-48ce-b159-6f2c6380bd8d

Connecting to localhost:9000 as user default.
Connected to ClickHouse server version 22.12.6 revision 54461.

┌─intDivOrZero(1, 0)─┐
│                  0 │
└────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 除法结果为0或者其他正常值
os21 :) select intDivOrZero(1,1);

SELECT intDivOrZero(1, 1)

Query id: cec5a630-65e8-4455-b4e6-7edcc03fa90b

┌─intDivOrZero(1, 1)─┐
│                  1 │
└────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) select intDivOrZero(0,1);

SELECT intDivOrZero(0, 1)

Query id: bd3b0529-be50-4cb0-a3e4-a710074fa06d

┌─intDivOrZero(0, 1)─┐
│                  0 │
└────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 除法结果为0或者其他正常值
os21 :) select intDivOrZero(1,0.01);

SELECT intDivOrZero(1, 0.01)

Query id: e9c5b522-a31f-405b-98b7-c2ba7bfb66eb

┌─intDivOrZero(1, 0.01)─┐
│                   100 │
└───────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) SELECT   intDiv(1, 0.001) AS res;
        

# 除法直接除于小数，则报错
SELECT intDiv(1, 0.001) AS res

Query id: 9560c3c3-e141-471d-9b81-9ba1c5674703


0 rows in set. Elapsed: 0.052 sec. 

Received exception from server (version 22.12.6):
Code: 153. DB::Exception: Received from localhost:9000. DB::Exception: Cannot perform integer division, because it will produce infinite or too large number: While processing intDiv(1, 0.001) AS res. (ILLEGAL_DIVISION)

os21 :) 
```



### 8.2 比较函数

```sh
https://clickhouse.com/docs/en/sql-reference/functions/comparison-functions
```

介绍

```markdown
Comparison Functions
Below comparison functions return 0 or 1 as Uint8.

The following types can be compared:

numbers
strings and fixed strings
dates
dates with times
Only values within the same group can be compared (e.g. UInt16 and UInt64) but not across groups (e.g. UInt16 and DateTime).

Strings are compared byte-by-byte. Note that this may lead to unexpected results if one of the strings contains UTF-8 encoded multi-byte characters.

A string S1 which has another string S2 as prefix is considered longer than S2.

```

函数

```sh
# 等于
# equals, =, == operators
equals(a, b)

# 不等于
# notEquals, !=, <> operators
notEquals(a, b)


# 小于函数
# less, < operator
less(a, b)
```

样例

```markdown
# 等于函数
os21 :) select equals(1,1), 1=1,toDateTime('2024-08-01') == toDateTime('2024-08-01');

SELECT
    1 = 1,
    1 = 1,
    toDateTime('2024-08-01') = toDateTime('2024-08-01')

Query id: a5cd3127-1826-49c7-8e35-e6629b9b2fe4

┌─equals(1, 1)─┬─equals(1, 1)─┬─equals(toDateTime('2024-08-01'), toDateTime('2024-08-01'))─┐
│            1 │            1 │                                                          1 │
└──────────────┴──────────────┴────────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 不等于函数
os21 :) select notEquals(1, 1), 1 != 2,toDateTime('2024-08-01') <> toDateTime('2024-08-01');

SELECT
    1 != 1,
    1 != 2,
    toDateTime('2024-08-01') != toDateTime('2024-08-01')

Query id: 26bf494f-b510-4769-8f0e-e8a27c911558

┌─notEquals(1, 1)─┬─notEquals(1, 2)─┬─notEquals(toDateTime('2024-08-01'), toDateTime('2024-08-01'))─┐
│               0 │               1 │                                                             0 │
└─────────────────┴─────────────────┴───────────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 


# 小于函数
os21 :) select less(1,2),toDateTime('2024-08-01') < toDateTime('2024-08-02');

SELECT
    1 < 2,
    toDateTime('2024-08-01') < toDateTime('2024-08-02')

Query id: feaef79c-e64b-4b7d-a40f-68c9670d8c25

┌─less(1, 2)─┬─less(toDateTime('2024-08-01'), toDateTime('2024-08-02'))─┐
│          1 │                                                        1 │
└────────────┴──────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 


```



### 8.3 逻辑函数

```sh
https://clickhouse.com/docs/en/sql-reference/functions/logical-functions
```

样例

```sh
# and
# 非0即为true
select and(0,1,2);
select and(1,1,2);

# or
# 有一个满足即为1
SELECT or(1, 0, 0, 2, NULL,-1);

# not 
# Returned value
# 1, if val evaluates to false,
# 0, if val evaluates to true,
# NULL, if val is NULL.
select not(1),not(0),not(-1),not(null);
```



输出

```sh
# and
os21 :) select and(0,1,2);

SELECT 0 AND 1 AND 2

Query id: e040536e-955c-4822-900e-9687703b28aa

┌─and(0, 1, 2)─┐
│            0 │
└──────────────┘

1 row in set. Elapsed: 0.003 sec. 

os21 :) select and(1,1,2);

SELECT 1 AND 1 AND 2

Query id: 622ff6b7-835d-408d-887c-c166e80c5044

┌─and(1, 1, 2)─┐
│            1 │
└──────────────┘

1 row in set. Elapsed: 0.001 sec.


# or
os21 :) SELECT or(1, 0, 0, 2, NULL,-1);

SELECT 1 OR 0 OR 0 OR 2 OR NULL OR -1

Query id: 0ef4b8be-a130-4dff-a407-198e7a328b76

┌─or(1, 0, 0, 2, NULL, -1)─┐
│                        1 │
└──────────────────────────┘

1 row in set. Elapsed: 0.001 sec.

# not 
os21 :) select not(1),not(0),not(-1),not(null);

SELECT
    NOT 1,
    NOT 0,
    NOT -1,
    NOT NULL

Query id: a6b7a741-bfcb-45c7-8fae-4ace642919dc

┌─not(1)─┬─not(0)─┬─not(-1)─┬─not(NULL)─┐
│      0 │      1 │       0 │ ᴺᵁᴸᴸ      │
└────────┴────────┴─────────┴───────────┘

1 row in set. Elapsed: 0.001 sec. 
```





### 8.4 取整函数

官网API地址：

```markdown
https://clickhouse.com/docs/en/sql-reference/functions/rounding-functions
```

```sql
-- 保留小数位数，向下取整
select floor(123.12,1);

-- 向上取整
select ceil(123.12,1);


-- 四舍五入
select round(123.56,1);
select round(123.42,1);
```

样例:

```sh
#  保留小数位数,向下取整
os21 :) select floor(123.12,1);

SELECT floor(123.12, 1)

Query id: 13550082-5675-4d29-a93f-3a3efcf902e3

┌─floor(123.12, 1)─┐
│            123.1 │
└──────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 向上取整
os21 :) select ceil(123.12,1);

SELECT ceil(123.12, 1)

Query id: 14d687c3-eda7-48c4-a0d8-b982dc36fb7b

┌─ceil(123.12, 1)─┐
│           123.2 │
└─────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 四舍五入
os21 :) select round(123.56,1);

SELECT round(123.56, 1)

Query id: dc02a013-8513-4b47-be55-c1cc8cc74a9b

┌─round(123.56, 1)─┐
│            123.6 │
└──────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) select round(123.42,1);

SELECT round(123.42, 1)

Query id: cfbe37ec-1a9c-402b-b2c9-5e0b4eb50b33

┌─round(123.42, 1)─┐
│            123.4 │
└──────────────────┘

1 row in set. Elapsed: 0.001 sec. 

```



### 8.5 转换函数

官网地址:

```sh
https://clickhouse.com/docs/en/sql-reference/functions/type-conversion-functions
```

样例：

```sh
# 转换为int8
select toInt8(8), toInt8(-8),toInt8('-8');

# 转换为DateTime
SELECT toDateTime('2024-08-02 13:44:17') as outTime, toDateTime(outTime, 'UTC');

# 转换为Datetime64
SELECT toDateTime64('2024-08-02 12:49:10.1234567890123456789',9) AS value, toTypeName(value);

# 格式化时间
SELECT parseDateTime('2024-08-02 12:49:10', '%Y-%M-%D %H:%m:%s')



# ToString函数
select toString('2024-08-02 12:57:49','Asia/Shanghai') ;
select toString('2024-08-02 12:57:49','UTC');

SELECT
    now() AS ts,
    time_zone as outzone,
    toString(ts, outzone) AS str_tz_datetime
FROM system.time_zones
WHERE time_zone LIKE 'Asia%'
LIMIT 10;




SELECT
    now() AS ts,
    time_zone
FROM system.time_zones
WHERE time_zone LIKE 'Asia%'
LIMIT 10

```

输出:

```sh
# 转换为int8
os21 :) select toInt8(8), toInt8(-8),toInt8('-8')

SELECT
    toInt8(8),
    toInt8(-8),
    toInt8('-8')

Query id: 961db50a-6123-4b87-b697-d4420359e42f

┌─toInt8(8)─┬─toInt8(-8)─┬─toInt8('-8')─┐
│         8 │         -8 │           -8 │
└───────────┴────────────┴──────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 转换为DateTime
os21 :) SELECT toDateTime('2024-08-02 13:44:17') as outTime, toDateTime(outTime, 'UTC');

SELECT
    toDateTime('2024-08-02 13:44:17') AS outTime,
    toDateTime(outTime, 'UTC')

Query id: 51eda2b7-5a04-4dc3-b8aa-15f14700ddec

┌─────────────outTime─┬─toDateTime(toDateTime('2024-08-02 13:44:17'), 'UTC')─┐
│ 2024-08-02 13:44:17 │                                  2024-08-02 05:44:17 │
└─────────────────────┴──────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 


# 转换为Datetime64
os21 :) SELECT toDateTime64('2024-08-02 12:49:10.1234567890123456789',9) AS value, toTypeName(value);

SELECT
    toDateTime64('2024-08-02 12:49:10.1234567890123456789', 9) AS value,
    toTypeName(value)

Query id: 615a7525-c3fc-4c3b-9443-2e3a44cfd747

┌─────────────────────────value─┬─toTypeName(toDateTime64('2024-08-02 12:49:10.1234567890123456789', 9))─┐
│ 2024-08-02 12:49:10.123456789 │ DateTime64(9)                                                          │
└───────────────────────────────┴────────────────────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

os21 :) 
```



### 8.6 逻辑函数

```sh
https://clickhouse.com/docs/en/sql-reference/functions/conditional-functions
```

函数

```sh
# if
if(cond, then, else)

# 多条件函数
multiIf(cond_1, then_1, cond_2, then_2, ..., else)

# null 值条件 
```

输出

```sh
# if函数
os21 :) select if(1,'is ok','is error');

SELECT if(1, 'is ok', 'is error')

Query id: 7a873ee8-6ed6-4479-923e-12caeea5c067

┌─'is ok'─┐
│ is ok   │
└─────────┘

1 row in set. Elapsed: 0.001 sec. 


# 多条件函数
os21 :) select multiIf(data.left < data.right, '<', data.left > data.right, '>',  '=')
        from (
        (select 2 as left, 1 as  right from numbers(1))
        UNION ALL
        (select 1 as left, 4 as  right  from numbers(1) )
        UNION  ALL
        (select null as left, 4 as  right  from numbers(1))
        ) data;

SELECT multiIf(data.left < data.right, '<', data.left > data.right, '>', '=')
FROM
(

        SELECT
        2 AS left,
        1 AS right
    FROM numbers(1)
    UNION ALL
        SELECT
        1 AS left,
        4 AS right
    FROM numbers(1)
    UNION ALL
        SELECT
        NULL AS left,
        4 AS right
    FROM numbers(1)
) AS data

Query id: f7e8d574-5224-441b-8363-5848f28b7a13

┌─multiIf(less(left, right), '<', greater(left, right), '>', '=')─┐
│ <                                                               │
└─────────────────────────────────────────────────────────────────┘
┌─multiIf(less(left, right), '<', greater(left, right), '>', '=')─┐
│ >                                                               │
└─────────────────────────────────────────────────────────────────┘
┌─multiIf(less(left, right), '<', greater(left, right), '>', '=')─┐
│ =                                                               │
└─────────────────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 


# 空值条件
os21 :) SELECT
            NULL < 1,
            2 < NULL,
            NULL < NULL,
            NULL = NULL;

SELECT
    NULL < 1,
    2 < NULL,
    NULL < NULL,
    NULL = NULL

Query id: 058be9d4-2454-4183-b22c-501cb0127fdc

┌─less(NULL, 1)─┬─less(2, NULL)─┬─less(NULL, NULL)─┬─equals(NULL, NULL)─┐
│ ᴺᵁᴸᴸ          │ ᴺᵁᴸᴸ          │ ᴺᵁᴸᴸ             │ ᴺᵁᴸᴸ               │
└───────────────┴───────────────┴──────────────────┴────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

```





## 9. ClickHouseDDL

### 9.1 插入数据

关于CK的数据格式：

```sh
https://clickhouse.com/docs/en/sql-reference/formats
```



创建的表

```sql
CREATE TABLE ddl_user
(
    `id` UInt8,
    `name` String,
    `address` String
)
ENGINE = TinyLog
```



方式1：普通的SQL插入

```sql
insert into ddl_user(id,name,address)
values(1,'张三','上海'),
(2,'李四','北京'),
(3,'王五','深圳');
```



方式2： 使用CSV文件导入

```sql
-- 1.准备数据文件 data.csv
10,小刘,上海
20,小曾,上海
30,小辰,上海

-- 使用命令导入
cat  data.csv | clickhouse-client --query "INSERT INTO nullnull.ddl_user FORMAT CSV" --max_insert_block_size=100000 --user maxwell --password 
```

方式3：

当大批量导出时，推荐此方式

```sql
INSERT INTO his_wafer FROM INFILE '/var/log/clickhouse-server/data.native' FORMAT Native;
```





### 9.9 分区操作

分区在于减少查询读取数据的条数

在ck中需要使用mergeTree引擎

分区操作的命令

```sh
# 分区装载
ALTER TABLE ${tableName}  ATTACH PARTITION ${partition}

# 分区卸载
ALTER TABLE ${tableName}   DROP PARTITION ${partition}
```

分区的样例,

分区表的创建与加载数据

```sh

# 创建分区表
create table nullnull_partition_01(
	id UInt8,
	name String,
	birthday DateTime
)engine = MergeTree()
partition by toDate(birthday)
order by id;

# 插入数据
insert into nullnull_partition_01(id,name,birthday)
values(1,'小刘','2024-10-25 12:46:00'),
(2,'小曾','2024-10-23 12:46:00'),
(3,'小丽','2024-10-22 12:46:00');


# 查询数据
select * from nullnull_partition_01;

```

日志输出：

```sh
mwrpt-clickhouse :) create table nullnull_partition_01(
^Iid UInt8,
^Iname String,
^Ibirthday DateTime
)engine = MergeTree()
partition by toDate(birthday)
order by id;

CREATE TABLE nullnull_partition_01
(
    `id` UInt8,
    `name` String,
    `birthday` DateTime
)
ENGINE = MergeTree
PARTITION BY toDate(birthday)
ORDER BY id

Query id: 779d4e41-8850-4746-adb9-32651a150c2a

Ok.

0 rows in set. Elapsed: 0.019 sec. 

mwrpt-clickhouse :) insert into nullnull_partition_01(id,name,birthday)
values(1,'小刘','2024-10-25 12:46:00'),
(2,'小曾','2024-10-23 12:46:00'),
(3,'小丽','2024-10-22 12:46:00');

INSERT INTO nullnull_partition_01 (id, name, birthday) FORMAT Values

Query id: b6f680c3-76e5-4070-a963-e3b3650a9571

Ok.

3 rows in set. Elapsed: 0.003 sec. 

mwrpt-clickhouse :) select * from nullnull_partition_01;

SELECT *
FROM nullnull_partition_01

Query id: ea7664a4-cd46-4e47-a51c-7cc1ad2c3340

┌─id─┬─name─┬────────────birthday─┐
│  3 │ 小丽 │ 2024-10-22 12:46:00 │
└────┴──────┴─────────────────────┘
┌─id─┬─name─┬────────────birthday─┐
│  1 │ 小刘 │ 2024-10-25 12:46:00 │
└────┴──────┴─────────────────────┘
┌─id─┬─name─┬────────────birthday─┐
│  2 │ 小曾 │ 2024-10-23 12:46:00 │
└────┴──────┴─────────────────────┘

3 rows in set. Elapsed: 0.002 sec. 

mwrpt-clickhouse :) 
```



分区表信息

```sql
select * from system.parts;
```

详细的分区信息

```sql
a5e651989f3b :) select database,table,name,partition_id from system.parts where table ='nullnull_partition_01'

SELECT
    database,
    table,
    name,
    partition_id
FROM system.parts
WHERE table = 'nullnull_partition_01'

Query id: 9d3b9be5-d2b8-4189-b979-1e81546e4fa2

┌─database─┬─table─────────────────┬─name───────────┬─partition_id─┐
│ default  │ nullnull_partition_01 │ 20241022_3_3_0 │ 20241022     │
│ default  │ nullnull_partition_01 │ 20241023_2_2_0 │ 20241023     │
│ default  │ nullnull_partition_01 │ 20241025_1_1_0 │ 20241025     │
└──────────┴───────────────────────┴────────────────┴──────────────┘

3 rows in set. Elapsed: 0.004 sec. 

a5e651989f3b :) 

```







## 10  多维数据分析

```sql
# 在传统的统计中，存在一种分析。比按A和B进行分组。
select XXX from xxx group by a,b
union all 
select XXX from xxx group by a
union all
select xxx from xxx group by b
union all
select xxx from xxx
# 将这种4种场景聚合在一起进行分析。

# 维度a,b
# rollup 上卷，从每一个维度开始，不能跳过
	# group by  统计所有
	# group by a
	# group by a,b
# cube : 多维分析,所有情况都会有
	# group by a,b
	# group by a
	# group by b
	# group by  统计所有
# total ： 总计 
	# group by a,b
	# group by  统计所有
	
	
	
```







## CK常用SQL

统计表的空间占用

```sql
SELECT
	sum(rows) AS `总行数`,
	sum(data_uncompressed_bytes) AS `原始大小-Byes`,
	formatReadableSize(sum(data_uncompressed_bytes)) AS `原始大小`,
	sum(data_compressed_bytes) AS `压缩大小-bytes`,
	formatReadableSize(sum(data_compressed_bytes)) AS `压缩大小`,
	round((sum(data_compressed_bytes) / sum(data_uncompressed_bytes)) * 100,
	0) AS `压缩率`,
	`table` AS `表名`
FROM
	system.parts
where
	database = 'maxwell_mes'
--and `table` = 'his_wafer_sorting'
and `table` not like  'ods_%'
group by
	`table`
order by `总行数`
```







## 结束

