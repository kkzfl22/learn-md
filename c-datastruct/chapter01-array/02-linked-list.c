#include <stdio.h>
#include <stdlib.h>

/**
 * 单向链表节点
 */
typedef struct
{
    int value;
    struct Node *next;

} Node;

/**
 * 节点信息
 */
typedef struct
{
    Node *head;
    size_t size;
} LinkedList;

/**
 * 初始化链表
 */
void initLinkedList(LinkedList *list)
{
    list->head = NULL;
    list->size = 0;
}

/**
 * 获取链表的长度
 */
size_t getLength(LinkedList *list)
{
    return list->size;
}

/**
 * 在指定的位置插入元素
 */
void insertAt(LinkedList *list, size_t index, int element)
{
    if (index > list->size)
    {
        return;
    }

    // 创建节点，并录入数据
    Node *nodeTmp = (Node *)malloc(sizeof(Node));
    nodeTmp->value = element;

    // 如果是首节点，则直接标识首元素
    if (index == 0)
    {
        nodeTmp->next = list->head;
        list->head = nodeTmp;
    }
    // 如果非首节点。找到位置，进行录入
    else
    {
        Node *findNode = list->head;
        for (int i = 0; i < list->size - 1; i++)
        {
            findNode = findNode->next;
        }
        // 将新的节点插入到指定的节点上，铸造进行连接节点
        nodeTmp->next = findNode->next;
        findNode->next = nodeTmp;
    }

    list->size++;
}

/**
 * 在末尾处插入节点
 */
void insertEnd(LinkedList *list, int element)
{
    insertAt(list, list->size, element);
}

/**
 * 删除指定元素
 */
int deleteAt(LinkedList *list, int index)
{
    if (index < 0 || index > list->size)
    {
        return -1;
    }

    int  resultValue;

    //如果为删除首元素
    if (index == 0)
    {
        Node *firstNode = list->head;
        list->head = firstNode->next;
        resultValue= firstNode->value;
        free(firstNode);
    }
    else
    {
        // 查找元素的前一个节点
        Node *firstNode = list->head;
        for (int i = 0; i < index - 1; i++)
        {
            firstNode = firstNode->next;
        }
        Node *deleteNode = firstNode->next;
        resultValue = deleteNode->value;
        firstNode->next = deleteNode->next;
        free(deleteNode);
    }

    list->size--;

    return resultValue;
}

/**
 * 删除末尾元素
 */
int deleteEnd(LinkedList *list)
{
    return deleteAt(list, list->size - 1);
}

void destoryLinkedList(LinkedList *list)
{
    Node *firstNode = list->head;
    Node *currentNode = NULL;
    while (firstNode->next != NULL)
    {
        currentNode = firstNode;
        firstNode = firstNode->next;
        // 释放当前节点的内存
        free(currentNode);
    }

    list->head = NULL;
    list->size = 0;
}

/**
 * 打印信息
 */
void printLinkedList(LinkedList *list)
{
    Node *firstNode = list->head;
    while (firstNode != NULL)
    {
        printf("节点值%d \t", firstNode->value);
        firstNode = firstNode->next;
    }
}

int main()
{

    LinkedList temp;

    // 1,初始化节点
    initLinkedList(&temp);

    for (int i = 0; i < 32; i++)
    {
        // insertAt(&temp,0,i);
        insertEnd(&temp, i);
    }

    // 删除30个元素
    for (int i = 0; i < 30; i++)
    {
        //deleteEnd(&temp);
        deleteAt(&temp,0);
    }

    printLinkedList(&temp);

    destoryLinkedList(&temp);

    return 0;
}