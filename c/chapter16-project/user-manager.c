#include <stdio.h>
#define MAX_LIMIT 3

struct user_data
{
    int id;         //数据编号
    char name[32];  // 姓名
    char grade;  // 性别
    int age;     // 年龄
    char phone[20]; // 电话
    char email[32]; // 邮箱
};

// 组结构取别名
typedef struct user_data user_item;

// 定义添加方法
void user_add(user_item *user_array);
// 定义修改方法
void user_update(user_item *user_array);

// 定义删除方法
void user_delete(user_item *user_array);

// 定义打印方法
void user_print(user_item *user_array);


//进行操作前瞻的确认，并得到结果
char confirm_msg();

// 定义一个struct类型的数组，用于存储用户信息
user_item user_array[MAX_LIMIT];
int user_array_size = 0;
//用户自增编号
int user_data_ser = 0;

int main()
{
    while (1)
    {
        printf("----------客户信息管理软件----------\n");
        printf("----------1 添加客户\n");
        printf("----------2 修改客户\n");
        printf("----------3 删除客户\n");
        printf("----------4 客户列表\n");
        printf("----------5 退出\n");
        printf("----------请选择(1-5)\n");

        int select;
        scanf("%d", &select);

        // 如果为1，则进行添加客户信息
         if (select == 1)
        {
            user_add(user_array);
        }
        // 2,修改用户信息
        else if (select == 2)
        {
            user_update(user_array);
        }
        // 3，删除用户信息
        else if (select == 3)
        {
            user_delete(user_array);
        }
        // 4,客户列表
        else if (select == 4)
        {
            user_print(user_array);
        }
        else if(select == 5)
        {
            //进行删除的确认
            char rsp = confirm_msg("您确认退出吗(Y/N))？");
            if(rsp == 'Y')
            {
                printf("当前执行退出操作! 88!");
                return 0;
            }
        }
    }
}

//二次确认操作，只接收Y/N之一
char confirm_msg(char *msg)
{
    char confirm_data;

    do{
        printf(msg);
        getchar();
        scanf("%c",&confirm_data);
    }while (confirm_data != 'Y' && confirm_data != 'N');
   
    return confirm_data;
}

// 添加方法
void user_add(user_item *user_array_tmp)
{
    if (user_array_size > MAX_LIMIT)
    {
        printf("用户输入的数量超过最大的数量,%d", MAX_LIMIT);
        return;
    }

    printf("----------添加客户----------\n");

    //生成下序号
    user_array_tmp[user_array_size].id = user_data_ser;
    user_data_ser++;

    printf("姓名:");
    scanf("%s", user_array_tmp[user_array_size].name);
    printf("性别(S/F):");
    getchar(); // 读取缓冲区的回车符
    scanf("%c", &user_array_tmp[user_array_size].grade);
    printf("年龄:");
    scanf("%d", &user_array_tmp[user_array_size].age);
    printf("电话:");
    scanf("%s", user_array_tmp[user_array_size].phone);
    printf("邮箱:");
    scanf("%s", user_array_tmp[user_array_size].email);
    printf("\n\n");


    user_array_size++;
    printf("----------添加完成----------\n");
}

// 打印用户列表
void user_print(user_item *user_array)
{
    printf("----------客户列表----------\n");

    int len = user_array_size;
    printf("大小:%d \n",len);

    for (int i=0;i<len;i++)
    {
        printf("编号=%d,名称=%s,性别=%c,年龄=%d,手机号=%s,email=%s \n",
               user_array[i].id,
               user_array[i].name,
               user_array[i].grade,
               user_array[i].age,
               user_array[i].phone,
               user_array[i].email);
    }

    printf("\n\n");
}

//修改客户信息
void user_update(user_item *user_array){
    int upd_index = 0;
    printf("请选择修改修改客户的编号:");
    scanf("%d",&upd_index);
    getchar();


    char buffer[32];


    printf("姓名(%s):",user_array[upd_index].name);
    //读取缓冲区中用户的输入
    fgets(buffer,sizeof buffer,stdin);
    //如果输入的非回车，则进行修改
    if(buffer[0] != '\n')
    {
        //修改姓名
        sscanf(buffer,"%s",user_array[upd_index].name);
    }

    printf("性别(S/F)(%c):",user_array[upd_index].grade);
    //读取缓冲区中用户的输入
    fgets(buffer,sizeof buffer,stdin);
    //如果输入的非回车，则进行修改
    if(buffer[0] != '\n')
    {
        //修改性别
        sscanf(buffer,"%c",&user_array[upd_index].grade);
    }


    printf("年龄(%d):",user_array[upd_index].age);
    //读取缓冲区中用户的输入
    fgets(buffer,sizeof buffer,stdin);
    //如果输入的非回车，则进行修改
    if(buffer[0] != '\n')
    {
        //修改性别
        sscanf(buffer,"%d",&user_array[upd_index].age);
    }

    
    printf("电话(%s):",user_array[upd_index].phone);
    //读取缓冲区中用户的输入
    fgets(buffer,sizeof buffer,stdin);
    //如果输入的非回车，则进行修改
    if(buffer[0] != '\n')
    {
        //修改电话
        sscanf(buffer,"%s",user_array[upd_index].phone);
    }


    printf("邮箱(%s):",user_array[upd_index].email);
        //读取缓冲区中用户的输入
    fgets(buffer,sizeof buffer,stdin);
    //如果输入的非回车，则进行修改
    if(buffer[0] != '\n')
    {
        //修改邮箱
        sscanf(buffer,"%s",user_array[upd_index].email);
    }
    
    printf("----------修改结束\n");
}

// 定义删除方法
void user_delete(user_item *user_array){
    int delete_index = 0;
    printf("请输入删除的索引号:");
    scanf("%d",&delete_index);

    //二次确认删除
    char rsp = confirm_msg("您确认删除吗(Y/N))？");
    if(rsp == 'N')
    {
        return ;
    }

    for(int i=delete_index;i<user_array_size-1;i++)
    {
        user_array[i]=user_array[i+1];
    }

    user_array_size--;

    printf("--------删除结束--\n");


}