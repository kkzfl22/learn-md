#include <stdio.h>

// 全局变量
double money = 1.1;
// 全局常量
const double PI = 3.1415926;
// 全避数组
char msg[] = "Hello World";

// 全局函数
void func()
{
    printf("func 函数中使用全局数组:\n");
    printf("money=%.2f \n", money);
    printf("PI=%.2f \n", PI);
    printf("msg=%s \n", msg);
    printf("\n");
    money += 100; // 修改全局变量的值
}

int main()
{
    // 调用func函数
    func();

    printf("func 函数中使用全局数组:\n");
    printf("money=%.2f \n", money);
    printf("PI=%.2f \n", PI);
    printf("msg=%s \n", msg);
    printf("\n");

    // 调用func函数
    func();

    return 0;
}