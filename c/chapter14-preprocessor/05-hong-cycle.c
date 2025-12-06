#include <stdio.h>

//宏定义嵌套
#define PI 3.1415926
#define S PI * y * y

int main(){
    int y = 10;
    printf("%f",S); //宏替换为printf("%f",3.1415926* y * y)
    return 0;
}