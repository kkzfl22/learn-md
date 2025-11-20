#include <stdio.h>
#include <string.h>

/**
 * 1. 定义函数，返回某个字符一个次出现在某个字符中的位置
 * 2. 定义函数，返回某个字符最后一次出在在某个字符中的位置 
 * 3. 定义函数，将一个字符数组中的小写字符转大写
 * 4. 定义函数。将一个字符数组中的大写字符转小大 
 */

 /**
  * @brief 定义函数，返回某个字符一个次出现在某个字符中的位置
  * 
  * @param src 定义的字符
  * @param vs 查询的字符
  * @return int 字符第一次出现的位置
  * 
  */
int find_first_index(char *src,char vs)
{
    int find_index = -1;

    int leng = strlen(src);
    for(int i=0;i<leng;i++)
    {
        if(src[i] == vs)
        {
            find_index= i;
            break;
        }
    }
    return find_index;
}



 /**
  * @brief 定义函数，返回某个字符最后一次出在在某个字符中的位置 
  * 
  * @param src 定义的字符
  * @param vs 查询的字符
  * @return int 字符最后一次次出现的位置
  * 
  */
int find_last_index(char *src,char vs)
{
    int find_index = -1;

    int leng = strlen(src);
    for(int i=leng;i>=0;i--)
    {
        if(src[i] == vs)
        {
            find_index= i;
            break;
        }
    }
    return find_index;
}


 /**
  * @brief 定义函数，将一个字符数组中的小写字符转大写
  * 
  * @param src 定义的字符
  * @return char * 大写的字符
  * 
  */
void upper_case(char *src,char *target)
{
    int leng = strlen(src);

    for(int i=0;i<leng;i++)
    {
        if(src[i]>=97 && src[i]<= 122)
        {
            target[i]=src[i]-32;
        }
        else{
            target[i]=src[i];
        }
    }
}


 /**
  * @brief 定义函数，将一个字符数组中的大写字符转小写
  * 
  * @param src 定义的字符
  * @return char * 小写的字符
  * 
  */
void lower_case(char *src,char *target)
{
    int leng = strlen(src);

    for(int i=0;i<leng;i++)
    {
        if(src[i]>=65 && src[i]<= 90)
        {
            target[i]=src[i]+32;
        }
        else{
            target[i]=src[i];
        }
    }
}


int main()
{   
    printf("第一次字符出现的位置:%d\n",find_first_index("hello workd!",'k'));
    printf("最后一次字符出现的位置:%d\n",find_last_index("hello world!",'o'));

    char target[15];
    upper_case("Hello Word!!",target);
    printf("小写转大写:%s\n",target);
    char out[15];
    lower_case(target,out);
    printf("大写转小写:%s\n",out);


    return 0;
}