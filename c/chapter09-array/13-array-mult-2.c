#include <stdio.h>

int main()
{
    int table[3][4] = {
        {10, 20, 30, 40},
        {50, 60, 70, 80},
        {90, 100, 110, 120}
    };

    // 计算行数
    int row = sizeof(table) / sizeof(table[0]);
    // 计算列数
    int cell = sizeof(table[0]) / sizeof(table[0][0]);

    // 遍历每个元素
    for (int i = 0; i < row; i++)
    {
        for (int j = 0; j < cell; j++)
        {
            printf("第%d行,第%d列，值：%d \t",i,j,table[i][j]);
        }
        printf("\n");
    }

    int sum = 0;
    for(int i=0; i< row;i++){
        for(int j=0;j<cell;j++){
            sum+=table[i][j];
        }
    }
    printf("所有元素和为:%d",sum);

    return 0;
}