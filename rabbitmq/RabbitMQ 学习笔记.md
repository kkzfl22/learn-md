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



### 3.4 交换机队列等详细参数

**队列信息**

```sh
rabbitmqctl list_queues [-p vhost] [[--offline] | [--online] | [--local]] [queueinfoitem ...]
# 返回队列的详细信息。如果 "-p" 标志不存在，那么将返回默认虚拟主机的队列详细信息。"-p" 可以用来覆盖默认vhost。可以使用一下互斥选项之一，通过其状态或者位置过滤显示的队列。
# [--offline] 表示仅仅列出当前不可用的持久队列（更具体地说，他们的主节点不是）
# [--online] 表示列出当前可用的队列（他们的主节点是）
# [--local] 表示仅仅列出那些主程序在当前节点上的队列
# queueinfoitem参数用于指示要包括在结果中的哪些队列信息项。结果中的列顺序将与参数的顺序相匹配。queueinfoitem可以从以下列表中获取任何值：
# name 表示队列的名称
# durable 表示服务器重启之后，队列是否存活
# auto_delete 表示不再使用的队列是否自动被删除
# arguments 表示队列的参数
# policy 表示应用在队列中的策略名称
# pid 表示和队列相关联的Erlang进程的ID
# owner_pid 表示作为队列的排他所有者的连接的Erlang进程的ID，如果队列是非排他，则为空
# exclusive 表示队列是否是排他的，有 owner_pid 返回 True，否则返回 False
# exclusive_consumer_pid 表示排他消费者订阅该队列的频道的Erlang进程的ID，如果没有独家消费者，则为空
# exclusive_consumer_tag 表示订阅该队列的排他消费者的消费tag。如果没有排他消费者，则为空
# messages_ready 表示准备被发送到客户端的消息数量
# messages_unacknowledged 表示已经被发送到客户端但是还没有被确认的消息数量
# messages 表示准备发送和没有被确认的消息数量总和（队列深度）
# messages_ready_ram 表示驻留在 ram 里的 messages_ready 的消息数量
# messages_unacknowledged_ram 表示驻留在 ram 里的 messages_unacknowledged 的消息数量
# messages_ram 表示驻留在 ram 里的消息总数
# messages_persistent 表示队列中持久消息的总数（对于临时队列，总是为0）
# message_bytes 表示在队列中所有消息body的大小，这并不包括消息属性（包括header）或者任何开销
# message_bytes_ready 表示类似于 messge_bytes 但仅仅计算那些将发送到客户端的消息
# message_bytes_unacknowledged 表示类似于 message_bytes 但仅仅计算那些已经发送到客户还没有确认的消息
# message_bytes_ram 表示类似于 message_bytes 但仅仅计算那些驻留在ram中的消息
# message_bytes_persistent 表示类似于 message_bytes 但仅仅计算那些持久消息
# head_message_timestamp 表示队列中第一个消息的时间戳属性（如果存在）。只有处在 paged-in 状态的消息才存在时间戳。
# disk_reads 表示该队列自start起，从磁盘读取消息的次数总和
# disk_writes 表示该队列自start起，被写入磁盘消息的次数总和
# consumers 表示consumer的数量
# consumer_utilisation 表示队列能够立即将消息传递给消费者的时间分数（0.0 ~ 1.0之间），如果消费者受到网络拥塞或者预取计数的限制，该值可能小于1.0
# memory 表示和该队列相关联的Erlang进程消耗的内存字节数，包括stack/heap/内部数据结构
# slave_pids 表示该队列目前的slave的ID号（如果该队列被镜像的话）
# synchronised_slave_pids 表示如果队列被镜像，给出与主队列同步的当前slave的ID号，即可以从主队列接管而不丢失消息的slave的ID
# state 表示队列的状态，一般是 "running"； 如果队列正在同步，也可能是 "{syncing, MsgCount}"； 如果队列所处的节点当前down了，队列显示的状态为 "down"
# 如果没有指定queueinfoitem，那么将显示队列的名称（name）和深度（messages）

```







**交换器信息**

```sh
rabbitmqctl list_exchanges [-p vhost] [exchangeinfoitem ...]
# 返回交换器的详细信息。如果 "-p" 标志不存在，那么将返回默认虚拟主机的交换器详细信息。"-p" 可以用来覆盖默认vhost。
# exchangeinfoitem参数用于指示要包括在结果中的哪些交换器信息项。结果中的列顺序将与参数的顺序相匹配。exchangeinfoitem可以从以下列表中获取任何值：
# name 表示交换器的名称
# type 表示交换器类型（例如： direct/topic/fanout/headers）
# durable 表示服务器重启之后，交换器是否存活
# auto_delete 表示交换器不再使用时，是否被自动删除
# internal 表示交换器是否是内部的，例如不能被客户端直接发布
# arguments 表示交换器的参数
# policy 表示引用在该交换器上的策略名称
# 如果没有指定任何 exchangeinfoitem，那么该命令将显示交换器的名称（name）和类型（type）

```



**绑定信息**

```sh
rabbitmqctl list_bindings [-p vhost] [bindinginfoitem ...]
# 返回绑定的详细信息。如果 "-p" 标志不存在，那么将返回默认虚拟主机的绑定详细信息。"-p" 可以用来覆盖默认vhost。
# bindinginfoitem参数用于指示要包括在结果中的哪些绑定信息项。结果中的列顺序将与参数的顺序相匹配。bindinginfoitem可以从以下列表中获取任何值：
# source_name 表示绑定附加到的消息源的名称
# source_kind 表示绑定附加到的消息源的类型，目前通常交换器
# destination_name 表示附加绑定到的消息目的地的名称
# destination_kind 表示附加绑定到的消息目的地的类型
# routing_key 表示绑定的routing key
# arguments 表示绑定的参数
# 如果没有指定任何的 bindinginfoitem ，那么将展示上述所有的参数
# rabbitmqctl list_bindings -p /myvhost exchange_name queue_name
# 上述命令，表示展示在 /myvhost 虚拟主机中的绑定的exchange名称和queue名称
```



**连接信息**

```sh

rabbitmqctl list_connections [connectioninfoitem ...]
# 返回TCP/IP连接统计信息
# connectioninfoitem 参数用于指示要包括在结果中的哪些连接信息项，结果中的列顺序将与参数的顺序相匹配。connectioninfoitem可以从以下列表中获取任何值：
# pid 表示与该connection相关联的Erlang进程的id号
# name 表示该连接的可读性名称
# port 表示服务端口
# host 表示通过反向DNS获取的服务器主机名，如果反向DNS失败或未启用，则为其IP地址
# peer_port 表示对等端口
# peer_host 表示通过反向DNS获取的对等主机名，如果反向DNS失败或未启用，则为其IP地址
# ssl 表示该连接是否使用SSL保护的bool值
# ssl_protocal 表示SSL协议(例如： tlsv1)
# ssl_key_exchange 表示SSL关键交换器算法（例如： rsa）
# ssl_cipher 表示SSL密码算法（例如： aes_256_cbc）
# ssl_hash 表示SSL哈希函数（例如： sha）
# peer_cert_issuer 表示对等体的SSL证书的颁发者，以RFC4514形式出现
# peer_cert_validity 表示对等体的SSL证书的有效期限
# state 表示连接状态（例如： starting/tuning/opening/running/flow/blocking/blocked/closing/closed）
# channels 表示正在使用连接的通道数量
# protocol 表示正在使用的AMQP的版本号。注意，如果一个客户端需要一个AMQP 0-9 连接，我们将其作为 AMQP 0-9-1
# auth_mechanism 表示使用SASL认证机制，如PLAN
# user 表示和该连接相关联的用户名
# vhost 表示vhost名称
# timeout 表示连接超时/协商心跳间隔，单位为秒
# frame_max 表示最大的frame大小（byte）
# channel_max 表示该连接上通道的最大数量
# client_properties 表示在连接建立期间，有客户端传送的消息属性
# recv_oct 表示接受到的八位字节
# recv_cnt 表示接受到的包
# send_oct 表示发送的八位字节
# send_cnt 表示发送的包
# send_pend 表示发送的队列大小
# connected_at 表示该连接被建立的日期和时间的时间戳格式
# 如果没有指定任何connectioninfoitem，那么将展示：user/peer_host/peer_port/流量控制和内存块状态之后的时间

```



**通道信息**

```sh

rabbitmqctl list_channels [channelinfoitem ...]
# 返回所有当前的通道的信息，通道即一个执行大多数AMQP命令的逻辑容器。这包括由普通AMQP连接的一部分通道、由各种插件和其他扩展程序创建的通道。
# channelinfoitem 参数用于指示要包括在结果中的哪些连接信息项，结果中的列顺序将与参数的顺序相匹配。channelinfoitem 可以从以下列表中获取任何值：
# pid 表示与该连接相关联的Erlang程序的ID号
# connection 表示与通道所属连接相关联的Erlang进程的ID号
# name 表示通道的可读性名称
# number 表示通道的号码，在连接中唯一表示它
# user 表示和该通道相关联的用户名
# vhost 表示通道操作所在的vhost
# transactional 表示通道是否处于事务模式，返回 true，否则返回 false
# confirm 表示通道是否处于确认模式，返回 true, 否则返回 false
# consumer_count 表示通过通道检索消息的逻辑AMQP消费者数量
# messages_unacknowledged 表示通过通道发送过但还没收到反馈的消息的数量
# messages_uncommitted 表示尚未提交的事务中接受的消息数
# acks_uncommitted 表示尚未提交的事务中接受的确认数
# messages_unconfirmed 表示尚未确认已发布的消息数量。在不处于确认模式中的通道上，该值为0
# prefetch_count 表示新消费者的QoS预取限制，如果没有限制则为0
# global_prefetch_count 表示整个通道的QoS预取限制，如果没有限制则为0
# 如果没有指定任何 channelinfoitem 项，那么将展示 pid/user/consumer_count/messages_unacknowledged

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

官方文档参考：https://www.rabbitmq.com/tutorials/tutorial-four-python.html

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

官方文档参考：https://www.rabbitmq.com/tutorials/tutorial-five-python.html



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



### 7.5 持久化存储机制

持久化是提高RabbitMQ可靠性的基础，否则当RabbitMQ遇到异常时（如重启、断电、停机等）数据将会丢失。主要从以下几个方面保障消息的持久性：

>1. Exchange 持久化通过定义时设置durable参数为true来保证Exchange相关的元数据不丢失。
>2. Queue的持久化。也是通过定义时设置durable参数为true来保证Queue相关的元数据不丢失。
>3. 消息的持久化，通过将消息的投递模式（BasicProperties中的deliveryMode属性）设置为2，即可实现消息的持久化，保证消息身身不丢失。



依赖

```xml
        <dependency>
            <groupId>com.rabbitmq</groupId>
            <artifactId>amqp-client</artifactId>
        </dependency>
```

持久化的实现

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class Product {

  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 交换器元数据持久化 durable参数为true，表示要持久化
      channel.exchangeDeclare(
          "persistent.ex", BuiltinExchangeType.DIRECT, true, false, false, null);
      // 队列元数据持久化，durable参数为true，表示要持久化
      channel.queueDeclare("persistent.qu", true, false, false, null);

      // 交换机与队列绑定
      channel.queueBind("persistent.qu", "persistent.ex", "persistent.rk");

      String msg = "hello world:" ;
      AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
      builder.contentType("text/plain");
      // 发送的消息持久化
      builder.deliveryMode(2);
      AMQP.BasicProperties properties = builder.build();
      channel.basicPublish(
          "persistent.ex", "persistent.rk", properties, msg.getBytes(StandardCharsets.UTF_8));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

```

运行生产者的代码，检查队列情况

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
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
│ persistent.ex      │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌───────────────┬─────────────┬──────────────────┬──────────────────┬───────────────┬───────────┐
│ source_name   │ source_kind │ destination_name │ destination_kind │ routing_key   │ arguments │
├───────────────┼─────────────┼──────────────────┼──────────────────┼───────────────┼───────────┤
│               │ exchange    │ persistent.qu    │ queue            │ persistent.qu │           │
├───────────────┼─────────────┼──────────────────┼──────────────────┼───────────────┼───────────┤
│               │ exchange    │ transient.qu     │ queue            │ transient.qu  │           │
├───────────────┼─────────────┼──────────────────┼──────────────────┼───────────────┼───────────┤
│ persistent.ex │ exchange    │ persistent.qu    │ queue            │ persistent.rk │           │
└───────────────┴─────────────┴──────────────────┴──────────────────┴───────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬──────────┐
│ name          │ messages │
├───────────────┼──────────┤
│ persistent.qu │ 1        │
├───────────────┼──────────┤
│ transient.qu  │ 1        │
└───────────────┴──────────┘
[root@nullnull-os rabbitmq]#
```

这里存在两个队列，一个是持久化的队列, 一个是非持久化的消息队列。重启下RabbitMQ看下情况。

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl stop
Stopping and halting node rabbit@nullnull-os ...
[root@nullnull-os rabbitmq]# ps -ef | grep rabbit
rabbitmq  3448     1  0 Aug18 ?        00:00:03 /usr/lib64/erlang/erts-11.0.2/bin/epmd -daemon
root     29042 28327  0 09:45 pts/0    00:00:00 grep --color=auto rabbit
[root@nullnull-os rabbitmq]# kill 3448
[root@nullnull-os rabbitmq]# systemctl start rabbitmq-server
[root@nullnull-os rabbitmq]# rabbitmqctl list_exchanges --formatter pretty_table
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
│ persistent.ex      │ direct  │
├────────────────────┼─────────┤
│                    │ direct  │
├────────────────────┼─────────┤
│ amq.match          │ headers │
└────────────────────┴─────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_bindings --formatter pretty_table
Listing bindings for vhost /...
┌───────────────┬─────────────┬──────────────────┬──────────────────┬───────────────┬───────────┐
│ source_name   │ source_kind │ destination_name │ destination_kind │ routing_key   │ arguments │
├───────────────┼─────────────┼──────────────────┼──────────────────┼───────────────┼───────────┤
│               │ exchange    │ persistent.qu    │ queue            │ persistent.qu │           │
├───────────────┼─────────────┼──────────────────┼──────────────────┼───────────────┼───────────┤
│ persistent.ex │ exchange    │ persistent.qu    │ queue            │ persistent.rk │           │
└───────────────┴─────────────┴──────────────────┴──────────────────┴───────────────┴───────────┘
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬──────────┐
│ name          │ messages │
├───────────────┼──────────┤
│ persistent.qu │ 1      │
└───────────────┴──────────┘
[root@nullnull-os rabbitmq]# 
```



在重启之后，非持久化的消息队列已经没有了，而定义为持久化的消息交换器、队列和消息都还是存在的。那再来看看此时消息存储在磁盘是上一个什么样子的结构。

```sh
[root@nullnull-os 628WB79CIFDYO9LJI6DKMI09L]# pwd
/var/lib/rabbitmq/mnesia/rabbit@nullnull-os/msg_stores/vhosts/628WB79CIFDYO9LJI6DKMI09L
[root@nullnull-os 628WB79CIFDYO9LJI6DKMI09L]# tree .
.
|-- msg_store_persistent (保存着持久化的消息数据)
|   `-- 0.rdq   
|-- msg_store_transient (保存着非持久化相关的数据)
|   `-- 0.rdq
|-- queues  (保存诂rabbit_queue_index相关数据，即队列索引)
|   `-- 1QKEY2HOMIM54YMCVC16QQ8U5
|       |-- 0.idx   (索引文件)
|       `-- journal.jif
`-- recovery.dets

4 directories, 5 files
[root@nullnull-os 628WB79CIFDYO9LJI6DKMI09L]# ll -R .
.:
total 20
drwxr-x--- 2 rabbitmq rabbitmq 4096 Aug 22 10:18 msg_store_persistent
drwxr-x--- 2 rabbitmq rabbitmq 4096 Aug 22 10:18 msg_store_transient
drwxr-x--- 3 rabbitmq rabbitmq 4096 Aug 22 10:18 queues
-rw-r----- 1 rabbitmq rabbitmq 5464 Aug 22 10:18 recovery.dets

./msg_store_persistent:
total 0
-rw-r----- 1 rabbitmq rabbitmq 0 Aug 22 10:18 0.rdq

./msg_store_transient:
total 0
-rw-r----- 1 rabbitmq rabbitmq 0 Aug 22 10:18 0.rdq

./queues:
total 4
drwxr-x--- 2 rabbitmq rabbitmq 4096 Aug 22 10:18 1QKEY2HOMIM54YMCVC16QQ8U5

./queues/1QKEY2HOMIM54YMCVC16QQ8U5:
total 4
-rw-r----- 1 rabbitmq rabbitmq 244 Aug 22 10:18 0.idx
-rw-r----- 1 rabbitmq rabbitmq   0 Aug 22 10:18 journal.jif
[root@nullnull-os 628WB79CIFDYO9LJI6DKMI09L]# 
```

 RabbitMQ通过配制queue_index_embed_msgs_below可以根据消息大小决定存储位置，默认queue_index_embed_msgs_below是4096字节(包含消息体、属性及headers)，小于该值的消息都存在rabbit_queue_index中。

vi 0.idx

```sh
À^@êÙ´jµp<9d>!ºBÀbdO_Ê^@^@^@^@^@^@^@^@^@^@^@^L^@^@^@Ò<83>h^Fd^@^Mbasic_messageh^Dd^@^Hresourcem^@^@^@^A/d^@^Hexchangem^@^@^@^Mpersistent.exl^@^@^@^Am^@^@^@^Mpersistent.rkjh^Fd^@^Gcontenta<d^@^Dnonem^@^@^@^N<90>^@
text/plain^Bd^@^Yrabbit_framing_amqp_0_9_1l^@^@^@^Am^@^@^@^Lhello world:jm^@^@^@^PêÙ´jµp<9d>!ºBÀbdO_Êd^@^Dtrue
```

在文件内容中，还有检查到发送的数据内容: hello world



### 7.6 消费端ACK机制

​	在这之前已经完成了发送端的确认机制。可以保证数据成功的发送到RabbitMQ，以及持久化机制，然尔这依然无法完全保证整个过程的可靠性，因为如果消息被消费过程中业务处理失败了，但是消息却已经被标记为消费了，如果又没有任何重度机制，那结果基本等于丢消息。在消费端如何保证消息不丢呢？

 	在rabbitMQ的消费端会有ACK机制。即消费端消费消息后需要发送ACK确认报文给Broker端，告知自己是否已经消费完成，否则可能会一直重发消息直到消息过期（AUTO模式）。同时这个也是最终一致性、可恢复性的基础。一般有如下手段：

>1. 采用NONE模式，消费的过程中自行捕捉异常，引发异常后直接记录日志并落到异常处理表，再通过后台定时任务扫描异常恢复表做重度动作。如果业务不自行处理则有丢失数据的风险。
>2. 采用AUTO（自动ACK）模式，不主动捕获异常，当消费过程中出现异常时，会将消息放回Queue中，然后消息会被重新分配到其他消费节点（如果没有则还是选择当前节点）重新被消费，默认会一直重发消息并直到消费完成返回ACK或者一直到过期。
>3. 采用MANUAL（手动ACK）模式，消费者自行控制流程并手动调用channel相关的方法返回ACK。

#### 7.6.1 手动ACK机制-Reject

**maven导入**

```xml
            <dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>5.9.0</version>
            </dependency>
```



**生产者**

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

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换器队列
      channel.exchangeDeclare("ack.ex", BuiltinExchangeType.DIRECT, false, false, false, null);
      // 定义队列
      channel.queueDeclare("ack.qu", false, false, false, null);
      // 队列绑定
      channel.queueBind("ack.qu", "ack.ex", "ack.rk");

      for (int i = 0; i < 5; i++) {
        byte[] sendBytes = ("hello-" + i).getBytes(StandardCharsets.UTF_8);
        channel.basicPublish("ack.ex", "ack.rk", null, sendBytes);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

```



**消费者**

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Consumer {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare("ack.qu", false, false, false, null);
    DefaultConsumer consumer =
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              // 消费者标签
              String consumerTag,
              // 消费者的封装
              Envelope envelope,
              // 消息属性
              AMQP.BasicProperties properties,
              // 消息体
              byte[] body)
              throws IOException {

            System.out.println("确认的消息内容:" + new String(body));
            // 找收消息
            // Nack与Reject的区别在于，nack可以对多条消息进行拒收，而reject只能拒收一条。
            // requeue为true表示不确认的消息会重新放回队列。
            channel.basicReject(envelope.getDeliveryTag(), true);
          }
        };

    channel.basicConsume(
        "ack.qu",
        // 非自动确认
        false,
        // 消费者的标签
        "ack.consumer",
        // 回调函数
        consumer);
  }
}

```



**发送测试**

首先执行生产者向队列中发送数据。然后执行消费者，检查拒收的处理。

在消费者的控制台，将持续不断的输出消息信息：

```sh
确认的消息内容:hello-0
确认的消息内容:hello-1
确认的消息内容:hello-2
确认的消息内容:hello-3
确认的消息内容:hello-4
确认的消息内容:hello-0
确认的消息内容:hello-1
......
确认的消息内容:hello-0
```

按照发送的顺序将不断的被打印。

那此时消息是什么状态呢？查看下消息队列中的信息

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 5                       │ 5        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]# 
```

可以看到当前的消息处于unack的状态。由于消息被不断的重新放回队列，而消费者又只有当前这一个，所以，在不断拒收中被放回。



那如果将消息拒绝改为不重新放回队列，会如何呢？来验证下。

首先修改消费者的代码:

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Consumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare("ack.qu", false, false, false, null);

    DefaultConsumer consumer =
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              // 消费者标签
              String consumerTag,
              // 消费者的封装
              Envelope envelope,
              // 消息属性
              AMQP.BasicProperties properties,
              // 消息体
              byte[] body)
              throws IOException {

            System.out.println("确认的消息内容:" + new String(body));


            // 找收消息
            // Nack与Reject的区别在于，nack可以对多条消息进行拒收，而reject只能拒收一条。
            // requeue为false表示不确认的消息不会重新放回队列。
            //channel.basicReject(envelope.getDeliveryTag(), true);
            channel.basicReject(envelope.getDeliveryTag(), false);
          }
        };

    channel.basicConsume(
        "ack.qu",
        // 非自动确认
        false,
        // 消费者的标签
        "ack.consumer",
        // 回调函数
        consumer);
  }
}

```

再次执行消费者。

```sh
确认的消息内容:hello-0
确认的消息内容:hello-1
确认的消息内容:hello-2
确认的消息内容:hello-3
确认的消息内容:hello-4
```

而这一次消息没有再循环打印。只输出一遍，再检查下消息在队列中的状态：

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 0                       │ 0        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]# 
```

通过观察发现，消息已经没有在队列中了，那就是消息已经被丢弃了。



#### 7.6.2 手动ACK机制-ack

消费者修改为ACK确认处理

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Consumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare("ack.qu", false, false, false, null);

    DefaultConsumer consumer =
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              // 消费者标签
              String consumerTag,
              // 消费者的封装
              Envelope envelope,
              // 消息属性
              AMQP.BasicProperties properties,
              // 消息体
              byte[] body)
              throws IOException {

            System.out.println("确认的消息内容:" + new String(body));

            // 消息确认，并且非批量确认，multiple为false，表示只确认了单条
            channel.basicAck(envelope.getDeliveryTag(), false);
          }
        };

    channel.basicConsume(
        "ack.qu",
        // 非自动确认
        false,
        // 消费者的标签
        "ack.consumer",
        // 回调函数
        consumer);
  }
}

```

此时可以先运行消息者。等待消息推送。然后运行生产者将消息推送，此时便可以看到消费者的控制台输出：

```java
确认的消息内容:hello-0
确认的消息内容:hello-1
确认的消息内容:hello-2
确认的消息内容:hello-3
确认的消息内容:hello-4
```

观察队列中的信息

```java
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 0                       │ 0        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]# 
```

在队列中，消息已经被成功的消费了。



#### 7.6.3 手动ACK机制-nack

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Consumer {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare("ack.qu", false, false, false, null);

    DefaultConsumer consumer =
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              // 消费者标签
              String consumerTag,
              // 消费者的封装
              Envelope envelope,
              // 消息属性
              AMQP.BasicProperties properties,
              // 消息体
              byte[] body)
              throws IOException {

            System.out.println("确认的消息内容:" + new String(body));
            // 消息批量不确认，即批量丢弃，每5个做一次批量消费
            // 参数1，消息的标签
            // multiple为false 表示不确认当前是一个消息。true就是多个消息。
            // requeue为true表示不确认的消息会重新放回队列。
            // 每5条做一次批量确认,_deliveryTag从1开始
            if (envelope.getDeliveryTag() % 5 == 0) {
                System.out.println("批量确认执行");
                channel.basicNack(envelope.getDeliveryTag(), true, false);
            }
          }
        };

    channel.basicConsume(
        "ack.qu",
        // 非自动确认
        false,
        // 消费者的标签
        "ack.consumer",
        // 回调函数
        consumer);
  }
}

```

执行消费者程序,然后再执行生产者。查看消费端的控制台：

```tex
确认的消息内容:hello-0
确认的消息内容:hello-1
确认的消息内容:hello-2
确认的消息内容:hello-3
确认的消息内容:hello-4
批量确认执行
```

由于此处采用的是不重新放回队列，所以，数据接收到之后被丢弃了。

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 0                       │ 0        │ 0         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
```

队列中的数据也已经被处理掉了。



#### 7.6.4 手动ACK机制-SpringBoot

首先是Maven导入

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
```

配制文件application.yml

```yaml
spring:
  application:
    name: consumer-ack
  rabbitmq:
    host: node1
    port: 5672
    virtual-host: /
    username: root
    password: 123456

    # 配制消费端ack信息。
    listener:
      simple:
        acknowledge-mode: manual
        # 重试超过最大次数后是否拒绝
        default-requeue-rejected: false
        retry:
          # 开启消费者重度(false时关闭消费者重试，false不是不重试，而是一直收到消息直到ack确认或者一直到超时）
          enable: true
          # 最大重度次数
          max-attempts: 5
          # 重试间隔时间(单位毫秒)
          initial-interval: 1000

```


主启动类

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;


@SpringBootApplication
public class Main {

  @Autowired private RabbitTemplate rabbitTemplate;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  /**
   * 在启动后就开始向MQ中发送消息
   *
   * @return
   */
  @Bean
  public ApplicationRunner runner() {
    return args -> {
      Thread.sleep(5000);
      for (int i = 0; i < 10; i++) {
        MessageProperties props = new MessageProperties();
        props.setDeliveryTag(i);
        Message message = new Message(("消息:" + i).getBytes(StandardCharsets.UTF_8), props);
        rabbitTemplate.convertAndSend("ack.ex", "ack.rk", message);
      }
    };
  }
}
```

当主类启动后，会延迟5秒，向MQ中发送10条记录。



队列配制

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
    return new Queue("ack.qu", false, false, false, null);
  }

  @Bean
  public Exchange exchange()
  {
    return new DirectExchange("ack.ex",false,false,null);
  }

  @Bean
  public Binding binding()
  {
    return BindingBuilder.bind(queue()).to(exchange()).with("ack.rk").noargs();
  }
}

```



**使用推送模式来查确认消息**

监听器，MQ队列推送消息至listener

```java
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MessageListener {

  /**
   * NONE模式，则只要收到消息后就立即确认（消息出列，标识已消费），有丢数据风险
   *
   * <p>AUTO模式，看情况确认，如果此时消费者抛出异常则消息会返回队列中
   *
   * <p>WANUAL模式，需要显示的调用当前channel的basicAck方法
   *
   * @param channel
   * @param deliveryTag
   * @param msg
   */
  // @RabbitListener(queues = "ack.qu", ackMode = "AUTO")
  // @RabbitListener(queues = "ack.qu", ackMode = "NONE")
  @RabbitListener(queues = "ack.qu", ackMode = "MANUAL")
  public void handMessageTopic(
      Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Payload String msg) {

    System.out.println("消息内容：" + msg);

    ThreadLocalRandom current = ThreadLocalRandom.current();

    try {
      if (current.nextInt(10) % 3 != 0) {
        // 手动nack，告诉broker消费者处理失败，最后一个参数表示是否需要将消息重新入列
        // channel.basicNack(deliveryTag, false, true);
        // 手动拒绝消息，第二个参数表示是否重新入列
        channel.basicReject(deliveryTag, true);
      } else {
        // 手动ACK，deliveryTag表示消息的唯一标志，multiple表示是否批量确认
        channel.basicAck(deliveryTag, false);
        System.out.println("已经确认的消息" + msg);
      }
      Thread.sleep(ThreadLocalRandom.current().nextInt(500, 3000));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
    }
  }
}
```

消息有33%的概率被拒绝，这样又会被重新放回队列，等待下次推送。

**启动测试**

运行main方法

```sh
【确认】消息内容：消息:0
【拒绝】消息内容：消息:1
【拒绝】消息内容：消息:2
【拒绝】消息内容：消息:3
【确认】消息内容：消息:4
【确认】消息内容：消息:5
【拒绝】消息内容：消息:6
【拒绝】消息内容：消息:7
【拒绝】消息内容：消息:8
【拒绝】消息内容：消息:9
【确认】消息内容：消息:1
【拒绝】消息内容：消息:2
【拒绝】消息内容：消息:3
【拒绝】消息内容：消息:6
【确认】消息内容：消息:7
【确认】消息内容：消息:8
【拒绝】消息内容：消息:9
【拒绝】消息内容：消息:2
【拒绝】消息内容：消息:3
【拒绝】消息内容：消息:6
【确认】消息内容：消息:9
【确认】消息内容：消息:2
【拒绝】消息内容：消息:3
【拒绝】消息内容：消息:6
【确认】消息内容：消息:3
【拒绝】消息内容：消息:6
【确认】消息内容：消息:6
```

从观察到的结果也印证了，反复的被推送，接收的一个过程中，使用命令查看队列的一个消费的情况

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 6                       │ 6        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 1                       │ 1        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 0                       │ 0        │ 1         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
```





**使用拉确认消息**

```java

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MsgController {

  @Autowired private RabbitTemplate rabbitTemplate;

  @RequestMapping("/msg")
  public String getMessage() {
    String message =
        rabbitTemplate.execute(
            new ChannelCallback<String>() {
              @Override
              public String doInRabbit(Channel channel) throws Exception {

                GetResponse getResponse = channel.basicGet("ack.qu", false);

                if (null == getResponse) {
                  return "你已经消费完所有的消息";
                }

                String message = new String(getResponse.getBody(), StandardCharsets.UTF_8);

                if (ThreadLocalRandom.current().nextInt(10) % 3 == 0) {
                  // 执行消息确认操作
                  channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);

                  return "已确认的消息:" + message;
                } else {
                  // 拒收一条消息并重新放回队列
                  channel.basicReject(getResponse.getEnvelope().getDeliveryTag(), true);
                  return "拒绝的消息:" + message;
                }
              }
            });

    return message;
  }
}

```

在浏览器中访问，同样有66%的概率会被拒绝，仅33%会被确认。



注：如果与监听在同一个工程，需将监听器给注释。

启动main函数，在浏览器中访问。http://127.0.0.1:8080/msg

可以看到返回:

```sh
拒绝的消息:消息:0
已确认的消息:消息:1
拒绝的消息:消息:2
......
已确认的消息:消息:9
你已经消费完所有的消息
```



同样的观察队列的一个消费情况：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 8              │ 0                       │ 8        │ 0         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 3              │ 0                       │ 3        │ 0         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ack.qu        │ 0              │ 0                       │ 0        │ 0         │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ persistent.qu │ 1              │ 0                       │ 1        │ 0         │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]#

```

使用拉模式进行消息ACK确认也已经完成。



### 7.7 消费端限流

在类似如秒杀活动中，一开始会有大量并发写请求到达服务端，城机对消息进行削峰处理，如何做？

当消息投递的速度远快于消费的速度时，随着时间积累就会出现“消息积压”。消息中间件本身是具备一定的缓冲能力的，但这个能力是有容量限制的，如果长期运行并没有任何处理，最终会导致Broker崩溃，而分布式系统的故障往往会发生上下游传递，连锁反应可能会引起系统大范围的宕机，这就很悲剧了

#### 7.7.1 资源限制限流

在RabbitMQ中可对**内存和磁盘使用量**设置阈值，当达到阈值后，生产者将被阻塞（block),直到对应指标恢复正常。全局上可以防止超大流量、消息积压等导致的Broker被压垮。当内存受限或磁盘可用空间受限的时候，服务器都会暂时阻止连接，服务器将暂停从发布消息的已连接的客户端的套接字读取数据。连接心中监视也将被禁用。所有网络连接将在rabbitmqctl和管理插件中显示为“已阻止”，这意味着它们尚未尝试发布，因此可以或者被阻止，这意味着它们已发布，现在已暂停。兼容的客户端被阻止将收到通知。

在`/etc/rabbitmq/rabbitmq.conf`中配置磁盘可用空间大小：

```sh
# 磁盘限制阈值设置
# 设置磁盘的可用空间大小，单位字节。当磁盘可用空间低于这个值的时候，发生磁盘告警，触发限流。
# 如果设置了相对大小，则忽略此绝对大小。
disk_free_limit.absolute = 2000

# 使用计量单位，从RabbitMQ 3.6.0开始有效，对vm_memory_high_watemark同样有效。
# disk_free_limit.absolute = 500KB
# disk_free_limit.absolute = 50mb
# disk_free_limit.absolute = 5GB

# Alternatively, we can set a limit relative to total avaliable RAM.
# Values lower than 1.0 can be dangerous and should be used carefully.
# 还可以使用相对于总可用内存来设置。注意，此值不要低于1.0！
# 当磁盘可用空间低于总可用内存的2.0倍的时候，触发限流
# disk_free_limit.relative = 2.0

# 内存限流阈值设置
# 0.4表示阈值总可用内存的比值。 总可用内存表示操作系统给每个进程分配的大小，或者实际内存大小。
# 如32位的Windows，系统给每个进程最大2GB的内存，则此比值表示阈值为820MB
vm_memory_high_watermark.relative = 0.4

# 还可以直接通过绝对值限制可用内存大小，单位字节
vm_memory_high_watermark.absolute = 1073741824

# 从RabbitMQ 3.6.0开始，绝对值支持计量单位。如果设置了相对值，则忽略此设置值
vm_memory_high_watermark.absolute = 1024MiB



k, kiB : kibibytes(2^10 - 1024 bytes)
M, MiB : mibibytes(2^20 - 1024576 bytes)
G, GiB : gibibytes(2^30 - 1073741824 bytes)

KB: kilobytes (10^3 - 1000 bytes)
MB: megabytes (10^6 - 1000000 bytes)
GB: gigabytes (10^9 - 1000000000 bytes)

```



可以通过两种来设置生效

1. **临时生效**

   此配制仅当前生效在重启后将失效。

```sh
# 硬盘资源限制
rabbitmqctl set_disk_free_limit 68996808704
# 内存资源限制
rabbitmqctl set_vm_memory_high_watermark 0.4
```

样例：

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl set_disk_free_limit 68996808704
Setting disk free limit on rabbit@nullnull-os to 68996808704 bytes ...
```

2. **长期生效**

在rabbitmq.conf的配制文件中加入

```properties
# 硬盘限制 
disk_free_limit.absolute=68455178240

# 内存限制
vm_memory_high_watermark.relative = 0.4
```

样例：

```sh
[root@nullnull-os rabbitmq]# vi  /etc/rabbitmq/rabbitmq.conf 
# 加入以下内容,注意单位到字节
disk_free_limit.absolute=68455178240

[root@nullnull-os rabbitmq]# cat /etc/rabbitmq/rabbitmq.conf 
disk_free_limit.absolute=68455178240

[root@nullnull-os rabbitmq]# systemctl restart rabbitmq-server
[root@nullnull-os rabbitmq]# 
```

注意，此需要重启rabbitMQ才能生效。



磁盘限制配制参考

>## [Configuring Disk Free Space Limit](https://www.rabbitmq.com/disk-alarms.html#configure)
>
>The disk free space limit is configured with the disk_free_limit setting. By default 50MB is required to be free on the database partition (see the description of [file locations](https://www.rabbitmq.com/relocate.html) for the default database location). This configuration file sets the disk free space limit to 1GB:
>
>```ini
>disk_free_limit.absolute = 1000000000
>```
>
>Or you can use memory units (KB, MB GB etc.) like this:
>
>```ini
>disk_free_limit.absolute = 1GB
>```
>
>It is also possible to set a free space limit relative to the RAM in the machine. This configuration file sets the disk free space limit to the same as the amount of RAM on the machine:
>
>```ini
>disk_free_limit.relative = 1.0
>```
>
>The limit can be changed while the broker is running using the rabbitmqctl set_disk_free_limit command or rabbitmqctl set_disk_free_limit mem_relative command. This command will take effect until next node restart.
>
>The corresponding configuration setting should also be changed when the effects should survive a node restart.
>
>

来自：https://www.rabbitmq.com/disk-alarms.html

内存配制限制参考

https://www.rabbitmq.com/memory.html

>## [Configuring the Memory Threshold](https://www.rabbitmq.com/memory.html#configuring-threshold)
>
>The memory threshold at which the flow control is triggered can be adjusted by editing the [configuration file](https://www.rabbitmq.com/configure.html#configuration-files).
>
>The example below sets the threshold to the default value of 0.4:
>
>```ini
>\# new style config format, recommended
>vm_memory_high_watermark.relative = 0.4
>```
>
>The default value of 0.4 stands for 40% of available (detected) RAM or 40% of available virtual address space, whichever is smaller. E.g. on a 32-bit platform with 4 GiB of RAM installed, 40% of 4 GiB is 1.6 GiB, but 32-bit Windows normally limits processes to 2 GiB, so the threshold is actually to 40% of 2 GiB (which is 820 MiB).
>
>Alternatively, the memory threshold can be adjusted by setting an absolute limit of RAM used by the node. The example below sets the threshold to 1073741824 bytes (1024 MiB):
>
>```ini
>vm_memory_high_watermark.absolute = 1073741824
>```
>
>Same example, but using memory units:
>
>```ini
>vm_memory_high_watermark.absolute = 1024MiB
>```
>
>If the absolute limit is larger than the installed RAM or available virtual address space, the threshold is set to whichever limit is smaller.
>
>The memory limit is appended to the [log file](https://www.rabbitmq.com/logging.html) when the RabbitMQ node starts:
>
>```ini
>2019-06-10 23:17:05.976 [info] <0.308.0> Memory high watermark set to 1024 MiB (1073741824 bytes) of 8192 MiB (8589934592 bytes) total
>```
>
>The memory limit may also be queried using the rabbitmq-diagnostics memory_breakdown and rabbitmq-diagnostics status commands.
>
>The threshold can be changed while the broker is running using the
>
>```bash
>rabbitmqctl set_vm_memory_high_watermark <fraction>
>```
>
>command or
>
>```bash
>rabbitmqctl set_vm_memory_high_watermark absolute <memory_limit>
>```
>
>For example:
>
>```bash
>rabbitmqctl set_vm_memory_high_watermark 0.6
>```
>
>and
>
>```bash
>rabbitmqctl set_vm_memory_high_watermark absolute "4G"
>```
>
>When using the absolute mode, it is possible to use one of the following memory units:
>
>- M, MiB for mebibytes (2^20 bytes)
>- MB for megabytes (10^6 bytes)
>- G, GiB for gibibytes (2^30 bytes)
>- GB for gigabytes (10^9 bytes)
>
>



中文配制可参考：https://www.cnblogs.com/kaishirenshi/p/12132703.html

更多配制可参见：https://www.rabbitmq.com/configure.html#config-file





样例程序：

maven导入

```xml
            <dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>5.9.0</version>
            </dependency>
```



生产程序：

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

public class ResourceLimitProduct {
  public static void main(String[] args) throws Exception {
    // 资源限制
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换器、队列和绑定
      channel.exchangeDeclare("res.limit.ex", BuiltinExchangeType.DIRECT, true, false, null);
      channel.queueDeclare("res.limit.qu", true, false, false, null);
      channel.queueBind("res.limit.qu", "res.limit.ex", "res.limit.rk");

      // 开启发送方确认机制
      AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

      ConfirmCallback confirm =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                System.out.println("【批量确认】:小于" + deliveryTag + "已经确认");
              } else {
                System.out.println("【单条确认】:等于" + deliveryTag + "已经确认");
              }
            }
          };

      ConfirmCallback nackConfirm =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                System.out.println("【批量不确认】:小于" + deliveryTag + "已经确认");
              } else {
                System.out.println("【单条不确认】:等于" + deliveryTag + "已经确认");
              }
            }
          };

      channel.addConfirmListener(confirm, nackConfirm);

      for (int i = 0; i < 100000000; i++) {
        String msg = getKbMessage(i);
        long sequence = channel.getNextPublishSeqNo();
        System.out.println("【发送】成功了序列消息:" + sequence);

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentType("text/plain");
        // 发送的消息持久化
        builder.deliveryMode(2);
        AMQP.BasicProperties properties = builder.build();

        channel.basicPublish(
            "res.limit.ex", "res.limit.rk", properties, msg.getBytes(StandardCharsets.UTF_8));

        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 100));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String getKbMessage(int i) {
    StringBuilder msg = new StringBuilder("发送确认消息:" + i + "--");
    for (int j = 0; j < 102400; j++) {
      msg.append(j);
    }
    return msg.toString();
  }
}
```



**设置硬盘资源限制**

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl set_disk_free_limit 68996808704
Setting disk free limit on rabbit@nullnull-os to 68996808704 bytes ...
```

运行生产者的应用程序，查看控制台的输出

```sh
【发送】成功了序列消息:1
【单条确认】:等于1已经确认
【发送】成功了序列消息:2
【发送】成功了序列消息:3
【单条确认】:等于2已经确认
【发送】成功了序列消息:4
【单条确认】:等于3已经确认
【发送】成功了序列消息:5
......
【单条确认】:等于702已经确认
【单条确认】:等于703已经确认
【发送】成功了序列消息:704
【发送】成功了序列消息:705
【发送】成功了序列消息:706
【发送】成功了序列消息:707
【发送】成功了序列消息:708
【发送】成功了序列消息:709
【发送】成功了序列消息:710
【发送】成功了序列消息:711
```

到此使用硬盘空间限制的测试完成。





**内存资源限制**

编辑配制文件rabbitmq.conf

```sh
vi /etc/rabbitmqrabbitmq.conf 

# 添加配制
vm_memory_high_watermark.absolute=120M

```

重启让其生效

```sh
systemctl restart rabbitmq-server
```

检查配制生效情况

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl environment

......
      {trace_vhosts,[]},
      {vhost_restart_strategy,continue},
      {vm_memory_calculation_strategy,rss},
      {vm_memory_high_watermark,{absolute,"120MB"}},
      {vm_memory_high_watermark_paging_ratio,0.5},
      {writer_gc_threshold,1000000000}]},
 {rabbit_common,[]},
......
```

查看到如下配制说明生效。





**运行生产者**

观察客户端输出

```sh
【发送】成功了序列消息:1
【发送】成功了序列消息:2
【单条确认】:等于1已经确认
【发送】成功了序列消息:3
【单条确认】:等于2已经确认
【单条确认】:等于3已经确认
【发送】成功了序列消息:4
【发送】成功了序列消息:5
【发送】成功了序列消息:6
【单条确认】:等于4已经确认
【单条确认】:等于5已经确认
【单条确认】:等于6已经确认
【发送】成功了序列消息:7
【单条确认】:等于7已经确认
......
【发送】成功了序列消息:174
【单条确认】:等于174已经确认
【发送】成功了序列消息:175
【单条确认】:等于175已经确认
【发送】成功了序列消息:176
【单条确认】:等于176已经确认
【发送】成功了序列消息:177
【发送】成功了序列消息:178
【发送】成功了序列消息:179
【发送】成功了序列消息:180
【发送】成功了序列消息:181
【发送】成功了序列消息:182
【发送】成功了序列消息:183
【发送】成功了序列消息:184
【发送】成功了序列消息:185
【发送】成功了序列消息:186
【发送】成功了序列消息:187

```



观察网页端的情况

![image-20230824222907448](img\image-20230824222907448.png)

到此内存资源限制而导致的限流测试完成。







#### 7.7.2 默认的credit flow流控

RabbitMQ Credit Flow Mechanism (信用流控制机制) 是 RabbitMQ 使用的一种流量控制机制，旨在确保生产者（publishers）不会发送太多的消息给消费者（consumers），从而导致系统超载或资源耗尽。这个机制主要是为了保护消费者免受生产者发送太多消息的影响。

以下是 RabbitMQ Credit Flow 机制的基本工作原理：

1. **信用计数器（Credit Counter）**：对于每个消费者，RabbitMQ 维护一个称为信用计数器的值。这个计数器表示消费者当前可以接收多少条消息。
2. **初始信用额度（Initial Credit）**：当一个消费者连接到队列并开始消费消息时，RabbitMQ 为该消费者分配一个初始信用额度。这个额度通常与队列中的未确认消息数量有关。
3. **消费者确认（Consumer Acknowledgments）**：当消费者成功处理一条消息并确认它时，它将会恢复一定数量的信用，这允许 RabbitMQ 将更多的消息发送给消费者。
4. **信用降低（Decreasing Credit）**：当消费者未确认消息超出其信用额度时，其信用额度将降低。这会导致生产者无法继续发送消息给该消费者，直到其信用额度恢复。
5. **自动降低的消费者（Auto-decrease Consumers）**：RabbitMQ 还可以配置为自动降低某些消费者的信用，以避免某个消费者占用太多资源。这通常用于处理慢速或长时间处理的消费者。

这个机制有助于平衡生产者和消费者之间的消息流量，防止生产者发送大量消息导致队列爆满，从而提高系统的稳定性和可靠性。

要注意的是，RabbitMQ 的信用流控制机制是可配置的，您可以根据您的需求来调整信用额度和其他参数，以满足特定的应用场景。此外，RabbitMQ 还提供了一些工具和插件，用于监控和管理流量控制，以确保系统的正常运行。

可以通过查看队列的状态信息来了解 Credit Flow 机制的当前状态。以下是一些常见的方式来查看 Credit Flow 状态：

1. **RabbitMQ Management UI**：RabbitMQ 提供了一个基于 Web 的管理界面，您可以通过该界面查看队列的状态和统计信息，包括队列的消息数量、未确认消息数量以及消费者的状态。要访问管理界面，请确保已启用 RabbitMQ Management 插件。默认情况下，它通常在 http://localhost:15672/ 上运行。

   在管理界面中，您可以选择特定的队列，然后查看其状态和相关的统计信息，包括未确认消息数量。这可以帮助您了解 Credit Flow 是否生效，是否有消费者的信用已用尽。

2. **命令行工具**：您还可以使用 RabbitMQ 的命令行工具来查看队列的状态。以下是一个示例命令，用于查看队列的状态：

   ```bash
   rabbitmqctl list_queues name messages consumers messages_unacknowledged
   ```

   这将显示队列的名称、消息数量、消费者数量以及未确认消息数量。未确认消息数量表示消费者尚未确认的消息数量，这可以用于判断 Credit Flow 是否生效。

3. **监控工具**：您可以使用监控工具（如Prometheus和Grafana）来设置自定义监控和警报，以便实时跟踪队列的状态和信用流控制情况。通过这些工具，您可以创建仪表板来显示队列的各种指标，包括未确认消息数量和消费者的信用。

通过以上方法，您可以监视 RabbitMQ 中队列的状态和 Credit Flow 机制的工作情况，以确保系统的稳定性和可靠性。



![image-20230825093023578](img\image-20230825093023578.png)



#### 7.7.3 Qos机制

>RabbitMQ中有一种Qos保证机制，可以限制channel上接收到的未被Ack的消息数量，如果过这个数量限制RabbitMQ将不会再往消费端推送消息。是一种流控手段，可以防止大量消息瞬时从Broker送达消费端造成消费端巨大压力（甚至压垮消费端）需要注意的是Qos机制仅对消费端推模式有效，对拉模式无效。而且不支持NONE-ACK模式。
>
> 执行`channel.basicConsume`方法之前通过channel.basicQos方法可以设置该数量。消息的发送是异步的，消息的确认也是异步的。在消费慢的时候可以设置Qos的prefetchCount，它表示broker在向消费者发送消息的时候，一旦发送了prefetchCount个消息而没有一个消息确认的时候，就停止发送。消费者确认一个.broker就发送一个，确认两个就发送两个，换句话说，消费者确认多少，broker就发送多少，消费者等待处理的个数永远限制在prefetchCount个。
>
>如果对于每个消息都发送确认，增加了网络流量，此时可以批量确认消息。如果设置了multiple为true，消费者在确认的时候，比如说id是8的消息确认了，则在8之前的所有消息都确认了。

生产者往往是希望自己产生的消息能快速投递出去，而当消息投递太快县城超过了下游的消费速度时就容易出现消息积压、堆积，所以，从上游来讲我们应该在生产端应用程序中也可以加入限流、应急开关等手段，避免超过broker端的极限承载能力或者压垮下游消费者。

 再讲消费者，我们期望消费者能够尽快的消费完消息，而且还要防止瞬时大量消息压垮消费端（推模式），我们期望消费端能够处理速度是最快、最稳定而且还相对均匀（比较理想化）

提供应用吞吐量和缩短消费过程的耗时，主要以下几种方式：

>1. 优化应用程序的性能，缩短响应时间
>2. 增加消费节点实例。
>3. 调整并发消费的线程数。



**测试**

maven导入:

```xml
            <dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>5.9.0</version>
            </dependency>
```



生产者

```java
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class QosProduct {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "qos.ex",
        BuiltinExchangeType.DIRECT,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    for (int i = 0; i < 100; i++) {
      String msg = "这是发送的消息:" + i;
      channel.basicPublish("qos.ex", "qos.rk", null, msg.getBytes(StandardCharsets.UTF_8));
    }
  }
}

```



消费者 ：

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


public class QosConsumer {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");
      
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换器、队列和绑定
    channel.exchangeDeclare("qos.ex", BuiltinExchangeType.DIRECT, false, false, null);
    channel.queueDeclare("qos.qu", false, false, false, null);
    channel.queueBind("qos.qu", "qos.ex", "qos.rk");

    // 设置Qos为5，未被确认ACK的为5，还有一个参数，即是否为全局,true为全局
    channel.basicQos(5);

    channel.basicConsume(
        "qos.qu",
        false,
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {

            LocalDateTime time = LocalDateTime.now();
            System.out.println(
                "[消费]" + time + "+收到的消息:" + new String(body, StandardCharsets.UTF_8));

            int randomSleep = ThreadLocalRandom.current().nextInt(20, 1000);
            try {
              Thread.sleep(randomSleep);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            if (envelope.getDeliveryTag() % 3 == 0) {
              // 进行消息确认
              channel.basicAck(envelope.getDeliveryTag(), true);
            }
          }
        });
  }
}

```



测试

先启动消费都，再启动生产者，查看控制台输出

```tex
[消费]2023-08-25T12:08:13.143+收到的消息:这是发送的消息:0
[消费]2023-08-25T12:08:13.765+收到的消息:这是发送的消息:1
[消费]2023-08-25T12:08:14.127+收到的消息:这是发送的消息:2
[消费]2023-08-25T12:08:14.892+收到的消息:这是发送的消息:3
......
[消费]2023-08-25T12:08:57.437+收到的消息:这是发送的消息:96
[消费]2023-08-25T12:08:57.530+收到的消息:这是发送的消息:97
[消费]2023-08-25T12:08:57.566+收到的消息:这是发送的消息:98
[消费]2023-08-25T12:08:57.649+收到的消息:这是发送的消息:99
```



查看队列的情况：

```sh
[root@nullnull-os ~]# rabbitmqctl list_channels name,prefetch_count,global_prefetch_count --formatter pretty_table
Listing channels ...
┌───────────────────────────────────────────┬────────────────┬───────────────────────┐
│ name                                      │ prefetch_count │ global_prefetch_count │
├───────────────────────────────────────────┼────────────────┼───────────────────────┤
│ 61.170.208.88:59116 -> 10.0.4.16:5672 (1) │ 5              │ 0                     │
└───────────────────────────────────────────┴────────────────┴───────────────────────┘
[root@nullnull-os ~]# 
```



网页端查看

![image-20230825112021066](img\image-20230825112021066.png)







**并行消费者**

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

public class QosThreadConsumer {
  public static void main(String[] args) throws Exception {

    // 资源限制
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    // 设置channel并发请求最大数
    factory.setRequestedChannelMax(5);

    // 自定义线程池工厂
    ThreadFactory thsFactory = Executors.privilegedThreadFactory();
    factory.setThreadFactory(thsFactory);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换器、队列和绑定
    channel.exchangeDeclare("qos.ex", BuiltinExchangeType.DIRECT, false, false, null);
    channel.queueDeclare("qos.qu", false, false, false, null);
    channel.queueBind("qos.qu", "qos.ex", "qos.rk");

    // 设置每秒处理2个
    channel.basicQos(5, true);

    channel.basicConsume(
        "qos.qu",
        false,
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {

            LocalDateTime time = LocalDateTime.now();
            long threadId = Thread.currentThread().getId();
            System.out.println(
                "[消费]"
                    + time
                    + ",线程:"
                    + threadId
                    + ",收到的消息:"
                    + new String(body, StandardCharsets.UTF_8));

            int randomSleep = ThreadLocalRandom.current().nextInt(20, 1000);
            try {
              Thread.sleep(randomSleep);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            if (envelope.getDeliveryTag() % 3 == 0) {
              // 进行消息确认
              channel.basicAck(envelope.getDeliveryTag(), true);
            }
          }
        });
  }
}

```



控制台输出：

```sh
[消费]2023-08-26T09:37:21.430,线程:24,收到的消息:这是发送的消息:0
[消费]2023-08-26T09:37:21.866,线程:25,收到的消息:这是发送的消息:1
[消费]2023-08-26T09:37:22.434,线程:25,收到的消息:这是发送的消息:2
[消费]2023-08-26T09:37:22.847,线程:25,收到的消息:这是发送的消息:3
[消费]2023-08-26T09:37:23.685,线程:25,收到的消息:这是发送的消息:4
[消费]2023-08-26T09:37:23.847,线程:26,收到的消息:这是发送的消息:5
......
[消费]2023-08-26T09:39:10.684,线程:28,收到的消息:这是发送的消息:526
[消费]2023-08-26T09:39:10.695,线程:32,收到的消息:这是发送的消息:527
[消费]2023-08-26T09:39:10.767,线程:32,收到的消息:这是发送的消息:528
......
[消费]2023-08-26T09:39:58.270,线程:27,收到的消息:这是发送的消息:996
[消费]2023-08-26T09:39:58.405,线程:27,收到的消息:这是发送的消息:997
[消费]2023-08-26T09:39:58.575,线程:27,收到的消息:这是发送的消息:998
[消费]2023-08-26T09:39:58.671,线程:27,收到的消息:这是发送的消息:999
```



如果Qos设置为全局，则可以看到到

```sh
[root@nullnull-os ~]# rabbitmqctl list_channels name,prefetch_count,global_prefetch_count --formatter pretty_table
Listing channels ...
┌───────────────────────────────────────────┬────────────────┬───────────────────────┐
│ name                                      │ prefetch_count │ global_prefetch_count │
├───────────────────────────────────────────┼────────────────┼───────────────────────┤
│ 61.170.208.88:60591 -> 10.0.4.16:5672 (1) │ 0              │ 5                     │
├───────────────────────────────────────────┼────────────────┼───────────────────────┤
│ 61.170.208.88:60610 -> 10.0.4.16:5672 (1) │ 0              │ 0                     │
└───────────────────────────────────────────┴────────────────┴───────────────────────┘
[root@nullnull-os ~]# 
```





### 7.8 消息幂等性处理

RabbitMQ层面有实现“去重机制”来保证“恰好一次”吗？答案是没并没有，而且现在主流的消息中间件都没有实现。

一般解决重复消息的办法是：在消费端让我们消费消息操作具有幂等性。

幂等性问题并不是消息系统独有，而是（分布式）系统中普遍存在的问题。一个幂等操作的特点是，其任意多次执行所产生的影响均与一次执行的影响相同。一个幂等的方法，使用同样的参数，对它进行多次调用和一次调用，对系统产生的影响是一样的。

对于幂等的方法，不用担心重复执行会对系统造成任何改变。

业界对于幂等性的一些常见的做法：

>1. 借助数据库唯一索引，重复插入直接报错，事务回滚。以经典的转账为例，为了保证不重复扣款或者重复加钱，系统维护一张资金变动表，这个表里至少需要记录交易单号、变动账户、变动金额等字段，使用交换单号和变动账号做联合唯一索引（单号一般由上游系统生成保证唯一性）这样如果同一笔交易发生重复请求时就会直接报索引冲突，事务直接回滚，现实中数据库唯一索引的方法通常做为兜底的保证；
>
>2. 前置检查机制。还以上面的转账为例，当我们在执行更改帐号余额这个动作之前，先检查下资金变动表是否存在这笔交换相关的记录了，如果已经存在，直接返回。否则执行正常的更新余额的动作。为防止并发问题，通常需要借助“排他锁”，我们也可以使用乐观锁或者CAS机制。乐观锁一般会使用扩展一个版本号字段做判断条件。
>3.  唯一ID机制。比较通用的方法。对于每条消息都可以生成唯一的ID,消费前判断交易表中是否存在，消费成功后将状态写入。可以防止重复消费。



### 7.9 可靠性分析

在使用消息中间件的过程中，难免会出现消息错误或者消息丢失等异常情况。这个时候就需要有一个良好的机制来跟踪记录消息的过程（轨迹溯源），帮助我们排查问题。

在RabbitMQ中可以使用Firehose实现消息的跟踪，Firehose可以记录每一次发送或者消息的记录，方便RabbitMQ的使用都进行调试、排错等。

FireHose的原理是将生产者投递给RabbitMQ的消息，或者RabbitMQ投递给消费者的消息按照指定的格式，发送到默认交换器上，这个默认交换器的名称是：`amq.rabbitmq.trace`它是一个topic类型的交换器。发送到交换器的消息的路由键为`publis.{exchangename}`和`deliver.{queuename}`。其中exchangename和queuename为交换器和队列名字。分别对应生产者投递到交换器的消息和消费者从队列中获取的消息。



![image-20230826103101487](img\image-20230826103101487.png)



上图是一个样例。生产者将消息发送至`trace.ex`交换器，交换器将消息路由至`trace.qu`这个队列，然后由消息者将消息取走。当消息到达`trace.ex`这个队列后，消息就会投递一份到名称为`amq.rabbitmq.trace`的交换器，按收到交换器的名称加上一个前缀变更`publish.trace.ex`作为路由的KEY，投递一份至publishtrace这个队列中；接收消息也样如此，当消费都取走消息时，会将消息发送一份到名称为`amq.rabbitmq.trace`的交换器,按消费者队列的名称加一个前缀变成`deliver.trace.qu`作为路由的KEY，投递至delivertrace这个队列中。

Firehose命令：

```sh
# 开启命令
rabbitmqctl trace_on [-p vhose]
# [-p vhose]是可选参数，用来指定虚拟主机的vhose

# 关闭命令
rabbitmqctl trace_off [-p vhose]
```

Firehose默认情况下处于关闭状态，并且Firehose的状态是非持久化的，会在RabbitMQ服务重启的时候还原成默认的状态。Firehose开启之后会影响RabbitMQ整体服务性能，因为它会引起额外的消息生成、路由和存储 。



#### 7.9.1 Firehose验证

首先开启追溯

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl trace_on -p / 
Starting tracing for vhost "/" ...
Trace enabled for vhost /
```

生产者

```sh
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TraceProduce {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {
      // 定义交换机
      channel.exchangeDeclare("trace.ex", BuiltinExchangeType.DIRECT, false, true, null);
      // 定义队列
      channel.queueDeclare("trace.qu", false, false, true, null);
      // 队列绑定
      channel.queueBind("trace.qu", "trace.ex", "");

      // 定义保留数据队列
      channel.queueDeclare("publishtrace", false, false, false, null);
      // 绑定
      channel.queueBind("publishtrace", "amq.rabbitmq.trace", "publish.trace.ex");

      for (int i = 0; i < 100; i++) {
        String msg = "这是发送的消息:" + i;
        channel.basicPublish("trace.ex", "", null, msg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }
}

```

检查队列的信息:

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌──────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name         │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├──────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ publishtrace │ 100            │ 0                       │ 100      │ 0         │
├──────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ trace.qu     │ 100            │ 0                       │ 100      │ 0         │
└──────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]# 
```

这样生产者发送的消息就已经被保存至publishtrace中了，后缀便可以通过检查队列中的消息，检查消息内容。



消费者

```sh
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import java.nio.charset.StandardCharsets;

public class TraceConsumer {
  public static void main(String[] args) throws Exception {

    // 资源限制
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换机
      channel.exchangeDeclare("trace.ex", BuiltinExchangeType.DIRECT, false, true, null);
      // 定义队列
      channel.queueDeclare("trace.qu", false, false, true, null);
      // 队列绑定
      channel.queueBind("trace.qu", "trace.ex", "");

      // 定义队列
      channel.queueDeclare("delivertrace", false, false, true, null);
      // 队列绑定
      channel.queueBind("delivertrace", "amq.rabbitmq.trace", "deliver.trace.qu");

      // 接收消息
      for (int i = 0; i < 25; i++) {
        GetResponse getResponse = channel.basicGet("trace.qu", true);
        String msg = new String(getResponse.getBody(), StandardCharsets.UTF_8);
        System.out.println("收到的消息：" + msg);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

此处采用的是拉模式，从队列中获取了25条记录，也就是说队列中还剩余75条记录。

首先查看控制台输出：

```sh
收到的消息：这是发送的消息:0
收到的消息：这是发送的消息:1
收到的消息：这是发送的消息:2
收到的消息：这是发送的消息:3
......
收到的消息：这是发送的消息:22
收到的消息：这是发送的消息:23
收到的消息：这是发送的消息:24
```

检查队列的情况：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌──────────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name         │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├──────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ delivertrace │ 25             │ 0                       │ 25       │ 0         │
├──────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ publishtrace │ 100            │ 0                       │ 100      │ 0         │
├──────────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ trace.qu     │ 75             │ 0                       │ 75       │ 0         │
└──────────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
[root@nullnull-os rabbitmq]# 
```

可以发现，拉的消息，都已经被推送到了delivertrace中了。



最后关闭Tracehose

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl trace_off -p / 
Stopping tracing for vhost "/" ...
Trace disabled for vhost /
```

使用Firehose验证完成。



#### 7.9.2 rabbitmq_tracing插件

rabbitmq_tracing插件相当于Firehose的GUI版本，它同样能跟踪RabbitMQ中消息的注入流出情况。rabbitmq_tracing插件同样会对流入流出的消息进行封装，然后将封装后的消息日志存入相应的trace文件中。

```sh
# 开启插件
rabbitmq-plugins enable rabbitmq_tracing

# 关闭插件
rabbitmq-plugins disable rabbitmq_tracing
```

代码使用之前Firehose的代码.

首先开启插件

```sh
[root@nullnull-os rabbitmq]# rabbitmq-plugins disable rabbitmq_tracing
Disabling plugins on node rabbit@nullnull-os:
rabbitmq_tracing
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@nullnull-os...
The following plugins have been disabled:
  rabbitmq_tracing

stopped 1 plugins.
[root@nullnull-os rabbitmq]# rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@nullnull-os
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
[E*] rabbitmq_tracing                  3.8.5
[  ] rabbitmq_trust_store              3.8.5
[e*] rabbitmq_web_dispatch             3.8.5
[  ] rabbitmq_web_mqtt                 3.8.5
[  ] rabbitmq_web_mqtt_examples        3.8.5
[  ] rabbitmq_web_stomp                3.8.5
[  ] rabbitmq_web_stomp_examples       3.8.5
[root@nullnull-os rabbitmq]# 
```

至网页端

![image-20230826113118594](img\image-20230826113118594.png)



填充跟踪信息

name表示rabbitmq_tracing的一个条目的名称，

format可以选择text或者JSON

连接用户名和密码，按创建的用户名管密码，这里填充:root/123456

pattern: 发布的消息: publish.<exchangename>

pattern: 消费的消息: deliver.<queuename>

![image-20230826144002791](img\image-20230826144002791.png)

分别添加生产者的追溯和消费都的追溯,然后分别运行生产者和消费者。

![image-20230826150047626](img\image-20230826150047626.png)

text格式的文件信息:

![image-20230826150205839](img\image-20230826150205839.png)

json格式数据：

![image-20230826150248019](img\image-20230826150248019.png)

在这里还在两个队列：

![image-20230826150358454](img\image-20230826150358454.png)

这两个临时队列，在追溯停止后，也将不存在了。这就是网页端的追溯，相对于Firehose使用起来也更简单。可以直接通过网页端进行查看。





## 8. TTL机制

![image-20230828092522336](img\image-20230828092522336.png)

在支付类的场景中，创建订单成功后，一般会给30分钟左右的等待时间，如果在这段时间内用户没有支付，则默认订单取消。

那如何实现呢？

### 8.1 定期轮询（数据库等）

用户下单成功，将订单信息放入数据库，同时将支付状态放入数据库，用户付款更改数据库状态。定期轮询数据库支付状态，如果超过30分钟就将该订单取消。

优点：设计实现简单

缺点：需要对数据库进行大量的IO操作，效率低下。

### 8.2 定时操作-Timer

```java
  public void timerTask() throws Exception {
    Timer timer = new Timer();

    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            LocalDateTime outTime = LocalDateTime.now();
            System.out.println("用户没有付款，交易取消:" + outTime);
            timer.cancel();
          }
        };

    System.out.println("等待用户付款:" + LocalDateTime.now());
    // 在20秒后执行定时调用
    timer.schedule(task, 5 * 1000);

    Thread.sleep(7000);
  }
```

缺点：

1. timers没有持久化机制。
2. timers不灵活（只可以设置开始时间与重复间隔，但对于等待支付的场景是够用的）
3. timers不能够利用池，一个timer一个线程。
4. times没有真正的管理计划。



### 8.3 使用ScheduledExecutorService

```java
  public void scheduleExecute() throws Exception {
    ThreadFactory factory = Executors.defaultThreadFactory();
    ScheduledExecutorService service = new ScheduledThreadPoolExecutor(10, factory);

    System.out.println("等待用户付款:" + LocalDateTime.now());

    service.schedule(
        new Runnable() {
          @Override
          public void run() {
            LocalDateTime outTime = LocalDateTime.now();
            System.out.println("用户没有付款，交易取消:" + outTime);
          }
        },
        // 等待5秒
        5,
        TimeUnit.SECONDS);

    Thread.sleep(7000);
  }
```

优点：可以多线程执行，一定程序上避免任何任务间的想到影响，单个任务异常不影响其他任务。

缺点：在高并情况下，不建议使用定时任务去做，因为太浪费服务器性能。不推荐。



### 8.4 RabbitMQ的TTL机制

TTL，time to live 的简称，即过期时间。

RabbitMQ可以对消息和队列两个维度来设置TTL。

任何消息中间件的容量和堆积都是有限的，如果有一些消息总是不被消费掉，那么需要有一种过期的机制来做兜底。

目前有两种方法可以设置消息的TTL。

1. 通过Queue属性设置，队列中所有的消息使用相同的过期时间。
2. 对消息自身进行单独的设置。每条消息的TTL可以不同。

如果两种方法一起使用，则消息的TTL以两者之间较小数值为准，通常来说，消息在队列中的生存时间一旦超过设置的TTL值时，就会变更“死信（Dead Message）”，消费者默认就无法再收到该消息。当然，“死信”也是可以被取出来消费的。

#### **使用原生API操作**

在队列上设置消息的过期时间

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProductTTL {
  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {
      // 定义相关的参数
      Map<String, Object> param = new HashMap<>();

      // 设置队列的TTL，即此消息10秒后过期，
      param.put("x-message-ttl", 10000);
      // 设置队列的空闲时间，（如果队列没有消费者或者一直没有使用，队列可存活的时间）
      // 可以理解为没有消费者时，消息队列20秒后删除。
      param.put("x-expires", 20000);

      channel.queueDeclare("ttl.qu", false, false, false, param);

      for (int i = 0; i < 100; i++) {
        String sendMsg = "this is test msg :" + i;
        channel.basicPublish("", "ttl.qu", null, sendMsg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```



启动生产者

检查队列的信息

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name   │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ttl.qu │ 100            │ 0                       │ 100      │ 0         │
└────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
```

经过10秒后

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌────────┬────────────────┬─────────────────────────┬──────────┬───────────┐
│ name   │ messages_ready │ messages_unacknowledged │ messages │ consumers │
├────────┼────────────────┼─────────────────────────┼──────────┼───────────┤
│ ttl.qu │ 0              │ 0                       │ 0        │ 0         │
└────────┴────────────────┴─────────────────────────┴──────────┴───────────┘
```

再经过10秒后

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
```



经过操作后可以发现，此消息队列中的消息存活了10秒，然后消息就被清空了，由于队列的存活时间设置为20秒，没有消费者，所以经过了20秒后，队列就被清空了。



还可以将消息指定在消息中，效果相同

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProductTTLMsg {
  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {
      // 定义相关的参数
      Map<String, Object> param = new HashMap<>();
      // 设置队列的空闲时间，（如果队列没有消费者或者一直没有使用，队列可存活的时间）
      // 可以理解为没有消费者时，消息队列20秒后删除。
      param.put("x-expires", 20000);
      channel.queueDeclare("ttl.qu", false, false, false, param);

      for (int i = 0; i < 100; i++) {
        String sendMsg = "this is test msg :" + i;
        // 在消息上指定存活时间为8秒
        AMQP.BasicProperties properties =
            new AMQP.BasicProperties().builder().expiration("8000").build();
        channel.basicPublish("", "ttl.qu", properties, sendMsg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```



还可以通过命令设置全局的TTL

```sh
rabbitmqctl set_policy TTL ".*" '{"message-ttl": 9000}' --apply-to queues
```





观察队列的policy信息

未设置策略前

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ ttl.tmp.queue │ 0              │ 0                       │ 0        │ 0         │        │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
```

设置策略后

```sh
[root@nullnull-os rabbitmq]# rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name          │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ ttl.tmp.queue │ 0              │ 0                       │ 0        │ 0         │ TTL    │
└───────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
```





默认规则：

1. 如果不设置TTL，则表示此消息不会过期；
2. 如果TTL设置为0，则表示除非此时可以直接将消息投递到消费者，否则该消息会被立即丢弃。



#### **使用SpringBoot**

maven导入

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.2.8.RELEASE</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit-test</artifactId>
            <version>2.2.7.RELEASE</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

springboot配制

```yaml
spring:
  application:
    name: ttl
  rabbitmq:
    host: node1
    port: 5672
    virtual-host: /
    username: root
    password: 123456
```

主入口类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TtlApplication {
  public static void main(String[] args) {
    SpringApplication.run(TtlApplication.class, args);
  }
}

```



创建队列与交换器

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TtlConfig {

  /**
   * 在队列上设置等待时间
   *
   * @return
   */
  @Bean
  public Queue queueTtlWaiting() {
    Map<String, Object> props = new HashMap<>();
    // 设置消息队列的等待时间为10S
    props.put("x-message-ttl", 10000);
    // 设置过期时间为20秒
    props.put("x-expires", 20000);
    Queue queue = new Queue("ttl.wait.qu", false, false, false, props);
    return queue;
  }

  /**
   * 定义交换器
   *
   * @return
   */
  @Bean
  public Exchange exchangeTtlWaiting() {
    return new DirectExchange("ttl.wait.ex", false, false);
  }

  /**
   * 队列与交换器绑定
   *
   * @return
   */
  @Bean
  public Binding bindingTtlWaiting() {
    return BindingBuilder.bind(queueTtlWaiting())
        .to(exchangeTtlWaiting())
        .with("ttl.wait.rk")
        .noargs();
  }

  /**
   * 定义一个普通的队列，不设置TTL，给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Queue queueWaiting() {
    Map<String, Object> props = new HashMap<>();
    // 设置队列的过期时间为20秒，如果没有消费者20秒后，队列被删除
    props.put("x-expires", 20000);

    Queue queue = new Queue("wait.qu", false, false, false, props);
    return queue;
  }

  /**
   * 定义交换器,给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Exchange exchangeWaiting() {
    return new DirectExchange("wait.ex", false, false);
  }

  /**
   * 定义一个队列与交换器的绑定,给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Binding bingingWaiting() {
    return BindingBuilder.bind(queueWaiting()).to(exchangeWaiting()).with("wait.rk").noargs();
  }
}

```



控制器处理

```java
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;

@RestController
public class TtlController {
  @Autowired private AmqpTemplate template;
  /**
   * 队列中设置了TTL
   *
   * @return
   */
  @RequestMapping("/queue/ttl")
  public String sendQueueTtl() {
    String msg = "这是发送的测试TTL数据-TTL-WAITING-MESSAGE";
    // 发送
    template.convertAndSend("ttl.wait.ex", "ttl.wait.rk", msg);
    return "ttl-ok";
  }

  @RequestMapping("/message/ttl")
  public String sendMessageTtl() {
    MessageProperties build =
        MessagePropertiesBuilder.newInstance()
            // 过期时间为5秒
            .setExpiration("5000")
            .build();
    String msgStr = "这是发送的数据-消息的TTL";
    Message msg = new Message(msgStr.getBytes(StandardCharsets.UTF_8), build);
    template.convertAndSend("wait.ex", "wait.rk", msg);
    return "msg-ok";
  }
}

```

启动SpringBoot的工程

首先测试给队列指定过期时间

http://127.0.0.1:8080/queue/ttl

首先观察队列中的数据

```java
[root@nullnull-os ~]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌─────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name        │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├─────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ wait.qu     │ 0              │ 0                       │ 0        │ 0         │ TTL    │
├─────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ ttl.wait.qu │ 1              │ 0                       │ 1        │ 0         │ TTL    │
└─────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
```

可以看到数据已经成功的进入了队列。

再等待10秒，观察队列

```java
[root@nullnull-os ~]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌─────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name        │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├─────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ wait.qu     │ 0              │ 0                       │ 0        │ 0         │ TTL    │
├─────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ ttl.wait.qu │ 0              │ 0                       │ 0        │ 0         │ TTL    │
└─────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘

```

队列中的消息已经过期，但队列还是存在的。

最后超过队列的过期时间后，队列被删除

```java
[root@nullnull-os ~]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
```

消息的过期时间：http://127.0.0.1:8080/message/ttl

此结果与队列的效果一致。



注意，消息的TTL和队列的TTL两个同时设置了，以两者之间较小数值为准。消息的TTL与队列的过期时间之间，同时被设置了也是一样，以较小的数值为准。



## 9. 死信队列

以下单打车为例，用户在下单后，寻找附近的车辆，有一个司机接单了，但是没有在规定的时间到来，导致订单超时了。这时候平台就会再次寻找附近的车辆，下单通知附件的车辆来接你。

这类的场景如果放到MQ上，能实现吗？结合之前的实现已经可以做到订单超时了，但是超时后，目前还没有办法处理，这就需要使用死信队列了。

DLX，全称Dead-Letter-Exchange，死信交换器。消息在一个队列中变成死信（Dead Letter）之后，被重新发送到一个特殊的交换器（DLX）中，同时DLX的队列就称为“死信队列”。

以下几种情况导致消息变为死信：

1. 消息被拒绝（Basic.Reject/Basic.Nack），并设置requeue参数为false.
2. 消息过期。
3. 队列达到最大长度。

对于RabbitMQ来说，DLX是一个非常有用的特性。它可以处理异常您那个况下，消息不能够被消息者正确消费（消费者调用了Basic.Nack或者Basic.Reject）而被置入死信队列中，后续分析程序可以通过消费死信队列中的内容来分析当时所遇到的异常情况。进而可以改善和优化系统。



### 9.1 被拒绝的消息

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DlxRejectProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义测试消息过期的队列和交换器
    channel.exchangeDeclare("dlx.biz.reject.ex", BuiltinExchangeType.FANOUT, false);
    Map<String, Object> argument = new HashMap<>();
    // 当消息过期后，放置于死信队列
    argument.put("x-dead-letter-exchange", "dlx.dead.ex");
    // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
    argument.put("x-dead-letter-routing-key", "rk.dlx.reject");
    channel.queueDeclare("dlx.biz.reject.qu", false, false, false, argument);
    channel.queueBind("dlx.biz.reject.qu", "dlx.biz.reject.ex", "dlx.biz.reject.rk");

    // 定义死信交换器和数据
    channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
    // 用于接收过期后消息的队列
    channel.queueDeclare("dlx.reject.qu", false, false, false, null);
    // 将用于接收过期消息队列与交换器相绑定
    channel.queueBind("dlx.reject.qu", "dlx.dead.ex", "rk.dlx.reject");

    channel.basicConsume(
        "dlx.biz.reject.qu",
        false,
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
            // 进行消息的拒绝,并且不进入队列
            channel.basicReject(envelope.getDeliveryTag(), false);
          }
        });
  }
}
```

定义了一个业务交换器和队列，并将其绑定，在消费端将消息拒绝，并且不重新加入队列。在定义业务队列时，设置了死信交换器即消息拒绝后放置的放置的交换器，并设置了死信的路由key。

然后再定义了死信交换器以及对应拒绝后的接收消息的队列。

当消息到达消费端后，由于开启了手动ACK确认，会进入处理，而客户端的处理是拒绝消息，并且不重新放回队列，就会被放入到死信交换器`dlx.dead.ex`中，而这个消息的死信路由key为`rk.dlx.reject`,而此路由key绑定了队列`dlx.reject.qu`,这样就看到了消息进入了最终我们看到的队列` dlx.reject.qu`中。



![image-20230829215635757](img\image-20230829215635757.png)

观察下队列的情况：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name              │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.reject.qu │ 0              │ 0                       │ 0        │ 1         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.reject.qu     │ 1              │ 0                       │ 1        │ 0         │        │
└───────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]# 
```

通过观察可以发现，消息已经进入了死信队列后的交换器。



### 9.2 过期的消息

```sh
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DlxExpireProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义测试消息过期的队列和交换器
      channel.exchangeDeclare("dlx.biz.expire.ex", BuiltinExchangeType.FANOUT, false);
      Map<String, Object> argument = new HashMap<>();
      // 消息10秒过期
      argument.put("x-message-ttl", 10000);
      // 当消息过期后，放置于死信队列
      argument.put("x-dead-letter-exchange", "dlx.dead.ex");
      // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
      argument.put("x-dead-letter-routing-key", "rk.dlx.expire");
      channel.queueDeclare("dlx.biz.expire.qu", false, false, false, argument);
      channel.queueBind("dlx.biz.expire.qu", "dlx.biz.expire.ex", "dlx.biz.expire.rk");

      // 定义死信交换器和数据
      channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
      // 用于接收过期后消息的队列
      channel.queueDeclare("dlx.expire.qu", false, false, false, null);
      // 将用于接收过期消息队列与交换器相绑定
      channel.queueBind("dlx.expire.qu", "dlx.dead.ex", "rk.dlx.expire");

      // 测试过期消息的发送
      String msgExpire = "测试过期消息";
      channel.basicPublish(
          "dlx.biz.expire.ex", "", null, msgExpire.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```



在队列上设置消息10秒过期。并设置了过期绑定的死信交换器和key。

当消息过期时，就会被放入到`dlx.dead.ex`交换器，并且此消息设置了死信的路由key为`rk.dlx.expire`,而此路由key绑定了`dlx.expire.qu`这个队列，所以消息最终就发送到了`dlx.expire.qu`中



观察队列中的消息

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name              │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.expire.qu     │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.expire.qu │ 1              │ 0                       │ 1        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.reject.qu │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.reject.qu     │ 1              │ 0                       │ 1        │ 0         │        │
└───────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]# 
```

等待10秒后，再查看队列：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name              │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.expire.qu     │ 1              │ 0                       │ 1        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.expire.qu │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.reject.qu │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.reject.qu     │ 1              │ 0                       │ 1        │ 0         │        │
└───────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]# 
```

可以发现，消息已经进入了死信队列了。



### 9.3 超过队列的长度的消息

```sh
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DlxMaxLengthProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义测试消息过期的队列和交换器
      channel.exchangeDeclare("dlx.biz.max.length.ex", BuiltinExchangeType.FANOUT, false);
      Map<String, Object> argument = new HashMap<>();
      // 当消息过期后，放置于死信队列
      argument.put("x-dead-letter-exchange", "dlx.dead.ex");
      // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
      argument.put("x-dead-letter-routing-key", "rk.dlx.max.length");
      // 定义消息的最大长度为2，超过2个，第三个即为死信消息
      argument.put("x-max-length", 2);
      channel.queueDeclare("dlx.biz.max.length.qu", false, false, false, argument);
      channel.queueBind("dlx.biz.max.length.qu", "dlx.biz.max.length.ex", "dlx.biz.max.length.rk");

      // 定义死信交换器和数据
      channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
      // 用于接收过期后消息的队列
      channel.queueDeclare("dlx.max.length.qu", false, false, false, null);
      // 将用于接收过期消息队列与交换器相绑定
      channel.queueBind("dlx.max.length.qu", "dlx.dead.ex", "rk.dlx.max.length");


      String push1 = "测试发送消息1";
      String push2 = "测试发送消息2";
      String push3 = "测试发送消息3";
      channel.basicPublish("dlx.biz.max.length.ex","",null,push1.getBytes(StandardCharsets.UTF_8));
      channel.basicPublish("dlx.biz.max.length.ex","",null,push2.getBytes(StandardCharsets.UTF_8));
      channel.basicPublish("dlx.biz.max.length.ex","",null,push3.getBytes(StandardCharsets.UTF_8));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

```



通过指定消息队列的长度为2，即第三个消息就会进入死信队列。规则还是同之前的过期和拒绝一样。

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌───────────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name                  │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.expire.qu         │ 1              │ 0                       │ 1        │ 0         │        │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.expire.qu     │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.reject.qu     │ 0              │ 0                       │ 0        │ 0         │        │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.max.length.qu     │ 1              │ 0                       │ 1        │ 0         │        │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.biz.max.length.qu │ 2              │ 0                       │ 2        │ 0         │        │
├───────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.reject.qu         │ 1              │ 0                       │ 1        │ 0         │        │
└───────────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]# 
```

最终的消息是两个在业务队列，而超过最大长度的消息在死信队列中。



### 9.4 SpringBoot使用死信队列

**maven导入**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.2.8.RELEASE</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit-test</artifactId>
            <version>2.2.7.RELEASE</version>
            <scope>test</scope>
        </dependency>
```

**连接配制**

```yaml
spring:
  application:
    name: dlx
  rabbitmq:
    host: node1
    port: 5672
    virtual-host: /
    username: root
    password: 123456
```

**主启动类**

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DlxApplication {
  public static void main(String[] args) {
    SpringApplication.run(DlxApplication.class, args);
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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DlxConfig {

  /**
   * 业务队列，并指定了死信交换器
   *
   * @return
   */
  @Bean
  public Queue bizQueue() {
    Map<String, Object> argument = new HashMap<>();

    // 消息在10秒后过期
    argument.put("x-message-ttl", 5000);
    // 设置该队列所关联的死信交换器，当消息超过10秒没有消费，则加入死信队列
    argument.put("x-dead-letter-exchange", "dlx.springboot.ex");
    // 设置该队列所关联的死信交换器的routingKey,如果没有特殊的指定，使用原队列的routingKey.
    argument.put("x-dead-letter-routing-key", "dlx.springboot.rk");
    Queue queue = new Queue("dlx.spring.biz.qu", false, false, false, argument);
    return queue;
  }

  /**
   * 业务交换器
   *
   * @return
   */
  @Bean
  public Exchange bizExchange() {
    return new DirectExchange("dlx.spring.biz.ex", false, false, null);
  }

  @Bean
  public Binding bizBind() {
    return BindingBuilder.bind(bizQueue()).to(bizExchange()).with("dlx.spring.biz.rk").noargs();
  }

  /**
   * 死信队列
   *
   * @return
   */
  @Bean
  public Queue queueDlx() {
    return new Queue("dlx.springboot.expire.qu", false, false, false);
  }

  @Bean
  public Exchange exchangeDlx() {
    return new DirectExchange("dlx.springboot.ex", true, false, null);
  }

  @Bean
  public Binding bindDlx() {
    return BindingBuilder.bind(queueDlx()).to(exchangeDlx()).with("dlx.springboot.rk").noargs();
  }
}

```

**控制层处理**

```java
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;


@RestController
public class BizController {

  @Autowired private AmqpTemplate template;

  @RequestMapping("/expire-dlx")
  public String expireDlx() {
    String msg = "测试发送消息，10秒超时";
    template.convertAndSend(
        "dlx.spring.biz.ex", "dlx.spring.biz.rk", msg.getBytes(StandardCharsets.UTF_8));

    return "expire-dlx";
  }

  @RequestMapping("/dlx/get")
  public String sendDlxMsg() {
    byte[] getMsg = (byte[]) (template.receiveAndConvert("dlx.springboot.expire.qu"));

    return new String(getMsg, StandardCharsets.UTF_8);
  }
}

```



启动项目

然后在浏览器中输入：http://127.0.0.1:8080/expire-dlx

观察队列信息：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌──────────────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name                     │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├──────────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.springboot.expire.qu │ 0              │ 0                       │ 0        │ 0         │        │
├──────────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.spring.biz.qu        │ 1              │ 0                       │ 1        │ 0         │        │
└──────────────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]# 
```

此时数据在业务队列中。等待5秒，再观察队列：

```sh
[root@nullnull-os rabbitmq]#  rabbitmqctl list_queues name,messages_ready,messages_unacknowledged,messages,consumers,policy  --formatter pretty_table
Timeout: 60.0 seconds ...
Listing queues for vhost / ...
┌──────────────────────────┬────────────────┬─────────────────────────┬──────────┬───────────┬────────┐
│ name                     │ messages_ready │ messages_unacknowledged │ messages │ consumers │ policy │
├──────────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.springboot.expire.qu │ 1              │ 0                       │ 1        │ 0         │        │
├──────────────────────────┼────────────────┼─────────────────────────┼──────────┼───────────┼────────┤
│ dlx.spring.biz.qu        │ 0              │ 0                       │ 0        │ 0         │        │
└──────────────────────────┴────────────────┴─────────────────────────┴──────────┴───────────┴────────┘
[root@nullnull-os rabbitmq]#
```

发现数据已经进入了死信队列中。

在浏览器中访问另外的一个接口, http://127.0.0.1:8080/dlx/get

便能得到发送的数据信息：

```
测试发送消息，10秒超时
```

## 10 延迟交换器

 在之前的延迟场景中，消息放入延迟队列的时间都是相同的。比如京东下单后，最大等待24小时进行付款。如果超过24小时还没有付款，那么订单将会被取消。由于下单后使用固定的时间等待。直接采用延迟队列没有任何问题。那如果是会议预订系统的提前20分钟通知功能呢？比如产品经理预订一个会议订在11点，到了10点45分（提前15分钟）的时候就会通知所有参会人员做好准备。会议会在15分钟后开始。如果我们将此通知放入延迟队列。会是什么样子呢？

![image-20230830093940262](img\image-20230830093940262.png)

通过分析可以发现，如果将消息放入延迟队列，由于延迟队列是顺序消费的特性，即按照顺序一个一个的消费。以图为例，可以发现，排在队首的是延迟30秒，会被最先消费，而第一个消费完，第二个和第三个都已经过期了。这时候再通知已经没有意义了。采用延迟队列是不行的。

在RabbitMQ中，可以使用rabbitmq_delayed_message_exchange插件来实现。

这里和TTL方式有很大的不同就是TTL存放的是死信队列（deayquque），而这个插件存放的消息是在延迟交换器里(x-delayed-message-exchange)

![image-20230830095213154](img\image-20230830095213154.png)

1. 生产者将消息和路由键发送至指定的延迟交换器上
2. 延迟交换器将存储消息等待消息到期根据路由键绑定到自己的队列将把消息给它。
3. 队列再把消息发给给监听它的消费者。

1. 下载插件

下载地址：https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/tag/3.8.9

![image-20230830141344703](img\image-20230830141344703.png)



2. 安装及启用插件

将插件拷贝到rabbitMQ-server的安装路径：/usr/lib/rabbitmq/lib/rabbitmq_server-3.8.5/plugins/

```sh
cd /usr/lib/rabbitmq/lib/rabbitmq_server-3.8.5/plugins/

# 检查插件
[root@nullnull-os plugins]# find . -name "*delayed*"
./rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez

# 检查插件
rabbitmq-plugins list
# 启动插件
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```

启用插件过程

```sh
[root@nullnull-os plugins]# find . -name "*delayed*"
./rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez
[root@nullnull-os plugins]# rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@nullnull-os
 |/
[  ] rabbitmq_amqp1_0                  3.8.5
[  ] rabbitmq_auth_backend_cache       3.8.5
[  ] rabbitmq_auth_backend_http        3.8.5
[  ] rabbitmq_auth_backend_ldap        3.8.5
[  ] rabbitmq_auth_backend_oauth2      3.8.5
[  ] rabbitmq_auth_mechanism_ssl       3.8.5
[  ] rabbitmq_consistent_hash_exchange 3.8.5
[  ] rabbitmq_delayed_message_exchange 3.8.9.0199d11c
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
[E*] rabbitmq_tracing                  3.8.5
[  ] rabbitmq_trust_store              3.8.5
[e*] rabbitmq_web_dispatch             3.8.5
[  ] rabbitmq_web_mqtt                 3.8.5
[  ] rabbitmq_web_mqtt_examples        3.8.5
[  ] rabbitmq_web_stomp                3.8.5
[  ] rabbitmq_web_stomp_examples       3.8.5
[root@nullnull-os plugins]# rabbitmq-plugins enable rabbitmq_delayed_message_exchange
Enabling plugins on node rabbit@nullnull-os:
rabbitmq_delayed_message_exchange
The following plugins have been configured:
  rabbitmq_delayed_message_exchange
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_tracing
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@nullnull-os...
The following plugins have been enabled:
  rabbitmq_delayed_message_exchange

started 1 plugins.
[root@nullnull-os plugins]# 
```

如果在未启动的情况下安装插件，在重启后才能生效

```
systemctl restart rabbitmq-server
```

**maven导入**

```xml
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.2.8.RELEASE</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit-test</artifactId>
            <version>2.2.7.RELEASE</version>
            <scope>test</scope>
        </dependency>
```

**springBoot的配制**

application.yml

```yaml
spring:
  application:
    name: delayedQueue
  rabbitmq:
    host: node1
    port: 5672
    virtual-host: /
    username: root
    password: 123456
```



**主入口**

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DelayExchangeApplication {
  public static void main(String[] args) {
    SpringApplication.run(DelayExchangeApplication.class, args);
  }
}
```



**队列配制**

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RabbitListener
@ComponentScan("com.nullnull.learn")
public class DelayConfig {

  @Bean
  public Queue queue() {
    Queue queue = new Queue("delay.qu", false, false, true, null);
    return queue;
  }

  @Bean
  public Exchange exchange() {
    Map<String, Object> argument = new HashMap<>();
    argument.put("x-delayed-type", ExchangeTypes.FANOUT);
    Exchange exchange = new CustomExchange("delay.ex", "x-delayed-message", true, false, argument);
    return exchange;
  }

  @Bean
  public Binding bind() {
    return BindingBuilder.bind(queue()).to(exchange()).with("delay.rk").noargs();
  }
}
```



**controller代码**

```java
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class DelayController {

  @Autowired private AmqpTemplate template;

  @RequestMapping("/notify/{seconds}")
  public String toMeeting(@PathVariable Integer seconds) throws Exception {

    MessageProperties prop = MessagePropertiesBuilder.newInstance()
            //编码
            .setContentEncoding(StandardCharsets.UTF_8.name())
            //延迟时间
            .setHeader("x-delay", seconds * 1000)
            .build();
    

    byte[] meetContainer = (seconds + "秒后通知部门会议").getBytes(StandardCharsets.UTF_8);
    Message msg = new Message(meetContainer, prop);

    template.convertAndSend("delay.ex", "delay.rk", msg);
    return "已经设定好了通知";
  }
}

```

**监听器代码**

```java
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class DelayListener {
  @RabbitListener(queues = "delay.qu")
  public void MeetingAlarm(Message msg, Channel channel) throws Exception {
    String alarmMsg = new String(msg.getBody(), msg.getMessageProperties().getContentEncoding());
    System.out.println("收到提醒：" + alarmMsg);
  }
}
```



**启动测试**

分钟别在浏览器中输入：

http://127.0.0.1:8080/notify/20

http://127.0.0.1:8080/notify/15

http://127.0.0.1:8080/notify/7

http://127.0.0.1:8080/notify/2



观察控制台输出：

```sh
收到提醒：2秒后通知部门会议
收到提醒：7秒后通知部门会议
收到提醒：15秒后通知部门会议
收到提醒：20秒后通知部门会议
```

查看交换器

![image-20230830141504301](img\image-20230830141504301.png)











## 12. 单机多实例部署

在单个节点上实现多个RabbitMQ的实例部署需要有以下几点：

1. 多个RabbitMQ使用的端口号不能冲突。
2. 多个RabbitMQ使用磁盘路径不能冲突。
3. 多个RabbitMQ的配制文件也不能冲突。
4. 多个RabbitMQ的虚拟主机的名称不能重复。

**端口号**

关于使用的端口号的说明：

RABBITMQ_NODE_PORT 用于设置RabbitMQ的服务发现，对外发布的其他端口在这个端口基础上计算得来的

| 端口号       | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| 4369         | epmd,RabbitMQ节点和CLI工具使用对等发现服务                   |
| 5672、5671   | 不带TLS和带TLS的AMQP 0-9-1和1.0客户端使用                    |
| 25672        | 用于节点间和CLI工具通信（Erlang分发服务器端口），并从动态范围分配（默认情况下限制为单个端口、计算AMQP端口+20000）。一般这些端口不应暴露出去。 |
| 35672-35782  | 由CLI工具（Erlang分发客户端端口）用于与节点进行难，并从动态范围（计算为服务器分发端口+10000通过服务器分发端口+10010）分配 |
| 15672        | HTTP的API客户端，管理 UI和RabbitMQadmin（仅在启用了插件的情况下） |
| 61613、61614 | 不带TLS和带TLS的STOMP客户端（仅在启用STOMP插件的情况下）     |
| 1883、8883   | 如果启用了MQTT插件，则不带TLS和具有TLS的MQTT客户端           |
| 15674        | STOMP-over-WebSockets客户端（仅在启用了Web STOMP插件的情况下） |
| 15675        | MQTT-over-WebSockets客户端（仅在启用Web MQTT插件的情况下）   |
| 15692        | Promethenus指标（仅在启用Promethenus插件的情况下）           |

**磁盘路径**

RABBITMQ_NODENAME 用于设置RabbitMQ的节点名称，@前缀是用户名，@后缀是RabbitMQ所在的Linux主机的hostname。

数据存储目录 

```sh
[root@nullnull-os rabbitmq]# pwd
/var/lib/rabbitmq
[root@nullnull-os rabbitmq]# cd mnesia/
[root@nullnull-os mnesia]# ll
drwxr-x---  4 rabbitmq rabbitmq 4096 Aug 30 14:08 rabbit@nullnull-os
-rw-r-----  1 rabbitmq rabbitmq  135 Aug 30 12:35 rabbit@nullnull-os-feature_flags
-rw-r-----  1 rabbitmq rabbitmq    5 Aug 30 12:35 rabbit@nullnull-os.pid
drwxr-x--- 10 rabbitmq rabbitmq 4096 Aug 30 12:35 rabbit@nullnull-os-plugins-expand
[root@nullnull-os mnesia]# ls
rabbit@nullnull-os                rabbit@nullnull-os-plugins-expand
rabbit@nullnull-os-feature_flags
rabbit@nullnull-os.pid
```

日志存储目录 

```sh
[root@nullnull-os rabbitmq]# pwd
/var/log/rabbitmq
[root@nullnull-os rabbitmq]# ls
log                                       rabbit@nullnull-os.log              rabbit@nullnull-os_upgrade.log-20230820.gz
rabbit@nullnull-os.log-20230820.gz  rabbit@nullnull-os_upgrade.log-20230827.gz
rabbit@nullnull-os.log-20230827.gz  rabbit@nullnull-os_upgrade.log-20230903.gz
rabbit@nullnull-os.log-20230903.gz
rabbit@nullnull-os_upgrade.log
[root@nullnull-os rabbitmq]# cd log/
[root@nullnull-os log]# ls
crash.log  crash.log.0  crash.log.1  crash.log.2  crash.log.3  crash.log.4
```



**环境变量**

| **环境变量**                                                 |
| ------------------------------------------------------------ |
| **RABBITMQ_NODE_IP_ADDRESS**                                 |
| 将RabbitMQ绑定到一个网络接口。 如果要绑定多个网络接口，可以在配置文件中配置。 默认值：空字串。表示绑定到所有的网络接口。 |
| **RABBITMQ_NODE_PORT**                                       |
| 默认值：5672                                                 |
| **RABBITMQ_DIST_PORT**                                       |
| RabbitMQ节点之间通信以及节点和CLI工具通信用到的端口。 如果在配置文件中配置了kernel.inet_dist_listen_min 或者kernel.inet_dist_listen_max，则忽略该配置。 默认值：$RABBITMQ_NODE_PORT + 20000 |
| **ERL_EPMD_ADDRESS**                                         |
| epmd 使用的网络接口， epmd 用于节点之间以及节点和CLI之间的通信。 默认值：所有网络接口，包括和IPv4。 |
| **ERL_EPMD_PORT**                                            |
| epmd 使用的端口。 默认值：4369。                             |
| **RABBITMQ_DISTRIBUTION_BUFFER_SIZE**                        |
| 节点之间通信连接使用的发送数据缓冲区大小限制， 单位是KB。推荐使用小于64MB的值。 默认值： |
| 128000。                                                     |
| **RABBITMQ_IO_THREAD_POOL_SIZE**                             |
| Erlang运行时的 I/O 用到的线程数。不推荐小于32的值。 默认值：128（Linux），64（Windows）。 |
| **RABBITMQ_NODENAME**                                        |
| RabbitMQ的节点名称。对于Erlang节点和机器，此名称应该唯一。 通过设置此值，可以在一台机器上多个RabbitMQ节点。 默认值：rabbit@$HOSTNAME（Unix-like），rabbit@%COMPUTERNAME%（Windows）。 |
| **RABBITMQ_CONFIG_FILE**                                     |
| RabbitMQ主要配置文件的路径。例如 /etc/rabbitmq/rabbitmq.conf 或者 /data/configuration/rabbitmq.conf 是新格式的配置文件。 如果是老格式的配置文件，扩展名是 .config 或者不写。 默认值： 对于Unix： $RABBITMQ_HOME/etc/rabbitmq/rabbitmq Debian: /etc/rabbitmq/rabbitmq RPM： /etc/rabbitmq/rabbitmq MacOS(Homebrew)：${install_prefix}/etc/rabbitmq/rabbitmq ， Homebrew的前缀通常是： /usr/local/  Windo %APPDATA%\RabbitMQ\rabbitmq |
| **RABBITMQ_ADVANCED_CONFIG_FILE**                            |
| RabbitMQ带 .config 的高级配置文件路径（基于Erlang配置）。例如， /data/rabbitmq/advanced.config 。默认值： Unix： $RABBITMQ_HOME/etc/rabbitmq/advanced   <br/> Debian： /etc/rabbitmq/advanced<br/> RPM： /etc/rabbitmq/advanced   <br/> MacOS(Homebrew)： ${install_prefix}/etc/rabbitmq/advanced ， 其中Homebrew前缀通常 <br/> 是 /etc/local/  <br/> Windows： %APPDATA%\RabbitMQ\advanced 。 |
| **RABBITMQ_CONF_ENV_FILE**                                   |
| 包含了环境变量定义的文件的目录（不使用 RABBITMQ_ 前缀）。 Windows上的文件名称与其他操作系同。 **默认值**： **UNIX**： $RABBITMQ_HOME/etc/rabbitmq/rabbitmq-env.conf **Ubuntu** **和** **Debian**： /etc/rabbitmq/rabbitmq-env.conf **RPM**： /etc/rabbitmq/rabbitmq-env.conf **Mac**  **(Homebrew)**： ${install_prefix}/etc/rabbitmq/rabbitmq-env.conf ，Homebrew的前缀一般是 /usr/local **Windows**： %APPDATA%\RabbitMQ\rabbitmq-env-conf.bat |
| **RABBITMQ_MNESIA_BASE**                                     |
| 包含了RabbitMQ服务器的节点数据库、消息存储以及 集群状态文件子目录的根目录。除非显式设置了RABBITMQ_MNESIA_DIR 的值。需要确保RabbitMQ用户 在该目录拥有读、写和创建文件以及子目录的该变量一般不要覆盖。一般覆盖 RABBITMQ_MNESIA_DIR 变量。 默认值： Unix：$RABBITMQ_HOME/var/lib/rabbitmq/mnesia Ubuntu和Debian： /var/lib/rabbitmq/mnesia/  RPM： /var/lib/rabbitmq/plugins MacOS（Homebrew）：${install_prefix}/var/lib/rabbitmq/mnesia ， 其中Homebrew的前缀一般是 /usr/local Windows： %APPDATA%\RabbitMQ |
| **RABBITMQ_MNESIA_DIR**                                      |
| RabbitMQ节点存储数据的目录。该目录中包含了数据库、 消息存储、集群成员信息以及节点其他的持状态。 默认值： **通用**  **UNIX**  **包**： $RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME **Ubuntu ** **和** **Debia** $RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME **RPM**： $RABBITMQ_MNESIA_BASE/$RABBITMQ_NO **MacOS (Homebrew)**： ${install_prefix}/var/lib/rabbitmq/mnesia/$RABBITMQ_NODENAME ，Homebrew的前缀一般是 /usr/local **Windows**: %APPDATA%\RabbitMQ\$RABBITMQ_NODENAME |
| **RABBITMQ_PLUGINS_DIR**                                     |
| 存放插件压缩文件的目录。RabbitMQ从此目录解压插件。 跟PATH变量语法类似，多个路径之间使用统的分隔符分隔 （Unix是 : ，Windows是';'）。插件可以安装到该变量指定的任何目录。 路径不要有符。 **默认值**： **通用** **UNIX** **包** ： $RABBITMQ_HOME/plugins **Ubuntu** **和** **Debian** 包： /var/lib/rabbitmq/plugins **RPM**： /var/lib/rabbitmq/plugins **MacOS (Homebrew)**：${install_prefix}/Cellar/rabbitmq/${version}/plugins ， Homebrew的前缀一般是 /usr/local **Windows**： %RABBITMQ_HOME%\plugins |
| **RABBITMQ_PLUGINS_EXPAND_DIR**                              |
| 节点解压插件的目录，并将该目录添加到代码路径。该路径不要包含特殊字符。 **默认值**： **UNIX**：<br/>$RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME-plugins-expand **Ubuntu** **和** **Debian**  packages：<br/>$RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME-plugins-expand **RPM**：$RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME-plugins-expand **MacOS (Homebrew)**：${install_prefix}/var/lib/rabbitmq/mnesia/$RABBITMQ_NODENAME-plugins-expand **Window**%APPDATA%\RabbitMQ\$RABBITMQ_NODENAME-plugins-expand |
| **RABBITMQ_USE_LONGNAME**                                    |
| 当设置为 true 的时候，RabbitMQ会使用全限定主机名标记节点。 在使用全限定域名的环境中使用。重置节点， 不能在全限定主机名和短名之间切换。 默认值： false 。 |
| **RABBITMQ_SERVER_CODE_PATH**                                |
| 当启用运行时的时候指定的外部代码路径（目录）。 当节点启动的时候，这个是值传给 erl 的命令行,默认值：(none) |
| **RABBITMQ_CTL_ERL_ARGS**                                    |
| 当调用 rabbitmqctl 的时候传给 erl 的命令行参数。 可以给Erlang设置使用端口的范围： -kernel,inet_dist_listen_min 35672 -kernel inet_dist_listen_max 35680 默认值：(none) |
| **RABBITMQ_SERVER_ERL_ARGS**                                 |
| 当调用RabbitMQ服务器的时候 erl 的标准命令行参数。 仅用于测试目的。使用该环境变量会覆盖默认,默认值： Unix*： +P 1048576 +t 5000000 + stbt db +zdbbl 128000 Windows：没有 |
| **RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS**                      |
| 调用RabbitMQ服务器的时候传递给 erl 命令的额外参数。 该变量指定的变量追加到默认参数列表,（ RABBITMQ_SERVER_ERL_ARGS ）。 **默认值**： **Unix\* ** **：** 没有 **Windows** **：** 没有 |
| **RABBITMQ_SERVER_START_ARGS**                               |
| 调用RabbitMQ服务器的时候传给 erl 命令的额外参数。该变量不覆盖 RABBITMQ_SERVER_ERL_ARGS |
| **默认值**：没有                                             |
| **RABBITMQ_ENABLED_PLUGINS_FILE**                            |
| 用于指定 enabled_plugins 文件所在的位置。默认： /etc/rabbitmq/enabled_plugins |

**方式1：**

```sh
export RABBITMQ_NODE_PORT=5672
export RABBITMQ_NODENAME=rabbit2
rabbitmq-server

export RABBITMQ_NODE_PORT=5673
export RABBITMQ_NODENAME=rabbit3
rabbitmq-server

export RABBITMQ_NODE_PORT=5674
export RABBITMQ_NODENAME=rabbit4
rabbitmq-server
```

**方式2：**

```sh
RABBITMQ_NODE_PORT=5672 RABBITMQ_NODENAME=rabbit2 rabbitmq-server
RABBITMQ_NODE_PORT=5673 RABBITMQ_NODENAME=rabbit3 rabbitmq-server
RABBITMQ_NODE_PORT=5674 RABBITMQ_NODENAME=rabbit4 rabbitmq-server
```

这两种方式涉及到一个环境变量的可见性问题。

使用分行来执行时，需要使用export来导出环境变量。凡是当前进程的sh文件都可以拿到值。

如果使用一行，则可以直接拿到环境变量的值。



**WEB控制台管理插件**

rabbitMQ从3.3开始禁用使用guest/guest权限通过除localhost外的访问，如果想使用guest/guest通过远程机器访问，需要在rabbitMQ配制文件中设置loopback_user.guest=false,当然还可以自己创建用户。





**单机启动单实例操作--方式1**

```sh
export RABBITMQ_NODE_PORT=5672
export RABBITMQ_NODENAME=rabbit2
rabbitmq-server
```

使可看到如下提示：

```sh
[root@nullnull-os ~]# export RABBITMQ_NODE_PORT=5672
[root@nullnull-os ~]# export RABBITMQ_NODENAME=rabbit2
[root@nullnull-os ~]# rabbitmq-server

  ##  ##      RabbitMQ 3.8.5
  ##  ##
  ##########  Copyright (c) 2007-2020 VMware, Inc. or its affiliates.
  ######  ##
  ##########  Licensed under the MPL 1.1. Website: https://rabbitmq.com

  Doc guides: https://rabbitmq.com/documentation.html
  Support:    https://rabbitmq.com/contact.html
  Tutorials:  https://rabbitmq.com/getstarted.html
  Monitoring: https://rabbitmq.com/monitoring.html

  Logs: /var/log/rabbitmq/rabbit2@nullnull-os.log
        /var/log/rabbitmq/rabbit2@nullnull-os_upgrade.log

  Config file(s): /etc/rabbitmq/rabbitmq.conf

  Starting broker... completed with 5 plugins.

```



输入浏览器地址便可查看WEB管理端:

http://node1:15672/

![image-20230907232456911](img\image-20230907232456911.png)







**单机启动多实例操作--方式2**

1. 创建配制文件，指定web管理的端口号

```sh
# 创建三个管理端的配制文件
mkdir -p /opt/rabbitmq
cd  /opt/rabbitmq
touch rabbitmq1.conf rabbitmq2.conf rabbitmq3.conf

# 修改文件的组归属为rabbitmq
chown :rabbitmq *.conf


# 在rabbitmq1.conf添加以下内容 vi rabbitmq1.conf ,主要是配制端口及放开guest可远程登录,
management.tcp.port=5001
loopback_users.guest=false


# rabbitmq2.conf添加以下内容,vi rabbitmq2.conf, 主要是配制端口及放开guest可远程登录
management.tcp.port=5002
loopback_users.guest=false


# rabbitmq3.conf添加以下内容,vi rabbitmq3.conf ,主要是配制端口及放开guest可远程登录
management.tcp.port=5003
loopback_users.guest=false

```

2. 启动多个实例

```sh
RABBITMQ_NODENAME=rabbit1 RABBITMQ_NODE_PORT=5671  RABBITMQ_CONFIG_FILE=/opt/rabbitmq/rabbitmq1.conf rabbitmq-server

RABBITMQ_NODENAME=rabbit2 RABBITMQ_NODE_PORT=5672  RABBITMQ_CONFIG_FILE=/opt/rabbitmq/rabbitmq2.conf rabbitmq-server

RABBITMQ_NODENAME=rabbit3 RABBITMQ_NODE_PORT=5673  RABBITMQ_CONFIG_FILE=/opt/rabbitmq/rabbitmq3.conf rabbitmq-server
```

当启动功能便会有如下输出：

```sh
[root@nullnull-os ~]# RABBITMQ_NODENAME=rabbit3 RABBITMQ_NODE_PORT=5673  RABBITMQ_CONFIG_FILE=/opt/rabbitmq/rabbitmq3.conf rabbitmq-server

  ##  ##      RabbitMQ 3.8.5
  ##  ##
  ##########  Copyright (c) 2007-2020 VMware, Inc. or its affiliates.
  ######  ##
  ##########  Licensed under the MPL 1.1. Website: https://rabbitmq.com

  Doc guides: https://rabbitmq.com/documentation.html
  Support:    https://rabbitmq.com/contact.html
  Tutorials:  https://rabbitmq.com/getstarted.html
  Monitoring: https://rabbitmq.com/monitoring.html

  Logs: /var/log/rabbitmq/rabbit3@nullnull-os.log
        /var/log/rabbitmq/rabbit3@nullnull-os_upgrade.log

  Config file(s): /opt/rabbitmq/rabbitmq3.conf

  Starting broker... completed with 5 plugins.
```



3. 通过浏览器访问测试：

http://node1:5001/

http://node1:5002/

http://node1:5003/



![image-20230907231241165](img\image-20230907231241165.png)



![image-20230907231330401](img\image-20230907231330401.png)



4. 通过命令行查看状态

```sh
rabbitmqctl status --node rabbit1@nullnull-os
```

可看到信息如下：

```sh
[root@nullnull-os ~]# rabbitmqctl status --node rabbit1@nullnull-os
Status of node rabbit1@nullnull-os ...
Runtime

OS PID: 14034
OS: Linux
Uptime (seconds): 1306
RabbitMQ version: 3.8.5
Node name: rabbit1@nullnull-os
Erlang configuration: Erlang/OTP 23 [erts-11.0.2] [source] [64-bit] [smp:2:2] [ds:2:2:10] [async-threads:64] [hipe]
Erlang processes: 450 used, 1048576 limit
Scheduler run queue: 1
Cluster heartbeat timeout (net_ticktime): 60

Plugins

Enabled plugin file: /etc/rabbitmq/enabled_plugins
Enabled plugins:

 * rabbitmq_delayed_message_exchange
 * rabbitmq_tracing
 * rabbitmq_management
 * amqp_client
 * rabbitmq_web_dispatch
 * cowboy
 * cowlib
 * rabbitmq_management_agent

Data directory

Node data directory: /var/lib/rabbitmq/mnesia/rabbit1@nullnull-os
Raft data directory: /var/lib/rabbitmq/mnesia/rabbit1@nullnull-os/quorum/rabbit1@nullnull-os

Config files

 * /opt/rabbitmq/rabbitmq1.conf

Log file(s)

 * /var/log/rabbitmq/rabbit1@nullnull-os.log
 * /var/log/rabbitmq/rabbit1@nullnull-os_upgrade.log

Alarms

(none)

Memory

Calculation strategy: rss
Memory high watermark setting: 0.4 of available memory, computed to: 1.5893 gb
code: 0.0278 gb (32.15 %)
other_proc: 0.0238 gb (27.49 %)
other_system: 0.014 gb (16.16 %)
allocated_unused: 0.0097 gb (11.25 %)
plugins: 0.0057 gb (6.63 %)
other_ets: 0.0033 gb (3.83 %)
atom: 0.0014 gb (1.64 %)
metrics: 0.0002 gb (0.24 %)
binary: 0.0002 gb (0.21 %)
mgmt_db: 0.0002 gb (0.2 %)
mnesia: 0.0001 gb (0.1 %)
quorum_ets: 0.0 gb (0.06 %)
msg_index: 0.0 gb (0.03 %)
connection_other: 0.0 gb (0.0 %)
connection_channels: 0.0 gb (0.0 %)
connection_readers: 0.0 gb (0.0 %)
connection_writers: 0.0 gb (0.0 %)
queue_procs: 0.0 gb (0.0 %)
queue_slave_procs: 0.0 gb (0.0 %)
quorum_queue_procs: 0.0 gb (0.0 %)
reserved_unallocated: 0.0 gb (0.0 %)

File Descriptors

Total: 2, limit: 99904
Sockets: 0, limit: 89911

Free Disk Space

Low free disk space watermark: 0.05 gb
Free disk space: 68.6983 gb

Totals

Connection count: 0
Queue count: 0
Virtual host count: 1

Listeners

Interface: [::], port: 25671, protocol: clustering, purpose: inter-node and CLI tool communication
Interface: [::], port: 5671, protocol: amqp, purpose: AMQP 0-9-1 and AMQP 1.0
Interface: [::], port: 5001, protocol: http, purpose: HTTP API
[root@nullnull-os ~]# 
```



5. 停止实例

```sh
rabbitmqctl -n rabbit1 stop

rabbitmqctl -n rabbit2 stop

rabbitmqctl -n rabbit3 stop
```



至此在单个机器上管理多个实例，便 已经成功了。



## 13. MQ的集群的管理

首先搭建RabitMQ集群。使用镜像队列，并借助HAProxy实现负载均衡。

**环境说明**

| 名称  | IP            | 说明  |
| ----- | ------------- | ----- |
| node1 | 192.168.3.150 | 节点1 |
| node2 | 192.168.3.151 | 节点2 |
| node3 | 192.168.3.152 | 节点3 |

### 3.1 搭建rabbitMQ集群

**1. 安装基础软件**

以下操作在每台节点上都需要执行.

```sh
# 安装socat
yum install -y socat

# 安装erlang和rabbitMQ-server
rpm -ivh erlang-23.0.2-1.el7.x86_64.rpm rabbitmq-server-3.8.5-1.el7.noarch.rpm
```



**2. 配制.erlang.cookie文件**

由于现在已经使集群了，用户登录后，需要能操作任意一台节点，此`.erlang.cookie`文件是会话的cookie.一旦登录将会全局有效。

如果没有`.erlang.cookie`文件，可以手动创建`/var/lib/rabbitmq/.erlang.cookie`,生成cookie字符串，或者启动一次RabbitMQ自动生成该文件。生产中建议使用第三方工具生成。

首先可以启动一个`rabbitmq-server`，以生成cookie文件。

```sh
systemctl start rabbitmq-server
```

开始同步`.erlang.cookie`文件。RabbitMQ的集群依赖Erlang的分布式特性，需要保持Erlang Cookie的一致才能实现集群节点的认证和通信，可以直接使用SCP命令从node节点远程传输。

```sh
# 直接拷贝到node2的当前目录.
#scp /var/lib/rabbitmq/.elrang.cookie node2:`pwd`

scp /var/lib/rabbitmq/.erlang.cookie root@node1:/var/lib/rabbitmq/
scp /var/lib/rabbitmq/.erlang.cookie root@node3:/var/lib/rabbitmq/

```









## 结束
