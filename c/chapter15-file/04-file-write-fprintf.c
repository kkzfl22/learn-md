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

    //写入字符数组
    char *datavalueout = "这是一段内容占位符";
    const char *put_text = "\n fprintf data value: this is Test data out , %s";
    int result = fprintf(writeFile,put_text,datavalueout);

    if(result != EOF)
    {
        printf("fprintf写入成功,返回值: %d",result);
    }

    //关闭文件
    fclose(writeFile);
    
    return 0;
}