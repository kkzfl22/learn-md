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

# 相关的数据地址
https://datasets.clickhouse.com/hits/tsv/hits_v1.tsv.xz (802MB)
https://datasets.clickhouse.com/visits/tsv/visits_v1.tsv.xz (405MB)
```





## 结束

