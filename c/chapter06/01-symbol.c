#include <stdio.h>

int main(){
    int x = 12;
    int x1 = -x,x2 = +x;
    int y = -67;
    int y1 = -y,y2=+y;
    printf("x1=%d,x2=%d \n",x1,x2);
    printf("y1=%d,y2=%d \n",y1,y2);
    return 0;
}