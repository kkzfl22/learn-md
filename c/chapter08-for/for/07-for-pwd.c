#include <stdio.h>

int main(){
    int pwd = 123456;
    int input;
    for(;input != pwd;)
    {
        printf("请输入6位密码:");
        scanf("%d",&input);
    }

    printf("密码正确，欢迎回家!");

    return 0;
}