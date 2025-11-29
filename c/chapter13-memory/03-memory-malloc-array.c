#include <stdio.h>
#include <stdlib.h>
#define TOTAL 10

int main(){
    //分配一个数组的空间
    //这是定义的数组首元素的指针
    int *p;
    p = (int *)malloc(TOTAL * sizeof(int));

    if(NULL == p)
    {
        printf("内存分配失败!");
        return -1;
    }

    //组分配的内存中填充数据
    for(int i=0;i<TOTAL;i++){
        p[i]=i*10;
    }

    //数出填充的数据
    for(int i=0;i<TOTAL;i++){
        printf("元素:%d,地址:%p,值:%d \n",i,&p[i],p[i]);
    }

    //释放内存
    free(p);


    return 0;
}