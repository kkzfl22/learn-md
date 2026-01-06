#include <stdio.h>

/**
 * 二分查找
 */
int binarySearch(int array[], int size, int target)
{
    int left = 0, right = size;
    while (left <= right)
    {
        // 计算中间位值
        int mid = (right + left) / 2;

        if (array[mid] == target)
        {
            return mid;
        }
        // 如果中间值大于了查找值，说明在左区间查找
        else if (array[mid] > target)
        {
            right = mid - 1;
        }
        // 如果中间值小于查找值，说明要在右区间查找
        else if (array[mid] < target)
        {
            left = mid + 1;
        }
    }

    return -1;
}

int main()
{

    int array[] = {1, 2, 3, 4, 6, 8, 10, 13};
    int size = sizeof(array) / sizeof(array[0]);

    printf("查找值%d的索引是%d\n", 3, binarySearch(array, size, 3));
    printf("查找值%d的索引是%d\n", 10, binarySearch(array, size, 10));

    return 0;
}