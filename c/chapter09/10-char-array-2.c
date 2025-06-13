#include <stdio.h>

int main(){
    //定义字符串
    char chars1[]={"I am happy"};//后面自动添加\0
    char chars2[]="I am happy";//省略{}号，后面自动添加\0

    printf("\n str1=%s",chars1);
    printf("\n str2=%s",chars2);


    return 0;
}