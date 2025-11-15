#include <stdio.h>

int main(){
    int res1 = 10%3;
    printf("%d \n",res1); //结果为1

    int res2 = -10%3;
    printf("%d \n",res2); //结果-1

    int res3 = 10%-3;
    printf("%d\n",res3); //结果为1

    int res4 = -10%-3;
    printf("%d\n",res4); //结果为-1

    return 0;
}