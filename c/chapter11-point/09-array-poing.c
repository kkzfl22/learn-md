#include <stdio.h>

int main(){
    //数组
    int arr[5]={10,20,30,40,50};

    //创建指针指向数组，&arr表示整个数组的地址
    int (*ptr)[5]=&arr;

    //二者的值是相同的
    printf("arr=%p \n",arr);
    printf("ptr=%p \n",ptr);

    //数组指针指向的是整个数组的地址，而不是第一个元素的地址。
    //数组指针+1向后移动4*5=20个字节；数组+1会向后移动4个字节。
    printf("arr+1=%p \n",arr+1);
    printf("ptr+1=%p \n",ptr+1);

    //使用数组指针遍历数组
    for(int i=0;i<5;i++){
        printf("%d\n",(*ptr)[i]);
    }
    
    return 0;
}