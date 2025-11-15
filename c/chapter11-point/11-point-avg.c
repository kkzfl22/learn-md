#include <stdio.h>


int get_avg(int *,int);

int main(){

    //1. 定义一个函数，接收数组名或首元素指针 和 数组长度 作为参数，计算数组的平均数
   // 函数原型： int get_avg(int *, int);

   int nums[5] = {10,20,30,40,50};
   int length = 5;

   int avgOut = get_avg(&nums[0],length);
   int avgOut2 = get_avg(nums,length);

   printf("平均值结果是%d\n",avgOut);
   printf("平均值结果是%d\n",avgOut2);
    return 0;
}

int get_avg(int *num_prt,int length)
{
    //读取首个值
    // int sum = *num_prt;
    // for(int i=1;i<length ;i++)
    // {
    //     num_prt++;
    //     sum += *num_prt;
    // }

    int sum = 0;
    //num_prt属于显示定义指向首个元素的指针。类型是int *
    //指向首元素的指针也可以通过[下标]来访问数组元素。
    for(int i=0;i<length;i++){
        sum+=num_prt[i];
    }

    return sum / length;
}
