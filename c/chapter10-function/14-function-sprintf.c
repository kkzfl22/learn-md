#include <stdio.h>

int main(){

    //-- sprintf的使用
    int age = 18;
    double score = 65.5;
    char name[] = "null null";

    //用于存储格式化后的字符串
    char outMsg[100];

    sprintf(outMsg,"我叫%s,今年%d,成绩%.2f分",name,age,score);

    //格式化后输出
    printf("%s ;\n",outMsg);

    //--sscanf 的使用
    char out[] = "张三成绩:55,李四成绩:65,王二麻子成绩:88";
    float score1,score2,score3;

    //使用sscanf从字符串中提取数据
    sscanf(out,"张三成绩:%f,李四成绩:%f,王二麻子成绩:%f",&score1,&score2,&score3);

    //输出信息
    printf("score1=%f,score2=%f,score3=%f",score1,score2,score3);


    return 0;
}