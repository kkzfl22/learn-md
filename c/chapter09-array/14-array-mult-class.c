#include <stdio.h>

int main(){
    //现在有三个班，每个班五名同学，用二维数组保存他们的成绩，并求出每个班级平均分、以及所有班级平均分，数据要求从控制台输入。
    int arr[3][5];

    int row = sizeof(arr)/sizeof(arr[0]);
    int cell =sizeof(arr[0])/sizeof(arr[0][0]);


    for(int i=0;i<row;i++){
        for(int j=0;j<cell;j++)
        {
            printf("请输入第%d班第%d个学生的成绩:",i,j);
            scanf("%d",&arr[i][j]);
        }
    }

    //计算班级平均分
    int allSum = 0;
    for(int i=0;i<row;i++){
        int sum = 0;
        for(int j=0;j<cell;j++)
        {
           sum += arr[i][j];
           allSum+=arr[i][j];
        }
        printf("第%d班的平均分数是:%d\n",i,sum/cell);
    }

    printf("所有班的平均分数是:%d\n",allSum/(row * cell));


    return 0;
}