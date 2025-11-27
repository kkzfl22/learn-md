#include <stdio.h>
#include <string.h>

/**
 * 3）景区门票案例
 *（1）一个景区根据游人的年龄收取不同价格的门票。
 *（2）请编写游人结构体（Visitor），根据年龄段决定能够购买的门票价格并输出。
 *（3）规则：年龄>18，门票为20元，其它情况免费。
 *（4）可以循环从控制台输入名字和年龄，打印门票收费情况，如果名字输入n，则退出程序。
 */
struct Visitor
{
    char name[16];
    int age;
    int money;
};

void count_money(struct Visitor *vis)
{
    if (vis->age > 18)
    {
        vis->money = 20;
    }
    else
    {
        vis->money = 0;
    }
}

int main()
{

    while (1)
    {

        // 定义结构和结构指针
        struct Visitor vis;
        struct Visitor *p_vis = &vis;

        // 接收用户的输入
        printf("请输入名称:(输入n退出)");
        scanf("%s", &(p_vis->name));

        if (strcmp("n", p_vis->name) == 0)
        {
            break;
        }

        printf("请输入年龄:");
        scanf("%d", &(p_vis->age));

        // 计算门票价格
        count_money(p_vis);
        printf("门票的价格为:%d \n", p_vis->money);

        printf("计算下一位：\n\n");
    }

    return 0;
}