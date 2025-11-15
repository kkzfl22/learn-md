#include <stdio.h>

int main(){
    //整数提升
    short s1 = 10;
    int n1 = 40000;
    
    //运算过程中，变量s1是char类型，会自动转换为int类型
    printf("%d \n",s1 + n1);


    //有符号的整数自动转换为无符号的整数
    int n2 = -100;
    unsigned int n3 = 20;
    //负数转换为无符号整数，两都绝对值的和是无符号整数的最大值加1
    printf("%u \n",n2+n3); //结果：4294967216

    //不同类型的浮点数运算，精度低的转换为精度高的
    float f1 = 1.25f;
    double d2 = 4.5431213;
    printf("%.10f \n",f1+d2);

    //整数与浮点数运算，整数转换为浮点数
    int n4 = 10;
    double d3 = 1.67;
    printf("%f",n4+d3);


    return 0;
}