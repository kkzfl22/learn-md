#include <stdio.h>

int main(){
    int passwrod = 654321;
    int input = 0;
    while(passwrod != input)
    {
        printf("请输入密码:");
        scanf("%d",&input);
    }
    printf("密码正常，欢迎回家！");

    return 0;
}