#include <stdio.h>

//1，定义结构体
struct Student{
    int id; //学号
    char *name; //姓名
    int age;  //年龄
    int gender; //性别
    char *address; //地址
};

int  main(){
    //先定义结构体变量
    struct Student stu1 = {2,"小花",2,2,"北京"};
    //通过结构体变量访问属性
    printf("id=%d,name=%s,age=%d,gender=%d,address=%s\n",stu1.id,stu1.name,stu1.age,stu1.gender,stu1.address);
    //声明结构体指针并初始化
    struct Student *stu_point = &stu1;
    //通过指针解引用 访问
    printf("id=%d,name=%s,age=%d,gender=%d,address=%s\n",(*stu_point).id,(*stu_point).name,(*stu_point).age,(*stu_point).gender,(*stu_point).address);
    //使用结构体指针使用->访问成员
    printf("id=%d,name=%s,age=%d,gender=%d,address=%s\n",stu_point->id,stu_point->name,stu_point->age,stu_point->gender,stu_point->address);
    
    return 0;
}