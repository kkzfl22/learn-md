#include <stdio.h>

//定义全局变量
int num01 = 10;
int num02 = 20;

void func(int count){
    //函数中定义变量、常量、数组，具有局部作用域
    int num01 = 100;
    const double PI01 = 3.14;
    char msg01[] = "Hello Beijing";
    //使用本作用域中的变量、常量、数组
    printf("func函数:\n");
    printf("本作用域变量:count=%d,num01=%d,PI01=%.2f,msg01=%s \n ",count,num01,PI01,msg01);
    printf("全局变量:num02=%d \n",num02);
    num01 +=1;
    num02 +=1;
    printf("----------------------------\n\n");
}

int main(){
    //定义变量，作用域，仅限于主函数
    int num02 = 100000;
    //局部变量没有初始化
    int num04;
    int arr[5];

    //调用函数
    func(220);

    //主函数中使用其他作用域中的变量
    printf("main函数:\n");
    printf("num02=%d \n ",num02);
    printf("本作用域变量:num04=%d,arr[0]=%d,arr[4]=%d \n ",num04,arr[0],arr[4]);
    return 0;
}