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

RUN ln -s /usr/local/src/fastdfs/init.d/fdfs_trackerd /etc/init.d/fdfs_trackerd \
  && ln -s /usr/local/src/fastdfs/init.d/fdfs_storaged /etc/init.d/fdfs_storaged

# export config
VOLUME /etc/fdfs


EXPOSE 22122 23000 8888 80
ENTRYPOINT ["/home/fastdfs.sh"]