#include <stdio.h>

int main(){
    double score = 70;
    if(score >= 70 || score <= 80){
        printf("out11\n");
    }
    else{
        printf("out22\n");
    }

    int a = 10,b=99;
    //短路现象
    if(a>5 || b++>100){
        printf("out33\n");
    }
    printf("b=%d\n",b);

    return 0;
}