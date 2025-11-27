#include <stdio.h>
#define TOTAL 2

// 学生信息包括姓名、编号、性别、职业、 分数，
// 教师的信息包括姓名、编号、性别、职业、教学科目
enum gender
{
    girl = 1,
    man = 2
};

enum input_type
{
    student = 's',
    teacher = 't'
};

// 共同体分数或者科目
union score_or_course
{
    float score;
    char course[32];
};

struct people
{
    char name[32];            // 名称
    int seq;                  // 编号
    enum gender sex;          // 性别
    enum input_type profession;     // 职业
    union score_or_course sc; // 共同体分数或者科目
};

int main()
{
    // 定义一个结构体数组
    struct people people_array[TOTAL];

    // 输入人员信息
    for (int i = 0; i < TOTAL; i++)
    {
        printf("请输入人员信息: 姓名 编号 性别(1/2) 职业(s/t)");
        scanf("%s %d %c %c",
              people_array[i].name,
              &(people_array[i].seq),
              (char *)&(people_array[i].sex),
              (char *)&(people_array[i].profession));

        printf("打印信息3：%s %d %c %c \n \n",
               people_array[i].name,
               people_array[i].seq,
               people_array[i].sex,
               people_array[i].profession);

        // 检查当前输入的类型，按类型填充工同体
        // 如果当前为老师，则输入教学科目
        if ((char)people_array[i].profession == teacher)
        {
            printf("请输入教学科目:");
            scanf("%s", &(people_array[i].sc.course));
        }
        else if ((char)people_array[i].profession == student)
        {
            printf("请输入学生成绩:");
            scanf("%f", &(people_array[i].sc.score));
        }
        printf("\n");

        // 刷新
        fflush(stdin);
    }

    // 输出人员信息
    printf("姓名\t编号\t性别\t职业\t科目/分数\n");
    for (int i = 0; i < TOTAL; i++)
    {
        // 如果当前为老师
        if (people_array[i].profession == teacher)
        {
            printf("%s %d %c %c %s \n",
                   people_array[i].name,
                   people_array[i].seq,
                   people_array[i].sex,
                   people_array[i].profession,
                   people_array[i].sc.course);
        }
        // 如果当前为学生
        else if (people_array[i].profession == student)
        {
            printf("%s %d %c %c %f \n",
                   people_array[i].name,
                   people_array[i].seq,
                   people_array[i].sex,
                   people_array[i].profession,
                   people_array[i].sc.score);
        }
    }

    return 0;
}