#include <stdio.h>

// 给字符指针取别名
typedef char *String;

//给结构体取别名
typedef struct {
    int id;
    String name;
    String profile;
} User;


int main(){
    //声明一个User变量
    User u;

    //初始化结构体变量赋值
    u.id = 101;
    u.name="小丽";
    u.profile="不积小流无以成江海";

    //打印结构体变量的属性
    printf("id=%d\n",u.id);
    printf("name=%s\n",u.name);
    printf("profile=%s\n",u.profile);
    

    return 0;
}