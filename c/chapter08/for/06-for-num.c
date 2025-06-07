#include <stdio.h>

int main(){
    int sum = 1;
    for(int i=1;i<10;i++){
        if(i%2!=0)
        {
            sum*=i;
        }
    }
    printf("10以内所有奇数乘积:%d",sum);
    return 0;
}