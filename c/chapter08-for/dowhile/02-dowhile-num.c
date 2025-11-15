#include <stdio.h>

int main(){
    int start = 7;
    int end = 15;
    do{
        printf("输出当前数字:%d\n",start);
        start++;
    }while(start <= end);
    return 0;
}