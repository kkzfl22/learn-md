#include <stdio.h>

int main(){
    int age = 0;
    printf("请输入年龄:");
    scanf("%d",&age);

    if(age > 18){
        printf("\n你年龄大于18岁，要对自己的行业负责!");
    }
    else{
        printf("\n你年龄还小，这次放过你了");
    }
    return 0;
}