#include <stdio.h>


//定义枚举
enum Weekday{
    SUNDAY=0,
    MONDAY=1,
    TUSEDAY=2,
    WEDNESDAY=3,
    THURDAY=4,
    FRIDAY=5,
    SATURDAY=6
};

// 定义枚举类型，表示一月到十二月。 给枚举元素JANUARY设置了值1，后面的枚举元素从1开始自增，分别是 1到12
enum Month 
{
    JANUARY = 1,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER
};

//定义四季的温度
enum Season{
    SPRING=18,
    SUMMER=38,
    AUTUMN=19,
    WINTER=-1
} season;

int main(){
    //遍历打印枚举数据
    for(enum Weekday day=SUNDAY;day <SATURDAY;day++ )
    {
        printf("枚举：%d, ",day);
    }
    printf("\n");
    printf("\n");

    //遍历打印枚举数据
    for(enum Month month=JANUARY;month < DECEMBER;month++)
    {
        printf("月份：%d, ",month);
    }
    printf("\n");
    printf("\n");

    // 如果枚举常量的值并不是连续的数字，则无法遍历
    for(enum Season season=SPRING;season <= WINTER;season++)
    {
        printf("季节：%d, ",season);
    }

    printf("\n");
    printf("\n");

    return 0;
}