#include <stdio.h>

int main(){
    int month;
    printf("请输入一个月份(1-12):");
    scanf("%d",&month);

    switch (month)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        printf("这个月有31天");
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        printf("这个月有30天！");
        break;
    case 2:
        printf("这个月有28天或者29天"); 
        break;  
    default:
        printf("输入的月份错误!");
        break;
    }
    return 0;
}
