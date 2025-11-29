#include <stdio.h>
#include <stdlib.h>
#define TOTAL 10

int main(){
    //定义整型指针
    int *p;
    //内存分配
    p = (int *)calloc(TOTAL,sizeof(int));

    //检查内存分配是否成功
    if(NULL == p)
    {
        printf("内存分配失败!");
        return -1;
    }

    //输出数组的的元素与值
    for(int i=0;i<TOTAL;i++){
        printf("元素:%d,地址:%p,值:%d\n",i,&p[i],p[i]);
    }

    //释放内存
    free(p);


    return 0;
}