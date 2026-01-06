#include <stdio.h>
#include <stdlib.h>

/**
 * 队列的结构
 */
typedef struct
{
    int *array;
    size_t size;
    size_t capacity;
    size_t put;
    size_t get;
} Queue;

/**
 * 初始化一个队列
 */
void initQueue(Queue *queue, size_t capacity)
{
    int *arrayMemory = (int *)malloc(sizeof(int) * capacity);
    if (NULL == arrayMemory)
    {
        printf("内存分配失败，退出");
        return;
    }
    queue->array = arrayMemory;
    queue->size = 0;
    queue->capacity = capacity;
    queue->put = 0;
    queue->get = 0;
}

/**
 * 返回一个队列的大小
 */
size_t getSize(Queue *queue)
{
    return queue->size;
}

/**
 * 向队列末尾添加元素
 */
void enQueue(Queue *queue, int element)
{
    // 1,检查队列是否已经满了,如果满了，则进行扩容操作
    if (queue->size >= queue->capacity)
    {
        printf("队列已经满了，无法再执行插入\n");
        return;
    }
    int putIndex = queue->put;
    queue->array[putIndex] = element;
    queue->put = (queue->put + 1) % (queue->capacity);
    queue->size++;
}

/**
 * 出队列操作
 */
int deQueue(Queue *queue)
{
    if (queue->size == 0)
    {
        return -1;
    }

    int getValue = queue->array[queue->get];
    queue->get = (queue->get + 1) % queue->capacity;
    queue->size--;
    return getValue;
}

/**
 * 进行队列的内存释放操作
 */
void destoryQueue(Queue *queue)
{
    free(queue->array);
    queue->capacity = 0;
    queue->get = 0;
    queue->put = 0;
    queue->size = 0;
}

/**
 * 打印队列的信息操作
 */
void printQueue(Queue *queue)
{
    int start = queue->get;
    int end = queue->get + queue->size;
    for (int i = start; i < end; i++)
    {
        int printIndex = (i) % queue->capacity;
        int value = queue->array[printIndex];
        printf("队列:i=%d,value=%d\n", printIndex, value);
    }
    printf("\n");
}

int main()
{

    // 定认队列
    Queue queueData;

    // 初始化队列
    initQueue(&queueData, 8);

    // 入队列操作
    for (int i = 0; i < 12; i++)
    {
        enQueue(&queueData, i);
        printf("入队列成功:%d\n", i);
    }

    printf("\n");

    printQueue(&queueData);

    printf("\n");

    for (int i = 0; i < 12; i++)
    {
        int value = deQueue(&queueData);
        printf("出队列 操作i=%d,value=%d\n", i, value);
    }

    // 释放队列
    destoryQueue(&queueData);

    return 0;
}