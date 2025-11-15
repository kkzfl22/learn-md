#include <stdio.h>

int main(){
    //指针加减操作
    int num[] = {10,20,30,40,50};

    //创建指针并指向第一个元素
    int *num_p1 = &num[0];

    //指针指针的值和指向的值
    printf("指针的值:%p,指向的值:%d.\n",num_p1,*num_p1);

    //指向加2
    num_p1+=2;
    printf("指针的值:%p,指向的值:%d.\n",num_p1,*num_p1);

    num_p1 -= 1;
    printf("指针的值:%p,指向的值:%d.\n",num_p1,*num_p1);

    return 0;
}