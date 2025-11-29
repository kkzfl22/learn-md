#include <stdio.h>

int main(){
    //使用Inter为int类型起别名，作用于int一致.
    typedef int Integer;

    Integer a,b;
    a = 10;
    b = 20;


    //为类型 unsign char 起别名 Byte
    typedef unsigned char Byte;
    Byte c = 'z';


    return 0;
}