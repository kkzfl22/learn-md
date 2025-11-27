#include <stdio.h>

/**
 * 1）小狗案例
 *（1）编写一个Dog结构体，包含name(char *)、age(int)、weight(double)属性。
 *（2）编写一个say函数，返回字符串，方法返回信息中包含所有成员值。
 *（3）在main函数中，创建Dog结构体变量，调用say函数，将调用结果打印输出。
 */
struct Dog{
    char *name;
    int age;
    double weight;
};

/**
 * 参数为结构体变量
 */
char *say(struct Dog dog)
{
    //内部修改,小狗的年龄
    dog.age = 15;
    static char printOut[64];
    sprintf(printOut,"小狗的名字：%s,小狗的年龄：%d,小狗的重量：%.3f \n",dog.name,dog.age,dog.weight);
    return printOut;
}



int main()
{
    //声明小狗
    struct Dog dog = {"小黑",3,35.25};

    //组装打印信息
    char *outPrintDog = say(dog);

    //可以观察到内部修改的值为15，
    printf("输出：%s",outPrintDog);

    
    //由于是值传递，所以修改的方法内部的dog的age,不影响原对象
    printf("小狗的的年级为:%d \n",dog.age);


    return 0;
}