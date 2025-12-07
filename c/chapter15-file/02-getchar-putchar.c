#include <stdio.h>

int main(){
    int c;

    printf("enter a value:");
    c = getchar(); //字符的ASCII码值

    printf("\n you entered:");
    putchar(c); //写入也是字符的ASCII码值,向输出流中写入。
    printf("\n");

    return 0;
}