#include <stdio.h>

int main(){
    
    //1,打写文件,w表示仅写入数据，后续做覆盖处理,a，追加模式。
    //FILE *writeFile = fopen("data.txt","w");
    FILE *writeFile = fopen("data.txt","a");

    //如果文件打开失败，则退出
    if(writeFile == NULL)
    {
        printf("文件打开失败!");
        return -1;
    }

    char putValue = 'a';
    int putResult = fputc(putValue,writeFile);

    //写入文件时出现错误
    if(putResult == EOF)
    {
        printf("写入文件时出现错误!");
    }
    else{
        printf("fputc成功写入,写入字符: %d 返回值: %d。\n",putValue,putResult);
    }

    //写入字符数组
    const char *put_text = "fputs data value: this is Test";
    int result = fputs(put_text,writeFile);

    if(result != EOF)
    {
        printf("写入")
    }



    //关闭文件
    fclose(writeFile);


    
    return 0;
}