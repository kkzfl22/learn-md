#include <stdio.h>

int main(){
    int i1 = 10,i2=20;
    int i = i1++;
    printf("%d\n",i); //i返回10
    printf("%d\n",i1); //i1返回11

    i = ++i1;
    printf("%d\n",i); //返回12
    printf("%d\n",i1); //12

    i=i2--;
    printf("%d\n",i); //20
    printf("%d\n",i2); //19

    i = --i2;
    printf("%d\n",i); //18
    printf("%d\n",i2); //18


    return 0;
}