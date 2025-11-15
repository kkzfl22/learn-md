#include <stdio.h>

int main(){
    int a = 10;
    int b = 99;
    int res = a>b?a++:b--;
    int n2 = a>b?1.1:2.2;

    printf("a=%d\n",a);
    printf("b=%d\n",b);
    printf("res=%d\n",res);
    printf("n2=%d\n",n2);
    return 0;
}