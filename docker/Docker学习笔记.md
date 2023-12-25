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
yum update

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

升级内核至最新的版本

```sh
#1、导入仓库源
rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org

rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm

#2、查看可安装的软件包
yum --enablerepo="elrepo-kernel" list --showduplicates | sort -r | grep kernel-ml.x86_64
yum --enablerepo="elrepo-kernel" list --showduplicates | sort -r | grep kernel-lt.x86_64

#3、选择 ML 或 LT 版本安装
无指定版本默认安装最新

# 安装 ML 版本
yum --enablerepo=elrepo-kernel install kernel-ml-devel kernel-ml -y

# 安装 LT 版本，K8S全部选这个
yum --enablerepo=elrepo-kernel install kernel-lt-devel kernel-lt -y

#4、查看现有内核启动顺序
awk -F\' '$1=="menuentry " {print $2}' /etc/grub2.cfg

#5、修改默认启动项
xxx 为序号数字，以指定启动列表中第x项为启动项，x从0开始计数
grub2-set-default xxxx
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



## 构建Docker镜像

构建Docker镜像主要有3种方式：

1. 基于已有的镜像创建。
2. 基于Dockerfile来创建。
3. 基于本地模板来导入。

### 基于已有的镜像创建

commit命令

docker commit ： 从容器创建一个新的镜像。

语法：

```sh
docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]
```

常用参数：

- -a ： 提交镜像的作者。
- -c ：使用Dockerfil指令来创建镜像。
- -m ：提交时的说明文字。
- -p ： 在commit时，将容器暂停。

**案例**

结合docker cp命令自定义nginx的index页面。

操作：

```sh
# 启动容器
docker run -itd --name nginx -p 80:80 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine

# 定制首页
docker cp nginx:/usr/share/nginx/html/index.html /data/nginx/index.html
vi /data/nginx/index.html
# 将首页的中添加自定义标识信息.比如标题添加nullnull

#将首页重新拷贝至容器中
docker cp  /data/nginx/index.html nginx:/usr/share/nginx/html/index.html

# 查看页面信息
curl http://127.0.0.1

# 以现在nginx容器制作新容器
docker container commit -m "update index.html file" -a "nullnull" nginx 192.168.5.21:5000/nullnull-edu/nginx:v1.0

# 查看镜像
docker images

# 停止正在运行容器
docker stop nginx && docker rm nginx


# 运行新制作的容器
docker run -itd --name nginx -p 80:80 192.168.5.21:5000/nullnull-edu/nginx:v1.0

# 查看页面信息
curl http://127.0.0.1


# 如果需要登录
# docker login -u admin -p Harbor12345 192.168.5.21:5000
docker push 192.168.5.21:5000/nullnull-edu/nginx:v1.0
```

操作样例:

```sh
[root@dockeros nginx]# docker run -itd --name nginx -p 80:80 192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine
2d4cd9089f203ee549c4af2f6bd6e04f5948c8983c5ab871cd4a1e1369d8f7ed
[root@dockeros nginx]# docker ps
CONTAINER ID   IMAGE                                                COMMAND                  CREATED         STATUS         PORTS                               NAMES
2d4cd9089f20   192.168.5.21:5000/nullnull-edu/nginx:1.19.3-alpine   "/docker-entrypoint.…"   3 seconds ago   Up 2 seconds   0.0.0.0:80->80/tcp, :::80->80/tcp   nginx
[root@dockeros nginx]# docker cp nginx:/usr/share/nginx/html/index.html /data/nginx/index.html
Successfully copied 2.56kB to /data/nginx/index.html
[root@dockeros nginx]# cd /data/nginx
[root@dockeros nginx]# ls
index.html
[root@dockeros nginx]# vi index.html 
[root@dockeros nginx]# docker cp  /data/nginx/index.html nginx:/usr/share/nginx/html/index.html
Successfully copied 2.56kB to nginx:/usr/share/nginx/html/index.html
[root@dockeros nginx]# curl http://127.0.0.1
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nullnull  nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nullnull  nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx. - nullnull  </em></p>
</body>
</html>
[root@dockeros nginx]# docker container commit -m "update index.html file" -a "nullnull" nginx 192.168.5.21:5000/nullnull-edu/nginx:v1.0
sha256:67422fe393e07744d1b7dd02fca3dd3ab9a8ef0c6c424999f5d81363e923589e
[root@dockeros nginx]# docker images
REPOSITORY                             TAG                  IMAGE ID       CREATED          SIZE
192.168.5.21:5000/nullnull-edu/nginx   v1.0                 67422fe393e0   25 seconds ago   21.8MB
ubuntu                                 20.04                ba6acccedd29   2 years ago      72.8MB
centos                                 centos7.9.2009       eeb6ee3f44bd   2 years ago      204MB
zookeeper                              3.6.2                a72350516291   2 years ago      268MB
debian                                 10.6-slim            79fa6b1da13a   3 years ago      69.2MB
debian                                 10.6                 ef05c61d5112   3 years ago      114MB
192.168.5.21:5000/nginx                v1                   4efb29ff172a   3 years ago      21.8MB
192.168.5.21:5000/nullnull-edu/nginx   1.19.3-alpine        4efb29ff172a   3 years ago      21.8MB
nginx                                  1.19.3-alpine        4efb29ff172a   3 years ago      21.8MB
alpine                                 3.12.1               d6e46aa2470d   3 years ago      5.57MB
sonatype/nexus3                        3.28.1               d4fbb85e8101   3 years ago      634MB
mysql                                  5.7.31               42cdba9f1b08   3 years ago      448MB
centos                                 7.8.2003             afb6fca791e0   3 years ago      203MB
centos                                 centos7.8.2003       afb6fca791e0   3 years ago      203MB
tomcat                                 9.0.20-jre8-alpine   387f9d021d3a   4 years ago      108MB
tomcat                                 9.0.20-jre8-slim     66140ac62adb   4 years ago      225MB
tomcat                                 9.0.20-jre8          e24825d32965   4 years ago      464MB
webcenter/activemq                     5.14.3               ab2a33f6de2b   6 years ago      422MB
[root@dockeros nginx]# docker stop nginx && docker rm nginx
nginx
nginx
[root@dockeros nginx]# docker stop nginx && docker rm nginx
nginx
nginx
[root@dockeros nginx]# docker run -itd --name nginx -p 80:80 192.168.5.21:5000/nullnull-edu/nginx:v1.0
12700d47a2620c9ccde672f7e8d97d3a6633ec7040a10f1f6cdfc29d20aaf564
[root@dockeros nginx]# curl http://127.0.0.1
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nullnull  nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nullnull  nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx. - nullnull  </em></p>
</body>
</html>
[root@dockeros nginx]# docker push 192.168.5.21:5000/nullnull-edu/nginx:v1.0
The push refers to repository [192.168.5.21:5000/nullnull-edu/nginx]
19b16b03844a: Preparing 
8d6d1951ab0a: Preparing 
d0e26daf1f58: Preparing 
835f5b67679c: Preparing 
4daeb7840e4d: Preparing 
ace0eda3e3be: Waiting 
denied: requested access to the resource is denied
[root@dockeros nginx]# docker login -u admin -p Harbor12345 192.168.5.21:5000
WARNING! Using --password via the CLI is insecure. Use --password-stdin.
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
[root@dockeros nginx]# 
[root@dockeros nginx]# docker push 192.168.5.21:5000/nullnull-edu/nginx:v1.0
The push refers to repository [192.168.5.21:5000/nullnull-edu/nginx]
19b16b03844a: Pushed 
8d6d1951ab0a: Layer already exists 
d0e26daf1f58: Layer already exists 
835f5b67679c: Layer already exists 
4daeb7840e4d: Layer already exists 
ace0eda3e3be: Layer already exists 
v1.0: digest: sha256:7cdbe6a1b2990717b9b0cf0468e472c3b377a242acbb5c2f416a224156882fff size: 1568
[root@dockeros nginx]# 
```



### 使用DOckerFile构建镜像

参考官网地址:

```http
https://docs.docker.com/engine/reference/builder/
```

>Dockerfile 其实就是我们用来构建Docker镜像的源码，当然这个不是所谓编程源码，而是一些命令集合，只要理解它的逻辑和语法格式，就可以很容器编写Dockerfile。简单点说，Dockerfile可以让用户个性化定制Docker镜像。因为工作环境中需求各式各样，网络上的镜像很难满足实际的需求。

**Dockerfile的基本结构**

Dockerfile是一个包含用于组合映射的命令的文本文档。可以使用命令行调用任何命令。Docker通过读取Dockerfile中的指令自动生成映射。

Docker build命令用于从Dockerfile构建镜像。可以在docker build命令中使用`-f`标识指向文件系统中的任何位置的Dockerfile。

Dockerfile由一行行命令语句组成，并且支持以#开头的注释行。

DOckerfile分为四部分：基础镜像信息、维护者信息、镜像操作指令和容器启动时执行指令。

**Dockerfile文件说明**

Docker以从上到下的顺序运行Dockerfile的指令。为了指定基本镜像，第一指令必须是FROM。一个声明以`#`字符开头则被视为注释。可以Docker文件中使用`RUN`,`CMD`,`FROM`,`EXPOSE`,`ENV`等指令。

Dockerfile常用命令

| 命令       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| FROM       | 指定基础镜像，必须为第一个命令                               |
| MAINTAINER | 维护者（作者）信息                                           |
| ENV        | 设置环境变量                                                 |
| RUN        | 构建镜像时执行的命令                                         |
| CMD        | 构建容器后调用，也就是在容器启动时才进行调用。               |
| ENTRYPOINT | 指定运行容器启动过程时执行的命令，覆盖CMD参数<br/>ENTRYPOINT与CMD非常类似，不同的是通过docker run执行的命令不会覆盖ENTRYPOINT，而docker run指定的任何参数，都会被当做参数两次传递给ENTRYPOINT。Dockerfile只允许有一个ENTRYPOINT命令，多次指定时会覆盖着面的设置，而只执行最后的ENTRYPOINT指令。 |
| ADD        | 将本地文件添加到容器中，tar类型会自动解压（网络压缩资源不会被解压），可以访问网络资源，类似wget。 |
| COPY       | 功能类似ADD，但是不会自动解压文件。也不能访问网络资源。      |
| WORKDIR    | 工作目录，类似于cd命令                                       |
| ARG        | 用于指定传递给构建运行时的变量                               |
| VOLUMN     | 用于指定持久化目录                                           |
| EXPOSE     | 指定于外界交互的端口。                                       |
| USER       | 指定运行容器时的用户名或UID，后续的RUN也会使用指定用户。使用USER指定用户时，可以使用用户名、UID或者GID，或者两者组合。当服务不需要管理员权限时，可以通过该命令指定运行用户。并且可以在之前创建所需要的用户。 |





### 使用Dockerfile构建mysql，同时设置时区

此文件建议在IDEA中编写，注意使用IDEA的高版本。目前测试IDEA2023版本没有问题，Dockerfile、docker-compose.yml文件大部分内容会有提示信息，方便开发。

```sh
# IDEA的插件：官网地址：
https://plugins.jetbrains.com/plugin/7724-docker/versions
```

Dockerfile文件

注意需要使用`Dockerfile`文件为文件名，默认以这个为文件名，可直接编译。

```sh
FROM mysql:5.7.31
# 作者信息
MAINTAINER mysql from data UTC by Asia/Shanghai "nullnull"

ENV TZ Asia/Shanghai
```

build命令

docker build命令用于使用Dockerfile创建镜像

语法：

```sh
docker build [OPTIONS] PATH | URL | -
```

常见参数特别多。这里只给几个常见的参数

- --build-arg=[] : 设置镜像创建时的变量；
- -f ： 指定要使用Dockerfile路径
- --rm ： 设置镜像成功后删除中间容器。
- --tag，-t：镜像的名字及标签，通常name:tag 或者name格式；可以在一次构建中为一个镜像设置多个标签。

相关的详细参数参考：

```sh
[root@dockeros build]# docker build --help

Usage:  docker buildx build [OPTIONS] PATH | URL | -

Start a build

Aliases:
  docker buildx build, docker buildx b

Options:
      --add-host strings              Add a custom host-to-IP mapping (format: "host:ip")
      --allow strings                 Allow extra privileged entitlement (e.g., "network.host", "security.insecure")
      --attest stringArray            Attestation parameters (format: "type=sbom,generator=image")
      --build-arg stringArray         Set build-time variables
      --build-context stringArray     Additional build contexts (e.g., name=path)
      --builder string                Override the configured builder instance (default "default")
      --cache-from stringArray        External cache sources (e.g., "user/app:cache", "type=local,src=path/to/dir")
      --cache-to stringArray          Cache export destinations (e.g., "user/app:cache", "type=local,dest=path/to/dir")
      --cgroup-parent string          Optional parent cgroup for the container
  -f, --file string                   Name of the Dockerfile (default: "PATH/Dockerfile")
      --iidfile string                Write the image ID to the file
      --label stringArray             Set metadata for an image
      --load                          Shorthand for "--output=type=docker"
      --metadata-file string          Write build result metadata to the file
      --network string                Set the networking mode for the "RUN" instructions during build (default "default")
      --no-cache                      Do not use cache when building the image
      --no-cache-filter stringArray   Do not cache specified stages
  -o, --output stringArray            Output destination (format: "type=local,dest=path")
      --platform stringArray          Set target platform for build
      --progress string               Set type of progress output ("auto", "plain", "tty"). Use plain to show container output (default "auto")
      --provenance string             Shorthand for "--attest=type=provenance"
      --pull                          Always attempt to pull all referenced images
      --push                          Shorthand for "--output=type=registry"
  -q, --quiet                         Suppress the build output and print image ID on success
      --sbom string                   Shorthand for "--attest=type=sbom"
      --secret stringArray            Secret to expose to the build (format: "id=mysecret[,src=/local/secret]")
      --shm-size bytes                Size of "/dev/shm"
      --ssh stringArray               SSH agent socket or keys to expose to the build (format: "default|<id>[=<socket>|<key>[,<key>]]")
  -t, --tag stringArray               Name and optionally a tag (format: "name:tag")
      --target string                 Set the target build stage to build
      --ulimit ulimit                 Ulimit options (default [])
[root@dockeros build]# 
```



```sh
# 制作镜像
docker build --rm -t 192.168.5.21:5000/nullnull-edu/mysql:v5.7.31 .

docker images

# 运行镜像
docker run -itd --name mysql --restart always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin 192.168.5.21:5000/nullnull-edu/mysql:v5.7.31

docker logs -f mysql

docker exec -it mysql bash
date

#测试连接
mysql -uroot -padmin

```

样例日志:

```sh
[root@dockeros build]# docker build --rm -t 192.168.5.21:5000/nullnull-edu/mysql:v5.7.31 .
[+] Building 0.4s (5/5) FINISHED                                                                                                                                                                       docker:default
 => [internal] load .dockerignore                                                                                                                                                                                0.1s
 => => transferring context: 2B                                                                                                                                                                                  0.0s
 => [internal] load build definition from Dockerfile                                                                                                                                                             0.1s
 => => transferring dockerfile: 207B                                                                                                                                                                             0.0s
 => [internal] load metadata for docker.io/library/mysql:5.7.31                                                                                                                                                  0.0s
 => [1/1] FROM docker.io/library/mysql:5.7.31                                                                                                                                                                    0.3s
 => exporting to image                                                                                                                                                                                           0.0s
 => => exporting layers                                                                                                                                                                                          0.0s
 => => writing image sha256:e663c6c969df25b3c77b27e364002c95ae0f4d2e54abfb2126363fb142ac6b98                                                                                                                     0.0s
 => => naming to 192.168.5.21:5000/nullnull-edu/mysql:v5.7.31                                                                                                                                                    0.0s
[root@dockeros build]# lsd
-bash: lsd: command not found
[root@dockeros build]# ls
Dockerfile
[root@dockeros build]# docker images
REPOSITORY                             TAG                  IMAGE ID       CREATED        SIZE
192.168.5.21:5000/nullnull-edu/nginx   v1.0                 67422fe393e0   24 hours ago   21.8MB
ubuntu                                 20.04                ba6acccedd29   2 years ago    72.8MB
centos                                 centos7.9.2009       eeb6ee3f44bd   2 years ago    204MB
zookeeper                              3.6.2                a72350516291   2 years ago    268MB
debian                                 10.6-slim            79fa6b1da13a   3 years ago    69.2MB
debian                                 10.6                 ef05c61d5112   3 years ago    114MB
192.168.5.21:5000/nginx                v1                   4efb29ff172a   3 years ago    21.8MB
192.168.5.21:5000/nullnull-edu/nginx   1.19.3-alpine        4efb29ff172a   3 years ago    21.8MB
nginx                                  1.19.3-alpine        4efb29ff172a   3 years ago    21.8MB
alpine                                 3.12.1               d6e46aa2470d   3 years ago    5.57MB
sonatype/nexus3                        3.28.1               d4fbb85e8101   3 years ago    634MB
192.168.5.21:5000/nullnull-edu/mysql   v5.7.31              e663c6c969df   3 years ago    448MB
mysql                                  5.7.31               42cdba9f1b08   3 years ago    448MB
centos                                 7.8.2003             afb6fca791e0   3 years ago    203MB
centos                                 centos7.8.2003       afb6fca791e0   3 years ago    203MB
tomcat                                 9.0.20-jre8-alpine   387f9d021d3a   4 years ago    108MB
tomcat                                 9.0.20-jre8-slim     66140ac62adb   4 years ago    225MB
tomcat                                 9.0.20-jre8          e24825d32965   4 years ago    464MB
webcenter/activemq                     5.14.3               ab2a33f6de2b   6 years ago    422MB
[root@dockeros build]# docker ps -a
CONTAINER ID   IMAGE                                       COMMAND                  CREATED        STATUS                    PORTS     NAMES
12700d47a262   192.168.5.21:5000/nullnull-edu/nginx:v1.0   "/docker-entrypoint.…"   24 hours ago   Exited (0) 23 hours ago             nginx
441c15c8b771   nginx:1.19.3-alpine                         "/docker-entrypoint.…"   3 days ago     Exited (0) 2 days ago               nginx02
7157de8950f7   nginx:1.19.3-alpine                         "/docker-entrypoint.…"   3 days ago     Exited (0) 2 days ago               nginx01
d10ca8754416   centos:7.8.2003                             "/bin/bash"              3 days ago     Exited (0) 3 days ago               nullnull-volume
[root@dockeros build]# docker run -itd --name mysql --restart always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin 192.168.5.21:5000/nullnull-edu/mysql:v5.7.31
ca73b705a8d0ccca160db79d6caf3964688198b345dd8eb1213b930bf13d33c6
[root@dockeros build]# docker logs -f mysql
2023-12-17 23:13:02+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-17 23:13:02+08:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2023-12-17 23:13:02+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-17 23:13:02+08:00 [Note] [Entrypoint]: Initializing database files
2023-12-17T15:13:02.408216Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-17T15:13:03.294862Z 0 [Warning] InnoDB: New log files created, LSN=45790
2023-12-17T15:13:03.351363Z 0 [Warning] InnoDB: Creating foreign key constraint system tables.
2023-12-17T15:13:03.364139Z 0 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: c608395b-9cee-11ee-8df9-0242ac110002.
2023-12-17T15:13:03.367569Z 0 [Warning] Gtid table is not ready to be used. Table 'mysql.gtid_executed' cannot be opened.
2023-12-17T15:13:03.761089Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-17T15:13:04.027107Z 1 [Warning] root@localhost is created with an empty password ! Please consider switching off the --initialize-insecure option.
2023-12-17 23:13:08+08:00 [Note] [Entrypoint]: Database files initialized
2023-12-17 23:13:08+08:00 [Note] [Entrypoint]: Starting temporary server
2023-12-17 23:13:08+08:00 [Note] [Entrypoint]: Waiting for server startup
2023-12-17T15:13:09.027778Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-17T15:13:09.033119Z 0 [Note] mysqld (mysqld 5.7.31) starting as process 79 ...
2023-12-17T15:13:09.038132Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-17T15:13:09.038174Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-17T15:13:09.038178Z 0 [Note] InnoDB: Uses event mutexes
2023-12-17T15:13:09.038179Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-17T15:13:09.038181Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-17T15:13:09.038183Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-17T15:13:09.038809Z 0 [Note] InnoDB: Number of pools: 1
2023-12-17T15:13:09.039161Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-17T15:13:09.043680Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-17T15:13:09.063493Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-17T15:13:09.069825Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-17T15:13:09.085198Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-17T15:13:09.094938Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-17T15:13:09.096154Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-17T15:13:09.128262Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-17T15:13:09.128659Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-17T15:13:09.128678Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-17T15:13:09.132344Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 2719885
2023-12-17T15:13:09.132574Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-17T15:13:09.133031Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-17T15:13:09.139130Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231217 23:13:09
2023-12-17T15:13:09.151151Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-17T15:13:09.151316Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-17T15:13:09.151618Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-17T15:13:09.151675Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-17T15:13:09.155191Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-17T15:13:09.160031Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-17T15:13:09.161054Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31'  socket: '/var/run/mysqld/mysqld.sock'  port: 0  MySQL Community Server (GPL)
2023-12-17 23:13:09+08:00 [Note] [Entrypoint]: Temporary server started.
Warning: Unable to load '/usr/share/zoneinfo/iso3166.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/leap-seconds.list' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone1970.tab' as time zone. Skipping it.

2023-12-17 23:13:12+08:00 [Note] [Entrypoint]: Stopping temporary server
2023-12-17T15:13:12.442600Z 0 [Note] Giving 0 client threads a chance to die gracefully
2023-12-17T15:13:12.442655Z 0 [Note] Shutting down slave threads
2023-12-17T15:13:12.442660Z 0 [Note] Forcefully disconnecting 0 remaining clients
2023-12-17T15:13:12.442665Z 0 [Note] Event Scheduler: Purging the queue. 0 events
2023-12-17T15:13:12.442778Z 0 [Note] Binlog end
2023-12-17T15:13:12.443622Z 0 [Note] Shutting down plugin 'ngram'
2023-12-17T15:13:12.443694Z 0 [Note] Shutting down plugin 'partition'
2023-12-17T15:13:12.443700Z 0 [Note] Shutting down plugin 'BLACKHOLE'
2023-12-17T15:13:12.443705Z 0 [Note] Shutting down plugin 'ARCHIVE'
2023-12-17T15:13:12.443707Z 0 [Note] Shutting down plugin 'PERFORMANCE_SCHEMA'
2023-12-17T15:13:12.443765Z 0 [Note] Shutting down plugin 'MRG_MYISAM'
2023-12-17T15:13:12.443768Z 0 [Note] Shutting down plugin 'MyISAM'
2023-12-17T15:13:12.443775Z 0 [Note] Shutting down plugin 'INNODB_SYS_VIRTUAL'
2023-12-17T15:13:12.443778Z 0 [Note] Shutting down plugin 'INNODB_SYS_DATAFILES'
2023-12-17T15:13:12.443781Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESPACES'
2023-12-17T15:13:12.443783Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN_COLS'
2023-12-17T15:13:12.443786Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN'
2023-12-17T15:13:12.443788Z 0 [Note] Shutting down plugin 'INNODB_SYS_FIELDS'
2023-12-17T15:13:12.443833Z 0 [Note] Shutting down plugin 'INNODB_SYS_COLUMNS'
2023-12-17T15:13:12.443836Z 0 [Note] Shutting down plugin 'INNODB_SYS_INDEXES'
2023-12-17T15:13:12.443839Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESTATS'
2023-12-17T15:13:12.443841Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLES'
2023-12-17T15:13:12.443844Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_TABLE'
2023-12-17T15:13:12.443846Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_CACHE'
2023-12-17T15:13:12.443848Z 0 [Note] Shutting down plugin 'INNODB_FT_CONFIG'
2023-12-17T15:13:12.443850Z 0 [Note] Shutting down plugin 'INNODB_FT_BEING_DELETED'
2023-12-17T15:13:12.443854Z 0 [Note] Shutting down plugin 'INNODB_FT_DELETED'
2023-12-17T15:13:12.443858Z 0 [Note] Shutting down plugin 'INNODB_FT_DEFAULT_STOPWORD'
2023-12-17T15:13:12.443860Z 0 [Note] Shutting down plugin 'INNODB_METRICS'
2023-12-17T15:13:12.443861Z 0 [Note] Shutting down plugin 'INNODB_TEMP_TABLE_INFO'
2023-12-17T15:13:12.443862Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_POOL_STATS'
2023-12-17T15:13:12.443863Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE_LRU'
2023-12-17T15:13:12.443864Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE'
2023-12-17T15:13:12.443866Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX_RESET'
2023-12-17T15:13:12.443867Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX'
2023-12-17T15:13:12.443869Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM_RESET'
2023-12-17T15:13:12.443870Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM'
2023-12-17T15:13:12.443872Z 0 [Note] Shutting down plugin 'INNODB_CMP_RESET'
2023-12-17T15:13:12.443873Z 0 [Note] Shutting down plugin 'INNODB_CMP'
2023-12-17T15:13:12.443875Z 0 [Note] Shutting down plugin 'INNODB_LOCK_WAITS'
2023-12-17T15:13:12.443876Z 0 [Note] Shutting down plugin 'INNODB_LOCKS'
2023-12-17T15:13:12.443878Z 0 [Note] Shutting down plugin 'INNODB_TRX'
2023-12-17T15:13:12.443879Z 0 [Note] Shutting down plugin 'InnoDB'
2023-12-17T15:13:12.443938Z 0 [Note] InnoDB: FTS optimize thread exiting.
2023-12-17T15:13:12.444265Z 0 [Note] InnoDB: Starting shutdown...
2023-12-17T15:13:12.576952Z 0 [Note] InnoDB: Dumping buffer pool(s) to /var/lib/mysql/ib_buffer_pool
2023-12-17T15:13:12.577216Z 0 [Note] InnoDB: Buffer pool(s) dump completed at 231217 23:13:12
2023-12-17T15:13:14.003535Z 0 [Note] InnoDB: Shutdown completed; log sequence number 12578046
2023-12-17T15:13:14.004553Z 0 [Note] InnoDB: Removed temporary tablespace data file: "ibtmp1"
2023-12-17T15:13:14.004567Z 0 [Note] Shutting down plugin 'MEMORY'
2023-12-17T15:13:14.004571Z 0 [Note] Shutting down plugin 'CSV'
2023-12-17T15:13:14.004574Z 0 [Note] Shutting down plugin 'sha256_password'
2023-12-17T15:13:14.004575Z 0 [Note] Shutting down plugin 'mysql_native_password'
2023-12-17T15:13:14.004674Z 0 [Note] Shutting down plugin 'binlog'
2023-12-17T15:13:14.004942Z 0 [Note] mysqld: Shutdown complete

2023-12-17 23:13:14+08:00 [Note] [Entrypoint]: Temporary server stopped

2023-12-17 23:13:14+08:00 [Note] [Entrypoint]: MySQL init process done. Ready for start up.

2023-12-17T15:13:14.594253Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-17T15:13:14.594937Z 0 [Note] mysqld (mysqld 5.7.31) starting as process 1 ...
2023-12-17T15:13:14.596912Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-17T15:13:14.596956Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-17T15:13:14.596960Z 0 [Note] InnoDB: Uses event mutexes
2023-12-17T15:13:14.596962Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-17T15:13:14.596963Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-17T15:13:14.596965Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-17T15:13:14.597071Z 0 [Note] InnoDB: Number of pools: 1
2023-12-17T15:13:14.597257Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-17T15:13:14.598635Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-17T15:13:14.603141Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-17T15:13:14.604679Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-17T15:13:14.617528Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-17T15:13:14.625284Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-17T15:13:14.625473Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-17T15:13:14.639998Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-17T15:13:14.640819Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-17T15:13:14.640879Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-17T15:13:14.641546Z 0 [Note] InnoDB: Waiting for purge to start
2023-12-17T15:13:14.693333Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 12578046
2023-12-17T15:13:14.693677Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-17T15:13:14.693891Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-17T15:13:14.695282Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231217 23:13:14
2023-12-17T15:13:14.696752Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-17T15:13:14.696891Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-17T15:13:14.697233Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-17T15:13:14.697249Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-17T15:13:14.697751Z 0 [Note] Server hostname (bind-address): '*'; port: 3306
2023-12-17T15:13:14.697973Z 0 [Note] IPv6 is available.
2023-12-17T15:13:14.698030Z 0 [Note]   - '::' resolves to '::';
2023-12-17T15:13:14.698049Z 0 [Note] Server socket created on IP: '::'.
2023-12-17T15:13:14.700348Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-17T15:13:14.704283Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-17T15:13:14.704717Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
^C
[root@dockeros build]# docker exec -it mysql bash
root@ca73b705a8d0:/# date
Sun Dec 17 23:13:30 CST 2023
root@ca73b705a8d0:/# mysql -uroot -padmin
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.31 MySQL Community Server (GPL)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 
```





## 部署微服务

### 部署数据库

user.sql

```sh
# 创建数据库并
CREATE DATABASE nullnulldb CHARACTER SET utf8 COLLATE utf8_bin;

# 切换数据库
use nullnulldb;

# 创建表语句
CREATE TABLE data_user (
 userid int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20) COLLATE utf8_bin DEFAULT NULL,
 password varchar(20) COLLATE utf8_bin DEFAULT NULL,
 userroles varchar(2) COLLATE utf8_bin DEFAULT NULL,
 nickname varchar(50) COLLATE utf8_bin DEFAULT NULL,
 PRIMARY KEY (userid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# 插入数据
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES 
('admin','1234','04','超级管理员'),
('nullnull','1234','03','管理员');
```





### 项目介绍

1. 使用SpringBoot技术
2. 数据库使用Mysql
3. springboot项目Docker容器化部署
4. MySQL数据库容器化部署

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
	<groupId>com.nullnull.learn</groupId>
    <artifactId>docker-spring-boot</artifactId>
    <version>1.0-SNAPSHOT</version>
   

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
        <mybatispluins.version>3.3.2</mybatispluins.version>
        <mysql.version>5.1.47</mysql.version>

    </properties>


    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatispluins.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.49</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>${project.name}</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!--跳过单元测试-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

**实体信息**

```java
package com.nullnull.learn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@TableName("data_user")
public class DataUserPO {

    @TableId("userid")
    private Integer userId;

    @TableField("username")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("userroles")
    private String userRoles;

    @TableField("nickname")
    private String nickName;

}

```



**mapper接口**

```java
package com.nullnull.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nullnull.learn.entity.DataUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库操作
 *
 */
@Mapper
public interface DataUserMapper extends BaseMapper<DataUserPO> {
}

```

**Service接口**

```java
package com.nullnull.learn.service;

import com.nullnull.learn.entity.DataUserPO;
import java.util.List;


public interface DataUserService {

    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<DataUserPO> queryUsers();

}

```

**ServiceImpl**

```java
package com.nullnull.learn.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nullnull.learn.entity.DataUserPO;
import com.nullnull.learn.mapper.DataUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataUserServiceImpl implements DataUserService {

    @Autowired
    private DataUserMapper userMapper;

    @Override
    public List<DataUserPO> queryUsers() {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }
}

```

**Controller**

```java
package com.nullnull.learn.controller;

import com.nullnull.learn.entity.DataUserPO;
import com.nullnull.learn.service.DataUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private DataUserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<DataUserPO> dataList() {
        List<DataUserPO> dataUserList = userService.queryUsers();
        return dataUserList;
    }
}
```



**启动类**

```java
package com.nullnull.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}

```



**application.yaml**

```
server:
  port: 8822
spring:
  datasource:
   driver-class-name: com.mysql.jdbc.Driver
   username: root
   password: admin
   url: jdbc:mysql://192.168.5.20:3306/nullnulldb?characterEncoding=utf8&useSSL=false&useTimezone=true&serverTimezone=GMT%2B8
   mybatis-plus:
     type-aliases-package: com.nullnull.learn.entity
     mapper-locations: mapper/*.xml
     configuration:
     #配置日志打印方式。不使用mybatis的日志信息。使用mp的日志配置
     log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```



本地可访问：

```http
http://127.0.0.1:8822/users
```

可看到响应:

```json
[
	{
		"userId": 3,
		"userName": "admin",
		"password": "1234",
		"userRoles": "04",
		"nickName": "超级管理员"
	},
	{
		"userId": 4,
		"userName": "nullnull",
		"password": "1234",
		"userRoles": "03",
		"nickName": "管理员"
	}
]
```



### 部署Mysql

主要步骤：

1. 清理20主机上的内容
2. 安装docker-compose
3. 脱离开发环境部署测试项目。
4. 部署MySQL容器
5. 制作MySQL自定义镜像。

```sh
# 1 清理容器
docker rm $(docker stop $(docker ps -qa))

# 2. 已经安装了docker-compose不再重复

# 运行MySQL镜像
docker run -itd --name mysql --restart always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin mysql:5.7.31

# 使用工具将SQL在MySQL中执行

```





日志：

```sh
[root@dockeros ~]# docker rm $(docker stop $(docker ps -qa))
ca73b705a8d0
12700d47a262
441c15c8b771
7157de8950f7
d10ca8754416
[root@dockeros ~]# docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@dockeros ~]# docker-compose -v
docker-compose version 1.27.4, build 40524192
[root@dockeros ~]# docker-compose version
docker-compose version 1.27.4, build 40524192
docker-py version: 4.3.1
CPython version: 3.7.7
OpenSSL version: OpenSSL 1.1.0l  10 Sep 2019
[root@dockeros ~]# docker run -itd --name mysql -p 3306:3306 --restart always -e MYSQL_ROOT_PASSWORD=admin mysql:5.7.31
fd3ded59aa2903036326dcfdc021222e255e6733c61915f8cc62d78d8995de3a
[root@dockeros ~]# 
```



本地执行：

```sh
# 本地使用maven打包，并启动
mvn clean package -DskipTests

java -jar docker-spring-boot.jar
```

通过浏览器访问：

http://127.0.0.1:8822/users

如果成功将得到正确的响应：

```sh
[
	{
		"userId": 3,
		"userName": "admin",
		"password": "1234",
		"userRoles": "04",
		"nickName": "超级管理员"
	},
	{
		"userId": 4,
		"userName": "nullnull",
		"password": "1234",
		"userRoles": "03",
		"nickName": "管理员"
	}
]
```



### Dockerfile构建mysql镜像

主要分为两个步骤：

- 在容器启动时，设置时区。
- 在容器启动时，直接导入数据。

Dockerfile

```sh
FROM mysql:5.7.31

# 作者信息
MAINTAINER mysql from date UTC by Asia/Shanghai "nullnull"

ENV TZ Asia/Shanghai
# 加载SQL文件至初始化数据的目录
COPY user.sql /docker-entrypoint-initdb.d
```

执行构建,注意user.sql文件与Dockerfile文件同级目录

```sh
# 注意清理旧容器
docker rm $(docker stop $(docker ps -qa))


docker build --rm -t 192.168.5.21:5000/nullnull-edu/mysql:5.7.1 .
docker images

# 运行命令
docker run -itd --name mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -v /data/mysql:/var/lib/mysql 192.168.5.21:5000/nullnull-edu/mysql:5.7.1 --character-set-server=utf8 --collation-server=utf8_general_ci

# 推送到仓库中
docker push 192.168.5.21:5000/nullnull-edu/mysql:5.7.1
```



输出:

```sh
[root@dockeros build]# docker rm $(docker stop $(docker ps -qa))
3b8bd9866f45
[root@dockeros build]# docker build --rm -t 192.168.5.21:5000/nullnull-edu/mysql:5.7.1 .
[+] Building 0.1s (7/7) FINISHED                                                                        docker:default
 => [internal] load .dockerignore                                                                                 0.0s
 => => transferring context: 2B                                                                                   0.0s
 => [internal] load build definition from Dockerfile                                                              0.0s
 => => transferring dockerfile: 254B                                                                              0.0s
 => [internal] load metadata for docker.io/library/mysql:5.7.31                                                   0.0s
 => [internal] load build context                                                                                 0.0s
 => => transferring context: 769B                                                                                 0.0s
 => CACHED [1/2] FROM docker.io/library/mysql:5.7.31                                                              0.0s
 => [2/2] COPY user.sql /docker-entrypoint-initdb.d                                                               0.0s
 => exporting to image                                                                                            0.0s
 => => exporting layers                                                                                           0.0s
 => => writing image sha256:faaced0d71f2add2addd241dd4827d98f4cb0384507feee68afd7e59340cbf52                      0.0s
 => => naming to 192.168.5.21:5000/nullnull-edu/mysql:5.7.1     
 [root@dockeros build]# docker images
REPOSITORY                             TAG                  IMAGE ID       CREATED              SIZE
192.168.5.21:5000/nullnull-edu/mysql   5.7.1                faaced0d71f2   About a minute ago   448MB
192.168.5.21:5000/nullnull-edu/nginx   v1.0                 67422fe393e0   44 hours ago         21.8MB
ubuntu                                 20.04                ba6acccedd29   2 years ago          72.8MB
centos                                 centos7.9.2009       eeb6ee3f44bd   2 years ago          204MB
zookeeper                              3.6.2                a72350516291   2 years ago          268MB
debian                                 10.6-slim            79fa6b1da13a   3 years ago          69.2MB
debian                                 10.6                 ef05c61d5112   3 years ago          114MB
192.168.5.21:5000/nullnull-edu/nginx   1.19.3-alpine        4efb29ff172a   3 years ago          21.8MB
nginx                                  1.19.3-alpine        4efb29ff172a   3 years ago          21.8MB
192.168.5.21:5000/nginx                v1                   4efb29ff172a   3 years ago          21.8MB
alpine                                 3.12.1               d6e46aa2470d   3 years ago          5.57MB
sonatype/nexus3                        3.28.1               d4fbb85e8101   3 years ago          634MB
mysql                                  5.7.31               42cdba9f1b08   3 years ago          448MB
192.168.5.21:5000/nullnull-edu/mysql   v5.7.31              e663c6c969df   3 years ago          448MB
centos                                 7.8.2003             afb6fca791e0   3 years ago          203MB
centos                                 centos7.8.2003       afb6fca791e0   3 years ago          203MB
tomcat                                 9.0.20-jre8-alpine   387f9d021d3a   4 years ago          108MB
tomcat                                 9.0.20-jre8-slim     66140ac62adb   4 years ago          225MB
tomcat                                 9.0.20-jre8          e24825d32965   4 years ago          464MB
webcenter/activemq                     5.14.3               ab2a33f6de2b   6 years ago          422MB
[root@dockeros build]# docker run -itd --name mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -v /data/mysql:/var/lib/mysql 192.168.5.21:5000/nullnull-edu/mysql:5.7.1 --character-set-server=utf8 --collation-server=utf8_general_ci
df6076bc5fe40296b110d55012977ba752be21b3de04fdf33925f685f4f61f65
[root@dockeros mysql]# docker push 192.168.5.21:5000/nullnull-edu/mysql:5.7.1
The push refers to repository [192.168.5.21:5000/nullnull-edu/mysql]
650f81b23e85: Pushed 
bdda49371b83: Pushed 
78a9edf56b5f: Pushed 
2e19acd09cf6: Pushed 
30f9c7764a3f: Pushed 
15b463db445c: Pushed 
c21e35e55228: Pushed 
36b89ee4c647: Pushed 
9dae2565e824: Pushed 
ec8c80284c72: Pushed 
329fe06a30f0: Pushed 
d0fe97fa8b8c: Pushed 
5.7.1: digest: sha256:ad927c0e00826fddec86bcbedc7709bc44307e76e358fd2a7a48c1a6ea5baaad size: 2828
[root@dockeros mysql]# 

```



### Dockerfile构建项目

Dockerfile

```sh
FROM openjdk:8-alpine3.9

# 作者信息
MAINTAINER nullnull docker springboot "nullnull"

# 修改源
RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" >> /etc/apk/repositories && \
  echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories
# 安装需要的软件，解决时区问题
RUN apk --update add curl bash tzdata && \
  rm -rf /var/cache/apk/*

# 修改镜像为东八区时间
ENV TZ Asia/Shanghai
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8822
ENTRYPOINT ["java","-jar","/app.jar"]
```

上传Dockerfile文件和sprintboot的jar包,这两上文件需要在同一个目录下

```sh
# 构建镜像
docker build --rm -t nullnull/docker-spring-boot:v1 --build-arg JAR_FILE=docker-spring-boot.jar .

# 运行镜像
docker run -itd --name docker-spring-boot -p 8822:8822 nullnull/docker-spring-boot:v1

# 查看日志
docker logs -f docker-spring-boot

# 便可以通过浏览器访问
http://192.168.5.20:8822/users


# 停止
docker stop docker-spring-boot

docker rm docker-spring-boot
```

样例

```sh
[root@dockeros springboot]# ls
Dockerfile  docker-spring-boot.jar

[root@dockeros springboot]# docker build --rm -t nullnull/docker-spring-boot:v1 --build-arg JAR_FILE=docker-spring-boot.jar .
[+] Building 192.4s (9/9) FINISHED                                                                      docker:default
 => [internal] load build definition from Dockerfile                                                              0.2s
 => => transferring dockerfile: 643B                                                                              0.0s
 => [internal] load .dockerignore                                                                                 0.2s
 => => transferring context: 2B                                                                                   0.0s
 => [internal] load metadata for docker.io/library/openjdk:8-alpine3.9                                            0.0s
 => [1/4] FROM docker.io/library/openjdk:8-alpine3.9                                                              0.0s
 => [internal] load build context                                                                                 0.0s
 => => transferring context: 103B                                                                                 0.0s
 => CACHED [2/4] RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" >> /etc/apk/repositories &&   e  0.0s
 => [3/4] RUN apk --update add curl bash tzdata &&   rm -rf /var/cache/apk/*                                    190.2s
 => [4/4] COPY docker-spring-boot.jar app.jar                                                                     0.3s
 => exporting to image                                                                                            1.2s 
 => => exporting layers                                                                                           0.8s 
 => => writing image sha256:51dd3b84a450e62c6c6910598593634af5bdd79c2e4dd754f52ca92c8a3f18e1                      0.0s 
 => => naming to docker.io/nullnull/docker-spring-boot:v1     
 
 [root@dockeros springboot]# docker run -itd --name docker-spring-boot -p 8822:8822 nullnull/docker-spring-boot:v1
227cf70762320eeee3b4cab72e6ac0f7bf53189c630f1b257d8f0ee788c8be06
[root@dockeros springboot]# docker logs -f docker-spring-boot

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.4.RELEASE)

2023-12-18 20:33:26.399  INFO 1 --- [           main] com.nullnull.learn.BootApplication       : Starting BootApplication v1.0-SNAPSHOT on 227cf7076232 with PID 1 (/app.jar started by root in /)
2023-12-18 20:33:26.401  INFO 1 --- [           main] com.nullnull.learn.BootApplication       : No active profile set, falling back to default profiles: default
2023-12-18 20:33:27.477  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8822 (http)
2023-12-18 20:33:27.488  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-12-18 20:33:27.488  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.38]
2023-12-18 20:33:27.553  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-12-18 20:33:27.553  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1086 ms
 _ _   |_  _ _|_. ___ _ |    _ 
| | |\/|_)(_| | |_\  |_)||_|_\ 
     /               |         
                        3.3.2 
2023-12-18 20:33:27.981  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2023-12-18 20:33:28.231  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8822 (http) with context path ''
2023-12-18 20:33:28.239  INFO 1 --- [           main] com.nullnull.learn.BootApplication       : Started BootApplication in 2.296 seconds (JVM running for 2.638)
2023-12-18 20:33:50.758  INFO 1 --- [nio-8822-exec-3] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-12-18 20:33:50.758  INFO 1 --- [nio-8822-exec-3] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-12-18 20:33:50.762  INFO 1 --- [nio-8822-exec-3] o.s.web.servlet.DispatcherServlet        : Completed initialization in 4 ms
2023-12-18 20:33:50.806  INFO 1 --- [nio-8822-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-12-18 20:33:50.962  INFO 1 --- [nio-8822-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
^C
[root@dockeros springboot]# 
```





## Docker安装Mysql主从复制

### 使用MySQL官方提供的镜像制作主从复制服务器集群。

**概念**

将主数据库的增删改查等操作记录到二进制日志文件中，从数据库接收主库日志文件，根据最后一次更新的起始位置，同步复制到从数据库，使得主从数据库保持一致。

**作用**

- 高可用性：主数据异常可切换到从数据库
- 负载均衡：实现读写分离。
- 备份：进行日常备份。

![image-20231219091913181](.\images\image-20231219091913181.png)



Binary Log: 主数据库的二进制日志；Relay log: 从服务器的中继日志：

过程：

1. 主数据库在每次事务完成前，将操作记录到Binlog日志文件中；
2. 从数据库中有一个I/O线程，负责连接主数据库服务，并读取binlog日志变化，如果发现有新的变动，则将变动写入到relay log,否则进入休眠状态；
3. 从数据中的SQL Thread读取中继日志，将串行执行SQL事件，使用从数据库与主数据库始终保持一致。

注意事项：

1. 涉及时间函数时，会出现数据不一致。原因是，复制过程的两次IO操作和网络、磁盘效率等问题势必导致时间戳不一致。
2. pd涉及系统函数时，会出现不一致。

**节点信息**

| 主机名          | IP           |
| --------------- | ------------ |
| mysql-master-20 | 192.168.5.20 |
| mysql-slave-21  | 192.168.5.21 |

基础镜像

```sh
docker pull mysql:5.7.31
```

#### **master节点操作**

mysql-master-20

my.cnf

```
mkdir -p /data/mysql/master

cd /data/mysql/master

vi my.cnf
[mysqld]
# [必须]启用二进制日志
log-bin=mysql-bin
# 必须 服务器唯一的ID，默认是20，一般取IP段最后一段，
server-id=20

```

Dockerfile

```sh
FROM mysql:5.7.31
# 作者信息
MAINTAINER mysql from date UTC by Asia/Shanghai "nullnull"
ENV TZ Asia/Shanghai
COPY my.cnf /etc/mysql/

```

制作镜像

Dockerfile和my.cnf需要在同一个目录下

```
docker build --rm -t nullnull/mysqlmaster:5.7.31 .
```

操作日志:

```sh
[root@dockeros master]# docker build --rm -t nullnull/mysqlmaster:5.7.31 .
[+] Building 0.3s (7/7) FINISHED                                                                        docker:default
 => [internal] load build definition from Dockerfile                                                              0.2s
 => => transferring dockerfile: 233B                                                                              0.0s
 => [internal] load .dockerignore                                                                                 0.2s
 => => transferring context: 2B                                                                                   0.0s
 => [internal] load metadata for docker.io/library/mysql:5.7.31                                                   0.0s
 => [internal] load build context                                                                                 0.0s
 => => transferring context: 241B                                                                                 0.0s
 => CACHED [1/2] FROM docker.io/library/mysql:5.7.31                                                              0.0s
 => [2/2] COPY my.cnf /etc/mysql/                                                                                 0.0s
 => exporting to image                                                                                            0.0s
 => => exporting layers                                                                                           0.0s
 => => writing image sha256:3ed9e9829207da951a805062df79f8c1a40bcc27270f0d17d26b4cc390968834                      0.0s
 => => naming to docker.io/nullnull/mysqlmaster:5.7.31                                                            0.0s
[root@dockeros master]# 
```

运行镜像

```sh
# 运行镜像
docker run -itd --name mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin nullnull/mysqlmaster:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci

# 查看日志
docker logs -f mysql

# 进入mysql容器
docker exec -it mysql bash

# 查看时间
date

# 查看linux版本
cat /etc/issue

# 登录mysql
mysql -uroot -padmin

show databases;

# 退出mysql
exit

# 退出容器
exit

```

日志

```sh
[root@dockeros master]# docker run -itd --name mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin nullnull/mysqlmaster:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci
bfb7584c95c4ef616ed390d2161c1c5c06929dd6912288b07f6979398add190c
[root@dockeros master]# docker logs mysql
2023-12-19 12:36:00+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-19 12:36:00+08:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2023-12-19 12:36:00+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-19 12:36:00+08:00 [Note] [Entrypoint]: Initializing database files
2023-12-19T04:36:00.148124Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:36:00.261174Z 0 [Warning] InnoDB: New log files created, LSN=45790
2023-12-19T04:36:00.289403Z 0 [Warning] InnoDB: Creating foreign key constraint system tables.
2023-12-19T04:36:00.359635Z 0 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: 1c2c181b-9e28-11ee-806c-0242ac110002.
2023-12-19T04:36:00.361569Z 0 [Warning] Gtid table is not ready to be used. Table 'mysql.gtid_executed' cannot be opened.
2023-12-19T04:36:00.799955Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:36:00.882868Z 1 [Warning] root@localhost is created with an empty password ! Please consider switching off the --initialize-insecure option.
2023-12-19 12:36:05+08:00 [Note] [Entrypoint]: Database files initialized
2023-12-19 12:36:05+08:00 [Note] [Entrypoint]: Starting temporary server
2023-12-19 12:36:05+08:00 [Note] [Entrypoint]: Waiting for server startup
2023-12-19T04:36:05.249177Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:36:05.254723Z 0 [Note] mysqld (mysqld 5.7.31-log) starting as process 78 ...
2023-12-19T04:36:05.260421Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-19T04:36:05.260541Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-19T04:36:05.260545Z 0 [Note] InnoDB: Uses event mutexes
2023-12-19T04:36:05.260547Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-19T04:36:05.260548Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-19T04:36:05.260550Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-19T04:36:05.261215Z 0 [Note] InnoDB: Number of pools: 1
2023-12-19T04:36:05.261490Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-19T04:36:05.265948Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-19T04:36:05.286760Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-19T04:36:05.293418Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-19T04:36:05.313633Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-19T04:36:05.324557Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-19T04:36:05.324602Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-19T04:36:05.355855Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-19T04:36:05.356150Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-19T04:36:05.356157Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-19T04:36:05.359585Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 2719885
2023-12-19T04:36:05.359763Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-19T04:36:05.364281Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-19T04:36:05.368088Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231219 12:36:05
2023-12-19T04:36:05.383444Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-19T04:36:05.383502Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-19T04:36:05.383876Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:36:05.383921Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-19T04:36:05.386193Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/lib/mysql' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-19T04:36:05.388313Z 0 [Note] Failed to start slave threads for channel ''
2023-12-19T04:36:05.392617Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-19T04:36:05.392908Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31-log'  socket: '/var/run/mysqld/mysqld.sock'  port: 0  MySQL Community Server (GPL)
2023-12-19 12:36:06+08:00 [Note] [Entrypoint]: Temporary server started.
Warning: Unable to load '/usr/share/zoneinfo/iso3166.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/leap-seconds.list' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone1970.tab' as time zone. Skipping it.

2023-12-19 12:36:08+08:00 [Note] [Entrypoint]: Stopping temporary server
2023-12-19T04:36:08.402563Z 0 [Note] Giving 0 client threads a chance to die gracefully
2023-12-19T04:36:08.402653Z 0 [Note] Shutting down slave threads
2023-12-19T04:36:08.402657Z 0 [Note] Forcefully disconnecting 0 remaining clients
2023-12-19T04:36:08.402661Z 0 [Note] Event Scheduler: Purging the queue. 0 events
2023-12-19T04:36:08.402736Z 0 [Note] Binlog end
2023-12-19T04:36:08.405075Z 0 [Note] Shutting down plugin 'ngram'
2023-12-19T04:36:08.405114Z 0 [Note] Shutting down plugin 'partition'
2023-12-19T04:36:08.405117Z 0 [Note] Shutting down plugin 'BLACKHOLE'
2023-12-19T04:36:08.405119Z 0 [Note] Shutting down plugin 'ARCHIVE'
2023-12-19T04:36:08.405120Z 0 [Note] Shutting down plugin 'PERFORMANCE_SCHEMA'
2023-12-19T04:36:08.405145Z 0 [Note] Shutting down plugin 'MRG_MYISAM'
2023-12-19T04:36:08.405147Z 0 [Note] Shutting down plugin 'MyISAM'
2023-12-19T04:36:08.405151Z 0 [Note] Shutting down plugin 'INNODB_SYS_VIRTUAL'
2023-12-19T04:36:08.405153Z 0 [Note] Shutting down plugin 'INNODB_SYS_DATAFILES'
2023-12-19T04:36:08.405154Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESPACES'
2023-12-19T04:36:08.405155Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN_COLS'
2023-12-19T04:36:08.405156Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN'
2023-12-19T04:36:08.405157Z 0 [Note] Shutting down plugin 'INNODB_SYS_FIELDS'
2023-12-19T04:36:08.405158Z 0 [Note] Shutting down plugin 'INNODB_SYS_COLUMNS'
2023-12-19T04:36:08.405159Z 0 [Note] Shutting down plugin 'INNODB_SYS_INDEXES'
2023-12-19T04:36:08.405160Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESTATS'
2023-12-19T04:36:08.405161Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLES'
2023-12-19T04:36:08.405162Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_TABLE'
2023-12-19T04:36:08.405163Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_CACHE'
2023-12-19T04:36:08.405164Z 0 [Note] Shutting down plugin 'INNODB_FT_CONFIG'
2023-12-19T04:36:08.405166Z 0 [Note] Shutting down plugin 'INNODB_FT_BEING_DELETED'
2023-12-19T04:36:08.405167Z 0 [Note] Shutting down plugin 'INNODB_FT_DELETED'
2023-12-19T04:36:08.405168Z 0 [Note] Shutting down plugin 'INNODB_FT_DEFAULT_STOPWORD'
2023-12-19T04:36:08.405169Z 0 [Note] Shutting down plugin 'INNODB_METRICS'
2023-12-19T04:36:08.405170Z 0 [Note] Shutting down plugin 'INNODB_TEMP_TABLE_INFO'
2023-12-19T04:36:08.405172Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_POOL_STATS'
2023-12-19T04:36:08.405174Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE_LRU'
2023-12-19T04:36:08.405175Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE'
2023-12-19T04:36:08.405176Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX_RESET'
2023-12-19T04:36:08.405177Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX'
2023-12-19T04:36:08.405178Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM_RESET'
2023-12-19T04:36:08.405179Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM'
2023-12-19T04:36:08.405180Z 0 [Note] Shutting down plugin 'INNODB_CMP_RESET'
2023-12-19T04:36:08.405181Z 0 [Note] Shutting down plugin 'INNODB_CMP'
2023-12-19T04:36:08.405182Z 0 [Note] Shutting down plugin 'INNODB_LOCK_WAITS'
2023-12-19T04:36:08.405183Z 0 [Note] Shutting down plugin 'INNODB_LOCKS'
2023-12-19T04:36:08.405184Z 0 [Note] Shutting down plugin 'INNODB_TRX'
2023-12-19T04:36:08.405185Z 0 [Note] Shutting down plugin 'InnoDB'
2023-12-19T04:36:08.405864Z 0 [Note] InnoDB: FTS optimize thread exiting.
2023-12-19T04:36:08.406152Z 0 [Note] InnoDB: Starting shutdown...
2023-12-19T04:36:08.508371Z 0 [Note] InnoDB: Dumping buffer pool(s) to /var/lib/mysql/ib_buffer_pool
2023-12-19T04:36:08.509100Z 0 [Note] InnoDB: Buffer pool(s) dump completed at 231219 12:36:08
2023-12-19T04:36:10.256486Z 0 [Note] InnoDB: Shutdown completed; log sequence number 12578428
2023-12-19T04:36:10.257054Z 0 [Note] InnoDB: Removed temporary tablespace data file: "ibtmp1"
2023-12-19T04:36:10.257095Z 0 [Note] Shutting down plugin 'MEMORY'
2023-12-19T04:36:10.257100Z 0 [Note] Shutting down plugin 'CSV'
2023-12-19T04:36:10.257103Z 0 [Note] Shutting down plugin 'sha256_password'
2023-12-19T04:36:10.257104Z 0 [Note] Shutting down plugin 'mysql_native_password'
2023-12-19T04:36:10.257196Z 0 [Note] Shutting down plugin 'binlog'
2023-12-19T04:36:10.257499Z 0 [Note] mysqld: Shutdown complete

2023-12-19 12:36:10+08:00 [Note] [Entrypoint]: Temporary server stopped

2023-12-19 12:36:10+08:00 [Note] [Entrypoint]: MySQL init process done. Ready for start up.

2023-12-19T04:36:15.941781Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:36:15.942520Z 0 [Note] mysqld (mysqld 5.7.31-log) starting as process 1 ...
2023-12-19T04:36:15.944912Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-19T04:36:15.944965Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-19T04:36:15.944968Z 0 [Note] InnoDB: Uses event mutexes
2023-12-19T04:36:15.944970Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-19T04:36:15.944971Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-19T04:36:15.944973Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-19T04:36:15.945079Z 0 [Note] InnoDB: Number of pools: 1
2023-12-19T04:36:15.945146Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-19T04:36:15.946194Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-19T04:36:15.949956Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-19T04:36:15.951318Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-19T04:36:15.962653Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-19T04:36:15.967417Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-19T04:36:15.967454Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-19T04:36:15.984043Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-19T04:36:15.984518Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-19T04:36:15.984527Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-19T04:36:15.985315Z 0 [Note] InnoDB: Waiting for purge to start
2023-12-19T04:36:16.036492Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 12578428
2023-12-19T04:36:16.036830Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-19T04:36:16.036891Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-19T04:36:16.038568Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231219 12:36:16
2023-12-19T04:36:16.052032Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-19T04:36:16.052081Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-19T04:36:16.052603Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:36:16.052713Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-19T04:36:16.053016Z 0 [Note] Server hostname (bind-address): '*'; port: 3306
2023-12-19T04:36:16.053041Z 0 [Note] IPv6 is available.
2023-12-19T04:36:16.053046Z 0 [Note]   - '::' resolves to '::';
2023-12-19T04:36:16.053054Z 0 [Note] Server socket created on IP: '::'.
2023-12-19T04:36:16.055277Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/lib/mysql' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-19T04:36:16.057540Z 0 [Note] Failed to start slave threads for channel ''
2023-12-19T04:36:16.060538Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-19T04:36:16.061118Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31-log'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
[root@dockeros master]# docker exec -it mysql bash
root@bfb7584c95c4:/# date
Tue Dec 19 12:37:40 CST 2023
root@bfb7584c95c4:/# cat /etc/issue
Debian GNU/Linux 10 \n \l

root@bfb7584c95c4:/# mysql -uroot -padmin
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 4
Server version: 5.7.31-log MySQL Community Server (GPL)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show database;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'database' at line 1
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
root@bfb7584c95c4:/# exit
exit
[root@dockeros master]# 
```

#### **slave节点**

操作

```sh
mkdir -p /data/mysql/slave

cd /data/mysql/slave

vi my.cnf
[mysqld]
#[必须]启用二进制日志
log-bin=mysql-bin
#[必须]服务器唯一id，默认是21，取IP后最后一段
server-id=21
```

Dockerfile

```sh
FROM mysql:5.7.31
MAINTAINER mysql from date UTC by Asia/Shanghai "nullnull"
ENV TZ Asia/Shanghai
COPY my.cnf /etc/mysql/
```

制作镜像

```
docker build --rm -t nullnull/mysqlslave:5.7.31 .
```

日志：

```sh
[root@dockeros21 ~]# mkdir -p /data/mysql/slave
[root@dockeros21 ~]# 
[root@dockeros21 ~]# cd /data/mysql/slave
[root@dockeros21 slave]# ls
[root@dockeros21 slave]# vi my.cnf
[root@dockeros21 slave]# ls
Dockerfile  my.cnf
[root@dockeros21 slave]# 
[root@dockeros21 slave]# docker build --rm -t nullnull/mysqlslave:5.7.31 .
[+] Building 43.2s (7/7) FINISHED                                                                       docker:default
 => [internal] load .dockerignore                                                                                 0.0s
 => => transferring context: 2B                                                                                   0.0s
 => [internal] load build definition from Dockerfile                                                              0.0s
 => => transferring dockerfile: 215B                                                                              0.0s
 => [internal] load metadata for docker.io/library/mysql:5.7.31                                                  16.7s
 => [internal] load build context                                                                                 0.0s
 => => transferring context: 227B                                                                                 0.0s
 => [1/2] FROM docker.io/library/mysql:5.7.31@sha256:b3dc8d10307ab7b9ca1a7981b1601a67e176408be618fc4216d137be37  24.1s
 => => resolve docker.io/library/mysql:5.7.31@sha256:b3dc8d10307ab7b9ca1a7981b1601a67e176408be618fc4216d137be37d  0.0s
 => => sha256:3830eda172a0285aa9899c422f26d739cde0ad5445962fbb9a2a8b0df00a1a64 2.62kB / 2.62kB                    0.0s
 => => sha256:42cdba9f1b0840cd63254898edeaf6def81a503a6a53d57301c3b38e69cd8f15 7.10kB / 7.10kB                    0.0s
 => => sha256:b3dc8d10307ab7b9ca1a7981b1601a67e176408be618fc4216d137be37dae10b 320B / 320B                        0.0s
 => => sha256:bb79b6b2107fea8e8a47133a660b78e3a546998fcf0427be39ac9a0af4a97e90 27.09MB / 27.09MB                  4.0s
 => => sha256:49e22f6fb9f7713028f8ed9b0beaa2ebac38d73ff6fd60532031e4a257f314c0 1.74kB / 1.74kB                    1.4s
 => => sha256:842b1255668c99365efe9cf8367baf458ad590033cfbe73c03e67a961d34a288 4.18MB / 4.18MB                    5.3s
 => => sha256:9f48d1f430002d35f5766b2ba9cfbe0c624a705a542622a94e5b86e2132aba7b 1.42MB / 1.42MB                    4.2s
 => => extracting sha256:bb79b6b2107fea8e8a47133a660b78e3a546998fcf0427be39ac9a0af4a97e90                         3.3s
 => => sha256:c693f0615bcee7154070df04d6cce10562437d69ee98d469252e950cd79e0d7f 115B / 115B                        5.5s
 => => sha256:8a621b9dbed2309be1806a0a750a6deef7c2558f25ceee4fcfc06ec421fad097 13.45MB / 13.45MB                  9.2s
 => => sha256:0807d32aef130f157419493647a5b252b6560b5385f14f102f388e576aeb1e98 2.40kB / 2.40kB                    6.0s
 => => sha256:6d2fc69dfa35df3eb1099501d90a60c477cc59bb043350815faa61273f30ebb2 225B / 225B                        6.2s
 => => sha256:56153548dd2c828e151b54d5dee6bc184254767aac1753e4f176a72ef098fccc 108.33MB / 108.33MB               19.7s
 => => sha256:3bb6ba94030303d9e95c4d436e78295a17912542c3d539116efe436678d90684 5.14kB / 5.14kB                    6.9s
 => => sha256:3e1888da91a7bfdd32ba485e7f82bec46eae19896901cc433c8203d8927087fb 121B / 121B                        7.6s
 => => extracting sha256:49e22f6fb9f7713028f8ed9b0beaa2ebac38d73ff6fd60532031e4a257f314c0                         0.0s
 => => extracting sha256:842b1255668c99365efe9cf8367baf458ad590033cfbe73c03e67a961d34a288                         0.3s
 => => extracting sha256:9f48d1f430002d35f5766b2ba9cfbe0c624a705a542622a94e5b86e2132aba7b                         0.1s
 => => extracting sha256:c693f0615bcee7154070df04d6cce10562437d69ee98d469252e950cd79e0d7f                         0.0s
 => => extracting sha256:8a621b9dbed2309be1806a0a750a6deef7c2558f25ceee4fcfc06ec421fad097                         1.8s
 => => extracting sha256:0807d32aef130f157419493647a5b252b6560b5385f14f102f388e576aeb1e98                         0.0s
 => => extracting sha256:6d2fc69dfa35df3eb1099501d90a60c477cc59bb043350815faa61273f30ebb2                         0.0s
 => => extracting sha256:56153548dd2c828e151b54d5dee6bc184254767aac1753e4f176a72ef098fccc                         4.1s
 => => extracting sha256:3bb6ba94030303d9e95c4d436e78295a17912542c3d539116efe436678d90684                         0.0s
 => => extracting sha256:3e1888da91a7bfdd32ba485e7f82bec46eae19896901cc433c8203d8927087fb                         0.0s
 => [2/2] COPY my.cnf /etc/mysql/                                                                                 0.8s
 => exporting to image                                                                                            0.3s
 => => exporting layers                                                                                           0.3s
 => => writing image sha256:04b63f6a1881256d0754ff2df05ee83d8acc147b3a0669981fee42e3a092fdbc                      0.0s
 => => naming to docker.io/nullnull/mysqlslave:5.7.31                                                             0.0s
[root@dockeros21 slave]# 
```

运行

```sh
docker run -itd --name=mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin nullnull/mysqlslave:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci


#查看日志
docker logs -f mysql

# 进入mysql容器
docker exec -it mysql bash


# 查看容器版本 
cat /etc/issue

# 登录mysql查看数据库
mysql -uroot -padmin

show databases;

exit


exit
```

日志

```sh
[root@dockeros21 slave]# docker run -itd --name=mysql --restart always --privileged=true -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin nullnull/mysqlslave:5.7.31 --character-set-server=utf8 --collation-server=utf8_general_ci
c1ed9afcb023d530f8725123353ce1f272046b33035877942af53e54e01b5fca
[root@dockeros21 slave]# docker logs -f mysql
2023-12-19 12:50:28+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-19 12:50:28+08:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2023-12-19 12:50:28+08:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.31-1debian10 started.
2023-12-19 12:50:28+08:00 [Note] [Entrypoint]: Initializing database files
2023-12-19T04:50:28.681876Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:50:28.882352Z 0 [Warning] InnoDB: New log files created, LSN=45790
2023-12-19T04:50:28.934794Z 0 [Warning] InnoDB: Creating foreign key constraint system tables.
2023-12-19T04:50:28.957441Z 0 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: 21e599d3-9e2a-11ee-b94a-0242ac110002.
2023-12-19T04:50:28.959570Z 0 [Warning] Gtid table is not ready to be used. Table 'mysql.gtid_executed' cannot be opened.
2023-12-19T04:50:29.278559Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:50:29.429016Z 1 [Warning] root@localhost is created with an empty password ! Please consider switching off the --initialize-insecure option.
2023-12-19 12:50:31+08:00 [Note] [Entrypoint]: Database files initialized
2023-12-19 12:50:31+08:00 [Note] [Entrypoint]: Starting temporary server
2023-12-19 12:50:31+08:00 [Note] [Entrypoint]: Waiting for server startup
2023-12-19T04:50:32.510496Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:50:32.517829Z 0 [Note] mysqld (mysqld 5.7.31-log) starting as process 78 ...
2023-12-19T04:50:32.526122Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-19T04:50:32.526230Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-19T04:50:32.526233Z 0 [Note] InnoDB: Uses event mutexes
2023-12-19T04:50:32.526234Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-19T04:50:32.526236Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-19T04:50:32.526238Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-19T04:50:32.526921Z 0 [Note] InnoDB: Number of pools: 1
2023-12-19T04:50:32.527272Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-19T04:50:32.532102Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-19T04:50:32.553039Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-19T04:50:32.560495Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-19T04:50:32.581507Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-19T04:50:32.592492Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-19T04:50:32.592544Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-19T04:50:32.622369Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-19T04:50:32.622701Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-19T04:50:32.622707Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-19T04:50:32.626210Z 0 [Note] InnoDB: Waiting for purge to start
2023-12-19T04:50:32.684529Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 2719885
2023-12-19T04:50:32.689557Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-19T04:50:32.689592Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-19T04:50:32.693778Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231219 12:50:32
2023-12-19T04:50:32.715185Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-19T04:50:32.715249Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-19T04:50:32.715775Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:50:32.715844Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-19T04:50:32.718216Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/lib/mysql' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-19T04:50:32.719540Z 0 [Note] Failed to start slave threads for channel ''
2023-12-19T04:50:32.722508Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-19T04:50:32.722961Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31-log'  socket: '/var/run/mysqld/mysqld.sock'  port: 0  MySQL Community Server (GPL)
2023-12-19 12:50:32+08:00 [Note] [Entrypoint]: Temporary server started.
Warning: Unable to load '/usr/share/zoneinfo/iso3166.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/leap-seconds.list' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone1970.tab' as time zone. Skipping it.

2023-12-19 12:50:35+08:00 [Note] [Entrypoint]: Stopping temporary server
2023-12-19T04:50:35.221727Z 0 [Note] Giving 0 client threads a chance to die gracefully
2023-12-19T04:50:35.221791Z 0 [Note] Shutting down slave threads
2023-12-19T04:50:35.221796Z 0 [Note] Forcefully disconnecting 0 remaining clients
2023-12-19T04:50:35.221801Z 0 [Note] Event Scheduler: Purging the queue. 0 events
2023-12-19T04:50:35.221846Z 0 [Note] Binlog end
2023-12-19T04:50:35.223932Z 0 [Note] Shutting down plugin 'ngram'
2023-12-19T04:50:35.223978Z 0 [Note] Shutting down plugin 'partition'
2023-12-19T04:50:35.223982Z 0 [Note] Shutting down plugin 'BLACKHOLE'
2023-12-19T04:50:35.223984Z 0 [Note] Shutting down plugin 'ARCHIVE'
2023-12-19T04:50:35.223985Z 0 [Note] Shutting down plugin 'PERFORMANCE_SCHEMA'
2023-12-19T04:50:35.224010Z 0 [Note] Shutting down plugin 'MRG_MYISAM'
2023-12-19T04:50:35.224013Z 0 [Note] Shutting down plugin 'MyISAM'
2023-12-19T04:50:35.224016Z 0 [Note] Shutting down plugin 'INNODB_SYS_VIRTUAL'
2023-12-19T04:50:35.224018Z 0 [Note] Shutting down plugin 'INNODB_SYS_DATAFILES'
2023-12-19T04:50:35.224019Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESPACES'
2023-12-19T04:50:35.224020Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN_COLS'
2023-12-19T04:50:35.224021Z 0 [Note] Shutting down plugin 'INNODB_SYS_FOREIGN'
2023-12-19T04:50:35.224022Z 0 [Note] Shutting down plugin 'INNODB_SYS_FIELDS'
2023-12-19T04:50:35.224023Z 0 [Note] Shutting down plugin 'INNODB_SYS_COLUMNS'
2023-12-19T04:50:35.224024Z 0 [Note] Shutting down plugin 'INNODB_SYS_INDEXES'
2023-12-19T04:50:35.224025Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLESTATS'
2023-12-19T04:50:35.224026Z 0 [Note] Shutting down plugin 'INNODB_SYS_TABLES'
2023-12-19T04:50:35.224027Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_TABLE'
2023-12-19T04:50:35.224027Z 0 [Note] Shutting down plugin 'INNODB_FT_INDEX_CACHE'
2023-12-19T04:50:35.224028Z 0 [Note] Shutting down plugin 'INNODB_FT_CONFIG'
2023-12-19T04:50:35.224030Z 0 [Note] Shutting down plugin 'INNODB_FT_BEING_DELETED'
2023-12-19T04:50:35.224030Z 0 [Note] Shutting down plugin 'INNODB_FT_DELETED'
2023-12-19T04:50:35.224031Z 0 [Note] Shutting down plugin 'INNODB_FT_DEFAULT_STOPWORD'
2023-12-19T04:50:35.224032Z 0 [Note] Shutting down plugin 'INNODB_METRICS'
2023-12-19T04:50:35.224033Z 0 [Note] Shutting down plugin 'INNODB_TEMP_TABLE_INFO'
2023-12-19T04:50:35.224035Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_POOL_STATS'
2023-12-19T04:50:35.224036Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE_LRU'
2023-12-19T04:50:35.224037Z 0 [Note] Shutting down plugin 'INNODB_BUFFER_PAGE'
2023-12-19T04:50:35.224038Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX_RESET'
2023-12-19T04:50:35.224038Z 0 [Note] Shutting down plugin 'INNODB_CMP_PER_INDEX'
2023-12-19T04:50:35.224039Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM_RESET'
2023-12-19T04:50:35.224040Z 0 [Note] Shutting down plugin 'INNODB_CMPMEM'
2023-12-19T04:50:35.224042Z 0 [Note] Shutting down plugin 'INNODB_CMP_RESET'
2023-12-19T04:50:35.224042Z 0 [Note] Shutting down plugin 'INNODB_CMP'
2023-12-19T04:50:35.224043Z 0 [Note] Shutting down plugin 'INNODB_LOCK_WAITS'
2023-12-19T04:50:35.224044Z 0 [Note] Shutting down plugin 'INNODB_LOCKS'
2023-12-19T04:50:35.224045Z 0 [Note] Shutting down plugin 'INNODB_TRX'
2023-12-19T04:50:35.224046Z 0 [Note] Shutting down plugin 'InnoDB'
2023-12-19T04:50:35.224104Z 0 [Note] InnoDB: FTS optimize thread exiting.
2023-12-19T04:50:35.224526Z 0 [Note] InnoDB: Starting shutdown...
2023-12-19T04:50:35.325168Z 0 [Note] InnoDB: Dumping buffer pool(s) to /var/lib/mysql/ib_buffer_pool
2023-12-19T04:50:35.325396Z 0 [Note] InnoDB: Buffer pool(s) dump completed at 231219 12:50:35
2023-12-19T04:50:36.553447Z 0 [Note] InnoDB: Shutdown completed; log sequence number 12578262
2023-12-19T04:50:36.553986Z 0 [Note] InnoDB: Removed temporary tablespace data file: "ibtmp1"
2023-12-19T04:50:36.553998Z 0 [Note] Shutting down plugin 'MEMORY'
2023-12-19T04:50:36.554002Z 0 [Note] Shutting down plugin 'CSV'
2023-12-19T04:50:36.554005Z 0 [Note] Shutting down plugin 'sha256_password'
2023-12-19T04:50:36.554006Z 0 [Note] Shutting down plugin 'mysql_native_password'
2023-12-19T04:50:36.554104Z 0 [Note] Shutting down plugin 'binlog'
2023-12-19T04:50:36.554383Z 0 [Note] mysqld: Shutdown complete

2023-12-19 12:50:37+08:00 [Note] [Entrypoint]: Temporary server stopped

2023-12-19 12:50:37+08:00 [Note] [Entrypoint]: MySQL init process done. Ready for start up.

2023-12-19T04:50:37.370298Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-19T04:50:37.370844Z 0 [Note] mysqld (mysqld 5.7.31-log) starting as process 1 ...
2023-12-19T04:50:37.374560Z 0 [Note] InnoDB: PUNCH HOLE support available
2023-12-19T04:50:37.374835Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2023-12-19T04:50:37.374840Z 0 [Note] InnoDB: Uses event mutexes
2023-12-19T04:50:37.374841Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2023-12-19T04:50:37.374843Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2023-12-19T04:50:37.374844Z 0 [Note] InnoDB: Using Linux native AIO
2023-12-19T04:50:37.374946Z 0 [Note] InnoDB: Number of pools: 1
2023-12-19T04:50:37.375176Z 0 [Note] InnoDB: Using CPU crc32 instructions
2023-12-19T04:50:37.376229Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2023-12-19T04:50:37.380158Z 0 [Note] InnoDB: Completed initialization of buffer pool
2023-12-19T04:50:37.382148Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2023-12-19T04:50:37.393669Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2023-12-19T04:50:37.398911Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2023-12-19T04:50:37.398954Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2023-12-19T04:50:37.409354Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2023-12-19T04:50:37.409764Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2023-12-19T04:50:37.409769Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2023-12-19T04:50:37.410211Z 0 [Note] InnoDB: Waiting for purge to start
2023-12-19T04:50:37.463924Z 0 [Note] InnoDB: 5.7.31 started; log sequence number 12578262
2023-12-19T04:50:37.464345Z 0 [Note] Plugin 'FEDERATED' is disabled.
2023-12-19T04:50:37.464656Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2023-12-19T04:50:37.465994Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231219 12:50:37
2023-12-19T04:50:37.478824Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2023-12-19T04:50:37.478879Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2023-12-19T04:50:37.479397Z 0 [Warning] CA certificate ca.pem is self signed.
2023-12-19T04:50:37.479503Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2023-12-19T04:50:37.479726Z 0 [Note] Server hostname (bind-address): '*'; port: 3306
2023-12-19T04:50:37.480115Z 0 [Note] IPv6 is available.
2023-12-19T04:50:37.480228Z 0 [Note]   - '::' resolves to '::';
2023-12-19T04:50:37.480240Z 0 [Note] Server socket created on IP: '::'.
2023-12-19T04:50:37.482570Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/lib/mysql' in the path is accessible to all OS users. Consider choosing a different directory.
2023-12-19T04:50:37.484550Z 0 [Note] Failed to start slave threads for channel ''
2023-12-19T04:50:37.486715Z 0 [Note] Event Scheduler: Loaded 0 events
2023-12-19T04:50:37.486904Z 0 [Note] mysqld: ready for connections.
Version: '5.7.31-log'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
^C
[root@dockeros21 slave]# docker exec -it mysql bash
root@c1ed9afcb023:/# date
Tue Dec 19 12:50:58 CST 2023
root@c1ed9afcb023:/# cat /etc/issue
Debian GNU/Linux 10 \n \l

root@c1ed9afcb023:/# mysql -uroot -padmin
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.31-log MySQL Community Server (GPL)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

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
root@c1ed9afcb023:/# exit
exit
[root@dockeros21 slave]#
```

#### 主从节点配制

**主节点配制**

```sql
#创建同步账户以及授权
create user 'nullnull'@'%' identified by 'nullnull';
grant replication slave on *.* to 'nullnull'@'%';
flush privileges;

#查看master状态
show master status;


#查看二进制日志相关的配置项
show global variables like 'binlog%';


#查看server相关的配置项
show global variables like 'server%';
```

从节点配制

```sql
#设置master相关信息
CHANGE MASTER TO
master_host='192.168.5.20',
master_user='nullnull',
master_password='nullnull',
master_port=3306,
master_log_file='mysql-bin.000006',
master_log_pos=812;


#启动同步
start slave;

#查看master状态
show slave status;
```

主从同步状态信息

show slave status的结果信息：

重点查看：**Slave_IO_Running和Slave_SQL_Running**

| Name                         |Value                                                 |
| -----------------------------|------------------------------------------------------|
| Slave_IO_State               |Waiting for master to send event                      |
| Master_Host                  |192.168.5.20                                          |
| Master_User                  |nullnull                                              |
| Master_Port                  |3306                                                  |
| Connect_Retry                |60                                                    |
| Master_Log_File              |mysql-bin.000006                                      |
| Read_Master_Log_Pos          |2905                                                  |
| Relay_Log_File               |c1ed9afcb023-relay-bin.000002                         |
| Relay_Log_Pos                |2413                                                  |
| Relay_Master_Log_File        |mysql-bin.000006                                      |
| Slave_IO_Running             |Yes                                                   |
| Slave_SQL_Running            |Yes                                                   |
| Replicate_Do_DB              |                                                      |
| Replicate_Ignore_DB          |                                                      |
| Replicate_Do_Table           |                                                      |
| Replicate_Ignore_Table       |                                                      |
| Replicate_Wild_Do_Table      |                                                      |
| Replicate_Wild_Ignore_Table  |                                                      |
| Last_Errno                   |0                                                     |
| Last_Error                   |                                                      |
| Skip_Counter                 |0                                                     |
| Exec_Master_Log_Pos          |2905                                                  |
| Relay_Log_Space              |2627                                                  |
| Until_Condition              |None                                                  |
| Until_Log_File               |                                                      |
| Until_Log_Pos                |0                                                     |
| Master_SSL_Allowed           |No                                                    |
| Master_SSL_CA_File           |                                                      |
| Master_SSL_CA_Path           |                                                      |
| Master_SSL_Cert              |                                                      |
| Master_SSL_Cipher            |                                                      |
| Master_SSL_Key               |                                                      |
| Seconds_Behind_Master        |0                                                     |
| Master_SSL_Verify_Server_Cert|No                                                    |
| Last_IO_Errno                |0                                                     |
| Last_IO_Error                |                                                      |
| Last_SQL_Errno               |0                                                     |
| Last_SQL_Error               |                                                      |
| Replicate_Ignore_Server_Ids  |                                                      |
| Master_Server_Id             |20                                                    |
| Master_UUID                  |1c2c181b-9e28-11ee-806c-0242ac110002                  |
| Master_Info_File             |/var/lib/mysql/master.info                            |
| SQL_Delay                    |0                                                     |
| SQL_Remaining_Delay          |                                                      |
| Slave_SQL_Running_State      |Slave has read all relay log; waiting for more updates|
| Master_Retry_Count           |86400                                                 |
| Master_Bind                  |                                                      |
| Last_IO_Error_Timestamp      |                                                      |
| Last_SQL_Error_Timestamp     |                                                      |
| Master_SSL_Crl               |                                                      |
| Master_SSL_Crlpath           |                                                      |
| Retrieved_Gtid_Set           |                                                      |
| Executed_Gtid_Set            |                                                      |
| Auto_Position                |0                                                     |
| Replicate_Rewrite_DB         |                                                      |
| Channel_Name                 |                                                      |
| Master_TLS_Version           |                                                      |




主从测试

在主库上执行建库、建表、插入数据检查从库是否能够成功同步

```sql
CREATE DATABASE nullnull-clu CHARACTER SET utf8 COLLATE utf8_bin;

use nullnull-clu;

CREATE TABLE `user_data_info` (
  `id` int(11) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO user_data_info (id,userName) 
VALUES (1,'nullnull');
```

连接从库，检查是否成功执行

![image-20231220093351264](.\images\image-20231220093351264.png)

数据已经成功同步。至此，主从同步的集群安装完成。



### 使用第三方的MySQL镜像

镜像名称：bitnami/mysql

官网地址：

```http
https://hub.docker.com/r/bitnami/mysql
```

注意：

在使用官网的镜像的MySQL的主从复制中，已经使用3306端口，此第三镜像，需我更换端口以及目录。注意目录的授权。

**节点信息**

| 主机名          | IP           |
| --------------- | ------------ |
| mysql-master-20 | 192.168.5.20 |
| mysql-slave-21  | 192.168.5.21 |

操作

```sh
docker pull bitnami/mysql:5.7.30


# 
mkdir -p /data/bitnamimysql/docker-entrypoint-initdb.d
chmod 777 /data/bitnamimysql/docker-entrypoint-initdb.d
#SQL初始化文件上传至/data/bitnamimysql/docker-entrypoint-initdb.d目录下


# master节点启动
docker run -itd --name mysql-master \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=admin \
  -e MYSQL_REPLICATION_MODE=master \
  -e MYSQL_REPLICATION_USER=nullnull \
  -e MYSQL_REPLICATION_PASSWORD=nullnull \
 bitnami/mysql:5.7.30
 
#运行master容器并导入数据库。  
docker run -itd --name mysql-master \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=admin \
  -e MYSQL_REPLICATION_MODE=master \
  -e MYSQL_REPLICATION_USER=nullnull \
  -e MYSQL_REPLICATION_PASSWORD=nullnull \
  -v /data/bitnamimysql/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d \
 bitnami/mysql:5.7.30 
 
 
 # 从节点启动
 docker run -itd --name mysql-slave \
  -p 3307:3306 \
  -e MYSQL_REPLICATION_MODE=slave \
  -e MYSQL_REPLICATION_USER=nullnull \
  -e MYSQL_REPLICATION_PASSWORD=nullnull \
  -e MYSQL_MASTER_HOST=192.168.5.20 \
  -e MYSQL_MASTER_ROOT_PASSWORD=admin \
  -e MYSQL_MASTER_PORT_NUMBER=3307 \
 bitnami/mysql:5.7.30
```



通过客户端检查从机数据

![image-20231220124530136](.\images\image-20231220124530136.png)

## mysql 主主复制

pxc模式，没有主从之分，每个数据库都可以进行写入，数据可以保持强一致性，执行时间较慢，由于一般是强一致性，所以一般 用于存储重要的信息，例如金融、电信、军工。

简介：

PXC属于一套近乎完美的mysql高可用集群解决方案，相比那些比较传统的基于主从复制模式的集群架构，最突出的特点就是解决了诟病已久的数据复制延迟问题，基本上可以达到实时同步。而且节点与节点之间，他们相互的关系是对等的。本身galera cluster也是一种多主架构。galera cluster最关注的是数据的一致性，对待事物的行为时，要么在所有节点上执行，要不都不执行，它的实现机制决定了它对待一致性的行为非常严格，它也能非常美完的保证MySQL集群的数据一致性；

要搭建PXC架构至少需要3个mysql实例来组成一个集群，三个实例之间不是主从模式，而是各自为主，所以三者对等关系，不分从属，这就叫multi-master架构。客户端写入和读取数据时，连接哪个实例都是一样的。读取到的数据时相同的，写入任意一个实例之后，集群自己会将新的写入数据同步到其他实例上，这种架构不共享任何数据，是一种高冗余架构。

PXC操作流程：

1. 客户端发起一个事务，该事务先在本地执行，执行完成之后就要发起对事务的提交操作了，在提交之前需要产生一个写集进行，并广播出去，然后获得一个事务的ID，一并传递到另外的节点上面。
2. 其他节点在接收到数据之后，进行合并数据之后，没有冲突数据，执行apply_cd和commit_cb动作，否则就需要取消此次事务操作。而当前server节点通过验证之后，执行提交操作，并返回ok，如果验证没有通过，则执行回滚。
3. 在生产环境中至少有3个节点的集群环境，如果其中一个节点没有验证通过，出现了数据冲突，那么此时采取的方多就是将出现不一致的节点踢出集群环境，而且它自己会执行shutdown命令，自动亲机。

PXC的优化：

1. 实现了mysql数据库集群架构的高可用性和强一致性。
2. 完成了真正的多节点读写集群方案。
3. 改善了传统意义上的主从复制延迟问题，基本到达了实时同步。
4. 新加入的节点可以自动部署，无须提供手动备份，维护起来很方便。
5. 由于是多节点写入，所以数据故障切换很容易。

PXC缺点：

1. 新加入节点开销大，需要复制完整的数据。采用SST传输开销大。
2. 任何更新事务都需要全局验证通过，才会在每个节点上执行。集群性能受限于性能最差的节点。也就是经常说的短板效应。
3. 因为要保证数据的一致性，所以在多节点并发写时，锁冲突问题比较严重。
4. 只支持innodb存储引擎的表。
5. 没有表级别的锁，执行DDL语句会把整个集群锁住，而且也kill不了（建议使用OSC操作，即在线DDL）
6. 所有的表都必须包含主键，不然操作数据时会报错。

镜像：

```sh
# 官方镜像
docker pull percona/percona-xtradb-cluster:5.7.30
docker pull percona/percona-xtradb-cluster:5.7

#如果觉得pxc镜像自带的PXC名字过长，我们可以将他的名字进行修改，方便使用
docker tag percona/percona-xtradb-cluster:5.7.30 pxc:5.7.30
```

注意：

1. MySQL从主复制中，已经使用3306端口，pxc在单机上的安装使用3301、3302、3302端口.
2. 使用docker-compose
3. 在master节点完成配制，如果需要部署多台服务器，推荐使用docker-swarm集群方式。

### docker命令行运行

先使用docker命令完成，然后按此逻辑改成docker-compose方式

操作过程：

每个PXC内部包含一个mysql实例，如果需要创建包含3个数据库节点的集群，那么要创建3个pxc节点，出于安全考虑，需要给PXC集群实例创建一个Docker内部网络。

```sh
# 1. 拉取镜像
docker pull percona/percona-xtradb-cluster:5.7.30
# 2. 镜像重命名
docker tag percona/percona-xtradb-cluster:5.7.30 pxc:5.7.30
# 3. 创建单独的网络
docker network create --subnet=172.20.0.0/24 pxc-net
# 4. 准备3个数据卷
docker volume create --name v1
docker volume create --name v2
docker volume create --name v3
# 5. 创建第一个节点
docker run -d -p 3301:3306 -v v1:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull --privileged=true --name=node1 --net=pxc-net --ip 172.20.0.2 pxc:5.7.30
# 6. 等待节点1完全启动后，创建另外两个节点
docker run -d -p 3302:3306 -v v2:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull -e CLUSTER_JOIN=node1 --privileged=true --name=node2 --net=pxc-net --ip 172.20.0.3 pxc:5.7.30

docker run -d -p 3303:3306 -v v3:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull -e CLUSTER_JOIN=node1 --privileged=true --name=node3 --net=pxc-net --ip 172.20.0.4 pxc:5.7.30
# 完成：测试3个节点的自动复制。
```

执行脚本测试：

```sql
-- 创建数据库并
CREATE DATABASE nullnulldb CHARACTER SET utf8 COLLATE utf8_bin;

-- 切换数据库
use nullnulldb;

-- 创建表语句
CREATE TABLE data_user (
 userid int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20) COLLATE utf8_bin DEFAULT NULL,
 password varchar(20) COLLATE utf8_bin DEFAULT NULL,
 userroles varchar(2) COLLATE utf8_bin DEFAULT NULL,
 nickname varchar(50) COLLATE utf8_bin DEFAULT NULL,
 PRIMARY KEY (userid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 插入数据
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES 
('admin','1234','04','超级管理员'),
('nullnull','1234','03','管理员');


-- 在任意节点插入数据，在其他节点查看
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES 
('nullnull2','1234','03','管理员2');
```



操作日志:

```sh
[root@dockeros ~]# docker network create --subnet=172.20.0.0/24 pxc-net
cbd80cff3cfd66429edde80d63c77ccfd9bf0022ca1dce8fddedc06f473bdabe
[root@dockeros ~]# docker volume create --name v1
v1
[root@dockeros ~]# docker volume create --name v2
v2
[root@dockeros ~]# docker volume create --name v3
v3
[root@dockeros ~]# ls
activity-5.14.3.image  data                nginx-1.19.3.image  zookeeper.3.6.2.image
anaconda-ks.cfg        mysql-5.7.31.image  tomcat2
[root@dockeros ~]# docker run -d -p 3301:3306 -v v1:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull --privileged=true --name=node1 --net=pxc-net --ip 172.20.0.2 pxc:5.7.30
70d1f3aa5f7803d5c5cde9e07ec2121893c6457aa3ac7f91d6d416633067fe4d
[root@dockeros ~]# docker logs -f  node1
+ '[' m = - ']'
+ CFG=/etc/mysql/node.cnf
+ wantHelp=
+ for arg in '"$@"'
+ case "$arg" in
+ vault_secret=/etc/mysql/vault-keyring-secret/keyring_vault.conf
+ '[' -f /etc/mysql/vault-keyring-secret/keyring_vault.conf ']'
+ file_env XTRABACKUP_PASSWORD xtrabackup
+ set +o xtrace
+ file_env CLUSTERCHECK_PASSWORD clustercheck
+ set +o xtrace
++ hostname -f
+ NODE_NAME=70d1f3aa5f78
+ NODE_PORT=3306
+ '[' -n '' ']'
+ '[' -n '' ']'
+ : checking incoming cluster parameters
++ hostname -I
++ awk ' { print $1 } '
+ NODE_IP=172.20.0.2
+ sed -r 's|^[#]?wsrep_node_address=.*$|wsrep_node_address=172.20.0.2|' /etc/mysql/node.cnf
+ sed -r 's|^[#]?wsrep_node_incoming_address=.*$|wsrep_node_incoming_address=70d1f3aa5f78:3306|' /etc/mysql/node.cnf
+ sed -r 's|^[#]?wsrep_sst_auth=.*$|wsrep_sst_auth='\''xtrabackup:nullnull'\''|' /etc/mysql/node.cnf
+ [[ -n '' ]]
+ [[ -n PXC ]]
+ sed -r 's|^[#]?wsrep_cluster_name=.*$|wsrep_cluster_name=PXC|' /etc/mysql/node.cnf
+ '[' -z '' ']'
+ '[' mysqld = mysqld -a -z '' ']'
+ _check_config mysqld
+ toRun=("$@" --verbose --help --wsrep-provider='none')
++ mysqld --verbose --help --wsrep-provider=none
+ errors=
++ _get_config datadir mysqld
++ local conf=datadir
++ shift
++ awk '$1 == "datadir" && /^[^ \t]/ { sub(/^[^ \t]+[ \t]+/, ""); print; exit }'
+++ mktemp -u
++ mysqld --verbose --help --wsrep-provider=none --log-bin-index=/tmp/tmp.LkfbwJnEuD
+ DATADIR=/var/lib/mysql/
+ '[' '!' -d /var/lib/mysql//mysql ']'
+ file_env MYSQL_ROOT_PASSWORD
+ set +o xtrace
+ '[' -z admin -a -z '' -a -z '' ']'
+ rm -rf
+ mkdir -p /var/lib/mysql/
+ echo 'Initializing database'
+ mysqld --initialize-insecure --skip-ssl
Initializing database
2023-12-21T00:50:37.759659Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2023-12-21T00:50:37.759712Z 0 [Warning] WSREP: Node is running in bootstrap/initialize mode. Disabling pxc_strict_mode checks
2023-12-21T00:50:38.584453Z 0 [Warning] InnoDB: New log files created, LSN=45790
2023-12-21T00:50:39.292945Z 0 [Warning] InnoDB: Creating foreign key constraint system tables.
2023-12-21T00:50:39.301312Z 0 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: f5d1d353-9f9a-11ee-98f3-0242ac140002.
2023-12-21T00:50:39.303386Z 0 [Warning] Gtid table is not ready to be used. Table 'mysql.gtid_executed' cannot be opened.
2023-12-21T00:50:39.314029Z 1 [Warning] root@localhost is created with an empty password ! Please consider switching off the --
......
2023-12-21T00:51:15.562167Z 0 [Note] WSREP: Service thread queue flushed.
2023-12-21T00:51:15.562315Z 2 [Note] WSREP: GCache history reset: fb898b24-9f9a-11ee-8fc1-bb56e18956e5:0 -> fb898b24-9f9a-11ee-8fc1-bb56e18956e5:18
2023-12-21T00:51:15.565156Z 2 [Note] WSREP: Synchronized with group, ready for connections
2023-12-21T00:51:15.565187Z 2 [Note] WSREP: Setting wsrep_ready to true
2023-12-21T00:51:15.565190Z 2 [Note] WSREP: wsrep_notify_cmd is not defined, skipping notification.
2023-12-21T00:51:15.606417Z 0 [Note] InnoDB: Buffer pool(s) load completed at 231221  0:51:15
2023-12-21T00:54:11.960886Z 0 [Note] InnoDB: page_cleaner: 1000ms intended loop took 5404ms. The settings might not be optimal. (flushed=0, during the time.)
2023-12-21T00:54:11.960941Z 0 [Warning] WSREP: last inactive check more than PT1.5S (3*evs.inactive_check_period) ago (PT4.76493S), skipping check
2023-12-21T00:55:08.729091Z 0 [Note] InnoDB: page_cleaner: 1000ms intended loop took 4733ms. The settings might not be optimal. (flushed=0, during the time.)
2023-12-21T00:55:08.729145Z 0 [Warning] WSREP: last inactive check more than PT1.5S (3*evs.inactive_check_period) ago (PT4.67463S), skipping check
2023-12-21T00:55:10.902366Z 0 [Warning] WSREP: last inactive check more than PT1.5S (3*evs.inactive_check_period) ago (PT2.1732S), skipping check

[root@dockeros ~]# docker run -d -p 3302:3306 -v v2:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull -e CLUSTER_JOIN=node1 --privileged=true --name=node2 --net=pxc-net --ip 172.20.0.3 pxc:5.7.30
6ba25d381d0819bfb6255d02f4969b0ed701317444cf67bf9e53af26debb5095
[root@dockeros ~]# 
[root@dockeros ~]# 
[root@dockeros ~]# 
[root@dockeros ~]# 
[root@dockeros ~]# 
[root@dockeros ~]# docker run -d -p 3303:3306 -v v3:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=nullnull -e CLUSTER_JOIN=node1 --privileged=true --name=node3 --net=pxc-net --ip 172.20.0.4 pxc:5.7.30
3188aa9cc15b5ca0563e6fe7a306d628982817c5e3cbc528ab4e620e4a4b2d0a
```

在客户端工具中执行SQL，检查数据同步：

![image-20231221090501913](.\images\image-20231221090501913.png)

其他节点执行插入

![image-20231221090540973](.\images\image-20231221090540973.png)

数据查看：

![image-20231221090713955](.\images\image-20231221090713955.png)

### **docker-compose方式**

在运行docker-compose方式前，停止之前使用命令启动的pxc

准备

```
# 准备相关的挂载目录
mkdir -p /data/pxc
cd /data/pxc
mkdir -p v1 v2 v3 master agent

# 对/data/pxc/目录及所有子目录授权
chmod 777 -R /data/pxc

# 创建网络,他们使用一个网络，需要提前创建
docker network create pxc_network --driver bridge
docker network ls

```

**master**

docker-compose.yml

```yaml
version: '3'
services:
  pxc01:
    restart: always
    image: pxc:5.7.30
    container_name: pxc01
    privileged: true
    ports:
      - 3301:3306
    environment:
      - MYSQL_ROOT_PASSWORD=admin
      - CLUSTER_NAME=pxc
    volumes:
      - /data/pxc/v1:/var/lib/mysql
networks:
  default:
    external:
      name: pxc_network
```

将docker-compose.yml文件上传/data/pxc/master目录

执行启动命令

```
docker-compose up -d
```



**agent**

docker-compose.yml

```yaml
version: '3'
services:
  pxc02:
    restart: always
    image: pxc:5.7.30
    container_name: pxc02
    privileged: true
    ports:
      - 3302:3306
    environment:
      - MYSQL_ROOT_PASSWORD=admin
      - CLUSTER_NAME=pxc
      - CLUSTER_JOIN=pxc01
    volumes:
      - /data/pxc/v2:/var/lib/mysql

  pxc03:
    restart: always
    image: pxc:5.7.30
    container_name: pxc03
    privileged: true
    ports:
      - 3303:3306
    environment:
      - MYSQL_ROOT_PASSWORD=admin
      - CLUSTER_NAME=pxc
      - CLUSTER_JOIN=pxc01
    volumes:
      - /data/pxc/v3:/var/lib/mysql

networks:
  default:
    external:
      name: pxc_network
```

将docker-compose.yml文件上传/data/pxc/agent目录

执行启动命令

```
docker-compose up -d
```



测试集群，在工具中执行SQL

```sql
show status like 'wsrep_cluster%';
```

输入出信息

| Variable_name            | Value                                |
| ------------------------ | ------------------------------------ |
| wsrep_cluster_weight     | 3                                    |
| wsrep_cluster_conf_id    | 2                                    |
| wsrep_cluster_size       | 3                                    |
| wsrep_cluster_state_uuid | 31bc122e-9f9f-11ee-b5b7-fe11985df30b |
| wsrep_cluster_status     | Primary                              |



执行SQL测试：

```sql
-- 创建数据库并
CREATE DATABASE nullnulldb CHARACTER SET utf8 COLLATE utf8_bin;

-- 切换数据库
use nullnulldb;

-- 创建表语句
CREATE TABLE data_user (
 userid int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20) COLLATE utf8_bin DEFAULT NULL,
 password varchar(20) COLLATE utf8_bin DEFAULT NULL,
 userroles varchar(2) COLLATE utf8_bin DEFAULT NULL,
 nickname varchar(50) COLLATE utf8_bin DEFAULT NULL,
 PRIMARY KEY (userid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 插入数据
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES 
('admin','1234','04','超级管理员'),
('nullnull','1234','03','管理员');


-- 在任意节点插入数据，在其他节点查看
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES 
('nullnull2','1234','03','管理员2');
```

至其他节点检查数据

![image-20231221092727915](.\images\image-20231221092727915.png)



注意事项：

1. 一定要等master节点起来，在进行启动agent之前不能相互注册
2. PXC节点不能太多，不然会把整体的性能下降。
3. PXC节点之间的服务器配制一致。
4. PXC集群只支持innoDB引擎
5. docker-compose网络。

```
1. 新建网络，新建一个名为front的bridge类型网络。但是在实际创建过程中。docker-compose会默认增加docker-compose.yml文件所在目录名称+front的网络。
例如：pxc/docker-compose.yml
实际创建网络名称为：pxc-front.不是很符合开发要求。

networks:
  front:
    driver: bridge
    
2. 使用已存在的网络：
2.1 创建网络
docker network create pxc_network --driver bridge
2.2 使用已存在的网络
networks:
  default:
    external:
      name: pxc_network
```





## docker安装ELK

官网地址 ：

```http
https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
```

基础镜像：

```sh
docker pull elasticsearch:7.7.0
docker pull kibana:7.7.0
docker pull bolingcavalry/elasticsearch-head:6
```

### **安装前的准备工作:**

>修改文件创建数
>
>1. 修改系统中允许应用最多创建多少文件等限制权限。Linux默认来说，一般 最多创建的文件是65536个，但是ES至少需要65536的文件创建数限制。
>1. 修改系统中允许用户启动的进程开启多少个线程。默认的Linux限制root用户开启的进程可以开启任意数量的线程，其他用户开启的线程数是1024个线程，必须修改限制数量为4096+，因为ES至少需要4096的线程池预备。

```sh
#新增如下内容在limits.conf文件中
vi /etc/security/limits.conf

es soft nofile 65536
es hard nofile 65536
es soft nproc 4096
es hard nproc 4096
```

>系统控制权限：
>
>修改系统控制权限，ElasticSearch需要开辟一个65536字节以上空间的虚拟内存。Linux默认不允许任何用户和应用程序直接开辟这么大的虚拟内存。

```sh
# 添加参数:新增如下内容在sysctl.conf文件中，当前用户拥有的内存权限大小
vi /etc/sysctl.conf
vm.max_map_count=262144

#重启生效:让系统控制权限配置生效
sysctl -p
```

### **试运行**

```sh
docker run -itd --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.7.0

#拷贝出elasticsearch.yml
docker cp elasticsearch:/usr/share/elasticsearch/config/elasticsearch.yml /data/elasticsearch/
```

elasticsearch.yml

将内存修改中如下：

```
cluster.name: "docker-cluster"
network.host: 0.0.0.0
http.cors.enabled: true
http.cors.allow-origin: "*"
```

Dockerfile

```sh
FROM elasticsearch:7.7.0
MAINTAINER  elasticsearch-zh form data UTC by Asia/Shanghai "nullnull"
ENV TZ Asia/Shanghai
COPY elasticsearch.yml /usr/share/elasticsearch/config/
```

制作镜像

```
docker build -t nullnull/elasticsearch:7.7.0 .
```

docker-compose

 挂载目录 

```sh
mkdir -p /data/elasticsearch/data
mkdir -p /data/elasticsearch/plugins
chmod 777  /data/elasticsearch/data
chmod 777 /data/elasticsearch/plugins
```

docker-compose.yml

```yaml
version: '3'
services:
  elasticsearch:
    image: nullnull/elasticsearch:7.7.0
    container_name: elasticsearch770
    ports:
      - 9200:9200
      - 9300:9300
    environment:
      - "discovery.type=single-node"
      - "ES_JAVA_OPTS=-Xms2048m -Xmx2048m"
    restart: always
    volumes:
      - "/data/elasticsearch/data:/usr/share/elasticsearch/data"
      - "/data/elasticsearch/plugins:/usr/share/elasticsearch/plugins"
  kibana:
    image: kibana:7.7.0
    container_name: kibana7
    ports:
      - 5601:5601
    restart: always
    depends_on:
      - elasticsearch
  elasticsearchhead:
    image: bolingcavalry/elasticsearch-head:6
    container_name: elasticsearchhead
    environment:
      - "TZ=Asia/Shanghai"
    ports:
      - 9100:9100
    restart: always
    depends_on:
      - elasticsearch
```

启动服务

将docker-compose启动上传服务器

```sh
docker-compose up -d
```

访问测试：

```sh
http://192.168.5.20:9200/

http://192.168.5.20:9100/

http://192.168.5.20:5601/
```



至此elastic的compose安装已经成功

**安装ik分词器**

```sh
# 官网地址
https://github.com/medcl/elasticsearch-analysis-ik

# 分词名下载地址
https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.7.0/elasticsearch-analysis-ik-7.7.0.zip

cd /data/elasticsearch/plugins
mkdir -p ik
# 放入指定的ik目录


# 重启ES
docker-compose restart
```

## docker安装fastDFS

安装条件：内存至少2G以上

官网地址：

```sh
https://github.com/happyfish100/

# 帮助手册
https://github.com/happyfish100/fastdfs/wiki

#下载地址:
https://github.com/happyfish100/fastdfs-nginx-module
https://github.com/happyfish100/libfastcommon
http://nginx.org/download/nginx-1.16.1.tar.gz

#指定下载的相关具体版本
https://codeload.github.com/happyfish100/libfastcommon/tar.gz/refs/tags/V1.0.43
https://codeload.github.com/happyfish100/fastdfs-nginx-module/tar.gz/refs/tags/V1.22
http://nginx.org/download/nginx-1.16.1.tar.gz
```

本方法安装采用自定义镜像的方法

官网提供镜像制作的素材，但是需要更改Dockerfile文件及Source目录中的软件包。将官方的fastdfs-6.06.tar.gz解压缩，里面有docker目录，提供单机和集群版本。但是都需要修改才能使用。此处使用local版本

Dockerfile

有3处改动

1.  ADD的时候源文件，需要增加相应的版本号信息
2. cd目录时需要增加对应版本号信息
3. 基础镜像从centos7升级到centos7.8.2003版本

```sh
# centos 7
FROM centos:7.8.2003
# 添加配置文件
# add profiles
ADD conf/client.conf /etc/fdfs/
ADD conf/http.conf /etc/fdfs/
ADD conf/mime.types /etc/fdfs/
ADD conf/storage.conf /etc/fdfs/
ADD conf/tracker.conf /etc/fdfs/
ADD fastdfs.sh /home
ADD conf/nginx.conf /etc/fdfs/
ADD conf/mod_fastdfs.conf /etc/fdfs

# 添加源文件,对应的文件需要下载，并且放到对应的目录下,官方虽然此文件，但大小为0，基本没有用。需要替换
# add source code
ADD source/libfastcommon-1.0.43.tar.gz /usr/local/src/
ADD source/fastdfs-6.06.tar.gz /usr/local/src/
ADD source/fastdfs-nginx-module-1.22.tar.gz /usr/local/src/
ADD source/nginx-1.16.1.tar.gz /usr/local/src/

# Run
RUN yum install git gcc gcc-c++ make automake autoconf libtool pcre pcre-devel zlib zlib-devel openssl-devel wget vim -y \
  &&  mkdir /home/dfs   \
  &&  cd /usr/local/src/  \
  &&  cd libfastcommon-1.0.43/   \
  &&  ./make.sh && ./make.sh install  \
  &&  cd ../  \
  &&  cd fastdfs-6.06/   \
  &&  ./make.sh && ./make.sh install  \
  &&  cd ../  \
  &&  cd nginx-1.16.1/  \
  &&  ./configure --add-module=/usr/local/src/fastdfs-nginx-module-1.22/src/   \
  &&  make && make install  \
  &&  chmod +x /home/fastdfs.sh

# 此需要注释 
# 不然会报错 did not complete successfully: exit code: 1
#RUN ln -s /usr/local/src/fastdfs/init.d/fdfs_trackerd /etc/init.d/fdfs_trackerd \
#  && ln -s /usr/local/src/fastdfs/init.d/fdfs_storaged /etc/init.d/fdfs_storaged

# export config
VOLUME /etc/fdfs

EXPOSE 22122 23000 8888 80
ENTRYPOINT ["/home/fastdfs.sh"]
```

制作镜像

```sh
docker build --rm -t nullnull/fastdfs:1.0 .
```

启动镜像

官网上提供方式启动，会报找到不到127.0.0.1:23000端口，需要使用--net=host方式启动

```sh
# 启动命令
docker run -d -e FASTDFS_IPADDR=192.168.5.20 --name fastdfs --restart=always --net=host nullnull/fastdfs:1.0
```

至此fastDFS安装完成。

## docker安装gitlab

>官网地址:
>
>https://hub.docker.com/r/gitlab/gitlab-ce
>
>教程地址：
>
>https://docs.gitlab.com/omnibus/docker/

基础镜像:

```sh
#英文版
docker pull gitlab/gitlab-ce:12.7.6-ce.0
#中文版
docker pull twang2218/gitlab-ce-zh:11.1.4
```

运行容器 

```
# 创建目录
mkdir -p /data/gitlab/config
mkdir -p /data/gitlab/log
mkdir -p /data/gitlab/data

docker run -itd --name gitlab -p 443:443 -p 80:80 -p 222:22 --restart always -m 4GB -v /data/gitlab/config:/etc/gitlab -v /data/gitlab/logs:/var/log/gitlab -v /data/gitlab/data:/var/opt/gitlab -e TZ=Asia/Shanghai gitlab/gitlab-ce:12.7.6-ce.0
```

进行

```sh
vi /data/gitlab/config/gitlab.rb

#配置项目访问地址：
external_url 'http://192.168.5.20'

# 配置ssh协议所使用的访问地址和端口
gitlab_rails['gitlab_ssh_host'] = '192.168.5.20'
gitlab_rails['time_zone'] = 'Asia/Shanghai'
gitlab_rails['gitlab_shell_ssh_port'] = 222

```

配制完成后，需要进行重启

开始登录网页版本的gitlab进行配制。

首先进入修改密码

```sh
username: root
password: 12345678

创建组:dev
# 组分类： private : 私有的，Internal: 内部的 public 公开的

创建项目：
# 注意，不要初始化README，否则本地项目无法直接上传至gitlab服务器上.

创建用户:
#权限分两种：Regular： 普通权限用户。 Admin: 具有管理员权限的用户。
#创建后才能再次设置密码.

#给群组中的用户分配权限分五种：
#Guest:可以创建issue、发表评论、不能读写版本库。
#Reporter:可以克隆代码，不能提交、QA、PM可以赋予这个权限。
#Developer:可以克隆代码、开发、提交、push，普通开发可以赋予这个权限。
#Maintainer:可以创建项目、添加tag、保护分支、添加项目成员、编辑项目，核心开发人员可以赋予这个权限。
#Owner:可以设置项目访问权限、-Visibility Level、删除项目、迁移项目、管理组成员、开发组组长可以赋予这个权限。
```



IDEA操作

```
# 1. 创建本地仓库
VCS->Enable Version Control Integration...

# 2. 建立缓冲区
项目右键->git->add

# 3. 将代码提交本地仓库
项目右键->git->commit Directory

# 4.设置远程仓库的地址
项目右键->git->Repository->Remote

# 5. 将本地代码推送远程gitlib仓库。
```



## Docker的Swarm集群

### 节点信息

| 主机名 | IP地址       | 说明        |
| ------ | ------------ | ----------- |
| os20   | 192.168.5.20 | manager节点 |
| os21   | 192.168.5.21 | work01节点  |
| 0s22   | 192.168.5.22 | work02节点  |

硬件要求：

| 硬件资源 | 最小本所 | 推荐配制 |
| -------- | -------- | -------- |
| CPU      | 1CPU     | 2CPU     |
| 内存     | 2G       | 2-4G     |
| 硬盘     | 20G      | 50G      |

### 安装docker-swarm

官方文档:

https://docs.docker.com/engine/swarm/

说明：

docker Swarm和Docker Compose一样，都是Docker官方容器编排项目，但不同的是，Docker Compose是一个在单个服务器或主机上创建多个容器的工具，可以将缓存某个应用的多个Docker容器编码在一起，同时管理，而Docker Swarm则可以在多个服务器或者主机上创建容器集群服务，其主要作用把若干台主机抽象为一个整体，并且通过一个入口 （Docker stack）统一管理这些Docker主机上的各种Docker资源。

stack是构建环境中的service集群，它是自动部署多个相互关联的服务的简便方法。而无需单独定义每个服务。

stack file是一种yaml格式的文件，类似于docker-compose.yml，它定义了一个或多个服务，并定义了服务的环境变量、部署标签、容器数量以及相关的环境特定配制等。

Docker Swarm由两部分组成

- docker集群：将一个或者多个Docker节点组织起来，用户就能以集群的方式进行管理。
- 应用编排：有一套APi用来部署和管理容器。

![image-20231225124428038](.\images\image-20231225124428038.png)

建议配制私有化仓库

非必要缓件，集群中的每个节点都需要安装镜像，如果不搭建私有镜像，下载速度比较耗时

```sh
vi /etc/docker/daemon.json
"insecure-registries":["192.168.5.21:5000"]
systemctl daemon-reload
systemctl restart docker
```

初始化集群

```sh
# --advertise-addr 用来指定其他节点连接m0时的地址
# --listen-addr 用来指定swarm流量的IP和端口
# 会在本地创建一个Docker网络，类型为
# 在docker19，版本时，需要指定这两个参数，新版本不再需要指定 --listen-addr 192.168.5.20:2377
docker swarm init  --advertise-addr 192.168.5.20:2377 --listen-addr 192.168.5.20:2377

docker swarm init --advertise-addr 192.168.5.20:2377 

docker node ls 

docker network ls
```

样例：

```+
[root@os20 ~]# docker swarm init --advertise-addr 192.168.5.20:2377 
Swarm initialized: current node (xvwlotjgywnz841e6ln3tznc9) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-4h1f93z8fexow1gaz0vnegyu2u8oe9j2w0wwuhunfhqkojmq5a-0napytkn83uf39d51febo1i9d 192.168.5.20:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.

[root@os20 ~]# docker node ls 
ID                            HOSTNAME   STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
xvwlotjgywnz841e6ln3tznc9 *   os20       Ready     Active         Leader           24.0.7
[root@os20 ~]# 
[root@os20 ~]# docker network ls
NETWORK ID     NAME              DRIVER    SCOPE
d2c35cac0418   bridge            bridge    local
ceaf6b0af37d   docker_gwbridge   bridge    local
2dfb13ba7720   host              host      local
3rd93mcyd8e5   ingress           overlay   swarm
cbeae8ea834c   none              null      local
[root@os20 ~]# 
```

其他机器加入集群

Docker swarm的新节点加入策略是从管理节点获取一个加入的命令，例如：

```sh
docker swarm join --token SWMTKN-1-4h1f93z8fexow1gaz0vnegyu2u8oe9j2w0wwuhunfhqkojmq5a-0napytkn83uf39d51febo1i9d 192.168.5.20:2377
```

任何想加入集群的机器，只需要自己执行了这个命令，就可以加入swarm集群。

```sh
#1 如果有新的管理节点需要加入，在管理节点执行docker swarm join-token manager 即可得到管理节点manager的join token
docker swarm join-token manager

#2. 如果有新的工作节点需要加入，在管理节点执行docker swarm join-token worker 即可得到管理节点work节点的join token
docker swarm join-token worker

docker node ls
```

manager节点状态说明

- Leader 意味着该节点是这个集群的管理和编排决策的主要管理器节点
- Reachable 意味着节点是管理者，正在参与Raft共识。如果领导节点不可用，则该节点有资格被选为领导者。
- Unavaiable 意味着节点是不能与其他管理器节点通信的管理器，如果管理节点不可用，应该将新的管理器节点加入集群，或者将工作器节点升级为管理器。

AVAILABILITY的说明

- Active 意味着调度程序可以将任务分配给节点。
- Pause 意味着调度程序不会将新任务分配给节点，但现在任务仍在运行。
- Drain 意味着调度程序不会向该节点分配新任务，调度程序关闭所有现有任务并在其他节点上调度它们 。

manager节点信息查看

```sh
[root@os20 ~]# docker info
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
 Containers: 1
  Running: 1
  Paused: 0
  Stopped: 0
 Images: 1
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
 Swarm: active
  NodeID: xvwlotjgywnz841e6ln3tznc9
  Is Manager: true
  ClusterID: qfjsswd463htaitvgofcyrucc
  Managers: 1
  Nodes: 3
  Default Address Pool: 10.0.0.0/8  
  SubnetSize: 24
  Data Path Port: 4789
  Orchestration:
   Task History Retention Limit: 5
  Raft:
   Snapshot Interval: 10000
   Number of Old Snapshots to Retain: 0
   Heartbeat Tick: 1
   Election Tick: 10
  Dispatcher:
   Heartbeat Period: 5 seconds
  CA Configuration:
   Expiry Duration: 3 months
   Force Rotate: 0
  Autolock Managers: false
  Root Rotation In Progress: false
  Node Address: 192.168.5.20
  Manager Addresses:
   192.168.5.20:2377
 Runtimes: io.containerd.runc.v2 runc
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: 3dd1e886e55dd695541fdcd67420c2888645a495
 runc version: v1.1.10-0-g18a0cb0
 init version: de40ad0
 Security Options:
  seccomp
   Profile: builtin
 Kernel Version: 5.4.265-1.el7.elrepo.x86_64
 Operating System: CentOS Linux 7 (Core)
 OSType: linux
 Architecture: x86_64
 CPUs: 2
 Total Memory: 15.63GiB
 Name: os20
 ID: ef9373fd-c897-40a2-a63c-7adc844837f4
 Docker Root Dir: /var/lib/docker
 Debug Mode: false
 Experimental: false
 Insecure Registries:
  127.0.0.0/8
 Live Restore Enabled: false
```

工作节点的信息

```sh
[root@os21 ~]# docker info
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
 Images: 1
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
 Swarm: active
  NodeID: js4h1ntetrejnv6dam67atf37
  Is Manager: false
  Node Address: 192.168.5.21
  Manager Addresses:
   192.168.5.20:2377
 Runtimes: io.containerd.runc.v2 runc
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: 3dd1e886e55dd695541fdcd67420c2888645a495
 runc version: v1.1.10-0-g18a0cb0
 init version: de40ad0
 Security Options:
  seccomp
   Profile: builtin
 Kernel Version: 5.4.265-1.el7.elrepo.x86_64
 Operating System: CentOS Linux 7 (Core)
 OSType: linux
 Architecture: x86_64
 CPUs: 2
 Total Memory: 15.63GiB
 Name: os21
 ID: de81ef7b-17b9-44dc-9292-29cc0c00681f
 Docker Root Dir: /var/lib/docker
 Debug Mode: false
 Experimental: false
 Insecure Registries:
  127.0.0.0/8
 Live Restore Enabled: false
```

### 节点权限的提升与降低

```sh
# 1, 将worker 节点提升为manager节点,在manager节点执行命令
docker node promote os21
docker node ls

# 2,将manager节点降低为workder节点，在manager节点执行命令
docker node demote os21
docker node ls
```

样例：

```sh
[root@os20 ~]# docker node promote os21
Node os21 promoted to a manager in the swarm.
[root@os20 ~]# docker node ls
ID                            HOSTNAME   STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
xvwlotjgywnz841e6ln3tznc9 *   os20       Ready     Active         Leader           24.0.7
js4h1ntetrejnv6dam67atf37     os21       Ready     Active         Reachable        24.0.7
n2zen7d2q1rqdxqs71n00xgf7     os22       Ready     Active                          24.0.7
[root@os20 ~]#
[root@os20 ~]# docker node demote os21
Manager os21 demoted in the swarm.
[root@os20 ~]# docker node ls
ID                            HOSTNAME   STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
xvwlotjgywnz841e6ln3tznc9 *   os20       Ready     Active         Leader           24.0.7
js4h1ntetrejnv6dam67atf37     os21       Ready     Active                          24.0.7
n2zen7d2q1rqdxqs71n00xgf7     os22       Ready     Active                          24.0.7
[root@os20 ~]# 
```

### 脱离集群

```sh
# 在要脱离集群的节点上执行命令:
docker swarm leave
# 过个几分钟去manager节点上查看节点的状态
docker node ls

# 如果要删除脱离集群的节点
# 1. 需要先降级为worker节点后，再删除
docker node rm 节点名称|节点ID


# manager节点只能强制退出，manager退出后整个集群将不存在。
docker swarm leave --force 
```

样例：

```sh
# os21节点
[root@os21 ~]# docker swarm leave
Node left the swarm.
[root@os21 ~]# 

# 至管理节点查看
[root@os20 ~]# docker node ls
ID                            HOSTNAME   STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
xvwlotjgywnz841e6ln3tznc9 *   os20       Ready     Active         Leader           24.0.7
js4h1ntetrejnv6dam67atf37     os21       Down      Active                          24.0.7
n2zen7d2q1rqdxqs71n00xgf7     os22       Ready     Active                          24.0.7
[root@os20 ~]# 

# 至管理节点操作
[root@os20 ~]# docker node rm os21
os21
[root@os20 ~]# docker node ls
ID                            HOSTNAME   STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
xvwlotjgywnz841e6ln3tznc9 *   os20       Ready     Active         Leader           24.0.7
n2zen7d2q1rqdxqs71n00xgf7     os22       Ready     Active                          24.0.7
[root@os20 ~]# 
```



### 图形化界面

注意此需要安装在manager节点上

官网地址：

```http
https://hub.docker.com/r/dockersamples/visualizer
```

镜像信息：

```
docker pull dockersamples/visualizer:latest

# 运行镜像
docker run -itd --name visualizer -p 8099:8080 -e HOST=192.168.5.20 -e 8080 -v /var/run/docker.sock:/var/run/docker.sock dockersamples/visualizer:latest


# 也可以采用server命令运行镜像
docker service create \
  --name=viz \
  --publish=8080:8080/tcp \
  --constraint=node.role==manager \
  --mount=type=bind,src=/var/run/docker.sock,dst=/var/run/docker.sock \
 dockersamples/visualizer

```

示例：

```
[root@os20 ~]# docker run -itd --name visualizer -p 8099:8080 -e HOST=192.168.5.20 -e 8080 -v /var/run/docker.sock:/var/run/docker.sock dockersamples/visualizer:latest
208af7b51f3c753210092575d915eb016e8200981cf6526d3ca17667768b07b1
[root@os20 ~]# docker stop visualizer && docker rm visualizer
visualizer
visualizer

[root@os20 ~]# docker service create \
>   --name=viz \
>   --publish=8080:8080/tcp \
>   --constraint=node.role==manager \
>   --mount=type=bind,src=/var/run/docker.sock,dst=/var/run/docker.sock \
>  dockersamples/visualizer
0r2s3o5e6sgqn09zzi5m07w5m
overall progress: 1 out of 1 tasks 
1/1: running   
verify: Service converged 
[root@os20 ~]# 
```

![image-20231225190840221](.\images\image-20231225190840221.png)

### swarm命令

| 命令                    | 描述                       |
| ----------------------- | -------------------------- |
| docker swarm init       | 初始化一个swarm集群        |
| docker swarm join       | 加入集群作为节点或者管理器 |
| docker swarm join-token | 管理用于加入集群的令牌     |
| docker swarm leave      | 离开swarm集群              |
| docker swarm unlock     | 解锁swarm集群              |
| docker swarm unlock-key | 管理解锁钥匙               |
| docker swarm update     | 更新swarm集群              |

node命令

| 命令                | 描述                                             |
| ------------------- | ------------------------------------------------ |
| docker node demote  | 从swarm集群管理器中降级一个或多个节点            |
| docker node inspect | 显示一个或者多个节点的详细信息                   |
| docker node ls      | 列出swarm集群的节点                              |
| docker node promote | 将一个或者多个节点加入集群管理器中               |
| docker node ps      | 列出一个或者多个节点上运行的任务，默认为当前节点 |
| docker node rm      | 从swarm集群删除一个或者多个节点                  |
| docker node update  | 更新一个节点                                     |

service命令

| 命令                    | 描述                           |
| ----------------------- | ------------------------------ |
| docker service create   | 创建服务                       |
| docker service inspect  | 显示一个或者多个服务的详细信息 |
| docker service logs     | 获取服务的日志                 |
| docker service ls       | 列出服务                       |
| docker service rm       | 删除一个或者多个服务           |
| docker service scale    | 设置服务的实例数量             |
| docker service update   | 更新服务                       |
| docker service rollback | 恢复服务至update之前的配置     |

stack命令

| 命令                  | 描述                         |
| --------------------- | ---------------------------- |
| docker stack deploy   | 部署新的堆栈或者更新现有堆栈 |
| docker stack ls       | 列出现有堆栈                 |
| docker stack ps       | 列出堆栈中的任务             |
| docker stack rm       | 删除一个或多个堆栈           |
| docker stack services | 列出堆栈中的服务             |



### 命令行部署nginx

```sh
# 准备基础镜像
docker pull nginx:1.18.0-alpine
docker pull nginx:1.19.3-alpine

docker images ls

# 导出镜像，并分发到其他节点
docker save -o nginx-1.18.0-alipine.image 684dbf9f01f3
docker save -o nginx-1.19.3.image 4efb29ff172a

scp nginx-*  root@192.168.5.21:/root/
scp nginx-*  root@192.168.5.22:/root/

# 基础节点装载镜像
docker load < nginx-1.18.0-alipine.image 
docker load < nginx-1.19.3.image 

```

主节点上执行

```sh
[root@os20 ~]# docker pull nginx:1.18.0-alpine
1.18.0-alpine: Pulling from library/nginx
ddad3d7c1e96: Already exists 
c7974bc8b744: Pull complete 
d04f0a2e9201: Pull complete 
df7ae1cb4591: Pull complete 
4259d8811e1d: Pull complete 
Digest: sha256:93baf2ec1bfefd04d29eb070900dd5d79b0f79863653453397e55a5b663a6cb1
Status: Downloaded newer image for nginx:1.18.0-alpine
docker.io/library/nginx:1.18.0-alpine
[root@os20 ~]# docker pull nginx:1.19.3-alpine
1.19.3-alpine: Pulling from library/nginx
188c0c94c7c5: Pull complete 
61c2c0635c35: Pull complete 
378d0a9d4d5f: Pull complete 
2fe865f77305: Pull complete 
b92535839843: Pull complete 
Digest: sha256:5aa44b407756b274a600c7399418bdfb1d02c33317ae27fd5e8a333afb115db1
Status: Downloaded newer image for nginx:1.19.3-alpine
docker.io/library/nginx:1.19.3-alpine
[root@os20 images]# docker images
REPOSITORY                 TAG             IMAGE ID       CREATED       SIZE
dockersamples/visualizer   latest          43ce62428b8c   2 years ago   185MB
nginx                      1.18.0-alpine   684dbf9f01f3   2 years ago   21.9MB
nginx                      1.19.3-alpine   4efb29ff172a   3 years ago   21.8MB
gitlab/gitlab-ce           12.7.6-ce.0     b9923370e7ce   3 years ago   1.85GB
[root@os20 images]# docker save -o nginx-1.18.0-alipine.image 684dbf9f01f3
[root@os20 images]# docker save -o nginx-1.19.3.image 4efb29ff172a
```

其他节点:

```
[root@os21 images]# docker load < nginx-1.18.0-alipine.image 
9a5d14f9f550: Loading layer [==================================================>]  5.885MB/5.885MB
d1cf28aead06: Loading layer [==================================================>]  17.48MB/17.48MB
154dfe1bc87d: Loading layer [==================================================>]  3.072kB/3.072kB
bda4c7e5e442: Loading layer [==================================================>]  4.096kB/4.096kB
f88365b5c5d3: Loading layer [==================================================>]  3.584kB/3.584kB
Loaded image ID: sha256:684dbf9f01f3250437d595669c7437c202573798ab34247d50338ff630e58b6a
[root@os21 images]# docker load < nginx-1.19.3.image 
ace0eda3e3be: Loading layer [==================================================>]  5.843MB/5.843MB
4daeb7840e4d: Loading layer [==================================================>]  17.45MB/17.45MB
835f5b67679c: Loading layer [==================================================>]  3.072kB/3.072kB
d0e26daf1f58: Loading layer [==================================================>]  4.096kB/4.096kB
8d6d1951ab0a: Loading layer [==================================================>]  3.584kB/3.584kB
Loaded image ID: sha256:4efb29ff172a12f4a5ed5bc47eda3596f8b812173cf609cbb489253dad6e737f
[root@os21 images]# 
```

部署nginx

```sh
# 在manager节点上创建overlay网络
docker network create -d overlay nginx-net

# 创建5个nginx容器的集群
docker service create --name nginx --network nginx-net -p 80:80 --replicas 5  nginx:1.18.0-alpine

# 在manager节点查看服务的情况，workder节点无法查看
docker service ls

# 在manager节点或者worker节点可以执行docker ps命令查看本机上的容器
docker ps

# manager节点上只用管理集群，不希望部署服务
docker node update --availability drain os20

# 使用命令进行服务的缩容
docker service scale nginx=2
```



操作 

```sh
[root@os20 ~]# docker network create -d overlay nginx-net
qznk57mr47jbk0ui95qkumwtd
[root@os20 ~]# docker service create --name nginx --network nginx-net -p 80:80 --replicas 5  nginx:1.18.0-alpine
6qz1fqdfrfpkjo04r14cjfyyu
overall progress: 5 out of 5 tasks 
1/5: running   [==================================================>] 
2/5: running   [==================================================>] 
3/5: running   [==================================================>] 
4/5: running   [==================================================>] 
5/5: running   [==================================================>] 
verify: Service converged 
[root@os20 ~]# docker service ls
ID             NAME      MODE         REPLICAS   IMAGE                 PORTS
6qz1fqdfrfpk   nginx     replicated   5/5        nginx:1.18.0-alpine   *:80->80/tcp
```

查看部署的nginx

![image-20231225232845682](.\images\image-20231225232845682.png)

当执行manager节点只管理不部署服务的命令后。

```
[root@os20 ~]# docker node update --availability drain os20
os20
```

![image-20231225233226629](.\images\image-20231225233226629.png)

当执行缩容后

```sh
[root@os20 ~]# docker service scale nginx=2
nginx scaled to 2
overall progress: 2 out of 2 tasks 
1/2:   
2/2: running   [==================================================>] 
verify: Service converged 
[root@os20 ~]# 
```

![image-20231225233504849](.\images\image-20231225233504849.png)



升级nginx版本

```sh
# 1. 更新镜像
docker service update --image nginx:1.19.3-alpine nginx


# 2. 添加或者更新一个对外端口
docker service  update --publish-add 8090:80 nginx
```

样例:

```sh
[root@os20 ~]# docker service update --image nginx:1.19.3-alpine nginx
nginx
overall progress: 2 out of 2 tasks 
1/2: running   [==================================================>] 
2/2: running   [==================================================>] 
verify: Service converged 
[root@os20 ~]# docker service  update --publish-add 8090:80 nginx
nginx
overall progress: 2 out of 2 tasks 
1/2: running   [==================================================>] 
2/2: running   [==================================================>] 
verify: Service converged 

```



![image-20231225233840591](.\images\image-20231225233840591.png)

清理

```sh
[root@os20 ~]# docker service rm nginx
nginx
[root@os20 ~]# docker network rm nginx-net
nginx-net
```







### docker-compose部署

docker-compose.yml

```yaml
version: '3'
services:
  nginx-web:
    image: nginx:1.19.3-alipine
    container_name: nginx
    networks:
      - nginx-net
    restart: always
    ports:
      - 80:80
    deploy:
      replicas: 5
networks:
  nginx-net:
    driver: overlay
```

上传服务器

```
在manager节点上创建docker-compose.yml文件，执行命令
docker stack deploy nginx-stack --compose-file=docker-compose.yml
docker stack deploy nginx-stack -c docker-compose.yml

# 查看stack服务运行情况
docker stack services nginx-stack

# 查看5个容器运行在哪些个节点。
docker service ls nginx-stack_nginx-web
docker service ps nginx-stack_nginx-web

```

日志

```sh
[root@os20 nginx]# docker service ls 
ID             NAME                    MODE         REPLICAS   IMAGE                  PORTS
nfj0hutdjks6   nginx-stack_nginx-web   replicated   0/5        nginx:1.19.3-alipine   *:80->80/tcp
[root@os20 nginx]# docker service ps nginx-stack_nginx-web
ID             NAME                          IMAGE                  NODE      DESIRED STATE   CURRENT STATE                      ERROR                              PORTS
tyc7vs2a6mhb   nginx-stack_nginx-web.1       nginx:1.19.3-alipine   os22      Running         Preparing 13 seconds ago                                              
dn9ffxyh9ufo    \_ nginx-stack_nginx-web.1   nginx:1.19.3-alipine   os21      Shutdown        Rejected 14 seconds ago            "No such image: nginx:1.19.3-a…"   
aaya0opqjfxu    \_ nginx-stack_nginx-web.1   nginx:1.19.3-alipine   os21      Shutdown        Rejected 48 seconds ago            "No such image: nginx:1.19.3-a…"   
1qsj3s1cg0c0    \_ nginx-stack_nginx-web.1   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
bzknjzhn0p9q    \_ nginx-stack_nginx-web.1   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
y3uyztpli81h   nginx-stack_nginx-web.2       nginx:1.19.3-alipine   os22      Ready           Preparing less than a second ago                                      
1askqsc687pa    \_ nginx-stack_nginx-web.2   nginx:1.19.3-alipine   os22      Shutdown        Rejected 1 second ago              "No such image: nginx:1.19.3-a…"   
kckcvc69j34m    \_ nginx-stack_nginx-web.2   nginx:1.19.3-alipine   os21      Shutdown        Rejected 19 seconds ago            "No such image: nginx:1.19.3-a…"   
woojxnm6cfeo    \_ nginx-stack_nginx-web.2   nginx:1.19.3-alipine   os22      Shutdown        Rejected 53 seconds ago            "No such image: nginx:1.19.3-a…"   
rxiq2dchv2pi    \_ nginx-stack_nginx-web.2   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
qbe4evfbpida   nginx-stack_nginx-web.3       nginx:1.19.3-alipine   os22      Running         Preparing 32 seconds ago                                              
x2kv5azkmmzx    \_ nginx-stack_nginx-web.3   nginx:1.19.3-alipine   os21      Shutdown        Rejected 33 seconds ago            "No such image: nginx:1.19.3-a…"   
i1r8kkrd9jzs    \_ nginx-stack_nginx-web.3   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
ki5p23iyzdw9    \_ nginx-stack_nginx-web.3   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
ka860gof6u5c    \_ nginx-stack_nginx-web.3   nginx:1.19.3-alipine   os21      Shutdown        Rejected 2 minutes ago             "No such image: nginx:1.19.3-a…"   
2jbp7vkdn4rt   nginx-stack_nginx-web.4       nginx:1.19.3-alipine   os22      Ready           Preparing 2 seconds ago                                               
yehyllbi1pxt    \_ nginx-stack_nginx-web.4   nginx:1.19.3-alipine   os21      Shutdown        Rejected 3 seconds ago             "No such image: nginx:1.19.3-a…"   
juha3b5f7tce    \_ nginx-stack_nginx-web.4   nginx:1.19.3-alipine   os21      Shutdown        Rejected 36 seconds ago            "No such image: nginx:1.19.3-a…"   
0yeyzlzh052x    \_ nginx-stack_nginx-web.4   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
hlpu128jj203    \_ nginx-stack_nginx-web.4   nginx:1.19.3-alipine   os21      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
x6wurftcgiyt   nginx-stack_nginx-web.5       nginx:1.19.3-alipine   os22      Ready           Preparing 3 seconds ago                                               
9llvg8qp07u1    \_ nginx-stack_nginx-web.5   nginx:1.19.3-alipine   os22      Shutdown        Rejected 4 seconds ago             "No such image: nginx:1.19.3-a…"   
sfwonpdanzuy    \_ nginx-stack_nginx-web.5   nginx:1.19.3-alipine   os21      Shutdown        Rejected 23 seconds ago            "No such image: nginx:1.19.3-a…"   
mpreaoh44760    \_ nginx-stack_nginx-web.5   nginx:1.19.3-alipine   os21      Shutdown        Rejected 57 seconds ago            "No such image: nginx:1.19.3-a…"   
9x8rox4k57m3    \_ nginx-stack_nginx-web.5   nginx:1.19.3-alipine   os22      Shutdown        Rejected about a minute ago        "No such image: nginx:1.19.3-a…"   
[root@os20 nginx]# 
```



总结：

- networks中也可以不指定driver:overlay, 因为docker swarm默认网络类型是overlay
- 整个network都可以不用配置。stack部署会默认创建网络。如果我们定义网络。docker stack deploy时会先默认创建一个网络，在创建一个我们定义的网络。
- 一定要把镜像先拉取到本地再执行。

### Docker stack和Docker Compose区别

- Docker stack会忽略了构建指令，无法使用stack命令构建新镜像，它是需要镜像预先构建好的。所以docker-compose更适合于开发场景。
- Docker Compose是一个Python项目，在内部，它使用Docker API规范来操作容器。 所以需要安装Docker-Compose，以便在Dokcer一起在计算机上使用。
- Docker stack功能包含在Docker引擎中。不需要安装额外的包来使用它，Docker stack只是swarm mode的一部分
- Docker stack不支持基于第2版本写的Docker-compose.yml，也就是version版本至少为3，然尔Docker Compose对版本为2和3的文件仍然可以处理。
- Docker stack把docker compose的所有工作都做完了，因此docker stack将占主导地位。对于大多数用户来说，切换到docker stack即不困难也不需要太多的开销，建议使用Docker stack.









## 结束
