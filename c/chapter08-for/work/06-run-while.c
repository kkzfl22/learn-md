#include <stdio.h>

int main()
{
    int start = 1000;
    int end = 9999;
    while (start <= end)
    {
        if ((start % 4 == 0 && start % 100 != 0) ||  (start % 400 == 0))
        {
            printf("当前%d年为闰年\n",start);
        }

        start++;
    }

    return 0;
}