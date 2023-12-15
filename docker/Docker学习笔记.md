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



## Docker镜像相关的命令

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

详细的信息输出:

```sh
[root@dockeros ~]# docker run --help

Usage:  docker run [OPTIONS] IMAGE [COMMAND] [ARG...]

Create and run a new container from an image

Aliases:
  docker container run, docker run

Options:
      --add-host list                  Add a custom host-to-IP mapping (host:ip)
      --annotation map                 Add an annotation to the container (passed through to the OCI runtime) (default map[])
  -a, --attach list                    Attach to STDIN, STDOUT or STDERR
      --blkio-weight uint16            Block IO (relative weight), between 10 and 1000, or 0 to disable (default 0)
      --blkio-weight-device list       Block IO weight (relative device weight) (default [])
      --cap-add list                   Add Linux capabilities
      --cap-drop list                  Drop Linux capabilities
      --cgroup-parent string           Optional parent cgroup for the container
      --cgroupns string                Cgroup namespace to use (host|private)
                                       'host':    Run the container in the Docker host's cgroup namespace
                                       'private': Run the container in its own private cgroup namespace
                                       '':        Use the cgroup namespace as configured by the
                                                  default-cgroupns-mode option on the daemon (default)
      --cidfile string                 Write the container ID to the file
      --cpu-period int                 Limit CPU CFS (Completely Fair Scheduler) period
      --cpu-quota int                  Limit CPU CFS (Completely Fair Scheduler) quota
      --cpu-rt-period int              Limit CPU real-time period in microseconds
      --cpu-rt-runtime int             Limit CPU real-time runtime in microseconds
  -c, --cpu-shares int                 CPU shares (relative weight)
      --cpus decimal                   Number of CPUs
      --cpuset-cpus string             CPUs in which to allow execution (0-3, 0,1)
      --cpuset-mems string             MEMs in which to allow execution (0-3, 0,1)
  -d, --detach                         Run container in background and print container ID
      --detach-keys string             Override the key sequence for detaching a container
      --device list                    Add a host device to the container
      --device-cgroup-rule list        Add a rule to the cgroup allowed devices list
      --device-read-bps list           Limit read rate (bytes per second) from a device (default [])
      --device-read-iops list          Limit read rate (IO per second) from a device (default [])
      --device-write-bps list          Limit write rate (bytes per second) to a device (default [])
      --device-write-iops list         Limit write rate (IO per second) to a device (default [])
      --disable-content-trust          Skip image verification (default true)
      --dns list                       Set custom DNS servers
      --dns-option list                Set DNS options
      --dns-search list                Set custom DNS search domains
      --domainname string              Container NIS domain name
      --entrypoint string              Overwrite the default ENTRYPOINT of the image
  -e, --env list                       Set environment variables
      --env-file list                  Read in a file of environment variables
      --expose list                    Expose a port or a range of ports
      --gpus gpu-request               GPU devices to add to the container ('all' to pass all GPUs)
      --group-add list                 Add additional groups to join
      --health-cmd string              Command to run to check health
      --health-interval duration       Time between running the check (ms|s|m|h) (default 0s)
      --health-retries int             Consecutive failures needed to report unhealthy
      --health-start-period duration   Start period for the container to initialize before starting health-retries countdown (ms|s|m|h) (default 0s)
      --health-timeout duration        Maximum time to allow one check to run (ms|s|m|h) (default 0s)
      --help                           Print usage
  -h, --hostname string                Container host name
      --init                           Run an init inside the container that forwards signals and reaps processes
  -i, --interactive                    Keep STDIN open even if not attached
      --ip string                      IPv4 address (e.g., 172.30.100.104)
      --ip6 string                     IPv6 address (e.g., 2001:db8::33)
      --ipc string                     IPC mode to use
      --isolation string               Container isolation technology
      --kernel-memory bytes            Kernel memory limit
  -l, --label list                     Set meta data on a container
      --label-file list                Read in a line delimited file of labels
      --link list                      Add link to another container
      --link-local-ip list             Container IPv4/IPv6 link-local addresses
      --log-driver string              Logging driver for the container
      --log-opt list                   Log driver options
      --mac-address string             Container MAC address (e.g., 92:d0:c6:0a:29:33)
  -m, --memory bytes                   Memory limit
      --memory-reservation bytes       Memory soft limit
      --memory-swap bytes              Swap limit equal to memory plus swap: '-1' to enable unlimited swap
      --memory-swappiness int          Tune container memory swappiness (0 to 100) (default -1)
      --mount mount                    Attach a filesystem mount to the container
      --name string                    Assign a name to the container
      --network network                Connect a container to a network
      --network-alias list             Add network-scoped alias for the container
      --no-healthcheck                 Disable any container-specified HEALTHCHECK
      --oom-kill-disable               Disable OOM Killer
      --oom-score-adj int              Tune host's OOM preferences (-1000 to 1000)
      --pid string                     PID namespace to use
      --pids-limit int                 Tune container pids limit (set -1 for unlimited)
      --platform string                Set platform if server is multi-platform capable
      --privileged                     Give extended privileges to this container
  -p, --publish list                   Publish a container's port(s) to the host
  -P, --publish-all                    Publish all exposed ports to random ports
      --pull string                    Pull image before running ("always", "missing", "never") (default "missing")
  -q, --quiet                          Suppress the pull output
      --read-only                      Mount the container's root filesystem as read only
      --restart string                 Restart policy to apply when a container exits (default "no")
      --rm                             Automatically remove the container when it exits
      --runtime string                 Runtime to use for this container
      --security-opt list              Security Options
      --shm-size bytes                 Size of /dev/shm
      --sig-proxy                      Proxy received signals to the process (default true)
      --stop-signal string             Signal to stop the container
      --stop-timeout int               Timeout (in seconds) to stop a container
      --storage-opt list               Storage driver options for the container
      --sysctl map                     Sysctl options (default map[])
      --tmpfs list                     Mount a tmpfs directory
  -t, --tty                            Allocate a pseudo-TTY
      --ulimit ulimit                  Ulimit options (default [])
  -u, --user string                    Username or UID (format: <name|uid>[:<group|gid>])
      --userns string                  User namespace to use
      --uts string                     UTS namespace to use
  -v, --volume list                    Bind mount a volume
      --volume-driver string           Optional volume driver for the container
      --volumes-from list              Mount volumes from the specified container(s)
  -w, --workdir string                 Working directory inside the container
[root@dockeros ~]# 
```





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



### 列出容器

语法

```sh
docker ps [OPTIONS]	
```

执行命令：

```sh
# 查看运行中的容器
docker ps

# 查看所有容器，包括停止的容器、创建未运行的等等
docker ps -a	
```

样例：

```sh
[root@dockeros ~]# docker ps -a
CONTAINER ID   IMAGE                       COMMAND             CREATED        STATUS         PORTS                                       NAMES
c4df62105d0e   tomcat:9.0.20-jre8-alpine   "catalina.sh run"   12 hours ago   Up 2 minutes   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   tomcat9
[root@dockeros ~]# 
```

输出详细介绍：

| 列           | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| CONTAINER ID | 容器的ID                                                     |
| IMAGE        | 使用镜像                                                     |
| COMMAND      | 启动容器时运行的命令                                         |
| CREATED      | 创建的时间                                                   |
| STATUS       | 容器的状态：状态共7种<br/>- created(已创建) <br/>-restarting（重启中）<br/>-running（运行中）<br/>-removing（迁移中）<br/>-paused（暂停中）<br/>-exited（停止）<br/>-dead（死亡） |
| PORTS        | 容器中的端口端口信息和使用连接类型(tcp/udp)                  |
| NAMES        | 自动分配的容器连接                                           |



实用技巧

```sh
# 停止所有运行容器 docker ps -qa 输出的是容器的ID
docker stop $(docker ps -qa)

# 删除所有的容器
docker rm $(docker ps -qa)

# 先停止运行中的容器再删除 
docker rm $(docker stop $(docker ps -q))

# 删除所有镜像
docker rmi $(docker images -q)
```



### 创建容器

docker create 创建一个新的容器但不启动它，用法与docker run命令相同

```sh
docker create [OPTIONS] IMAGE [COMMAND] [ARG...]
```

测试命令

```
docker create -it --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine
```

常见参数用法与docker run命令相同。



### 启动、重启、终止容器

docker start 启动一个或多个已经被停止的容器

docker stop 停止一个运行中的容器

docker restart 重启容器

语法：

```sh
docker start [OPTIONS] CONTAINER [CONTAINER...]
docker stop [OPTIONS] CONTAINER [CONTAINER...]
docker restart [OPTIONS] CONTAINER [CONTAINER...]
```

执行命令：

```sh
docker start tomcat9
docker stop tomcat9
docker restart tomcat9
```



### 进入容器

docker exec :  在运行的容器中执行命令。早期有attach命令，对于阻塞命令会去等待，所有不方便。在进入Docker 1.3.0后提供了exec可以在容器内直接执行任意命令.

语法：

```sh
docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
```

命令：

```sh
# 启动一个容器
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine
# 进入一个容器
docker exec -it tomcat9 /bin/bash
docker exec -it tomcat9 bash
# sh进入与上面的方式，进入的目录会存在区别
docker exec -it tomcat9 sh
```

常见参数

- -t 即没有附加也保持STDIN打开
- -t 分配一个伪终端。

### 查看容器

docker inspect: 获取容器/镜像的元数据

语法：

```sh
docker inspect [OPTIONS] NAME|ID [NAME|ID...]
```

操作命令：

```sh
docker rm $(docker stop $(docker ps -q))
# 启动一个容器
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine
# 查看一个容器的元数据
docker inspect tomcat9	
```

常用参数：

- -f 指定返回值的模板文件
- -s 显示总文件大小
- --type 为指定类型返回JSON

样例：

```sh
[root@dockeros ~]# docker inspect tomcat9
[
    {
        "Id": "10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32",
        "Created": "2023-12-10T03:43:23.483835969Z",
        "Path": "catalina.sh",
        "Args": [
            "run"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 5520,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2023-12-10T03:43:24.501237091Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:387f9d021d3ae76a2dafd661142a2659939389def2f390d40ab73bd49df242ba",
        "ResolvConfPath": "/var/lib/docker/containers/10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32/hostname",
        "HostsPath": "/var/lib/docker/containers/10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32/hosts",
        "LogPath": "/var/lib/docker/containers/10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32/10d9d326ced8870b328c7f2806dc85f9658d7c286143c0e716894d6256441d32-json.log",
        "Name": "/tomcat9",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "default",
            "PortBindings": {
                "8080/tcp": [
                    {
                        "HostIp": "",
                        "HostPort": "8080"
                    }
                ]
            },
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "ConsoleSize": [
                66,
                214
            ],
            "CapAdd": null,
            "CapDrop": null,
            "CgroupnsMode": "host",
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": [],
            "BlkioDeviceWriteBps": [],
            "BlkioDeviceReadIOps": [],
            "BlkioDeviceWriteIOps": [],
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": null,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware",
                "/sys/devices/virtual/powercap"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/05a73552b81d379ceebbf4a18a0fabb004fc89e2cce47dab1f03ed090e97cd2d-init/diff:/var/lib/docker/overlay2/b6987c4aa41ed5c5987becdd6e10d2ba45e9eeb25f0a185c55828f18531c24c4/diff:/var/lib/docker/overlay2/13848467fdceabed51baad2490c1aff4f9c5db59332ed233bcd5fbe69c8cda05/diff:/var/lib/docker/overlay2/9f6ac985a0aa7aeec68338aba7577f8df0324edd6c9bceceb484e32d9fdfff0a/diff:/var/lib/docker/overlay2/859fbd9b91bf75f3a1127e4f8307d4cb6ce809619549e29a4b4bc3a99000fc03/diff:/var/lib/docker/overlay2/501d73a549e5de96e6950f5f2210da363c6315f5d23932b964637e00438dc3f1/diff:/var/lib/docker/overlay2/9acb4042de754938778df544a227716e7ea9b9aec176672b35e7f1a0c5cc7743/diff",
                "MergedDir": "/var/lib/docker/overlay2/05a73552b81d379ceebbf4a18a0fabb004fc89e2cce47dab1f03ed090e97cd2d/merged",
                "UpperDir": "/var/lib/docker/overlay2/05a73552b81d379ceebbf4a18a0fabb004fc89e2cce47dab1f03ed090e97cd2d/diff",
                "WorkDir": "/var/lib/docker/overlay2/05a73552b81d379ceebbf4a18a0fabb004fc89e2cce47dab1f03ed090e97cd2d/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [],
        "Config": {
            "Hostname": "10d9d326ced8",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "8080/tcp": {}
            },
            "Tty": true,
            "OpenStdin": true,
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
            "Image": "tomcat:9.0.20-jre8-alpine",
            "Volumes": null,
            "WorkingDir": "/usr/local/tomcat",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {}
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "1696f22f6a5ade9749cd41f325e3ae33b9b9ea53f1c46b8fdb170ec4493fb15d",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {
                "8080/tcp": [
                    {
                        "HostIp": "0.0.0.0",
                        "HostPort": "8080"
                    },
                    {
                        "HostIp": "::",
                        "HostPort": "8080"
                    }
                ]
            },
            "SandboxKey": "/var/run/docker/netns/1696f22f6a5a",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "8796a34d4ead2253f5c38367305f04735b17a491b1939e08ee8a0c9dac1a537b",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "15661a14fe2997dbfc17014ff9ff21572d94f98f5230ecef37a57039fb9df10b",
                    "EndpointID": "8796a34d4ead2253f5c38367305f04735b17a491b1939e08ee8a0c9dac1a537b",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]
[root@dockeros ~]# 
```



### 更新容器

docker update 可以动态地更新容器配制。可以更新一个或多个容器本所。多个容器名称或者ID之前使用空格分隔。但update命令不是很成熟，有很多本配制项不能动态更新。推荐还是rm容器后，再新run一个新的镜像

语法：

```sh
docker update [OPTIONS] CONTAINER [CONTAINER...]
```

执行命令

```sh
docker rm $(docker stop $(docker ps -q))
# 启动一个容器
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine

# 更新容器restart策略
docker update --restart always tomcat9
```



### 杀掉容器

docker kill 杀掉一个运行中的容器

语法：

```sh
docker kill [OPTIONS] CONTAINER [CONTAINER...]
```

执行命令

```sh
docker rm $(docker stop $(docker ps -q))
# 启动一个容器
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine

docker kill tomcat9

docker ps 

docker ps -a

docker start tomcat9
```

常用参数

- -s 向容器发送一个信号



### Docker常用命令汇总

![image-20231210120034149](.\images\image-20231210120034149.png)





## Docker安装软件

### docker安装nginx

```sh
# nginx官方docker地址:
https://hub.docker.com/_/nginx

# 摘取镜像
docker pull nginx:1.19.3-alpine

# 备份镜像
docker save -o nginx-1.19.3.image nginx:1.19.3-alpine

# 导入镜像
docker load < nginx-1.19.3.image

# 运行镜像
docker run -itd --name nginx -p 80:80 nginx:1.19.3-alpine

# 进入容器
docker exec -it nginx sh

# 查看nginx的页面信息
cat /usr/share/nginx/html/index.html
# 或者编辑下vi /usr/share/nginx/html/index.html
```

浏览器访问

```sh
http://192.168.5.20/
```

此为宿主机的IP，由于映射为80端口，无需写入端口

![image-20231210123845573](./images\image-20231210123845573.png)



### Docker安装mysql

docker中mysql的官网地址:

```sh
# mysql官方docker地址:
https://hub.docker.com/_/mysql

# 摘取镜像
docker pull mysql:5.7.31

# 备份镜像
docker save -o mysql-5.7.31.image  mysql:5.7.31 

# 导入镜像 
docker load < mysql-5.7.31.image

# 运行镜像
docker run -itd --name mysql-5.7.31 --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin mysql:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci

```

**参数说明**

-e,--env[] 设置环境变量，容器中可以使用该环境变量
#官网中给出进入容器的第三种方式，结合前面的/bin/bash,sh，这是第三种，向my.conf文件中追加相关配制项,--character-set-server=utf8 --collation-server=utf8_general_ci
--privileged=true 对于熟悉UNIX类系统的人，都习惯于通过使用sudo来随意提升自己的权限，成为root用户，在使用docker容器的过程中知道，docker提供了一个--privileged的参数，它与随意使用sudo有很大的区别，它可能让你的应用程序面临不必要的风险，下面将向你展示以root身份运行的区别，以及特权的实际含义

作为Root运行

Docker允许在宿主机上隔离一个进程，capabilities和文件系统，但是大多数容器上都是默认以root身份。

避免以root运行

虽然在容器内以root身份运行是很正常的，但如果你想加固容器的安全性，还是应该避免这样做。

特权模式

--privileged 可以不受限制地访问任何自己的系统调用。在正常的操作中，即使容器有root，Docker也会限制容器的Linux Capabilities的，这种限制包括像CAP_AUDIT_WRITE这样的东西，它允许覆盖内核的审计日志-你的容器化工作负载很可能不需要这个Capabilities，所以特权只应该在你真正需要它的特定设置中使用，简而言之，它给容器提供了几乎所有主机（作为root）可做的事情的权限

本质上，它就是一个免费的通行证，可以逃避容器所包含的文件系统，进程、楼台ets套接字等，当然它有特定的使用场景，比如在很多CI/CD系统中需要使用Docker IN Docker模式（在Docker容器内部需要Docker守护进程）以及需要极端网络的地方。

测试mysql

```sh
# 进入容器，使用bash命令进入容器
docker exec -it mysql-5.7.31 bash

# 登录mysql
mysql -uroot -padmin

# 切换到MySQL数据库
use mysql

# 查看库信息
show databases;

exit

exit
```

日志查看

```sh
[root@dockeros ~]# docker exec -it mysql-5.7.31 bash
root@c2078b9dccfc:/# mysql -uroot -padmin
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 5
Server version: 5.7.31 MySQL Community Server (GPL)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use mysql;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
4 rows in set (0.00 sec)

mysql> exit
Bye
root@c2078b9dccfc:/# 
```



### Docker安装Zookeeper

```sh
# 官方docker地址
https://hub.docker.com/_/zookeeper

# 摘取镜像
docker pull zookeeper:3.6.2

# 导出镜像
docker save -o zookeeper.3.6.2.image zookeeper:3.6.2

# 导入镜像
docker load < zookeeper.3.6.2.image
```

单机版zookeeper运行

```sh
docker run -itd --name zookeeper --restart always -p 2181:2181 zookeeper:3.6.2

docker exec -it zookeeper /bin/bash

cat /etc/issue
# 返回Debian GNU/Linux 10 \n \l
```

通过客户端工具查看zookeeper的内容

```sh
#工具地址：
https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip
```

查看zookeeper的信息

![image-20231210185645056](./images\image-20231210185645056.png)



### ActiveMQ

activity没有官网镜像版本，需要使用第三方的镜像。

```sh
# 镜像地址
https://hub.docker.com/r/webcenter/activemq

# 拉取镜像
docker pull webcenter/activemq:5.14.3

# 导出镜像
docker save webcenter/activemq:5.14.3 -o activity-5.14.3.image

# 导入镜像
docker load < activity-5.14.3.image

```

单机版运行

```sh
# 61616为ActiveMQ的外部访问端口，8161为WEB页面访问的端口
docker run -itd --name activmq --restart always -p 61616:61616 -p 8161:8161 webcenter/activemq:5.14.3

# 查看容器的系统版本信息
docker exec -it activmq /bin/bash
cat /etc/issue
# 返回
Ubuntu 15.10 \n \l
```

在WEB端口访问ActivityMQ管理界面，登录账号的密码默认：admin/admin

```
http://192.168.5.20:8161
```

![image-20231210190429729](.\images\image-20231210190429729.png)



## Docker网络

### 网络模式

| Docker网络模式  | 配置                      | 说明                                                         |
| --------------- | ------------------------- | ------------------------------------------------------------ |
| host模式        | -net=host                 | 容器和宿主机共享Network namespace。<br/>容器将不会虚拟出自己的网卡，配置自己的IP等，而是使用宿主机的IP和端口 |
| container模式   | -net=container:NAME_or_ID | 容器和另外一个容器共享Network namespace。<br/>kubernetes中的pod就是多个容器共享一个Network namespace。<br/>创建的容器不会创建自己的网卡，配置自己的IP，<br/>而是和一个指定的容器共享IP、端口范围 |
| none模式        | -net=none                 | 容器有独立的Network namespace，并没有对其进行任何网络设置，<br/>如分配veth pair和网络连接，配制IP等。<br/>该模式关闭了容器的网络功能。 |
| bridge模式      | -net=bridge               | 默认该模式.<br/>此模式会为每一个容器分配、设置IP等，<br/>并将容器连接到一个docker0虚拟网络，<br/>通过docker0网桥以及iptables nat表配制与宿主机通信 |
| Macvlan network | 无                        | 容器具备Mac地址，使其显示为网络上的物理设备。                |
| overlay         | 无                        | （覆盖网络）：利用VXLAN实现的bridge模式。                    |

**bridge模式**

默认的网络模式。bridge模式下容器没有一个公网IP，只有宿主机可以直接访问，外部主机是不可见的，但容器通过宿主机的NAT规则后可以访问外网。

bridge桥接模式工实现步骤：

- Docker Daemon利用veth pair技术，在宿主机上创建两个虚拟网络接口设备，假设为veth0和vetho1.而veth pair技术的特性可以保证无论哪一个veth接收到网络报文，都会将报文传输给另一方。
- Docker Daemon将veth0附加到Docker Daemon创建的docker0网桥上。保证宿主机的网络报文可以发往veth0;
- Docker Daemon将veth1添加到Docker Container所属的namespace下，并被改名为eth0,如果一来，保证宿主机的网络报文若发往veth0，则立即会被eth0接收，实现宿主机到Docker Container网络的联通性同时，也保证Docker Container单独使用eth0，实现容器网络环境隔离性。

Bridge桥接模式的缺陷:

1. 最明显的是，该模式下Docker Container不具有公有IP，即和宿主机的eth0不处于同一个网段。导致结果是宿主机以外的世界不能直接和容器进行通信。
2. 虽然NAT模式经过中间处理实现了这一段，但是NAT模式仍然存在问题与不便，如：容器均需要在宿主机上竞争端口，容器内部服务的访问者需要使用服务发现获知服务的外部端口等。
3. 另外NAT模式由于是在三层网络上的实现手段，故肯定会影响网络的传输效率。

**注意：**

veth设备是成对出现的，一端容器内部命令为eth0，一端是加入网桥并命名为veth(通常命令为veth),它们组成了一个数据传输通道，一端进一端出，veth设备连接了两个网络设备并实现了数据通信。



**host模式**

相当于Vmware中的NAT模式，与宿主机在同一个网络中，但没有独立IP地址。

如果启动容器的时候使用host模式，那么这个容器将不会获得一个独立的NETwork namespace，而是和宿主机共用一个Network namespace。容器将不会虚拟出自己的网卡，配置自己的IP等，而是使用宿主机的IP和端口。但是，容器的其他方面，如果文件系统、进程列表等还是和宿主机隔离的。

使用host模式的容器可以直接使用宿主机的IP地址与外界通信，容器内部服务端口也可以使用宿主机的端口，不需要进行NAT，host最大优势就是网络性能比较好，但是docker host已经使用端口就不能再用了，网络的隔离性不好

host网络模式需要在容器创建时指定-network=host

host模式是bridge桥接模式很好的补充。采用host模式的Docker Container，可以直接使用宿主机的IP地址与外界进行通信，基宿主机的eth0是一个公网IP，那么容器也拥有这个公网IP。同时容器内部的服务端口也可以宿主机的端口，无需进行额外的NAT转换。

host械可以让容器共享宿主机网络栈，这样好处是外部主机与容器直接通信，但是容器的网络缺少隔离性。

host网络模式的缺陷：

最明显的是Docker Container网络环境隔离性弱化。即容器不再拥有隔离、独立的网络环境。

另外，使用host模式的Docker Container虽然可以让容器内部的服务和传统情况无差别、无改造的使用，但是由于网络隔离性的弱化，该容器会与宿主机共享竞争网络栈的使用。

另外、容器内部将不再拥有所有的端口资源，原因是部分端口资源已经被宿主机本身占用，还有部分端口用于bridge网络模式容器的端口映射。



**Container网络模式**

一种特殊的host网络模式

Container网络模式是Docker中一种较为特别的网络模式，在容器创建时使用-network=container:vm1指定。（vm1指定的是运行的容器名）

处于这个模式下的Docker容器会共享一个网络环境，这样两个容器之间可以使用localhost高效快速通信。

缺陷：它并没有改善容器与宿主机以外世界通信的情况（和桥接模式一样，不能连接宿主机以外的其他设备）

这个模式指定创建的容器和已经存在的一个容器共享一个Network Namespace，而不是和宿主机共享。新创建的容器不会创建自己的网卡，配置自己的IP，而是和一个指定的共享IP、端口范围等。同样，两个容器除了网络方面，其他的如文件系统、进程列表等还是隔离的。两个容器的进程可以通过lo网卡设备通信。

**none模式**

使用none模式，Docker容器拥有自己的Network Namespace，但是，并不为Docker容器进行任何网络配制，也就是说，这个Docker容器没有网卡、IP、路由等信息，需要我们自己为Docker容器添加网卡、配置IP等。

这种网络模式容器只有lo回环网络，没有其他网卡。none模式可以在容器创建时通过network=none来指定。这种类型的网络没有办法联网，封装网络能很的保证容器的安全性。

**overlay网络模式**

overlay网络，也称为覆盖网络，主要用于docker集群部署。

Overlay网络的实现方式和方案有多种。DOcker自身集成了一种，基于VXLAN隧道技术实现。

Overlay网络用于实现跨主机容器之间在的通信。

应用场景：需要管理上千个跨主机的容器集群的网络时。

**macvlan网络模式**

macvlan网络模式，最主要的特征就是他们的通信会基于mac地址进行转发。

这时宿主机充当一个二层交换机。Docker会维护着一个MAC地址表，当宿主机网络收到一个数据包后，直接根据mac地址找到对应的容器，再氢数据交换对应的容器。

容器之间通过IP互通，通过宿主机上内建的虚拟网络设备 （创建macvlan网络时自动创建），但与主机无法直接利用IP互通。

应用场景：由于每个外来的数据包目的mac地址就是容器的mac地址，这时每个容器对于外来网络来说就相当于这个真实的物理网络设备。因此当需要让容器的网络看起来是一个真实的物理机时，使用macvlan模式。

macvlan是一个新的尝试，是真正网络虚拟化技术的转折点。linux实现非常轻量级，因为与传统的Linux Bridge隔离相比，它们只是简单的与一个Linux以太网接口或子接口相关联，以实现网络之间的分离和物理网络的连接。

Macvlan提供了许多独特的功能，并有充足的空间进一步创新与各种模式。这些方法的两个高级优点是绕过Linux的网桥的正面性能以及移动部件少的简单性。删除传统上驻留在Docker主机NIC和容器接口之间的网络留下了一个非常简单的设置，包括容器接口，直接连接Docker主机接口。由于在这些情况下没有端口映射，因此可以轻松访问外部服务。

Macvlan Bridge模式每个容器都有唯一的MAC地址，用于跟踪Docker主机的MAC地址到端口映射。

Macvlan驱动程序网络连接父Docker主机接口。是物理接口，例eth0，用于802.1qVLAN标记的子接口eth0.10（.10代表VLAN10）或者甚至绑定的主机适配器，将两个以太网接口捆绑为单个逻辑接口。指定的网关由网络基础设施提供的主机外部。每个Macvlan Bridge模式的Docker网络彼此隔离，一次只有一个网络连接到父节点。

每个主机适配器有一个理论限制，每个主机适配器可以连接一个Docker网络。同一子网内的任何容器都可以与没有网关的同一网络中的任何其他容器进行通信macvlan bridge。相同的Docker network命令适用于vlan驱动程序。在Macvlan模式下，在两个网络/子网之前没有外部进程路由的情况下，单独网络上的容器无法互联访问，这也适用于同一网络内的多个子网。



### bridge网络

bridge网络表现形式就是docker0这个网络接口。容器默认都是通过docker0这个接口进行通信。也可通过docker0去和本机的以太网接口连接，这样容器内部才能访问互联网。

```sh
# 查看docker0网络，默认环境中，一个名为docker0的linux bridge自动被创建好了，其上有一个docker0内部接口，IP为127.17.0.1/16
ip a

# 查看docker 网络
docker network ls

# 查看bridge网络详情，主要关注containers节点信息。
docker network inspect bridge
```

无容器运行时样例数据

```sh
[root@dockeros ~]# ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: enp0s3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:ed:b1:b5 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic enp0s3
       valid_lft 70587sec preferred_lft 70587sec
    inet6 fe80::1026:97f6:36:38b7/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:d4:0a:e2 brd ff:ff:ff:ff:ff:ff
    inet 192.168.5.20/24 brd 192.168.5.255 scope global noprefixroute enp0s8
       valid_lft forever preferred_lft forever
    inet6 fe80::6826:6d62:c48d:5ac/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default 
    link/ether 02:42:51:ec:9b:bd brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:51ff:feec:9bbd/64 scope link 
       valid_lft forever preferred_lft forever
[root@dockeros ~]# docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
33e9444e71e1   bridge    bridge    local
4fc8da9792df   host      host      local
f6f389d033fe   none      null      local
[root@dockeros ~]# docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "33e9444e71e1a13da4a9ad8f7b91bfc0a8cc3156785198077579454bb1ef0bbd",
        "Created": "2023-12-10T18:42:44.091003478+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {},
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[root@dockeros ~]# 
```

运行镜像

```sh
# 运行一个nginx镜像。
docker run -itd --name nginx nginx:1.19.3-alpine

# 查看bridge网络详情。主要关注Containers节点信息。发现nginx1容器使用bridge网络。
docker network inspect bridge

# 查看容器创建后IP地址的分配,在主机的网络中，会发现多出一块网卡veth779d309@if13
ip a

# 查看docker网络,
docker network ls

```

样例输出：

```sh
[root@dockeros ~]# docker run -itd --name nginx nginx:1.19.3-alpine
4564a52f52b821f1dc886bcd85c60ea5b49f869ab2c60d1775327207a506c2b5
[root@dockeros ~]# docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "33e9444e71e1a13da4a9ad8f7b91bfc0a8cc3156785198077579454bb1ef0bbd",
        "Created": "2023-12-10T18:42:44.091003478+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "4564a52f52b821f1dc886bcd85c60ea5b49f869ab2c60d1775327207a506c2b5": {
                "Name": "nginx",
                "EndpointID": "1f1d4099e246bd83aa63c1310fc41fa2bd8c065013e5477fd4c19619cb4c7886",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[root@dockeros ~]# ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: enp0s3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:ed:b1:b5 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic enp0s3
       valid_lft 85616sec preferred_lft 85616sec
    inet6 fe80::1026:97f6:36:38b7/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:d4:0a:e2 brd ff:ff:ff:ff:ff:ff
    inet 192.168.5.20/24 brd 192.168.5.255 scope global noprefixroute enp0s8
       valid_lft forever preferred_lft forever
    inet6 fe80::6826:6d62:c48d:5ac/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever
4: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default 
    link/ether 02:42:3f:68:21:16 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:3fff:fe68:2116/64 scope link 
       valid_lft forever preferred_lft forever
6: veth7ea6b4e@if5: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP group default 
    link/ether ba:57:ba:40:2c:9f brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet6 fe80::b857:baff:fe40:2c9f/64 scope link 
       valid_lft forever preferred_lft forever
[root@dockeros ~]# docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
33e9444e71e1   bridge    bridge    local
4fc8da9792df   host      host      local
f6f389d033fe   none      null      local
[root@dockeros ~]# docker exec -it nginx ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
5: eth0@if6: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue state UP 
    link/ether 02:42:ac:11:00:02 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.2/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
```



Docker创建一个容器的时候，会执行如下操作：

- 创建一对虚拟接口/网卡，也就是veth pair,分别放到本地主机和新容器中中；
- 本地主机一端桥接到默认的docker0或指定网桥上，并具有一个唯一的名字，如veth7ea6b4e;
- 新容器一端放到新容器中，并修改名称作为eth0，这个网卡/接口只在容器的命名空间内可见；
- 从桥接可以地址段中（也就是与该bridge对应的network）获取一个空闲地址分配给容器的eth0，并配置默认路由到桥接网卡veth7ea6b4e。

完成这些后，容器就可以使用eth0虚拟网卡来连接其他容器和其他网络。

如果不指定--network，创建的容器默认都会挂到docker0上，使用本地主机上docker0接口的IP作为所有容器的默认网关。

安装bridge工具。来查看对应的桥接信息

```sh
yum install -y bridge-utils

# 运行命令
brctl show

```

样例输出：

```sh
[root@dockeros ~]# brctl show
bridge name     bridge id               STP enabled     interfaces
docker0         8000.02423f682116       no              veth7ea6b4e
```



结合之前的输出，可以发现，在宿主主机创建了一个虚拟网卡veth7ea6b4e，这个虚拟网卡将连接docker0上，通就过`brctl show`也能发现此docker0的网卡与veth7ea6b4e是相通的。然后在运行的容器中，将通过docker0这`172.16.0.1/16`这个地址段中获取一个IP，并给容器内部的eth0来使用，这样容器内部与外部就联系在了一起。



**多容器之间的通讯**

```sh
docker rm $(docker stop $(docker ps -q))

# 创建多容器
docker run -itd --name nginx1 nginx:1.19.3-alpine
docker run -itd --name nginx2 nginx:1.19.3-alpine


# 查看网桥
docker network inspect bridge

docker exec -it nginx1 sh 
ping 172.17.0.2
ping nginx1
ping 172.17.0.3
ping nginx2
ping www.baidu.com
exit

docker exec -it nginx2 sh
ping 127.17.0.2
ping nginx1
ping 172.17.0.3
ping nginx3
ping www.baidu.com
exit


```

样例输出

```sh
[root@dockeros ~]# docker rm $(docker stop $(docker ps -q))
4564a52f52b8
[root@dockeros ~]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros ~]# docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros ~]# docker run -itd --name nginx1 nginx:1.19.3-alpine

3f9598707f8789ae5a57e8aad3a29d4a7a7aa8703c38784fe5bd6b0237a14a64
[root@dockeros ~]# docker run -itd --name nginx2 nginx:1.19.3-alpine
b976a79292faf00e30c3528010023938b0c62e70a78e40b8a49d2d6df2a7033f
[root@dockeros ~]# docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "5e93f62ab84e84db7ae43ce144b735df98289e57da2f88682fc430e5309dfa63",
        "Created": "2023-12-11T12:11:39.326176651+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "3f9598707f8789ae5a57e8aad3a29d4a7a7aa8703c38784fe5bd6b0237a14a64": {
                "Name": "nginx1",
                "EndpointID": "6221946ff5b31f135c1d6b925b41cae6ae32c38b2e5c0bb0e52bd38aafdf0dde",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            },
            "b976a79292faf00e30c3528010023938b0c62e70a78e40b8a49d2d6df2a7033f": {
                "Name": "nginx2",
                "EndpointID": "671b3cbf3887600cb3271509bffbf1ff154e8744f16eeee659eca96ca6d9132f",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[root@dockeros ~]# docker exec -it nginx1 sh 
/ # ping 172.17.0.2
PING 172.17.0.2 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.027 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.058 ms
^C
--- 172.17.0.2 ping statistics ---
2 packets transmitted, 2 packets received, 0% packet loss
round-trip min/avg/max = 0.027/0.042/0.058 ms
/ # ping nginx1
ping: bad address 'nginx1'
/ # ping 172.17.0.3
PING 172.17.0.3 (172.17.0.3): 56 data bytes
64 bytes from 172.17.0.3: seq=0 ttl=64 time=0.089 ms
64 bytes from 172.17.0.3: seq=1 ttl=64 time=0.075 ms
64 bytes from 172.17.0.3: seq=2 ttl=64 time=0.078 ms
^C
--- 172.17.0.3 ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 0.075/0.080/0.089 ms
/ # ping nginx2
ping: bad address 'nginx2'
/ # ping www.baidu.com
PING www.baidu.com (180.101.50.242): 56 data bytes
64 bytes from 180.101.50.242: seq=0 ttl=50 time=14.444 ms
64 bytes from 180.101.50.242: seq=1 ttl=50 time=17.937 ms
64 bytes from 180.101.50.242: seq=2 ttl=50 time=12.389 ms
^C
--- www.baidu.com ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 12.389/14.923/17.937 ms
/ # exit
[root@dockeros ~]# docker exec -it nginx2 sh 
/ # ping 172.17.0.2
PING 172.17.0.2 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.048 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.077 ms
64 bytes from 172.17.0.2: seq=2 ttl=64 time=0.077 ms
^C
--- 172.17.0.2 ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 0.048/0.067/0.077 ms
/ # ping nginx1
ping: bad address 'nginx1'
/ # ping 172.17.0.3
PING 172.17.0.3 (172.17.0.3): 56 data bytes
64 bytes from 172.17.0.3: seq=0 ttl=64 time=0.027 ms
64 bytes from 172.17.0.3: seq=1 ttl=64 time=0.057 ms
64 bytes from 172.17.0.3: seq=2 ttl=64 time=0.154 ms
^C
--- 172.17.0.3 ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 0.027/0.079/0.154 ms
/ # ping nginx2
ping: bad address 'nginx2'
/ # ping www.baidu.com
PING www.baidu.com (180.101.50.242): 56 data bytes
64 bytes from 180.101.50.242: seq=0 ttl=50 time=14.444 ms
64 bytes from 180.101.50.242: seq=1 ttl=50 time=17.937 ms
64 bytes from 180.101.50.242: seq=2 ttl=50 time=12.389 ms
^C
--- www.baidu.com ping statistics ---
3 packets transmitted, 3 packets received, 0% packet loss
round-trip min/avg/max = 12.389/14.923/17.937 ms
/ # exit
[root@dockeros ~]# 
```

经过上面的试验可以发现，使用IP都可以ping通两个主机，但是用运行的容器的名称是不可能ping通的。







**容器停止后IP的变化**

```sh
# 容器停止后，容器的IP之后会如何变化，
docker stop nginx1 nginx2

# 先启动nginx2,再启动nginx1
docker start nginx2

docker start nginx1

docker network inspect bridge
```

样例输出

```sh
[root@dockeros ~]# docker stop nginx1 nginx2
nginx1
nginx2
[root@dockeros ~]# docker start nginx2
nginx2
[root@dockeros ~]# docker start nginx1
nginx1
[root@dockeros ~]# docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "5e93f62ab84e84db7ae43ce144b735df98289e57da2f88682fc430e5309dfa63",
        "Created": "2023-12-11T12:11:39.326176651+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "3f9598707f8789ae5a57e8aad3a29d4a7a7aa8703c38784fe5bd6b0237a14a64": {
                "Name": "nginx1",
                "EndpointID": "366fe7a4f1dd55b94f783ddb7edd9a7db92c9dd6be421515be6fb925d2911785",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            },
            "b976a79292faf00e30c3528010023938b0c62e70a78e40b8a49d2d6df2a7033f": {
                "Name": "nginx2",
                "EndpointID": "24a0bc7fcb968e296fb9cd5f6ae8b34a331c353f0cc2e97f694aea46ddfb7e32",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[root@dockeros ~]# 
```

经过此项验证发现，容器的域名所绑定的IP并不是固定不变的，而是在启动时动态分配的。

#### 解决bridgem网络模式下ping其他容器不通的问题-方式1-(--link)

```sh
--link=[]: 添加链接到另一个容器；此方法官方已经不推荐使用。
```

>场景1：在企业开发环境中，我们有一个mysql服务器容器mysql_1, 还有一个web应用程序web_1, 这个web_1容器是需要连接mysql_1这个数据库。前面的网络命名空间的知识告诉我们，两个容器需要能通信，需要知道对方的具体的IP地址。生产环境还比较好，IP地址很少发生变化，但是在我们内部测试环境，容器的IP可能不断变化的，所以开发人员不能在代码中写死数据库的IP地址。这个时候，可以利用容器之间link来解决这个问题.

```sh
docker rm -f nginx2

# 使用--linke参数来运行nginx2这个容器
docker run -itd --name nginx2 --link nginx1 nginx:1.19.3-alpine

# 查看容器的IP信息
docker network inspect bridge

docker exec -it nginx2 sh 

# ping下IP的情况
ping 172.17.0.2
ping 172.17.0.3

# ping域名的情况
ping nginx2
ping nginx1

exit

# 在nginx1中去ping下nginx2
docker exec -it nginx1 sh 
# ping域名的情况
ping nginx2
ping nginx1
exit

```

操作样例

```sh
[root@dockeros ~]# docker ps
CONTAINER ID   IMAGE                 COMMAND                  CREATED      STATUS         PORTS     NAMES
b976a79292fa   nginx:1.19.3-alpine   "/docker-entrypoint.…"   2 days ago   Up 1 second    80/tcp    nginx2
3f9598707f87   nginx:1.19.3-alpine   "/docker-entrypoint.…"   2 days ago   Up 2 seconds   80/tcp    nginx1
[root@dockeros ~]# docker rm -f nginx2
nginx2
[root@dockeros ~]# docker run -itd --name nginx2 --link nginx1 nginx:1.19.3-alpine
33424ed9987d30347ed509f6de38956c1514e2de742cef96b8dbae6a4e6c6b99
[root@dockeros ~]# docker network inspect  bridge 
[
    {
        "Name": "bridge",
        "Id": "e64b6ee653c3101c109ce5a329811f800f1f5571cc2e7654ff24cd15c34e6768",
        "Created": "2023-12-13T12:23:31.163071662+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "33424ed9987d30347ed509f6de38956c1514e2de742cef96b8dbae6a4e6c6b99": {
                "Name": "nginx2",
                "EndpointID": "fd7ab1974924de91d77091b721ca5a0f3e29aaa1ade07d23c848da6fd91d88de",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            },
            "3f9598707f8789ae5a57e8aad3a29d4a7a7aa8703c38784fe5bd6b0237a14a64": {
                "Name": "nginx1",
                "EndpointID": "8719789d28e95810de47d437ec437b15bb970d89b9c9c67b635642051b69d77c",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
[root@dockeros ~]# docker exec -it nginx2 sh
/ # ping 172.17.0.2
PING 172.17.0.2 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.075 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.120 ms
^C
--- 172.17.0.2 ping statistics ---
2 packets transmitted, 2 packets received, 0% packet loss
round-trip min/avg/max = 0.075/0.097/0.120 ms
/ # ping 172.17.0.3
PING 172.17.0.3 (172.17.0.3): 56 data bytes
64 bytes from 172.17.0.3: seq=0 ttl=64 time=0.031 ms
64 bytes from 172.17.0.3: seq=1 ttl=64 time=0.062 ms
^C
--- 172.17.0.3 ping statistics ---
2 packets transmitted, 2 packets received, 0% packet loss
round-trip min/avg/max = 0.031/0.046/0.062 ms
/ # ping nginx2
ping: bad address 'nginx2'
/ # ping nginx1
PING nginx1 (172.17.0.2): 56 data bytes
64 bytes from 172.17.0.2: seq=0 ttl=64 time=0.055 ms
64 bytes from 172.17.0.2: seq=1 ttl=64 time=0.079 ms
^C
--- nginx1 ping statistics ---
2 packets transmitted, 2 packets received, 0% packet loss
round-trip min/avg/max = 0.055/0.067/0.079 ms
/ # exit
[root@dockeros ~]# docker exec -it nginx1 sh 
/ # ping nginx2
ping: bad address 'nginx2'
/ # ping nginx1
ping: bad address 'nginx1'
/ # exit
[root@dockeros ~]# 
```

到此可以发现，在nginx2容器中，已经可以成功的将nginx1这个容器名称ping通了,link的作用添加了DNS解析。但在这里提醒下nginx1容器里去ping容器nginx2还是不通的,因为link关系是单向的，不可逆的。

实际工作中，docker官网已经不推荐我们使用--link参数

dokcer用其他方式替换掉了link参数



#### 解决bridgem网络模式下ping其他容器不通的问题-方式2-新建bridge网络

```sh
docker network create -d bridge nullnull-bridge
```

上面的命令中-d是指DRIVER的类型，后面的nullnull-bridge是自定义的名称，这个和docker0类似的，下面开始把其他容器连接到nullnull-bridge这个网络上。

```sh
# 相看本地bridge的网络的相关接口信息
brctl show

docker network ls
docker network inspect nullnull-bridge

# 创建容器连接到这个bridge网络
docker run -itd --name nginx3 --network nullnull-bridge nginx:1.19.3-alpine

brctl show
docker network inspect nullnull-bridge

# 其他容器连接到nullnull-bridge网络
docker network connect nullnull-bridge nginx2
docker network inspect nullnull-bridge

# 测试容器的互ping操作
docker exec -it nginx2 sh
ping nginx3
exit

docker exec -it nginx3 sh
ping nginx2
exit
```

通过此可以发现，此时两个容器之间使用可直接使用域名ping通。

样例：

```sh
[root@dockeros ~]# docker network create -d bridge nullnull-bridge
70d7e9e9674fe985057aa12b328aff4ed340dcd924b2b01a33f233144b204d3e


[root@dockeros ~]# brctl show
bridge name     bridge id               STP enabled     interfaces
br-70d7e9e9674f         8000.02426f6cd67a       no
docker0         8000.0242aedd5c16       no              veth50d5a03
                                                        veth9f8a876
[root@dockeros ~]# docker network ls
NETWORK ID     NAME              DRIVER    SCOPE
e64b6ee653c3   bridge            bridge    local
4fc8da9792df   host              host      local
f6f389d033fe   none              null      local
70d7e9e9674f   nullnull-bridge   bridge    local
[root@dockeros ~]# docker network inspect nullnull-bridge
[
    {
        "Name": "nullnull-bridge",
        "Id": "70d7e9e9674fe985057aa12b328aff4ed340dcd924b2b01a33f233144b204d3e",
        "Created": "2023-12-13T12:35:14.991310516+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.18.0.0/16",
                    "Gateway": "172.18.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {},
        "Options": {},
        "Labels": {}
    }
]
[root@dockeros ~]# docker run -itd --name nginx3 --network nullnull-bridge nginx:1.19.3-alpine
fcec481653ba046b4ba8b8f4fc1fe52bca9314adbf36993de03d730bd240dae3
[root@dockeros ~]# brctl show
bridge name     bridge id               STP enabled     interfaces
br-70d7e9e9674f         8000.02426f6cd67a       no              veth0962e13
docker0         8000.0242aedd5c16       no              veth50d5a03
                                                        veth9f8a876
[root@dockeros ~]# docker network inspect nullnull-bridge
[
    {
        "Name": "nullnull-bridge",
        "Id": "70d7e9e9674fe985057aa12b328aff4ed340dcd924b2b01a33f233144b204d3e",
        "Created": "2023-12-13T12:35:14.991310516+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.18.0.0/16",
                    "Gateway": "172.18.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "fcec481653ba046b4ba8b8f4fc1fe52bca9314adbf36993de03d730bd240dae3": {
                "Name": "nginx3",
                "EndpointID": "1397ba389869b8a55994b297845844db91d24403f2496eee14c2f5fdf6b34295",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[root@dockeros ~]# docker network connect nullnull-bridge nginx2
[root@dockeros ~]# docker network inspect nullnull-bridge
[
    {
        "Name": "nullnull-bridge",
        "Id": "70d7e9e9674fe985057aa12b328aff4ed340dcd924b2b01a33f233144b204d3e",
        "Created": "2023-12-13T12:35:14.991310516+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.18.0.0/16",
                    "Gateway": "172.18.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "33424ed9987d30347ed509f6de38956c1514e2de742cef96b8dbae6a4e6c6b99": {
                "Name": "nginx2",
                "EndpointID": "6afecfb2aeaa945dc2824b6b618a8181595b8baf262e15ad062838c556220a0e",
                "MacAddress": "02:42:ac:12:00:03",
                "IPv4Address": "172.18.0.3/16",
                "IPv6Address": ""
            },
            "fcec481653ba046b4ba8b8f4fc1fe52bca9314adbf36993de03d730bd240dae3": {
                "Name": "nginx3",
                "EndpointID": "1397ba389869b8a55994b297845844db91d24403f2496eee14c2f5fdf6b34295",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[root@dockeros ~]# docker exec -it nginx2 sh
/ # ping nginx3
PING nginx3 (172.18.0.2): 56 data bytes
64 bytes from 172.18.0.2: seq=0 ttl=64 time=0.130 ms
64 bytes from 172.18.0.2: seq=1 ttl=64 time=0.117 ms
^C
--- nginx3 ping statistics ---
2 packets transmitted, 2 packets received, 0% packet loss
round-trip min/avg/max = 0.117/0.123/0.130 ms
/ # exit
[root@dockeros ~]# docker exec -it nginx3 sh
/ # ping nginx2
PING nginx2 (172.18.0.3): 56 data bytes
64 bytes from 172.18.0.3: seq=0 ttl=64 time=0.044 ms
^C
--- nginx2 ping statistics ---
1 packets transmitted, 1 packets received, 0% packet loss
round-trip min/avg/max = 0.044/0.044/0.044 ms
/ # exit
[root@dockeros ~]# 

```







### none 网络

 在开始前，记得清理下容器和网络

```sh
docker rm -f $(docker ps -aq)

docker network rm nullnull-bridge

docker network ls
```

执行

```sh
# 创建一个nginx1，并连接到none网络。
docker run -itd --name nginx1 --network none nginx:1.19.3-alpine
docker network inspect none

# 进入容器，检查网络情况
docker exec -it nginx1 sh
ip a
```

容器使用none模式，是没有物理地址和IP地址，从进入容器后就可以发现，只有一个lo回环地址，没有其他网络地址，没有IP，也就是说这个容器不能被其他容器访问到。这种使用场景很少，只有项目安全性很高的功能才能使用到。例如密码加密算法容器。

样例输出：

```sh
[root@dockeros ~]# docker run -itd --name nginx1 --network none nginx:1.19.3-alpine
4548b7443897168361b1509ab070479aa9ec17da8a3e341d4ec497976a14ddac
[root@dockeros ~]# docker network inspect none
[
    {
        "Name": "none",
        "Id": "f6f389d033fead9237ecdb1da69b1c5234b3b36d892c9e4ac8adf50be5113310",
        "Created": "2023-12-07T18:49:34.493868727+08:00",
        "Scope": "local",
        "Driver": "null",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": []
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "4548b7443897168361b1509ab070479aa9ec17da8a3e341d4ec497976a14ddac": {
                "Name": "nginx1",
                "EndpointID": "c66be3d02da6e2311c61ecc42b25a121f34883604f3fe447fe836c16395917b9",
                "MacAddress": "",
                "IPv4Address": "",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[root@dockeros ~]# docker exec -it nginx1 sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
/ # 
```





### host网络

执行：

```sh
# 容器信息
docker run -itd --name nginx2 --network host nginx:1.19.3-alpine

# 查看网络信息
docker network inspect host

```

通过运行的结果来分析，这是也是不显示IP地址的，那是不是和none一样呢？这肯定不是，不然也不会设计成none和host网络进行区分。那进入容器，看下容器的IP地址信息。

```sh
# 进入nginx2 查看下IP信息
docker exec -it nginx2 sh
ip a
```

从容器的输出来看，host模式，容器和外层linux主机共享一套网络接口。这种容器和本机使用共享一套网络接口，缺点也很明显，例如我们知道Web服务器的端口是80，共享一套网络接口，那么这台机器上只能启动一个nginx端口为80的服务了，否则会出现端口被占用的情况



样例输出:

```sh
[root@dockeros ~]# docker run -itd --name nginx2 --network host nginx:1.19.3-alpine
ac06c6f161d8aaf2cf618d357f1c9d8b6a3743fce49fed8e5972c68a78c517db
[root@dockeros ~]# docker network inspect host
[
    {
        "Name": "host",
        "Id": "4fc8da9792df79859b506a85c78faaf3597b26d15210b3c66bffae37ff83ef91",
        "Created": "2023-12-07T18:49:34.50673008+08:00",
        "Scope": "local",
        "Driver": "host",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": []
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "ac06c6f161d8aaf2cf618d357f1c9d8b6a3743fce49fed8e5972c68a78c517db": {
                "Name": "nginx2",
                "EndpointID": "cd1f5daa1be6f58af6eb6b38458adc98b8882d32fb238c74fbfec13624f7505d",
                "MacAddress": "",
                "IPv4Address": "",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
[root@dockeros ~]# docker exec -it nginx2 sh
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: enp0s3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP qlen 1000
    link/ether 08:00:27:ed:b1:b5 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global dynamic enp0s3
       valid_lft 62824sec preferred_lft 62824sec
    inet6 fe80::1026:97f6:36:38b7/64 scope link 
       valid_lft forever preferred_lft forever
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP qlen 1000
    link/ether 08:00:27:d4:0a:e2 brd ff:ff:ff:ff:ff:ff
    inet 192.168.5.20/24 brd 192.168.5.255 scope global enp0s8
       valid_lft forever preferred_lft forever
    inet6 fe80::6826:6d62:c48d:5ac/64 scope link 
       valid_lft forever preferred_lft forever
4: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN 
    link/ether 02:42:ae:dd:5c:16 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:aeff:fedd:5c16/64 scope link 
       valid_lft forever preferred_lft forever
/ # 
```

### 固定IP

```sh
# 如果想要容器固定一个IP地址。可以这样操作
docker network create -d bridge --subnet=172.18.0.0/24 --gateway 172.18.0.1 nullnull-network

172.18.0.0/24 : 24代表子码掩码是255.255.255.0
172.172.0.0/16: 16代表子码掩码是255.255.0.0

docker run -itd --name nginx5 -p 82:80 --net nullnull-network --ip 172.18.0.28 nginx:1.19.3-alpine

# 进入容器查看IP
docker exec -it nginx5 sh 
ip a

```

样例输出:

```sh
[root@dockeros ~]# docker network create -d bridge --subnet=172.18.0.0/24 --gateway 172.18.0.1 nullnull-network
d946c0b14e28157b80271da76227bd8b98e63bb8554c35ff38f8e16568204670
[root@dockeros ~]# docker run -itd --name nginx5 -p 82:80 --net nullnull-network --ip 172.18.0.28 nginx:1.19.3-alpine
810606da612b110216963863059d6256c6772591d2c2b073d7408399920265ed
[root@dockeros ~]# docker exec -it nginx5 sh 
/ # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
19: eth0@if20: <BROADCAST,MULTICAST,UP,LOWER_UP,M-DOWN> mtu 1500 qdisc noqueue state UP 
    link/ether 02:42:ac:12:00:1c brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.28/24 brd 172.18.0.255 scope global eth0
       valid_lft forever preferred_lft forever
/ # 
```





## Docker数据卷

什么是数据卷？

当我们在使用docker容器的时候，会产生一系列的数据文件，这些数据文件在我们删除docker容器的时会消失的，但是其中产生的部分内容我们希望能够把它给保存起来另作用途的，Docker将应用与运行环境打包成容器发布，我们希望在运行过程中产生的部分数据是可以持久化的。而且容器之间我们希望能够实现数据共享。

通俗来说，docker容器数据卷可以看作我们生产中常用的U盘，它存在于一个或者多个容器中，由docker挂载以容器，但不属于联合文件系统。Docker不会在容器删除时删除其挂载的数据券。

特点：

1. 数据卷可以在容器之间共享或者重用数据。
2. 数据卷中的更改可以立即生效。
3. 数据卷中的更改不会打包在镜像的更新中。
4. 数据卷会默认一直存在，即使容器被删除。
5. 数据卷的生命周期一直持续到没有容器使用它为止。

容器中的管理数据主要有两种方式：

- 数据卷，Data Valumes容器内数据直接映射到本地主机环境。
- 数据卷容器，Data Valumes Containers 使用特定容器维护数据卷。

### cp命令

用于容器与主机之前的数据拷贝。

语法：

```sh
# 容器向宿主机拷贝数据
docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-

# 宿主机向容器拷贝数据
docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH
```

常用参数

- -L ： 保持源目标中的链接。



### 数据卷

数据卷（Data Volumes）是一个可供一个或者多个容器使用的特殊目录，它将主机操作系统目录直接映射进容器。

**注意事项**

- 挂载数据卷，最好通过run而非create/start创建启动容器，create/start命令启动容器后，再挂载数据相当麻烦，要修改很多配制文件，但并非不可以。
- docker官网推荐尽量进行目录挂载，不要进行文件挂载。

**数据卷类型：**

有三种数据卷类型：

1. 宿主数据卷：直接在宿主机的文件系统中但容器可以访问（bind mount)
2. 命名的数据卷：磁盘上的Docker管理的数据卷，但是这个卷有个名字。
3. 匿名数据卷：磁盘上Docker管理的数据卷，因为没有名称想要找到不容易，Docker来管理这些文件。

数据卷其实都在（如果没有网络文件系统等情况下）宿主机文件系统里面的。只是第一种是在宿主机内的特定目录下，而后两种则在docker管理的目录下。这个目录一般为/var/lib/docker/volumes/

推荐使用`宿主机数据卷`方式持久化数据





### 宿主机数据卷

bind mounts: 容器内的数据被存放在宿主机文件系统的任意位置，甚至存放到一些重要的系统目录或者文件中。除了docker之外的进程也可以任意对他们进行修改。

当使用bind mounts时，宿主机的目录或者文件被挂载到容器中，容器将按照挂载目录或者文件的绝对路径来使用或者修改宿主机的数据。宿主机的目录或者文件不需要预先存在，在需要使用时会自动创建。

使用bind mounts在性能上是非常好的，但这依赖于宿主机有一个目录妥善结构化的文件系统。

使用bind mounts的容器可以通过容器内部的进程对主机文件系统进行修改。包括创建、修改和删除重要的系统文件和目录，这个功能虽然很强大，但显然也会造成安全方面的影响，包括影响到宿主机上Docker进程以外的进程。



**数据覆盖问题**

- 如果挂载一个空的数据卷到容器中的一个非空目录中，那么这个目录下的文件会被复杂到数据卷中
- 如果挂载一个非空的数据卷到容器中的一个目录中，那么容器中的目录会显示数据卷中的数据，如果原来容器中有的目录有数据，那么原始数据会被隐藏掉。

语法：

```sh
docker run -v /宿主机绝对路径目录：/容器内的目录 镜像名
```

操作

```sh
# 拉取镜像
docker pull mysql:5.7.31

# 运行镜像
# 推荐先将目录创建好，再进行数据挂载
mkdir -p /data/mysql
chmod 777 /data/mysql

docker run -itd --name mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -v /data/mysql:/var/lib/mysql mysql:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci

# 容器目录权限
# 通过 -v 容器内路径： ro rw 改变读写权限
ro:readonly 只读
rw:readwrite 可读可写

docker run -it -v /宿主机绝对路径目录：/容器内目录：ro 镜像名
docker run -it -v /宿主机绝对路径目录：/容器内目录：rw 镜像名

ro: 只要看到ro就说明这个路径只能通过宿主机来操作，容器内部是无法操作。
```



挂载目录权限问题演示：

```sh
https://hub.docker.com/r/sonatype/nexus3


# 拉取镜像
docker pull sonatype/nexus3:3.28.1


# 备份镜像
docker save sonatype/nexus3:3.28.1 -o sonatype-nexus.image


# 运行容器
docker run -d -p 8081:8081 --name nexus3 sonatype/nexus3:3.28.1 

# 进入容器查看初始密码
docker exec -it nexus3 /bin/bash

cd /nexus-data/

cat admin.password


# 浏览器访问
http://192.168.5.20:8081/

docker rm $(docker stop $(docker ps -aq))


# 数据卷挂载
docker run -d -p 8081:8081 --name nexus3 -v /data/nexus3/:/nexus-data  sonatype/nexus3:3.28.1 

# 查看容器日志
docker logs -f nexus3
# 在报错信息中可以看到容器的目录存在权限问题。
mkdir: cannot create directory '../sonatype-work/nexus3/log': Permission denied
mkdir: cannot create directory '../sonatype-work/nexus3/tmp': Permission denied
Warning:  Cannot open log file: ../sonatype-work/nexus3/log/jvm.log

# 删除容器
docker rm -f nexus3

# 查看官网的一个说明：
A persistent directory, /nexus-data, is used for configuration, logs, and storage. This directory needs to be writable by the Nexus process, which runs as UID 200.
# 为挂载目录授权
chown -R 200 /data/nexus3

# 再次运行容器
docker run -d -p 8081:8081 --name nexus3 -v /data/nexus3/:/nexus-data  sonatype/nexus3:3.28.1 

# 查看日志
docker logs -f nexus3

```

在开发环境推荐对挂载目录授最高权限777；生产环境需要查看官网文档，结合实际生产环境进行授权。

样例:

```sh
[root@dockeros mysql]# docker pull sonatype/nexus3:3.28.1
3.28.1: Pulling from sonatype/nexus3
ec1681b6a383: Pull complete 
c4d668e229cd: Pull complete 
79161e5880bc: Pull complete 
c65b6b9a3763: Pull complete 
Digest: sha256:e788154207df95a86287fc8ecae1a5f789e74d0124f2dbda846fc4c769603bdb
Status: Downloaded newer image for sonatype/nexus3:3.28.1
docker.io/sonatype/nexus3:3.28.1
[root@dockeros data]# docker save sonatype/nexus3:3.28.1 -o sonatype-nexus.image
[root@dockeros data]# docker run -d -p 8081:8081 --name nexus3 sonatype/nexus3:3.28.1 
6bde52222290b59bbe6d08c9726bc70a0bc756def193f2626f0cb0eee85d50e2
[root@dockeros data]# docker exec -it nexus3 /bin/bash
bash-4.4$ cd /nexus-data/
bash-4.4$ cat admin.password
28202dab-6b95-46a2-9128-283cfcd2b55a
[root@dockeros data]# docker rm $(docker stop $(docker ps -aq))
6bde52222290
f95126e88fe0
810606da612b
ac06c6f161d8
4548b7443897
[root@dockeros data]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros data]# docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros data]# docker run -d -p 8081:8081 --name nexus3 -v /data/nexus3/:/nexus-data  sonatype/nexus3:3.28.1 
13bef2125a23ff97a0d44a844c7ff14db243c79b3b37acea57e1b41e778a385b
[root@dockeros data]# docker logs -f nexus3
mkdir: cannot create directory '../sonatype-work/nexus3/log': Permission denied
mkdir: cannot create directory '../sonatype-work/nexus3/tmp': Permission denied
Warning:  Cannot open log file: ../sonatype-work/nexus3/log/jvm.log
Warning:  Forcing option -XX:LogFile=/tmp/jvm.log
OpenJDK 64-Bit Server VM warning: Cannot open file ../sonatype-work/nexus3/log/jvm.log due to No such file or directory

java.io.FileNotFoundException: ../sonatype-work/nexus3/tmp/i4j_ZTDnGON8hezynsMX2ZCYAVDtQog=.lock (No such file or directory)
        at java.io.RandomAccessFile.open0(Native Method)
        at java.io.RandomAccessFile.open(RandomAccessFile.java:316)
        at java.io.RandomAccessFile.<init>(RandomAccessFile.java:243)
        at com.install4j.runtime.launcher.util.SingleInstance.check(SingleInstance.java:72)
        at com.install4j.runtime.launcher.util.SingleInstance.checkForCurrentLauncher(SingleInstance.java:31)
        at com.install4j.runtime.launcher.UnixLauncher.checkSingleInstance(UnixLauncher.java:88)
        at com.install4j.runtime.launcher.UnixLauncher.main(UnixLauncher.java:67)
java.io.FileNotFoundException: /nexus-data/karaf.pid (Permission denied)
        at java.io.FileOutputStream.open0(Native Method)
        at java.io.FileOutputStream.open(FileOutputStream.java:270)
        at java.io.FileOutputStream.<init>(FileOutputStream.java:213)
        at java.io.FileOutputStream.<init>(FileOutputStream.java:101)
        at org.apache.karaf.main.InstanceHelper.writePid(InstanceHelper.java:127)
        at org.apache.karaf.main.Main.launch(Main.java:243)
        at org.sonatype.nexus.karaf.NexusMain.launch(NexusMain.java:113)
        at org.sonatype.nexus.karaf.NexusMain.main(NexusMain.java:52)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.exe4j.runtime.LauncherEngine.launch(LauncherEngine.java:85)
        at com.install4j.runtime.launcher.UnixLauncher.main(UnixLauncher.java:69)
java.lang.RuntimeException: /nexus-data/log/karaf.log (No such file or directory)
        at org.apache.karaf.main.util.BootstrapLogManager.getDefaultHandlerInternal(BootstrapLogManager.java:102)
        at org.apache.karaf.main.util.BootstrapLogManager.getDefaultHandlersInternal(BootstrapLogManager.java:137)
        at org.apache.karaf.main.util.BootstrapLogManager.getDefaultHandlers(BootstrapLogManager.java:70)
        at org.apache.karaf.main.util.BootstrapLogManager.configureLogger(BootstrapLogManager.java:75)
        at org.apache.karaf.main.Main.launch(Main.java:244)
        at org.sonatype.nexus.karaf.NexusMain.launch(NexusMain.java:113)
        at org.sonatype.nexus.karaf.NexusMain.main(NexusMain.java:52)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.exe4j.runtime.LauncherEngine.launch(LauncherEngine.java:85)
        at com.install4j.runtime.launcher.UnixLauncher.main(UnixLauncher.java:69)
Caused by: java.io.FileNotFoundException: /nexus-data/log/karaf.log (No such file or directory)
        at java.io.FileOutputStream.open0(Native Method)
        at java.io.FileOutputStream.open(FileOutputStream.java:270)
        at java.io.FileOutputStream.<init>(FileOutputStream.java:213)
        at org.apache.karaf.main.util.BootstrapLogManager$SimpleFileHandler.open(BootstrapLogManager.java:193)
        at org.apache.karaf.main.util.BootstrapLogManager$SimpleFileHandler.<init>(BootstrapLogManager.java:182)
        at org.apache.karaf.main.util.BootstrapLogManager.getDefaultHandlerInternal(BootstrapLogManager.java:100)
        ... 12 more
Error creating bundle cache.
Unable to update instance pid: Unable to create directory /nexus-data/instances
[root@dockeros data]# docker run -d -p 8081:8081 --name nexus3 -v /data/nexus3/:/nexus-data  sonatype/nexus3:3.28.1 
14417d952d4d7d9c15a0039ae14d05adf646c56b5c57cbebadfccf20fe1f3ef9
[root@dockeros data]# docker logs -f nexus3
2023-12-13 15:19:29,964+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.pax.logging.NexusLogActivator - start
2023-12-13 15:19:30,189+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.features.internal.FeaturesWrapper - Fast FeaturesService starting
2023-12-13 15:19:30,790+0000 WARN  [FelixStartLevel] *SYSTEM uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4JInitialiser - Your logging framework class org.ops4j.pax.logging.slf4j.Slf4jLogger is not known - if it needs access to the standard println methods on the console you will need to register it by calling registerLoggingSystemPackage
2023-12-13 15:19:30,791+0000 INFO  [FelixStartLevel] *SYSTEM uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J - Package org.ops4j.pax.logging.slf4j registered; all classes within it or subpackages of it will be allowed to print to System.out and System.err
2023-12-13 15:19:30,794+0000 INFO  [FelixStartLevel] *SYSTEM uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J - Replaced standard System.out and System.err PrintStreams with SLF4JPrintStreams
2023-12-13 15:19:30,795+0000 INFO  [FelixStartLevel] *SYSTEM uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J - Redirected System.out and System.err to SLF4J for this context
2023-12-13 15:19:30,797+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder - Properties:
2023-12-13 15:19:30,797+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder -   application-host='0.0.0.0'
2023-12-13 15:19:30,798+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder -   application-port='8081'
2023-12-13 15:19:30,798+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder -   fabric.etc='/opt/sonatype/nexus/etc/fabric'
2023-12-13 15:19:30,798+0000 INFO  [FelixStartLevel] *SYSTEM org.sonatype.nexus.bootstrap.ConfigurationBuilder -   jetty.etc='/opt/sonatype/nexus/etc/jetty'
......
2023-12-13 15:20:01,237+0000 INFO  [jetty-main-1] *SYSTEM org.sonatype.nexus.repository.httpbridge.internal.ViewServlet - Initialized
2023-12-13 15:20:01,258+0000 INFO  [jetty-main-1] *SYSTEM org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.w.WebAppContext@6c8844a9{Sonatype Nexus,/,file:///opt/sonatype/nexus/public/,AVAILABLE}
2023-12-13 15:20:01,284+0000 INFO  [jetty-main-1] *SYSTEM org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@153042ce{HTTP/1.1, (http/1.1)}{0.0.0.0:8081}
2023-12-13 15:20:01,284+0000 INFO  [jetty-main-1] *SYSTEM org.eclipse.jetty.server.Server - Started @32247ms
2023-12-13 15:20:01,284+0000 INFO  [jetty-main-1] *SYSTEM org.sonatype.nexus.bootstrap.jetty.JettyServer - 
-------------------------------------------------

Started Sonatype Nexus OSS 3.28.1-01

-------------------------------------------------
```



### 命名的数据卷

操作:

```sh
# 使用命名的数据卷运行容器
docker run -itd --name nginx -p 80:80 -v nullnull-nginx:/etc/nginx nginx:1.19.3-alpine

# 查看docker 数据卷
docker volume ls

# 查看nullnull-nginx宿主机目录 
docker volume inspect nullnull-nginx

# 进入docker数据卷默认目录 
cd /var/lib/docker/volumes/nullnull-nginx

# 所有文件docker默认保存在_data目录中
cd _data

# 删除容器
docker rm $(docker stop $(docker ps -aq))

# 查看挂载数据是否还存在，通过查看数据后发现，宿主机中的数据还存在
ls
```



样例输出:

```sh
[root@dockeros ~]# docker run -itd --name nginx -p 80:80 -v nullnull-nginx:/etc/nginx nginx:1.19.3-alpine
318a5940b00b401f0cc259da510c1aa3b319429961bffc181b4ac92070fe041f
[root@dockeros ~]# docker volume ls
DRIVER    VOLUME NAME
local     1b88613eda206718ade47da8d1cbe2469aa98ec8f6b3b9fd5521faafeb6c65de
local     4f85b7e7784f042514cbcfb16b1114ba3697e4bfa2369bb7504ee764b857d3d1
local     87cfbd88a862abe08014f4a8de1da17814b6b707fe42b4a02b9394bbcdf0a7de
local     94a2903ea00e7dffda8fbc8932ab1b7d024f0af6d52a8443054568b3bdc0905e
local     417bee3999af1a232206b5ff643dc113c32e20c7253bd048710b96d25c3c7fb0
local     930a7237f6ea9403cb89b470e6dd30ce076192aa0d61f3a84afe20f52eb1a9c7
local     a1c5b5eca4b33f53c90b0a273ef6acd677267861905da9b3d8fe9934be038026
local     ba7ae8a34fe7d5e4636229f2ae6eb14d6658257fabd31ccc29c417d85280933d
local     bd6af107654680b10e0f7e70c3371c8f7b44f4cf563afc84e1cbb5f0974a37ec
local     d1d11f5f4344950b15a80bebf9040dba7569f635c8f86c0e6b050f2189bccdb7
local     fe1d7c17a031d93db56991aaf831b24627e8c84ddfa49f881d9841ce178e5546
local     nullnull-nginx
[root@dockeros ~]# docker volume inspect nullnull-nginx
[
    {
        "CreatedAt": "2023-12-14T12:34:21+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/nullnull-nginx/_data",
        "Name": "nullnull-nginx",
        "Options": null,
        "Scope": "local"
    }
]
[root@dockeros ~]# cd /var/lib/docker/volumes/nullnull-nginx
[root@dockeros nullnull-nginx]# ls
_data
[root@dockeros nullnull-nginx]# cd _data/
[root@dockeros _data]# ls
conf.d        fastcgi_params  koi-win     modules     scgi_params   win-utf
fastcgi.conf  koi-utf         mime.types  nginx.conf  uwsgi_params
[root@dockeros _data]# docker rm $(docker stop $(docker ps -aq))
318a5940b00b
14417d952d4d
[root@dockeros _data]# ls
conf.d        fastcgi_params  koi-win     modules     scgi_params   win-utf
fastcgi.conf  koi-utf         mime.types  nginx.conf  uwsgi_params
[root@dockeros _data]# 
```



### 匿名数据卷

操作

```sh
# 使用匿名数据卷挂载容器的数据
docker run -itd --name nginx -p 80:80 -v /etc/nginx nginx:1.19.3-alpine

# 查看docker数据卷
docker volume ls

cd /var/lib/docker/volumes

# 查看容器所使用匿名目录
docker inspect nginx
# 在volume的详细信息中

# 查看宿主机目录
docker volume inspect 84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961


# 进入容器数据目录
cd /var/lib/docker/volumes/84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961/_data
ls

# 删除容器
docker rm $(docker stop $(docker ps -aq))

# 查看挂载数据是否还存在，通过查看数据，发现删除容器后，宿主机中的数据还存在
ls

```

样例：

```sh
[root@dockeros _data]# docker run -itd --name nginx -p 80:80 -v /etc/nginx nginx:1.19.3-alpine
17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b
[root@dockeros _data]# docker volume ls
DRIVER    VOLUME NAME
local     1b88613eda206718ade47da8d1cbe2469aa98ec8f6b3b9fd5521faafeb6c65de
local     4f85b7e7784f042514cbcfb16b1114ba3697e4bfa2369bb7504ee764b857d3d1
local     87cfbd88a862abe08014f4a8de1da17814b6b707fe42b4a02b9394bbcdf0a7de
local     94a2903ea00e7dffda8fbc8932ab1b7d024f0af6d52a8443054568b3bdc0905e
local     417bee3999af1a232206b5ff643dc113c32e20c7253bd048710b96d25c3c7fb0
local     930a7237f6ea9403cb89b470e6dd30ce076192aa0d61f3a84afe20f52eb1a9c7
local     84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961
local     a1c5b5eca4b33f53c90b0a273ef6acd677267861905da9b3d8fe9934be038026
local     ba7ae8a34fe7d5e4636229f2ae6eb14d6658257fabd31ccc29c417d85280933d
local     bd6af107654680b10e0f7e70c3371c8f7b44f4cf563afc84e1cbb5f0974a37ec
local     d1d11f5f4344950b15a80bebf9040dba7569f635c8f86c0e6b050f2189bccdb7
local     fe1d7c17a031d93db56991aaf831b24627e8c84ddfa49f881d9841ce178e5546
local     nullnull-nginx
[root@dockeros _data]# cd /var/lib/docker/volumes
[root@dockeros volumes]# pwd
/var/lib/docker/volumes
[root@dockeros volumes]# docker inspect nginx
[
    {
        "Id": "17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b",
        "Created": "2023-12-14T04:41:21.049383195Z",
        "Path": "/docker-entrypoint.sh",
        "Args": [
            "nginx",
            "-g",
            "daemon off;"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 2168,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2023-12-14T04:41:22.374869833Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:4efb29ff172a12f4a5ed5bc47eda3596f8b812173cf609cbb489253dad6e737f",
        "ResolvConfPath": "/var/lib/docker/containers/17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b/hostname",
        "HostsPath": "/var/lib/docker/containers/17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b/hosts",
        "LogPath": "/var/lib/docker/containers/17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b/17220d34b95ea872f3b3880df801aa7f57ce9c7048ce544b32fa818d361afa9b-json.log",
        "Name": "/nginx",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "default",
            "PortBindings": {
                "80/tcp": [
                    {
                        "HostIp": "",
                        "HostPort": "80"
                    }
                ]
            },
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "ConsoleSize": [
                128,
                120
            ],
            "CapAdd": null,
            "CapDrop": null,
            "CgroupnsMode": "host",
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": [],
            "BlkioDeviceWriteBps": [],
            "BlkioDeviceReadIOps": [],
            "BlkioDeviceWriteIOps": [],
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": null,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware",
                "/sys/devices/virtual/powercap"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/0c393fa408836eccc524424eb6b8f1a1d01bdff8eb5a861ba78326b7919ff12d-init/diff:/var/lib/docker/overlay2/17521d4797960134262bba76b12bb97089f23db2ef867983ea63d5a6ca6cfb26/diff:/var/lib/docker/overlay2/75211b624dbc457c25042c1a0967c93a609192877e19737af1a6f1a9ec07003e/diff:/var/lib/docker/overlay2/e28bd1904f8cc52fd3e787a5416333596c4fc655de87f69c78f39c0f55f693fc/diff:/var/lib/docker/overlay2/748c131111006a67844de5567df39d1663bb0930249ceb4c228f2621aae8ca5b/diff:/var/lib/docker/overlay2/883ae049fecb3ba3cb22de2bae558a20f9644ff7c66c86740ab23fa53d79ef08/diff",
                "MergedDir": "/var/lib/docker/overlay2/0c393fa408836eccc524424eb6b8f1a1d01bdff8eb5a861ba78326b7919ff12d/merged",
                "UpperDir": "/var/lib/docker/overlay2/0c393fa408836eccc524424eb6b8f1a1d01bdff8eb5a861ba78326b7919ff12d/diff",
                "WorkDir": "/var/lib/docker/overlay2/0c393fa408836eccc524424eb6b8f1a1d01bdff8eb5a861ba78326b7919ff12d/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [
            {
                "Type": "volume",
                "Name": "84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961",
                "Source": "/var/lib/docker/volumes/84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961/_data",
                "Destination": "/etc/nginx",
                "Driver": "local",
                "Mode": "",
                "RW": true,
                "Propagation": ""
            }
        ],
        "Config": {
            "Hostname": "17220d34b95e",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "80/tcp": {}
            },
            "Tty": true,
            "OpenStdin": true,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
                "NGINX_VERSION=1.19.3",
                "NJS_VERSION=0.4.4",
                "PKG_RELEASE=1"
            ],
            "Cmd": [
                "nginx",
                "-g",
                "daemon off;"
            ],
            "Image": "nginx:1.19.3-alpine",
            "Volumes": {
                "/etc/nginx": {}
            },
            "WorkingDir": "",
            "Entrypoint": [
                "/docker-entrypoint.sh"
            ],
            "OnBuild": null,
            "Labels": {
                "maintainer": "NGINX Docker Maintainers <docker-maint@nginx.com>"
            },
            "StopSignal": "SIGTERM"
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "0f71b7359b73af6e9d520454fe34c0111795a95f40a8890a7f8d6bc1cacd9cfb",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {
                "80/tcp": [
                    {
                        "HostIp": "0.0.0.0",
                        "HostPort": "80"
                    },
                    {
                        "HostIp": "::",
                        "HostPort": "80"
                    }
                ]
            },
            "SandboxKey": "/var/run/docker/netns/0f71b7359b73",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "bab090b8ec725aac39b7c7c0340f2a5517f529086abc38200908208b5e5b1c92",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "636857a5233a67f591483603ee50f10b88ab0ddba6ad8e85bd419319b49376ac",
                    "EndpointID": "bab090b8ec725aac39b7c7c0340f2a5517f529086abc38200908208b5e5b1c92",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]
[root@dockeros volumes]# docker volume inspect 84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961
[
    {
        "CreatedAt": "2023-12-14T12:41:21+08:00",
        "Driver": "local",
        "Labels": {
            "com.docker.volume.anonymous": ""
        },
        "Mountpoint": "/var/lib/docker/volumes/84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961/_data",
        "Name": "84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961",
        "Options": null,
        "Scope": "local"
    }
]
[root@dockeros volumes]# cd /var/lib/docker/volumes/84496c80ad58ad605714f4c1c73385209ab718734ad244eb406d8af637ec6961/_data
[root@dockeros _data]# ls
conf.d        fastcgi_params  koi-win     modules     scgi_params   win-utf
fastcgi.conf  koi-utf         mime.types  nginx.conf  uwsgi_params
[root@dockeros _data]# docker rm $(docker stop $(docker ps -aq))
17220d34b95e
[root@dockeros _data]# ls
conf.d        fastcgi_params  koi-win     modules     scgi_params   win-utf
fastcgi.conf  koi-utf         mime.types  nginx.conf  uwsgi_params
[root@dockeros _data]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros _data]# docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros _data]# 

```



### 数据卷容器

操作

```sh
# 基础镜像
docker pull centos:7.8.2003
docker pull nginx:1.19.3-alpine
docker pull mysql:5.7.31
```

--volumes-from:

如果用户需要在多个容器之间共享一些持续更新的数据，最简单的方式是使用数据卷容器。数据卷容器也是一个容器，但是它的目的是专门用来提供数据卷供其他容器挂载。

创建好的数据卷容器是处于停止运行的状态。因为使用`--volumes-from`参数所挂载数据卷的容器自己并不需要保持在运行状态。

注意：并不是所有的容器都可以共享数据的。这个得做好验证。

![image-20231214231928569](.\images\image-20231214231928569.png)



```sh
# 首先创建数据容器卷
docker run -d --name nullnull-volume -v /data/nginx:/usr/share/nginx/html -v /data/mysql:/var/lib/mysql centos:7.8.2003

# 创建nginx容器
docker run -itd --name nginx01 -p 80:80 --volumes-from nullnull-volume nginx:1.19.3-alpine
echo "this is nullnull data nginx" > /data/nginx/index.html
http://192.168.5.20

# 创建另外的nginx容器
docker run -itd --name nginx02 -p 81:80 --volumes-from nullnull-volume nginx:1.19.3-alpine 
http://192.168.5.20:81

docker ps -a


# 运行mysql容器
docker run -itd --name mysql10 --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin --volumes-from nullnull-volume mysql:5.7.31  --character-set-server=utf8 --collation-server=utf8_general_ci
# 连接上数据库，在上面执行下建库、建表操作，
CREATE TABLE `data`.user_data (
	id integer not null  primary key,
	name varchar(32)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

insert into `data`.user_data(id,name)
values(1,'test');




# 演示MySQL共享的问题，并不是所有容器都可以正常的直接进行共享。
docker run -itd --name mysql11 --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin --volumes-from nullnull-volume mysql:5.7.31  --character-set-server=utf8 --collation-server=utf8_general_ci

docker run -itd --name mysql11 --restart always --privileged=true -p 3308:3306 -e MYSQL_ROOT_PASSWORD=admin --volumes-from nullnull-volume mysql:5.7.31  --character-set-server=utf8 --collation-server=utf8_general_ci

```

样例：

```sh
[root@dockeros ~]# docker run -d --name nullnull-volume -v /data/nginx:/usr/share/nginx/html -v /data/mysql:/var/lib/mysql centos:7.8.2003
d10ca8754416e5cf309a95b08eabe5cacdf7f1c54087fb51f19d0a01f77e6f29
[root@dockeros ~]# docker run -itd --name nginx01 -p 80:80 --volumes-from nullnull-volume nginx:1.19.3-alpine
7157de8950f7721559692481974af00ed67e91e42d0ec01fe8f67103e2958441
[root@dockeros ~]# echo "this is nullnull data nginx" > /data/nginx/index.html
[root@dockeros ~]# docker run -itd --name nginx02 -p 81:80 --volumes-from nullnull-volume nginx:1.19.3-alpine 
441c15c8b771ec2deb79c368559366be4ed9e56f5c8363e6340d03a547483be3
[root@dockeros ~]# docker ps -a
CONTAINER ID   IMAGE                 COMMAND                  CREATED         STATUS                     PORTS                               NAMES
441c15c8b771   nginx:1.19.3-alpine   "/docker-entrypoint.…"   2 minutes ago   Up 2 minutes               0.0.0.0:81->80/tcp, :::81->80/tcp   nginx02
7157de8950f7   nginx:1.19.3-alpine   "/docker-entrypoint.…"   2 minutes ago   Up 2 minutes               0.0.0.0:80->80/tcp, :::80->80/tcp   nginx01
d10ca8754416   centos:7.8.2003       "/bin/bash"              2 minutes ago   Exited (0) 2 minutes ago                                       nullnull-volume

[root@dockeros ~]# docker run -itd --name mysql10 --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin --volumes-from nullnull-volume mysql:5.7.31  --character-set-server=utf8 --collation-server=utf8_general_ci
36fdb3c8376f123cd07c0088192df1da261d8aa46159bed433ce28420c649b21
[root@dockeros ~]# docker run -itd --name mysql11 --restart always --privileged=true -p 3308:3306 -e MYSQL_ROOT_PASSWORD=admin --volumes-from nullnull-volume mysql:5.7.31  --character-set-server=utf8 --collation-server=utf8_general_ci

[root@dockeros ~]# docker logs mysql11
2023-12-14 15:32:12+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-14 15:32:12+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2023-12-14 15:32:12+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-14T15:32:12.863859Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-14T15:32:12.864531Z 0 [Note] mysqld (mysqld 5.7.31) starting as process 1 ...
2023-12-14T15:32:12.867757Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-14T15:32:12.867792Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-14T15:32:12.867794Z 0 [Note] InnoDB: Uses event mutexes
2023-12-14T15:32:12.867796Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-14T15:32:12.867797Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-14T15:32:12.867799Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-14T15:32:12.868221Z 0 [Note] InnoDB: Number of pools: 1
2023-12-14T15:32:12.868281Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-14T15:32:12.869274Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-14T15:32:12.873016Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-14T15:32:12.874658Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-14T15:32:12.884650Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:12.884700Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:12.884704Z 0 [Note] InnoDB: Retrying to lock the first data file
2023-12-14T15:32:13.885117Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:13.885182Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:14.886303Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:14.886383Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:15.887018Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:15.887084Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:16.888855Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:16.888921Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:17.892928Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:17.892995Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:18.893980Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:18.894051Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:19.894176Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:19.894264Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:20.895230Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:20.895304Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:21.895956Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:21.896041Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:22.896877Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:22.896945Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:23.897773Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:23.897846Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:24.898299Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:24.898373Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:25.899391Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:25.899461Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:26.900166Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:26.900239Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:27.900758Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:27.900847Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.
2023-12-14T15:32:28.902276Z 0 [ERROR] InnoDB: Unable to lock ./ibdata1 error: 11
2023-12-14T15:32:28.902351Z 0 [Note] InnoDB: Check that you do not already have another mysqld process using the same InnoDB data or log files.

```



![image-20231214231255071](.\images\image-20231214231255071.png)

![image-20231214231319400](.\images\image-20231214231319400.png)



## Docker compose

 Docker Compose能够在Docker节点上，以单引擎(Single-Engine Mode)进行多容器应用的部署和管理。多数现代应用通过更小的微服务互相协同起来组成一个完整可用的应用。

部署和管理繁多的服务是困难的，而这正是Docker Compose要解决的问题。Docker Comose并不是通过脚本和各种冗长的Docker命令将应用组件组织起来，而是通过一个声明式的配制文件描述整个应用，从而使一条命令完成整个部署。应用部署成功后，还可以通过一系列简单的命令实现对其完整生命周期的管理。甚至、配制文件还可以置于版本控制系统中进行存储和管理。

Docker Compose是一个以基于Python工具，允许用户基于一个YAML文件定义多容器应用。从而使用命令行工虎进行应用的部署。

### Docker Compose安装

在Linux上安装Docker Compose分为两步

- 下载
- 授权

操作：

```sh
# 下载  版本 1.27.4
https://github.com/docker/compose

# 详细地址：
https://github.com/docker/compose/releases/tag/1.27.4
https://github.com/docker/compose/releases/download/1.27.4/docker-compose-Linux-x86_64



# 将文件放到可以加载的环境中
mv /data/docker-compose-Linux-x86_64 /usr/local/bin/docker-compose
cp /data/docker-compose-Linux-x86_64 /usr/local/bin/docker-compose

# 授权
chmod +x /usr/local/bin/docker-compose

#开发环境可以授权最高权限
chmod 777 /usr/local/bin/docker-compose


# 检查安装情况以及版本 
docker-compose -v
docker-compose --version
docker-compose version
```

样例操作:

```sh
[root@dockeros data]# cp /data/docker-compose-Linux-x86_64 /usr/local/bin/docker-compose
[root@dockeros data]# chmod +x /usr/local/bin/docker-compose
[root@dockeros data]# chmod 777  /usr/local/bin/docker-compose
[root@dockeros data]# docker-compose -v
docker-compose version 1.27.4, build 40524192
[root@dockeros data]# docker-compose version
docker-compose version 1.27.4, build 40524192
docker-py version: 4.3.1
CPython version: 3.7.7
OpenSSL version: OpenSSL 1.1.0l  10 Sep 2019
[root@dockeros data]# 
```



### 卸载Docker-compose

docker-compose卸载只需要删除二进制文件就可以了

```sh
rm -rf /usr/local/bin/docker-compose
reboot
```



### yml配制文件及常用指令

Docker Compose 使用Yaml文件来定义多服务应用，YAML是JSON的一个子集，因此也可以使用JSON。

Docker Compose默认使用文件名docker-compose.yml。当然也可用使用-f参数指定具体文件。

Docker Compose的YML文件包含4个一级key: version, services, networks, volumes

- version是必须指定的，而且总是位于文件的第一排，它定义Compose文件格式（主要是API）的版本。注意，version并非定义docker Compose或者Docker引擎的版本号。
- services用于定义不同的应用服务。Docker Compose会将此定义的的服务部署在各自的容器中。
- network用于指引Docker创建新的网络。默认情况下 Docker Compose会创建birdge网络。这是一种单主机网络，只能够实现同一主机上容器的连接。当然也可以使用docker属性指定不同类型的网络。
- volumes用于指引Docker来创建新的卷。



### 反向代理案例

**准备工作**

- 清理宿主机相关的容器。
- 安装dockerr-compose

**检查IDEA中的docker插件**

在最新版本的IDEA中，已经默认带了docker插件。老版本，需要手动安装下docker插件

```sh
# 插件地址
https://plugins.jetbrains.com/plugin/7724-docker/versions
```

**操作**

```sh
# 基础镜像
docker pull nginx:1.19.3-alpine
docker pull tomcat:9.0.20-jre8-alpine


# 试运行镜像容器
docker run -itd --name nginx -p 80:80 nginx:1.19.3-alpine
docker run -itd --name tomcat -p 8080:8080 tomcat:9.0.20-jre8-alpine

mkdir -p /data/tomcat1 /data/tomcat2

docker cp nginx:/etc/nginx /data
docker cp tomcat:/usr/local/tomcat/webapps /data/tomcat1/webapps
docker cp tomcat:/usr/local/tomcat/webapps /data/tomcat2/webapps

# 编辑首页文件,样例中就是Title中添加一个标识
vi /data/tomcat1/webapps/ROOT/index.jsp
vi /data/tomcat1/webapps/ROOT/index.jsp

docker stop nginx tomcat &&  docker rm nginx tomcat


# nginx反向代理配制，增加导入一个文件
vi /data/nginx/nginx.cfg
include vhost/*.conf;

# 反向代理配制
mkdir -p /data/nginx/vhost
cd vhost
vi nullnull.conf
upstream nginx-tomcat{
     server 192.168.5.20:8081;
     server 192.168.5.20:8082;
}
server{
     listen 80;
     server_name 192.168.5.20;
     autoindex on;
     index index.html index.htm index.jsp;
     location / {
         proxy_pass http://nginx-tomcat;
         add_header Access-Control-Allow-Origin *;
     }
 }
```

docker-compose.yml的配制

```sh
version: '3'
services:
  nullnull-nginx:
    restart: always
    container_name: nullnull-nginx
    volumes:
      - /data/nginx:/etc/nginx/
    image: nginx:1.19.3-alpine
    ports:
      - 80:80
  nullnull-tomcat1:
    restart: always
    container_name: nullnull-tomcat1
    volumes:
      - /data/tomcat1/webapps:/usr/local/tomcat/webapps
    image: tomcat:9.0.20-jre8-alpine
    ports:
      - 8081:8080
    depends_on:
      - nullnull-nginx
  nullnull-tomcat2:
    restart: always
    container_name: nullnull-tomcat2
    volumes:
      - /data/tomcat2/webapps:/usr/local/tomcat/webapps
    image: tomcat:9.0.20-jre8-alpine
    ports:
      - 8082:8080
    depends_on:
      - nullnull-nginx
```

执行

```sh
# 将docker-compose.yml文件上传服务器。
# 启动服务
docker-compose up
# 后台启动
docker-compose up -d

```





样例：

```sh
[root@dockeros ~]# docker run -itd --name nginx -p 80:80 nginx:1.19.3-alpine

2530e6be67b47c3ee6908c08274da4f758ea660ec79dffc32e6e2c397ddc6641
[root@dockeros ~]# docker run -itd --name tomcat -p 8080:8080 tomcat:9.0.20-jre8-alpine
c4011e6095319182d1cf23b4e57ab4e65038b3a8deefeee4bc33b84fa78b7931
[root@dockeros ~]# mkdir -p /data/tomcat1 /data/tomcat2
[root@dockeros ~]# docker cp nginx:/etc/nginx /data
Successfully copied 30.2kB to /data
[root@dockeros ~]# docker cp tomcat:/usr/local/tomcat/webapps /data/tomcat1/webapps
Successfully copied 5.28MB to /data/tomcat1/webapps
[root@dockeros ~]# docker cp tomcat:/usr/local/tomcat/webapps /data/tomcat2/webapps
Successfully copied 5.28MB to /data/tomcat2/webapps
[root@dockeros ~]# vi /data/tomcat1/webapps/ROOT/index.jsp
[root@dockeros ~]# vi /data/tomcat1/webapps/ROOT/index.jsp
[root@dockeros compose]# cd /data/compose/
[root@dockeros compose]# ls
docker-compose.yml
[root@dockeros compose]# docker-compose up
Starting nullnull-nginx ... done
Starting nullnull-tomcat2 ... done
Starting nullnull-tomcat1 ... done
Attaching to nullnull-nginx, nullnull-tomcat2, nullnull-tomcat1
nullnull-nginx      | /docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
nullnull-nginx      | /docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
nullnull-nginx      | /docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
nullnull-nginx      | 10-listen-on-ipv6-by-default.sh: error: IPv6 listen already enabled
nullnull-nginx      | /docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
nullnull-nginx      | /docker-entrypoint.sh: Configuration complete; ready for start up
nullnull-tomcat1    | 15-Dec-2023 10:54:35.967 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server version name:   Apache Tomcat/9.0.20
nullnull-tomcat1    | 15-Dec-2023 10:54:35.969 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server built:          May 3 2019 22:26:00 UTC
nullnull-tomcat1    | 15-Dec-2023 10:54:35.969 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server version number: 9.0.20.0
nullnull-tomcat1    | 15-Dec-2023 10:54:35.969 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Name:               Linux
nullnull-tomcat1    | 15-Dec-2023 10:54:35.969 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Version:            5.4.262-1.el7.elrepo.x86_64
nullnull-tomcat1    | 15-Dec-2023 10:54:35.969 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Architecture:          amd64
......
nullnull-tomcat2    | 15-Dec-2023 10:54:36.682 INFO [main] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/usr/local/tomcat/webapps/manager] has finished in [12] ms
nullnull-tomcat2    | 15-Dec-2023 10:54:36.684 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
nullnull-tomcat1    | 15-Dec-2023 10:54:36.688 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
nullnull-tomcat1    | 15-Dec-2023 10:54:36.691 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [458] milliseconds
nullnull-tomcat2    | 15-Dec-2023 10:54:36.692 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
nullnull-tomcat2    | 15-Dec-2023 10:54:36.695 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [460] milliseconds

```



再通过浏览器访问

```http
http://192.168.5.20/

http://192.168.5.20:8081/
http://192.168.5.20:8082/

```

验证访问

![image-20231215185623194](.\images\image-20231215185623194.png)

![image-20231215185718539](.\images\image-20231215185718539.png)



至此docker-compose的反向代理验证成功。

### docker-compose常用命令

```sh
# 官网地址
https://docs.docker.com/compose/reference/build/

# 列出所有运行容器
docker-compose ps

# 查看服务日志
docker-compose logs

# 构建或者重新构建服务
docker-compose build

# 启动服务
docker-compose start

# 停止服务
docker-compose stop

# 重启服务
docker-compose restart


```



## 安装Docker私服

docker镜像仓库就类型maven中的nexus, maven管理java中的jar包，为避免每次都从中央仓库摘取依赖包，使用nexus做了代理仓库，docker镜像仓库与nexus私服仓库作用类似，用于将打包好的镜像保存在仓库中方便开发、测试、生产环境拉取，减轻环境部署需要相应操作。

### 节点信息

|    主机名    |    IP地址    |    说明    |
| :----------: | :----------: | :--------: |
| docker-os20  | 192.168.5.20 | docker主机 |
| docker-os221 | 192.168.5.21 | harbor主机 |



### 官方私服

harbor主机上操作

```sh
# 官网地址
https://hub.docker.com/_/registry


# 基础镜像
docker pull registry:2.7.1

# 运行容器
docker run -itd -p 5000:5000 --name registry --restart=always registry:2.7.1


# 浏览器中访问
http://192.168.5.21:5000/v2/_catalog

```

docker主机上操作

```sh
# 编辑配制文件
vi /etc/docker/daemon.json

# 添加仓库配制信息
{"insecure-registries": ["192.168.5.21:5000"]}

# 重启docker
systemctl daemon-reload
systemctl restart docker

# 查看docker信息确认仓库是否添加
docker info


# 上传镜像测试
docker tag nginx:1.19.3-alpine 192.168.5.21:5000/nginx:v1
docker push 192.168.5.21:5000/nginx:v1

# 浏览器检查是否上传成功
http://192.168.5.21:5000/v2/nginx/tags/list
# 响应： {"name":"nginx","tags":["v1"]}

```

样例输出 ：

```sh
[root@dockeros ~]# vi /etc/docker/daemon.json
[root@dockeros ~]# systemctl daemon-reload
[root@dockeros ~]# systemctl restart docker
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
 Containers: 3
  Running: 0
  Paused: 0
  Stopped: 3
 Images: 14
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
 Runtimes: runc io.containerd.runc.v2
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: d8f198a4ed8892c764191ef7b3b06d8a2eeb5c7f
 runc version: v1.1.10-0-g18a0cb0
 init version: de40ad0
 Security Options:
  seccomp
   Profile: builtin
 Kernel Version: 5.4.262-1.el7.elrepo.x86_64
 Operating System: CentOS Linux 7 (Core)
 OSType: linux
 Architecture: x86_64
 CPUs: 4
 Total Memory: 15.63GiB
 Name: dockeros
 ID: c185e0e5-64d8-44ba-b405-02b4344cbaf1
 Docker Root Dir: /var/lib/docker
 Debug Mode: false
 Experimental: false
 Insecure Registries:
  192.168.5.21:5000
  127.0.0.0/8
 Registry Mirrors:
  https://ys2mfbsh.mirror.aliyuncs.com/
 Live Restore Enabled: false

[root@dockeros ~]# docker tag nginx:1.19.3-alpine 192.168.5.21:5000/nginx:v1
[root@dockeros ~]# docker push 192.168.5.21:5000/nginx:v1
The push refers to repository [192.168.5.21:5000/nginx]
8d6d1951ab0a: Pushed 
d0e26daf1f58: Pushed 
835f5b67679c: Pushed 
4daeb7840e4d: Pushed 
ace0eda3e3be: Pushed 
v1: digest: sha256:a411d06ab4f5347ac9652357ac35600555aeff0b910326cc7adc36d471e0b36f size: 1360
[root@dockeros ~]# 
```



### 企业私服

harbor官网地址：

```sh
# harbor官网地址：
https://goharbor.io/

# github地址
https://github.com/goharbor/harbor

# 官方帮助文档
https://github.com/goharbor/harbor/blob/v1.9.4/docs/installation_guide.md


#须安装docker-compose
docker-compose -v
```

硬件要求:

| 硬件资源 | 最小配制 | 推荐配制 |
| -------- | -------- | -------- |
| CPU      | 2C       | 4C       |
| 内存     | 4G       | 8G       |
| 硬盘     | 40G      | 160G     |

**安装harbor**

开发环境大部分采用http方式进行安装，生产环境必须采用HTTPS方式安装。此处使用http的方式来安装

```sh
# 1. 将软件包上传解压
cd /data
tar zxf harbor-offline-installer-v1.9.4.tgz
 
# 2. 进入安装目录 
cd harbor

# 3. 修改配制文件
vi harbor.yml
# 3.1 修改私服镜像地址
hostname: 192.168.5.21
# 3.2 修改镜像地址访问端口号
port: 5000
# 3.3 harbor管理员登录系统密码
harbor_admin_password: Harbor12345
# 3.4 修改harbor映射卷目录
data_volume: /data/harbor


# 4. 安装harbor
# 4.1 执行启动脚本，经过以下3个步骤，成功安装harbor私服
./install.sh
# 4.2 准备安装环境，检查docker版本和docker-compose版本
# 4.3 加载harbor需要的镜像
# 4.4 准备编译环境
# 4.5 启动harbor,通过docker-compose方式启动服务
# 4.6 google浏览器访问harbor私服
http://192.168.5.21:5000
```

操作:

```sh
[root@dockeros21 harbor]# tar zxf harbor-offline-installer-v1.9.4.tgz 
[root@dockeros21 harbor]# cd harbor
[root@dockeros21 harbor]# ls
harbor.v1.9.4.tar.gz  harbor.yml  install.sh  LICENSE  prepare
[root@dockeros21 harbor]# vi harbor.yml
[root@dockeros21 harbor]# ./install.sh

[Step 0]: checking installation environment ...

Note: docker version: 24.0.7

Note: docker-compose version: 1.27.4

[Step 1]: loading Harbor images ...
62b223a46a15: Loading layer [==================================================>]  34.29MB/34.29MB
40f95e7c4d8c: Loading layer [==================================================>]  12.77MB/12.77MB
87bc69f1a650: Loading layer [==================================================>]  55.42MB/55.42MB
2d7b6446b66d: Loading layer [==================================================>]  5.632kB/5.632kB
4fff34e50f40: Loading layer [==================================================>]  37.38kB/37.38kB
5e79cfafc57c: Loading layer [==================================================>]  55.42MB/55.42MB
Loaded image: goharbor/harbor-core:v1.9.4
57c193635092: Loading layer [==================================================>]  115.8MB/115.8MB
48c741dd71e6: Loading layer [==================================================>]  12.23MB/12.23MB
bca1df60136e: Loading layer [==================================================>]  2.048kB/2.048kB
3ded12c0b4d9: Loading layer [==================================================>]  48.13kB/48.13kB
1ab30734b178: Loading layer [==================================================>]  3.072kB/3.072kB
09dcb0a00864: Loading layer [==================================================>]  12.28MB/12.28MB
Loaded image: goharbor/clair-photon:v2.1.0-v1.9.4
b3a6b161a0f0: Loading layer [==================================================>]  7.039MB/7.039MB
1ed6312f133c: Loading layer [==================================================>]  196.6kB/196.6kB
fee283579213: Loading layer [==================================================>]    172kB/172kB
1946b2964bfc: Loading layer [==================================================>]  15.36kB/15.36kB
026952c4573d: Loading layer [==================================================>]  3.584kB/3.584kB
37bd829992ae: Loading layer [==================================================>]  10.84MB/10.84MB
Loaded image: goharbor/harbor-portal:v1.9.4
0fa4e197a1e0: Loading layer [==================================================>]  10.84MB/10.84MB
Loaded image: goharbor/nginx-photon:v1.9.4
9f4e1ee20fe3: Loading layer [==================================================>]  9.009MB/9.009MB
eb044190906a: Loading layer [==================================================>]  42.31MB/42.31MB
04e55b2b95d5: Loading layer [==================================================>]  2.048kB/2.048kB
41efcb18a521: Loading layer [==================================================>]  3.072kB/3.072kB
16903b9eaf51: Loading layer [==================================================>]  42.31MB/42.31MB
Loaded image: goharbor/chartmuseum-photon:v0.9.0-v1.9.4
19ccaba72e02: Loading layer [==================================================>]   2.56kB/2.56kB
bf35853b2d08: Loading layer [==================================================>]  1.536kB/1.536kB
facfe762a35b: Loading layer [==================================================>]  75.33MB/75.33MB
cfcf6d4e7653: Loading layer [==================================================>]  42.65MB/42.65MB
c497e06e4e96: Loading layer [==================================================>]  157.2kB/157.2kB
f444f52f4af6: Loading layer [==================================================>]   3.01MB/3.01MB
Loaded image: goharbor/prepare:v1.9.4
12f86854bc80: Loading layer [==================================================>]   80.2MB/80.2MB
5e76c79bec2e: Loading layer [==================================================>]  3.072kB/3.072kB
694b3e7869d5: Loading layer [==================================================>]   59.9kB/59.9kB
27609e3dd221: Loading layer [==================================================>]  61.95kB/61.95kB
Loaded image: goharbor/redis-photon:v1.9.4
01c4d294000a: Loading layer [==================================================>]  9.005MB/9.005MB
e836fee5658c: Loading layer [==================================================>]  3.072kB/3.072kB
ac9add5e34a0: Loading layer [==================================================>]   2.56kB/2.56kB
1af6a0c8f2bb: Loading layer [==================================================>]  21.76MB/21.76MB
692f3a6593bb: Loading layer [==================================================>]  21.76MB/21.76MB
Loaded image: goharbor/registry-photon:v2.7.1-patch-2819-2553-v1.9.4
50d697c7c241: Loading layer [==================================================>]  9.004MB/9.004MB
ccc72da8223b: Loading layer [==================================================>]  6.239MB/6.239MB
d8f9724c0195: Loading layer [==================================================>]  16.04MB/16.04MB
813deff8bdee: Loading layer [==================================================>]  28.24MB/28.24MB
27eeaef358bd: Loading layer [==================================================>]  22.02kB/22.02kB
52a7091247e7: Loading layer [==================================================>]  50.52MB/50.52MB
Loaded image: goharbor/notary-server-photon:v0.6.1-v1.9.4
391081d598f3: Loading layer [==================================================>]  50.36MB/50.36MB
6e82a6bf9097: Loading layer [==================================================>]  3.584kB/3.584kB
f44a631c4f72: Loading layer [==================================================>]  3.072kB/3.072kB
c841d5236832: Loading layer [==================================================>]   2.56kB/2.56kB
253571258f05: Loading layer [==================================================>]  3.072kB/3.072kB
88d0f16c60e6: Loading layer [==================================================>]  3.584kB/3.584kB
a4499b4f52d1: Loading layer [==================================================>]  12.29kB/12.29kB
Loaded image: goharbor/harbor-log:v1.9.4
b94d1cd23704: Loading layer [==================================================>]  63.49MB/63.49MB
7108c9c351ce: Loading layer [==================================================>]  56.37MB/56.37MB
013a04104e87: Loading layer [==================================================>]  5.632kB/5.632kB
c91b8f37358e: Loading layer [==================================================>]  2.048kB/2.048kB
aa04ed1247cb: Loading layer [==================================================>]   2.56kB/2.56kB
ea3d1b630734: Loading layer [==================================================>]   2.56kB/2.56kB
9dd11aa8e16a: Loading layer [==================================================>]   2.56kB/2.56kB
69fab71b0bc5: Loading layer [==================================================>]  10.24kB/10.24kB
Loaded image: goharbor/harbor-db:v1.9.4
573233339cd8: Loading layer [==================================================>]  12.77MB/12.77MB
1c4fa76d32f8: Loading layer [==================================================>]  48.14MB/48.14MB
Loaded image: goharbor/harbor-jobservice:v1.9.4
401a522fd2d5: Loading layer [==================================================>]  9.005MB/9.005MB
93bfd55aee1a: Loading layer [==================================================>]  3.072kB/3.072kB
41bc5eeff535: Loading layer [==================================================>]  21.76MB/21.76MB
768368529512: Loading layer [==================================================>]  3.072kB/3.072kB
d0f0f102247d: Loading layer [==================================================>]  8.661MB/8.661MB
32b9c7908fb4: Loading layer [==================================================>]  30.42MB/30.42MB
Loaded image: goharbor/harbor-registryctl:v1.9.4
f8bf76e63a50: Loading layer [==================================================>]  14.61MB/14.61MB
9cc53a4748a9: Loading layer [==================================================>]  28.24MB/28.24MB
ae2e3edc6219: Loading layer [==================================================>]  22.02kB/22.02kB
43599ec252a3: Loading layer [==================================================>]  49.09MB/49.09MB
Loaded image: goharbor/notary-signer-photon:v0.6.1-v1.9.4
713f7d39cadb: Loading layer [==================================================>]  338.3MB/338.3MB
dc092fe63769: Loading layer [==================================================>]  119.8kB/119.8kB
Loaded image: goharbor/harbor-migrator:v1.9.4


[Step 2]: preparing environment ...
prepare base dir is set to /data/harbor/harbor
Generated configuration file: /config/log/logrotate.conf
Generated configuration file: /config/log/rsyslog_docker.conf
Generated configuration file: /config/nginx/nginx.conf
Generated configuration file: /config/core/env
Generated configuration file: /config/core/app.conf
Generated configuration file: /config/registry/config.yml
Generated configuration file: /config/registryctl/env
Generated configuration file: /config/db/env
Generated configuration file: /config/jobservice/env
Generated configuration file: /config/jobservice/config.yml
Generated and saved secret to file: /secret/keys/secretkey
Generated certificate, key file: /secret/core/private_key.pem, cert file: /secret/registry/root.crt
Generated configuration file: /compose_location/docker-compose.yml
Clean up the input dir


Note: stopping existing Harbor instance ...
Stopping harbor-db     ... done
Stopping registryctl   ... done
Stopping harbor-portal ... done
Stopping redis         ... done
Stopping harbor-log    ... done
Removing harbor-db     ... done
Removing registryctl   ... done
Removing harbor-portal ... done
Removing redis         ... done
Removing harbor-log    ... done
Removing network harbor_harbor


[Step 3]: starting Harbor ...
Creating network "harbor_harbor" with the default driver
Creating harbor-log ... done
Creating redis         ... done
Creating registry      ... done
Creating harbor-portal ... done
Creating registryctl   ... done
Creating harbor-db     ... done
Creating harbor-core   ... done
Creating harbor-jobservice ... done
Creating nginx             ... done

✔ ----Harbor has been installed and started successfully.----

Now you should be able to visit the admin portal at http://192.168.5.21. 
For more details, please visit https://github.com/goharbor/harbor .


```



浏览器访问：

http://192.168.5.21:5000/

输入: admin/Harbor12345

![image-20231215232602871](.\images\image-20231215232602871.png)



**在docker主机上配制私服**

```sh
 vi /etc/docker/daemon.json 
# 添加
"insecure-registries": ["192.168.5.21:5000"]


# 重启docker服务
systemctl daemon-reload
systemctl restart docker
```



**在harbor上新建项目**

```sh
# 新建公共项目,先创建一个私有的nullnull-edu
```

**在docker主机上登录私服上传镜像**

```sh
# 登录私服
docker login -u admin -p Harbor12345 192.168.5.21:5000

# 上传镜像
docker tag nginx:1.19.3-alpine 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine

docker push  192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine

# 退出 
docker logout 192.168.5.21:5000
```

操作：

```sh
[root@dockeros ~]# docker login -u admin -p Harbor12345 192.168.5.21:5000
WARNING! Using --password via the CLI is insecure. Use --password-stdin.
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
[root@dockeros ~]# docker tag nginx:1.19.3-alpine 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
[root@dockeros ~]# docker push  192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
The push refers to repository [192.168.5.21:5000/nullnull-edu/nginx]
8d6d1951ab0a: Pushed 
d0e26daf1f58: Pushed 
835f5b67679c: Pushed 
4daeb7840e4d: Pushed 
ace0eda3e3be: Pushed 
1.19.3-alpine: digest: sha256:a411d06ab4f5347ac9652357ac35600555aeff0b910326cc7adc36d471e0b36f size: 1360
[root@dockeros ~]# docker images
REPOSITORY                             TAG                  IMAGE ID       CREATED       SIZE
ubuntu                                 20.04                ba6acccedd29   2 years ago   72.8MB
centos                                 centos7.9.2009       eeb6ee3f44bd   2 years ago   204MB
zookeeper                              3.6.2                a72350516291   2 years ago   268MB
debian                                 10.6-slim            79fa6b1da13a   3 years ago   69.2MB
debian                                 10.6                 ef05c61d5112   3 years ago   114MB
192.168.5.21:5000/nginx                v1                   4efb29ff172a   3 years ago   21.8MB
192.168.5.21:5000/nullnull-edu/nginx   1.19.3-alpine        4efb29ff172a   3 years ago   21.8MB
nginx                                  1.19.3-alpine        4efb29ff172a   3 years ago   21.8MB
alpine                                 3.12.1               d6e46aa2470d   3 years ago   5.57MB
sonatype/nexus3                        3.28.1               d4fbb85e8101   3 years ago   634MB
mysql                                  5.7.31               42cdba9f1b08   3 years ago   448MB
centos                                 7.8.2003             afb6fca791e0   3 years ago   203MB
centos                                 centos7.8.2003       afb6fca791e0   3 years ago   203MB
tomcat                                 9.0.20-jre8-alpine   387f9d021d3a   4 years ago   108MB
tomcat                                 9.0.20-jre8-slim     66140ac62adb   4 years ago   225MB
tomcat                                 9.0.20-jre8          e24825d32965   4 years ago   464MB
webcenter/activemq                     5.14.3               ab2a33f6de2b   6 years ago   422MB
[root@dockeros ~]# docker rmi 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
Untagged: 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
Untagged: 192.168.5.21:5000/nullnull-edu/nginx@sha256:a411d06ab4f5347ac9652357ac35600555aeff0b910326cc7adc36d471e0b36f
[root@dockeros ~]# docker pull  192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
1.19.3-alpine: Pulling from nullnull-edu/nginx
Digest: sha256:a411d06ab4f5347ac9652357ac35600555aeff0b910326cc7adc36d471e0b36f
Status: Downloaded newer image for 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
[root@dockeros ~]# docker images
REPOSITORY                             TAG                  IMAGE ID       CREATED       SIZE
ubuntu                                 20.04                ba6acccedd29   2 years ago   72.8MB
centos                                 centos7.9.2009       eeb6ee3f44bd   2 years ago   204MB
zookeeper                              3.6.2                a72350516291   2 years ago   268MB
debian                                 10.6-slim            79fa6b1da13a   3 years ago   69.2MB
debian                                 10.6                 ef05c61d5112   3 years ago   114MB
192.168.5.21:5000/nginx                v1                   4efb29ff172a   3 years ago   21.8MB
192.168.5.21:5000/nullnull-edu/nginx   1.19.3-alpine        4efb29ff172a   3 years ago   21.8MB
nginx                                  1.19.3-alpine        4efb29ff172a   3 years ago   21.8MB
alpine                                 3.12.1               d6e46aa2470d   3 years ago   5.57MB
sonatype/nexus3                        3.28.1               d4fbb85e8101   3 years ago   634MB
mysql                                  5.7.31               42cdba9f1b08   3 years ago   448MB
centos                                 7.8.2003             afb6fca791e0   3 years ago   203MB
centos                                 centos7.8.2003       afb6fca791e0   3 years ago   203MB
tomcat                                 9.0.20-jre8-alpine   387f9d021d3a   4 years ago   108MB
tomcat                                 9.0.20-jre8-slim     66140ac62adb   4 years ago   225MB
tomcat                                 9.0.20-jre8          e24825d32965   4 years ago   464MB
webcenter/activemq                     5.14.3               ab2a33f6de2b   6 years ago   422MB
[root@dockeros ~]# docker logout 192.168.5.21:5000
Removing login credentials for 192.168.5.21:5000 
```

查看私服

![image-20231215233934101](.\images\image-20231215233934101.png)







## 结束
