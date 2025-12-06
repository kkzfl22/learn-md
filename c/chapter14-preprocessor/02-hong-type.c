#include <stdio.h>

//宏定义数据类型
#define BOOL int
#define TRUE 1
#define FALSE 0
//上面代码中使用宏定义声明了BOOL、TURE、FALSE，后面代码中出现BOOL会替换成int，出现TRUE会替换成1，出现FALSE替换成0。

int main(){

    //使用int类型表示两种 状态 
    // int isPass = 0;
    // int isOK = 1;

    //借助于宏定义
    BOOL isPass = FALSE;
    BOOL isOK = TRUE;

    if(isPass){
        printf("Pass");
    }

    if(isOK){
        printf("ok");
    }


    return 0;
}