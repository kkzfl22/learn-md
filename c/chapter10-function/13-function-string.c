#include <stdio.h>
#include <string.h>

int main()
{
    //获取字段串的长度，不包括结束符
    char msg[] = "hello word";
    printf("strlen=%d, \n",strlen(msg));

    //将msg字符拷贝到msg2中，进行覆盖
    char msg2[100];
    strcpy(msg2,msg);    
    printf("msg2=%s \n",msg2);

    //将msg的字符追加到msg3中
    char msg3[50] = "123";
    strcat(msg3,msg);
    printf("msg3=%s \n",msg3);


    return 0;
}