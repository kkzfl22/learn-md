#include <stdio.h>

int main(){

    //1,打开文件
    FILE *read_filie = fopen("data.txt","r");

    if(read_filie == NULL)
    {
        printf("文件打开失败");
        return -1;
    }

    char name[20],age[4],address[32];

    fscanf(read_filie,"%s %s %s",name,age,address);

    printf("姓名:%s,年龄:%s,地址：%s",name,age,address);

    //关闭打开的文件
    fclose(read_filie);

    return 0;
}