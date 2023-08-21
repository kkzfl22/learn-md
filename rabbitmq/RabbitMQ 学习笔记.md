RabbitMQ 学习笔记

## 1. RabbitMQ 安装-linux

| 软件     | 版本                          |
| -------- | ----------------------------- |
| 操作系统 | CentOS Linux release 7.9.2009 |
| erlang   | erlang-23.0.2-1.el7.x86_64    |
| rabbitMQ | rabbitmq-server-3.8.5-1.el7   |

RabbitMQ的安装首先需要安装Erlang,因为它是基于Erlang的VM运行的。

RabbitMQ安装需要依赖:socat和logrotate，logrotate操作系统已经存在了，只需要安装socat就可以了。

RabbitMQ与Erlang的兼容关系详见:

[地址]([RabbitMQ Erlang Version Requirements — RabbitMQ](https://www.rabbitmq.com/which-erlang.html))

表格如下：

| [RabbitMQ version](https://www.rabbitmq.com/versions.html)   | Minimum required Erlang/OTP | Maximum supported Erlang/OTP | Notes                                                        |
| :----------------------------------------------------------- | :-------------------------- | :--------------------------- | :----------------------------------------------------------- |
| 3.12.2<br/>3.12.1<br/>3.12.0                                 | 25.0                        | 26.0                         | The 3.12 release series is compatible wtih Erlang 26.OpenSSL 3 support in Erlang is considered to be mature enough for production.Erlang 26.1 will [support FIPS mode on OpenSSL 3](https://github.com/erlang/otp/pull/7392) |
| 3.11.20<br/>3.11.19<br/>3.11.18<br/>3.11.17<br/>3.11.16<br/>3.11.15<br/>3.11.14<br/>3.11.13<br/>3.11.12<br/>3.11.11<br/>3.11.10<br/>3.11.9<br/>3.11.8<br/>3.11.7<br/>3.11.6<br/>3.11.5<br/>3.11.4<br/>3.11.3<br/>3.11.2<br/>3.11.1<br/>3.11.0 | 25.0                        | 25.3.x                       | Erlang 26 is supported starting with Erlang 3.12.0.As of Erlang 25.1, OpenSSL 3.0 support in Erlang is considered to be mature enough for production.Erlang 25 before 25.0.2 is affected by [CVE-2022-37026](https://nvd.nist.gov/vuln/detail/CVE-2022-37026), a CVE with critical severity (CVSS 3.x Base Score: 9.8) |
| 3.10.25<br/>3.10.24<br/>3.10.23<br/>3.10.22<br/>3.10.21<br/>3.10.20<br/>3.10.19 | 24.3.4.8                    | 25.3.x                       | 24.3 is the only maintained (updated) series of Erlang 24.As of Erlang 25.1, OpenSSL 3.0 support in Erlang is considered to be mature enough to consider for production.Erlang 25 before 25.0.2 and 24 before 24.3.4.2 are affected by [CVE-2022-37026](https://nvd.nist.gov/vuln/detail/CVE-2022-37026), a CVE with critical severity (CVSS 3.x Base Score: 9.8) |
| 3.10.18<br/>3.10.17<br/>3.10.16<br/>3.10.14                  | 24.3                        | 25.2                         | 24.3 is the only maintained (updated) series of Erlang 24.As of Erlang 25.1, OpenSSL 3.0 support in Erlang is considered to be mature enough to consider for production.Erlang 25 before 25.0.2 and 24 before 24.3.4.2 are affected by [CVE-2022-37026](https://nvd.nist.gov/vuln/detail/CVE-2022-37026), a CVE with critical severity (CVSS 3.x Base Score: 9.8) |
| 3.10.13<br/>3.10.12<br/>3.10.11<br/>3.10.10<br/>3.10.9<br/>3.10.8 | 24.2                        | 25.2                         | As of Erlang 25.1, OpenSSL 3.0 support in Erlang is considered to be mature enough to consider for production.Erlang 25 before 25.0.2 and 24 before 24.3.4.2 are affected by [CVE-2022-37026](https://nvd.nist.gov/vuln/detail/CVE-2022-37026), a CVE with critical severity (CVSS 3.x Base Score: 9.8) |
| 3.10.7<br/>3.10.6<br/>3.10.5                                 | 23.2                        | 25.2                         | Erlang 25 is the recommended series.Erlang 25 before 25.0.2 and 24 before 24.3.4.2 are affected by [CVE-2022-37026](https://nvd.nist.gov/vuln/detail/CVE-2022-37026), a CVE with critical severity (CVSS 3.x Base Score: 9.8)Erlang 23 support was discontinued on July 31st, 2022. |
| 3.10.4<br/>3.10.2<br/>3.10.1<br/>3.10.0                      | 23.2                        | 24.3                         | Erlang 24.3 is the recommended series.Erlang 23 support was discontinued on July 31st, 2022. |



1. **安装依赖socat**

```bash
yum install socat -y
```

2. **安装Erlang**

Erlang的下载地址：

https://github.com/rabbitmq/erlang-rpm/releases/download/v23.0.2/erlang-23.0.2-1.el7.x86_64.rpm

将文件上传服务器，执行安装命令

```sh
rpm -ivh erlang-23.0.2-1.el7.x86_64.rpm 
```

3. **安装RabbitMQ**

rabbitMQ的下载地址 ：

https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.5/rabbitmq-server-3. 8.5-1.el7.noarch.rpm

下载后将文件上传服务器，执行安装命令

```
rpm -ivh rabbitmq-server-3.8.5-1.el7.noarch.rpm
```

4. **启动RabbitMQ的管理插件**

   ```sh
   rabbitmq-plugins enable rabbitmq_management
   ```

   可以看到如下提示信息:

   ```sh
   [root@mes01 soft]# rabbitmq-plugins enable rabbitmq_management
   Enabling plugins on node rabbit@mes01:
   rabbitmq_management
   The following plugins have been configured:
     rabbitmq_management
     rabbitmq_management_agent
     rabbitmq_web_dispatch
   Applying plugin configuration to rabbit@mes01...
   The following plugins have been enabled:
     rabbitmq_management
     rabbitmq_management_agent
     rabbitmq_web_dispatch
   
   started 3 plugins.
   ```

   当启用rabbitmq_management插件时，自动启动了依赖的rabbitmq_management_agent插件和rabbitmq_web_dispatch

   5. **启动RabbitMQ**

   启动命令

   ```sh
    systemctl start rabbitmq-server
   ```

   或者

   ```sh
   rabbitmq-server
   ```

   或者

   ```
   rabbitmq-server -detached
   ```

   将端口在防火墙上开放

   ```
   firewall-cmd --zone=public --add-port=15672/tcp --permanent
   firewall-cmd --reload
   ```

   6. **添加用户**

   ```
   rabbitmqctl add_user root 123456
   ```

   7. **给用户添加权限**

   ```
   rabbitmqctl set_permissions root -p / ".*" ".*" ".*"
   ```

   8. **给用户设置标签**

   ```sh
   rabbitmqctl set_user_tags root administrator
   ```

​			权限相关的描述

| 标签          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| (None)        | 没有访问management插件的权限                                 |
| management    | 可以使用消息协议做任何操作的权限，加上：<br/>1. 可以使用AMQP协议登录的虚拟主机的权限<br/>2.查看它们能登录的所有虚拟主机中所有队列、交换器和绑定的权限<br/>3. 查看和关闭它们自己的通道和连接的权限<br/>4.查看它们能访问的虚拟主机中的全局统计信息，包括其他用户的活动 |
| policymaker   | 所有management标签可以做的，加上：<br/>1.在它们能通过AMQP协议登录的虚拟主机上，查看、创建和删除策略以及虚拟主机参数的权限 |
| monitoring    | 所有management能做的，加上：<br/>1.列出所有的虚拟主机，包括列出不能使用消息协议访问的虚拟主机的权限<br/>2. 查看其他用户连接和通道的权限<br/>3. 查看节点级别的数据如内存使用和集群的权限<br/><br/>4. 查看真正的全局所有虚拟主机统计数据的权限 |
| administrator | 所有policymaker和monitoring能做的，加上：<br/>1. 创建删除虚拟主机的权限<br/>2. 查看、创建和删除用户的权限<br/>3. 查看、创建和删除权限的权限<br/>4. 关闭其他用户连接的权限 |

9. **打开浏览器访问**

http://<安装RabbitMQ的机器的IP>:15672

在rabbitMQ中存在一个内置帐号 guest，但此帐号仅限制本地访问，远程访问使用创建的的root帐号

![image-20230814121107779](img\image-20230814121107779.png)

登录成功后，可以看到

![image-20230814121303977](img\image-20230814121303977.png)







## 2. RabbitMQ介绍

>RabbitMQ，俗称“兔子MQ”（可见其轻巧、敏捷），是目前非常热门的一款消息中间件，不管是互联网行业还是传统行业都广泛使用

特点：

1. 高可靠性、易扩展、高可用、功能丰富等
2. 支持大多数（甚至冷门）的编程语言客户端。
3. RabbitMQ遵循AMQP协议，自身采用Erlang（一种由爱立信开发的通用面向并发编程的语言）编写。
4. RabbitMQ也支持MQTT等其他协议。
5. RabbitMQ具有强大的插件扩展能力，官方和社区提供了丰富的插件可供选择。插件地址：https://www.rabbitmq.com/community-plugins.html



### 2.1 Exchange类型

RabbitMQ常用的交换器类型有: fanout、direct、topic、headers四种

**fanout交换器**

会把所有发送给该交换器的消息路由到所有该交换器绑定的队列中。

![](img\ex_fanout.webp)

**direct交换器**

direct类型的交换器路由规则很简单，它会把消息路由到那些BindingKey和RoutingKey匹配的队列中

![](img\ex_direct.webp)

**topic交换器**

topic类型的交换器在direct类型匹配规则上进行了扩展。也是将原来消息路由到BindingKey和RoutingKey相匹配的队列中，这里匹配的规则稍微不同,它约定:

BindingKey和RoutingKey一样都是由“.”分隔的字符串；

BingingKey中可以存在两种特殊字符“\*”和“#”，用于模糊匹配，其中“\*”用于匹配一个单词，“#”用于匹配多个单词（可以是0个）

![](img\ex_topic.webp)

**Headers**

headers类型的交换器不依赖于路由键的匹配规则来路由信息，而是根据发送信息内容中的headers属性进行匹配。在绑定队列和交换器时指定 一组键值对，当发送的海波 到达交换器时，RabbitMQ会获取到该消息的headers，对比其中的键值对是否完全匹配队列和交换器绑定时指定的键值对，如果匹配，消息就会路由到该队列。headers类型的性能很差，不实用。



### 2.1 存储机制

消息类型有两种: 持久化消息和非持久化消息。

这两种消息都会被写入磁盘。

持久化消息在到达队列时写入磁盘，同时会在内存中保存一份备份，当内存吃紧时，消息从内存中清除。提高一定的性能。

非持久化消息一般只存于内存中，当内存压力大时数据刷盘处理，以节省内存空间。

存储层包含两个部分：队列索引和消息索引。

![image-20230817175813085](img\image-20230817175813085.png)

队列索引：rabbit_queue_index

索引维护队列的落盘消息的信息，如存储地点、是否已被消费都接收，是否已被消费者ack等。

每个队列都有相应的索引。

索引使用顺序段文件来存储，后缀名为.idx，文件名从0开始累加。每个段文件中包含固定的segment_entry_count条记录，默认值为16384

消息索引：rabbit_msg_store

消息以键值对的形式存储到文件中，一个虚拟主机上的所有队列使用同块存储。每个节点只有一个。存储分为持久化存储 （msg_store_persistent）和短暂存储(msg_store_transient) 。持久化存储的内容在broker重启后不会丢失，短暂存储的内容在borker重启后丢失。





## 3. RabbitMQ常用命令

### 3.1 启动停止rabbitMQ命令

```sh
# 前台启动Erlang VM 和 RabbitMQ 当窗口关闭或者ctrl+c时，使退出了。
rabbitmq-server

# 使用系统命令启动
systemctl start rabbitmq-server

# 后台启动
rabbitmq-server -detached

# 停止rabbitMQ和Erlang VM
rabbitmq-server stop
```

样例输出

启动命令-rabbitmq-server

```sh
[root@mes01 soft]# rabbitmq-server

  ##  ##      RabbitMQ 3.8.5
  ##  ##
  ##########  Copyright (c) 2007-2020 VMware, Inc. or its affiliates.
  ######  ##
  ##########  Licensed under the MPL 1.1. Website: https://rabbitmq.com

  Doc guides: https://rabbitmq.com/documentation.html
  Support:    https://rabbitmq.com/contact.html
  Tutorials:  https://rabbitmq.com/getstarted.html
  Monitoring: https://rabbitmq.com/monitoring.html

  Logs: /var/log/rabbitmq/rabbit@mes01.log
        /var/log/rabbitmq/rabbit@mes01_upgrade.log

  Config file(s): (none)

  Starting broker... completed with 3 plugins.

```

后台启动命令-rabbitmq-server -detached

```sh
[root@mes01 soft]# rabbitmq-server -detached
[root@mes01 soft]# 
```

停止命令-rabbitmq-server stop

```sh
[root@mes01 soft]# rabbitmq-server stop
BOOT FAILED
===========
ERROR: distribution port 25672 in use by rabbit@mes01

14:12:35.938 [error] 
14:12:35.943 [error] BOOT FAILED
14:12:35.943 [error] ===========
14:12:35.943 [error] ERROR: distribution port 25672 in use by rabbit@mes01
14:12:35.943 [error] 
14:12:36.946 [error] Supervisor rabbit_prelaunch_sup had child prelaunch started with rabbit_prelaunch:run_prelaunch_first_phase() at undefined exit with reason {dist_port_already_used,25672,"rabbit","mes01"} in context start_error
14:12:36.947 [error] CRASH REPORT Process <0.154.0> with 0 neighbours exited with reason: {{shutdown,{failed_to_start_child,prelaunch,{dist_port_already_used,25672,"rabbit","mes01"}}},{rabbit_prelaunch_app,start,[normal,[]]}} in application_master:init/4 line 138
{"Kernel pid terminated",application_controller,"{application_start_failure,rabbitmq_prelaunch,{{shutdown,{failed_to_start_child,prelaunch,{dist_port_already_used,25672,\"rabbit\",\"mes01\"}}},{rabbit_prelaunch_app,start,[normal,[]]}}}"}
Kernel pid terminated (application_controller) ({application_start_failure,rabbitmq_prelaunch,{{shutdown,{failed_to_start_child,prelaunch,{dist_port_already_used,25672,"rabbit","mes01"}}},{rabbit_prel

Crash dump is being written to: erl_crash.dump...done
```

停止命令-rabbitmq-server stop

```
[root@mes01 soft]# rabbitmqctl stop
Stopping and halting node rabbit@mes01 ...
```

启动后的进程信息

```sh
[root@mes01 ~]# ps -ef | grep rabbitmq
root      1413  1333  0 14:19 pts/0    00:00:00 /sbin/runuser -u rabbitmq -- /usr/lib/rabbitmq/bin/rabbitmq-server
rabbitmq  1421  1413  0 14:19 pts/0    00:00:00 /bin/sh /usr/lib/rabbitmq/bin/rabbitmq-server
rabbitmq  1428  1421 43 14:19 pts/0    00:00:12 /usr/lib64/erlang/erts-11.0.2/bin/beam.smp -W w -K true -A 64 -MBas ageffcbf -MHas ageffcbf -MBlmbcs 512 -MHlmbcs 512 -MMmcs 30 -P 1048576 -t 5000000 -stbt db -zdbbl 128000 -B i -- -root /usr/lib64/erlang -progname erl -- -home /var/lib/rabbitmq -- -pa  -noshell -noinput -s rabbit boot -boot start_sasl -lager crash_log false -lager handlers []
rabbitmq  1539  1428  0 14:19 ?        00:00:00 erl_child_setup 1024
rabbitmq  1568     1  0 14:19 ?        00:00:00 /usr/lib64/erlang/erts-11.0.2/bin/epmd -daemon
rabbitmq  1595  1539  0 14:19 ?        00:00:00 inet_gethost 4
rabbitmq  1596  1595  0 14:19 ?        00:00:00 inet_gethost 4
root      1679  1621  0 14:19 pts/1    00:00:00 grep --color=auto rabbitmq
```





### 3.2 一般操作命令

```sh
# 查看所有队列
rabbitmqctl list_queues

# 查看所有虚拟主机
rabbitmqctl list_vhosts

# 在Erlang VM运行的情况下启动或者停止RabbitMQ应用
rabbitmqctl start_app
rabbitmqctl stop_app

# 查看节点状态
rabbitmqctl status

# 查看可用插件
rabbitmq-plugins list

# 启用插件
rabbitmq-plugins enable <plugin-name>

# 停用插件
rabbitmq-plugins disable <plugin-name>

# 移除所有数据 要在rabbitmqctl stop_app之后使用
rabbitmqctl reset


# 查看所有交换器信息-列表式
rabbitmqctl list_exchanges
# 格式化查看
rabbitmqctl list_exchanges --formatter pretty_table 


# 查看绑定的列表 - 查看交换机队列绑定信息
rabbitmqctl list_bindings
# 格式化查看
rabbitmqctl list_bindings --formatter pretty_table

```

样例:

**查看所有队列-rabbitmqctl list_queues**

```sh
[root@mes01 ~]# rabbitmqctl list_queues
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
```

**查看所有虚拟主机-rabbitmqctl list_vhosts**

```sh
[root@mes01 ~]# rabbitmqctl list_vhosts
Listing vhosts ...
name
/
```

**停止RabbitMQ应用-rabbitmqctl stop_app**

```
[root@mes01 ~]# rabbitmqctl stop_app
Stopping rabbit application on node rabbit@mes01 ...
```

当停止应用后，可以观察到mq的进程都还是存在的。但页面已经无法访问

```shell
[root@mes01 ~]# ps -ef | grep rabbitmq
rabbitmq  1568     1  0 14:19 ?        00:00:00 /usr/lib64/erlang/erts-11.0.2/bin/epmd -daemon
rabbitmq  1971     1  5 14:22 ?        00:00:41 /usr/lib64/erlang/erts-11.0.2/bin/beam.smp -W w -K true -A 64 -MBas ageffcbf -MHas ageffcbf -MBlmbcs 512 -MHlmbcs 512 -MMmcs 30 -P 1048576 -t 5000000 -stbt db -zdbbl 128000 -- -root /usr/lib64/erlang -progname erl -- -home /var/lib/rabbitmq -- -pa  -noshell -noinput -s rabbit boot -boot start_sasl -lager crash_log false -lager handlers []
rabbitmq  2080  1971  0 14:22 ?        00:00:00 erl_child_setup 32768
rabbitmq  2146  2080  0 14:22 ?        00:00:00 inet_gethost 4
rabbitmq  2396  2146  0 14:24 ?        00:00:00 inet_gethost 4
root      3584  1621  0 14:35 pts/1    00:00:00 grep --color=auto rabbitmq
```

观察页面

![image-20230814143701852](img\image-20230814143701852.png)

**启动RabbitMQ应用-rabbitmqctl start_app**

```sh
[root@mes01 ~]# rabbitmqctl start_app
Starting node rabbit@mes01 ...
```

此时查看进程

```sh
[root@mes01 ~]# ps -ef | grep rabbitmq
rabbitmq  1568     1  0 14:19 ?        00:00:00 /usr/lib64/erlang/erts-11.0.2/bin/epmd -daemon
rabbitmq  1971     1  5 14:22 ?        00:00:50 /usr/lib64/erlang/erts-11.0.2/bin/beam.smp -W w -K true -A 64 -MBas ageffcbf -MHas ageffcbf -MBlmbcs 512 -MHlmbcs 512 -MMmcs 30 -P 1048576 -t 5000000 -stbt db -zdbbl 128000 -- -root /usr/lib64/erlang -progname erl -- -home /var/lib/rabbitmq -- -pa  -noshell -noinput -s rabbit boot -boot start_sasl -lager crash_log false -lager handlers []
rabbitmq  2080  1971  0 14:22 ?        00:00:00 erl_child_setup 32768
rabbitmq  2146  2080  0 14:22 ?        00:00:00 inet_gethost 4
rabbitmq  2396  2146  0 14:24 ?        00:00:00 inet_gethost 4
root      3841  1621  0 14:38 pts/1    00:00:00 grep --color=auto rabbitmq
```

查看rabbitMQ管理页面

![image-20230814143932787](img\image-20230814143932787.png)



**查看节点状态-rabbitmqctl status**

```sh
[root@mes01 ~]# rabbitmqctl status
Status of node rabbit@mes01 ...
Runtime

OS PID: 1971
OS: Linux
Uptime (seconds): 1137
RabbitMQ version: 3.8.5
Node name: rabbit@mes01
Erlang configuration: Erlang/OTP 23 [erts-11.0.2] [source] [64-bit] [smp:4:4] [ds:4:4:10] [async-threads:64] [hipe]
Erlang processes: 448 used, 1048576 limit
Scheduler run queue: 1
Cluster heartbeat timeout (net_ticktime): 60

Plugins

Enabled plugin file: /etc/rabbitmq/enabled_plugins
Enabled plugins:

 * rabbitmq_management
 * amqp_client
 * rabbitmq_web_dispatch
 * cowboy
 * cowlib
 * rabbitmq_management_agent

Data directory

Node data directory: /var/lib/rabbitmq/mnesia/rabbit@mes01
Raft data directory: /var/lib/rabbitmq/mnesia/rabbit@mes01/quorum/rabbit@mes01

Config files


Log file(s)

 * /var/log/rabbitmq/rabbit@mes01.log
 * /var/log/rabbitmq/rabbit@mes01_upgrade.log

Alarms

(none)

Memory

Calculation strategy: rss
Memory high watermark setting: 0.4 of available memory, computed to: 3.295 gb
other_proc: 0.0333 gb (31.58 %)
code: 0.0277 gb (26.21 %)
allocated_unused: 0.0171 gb (16.17 %)
other_system: 0.0143 gb (13.52 %)
reserved_unallocated: 0.0062 gb (5.9 %)
other_ets: 0.0032 gb (3.05 %)
atom: 0.0014 gb (1.34 %)
plugins: 0.0014 gb (1.32 %)
binary: 0.0002 gb (0.21 %)
mgmt_db: 0.0002 gb (0.21 %)
metrics: 0.0002 gb (0.2 %)
connection_other: 0.0002 gb (0.15 %)
mnesia: 0.0001 gb (0.07 %)
quorum_ets: 0.0 gb (0.05 %)
msg_index: 0.0 gb (0.03 %)
connection_channels: 0.0 gb (0.0 %)
connection_readers: 0.0 gb (0.0 %)
connection_writers: 0.0 gb (0.0 %)
queue_procs: 0.0 gb (0.0 %)
queue_slave_procs: 0.0 gb (0.0 %)
quorum_queue_procs: 0.0 gb (0.0 %)

File Descriptors

Total: 2, limit: 32671
Sockets: 0, limit: 29401

Free Disk Space

Low free disk space watermark: 0.05 gb
Free disk space: 23.2459 gb

Totals

Connection count: 0
Queue count: 0
Virtual host count: 1

Listeners

Interface: [::], port: 25672, protocol: clustering, purpose: inter-node and CLI tool communication
Interface: [::], port: 5672, protocol: amqp, purpose: AMQP 0-9-1 and AMQP 1.0
Interface: [::], port: 15672, protocol: http, purpose: HTTP API
```

**查看可用插件-rabbitmq-plugins list**

```sh
[root@mes01 ~]# rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@mes01
 |/
[  ] rabbitmq_amqp1_0                  3.8.5
[  ] rabbitmq_auth_backend_cache       3.8.5
[  ] rabbitmq_auth_backend_http        3.8.5
[  ] rabbitmq_auth_backend_ldap        3.8.5
[  ] rabbitmq_auth_backend_oauth2      3.8.5
[  ] rabbitmq_auth_mechanism_ssl       3.8.5
[  ] rabbitmq_consistent_hash_exchange 3.8.5
[  ] rabbitmq_event_exchange           3.8.5
[  ] rabbitmq_federation               3.8.5
[  ] rabbitmq_federation_management    3.8.5
[  ] rabbitmq_jms_topic_exchange       3.8.5
[E*] rabbitmq_management               3.8.5
[e*] rabbitmq_management_agent         3.8.5
[  ] rabbitmq_mqtt                     3.8.5
[  ] rabbitmq_peer_discovery_aws       3.8.5
[  ] rabbitmq_peer_discovery_common    3.8.5
[  ] rabbitmq_peer_discovery_consul    3.8.5
[  ] rabbitmq_peer_discovery_etcd      3.8.5
[  ] rabbitmq_peer_discovery_k8s       3.8.5
[  ] rabbitmq_prometheus               3.8.5
[  ] rabbitmq_random_exchange          3.8.5
[  ] rabbitmq_recent_history_exchange  3.8.5
[  ] rabbitmq_sharding                 3.8.5
[  ] rabbitmq_shovel                   3.8.5
[  ] rabbitmq_shovel_management        3.8.5
[  ] rabbitmq_stomp                    3.8.5
[  ] rabbitmq_top                      3.8.5
[  ] rabbitmq_tracing                  3.8.5
[  ] rabbitmq_trust_store              3.8.5
[e*] rabbitmq_web_dispatch             3.8.5
[  ] rabbitmq_web_mqtt                 3.8.5
[  ] rabbitmq_web_mqtt_examples        3.8.5
[  ] rabbitmq_web_stomp                3.8.5
[  ] rabbitmq_web_stomp_examples       3.8.5
```

E* 表示启动的插件 e* 表示依赖启动的插件

rabbitmq_management  依赖于rabbitmq_management_agent和rabbitmq_web_dispatch  



**启用插件-rabbitmq-plugins enable rabbitmq_management**

```sh
[root@mes01 soft]# rabbitmq-plugins enable rabbitmq_management
Enabling plugins on node rabbit@mes01:
rabbitmq_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@mes01...
The following plugins have been enabled:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch

started 3 plugins.
```

**信用插件-rabbitmq-plugins disable rabbitmq_management**

```sh
[root@mes01 ~]# rabbitmq-plugins disable rabbitmq_management
Disabling plugins on node rabbit@mes01:
rabbitmq_management
All plugins have been disabled.
Applying plugin configuration to rabbit@mes01...
The following plugins have been disabled:
  rabbitmq_management_agent
  rabbitmq_web_dispatch
  rabbitmq_management

stopped 3 plugins.
```

再次检查插件列表

```sh
[root@mes01 ~]# rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@mes01
 |/
[  ] rabbitmq_amqp1_0                  3.8.5
[  ] rabbitmq_auth_backend_cache       3.8.5
[  ] rabbitmq_auth_backend_http        3.8.5
[  ] rabbitmq_auth_backend_ldap        3.8.5
[  ] rabbitmq_auth_backend_oauth2      3.8.5
[  ] rabbitmq_auth_mechanism_ssl       3.8.5
[  ] rabbitmq_consistent_hash_exchange 3.8.5
[  ] rabbitmq_event_exchange           3.8.5
[  ] rabbitmq_federation               3.8.5
[  ] rabbitmq_federation_management    3.8.5
[  ] rabbitmq_jms_topic_exchange       3.8.5
[  ] rabbitmq_management               3.8.5
[  ] rabbitmq_management_agent         3.8.5
[  ] rabbitmq_mqtt                     3.8.5
[  ] rabbitmq_peer_discovery_aws       3.8.5
[  ] rabbitmq_peer_discovery_common    3.8.5
[  ] rabbitmq_peer_discovery_consul    3.8.5
[  ] rabbitmq_peer_discovery_etcd      3.8.5
[  ] rabbitmq_peer_discovery_k8s       3.8.5
[  ] rabbitmq_prometheus               3.8.5
[  ] rabbitmq_random_exchange          3.8.5
[  ] rabbitmq_recent_history_exchange  3.8.5
[  ] rabbitmq_sharding                 3.8.5
[  ] rabbitmq_shovel                   3.8.5
[  ] rabbitmq_shovel_management        3.8.5
[  ] rabbitmq_stomp                    3.8.5
[  ] rabbitmq_top                      3.8.5
[  ] rabbitmq_tracing                  3.8.5
[  ] rabbitmq_trust_store              3.8.5
[  ] rabbitmq_web_dispatch             3.8.5
[  ] rabbitmq_web_mqtt                 3.8.5
[  ] rabbitmq_web_mqtt_examples        3.8.5
[  ] rabbitmq_web_stomp                3.8.5
[  ] rabbitmq_web_stomp_examples       3.8.5
```

**重置所有数据**

```sh
[root@mes01 ~]# rabbitmqctl stop_app
Stopping rabbit application on node rabbit@mes01 ...
[root@mes01 ~]# rabbitmqctl reset
Resetting node rabbit@mes01 ...
[root@mes01 ~]# 
```





### 3.3 用户权限管理命令

```sh
# 查看所有用户
rabbitmqctl list_users

# 添加用户
rabbitmqctl add_user username password

# 修改用户密码
rabbitmqctl change_password username password

# 删除用户
rabbitmqctl delete_user username

# 设置用户权限
rabbitmqctl set_permissions -p vhostpath username ".*" ".*" ".*"

# 列表用户权限
rabbitmqctl list_user_permissions username

# 清除用户权限
rabbitmqctl clear_permissions -p vhostspath username

# 给用户设置标签
rabbitmqctl set_user_tags username tag

# 创建虚拟主机
rabbitmqctl add_vhost vhostpath

# 列表所有虚拟主机
rabbitmqctl list_vhosts

# 列表所有虚拟主机的权限
rabbitmqctl list_permissions -p vhostpath

# 删除虚拟主机
rabbitmqctl delete_vhosts vhostspath
```

样例

**列出所有用户-rabbitmqctl list_users**

```sh
[root@mes01 ~]# rabbitmqctl list_users
Listing users ...
user    tags
guest   [administrator]
root    [administrator]
```

**添加用户**

```sh
[root@mes01 ~]# rabbitmqctl add_user test 123456
Adding user "test" ...
[root@mes01 ~]# rabbitmqctl list_users
Listing users ...
user    tags
guest   [administrator]
test    []
root    [administrator]
[root@mes01 ~]# 
```

**修改用户密码**

```sh
root@mes01 ~]# rabbitmqctl change_password test 654321
Changing password for user "test" ...
```

**设置并查看权限**

```sh
[root@mes01 ~]# rabbitmqctl set_permissions -p / test ".*" ".*" ".*"
Setting permissions for user "test" in vhost "/" ...
[root@mes01 ~]# rabbitmqctl list_user_permissions test
Listing permissions for user "test" ...
vhost   configure       write   read
/       .*      .*      .*
```

**设置用户标签并查看**

```sh
root@mes01 ~]# rabbitmqctl set_user_tags test management
Setting tags for user "test" to [management] ...
[root@mes01 ~]# rabbitmqctl list_users
Listing users ...
user    tags
guest   [administrator]
test    [management]
root    [administrator]
```

**清除并查看权限**

```sh
[root@mes01 ~]# rabbitmqctl clear_permissions -p / test
Clearing permissions for user "test" in vhost "/" ...
[root@mes01 ~]# rabbitmqctl list_user_permissions test
Listing permissions for user "test" ...
[root@mes01 ~]# 
```

**删除用户**

```sh
[root@mes01 ~]# rabbitmqctl delete_user test
Deleting user "test" ...
[root@mes01 ~]# rabbitmqctl list_users
Listing users ...
user    tags
guest   [administrator]
root    [administrator]
```

**创建虚拟主机并查看**

```sh
[root@mes01 ~]# rabbitmqctl add_vhost /test
Adding vhost "/test" ...
[root@mes01 ~]# rabbitmqctl list_vhosts
Listing vhosts ...
name
/test
/
[root@mes01 ~]# 
```

**列出虚拟主机的权限**

```sh
[root@mes01 ~]# rabbitmqctl list_permissions -p /
Listing permissions for vhost "/" ...
user    configure       write   read
root    .*      .*      .*
test    .*      .*      .*
guest   .*      .*      .*
```

**删除虚拟主机并查看**

```sh
[root@mes01 ~]# rabbitmqctl delete_vhost /test
Deleting vhost "/test" ...
[root@mes01 ~]# rabbitmqctl list_vhosts
Listing vhosts ...
name
/
[root@mes01 ~]# 
```

**查看交换器**

```shell
[root@os ~]# rabbitmqctl list_exchanges
Listing exchanges for vhost / ...
name    type
amq.fanout      fanout
amq.rabbitmq.trace      topic
amq.headers     headers
amq.topic       topic
ex.biz  direct
amq.direct      direct
        direct
amq.match       headers
ex.wk   direct
[root@os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ ex.biz             │ direct  │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
├────────────────────┼─────────┤
│ ex.wk              │ direct  │
└────────────────────┴─────────┘
[root@os ~]# 
```



**查看绑定的列表**

```sh
[root@os ~]# rabbitmqctl list_bindings
Listing bindings for vhost /...
source_name     source_kind     destination_name        destination_kind        routing_key     arguments
        exchange        qu.wk   queue   qu.wk   []
ex.wk   exchange        qu.wk   queue   rk.wq   []
[root@os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ qu.wk            │ queue            │ qu.wk       │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ ex.wk       │ exchange    │ qu.wk            │ queue            │ rk.wq       │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘

```



## 4. RabbitMQ工作流程

### 4.1 基本的工作流程

**生产者发送消息的流程**

>1. 生产者连接RabbitMQ，建立TCP连接（connect），开启信道（channel）
>2. 生成者声明一个Exchange（交换器），并设置相关属性，比如交换器类型，是否持久化等
>3. 生产者声明一个队列并设置相关属性，比如是否排他，是否持久化，是否自动删除等
>4. 生产者通过BindingKey（绑定key）将交换器和队列绑定（binding）起来。
>5. 生产者发送相关消息至RabbitMQ Broker，其中包含routingkey（路由键）、交换器等信息
>6. 相应的交换器根据接收到的routingKey查找相匹配的队列。
>7. 如果找到，则将从生产者发送过来的消息存入相应的队列中。
>8. 如果没有找到，则根据生产者配置的属性选择丢弃还是回退给生产者。
>9. 关闭信道
>10. 关闭连接

**消费者接收消息的过程**

>1. 消费者连接到RabbitMQ Broker，建立一个连接（connect），开启一个信道（channel）。
>2. 消费者向RabbitMQ Broker请求消费相应 队列中的消息，可能会设置相应的回调函数，以及做一些准备工作。
>3. 等待RabbitMQ Broker回应并投递相应队列中的消息，消费者接收消息。
>4. 消费者确认ACK接收到相应的消息。
>5. RabbitMQ从队列中删除相应已经被确认的消息。
>6. 关闭信道。
>7. 关闭连接。



### 4.2 RabbitMQ的Hello World

导入依赖:

```xml
           <dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>5.9.0</version>
            </dependency>
```





RabbitMQ的生产者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 *
 */
public class HelloProduct {
    public static void main(String[] args) {
        //获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置主机名
        factory.setHost("node1");
        //设置虚拟主机名 / 在URL中的转义字符为%2f
        factory.setVirtualHost("/");
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("123456");
        //设置端口
        factory.setPort(5672);

        try (
                //建立TCP连接
                Connection connection = factory.newConnection();
                //获取通道
                Channel channel = connection.createChannel()
        ) {
            //声明一个队列
            //第一个参数为队列名称
            //是否是持久化的
            //是否是排他的
            //是否是自动删除
            //是消除队列的属性，使用默认值
            channel.queueDeclare("queue.biz", false, false, true, null);
            //声明一个交换机
            //交换器的名称
            //交换器的类型
            //交换器是否是持久化
            //交换器是否是自动删除
            //交换器属性map集合
            channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT, false, false, null);

            //将队列和交换机进行绑定,并指定路由键
            channel.queueBind("queue.biz", "ex.biz", "hello.world");

            //发送消息
            //交换器的名称
            //该消息的路由键
            //该消息的属性MessageProperties对象
            //消息的内容字节数组。
            channel.basicPublish("ex.biz", "hello.world", null, "hello world 1".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

使用拉的模式获取数据

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * 拉模式的消费者
 */
public class HelloGetConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //指定协议:amqp
        //指定用户名 root
        //指定密码 123456
        //指定host node1
        //指定端口号 5672
        //指定虚拟主机 %2f 即是/
        factory.setUri("amqp://root:123456@node1:5672/%2f");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();) {

            //使用拉模式获取数据
            //指定从哪个消费队列中消费消息
            //表示自动ack确认。
            GetResponse getResponse = channel.basicGet("queue.biz", true);
            byte[] body = getResponse.getBody();
            System.out.println("最终的消息:"+new String(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```



使用推模式进行获取数据

```java
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * 消费者使用推模式获取消息
 */
public class HelloConsumeConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    // 指定协议:amqp
    // 指定用户名 root
    // 指定密码 123456
    // 指定host node1
    // 指定端口号 5672
    // 指定虚拟主机 %2f 即是/
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 由于消息在完成后会删除队列，所以，需要保证消息队列存在
    // 声明一个队列
    // 第一个参数为队列名称
    // 是否是持久化的
    // 是否是排他的
    // 是否是自动删除
    // 是消除队列的属性，使用默认值
    channel.queueDeclare("queue.biz", false, false, true, null);

    // 监听消息，一旦有消息推送过来，就调用第一个lambda表达式。
    DeliverCallback callback =
        new DeliverCallback() {
          @Override
          public void handle(String consumerTag, Delivery message) throws IOException {
            System.out.println("获取的数据:" + new String(message.getBody()));
          }
        };
    CancelCallback cancelCallback =
        new CancelCallback() {
          @Override
          public void handle(String consumerTag) throws IOException {
            System.out.println("CancelCallback:" + new String(consumerTag.getBytes()));
          }
        };
    channel.basicConsume("queue.biz", callback, cancelCallback);

    channel.basicConsume("queue.biz", (consumerTag, message) -> {}, (consumerTag -> {}));
  }
}
```

由于采用了推模式，客户端与服务端的连接是不能被关闭的，当客户端向MQ中发送消息后，会以最快的速度推送给此客户端。



**使用拉模式获取数据测试**

1. 首先启动生产者，向MQ中发送一条消息。

   即启动HelloProduct的main函数。便将消息发送了出去.

   

   ![image-20230815111514113](img\image-20230815111514113.png)

   可以发现，交换机已经创建了。

![image-20230815111548267](img\image-20230815111548267.png)

队列也已经被创建了。点击队列进去，通过Get messages便可查看发送的数据

![image-20230815111728426](img\image-20230815111728426.png)

2. **使用拉模式获取数据**

启动HelloGetConsumer，便可获取MQ中的记录。

此时便可看到数据。

![image-20230815111940196](img\image-20230815111940196.png)



**使用推模式获取数据测试**

1. 使用推模式获取数据，首先要启动消费者，让消费都首先开始监听队列。

即启动HelloConsumeConsumer的Main函数，便可发现，此时已经处于监听状态。

![image-20230815112311954](img\image-20230815112311954.png)

2. 启动生产者发送测试数据。

   调用HelloProduct来发送测试数据。

3. 再观察HelloConsumeConsumer的控制台，发现已经将数据成功接收。

   ![image-20230815112833731](img\image-20230815112833731.png)




### 4.3 RabbitMQ工作模式

官网关于工作模式的解释地址：https://www.rabbitmq.com/getstarted.html



#### 4.3.1 Work Queue（工作队列）

生产者发消息，启动多个消费者来消费消息，每个消费者仅消费部分消息，可达到负载均衡的效果。

![image-20230815112833731](img\python-two.png)

**创建生产者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class Product {


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://root:123456@node1:5672/%2f");

        //创建连接和队列
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        //声明队列,持久化，不自动删除
        channel.queueDeclare("qu.wk", true, false, false, null);

        //声明交换机,消息持久化，不自动删除
        channel.exchangeDeclare("ex.wk", BuiltinExchangeType.DIRECT, true, false, null);

        //队列和交换机绑定
        channel.queueBind("qu.wk", "ex.wk", "rk.wq");

        for (int i = 0; i < 20; i++) {
            //发送消息
            channel.basicPublish("ex.wk",
                    "rk.wq", null, ("data msg " + i).getBytes(StandardCharsets.UTF_8));
        }


        channel.close();
        connection.close();
    }

}

```



**创建消费者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://root:123456@node1:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明队列,持久化，不自动删除
        channel.queueDeclare("qu.wk", true, false, false, null);

        channel.basicConsume("qu.wk", new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("cancel的消息:" + consumerTag);
            }
        });
    }
}
```



首先运行消息费，为了测试工作队列模式，消费都需要启动多个，看是否能够进行负载均衡操作。

在IDEA中启动多个消费者，注意需要沟选启动运行参数：

![image-20230815232238577](img\image-20230815232238577.png)

此样例中启动是4个。

![image-20230815232405898](img\image-20230815232405898.png)



启动生产者，再观察消费者的输出信息：



再次观察消费者的输出便可发现：

![image-20230815232518227](img\image-20230815232518227.png)

![image-20230815232544082](img\image-20230815232544082.png)

![image-20230815232602794](img\image-20230815232602794.png)

![image-20230815232621424](img\image-20230815232621424.png)

可以发现每个工作队列都收到了5条消息。

此便可看出工作队列的一个重要特性，负载均衡。





#### 4.3.2  Publish/Subscribe（发布订阅模式）

官方文档： https://www.rabbitmq.com/tutorials/tutorial-three-python.html

>使用fanout类型类型的交换器，routingKey忽略。每个消费者定义生成一个队列关绑定到同一个Exchange，每个消费者都可以消费完整的消息。
>
>消息广播给所有订阅该消息的消费者。
>
>在RabbitMQ中，生产者不是将消息直接发送给消息消息队列，实际上生产者根本不知道一个消息被发送到哪个队列。
>
>生产者将消息发送给交换器。交换器非常简单，从生成者接收消息，将消息推送给消息队列。交换器必须清楚的知道要怎么处理接收到的消息。应该是追加到一个指定的队列，还是追加到多个队列，还是丢弃。规则就是交换器的类型。

![11](img\exchanges.png)



发布订阅使用fanout的交换器，创建交换器，名称为test

```java
channel.exchangeDeclare("test","fanout");
```

`fanout`交换器很简单，从名称就可以看出来（用风扇吹出去），将所有的收到的消息发给它的知道的所有队列。



存在一个默认的交换器。

此样例使用的是临时队列，即消费都实现将自动创建此队列，当消费都退出后，此队列也将自动删除。

队列名称如

```sh
amq.gen-gjKBgQ9PSmoj2YQGMOdPfA
```



**样例代码**

消费者1的代码:

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OneConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明的临时队列，名称由rabbitMQ自动生成
    String queueName = channel.queueDeclare().getQueue();
    System.out.println("临时队列的名称：" + queueName);

    // 定义交换机
    channel.exchangeDeclare("ex.testfan", BuiltinExchangeType.FANOUT, true, false, null);

    // 消息队列与交换机的绑定
    channel.queueBind(queueName, "ex.testfan", "");

    channel.basicConsume(
        queueName,
        new DeliverCallback() {
          @Override
          public void handle(String consumerTag, Delivery message) throws IOException {
            System.out.println(
                "one 获取到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
          }
        },
        new CancelCallback() {
          @Override
          public void handle(String consumerTag) throws IOException {}
        });
  }
}

```

**消费者2**

```sh
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TwoConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 生成的临时队列
    String queueName = channel.queueDeclare().getQueue();
    System.out.println("临时队列的名称：" + queueName);

    // 定义交换机
    channel.exchangeDeclare("ex.testfan", BuiltinExchangeType.FANOUT, true, false, null);

    // 消息队列与交换机的绑定
    channel.queueBind(queueName, "ex.testfan", "");

    channel.basicConsume(
        queueName,
        new DeliverCallback() {
          @Override
          public void handle(String consumerTag, Delivery message) throws IOException {
            System.out.println(
                "two 获取到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
          }
        },
        new CancelCallback() {
          @Override
          public void handle(String consumerTag) throws IOException {}
        });
  }
}

```

**消费者3**

```sh
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ThirdConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 生成的临时队列
    String queueName = channel.queueDeclare().getQueue();
    System.out.println("临时队列的名称：" + queueName);

    // 定义交换机
    channel.exchangeDeclare("ex.testfan", BuiltinExchangeType.FANOUT, true, false, null);

    // 消息队列与交换机的绑定
    channel.queueBind(queueName, "ex.testfan", "");

    channel.basicConsume(
        queueName,
        new DeliverCallback() {
          @Override
          public void handle(String consumerTag, Delivery message) throws IOException {
            System.out.println(
                "third 获取到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
          }
        },
        new CancelCallback() {
          @Override
          public void handle(String consumerTag) throws IOException {}
        });
  }
}
```

**生产者**

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Product {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    try {
      // 声明fanout类型交换机
      channel.exchangeDeclare("ex.testfan", "fanout", true, false, false, null);

      for (int i = 0; i < 20; i++) {
        channel.basicPublish(
            "ex.testfan",
            // 路由key
            "",
            // 属性
            null,
            // 信息
            ("hello world fan " + i).getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      channel.close();
      connection.close();
    }
  }
}
```



观察下队列的绑定的情况：

在未启动消费都队列之前:

```sh
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
[root@nullnull-os ~]# 
```

在未启动消费者之前，只有看到几个默认的生产者。绑定的队列为空。

启动三个消费者：

```sh
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ ex.testfan         │ fanout  │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬────────────────────────────────┬──────────────────┬────────────────────────────────┬───────────┐
│ source_name │ source_kind │ destination_name               │ destination_kind │ routing_key                    │ arguments │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│             │ exchange    │ amq.gen-VbV63vwAn0IBzC7n6I--vQ │ queue            │ amq.gen-VbV63vwAn0IBzC7n6I--vQ │           │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│             │ exchange    │ amq.gen-UG67rAw03FGbBupHX6o18g │ queue            │ amq.gen-UG67rAw03FGbBupHX6o18g │           │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│             │ exchange    │ amq.gen-HnQLeaOB1YOEJXXfXP5_Mg │ queue            │ amq.gen-HnQLeaOB1YOEJXXfXP5_Mg │           │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│ ex.testfan  │ exchange    │ amq.gen-HnQLeaOB1YOEJXXfXP5_Mg │ queue            │                                │           │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│ ex.testfan  │ exchange    │ amq.gen-UG67rAw03FGbBupHX6o18g │ queue            │                                │           │
├─────────────┼─────────────┼────────────────────────────────┼──────────────────┼────────────────────────────────┼───────────┤
│ ex.testfan  │ exchange    │ amq.gen-VbV63vwAn0IBzC7n6I--vQ │ queue            │                                │           │
└─────────────┴─────────────┴────────────────────────────────┴──────────────────┴────────────────────────────────┴───────────┘
[root@nullnull-os ~]# 
```

当启动生产者后，可以发现已经产生了3个默认的交换机及队列的绑定关系。以及手动绑定的3个队列的关系。

启动生产者，查看消费情况：

消费者1

```sh
临时队列的名称：amq.gen-VbV63vwAn0IBzC7n6I--vQ
one 获取到的消息：hello world fan 0
one 获取到的消息：hello world fan 1
one 获取到的消息：hello world fan 2
one 获取到的消息：hello world fan 3
one 获取到的消息：hello world fan 4
one 获取到的消息：hello world fan 5
one 获取到的消息：hello world fan 6
one 获取到的消息：hello world fan 7
one 获取到的消息：hello world fan 8
one 获取到的消息：hello world fan 9
one 获取到的消息：hello world fan 10
one 获取到的消息：hello world fan 11
one 获取到的消息：hello world fan 12
one 获取到的消息：hello world fan 13
one 获取到的消息：hello world fan 14
one 获取到的消息：hello world fan 15
one 获取到的消息：hello world fan 16
one 获取到的消息：hello world fan 17
one 获取到的消息：hello world fan 18
one 获取到的消息：hello world fan 19
```

消费者2：

```sh
临时队列的名称：amq.gen-KadV2OsCRLb84p2k_ijuww
two 获取到的消息：hello world fan 0
two 获取到的消息：hello world fan 1
two 获取到的消息：hello world fan 2
two 获取到的消息：hello world fan 3
two 获取到的消息：hello world fan 4
two 获取到的消息：hello world fan 5
two 获取到的消息：hello world fan 6
two 获取到的消息：hello world fan 7
two 获取到的消息：hello world fan 8
two 获取到的消息：hello world fan 9
two 获取到的消息：hello world fan 10
two 获取到的消息：hello world fan 11
two 获取到的消息：hello world fan 12
two 获取到的消息：hello world fan 13
two 获取到的消息：hello world fan 14
two 获取到的消息：hello world fan 15
two 获取到的消息：hello world fan 16
two 获取到的消息：hello world fan 17
two 获取到的消息：hello world fan 18
two 获取到的消息：hello world fan 19
```

消息者3：

```sh
临时队列的名称：amq.gen-TcqXVnoS2mjOpfCw1o1CZw
third 获取到的消息：hello world fan 0
third 获取到的消息：hello world fan 1
third 获取到的消息：hello world fan 2
third 获取到的消息：hello world fan 3
third 获取到的消息：hello world fan 4
third 获取到的消息：hello world fan 5
third 获取到的消息：hello world fan 6
third 获取到的消息：hello world fan 7
third 获取到的消息：hello world fan 8
third 获取到的消息：hello world fan 9
third 获取到的消息：hello world fan 10
third 获取到的消息：hello world fan 11
third 获取到的消息：hello world fan 12
third 获取到的消息：hello world fan 13
third 获取到的消息：hello world fan 14
third 获取到的消息：hello world fan 15
third 获取到的消息：hello world fan 16
third 获取到的消息：hello world fan 17
third 获取到的消息：hello world fan 18
third 获取到的消息：hello world fan 19
```



再停止几个消费者查看队列信息 

```
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ ex.testfan         │ fanout  │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
[root@nullnull-os ~]# 
```

可以看到，当客户端退出之后，临时队列也就消失了。

#### 4.3.3 **默认交换器**

默认交换器的生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Product {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(
        "queue.default.ex",
        // 是否活动MQ重启
        false,
        // 是否只能自己这个连接来使用
        false,
        // 是否自动删除
        false,
        // 参数
        null);

    try {
      //在发送消息的时候没有指定交换器的名称，此时使用的是默认的交换器，默认交换器没有名称。
      //路由键就是目的消息队列的名称
      channel.basicPublish(
          // 空字符使用默认交换机
          "",
          // 指定队列
          "queue.default.ex",
          // 参数
          null,
          // 发送的消息
          "hello nullnull".getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      channel.close();
      connection.close();
    }
  }
}

```

生产者不需要指定交换机。创建 一个队列即可。



消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;

public class Consumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    try {
      GetResponse getResponse = channel.basicGet("queue.default.ex", true);

      System.out.println("信息输出:" + new String(getResponse.getBody()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      channel.close();
      connection.close();
    }
  }
}
```



在启动生产者消息后，便可以看到消息被发送到了队列。 

![image-20230818142123289](img\image-20230818142123289.png)

在启动消费者后。便可看到消息扑火队成功的消费。控制台输出也可以看到：

```sh
信息输出:hello nullnull
```



消息的推拉：

实现RabbitMQ的消费者有两种模式：推模式(push)和拉模式(pull)

推模式，需要与服务建立长连接，不能关闭连接。推模式使用basicConsume

拉模式，按需拉取一次，拉取完成后是可以关闭连接。而拉械，则使用basicGet。



#### 4.3.4 路由模式

![](img\direct-exchange.png)

使用`direct`类型的Exchange,发N条消息并使用不同的routingKey,消费者定义队列并将队列`routingKey`、Exchange绑定。此时使用`direct`模式Exchange必须要`routingKey`完成匹配的情况下消息才会转发到对应的队列中被消费。

样例使用日志分发为样例。即按日志不同的级别，分发到不同的队列。每个队列只处理自己的对应的级别日志。

创建生产者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class Product {

  private static final String[] LOG_LEVEL = {"ERROR", "WARN", "INFO"};

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明交换机，交换器和消息队列的绑定不需要在这里处理。
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        false,
        // 属性
        null);

    for (int i = 0; i < 30; i++) {
      String level = LOG_LEVEL[ThreadLocalRandom.current().nextInt(0, LOG_LEVEL.length)];
      String dataMsg = "[" + level + "] 消息发送 :" + i;
      // 发送消息
      channel.basicPublish("ex.routing", level, null, dataMsg.getBytes(StandardCharsets.UTF_8));
    }
  }
}
```



创建ERROR的消费者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class ErrorConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明队列并绑定
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        false,
        // 属性
        null);

    // 此也可以声明为临时队列，但是如果消息很重要，不要声明临时队列。
    channel.queueDeclare(
        "log.error",
        // 永久
        false,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    //消费者享有绑定到交换器的权力。
    channel.queueBind("log.error", "ex.routing", "ERROR");

    // 通过chanel消费消息
    channel.basicConsume(
        "log.error",
        (consumerTag, message) -> {
          System.out.println("ERROR收到的消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
        },
        consumerTag -> {});
  }
}
```

创建INFO级的消费者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class InfoConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明队列并绑定
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        true,
        // 属性
        null);

    // 此也可以声明为临时队列，但是如果消息很重要，不要声明临时队列。
    channel.queueDeclare(
        "log.info",
        // 永久
        false,
        // 排他
        false,
        // 自动删除
        false,
        // 属性
        null);

    //消费者享有绑定到交换器的权力。
    channel.queueBind("log.info", "ex.routing", "INFO");

    // 通过chanel消费消息
    channel.basicConsume(
        "log.info",
        (consumerTag, message) -> {
          System.out.println("INFO收到的消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
        },
        consumerTag -> {});
  }
}
```

创建WARN级别的消息者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class WarnConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明队列并绑定
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        false,
        // 属性
        null);

    // 此也可以声明为临时队列，但是如果消息很重要，不要声明临时队列。
    channel.queueDeclare(
        "log.warn",
        // 永久
        false,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    //消费者享有绑定到交换器的权力。
    channel.queueBind("log.warn", "ex.routing", "WARN");

    // 通过chanel消费消息
    channel.basicConsume(
        "log.warn",
        (consumerTag, message) -> {
          System.out.println("warn收到的消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
        },
        consumerTag -> {});
  }
}
```

首先启动三个消费者：

查看队列及交换机情况

```
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ ex.routing         │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ log.info         │ queue            │ log.info    │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ log.warn         │ queue            │ log.warn    │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ log.error        │ queue            │ log.error   │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ ex.routing  │ exchange    │ log.error        │ queue            │ ERROR       │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ ex.routing  │ exchange    │ log.info         │ queue            │ INFO        │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ ex.routing  │ exchange    │ log.warn         │ queue            │ WARN        │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os ~]# 
```

可以发现，交换器ex.routing 绑定了三个队列`log.error`、`log.info `、`log.warn`并指定了路由键。

启动消费者，查看消息通否被正常消费。

ERROR的消费者控制台输出	

```java
ERROR收到的消息:[ERROR] 消息发送 :1
ERROR收到的消息:[ERROR] 消息发送 :2
ERROR收到的消息:[ERROR] 消息发送 :6
ERROR收到的消息:[ERROR] 消息发送 :8
ERROR收到的消息:[ERROR] 消息发送 :9
ERROR收到的消息:[ERROR] 消息发送 :11
ERROR收到的消息:[ERROR] 消息发送 :15
ERROR收到的消息:[ERROR] 消息发送 :16
ERROR收到的消息:[ERROR] 消息发送 :19
ERROR收到的消息:[ERROR] 消息发送 :20
ERROR收到的消息:[ERROR] 消息发送 :21
ERROR收到的消息:[ERROR] 消息发送 :23
ERROR收到的消息:[ERROR] 消息发送 :24
ERROR收到的消息:[ERROR] 消息发送 :27
ERROR收到的消息:[ERROR] 消息发送 :28
```

INFO的消费者控制台输出：

```java
INFO收到的消息:[INFO] 消息发送 :0
INFO收到的消息:[INFO] 消息发送 :3
INFO收到的消息:[INFO] 消息发送 :4
INFO收到的消息:[INFO] 消息发送 :13
INFO收到的消息:[INFO] 消息发送 :14
INFO收到的消息:[INFO] 消息发送 :22
INFO收到的消息:[INFO] 消息发送 :25
```

WARN的消费都控制台输出:

```java
warn收到的消息:[WARN] 消息发送 :5
warn收到的消息:[WARN] 消息发送 :7
warn收到的消息:[WARN] 消息发送 :10
warn收到的消息:[WARN] 消息发送 :12
warn收到的消息:[WARN] 消息发送 :17
warn收到的消息:[WARN] 消息发送 :18
warn收到的消息:[WARN] 消息发送 :26
warn收到的消息:[WARN] 消息发送 :29
```



至此，验证已经完成。



#### 4.3.5 主题模式



使用topic类型的交换器，队列绑定到交换器、bingingKey时使用通配符，交换器将消息路由转发到具体队列时，会根据消息routingKey模糊匹配，比较灵活。

在Direct类型的交换器做到了根据日志级别的不同，将消息发送给了不同队列的。

这里再加入一个需求，不仅想根据日志级别进行划分，还想根据日志的来源分日志，如何来做呢？

使用topic类型的交换器, routingKey就不能随便写了，它必须是点分单词，单词可以随便写，一般按消息的特征，该点分单词字符串最长255字节。

bindingKey也必须是这种形式。top类型的交换器背后原理跟direct类型类似只要队列的bingingkey的值与消息的routingKey的匹配，队列就可以收到该消息。有两个不同

1. \* (star)匹配一个单词。
2. \# 匹配0到多个单词。

![](img\python-five.png)

上报的数据的RoutingKey，格式如下

地区.业务.日志级别  如shanghai.busi.INFO 、 hangzhou.line.ERROR

**生产者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class Product {

  private static final String[] ADDRESS_ARRAYS = {"shanghai", "suzhou", "hangzhou"};

  private static final String[] BUSI_NAMES = {"product", "user", "schedule"};

  private static final String[] LOG_LEVEL = {"ERROR", "WARN", "INFO"};

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "ex.busi.topic",
        BuiltinExchangeType.TOPIC,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    for (int i = 0; i < 50; i++) {

      String level = LOG_LEVEL[ThreadLocalRandom.current().nextInt(0, LOG_LEVEL.length)];
      String busiName = BUSI_NAMES[ThreadLocalRandom.current().nextInt(0, BUSI_NAMES.length)];
      String address =
          ADDRESS_ARRAYS[ThreadLocalRandom.current().nextInt(0, ADDRESS_ARRAYS.length)];
      String routingKey = address + "." + busiName + "." + level;

      String pushMsg = "地址[" + address + "],业务[" + busiName + "],级别[" + level + "],消息:" + i;

      channel.basicPublish(
          "ex.busi.topic", routingKey, null, pushMsg.getBytes(StandardCharsets.UTF_8));
    }
  }
}

```



**上海的消费者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 上海地区的消费都，获取所有的上海信息
 */
public class ShangHaiConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "ex.busi.topic",
        BuiltinExchangeType.TOPIC,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    // 定义队列
    channel.queueDeclare(
        "shanghai.all.log",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("shanghai.all.log", "ex.busi.topic", "shanghai.#", null);

    channel.basicConsume(
        "shanghai.all.log",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("shanghai consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}
```



**所有错误日志的消费者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class ErrorLogConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "ex.busi.topic",
        BuiltinExchangeType.TOPIC,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    // 定义队列
    channel.queueDeclare(
        "log.all.error",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("log.all.error", "ex.busi.topic", "#.ERROR", null);

    channel.basicConsume(
        "log.all.error",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("错误日志 consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}

```

**苏州用户的消费者**

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class SuZhouUserConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "ex.busi.topic",
        BuiltinExchangeType.TOPIC,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    // 定义队列
    channel.queueDeclare(
        "suzhou.user.consumer",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("suzhou.user.consumer", "ex.busi.topic", "suzhou.user.*", null);

    channel.basicConsume(
        "suzhou.user.consumer",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("suzhou consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}
```



首先启动三个消费者，查看队列和交换器的信息

```
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ ex.busi.topic      │ topic   │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ ex.routing         │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌───────────────┬─────────────┬──────────────────────┬──────────────────┬──────────────────────┬───────────┐
│ source_name   │ source_kind │ destination_name     │ destination_kind │ routing_key          │ arguments │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│               │ exchange    │ suzhou.user.consumer │ queue            │ suzhou.user.consumer │           │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│               │ exchange    │ shanghai.all.log     │ queue            │ shanghai.all.log     │           │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│               │ exchange    │ log.all.error        │ queue            │ log.all.error        │           │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│ ex.busi.topic │ exchange    │ log.all.error        │ queue            │ #.ERROR              │           │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│ ex.busi.topic │ exchange    │ shanghai.all.log     │ queue            │ shanghai.#           │           │
├───────────────┼─────────────┼──────────────────────┼──────────────────┼──────────────────────┼───────────┤
│ ex.busi.topic │ exchange    │ suzhou.user.consumer │ queue            │ suzhou.user.*        │           │
└───────────────┴─────────────┴──────────────────────┴──────────────────┴──────────────────────┴───────────┘
[root@nullnull-os ~]# 
```

观察可以发现，此队列与消息的绑定已经成功。接下使用生产者发送消息。观察控制台输出:

错误日志消费者

```java
错误日志 consumer 收到数据:地址[suzhou],业务[schedule],级别[ERROR],消息:6
错误日志 consumer 收到数据:地址[suzhou],业务[product],级别[ERROR],消息:8
错误日志 consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:10
错误日志 consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:12
错误日志 consumer 收到数据:地址[suzhou],业务[schedule],级别[ERROR],消息:15
错误日志 consumer 收到数据:地址[hangzhou],业务[user],级别[ERROR],消息:16
错误日志 consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:17
错误日志 consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:18
错误日志 consumer 收到数据:地址[hangzhou],业务[user],级别[ERROR],消息:21
错误日志 consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:22
错误日志 consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:24
错误日志 consumer 收到数据:地址[hangzhou],业务[product],级别[ERROR],消息:28
错误日志 consumer 收到数据:地址[suzhou],业务[schedule],级别[ERROR],消息:33
错误日志 consumer 收到数据:地址[hangzhou],业务[schedule],级别[ERROR],消息:39
错误日志 consumer 收到数据:地址[suzhou],业务[user],级别[ERROR],消息:40
错误日志 consumer 收到数据:地址[suzhou],业务[user],级别[ERROR],消息:43
错误日志 consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:46
```

上海地区的消费者

```java
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[WARN],消息:0
shanghai consumer 收到数据:地址[shanghai],业务[user],级别[INFO],消息:1
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[INFO],消息:2
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[WARN],消息:5
shanghai consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:10
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:12
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:17
shanghai consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:18
shanghai consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:22
shanghai consumer 收到数据:地址[shanghai],业务[product],级别[ERROR],消息:24
shanghai consumer 收到数据:地址[shanghai],业务[user],级别[INFO],消息:32
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[INFO],消息:35
shanghai consumer 收到数据:地址[shanghai],业务[product],级别[INFO],消息:38
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[WARN],消息:41
shanghai consumer 收到数据:地址[shanghai],业务[schedule],级别[ERROR],消息:46
shanghai consumer 收到数据:地址[shanghai],业务[user],级别[INFO],消息:48
```

苏州用户的消费者

```java
suzhou consumer 收到数据:地址[suzhou],业务[user],级别[WARN],消息:37
suzhou consumer 收到数据:地址[suzhou],业务[user],级别[ERROR],消息:40
suzhou consumer 收到数据:地址[suzhou],业务[user],级别[ERROR],消息:43
suzhou consumer 收到数据:地址[suzhou],业务[user],级别[WARN],消息:45
```



至此topic模式操作成功。

## 5. Spring整合RabbitMQ

Spring-amqp是对AMQP的一些概念的一些抽象，Spring-rabbit是对RabbitMQ操作的封装实现。

主要有几个核心类`RabbitAdmin`、`RabbitTemplate`、`SimpleMessageListenerContainer`等

`RabbitAdmin`类完成对Exchange、Queue、Binding的操作，在容器中管理 了`RabbitAdmin`类的时候，可以对Exchange、Queue、Binding进行自动声明。

`RabbitTemplate`类是发送和接收消息的工具类。

`SimpleMessageListenerContainer`是消费消息的容器。

目前一些比较新的项目会使用基于注解的方式，而比较老的一些项目可能还是基于配制文件的方式。

此处使用的Spring依赖为:

```xml
            <dependency>
                <groupId>org.springframework.amqp</groupId>
                <artifactId>spring-rabbit</artifactId>
                <version>2.2.7.RELEASE</version>
            </dependency>
```





### 5.1 基于配制文件的整合。

#### 5.1.1 消息的生产者

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Product {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");


        RabbitTemplate template = context.getBean(RabbitTemplate.class);


        MessagePropertiesBuilder propertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertiesBuilder.setContentEncoding("gbk");
        propertiesBuilder.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);


        Message msg = MessageBuilder.withBody("hello world".getBytes("gbk"))
                .andProperties(propertiesBuilder.build()).build();

        template.convertAndSend("ex.direct", "routing.q1", msg);


        context.close();
    }
}

```

**spring-rabbit.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/rabbit
    http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">

    <!--配制连接工厂-->
    <rabbit:connection-factory id="connectFactory"
                               host="node1" virtual-host="/"
                               username="root" password="123456"
                               port="5672"
    ></rabbit:connection-factory>


    <!--用于自动向RabbitMQ声明队列、交换器、绑定 等操作工具类-->
    <rabbit:admin id="rabbitAdmin" connection-factory="connectFactory"></rabbit:admin>


    <!--用于简化操作的模板类-->
    <rabbit:template connection-factory="connectFactory" id="rabbitTemplate" />


    <!--声明队列队列-->
    <rabbit:queue id="msg1" name="queue.msg" durable="false" exclusive="false" auto-delete="false" ></rabbit:queue>

    <!--声明交换器-->
    <rabbit:direct-exchange name="ex.direct" durable="false" auto-delete="false" id="directExchange" >
        <rabbit:bindings>
            <!--key表示绑定键-->
            <!--queue表示将交换器绑定到哪个消息队列,使用队列换id，不要使用Bean的name-->
            <!--exchange表示交接交换器绑定到哪个交换器。-->
            <rabbit:binding queue="msg1" key="routing.q1" ></rabbit:binding>
        </rabbit:bindings>
    </rabbit:direct-exchange>


</beans>
```



运行生产者的代码，便可查看数据已经发送成功

```sh
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ ex.busi.topic      │ topic   │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│ ex.direct          │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ ex.routing         │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ queue.msg        │ queue            │ queue.msg   │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ ex.direct   │ exchange    │ queue.msg        │ queue            │ routing.q1  │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os ~]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────┬──────────┐
│ name      │ messages │
├───────────┼──────────┤
│ queue.msg │ 1        │
└───────────┴──────────┘
[root@nullnull-os ~]# 
```

可以观察到数据已经成功的发送了。



遇到的问题：

```java
Exception in thread "main" org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.amqp.rabbit.config.BindingFactoryBean#0': Cannot resolve reference to bean 'queue.msg' while setting bean property 'destinationQueue'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'queue.msg' available
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:342)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:113)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1699)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1444)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:594)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:517)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:323)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:226)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:321)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:876)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:878)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:550)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:144)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:85)
	at com.nullnull.learn.Product.main(Product.java:18)
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'queue.msg' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:814)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1282)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:297)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:330)
	... 15 more

```

问题原因：

```xml
    <rabbit:direct-exchange name="ex.direct" durable="false" auto-delete="false" id="directExchange" >
        <rabbit:bindings>
            <rabbit:binding queue="queue.msg" key="routing.q1" ></rabbit:binding>
        </rabbit:bindings>
    </rabbit:direct-exchange>
```

此处要配制的是队列的id，而不是队列的名称。

修改后:

```xml
 <!--声明交换器-->
    <rabbit:direct-exchange name="ex.direct" durable="false" auto-delete="false" id="directExchange" >
        <rabbit:bindings>
            <!--key表示绑定键-->
            <!--queue表示将交换器绑定到哪个消息队列,使用队列换id，不要使用Bean的name-->
            <!--exchange表示交接交换器绑定到哪个交换器。-->
            <rabbit:binding queue="msg1" key="routing.q1" ></rabbit:binding>
        </rabbit:bindings>
    </rabbit:direct-exchange>
```



#### 5.1.2 拉消息的消费者

```java

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerGet {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");

        RabbitTemplate template = context.getBean(RabbitTemplate.class);


        Message receive = template.receive("queue.msg");

        //报文头中的消息编码
        String encoding = receive.getMessageProperties().getContentEncoding();

        System.out.println("收到的消息:" + new String(receive.getBody(), encoding));


        context.close();
    }
}

```

spring-rabbit.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/rabbit
    http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">

    <!--配制连接工厂-->
    <rabbit:connection-factory id="connectFactory"
                               host="node1" virtual-host="/"
                               username="root" password="123456"
                               port="5672"
    ></rabbit:connection-factory>


    <!--用于自动向RabbitMQ声明队列、交换器、绑定 等操作工具类-->
    <rabbit:admin id="rabbitAdmin" connection-factory="connectFactory"></rabbit:admin>


    <!--用于简化操作的模板类-->
    <rabbit:template connection-factory="connectFactory" id="rabbitTemplate"/>


    <!--声明队列队列-->
    <rabbit:queue id="msg1" name="queue.msg" durable="false" exclusive="false" auto-delete="false"></rabbit:queue>


</beans>
```



当启动消费者后，便可获取到发送至队列的消息

```sh
收到的消息:hello world
```

检查队列的消息的情况：

```
[root@nullnull-os ~]# rabbitmqctl list_queues  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────┬──────────┐
│ name      │ messages │
├───────────┼──────────┤
│ queue.msg │ 0        │
└───────────┴──────────┘
```

经过检查确认，发现消息已经被消费了。

至此拉模式的消费者完成。



#### 5.1.3 推模式的消费者

在推模式中使用可以两种实现：

1. 使用ChannelAwareMessageListener.

除消息外，还提供了Channel这个对象，通过channel可以有更大的灵活性。

```java
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

public class MesListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        
    }
}

```



2. 使用MessageListener

基本的消息的临时。普通的场景基本够用。

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MesListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}

```



此处以ChannelAwareMessageListener为样例：

```java
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

public class MesListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String encoding = message.getMessageProperties().getContentEncoding();
        System.out.println("收到的消息是：" + new String(message.getBody(), encoding));
    }
}
```

spring-rabbit.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/rabbit
    http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">

    <!--配制连接工厂-->
    <rabbit:connection-factory id="connectFactory"
                               host="node1" virtual-host="/"
                               username="root" password="123456"
                               port="5672"
    ></rabbit:connection-factory>


    <!--用于自动向RabbitMQ声明队列、交换器、绑定 等操作工具类-->
    <rabbit:admin id="rabbitAdmin" connection-factory="connectFactory"></rabbit:admin>


    <!--用于简化操作的模板类-->
    <rabbit:template connection-factory="connectFactory" id="rabbitTemplate"/>


    <!--声明队列队列-->
    <rabbit:queue id="msg1" name="queue.msg" durable="false" exclusive="false" auto-delete="false"></rabbit:queue>


    <!--配制鉴别器对象-->
    <bean id="msgListener" class="com.nullnull.learn.MesListener"/>

    <!--配制监听器-->
    <rabbit:listener-container connection-factory="connectFactory">
        <rabbit:listener ref="msgListener" queues="msg1"></rabbit:listener>
    </rabbit:listener-container>


</beans>
```

容器启动类

```java
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ListenerApplication {
    public static void main(String[] args) {
        //启动Spring容器
        new ClassPathXmlApplicationContext("spring-rabbit.xml");
    }
}

```



首先启动消费者。这样监听者就会处于监听状态。

再启动生产者，向队列中发送消息。

再观察消息者，便能看到消费者队列中已经收到了发送的消息。

```java
收到的消息是：hello world
```



在推模式中消息的即时性比拉模式会好。



### 5.2 基于注解的整合

#### **5.2.1 消息的生产者**

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.nio.charset.StandardCharsets;

public class ProducterApplication {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        MessageProperties msgBuild = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding(StandardCharsets.UTF_8.name())
                .setHeader("test.header", "test.value")
                .build();

        Message msg = MessageBuilder.withBody("你好 RabbitMQ!".getBytes(StandardCharsets.UTF_8))
                .andProperties(msgBuild)
                .build();

        template.send("ex.anno.fanout", "routing.anno", msg);

        context.close();
    }

}

```

**RabbitConfig**

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import java.net.URI;

@Configurable
public class RabbitConfig {

    /**
     * 连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory getConnectionFactory() {
        URI uri = URI.create("amqp://root:123456@node1:5672/%2f");
        ConnectionFactory factory = new CachingConnectionFactory(uri);
        return factory;
    }

    /**
     * RabbitTemplate
     */
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        return rabbitTemplate;
    }


    /**
     * RabbitAdmin
     */
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }

    /**
     * Queue
     */
    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.nonDurable("queue.anno")
                //是否排外，即是否只有当前这个连接才能看到。
                //.exclusive()
                //是否自动删除
                //.autoDelete()
                .build();

        return queue;
    }

    /**
     * Exchange
     */
    @Bean
    public Exchange exchange() {
        Exchange exchange = new FanoutExchange("ex.anno.fanout", false, false, null);
        return exchange;
    }

    /**
     * Binding
     */
    @Bean
    @Autowired
    public Binding binding(Queue queue, Exchange exchange) {
        //创建一个不指定参数的绑定
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("routing.anno").noargs();
        return binding;
    }
}
```

提示：

>ConnectionFactory有三个实现
>
>CachingConnectionFactory 基于channel的缓存模式  最常用是这个。
>
>LocalizedQueueConnectionFactory 直接连接某个节点的方式。如果是集群，此种不太适合。
>
>SimpleRoutingConnectionFactory 在当前的连接工厂中按查找的KEY获取连接工厂。





运行消息的生产者，查看消息发送信息

```java
[root@nullnull-os ~]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ ex.anno.fanout     │ fanout  │
├────────────────────┼─────────┤
│ ex.busi.topic      │ topic   │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│ ex.direct          │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ ex.routing         │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os ~]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌────────────────┬─────────────┬──────────────────┬──────────────────┬──────────────┬───────────┐
│ source_name    │ source_kind │ destination_name │ destination_kind │ routing_key  │ arguments │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│                │ exchange    │ queue.msg        │ queue            │ queue.msg    │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│                │ exchange    │ queue.anno       │ queue            │ queue.anno   │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│ ex.anno.fanout │ exchange    │ queue.anno       │ queue            │ routing.anno │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│ ex.direct      │ exchange    │ queue.msg        │ queue            │ routing.q1   │           │
└────────────────┴─────────────┴──────────────────┴──────────────────┴──────────────┴───────────┘
[root@nullnull-os ~]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ queue.msg  │ 0        │
├────────────┼──────────┤
│ queue.anno │ 1        │
└────────────┴──────────┘
[root@nullnull-os ~]# 
```

通过检查发现，消息已经成功的发送到了队列





#### **5.2.2 使用拉模式获取消息**

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ConsumerGetApplication {

    public static void main(String[] args) throws Exception {
        //从指定类加载配制信息
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
        RabbitTemplate rabbit = context.getBean(RabbitTemplate.class);

        Message receive = rabbit.receive("queue.anno");
        String encoding = receive.getMessageProperties().getContentEncoding();
        System.out.println("消息信息：" + new String(receive.getBody(), encoding));

        context.close();
    }

}

```



**RabbitConfig的配制**

```java
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import java.net.URI;

@Configurable
public class RabbitConfig {

    /**
     * 连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory getConnectionFactory() {
        URI uri = URI.create("amqp://root:123456@node1:5672/%2f");
        ConnectionFactory factory = new CachingConnectionFactory(uri);
        return factory;
    }

    /**
     * RabbitTemplate
     */
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        return rabbitTemplate;
    }


    /**
     * RabbitAdmin
     */
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }

    /**
     * Queue
     */
    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.nonDurable("queue.anno")
                //是否排外，即是否只有当前这个连接才能看到。
                //.exclusive()
                //是否自动删除
                //.autoDelete()
                .build();

        return queue;
    }
}
```



运行主程序,检查控制台的输出。

```
消息信息：你好 RabbitMQ!
```

至此使用拉模式，已经成功的获取队列中的数据。



#### **5.2.3 使用推模式获取数据 **

消费者处理的代码

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {


    /**
     * com.rabbitmq.client.Channel to get access to the Channel channel对象
     * org.springframework.amqp.core.Message  message对象，可以直接操作原生的AMQP消息
     * org.springframework.messaging.Message to use the messaging abstraction counterpart
     *
     * @Payload-annotated 注解方法参数，该参数的值就是消息体。   method arguments including the support of validation
     * @Header-annotated 注解方法参数，访问指定的消息头字段的值。 method arguments to extract a specific header value, including standard AMQP headers defined by AmqpHeaders
     * @Headers-annotated 该注解的参数获取该消息的消息头的所有字段，参数集合类型对应的MAP argument that must also be assignable to java.util.Map for getting access to all headers.
     * MessageHeaders 参数类型，访问所有消息头字段  arguments for getting access to all headers.
     * MessageHeaderAccessor or AmqpMessageHeaderAccessor  访问所有消息头字段。
     * <p>
     * 消息监听
     */
    @RabbitListener(queues = "queue.anno")
    public void whenMessageCome(Message msg) throws Exception {
        String encoding = msg.getMessageProperties().getContentEncoding();
        System.out.println("收到的消息:" + new String(msg.getBody(), encoding));
    }


    /**
    // * 使用payload进行消费
    // *
    // * 不可同时存在相同的队列被两个监听
    // *
    // * @param data
    // */
    //@RabbitListener(queues = "queue.anno")
    //public void whenMessageConsumer(@Payload String data) {
    //    System.out.println("收到的消息:" + data);
    //}

}

```

此处存在两种方式，一种是接收Message作为参数，还有一种是使用@Payload接收内容作为参数

配制处理

```java
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.amqp.core.Queue;
import java.net.URI;

@EnableRabbit
//@ComponentScan("com.nullnull.learn")
@ComponentScan
@Configurable //xml中也可以使用<rabbit:annotation-driven/> 启用@RabbitListener注解
public class RabbitConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        URI uriInfo = URI.create("amqp://root:123456@node1:5672/%2f");
        return new CachingConnectionFactory(uriInfo);
    }


    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }


    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.anno").build();
    }


    /**
     * RabbitListener的容器管理对象
     * <p>
     * 使用监听器监听推送过来的消息。在一个应用中可能会有多个监听器。这些监听器是需要一个工厂管理起来的。
     *
     * @return
     */
    @Bean("rabbitListenerContainerFactory")
    @Autowired
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();

        //要管理容器就得有连接
        containerFactory.setConnectionFactory(connectFactory);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //containerFactory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发的消费者，即可以同时存在10个消费都消费消息。
        containerFactory.setConcurrentConsumers(10);
        //设置并发的最大消费者。
        containerFactory.setMaxConcurrentConsumers(15);
        //按照批次处理消息消息。
        containerFactory.setBatchSize(10);
        return containerFactory;
    }

}

```



启动类

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsumerListenerApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RabbitConfig.class);
    }

}

```



再启动生产者

对生产者作一点改造，让其发送多条

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.nio.charset.StandardCharsets;

public class ProducterApplication {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        MessageProperties msgBuild = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding(StandardCharsets.UTF_8.name())
                .setHeader("test.header", "test.value")
                .build();

        for (int i = 0; i < 20; i++) {
            Message msg = MessageBuilder.withBody(("你好 RabbitMQ! id :" + i).getBytes(StandardCharsets.UTF_8))
                    .andProperties(msgBuild)
                    .build();

            template.send("ex.anno.fanout", "routing.anno", msg);
        }

        context.close();
    }

}

```



客户端接收,查看控制台

```java
收到的消息:你好 RabbitMQ! id :4
收到的消息:你好 RabbitMQ! id :9
收到的消息:你好 RabbitMQ! id :8
收到的消息:你好 RabbitMQ! id :7
收到的消息:你好 RabbitMQ! id :6
收到的消息:你好 RabbitMQ! id :2
收到的消息:你好 RabbitMQ! id :3
收到的消息:你好 RabbitMQ! id :5
收到的消息:你好 RabbitMQ! id :14
收到的消息:你好 RabbitMQ! id :17
收到的消息:你好 RabbitMQ! id :1
收到的消息:你好 RabbitMQ! id :0
收到的消息:你好 RabbitMQ! id :13
收到的消息:你好 RabbitMQ! id :15
收到的消息:你好 RabbitMQ! id :12
收到的消息:你好 RabbitMQ! id :16
收到的消息:你好 RabbitMQ! id :18
收到的消息:你好 RabbitMQ! id :19
收到的消息:你好 RabbitMQ! id :11
收到的消息:你好 RabbitMQ! id :10
```

通过观察发现，此处接收的顺序与并非发送的顺序进行的接收，这是因为批量以及并发的控制在这里起的作用，如果要按顺序，去接批量及并发则就是按顺序接收。





## 6. SpringBoot整合RabbitMQ

引入SpringBoot的父类

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.8.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```





maven的导入

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit-test</artifactId>
            <scope>test</scope>
        </dependency>
```





### 6.1 生产者

生产者的controller

```java
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.nio.charset.StandardCharsets;

@Controller
public class SenderController {

    @Autowired
    private AmqpTemplate template;

    @GetMapping("/send/{msg}")
    @ResponseBody
    public String sendMsg(@PathVariable String msg) {
        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setContentEncoding(StandardCharsets.UTF_8.name())
                .setHeader("key", "value").build();

        Message msgData = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8))
                .andProperties(properties)
                .build();

        template.send("boot.ex", "boot.rk", msgData);

        return "successMsg";
    }

}
```

生产者的配制 

```java
package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujun
 * @since 2023/8/20
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("boot.queue").build();
    }

    @Bean
    public Exchange exchange() {
        Exchange exchange = ExchangeBuilder.directExchange("boot.ex").build();
        return exchange;
    }

    @Bean
    public Binding binding() {
        return new Binding("boot.queue", Binding.DestinationType.QUEUE, "boot.ex", "boot.rk", null);
    }
}
```

主启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }
}
```

启动SpringBoot工程，即运行RabbitApplication中的main函数

在浏览器中输入测试地址:http://127.0.0.1:8080/send/testmsg 

页面将收到响应: successMsg



检查队列的情况

```sh
root@nullnull-os mellanox]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ ex.anno.fanout     │ fanout  │
├────────────────────┼─────────┤
│ ex.busi.topic      │ topic   │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│ ex.direct          │ direct  │
├────────────────────┼─────────┤
│ boot.ex            │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ ex.routing         │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os mellanox]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ queue.msg  │ 0        │
├────────────┼──────────┤
│ queue.anno │ 0        │
├────────────┼──────────┤
│ boot.queue │ 1        │
└────────────┴──────────┘
[root@nullnull-os mellanox]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌────────────────┬─────────────┬──────────────────┬──────────────────┬──────────────┬───────────┐
│ source_name    │ source_kind │ destination_name │ destination_kind │ routing_key  │ arguments │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│                │ exchange    │ queue.msg        │ queue            │ queue.msg    │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│                │ exchange    │ queue.anno       │ queue            │ queue.anno   │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│                │ exchange    │ boot.queue       │ queue            │ boot.queue   │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│ boot.ex        │ exchange    │ boot.queue       │ queue            │ boot.rk      │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│ ex.anno.fanout │ exchange    │ queue.anno       │ queue            │ routing.anno │           │
├────────────────┼─────────────┼──────────────────┼──────────────────┼──────────────┼───────────┤
│ ex.direct      │ exchange    │ queue.msg        │ queue            │ routing.q1   │           │
└────────────────┴─────────────┴──────────────────┴──────────────────┴──────────────┴───────────┘
[root@nullnull-os mellanox]# 
```

观察发现，数据已经成功的发送至了RabbitMQ中了，至此生产者编写完成。



### 6.2 消费者

消费队列的配制文件 

```yaml
spring:
  application:
    name: springboot_rabbitmq

  rabbitmq:
    host: node1
    virtual-host: /
    username: root
    password: 123456
    port: 5672
```





队列配制信息

```java
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("boot.queue").build();
    }

}

```





监听器的代码

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {


    //@RabbitListener(queues = "boot.queue")
    //public void getMsg(Message msg) {
    //    String headValue = msg.getMessageProperties().getHeader("key");
    //    String getValue = new String(msg.getBody(), StandardCharsets.UTF_8);
    //    System.out.println("接收到的消息头:" + headValue);
    //    System.out.println("接收到的消息:" + getValue);
    //}


    @RabbitListener(queues = "boot.queue")
    public void getMsgPay(@Payload String msg, @Header("key") String keyValue) {
        System.out.println("接收到的消息头:" + keyValue);
        System.out.println("接收到的消息:" + msg);
    }

}

```



启动消费都后，观察控制台，便会有如下的输出:

```sh
2023-08-20 15:17:22.680  INFO 21720 --- [           main] o.s.amqp.rabbit.core.RabbitAdmin         : Auto-declaring a non-durable, auto-delete, or exclusive Queue (boot.queue) durable:false, auto-delete:false, exclusive:false. It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.
接收到的消息头value
接收到的消息:testmsg321
```

至此，SpringBoot消费都也已经成功的集成。







## 7. RabbitMQ的消息可靠性



### 7.1 消息的可靠性介绍

在支付的场景中，消息的可靠性是一个非常重要的保证。支付平台必须保证数据的正确性、保证数据并发安全性，保证数据最终一致性。

可能通过如下几种方式保证数据的一致性：

1. 分布式锁

​	这个比较容易理解，就是在操作某条数据的时，先锁定，可以用Redis或者zeekeeper等常用框架来实现。比如我们在修改账单时，先锁定账单，如果该账单有并发操作，后面的操作只能等待上一个操作锁释放后再执行。

2. 消息队列

   消息队列是为了保证最终一致性，我们需要确保消息队列有ack机制，客户端收到消息后并消费处理完成后，客户端发送ack消息给消息中间件，如果消息中件件超过指定时间还没有收到ack消息，则定时云重发消息。

   优点：异常、高并发

   缺点：有一定的延时、数据弱一致性，并且必须能够确保该业务操作肯定能够成功，不可能失败。

   

   可以通过以下几个方面来保证消息的可靠性：

   >1. 客户端代码中的异常捕获，包括生产者和消费者。
   >2. AMQP/RabbitMQ的事务机制。
   >3. 发送端确认机制
   >4. 消息持久化机制
   >5. Broker端的高可用集群。
   >6. 消费者确认机制
   >7. 消费端限流
   >8. 消息幂等性

   

### 7.2 异常捕捉机制

 >先执行业务操作，业务操作成功后执行消息发送，消息发送过程中通过try-catch方式捕捉异常，在异常处理的代码块中回滚业务或者执行重试操作等，这是一种最大努力确保的方式，并无法保证100%绝对可靠，因为这里没有异常并不代表消息就一定投递成功。



```java
        boolean result = doBiz();
        if(result)
        {
            try {
                sendMsg();
            } catch (Exception e) {
                //retrySend();
                //delaySend();
                rollbackBiz();
            }
        }
```

在SpringBoot的项目中可以通过Spring.rabbitmq.template.retry.enabled=true 配制开启发送端的重试，并配合其他参数。



### 7.3 AMQP/RabbitMQ的事务机制

没有捕获到异常并不能代表消息就一定投递成功了。

在RabbitMQ中，可以使用事务机制，一直到事务提交后都没有异常，确实说明消息是投递成功了，但是这种方式在性能方面的开销比较大，一般也不推荐使用。

```java
        try {
            //将channel设置为事务模式
            channel.txSelect();

            //发布消息到交换器、路由Key为空
            channel.basicPublish("ex.push", "", null, msg.getBytes(StandardCharsets.UTF_8));

            //提交事务、只有消息成功被Broker接收了才能提交成功
            channel.txCommit();
        } catch (IOException e) {
            //事务回滚
            channel.txRollback();
        }
```



### 7.4 发送端确认机制

​    RabbitMQ后来引入了一种轻量级的方式，叫发送方确认(publisher confirm)机制，生产者将信息设置成confirm（确认）模式，一旦信道进入了confirm模式，所有在该信道上面发送的消息都会被指派成一个唯一的ID（从1开始），一旦消息被投递到所有匹配的队列之后（如果消息和队列是持久化的，那么消息会在消息持久化后发出），RabbitMQ就会发送一个确认（Basic.Ack）给生产者（包含消息的唯一的ID），这样生产者就知道消息已经正确送达了。

![image-20230820234348357](img\image-20230820234348357.png)

RabbitMQ回传给生产者的确认消息中的devliveryTag字段包含了确认消息的序号，另外通过设置channel.basicAck方法中的mulitiple参数，表示到这个序号之前的所有消息是否都已经得到了处理了。生产都投递消息后并不需要一直阻塞着，可以继续投递下一条消息并通过回调方式处理ACK响应。如果RabbitMQ因数自身内部错误导致消息丢失等异常情况发生，就会响应一条nack（Basic.Nack)命令，生产者应用程序同样可以在回调方法中处理该nack命令。



首先导入maven依赖

```xml
           <dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>5.9.0</version>
            </dependency>
```



#### 7.4.1 单条带确认模式

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Product {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 开启发送方确认机制
    AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

    channel.exchangeDeclare("confirm.ex", BuiltinExchangeType.DIRECT);
    channel.queueDeclare("confirm.qe", false, false, false, null);
    channel.queueBind("confirm.qe", "confirm.ex", "confirm.rk");

    String pushMsg = "confirm 这是推送确认的消息";

    channel.basicPublish(
        "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));

    // 执行发送端确认机制
    try {
      channel.waitForConfirmsOrDie(5000);
      System.out.println("发送的消息被确认:" + pushMsg);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("消息被拒绝:" + pushMsg);
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("非Publisher confirm的通道上使用该方法");
    } catch (TimeoutException e) {
      e.printStackTrace();
      System.out.println("等待消息确认超时");
    }
  }
}
```

执行生产者的代码后，查看RabbitMQ中的是否到达:

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ confirm.ex         │ direct  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ confirm.qe       │ queue            │ confirm.qe  │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ confirm.ex  │ exchange    │ confirm.qe       │ queue            │ confirm.rk  │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ confirm.qe │ 1        │
└────────────┴──────────┘
[root@nullnull-os rabbitmq]# 
```

经过检查发现消息成功成功的送到，并且收到了服务端的确认:

```sh
发送的消息被确认:confirm 这是推送确认的消息
```



#### 7.4.2 批量确认模式

在以上的样例中，发送方在发送消息后，便进行入等待确认，这是一个同步阻塞的机制性能不是太好，接下来将使用批处理对此进行优化。

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ProductBatch {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 开启发送方确认机制
    AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

    channel.exchangeDeclare("confirm.ex", BuiltinExchangeType.DIRECT);
    channel.queueDeclare("confirm.qe", false, false, false, null);
    channel.queueBind("confirm.qe", "confirm.ex", "confirm.rk");

    // 批量处理大小，即每10条进行一次确认
    int batch = 10;
    // 确认的计数
    int confirmNum = 0;

    // 执行发送端确认机制
    try {
      for (int i = 0; i < 108; i++) {
        confirmNum++;
        String pushMsg = "confirm 这是推送确认的消息" + i;
        channel.basicPublish(
            "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));

        if (confirmNum == batch) {
          channel.waitForConfirmsOrDie(5000);
          System.out.println("批量发送的消息被确认:");
          confirmNum = 0;
        }
      }

      if (confirmNum > 0) {
        channel.waitForConfirmsOrDie(5000);
        System.out.println("剩余发送的消息被确认:");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("消息被拒绝:");
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("非Publisher confirm的通道上使用该方法");
    } catch (TimeoutException e) {
      e.printStackTrace();
      System.out.println("等待消息确认超时");
    }
  }
}

```



运行生产者，检查控制台输出:

```tex
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
批量发送的消息被确认:
剩余发送的消息被确认:
```

可以观察到，数据已经成功的发送。

再检查下队列的情况:

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ confirm.ex         │ direct  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ confirm.qe       │ queue            │ confirm.qe  │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ confirm.ex  │ exchange    │ confirm.qe       │ queue            │ confirm.rk  │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ confirm.qe │ 108      │
└────────────┴──────────┘
[root@nullnull-os rabbitmq]# 
```

检查服务端后发现，数据已经成功的发送至队列。



#### 7.4.3 异步回调模式

除了批量确认外，还可以使用回调模式，回调模式，与批量相比，则是异步模式，不再会有阻塞的问题。

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

public class ProductCallBack {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    try {
      // 开启发送方确认机制
      AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

      channel.exchangeDeclare("confirm.ex", BuiltinExchangeType.DIRECT);
      channel.queueDeclare("confirm.qe", false, false, false, null);
      channel.queueBind("confirm.qe", "confirm.ex", "confirm.rk");

      // 记录下发送与确认的消息
      ConcurrentNavigableMap<Long, String> ackConfirmMap = new ConcurrentSkipListMap<>();
      ConcurrentNavigableMap<Long, String> nackConfirmMap = new ConcurrentSkipListMap<>();

      // ack确认的信息
      ConfirmCallback ackCallback =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                // 获取已经被确认的Map集合
                ConcurrentNavigableMap<Long, String> headMap =
                    ackConfirmMap.headMap(deliveryTag, true);
                long threadId = Thread.currentThread().getId();
                System.out.println(
                    "【ack确认】线程ID："
                        + threadId
                        + "，批量：小于等于:"
                        + deliveryTag
                        + "的消息都被确认了，内容："
                        + headMap.keySet());
                headMap.clear();
              } else {
                long threadId = Thread.currentThread().getId();
                ackConfirmMap.remove(deliveryTag);
                System.out.println(
                    "【ack确认】线程ID：" + threadId + "，单条:" + deliveryTag + "对应的消息被确认,key:" + deliveryTag);
              }
            }
          };

      // 不确认的消息
      ConfirmCallback nackCallBack =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {

                ConcurrentNavigableMap<Long, String> headMap =
                    ackConfirmMap.headMap(deliveryTag, true);
                long threadId = Thread.currentThread().getId();
                System.out.println(
                    "【nack确认】线程ID："
                        + threadId
                        + "，批量：小于等于"
                        + deliveryTag
                        + "的消息都不被确认了,内容:"
                        + headMap.keySet());
                nackConfirmMap.putAll(headMap);
                headMap.clear();
              } else {
                long threadId = Thread.currentThread().getId();
                System.out.println("【nack确认】线程的ID：" + threadId + "，单条:" + deliveryTag + "对应的消息被确认");
                String value = ackConfirmMap.remove(deliveryTag);
                nackConfirmMap.put(deliveryTag, value);
              }
            }
          };

      channel.addConfirmListener(ackCallback, nackCallBack);

      // 执行数据发送
      try {
        for (int i = 0; i < 28; i++) {
          // 获取当前发送的序列号
          long nextPublishSeqNo = channel.getNextPublishSeqNo();
          String pushMsg = "序列号:" + nextPublishSeqNo + ":已经发送了消息信息:" + (i + 1);
          channel.basicPublish(
              "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));
          ackConfirmMap.put(nextPublishSeqNo, pushMsg);
          long threadId = Thread.currentThread().getId();
          System.out.println("【发送】线程的ID：" + threadId + "，序号：" + nextPublishSeqNo + "发送完毕");

          // 随机休眠
          Thread.sleep(ThreadLocalRandom.current().nextInt(0, 5));
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("消息被拒绝:");
      }

      Thread.sleep(5000);

      System.out.println("确认的消息:" + ackConfirmMap.size());
      System.out.println("未确认的消息:" + nackConfirmMap.size());

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      channel.close();
      connection.close();
    }
  }
}

```

运行生产者的代码后，观察控制台：

```sh
【发送】线程的ID：1，序号：1发送完毕
【发送】线程的ID：1，序号：2发送完毕
【发送】线程的ID：1，序号：3发送完毕
【ack确认】线程ID：20，单条:1对应的消息被确认,key:1
【发送】线程的ID：1，序号：4发送完毕
【发送】线程的ID：1，序号：5发送完毕
【发送】线程的ID：1，序号：6发送完毕
【ack确认】线程ID：20，单条:2对应的消息被确认,key:2
【ack确认】线程ID：20，单条:3对应的消息被确认,key:3
【发送】线程的ID：1，序号：7发送完毕
【发送】线程的ID：1，序号：8发送完毕
【发送】线程的ID：1，序号：9发送完毕
【ack确认】线程ID：20，批量：小于等于:6的消息都被确认了，内容：[4, 5, 6]
【发送】线程的ID：1，序号：10发送完毕
【发送】线程的ID：1，序号：11发送完毕
【ack确认】线程ID：20，批量：小于等于:8的消息都被确认了，内容：[7, 8]
【ack确认】线程ID：20，单条:9对应的消息被确认,key:9
【发送】线程的ID：1，序号：12发送完毕
【发送】线程的ID：1，序号：13发送完毕
【ack确认】线程ID：20，单条:10对应的消息被确认,key:10
【ack确认】线程ID：20，单条:11对应的消息被确认,key:11
【发送】线程的ID：1，序号：14发送完毕
【ack确认】线程ID：20，单条:12对应的消息被确认,key:12
【发送】线程的ID：1，序号：15发送完毕
【发送】线程的ID：1，序号：16发送完毕
【发送】线程的ID：1，序号：17发送完毕
【ack确认】线程ID：20，单条:13对应的消息被确认,key:13
【ack确认】线程ID：20，单条:14对应的消息被确认,key:14
【发送】线程的ID：1，序号：18发送完毕
【ack确认】线程ID：20，单条:15对应的消息被确认,key:15
【发送】线程的ID：1，序号：19发送完毕
【ack确认】线程ID：20，单条:16对应的消息被确认,key:16
【ack确认】线程ID：20，单条:17对应的消息被确认,key:17
【发送】线程的ID：1，序号：20发送完毕
【发送】线程的ID：1，序号：21发送完毕
【ack确认】线程ID：20，单条:18对应的消息被确认,key:18
【发送】线程的ID：1，序号：22发送完毕
【发送】线程的ID：1，序号：23发送完毕
【ack确认】线程ID：20，单条:19对应的消息被确认,key:19
【发送】线程的ID：1，序号：24发送完毕
【ack确认】线程ID：20，单条:20对应的消息被确认,key:20
【ack确认】线程ID：20，单条:21对应的消息被确认,key:21
【发送】线程的ID：1，序号：25发送完毕
【发送】线程的ID：1，序号：26发送完毕
【发送】线程的ID：1，序号：27发送完毕
【发送】线程的ID：1，序号：28发送完毕
【ack确认】线程ID：20，批量：小于等于:23的消息都被确认了，内容：[22, 23]
【ack确认】线程ID：20，单条:24对应的消息被确认,key:24
【ack确认】线程ID：20，批量：小于等于:26的消息都被确认了，内容：[25, 26]
【ack确认】线程ID：20，单条:27对应的消息被确认,key:27
【ack确认】线程ID：20，单条:28对应的消息被确认,key:28
确认的消息:0
未确认的消息:0


```

可以发现，生产者与确认机制，是完全异步的，生产者一个线程，而ack在另外的一个线程。可以并行处理。

最后再来检查下队列的信息：

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ confirm.ex         │ direct  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ confirm.qe       │ queue            │ confirm.qe  │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ confirm.ex  │ exchange    │ confirm.qe       │ queue            │ confirm.rk  │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ confirm.qe │ 28       │
└────────────┴──────────┘
[root@nullnull-os rabbitmq]# 
```

至此使用异步监听回调模式已经完成。



#### 7.4.4 Spring发送端确认机制

**导入依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```



**配制RabbitMQ**

```yaml
spring:
  application:
    name: publisherConfirm
  rabbitmq:
    host: node1
    virtual-host: /
    username: root
    password: 123456
    port: 5672
    # 启用发送方确认机制
    publisher-returns: true
    #NONE值是禁用发布确认模式，是默认值
    #CORRELATED值是发布消息成功到交换器后会触发回调方法
    #SIMPLE值经测试有两种效果，其一效果和CORRELATED值一样会触发回调方法，
    #其二在发布消息成功后使用rabbitTemplate调用waitForConfirms或waitForConfirmsOrDie
    #方法等待broker节点返回发送结果，根据返回结果来判定下一步的逻辑，
    #要注意的点是waitForConfirmsOrDie方法如果返回false则会关闭channel，则接下来无法发送消息到broker;
    publisher-confirm-type: correlated
```





**主入口类**

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublisherConfirmApplication {

  public static void main(String[] args) {
    SpringApplication.run(PublisherConfirmApplication.class, args);
  }
}
```





**队列配制**

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public Queue queue() {
    return new Queue("confirm.qe", false, false, false, null);
  }

  @Bean
  public Exchange exchange() {
    return new DirectExchange("confirm.ex", false, false, null);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(exchange()).with("confirm.rk").noargs();
  }
}
```



**控制类**

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class DataController {

  private RabbitTemplate template;

  /** 用于记录下发送的容器 */
  private Map<Long, String> ackMap = new ConcurrentHashMap<>();

  @Autowired
  public void setTemplate(RabbitTemplate template) {
    this.template = template;

    // 设置回调函数
    RabbitTemplate.ConfirmCallback ackCallback =
        new RabbitTemplate.ConfirmCallback() {
          @Override
          public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (ack) {
              String msg =
                  new String(
                      correlationData.getReturnedMessage().getBody(), StandardCharsets.UTF_8);
              ackMap.remove(Long.parseLong(correlationData.getId()));
              System.out.println("【回调】消息确认：" + correlationData.getId() + "--" + msg);
            } else {
              System.out.println("异常：" + cause);
            }
          }
        };

    this.template.setConfirmCallback(ackCallback);
  }

  @RequestMapping("/biz")
  public String doInvoke() throws Exception {

    long sendSeq = 1;

    for (int i = 0; i < 28; i++) {
      MessageProperties build =
          MessagePropertiesBuilder.newInstance()
              .setHeader("key1", "value1")
              .setCorrelationId(sendSeq + "")
              .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
              .build();
      build.setConsumerTag("msg");
      CorrelationData dataSend = new CorrelationData();
      dataSend.setId(sendSeq + "");

      // 用于回调验证的消息
      byte[] returnBytes = "这是响应的消息:".getBytes(StandardCharsets.UTF_8);
      dataSend.setReturnedMessage(new Message(returnBytes, null));

      // 发送的消息
      String msg = "这是等待确认的消息";
      byte[] sendBytes = msg.getBytes(StandardCharsets.UTF_8);
      Message message = new Message(sendBytes, build);
      template.convertAndSend("confirm.ex", "confirm.rk", message, dataSend);

      ackMap.put(sendSeq, msg);

      System.out.println("【发送】发送成功:" + sendSeq);
      Thread.sleep(ThreadLocalRandom.current().nextInt(0, 10));

      sendSeq = sendSeq + 1;
    }

    Thread.sleep(3000);
    System.out.println("未确认ACK的消息:" + ackMap.size());

    return "ok";
  }
}
```



运行应用程序,查看控制台输出:

```sh
2023-08-21 22:33:24.969  INFO 8628 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-08-21 22:33:24.969  INFO 8628 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-08-21 22:33:24.974  INFO 8628 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 5 ms
2023-08-21 22:33:25.001  INFO 8628 --- [nio-8080-exec-1] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [node1:5672]
2023-08-21 22:33:25.119  INFO 8628 --- [nio-8080-exec-1] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#3bd6ba24:0/SimpleConnection@5b31efba [delegate=amqp://root@150.158.137.207:5672/, localPort= 63833]
2023-08-21 22:33:25.123  INFO 8628 --- [nio-8080-exec-1] o.s.amqp.rabbit.core.RabbitAdmin         : Auto-declaring a non-durable or auto-delete Exchange (confirm.ex) durable:false, auto-delete:false. It will be deleted by the broker if it shuts down, and can be redeclared by closing and reopening the connection.
2023-08-21 22:33:25.123  INFO 8628 --- [nio-8080-exec-1] o.s.amqp.rabbit.core.RabbitAdmin         : Auto-declaring a non-durable, auto-delete, or exclusive Queue (confirm.qe) durable:false, auto-delete:false, exclusive:false. It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.
【发送】发送成功:1
【回调】消息确认：1--这是响应的消息:
【发送】发送成功:2
【发送】发送成功:3
【回调】消息确认：2--这是响应的消息:
【回调】消息确认：3--这是响应的消息:
【发送】发送成功:4
【发送】发送成功:5
【发送】发送成功:6
【回调】消息确认：4--这是响应的消息:
【发送】发送成功:7
【回调】消息确认：5--这是响应的消息:
【回调】消息确认：6--这是响应的消息:
【回调】消息确认：7--这是响应的消息:
【发送】发送成功:8
【发送】发送成功:9
【发送】发送成功:10
【发送】发送成功:11
【回调】消息确认：8--这是响应的消息:
【回调】消息确认：9--这是响应的消息:
【回调】消息确认：10--这是响应的消息:
【回调】消息确认：11--这是响应的消息:
【发送】发送成功:12
【发送】发送成功:13
【发送】发送成功:14
【回调】消息确认：12--这是响应的消息:
【发送】发送成功:15
【发送】发送成功:16
【回调】消息确认：13--这是响应的消息:
【发送】发送成功:17
【回调】消息确认：14--这是响应的消息:
【发送】发送成功:18
【发送】发送成功:19
【回调】消息确认：15--这是响应的消息:
【发送】发送成功:20
【回调】消息确认：16--这是响应的消息:
【回调】消息确认：17--这是响应的消息:
【回调】消息确认：18--这是响应的消息:
【回调】消息确认：19--这是响应的消息:
【回调】消息确认：20--这是响应的消息:
【发送】发送成功:21
【发送】发送成功:22
【发送】发送成功:23
【回调】消息确认：21--这是响应的消息:
【回调】消息确认：22--这是响应的消息:
【发送】发送成功:24
【回调】消息确认：23--这是响应的消息:
【发送】发送成功:25
【回调】消息确认：24--这是响应的消息:
【发送】发送成功:26
【回调】消息确认：25--这是响应的消息:
【发送】发送成功:27
【发送】发送成功:28
【回调】消息确认：26--这是响应的消息:
【回调】消息确认：27--这是响应的消息:
【回调】消息确认：28--这是响应的消息:
未确认ACK的消息:0

```

可以发现数据都已经成功的执行ACK的确认机制。此与原生的API，还是存在一些不同。此在发送时，已经将响应关联的ID进行了指定。这样当收到了confirm时，即能与之前发送的数据关联上。



检查队列的信息：

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
Listing exchanges for vhost / ...
┌────────────────────┬─────────┐
│ name               │ type    │
├────────────────────┼─────────┤
│ amq.fanout         │ fanout  │
├────────────────────┼─────────┤
│ confirm.ex         │ direct  │
├────────────────────┼─────────┤
│ amq.rabbitmq.trace │ topic   │
├────────────────────┼─────────┤
│ amq.headers        │ headers │
├────────────────────┼─────────┤
│ amq.topic          │ topic   │
├────────────────────┼─────────┤
│ amq.direct         │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]#  rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌─────────────┬─────────────┬──────────────────┬──────────────────┬─────────────┬───────────┐
│ source_name │ source_kind │ destination_name │ destination_kind │ routing_key │ arguments │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│             │ exchange    │ confirm.qe       │ queue            │ confirm.qe  │           │
├─────────────┼─────────────┼──────────────────┼──────────────────┼─────────────┼───────────┤
│ confirm.ex  │ exchange    │ confirm.qe       │ queue            │ confirm.rk  │           │
└─────────────┴─────────────┴──────────────────┴──────────────────┴─────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────────┬──────────┐
│ name       │ messages │
├────────────┼──────────┤
│ confirm.qe │ 28       │
└────────────┴──────────┘
[root@nullnull-os rabbitmq]# 
```









## 结束

