#include <stdio.h>

int main(){

    for(int i = 1 ; i <= 9 ;i++){
        for(int j = 1 ; j <= i; j++ )
        {
            printf("%d*%d=%d \t",i,j,i*j);
        }
        printf("\n");
    }


    return 0;
}