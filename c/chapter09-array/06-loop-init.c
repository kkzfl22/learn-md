#include <stdio.h>

int main(){
    //声明数组
    int arr[10];

    //计算数组的长度
    int len = sizeof(arr) / sizeof(arr[0]);

    //进行初始化赋值
    for(int i = 0; i< len ; i++){
        arr[i] = i+10;
    }

    //打印数组中的值
    for(int i = 0; i< len ; i++){
        printf("下标:%d->值:%d\n",i,arr[i]);
    }
    return 0;
}