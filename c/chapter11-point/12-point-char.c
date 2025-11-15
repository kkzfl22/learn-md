#include <stdio.h>
#include <string.h>

int char_count(char *, char);

int main(){

    //2. 定义函数，接收一共字符串和字符，统计字符在字符串中出现的次数
    //   函数原型:  int char_count(char *, char)   
    char *src="hello word !!!;";
    int find_num = char_count(src,'!');
    printf("查找的字符串一共找到%d次",find_num);
    return 0;
}


int char_count(char *src, char item){
    //1,计算得到字符数组的长度
    int len = strlen(src);
    char char_array[len];
    strcpy(char_array,src);

    char *char_first_point = &char_array[0];

    int find_num = 0;
    for(int i=0;i<len;i++)
    {
        char charValue = *char_first_point;
        if(charValue == item)
        {
            find_num++;
        }
        char_first_point++;
    }
    return find_num;
}