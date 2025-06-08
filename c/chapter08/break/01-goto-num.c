#include <stdio.h>

int main(){
    int num = 1;

loopStart:
    if(num<5){
        printf("%d\n",num);
        num++;
        goto loopStart;
    }

    return 0;
}