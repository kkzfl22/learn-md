#include <stdio.h>

//定义全局变量不进行初始化赋值
int a; //自动初始化为0
double b; //自动初始化为0.0
char c; //自动初始化为空字符\0

//定义全局数组不进行初始化
int arr[5]; //所有元素自动初始化为0
char msg[6]; //所有元素自动初始化为空字符符\0


int main(){
    printf("a=%d \n",a); // a =0
    printf("b=%f \n",b); // b = 0.000000
    printf("c=%c \n",c); // c= 
    printf("\n");

    //计算数组的长度
    int len = sizeof(arr) / sizeof(arr[0]);

    //遍历数组
    for(int i = 0;i<len; i++){
        printf("%d ",arr[0]);
    }

    printf("\n\n");

    printf("字符串数组:%s",msg);

    return 0;
}