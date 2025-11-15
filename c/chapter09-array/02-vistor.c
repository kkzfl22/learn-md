#include <stdio.h>

int main(){
    int nums[4]= {10,20,30,40};

    //修改元素的值
    nums[1]+=50;

    //读取元素
    printf("第1个元素的值%d\n",nums[0]);
    printf("第2个元素的值%d\n",nums[1]);
    printf("第3个元素的值%d\n",nums[2]);
    printf("第5个元素的值%d\n",nums[3]);
    return 0;
}
