#include <stdio.h>

//返回一个指向局部变量的指针。
int *func(){
    //将返回的类型变为static类型的，函数被销毁，而静态对象将一直存在。
    static int num = 100;
    return &num;
}

int main(){
    
    printf("\n");
    int *p =  func();
    printf("地址：%p,址:%d",p,*p);

    return 0;
}