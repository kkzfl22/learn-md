#include <stdio.h>

int main(){

    //1,创建数组
    int nums[] = {10,20,30,40,50};
    //创建指针，指向数组的第一个元素
    int *prt_array1 = &nums[0];


    //数组名中存储了一第一个元素的地址
    printf("数组名的元素地址和值,%p,%d\n",nums,*nums);
    printf("第一个元素的地址和值,%p,%d\n",prt_array1,*prt_array1);

    //进行比较是否为一致
    if(nums == prt_array1)
    {
        printf("数组与第一个元素的指针相等\n");
    }
    else{
        printf("数组与第一个元素的指针不相等\n");
    }


    //数组名与指针的真正差别
    //sizeof数组名运算符返回的是整个数组的大小，sizeof指针返回的是指针本身的大小。
    printf("%zu, %zu \n",sizeof nums,sizeof prt_array1);

    //2, 数组名不能自增、自减运算
    //nums++; 报错
    prt_array1++;
    printf("自增加后的元素:%p,%d \n",prt_array1,*prt_array1);

    //3,数组名的指向不能修改。
    int n = 100;
    //nums=&n; 报错，指向不能修改。
    prt_array1 = &n;
    printf("修改指针的地址后：%p,%d\n",prt_array1,*prt_array1);


    return 0;
}