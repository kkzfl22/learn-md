#include <stdio.h>

int main(){
    //（3）倒序输出数字 56 ~ 43
    int end = 56;
    int start = 43;
    while(end >= start)
    {
        printf("倒序数字:%d \n",end);
        end--;
    }
    return 0;
}