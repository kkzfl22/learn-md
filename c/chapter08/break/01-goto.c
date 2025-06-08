#include <stdio.h>

int main(){
    printf("start \n");
    goto label1;

    printf("ok1 \n");
    printf("ok2 \n");
    label1:
    printf("ok2 \n");
    printf("ok4 \n");
    return 0;
}