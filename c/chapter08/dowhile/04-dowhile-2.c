#include <stdio.h>

int main(){
    int start = 0;
    int end = 10;
    do
    {
        if(start % 2 == 0)
        {
            printf("当前偶数是:%d\n",start);
        }
        start++;
    } while (start <= end);
    return 0;
}