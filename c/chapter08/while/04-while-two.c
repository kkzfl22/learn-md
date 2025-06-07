#include <stdio.h>

int main(){
    //（4）输出10（包括10）以内所有的偶数
    int num = 10;
    while(num >= 0){
        if(num % 2 == 0)
        {
            printf("偶数:%d \n",num);
        }
        
        num--;
    }
    return 0;
}