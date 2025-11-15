#include <stdio.h>

int main(){

    for(int i = 9 ; i > 0 ;i--){
        for(int j = 1 ; j <= i; j++ )
        {
            printf("%d*%d=%d \t",i,j,i*j);
        }
        printf("\n");
    }


    return 0;
}