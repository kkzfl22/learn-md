#include <stdio.h>

int main(){
    int num;
    printf("请输入一个整数:");
    scanf("%d",&num);

    if(num > 0 )
    {
        printf("输入数字大于0");
    }

    if(num < 0)
    {
        printf("输入数字小于0");
    }

    if(num == 0)
    {
        printf("输入数字等于0");
    }

    return 0;
}