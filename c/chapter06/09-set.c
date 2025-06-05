#include <stdio.h>

int main(){
    int a = 10,b=20,c=30;
    c+=3; //33
    c+=b; //53
    a+=1.7; //11

    printf("a=%d b=%d c=%d",a,b,c);
    return 0;
}