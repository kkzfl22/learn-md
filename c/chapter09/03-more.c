#include <stdio.h>

int main(){
    //定义数组
    int array[] = {10,20,30};
    
    printf("下标为0的元素:%d\n",array[0]); //10
    printf("下标为2的元素:%d\n",array[2]); //30
    printf("下标为-1的元素:%d\n",array[-1]); //得到不确定的结果
    printf("下标为5的元素:%d\n",array[5]); //得到不确定的结果


    return 0;
}