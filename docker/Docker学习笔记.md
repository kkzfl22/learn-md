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



## docker相关的命令

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







