#include <stdio.h>

int main(){
    
    int start = 1;
    int end = 10;

    int sum = 1;
    while(start < end)
    {
        if(start % 2 != 0)
        {
            sum *= start;
        }

        start++;
    }

    printf("10以内的奇数乘积是:%d",sum);

    return 0;
}