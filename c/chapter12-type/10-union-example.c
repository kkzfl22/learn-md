#include <stdio.h>

union data{
    int a;
    float b;
    char c;
};

int main()
{
    union data item = {.c ='a'};

    //输出各信息
    printf("a=%d\n",item.a);
    printf("b=%f\n",item.b);
    printf("c=%c\n",item.c);

    printf("\n\n");


    item.b = 98.0;

    //输出各信息
    printf("a=%d\n",item.a);
    printf("b=%f\n",item.b);
    printf("c=%c\n",item.c);


    printf("\n\n");

    
    union data item_3 = {99};

    //输出各信息
    printf("a=%d\n",item_3.a);
    printf("b=%f\n",item_3.b);
    printf("c=%c\n",item_3.c);


    printf("\n\n");


    return  0;
}