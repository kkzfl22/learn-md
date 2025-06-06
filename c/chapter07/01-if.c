#include <stdio.h>

int main(){
    int age = 0;
    printf("请输入年龄:");
    scanf("%d",&age);

    //根据年龄来判断处理
    if(age < 18)
    {
        printf("未成年人请在家长陪同下访问! ");
    }

    printf("欢迎继续访问");
    return 0;
}