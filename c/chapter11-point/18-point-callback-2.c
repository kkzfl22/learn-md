#include <stdio.h>
#include <stdlib.h>

/**
 * 组数据做初始化赋值
 * 参数1，数组
 * 参数2，长度，
 * 参数3，返回值的函数
 * 
 */
void array_init(int *array_int,int len,int (*init_fun)()){
    for(int i=1;i<=len;i++){
        array_int[i-1]=init_fun(i);
    }
}

int rand_scope(int len){
    return rand() % len;
}

int fib(int len)
{
    if(len == 0 || len == 1)
    {
        return 1;
    }

    return fib(len-1) + fib(len-2);
}

int main(){
    //给函数使用随机数赋值
    int array[10];
    int len = 10;
    array_init(array,len,rand);
    for(int i=0;i<len;i++)
    {
        printf("%d \t",array[i]);
    }
    printf("\n");

    //使用自定义的函数给数组赋值
    array_init(array,len,rand_scope);
    for(int i=0;i<len;i++)
    {
        printf("%d \t",array[i]);
    }
    printf("\n");

    //带参数的回调函数,返回斐波那契数列
     array_init(array,len,fib);
    for(int i=0;i<len;i++)
    {
        printf("%d \t",array[i]);
    }
    printf("\n");

    return 0;
}