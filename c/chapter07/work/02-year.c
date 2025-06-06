#include <stdio.h>

int main(){
    int year;
    printf("请输入一个年份");
    scanf("%d",&year);

    if(year % 400 == 0  || year % 4 == 0)
    {
        printf("为一个闰年");
    }
    else{
        printf("不是一个闰年");
    }
    
    return 0;
}