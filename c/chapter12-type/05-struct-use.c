#include <stdio.h>


// //1，定义结构体
struct Student{
    int id; //学号
    char *name; //姓名
    int age;  //年龄
    int gender; //性别
    char *address; //地址
};


void print_student_info(struct Student stu){
    printf("id:%d \n",stu.id);
    printf("name:%s \n",stu.name);
    printf("age:%d \n",stu.age);
    printf("gender:%d \n",stu.gender);
    printf("address:%s",stu.address);
}

int  main(){
    struct Student stu1;

    //对成员变量能赋值操作
    stu1.id=1;
    stu1.name="张三";
    stu1.age=25;
    stu1.gender=1;
    stu1.address="上海";

    //打印信息
    print_student_info(stu1);

    return 0;
}