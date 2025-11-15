#include <stdio.h>

int main(){

    //通过指针修改所指向的值
    
    //创建double类型的变量
    double num1 = 321.22;

    //创建p1指针指向num1
    double *p1 = &num1;

    //创建p2指针，将p1的值赋给p2，p1存储的num1的内存地址，此时p1和P2都是num1
    double *p2 = p1;

    //指针num的值
    printf("num1=%.2f \n",num1);

    //通过p1指针修改num1的值
    *p1 = 333.35;
    printf("num1=%.2f \n",num1);

    //通过p2指针修改num2的值
    *p2+=100.01;
    printf("num1=%.2f \n",num1);


    return 0;
}