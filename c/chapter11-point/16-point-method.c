#include <stdio.h>

int max(int a,int b)
{
    return a>b?a:b;
}

int main(){
    int a = 10,b=5,max_val1,max_val2;
    //1，定义函数指针。
    //int (*pmax)(int a,int b) = max;
    //2, 参数名称可以改
    //int (*pmax)(int x,int y)=max;
    //3, 参数名称可以不写
    int (*pmax)(int,int)=max;

    //调用函数
    //1,解引用之后调用
    max_val1 = (*pmax)(a,b);
    //不解引用,直接调用
    max_val2 = pmax(a,b);

    //观察pmax指针函数与max函数
    printf("( 解引用)函数指针：%p-->%d\n",*pmax,max_val1);
    printf("(不解引用)函数指针：%p-->%d\n",pmax,max_val2);
    printf("( 解引用)直接调用: %p-->%d\n",*max,(*max)(a,b));
    printf("(不解引用)直接调用: %p-->%d",max,max(a,b));
    printf("\n");
    printf("\n");

    printf("函数地址：%p,解引用后地址:%p\n",pmax,*pmax);
    printf("函数地址：%p,解引用后地址:%p\n",max,*max);



    return 0;
}