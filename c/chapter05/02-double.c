#include <stdio.h>

int main(){
    //double类型
    double d1 = 3.1415926;
    double d2 = .123456;
    double d3 = -2e12;
    double d4 = 1.9823e2;

    printf("d1=%f,d2=%.10f,d3=%.2lf,d4=%lf\n",d1,d2,d3,d4);
    printf("d1=%e,d2=%e,d3=%e,d4=%e \n",d1,d2,d3,d4);

    //float类型
    float f1 = 3.1415f;
    float f2 = .123456f;
    float f3 = -2e12f;
    float f4 = 1.9823e2f;
    printf("f1=%f,f2=%f,f3=%f,f4=%f \n",f1,f2,f3,f4);
    printf("f1=%e,f2=%e,f3=%e,f4=%e \n",f1,f2,f3,f4);


    //long double类型
    long double ld1=3.14L;
    long double ld2=.1234L;
    long double ld3=-2e3L;
    long double ld4=1.982e2L;

    printf("ld1=%LF,ld2=%LF,ld3=%LF,ld4=%LF \n",ld1,ld2,ld3,ld4);
    printf("ld1=%e,ld2=%e,ld3=%e,ld4=%e  \n",ld1,ld2,ld3,ld4);
    return 0;
}