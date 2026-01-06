#include <stdio.h>

void arraySort(int array[], int size)
{
    for (int i = 0; i < size; i++)
    {
        for (int j = i; j < size; j++)
        {
            if (array[i] < array[j])
            {
                int arrayTmp = array[i];
                array[i] = array[j];
                array[j] = arrayTmp;
            }
        }
    }
}

int main()
{
    int array[] = {9, 8, 4, 2, 11, 5, 8};
    int size = sizeof(array) / sizeof(array[0]);
    arraySort(array, size);

    for (int i = 0; i < size; i++)
    {
        printf("%d=>%d\t", i, array[i]);
    }

    return 0;
}