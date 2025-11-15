#include <stdio.h>

int main(){
    int a = 10;
    int b = 100;
    int max = a > b ? a: b;
    printf("a和b中最大的数字:%d",max);
    return 0;
}