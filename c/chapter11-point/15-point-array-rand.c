#include <stdio.h>
#include <stdlib.h>

int *rand_array_10()
{
    static int int_array[10];  // 必须加上static,让array在静态区域上分配。

    for(int i=0;i<10;i++)
    {
        int_array[i]=rand();
    }
    return int_array;
}

int main(){
    //编写一个函数，它会生成10个随机数，并使用数组名作为返回值。
    //说明：生成随机数可以使用 rand() 函数，由C语言标准库 <stdlib.h> 提供，可以返回一个随机整数。
    int *array_num=rand_array_10();

    //打印随机数据的信息
    for(int i=0;i<10;i++)
    {
        //直接使用数据地址操作
        //printf("地址：%p-->%d\n",&(array_num[i]),array_num[i]);
        //使用数组名进行地址遍历取值
        printf("地址：%p-->%d\n",array_num,*(array_num));
        array_num=array_num+1;
    }
    return 0;

}