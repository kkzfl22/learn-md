#include <stdio.h>

int main(){
    int oilNum,oilL;
    printf("请输入加油号(92或者95):");
    scanf("%d",&oilNum);
    printf("请输入加油量:");
    scanf("%d",&oilL);

    double price;

    if(oilNum == 92)
    {
        if(oilL >= 40)
        {
            price = 3.5;
        }
        else{
            price = 4;
        }
    }
    else if(oilNum == 95)
    {
        if(oilL >= 40)
        {
            price = 4.5;
        }
        else{
            price = 5;
        }
    }
    else
    {
        printf("加油号输入错误！");
    }
    
    double sum = oilL * price;
    printf("支付的金额为:%lf",sum);

    return 0;
}