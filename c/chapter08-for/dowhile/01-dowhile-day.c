#include <stdio.h>

int main(){
    int start = 0;
    int end = 10;
    do{
        printf("第%d天我吃%d个饺子。\n",start,start);
        start++;
    } while(start <= end);

    printf("结束后start的值:%d",start);

    return 0;
}