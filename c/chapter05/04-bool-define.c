#include <stdio.h>

#define BOOL int
#define TRUE  1;
#define FALSE  0

int main(){

    BOOL isPASS = FALSE;
    BOOL ISOK = TRUE;

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