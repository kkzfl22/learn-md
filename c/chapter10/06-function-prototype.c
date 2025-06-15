#include <stdio.h>

//使用函数原型进行声明
int max(int a,int b);

int main(){
    printf("求最大数为:%d\n",max(10,20));
    return 0;
}
//函数定义
int max(int a,int b)
{
    return a > b ? a : b;
}