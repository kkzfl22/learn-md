#include <stdio.h>

int main(){
    //显示的设置\0，表示字符串已经结束
    char str1[12] = {'H','e','l','l','0',' ','W','o','r','l','d','\0'};
    char str2[4] = {'t','o','m'};
    char str3[] = {'j','a','c','k'};

    printf("str1=%s \n",str1); 
    printf("str2=%s \n",str2); 
    printf("str3=%s \n",str3); //由于没有显示的结束标识，会包括相邻内存数组，直接遇到结束标识！
    return 0;
}