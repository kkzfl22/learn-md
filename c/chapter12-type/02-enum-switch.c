#include <stdio.h>

//定义枚举
enum Season{
    SPRING=1,
    SUMMER,
    AUTUMN,
    WINTER
};

int main()
{
    enum Season input_seacon;

    printf("请输入您喜欢的季节:");
    scanf("%d",&input_seacon);

    switch (input_seacon)
    {
    case SPRING :
        printf("你喜欢的季节是春天!");
        break;
    case SUMMER:
        printf("你喜欢的季度是夏天!");
        break;
    case AUTUMN:
        printf("你喜欢的季度是秋天!");
        break;
    case WINTER:
        printf("你喜欢的季节是冬天！");
        break;
    default:
        printf("输入的季度错误！");
        break;
    }

    return 0;
}