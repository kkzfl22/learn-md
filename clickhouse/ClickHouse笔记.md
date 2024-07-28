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

-Inf — Negative infinity.
SELECT -0.5 / 0

┌─divide(-0.5, 0)─┐
│            -inf │
└─────────────────┘

NaN — Not a number.
SELECT 0 / 0

┌─divide(0, 0)─┐
│          nan │
└──────────────┘

See the rules for NaN sorting in the section ORDER BY clause.
```

















## 结束

