#include <stdio.h>

//函数原型,使用指针传递
void func(int *);

//函数原型，使用值传递
void fn01(int n);

int main(){
    int num = 100;
    int *ptr=&num;

    //进行普通值传递
    fn01(num);
    printf("num=%d\n",num);

    //调用函数传递指针
    func(&num);
    printf("num=%d\n",num);


     //进行普通值传递
    fn01(num);
    printf("num=%d\n",num);

    //调用函数传递指针
    func(ptr);
    printf("num=%d\n",num);
    
    return 0;
}

void func(int *p){
    //*取值后加1操作
    *p+=1;
}

void fn01(int n){
    n=n+1;
}