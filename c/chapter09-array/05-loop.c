#include <stdio.h>

int main(){
    //定义数组
    int arr[5] = {1,2,3,4,5};

    //计算数组的长度
    int len = sizeof(arr)/sizeof(arr[0]);

    //遍历数组的元素
    printf("遍历数组中的元素:\n");
    for(int i = 0; i< len ; i++){
        printf("下标:%d,值:%d \n",i,arr[i]);
    }

    return 0;
}