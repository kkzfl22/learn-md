#include <stdio.h>

int main(){
    double d1 = 1.934;
    double d2 = 4.2;
    int num1 = (int)d1 + (int)d2; //d1转为1，d2转为4，结果为5
    int num2 = (int)(d1 + d2); //d1+d2=6.134 结果为6
    int num3 = (int) (3.5 * 10 + 6 * 1.5); //35 + 9.0 = 44 强转结果为44;

    printf("num1=%d \n",num1);
    printf("num2=%d \n",num2);
    printf("num3=%d \n",num3);
    return 0;
}