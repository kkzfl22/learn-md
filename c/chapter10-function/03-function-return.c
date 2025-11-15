#include <stdio.h>

//无返回值
void fun01(){
    printf("fun01\n");
}
//有明确的返回值
double fun02(){
    return 3.14;
}
//函数返回一个不确定的值
int fun03(){
    10 + 20;
}
//返回类型与return类型不一致，可能造成精度丢失
int fun04(){
    return 20.89;
}

int main(){
    fun01();
    printf("%f \n",fun02());
    printf("%d \n",fun03());
    printf("%d \n",fun04());
    return 0;
}