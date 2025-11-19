#include <stdio.h>
#include <time.h>

int main(){
    time_t t = time(NULL);

    printf("时间戳：%lld \n",t);

    //给指针赋值NULL为初始值
    int *p = NULL;
    int num = 128;
    p = &num;

    printf("p=%p,值=%d",p,*p);

    return 0;
}