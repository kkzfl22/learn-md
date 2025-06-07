#include <stdio.h>

int main(){
    int pwd = 23456;
    int input;
    while (input != pwd)
    {
        printf("请输入密码:");
        scanf("%d",&input);
    }

    printf("当前密码正常，欢迎回家！");
    
    return 0;
}