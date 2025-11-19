#include <stdio.h>

int main(){

    //定义变量
    int src = 150;
    //定义三级指针;
    int *ptr1=&src;
    int **ptr1_1=&ptr1;
    int ***ptr1_1_1=&ptr1_1;

    //首先打印一级指针
    printf("一级指针，地址：%p,值：%d\n",ptr1,*ptr1);
    printf("二级指针，地址：%p,值:%p,取一级指针:%d\n",ptr1_1,*ptr1_1,**ptr1_1);
    printf("三级指针，地址: %p,值:%p,取二批指针：%p,取一级指针：%d\n",ptr1_1_1,*ptr1_1_1,**ptr1_1_1,***ptr1_1_1);

    return 0;
}