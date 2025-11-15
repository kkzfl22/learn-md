#include <stdio.h>

int main()
{
    int week = 0;
    printf("请输入星期几：");
    scanf("%d", &week);

    switch (week)
    {
    case 1:
    case 2:
    case 3:
        printf("AAA");
        break;
    case 4:
    case 5:
        printf("BBB");
        break;
    case 6:
    case 7:
        printf("CCC");
        break;
    default:
        printf("输入错误");
        break;
    }

    return 0;
}
