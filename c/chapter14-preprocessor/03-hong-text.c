#include <stdio.h>

#define M (n*n +3*n)
#define PRINTF_SUM printf("sum=%d",sum)

int main(){
    int n = 10;
    int sum = 3*M + 4*M + 5*M; //宏展开3*(n*n +3*n) + 4* (n*n +3*n) + 5* (n*n +3*n)
    
    PRINTF_SUM; //宏展开 printf("sum=%d",sum)

    return 0;
}