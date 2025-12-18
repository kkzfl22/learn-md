#include <stdio.h>
#include <stdlib.h>

//定义一个动态数组的结构
typedef struct {
    //存储的数组
    int *data;
    //当前数组的大小
    size_t size;
    //容量
    int capacity;    
} DynamicArray;

/**
 * 初始化一个动态数组
 */
void initDynamicArray(DynamicArray *array,size_t initDynamicSize)
{
    //进行内存的初始化操作
    int *mallocData = (int *)malloc(sizeof(int) * initDynamicSize);
    if(NULL == mallocData)
    {
        printf("内存分配失败，退出");
        return;
    }
    array->data = mallocData;
    array->size=0;
    array->capacity = initDynamicSize;    
}


/**
 * 释放一个数组的内存
 */
void destroyDynamicArray(DynamicArray *array){
    free(array->data);
    array->size=0;
    array->data = NULL;
    array->capacity=0;
}

/**
 * 调整动态数组内存大小
 * @param array 结构定义
 * @param newSize 新的大小
 */
void resizeDynamicArray(DynamicArray *array,size_t newSize)
{
  int *newArray = (int *)realloc(array->data,newSize*sizeof(int));
  if(NULL == newArray)
  {
    printf("重置内存失败");    
    return;
  }

  array->data = newArray;
  array->capacity=newSize;
}


/**
 * 获取动态数组的大小
 */
size_t getLength(DynamicArray *array)
{
    return array->size;
}

/**
 * 在指定的位置插入元素
 */
void insertAt(DynamicArray *array,size_t index,int element)
{
    //检查位置
    if(index < 0 || index > array->size)
    {
        return;
    }

    //进行扩容检查,并扩容操作
    if(array->size >= array->capacity)
    {
        size_t newSize = (array->capacity)*2;
        resizeDynamicArray(array,newSize);
    }

    //进行位置的移动,从后向前，进行数据覆盖操作
    for(int i=array->size;i>index;i--)
    {
        array->data[i]=array->data[i-1];
    }
    
    array->data[index]=element;
    array->size++;
}

/**
 * 在元素的末尾插入元素
 */
void insertEnd(DynamicArray *array,int element)
{
    insertAt(array,array->size,element);
}

/**
 * 删除指定下标的元素
 */
int deleteAt(DynamicArray *array,int index)
{
    //检查下标是否在正常范围内
    if(index < 0 || index > array->size)
    {
        return -1;
    }

    int deleteValue = array->data[index];

    //删除从前到后，让元素直接覆盖
    for(int i=index;i<array->size;i++)
    {
        array->data[i]=array->data[i+1];
    }

    array->size--;

    return deleteValue;
}

/**
 * 删除动态数组的末尾元素
 */
int deleteEnd(DynamicArray *array)
{
    return deleteAt(array,array->size);
}

/**
 * 打印数组信息
 */
void printDynamic(DynamicArray *array)
{
    for(int i=0;i<array->size;i++)
    {
        printf("%d=%d \t",i,array->data[i]);

        if(i%8==0 )
        {
            printf("\n");            
        }

    }
    printf("\n");    
}


int main()
{
    //定义一个动态数组
    DynamicArray data;

    //初始化一个动态数组
    initDynamicArray(&data,4);

    //输出动态数组的容量
    printf("数组的容量%d \n",data.capacity);


    //调整内存大小
    // resizeDynamicArray(&data,16);


    //插入12个元素
    for(int i=0;i<128;i++)
    {
        insertEnd(&data,i);
    }

    //删除元素操作
    for(int i=0;i<118;i++)
    {
        //删除末尾元素
        deleteEnd(&data);
    }
    
    printDynamic(&data);

    //打印数组的长度
    printf("size = %d",getLength(&data));

    
    destroyDynamicArray(&data);


    return 0;
}