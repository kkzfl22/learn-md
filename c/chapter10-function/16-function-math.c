#include <stdio.h>
#include <math.h>

int main(){
    
    printf("2的绝对值%.0f,-123的绝对值:%.2f,\n",fabs(2),fabs(-123));
    printf("10的平方根:%f,\n",sqrt(10));
    printf("8的立方根%.f,\n",cbrt(8));
    printf("2的12次方:%.f,\n",pow(2,12));
    printf("12.31向上取整:%.f,-12.31向上取整:%.f,\n",ceil(12.31),ceil(-12.31));
    printf("15.31向下取整:%.f,-15.31向下取整:%.f,\n",floor(15.31),floor(-15.31));
    printf("15.31四舍五入:%.f,-15.31四舍五入:%.f,\n",round(15.31),round(-15.31));
    printf("15.31截取小数:%.f,-15.31截取小数:%.f,\n",trunc(15.31),trunc(-15.31));
    

    return 0;
}