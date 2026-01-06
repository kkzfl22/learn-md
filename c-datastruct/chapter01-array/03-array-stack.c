#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int *arrays;
    size_t size;
    size_t capacity;
} Stack;


/**
 * 初始化栈
 */
void initStack(Stack *stack,size_t capacity)
{
    int *array=(int *)malloc(sizeof(int)*capacity);
    if(NULL == array)
    {
        printf("初始化栈内存失败");
        return;
    }
    stack->arrays=array;
    stack->size=0;
    stack->capacity=capacity;
}


/**
 * 获取栈内元素个数
 */
size_t getSize(Stack *stack)
{
    return stack->size;
}


/**
 * 向栈添加一个元素
 */
void push(Stack *stack,int element)
{
    int putIndex=stack->size;

    //当容量满了之后需要进行扩容操作
    if(putIndex>= stack->capacity)
    {
        int newCapacity=stack->capacity*2;
        int *newArray = realloc(stack->arrays,sizeof(int)*newCapacity);
        if(NULL == newArray)
        {
            printf("新的内存分配失败退出!");
            return;
        }
        printf("\n");
        printf("新的内存分配成功,大小=%d",newCapacity);
        printf("\n");
        stack->arrays = newArray;
        stack->capacity=newCapacity;
    }


    stack->arrays[putIndex]=element;
    stack->size++;    
}

/**
 * 栈柄元素出栈并返回
 */
int pop(Stack *stack)
{
    //如果大小已经为0，则不能再进行出栈操作
    if(stack->size == 0)
    {
        return -1;
    }

    stack->size--;
    int index = stack->size;
    int value = stack->arrays[index];

    return value;
}

/**
 * 释放栈的内存
 */
void destoryStack(Stack *stack)
{
    free(stack->arrays);
    stack->arrays=NULL;
    stack->size=0;
    stack->capacity=0;
}


int main(){
    //1,初始化一个栈
    Stack stackData;
    initStack(&stackData,4);

    //入栈操作
    for(int i=0;i<8;i++)
    {
        push(&stackData,i);
        printf("入栈值=%d\t",i);
    }

    printf("\n\n");

    //出栈操作
    for(int i=0;i<11;i++)
    {
        int value = pop(&stackData);
        printf("出栈值=%d \t",value);
    }

    //释放内存
    destoryStack(&stackData);


    return 0;
}