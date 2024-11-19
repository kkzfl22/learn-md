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

![Row-oriented](.\images\row-oriented-3e6fd5aa48e3075202d242b4799da8fa.gif)

**Column-oriented DBMS**

![Column-oriented](.\images\column-oriented-d082e49b7743d4ded32c7952bfdb028f.gif)

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

firewall-cmd --permanent --zone=public --add-port=8123/tcp
firewall-cmd --reload



firewall-cmd --permanent --zone=public --add-port=9000/tcp
firewall-cmd --reload
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

clickhouse TGZ安装方式卸载

```sh
# 1．停止ClickHouse服务
sudo service clickhouse-server stop

# 2．删除所有的数据和日志文件
sudo rm -rf /var/lib/clickhouse/
sudo rm -rf /var/log/clickhouse-server/

# 3．如果你使用的是systemd来管理服务，你可以通过以下命令来停止并删除服务
sudo systemctl stop clickhouse-server
sudo systemctl disable clickhouse-server
sudo systemctl daemon-reload

# 4．删除所有的配置文件
sudo rm -rf /etc/clickhouse-server/
sudo rm -rf /etc/clickhouse-client/
sudo rm -rf /etc/systemd/system/clickhouse-server.service

# 5．删除所有的启动文件
sudo rm -rf  /usr/bin/clickhouse*
sudo rm -rf  /usr/share/bash-completion/completions/clickhouse*
sudo rm -rf  /usr/share/doc/clickhouse*

# 6．清理残留的用户和用户组
sudo userdel -r clickhouse
sudo groupdel clickhouse

# 7．查找是否卸载干净（这一步很关键，否则重装可能无法启动）
sudo  find  / -name clickhouse*


rm -rf /run/lock/clickhouse-server
rm -rf /etc/rc.d/init.d/clickhouse-server
rm -rf /etc/security/limits.d/clickhouse.conf
rm -rf /usr/lib/debug/usr/bin/clickhouse-library-bridge.debug
rm -rf /usr/lib/debug/usr/bin/clickhouse.debug
rm -rf /usr/lib/debug/usr/bin/clickhouse-odbc-bridge.debug
rm -rf /usr/lib/systemd/system/clickhouse-server.service
```







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
# docker pull dockerpull.com/clickhouse/clickhouse-server:22.6.3.35
docker pull dockerpull.com/clickhouse/clickhouse-server:24.2.1.2248

docker run -d -p 18123:8123 -p19000:9000  \
     -v /opt/nullnull/clickhouse/data:/var/lib/clickhouse/:z \
     -v /opt/nullnull/clickhouse/log:/var/log/clickhouse-server/:z \
     -v /opt/nullnull/clickhouse/config/config.d:/etc/clickhouse-server/config.d/:z \
     -v /opt/nullnull/clickhouse/config/users.d:/etc/clickhouse-server/users.d/:z \
     --name some-clickhouse-server  \
     --ulimit nofile=262144:262144 \
     dockerpull.com/clickhouse/clickhouse-server:24.2.1.2248


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

MergeTree数据目录结构

```sh
[root@mes01 6f281bfb-015f-4957-a58c-ee87927e1db7]# tree .
.
├── 20241025_2_4_1
│   ├── checksums.txt
│   ├── columns.txt
│   ├── count.txt
│   ├── data.bin
│   ├── data.cmrk3
│   ├── default_compression_codec.txt
│   ├── metadata_version.txt
│   ├── minmax_create_time.idx
│   ├── partition.dat
│   ├── primary.cidx
│   └── serialization.json
├── 20241027_1_3_1
│   ├── checksums.txt
│   ├── columns.txt
│   ├── count.txt
│   ├── data.bin
│   ├── data.cmrk3
│   ├── default_compression_codec.txt
│   ├── metadata_version.txt
│   ├── minmax_create_time.idx
│   ├── partition.dat
│   ├── primary.cidx
│   └── serialization.json
├── detached
└── format_version.txt

root@mes01 6f281bfb-015f-4957-a58c-ee87927e1db7]# ll
total 4
drwxr-x---. 2 101 101 259 Oct 27 22:44 20241025_2_4_1
drwxr-x---. 2 101 101 259 Oct 27 22:44 20241027_1_3_1
drwxr-x---. 2 101 101   6 Oct 27 22:43 detached
-rw-r-----. 1 101 101   1 Oct 27 22:43 format_version.txt
[root@mes01 6f281bfb-015f-4957-a58c-ee87927e1db7]# cd 20241025_2_4_1/
[root@mes01 20241025_2_4_1]# ll
total 44
-rw-r-----. 1 101 101 333 Oct 27 22:44 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 22:44 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 22:44 count.txt
-rw-r-----. 1 101 101 186 Oct 27 22:44 data.bin
-rw-r-----. 1 101 101  70 Oct 27 22:44 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 22:44 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 22:44 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 22:44 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 22:44 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 22:44 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 22:44 serialization.json

# 数据行数
[root@mes01 20241025_2_4_1]# cat count.txt 
2[root@mes01 20241025_2_4_1]# 
# 列信息
[root@mes01 20241025_2_4_1]# cat columns.txt
columns format version: 1
5 columns:
`id` UInt32
`order_id` String
`name` String
`money` Decimal(16, 2)
`create_time` DateTime
[root@mes01 20241025_2_4_1]# 
```

解释

```sh
#分区目录解释
20241027_1_3_1
# 20241027 分区的日期
# 1 分区内最小块的编号
# 3 分区内最大块的编号
# 1 最后表示为合并的等级，也就是合并的次数。


# 分区内的目录文件详细信息
data.bin 数据文件
data.cmrk3 数据标记文件
default_compression_codec.txt 默认的压缩格式
count.txt 数据条数
columns.txt 列的信息
checksums.txt 校验信息
primary.cidx  主健的索引文件，稀疏索引结构。
partition.dat 分区信息
minmax_create_time.idx 分区内的索引文件，给分区使用。

```

详细的分区信息解释

```sh
PartitionId_MinBlockNum_MaxBlockNum_Level
分区值_最小分区块编号_最大分区块编号_合并层级
    =》PartitionId
        数据分区ID生成规则
        数据分区规则由分区ID决定，分区ID由PARTITION BY分区键决定。根据分区键字段类型，ID生成规则可分为：
            未定义分区键
                没有定义PARTITION BY，默认生成一个目录名为all的数据分区，所有数据均存放在all目录下。

            整型分区键
                分区键为整型，那么直接用该整型值的字符串形式做为分区ID。

            日期类分区键
                分区键为日期类型，或者可以转化成日期类型。

            其他类型分区键
                String、Float类型等，通过128位的Hash算法取其Hash值作为分区ID。
    =》MinBlockNum
        最小分区块编号，自增类型，从1开始向上递增。每产生一个新的目录分区就向上递增一个数字。
    =》MaxBlockNum
        最大分区块编号，新创建的分区MinBlockNum等于MaxBlockNum的编号。
    =》Level
        合并的层级，被合并的次数。合并次数越多，层级值越大。
        
        
bin文件：数据文件
mrk文件：标记文件
    标记文件在 idx索引文件 和 bin数据文件 之间起到了桥梁作用。
    以mrk2结尾的文件，表示该表启用了自适应索引间隔。
primary.idx文件：主键索引文件，用于加快查询效率。
minmax_create_time.idx：分区键的最大最小值。
checksums.txt：校验文件，用于校验各个文件的正确性。存放各个文件的size以及hash值。
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
mkdir -p /opt/nullnull/example/webAnalyticsData
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



### 7.2 浮点数

Float32-float

Float64-double

一般尽可能以整数的形式存储数据，将固定精度的数字转换为整数值，时间以毫秒为单位表示。浮点数在计算时，可能引起四舍五入的误差。





### 7.3  布尔类型

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

没有单独的类型来存储布尔值，可以使用Uint8类型，取值限制为0或者1.

### 7.4 Decimal类型

有符号的浮点数，可以加、减和乘法运算过程中保持精度。对于除法，最低有效数字会被丢弃。

有符号的定点数，可在加、减和乘法运算过程中保持精度。ClickHouse提供了Decimal32、Decimal64和Decimal128三种精度的定点数，支持几种写法：

- Decimal(P, S)

- Decimal32(S)

  **数据范围：( -1 \* 10^(9 - S), 1 \* 10^(9 - S) )**

- Decimal64(S)

  **数据范围：( -1 \* 10^(18 - S), 1 \* 10^(18 - S) )**

- Decimal128(S)

  **数据范围： ( -1 \* 10^(38 - S), 1 \* 10^(38 - S) )**

- Decimal256(S)

  **数据范围：( -1 \* 10^(76 - S), 1 \* 10^(76 - S) )**

其中：**P**代表精度，决定总位数（整数部分+小数部分），取值范围是1～76

​           **S**代表规模，决定小数位数，取值范围是0～P

根据**P**的范围，可以有如下的等同写法：

| P 取值      | 原生写法示例  | 等同于        |
| ----------- | ------------- | ------------- |
| [ 1 : 9 ]   | Decimal(9,2)  | Decimal32(2)  |
| [ 10 : 18 ] | Decimal(18,2) | Decimal64(2)  |
| [ 19 : 38 ] | Decimal(38,2) | Decimal128(2) |
| [ 39 : 76 ] | Decimal(76,2) | Decimal256(2) |

**注意点**：不同精度的数据进行四则运算时，**精度(总位数)和规模(小数点位数)**会发生变化，具体规则如下：

- 精度对应的规则

  - Decimal64(S1) `运算符` Decimal32(S2)   ->  Decimal64(S)
  - Decimal128(S1) `运算符`  Decimal32(S2) -> Decimal128(S）
  - Decimal128(S1) `运算符`  Decimal64(S2) -> Decimal128(S)
  - Decimal256(S1) `运算符`  Decimal<32|64|128>(S2) -> Decimal256(S)

  **可以看出：两个不同精度的数据进行四则运算时，结果数据已最大精度为准**

- 规模(小数点位数)对应的规则

  - 加法|减法：S = max(S1, S2)，即以两个数据中小数点位数最多的为准
  - 乘法： S = S1 + S2(注意：S1精度 >= S2精度)，即以两个数据的小数位相加为准

s，标识小数位数，一般金额字段、汇率、利率等为了保证小数精度，使用Decimal类型。

```sh
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
```





### 7.5 String类型与FixedString(N)

String

字符串可以任意长度的。它可以包含任意的字符集，包含空字节。

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

FixedString(N)

固定长度N的字符串,N必须是严格的正自然数。当服务端读取长度小于N的字符串时候，通过在字符串末尾添加空字节来达到N字节长度。当服务端读取长度大于N的字符串时候，将返回错误消息。

与String相比，极光会使用FixString，因为使用起来不是很方便。

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
# 创建一个类型表
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





### 7.6 枚举类型

包括Enum8和Enum16类型。Enum保存String=interger的对应关系。

Enum8用'String'=in8 描述

Enum16 用'String'=int16 描述

```sh
Enumerated type consisting of named values.

Named values can be declared as 'string' = integer pairs or 'string' names . ClickHouse stores only numbers, but supports operations with the values through their names.

ClickHouse supports:

8-bit Enum. It can contain up to 256 values enumerated in the [-128, 127] range.
16-bit Enum. It can contain up to 65536 values enumerated in the [-32768, 32767] range.
ClickHouse automatically chooses the type of Enum when data is inserted. You can also use Enum8 or Enum16 types to be sure in the size of storage.
```

用例演示

```sh
# 创建一个枚举Enum8（'nullnull'=1,'feifei'=2）的类型的列
create table enum8_demo_1(
enum_col Enum8('nullnull'=1,'feifei'=2)
)
ENGINE=TinyLog;

# 插入数据，这个列只能插入'nullnull'或者'feifei'
insert into enum8_demo_1 values('nullnull'),('feifei'),('nullnull');

# 查询数据
select * from enum8_demo_1;


# 如果插入其他的值，Clickhouse则抛出异常
insert into enum8_demo_1 values('null','fei');
```

样例输出:

```sh
a5e651989f3b :) create table enum8_demo_1(
                enum_col Enum8('nullnull'=1,'feifei'=2)
                )
                ENGINE=TinyLog;

CREATE TABLE enum8_demo_1
(
    `enum_col` Enum8('nullnull' = 1, 'feifei' = 2)
)
ENGINE = TinyLog

Query id: 77363427-aecd-4124-a412-4b3cadbdc61c

Ok.

0 rows in set. Elapsed: 0.004 sec. 
a5e651989f3b :) insert into enum8_demo_1 values('nullnull'),('feifei'),('nullnull');

INSERT INTO enum8_demo_1 FORMAT Values

Query id: 73d31663-2748-4765-b447-f1dbb49b79ce

Ok.

3 rows in set. Elapsed: 0.004 sec. 
a5e651989f3b :) select * from enum8_demo_1;

SELECT *
FROM enum8_demo_1

Query id: 3c4615b9-77c4-4044-9308-581bad2550e3

┌─enum_col─┐
│ nullnull │
│ feifei   │
│ nullnull │
└──────────┘

3 rows in set. Elapsed: 0.003 sec. 

a5e651989f3b :) insert into enum8_demo_1 values('null','fei');

INSERT INTO enum8_demo_1 FORMAT Values

Query id: f1c25989-a81e-4153-9cf9-09d5ab8843c7

Ok.
Exception on client:
Code: 36. DB::Exception: Unknown element 'null' for enum: While executing ValuesBlockInputFormat: data for INSERT was parsed from query. (BAD_ARGUMENTS)
```



### 7.7 时间类型

Clickhouse有三种时间类型:

- Date 接受年-月-日的字符串，比如：'2024-10-27'
- DatetIme 接受 年-月-日 时:分:秒的字符串，比如: '2024-10-24 19:27:00'
- Datetime64 接受 年-月-日 时:分:秒.亚秒 ，比如：'2024-10-24 19:06:00.002'

日期类型，用两个字符串存储，表示从1970-01-01(无符号)到当前的日期值。



### 7.8 数组类型

官方参考

```sh
https://clickhouse.com/docs/en/sql-reference/data-types/array
```

Array(T): 由T类型元素组成的数组。

T可以是任意类型，包含数组类型。但不推荐使用多维数组，Clickhouse对多组数组的支持有限。例如，不能在MergeTree表中存储多组数组

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





### 7.9  UUID类型

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



### 7.10 Nullable类型

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

### 7.11 Map类型

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



## 8 表引擎

官方文档地址：

```sh
https://clickhouse.com/docs/en/engines/table-engines
```



### 8.1 MergeTree引擎家族

表引擎使用的重点关注：

1. 数据存储式方式和位置。写到哪里以及从哪里读取数据。
2. 支持哪些查询以及如何支持。
3. 并发数据访问。
4. 索引的使用
5. 是否可以执行多线程请求。
6. 数据复制参数。

表引擎的名称大小写敏感。



### 8.1 TinyLog

​	以列文件的形式保存在磁盘上，不支持索引，没有并发控制。一般保存少量数据的小表。生产环境上作用有限。可以用于平时练习测试用。少于100万的量级。

```sh
这些引擎是为了需要写入许多小数据量（少于一百万行）的表的场景而开发的。

这系列的引擎有：

StripeLog
Log
TinyLog
共同属性
引擎：

数据存储在磁盘上。

写入时将数据追加在文件末尾。

不支持突变操作。

不支持索引。

这意味着 `SELECT` 在范围查询时效率不高。

非原子地写入数据。

如果某些事情破坏了写操作，例如服务器的异常关闭，你将会得到一张包含了损坏数据的表。
```



### 8.2 Memory

​    内存引擎，数据未压缩的原始形式直接保存在内存当中，服务器重启数据就会消失。读写不会相互阻塞，不支持索引。简单查询下有非常非常高的性能表现（超过10G/S)

​    一般用到它的地方不多，除了用来测试，就是需要非常高的性能，同时数据量又不太大（上限1亿行）的场景。

### 8.3 MergeTree家族

​    ClickHouse中最强大的表引擎当属MergeTree（合并树）引擎及该系统（*MergeTree）中的其他引擎，支持索引和分区。而且基于MergeTree引擎，还衍生了许多小弟，也是非常有特色的引擎。

演示用例：

```sql
# 建表语句
create table mt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime    
)engine=MergeTree
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time);

# 插入数据
insert into mt_user values
(1,'001','空空1',20000,'2024-10-27 19:50:00'),
(2,'001','空空2',20000,'2024-10-27 19:50:00'),
(2,'002','空空3',20000,'2024-10-27 19:50:00'),
(2,'002','空空4',20000,'2024-10-27 19:50:00'),
(2,'001','空空5',20000,'2024-10-27 19:50:00'),
(2,'002','空空6',20000,'2024-10-25 19:50:00');


# 查询数据
select * from mt_user;
```

样例输出：

```sh

a5e651989f3b :) create table mt_user(
                ^Iid UInt32,
                    order_id String, 
                    name String,
                    money decimal(16,2),
                    create_time Datetime    
                )engine=MergeTree
                partition by toYYYYMMDD(create_time)
                primary key (id)
                order by (id,order_id,create_time);

CREATE TABLE mt_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = MergeTree
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id, create_time)

Query id: 507d13dc-200f-4bd6-9236-6acee9ad8ab5

Ok.

0 rows in set. Elapsed: 0.013 sec. 

a5e651989f3b :) insert into mt_user values
                (1,'001','空空1',20000,'2024-10-27 19:50:00'),
                (2,'001','空空2',20000,'2024-10-27 19:50:00'),
                (2,'002','空空3',20000,'2024-10-27 19:50:00'),
                (2,'002','空空4',20000,'2024-10-27 19:50:00'),
                (2,'001','空空5',20000,'2024-10-27 19:50:00'),
                (2,'002','空空6',20000,'2024-10-25 19:50:00');

INSERT INTO mt_user FORMAT Values

Query id: fea0192d-9b4b-4163-9d75-f5c7590382a4

Ok.

6 rows in set. Elapsed: 0.018 sec. 

a5e651989f3b :) select * from mt_user;

SELECT *
FROM mt_user

Query id: 6a7035b8-69d9-4b83-ac05-2a01c2dca978

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

6 rows in set. Elapsed: 0.005 sec. 

a5e651989f3b :) 
```



#### partition（分区）（可选）

**作用**

分区的目的主要是降低扫描的范围，优化查询的速度，其实分区的作用就是将数据在磁盘上分目录。

**如果不填**

只会使用一个分区,那就只有一个分区all。

**分区目录**

MergeTree是以列文件+索引文件+表定义文件组成的，但如果设定了分区那么这些文件会保存到不同的分区目录中

**并行**

分区后，面对涉及跨分区的的查询，Clickhouse会以分区为单位进行并行处理。

**数据写入与分区合并**

任何一个批次的数据的写入都会产生一个临时分区，不会纳入任何一个已有的分区。写入后的某个时刻（大约10-15分钟），Clickhouse会自动执行合并操作（如果需要立即执行可以使用optimize执行）把临时分区的数据，合并到已有分区中。

```sql
optimize table xxx final;
```

插入，手动合并

```sql

# 插入数据
insert into mt_user values
(1,'001','空空1',20000,'2024-10-27 19:50:00'),
(2,'001','空空2',20000,'2024-10-27 19:50:00'),
(2,'002','空空3',20000,'2024-10-27 19:50:00'),
(2,'002','空空4',20000,'2024-10-27 19:50:00'),
(2,'001','空空5',20000,'2024-10-27 19:50:00'),
(2,'002','空空6',20000,'2024-10-25 19:50:00');

# 查询数据
select * from mt_user;

# 手动执行optimize之后
optimize table mt_user final;

# 如果要只合并某个分区，使用语法
optimize table mt_user parttion '20241025'  final;

# 再次查询
select * from mt_user;
```

日志样例：

```sh
# 查询之前的数据的情况
# 可以看出分成了，两个分区，分别在25号与27号
0a8870a03faa :) select * from mt_user;

SELECT *
FROM mt_user

Query id: 5af2b8e5-9975-4201-a1d5-f03c5989e7a0

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

6 rows in set. Elapsed: 0.006 sec. 

0a8870a03faa :) 
0a8870a03faa :) insert into mt_user values
(1,'001','空空1',20000,'2024-10-27 19:50:00'),
(2,'001','空空2',20000,'2024-10-27 19:50:00'),
(2,'002','空空3',20000,'2024-10-27 19:50:00'),
(2,'002','空空4',20000,'2024-10-27 19:50:00'),
(2,'001','空空5',20000,'2024-10-27 19:50:00'),
(2,'002','空空6',20000,'2024-10-25 19:50:00');

INSERT INTO mt_user FORMAT Values

Query id: 984361e6-1908-4427-9188-bfa06363d08e

Ok.

6 rows in set. Elapsed: 0.013 sec. 

# 当再次插入完成后查询数据，可以发现
# 新增加了两个分区，分别为25号的分区，以及27号的分区。
0a8870a03faa :) select * from mt_user;

SELECT *
FROM mt_user

Query id: 5ac735fb-8925-4b2e-8374-2b26274372f4

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

12 rows in set. Elapsed: 0.010 sec. 


# 执行手动分区合并操作。
0a8870a03faa :) optimize table mt_user final;

OPTIMIZE TABLE mt_user FINAL

Query id: 4de271b2-0ead-49fa-a79e-94dccb970ccc

Ok.

0 rows in set. Elapsed: 0.012 sec. 

# 再次观察情况，发现分区已经被合并了。
0a8870a03faa :) select * from mt_user;

SELECT *
FROM mt_user

Query id: 5a11f8c5-9837-4ee6-ab11-893c262ae089

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

12 rows in set. Elapsed: 0.005 sec. 

0a8870a03faa :) 
```



观察数据目录的变化

```sh
# 在未插入数据前的结构
[root@mes01 70e4d6a0-0f56-40af-9878-4757a38f976d]# ll
total 4
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241025_2_2_0
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241027_1_1_0
drwxr-x---. 2 101 101   6 Oct 27 23:33 detached
-rw-r-----. 1 101 101   1 Oct 27 23:33 format_version.txt



# 再次插入数据后的结构
[root@mes01 70e4d6a0-0f56-40af-9878-4757a38f976d]# ll
total 4
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241025_2_2_0
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241025_4_4_0
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241027_1_1_0
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241027_3_3_0
drwxr-x---. 2 101 101   6 Oct 27 23:33 detached
-rw-r-----. 1 101 101   1 Oct 27 23:33 format_version.txt

# 当执行分区合并后的变化
[root@mes01 70e4d6a0-0f56-40af-9878-4757a38f976d]# ll
total 4
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241025_2_2_0
drwxr-x---. 2 101 101 259 Oct 27 23:36 20241025_2_4_1
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241025_4_4_0
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241027_1_1_0
drwxr-x---. 2 101 101 259 Oct 27 23:36 20241027_1_3_1
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241027_3_3_0
drwxr-x---. 2 101 101   6 Oct 27 23:33 detached
-rw-r-----. 1 101 101   1 Oct 27 23:33 format_version.txt

# 通过观察后，发现，当插入数据是，是新增加了2个分区，分别是20241027_3_3_0和20241025_4_4_0，当执行分区合并后，又增加了两个分区，分别为20241027_1_3_1和20241025_2_4_1，数据完成了合并。


# 当再经过一段时间后，会将原来之前的分区进行清除。保留最后合并的分区。 
[root@mes01 6f281bfb-015f-4957-a58c-ee87927e1db7]# ll
total 4
drwxr-x---. 2 101 101 259 Oct 27 22:44 20241025_2_4_1
drwxr-x---. 2 101 101 259 Oct 27 22:44 20241027_1_3_1
drwxr-x---. 2 101 101   6 Oct 27 22:43 detached
-rw-r-----. 1 101 101   1 Oct 27 22:43 format_version.txt
```

文件的变化

```sh
[root@mes01 70e4d6a0-0f56-40af-9878-4757a38f976d]# ls -l -R
.:
total 4
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241025_2_2_0
drwxr-x---. 2 101 101 259 Oct 27 23:36 20241025_2_4_1
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241025_4_4_0
drwxr-x---. 2 101 101 259 Oct 27 23:33 20241027_1_1_0
drwxr-x---. 2 101 101 259 Oct 27 23:36 20241027_1_3_1
drwxr-x---. 2 101 101 259 Oct 27 23:35 20241027_3_3_0
drwxr-x---. 2 101 101   6 Oct 27 23:33 detached
-rw-r-----. 1 101 101   1 Oct 27 23:33 format_version.txt

./20241025_2_2_0:
total 44
-rw-r-----. 1 101 101 333 Oct 27 23:33 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:33 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 23:33 count.txt
-rw-r-----. 1 101 101 158 Oct 27 23:33 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:33 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:33 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:33 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:33 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:33 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:33 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 23:33 serialization.json

./20241025_2_4_1:
total 44
-rw-r-----. 1 101 101 333 Oct 27 23:36 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:36 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 23:36 count.txt
-rw-r-----. 1 101 101 186 Oct 27 23:36 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:36 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:36 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:36 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:36 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:36 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:36 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 23:36 serialization.json

./20241025_4_4_0:
total 44
-rw-r-----. 1 101 101 333 Oct 27 23:35 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:35 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 23:35 count.txt
-rw-r-----. 1 101 101 158 Oct 27 23:35 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:35 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:35 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:35 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:35 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:35 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:35 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 23:35 serialization.json

./20241027_1_1_0:
total 44
-rw-r-----. 1 101 101 334 Oct 27 23:33 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:33 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 23:33 count.txt
-rw-r-----. 1 101 101 211 Oct 27 23:33 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:33 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:33 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:33 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:33 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:33 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:33 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 23:33 serialization.json

./20241027_1_3_1:
total 44
-rw-r-----. 1 101 101 334 Oct 27 23:36 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:36 columns.txt
-rw-r-----. 1 101 101   2 Oct 27 23:36 count.txt
-rw-r-----. 1 101 101 236 Oct 27 23:36 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:36 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:36 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:36 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:36 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:36 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:36 primary.cidx
-rw-r-----. 1 101 101 355 Oct 27 23:36 serialization.json

./20241027_3_3_0:
total 44
-rw-r-----. 1 101 101 334 Oct 27 23:35 checksums.txt
-rw-r-----. 1 101 101 127 Oct 27 23:35 columns.txt
-rw-r-----. 1 101 101   1 Oct 27 23:35 count.txt
-rw-r-----. 1 101 101 211 Oct 27 23:35 data.bin
-rw-r-----. 1 101 101  70 Oct 27 23:35 data.cmrk3
-rw-r-----. 1 101 101  10 Oct 27 23:35 default_compression_codec.txt
-rw-r-----. 1 101 101   1 Oct 27 23:35 metadata_version.txt
-rw-r-----. 1 101 101   8 Oct 27 23:35 minmax_create_time.idx
-rw-r-----. 1 101 101   4 Oct 27 23:35 partition.dat
-rw-r-----. 1 101 101  42 Oct 27 23:35 primary.cidx
-rw-r-----. 1 101 101 350 Oct 27 23:35 serialization.json

./detached:
total 0
[root@mes01 70e4d6a0-0f56-40af-9878-4757a38f976d]# 
```



#### primary key（主键）（可选）

​	Clickhouse的主键，和其他数据库不太一样，它只提供了数据的一线索引，但是却不是唯一约束，这就意味着是可以存储相同primary key的数据的。

​    主键的设定主要是查询语句中的where条件。

​	根据条件通过对主键进行某种形式的二分查找，能够快速定位到对应的index granularity，避免了全表扫描。

​	index granularity: 直接翻译是索引粒度，指在稀疏索引中两个相邻索引对应数据的间隔，Clickhouse中的MergeTree默认是8192，官方不建议修改这个值，除非该列存在大量重复值，比一在一个分区中几万行才有一个不同数据。

​	稀疏索引的好处就是可以用很少的索引数据，定位更多的数据，代价就是只能定位到索引粒度的第一行，然后再进行一点的扫描。

```sh
# 在建表语句中，通过primary key来指定主键，并且通过index_granularity来指定索引的间隔，默认就是8192，不建议修改。
create table mt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime    
)engine=MergeTree
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time)
SETTINGS index_granularity = 8192;
```



#### order by （排序）(必选)

​	order by 定义了分区内的数据按照哪些字段顺序进行有序保存。

​	order by 是MergeTree中唯一一个必填项，甚至比Primary key还重要，因为当用户不设置主键的情况下，很多会依照order by的字段进行处理

​    主键必须是order by字段中的前缀字段。

​	比如: order by 字段是(id,mobile),那么主键必须是 id,或者id+mobile，不要单独的mobile,

稀疏索引：

![image-20241028231421383](.\image-20241028231421383.png)

稀疏索引的好处就是可以用很少的索引数据，定位更多的数据，代价就是只能定位到索引粒度的第一第，希捷再进行一点扫描。

​     

#### 二级索引

目前在Clickhouse官网上，二级索引功能在`v20.1.2.4`之前被标注为实验性质，在这个版本之后是默认开启的。

```sh
# 老版本需要添加参数,v20.1.2.4 开始，这个参数已被删除，默认开启
set allow_experimental_data_skipping_indices=1;
```

实验SQL，二级索引

```sql
# 创建表
create table nullnull.mt_user_2(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime,
    INDEX a money TYPE minmax GRANULARITY 5
)engine=MergeTree
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time)
SETTINGS index_granularity = 8192;
# GRANULARITY N 是设定二级索引对于一级索引粒度的粒度。
# INDEX a money TYPE minmax GRANULARITY 5
# INDEX 添加索引的关键字
# a 索引名称
# money 库中的索引列
# TYPE 用于指定索引的类型，一般有4种，


# 插入数据
insert into mt_user_2 values
(1,'011','空空1',20000,'2024-10-25 19:50:00'),
(1,'011','空空2',10000,'2024-10-25 19:50:00'),
(2,'012','空空3',30000,'2024-10-27 19:50:00'),
(2,'012','空空4',50000,'2024-10-27 19:50:00'),
(2,'011','空空5',10000,'2024-10-27 19:50:00'),
(2,'012','空空6',60000,'2024-10-27 19:50:00');


# 查询数据
 clickhouse-client --send_logs_level=trace <<< 'select * from nullnull.mt_user_2 where money > toDecimal32(20000,2)'

```

输出:

```sh

c68d406a3602 :) create table nullnull.mt_user_2(
^Iid UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime,
    INDEX a money TYPE minmax GRANULARITY 5
)engine=MergeTree
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time)
SETTINGS index_granularity = 8192;

CREATE TABLE nullnull.mt_user_2
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime,
    INDEX a money TYPE minmax GRANULARITY 5
)
ENGINE = MergeTree
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id, create_time)
SETTINGS index_granularity = 8192

Query id: 23886122-213f-4ccc-9082-9218489f1bcc

Ok.

0 rows in set. Elapsed: 0.285 sec. 

c68d406a3602 :) insert into mt_user_2 values
(1,'011','空空1',20000,'2024-10-25 19:50:00'),
(1,'011','空空2',10000,'2024-10-25 19:50:00'),
(2,'012','空空3',30000,'2024-10-27 19:50:00'),
(2,'012','空空4',50000,'2024-10-27 19:50:00'),
(2,'011','空空5',10000,'2024-10-27 19:50:00'),
(2,'012','空空6',60000,'2024-10-27 19:50:00');

INSERT INTO mt_user_2 FORMAT Values

Query id: 1c78e3f0-6f76-4da3-8508-3d6192033914

Ok.

6 rows in set. Elapsed: 0.004 sec. 

c68d406a3602 :) exit
Bye.
root@c68d406a3602:/#  clickhouse-client --send_logs_level=trace <<< 'select * from nullnull.mt_user_2 where money > toDecimal32(20000,2)'
[c68d406a3602] 2024.10.28 11:49:49.926069 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> executeQuery: (from 127.0.0.1:40076) select * from nullnull.mt_user_2 where money > toDecimal32(20000,2)  (stage: Complete)
[c68d406a3602] 2024.10.28 11:49:49.926492 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> ContextAccess (default): Access granted: SELECT(id, order_id, name, money, create_time) ON nullnull.mt_user_2
[c68d406a3602] 2024.10.28 11:49:49.926532 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> InterpreterSelectQuery: FetchColumns -> Complete
[c68d406a3602] 2024.10.28 11:49:49.926674 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> QueryPlanOptimizePrewhere: The min valid primary key position for moving to the tail of PREWHERE is -1
[c68d406a3602] 2024.10.28 11:49:49.926727 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): Key condition: unknown
[c68d406a3602] 2024.10.28 11:49:49.926741 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): MinMax index condition: unknown
[c68d406a3602] 2024.10.28 11:49:49.927245 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): Index `a` has dropped 1/2 granules.
# Index `a` has dropped 1/2 granules. 此表示已经在2分之一的数据已经被剔除。
[c68d406a3602] 2024.10.28 11:49:49.927264 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): Selected 2/2 parts by partition key, 1 parts by primary key, 2/2 marks by primary key, 1 marks to read from 1 ranges
#  Selected 2/2 parts by partition key, 1 parts by primary key, 2/2 marks by primary key, 1 marks to read from 1 ranges
[c68d406a3602] 2024.10.28 11:49:49.927273 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): Spreading mark ranges among streams (default reading)
[c68d406a3602] 2024.10.28 11:49:49.927302 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> nullnull.mt_user_2 (175ddd49-fada-4e72-88aa-e3cbdb7b24d6) (SelectExecutor): Reading 1 ranges in order from part 20241027_2_2_0, approx. 4 rows starting from 0
[c68d406a3602] 2024.10.28 11:49:49.927323 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Trace> MergeTreeSelectProcessor: PREWHERE condition was split into 1 steps: "greater(money, toDecimal32(20000, 2))"
[c68d406a3602] 2024.10.28 11:49:49.928150 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> executeQuery: Read 4 rows, 176.00 B in 0.002144 sec., 1865.6716417910447 rows/sec., 80.17 KiB/sec.
[c68d406a3602] 2024.10.28 11:49:49.928190 [ 48 ] {28a7c776-4d00-41ba-88a5-c304426fc3e4} <Debug> TCPHandler: Processed in 0.002397704 sec.
2       012     空空3   30000   2024-10-27 19:50:00
2       012     空空4   50000   2024-10-27 19:50:00
2       012     空空6   60000   2024-10-27 19:50:00


# 进入数据目录可以发现，多出来一个skp开头的两个索引文件。

[root@mes-01 175ddd49-fada-4e72-88aa-e3cbdb7b24d6]# ll
总用量 12
drwxr-x---. 2 101 101 4096 10月 28 19:49 20241025_1_1_0
drwxr-x---. 2 101 101 4096 10月 28 19:49 20241027_2_2_0
drwxr-x---. 2 101 101    6 10月 28 19:49 detached
-rw-r-----. 1 101 101    1 10月 28 19:49 format_version.txt
[root@mes-01 175ddd49-fada-4e72-88aa-e3cbdb7b24d6]# cd 20241027_2_2_0/
[root@mes-01 20241027_2_2_0]# ll
总用量 52
-rw-r-----. 1 101 101 425 10月 28 19:49 checksums.txt
-rw-r-----. 1 101 101 127 10月 28 19:49 columns.txt
-rw-r-----. 1 101 101   1 10月 28 19:49 count.txt
-rw-r-----. 1 101 101 223 10月 28 19:49 data.bin
-rw-r-----. 1 101 101  70 10月 28 19:49 data.cmrk3
-rw-r-----. 1 101 101  10 10月 28 19:49 default_compression_codec.txt
-rw-r-----. 1 101 101   1 10月 28 19:49 metadata_version.txt
-rw-r-----. 1 101 101   8 10月 28 19:49 minmax_create_time.idx
-rw-r-----. 1 101 101   4 10月 28 19:49 partition.dat
-rw-r-----. 1 101 101  42 10月 28 19:49 primary.cidx
-rw-r-----. 1 101 101 350 10月 28 19:49 serialization.json
-rw-r-----. 1 101 101  50 10月 28 19:49 skp_idx_a.cmrk3
-rw-r-----. 1 101 101  41 10月 28 19:49 skp_idx_a.idx2

# 还可以通过SQL语句查看二级索引
c68d406a3602 :) show create table mt_user_2;

SHOW CREATE TABLE mt_user_2

Query id: 73d64b68-c5c3-4dbe-9b98-47c5559ac6d4

┌─statement──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ CREATE TABLE nullnull.mt_user_2
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` Decimal(16, 2),
    `create_time` DateTime,
    INDEX a money TYPE minmax GRANULARITY 5
)
ENGINE = MergeTree
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id, create_time)
SETTINGS index_granularity = 8192 │
└────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

c68d406a3602 :) 
```





#### TTL

官方地址

```javascript
https://clickhouse.com/docs/en/engines/table-engines/mergetree-family/mergetree
```

TTL即Time To LIve,MergeTree提供了可以管理数据表或者列的生命周期。

涉及到判断TTL的字段必须是Date或者DateTime类型

可以使用时间周期：

```sh
- SECOND 秒
- MINUTE  分
- HOUR   小时
- DAY    天
- WEEK   周
- MONTH  月
- QUARTER 季度
- YEAR    年
```



```sql
# 创建一个表，其中一个列带TTL
create table mt_user_ttl(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2) TTL create_time + interval 10 SECOND,
    create_time Datetime
)engine=MergeTree
primary key (id)
order by (id,order_id);


# 插入数据
insert into mt_user_ttl values
(1,'010','空空1',20000,'2024-10-29 12:08:40'),
(2,'011','空空2',10000,'2024-10-29 12:08:50'),
(3,'012','空空3',30000,'2024-10-29 12:08:30');


# 查询数据
select * from mt_user_ttl;


# 进行触发合并操作
optimize table mt_user_ttl final;


# 查询数据
select * from mt_user_ttl;
# 此时还可以看见数据，最快速的办法是重启下数据库

# 然后再次手动执行查合并分区操作，查看数据
optimize table mt_user_ttl final;




# 修改TTL
ALTER TABLE tab  MODIFY TTL d + INTERVAL 1 DAY;

# 还可以对表做TTL，并且可以过期后做数据的移动操作。
CREATE TABLE tab
(
    d DateTime,
    a Int
)
ENGINE = MergeTree
PARTITION BY toYYYYMM(d)
ORDER BY d
TTL d + INTERVAL 1 MONTH DELETE,
    d + INTERVAL 1 WEEK TO VOLUME 'aaa',
    d + INTERVAL 2 WEEK TO DISK 'bbb';
```

TTL过期后的操作

```sh
Type of TTL rule may follow each TTL expression. It affects an action which is to be done once the expression is satisfied (reaches current time):

DELETE - delete expired rows (default action);
RECOMPRESS codec_name - recompress data part with the codec_name;
TO DISK 'aaa' - move part to the disk aaa;
TO VOLUME 'bbb' - move part to the disk bbb;
GROUP BY - aggregate expired rows.
```



日志输出：

```sh
ck :) create table mt_user_ttl(
      ^Iid UInt32,
          order_id String, 
          name String,
          money decimal(16,2) TTL create_time + interval 10 SECOND,
          create_time Datetime
      )engine=MergeTree
      primary key (id)
      order by (id,order_id);

CREATE TABLE mt_user_ttl
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2) TTL create_time + toIntervalSecond(10),
    `create_time` Datetime
)
ENGINE = MergeTree
PRIMARY KEY id
ORDER BY (id, order_id)

Query id: 55e747a9-a010-4de6-bccf-53aeb3625f66

Ok.

0 rows in set. Elapsed: 0.036 sec. 

ck :) insert into mt_user_ttl values
      (1,'010','空空1',20000,'2024-10-29 12:08:40'),
      (2,'011','空空2',10000,'2024-10-29 12:08:50'),
      (3,'012','空空3',30000,'2024-10-29 12:07:30');

INSERT INTO mt_user_ttl FORMAT Values

Query id: 845fe662-8bf4-4aa2-ae5f-ad3a429a70c5

Ok.

3 rows in set. Elapsed: 0.002 sec. 

ck :) select * from mt_user_ttl;

SELECT *
FROM mt_user_ttl

Query id: 0acce45a-0e6f-4e32-a294-b123124faebd

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 20000 │ 2024-10-29 12:08:40 │
│  2 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  3 │ 012      │ 空空3 │ 30000 │ 2024-10-29 12:07:30 │
└────┴──────────┴───────┴───────┴─────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 

ck :) optimize table mt_user_ttl final;

OPTIMIZE TABLE mt_user_ttl FINAL

Query id: b87383d2-72e5-4d6b-8c59-d5226d54a3c2

Ok.

0 rows in set. Elapsed: 0.002 sec. 

ck :) select * from mt_user_ttl;

SELECT *
FROM mt_user_ttl

Query id: 13b225a0-4668-4fc6-9fdb-d4f58e2d6a79

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 20000 │ 2024-10-29 12:08:40 │
│  2 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  3 │ 012      │ 空空3 │ 30000 │ 2024-10-29 12:07:30 │
└────┴──────────┴───────┴───────┴─────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 


# 重启数据库，再次检查TTL

ck :) optimize table mt_user_ttl final;

OPTIMIZE TABLE mt_user_ttl FINAL

Query id: b3af4de2-cfc6-4b0f-afc6-f284d7aff03f

Ok.

0 rows in set. Elapsed: 0.001 sec. 

ck :) select * from mt_user_ttl;

SELECT *
FROM mt_user_ttl

Query id: 2624d620-b4fc-490f-920e-4188cdda43fa

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 20000 │ 2024-10-29 12:08:40 │
│  2 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  3 │ 012      │ 空空3 │     0 │ 2024-10-29 12:07:30 │
└────┴──────────┴───────┴───────┴─────────────────────┘

3 rows in set. Elapsed: 0.001 sec. 

# 可以发现空空3的数据已经过期。
```



### 8.4 ReplacingMergeTree

#### 介绍

​	ReplacingMergeTree是MergeTree的一个变种，它存储特性完全继承MergeTree，只是多了一个去重的功能。尽管MergeTree可以设置主键，但是primary Key其实并没有唯一约束的功能，如果想处理掉重复的数据，可以借助这个ReplacingMergeTree。

 	1）去重时机

​	数据去重只会在合并的过程中出现。合并会在未知的时间在后台进行，所以无法预先作出计划，有一些数据可能仍未被处理。

​	2） 去重的范围

​	如果表经过了分区，去重只会在分区内部进行去重，不能执行跨分区的去重。

​	所以ReplacingMergeTree能力有限，ReplacingMergeTree适用于在后台清除重复的数据以节省空间，但是它不保证没有重复的数据出现。

#### 样例：

```sql
create table rmt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime
)engine=ReplacingMergeTree(create_time)
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id);
-- ReplacingMergeTree() 填入的参数为版本号字段，重复数据保留版本字段值最大的。如果不填字段，默认认按照插入顺序保留最后一条。

-- 插入数据
insert into rmt_user values
(1,'010','空空1',20000,'2024-10-28 12:08:40'),
(1,'010','空空2',20000,'2024-10-28 12:08:40'),
(3,'011','空空2',10000,'2024-10-29 12:08:50'),
(3,'011','空空3',30000,'2024-10-29 12:08:30'),
(5,'013','空空4',40000,'2024-10-28 12:08:40'),
(5,'013','空空4',40000,'2024-10-28 12:08:40'),
(7,'014','空空5',60000,'2024-10-29 12:08:50'),
(7,'014','空空6',50000,'2024-10-29 12:08:30');
-- 数据存在两两重复

-- 执行查询，以检查数据
select * from rmt_user;
-- 通过观察查询结果可以发现，插入数据已经将重复数据给合并，可再次执行数据插入，以获得重复数据

insert into rmt_user values
(1,'010','空空1',20000,'2024-10-28 12:08:40'),
(1,'010','空空2',20000,'2024-10-28 12:08:40'),
(3,'011','空空2',10000,'2024-10-29 12:08:50'),
(3,'011','空空3',30000,'2024-10-29 12:08:30'),
(5,'013','空空4',40000,'2024-10-28 12:08:40'),
(5,'013','空空4',40000,'2024-10-28 12:08:40'),
(7,'014','空空5',60000,'2024-10-29 12:08:50'),
(7,'014','空空6',50000,'2024-10-29 12:08:30');

-- 执行查询，以检查数据
select * from rmt_user;

-- 执行手动合并分区操作
optimize table rmt_user;

-- 执行查询，以检查数据
select * from rmt_user;

```

输出

```sh
ck :) create table rmt_user(
      ^Iid UInt32,
          order_id String, 
          name String,
          money decimal(16,2),
          create_time Datetime
      )engine=ReplacingMergeTree(create_time)
      partition by toYYYYMMDD(create_time)
      primary key (id)
      order by (id,order_id);

CREATE TABLE rmt_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = ReplacingMergeTree(create_time)
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id)

Query id: 5b31b71a-fafa-4c16-b326-f1f4092ab0dd

Ok.

0 rows in set. Elapsed: 0.017 sec. 

# 插入数据
ck :) insert into rmt_user values
      (1,'010','空空1',20000,'2024-10-28 12:08:40'),
      (1,'010','空空2',20000,'2024-10-28 12:08:40'),
      (3,'011','空空2',10000,'2024-10-29 12:08:50'),
      (3,'011','空空3',30000,'2024-10-29 12:08:30'),
      (5,'013','空空4',40000,'2024-10-28 12:08:40'),
      (5,'013','空空4',40000,'2024-10-28 12:08:40'),
      (7,'014','空空5',60000,'2024-10-29 12:08:50'),
      (7,'014','空空6',50000,'2024-10-29 12:08:30');

INSERT INTO rmt_user FORMAT Values

Query id: 396e43dd-8a2a-421a-aaf7-8b6fb28a5a56

Ok.

8 rows in set. Elapsed: 0.002 sec. 

# 查看重复的数据
ck :) select * from rmt_user;

SELECT *
FROM rmt_user

Query id: 9a1e7622-d3a9-4acc-908d-6aa919682502

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空2 │ 20000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空4 │ 40000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘

4 rows in set. Elapsed: 0.001 sec. 

# 再次录入重复的数据
ck :) insert into rmt_user values
      (1,'010','空空1',20000,'2024-10-28 12:08:40'),
      (1,'010','空空2',20000,'2024-10-28 12:08:40'),
      (3,'011','空空2',10000,'2024-10-29 12:08:50'),
      (3,'011','空空3',30000,'2024-10-29 12:08:30'),
      (5,'013','空空4',40000,'2024-10-28 12:08:40'),
      (5,'013','空空4',40000,'2024-10-28 12:08:40'),
      (7,'014','空空5',60000,'2024-10-29 12:08:50'),
      (7,'014','空空6',50000,'2024-10-29 12:08:30');

INSERT INTO rmt_user FORMAT Values

Query id: 194fde9f-eb84-4165-a208-224fe883b233

Ok.

8 rows in set. Elapsed: 0.002 sec. 


# 查询数据，发现重复数据已经录入
ck :) select * from rmt_user;

SELECT *
FROM rmt_user

Query id: 05501ee2-1bdf-49c5-a239-ca36ffeabd0e

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空2 │ 20000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空4 │ 40000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空2 │ 20000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空4 │ 40000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘

8 rows in set. Elapsed: 0.002 sec. 

# 手动合并分区操作
ck :) optimize table rmt_user;

OPTIMIZE TABLE rmt_user

Query id: 11135d71-17ba-4ec5-9c33-98cc4b829139

Ok.

0 rows in set. Elapsed: 0.002 sec. 

# 查询数据，发现一个分区一个被合并，还有一个未被合并
ck :) select * from rmt_user;

SELECT *
FROM rmt_user

Query id: 2010c5a8-8b50-4754-98d0-7cd9ba6c18f7

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空2 │ 20000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空4 │ 40000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘

6 rows in set. Elapsed: 0.002 sec. 

# 再次执行手动合并分区操作
ck :) optimize table rmt_user;

OPTIMIZE TABLE rmt_user

Query id: d51a6413-9d1d-479b-a58c-732c6d50a485

Ok.

0 rows in set. Elapsed: 0.002 sec. 

# 查询数据，所有重复数据已经被合并，
ck :) select * from rmt_user;

SELECT *
FROM rmt_user

Query id: e92460c3-bbd5-4765-a350-bde7ec0871fa

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空2 │ 20000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空4 │ 40000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空2 │ 10000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空5 │ 60000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴───────┴─────────────────────┘

4 rows in set. Elapsed: 0.001 sec. 

ck :) 

```

经过样例的测试，此ReplacingMergeTree只保证最终的数据是去重的，中间数据不保证不存在重复，需要特别注意。

#### 结论：

- 使用order by 字段作为唯一的键。
- 去重不能跨分区
- 只有同一批次插入的数据或者合并分区时才会进行去重
- 认定重复的数据保留版本字段值最大的，未指定版本字段按插入先后顺序
- 字段相同则插入顺序保留最后一笔。



### 8.5 SummingMergeTree

#### 介绍

​    对于不查询明细，只关心以维度进行聚合结果的场景。如果只使用普通的MergeTree的话，无论是存储空间的开销，还是查询时临时聚合的开销都比较大。

​	Clickhouse为了这种场景，提供了一种能够“预聚合”的引擎SummingMergeTree

1.  分区内聚合
2. 分片合并时做合并

#### 案例

```sql
create table smt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime
)engine=SummingMergeTree(money)
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id);
-- SummingMergeTree() 填入聚合的的数据字段，
-- 按order by的字段进行预聚合操作。


-- 插入数据
insert into smt_user values
(1,'010','空空1',20000,'2024-10-28 12:08:40'),
(1,'010','空空2',20000,'2024-10-28 12:08:40'),
(3,'011','空空3',10000,'2024-10-29 12:08:50'),
(3,'011','空空4',30000,'2024-10-29 12:08:30'),
(5,'013','空空5',40000,'2024-10-28 12:08:40'),
(5,'013','空空6',40000,'2024-10-28 12:08:40'),
(7,'014','空空7',60000,'2024-10-29 12:08:50'),
(7,'014','空空8',50000,'2024-10-29 12:08:30');


# 经过插入后，原来的8条数据，现在只剩下了4条。说明已经将数据做了预聚合。
# 通过观察发现 数据时间取的是最早插入的那条记录。
select * from smt_user;


# 在同一个分区内，再次插入数据
insert into smt_user values
(1,'010','空空1',20001,'2024-10-28 15:08:40');


# 再次观察数据
# 可以发现，在新的分区内，又插入了一条记录
select * from smt_user;


# 手动执行合并操作
optimize table smt_user final;


# 检查数据预聚合的合并树
select * from smt_user;


```



输出

```sql
ck :) create table smt_user(
      ^Iid UInt32,
          order_id String, 
          name String,
          money decimal(16,2),
          create_time Datetime
      )engine=SummingMergeTree(money)
      partition by toYYYYMMDD(create_time)
      primary key (id)
      order by (id,order_id);

CREATE TABLE smt_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = SummingMergeTree(money)
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id)

Query id: 19f2f47f-287c-4c44-bfa1-14463ddc7a3f

Ok.

0 rows in set. Elapsed: 0.015 sec. 

ck :) insert into smt_user values
      (1,'010','空空1',20000,'2024-10-28 12:08:40'),
      (1,'010','空空2',20000,'2024-10-28 12:08:40'),
      (3,'011','空空3',10000,'2024-10-29 12:08:50'),
      (3,'011','空空4',30000,'2024-10-29 12:08:30'),
      (5,'013','空空5',40000,'2024-10-28 12:08:40'),
      (5,'013','空空6',40000,'2024-10-28 12:08:40'),
      (7,'014','空空7',60000,'2024-10-29 12:08:50'),
      (7,'014','空空8',50000,'2024-10-29 12:08:30');

INSERT INTO smt_user FORMAT Values

Query id: fcc57e6a-8e98-4b09-b4c6-513ab8b95b59

Ok.

8 rows in set. Elapsed: 0.002 sec. 

# 经过插入后，原来的8条数据，现在只剩下了4条。说明已经将数据做了预聚合。
# 通过观察发现 数据时间取的是最早插入的那条记录。

ck :) select * from smt_user;

SELECT *
FROM smt_user

Query id: 65ac0a38-3f61-41b9-aa58-9c21002328c5

┌─id─┬─order_id─┬─name──┬──money─┬─────────create_time─┐
│  3 │ 011      │ 空空3 │  40000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空7 │ 110000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴────────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 40000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空5 │ 80000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘

4 rows in set. Elapsed: 0.002 sec.


# 在同一个分区内，再次插入数据
ck :) insert into smt_user values
      (1,'010','空空1',20001,'2024-10-28 15:08:40');

INSERT INTO smt_user FORMAT Values

Query id: 8f262825-78e4-4a2e-bbd4-f1767bc9f407

Ok.

1 row in set. Elapsed: 0.002 sec. 


# 再次观察数据
# 可以发现，在新的分区内，又插入了一条记录
ck :) select * from smt_user;

SELECT *
FROM smt_user

Query id: ebd55e06-5a2f-44eb-bf35-ef1961a307fe

┌─id─┬─order_id─┬─name──┬──money─┬─────────create_time─┐
│  3 │ 011      │ 空空3 │  40000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空7 │ 110000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴────────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 40000 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空5 │ 80000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 20001 │ 2024-10-28 15:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘

5 rows in set. Elapsed: 0.002 sec. 

# 手动执行合并操作
ck :) optimize table smt_user final;

OPTIMIZE TABLE smt_user FINAL

Query id: ff6e4bd7-da61-4ee2-94cb-efcfc10d55eb

Ok.

0 rows in set. Elapsed: 0.002 sec. 

# 观察数据，
# 刚插入的010，数据已经被合并，money已经被相加，时间还是第一次插入的时间，不是使用最新的。
ck :) select * from smt_user;

SELECT *
FROM smt_user

Query id: 170635d2-6c9b-46b2-956b-85a23d0c02f2

┌─id─┬─order_id─┬─name──┬──money─┬─────────create_time─┐
│  3 │ 011      │ 空空3 │  40000 │ 2024-10-29 12:08:50 │
│  7 │ 014      │ 空空7 │ 110000 │ 2024-10-29 12:08:50 │
└────┴──────────┴───────┴────────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 60001 │ 2024-10-28 12:08:40 │
│  5 │ 013      │ 空空5 │ 80000 │ 2024-10-28 12:08:40 │
└────┴──────────┴───────┴───────┴─────────────────────┘

4 rows in set. Elapsed: 0.002 sec. 
```

#### 结论：

1. 以SummingMergeTree（）指定的列作为汇总数据列
2. 可以填写多列必须数字列，如果不填，以所有非维度列且数字列的字段汇总数据。
3. 以order by的列为准，作为维度列。
4. 其他列按插入顺序保留第一行。
5. 不在一个分区的数据不会被聚合。
6. 只有在同一批次（新版本）或者分区合并时才会进行聚合。
7. 在查询时，需要指定sum,因为存在没有合并的情况，为保证数据的正确性，需要带上sum





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



方式4：

使用命令行执插入SQL

```sql
clickhouse-client --password= --user=  --send_logs_level=trace   <<< "$(cat /home/data/xxsx.sql)" > /dev/null
```







### 9.2  分区操作

```sh
https://clickhouse.com/docs/en/sql-reference/statements/alter/partition
```

相关的操作

```sh
The following operations with partitions are available:

# 卸载分区至detached目录,然后数据不能查询到
DETACH PARTITION|PART — Moves a partition or part to the detached directory and forget it.
# 删除卸载分区
DROP PARTITION|PART — Deletes a partition or part.
# 删除卸载的分区
DROP DETACHED PRTITION|PART - Delete a part or all parts of a partition from detached.
#
FORGET PARTITION — Deletes a partition metadata from zookeeper if it's empty.
# 装载分区
ATTACH PARTITION|PART — Adds a partition or part from the detached directory to the table.
ATTACH PARTITION FROM — Copies the data partition from one table to another and adds.
# 复制分区,从一个表至另外一个表
REPLACE PARTITION — Copies the data partition from one table to another and replaces.
# 移动分区
MOVE PARTITION TO TABLE — Moves the data partition from one table to another.
# 清除分区
CLEAR COLUMN IN PARTITION — Resets the value of a specified column in a partition.
# 清除分区索引
CLEAR INDEX IN PARTITION — Resets the specified secondary index in a partition.
# 创建分区备份
FREEZE PARTITION — Creates a backup of a partition.
# 移除一个备份
UNFREEZE PARTITION — Removes a backup of a partition.
# 从另外一个服务器下载部分分区
FETCH PARTITION|PART — Downloads a part or partition from another server.
# 迁移分区或者数据至另外的磁盘或者券
MOVE PARTITION|PART — Move partition/data part to another disk or volume.
# 按条件更新分区
UPDATE IN PARTITION — Update data inside the partition by condition.
# 按条件删除分区
DELETE IN PARTITION — Delete data inside the partition by condition.
```



分区在于减少查询读取数据的条数

在ck中需要使用mergeTree引擎

分区操作的命令

```sh
# 分区装载
ALTER TABLE ${tableName}  ATTACH PARTITION ${partition}


# 分区卸载
ALTER TABLE ${tableName}  DETACH PARTITION ${partition}

# 分区删除
ALTER TABLE ${tableName}   DROP PARTITION ${partition}

# 复制分区
alter table  ${tableName} REPLACE PARTITION ${partition} from  ${tableNameOther} 
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



### 9.3 Update和Delete操作

​	ClickHouse提供了Delete和Update的能力，这类操作称为Mutation查询，它可以看做Alter的一种。

​	虽然可以实现修改和删除，但是和一般的OLTP数据库不一样，Mutation语句是一种很“重”的操作，而且不支持事务。

​	“重”的原因主要是每次修改或者删除都会导致放弃目标数据的原有分区，重建新分区。所以尽量做批量的变更，不要进行频繁小数据操作。

​	（1）删除操作

```sql
alter table ddl_user delete where id = 1;
```

​	（2）修改操作

```sql
alter table ddl_user update name='New1',money=toDecimal32(2000.01,2),create_time=now64() where id = 3 and order_id= '011'
```

样例：

```sh
create table ddl_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime
)engine=MergeTree()
primary key (id)
order by (id,order_id);


-- 插入数据
insert into ddl_user values
(1,'010','空空1',20000,'2024-10-28 12:08:40'),
(3,'011','空空3',10000,'2024-10-29 12:08:50'),
(5,'013','空空5',40000,'2024-10-28 12:08:40'),
(7,'014','空空8',50000,'2024-10-29 12:08:30');

-- 查询
select * from ddl_user;

-- 删除操作
alter table ddl_user delete where id = 1;

-- 查询
select * from ddl_user;


-- 修改操作
alter table ddl_user update name='New1',money=toDecimal32(2000.01,2),create_time=now64() where id = 3 and order_id= '011';

-- 查询
select * from ddl_user;

```

输出：

```sql
ck :) create table ddl_user(
      ^Iid UInt32,
          order_id String, 
          name String,
          money decimal(16,2),
          create_time Datetime
      )engine=MergeTree()
      primary key (id)
      order by (id,order_id);

CREATE TABLE ddl_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = MergeTree
PRIMARY KEY id
ORDER BY (id, order_id)

Query id: 761c45fa-ee5b-4a3b-91dc-4d489198a9dc

Ok.

0 rows in set. Elapsed: 0.063 sec. 

ck :) insert into ddl_user values
      (1,'010','空空1',20000,'2024-10-28 12:08:40'),
      (3,'011','空空3',10000,'2024-10-29 12:08:50'),
      (5,'013','空空5',40000,'2024-10-28 12:08:40'),
      (7,'014','空空8',50000,'2024-10-29 12:08:30');

INSERT INTO ddl_user FORMAT Values

Query id: bc66292e-7933-4d58-b863-ee06ef342fce

Ok.

4 rows in set. Elapsed: 0.003 sec. 

# 查询插入后的数据
ck :) select * from ddl_user;

SELECT *
FROM ddl_user

Query id: 7cd5ef49-85f9-4716-a8f4-901803f0c019

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 010      │ 空空1 │ 20000 │ 2024-10-28 12:08:40 │
│  3 │ 011      │ 空空3 │ 10000 │ 2024-10-29 12:08:50 │
│  5 │ 013      │ 空空5 │ 40000 │ 2024-10-28 12:08:40 │
│  7 │ 014      │ 空空8 │ 50000 │ 2024-10-29 12:08:30 │
└────┴──────────┴───────┴───────┴─────────────────────┘

4 rows in set. Elapsed: 0.004 sec. 

# 删除操作
ck :) alter table ddl_user delete where id = 1;

ALTER TABLE ddl_user
    DELETE WHERE id = 1

Query id: 48707750-39e7-43ee-91be-893c77653a61

Ok.

0 rows in set. Elapsed: 0.005 sec. 

# 查询后数据已经被删除
ck :) select * from ddl_user;

SELECT *
FROM ddl_user

Query id: f5bccb80-2a7f-4952-a02f-64811cbbf8ab

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  3 │ 011      │ 空空3 │ 10000 │ 2024-10-29 12:08:50 │
│  5 │ 013      │ 空空5 │ 40000 │ 2024-10-28 12:08:40 │
│  7 │ 014      │ 空空8 │ 50000 │ 2024-10-29 12:08:30 │
└────┴──────────┴───────┴───────┴─────────────────────┘

3 rows in set. Elapsed: 0.004 sec. 

# 修改操作
ck :) alter table ddl_user update name='New1',money=toDecimal32(2000.01,2),create_time=now64() where id = 3 and order_id= '011'

ALTER TABLE ddl_user
    UPDATE name = 'New1', money = toDecimal32(2000.01, 2), create_time = now64() WHERE (id = 3) AND (order_id = '011')

Query id: 1375d582-ccbc-43e5-b934-8d04d1fa27f2

Ok.

0 rows in set. Elapsed: 0.031 sec. 

# 数据已经被更新到最新
ck :) select * from ddl_user;

SELECT *
FROM ddl_user

Query id: 5b0a4097-3377-48bf-9b89-9abcada67dea

┌─id─┬─order_id─┬─name──┬───money─┬─────────create_time─┐
│  3 │ 011      │ New1  │ 2000.01 │ 2024-11-03 10:19:08 │
│  5 │ 013      │ 空空5 │   40000 │ 2024-10-28 12:08:40 │
│  7 │ 014      │ 空空8 │   50000 │ 2024-10-29 12:08:30 │
└────┴──────────┴───────┴─────────┴─────────────────────┘

3 rows in set. Elapsed: 0.003 sec. 

ck :) 
```

### 9.4 查询操作

ClickHouse与标准SQL差别不大

	1. 支持子查询
	1. 支持CTE(Common Table Expression 公用表达式 with 子句)
	1. 支持各种Join，但是Join操作无法使用缓存，所以即使两次相同 的Join语句，ClickHouse也会视为两条新SQL。
	1. 窗口函数（https://clickhouse.com/docs/en/sql-reference/window-functions）
	1. 不支持自定义函数
	1. Group By操作增加了 With rollup \ with cube \ with total 用来计算小计和总和

#### 多维函数分析

```sql
# 建表
create table ddl_with_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime
)engine=MergeTree()
primary key (id)
order by (id,order_id);


# 插入数据
insert into ddl_with_user values
(1,'011','空空1',20000,'2024-10-28 12:08:40'),
(1,'011','空空2',20000,'2024-10-28 12:08:42'),
(2,'012','空空3',10000,'2024-10-29 12:08:50'),
(2,'012','空空4',10000,'2024-10-29 12:08:52'),
(3,'013','空空5',40000,'2024-10-28 12:08:40'),
(3,'015','空空6',40000,'2024-10-28 12:08:46'),
(4,'016','空空8',50000,'2024-10-29 12:08:30');


# 查询数据
select * from ddl_with_user;


# 维度a,b
# with rollUp: 从右至左去掉维度进行小计
# rollup 上卷，从每一个维度开始，不能跳过
	# group by  统计所有
	# group by a
	# group by a,b
select id,order_id,sum(money) 
from ddl_with_user
group by id,order_id
with rollup;



# with cube : 从右至左去掉维度进行小计，再从左至右进行维度小计
# cube : 多维分析,所有情况都会有
	# group by a,b
	# group by a
	# group by b
	# group by  统计所有
select id,order_id,sum(money) 
from ddl_with_user
group by id,order_id
with cube;

# with totals: 只算合计
# total ： 总计 
	# group by a,b
	# group by  统计所有
select id,order_id,sum(money) 
from ddl_with_user
group by id,order_id
with totals;

```

样例：

```sql
# with rollUp: 从右至左去掉维度进行小计
ck :) select id,order_id,sum(money) 
      from ddl_with_user
      group by id,order_id
      with rollup;

SELECT
    id,
    order_id,
    sum(money)
FROM ddl_with_user
GROUP BY
    id,
    order_id
    WITH ROLLUP

Query id: 364a8abc-67a9-49af-9090-8ecd8b64e136

┌─id─┬─order_id─┬─sum(money)─┐
│  3 │ 015      │      40000 │
│  3 │ 013      │      40000 │
│  1 │ 011      │      40000 │
│  4 │ 016      │      50000 │
│  2 │ 012      │      20000 │
└────┴──────────┴────────────┘
┌─id─┬─order_id─┬─sum(money)─┐
│  4 │          │      50000 │
│  1 │          │      40000 │
│  2 │          │      20000 │
│  3 │          │      80000 │
└────┴──────────┴────────────┘
┌─id─┬─order_id─┬─sum(money)─┐
│  0 │          │     190000 │
└────┴──────────┴────────────┘

10 rows in set. Elapsed: 0.005 sec. 


with cube : 从右至左去掉维度进行小计，再从左至右进行维度小计
ck :) select id,order_id,sum(money) 
      from ddl_with_user
      group by id,order_id
      with cube;

SELECT
    id,
    order_id,
    sum(money)
FROM ddl_with_user
GROUP BY
    id,
    order_id
    WITH CUBE

Query id: 78854bf4-96f0-4cbe-9e4c-9613054259f9

┌─id─┬─order_id─┬─sum(money)─┐
│  3 │ 015      │      40000 │
│  3 │ 013      │      40000 │
│  1 │ 011      │      40000 │
│  4 │ 016      │      50000 │
│  2 │ 012      │      20000 │
└────┴──────────┴────────────┘
┌─id─┬─order_id─┬─sum(money)─┐
│  4 │          │      50000 │
│  1 │          │      40000 │
│  2 │          │      20000 │
│  3 │          │      80000 │
└────┴──────────┴────────────┘
┌─id─┬─order_id─┬─sum(money)─┐
│  0 │ 015      │      40000 │
│  0 │ 016      │      50000 │
│  0 │ 013      │      40000 │
│  0 │ 011      │      40000 │
│  0 │ 012      │      20000 │
└────┴──────────┴────────────┘
┌─id─┬─order_id─┬─sum(money)─┐
│  0 │          │     190000 │
└────┴──────────┴────────────┘

15 rows in set. Elapsed: 0.006 sec. 

# with totals: 只算合计
ck :) select id,order_id,sum(money) 
      from ddl_with_user
      group by id,order_id
      with totals;

SELECT
    id,
    order_id,
    sum(money)
FROM ddl_with_user
GROUP BY
    id,
    order_id
    WITH TOTALS

Query id: 4e4d65b8-0cc1-43e2-bef1-a95c6a044b96

┌─id─┬─order_id─┬─sum(money)─┐
│  3 │ 015      │      40000 │
│  3 │ 013      │      40000 │
│  1 │ 011      │      40000 │
│  4 │ 016      │      50000 │
│  2 │ 012      │      20000 │
└────┴──────────┴────────────┘

Totals:
┌─id─┬─order_id─┬─sum(money)─┐
│  0 │          │     190000 │
└────┴──────────┴────────────┘

5 rows in set. Elapsed: 0.021 sec. 

ck :) 
```





### 9.5 ALTER操作操作

参考官方文档：

```sh
https://clickhouse.com/docs/en/sql-reference/statements/alter
```

常用操作

```sh
# 1. 新加字段
ALTER TABLE  tableName ADD COLUMN IF NOT EXISTS newColumnName String COMMENT '描述' after coll;


# 2. 修改字段
ALTER TABLE  tableName modify COLUMN IF EXISTS newColumnName String COMMENT '描述';


# 3. 删除字段
ALTER TABLE  tableName DROP COLUMN IF EXISTS newColumnName ;
```

样例

```sql
# 使用多给分析的表继续操作
# 添加字段
ALTER TABLE  ddl_with_user ADD COLUMN IF NOT EXISTS address String COMMENT '地址' after money;

# 查询
select * from ddl_with_user;

# 插入
insert into ddl_with_user values
(10,'111','空空11',20000,'上海','2024-11-01 12:08:40');

# 查询
select * from ddl_with_user;

# 2. 修改字段
ALTER TABLE  ddl_with_user modify COLUMN IF EXISTS address String COMMENT '地址信息';


# 查询
desc ddl_with_user;


# 删除字段
ALTER TABLE  ddl_with_user DROP COLUMN IF EXISTS address ;


# 查询
desc ddl_with_user;
```

输出:

```sh
# 添加字段
ck :) ALTER TABLE  ddl_with_user ADD COLUMN IF NOT EXISTS address String COMMENT '地址' after money;

ALTER TABLE ddl_with_user
    ADD COLUMN IF NOT EXISTS `address` String COMMENT '地址' AFTER money

Query id: d8566c57-9a7d-4a6b-9b5f-06c9f67dc1a1

Ok.

0 rows in set. Elapsed: 0.025 sec. 

# 查询
ck :) select * from ddl_with_user;

SELECT *
FROM ddl_with_user

Query id: e0c2beba-c84e-4f13-875b-a331bfb3c2fa

┌─id─┬─order_id─┬─name──┬─money─┬─address─┬─────────create_time─┐
│  1 │ 011      │ 空空1 │ 20000 │         │ 2024-10-28 12:08:40 │
│  1 │ 011      │ 空空2 │ 20000 │         │ 2024-10-28 12:08:42 │
│  2 │ 012      │ 空空3 │ 10000 │         │ 2024-10-29 12:08:50 │
│  2 │ 012      │ 空空4 │ 10000 │         │ 2024-10-29 12:08:52 │
│  3 │ 013      │ 空空5 │ 40000 │         │ 2024-10-28 12:08:40 │
│  3 │ 015      │ 空空6 │ 40000 │         │ 2024-10-28 12:08:46 │
│  4 │ 016      │ 空空8 │ 50000 │         │ 2024-10-29 12:08:30 │
└────┴──────────┴───────┴───────┴─────────┴─────────────────────┘

7 rows in set. Elapsed: 0.004 sec. 

#修改字段
ck :) ALTER TABLE  ddl_with_user modify COLUMN IF EXISTS address String COMMENT '地址信息';

ALTER TABLE ddl_with_user
    MODIFY COLUMN IF EXISTS `address` String COMMENT '地址信息'

Query id: eb89eee7-e132-4c4b-9105-536e0621da04

Ok.

0 rows in set. Elapsed: 0.025 sec. 

ck :) 
ck :) # 查询
      select * from ddl_with_user;

SELECT *
FROM ddl_with_user

Query id: 028efb5b-488b-46b5-a2c2-66e816b5d502

┌─id─┬─order_id─┬─name───┬─money─┬─address─┬─────────create_time─┐
│ 10 │ 111      │ 空空11 │ 20000 │ 上海    │ 2024-11-01 12:08:40 │
└────┴──────────┴────────┴───────┴─────────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─address─┬─────────create_time─┐
│  1 │ 011      │ 空空1 │ 20000 │         │ 2024-10-28 12:08:40 │
│  1 │ 011      │ 空空2 │ 20000 │         │ 2024-10-28 12:08:42 │
│  2 │ 012      │ 空空3 │ 10000 │         │ 2024-10-29 12:08:50 │
│  2 │ 012      │ 空空4 │ 10000 │         │ 2024-10-29 12:08:52 │
│  3 │ 013      │ 空空5 │ 40000 │         │ 2024-10-28 12:08:40 │
│  3 │ 015      │ 空空6 │ 40000 │         │ 2024-10-28 12:08:46 │
│  4 │ 016      │ 空空8 │ 50000 │         │ 2024-10-29 12:08:30 │
└────┴──────────┴───────┴───────┴─────────┴─────────────────────┘

8 rows in set. Elapsed: 0.017 sec. 

ck :) desc ddl_with_user;

DESCRIBE TABLE ddl_with_user

Query id: a3336215-8c07-4587-82fc-650942714d82

┌─name────────┬─type───────────┬─default_type─┬─default_expression─┬─comment──┬─codec_expression─┬─ttl_expression─┐
│ id          │ UInt32         │              │                    │          │                  │                │
│ order_id    │ String         │              │                    │          │                  │                │
│ name        │ String         │              │                    │          │                  │                │
│ money       │ Decimal(16, 2) │              │                    │          │                  │                │
│ address     │ String         │              │                    │ 地址信息 │                  │                │
│ create_time │ DateTime       │              │                    │          │                  │                │
└─────────────┴────────────────┴──────────────┴────────────────────┴──────────┴──────────────────┴────────────────┘

6 rows in set. Elapsed: 0.001 sec. 


# 删除字段
ck :) ALTER TABLE  ddl_with_user DROP COLUMN IF EXISTS address ;

ALTER TABLE ddl_with_user
    DROP COLUMN IF EXISTS address

Query id: 81f7b406-6602-491c-9f92-bf5d91935097

Ok.

0 rows in set. Elapsed: 0.018 sec. 

ck :) # 查询
      desc ddl_with_user;

DESCRIBE TABLE ddl_with_user

Query id: 7df216c7-ecb6-46be-8c07-e303c8666fb3

┌─name────────┬─type───────────┬─default_type─┬─default_expression─┬─comment─┬─codec_expression─┬─ttl_expression─┐
│ id          │ UInt32         │              │                    │         │                  │                │
│ order_id    │ String         │              │                    │         │                  │                │
│ name        │ String         │              │                    │         │                  │                │
│ money       │ Decimal(16, 2) │              │                    │         │                  │                │
│ create_time │ DateTime       │              │                    │         │                  │                │
└─────────────┴────────────────┴──────────────┴────────────────────┴─────────┴──────────────────┴────────────────┘

5 rows in set. Elapsed: 0.003 sec. 

ck :) 
```





### 9.6 导出数据

可参考数据格式：

```sh
https://clickhouse.com/docs/en/sql-reference/formats
```

导出数据

```sql
clickhouse-client --password --query "select * from nullnull.ddl_with_user where create_time >= '2024-10-28 12:00:00' and create_time <= '2024-10-29 12:00:00'" --format CSVWithNames > /opt/data/export/user.csv
```

输出：

```markdown
[root@ck export]# clickhouse-client --password --query "select * from nullnull.ddl_with_user where create_time >= '2024-10-28 12:00:00' and create_time <= '2024-10-29 12:00:00'" --format CSVWithNames > /opt/data/export/user.csv
Password for user (default): 
[root@ck export]# ll
总用量 4
-rw-r--r--. 1 root root 229 11月  3 11:35 user.csv
[root@ck export]# cat user.csv 
"id","order_id","name","money","create_time"
1,"011","空空1",20000,"2024-10-28 12:08:40"
1,"011","空空2",20000,"2024-10-28 12:08:42"
3,"013","空空5",40000,"2024-10-28 12:08:40"
3,"015","空空6",40000,"2024-10-28 12:08:46"
[root@ck export]# 
```





## 10. ClickHouse内置函数



### 10.1 算术函数

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





### 10.2 比较函数

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





### 10.3 逻辑函数

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







### 10.4 取整函数

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





### 10.5 转换函数

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





### 10.6 逻辑函数

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





### 10.7 字符串函数

官方地址：

```javascript
https://clickhouse.com/docs/en/sql-reference/functions/string-functions
```



### 10.8 时间函数

官方地址:

```java
https://clickhouse.com/docs/en/sql-reference/functions/date-time-functions
```

样例：

```sql
# 时间输出:
SELECT
    toDateTime('2016-06-15 23:00:00') AS time,
    toDate(time) AS date_local,
    toDate(time, 'Asia/Yekaterinburg') AS date_yekat,
    toString(time, 'US/Samoa') AS time_samoa
    
    
   # 当时间时间戳 
   select 
   -- 当时时间
   now(),
   -- 当天的
   today(),
   yesterday();
   
   
   # 抽取年份
   select toDate('2024-10-21 12:56:55') dt,
   -- 抽取年份
   extract(year from dt),
   toYear(dt)
   
   # 时间加1天
   select toDate('2024-10-21 12:56:55') dt,
   dt + INTERVAL 1 DAY as d1,
   dt + INTERVAL 1 MONTH as m1,
   dt + INTERVAL 1 year as y1
   
```



输出

```sql
SELECT
    toDateTime('2016-06-15 23:00:00') AS time,
    toDate(time) AS date_local,
    toDate(time, 'Asia/Yekaterinburg') AS date_yekat,
    toString(time, 'US/Samoa') AS time_samoa

Query id: 42ef8595-b53a-437c-9acd-800045fa7e77

┌────────────────time─┬─date_local─┬─date_yekat─┬─time_samoa──────────┐
│ 2016-06-15 23:00:00 │ 2016-06-15 │ 2016-06-16 │ 2016-06-15 12:00:00 │
└─────────────────────┴────────────┴────────────┴─────────────────────┘

1 row in set. Elapsed: 0.011 sec. 


mwrpt-clickhouse :)    select 
   -- 当时时间
   now(),
   -- 当天的
   today(),
   yesterday()

SELECT
    now(),
    today(),
    yesterday()

Query id: 016f4d35-7a4e-40f4-a695-f4415c576940

┌───────────────now()─┬────today()─┬─yesterday()─┐
│ 2024-10-21 04:54:04 │ 2024-10-21 │  2024-10-20 │
└─────────────────────┴────────────┴─────────────┘


mwrpt-clickhouse :)   select toDate('2024-10-21 12:56:55') dt,
   -- 抽取年份
   extract(year from dt),
   toYear(dt)

SELECT
    toDate('2024-10-21 12:56:55') AS dt,
    toYear(dt),
    toYear(dt)

Query id: f2db0790-ef5c-43d9-aa77-bbff7b458f36

┌─────────dt─┬─toYear(toDate('2024-10-21 12:56:55'))─┬─toYear(toDate('2024-10-21 12:56:55'))─┐
│ 2024-10-21 │                                  2024 │                                  2024 │
└────────────┴───────────────────────────────────────┴───────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 



mwrpt-clickhouse :) select toDate('2024-10-21 12:56:55') dt,
   dt + INTERVAL 1 DAY as d1,
   dt + INTERVAL 1 MONTH as m1,
   dt + INTERVAL 1 year as y1

SELECT
    toDate('2024-10-21 12:56:55') AS dt,
    dt + toIntervalDay(1) AS d1,
    dt + toIntervalMonth(1) AS m1,
    dt + toIntervalYear(1) AS y1

Query id: 35d396b8-a64c-4eee-9376-838f5092d0c2

┌─────────dt─┬─────────d1─┬─────────m1─┬─────────y1─┐
│ 2024-10-21 │ 2024-10-22 │ 2024-11-21 │ 2025-10-21 │
└────────────┴────────────┴────────────┴────────────┘

1 row in set. Elapsed: 0.001 sec. 
```



## 11 副本与分片集群

### 11.1 副本

副本的主要目的是保障数据的高可用性，即使一台ClickHouse节点宕机，那么也可以从其他服务器获得相同的数据：

```sh
https://clickhouse.com/docs/en/engines/table-engines/mergetree-family/replication
```

副本写入流程

```mermaid
graph LR;
	A[client]-->|1-写入数据| B[clickhouse-a]
	B-->|2-提交写入日志| C[zookeeper-cluster]
	C-->|3-收到写入日志| D[zookeeper-b]
	B-->|4-从目标副本下载数据| D[zookeeper-b]
```



![image-20241103130519343](.\images\image-20241103130519343.png)

#### 配制步骤

启动zookeeper集群

```properties
# zookeeper中的每台机器都需要安装与配制

# server.A=B:C:D
# A标识机器序列号不重复就行判断myid文件
# B填写集群中机器IP或域名
# C是集群中Follower与Leader服务器交换信息的端口
# D是万一集群中的Leader服务器挂了，需要一个端口来重新进行选举，选出一个新的Leader，用来执行选举时服务器相互通信的端口
server.1=192.168.5.11:2182:2183
server.2=192.168.5.12:2182:2183
server.3=192.168.5.13:2182:2183

#开放防火墙
firewall-cmd --permanent --zone=public --add-port=2181/tcp
firewall-cmd --permanent --zone=public --add-port=2182/tcp
firewall-cmd --permanent --zone=public --add-port=2183/tcp
firewall-cmd --reload

# 此端口用于数据访问
firewall-cmd --permanent --zone=public --add-port=9009/tcp
firewall-cmd --reload
```

2. 对Clickhouse配制zookeeper(每个CK副本节点都需要修改)

使用外部文件方式

```sh
# 编辑
/etc/clickhouse-server/config.xml

# 找到文件中此内容
<!-- If element has 'incl' attribute, then for it's value will be used corresponding substitution from another file.
         By default, path to file with substitutions is /etc/metrika.xml. It could be changed in config in 'include_from' element.
         Values for substitutions are specified in /clickhouse/name_of_substitution elements in that file.
      -->

    <!-- ZooKeeper is used to store metadata about replicas, when using Replicated tables.
         Optional. If you don't use replicated tables, you could omit that.

         See https://clickhouse.com/docs/en/engines/table-engines/mergetree-family/replication/
      -->

    <!--
    <zookeeper>
        <node>
            <host>example1</host>
            <port>2181</port>
        </node>
        <node>
            <host>example2</host>
            <port>2181</port>
        </node>
        <node>
            <host>example3</host>
            <port>2181</port>
        </node>
    </zookeeper>
    -->
    
# 添加以下内容
<zookeeper incl="zookeeper-servers" optional="true" />
<include_from>/etc/clickhouse-server/config.d/metrika.xml</include_from>



# 文件为外部文件
/etc/clickhouse-server/config.d/metrika.xml

# 注意需要修改文件所尾的组为clickhouse
chown clickhouse:clickhouse metrika.xml 

```

metrika.xml内容

```xml
<?xml version="1.0"?>
<yandex>
	<zookeeper-servers>
		<node index="1">
			<host>os11</host>
			<port>2181</port>
		</node>
		<node index="2">
			<host>os12</host>
			<port>2181</port>
		</node>
		<node index="3">
			<host>os13</host>
			<port>2181</port>
		</node>
	</zookeeper-servers>
</yandex>
```

使用内置文件

```sh
# 编辑
/etc/clickhouse-server/config.xml

# 找到文件中此内容
<!-- If element has 'incl' attribute, then for it's value will be used corresponding substitution from another file.
         By default, path to file with substitutions is /etc/metrika.xml. It could be changed in config in 'include_from' element.
         Values for substitutions are specified in /clickhouse/name_of_substitution elements in that file.
      -->

    <!-- ZooKeeper is used to store metadata about replicas, when using Replicated tables.
         Optional. If you don't use replicated tables, you could omit that.

         See https://clickhouse.com/docs/en/engines/table-engines/mergetree-family/replication/
      -->

    <zookeeper>
        <node>
            <host>os11</host>
            <port>2181</port>
        </node>
        <node>
            <host>os12</host>
            <port>2181</port>
        </node>
        <node>
            <host>os13</host>
            <port>2181</port>
        </node>
    </zookeeper>

```

3. 配制host

```sh
cat >> /etc/hosts << EOF
192.168.5.11 os11
192.168.5.12 os12
192.168.5.13 os13
EOF
```

4. 重启Clickhouse节点。

```sh
clickhouse restart
```

5. 在Clickhouse的节点上进行分别的建表数据查询操作。

```sql
# os11上的建表语句
# drop table nullnull.replicatemt_user;
create table nullnull.replicatemt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime    
)engine==ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user','rep_11')
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time);

# os12上的建表语句
create table nullnull.replicatemt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime    
)engine==ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user','rep_12')
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time);


# 至CK节点查看数据信息 
zkCli.sh
ls -R  /clickhouse/table/nullnull/r1/replicatemt_user



# 在os11上 插入数据
insert into nullnull.replicatemt_user values
(1,'001','空空1',20000,'2024-10-27 19:50:00'),
(2,'001','空空2',20000,'2024-10-27 19:50:00'),
(2,'002','空空3',20000,'2024-10-27 19:50:00'),
(2,'002','空空4',20000,'2024-10-27 19:50:00'),
(2,'001','空空5',20000,'2024-10-27 19:50:00'),
(2,'002','空空6',20000,'2024-10-25 19:50:00');


# os12查询数据
select * from nullnull.replicatemt_user;


# os12插入数据
insert into nullnull.replicatemt_user values
(10,'011','空空1',20000,'2024-10-28 19:50:00'),
(20,'011','空空2',20000,'2024-10-28 19:50:00'),
(21,'012','空空3',20000,'2024-10-28 19:50:00'),
(22,'012','空空4',20000,'2024-10-28 19:50:00'),
(23,'011','空空5',20000,'2024-10-28 19:50:00'),
(24,'012','空空6',20000,'2024-10-29 19:50:00');


# os12查询数据
select * from nullnull.replicatemt_user;
```

#### 数据查看

```sh
# os1创建表
os11 :) create table nullnull.replicatemt_user(
        ^Iid UInt32,
            order_id String, 
            name String,
            money decimal(16,2),
            create_time Datetime    
        )engine==ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user','rep_11')
        partition by toYYYYMMDD(create_time)
        primary key (id)
        order by (id,order_id,create_time);

CREATE TABLE nullnull.replicatemt_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user', 'rep_11')
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id, create_time)

Query id: b7b7849e-17e5-4962-bd8b-fc9a86a06243

Ok.

0 rows in set. Elapsed: 0.060 sec. 



# os12创建表
os12 :) create table nullnull.replicatemt_user(
        ^Iid UInt32,
            order_id String, 
            name String,
            money decimal(16,2),
            create_time Datetime    
        )engine==ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user','rep_12')
        partition by toYYYYMMDD(create_time)
        primary key (id)
        order by (id,order_id,create_time);

CREATE TABLE nullnull.replicatemt_user
(
    `id` UInt32,
    `order_id` String,
    `name` String,
    `money` decimal(16, 2),
    `create_time` Datetime
)
ENGINE = ReplicatedMergeTree('/clickhouse/table/nullnull/r1/replicatemt_user', 'rep_12')
PARTITION BY toYYYYMMDD(create_time)
PRIMARY KEY id
ORDER BY (id, order_id, create_time)

Query id: 3c3fdafe-4a24-4a21-ab1c-e38bbb9805b9

Ok.

0 rows in set. Elapsed: 0.053 sec.



# 查看Ck中的数据信息
zkCli.sh
[zk: localhost:2181(CONNECTED) 20] ls -R  /clickhouse/table/nullnull/r1/replicatemt_user
/clickhouse/table/nullnull/r1/replicatemt_user
/clickhouse/table/nullnull/r1/replicatemt_user/alter_partition_version
/clickhouse/table/nullnull/r1/replicatemt_user/async_blocks
/clickhouse/table/nullnull/r1/replicatemt_user/block_numbers
/clickhouse/table/nullnull/r1/replicatemt_user/blocks
/clickhouse/table/nullnull/r1/replicatemt_user/columns
/clickhouse/table/nullnull/r1/replicatemt_user/leader_election
/clickhouse/table/nullnull/r1/replicatemt_user/log
/clickhouse/table/nullnull/r1/replicatemt_user/metadata
/clickhouse/table/nullnull/r1/replicatemt_user/mutations
/clickhouse/table/nullnull/r1/replicatemt_user/nonincrement_block_numbers
/clickhouse/table/nullnull/r1/replicatemt_user/part_moves_shard
/clickhouse/table/nullnull/r1/replicatemt_user/pinned_part_uuids
/clickhouse/table/nullnull/r1/replicatemt_user/quorum
/clickhouse/table/nullnull/r1/replicatemt_user/replicas
/clickhouse/table/nullnull/r1/replicatemt_user/table_shared_id
/clickhouse/table/nullnull/r1/replicatemt_user/temp
/clickhouse/table/nullnull/r1/replicatemt_user/block_numbers/20241025
/clickhouse/table/nullnull/r1/replicatemt_user/block_numbers/20241027
/clickhouse/table/nullnull/r1/replicatemt_user/blocks/20241025_13015592445632956282_4562001324984579865
/clickhouse/table/nullnull/r1/replicatemt_user/blocks/20241027_4578369839963845089_13189989263489756522
/clickhouse/table/nullnull/r1/replicatemt_user/leader_election/leader_election-0
/clickhouse/table/nullnull/r1/replicatemt_user/log/log-0000000000
/clickhouse/table/nullnull/r1/replicatemt_user/log/log-0000000001
/clickhouse/table/nullnull/r1/replicatemt_user/quorum/failed_parts
/clickhouse/table/nullnull/r1/replicatemt_user/quorum/last_part
/clickhouse/table/nullnull/r1/replicatemt_user/quorum/parallel
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/columns
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/flags
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/host
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/is_active
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/is_lost
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/log_pointer
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/max_processed_insert_time
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/metadata
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/metadata_version
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/min_unprocessed_insert_time
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/mutation_pointer
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/parts
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/queue
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/parts/20241025_0_0_0
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_11/parts/20241027_0_0_0
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/columns
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/flags
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/host
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/is_active
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/is_lost
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/log_pointer
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/max_processed_insert_time
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/metadata
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/metadata_version
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/min_unprocessed_insert_time
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/mutation_pointer
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/parts
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/queue
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/parts/20241025_0_0_0
/clickhouse/table/nullnull/r1/replicatemt_user/replicas/rep_12/parts/20241027_0_0_0
/clickhouse/table/nullnull/r1/replicatemt_user/temp/abandonable_lock-insert
/clickhouse/table/nullnull/r1/replicatemt_user/temp/abandonable_lock-other



# os11 插入数据
os11 :) insert into nullnull.replicatemt_user values
        (1,'001','空空1',20000,'2024-10-27 19:50:00'),
        (2,'001','空空2',20000,'2024-10-27 19:50:00'),
        (2,'002','空空3',20000,'2024-10-27 19:50:00'),
        (2,'002','空空4',20000,'2024-10-27 19:50:00'),
        (2,'001','空空5',20000,'2024-10-27 19:50:00'),
        (2,'002','空空6',20000,'2024-10-25 19:50:00');

INSERT INTO nullnull.replicatemt_user FORMAT Values

Query id: a858753c-8ded-4518-be8b-2575db2633ae

Ok.

6 rows in set. Elapsed: 0.030 sec. 




# os12查询数据
os12 :) select * from replicatemt_user;

SELECT *
FROM replicatemt_user

Query id: bc2b910b-d0b6-4c33-9591-6654fb7006db

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

6 rows in set. Elapsed: 0.002 sec. 

os12 :) 
# 可以发现数据已经同步完成



# os12插入数据
os12 :) insert into nullnull.replicatemt_user values
        (10,'011','空空1',20000,'2024-10-28 19:50:00'),
        (20,'011','空空2',20000,'2024-10-28 19:50:00'),
        (21,'012','空空3',20000,'2024-10-28 19:50:00'),
        (22,'012','空空4',20000,'2024-10-28 19:50:00'),
        (23,'011','空空5',20000,'2024-10-28 19:50:00'),
        (24,'012','空空6',20000,'2024-10-29 19:50:00');

INSERT INTO nullnull.replicatemt_user FORMAT Values

Query id: 68820529-2429-4718-8c39-11329a364be8

Ok.

6 rows in set. Elapsed: 0.024 sec.


# os11查询数据 
os11 :) select * from nullnull.replicatemt_user;

SELECT *
FROM nullnull.replicatemt_user

Query id: c8e27adc-d1a5-4ba2-b9a8-452278998083

┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  2 │ 002      │ 空空6 │ 20000 │ 2024-10-25 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│  1 │ 001      │ 空空1 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空2 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 001      │ 空空5 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空3 │ 20000 │ 2024-10-27 19:50:00 │
│  2 │ 002      │ 空空4 │ 20000 │ 2024-10-27 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│ 10 │ 011      │ 空空1 │ 20000 │ 2024-10-28 19:50:00 │
│ 20 │ 011      │ 空空2 │ 20000 │ 2024-10-28 19:50:00 │
│ 21 │ 012      │ 空空3 │ 20000 │ 2024-10-28 19:50:00 │
│ 22 │ 012      │ 空空4 │ 20000 │ 2024-10-28 19:50:00 │
│ 23 │ 011      │ 空空5 │ 20000 │ 2024-10-28 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘
┌─id─┬─order_id─┬─name──┬─money─┬─────────create_time─┐
│ 24 │ 012      │ 空空6 │ 20000 │ 2024-10-29 19:50:00 │
└────┴──────────┴───────┴───────┴─────────────────────┘

12 rows in set. Elapsed: 0.004 sec. 


# 至此双向同步已经成功的完成。
```



### 11.2 分片集群的高可用方案

既然CK在数据库层面实现了高可用，两个节点都可都提供读写请求。那就可以使用中间件提供高可用的方案。

![image-20241119235214876](.\images\image-20241119235214876.png)









### 11.3 分片集群

副本虽然能够提高数据的可用性，降低丢失风险，但是每台机器实际上必须容纳全量数据，对数据的横向扩容没有解决。

要解决数据水平切分的问题，需要引入分片的概念，通过分片把一份完整的数据进行切分。不同的分片分布到不同的节点上，再通过Distributed表引擎把数据拼接起来一起使用。 

Distributed表本身不存储数据，作为一种中间件来使用，通过分布式逻辑表来写入、分发、路由来操作多台节点不同分片的分布式数据。

注意：Clickhouse的集群是表级别的，实际企业中，大部分做了高可用，但是没有分片，避免降低查询性能以及操作集群的复杂性。



集群可查看《配制3个节点2副本的分片.md》



## 12 CK优化相关(系统)

在clickhouse 20.6版本之前要查看SQL语句的执行计划，需要设置日志级别为trace才能看到，并且只能真正执行SQL，在执行日志里里查看。在20.6 版本引入了原生的执行计划。20.6.3版本成为正式版本的功能。

​	本文档版本：21.7.3.14

官方文档地址：

```sh
https://clickhouse.com/docs/en/sql-reference/statements/explain
```

基本的语法：

```sh
EXPLAIN [AST | SYNTAX | QUERY TREE | PLAN | PIPELINE | ESTIMATE | TABLE OVERRIDE] [setting = value, ...]
    [
      SELECT ... |
      tableFunction(...) [COLUMNS (...)] [ORDER BY ...] [PARTITION BY ...] [PRIMARY KEY] [SAMPLE BY ...] [TTL ...]
    ]
    [FORMAT ...]
```

- plan : 用户于查看执行计算，默认。
  - header            : 打印计划中各个步骤的head说明，默认关闭，默认值为0
  - description      : 打印计划中各个步骤的描述，默认开启，默认值为1
  - actions            : 打印计划中各个步骤的详细信息，默认关闭。
- AST ： 用于查看语法树
- SYNTAX： 用于优化语法
- PIPELINE： 用于查看PIPELINE计划
  - header           ： 打印计划中各个步骤的header说明，默认关闭
  - graph              :   用DOT图形语言描述管道图，默认关闭，需要查看相关的图形需要配合graphviz查看。
  - actions            ： 如果开启了graph，紧凑打印打，默认开启。

### 12.1  EXPLAIN （执行计划）

 1） 查看plain

```sql
# 简单SQL

# 从0到9整数
select number as x from numbers(10);

# 查看执行计划
explain plan select number as x from numbers(10);

# 复杂SQL的统计，查看各表的空间占用情况
explain SELECT
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
group by
	`table`
order by `总行数`

# 打开执行计划的全部参数
explain  header=1,actions=1,description=1 SELECT
	`table` AS `表名`,
	sum(rows) AS `总行数`
FROM
	system.parts
group by
	`table`
order by `总行数`
```

输出 

```sql
ck :) select number as x from numbers(10);

SELECT number AS x
FROM numbers(10)

Query id: 482a4ccc-ebad-42d1-b3f0-2bfab65ff2fd

┌─x─┐
│ 0 │
│ 1 │
│ 2 │
│ 3 │
│ 4 │
│ 5 │
│ 6 │
│ 7 │
│ 8 │
│ 9 │
└───┘


ck :) explain plan select number as x from numbers(10);

EXPLAIN
SELECT number AS x
FROM numbers(10)

Query id: b7c2422d-0edc-4105-921d-f393095df5b1

┌─explain───────────────────────────────────────────────────────────────────┐
│ Expression ((Projection + Before ORDER BY))                               │
│   SettingQuotaAndLimits (Set limits and quota after reading from storage) │
│     ReadFromStorage (SystemNumbers)                                       │
└───────────────────────────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.002 sec. 

ck :) 

# 复杂SQL的统计，查看各表的空间占用情况
ck :) explain SELECT
:-] sum(rows) AS `总行数`,
:-] sum(data_uncompressed_bytes) AS `原始大小-Byes`,
:-] formatReadableSize(sum(data_uncompressed_bytes)) AS `原始大小`,
:-] sum(data_compressed_bytes) AS `压缩大小-bytes`,
:-] formatReadableSize(sum(data_compressed_bytes)) AS `压缩大小`,
:-] round((sum(data_compressed_bytes) / sum(data_uncompressed_bytes)) * 100,
:-] 0) AS `压缩率`,
:-] `table` AS `表名`
:-] FROM
:-] system.parts
:-] group by
:-] `table`
:-] order by `总行数`

EXPLAIN
SELECT
    sum(rows) AS `总行数`,
    sum(data_uncompressed_bytes) AS `原始大小-Byes`,
    formatReadableSize(sum(data_uncompressed_bytes)) AS `原始大小`,
    sum(data_compressed_bytes) AS `压缩大小-bytes`,
    formatReadableSize(sum(data_compressed_bytes)) AS `压缩大小`,
    round((sum(data_compressed_bytes) / sum(data_uncompressed_bytes)) * 100, 0) AS `压缩率`,
    table AS `表名`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC

Query id: 3c5f1e7e-bade-41e9-9d11-b967bfa70ee0

┌─explain───────────────────────────────────────────────────────────────────────────────┐
│ Expression (Projection)                                                               │
│   MergingSorted (Merge sorted streams for ORDER BY)                                   │
│     MergeSorting (Merge sorted blocks for ORDER BY)                                   │
│       PartialSorting (Sort each block for ORDER BY)                                   │
│         Expression (Before ORDER BY)                                                  │
│           Aggregating                                                                 │
│             Expression (Before GROUP BY)                                              │
│               SettingQuotaAndLimits (Set limits and quota after reading from storage) │
│                 ReadFromStorage (SystemParts)                                         │
└───────────────────────────────────────────────────────────────────────────────────────┘

9 rows in set. Elapsed: 0.002 sec. 


# 打开全部参数
ck :) explain  header=1,actions=1,description=1 SELECT
:-] `table` AS `表名`,
:-] sum(rows) AS `总行数`
:-] FROM
:-] system.parts
:-] group by
:-] `table`
:-] order by `总行数`

EXPLAIN header = 1, actions = 1, description = 1
SELECT
    table AS `表名`,
    sum(rows) AS `总行数`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC

Query id: b3a3b3f6-62d0-4354-aa54-1da27e54d340

┌─explain───────────────────────────────────────────────────────────────────────────────┐
│ Expression (Projection)                                                               │
│ Header: 表名 String                                                                   │
│         总行数 UInt64                                                                 │
│ Actions: INPUT : 0 -> table String : 0                                                │
│          INPUT : 1 -> sum(rows) UInt64 : 1                                            │
│          ALIAS table :: 0 -> 表名 String : 2                                          │
│          ALIAS sum(rows) :: 1 -> 总行数 UInt64 : 0                                    │
│ Positions: 2 0                                                                        │
│   MergingSorted (Merge sorted streams for ORDER BY)                                   │
│   Header: table String                                                                │
│           sum(rows) UInt64                                                            │
│   Sort description: sum(rows) ASC                                                     │
│     MergeSorting (Merge sorted blocks for ORDER BY)                                   │
│     Header: table String                                                              │
│             sum(rows) UInt64                                                          │
│     Sort description: sum(rows) ASC                                                   │
│       PartialSorting (Sort each block for ORDER BY)                                   │
│       Header: table String                                                            │
│               sum(rows) UInt64                                                        │
│       Sort description: sum(rows) ASC                                                 │
│         Expression (Before ORDER BY)                                                  │
│         Header: table String                                                          │
│                 sum(rows) UInt64                                                      │
│         Actions: INPUT :: 0 -> table String : 0                                       │
│                  INPUT :: 1 -> sum(rows) UInt64 : 1                                   │
│         Positions: 0 1                                                                │
│           Aggregating                                                                 │
│           Header: table String                                                        │
│                   sum(rows) UInt64                                                    │
│           Keys: table                                                                 │
│           Aggregates:                                                                 │
│               sum(rows)                                                               │
│                 Function: sum(UInt64) → UInt64                                        │
│                 Arguments: rows                                                       │
│                 Argument positions: 0                                                 │
│             Expression (Before GROUP BY)                                              │
│             Header: rows UInt64                                                       │
│                     table String                                                      │
│             Actions: INPUT :: 0 -> rows UInt64 : 0                                    │
│                      INPUT :: 1 -> table String : 1                                   │
│             Positions: 0 1                                                            │
│               SettingQuotaAndLimits (Set limits and quota after reading from storage) │
│               Header: rows UInt64                                                     │
│                       table String                                                    │
│                 ReadFromStorage (SystemParts)                                         │
│                 Header: rows UInt64                                                   │
│                         table String                                                  │
└───────────────────────────────────────────────────────────────────────────────────────┘

47 rows in set. Elapsed: 0.002 sec. 

ck :) 
```

2) 查看AST语法树

```sql
EXPLAIN AST SELECT
    table AS `表名`,
    sum(rows) AS `总行数`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC
```

输出：

```sql
ck :) EXPLAIN AST SELECT
:-]     table AS `表名`,
:-]     sum(rows) AS `总行数`
:-] FROM system.parts
:-] GROUP BY table
:-] ORDER BY `总行数` ASC

EXPLAIN AST
SELECT
    table AS `表名`,
    sum(rows) AS `总行数`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC

Query id: 00e1f763-a729-4b35-ab17-14d01a80be8e

┌─explain──────────────────────────────────────┐
│ SelectWithUnionQuery (children 1)            │
│  ExpressionList (children 1)                 │
│   SelectQuery (children 4)                   │
│    ExpressionList (children 2)               │
│     Identifier table (alias 表名)            │
│     Function sum (alias 总行数) (children 1) │
│      ExpressionList (children 1)             │
│       Identifier rows                        │
│    TablesInSelectQuery (children 1)          │
│     TablesInSelectQueryElement (children 1)  │
│      TableExpression (children 1)            │
│       TableIdentifier system.parts           │
│    ExpressionList (children 1)               │
│     Identifier table                         │
│    ExpressionList (children 1)               │
│     OrderByElement (children 1)              │
│      Identifier 总行数                       │
└──────────────────────────────────────────────┘

17 rows in set. Elapsed: 0.001 sec. 

ck :) 
```

3) SYNTAX语法优化

```sql
# 先做一次查询
select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3)  
# 查看优化后的语法树
EXPLAIN SYNTAX select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3) 
# 开启三元运算符优化
SET optimize_if_chain_to_multiif = 1;
# 再次执行，查看优化后的语法
EXPLAIN SYNTAX select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3) 
 SELECT multiIf(number = 1, 'hello nullnull', number = 2, 'feifei', '1234') │
│ FROM numbers(3) 
```

输出

```sql
ck :) select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3)  

SELECT if(number = 1, 'hello nullnull', if(number = 2, 'feifei', '1234'))
FROM numbers(3)

Query id: 500e07d2-952e-453f-8dbf-fe6b8d0e4bb3

┌─if(equals(number, 1), 'hello nullnull', if(equals(number, 2), 'feifei', '1234'))─┐
│ 1234                                                                             │
│ hello nullnull                                                                   │
│ feifei                                                                           │
└──────────────────────────────────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.011 sec. 

ck :) EXPLAIN SYNTAX select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3)  

EXPLAIN SYNTAX
SELECT if(number = 1, 'hello nullnull', if(number = 2, 'feifei', '1234'))
FROM numbers(3)

Query id: 3511cd72-24ad-4380-946a-79e25c85a478

┌─explain───────────────────────────────────────────────────────────────────┐
│ SELECT if(number = 1, 'hello nullnull', if(number = 2, 'feifei', '1234')) │
│ FROM numbers(3)                                                           │
└───────────────────────────────────────────────────────────────────────────┘

2 rows in set. Elapsed: 0.003 sec. 

ck :) SET optimize_if_chain_to_multiif = 1;

SET optimize_if_chain_to_multiif = 1

Query id: 1678ff66-fe35-4a3b-8c32-2a7a32c50dcc

Ok.

0 rows in set. Elapsed: 0.001 sec. 

ck :) EXPLAIN SYNTAX select number = 1 ? 'hello nullnull' : (number = 2  ? 'feifei' : '1234') from numbers(3) 

EXPLAIN SYNTAX
SELECT if(number = 1, 'hello nullnull', if(number = 2, 'feifei', '1234'))
FROM numbers(3)

Query id: 1e544a1f-f9f1-4e8d-8fc7-252a6b359c23

┌─explain────────────────────────────────────────────────────────────────────┐
│ SELECT multiIf(number = 1, 'hello nullnull', number = 2, 'feifei', '1234') │
│ FROM numbers(3)                                                            │
└────────────────────────────────────────────────────────────────────────────┘

2 rows in set. Elapsed: 0.001 sec. 

ck :) 
```

查看PIPELINE,查看执行的流水线

```sql
# 数据汇聚查询
EXPLAIN PIPELINE SELECT
    table AS `表名`,
    sum(rows) AS `总行数`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC

# 
EXPLAIN PIPELINE SELECT sum(number) from numbers_mt(10000) group by number % 20;


```

输出：

```sql
ck :) EXPLAIN PIPELINE SELECT
:-]     table AS `表名`,
:-]     sum(rows) AS `总行数`
:-] FROM system.parts
:-] GROUP BY table
:-] ORDER BY `总行数` ASC

EXPLAIN PIPELINE
SELECT
    table AS `表名`,
    sum(rows) AS `总行数`
FROM system.parts
GROUP BY table
ORDER BY `总行数` ASC

Query id: fe1aec86-8b4f-4fc3-8184-aa73a55ad879

┌─explain───────────────────────────────────────┐
│ (Expression)                                  │
│ ExpressionTransform                           │
│   (MergingSorted)                             │
│     (MergeSorting)                            │
│     MergeSortingTransform                     │
│       (PartialSorting)                        │
│       LimitsCheckingTransform                 │
│         PartialSortingTransform               │
│           (Expression)                        │
│           ExpressionTransform                 │
│             (Aggregating)                     │
│             AggregatingTransform              │
│               (Expression)                    │
│               ExpressionTransform             │
│                 (SettingQuotaAndLimits)       │
│                   (ReadFromStorage)           │
│                   SourceFromSingleChunk 0 → 1 │
└───────────────────────────────────────────────┘

17 rows in set. Elapsed: 0.002 sec.
```

老版本查看执行计划

```sql
clickhouse-client -h 127.0.0.1 --send_logs_level=trace <<< " select number as x from numbers(10);" > /dev/null
```

输出：

```sql
[root@ck ~]# clickhouse-client -h 127.0.0.1 --send_logs_level=trace <<< " select number as x from numbers(10);" > /dev/null
[ck] 2024.11.07 23:55:38.759547 [ 1361 ] {ae0b07a7-f0ca-4d0d-a932-f39fda69f254} <Debug> executeQuery: (from 127.0.0.1:58488, using production parser)  select number as x from numbers(10); 
[ck] 2024.11.07 23:55:38.759658 [ 1361 ] {ae0b07a7-f0ca-4d0d-a932-f39fda69f254} <Trace> ContextAccess (default): Access granted: CREATE TEMPORARY TABLE ON *.*
[ck] 2024.11.07 23:55:38.759782 [ 1361 ] {ae0b07a7-f0ca-4d0d-a932-f39fda69f254} <Trace> InterpreterSelectQuery: FetchColumns -> Complete
[ck] 2024.11.07 23:55:38.760165 [ 1361 ] {ae0b07a7-f0ca-4d0d-a932-f39fda69f254} <Information> executeQuery: Read 10 rows, 80.00 B in 0.000588194 sec., 17001 rows/sec., 132.82 KiB/sec.
[ck] 2024.11.07 23:55:38.760182 [ 1361 ] {ae0b07a7-f0ca-4d0d-a932-f39fda69f254} <Debug> MemoryTracker: Peak memory usage (for query): 0.00 B.
```



### 12.2 数据类型

**建表时的时间字段**

```sql
# 建表语句
create table mt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time INT32    
)engine=MergeTree
partition by toYYYYMMDD(toDate(create_time))     -- 如果使用了int类型，还需要做一次转换。
primary key (id)
order by (id,order_id,create_time);
```

建表时能用数值型或者日期时间型表示的字段就不要用字符串。

虽然Clickhouse底层将DateTime存储为时间戳Long类型，但不建议存储Long类型，因为DateTime不需要经过函数转换，执行效率高，可读取性好。



**空值存储类型**

```sh
https://clickhouse.com/docs/en/sql-reference/data-types/nullable

Note
Using Nullable almost always negatively affects performance, keep this in mind when designing your databases.
```

在Clickhouse的官方已经指出，Nullable类型会拖累性能。

具体的原因为2点：

1. 存储Nullable列时需要创建一个额外的文件存储Null的标记。
2. Nullablle 列无法被索引。

除非极其特殊的情况，使用Null，否则应该直接使用字段的默认值表示空，或者自行指定一个业务中无意义的值（例如数字-1）

```sql
create database null_demo;
create table null_demo.user_null(x Int8, y Nullable(Int8)) engine=TinyLog;
insert into null_demo.user_null values(5,null),(6,2);
select x + y from null_demo.user_null;
```

输出:

```sql
# 建库
ck :) create database null_demo;

CREATE DATABASE null_demo

Query id: 9a8170f5-56ec-4979-b70f-936d2f759e89

Ok.

0 rows in set. Elapsed: 0.017 sec. 


# 建表
ck :) create table null_demo.user_null(x Int8, y Nullable(Int8)) engine=TinyLog;

CREATE TABLE null_demo.user_null
(
    `x` Int8,
    `y` Nullable(Int8)
)
ENGINE = TinyLog

Query id: df13ae46-de81-4336-87b0-fabec08f1d7b

Ok.

0 rows in set. Elapsed: 0.016 sec. 

# 插入数据
ck :) insert into null_demo.user_null values(5,null),(6,2);

INSERT INTO null_demo.user_null VALUES

Query id: cffc13c5-ac5f-43d1-97c9-5a5e27788a55

Ok.

2 rows in set. Elapsed: 0.002 sec. 

# 查询
ck :) select x + y from null_demo.user_null;

SELECT x + y
FROM null_demo.user_null

Query id: a465b916-e440-49a6-b056-40f1283df19c

┌─plus(x, y)─┐
│       ᴺᵁᴸᴸ │
│          8 │
└────────────┘

2 rows in set. Elapsed: 0.002 sec. 


# 至存储目录查看文件,可以发现，null值多了一个列来专门存储。
[root@ck user_null]# cd /var/lib/clickhouse/data/null_demo/user_null
[root@ck user_null]# ls -l
总用量 16
-rw-r-----. 1 clickhouse clickhouse 91 11月  8 09:09 sizes.json
-rw-r-----. 1 clickhouse clickhouse 28 11月  8 09:09 x.bin
-rw-r-----. 1 clickhouse clickhouse 28 11月  8 09:09 y.bin
-rw-r-----. 1 clickhouse clickhouse 28 11月  8 09:09 y.null.bin
```



### 12.3 分区与索引

分区粒度根据业务特点决定。不宜过粗或者过细。一般选择按天分区，也可以指定为Tuple(), 以单表1亿数据为例，分区大小控制在10-30个为最佳。

必须指定索引列。Clickhouse中的索引列，即排序列，通过order by指定，一般在查询条件中经常被用来充当筛选条件的属性被纳入进来；可以是单一维度，也可以是组合的索引；通常需要满足高级列在前、查询频率大的在前的原则；还有基数特别大的不适合做索引列， 如用户表的userId字段；通常筛选后的数据满足在百万以内为最佳。

**索引参数**

```sql
SETTINGS index_granularity = 8192;
```

index_granularity是用来控制索引粒度的，默认就是8192，Clickhouse为跳数索引，每次遍历按此粒度跳过8192个数据，以快速定位数据。如非必须不建议调整。

### 12.4 写入与删除优化

1) 尽量不要执行单条或者小批量删除和插入操作，这样会产生小分区文件，给后台Merge带来巨大压力。
2) 不要一次写入太多分区，或者数据写入太快，数据写入太快会导致Merge速度跟不上而报错，一般建议每秒钟发起2-3次写入操作，每次操作写入2W-5W数据（依服务器性能而定）

写入过快的报错：

```sh
1. Code: 252, e.displayText() = DB::Exception: Too many parts(304). 
Merges are processing significantly slower than inserts

2. Code: 241, e.displayText() = DB::Exception: Memory limit (for query) 
exceeded:would use 9.37 GiB (attempt to allocate chunk of 301989888 
bytes), maximum: 9.31 GiB
```

处理方式：

"Too many parts"

使用预写日志，提高写入性能。将参数：in_memory_parts_enable_wal 设置为true

在服务器内存充足的情况下，增加内存配额，一般通过max_memory_usage来实现

在服务器内存不充足的情况下，建议将超出部分内容分配到系统硬盘上，但会降低执行速度，一般通过max_bytes_before_external_group_by、max_bytes_before_external_sort 参数 来实现。





### 12.5 常用配制

配置项主要在config.xml或者user.xml中，基本都在user.xml中

config.xml的配制项

```javascript
https://clickhouse.com/docs/en/operations/server-configuration-parameters/settings
```

user.xml的配制项：

```sh
https://clickhouse.com/docs/en/operations/settings/settings
```

#### 12.4.1 CPU

| 配制                                      | 描述                                                         |
| ----------------------------------------- | ------------------------------------------------------------ |
| background_pool_size                      | 后台线程池的大小，merge线程就是在该线程池中执行，该线程池不仅仅给merge线程用，默认16，允许的前提下建议改成CPU核数的2倍 |
| background_schedule_pool_size             | 执行后台任务（复制表、kafka流、DNS缓存更新）的线程数，默认128， |
| background_distributed_schedule_pool_size | 设置为分布式发送执行后台任务的线程数，默认为16，建议改成CPU核数*2 |
| max_concurrent_queries                    | 最大并发处理的请求数（包含select,insert等）默认值100，推荐150，最大300 |
| max_thread                                | 设置凌晨个查询所能使用的最大的CPU个数，默认就是CPU核数。     |

优先在user.xml文件中配制。



#### 12.4.2 内存

| 配制                               | 描述                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| max_memroy_usage                   | 此参数在users.xml表示单次query占用内存最大值，该值可以设置的比较大，这样可以提升集群查询的上限。保留一点给OS，比如128G的内存，设置为100GB |
| max_bytes_before_external_group_by | 一般按照max_memory_usage的一半设置内存，当group使用内存超过阈值后，会刷新到磁盘进行。               因为clickhouse聚合分为两阶段。查询并及建立中间数据、合并中间数据，结合上一项，建议50GB。 |
| max_bytes_before_external_sort     | 当order by已经使用max_bytes_before_external_sort内存进行溢写磁盘（基于磁盘排序），如果不设置该值，那么当内存不够直接抛错，设置了该值order by可以正常完成，但是速度正对来说肯定要慢点（实则慢非常多） |
| max_table_size_to_drop             | 此参数在config.xml中，应用于需要删除表或者分区的情况，默认是50GB，意思是如果删除50GB以上的分区表就会失败，建议修改为0，这样不管多大的分区表都可以删除。 |



#### 12.4.3 硬盘

​	clickhouse不支持设置多数据目录，为了提升数据IO性能，可以挂载虚拟券组，一个券组绑定多块物理磁盘提升读写性能，多数据查询SSD会比普通机械快2-3倍。

## 13 查询优化

### 13.1 单表优化

#### 13.1.1 **count优化**

在调用count时如果使用的是count()或者count(*),或者count(1),且没有where条件，则会直接使用system.tables的total_rows

如果count指定具体的列，则不会优化此项。

```sh
# 不指定具体的列
EXPLAIN select count() from datasets.hits_v1;
EXPLAIN select count(*) from datasets.hits_v1;
EXPLAIN select count(1) from datasets.hits_v1;

# 指定具体的列，优化措施，将失效
EXPLAIN select count(CounterID) from datasets.hits_v1;
```

输出:

```sh
# 不指定具体的列，将会使用优化措施
ck :) EXPLAIN select count() from datasets.hits_v1;

EXPLAIN
SELECT count()
FROM datasets.hits_v1

Query id: c00b86cf-b3e3-463c-9ec6-232512a6c2cf

┌─explain──────────────────────────────────────────────┐
│ Expression ((Projection + Before ORDER BY))          │
│   MergingAggregated                                  │
│     ReadFromPreparedSource (Optimized trivial count) │
└──────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.002 sec. 


ck :) EXPLAIN select count(*) from datasets.hits_v1;

EXPLAIN
SELECT count(*)
FROM datasets.hits_v1

Query id: 73e55e28-8f97-4516-8a02-e21a318c8253

┌─explain──────────────────────────────────────────────┐
│ Expression ((Projection + Before ORDER BY))          │
│   MergingAggregated                                  │
│     ReadFromPreparedSource (Optimized trivial count) │
└──────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.005 sec. 

ck :) EXPLAIN select count(1) from datasets.hits_v1;

EXPLAIN
SELECT count(1)
FROM datasets.hits_v1

Query id: d895f495-a0dc-4d05-a720-44bfe4f37200

┌─explain──────────────────────────────────────────────┐
│ Expression ((Projection + Before ORDER BY))          │
│   MergingAggregated                                  │
│     ReadFromPreparedSource (Optimized trivial count) │
└──────────────────────────────────────────────────────┘

3 rows in set. Elapsed: 0.004 sec. 

# 此将进行真正的查询操作返回。
ck :) EXPLAIN select count(CounterID) from datasets.hits_v1;

EXPLAIN
SELECT count(CounterID)
FROM datasets.hits_v1

Query id: ede92fee-1c85-417f-8112-3355ce37977d

┌─explain───────────────────────────────────────────────────────────────────────┐
│ Expression ((Projection + Before ORDER BY))                                   │
│   Aggregating                                                                 │
│     Expression (Before GROUP BY)                                              │
│       SettingQuotaAndLimits (Set limits and quota after reading from storage) │
│         ReadFromMergeTree                                                     │
└───────────────────────────────────────────────────────────────────────────────┘

5 rows in set. Elapsed: 0.001 sec. 
```

Optimized trivial count 此就是对count做的优化。

#### 13.1.2 **使用Prewhere替代where**

​	prewhere和where语句的作用相同，用来过滤数据，不同之处在于prewhere只支持MergeTree引擎，首先会读取指定的列数据，来判断数据过滤，等过滤之后再读取select声明的列字段补齐其余属性。

​	当查询列多于筛选列时使用Prewhere可十倍提升查询性能，prewhere会自动优化执行过滤阶段的数据读取方式，降低IO操作。

​	在某此场景下,Prewhere语句比where语句处理的数据量更少性能更高。

```sh
# SQL被自动优化成prewhere
EXPLAIN SYNTAX
select WatchID, 
JavaEnable, 
Title, 
GoodEvent, 
EventTime, 
EventDate, 
CounterID, 
ClientIP, 
ClientIP6, 
RegionID, 
UserID, 
CounterClass, 
OS, 
UserAgent, 
URL, 
Referer, 
URLDomain, 
RefererDomain, 
Refresh, 
IsRobot, 
RefererCategories, 
URLCategories, 
URLRegions, 
RefererRegions, 
ResolutionWidth, 
ResolutionHeight, 
ResolutionDepth, 
FlashMajor, 
FlashMinor, 
FlashMinor2
from datasets.hits_v1 where UserID='3198390223272470366';


# 关闭where自动转prewhere(在mergeTree引擎下，默认会将where条件转换成prewhere)
set optimize_move_to_prewhere=0;

# 使用 where
select WatchID, 
JavaEnable, 
Title, 
GoodEvent, 
EventTime, 
EventDate, 
CounterID, 
ClientIP, 
ClientIP6, 
RegionID, 
UserID, 
CounterClass, 
OS, 
UserAgent, 
URL, 
Referer, 
URLDomain, 
RefererDomain, 
Refresh, 
IsRobot, 
RefererCategories, 
URLCategories, 
URLRegions, 
RefererRegions, 
ResolutionWidth, 
ResolutionHeight, 
ResolutionDepth, 
FlashMajor, 
FlashMinor, 
FlashMinor2
from datasets.hits_v1 where UserID='3198390223272470366';

# 使用prewhere来查询
select WatchID, 
JavaEnable, 
Title, 
GoodEvent, 
EventTime, 
EventDate, 
CounterID, 
ClientIP, 
ClientIP6, 
RegionID, 
UserID, 
CounterClass, 
OS, 
UserAgent, 
URL, 
Referer, 
URLDomain, 
RefererDomain, 
Refresh, 
IsRobot, 
RefererCategories, 
URLCategories, 
URLRegions, 
RefererRegions, 
ResolutionWidth, 
ResolutionHeight, 
ResolutionDepth, 
FlashMajor, 
FlashMinor, 
FlashMinor2
from datasets.hits_v1 prewhere UserID='3198390223272470366';


# 打开优化设置
set optimize_move_to_prewhere=1;

select WatchID, 
JavaEnable, 
Title, 
GoodEvent, 
EventTime, 
EventDate, 
CounterID, 
ClientIP, 
ClientIP6, 
RegionID, 
UserID, 
CounterClass, 
OS, 
UserAgent, 
URL, 
Referer, 
URLDomain, 
RefererDomain, 
Refresh, 
IsRobot, 
RefererCategories, 
URLCategories, 
URLRegions, 
RefererRegions, 
ResolutionWidth, 
ResolutionHeight, 
ResolutionDepth, 
FlashMajor, 
FlashMinor, 
FlashMinor2
from datasets.hits_v1 where UserID='3198390223272470366';

```

输出:

```sh
ck :) EXPLAIN SYNTAX
:-] select WatchID, 
:-] JavaEnable, 
:-] Title, 
:-] GoodEvent, 
:-] EventTime, 
:-] EventDate, 
:-] CounterID, 
:-] ClientIP, 
:-] ClientIP6, 
:-] RegionID, 
:-] UserID, 
:-] CounterClass, 
:-] OS, 
:-] UserAgent, 
:-] URL, 
:-] Referer, 
:-] URLDomain, 
:-] RefererDomain, 
:-] Refresh, 
:-] IsRobot, 
:-] RefererCategories, 
:-] URLCategories, 
:-] URLRegions, 
:-] RefererRegions, 
:-] ResolutionWidth, 
:-] ResolutionHeight, 
:-] ResolutionDepth, 
:-] FlashMajor, 
:-] FlashMinor, 
:-] FlashMinor2
:-] from datasets.hits_v1 where UserID='3198390223272470366';

EXPLAIN SYNTAX
SELECT
    WatchID,
    JavaEnable,
    Title,
    GoodEvent,
    EventTime,
    EventDate,
    CounterID,
    ClientIP,
    ClientIP6,
    RegionID,
    UserID,
    CounterClass,
    OS,
    UserAgent,
    URL,
    Referer,
    URLDomain,
    RefererDomain,
    Refresh,
    IsRobot,
    RefererCategories,
    URLCategories,
    URLRegions,
    RefererRegions,
    ResolutionWidth,
    ResolutionHeight,
    ResolutionDepth,
    FlashMajor,
    FlashMinor,
    FlashMinor2
FROM datasets.hits_v1
WHERE UserID = '3198390223272470366'

Query id: c14a9d66-7b5d-4cd6-af80-5a7a1a595be9

┌─explain─────────────────────────────────┐
│ SELECT                                  │
│     WatchID,                            │
│     JavaEnable,                         │
│     Title,                              │
│     GoodEvent,                          │
│     EventTime,                          │
│     EventDate,                          │
│     CounterID,                          │
│     ClientIP,                           │
│     ClientIP6,                          │
│     RegionID,                           │
│     UserID,                             │
│     CounterClass,                       │
│     OS,                                 │
│     UserAgent,                          │
│     URL,                                │
│     Referer,                            │
│     URLDomain,                          │
│     RefererDomain,                      │
│     Refresh,                            │
│     IsRobot,                            │
│     RefererCategories,                  │
│     URLCategories,                      │
│     URLRegions,                         │
│     RefererRegions,                     │
│     ResolutionWidth,                    │
│     ResolutionHeight,                   │
│     ResolutionDepth,                    │
│     FlashMajor,                         │
│     FlashMinor,                         │
│     FlashMinor2                         │
│ FROM datasets.hits_v1                   │
│ PREWHERE UserID = '3198390223272470366' │
└─────────────────────────────────────────┘

33 rows in set. Elapsed: 0.012 sec.



# 关闭自动优化
ck :) set optimize_move_to_prewhere=0;

SET optimize_move_to_prewhere = 0

Query id: 4f82c228-3077-4926-a8f6-707591de933d

Ok.

0 rows in set. Elapsed: 0.002 sec. 

ck :) select WatchID, 
:-] JavaEnable, 
:-] Title, 
:-] GoodEvent, 
:-] EventTime, 
:-] EventDate, 
:-] CounterID, 
:-] ClientIP, 
:-] ClientIP6, 
:-] RegionID, 
:-] UserID, 
:-] CounterClass, 
:-] OS, 
:-] UserAgent, 
:-] URL, 
:-] Referer, 
:-] URLDomain, 
:-] RefererDomain, 
:-] Refresh, 
:-] IsRobot, 
:-] RefererCategories, 
:-] URLCategories, 
:-] URLRegions, 
:-] RefererRegions, 
:-] ResolutionWidth, 
:-] ResolutionHeight, 
:-] ResolutionDepth, 
:-] FlashMajor, 
:-] FlashMinor, 
:-] FlashMinor2
:-] from datasets.hits_v1 where UserID='3198390223272470366';

SELECT
    WatchID,
    JavaEnable,
    Title,
    GoodEvent,
    EventTime,
    EventDate,
    CounterID,
    ClientIP,
    ClientIP6,
    RegionID,
    UserID,
    CounterClass,
    OS,
    UserAgent,
    URL,
    Referer,
    URLDomain,
    RefererDomain,
    Refresh,
    IsRobot,
    RefererCategories,
    URLCategories,
    URLRegions,
    RefererRegions,
    ResolutionWidth,
    ResolutionHeight,
    ResolutionDepth,
    FlashMajor,
    FlashMinor,
    FlashMinor2
FROM datasets.hits_v1
WHERE UserID = '3198390223272470366'

Query id: 41de1864-0852-4532-87c9-65455dd24686

# 省略内容输出
152 rows in set. Elapsed: 1.216 sec. Processed 8.87 million rows, 3.86 GB (7.30 million rows/s., 3.17 GB/s.)

ck :) 



# 使用prewhere关键字
ck :) 
ck :) 
ck :) select WatchID, 
:-] JavaEnable, 
:-] Title, 
:-] GoodEvent, 
:-] EventTime, 
:-] EventDate, 
:-] CounterID, 
:-] ClientIP, 
:-] ClientIP6, 
:-] RegionID, 
:-] UserID, 
:-] CounterClass, 
:-] OS, 
:-] UserAgent, 
:-] URL, 
:-] Referer, 
:-] URLDomain, 
:-] RefererDomain, 
:-] Refresh, 
:-] IsRobot, 
:-] RefererCategories, 
:-] URLCategories, 
:-] URLRegions, 
:-] RefererRegions, 
:-] ResolutionWidth, 
:-] ResolutionHeight, 
:-] ResolutionDepth, 
:-] FlashMajor, 
:-] FlashMinor, 
:-] FlashMinor2
:-] from datasets.hits_v1 prewhere UserID='3198390223272470366';

SELECT
    WatchID,
    JavaEnable,
    Title,
    GoodEvent,
    EventTime,
    EventDate,
    CounterID,
    ClientIP,
    ClientIP6,
    RegionID,
    UserID,
    CounterClass,
    OS,
    UserAgent,
    URL,
    Referer,
    URLDomain,
    RefererDomain,
    Refresh,
    IsRobot,
    RefererCategories,
    URLCategories,
    URLRegions,
    RefererRegions,
    ResolutionWidth,
    ResolutionHeight,
    ResolutionDepth,
    FlashMajor,
    FlashMinor,
    FlashMinor2
FROM datasets.hits_v1
PREWHERE UserID = '3198390223272470366'

Query id: adaf4023-ad75-4a21-a131-838008665146
# 省略内容
152 rows in set. Elapsed: 0.094 sec. Processed 8.87 million rows, 119.04 MB (94.28 million rows/s., 1.26 GB/s.)

ck :) 

# 打开优化设置
ck :) set optimize_move_to_prewhere=1;

SET optimize_move_to_prewhere = 1

Query id: c4132092-acdc-41ab-86d7-ff359bd548b9

Ok.

0 rows in set. Elapsed: 0.064 sec. 

# 使用where来查询
ck :) select WatchID, 
:-] JavaEnable, 
:-] Title, 
:-] GoodEvent, 
:-] EventTime, 
:-] EventDate, 
:-] CounterID, 
:-] ClientIP, 
:-] ClientIP6, 
:-] RegionID, 
:-] UserID, 
:-] CounterClass, 
:-] OS, 
:-] UserAgent, 
:-] URL, 
:-] Referer, 
:-] URLDomain, 
:-] RefererDomain, 
:-] Refresh, 
:-] IsRobot, 
:-] RefererCategories, 
:-] URLCategories, 
:-] URLRegions, 
:-] RefererRegions, 
:-] ResolutionWidth, 
:-] ResolutionHeight, 
:-] ResolutionDepth, 
:-] FlashMajor, 
:-] FlashMinor, 
:-] FlashMinor2
:-] from datasets.hits_v1 where UserID='3198390223272470366';

SELECT
    WatchID,
    JavaEnable,
    Title,
    GoodEvent,
    EventTime,
    EventDate,
    CounterID,
    ClientIP,
    ClientIP6,
    RegionID,
    UserID,
    CounterClass,
    OS,
    UserAgent,
    URL,
    Referer,
    URLDomain,
    RefererDomain,
    Refresh,
    IsRobot,
    RefererCategories,
    URLCategories,
    URLRegions,
    RefererRegions,
    ResolutionWidth,
    ResolutionHeight,
    ResolutionDepth,
    FlashMajor,
    FlashMinor,
    FlashMinor2
FROM datasets.hits_v1
WHERE UserID = '3198390223272470366'

Query id: a5c2cce9-d285-4bac-bcfd-0c1ecbf88f21
# 省略内容

152 rows in set. Elapsed: 0.087 sec. Processed 8.87 million rows, 119.04 MB (101.86 million rows/s., 1.37 GB/s.)

ck :) 
```

默认情况下，肯定不会关闭where自动优化成prewhere，但是在某些场景下，即使开启优化，也不会自动转换成prewhere，需要手动指定为prewhere。

1)  使用了常量表达式。
2) 使用默认值为alias类型的字段。
3) 包含了arrayJoin, globalIn，globalNotIn或者indexHint的查询
4) select查询的列字段和where的谓词相同。
5) 使用了主键字段。

场景演示，不会自动转换为prewhere

```sql
# 1)  使用了常量表达式。
EXPLAIN SYNTAX SELECT WatchID, Title FROM datasets.hits_v1 WHERE UserID = '3198390223272470366' and  1 = 1;

┌─explain────────────────────────────────────────────┐
│ SELECT                                             │
│     WatchID,                                       │
│     Title                                          │
│ FROM datasets.hits_v1                              │
│ PREWHERE UserID = '3198390223272470366'            │
│ WHERE (UserID = '3198390223272470366') AND (1 = 1) │
└────────────────────────────────────────────────────┘

# 2)  使用默认值为alias类型的字段。
-- 假设 Title 的默认值类型是 ALIAS
SELECT WatchID, Title FROM datasets.hits_v1 WHERE Title = 'A000'


# 3)  包含了arrayJoin, globalIn，globalNotIn或者indexHint的查询

# 4)  select查询的列字段和where的谓词相同。
EXPLAIN SYNTAX SELECT UserID FROM datasets.hits_v1 WHERE UserID = '3198390223272470366' ;

┌─explain──────────────────────────────┐
│ SELECT UserID                        │
│ FROM datasets.hits_v1                │
│ WHERE UserID = '3198390223272470366' │
└──────────────────────────────────────┘

# 5)  使用了主键字段。
EXPLAIN SYNTAX  SELECT CounterID FROM datasets.hits_v1 WHERE  CounterID = '57'

┌─explain────────────────┐
│ SELECT CounterID       │
│ FROM datasets.hits_v1  │
│ WHERE CounterID = '57' │
└────────────────────────┘

```

#### 13.1.3 **数据采样**

```sql
# SAMPLE 0.1 表示采样10%，也可以是具体的条数
SELECT 
Title,count(1) as pageViews
FROM datasets.hits_v1 
SAMPLE 0.1
group by Title
order by pageViews desc
limit 3;


# 转换后执行的SQL
SELECT
    Title,
    count(1) AS pageViews
FROM datasets.hits_v1
SAMPLE 1 / 10
GROUP BY Title
ORDER BY pageViews DESC
LIMIT 3

Query id: 9dcefb3e-10df-4219-9c9c-f1a9ad5d3a37

┌─Title─────────┬─pageViews─┐
│               │    234143 │
│ HD Tube 5*    │     20146 │
│ Играть кварти │      4511 │
└───────────────┴───────────┘

3 rows in set. Elapsed: 0.273 sec. Processed 7.41 million rows, 725.76 MB (27.15 million rows/s., 2.66 GB/s.)

ck :)
```

数据采样只有对MergeTree 引擎表中才有效，县城在创建表时需要指定采样策略。



#### 13.1.4 **列裁剪与分区裁剪**

数据量大时，应避免使用select *，查询性能会与查询的字段大小和数量成线性，字段越少，消耗的IO资源越少，性能就会越高。

```sh
# 反例：
select * from datasets.hits_v1 limit 100;

# 指定需要的字段
select
WatchID,
JavaEnable,
Title
from 
datasets.hits_v1
limit 100;
```

输出:

```shell
# 反例
ck :) select * from datasets.hits_v1 limit 100;

SELECT *
FROM datasets.hits_v1
LIMIT 100

Query id: 804e4f37-d112-4010-bf59-fc758505046b
# 略过数据内容
100 rows in set. Elapsed: 0.029 sec. 


# 指定列查询
ck :) select
:-] WatchID,
:-] JavaEnable,
:-] Title
:-] from 
:-] datasets.hits_v1
:-] limit 100;

SELECT
    WatchID,
    JavaEnable,
    Title
FROM datasets.hits_v1
LIMIT 100

Query id: 7f4a1b2e-bb76-4d9b-9662-17b200b7e0b3
# 略过数据内容
100 rows in set. Elapsed: 0.004 sec.
```

分区裁前就是只读取需要的分区，在过滤条件中指定

```sql
EXPLAIN 
select WatchID, 
 JavaEnable, 
 Title, 
 GoodEvent, 
 EventTime, 
 EventDate, 
 CounterID, 
 ClientIP, 
 ClientIP6, 
 RegionID, 
 UserID
from datasets.hits_v1
where EventDate='2014-03-23'
limit 10;
;
```



#### 13.1.5  **order by使用时注意事项**

千万以上的数据集进行order by时，需要搭配where条件和limit语句一起使用

```sql
# 反例
select UserID,Age
FROM hists_v1
ORDER BY AGE DESC;

# 一般使用的语法
select UserID,Age
FROM hists_v1
where CounterID=57
ORDER BY AGE DESC 
limit 100;
```



#### 13.1.6 避免使用虚拟列

虚拟列，即为表中不存在列。比如两个列进行乘法或者除法运算。

如非必要，不要在结果集上构建虚拟列，虚拟列是非常消耗资源浪费性能，可以考滤在查询完数据后，进行数据处理。或者在表中构建实际的字段进行额外的存储。

```sh
# 反例
select 
Income,UserID,(Income/UserID) as out
from 
datasets.visits_v1
limit 1000;

# 结果时间
1000 rows in set. Elapsed: 0.016 sec. Processed 1.00 thousand rows, 9.00 KB (63.97 thousand rows/s., 575.72 KB/s.)

# 直接获取
select 
Income,UserID
from 
datasets.visits_v1
limit 1000;

# 结果时间
1000 rows in set. Elapsed: 0.004 sec. Processed 1.00 thousand rows, 9.00 KB (263.32 thousand rows/s., 2.37 MB/s.)
```

通过对比发现，带上一个虚拟的列进行计算后，响应的时间相差了4倍。

#### 13.1.7 使用近似计算替代精确计算

**uniqCombined 替代 distinct**

性能可提供10倍以上， uniqCombined能接受2%左右数据误差，而精准去重使用的是uniqExact

千万不建议在千万级别的数据上执行distinct去重查询，改为近似去重。

```sql
# 近似
select uniqCombined(rand()) from datasets.hits_v1;

# 结果
┌─uniqCombined(rand())─┐
│              8877260 │
└──────────────────────┘

1 rows in set. Elapsed: 0.122 sec. Processed 8.87 million rows, 80.31 MB (72.54 million rows/s., 656.45 MB/s.)


# 精确
select count(distinct rand()) from datasets.hits_v1;


# 结果
┌─uniqExact(rand())─┐
│           8864849 │
└───────────────────┘

1 rows in set. Elapsed: 0.344 sec. Processed 8.87 million rows, 80.31 MB (25.77 million rows/s., 233.26 MB/s.)

```



#### 13.1.8 物化视图

后续有专门的章节的笔记 



#### 13.1.9 其他注意事项

1. 查询熔断。

为了避免因为个别查询引起服务的雪崩问题，除了可以为单个查询设置超时时间以外，还可以配制周期熔断，在一个查询周期内，如果用户频繁进行慢查询操作，超出规定阈值后无法进行查询操作。

2. 关闭虚拟内存

物理内存与虚拟内存的数据交换，会导致查询变慢，资源允许的情况下关闭虚拟内存。

3. 配制join_use_nulls

为每个一账号添加join_use_nulls配制，左右中的一条记录在右表中不存在，右表的相关字段会返回该字段相应数据类型的默认值，而不标准SQL中的NULL值。

4. 批量写入时先排序。

批量写入数据时，必须控制每个批次的数据中涉及到的分区的数量，在写入之前最好对需要导入的数据进行排序。无序的数据或者涉及分区太多，会导致Clickhouse无法及时对新导入的数据进行合并，从而影响查询性能。

5. 关注CPU

CPU一般在50%左右会出现查询波动，达到70%会出现大范围的查询超时，CPU最是最关键的指标，要非常关注。



实际操作

写入时排序与不排序的实验

```sql
create database nullnull;


create table nullnull.replicatemt_user(
	id UInt32,
    order_id String, 
    name String,
    money decimal(16,2),
    create_time Datetime    
)engine==MergeTree()
partition by toYYYYMMDD(create_time)
primary key (id)
order by (id,order_id,create_time);


truncate table  nullnull.replicatemt_user;





# 测试写入20万行数据,有序数据集

[root@ck clickhouse]# clickhouse-client    --send_logs_level=trace <<< "$(cat /root/clickhouse/order_insert.sql)"  > /dev/null
[ck] 2024.11.13 23:40:01.789669 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Debug> executeQuery: (from [::1]:40242) insert into nullnull.replicatemt_user values  (stage: Complete)
[ck] 2024.11.13 23:40:01.789898 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> ContextAccess (default): Access granted: INSERT(id, order_id, name, money, create_time) ON nullnull.replicatemt_user
[ck] 2024.11.13 23:40:02.204982 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 1.00 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.205077 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 1.00 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.212243 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241112_3_3_0 (state Temporary)
[ck] 2024.11.13 23:40:02.214744 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.14 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.214819 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 2.14 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.231791 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241113_4_4_0 (state Temporary)
[ck] 2024.11.13 23:40:02.232411 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241112_3_3_0 to 20241112_3_3_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.234388 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.15 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.234439 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 2.15 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.248380 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241114_5_5_0 (state Temporary)
[ck] 2024.11.13 23:40:02.248978 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241113_4_4_0 to 20241113_4_4_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.250648 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.22 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.250696 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 2.22 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.265352 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241115_6_6_0 (state Temporary)
[ck] 2024.11.13 23:40:02.265913 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241114_5_5_0 to 20241114_5_5_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.267660 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.22 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.267704 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 2.22 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.281699 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241116_7_7_0 (state Temporary)
[ck] 2024.11.13 23:40:02.282256 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241115_6_6_0 to 20241115_6_6_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.282751 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 1.00 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:40:02.282797 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> DiskLocal: Reserved 1.00 MiB on local disk `default`, having unreserved 45.40 GiB.
[ck] 2024.11.13 23:40:02.285955 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> MergedBlockOutputStream: filled checksums 20241117_8_8_0 (state Temporary)
[ck] 2024.11.13 23:40:02.286464 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241116_7_7_0 to 20241116_7_7_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.286782 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241117_8_8_0 to 20241117_8_8_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:40:02.287174 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Information> executeQuery: Read 200000 rows, 10.09 MiB in 0.497449614 sec., 402050 rows/sec., 20.28 MiB/sec.
[ck] 2024.11.13 23:40:02.287240 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Debug> MemoryTracker: Peak memory usage (for query): 45.24 MiB.
[ck] 2024.11.13 23:40:02.287271 [ 2433 ] {8d4f794c-161a-4264-8a96-6f1b1318ebdf} <Debug> TCPHandler: Processed in 0.497953854 sec.



# 无序数据集插入，同样20万

[root@ck clickhouse]# clickhouse-client    --send_logs_level=trace <<< "$(cat /root/clickhouse/not_order_insert.sql)"  > /dev/null
[ck] 2024.11.13 23:47:40.755011 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Debug> executeQuery: (from [::1]:38018) insert into nullnull.replicatemt_user values  (stage: Complete)
[ck] 2024.11.13 23:47:40.755179 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> ContextAccess (default): Access granted: INSERT(id, order_id, name, money, create_time) ON nullnull.replicatemt_user
[ck] 2024.11.13 23:47:41.191773 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 1.12 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:47:41.191851 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> DiskLocal: Reserved 1.12 MiB on local disk `default`, having unreserved 45.39 GiB.
[ck] 2024.11.13 23:47:41.200601 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> MergedBlockOutputStream: filled checksums 20241112_9_9_0 (state Temporary)
[ck] 2024.11.13 23:47:41.203244 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.53 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:47:41.203331 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> DiskLocal: Reserved 2.53 MiB on local disk `default`, having unreserved 45.39 GiB.
[ck] 2024.11.13 23:47:41.221249 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> MergedBlockOutputStream: filled checksums 20241113_10_10_0 (state Temporary)
[ck] 2024.11.13 23:47:41.222810 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241112_9_9_0 to 20241112_9_9_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:47:41.224608 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.52 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:47:41.224658 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> DiskLocal: Reserved 2.52 MiB on local disk `default`, having unreserved 45.38 GiB.
[ck] 2024.11.13 23:47:41.241223 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> MergedBlockOutputStream: filled checksums 20241114_11_11_0 (state Temporary)
[ck] 2024.11.13 23:47:41.241763 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241113_10_10_0 to 20241113_10_10_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:47:41.243603 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 2.53 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:47:41.243648 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> DiskLocal: Reserved 2.53 MiB on local disk `default`, having unreserved 45.38 GiB.
[ck] 2024.11.13 23:47:41.259631 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> MergedBlockOutputStream: filled checksums 20241115_12_12_0 (state Temporary)
[ck] 2024.11.13 23:47:41.260257 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241114_11_11_0 to 20241114_11_11_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:47:41.261365 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Trying to reserve 1.38 MiB using storage policy from min volume index 0
[ck] 2024.11.13 23:47:41.261412 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> DiskLocal: Reserved 1.38 MiB on local disk `default`, having unreserved 45.38 GiB.
[ck] 2024.11.13 23:47:41.275331 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> MergedBlockOutputStream: filled checksums 20241116_13_13_0 (state Temporary)
[ck] 2024.11.13 23:47:41.275905 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241115_12_12_0 to 20241115_12_12_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:47:41.276254 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Trace> nullnull.replicatemt_user (bb037a4d-ecdf-4bf1-8a3d-046b3909fc93): Renaming temporary part tmp_insert_20241116_13_13_0 to 20241116_13_13_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[ck] 2024.11.13 23:47:41.276634 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Information> executeQuery: Read 200000 rows, 10.09 MiB in 0.521569463 sec., 383458 rows/sec., 19.34 MiB/sec.
[ck] 2024.11.13 23:47:41.276709 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Debug> MemoryTracker: Peak memory usage (for query): 40.33 MiB.
[ck] 2024.11.13 23:47:41.276725 [ 2433 ] {02f330be-8fb6-460f-b4e0-444aac6fbce8} <Debug> TCPHandler: Processed in 0.522081973 sec.


```

可以看出，经过有序后，还有有点点提供，但是在后期的合并中，由于已经有序，合并的压力会小很多。



### 13.2 多表JOIN

在Clickhouse中不推荐使用多表关联的操作。Join查询的时候效率效低，是将右边的数据，完整的加载到内存中,然后从左边中分批读取数据，然后进行Join操作。这一操作，注定关联效率很低。

如果一定要使用JOIN，以下这些操作可以优化：

#### 13.2.1 数据准备

```sql
# 创建数据表
CREATE TABLE nullnull.visits_limit_200000
ENGINE = MergeTree()
PARTITION BY toYYYYMM(StartDate)
ORDER BY (CounterID, StartDate, intHash32(UserID), VisitID)
SAMPLE BY intHash32(UserID)
SETTINGS index_granularity = 8192
as select * from datasets.visits_v1 limit 10000;


#创建 join 结果表：避免控制台疯狂打印数据
CREATE TABLE nullnull.hits_v2 
ENGINE = MergeTree()
PARTITION BY toYYYYMM(EventDate)
ORDER BY (CounterID, EventDate, intHash32(UserID))
SAMPLE BY intHash32(UserID)
SETTINGS index_granularity = 8192
as select * from datasets.hits_v1 where 1=0;

```

#### 13.2.2 **使用IN代替Join**

当多表联查时，查询的数据仅从其中一张表出时，可考虑用IN操作而不是JOIN

操作的过程的SQL

```sql
# 清理数据
truncate table nullnull.hits_v2;

# 使用IN操作
insert into nullnull.hits_v2
select a.* from datasets.hits_v1 a where a. CounterID in (select CounterID from datasets.visits_v1 );

# 清理数据
truncate table nullnull.hits_v2;

# 使用JOIN操作
insert into table nullnull.hits_v2
select a.* from datasets.hits_v1 a left join datasets.visits_v1 b on a. CounterID=b.CounterID;


```

日志

```sh
# 清理数据
ck :) truncate table nullnull.hits_v2;

TRUNCATE TABLE nullnull.hits_v2

Query id: 0e8ed7fe-5d67-4b95-8359-6b609324d3b1

Ok.

0 rows in set. Elapsed: 0.006 sec. 

# 使用IN操作
ck :) insert into nullnull.hits_v2
:-] select a.* from datasets.hits_v1 a where a. CounterID in (select CounterID from datasets.visits_v1 );

INSERT INTO nullnull.hits_v2 SELECT a.*
FROM datasets.hits_v1 AS a
WHERE a.CounterID IN (
    SELECT CounterID
    FROM datasets.visits_v1
)

Query id: d8a0f271-8402-44c4-8894-c0cd7ccac214

Ok.

0 rows in set. Elapsed: 3.146 sec. Processed 6.17 million rows, 5.85 GB (1.96 million rows/s., 1.86 GB/s.)




# 清理数据
ck :) truncate table nullnull.hits_v2;

TRUNCATE TABLE nullnull.hits_v2

Query id: 38f4c640-dcbe-4ae3-8ce7-dd81b7e5e646

Ok.

0 rows in set. Elapsed: 0.004 sec. 

# 使用JOIN操作
ck :) insert into table nullnull.hits_v2
:-] select a.* from datasets.hits_v1 a left join datasets.visits_v1 b on a. CounterID=b.CounterID;

INSERT INTO nullnull.hits_v2 SELECT a.*
FROM datasets.hits_v1 AS a
LEFT JOIN datasets.visits_v1 AS b ON a.CounterID = b.CounterID

Query id: 9dcf75b7-bb70-4365-8648-df1f14b61d9e

Ok.

0 rows in set. Elapsed: 14.071 sec. Processed 10.55 million rows, 8.47 GB (750.03 thousand rows/s., 601.75 MB/s.)


```

作为最直观的对比，使用In操作，仅需3秒，而JOIN操作，使用了14秒，差了5倍，内存使用上，IN使用5.85G，而JOIN使用了8.47G。



#### 13.2.3 大小表JOIN

多表Join时，要满足小表在右的原则，右表关联时被加载到内存中与左表进行比较，ClickHouse中无论是Left Join、Right

Join 还是Inner Join永远都是使着右表中的每一条记录到左表中查询该记录是否存在，所以右表必须是小表。

直观对比

```sh

# datasets.hits_v1   行数 8873898
# datasets.visits_limit_200000 行数 200000

# 清理数据
truncate table nullnull.hits_v2;

# 小表在右
insert into table nullnull.hits_v2
select a.* from datasets.hits_v1 a left join nullnull.visits_limit_200000 b on a.CounterID=b.CounterID;


# 结果：
0 rows in set. Elapsed: 13.502 sec. Processed 8.88 million rows, 8.46 GB (657.95 thousand rows/s., 626.59 MB/s.)

# 大表在右
insert into table nullnull.hits_v2
select a.* from nullnull.visits_limit_200000 b left join datasets.hits_v1 a on a. CounterID=b.CounterID;


# 此将直接导致配制的10G内存不够，直接超出内存限制。
↓ Progress: 262.14 thousand rows, 250.99 MB (1.82 million rows/s., 1.74 GB/s.)  2%
0 rows in set. Elapsed: 0.244 sec. Processed 262.14 thousand rows, 250.99 MB (1.07 million rows/s., 1.03 GB/s.)

Received exception from server (version 21.7.3):
Code: 241. DB::Exception: Received from localhost:9000. DB::Exception: Memory limit (for query) exceeded: would use 955.17 MiB (attempt to allocate chunk of 4484528 bytes), maximum: 953.67 MiB: (avg_value_size_hint = 160.075439453125, avg_chars_size = 182.49052734375, limit = 8192): (while reading column URL): (while reading from part /var/lib/clickhouse/store/a9a/a9a61af4-b8ea-434e-a9a6-1af4b8ea634e/201403_1_6_1/ from mark 0 with max_rows_to_read = 8192): While executing MergeTree. 

```



#### 13.2.4 分布式表使用GLOBAL

两张分布式表上的IN和JOIN之前必须加上GLOBAL关键字，右表只会在接收查询请求的那个节点查询一次，并将其分发到其他节点上。如果不加GLOBAL关键字的话，每个节点都会单独发起一次对右表的查询，而右表又是分布式表，就导致右表一共会被 查询N的平方次（N为该分片表的分片数量），这就是查询放大，会带来很大的开销。



#### 13.2.5 使用字典表

将一些需要关联分析的业务创建成字典表进行JOIN操作，前提是字典表不宜太大，因为字典表会常驻内存。



#### 13.2.6 提前过滤

通过增加过滤可以减少数据扫描，达到提高执行速度及降低内存消耗的目的。

## 14 数据一致性

针对ClickHouse，即便是对数据一致性最好的MergeTree，也只保证最终一致性。

**ReplacingMergeTree**

该引擎和MergeTree的不同之处在于它会删除排序键值相同的重复项。

数据的去重只会在数据合并期间进行。合并会在后台一个不确定的时间进行，因此无法预先作出计划。有一些数据可能仍未被处理。尽管可以调用`OPTIMIZE`语句发起计划外的合并，但不能依靠它，因为`OPTIMIZE`语句会发数据的大量读写。

因此,`ReplacingMergeTree`适用于在后台清除重复的数据以节省空间，但是它并不保证没有重复数据出现。

我们在使用时`ReplacingMergeTree`、`SummmingMergeTree`这类表引擎的时候 ，会出现短暂数据不一致的情况

针对一致性敏感的场景，可以有以下三种解决方案：

数据准备：

```sql
CREATE TABLE nullnull.rmt_user_distinct(
 user_id UInt64,
 score String,
 deleted UInt8 DEFAULT 0,
 create_time DateTime DEFAULT toDateTime(0)
)ENGINE= ReplacingMergeTree(create_time)
ORDER BY user_id;

# user_id 是数据去重更新的标识
# create_time 是版本号，每组数据中create_time最大的一行表示最新的数据；
# deleted是自定的一个标识位，比如0代表未删除，1代表删除数据。


# 写入1000万和测试数据
INSERT INTO TABLE nullnull.rmt_user_distinct(user_id,score)
WITH(
 SELECT ['A','B','C','D','E','F','G']
)AS dict
SELECT number AS user_id, dict[number%7+1] FROM numbers(10000000);


# 修改前 100 万 行数据，修改内容包括 name 字段和 create_time 版本号字段
INSERT INTO TABLE nullnull.rmt_user_distinct(user_id,score,create_time)
WITH(
 SELECT ['AA','BB','CC','DD','EE','FF','GG']
)AS dict
SELECT number AS user_id, dict[number%7+1], now() AS create_time FROM numbers(1000000);


# 统计总数
SELECT COUNT() FROM nullnull.rmt_user_distinct;
11000000
```



### 14.1  手动执行OPTIMIZE

经慎重，生产环境操作可能会阻塞其他SQL的执行。

在写入数据后，立刻执行OPTIMIZE强制触 发新写入分区的合并动作。

```sh
OPTIMIZE TABLE nullnull.rmt_user_distinct FINAL;

#语法：OPTIMIZE TABLE [db.]name [ON CLUSTER cluster] [PARTITION partition | PARTITION ID 'partition_id'] [FINAL] [DEDUPLICATE [BY expression]]
```

查看日志

```sh
[root@ck ~]# clickhouse-client -h 127.0.0.1 --send_logs_level=trace <<< " OPTIMIZE TABLE nullnull.rmt_user_distinct FINAL;" > /dev/null
[ck] 2024.11.15 09:16:27.440947 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> executeQuery: (from 127.0.0.1:58160, using production parser)  OPTIMIZE TABLE nullnull.rmt_user_distinct FINAL; 
[ck] 2024.11.15 09:16:27.441065 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Trace> ContextAccess (default): Access granted: OPTIMIZE ON nullnull.rmt_user_distinct
[ck] 2024.11.15 09:16:27.441096 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb) (MergerMutator): Selected 6 parts from all_1_6_1 to all_11_11_0
[ck] 2024.11.15 09:16:27.441123 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> DiskLocal: Reserving 46.73 MiB on disk `default`, having unreserved 36.61 GiB.
[ck] 2024.11.15 09:16:27.441142 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb) (MergerMutator): Merging 6 parts: from all_1_6_1 to all_11_11_0 into Wide
[ck] 2024.11.15 09:16:27.441165 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb) (MergerMutator): Selected MergeAlgorithm: Horizontal
[ck] 2024.11.15 09:16:27.441192 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 769 marks from part all_1_6_1, total 6291270 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.441351 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 129 marks from part all_7_7_0, total 1048545 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.441445 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 129 marks from part all_8_8_0, total 1048545 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.441524 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 129 marks from part all_9_9_0, total 1048545 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.441585 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 70 marks from part all_10_10_0, total 563095 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.441653 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MergeTreeSequentialSource: Reading 124 marks from part all_11_11_0, total 1000000 rows starting from the beginning of the part
[ck] 2024.11.15 09:16:27.983530 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb) (MergerMutator): Merge sorted 11000000 rows, containing 4 columns (4 merged, 0 gathered) in 0.542382502 sec., 20280890.256301075 rows/sec., 446.61 MiB/sec.
[ck] 2024.11.15 09:16:27.983872 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Trace> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb): Renaming temporary part tmp_merge_all_1_11_2 to all_1_11_2.
[ck] 2024.11.15 09:16:27.983946 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Trace> nullnull.rmt_user_distinct (044f1314-5c27-45eb-844f-13145c27f5eb) (MergerMutator): Merged 6 parts: from all_1_6_1 to all_11_11_0
[ck] 2024.11.15 09:16:27.983964 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MemoryTracker: Peak memory usage: 25.00 MiB.
[ck] 2024.11.15 09:16:27.984016 [ 1362 ] {e5ae49f7-9913-48ff-9821-2ecbbb4a9edd} <Debug> MemoryTracker: Peak memory usage (for query): 25.00 MiB.


[root@ck ~]# clickhouse-client -h 127.0.0.1 --query  "SELECT COUNT() FROM nullnull.rmt_user_distinct;"
10000000
```



### 14.2 通过Group By去重

```sql
# 执行去重语句
select 
	user_id,
	argMax(score,create_time) as score,
	argMax(deleted,create_time) as deleted,
	max(create_time) as ctime
from nullnull.rmt_user_distinct
group by user_id
having deleted=0;

# argMax(field1,field2) 按照field2的最大值取field1的值
# 当更新数据时，会写入一行新的数据，查询时间最大的create_time，得到修改后的scopre的字段值


# 数据修改操作，插入一条数据
insert into table nullnull.rmt_user_distinct(user_id,score,create_time)
values(0,'A4',now());

# 再次查询
select 
	user_id,
	argMax(score,create_time) as score,
	argMax(deleted,create_time) as deleted,
	max(create_time) as ctime
from nullnull.rmt_user_distinct
group by user_id
having deleted=0
and user_id=0;

# 结果
Query id: 7237e8d7-b6d4-4562-9402-3efb8ce2fe86

┌─user_id─┬─score─┬─deleted─┬───────────────ctime─┐
│       0 │ A4    │       0 │ 2024-11-15 12:38:36 │
└─────────┴───────┴─────────┴─────────────────────┘

1 rows in set. Elapsed: 0.002 sec. Processed 8.19 thousand rows, 196.63 KB (3.63 million rows/s., 87.16 MB/s.)
─────────┴───────┴─────────┴─────────────────────┘

# 数据删除操作，再添加一条数据
insert into table nullnull.rmt_user_distinct(user_id,score,deleted,create_time)
values(0,'delete',1,now());

# 再次查询,数据查询不到了
select 
	user_id,
	argMax(score,create_time) as score,
	argMax(deleted,create_time) as deleted,
	max(create_time) as ctime
from nullnull.rmt_user_distinct
group by user_id
having deleted=0
and user_id=0;

Ok.

0 rows in set. Elapsed: 0.003 sec. Processed 8.19 thousand rows, 196.66 KB (3.16 million rows/s., 75.89 MB/s.)


# 数据插入操作
insert into table nullnull.rmt_user_distinct(user_id,score,create_time)
values(0,'A5',now());

select 
	user_id,
	argMax(score,create_time) as score,
	argMax(deleted,create_time) as deleted,
	max(create_time) as ctime
from nullnull.rmt_user_distinct
group by user_id
having deleted=0
and user_id=0;

# 结果
Query id: e5ed1fda-a5db-43d0-9a96-a084cfd3cbf4

┌─user_id─┬─score─┬─deleted─┬───────────────ctime─┐
│       0 │ A5    │       0 │ 2024-11-15 12:42:46 │
└─────────┴───────┴─────────┴─────────────────────┘

1 rows in set. Elapsed: 0.005 sec. Processed 8.20 thousand rows, 196.68 KB (1.71 million rows/s., 41.03 MB/s.)


# 最后，查看下刚刚那些记录
select 
	*
from nullnull.rmt_user_distinct
where user_id=0;

# 结果响应
Query id: e2dd9cbb-60d9-42ad-aed7-728d88fcd3ee

┌─user_id─┬─score─┬─deleted─┬─────────create_time─┐
│       0 │ AA    │       0 │ 2024-11-15 09:15:19 │
└─────────┴───────┴─────────┴─────────────────────┘
┌─user_id─┬─score─┬─deleted─┬─────────create_time─┐
│       0 │ A4    │       0 │ 2024-11-15 12:38:36 │
└─────────┴───────┴─────────┴─────────────────────┘
┌─user_id─┬─score──┬─deleted─┬─────────create_time─┐
│       0 │ delete │       1 │ 2024-11-15 12:40:38 │
└─────────┴────────┴─────────┴─────────────────────┘
┌─user_id─┬─score─┬─deleted─┬─────────create_time─┐
│       0 │ A5    │       0 │ 2024-11-15 12:42:46 │
└─────────┴───────┴─────────┴─────────────────────┘

4 rows in set. Elapsed: 0.002 sec. Processed 8.20 thousand rows, 196.68 KB (3.54 million rows/s., 84.95 MB/s.)

```



### 14.3 通过FINAL查询

在查询语句后增加FINAL修饰符，这样在查询的过程中将会执行Merge的特殊逻辑。

但这种方法在早期版本基本没有人使用，因为在增加FINAL后，我们的查询将会变成一个单线程的执行过程。查询速度非常的慢。

在V20.5.2.7-stable版本中，FINAL查询支持多线程执行，并且可以通过Max_final_threads参数控制单个查询的线程数，21.7版本中读取part部分的动作依然是串行的。22版本中，已经是并发执行了。

```sql
# 执行查询
select * from datasets.visits_v1 WHERE StartDate = '2014-03-17' limit 100 settings max_threads = 2;

100 rows in set. Elapsed: 0.036 sec. Processed 13.43 thousand rows, 20.25 MB (375.24 thousand rows/s., 565.83 MB/s.)

# 查看执行计划，21.7.3.14版本中还存在部分串行
explain pipeline select * from datasets.visits_v1 WHERE StartDate = '2014-03-17' limit 100 settings max_threads = 2;

Query id: a336024f-6af2-474a-88d8-0c352c45adf9

┌─explain─────────────────────────┐
│ (Expression)                    │
│ ExpressionTransform × 2         │
│   (SettingQuotaAndLimits)       │
│     (Limit)                     │
│     Limit 2 → 2                 │
│       (ReadFromMergeTree)       │
│       MergeTreeThread × 2 0 → 1 │
└─────────────────────────────────┘

7 rows in set. Elapsed: 0.044 sec. 



# 在 22.12.6版本中，可以发现，此运行已经都可以多线程来运行。
┌─explain───────────────────────────────────────────────────────────────────────────────────────────────────────┐
│ (Expression)                                                                                                  │
│ ExpressionTransform × 2                                                                                       │
│   (Limit)                                                                                                     │
│   Limit 2 → 2                                                                                                 │
│     (Filter)                                                                                                  │
│     FilterTransform × 2                                                                                       │
│       (ReadFromMergeTree)                                                                                     │
│       ExpressionTransform × 2                                                                                 │
│         CollapsingSortedTransform 5 → 1                                                                       │
│           ExpressionTransform × 5                                                                             │
│             FilterSortedStreamByRange × 5                                                                     │
│             Description: filter values in [(11651763, 16150, 492737595, 6456139558230001359), +inf)           │
│               ExpressionTransform × 5                                                                         │
│                 MergeTreeInOrder × 5 0 → 1                                                                    │
│                   CollapsingSortedTransform 5 → 1                                                             │
│                     ExpressionTransform × 5                                                                   │
│                       FilterSortedStreamByRange × 5                                                           │
│                       Description: filter values in [-inf, (11651763, 16150, 492737595, 6456139558230001359)) │
│                         ExpressionTransform × 5                                                               │
│                           MergeTreeInOrder × 5 0 → 1                                                          │
└───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘

20 rows in set. Elapsed: 0.069 sec. 

```



## 15. 物化视图

官方地址：

```sh
https://clickhouse.com/docs/en/guides/developer/cascading-materialized-views
```



Clickhouse的物化视图是一种查询结果的持久化，它确实是给我们带来了查询效率的提升。用户查询查起来跟表没有区别，它就是一张表，它也像是一张时刻在计算的表。创建的过程它是用一种特殊的引擎，加上查询语句。

物化视图的基础表不会随着基础表的变化而变化，所以它也称为快照。

###  物化视图与普通视图的区别

普通视图不保存数据，保存的仅仅是查询的语句，查询的时候还是从原表中读取数据，可以将普通视图理解为是个子查询。物化视图则是把查询的结果根据相应的引擎存入到了磁盘或者内存中，对数据重新进行了组织，可以理解物化视图完全是一张新的表。

**优点**

查询速度快，要是把物化视图的这些规则全部写入，它比原来查询语句快很多，总的行数少了，因为都预计算好了。

**缺点**

经的本质是一个流式数据的使用场景，是累加式的技术，如果要用历史数据做去重，去核这样的分析，在物化视图里是不太好用的。在某些场景的使用也是有限的。而且如果一张表加了好多的物化视图，在写这张表的时候，就会消耗很多的机器资源，比如数据带宽占满，存储一下子多了很多。



### **物化视图的语法**

```sql
CREATE MATERIALIZED VIEW [IF NOT EXISTS] [db.]table_name [ON CLUSTER cluster_name] [TO[db.]name] [ENGINE = engine] [POPULATE]
[DEFINER = { user | CURRENT_USER }] [SQL SECURITY { DEFINER | INVOKER | NONE }]
AS SELECT ...
[COMMENT 'comment']

# 注意POPULATE关键字，如果加上，则会进行历史数据的导入，时间较长。不建议使用 POPULATE因为在视图创建期间插入表中的数据不会插入到表中。

```

### **创建物化视图的限制**

1. 必须指定物化视图的engine用于存储。
2.  [TO[db.]name] 不使用POPULATE
3. 物化视图的alter操作有些限制，操作起来不太方便。
4. 若视图定义使用了[TO[db.]name] 子语句，则可以将目标表的视图卸载 DETACH再装载ATTACH

**物化视图的数据更新**

1. 物化视图创建好之后，叵源表被写入新数据则物化视图也会同步更新
2. POPULATE关键字决定了物化视图的更新策略。
   1. 若有POPULATE则在创建视图的过程中会将源表已经存在的数据一并导入，类似于create table ...as
   2. 若无POPULATE则物化视图在创建之后没有数据，只会在创建只有同步之后写入源表的数据
   3. clickhouse官方并不推荐使用POPULATE，因为在创建物化视图的过程中同时写入的数据不能被插入物化视图。
3. 物化视图不支持同步删除，若源表的数据不存在，则物化视图的数据仍然保留



### 操作

```sh
# 创建库
CREATE DATABASE IF NOT EXISTS analytics;

# 创建存储的数据表
CREATE TABLE analytics.hits_data_mw
(
     EventDate Date, 
     CounterID UInt32, 
     UserID UInt64, 
     URL String, 
     Income UInt8
)
ENGINE = MergeTree()
PARTITION BY toYYYYMM(EventDate)
ORDER BY (CounterID, EventDate, intHash32(UserID))
SAMPLE BY intHash32(UserID)
SETTINGS index_granularity = 8192;


# 导入数据
INSERT INTO analytics.hits_data_mw 
 SELECT 
 EventDate, CounterID, UserID, URL, Income 
FROM datasets.hits_v1 limit 10000;



# 创建物化视图,并存储
CREATE MATERIALIZED VIEW analytics.hits_mv 
ENGINE=SummingMergeTree
PARTITION BY toYYYYMM(EventDate) 
ORDER BY (EventDate, intHash32(UserID)) 
AS SELECT
UserID,
EventDate,
count(URL) as ClickCount,
sum(Income) AS IncomeSum
FROM analytics.hits_data_mw
WHERE EventDate >= '2014-03-20' 
GROUP BY UserID,EventDate;

# 使用show table 查看表,可看到一张以.inner_id开始的表，此就是默认物化视图存储数据的表
┌─name───────────────────────────────────────────┐
│ .inner_id.0388a3c9-4290-4c2c-8388-a3c942904c2c │
│ hits_data_mw                                   │
│ hits_mv                                        │
└────────────────────────────────────────────────┘


#或者可以用下列语法，表 A 可以是一张 mergetree 表
CREATE MATERIALIZED VIEW 物化视图名 TO 表 A
AS SELECT FROM 表 B;


# 导入增加数据
INSERT INTO analytics.hits_data_mw 
SELECT 
 EventDate, CounterID, UserID, URL, Income 
FROM datasets.hits_v1 
WHERE EventDate >= '2014-03-23' 
limit 10;


#0 rows in set. Elapsed: 0.005 sec. Processed 8.19 thousand rows, 894.74 KB (1.58 million rows/s., 173.07 MB/s.)

#查询物化视图
SELECT * FROM analytics.hits_mv;
# 可看到结果

┌──────────────UserID─┬──EventDate─┬─ClickCount─┬─IncomeSum─┐
│ 8585742290196126178 │ 2014-03-23 │          8 │        16 │
│ 1095363898647626948 │ 2014-03-23 │          2 │         0 │
└─────────────────────┴────────────┴────────────┴───────────┘

2 rows in set. Elapsed: 0.002 sec. 

# 再导入数据，进行查看
INSERT INTO hits_mv
SELECT UserID, EventDate, count(URL) as ClickCount, sum(Income) AS IncomeSum
FROM analytics.hits_data_mw
WHERE EventDate = '2014-03-20'
GROUP BY UserID,EventDate;

# 0 rows in set. Elapsed: 0.005 sec. Processed 10.00 thousand rows, 1.02 MB (1.87 million rows/s., 191.35 MB/s.)

#查询物化视图
SELECT * FROM analytics.hits_mv;
# 可看到结果
┌──────────────UserID─┬──EventDate─┬─ClickCount─┬─IncomeSum─┐
│ 8585742290196126178 │ 2014-03-23 │          8 │        16 │
│ 1095363898647626948 │ 2014-03-23 │          2 │         0 │
└─────────────────────┴────────────┴────────────┴───────────┘
┌───────────────UserID─┬──EventDate─┬─ClickCount─┬─IncomeSum─┐
│  8682581061680449960 │ 2014-03-20 │         36 │         0 │
......
│  1913746513358768143 │ 2014-03-20 │          2 │         4 │
└──────────────────────┴────────────┴────────────┴───────────┘

341 rows in set. Elapsed: 0.003 sec. 
```

### 官方推荐

```sh
# 官方推荐使用空表，可以在 Null 表上创建物化视图。因此，写入表的数据最终会影响视图，但原始数据仍将被丢弃。
drop table analytics.hits_data_mw_null;

CREATE TABLE analytics.hits_data_mw_null
(
 EventDate Date, 
 CounterID UInt32, 
 UserID UInt64, 
 Income UInt8
)
ENGINE = Null;


# 创建聚合引擎，用于汇聚数据指标
drop table analytics.monthly_aggregated_data_agg;

CREATE TABLE analytics.monthly_aggregated_data_agg
(
     EventDate Date, 
     CounterID UInt32, 
     UserID UInt64, 
     Income UInt64,
    `icomeSum` AggregateFunction(sum, UInt8),
    `countNum` AggregateFunction(count, UInt64)
)
ENGINE = AggregatingMergeTree
PARTITION BY toYYYYMM(EventDate) 
ORDER BY (EventDate,UserID);


# 创建物化视图，并将数据存储到聚合引擎中
drop table analytics.monthly_aggregated_data_agg_mv;
CREATE MATERIALIZED VIEW analytics.monthly_aggregated_data_agg_mv
to analytics.monthly_aggregated_data_agg
AS SELECT
EventDate,
CounterID,
UserID,
Income,
sumState(Income) AS  icomeSum,
countState(UserID) as countNum
FROM analytics.hits_data_mw_null
GROUP BY EventDate,CounterID,UserID,Income;

# 向空表中导入数据
INSERT INTO analytics.hits_data_mw_null 
SELECT EventDate, CounterID, UserID, Income 
 FROM datasets.hits_v1 
WHERE EventDate >= '2014-03-23' 
limit 20;

#0 rows in set. Elapsed: 0.006 sec. Processed 8.19 thousand rows, 894.74 KB (1.34 million rows/s., 146.29 MB/s.)

# 查询数据聚合引擎中的的存储信息
select
 EventDate, CounterID, UserID, Income,
 sumMerge(icomeSum) as icomeSum,
 countMerge(countNum) as countNum
from analytics.monthly_aggregated_data_agg 
group by EventDate,CounterID,UserID,Income ;

# 查询结果：
┌──EventDate─┬─CounterID─┬──────────────UserID─┬─Income─┬─icomeSum─┬─countNum─┐
│ 2014-03-23 │        57 │ 1095363898647626948 │      0 │        0 │        2 │
│ 2014-03-23 │        57 │ 4210364363248184944 │      2 │       20 │       10 │
│ 2014-03-23 │        57 │ 8585742290196126178 │      2 │       16 │        8 │
└────────────┴───────────┴─────────────────────┴────────┴──────────┴──────────┘

3 rows in set. Elapsed: 0.003 sec. 
```



## 16 MetarializeMySQL引擎

注：此引擎Clickhouse官方已经不推荐使用。

### 介绍

MySQL的用户集体很大，为了能够增强数据的实时性，很多解决方案会利用BinLog将数据写入到ClickHouse，为了能够监听BinLog事件，我们需要用到类似cancal这样的第三方组件，这无疑增加了系统的复杂度。

ClickHouse20.8.2.3 版本新增加了 MaterializeMySQL的database引擎，该database能映射到MySQL中的某个database，并自动在ClickHouse中创建对应的ReplcingMergeTree。ClickHouse服务做为MySQL副本，读取BinLog并执行DDL和DML请求，实现了基本于MySQL的BinLog机制的业务数据库实时同步功能。

（1） MaterializeMySQL同时支持全量和增量同步，在database创建之初会全量同步MySQL中的表和数据，之后会通过BinLog进行增量同步。

（2）MaterializeMySQL database为其创建的每张ReplacingMergeTree 自动增加了`_sign`和`_version`字段

其中，`_version`用作ReplacingMergeTree的ver版本参数，每当监听到insert、update、和delete事件时，在database内全局自增。而`_sign`则用于标识是否被删除，取值1或者-1

目前MaterializeMySQL支持如下几种binlog事件：

- MYSQL_WRITE_ROWS_EVENT:  `_sign`=1,`_version`++
- MYSQL_DELETE_ROWS_EVENT: `_sign`=-1,`_version`++
- MYSQL_UPDATE_ROWS_EVENT: 新数据 `_sign`=1
- MYSQL_QUERY_EVENT: 支持CREATE TABLE、DROP TABLE、RENAME TABLE等。

### **细则**

DDL查询

MySQL DDL查询被转换成相应的Clickhouse DDL查询（ALTER,CREATE,DROP，RENAME）。

如果Clickhouse不能解析某些DDL查询，该查询将被忽略。

**数据复制**

MaterializeMySQL不支持直接插入、删除和更新查询，而是将DDL语句进行相应的转换。

1) MySQL INSERT 查询被转换为 INSERT with _sign=1
2) MySQL DELETE 查询被转换为 INSERT with _sign=-1
3) MySQL UPDATE 查询被转换成 INSERT with _sign=1 和 insert with _sign=1

如果在SELECT查询中没有指定_version,则使用FINAL修饰，返回`_version`的最大值对应的数据，即最新版本的数据。

如果在SELECT查询中没有指定`_sign`，则默认使用`where _sign=1`，返回未删除状态(`_sign`)的数据。

ClickHouse数据库表会自动将MySQL主键和索引子句转换为Order By元组。

ClickHouse只有一个物理顺序，由Order By子句决定，如果需要创建新的物理顺序，可以使用物化视图。



### 案例

**1. mysql配制**

```sh
# 使用docker安装
docker run --name mysql-5.7 -p 3306:3306 \
-v /opt/nullnull/mysql/conf:/etc/mysql/conf.d \
-v /opt/nullnull/mysql/logs:/logs \
-v /opt/nullnull/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=nullnull \
-d mysql:5.7.27  \
--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci


firewall-cmd --permanent --zone=public --add-port=3306/tcp
firewall-cmd --reload

# 1. MySQL开启BinLog功能，且格式为ROW
# CK就20.8 prestable之后发布的版本，那么MySQL还需要配制开启GTID模式，这种方式在mysql主从模式下可以确保数据同步的一致性
# GTID是MySQL复制的增强版，从MySQL5.6版本开始支持，目前已经是MySQL主流的复制模式，它分配一个全局唯一ID和序列号，我们可以不用关心MySQL集群评价拓扑结构，直接告知MySQL这个GTID即可。
vi /opt/nullnull/mysql/conf/my.cnf
[mysqld]
server-id=1 
log-bin=mysql-bin
binlog_format=ROW

gtid-mode=on
enforce-gtid-consistency=1
log-slave-updates=1

# 重启MySQL
docker restart mysql-5.7

```

**2. MySQL库表创建**

```sql
CREATE DATABASE nullnull_ck;
-- 组织表
CREATE TABLE `nullnull_ck`.`t_organization` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `code` int NOT NULL,
 `name` text DEFAULT NULL,
 `updatetime` datetime DEFAULT NULL,
 PRIMARY KEY (`id`),
 UNIQUE KEY (`code`)
) ENGINE=InnoDB;

-- 插入数据
INSERT INTO nullnull_ck.t_organization (code, name,updatetime) 
VALUES(1000,'总经理',NOW());
INSERT INTO nullnull_ck.t_organization (code, name,updatetime) 
VALUES(1001, '财务部',NOW());
INSERT INTO nullnull_ck.t_organization (code, name,updatetime) 
VALUES(1002,'人事部',NOW());

# 用户表
CREATE TABLE `nullnull_ck`.`t_user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `code` int,
 `name` varchar(64) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB;


INSERT INTO nullnull_ck.t_user (code,name) VALUES(1,'nullnull');

```

**3. CK开启MySQL物化引擎**

```sh
clickhouse-client 

set allow_experimental_database_materialize_mysql=1;

```



**4. 创建复制管道**

```sh
CREATE DATABASE nullnull_binlog ENGINE=MaterializeMySQL('192.168.5.22:3306','nullnull_ck','root','nullnull');
```

**5. 查看CK中表的数据**

```sql
use nullnull_binlog;

show tables;
┌─name───────────┐
│ t_organization │
│ t_user         │
└────────────────┘

select * from t_organization;
┌─id─┬─code─┬─name───┬──────────updatetime─┐
│  1 │ 1000 │ 总经理 │ 2024-11-18 15:32:39 │
│  2 │ 1001 │ 财务部 │ 2024-11-18 15:32:39 │
│  3 │ 1002 │ 人事部 │ 2024-11-18 15:32:39 │
└────┴──────┴────────┴─────────────────────┘
3 rows in set. Elapsed: 0.003 sec. 

select * from t_user;
┌─id─┬─code─┬─name─────┐
│  1 │    1 │ nullnull │
└────┴──────┴──────────┘
1 rows in set. Elapsed: 0.002 sec. 

```

**6. 修改MySQL中表的数据**

```sql
update t_organization set name = '总经理-plus' where id = 1
```

**7. 检查CK日志 **

/var/log/clickhouse-server/clickhouse-server.log 

```sh
2024.11.18 23:42:52.518967 [ 1943 ] {} <Debug> MaterializeMySQLSyncThread: Skip MySQL event: 
=== GTIDEvent ===
Timestamp: 1731944572
Event Type: GTIDEvent
Server ID: 1
Event Size: 65
Log Pos: 3345
Flags: 0
GTID Next: 5e7cda91-a5c0-11ef-8556-0242ac110002:11

2024.11.18 23:42:52.519004 [ 1943 ] {} <Debug> MaterializeMySQLSyncThread: Skip MySQL event: 
=== QueryEvent ===
Timestamp: 1731944572
Event Type: QueryEvent
Server ID: 1
Event Size: 79
Log Pos: 3424
Flags: 8
[DryRun Event]

2024.11.18 23:42:52.519017 [ 1943 ] {} <Debug> MaterializeMySQLSyncThread: Skip MySQL event: 
=== TableMapEvent ===
Timestamp: 1731944572
Event Type: TableMapEvent
Server ID: 1
Event Size: 69
Log Pos: 3493
Flags: 0
Table ID: 112
Flags: 1
Schema Len: 11
Schema: nullnull_ck
Table Len: 14
Table: t_organization
Column Count: 4
Column Type [0]: 3, Meta: 0
Column Type [1]: 3, Meta: 0
Column Type [2]: 252, Meta: 2
Column Type [3]: 18, Meta: 0
Null Bitmap: 00001100

2024.11.18 23:42:52.519096 [ 1943 ] {} <Debug> MaterializeMySQLSyncThread: Skip MySQL event: 
=== XIDEvent ===
Timestamp: 1731944572
Event Type: XIDEvent
Server ID: 1
Event Size: 31
Log Pos: 3615
Flags: 0
XID: 166

2024.11.18 23:42:52.998345 [ 1943 ] {} <Debug> MemoryTracker: Peak memory usage (for query): 0.00 B.
2024.11.18 23:42:52.998564 [ 1943 ] {} <Debug> executeQuery: (internal) /*Materialize MySQL step 1: execute dump data*/ INSERT INTO t_organization(id, code, name, updatetime, _sign, _version) VALUES
2024.11.18 23:42:52.999196 [ 1943 ] {} <Debug> DiskLocal: Reserving 1.00 MiB on disk `default`, having unreserved 37.66 GiB.
2024.11.18 23:42:52.999750 [ 1943 ] {} <Trace> nullnull_binlog.t_organization (caa8fba8-98f6-43e9-8aa8-fba898f6b3e9): Renaming temporary part tmp_insert_0_4_4_0 to 0_4_4_0.
2024.11.18 23:42:52.999914 [ 1943 ] {} <Information> MaterializeMySQLSyncThread: MySQL executed position: 
 
=== Binlog Position ===
Binlog: mysql-bin.000001
Position: 3615
GTIDSets: 5e7cda91-a5c0-11ef-8556-0242ac110002:1-11
```

**8. 查看CK同步的最新的数据**

```sql
select * from t_organization;

┌─id─┬─code─┬─name────────┬──────────updatetime─┐
│  1 │ 1000 │ 总经理-plus │ 2024-11-18 15:32:39 │
│  2 │ 1001 │ 财务部      │ 2024-11-18 15:32:39 │
│  3 │ 1002 │ 人事部      │ 2024-11-18 15:32:39 │
└────┴──────┴─────────────┴─────────────────────┘

3 rows in set. Elapsed: 0.003 sec. 

```

**9. 删除MySQL中的记录**·

```sql
DELETE FROM t_organization where id = 2;
```

**10.查看CK同步的数据**

```sh
# 检查查看被删除的账务部信息
select * from t_organization;

┌─id─┬─code─┬─name────────┬──────────updatetime─┐
│  1 │ 1000 │ 总经理-plus │ 2024-11-18 15:32:39 │
│  3 │ 1002 │ 人事部      │ 2024-11-18 15:32:39 │
└────┴──────┴─────────────┴─────────────────────┘

2 rows in set. Elapsed: 0.002 sec. 

# 查看_sign和_version字段信息

select *,_sign,_version from t_organization order by _sign desc,_version desc;

┌─id─┬─code─┬─name────────┬──────────updatetime─┬─_sign─┬─_version─┐
│  1 │ 1000 │ 总经理-plus │ 2024-11-18 15:32:39 │     1 │        4 │
│  3 │ 1002 │ 人事部      │ 2024-11-18 15:32:39 │     1 │        1 │
│  2 │ 1001 │ 财务部      │ 2024-11-18 15:32:39 │    -1 │        5 │
└────┴──────┴─────────────┴─────────────────────┴───────┴──────────┘

3 rows in set. Elapsed: 0.003 sec. 
# 查询时，对于已经被删除的数据,_sign=-1,CK会自动重写SQL，将_sign=-1的数据过滤掉。
```

**11.删除user表**

```sql
drop table t_user;
```

**12. 至CK中查看**

```sql
show tables;
┌─name───────────┐
│ t_organization │
└────────────────┘

1 rows in set. Elapsed: 0.002 sec. 


select * from t_user;
Code: 60. DB::Exception: Received from localhost:9000. DB::Exception: Table nullnull_binlog.t_user doesn't exist. 

```

mysql再次建表

```sql
-- mysql再次新建表
CREATE TABLE `nullnull_ck`.`t_user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `code` int,
 `name` varchar(64) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB;


INSERT INTO nullnull_ck.t_user (code,name) VALUES(1,'nullnull');
```

查询数据

```sql
-- 查询数据
show tables;
┌─name───────────┐
│ t_organization │
│ t_user         │
└────────────────┘
2 rows in set. Elapsed: 0.002 sec. 

select * from t_user;
┌─id─┬─code─┬─name─────┐
│  1 │    1 │ nullnull │
└────┴──────┴──────────┘
1 rows in set. Elapsed: 0.003 sec. 
```







## 17. MaterializedPostgreSQL引擎

官网地址：

```sh
https://clickhouse.com/docs/en/engines/database-engines/materialized-postgresql
```



使用 PostgreSQL 数据库中的表创建 ClickHouse 数据库。首先，带有引擎 `MaterializedPostgreSQL` 的数据库创建 PostgreSQL 数据库的快照并加载所需的表。所需的表可以包括来自指定数据库的任何架构子集的任何表子集。随着快照数据库引擎获取 LSN，并且一旦执行表的初始转储，它就会开始从 WAL 中提取更新。创建数据库后，新添加到 PostgreSQL 数据库的表不会自动添加到复制中。必须使用 `ATTACH TABLE db.table` 查询手动添加它们。

ClickHouse服务器作为PostgreSQL副本工作。它读取WAL并执行DML查询。DDL不是复制的，但可以处理

此引擎在 version 21.12之后加入。

**1. 创建一个PostgreSQL数据库**

```sh
docker pull postgres:11.5


docker run -d --name pgsql-11 \
 --network host \
 --restart always \
 -e SSH_PORT=10022 \
 -e POSTGRES_PASSWORD=nullnull \
 -v /opt/nullnull/pgsql/data/:/var/lib/postgresql/data:z \
postgres:11.5

firewall-cmd --permanent --zone=public --add-port=5432/tcp
firewall-cmd --reload

# 对默认的PG配制进行修改
vi /opt/nullnull/pgsql/data/postgresql.conf
# 将wal_level的值改为
# minimal, replica, or logical
# (change requires restart)
wal_level = logical
```

**2. 创建一个PostgreSQL数据库及表**

```sql
create database nullnull_pg_ck;

-- 组织表
CREATE TABLE nullnull_pg_ck.public.t_organization (
 id Integer NOT NULL,
 code Integer NOT NULL,
 name varchar DEFAULT NULL,
 updatetime TIMESTAMP(6) DEFAULT NULL
);
-- 唯一约束
CREATE unique INDEX nullnull_pg_ck.public.t_org_unique_index on t_organization(id, code);
-- 当 postgres_table 表中的数据发生变化时，PostgreSQL 会查看 postgres_table_index 索引中包含的列，并将这些列的变化记录在 WAL 日志中，以便逻辑复制过程可以使用这些信息
ALTER TABLE nullnull_pg_ck.public.t_organization REPLICA IDENTITY USING INDEX t_org_unique_index;


-- 插入数据
INSERT INTO nullnull_pg_ck.public.t_organization (id,code, name,updatetime) 
VALUES(1,1000,'总经理',NOW());
INSERT INTO nullnull_pg_ck.public.t_organization (id,code, name,updatetime) 
VALUES(2,1001, '财务部',NOW());
INSERT INTO nullnull_pg_ck.public.t_organization (id,code, name,updatetime) 
VALUES(3,1002,'人事部',NOW());

-- 用户表
drop table nullnull_pg_ck.public.t_user;

CREATE TABLE nullnull_pg_ck.public.t_user (
 id int NOT NULL,
 code int  NOT NULL,
 name varchar(64) DEFAULT NULL
);

-- 唯一约束
CREATE unique INDEX t_user_unidex on t_user(id, code);
-- 当 postgres_table 表中的数据发生变化时，PostgreSQL 会查看 postgres_table_index 索引中包含的列，并将这些列的变化记录在 WAL 日志中，以便逻辑复制过程可以使用这些信息
ALTER TABLE t_user REPLICA IDENTITY USING INDEX t_user_unidex;


INSERT INTO nullnull_pg_ck.public.t_user (id,code,name) VALUES(1,1,'nullnull');
INSERT INTO nullnull_pg_ck.public.t_user (id,code,name) VALUES(2,2,'feifei');

```



**3. 开启引擎配制**

```sh
# 此处也使用docker安装24版本
# 此处使用代理下载。
docker pull dockerproxy.net/clickhouse/clickhouse-server:22.8.21.38-alpine


docker stop clickhouse-22 && docker rm clickhouse-22


# 注意此版本启动，需要手动在 /opt/nullnull/clickhouse/data/config/目录下放入ck的配制文件
docker run -d --restart=always \
--name clickhouse-22 \
-p 8123:8123 \
-p 9000:9000 \
--ulimit nofile=262144:262144 \
-v /opt/nullnull/clickhouse/data/config/:/etc/clickhouse-server:z \
-v /opt/nullnull/clickhouse/data/data/:/var/lib/clickhouse:z \
-v /opt/nullnull/clickhouse/data/log/:/var/log/clickhouse-server:z \
clickhouse/clickhouse-server:22.8.21.38-alpine



 clickhouse-client

# 开启参数
SET allow_experimental_database_materialized_postgresql=1

# 有些输出证明设置成功
SET allow_experimental_database_materialized_postgresql = 1
Query id: 5dcefde0-e212-45bf-830f-d0170790ef24
Ok.
0 rows in set. Elapsed: 0.001 sec. 

# 可通过系统表查看参数
SELECT getSetting('allow_experimental_database_materialized_postgresql');

┌─getSetting('allow_experimental_database_materialized_postgresql')─┐
│ true                                                              │
└───────────────────────────────────────────────────────────────────┘

1 row in set. Elapsed: 0.001 sec. 

# 系统参数表查看
SELECT value FROM system.settings where name='allow_experimental_database_materialized_postgresql';

┌─value─┐
│ 1     │
└───────┘

1 row in set. Elapsed: 0.001 sec.

```



**4. 在CK创建PG的同步表，并查看数据**

```sql
drop database IF  EXISTS nullnull_mpg ;

CREATE DATABASE IF NOT EXISTS nullnull_mpg 
ENGINE = MaterializedPostgreSQL('192.168.5.22:5432', 'nullnull_pg_ck', 'postgres', 'nullnull') 


Ok.
0 rows in set. Elapsed: 0.383 sec. 

# 查看CK日志
2024.11.19 05:43:52.816077 [ 171 ] {} <Debug> PostgreSQLConnection: New connection to 192.168.5.22:5432
2024.11.19 05:43:52.816265 [ 171 ] {} <Debug> PostgreSQLReplicationHandler: Loading PostgreSQL table nullnull_pg_ck."t_user"
2024.11.19 05:43:52.818443 [ 171 ] {} <Debug> StorageMaterializedPostgreSQL(nullnull_pg_ck.t_user): Creating clickhouse table for postgresql table nullnull_mpg.t_user
2024.11.19 05:43:52.819885 [ 171 ] {} <Debug> nullnull_mpg.t_user (de0b0597-d603-45d0-a5cd-eb8f174ac37f): Loading data parts
2024.11.19 05:43:52.820019 [ 171 ] {} <Debug> nullnull_mpg.t_user (de0b0597-d603-45d0-a5cd-eb8f174ac37f): There are no data parts
2024.11.19 05:43:52.821493 [ 171 ] {} <Debug> DiskLocal: Reserving 1.00 MiB on disk `default`, having unreserved 51.57 GiB.
2024.11.19 05:43:52.821688 [ 171 ] {} <Trace> MergedBlockOutputStream: filled checksums all_1_1_0 (state Temporary)
2024.11.19 05:43:52.821823 [ 171 ] {} <Trace> nullnull_mpg.t_user (de0b0597-d603-45d0-a5cd-eb8f174ac37f): Renaming temporary part tmp_insert_all_1_1_0 to all_1_1_0.
2024.11.19 05:43:52.821966 [ 171 ] {} <Debug> PostgreSQLReplicationHandler: Loaded table nullnull_mpg.t_user (uuid: de0b0597-d603-45d0-a5cd-eb8f174ac37f)
2024.11.19 05:43:52.822185 [ 171 ] {} <Debug> PostgreSQLReplicationHandler: Loading PostgreSQL table nullnull_pg_ck."t_organization"
2024.11.19 05:43:52.823085 [ 171 ] {} <Debug> StorageMaterializedPostgreSQL(nullnull_pg_ck.t_organization): Creating clickhouse table for postgresql table nullnull_mpg.t_organization
2024.11.19 05:43:53.028603 [ 171 ] {} <Debug> nullnull_mpg.t_organization (c5a4a6f1-002a-48b2-9a48-c843f5d403b6): Loading data parts
2024.11.19 05:43:53.028708 [ 171 ] {} <Debug> nullnull_mpg.t_organization (c5a4a6f1-002a-48b2-9a48-c843f5d403b6): There are no data parts
2024.11.19 05:43:53.030734 [ 171 ] {} <Debug> DiskLocal: Reserving 1.00 MiB on disk `default`, having unreserved 51.57 GiB.
2024.11.19 05:43:53.031003 [ 171 ] {} <Trace> MergedBlockOutputStream: filled checksums all_1_1_0 (state Temporary)
2024.11.19 05:43:53.031165 [ 171 ] {} <Trace> nullnull_mpg.t_organization (c5a4a6f1-002a-48b2-9a48-c843f5d403b6): Renaming temporary part tmp_insert_all_1_1_0 to all_1_1_0.
2024.11.19 05:43:53.031280 [ 171 ] {} <Debug> PostgreSQLReplicationHandler: Loaded table nullnull_mpg.t_organization (uuid: c5a4a6f1-002a-48b2-9a48-c843f5d403b6)
2024.11.19 05:43:53.031937 [ 171 ] {} <Trace> PostgreSQLReplicaConsumer(nullnull_pg_ck): Advanced LSN up to: 23834144
2024.11.19 05:43:53.031955 [ 171 ] {} <Trace> PostgreSQLReplicaConsumer(nullnull_pg_ck): Starting replication. LSN: 23834144 (last: 23834144)
2024.11.19 05:43:53.031987 [ 171 ] {} <Trace> StorageMaterializedPostgreSQL: New buffer for table nullnull_mpg.t_user (de0b0597-d603-45d0-a5cd-eb8f174ac37f), number of attributes: 3, number if columns: 5, structure: id Int32 Int32(size = 1), code Int32 Int32(size = 1), name Nullable(String) Nullable(size = 1, String(size = 1), UInt8(size = 1)), _sign Int8 Int8(size = 1), _version UInt64 UInt64(size = 1)
2024.11.19 05:43:53.032051 [ 171 ] {} <Trace> StorageMaterializedPostgreSQL: New buffer for table nullnull_mpg.t_organization (c5a4a6f1-002a-48b2-9a48-c843f5d403b6), number of attributes: 4, number if columns: 6, structure: id Int32 Int32(size = 1), code Int32 Int32(size = 1), name Nullable(String) Nullable(size = 1, String(size = 1), UInt8(size = 1)), updatetime Nullable(DateTime64(6)) Nullable(size = 1, DateTime64(size = 1), UInt8(size = 1)), _sign Int8 Int8(size = 1), _version UInt64 UInt64(size = 1)
2024.11.19 05:43:53.032136 [ 171 ] {} <Trace> MaterializedPostgreSQLDatabaseStartup: Execution took 1200 ms.

# 查年表
use nullnull_mpg;
show tables;

┌─name───────────┐
│ t_organization │
│ t_user         │
└────────────────┘
2 rows in set. Elapsed: 0.001 sec. 


# 查看组织信息表
select * from t_organization;
┌─id─┬─code─┬─name───┬─────────────────updatetime─┐
│  1 │ 1000 │ 总经理 │ 2024-11-19 09:35:00.783856 │
│  2 │ 1001 │ 财务部 │ 2024-11-19 09:35:15.940007 │
│  3 │ 1002 │ 人事部 │ 2024-11-19 09:35:15.942534 │
└────┴──────┴────────┴────────────────────────────┘
3 rows in set. Elapsed: 0.002 sec. 

# 查看用户信息表
select * from t_user;
┌─id─┬─code─┬─name─────┐
│  1 │    1 │ nullnull │
│  2 │    2 │ feifei   │
└────┴──────┴──────────┘
2 rows in set. Elapsed: 0.002 sec. 
```

**5. 在CK创建PG的同步表，并查看数据**

```sql
update t_organization set name = '总经理-新' where id = 1;
update t_user set name = 'nullnull-新' where id = 1;
```

**6. CK查看最新的数据**

由于此是自动复制所以，立即查看CK中的PG表，数据已经同步成最新的。

```sql
# 查看组织信息表
select * from t_organization;
┌─id─┬─code─┬─name───┬─────────────────updatetime─┐
│  2 │ 1001 │ 财务部 │ 2024-11-19 09:35:15.940007 │
│  3 │ 1002 │ 人事部 │ 2024-11-19 09:35:15.942534 │
└────┴──────┴────────┴────────────────────────────┘
┌─id─┬─code─┬─name──────┬─────────────────updatetime─┐
│  1 │ 1000 │ 总经理-新 │ 2024-11-19 09:35:00.783856 │
└────┴──────┴───────────┴────────────────────────────┘
↑ Progress: 0.00 rows, 0.00 B (0.00 rows/s., 0.00 B/s.)  

# 查看用户信息表
select * from t_user;
┌─id─┬─code─┬─name────────┐
│  1 │    1 │ nullnull-新 │
└────┴──────┴─────────────┘
┌─id─┬─code─┬─name───┐
│  2 │    2 │ feifei │
└────┴──────┴────────┘
2 rows in set. Elapsed: 0.003 sec. 


# 最新的组织结构表信息
select *,_sign,_version from t_organization order by _sign desc,_version desc;

┌─id─┬─code─┬─name──────┬─────────────────updatetime─┬─_sign─┬─_version─┐
│  1 │ 1000 │ 总经理-新 │ 2024-11-19 09:35:00.783856 │     1 │ 23834424 │
└────┴──────┴───────────┴────────────────────────────┴───────┴──────────┘
┌─id─┬─code─┬─name───┬─────────────────updatetime─┬─_sign─┬─_version─┐
│  1 │ 1000 │ 总经理 │ 2024-11-19 09:35:00.783856 │     1 │        1 │
│  2 │ 1001 │ 财务部 │ 2024-11-19 09:35:15.940007 │     1 │        1 │
│  3 │ 1002 │ 人事部 │ 2024-11-19 09:35:15.942534 │     1 │        1 │
└────┴──────┴────────┴────────────────────────────┴───────┴──────────┘
4 rows in set. Elapsed: 0.006 sec. 

#最新的用户表信息
select *,_sign,_version from t_user order by _sign desc,_version desc;

┌─id─┬─code─┬─name────────┬─_sign─┬─_version─┐
│  1 │    1 │ nullnull-新 │     1 │ 23834912 │
└────┴──────┴─────────────┴───────┴──────────┘
┌─id─┬─code─┬─name─────┬─_sign─┬─_version─┐
│  1 │    1 │ nullnull │     1 │        1 │
│  2 │    2 │ feifei   │     1 │        1 │
└────┴──────┴──────────┴───────┴──────────┘
3 rows in set. Elapsed: 0.003 sec. 
```





## 18 插入调优

```sh
# 插入控制参数
SELECT getSetting('min_insert_block_size_rows');
┌─getSetting('min_insert_block_size_rows')─┐
│                                  1048449 │ -- 1.05 million
└──────────────────────────────────────────┘

1 row in set. Elapsed: 0.003 sec. 

default: 1048545 million rows



SELECT getSetting('min_insert_block_size_bytes');
┌─getSetting('min_insert_block_size_bytes')─┐
│                                 268402944 │ -- 268.40 million
└───────────────────────────────────────────┘
set min_insert_block_size_bytes=458333333

1 row in set. Elapsed: 0.003 sec. 

default: 256 MB


SELECT getSetting('max_insert_threads');
┌─getSetting('max_insert_threads')─┐
│                                0 │
└──────────────────────────────────┘
1 row in set. Elapsed: 0.003 sec. 

SELECT getSetting('max_insert_block_size');

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



```xml
<macros>
	<shard>01</shard>
	<replica>rep_1_1</replica>
</macros>
```





## 结束

wps
