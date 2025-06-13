#include <stdio.h>

int main()
{
    int arr[10] = {10,9,8,7,6,1,2,3,4,5};

    int min = arr[0];
    int max = arr[0];
    
    int len = sizeof arr / sizeof arr[0];

    for(int i = 1; i < len ; i++){
        //求最小值
        if(min > arr[i]){
            min = arr[i];
        }
        //求最大值
        if(max < arr[i]){
            max = arr[i];
        }    
    }

    printf("最大值：%d,最小值：%d",max,min);

    return 0;
}