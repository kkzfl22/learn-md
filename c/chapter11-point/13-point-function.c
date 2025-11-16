#include <stdio.h>
#include <string.h>

//指针函数，是个函数，返回值是指针。

//返回字符串中较长的一个
char *strLong(char *str1,char *str2){
    int str_length_1 = strlen(str1);
    int str_length_2 = strlen(str2);

    if(str_length_1 > str_length_2){
        return str1;
    }
    else{
        return str2;
    }
}



int main(){

    printf("较长的字符串;%s",strLong("beijin","shanghai"));
    printf("\n\n");

    //获取用户输入作比较打印
    //声明字符串数组时，需要固定长度。可以先预估下长度，再声明
    char msg_01[100],msg_02[100];
    //不推荐写法,此处编译器无法感知到需要定义的字符数据的长度，继而不能确定分配的空间大小。
    //char *msg_01,*msg_02

    printf("请输入第一个字符串:");
    //此处msg_01，不需要写取地址符,因为字符数组，本身就是一个指针
    //当传递msg_01时，就是首元素的地址。
    scanf("%s",msg_01);
    printf("请输入第二个字符串:");
    scanf("%s",msg_02);
    printf("较长的字符串:%s",strLong(msg_01,msg_02));

    return 0;    
}