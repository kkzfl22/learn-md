#include <stdio.h>

int main(){
    //计算数据类型的大小，必须使用括号将数据类型包裹起来。
    printf("char %zu \n",sizeof(char)); //char 1
    printf("short %zu \n",sizeof(short)); //short 2
    printf("int %zu \n",sizeof(int));     //int 4
    printf("long %zu \n",sizeof(long));  //long 4
    printf("long long : %zu \n",sizeof(long long)); //long long 8
    printf("float %zu \n",sizeof(float));  //float 4
    printf("double %zu \n",sizeof(double)); //double 8
    printf("long double %zu \n",sizeof(long double)); //long double 16
    printf("char %zu \n",sizeof(char));
    printf("\n");

    //计算字面量数据大小，可以省略括号
    printf("%zu \n",sizeof('a')); //使用Int类型，大小为4
    printf("%zu \n",sizeof(431));   //int 4
    printf("%zu \n",sizeof 4.31); //double 8
    printf("\n");

    //计算变量的大小，变量可以省略括号
    char a = 'A';
    int b = 90;
    long long c = 100;
    double d = 10.8;
    printf("a=%zu \n",sizeof(a));  // char 1
    printf("b=%zu \n",sizeof(b));  // int 4
    printf("c=%zu \n",sizeof(c));  // long long 8
    printf("d=%zu \n",sizeof(d));  // double 8
    printf("\n");

    return 0;
}