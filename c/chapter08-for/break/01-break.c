#include <stdio.h>
#include <stdbool.h>


int main(){
    
    //要求输入一个数字，判断该数字是否是质数。
    int num;

    printf("请输入一个数字:");
    scanf("%d",&num);

    //标识当前是否为质数
    bool isPrime = num > 1 ? 1 : 0;


    for(int i = 2; i<num/2;i++)
    {
        if(num % i == 0)
        {
            isPrime = 0;
            break;
        }
    }


    if(isPrime)
    {
        printf("%d是质数！",num);
    }
    else
    {
        printf("%d 不是质数!",num);
    }


    return 0;
}