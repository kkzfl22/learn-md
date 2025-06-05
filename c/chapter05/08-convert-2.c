#include <stdio.h>

int main(){
    
    //赋值，窄类型赋值给宽类型
    int a1 = 10;
    double a2 = a1;
    printf("%f \n",a2);


    //赋值 宽类型赋值给窄类型,直接做整数截取
    double b1 = 1.2;
    int b2 = b1;
    printf("%d",b2);

    return 0;
}