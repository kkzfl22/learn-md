#include <stdio.h>

int main(){
    //定义数组
    int array[] = {10,20,30,40,50,60};

    //计算数组总的字节长度
    int arrayByteLen = sizeof array;

    printf("数组的总字节长度:%d\n",arrayByteLen);

    //单个长度
    int itemByteLen = sizeof array[0];

    //计算数组的长度
    int arrayLen = arrayByteLen / itemByteLen;

    printf("数组的长度:%d",arrayLen);

    return 0;
}