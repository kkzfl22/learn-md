#include <stdio.h>

int main(){
    for(int i=0;i<=100;i++)
    {
        //跳过那些7的倍数或包含7的数字。   

        //i % 7==0  7的倍数
        //i % 10==7 个位包含7的数字
        //i / 10=7  十位包含7的数字
        if(i % 7==0 
        || i % 10==7
        || i / 10==7){
            continue;
        }
        printf("数字：%d \t",i);
        if(i % 10 == 0)
        {
            printf("\n");
        }
    }
    return 0;
}