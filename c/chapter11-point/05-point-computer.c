#include <stdio.h>

int main(){
    int nums[] = {10,20,30,40,50};
    //声明两个指针指向第一元素和第4个元素
    int *num1 = &nums[0];
    int *num4 = &nums[4];

    //计算两个指针相减，返回相隔多少距离
    //低位地址减高位地址返回负数，
    printf("num1-num4=%td,\n",num1-num4);
    //高位地址减低位地址返回正数
    printf("num4-num1=%td.\n",num4-num1);

    double dv1 = 11.2;
    double dv2 = 13.2;
    double *d1 = &dv1;
    double *d2 = &dv2;

    //此也可以计算两个地址间的距离
    printf("d1-d2 = %td.\n",d1-d2);
    printf("d2-d1 = %td.\n",d2-d1);

    return 0;
} 