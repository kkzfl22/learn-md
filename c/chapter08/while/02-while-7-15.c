#include <stdio.h>

int main(){
    //（2）循环输出数字 7~15
    int start = 7;
    int end = 15;
    while(start <= end)
    {
        printf("当前数字是:%d \n",start);
        start++;
    }
    return 0;
}