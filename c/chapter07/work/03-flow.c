#include <stdio.h>

int main(){
    int num;
    printf("请输入一个数字，以判断是否为水仙花数:");
    scanf("%d",&num);

    int num1 = num/100;
    int num2 = (num  - num1 * 100)/10;
    int num3 = num  - num1 * 100 - num2*10;

    int num1Sum = num1 * num1 * num1;
    int num2Sum = num2 * num2 * num2;
    int num3Sum = num3 * num3 * num3;
    int sum = num1Sum + num2Sum + num3Sum;

    if(num == sum)
    {
        printf("数字是一个水化花数");
    }
    else{
        printf("输入的数字不是一个水仙花数");
    }

    return 0;
}