#include <stdio.h>

void fn()
{
    int num1 = 1;
    int a;
    printf("1,num1=%d,a=%d,\n",num1,a);
    num1++;
    printf("2,num1=%d,a=%d,\n",num1,a);
}

void fn_static()
{
    static int num1 = 1;
    static int a;
    printf("static 1,num1=%d,a=%d,\n",num1,a);
    num1++;
    printf("static 2,num1=%d,a=%d,\n",num1,a);
}

int main(){
    fn();
    fn_static();
    printf("------------\n");
    fn();
    fn_static();
}