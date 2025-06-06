#include <stdio.h>

int main(){
    //（1）输出10次 "我第n天吃了n个韭菜馅的包子"。
    int num = 10;
    int index = 0;
    while(index < num){
        printf("我第%d天吃了%d个韭菜馅的包子!\n",index,index);
        index++;
    }

    return 0;
}