#include <stdio.h>

int main(){
    //二进制表示
    int num1 = 0b10;
    //十进制，正常
    int num2 = 100;
    //十六进制.
    int num3 = 0x1f;

    printf("二进制: %d \n",num1);
    printf("十进制: %d \n",num2);
    printf("十六进制: %d \n",num3);

    return 0;
}