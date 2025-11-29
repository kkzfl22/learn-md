#include <stdio.h>
#include <stdlib.h>

int main(){

    //分配一个int类型4字节大小的内存
    int *p;
    p = (int *)malloc(sizeof(int));

    //判断分配是否成功
    if(p == NULL)
    {
        printf("内存分配失败");
        return -1;
    }

    //如果分配成功，则可以直接使用
    *p = 120;

    printf("p指向的内存地址：%p,值为：%d",p,*p);

    //释放内存
    free(p);


    return 0;
}