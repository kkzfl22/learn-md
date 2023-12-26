# 此平台不支持虚拟化的 AMD-V/RVI

## 问题描述:

在安装了VM虚拟机后，启动CPU的硬件虚拟化，沟选“勾选虚拟机CPU的虚拟化AMD-V/RVI” ，启动VMware后提示： “此平台不支持虚拟化的 amd-v/rvi”



## 问题分析：

此问题在我开始遇到的时候，我以为是我电脑不支持。直到看到。这个CPU是支持的。

![image-20231226171402577](./images\image-20231226171402577.png)

由此可以发现，还是存在软件的问题。



## 处理1：关闭hyper-v

那这个问题是哪里引起的，在网上搜索了之后，

https://blog.csdn.net/rongye4/article/details/128043194

这位博主解释了这个问题。那就是docker使用的WSL，而WSL依赖于hyper-v，而虚拟机与hyper-v是相冲突的，是需要关闭hyper-v，才能使用硬件的虚拟化。

```sh
# 关闭
# 注：需要使用管理员权限，记得用管理员模式打开CMD
bcdedit /set hypervisorlaunchtype off
```

我就是此问题引起的。

除让给之外，还需要关闭：

![img](.\images\v2-e6e79513727d2ca3902e1ac40fe0889f_1440w.webp)

## 处理2： 硬件虚拟化是否打开

如果电脑未开启虚拟机，是需要至BIOS中将硬件虚拟化给打开的。

## 处理3：内核隔离关闭

打开 Windows 安全中心 --- 设备安全性 --- 内核隔离 --- 内核隔离详细信息，然后将内存完整性保持关闭。

![image-20231226172152917](.\images\image-20231226172152917.png)



以完成以上操作之后，就能够解决“此平台不支持虚拟化Intel VT-x/EPT 或 AMD-V/RVI（V）”的问题，





参考：

https://zhuanlan.zhihu.com/p/614643382?utm_id=0&wd=&eqid=d6ec0a6a000ea9d8000000046492a124

https://blog.csdn.net/rongye4/article/details/128043194



