#include <stdio.h>

int main(){

    char input;
    printf("输入一个字符(a,b,c,d):");
    scanf("%c",&input);

    switch (input)
    {
    case 'a':
        printf("星期一，穿衬衣!");
        break;
    case 'b':
        printf("星期二，穿短袖!");
        break;
    case 'c':
        printf("星期三, 穿T恤!");
        break;
    case 'd':
        printf("星期四，穿格式衫!");
        break;
    default:
        printf("没有相应的匹配字符!");
        break;
    }


    return 0;
}