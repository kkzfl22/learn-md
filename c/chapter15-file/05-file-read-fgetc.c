#include <stdio.h>

int main(){

    //1,打开文件
    FILE *read_filie = fopen("data.txt","r");

    if(read_filie == NULL)
    {
        printf("文件打开失败");
        return -1;
    }

    char ch;
    //使用fgetc逐个字符的读取
    while((ch = fgetc(read_filie)) != EOF)
    {
        printf("%c",ch);
    }

    //关闭打开的文件
    fclose(read_filie);

    return 0;
}