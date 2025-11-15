#include <stdio.h>

int main(){
    //（5）计算100以内（包括100）所有数字的和
    int end = 100;
    int index = 0;
    int sum = 0;
    while(index <= end){
        sum+=index;
        index++;
    }

    printf("100以内所有数字和:%d",sum);


    return 0;
}