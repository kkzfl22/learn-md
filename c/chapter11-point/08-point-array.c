#include <stdio.h>

int main(){

    //创建三个变量
    int num1 = 10,num2 = 20,num3 = 30;
    
    //创建一个长度为3的指针数组
    int *prt_array[3];

    //给指针数组中的每个元素赋值
    prt_array[0]=&num1;
    prt_array[1]=&num2;
    prt_array[2]=&num3;

    //遍历指针数组打印
    for(int i=0;i<3;i++){
        printf("第%d个元素，地址：%p,值:%d\n",i,prt_array[i],*prt_array[i]);
    }



    return 0;
}