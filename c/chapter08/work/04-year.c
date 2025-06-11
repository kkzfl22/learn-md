#include <stdio.h>
#include <stdbool.h>

int main()
{
    int year, month;

    printf("请输入年份：");
    scanf("%d", &year);

    printf("请输入月份: ");
    scanf("%d", &month);

    bool isRun = false;

    // 判断是否为闰年
    if (year % 400 == 0 || (year % 4 == 0))
    {
        printf("%d年为闰年", year);
        isRun = true;
    }
    else
    {
        printf("%d年非闰年", year);
    }
    printf("\n");

    switch (month)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        printf("%d月有31天",month);
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        printf("%d月有30天",month);
        break;
    case 2:
        if(isRun)
        {
            printf("2月为29天");
        }
        else{
            printf("2月为28天");
        }
        break;
    default:
        break;
    }

    return 0;
}