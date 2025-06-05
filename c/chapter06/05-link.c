#include <stdio.h>

int main(){
    int a = 8;
    int b = 7;
    printf("a>b的值:%d\n",a>b);  //true 1
    printf("a>=b的值:%d\n",a>=b); //true 1
    printf("a<b的值:%d\n",a <b); // false 0
    printf("a<=b的值: %d\n",a<b); // false 0
    printf("a==b的值:%d\n",a==b); // false 0
    printf("a!=b的值:%d\n",a!=b); // true 1
    return 0;
}