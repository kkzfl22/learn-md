#include <stdio.h>

#if _WIN32 //如果是windows平台，就引入<windows.h>
    #include <windows.h>    
    #define SLEEP(t) Sleep(t * 1000) //windows平台单位是毫秒，由秒至毫秒*1000
    #define PLATFORM "Windows"
#elif __linux__ //如果是linux平台，就引入<unistd.h>
    #include <unistd.h>
    #define SLEEP sleep
    #define PLATFORM "Linux"
#endif

int main(){
    printf("start run ... \n");

    SLEEP(3);

    printf("hello,C语言,当前平台:%s \n",PLATFORM);

    return 0;
}