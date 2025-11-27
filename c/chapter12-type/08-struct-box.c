#include <stdio.h>

/**
 * （1）编程创建一个Box结构体，在其中定义三个成员表示一个长方体的长、宽和高，长宽高可以通过控制台输入。
 *（1）定义一个函数获取长方体的体积（volume）。
 *（2）创建一个结构体指针，打印给定尺寸的长方体的体积。
 */
struct Box{
    double length;
    double width;
    double height;
};

double volume(struct Box *box){
    return box->length * box->width * box->height;
}


int main()
{
    struct Box box;
    //创建结构体指针
    struct Box *p_box = &box;

    //获取用户输入的长方体的、长宽高
    printf("请输入长度:");
    scanf("%lf",&(p_box->length));

    printf("请输入宽度:");
    scanf("%lf",&(p_box->width));


    printf("请输入高度:");
    scanf("%lf",&(p_box->height));

    //计算体积
    double result = volume(p_box);

    printf("体积为:%.2f",result);

    return 0;
}