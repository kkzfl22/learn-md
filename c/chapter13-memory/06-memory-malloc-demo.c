#include <stdio.h>
#include <stdlib.h>
#define TOTAL 5




int main(){
    //动态创建数组，输入5个学生的成绩，再定义一个函数检测成绩低于60分的，输出不合格的成绩。
    double *p;
    p = (double *)malloc(sizeof(double)*TOTAL);

    //对分配的内存做初始化操作
    for(int i=0;i<TOTAL;i++)
    {
        p[i]=0;
    }

    //获取用户输入的5个学生成功
   for(int i=0;i<TOTAL;i++)
    {
        printf("请输入第%d个学生的成功: \n",i);
        scanf("%lf",p+i);
    }

    //检查分数
    check(p);


    //释放内存
    free(p);

    return 0;
}


void check(double *p)
{
    printf("打印不合格的分数：\n");
    for(int i=0;i<TOTAL;i++)
    {
       if(p[i]<60.0)
       {
            printf("第%d个学生成功不合格,分数是:%.2f\n",i,p[i]);
       }
    }
}