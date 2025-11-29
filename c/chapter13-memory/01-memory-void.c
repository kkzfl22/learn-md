#include <stdio.h>

int main()
{
    int num = 45;
    double pi = 3.1415926;

    // int类型的指针转为void指针
    void *viprt = &num;
    // double类型的指针转为void指针
    void *vdprt = &pi;

    // void类型的指针转换为int类型指针并解引用
    //隐式类型转换
    //int *intptr = viprt;
    //显示类型转换，一般使用显示转换，更明显
    int *intptr = (int *)viprt;
    printf("整数值:%d \n",*intptr);

    //void类型的指针转换为double类型指针并解引用
    //隐式转换
    //double *doubleptr = vdprt;
    //显示类型转换，
    double *doubleptr = (double *)vdprt;
    printf("浮点数:%f \n",*doubleptr);


    return 0;
}