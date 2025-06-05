#include <stdio.h>

int main(){
    int a = 10;
    int b = 100;
    int c = 199;

    int max = (a>b?a:b)>c?(a>b?a:b):c;
    printf("a、b、c中最大的数字:%d",max);
    return 0;
}