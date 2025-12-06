#include <stdio.h>

//宏定义有点骚的操作 可以对关键字操作
#define 如果 if
#define 否则 else
#define 格式输入 scanf
#define 格式输出 printf
#define 整型 int


int main(){
    //定义变量
    整型 年龄;
    格式输出("请输入您的年龄:");
    格式输入("%d",&年龄);

    如果(年龄 > 18)
    {
        格式输出("您是成年人了，请进");
    }
    否则
    {
        格式输出("小孩子，一边去");
    }

    return 0;
}