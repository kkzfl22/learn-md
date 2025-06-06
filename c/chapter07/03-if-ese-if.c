#include <stdio.h>

int main(){
    double score = 0;
    printf("请输入分数:");
    scanf("%lf",&score);

    if(score < 60){
        printf("你的成绩不及格，没有任何奖励！");
    }
    else if(score < 80){
        printf("奖励你一个肉夹馍");
    }
    else if(score < 90 )
    {
        printf("奖励你一个ipad!");
    }
    else if(score <= 100){
        printf("奖励你一个mate60 pro");
    }
    else{
        printf("分数无效!");
    }
    return 0;
}