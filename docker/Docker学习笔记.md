# Docker学习笔记

## Docker安装

### 系统安装

当安装Docker的时候，会涉及两个主要组件：

Docker CLI： 客户端

Docker daemon: 有时也被称为服务端或者引擎

**硬件叫低要求**

| 序号 | 硬件 | 要求                                        |
| ---- | ---- | ------------------------------------------- |
| 1    | CPU  | 至少2核                                     |
| 2    | 内存 | 至少2G                                      |
| 3    | 硬盘 | 至少50G                                     |
| 4    | 系统 | 系统至少在Centos7.8以上<br/>需要小于Cetnso8 |

下载系统

```sh
http://mirrors.aliyun.com/centos/7/isos/x86_64/
```

目前为Centos7.9版本

**软件源配制**

```sh
# 1.下载安装wget
yum install -y wget

# 2.备份默认的yum
mv /etc/yum.repos.d /etc/yum.repos.d.backup

#3.设置新的yum目录
mkdir -p /etc/yum.repos.d

# 4.下载阿里yum配置到该目录中，选择对应版本
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

#5.更新epel源为阿里云epel源
mv /etc/yum.repos.d/epel.repo /etc/yum.repos.d/epel.repo.backup
mv /etc/yum.repos.d/epel-testing.repo /etc/yum.repos.d/epel-testing.repo.backup
wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo

# 6.重建缓存
yum clean all
yum makecache

#7.看一下yum仓库有多少包
yum repolist

```

**升级系统内核**

```sh
rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm
yum --enablerepo=elrepo-kernel install -y kernel-lt
grep initrd16 /boot/grub2/grub.cfg
grub2-set-default 0


reboot
```

**查看系统内核版本**

```sh
uname -r
uname -a

# 参考样例:
[root@dockeros ~]# uname -r
5.4.262-1.el7.elrepo.x86_64
[root@dockeros ~]# uname -a
Linux dockeros 5.4.262-1.el7.elrepo.x86_64 #1 SMP Wed Nov 29 00:56:30 EST 2023 x86_64 x86_64 x86_64 GNU/Linux
[root@dockeros ~]#
```

**操作命令：**

```sh
# 查看CPU
lscpu

# 查看内存
free
free -h

# 查看硬盘
fdisk -l

# 关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

# 关闭selinux
sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/sysconfig/selinux
setenforce 0

# 网络过滤
vi /etc/sysctl.conf

net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-arptables = 1
net.ipv4.ip_forward=1
net.ipv4.ip_forward_use_pmtu = 0

# 生效命令
sysctl --system


# 命令补全
# 安装bash-completion
yum -y install bash-completion bash-completion-extras

# 使用bash-completion
source /etc/profile.d/bash_completion.sh


# 上传文件
yum -y install lrzsz

# 使用方法
1.鼠标拖拽上传文件
2.下载文件
    2.1下载一个文件
   sz filename 
   
    2.2下载多个文件
   sz filename1 filename2
   
    2.3下载dir目录下所有文件，不包含dir下的文件夹
   sz dir/*
```

### docker安装

```sh
# 安装docker前置条件
yum install -y yum-utils device-mapper-persistent-data lvm2

# 添加源
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

yum makecache fast

yum update -y



# 安装最新版：推荐大家安装最新版本
yum install -y docker-ce docker-ce-cli containerd.io

# 安装指定版本：
# 语法规则：yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io
yum -y install docker-ce-18.06.3.ce-3.el7 docker-ce-cli.x86_64
 
yum install -y docker-ce-19.03.9-3.el7 docker-ce-cli-19.03.9-3.el7 



# 开机自动启动docker服务
systemctl enable docker

# 启动docker服务
systemctl start docker

# 查看docker版本
docker -v

```

**阿里云安装说明**

### CentOS 7 (使用yum进行安装)

```sh
# step 1: 安装必要的一些系统工具
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
# Step 2: 添加软件源信息
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# Step 3: 更新并安装 Docker-CE
sudo yum makecache fast
sudo yum -y install docker-ce
# Step 4: 开启Docker服务
sudo service docker start

注意：其他注意事项在下面的注释中
# 官方软件源默认启用了最新的软件，您可以通过编辑软件源的方式获取各个版本的软件包。例如官方并没有将测试版本的软件源置为可用，你可以通过以下方式开启。同理可以开启各种测试版本等。
# vim /etc/yum.repos.d/docker-ce.repo
#   将 [docker-ce-test] 下方的 enabled=0 修改为 enabled=1
#
# 安装指定版本的Docker-CE:
# Step 1: 查找Docker-CE的版本:
# yum list docker-ce.x86_64 --showduplicates | sort -r
#   Loading mirror speeds from cached hostfile
#   Loaded plugins: branch, fastestmirror, langpacks
#   docker-ce.x86_64            17.03.1.ce-1.el7.centos            docker-ce-stable
#   docker-ce.x86_64            17.03.1.ce-1.el7.centos            @docker-ce-stable
#   docker-ce.x86_64            17.03.0.ce-1.el7.centos            docker-ce-stable
#   Available Packages
# Step2 : 安装指定版本的Docker-CE: (VERSION 例如上面的 17.03.0.ce.1-1.el7.centos)
# sudo yum -y install docker-ce-[VERSION]
# 注意：在某些版本之后，docker-ce安装出现了其他依赖包，如果安装失败的话请关注错误信息。例如 docker-ce 17.03 之后，需要先安装 docker-ce-selinux。
# yum list docker-ce-selinux- --showduplicates | sort -r
# sudo yum -y install docker-ce-selinux-[VERSION]

# 通过经典网络、VPC网络内网安装时，用以下命令替换Step 2中的命令
# 经典网络：
# sudo yum-config-manager --add-repo http://mirrors.aliyuncs.com/docker-ce/linux/centos/docker-ce.repo
# VPC网络：
# sudo yum-config-manager --add-repo http://mirrors.could.aliyuncs.com/docker-ce/linux/centos/docker-ce.repo
```



**docker镜像加速**

使用阿里云做镜像加速

```sh
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://ys2mfbsh.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

到此阿云的镜像回事配制已经完成

检查下配制是否生产

```sh
[root@dockeros ~]# docker info
Client: Docker Engine - Community
 Version:    24.0.7
 Context:    default
 Debug Mode: false
 Plugins:
  buildx: Docker Buildx (Docker Inc.)
    Version:  v0.11.2
    Path:     /usr/libexec/docker/cli-plugins/docker-buildx
  compose: Docker Compose (Docker Inc.)
    Version:  v2.21.0
    Path:     /usr/libexec/docker/cli-plugins/docker-compose

Server:
 Containers: 0
  Running: 0
  Paused: 0
  Stopped: 0
 Images: 0
 Server Version: 24.0.7
 Storage Driver: overlay2
  Backing Filesystem: xfs
  Supports d_type: true
  Using metacopy: false
  Native Overlay Diff: true
  userxattr: false
 Logging Driver: json-file
 Cgroup Driver: cgroupfs
 Cgroup Version: 1
 Plugins:
  Volume: local
  Network: bridge host ipvlan macvlan null overlay
  Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
 Swarm: inactive
 Runtimes: io.containerd.runc.v2 runc
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: d8f198a4ed8892c764191ef7b3b06d8a2eeb5c7f
 runc version: v1.1.10-0-g18a0cb0
 init version: de40ad0
 Security Options:
  seccomp
   Profile: builtin
 Kernel Version: 3.10.0-1160.102.1.el7.x86_64
 Operating System: CentOS Linux 7 (Core)
 OSType: linux
 Architecture: x86_64
 CPUs: 4
 Total Memory: 15.51GiB
 Name: dockeros
 ID: c185e0e5-64d8-44ba-b405-02b4344cbaf1
 Docker Root Dir: /var/lib/docker
 Debug Mode: false
 Experimental: false
 Insecure Registries:
  127.0.0.0/8
 Registry Mirrors:
  https://ys2mfbsh.mirror.aliyuncs.com/
 Live Restore Enabled: false

```

在Registry中，可以发现配制的镜像加速已经生效.



## docker镜像相关的命令

本章节记录的docker命令在大部分情况下的使用，如果想了解每一个选项的细节，请参考官方文档，根据docker官网案例，可以分为以下几类：

- Docker环境信息 —— docker [info  | version]
- 容器生命周期管理 —— docker [create | exec | run | start | stop | restart | kill | rm | pause | unpause ]
- 容器操作命令 —— docker [ps | inspect | top | attach | wait | export | port | rename | stat]
- 容器rootfs命令——docker [commit | cp | diff]
- 镜像仓库 ——docker [login | pull | push | search]
- 本地镜像管理——docker [build | images | rmi | tag | save | import | load]
- 容器资源管理——docker [volume | network]
- 系统日志信息——docker [events | history logs]

官网地址：

```
https://docs.docker.com/engine/reference/run/
```

基本的容器的操作命令的一个结构：

![image-20231207233006739](./images\image-20231207233006739.png)



**Docker镜像（image)**

docker Hub类似maven远程仓库地址：

```sh
https://hub.docker.com/
```

### **pull命令**

下载镜像命令，镜像从远程镜像仓库服务的仓库中下载。默认情况下，镜像会从Docker Hub的仓库中摘取。



```sh
# docker官方镜像的容器地址，搜索tomcat
https://hub.docker.com/_/tomcat

# 下载tomcat的9.0.20版本
docker pull tomcat:9.0.20-jre8
docker pull tomcat:9.0.20-jre8-alpine
docker pull tomcat:9.0.20-jre8-slim


# centos7.8.2003的系统
https://hub.docker.com/_/centos
docker pull centos:centos7.8.2003
docker pull centos:centos7.9.2009

# ubuntu的系统镜像
https://hub.docker.com/_/ubuntu
docker pull ubuntu:20.04

# debian
https://hub.docker.com/_/debian
docker pull debian:10.6-slim
docker pull debian:10.6


# alpine
https://hub.docker.com/_/alpine
docker pull alpine:3.12.1

```

**常用参数**

- -a, --all-tags=true|false 是否获取仓库中的所有镜像，默认为false
- --disable-content-trus 跳过镜像内容的校验，默认为true

### images命令

通过images，可以列出本机已有的镜像

```sh
docker images
docker images ls
```

样例：

```sh
[root@dockeros ~]# docker images
REPOSITORY   TAG                  IMAGE ID       CREATED       SIZE
ubuntu       20.04                ba6acccedd29   2 years ago   72.8MB
centos       centos7.9.2009       eeb6ee3f44bd   2 years ago   204MB
debian       10.6-slim            79fa6b1da13a   3 years ago   69.2MB
debian       10.6                 ef05c61d5112   3 years ago   114MB
alpine       3.12.1               d6e46aa2470d   3 years ago   5.57MB
centos       centos7.8.2003       afb6fca791e0   3 years ago   203MB
tomcat       9.0.20-jre8-alpine   387f9d021d3a   4 years ago   108MB
tomcat       9.0.20-jre8-slim     66140ac62adb   4 years ago   225MB
tomcat       9.0.20-jre8          e24825d32965   4 years ago   464MB
```

REPOSITORY : 表示镜像的仓库源

TAG: 镜像的标签

IMAGE ID : 镜像的ID

CREATED： 镜像创建的时间

SIZE: 镜像的大小



### save 命令

save命令可以用于保存一个或者多个镜像至文件中

```sh
# 保存一个镜像
docker save tomcat:9.0.20-jre8-alpine -o tomcat9-alpine.image
docker save tomcat:9.0.20-jre8-alpine > tomcat9-alpine.image


#  保存多个镜像
docker save \
> debian:10.6-slim \
> alpine:3.12.1 \
> ubuntu:20.04 \
> -o image-mult.tar

```

- `-o` 输出到文件中



### load命令

将save保存下载的镜像加载到仓库中

```sh
docker load -i tomcat9-alpine.image
docker load <  image-mult.tar
```

**常用参数**

- --input , -i 指定导入的文件
- --quiet , -q 精简输出信息



### search命令

search命令不推荐使用，最主要是不够直观

```sh
docker search tomcat
```

样例

```
[root@dockeros ~]# docker search tomcat
NAME                          DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
tomcat                        Apache Tomcat is an open source implementati…   3615      [OK]       
tomee                         Apache TomEE is an all-Apache Java EE certif…   113       [OK]       
bitnami/tomcat                Bitnami Tomcat Docker Image                     51                   [OK]
bitnamicharts/tomcat                                                          0                    
secoresearch/tomcat-varnish   Tomcat and Varnish 5.0                          0                    [OK]
vulhub/tomcat                                                                 0                    
islandora/tomcat                                                              0                    
wnprcehr/tomcat                                                               0                    
hivdb/tomcat-with-nucamino                                                    0                    
jumpserver/tomcat                                                             0                    
sismics/tomcat                Apache Tomcat Servlet Container                 1                    
eclipse/rdf4j-workbench       Dockerfile for Eclipse RDF4J Server and Work…   6                    
semoss/docker-tomcat          Tomcat, Java, Maven, and Git on top of debian   0                    [OK]
eclipse/hadoop-dev            Ubuntu 14.04, Maven 3.3.9, JDK8, Tomcat 8       0                    [OK]
gbif/ipt                      The GBIF Integrated Publishing Toolkit (IPT)…   2                    
dhis2/base-dev                Images in this repository contains DHIS2 WAR…   0                    
eclipse/alpine_jdk8           Based on Alpine 3.3. JDK 1.8, Maven 3.3.9, T…   1                    [OK]
misolims/miso-base            MySQL 5.7 Database and Tomcat 8 Server neede…   0                    
dhis2/base                    Images in this repository contains DHIS2 WAR…   0                    
jelastic/tomcat               An image of the Tomcat Java application serv…   4                    
cfje/tomcat-resource          Tomcat Concourse Resource                       2                    
rightctrl/tomcat              CentOS , Oracle Java, tomcat application ssl…   7                    [OK]
amd64/tomcat                  Apache Tomcat is an open source implementati…   8                    
arm64v8/tomcat                Apache Tomcat is an open source implementati…   9                    
tomcat2111/papercut-mf        PaperCut MF Application Server                  0                    
[root@dockeros ~]# 
```

这用参数

- -f,--filter filter: 过滤输出的内容
- --limit int 指定搜索内容展示个数
- --no-index 不截断输出内容
- --no-trunc: 不戴断输出内容

### inspect命令

通过docker inspect命令，可以获取镜像的详细信息，其中包括创建者、各层的数字摘要等

docker inspect返回的是json格式的信息，如果想获取其中指定的一项内容，可以通过-f来指定，如获取镜像大小

```sh
docker inspect tomcat:9.0.20-jre8-alpine

docker inspect -f {{".Size"}} tomcat:9.0.20-jre8-alpine
```

例如：

```sh
[root@dockeros ~]# docker inspect tomcat:9.0.20-jre8-alpine
[
    {
        "Id": "sha256:387f9d021d3ae76a2dafd661142a2659939389def2f390d40ab73bd49df242ba",
        "RepoTags": [
            "tomcat:9.0.20-jre8-alpine"
        ],
        "RepoDigests": [
            "tomcat@sha256:17accf0afeeecce0310d363490cd60a788aa4630ab9c9c802231d6fbd4bb2375"
        ],
        "Parent": "",
        "Comment": "",
        "Created": "2019-05-16T00:57:50.863755612Z",
        "Container": "24ab35a23fd83a3748e701f6fdca86f3618ffb29a29bd32ed2db6f9b4336056d",
        "ContainerConfig": {
            "Hostname": "24ab35a23fd8",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "8080/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/tomcat/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin",
                "LANG=C.UTF-8",
                "JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk/jre",
                "JAVA_VERSION=8u212",
                "JAVA_ALPINE_VERSION=8.212.04-r0",
                "CATALINA_HOME=/usr/local/tomcat",
                "TOMCAT_NATIVE_LIBDIR=/usr/local/tomcat/native-jni-lib",
                "LD_LIBRARY_PATH=/usr/local/tomcat/native-jni-lib",
                "GPG_KEYS=05AB33110949707C93A279E3D3EFE6B686867BA6 07E48665A34DCAFAE522E5E6266191C37C037D42 47309207D818FFD8DCD3F83F1931D684307A10A5 541FBE7D8F78B25E055DDEE13C370389288584E7 61B832AC2F1C5A90F0F9B00A1C506407564C17A3 79F7026C690BAA50B92CD8B66A3AD3F4F22C4FED 9BA44C2621385CB966EBA586F72C284D731FABEE A27677289986DB50844682F8ACB77FC2E86E29AC A9C5DF4D22E99998D9875A5110C01C5A2F6059E7 DCFD35E0BF8CA7344752DE8B6FB21E8933C60243 F3A04C595DB5B6A5F1ECA43E3B7BBB100D811BBE F7DA48BB64BCB84ECBA7EE6935CD23C10D498E23",
                "TOMCAT_MAJOR=9",
                "TOMCAT_VERSION=9.0.20",
                "TOMCAT_SHA512=6d2df51f0bfc6a90cfca61c86473b8843da4162c430ab06b8f66f364931f3d8a3ad399703acdd600ff4f633d7d6725edf05d5d5d19534716a2f3f9f5238a32a0",
                "TOMCAT_TGZ_URLS=https://www.apache.org/dyn/closer.cgi?action=download&filename=tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://www.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://archive.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz",
                "TOMCAT_ASC_URLS=https://www.apache.org/dyn/closer.cgi?action=download&filename=tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://www.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://archive.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc"
            ],
            "Cmd": [
                "/bin/sh",
                "-c",
                "#(nop) ",
                "CMD [\"catalina.sh\" \"run\"]"
            ],
            "ArgsEscaped": true,
            "Image": "sha256:a9ebb0fcc929c1b8ddd758efe32531ddadb78547da29a01dd8854c2d32ab5eb8",
            "Volumes": null,
            "WorkingDir": "/usr/local/tomcat",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {}
        },
        "DockerVersion": "18.06.1-ce",
        "Author": "",
        "Config": {
            "Hostname": "",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "8080/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/tomcat/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin",
                "LANG=C.UTF-8",
                "JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk/jre",
                "JAVA_VERSION=8u212",
                "JAVA_ALPINE_VERSION=8.212.04-r0",
                "CATALINA_HOME=/usr/local/tomcat",
                "TOMCAT_NATIVE_LIBDIR=/usr/local/tomcat/native-jni-lib",
                "LD_LIBRARY_PATH=/usr/local/tomcat/native-jni-lib",
                "GPG_KEYS=05AB33110949707C93A279E3D3EFE6B686867BA6 07E48665A34DCAFAE522E5E6266191C37C037D42 47309207D818FFD8DCD3F83F1931D684307A10A5 541FBE7D8F78B25E055DDEE13C370389288584E7 61B832AC2F1C5A90F0F9B00A1C506407564C17A3 79F7026C690BAA50B92CD8B66A3AD3F4F22C4FED 9BA44C2621385CB966EBA586F72C284D731FABEE A27677289986DB50844682F8ACB77FC2E86E29AC A9C5DF4D22E99998D9875A5110C01C5A2F6059E7 DCFD35E0BF8CA7344752DE8B6FB21E8933C60243 F3A04C595DB5B6A5F1ECA43E3B7BBB100D811BBE F7DA48BB64BCB84ECBA7EE6935CD23C10D498E23",
                "TOMCAT_MAJOR=9",
                "TOMCAT_VERSION=9.0.20",
                "TOMCAT_SHA512=6d2df51f0bfc6a90cfca61c86473b8843da4162c430ab06b8f66f364931f3d8a3ad399703acdd600ff4f633d7d6725edf05d5d5d19534716a2f3f9f5238a32a0",
                "TOMCAT_TGZ_URLS=https://www.apache.org/dyn/closer.cgi?action=download&filename=tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://www.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz \thttps://archive.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz",
                "TOMCAT_ASC_URLS=https://www.apache.org/dyn/closer.cgi?action=download&filename=tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://www.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc \thttps://archive.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.tar.gz.asc"
            ],
            "Cmd": [
                "catalina.sh",
                "run"
            ],
            "ArgsEscaped": true,
            "Image": "sha256:a9ebb0fcc929c1b8ddd758efe32531ddadb78547da29a01dd8854c2d32ab5eb8",
            "Volumes": null,
            "WorkingDir": "/usr/local/tomcat",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": null
        },
        "Architecture": "amd64",
        "Os": "linux",
        "Size": 107881954,
        "VirtualSize": 107881954,
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/76a969bb3e2a81dc50d62ab3916fa0422946832d3521ba1621a24108ff7f987b/diff:/var/lib/docker/overlay2/2932f798aac8f161adfafb4704f93a6e204a44ac8b7f1ffa2fd26c0bb7f99fab/diff:/var/lib/docker/overlay2/06b9d577eccd5bbad73d0018452e23a508066956ebceff055d88b8ffe578d0af/diff:/var/lib/docker/overlay2/13499780bcc8d61a7b51fbe5c9d397bcd9e5aa1c78655c45224ad5c9faf6800d/diff:/var/lib/docker/overlay2/2cec91e030e7d3041443d78871bdf6b90873e6964f0875f68eb0bba378041bb6/diff",
                "MergedDir": "/var/lib/docker/overlay2/7ceb0be1e3e98d7adcf864a5d231959ed43ce52b15e042bd535a0d18611c4abb/merged",
                "UpperDir": "/var/lib/docker/overlay2/7ceb0be1e3e98d7adcf864a5d231959ed43ce52b15e042bd535a0d18611c4abb/diff",
                "WorkDir": "/var/lib/docker/overlay2/7ceb0be1e3e98d7adcf864a5d231959ed43ce52b15e042bd535a0d18611c4abb/work"
            },
            "Name": "overlay2"
        },
        "RootFS": {
            "Type": "layers",
            "Layers": [
                "sha256:f1b5933fe4b5f49bbe8258745cf396afe07e625bdab3168e364daf7c956b6b81",
                "sha256:9b9b7f3d56a01e3d9076874990c62e7a516cc4032f784f421574d06b18ef9aa4",
                "sha256:edd61588d12669e2d71a0de2aab96add3304bf565730e1e6144ec3c3fac339e4",
                "sha256:48988bb7b8615c1af859b04776a46ddeb4d2bd1aa9b0a9a3ac099cf3f73ab29d",
                "sha256:d1d0b1719b963394ea25c3936f2d67f8bdb6c90bd7166137167c7b7f135742e1",
                "sha256:b7d850202de0030b2e8e076a40364bb695c891339ba321e6a743e9db0cc72766"
            ]
        },
        "Metadata": {
            "LastTagTime": "0001-01-01T00:00:00Z"
        }
    }
]
[root@dockeros ~]# docker inspect -f {{".Size"}} tomcat:9.0.20-jre8-alpine
107881954

```



### history 命令

一个命令由多个层组成的，那么如何知道各层的具体内容呢？这便可以通过docker history命令，列出各个层的创建信息

```
docker history tomcat:9.0.20-jre8-alpine
```

样例：

```sh
[root@dockeros ~]# docker history tomcat:9.0.20-jre8-alpine
IMAGE          CREATED       CREATED BY                                      SIZE      COMMENT
387f9d021d3a   4 years ago   /bin/sh -c #(nop)  CMD ["catalina.sh" "run"]    0B        
<missing>      4 years ago   /bin/sh -c #(nop)  EXPOSE 8080                  0B        
<missing>      4 years ago   /bin/sh -c set -e  && nativeLines="$(catalin…   0B        
<missing>      4 years ago   /bin/sh -c set -eux;   apk add --no-cache --…   23MB      
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_ASC_URLS=https…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_TGZ_URLS=https…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_SHA512=6d2df51…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_VERSION=9.0.20    0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_MAJOR=9           0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV GPG_KEYS=05AB33110949…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV LD_LIBRARY_PATH=/usr/…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV TOMCAT_NATIVE_LIBDIR=…   0B        
<missing>      4 years ago   /bin/sh -c #(nop) WORKDIR /usr/local/tomcat     0B        
<missing>      4 years ago   /bin/sh -c mkdir -p "$CATALINA_HOME"            0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV PATH=/usr/local/tomca…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV CATALINA_HOME=/usr/lo…   0B        
<missing>      4 years ago   /bin/sh -c set -x  && apk add --no-cache   o…   79.4MB    
<missing>      4 years ago   /bin/sh -c #(nop)  ENV JAVA_ALPINE_VERSION=8…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV JAVA_VERSION=8u212       0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV PATH=/usr/local/sbin:…   0B        
<missing>      4 years ago   /bin/sh -c #(nop)  ENV JAVA_HOME=/usr/lib/jv…   0B        
<missing>      4 years ago   /bin/sh -c {   echo '#!/bin/sh';   echo 'set…   87B       
<missing>      4 years ago   /bin/sh -c #(nop)  ENV LANG=C.UTF-8             0B        
<missing>      4 years ago   /bin/sh -c #(nop)  CMD ["/bin/sh"]              0B        
<missing>      4 years ago   /bin/sh -c #(nop) ADD file:a86aea1f3a7d68f6a…   5.53MB 
```



### tag命令

标记本地镜像，将其归入某一仓库

```sh
docker tag tomcat:9.0.20-jre8-alpine nullnull/tomcat:9
```

样例:

```sh
[root@dockeros ~]# docker tag tomcat:9.0.20-jre8-alpine nullnull/tomcat:9
[root@dockeros ~]# docker images
REPOSITORY        TAG                  IMAGE ID       CREATED       SIZE
ubuntu            20.04                ba6acccedd29   2 years ago   72.8MB
centos            centos7.9.2009       eeb6ee3f44bd   2 years ago   204MB
debian            10.6-slim            79fa6b1da13a   3 years ago   69.2MB
debian            10.6                 ef05c61d5112   3 years ago   114MB
alpine            3.12.1               d6e46aa2470d   3 years ago   5.57MB
centos            centos7.8.2003       afb6fca791e0   3 years ago   203MB
tomcat            9.0.20-jre8-alpine   387f9d021d3a   4 years ago   108MB
nullnull/tomcat   9                    387f9d021d3a   4 years ago   108MB
tomcat            9.0.20-jre8-slim     66140ac62adb   4 years ago   225MB
tomcat            9.0.20-jre8          e24825d32965   4 years ago   464MB
```



### rmi命令

 如下两个命令都可以删除镜像：

```sh
docker rmi nullnull/tomcat:9
docker image rm nullnull/tomcat:9
```

- -f ,-force: 强制删除镜像，即便有容器引用该镜像
- -no-prune: 不要删除未带标签的父镜像

通过id删除镜像

除了通过标签来删除镜像外，还可以通过镜像id来删除镜像。一旦通过ID来删除镜像，它会先尝试删除所有指向该镜像的标签，然后再删除镜像本身

```sh
docker rmi 387f9d021d3a

# 如果存在多个镜像指向同一个ID，删除时，需要添加强制删除的参数，将多个镜像进行删除
docker rmi -f  387f9d021d3a
```

如果不添加强制删除的标识将报错：

```
[root@dockeros ~]# docker rmi 387f9d021d3a
Error response from daemon: conflict: unable to delete 387f9d021d3a (must be forced) - image is referenced in multiple repositories
```

总结：

- 推荐通过image的名称删除镜像。
- image的ID在终端长度未完成显示，ID值会出现重复。
- 极力不推荐进行暴力的（添加-f参数）进行删除操作
- 推荐正确的做法：先删除引用这个镜像的容器；再删除这个镜像。



### 清理镜像

在使用docker一段时间后，系统一般都会残存一些临时的、没有被使用的镜像文件，可以通过以下命令进行清理。执行命令后还会告诉我们释放了多少存储空间

```sh
docker image prune
```

常用参数

- -a,--all 删除所有没有用的镜像，而不仅仅是临时文件
- -f, --force: 强制删除镜像文件，无需弹出提示确认。



## Docker容器常用命令



### 新建并启动容器

语法：

```sh
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

运行容器

```sh
docker run -it --rm -p 8080:8080 tomcat:9.0.20-jre8-alpine
```

**常见参数：**

docker run命令常用参数比较多，这里列出开发常用参数：

- -d,--detach=false: 后台运行容器，并返回容器的ID
- -i， --interactive=false : 以交互模式运行容器，通常与-t同时使用
- -P, --publish-all=false: 随机端口映射，容器内部端口随机映射到主机的端口。不推荐使用该参数
- -p, --publish=[] 指定端口映射，格式为主机（宿主）端口：容器端口，推荐使用此参数配制
- -t,--tty=false 为容器重新分配一个伪输入终端，通常与-i同时使用
- --name=“tomcat” 为容器指定一个名称
- -h ,--hostname="tomcathost" 指定容器的hostname
- -e,--env=[] 设置环境变量，容器中可以使用该环境变量。
- --net="bridge" 指定容器的网络连接类型，支持bridge/host/nonde/container:四种类型
- --link=[] 添加链接到另一个容器；不推荐使用此参数
- -v, --volume: 绑定一个卷
- --privileged=false 指定容器是否为特权容器，特权容器拥有所有的capabilities
- --restart=no 指定容器停止后的重启策略
  - no:容器退出时不重启
  - on-failure: 容器故障退出（返回值非零）时重启
  - always： 容器退出时总是重启，推荐此参数
- --rm=false,指定容器停止后自动删除容器，不能以docker run -d启动的容器

​	

### 容器日志

docker logs: 获取容器的日志

```sh
docker logs [OPTIONS] CONTAINER
```

日志查看

```sh
 docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine

 docker logs -f tomcat9
```

样例输出：

```sh
[root@dockeros ~]# docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine
c4df62105d0ec2a8220e21391afa35fd7fd584f0e1c3d03323678a9fe328e200
[root@dockeros ~]# docker logs -f tomcat9
Using CATALINA_BASE:   /usr/local/tomcat
Using CATALINA_HOME:   /usr/local/tomcat
Using CATALINA_TMPDIR: /usr/local/tomcat/temp
Using JRE_HOME:        /usr/lib/jvm/java-1.8-openjdk/jre
Using CLASSPATH:       /usr/local/tomcat/bin/bootstrap.jar:/usr/local/tomcat/bin/tomcat-juli.jar
09-Dec-2023 15:20:28.775 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server version name:   Apache Tomcat/9.0.20
09-Dec-2023 15:20:28.777 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server built:          May 3 2019 22:26:00 UTC
.......
09-Dec-2023 15:20:29.266 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
09-Dec-2023 15:20:29.283 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
09-Dec-2023 15:20:29.286 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [326] milliseconds
```

常用参数：

- -f 跟踪日志输出
- --tail 仅列出最新N条容器日志



### 删除容器

docker rm 删除一个或者多个容器，docker rm 命令只能删除处于终止或退出状态的容器，不能删除还处于运行状态的容器

```sh
docker rm [OPTIONS] CONTAINER [CONTAINER...]
```

命令操作

```sh
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine

# 需要首先停止运行中的容器，再做删除操作，否则无法删除
docker stop tomcat9

# 按照容器名称进行删除 
docker rm tomcat9

# 按照容器的ID进行删除 
docker rm c4df62105d0e
```

常用参数：

- -f 通过SIGKILL信号强制删除一个运行中的容器
- -l 移除容器间的网络连接，而非容器本身
- -v 删除与容器关联的卷







