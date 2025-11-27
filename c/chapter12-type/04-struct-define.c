#include <stdio.h>

// //1，定义结构体
// struct Student{
//     int id; //学号
//     char *name; //姓名
//     int age;  //年龄
//     int gender; //性别
//     char *address; //地址
// };

int main(){
    // //声明变量
    // struct Student stu1,stu2;

    // struct Student{
    //     int id; //学号
    //     char *name; //姓名
    //     int age;  //年龄
    //     int gender; //性别
    //     char *address; //地址
    // } stu1,stu2 ;

        //在定义结构体的同时定义结构体变量,不给出结构体的名称
    struct {
        int id; //学号
        char *name; //姓名
        int age;  //年龄
        int gender; //性别
        char *address; //地址
    } stu1,stu2 ;

    return 0;
}