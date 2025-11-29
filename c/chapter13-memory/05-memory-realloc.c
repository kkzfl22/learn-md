#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>

int main(){
    //声明指针
    int *p;
    
    //首次分配内存
    p = (int *)malloc(sizeof(int)*10);
    printf("指针的地址:%p,内存大小:%d\n",p,_msize(p));

    //调整内存大小
    p = (int *)realloc(p,sizeof(int)*100);
    printf("指针的地址:%p,内存大小:%d\n",p,_msize(p));

    //再调整内存大小
    p = (int *)realloc(p,sizeof(int)*220);
    printf("指针的地址:%p,内存大小:%d\n",p,_msize(p));

    
    //释放内存
    free(p);


    return 0;
}