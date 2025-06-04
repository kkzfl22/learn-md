#include <stdio.h>

int main(){
    //char类型字符量需要使用单引号包裹
    char c1 = 'a';
    char c2 = '9';
    char c3 = '\t';
    printf("c1=%c,c2=%c,c3=%c| \n",c1,c2,c3);

    //char的本质是整数，可以进行运算。
    char b1 = 'b';
    char b2 = 102;
    printf("%c->%d \n",b1,b1);
    printf("%c->%d \n",b2,b2);
    printf("%c+%c=%d \n",b1,b2,b1+b2);

    //char类型的范围
    unsigned char c11 = 200; //无符号char的取值范围：0~255;
    signed char c12 = 200; //有符号的取值范围-128~127,此会超出范围
    char c13 = 200; //当前系统char的默认是signed char
    printf("c12=%d,c12=%d,c13=%d, \n",c11,c12,c13);

    return 0;
}