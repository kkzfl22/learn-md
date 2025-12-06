#include <stdio.h>

#define PI 3.1415926


int main(){
    printf("PI=%f",PI);
    return 0;
}

//取消宏定义
#undef PI

void func(){
    //printf("PI=%f",PI); //发生编译报错，这里不能找到PI
}