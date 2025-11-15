#include <stdio.h>

int main()
{
    // 该代码块中具有块级作用域
    {
        // 块级变量
        int a = 20;
        // 块级常量
        const double PI = 3.14;

        printf("a*PI=%f \n", a * PI);
    }

    //分支语句具有块级作用域
    if(1){
        //局部数组
        int nums[] = {10,20,30};
        printf("%d %d %d \n",nums[0],nums[1],nums[2]);
    }

    //循环语句中的标识变量i是块级作用域
    for(int i = 0;i<5;i++){
        printf("%d ",i);
    }

    return 0;
}