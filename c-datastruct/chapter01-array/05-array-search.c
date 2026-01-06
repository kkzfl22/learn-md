#include <stdio.h>


/**
 * 顺序查找算法
 */
int search(int array[],int size,int target)
{
    for(int i=0; i<size;i++)
    {
        if(array[i]==target){
            return i;
        }
    }

    return -1;
}

int main(){
    int array[] = {10,2,3,4,5,8,1};
    int size = sizeof(array)/sizeof(array[0]);

    printf("查找值的%d的下标为%d",3,search(&array,size,3));

    return 0;
}