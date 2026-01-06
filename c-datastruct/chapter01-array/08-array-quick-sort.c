#include <stdio.h>

/**
 * 执行快速排序操作
 */
void quickSort(int array[], int start, int end)
{
    if(end-start < 1)
    {
        return;
    }

    int point = partition(array, start, end);
    quickSort(array, 0, point - 1);
    quickSort(array, point + 1, end);
}

/**
 * 分区函数，每次分区，即选择一个点，左边的比这个点小，右边的比这个点大
 */
int partition(int array[], int start, int end)
{
    // 默认以最后一个数据为分区点
    int point = end;
    int s = start;
    for (int i = start; i <= end-1; i++)
    {
        if (array[i] < array[point])
        {
            int tmp = array[i];
            array[i] = array[s];
            array[s] = tmp;
            s++;
        }
    }

    // 分区点的数据交换
    int tmp = array[s];
    array[s] = array[point];
    array[point] = tmp;

    return s;
}


int main()
{
    int array[] = {9, 8, 4, 2, 11, 5, 7,22,15};
    int size = sizeof(array) / sizeof(array[0]);
    quickSort(&array, 0, size - 1);

    for (int i = 0; i < size; i++)
    {
        printf("%d=>%d\t", i, array[i]);
    }

    return 0;
}