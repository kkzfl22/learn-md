#include <stdio.h>

int main(){

    /**
     * 创建一个int类型的变量，使用取址运算符取出其地址，并将其地址赋值给一个指针，
     * 然后分别打印变量的值、变量的地址、指针的值、指针的地址、指针指向的值。  
     */
    //定义变量
    int num1 = 123;
    //使用取址运算符取出地址，并赋值给一个int指针
    int *num_point = &num1;

    //，1打印变量的值和地址
    printf("变量num1的值:%d,\n",num1);
    printf("变量num1的内存地址：%p \n",&num1);

    //打印指针的值和指针的地址及指针指向的值
    printf("指针的值:%p,\n",num_point);
    printf("指针的地址：%p, \n",&num_point);
    printf("指针指向的值:%d,\n",*num_point);


    return  0;
}