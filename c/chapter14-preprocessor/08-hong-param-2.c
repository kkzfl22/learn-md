#include <stdio.h>


//定义宏，计算某数的平方，尽量在替换方本时用（）把形参数括起来
#define SQ1(n) n * n
#define SQ2(n) (n) * (n)


int main(){
    int a = 10;
    printf("nums的平方:%d\n",SQ1(a+1)); //执行宏替换为a+1*a+1
    printf("nums的平方:%d\n",SQ2(a+1)); //执行宏替换为(a+1)* (a+1)

    return 0;
}