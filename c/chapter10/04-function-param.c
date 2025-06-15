#include <stdio.h>

//在定义函数时，函数括号里的声明变量称为形式参数，称为形参
int func(int x,int y){
    return x + y;
}

int main(){
    //在调用时传递的参数，称为实参
    int sum = func(3,8);
    printf("3+8=%d\n",sum);
    return 0;
}