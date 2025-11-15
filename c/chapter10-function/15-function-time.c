#include <stdio.h>
#include <time.h> //该头文件中，声明和日期和时间相关的函数

int main(){

    time_t curr_time;
    //获取当前系统时间戳
    time(&curr_time);
    printf("当时间时间戳:%lld。 \n",curr_time);

    //字符串格式输出
    printf("字符串时间输出:%s",ctime(&curr_time));

    return 0;
}