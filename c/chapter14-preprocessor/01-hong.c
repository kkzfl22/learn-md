#include <stdio.h>

//使用宏定义常量
#define PI 3.14

int main(){

    //定义保存半径，值通过用户输入
    double radius;
    printf("请输入半径:");
    scanf("%lf",&radius);

    //计算面积
    printf("圆的面积:%.2f",radius * radius * PI);
}