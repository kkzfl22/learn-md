#include <stdio.h>
#include <stdbool.h>


int main(){

    bool isPASS = false;
    bool ISOK = true;

    printf("isPass=%d,isOK=%d \n",isPASS,ISOK);

    if(isPASS)
    {
        printf("Pass");
    }

    if(ISOK)
    {
        printf("OK");
    }


    return 0;
}