#include <stdio.h>

/**
 * argc 传递的参数大小
 * 
 * argv 传递的是参数的值
 */
int main(int argc, char *argv[])
{ 
    printf("param num:%d\n",argc);
    //打印参数
    for(int i =0 ;i < argc ;i++){
        printf("%d --> %s\n",i,argv[i]);
    }

    return 0;
}