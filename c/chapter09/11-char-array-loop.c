#include <stdio.h>

int main()
{
    // 定义字符串
    char chars[] = "Hello";

    // 计算字符串长度
    int len = sizeof(chars) / sizeof(chars[0]);

    printf("%s \n", chars);
    printf("数组的长度:%d \n", len);
    printf("第三个元素是:%c \n", chars[2]);
    printf("\n");

    // 遍历字符串
    for (int i = 0; i < len; i++)
    {
        printf("%c \n",chars[i]);
    }

    return 0;
}