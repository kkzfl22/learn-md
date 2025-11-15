#include <stdio.h>

int main(){
    int start = 0;
    int end = 100;
    int sum = 0;
    do
    {
        sum += start;
        start++;
    } while (start <= end);
    
    printf("100以内所有数字的和是:%d",sum);

    return 0;
}