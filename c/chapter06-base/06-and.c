#include <stdio.h>

int main(){
    double score = 70;
    if(score >= 70 && score <= 100)
    {
        printf("成绩良好!\n");
    }
    else{
        printf("成绩一般 \n");
    }

    int a = 10,b=99;
    //短路现象
    if(a<2&&++b>99)
    {
        printf("短路不执行!\n");
    }
    printf("b=%d\n",b);

    return 0;
}