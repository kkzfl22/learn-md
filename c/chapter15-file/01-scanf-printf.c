#include <stdio.h>
#include <stdlib.h>

int main(){
    int num1,num2;
    char ch1,ch2;
    char msg[64];

    printf("请输入第一个数字：");
    scanf("%d",&num1);

    printf("请输入第二个数字:");
    scanf("%d",&num2);


    printf("请输入第一个字符:"); 
    getchar(); // 使用此方法可以读取缓冲区中的第一个字符，从而进行匹配。
    scanf("%c",&ch1); //由于此时缓存区中还存在一个换行符，所以就直接跳过了匹配。

    printf("请输入第二个字符：");
    getchar(); //获取缓冲区中的换行符。
    scanf("%c",&ch2);

    printf("请输入字符串:");
    scanf("%s",msg); //遇到空格就结束了，即遇到空白符结束
    
    printf("num1=%d,num2=%d \n",num1,num2);
    printf("ch1=%c,ch2=%c \n",ch1,ch2);
    printf("msg=%s",msg);

    return 0;
}