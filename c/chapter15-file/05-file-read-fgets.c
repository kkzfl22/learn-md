#include <stdio.h>

int main(){

    //1,打开文件
    FILE *read_filie = fopen("data.txt","r");

    if(read_filie == NULL)
    {
        printf("文件打开失败");
        return -1;
    }

    char buffer[5];
    //使用fgetc逐个字符的读取
    while((fgets(buffer,sizeof(buffer),read_filie)) != NULL)
    {
        //fputs(buffer, stdout);
        printf("%s",buffer);
    }

    //关闭打开的文件
    fclose(read_filie);

    return 0;
}