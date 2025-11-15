#include <stdio.h>

int main(){
    int sum = 0;
    for(int i=0;i<=100;i++)
    {
        sum+=i;
    }
    printf("100以内的数字和是:%d",sum);
    return 0;
}