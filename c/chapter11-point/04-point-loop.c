#include <stdio.h>

int main(){
    //int数组
    int nums[] = {10,20,30,40,50};

    //得到首个地址
    int *num1 = &nums[0];

    //遍历地址，打印地址与值
    for(int i=0;i<5;i++)
    {
        printf("第%d个数据，地址：%p,值:%d。\n",i,num1,*num1);
        num1++;
    }

    printf("\n");
    printf("------------------------------");
    printf("\n");


    int *numout = &nums[4];

    //倒序遍历，打印地址与值
    for(int i=4;i>=0;i--)
    {
        printf("第%d个数据，地址：%p,值:%d。\n",i,numout,*numout);
        numout--;
    }

    return 0;
}