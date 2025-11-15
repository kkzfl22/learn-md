#include <stdio.h>

int main(){
    //定义数组
    int arr[10]={1,2,3,4,5,6,7,8,9,10};

    int len = sizeof(arr) / sizeof arr[0];

    int sum;

    for(int i = 0; i< len ; i++){
        sum += arr[i];
    }
    printf("所有元素的和为:%d \n",sum);


    //计算平均数
    int avg = sum / len;
    printf("平均数为：%d",avg);


    return 0;
}