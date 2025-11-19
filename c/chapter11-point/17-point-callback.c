#include <stdio.h>

/**
 * 定义加法函数
 */
int add(int a,int b){
    return a+b;
}

/**
 * 定义减法函数
 */
int minus(int a,int b){
    return a-b;
}

/**
 * 定义回调函数的方法，及参数的调用
 */
int doInvokde(int (*run)(int,int),int num1,int num2)
{
    return run(num1,num2);
}

int main(){
    printf("加法运算:%d\n",doInvokde(add,3,2));
    printf("减法运算:%d",doInvokde(minus,3,2));
    return 0;
}