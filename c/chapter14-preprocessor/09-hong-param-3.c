#include <stdio.h>

//宏定义和函数的区别

#define CUBE(n) (n) * (n) * (n)
int cube(n)
{
    return n * n * n;
}

int main(){

    //输出1到5所有数字的立方
    int i = 0;
    while (i<5)
    {
        //使用函数
        printf("%d \n",cube(i++));
    }

    printf("=============\n");
    
    i=0;
     while (i<5)
    {
        //使用带参数的宏定义
        printf("%d \n",CUBE(i++)); //文本替换(i++)*(i++)*(i++);
    }

    return 0;
}