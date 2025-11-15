#include <stdio.h>

int main(){
    int nums[] = {10,20,30,40,50};
    double n = 1.2;

    //创建第一个指针指向数组第一个元素
    int *ptr1 = &nums[0];
    //创建第二个指针指向数组第四个元素
    int *ptr2 = &nums[3];
    //创建第三个指针，还是指向第一个元素。
    int *ptr3 = &nums[0];
    //第四个指针指向第一个元素
    double *ptr4 = &n;

    //输出指针的地址
    printf("ptr1=%p\n",ptr1);
    printf("ptr2=%p\n",ptr2);
    printf("ptr3=%p\n",ptr3);
    printf("ptr4=%p\n",ptr4);

    //进行指针的比较运算
    printf("ptr1 > ptr2 : %d \n",ptr1 > ptr2);
    printf("ptr1 < ptr2 : %d \n",ptr1 < ptr2);
    printf("ptr1 == ptr3 : %d \n",ptr1 == ptr3);
    printf("ptr1 > ptr1 : %d \n",ptr4 > ptr1);


    return 0;
}