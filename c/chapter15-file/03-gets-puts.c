#include <stdio.h>

int main(){

    char str[10];

    printf("entrer a line of text :");
    //gets(str); //此处存在一个问题，如果用户输入的字符超过了，str数组的长度，则会出现错误。不建议使用，建议使用 fgets() 代替
    fgets(str,sizeof str,stdin);

    printf("you entered:");
    puts(str); 


    return 0;
}