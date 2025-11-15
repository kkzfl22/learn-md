#include <stdio.h>

extern int a;
extern double PI;
extern char msg[];
extern void fn01();
extern int static_num;

int main()
{
    printf(" a=%d \n",a);
    printf(" PI=%f \n",PI);
    printf(" PI=%s \n",msg);
    printf(" static_num=%d \n",static_num);
    fn01();
    return 0;
}