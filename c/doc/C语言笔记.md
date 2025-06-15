# C语言笔记

## 1. C语言开发入门

### C程序分析

![image-20250601122624156](.\images\image-20250601122624156.png)

（1）编写

编写C语言源程序代码，并以文件的形式存储到磁盘中，源代码文件以“.c”作为扩展名，如本项目中main.c 文件。

（2）预处理

在编译之前，预处理器会处理源代码文件，主要进行一些文本上的处理，包括去掉多余的空格和注释，处理预处理指令（后面会学到），生成经过处理的源代码文件，通常带有`.i`扩展名。

```sh
gcc -E main.c -o main.i
```



（3）编译

编译器接受预处理后的源代码文件，并将其翻译成汇编代码，生成汇编文件，通常带有.s或.asm扩展名。

```sh
gcc -S main.i -o main.s
```



（4）汇编

汇编器将汇编代码翻译成机器码并生成一个或多个目标文件，目标文件是二进制文件，通常带有`.o`（在Unix-like系统上）或`.obj`（在Windows上）

```sh
gcc -c main.s -o main.obj
```

（5）链接

链接器将多个目标文件（如果有的话）合并在一起，包括C标准库文件和其他库文件，生成最终的可执行的二进制程序（.exe 程序）。                                                

库文件是由系统提供，里面包括内置的标准函数和数据结构集合等，我们代码中使用的 <stdio.h> 就是标准库提供的，里面定义 printf 这个函数。

```sh
gcc main.obj -o main.exe
```



（6）运行

执行该.exe文件得到程序的运行结果。

```sh
./main.exe
```





```sh
Usage: gcc.exe [options] file...
Options:
  -pass-exit-codes         Exit with highest error code from a phase.
  --help                   Display this information.
                           Display specific types of command line options.
  (Use '-v --help' to display command line options of sub-processes).
  --version                Display compiler version information.
  -dumpspecs               Display all of the built in spec strings.
  -dumpversion             Display the version of the compiler.
  -dumpmachine             Display the compiler's target processor.
  -foffload=<targets>      Specify offloading targets.
  -print-search-dirs       Display the directories in the compiler's search path.
  -print-libgcc-file-name  Display the name of the compiler's companion library.
  -print-file-name=<lib>   Display the full path to library <lib>.
  -print-prog-name=<prog>  Display the full path to compiler component <prog>.
  -print-multiarch         Display the target's normalized GNU triplet, used as
                           a component in the library path.
  -print-multi-directory   Display the root directory for versions of libgcc.
  -print-multi-lib         Display the mapping between command line options and
                           multiple library search directories.
  -print-multi-os-directory Display the relative path to OS libraries.
  -print-sysroot           Display the target libraries directory.
  -print-sysroot-headers-suffix Display the sysroot suffix used to find headers.
  -Wa,<options>            Pass comma-separated <options> on to the assembler.
  -Wp,<options>            Pass comma-separated <options> on to the preprocessor.
  -Wl,<options>            Pass comma-separated <options> on to the linker.
  -Xassembler <arg>        Pass <arg> on to the assembler.
  -Xpreprocessor <arg>     Pass <arg> on to the preprocessor.
  -Xlinker <arg>           Pass <arg> on to the linker.
  -save-temps              Do not delete intermediate files.
  -save-temps=<arg>        Do not delete intermediate files.
  -no-canonical-prefixes   Do not canonicalize paths when building relative
                           prefixes to other gcc components.
  -pipe                    Use pipes rather than intermediate files.
  -time                    Time the execution of each subprocess.
  -specs=<file>            Override built-in specs with the contents of <file>.
  -std=<standard>          Assume that the input sources are for <standard>.
  --sysroot=<directory>    Use <directory> as the root directory for headers
                           and libraries.
  -B <directory>           Add <directory> to the compiler's search paths.
  -v                       Display the programs invoked by the compiler.
  -###                     Like -v but options quoted and commands not executed.
  -E                       Preprocess only; do not compile, assemble or link.
  -S                       Compile only; do not assemble or link.
  -c                       Compile and assemble, but do not link.
  -o <file>                Place the output into <file>.
  -pie                     Create a dynamically linked position independent
                           executable.
  -shared                  Create a shared library.
  -x <language>            Specify the language of the following input files.
                           Permissible languages include: c c++ assembler none
                           'none' means revert to the default behavior of
                           guessing the language based on the file's extension.

Options starting with -g, -f, -m, -O, -W, or --param are automatically
 passed on to the various sub-processes invoked by gcc.exe.  In order to pass
 other options on to these processes the -W<letter> options must be used.

For bug reporting instructions, please see:
```



### 注释

```c
单行注释
// 注释内容

多行注释
/* 注释内容 */ 

/* 
  注释内容
*/
```



### 输出

```c
#include <stdio.h>

int main()
{
    printf("Hello, World!");
    printf("\nMusk is learning C\n");
    printf("\n锄禾日当午，\n汗滴禾下土，\n谁知盘中餐，\n粒粒皆辛苦");

    return 0;
}

```



##  2. 变量

 输入数据赋值给变量

```c
#include <stdio.h>


int main(){
    //声明一个变量，
    int num;

    //提示输入信息
    printf("请输入一个数字:");
    //从标准输入读取整数，并将其保存到变量num中
    scanf("%d",&num);
    //输出数据
    printf("Your number is %d \n",num);

    //从标准输入中读取多个数据
    int num1,num2,num3;
    printf("Please Input three Number:");
    //从标准输入中读取3个整数，使用空格分隔，并将其保存到变量num1,num2,num3中
    //在运行时，可使用空格，也可以使用回车进行分隔
    scanf("%d %d %d",&num1,&num2,&num3);
    printf("Numbers: num1=%d , num2=%d , num3=%d",num1,num2,num3);

    return 0;
}
```

输出：

```tex
请输入一个数字:1
Your number is 1 
Please Input three Number:11 22 33
Numbers: num1=11 , num2=22 , num3=33
```

### 命名规范：

标识符命名规范

1）强制规范

（1）只能由小写或大写英文字母，0-9 或 _ 组成。

（2）不能以数字开头。

（3）不可以是关键字。

（4）标识符具有长度限制，不同编译器和平台会有所不同，一般限制在63个字符内。

（5）严格区分大小写字母。比如：Hello、hello是不同的标识符。

合法的标识符举例：

a、BOOK_sun、MAX_SIZE、Mouse、student23、Football、FOOTBALL、max、_add、num_1、sum_of_numbers

非法的标识符举例：

$zj、3sum、ab#cd、23student、Foot-baii、s.com、b＆c、j**p、book-1、

tax rate、don't

2）建议规范

（1）为了提高阅读性，使用有意义的单词，见名知意，如：sum，name，max，year等。

（2）使用下划线连接多个单词组成的标识符，如：max_classes_per_student。

（3）多个单词组成的标识符，除了使用下划线连接，也可以使用小驼峰命名法，除第一个单词外，后续单词的首字母大写，如 `myVariableName`、maxClassesPerStudent。

（4）不要出现仅靠大小写区分不同的标识符，如：name、Name容易混淆。

（5）系统内部使用了一些下划线开头的标识符，比如，C99标准添加的类型 `_Bool`，为防止冲突，建议开发者尽量避免使用下划线开头的标识符。

关键字

关键字是一些具有特殊含义的保留单词。

ANSI C有32个关键字。如下：

| **类型**                        | **具体关键字**                                               |
| ------------------------------- | ------------------------------------------------------------ |
| **控制语句关键字（12** **个）** | break, case, continue, default, do, else, for, goto,  if, return, switch, while |
| **数据类型关键字（12** **个）** | char, enum, double, long, float, int, short, signed,  struct, unsigned, union, void |
| **存储类型关键字（4** **个）**  | auto, extern, register, static                               |
| **其他关键字（4** **个）**      | const, sizeof, typedef, volatile                             |

C99标准增加了5个关键字：inline、restrict、_Bool、_Complex和_Imaginary。

C11标准增加了7个关键字：_Alignas、_Alignof、_Atomic、_Static_assert、_Noreturn、_Thread_local和_Generic。



### 小案例

定义两个整数变量，获取用户输入作为它们的值，计算它们的和并输出。

```c
#include <stdio.h>

int main(){
    int num1,num2,sum;

    printf("请输入两个整数并使用空格隔开：");
    scanf("%d %d",&num1,&num2);

    //计算两数之和
    sum = num1 + num2;

    printf("sum = %d",sum);
    return 0;
}
```

输出：

```tex
请输入两个整数并使用空格隔开：5 1234
sum = 1239
```



## 3. 常量

什么是常量？

程序运行时，其值不能改变的量，即为`常量`。

常量的分类

（1）字面量常量，直接使用的常量，不需要定义或声明，包括整数常量、浮点数常量、字符常量。

（2）标识符常量，使用标识符作为常量名，包括 #define 定义的标识符常量和const 关键字定义的标识符常量以及枚举常量。 

### 常量的定义

字面常量不需要定义或声明，我们主要学习如何定义标识符常量，习惯上常量名使用大写，方便与变量区分。

#### 使用`#define`定义常量

\#define 来定义常量，也叫作宏定义，就是用一个标识符来表示一个常量值，如果在后面的代码中出现了该标识符，那么编译时就全部替换成指定的常量值，即用宏体替换所有宏名，简称`宏替换`。

1）#define 定义常量的格式

\#define 常量名 常量值

注意：

（1）不要以分号结尾，如有分号，分号会成为常量值的一部分。

（2）#define 必须写在 main 函数的外面（有些编译器的扩展允许 #define 写在 main 函数里，但我们强烈不建议这么做）。

```c
#include <stdio.h>

//定义常量，使用
#define PI  3.14

int main(){

    double area;
    double r = 1.2;
    area = PI * r * r;

    printf("area : %.4f。",area);

    return 0;
}
```

结果：

```tex
area : 4.5216。
```

查看预编译的结果：

```tex
gcc -E .\defineuse.c -o defineuse.i

# 可查看到以下内容

# 6 ".\\defineuse.c"
int main(){

    double area;
    double r = 1.2;
    area = 3.14 * r * r;

    printf("area : %.4f。",area);

    return 0;
}

```



#### 使用`const`定义常量

C99标准新增，这种方式跟定义一个变量是一样的，只需要在变量的数据类型前再加上一个const关键字。

跟使用 #define定义宏常量相比，const定义的常量有详细的数据类型，而且会在编译阶段进行安全检查，在运行时才完成替换，所以会更加安全和方便。

1）const 定义常量的格式

const 数据类型 常量名 = 常量值;

```c
#include <stdio.h>

//定义常量，使用const
const double PI = 3.14;

int main(){

    double area;
    double r = 1.2;
    area = PI * r * r;

    printf("area : %.4f。",area);

    return 0;
}
```

输出:

```tex
area : 4.5216。
```

使用gcc查看预编译的结果：`gcc -E .\const.c -o const.i`

```c
const double PI = 3.14;

int main(){

    double area;
    double r = 1.2;
    area = PI * r * r;

    printf("area : %.4f。",area);

    return 0;
}

```

#### #define定义常量和const定义常量的区别

（1）执行时机：#define是预处理指令，在编译之前执行；const是关键字，在编译过程中执行。

（2）类型检查：#define定义常量不用指定类型，不进行类型检查，只是简单地文本替换；const定义常量需指定数据类型，会进行类型检查，类型安全性更强。

相比之下更推荐使用const

## 4. 进制

(1) 二进制：以0b或者0B开头表示

(2) 十进制：正常的数字表示.

(3) 十六进制： 以0x或者0X开头表示，此处的A-F不区分大小写。



```c
#include <stdio.h>

int main(){
    //二进制表示
    int num1 = 0b10;
    //十进制，正常
    int num2 = 100;
    //十六进制.
    int num3 = 0x1f;

    printf("二进制: %d \n",num1);
    printf("十进制: %d \n",num2);
    printf("十六进制: %d \n",num3);

    return 0;
}
```

输出：

```tex
二进制: 2 
十进制: 100 
十六进制: 31 
```







## 5. 数据类型

### 4.1 整数类型

| **类型**                            | **存储大小**               | **值范围**                                                   |
| ----------------------------------- | -------------------------- | ------------------------------------------------------------ |
| **short**   **signed short**        | 2 字节                     | -32,768 (- 2^15)到 32,767 (2^15 -1)                          |
| **unsigned short**                  | 2 字节                     | 0 到 65,535 (2^16 - 1)                                       |
| **int**  **signed int**             | 16位：2 字节  32位：4 字节 | 16位：-32,768 (- 2^15 ) 到 32,767 (2^15-1)  <br/>32位：-2,147,483,648 (- 2^31)  到 2,147,483,647 (2^31  -1) |
| **unsigned int**                    | 16位：2 字节  32位：4 字节 | 16位：0 到  65,535 (2^16-1)   32位：0 到 4,294,967,295  (2^32 -1) |
| **long**   **signed long**          | 32位：4 字节 64位：8 字节  | 32位：-2,147,483,648 (- 2^31)  到 2,147,483,647 (2^31  - 1)  <br/>64位：9223372036854775808(-2^63)  到9223372036854775807 (2^63-1) |
| **unsigned long**                   | 4 或 8 字节                | 32位：0 到 4,294,967,295  (2^32 - 1)  <br/>64位：0 ~ 18446744073709551615 (2^64 - 1) |
| **long long**  **signed long long** | 8字节                      | 9223372036854775808(-2^63)  到9223372036854775807 (2^63-1)   |
| **unsigned long long**              | 8 字节                     | 0 ~ 18446744073709551615 (2^64 - 1)                          |

关于存储大小单位：

bit（位）：计算机中的最小存储单位，表示一个二进制位。

byte（字节）：计算机中基本存储单元，1byte = 8bit。

整型注意事项

（1）各类型存储大小受到操作系统、编译器、硬件平台的影响。

（2）整型分为有符号signed和无符号unsigned两种，默认是signed。

（3）开发中使用整型一般用int型，如果不足以表示大数，可以使用long long。

字面量是源代码中一个固定值的表示法，用于直接表示数据，

```c
int num1 = 100; 
int num2 = 200L;
long long num3 = 330LL;
```

（1）一个整数字面量默认是int类型。

（2）如果需要表示 long 类型字面量，需要添加后缀 l 或 L。

（3）如果需要表示 long long 类型字面量，需要添加后缀 ll 或 LL。

（4）如果需要表示无符号整数字面量，需要添加后缀 u 或 U， 注意， u 和 l 可以结合使用，不分先后，如 ul（无符号long类型）、ULL（无符号 long long类型），lu（无符号 long 类型） 等。

格式占位符

(1)  %d对应int类型，%u对应unsigned int类型。

(2) %hd 对应 short类型，%hu对应unsigned short类型。

(3) %ld对应long类型，%lu 对应unsigned long类型。

(4) %lld对应long long类型，%llu对应unsigned long long 类型。



案例:

```c
#include <stdio.h>

int main(){
    //short类型
    short s1 = 10; //等同于signed short s1 = 10;
    short s2 = -10; 
    unsigned short s3 = 100;
    printf("short 类型：s1=%hd; s2=%hd; s3=%d; \n",s1,s2,s3);

    //int 类型
    int i1 = 100;
    int i2 = -100;
    unsigned int i3 = 200u;
    unsigned int i4 = 300U;
    printf("int 类型： i1=%d; i2=%d;i3=%d; i4=%d \n",i1,i2,i3,i4);


    //long类型
    long l1 = 1000l;
    long l2 = -1000L;
    unsigned long l3 = 2000UL;
    printf("long类型： l1=%ld; i2=%ld; i3=%lu \n",l1,l2,l3);

    //long long类型
    long long ll1 = 10000ll;
    long long ll2 = -10000LL;
    unsigned long long ll3 = 200000UL;

    printf("long long类型 ll1=%lld; ll2=%lld; ll3=%llu",ll1,ll2,ll3);

    return 0;
}
```



输出：

```tex
short 类型：s1=10; s2=-10; s3=100;        
int 类型： i1=100; i2=-100;i3=200; i4=300 
long类型： l1=1000; i2=-1000; i3=2000     
long long类型 ll1=10000; ll2=-10000; ll3=200000
```



精确宽度类型

C 语言的整数类型（short、int、long）在不同计算机上，占用的字节宽度可能是不一样的。程序员有时需要精准的字节宽度，以提高代码的可移植性，尤其是嵌入式开发中，使用精确宽度类型可以确保代码在各种平台上的一致性。
标准库的头文件 <stdint.h> 中定义了一些新的类型别名，如下：

| 类型名称 | 含义            |
| -------- | --------------- |
| int8_t   | 8 位有符号整数  |
| int16_t  | 16 位有符号整数 |
| int32_t  | 32 位有符号整数 |
| int64_t  | 64 位有符号整数 |
| uint8_t  | 8 位无符号整数  |
| uint16_t | 16 位无符号整数 |
| uint32_t | 32 位无符号整数 |
| uint64_t | 64 位无符号整数 |

上面这些都是类型别名，编译器会指定它们指向的底层类型。比如，某个系统中，如果 int 类型为32位， int32_t 就会指向 int ；如果 long 类型为32位， int32_t 则会指向 long 。

案例：

```c
#include <stdio.h>
#include <stdint.h>

int main(){
    
    //定义32位等宽的整型
    int32_t num1=56;
    uint32_t num2 = 200;
    
    printf("num01=%d,num02=%u",num1,num2);

    return 0;
}
```

输出：

```tex
num01=56,num02=200
```





### 4.2 浮点数

| 类型                 | 存储大小                   | 值范围                     | 有效小数位数 |
| -------------------- | -------------------------- | -------------------------- | ------------ |
| float 单精度         | 4 字节                     | 1.2E-38 到 3.4E+38         | 6 ~ 9        |
| double 双精度        | 8 字节                     | 2.3E-308 到 1.7E+308       | 15 ~18       |
| long double 长双精度 | 32位：10字节               | 32位：与 double 相同或更大 | 18或更多     |
| 64位：16字节         | 64位：3.4E-4932到1.2E+4932 |                            |              |

3）浮点型注意事项
（1）各类型的存储大小和精度受到操作系统、编译器、硬件平台的影响。
（2）浮点型数据有两种表示形式。
十进制数形式：如：5.12、512.0f、.512（0.512 可以省略 0）
科学计数法形式：如：5.12e2、5.12E-2
开发中用到浮点型数字，建议使用double型，如精度要求更高可以使用long double 

4）字面量后缀
（1）浮点数字面量默认是double型，
（2）如果需要表示float类型字面量，须加后缀 f 或 F。
（3）如果需要表示long double类型字面量，需加后缀 l 或 L。

5）格式占位符
在C语言中，占位符是一种用于格式化输出的特殊字符，通常用于 printf() 等输出函数中，用于指定输出的格式和内容。

（1）%f 是浮点类型的格式占位符，在printf中对应double类型（float类型会转换成double来处理）；默认会保留6位小数，可以指定小数位数，如：%.2f 表示保留2位小数。
（2）%lf在printf中和 %f意思相同（C99标准加入），也对应double类型，默认保留6位小数，可以指定小数位数，如：%.2lf 表示保留2位小数。但需要注意的是，在scanf中 %lf和 %f含义不同：输入一个float类型数据时使用 %f；而输入double类型时必须使用 %lf。
（3）%Lf 对应的是long double 类型，默认保留6位小数，可以指定小数位数，如： %.2Lf 表示保留2位小数。需要注意的是，输入输出 long double 类型都必须使用 %Lf 占位符。
%e 对应科学计数法表示的浮点数，可以指定尾数部分所保留的小数位数，如 %.2e 表示尾数部分保留两位小数。

![image-20250604192453691](.\images\image-20250604192453691.png)

代码:

```c
#include <stdio.h>

int main(){
    //double类型
    double d1 = 3.1415926;
    double d2 = .123456;
    double d3 = -2e12;
    double d4 = 1.9823e2;

    printf("d1=%f,d2=%.10f,d3=%.2lf,d4=%lf\n",d1,d2,d3,d4);
    printf("d1=%e,d2=%e,d3=%e,d4=%e \n",d1,d2,d3,d4);

    //float类型
    float f1 = 3.1415f;
    float f2 = .123456f;
    float f3 = -2e12f;
    float f4 = 1.9823e2f;
    printf("f1=%f,f2=%f,f3=%f,f4=%f \n",f1,f2,f3,f4);
    printf("f1=%e,f2=%e,f3=%e,f4=%e \n",f1,f2,f3,f4);


    //long double类型
    long double ld1=3.14L;
    long double ld2=.1234L;
    long double ld3=-2e3L;
    long double ld4=1.982e2L;

    printf("ld1=%LF,ld2=%LF,ld3=%LF,ld4=%LF \n",ld1,ld2,ld3,ld4);
    printf("ld1=%e,ld2=%e,ld3=%e,ld4=%e  \n",ld1,ld2,ld3,ld4);
    return 0;
}
```

输出:

```tex
d1=3.141593,d2=0.1234560000,d3=-2000000000000.00,d4=198.230000
d1=3.141593e+00,d2=1.234560e-01,d3=-2.000000e+12,d4=1.982300e+02 
f1=3.141500,f2=0.123456,f3=-1999999991808.000000,f4=198.229996 
f1=3.141500e+00,f2=1.234560e-01,f3=-2.000000e+12,f4=1.982300e+02 
ld1=3.140000,ld2=0.123400,ld3=-2000.000000,ld4=198.200000 
ld1=3.519001e-312,ld2=3.519001e-312,ld3=3.519001e-312,ld4=3.519001e-312 
```



### 4.3 字符串

字符类型 char 可以表示单个字符，如一个数字、一个字母、一个符号。

2）注意事项
（1）char类型的字面量是用单引号括起来的单个字符。
（2）可以使用转义字符 \ 表示特殊含义的字符。

| 转义字符 | 说明   |
| -------- | ------ |
| \b       | 退格   |
| \n       | 换行符 |
| \r       | 回车符 |
| \t       | 制表符 |
| \”       | 双引号 |
| \’       | 单引号 |
| \\       | 反斜杠 |

（3）多个字符称为字符串，在C语言中使用char数组表示，数组不是基本数据类型，而是构造类型。

格式占位符：使用%c表示char类型。

4）字符类型本质
（1）C语言中，char类型本质是一个整数，是ASCII码中对应的数字，存储长度是 1 个字节，char类型也可以进行数学运算。
（2）字符型同样分为signed char（无符号）和unsigned char（有符号），其中signed char取值范围-128 ~ 127，unsigned char取值范围0 ~ 255。默认是否带符号取决于当前运行环境。
（3）字符型数据在计算中存储和读取的过程：



ASCII（American Standard Code for Information Interchange）码是一种用于表示文本字符的字符编码标准，一共规定了128个字符的编码，比如空格“SPACE” 是32（二进制00100000），大写的字母A是65（二进制01000001）。

![image-20250604194052781](.\images\image-20250604194052781.png)

代码

```c
#include <stdio.h>

int main(){
    //char类型字符量需要使用单引号包裹
    char c1 = 'a';
    char c2 = '9';
    char c3 = '\t';
    printf("c1=%c,c2=%c,c3=%c| \n",c1,c2,c3);

    //char的本质是整数，可以进行运算。
    char b1 = 'b';
    char b2 = 102;
    printf("%c->%d \n",b1,b1);
    printf("%c->%d \n",b2,b2);
    printf("%c+%c=%d \n",b1,b2,b1+b2);

    //char类型的范围
    unsigned char c11 = 200; //无符号char的取值范围：0~255;
    signed char c12 = 200; //有符号的取值范围-128~127,此会超出范围
    char c13 = 200; //当前系统char的默认是signed char
    printf("c12=%d,c12=%d,c13=%d, \n",c11,c12,c13);

    return 0;
}
```

输出：

```tex
c1=a,c2=9,c3=   | 
b->98 
f->102 
b+f=200 
c12=200,c12=-56,c13=-56, 
```

### 4.4 布尔类型

布尔值用于表示真、假两种状态，通常用于逻辑运算和条件判断。

2）声明布尔类型的三种方式
（1）C89标准没有定义布尔类型，判断真假时以0为假，非0为真 ，但这种做法不直观，我们一般需要借助C语言的宏定义。

（2）C99标准提供了_Bool 型，_Bool仍是整数类型，但与一般整型不同的是，_Bool变量只能赋值为0或1，非0的值都会被存储为1。 

（3）C99标准还提供了一个头文件 <stdbool.h> 定义了bool代表_Bool，true代表1，false代表0。



宏定义的方式

```c
#include <stdio.h>

#define BOOL int
#define TRUE  1;
#define FALSE  0

int main(){

    BOOL isPASS = FALSE;
    BOOL ISOK = TRUE;

    printf("isPass=%d,isOK=%d \n",isPASS,ISOK);

    if(isPASS)
    {
        printf("Pass");
    }

    if(ISOK)
    {
        printf("OK");
    }


    return 0;
}
```

输出

```tex
isPass=0,isOK=1 
OK
```



C99中的_Bool类型

```c
#include <stdio.h>


int main(){

    _Bool isPASS = 0;
    _Bool ISOK = 1;

    printf("isPass=%d,isOK=%d \n",isPASS,ISOK);

    if(isPASS)
    {
        printf("Pass");
    }

    if(ISOK)
    {
        printf("OK");
    }


    return 0;
}
```

输出：

```tex
isPass=0,isOK=1 
OK
```



C99中的头文件方式

```c
#include <stdio.h>
#include <stdbool.h>


int main(){

    bool isPASS = false;
    bool ISOK = true;

    printf("isPass=%d,isOK=%d \n",isPASS,ISOK);

    if(isPASS)
    {
        printf("Pass");
    }

    if(ISOK)
    {
        printf("OK");
    }


    return 0;
}
```

输出：

```tex
isPass=0,isOK=1 
OK
```



### 4.5 获取数据的存储大小

使用sizeof 可以获取数据类型或变量、字面量的存储大小，单位是字节。sizeof返回一个size_t类型的无符号整数值，格式占位符是 %zu。
size_t 通常是 unsigned int 或 unsigned long 的别名，具体是哪个类型的别名，由系统和编译器决定。

```c
#include <stdio.h>

int main(){
    //计算数据类型的大小，必须使用括号将数据类型包裹起来。
    printf("char %zu \n",sizeof(char)); //char 1
    printf("short %zu \n",sizeof(short)); //short 2
    printf("int %zu \n",sizeof(int));     //int 4
    printf("long %zu \n",sizeof(long));  //long 4
    printf("long long : %zu \n",sizeof(long long)); //long long 8
    printf("float %zu \n",sizeof(float));  //float 4
    printf("double %zu \n",sizeof(double)); //double 8
    printf("long double %zu \n",sizeof(long double)); //long double 16
    printf("char %zu \n",sizeof(char));
    printf("\n");

    //计算字面量数据大小，可以省略括号
    printf("%zu \n",sizeof('a')); //使用Int类型，大小为4
    printf("%zu \n",sizeof(431));   //int 4
    printf("%zu \n",sizeof 4.31); //double 8
    printf("\n");

    //计算变量的大小，变量可以省略括号
    char a = 'A';
    int b = 90;
    long long c = 100;
    double d = 10.8;
    printf("a=%zu \n",sizeof(a));  // char 1
    printf("b=%zu \n",sizeof(b));  // int 4
    printf("c=%zu \n",sizeof(c));  // long long 8
    printf("d=%zu \n",sizeof(d));  // double 8
    printf("\n");

    return 0;
}
```

输出:

```tex
char 1 
short 2 
int 4 
long 4 
long long : 8 
float 4 
double 8 
long double 16 
char 1 

4 
4 
8 

a=1 
b=4 
c=8
d=8
```

### 4.6 数据类型转换

#### 4.6.1 自动类型转换（隐式）

**运算过程中的自动类型转换**
不同类型的数据进行混合运算，会发生数据类型转换，窄类型会自动转为宽类型，这样不会造成精度损失。

1）转换规则
（1）不同类型整数进行运算，窄类型整数自动转换为宽类型整数。
（2）不同类型浮点数进行运算，精度小的类型自动转换为精度大的类型。
（3）整数与浮点数进行运算，整数自动转换为浮点数。

```html
short -->  int --> unsigned int --> long --> unsigned long --> long long --> unsigned long lgon --> float -->double --> long double --> char
```

案例：

```c
#include <stdio.h>

int main(){
    //整数提升
    short s1 = 10;
    int n1 = 40000;
    
    //运算过程中，变量s1是char类型，会自动转换为int类型
    printf("%d \n",s1 + n1);


    //有符号的整数自动转换为无符号的整数
    int n2 = -100;
    unsigned int n3 = 20;
    //负数转换为无符号整数，两都绝对值的和是无符号整数的最大值加1
    printf("%u \n",n2+n3); //结果：4294967216

    //不同类型的浮点数运算，精度低的转换为精度高的
    float f1 = 1.25f;
    double d2 = 4.5431213;
    printf("%.10f \n",f1+d2);

    //整数与浮点数运算，整数转换为浮点数
    int n4 = 10;
    double d3 = 1.67;
    printf("%f",n4+d3);


    return 0;
}
```

输出：

```tex
40010 
4294967216 
5.7931213000 
11.670000
```



**赋值时的自动类型转换**

在赋值运算中，赋值号两边量的数据类型不同时，等号右边的类型将转换为左边的类型。 如果窄类型赋值给宽类型，不会造成精度损失；如果宽类型赋值给窄类型，会造成精度损失。

```c
#include <stdio.h>

int main(){
    
    //赋值，窄类型赋值给宽类型
    int a1 = 10;
    double a2 = a1;
    printf("%f \n",a2);


    //赋值 宽类型赋值给窄类型,直接做整数截取
    double b1 = 1.2;
    int b2 = b1;
    printf("%d",b2);

    return 0;
}
```

输出：

```tex
10.000000 
1
```



#### 4.6.2强制类型转换（显式转换）

隐式类型转换中的宽类型赋值给窄类型，编译器是会产生警告的，提示程序存在潜在的隐患，如果非常明确地希望转换数据类型，就需要用到强制（或显式）类型转换。

2）转换格式

（类型名）变量、常量或表达式

```c
#include <stdio.h>

int main(){
    double d1 = 1.934;
    double d2 = 4.2;
    int num1 = (int)d1 + (int)d2; //d1转为1，d2转为4，结果为5
    int num2 = (int)(d1 + d2); //d1+d2=6.134 结果为6
    int num3 = (int) (3.5 * 10 + 6 * 1.5); //35 + 9.0 = 44 强转结果为44;

    printf("num1=%d \n",num1);
    printf("num2=%d \n",num2);
    printf("num3=%d \n",num3);
    return 0;
}
```

结果：

```tex
num1=5 
num2=6 
num3=44 
```



## 6 运算符

### 6.1 介绍

运算符是一种特殊的符号，用以用于数据的运算、赋值和比较等。

表达式指的是一组运算数、运算符的组合，表达式一定具有值，一个变量或一个常量就是一个表达式，变量、常量与运算符也可以组成复杂一些的表达式。

1）按照操作数个数分类：
一元运算符（一目运算符）
二元运算符（二目运算符）
三元运算符（三目运算符）
2）按照功能分类：
算术运算符
赋值运算符
关系运算符
逻辑运算符
位运算符

掌握运算符

掌握一个运算符，关注以下几个方面：
（1）运算符的含义。
（2）运算符操作数的个数。
（3）运算符所组成的表达式值。
（4）运算符有无副作用，副作用指运算后是否会修改操作数的值。



### 6.2 算术运算符

| 运算符 | 描述         | 操作数个数 | 组成的表达式的值         | 副作用 |
| ------ | ------------ | ---------- | ------------------------ | ------ |
| +      | 正号         | 1          | 操作数本身               | 无     |
| -      | 负号         | 1          | 操作数符号取反           | 无     |
| +      | 加号         | 2          | 两个操作数之和           | 无     |
| -      | 减号         | 2          | 两个操作数之差           | 无     |
| *      | 乘号         | 2          | 两个操作数之积           | 无     |
| /      | 除号         | 2          | 两个操作数之商           | 无     |
| %      | 取模（取余） | 2          | 两个操作数相除的余数     | 无     |
| ++     | 自增         | 1          | 操作数自增前或自增后的值 | 有     |
| - -    | 自减         | 1          | 操作数自减前或自减后的值 | 有     |



**正负数**

```c
#include <stdio.h>

int main(){
    int x = 12;
    int x1 = -x,x2 = +x;
    int y = -67;
    int y1 = -y,y2=+y;
    printf("x1=%d,x2=%d \n",x1,x2);
    printf("y1=%d,y2=%d \n",y1,y2);
    return 0;
}
```

输出：

```tex
x1=-12,x2=12 
y1=67,y2=-67 
```



**加减乘除**

```c
#include <stdio.h>

int main(){
    int a = 5+2.5;
    printf("%d \n",a*a);//结果49

    double b = 6/4;
    printf("%f \n",b); //结果为1

    double c = 6.0 / 4;
    printf("%f \n",c); //结果为1.5
    return 0;
}
```

输出：

```tex
49 
1.000000 
1.500000 
```



**取模**

```c
#include <stdio.h>

int main(){
    int res1 = 10%3;
    printf("%d \n",res1); //结果为1

    int res2 = -10%3;
    printf("%d \n",res2); //结果-1

    int res3 = 10%-3;
    printf("%d\n",res3); //结果为1

    int res4 = -10%-3;
    printf("%d\n",res4); //结果为-1

    return 0;
}
```

输出：

```tex
1 
-1 
1
-1
```

注意：运算结果的符号与被模数也就是第一个操作数相同。



**自增和自减**

（1）自增、自减运算符可以写在操作数的前面也可以写在操作数后面，不论前面还是后面，对操作数的副作用是一致的。 
（2）自增、自减运算符在前在后，对于表达式的值是不同的。 如果运算符在前，表达式的值是操作数自增、自减之后的值；如果运算符在后，表达式的值是操作数自增、自减之前的值。

```c
#include <stdio.h>

int main(){
    int i1 = 10,i2=20;
    int i = i1++;
    printf("%d\n",i); //i返回10
    printf("%d\n",i1); //i1返回11

    i = ++i1;
    printf("%d\n",i); //返回12
    printf("%d\n",i1); //12

    i=i2--;
    printf("%d\n",i); //20
    printf("%d\n",i2); //19

    i = --i2;
    printf("%d\n",i); //18
    printf("%d\n",i2); //18


    return 0;
}
```

输出：

```tex
10
11
12
12
20
19
18
18
```



### 6.3 关系运算符（比较运算符）

| 运算符 | 描述     | 操作数个数 | 表达式的值 | 副作用 |
| ------ | -------- | ---------- | ---------- | ------ |
| ==     | 相等     | 2          | 0 或 1     | 无     |
| !=     | 不等     | 2          | 0 或 1     | 无     |
| <      | 小于     | 2          | 0 或 1     | 无     |
| >      | 大于     | 2          | 0 或 1     | 无     |
| <=     | 小于等于 | 2          | 0 或 1     | 无     |
| >=     | 大于等于 | 2          | 0 或 1     | 无     |



```c
#include <stdio.h>

int main(){
    int a = 8;
    int b = 7;
    printf("a>b的值:%d\n",a>b);  //true 1
    printf("a>=b的值:%d\n",a>=b); //true 1
    printf("a<b的值:%d\n",a <b); // false 0
    printf("a<=b的值: %d\n",a<b); // false 0
    printf("a==b的值:%d\n",a==b); // false 0
    printf("a!=b的值:%d\n",a!=b); // true 1
    return 0;
}
```

结果：

```tex
a>b的值:1
a>=b的值:1
a<b的值：0
a<=b的值: 0
a==b的值:0
a!=b的值:1
```



### 6.4 逻辑运算符

| 运算符 | 描述   | 操作数个数 | 表达式的值 | 副作用 |
| ------ | ------ | ---------- | ---------- | ------ |
| &&     | 逻辑与 | 2          | 0 或 1     | 无     |
| \|\|   | 逻辑或 | 2          | 0 或 1     | 无     |
| !      | 逻辑非 | 1          | 0 或 1     | 无     |

1）逻辑与 &&
（1）如果两个操作数都为真（非零），那么表达式的值为真，否则为假。
（2）如果第一个操作数为假，第二个操作数没有计算的必要了，这种现象称为短路现象。

```c
#include <stdio.h>

int main(){
    double score = 70;
    if(score >= 70 && score <= 100)
    {
        printf("成绩良好!\n");
    }
    else{
        printf("成绩一般 \n");
    }

    int a = 10,b=99;
    //短路现象
    if(a<2&&++b>99)
    {
        printf("短路不执行!\n");
    }
    printf("b=%d\n",b);

    return 0;
}
```

输出：

```tex
成绩良好!
b=99
```

2）逻辑或 ||
（1）只要有一个操作数为真，表达式的值就为真；两个操作数都为假，表达式的值为假。
（2）如果第一个操作数为真，第二个操作数没有计算的必要了，这种现象称为短路现象。

```c
#include <stdio.h>

int main(){
    double score = 70;
    if(score >= 70 || score <= 80){
        printf("out11\n");
    }
    else{
        printf("out22\n");
    }

    int a = 10,b=99;
    //短路现象
    if(a>5 || b++>100){
        printf("out33\n");
    }
    printf("b=%d\n",b);

    return 0;
}
```

输出：

```tex
out11
out33
b=99
```

3）逻辑非
操作数状态取反作为表达式的值。

```c
#include <stdio.h>

int main(){
    int score = 100;
    int res = score > 99;
    if(res){
        printf("hello,kk\n");
    }
    if(!res){
        printf("hello,not");
    }
    return 0;
}
```

输出：

```tex
hello,kk
```



### 6.5  赋值运算符

| 运算符 | 描述         | 操作数个数 | 表达式的值     | 副作用 |
| ------ | ------------ | ---------- | -------------- | ------ |
| =      | 赋值         | 2          | 左边操作数的值 | 有     |
| +=     | 相加赋值     | 2          | 左边操作数的值 | 有     |
| -=     | 相减赋值     | 2          | 左边操作数的值 | 有     |
| *=     | 相乘赋值     | 2          | 左边操作数的值 | 有     |
| /=     | 相除赋值     | 2          | 左边操作数的值 | 有     |
| %=     | 取余赋值     | 2          | 左边操作数的值 | 有     |
| <<=    | 左移赋值     | 2          | 左边操作数的值 | 有     |
| >>=    | 右移赋值     | 2          | 左边操作数的值 | 有     |
| &=     | 按位与赋值   | 2          | 左边操作数的值 | 有     |
| ^=     | 按位异或赋值 | 2          | 左边操作数的值 | 有     |
| \|=    | 按位或赋值   | 2          | 左边操作数的值 | 有     |

注意：
（1）赋值运算符的第一个操作数（左值）必须是变量的形式，第二个操作数可以是任何形式的表达式。
（2）赋值运算符的副作用针对第一个操作数。

```c
#include <stdio.h>

int main(){
    int a = 10,b=20,c=30;
    c+=3; //33
    c+=b; //53
    a+=1.7; //11

    printf("a=%d b=%d c=%d",a,b,c);
    return 0;
}
```

输出：

```tex
a=11 b=20 c=53
```



### 6.6 位运算符

| 运算符 | 描述     | 操作数个数 | 副作用 |
| ------ | -------- | ---------- | ------ |
| &      | 按位与   | 2          | 无     |
| \|     | 按位或   | 2          | 无     |
| ^      | 按位异或 | 2          | 无     |
| ~      | 按位取反 | 1          | 无     |
| <<     | 按位左移 | 2          | 无     |
| >>     | 按位右移 | 2          | 无     |

注意：操作数进行位运算的时候，以它的补码形式进行运算。

```c
#include <stdio.h>

int main(){
    int a = 17;  //原码： 00010001  反码: 00010001  补码: 00010001
    int b = -12; //原码： 10001100  反码：11110011  补码: 11110100
    printf("a&b=%d\n",a&b); //按位与：相同即为1, 00010000 得16
    printf("a|b=%d\n",a|b); //按位或，有1个为1即为1，11110101 得-11
    printf("a^b=%d\n",a^b); //按位异或者 相同即为0，不同即为1 11100101 得负27
    return 0;
}
```

输出：

```tex
a&b=16
a|b=-11
a^b=-27
```

位运算操作

```c
#include <stdio.h>

int main(){
    int a = 17;
    int b = -12;

    //接位左移
    printf("a<<2=%d\n",a<<2); // 原码00010001->反码00010001->补码00010001->左移01000100->反码01000100->原码01000100->68
    printf("b<<2=%d\n",b<<2); // 原码10001100->反码11110011->补码11110100->左移11010000->反码11001111->原码10110000->-48

    //按拉右移
    printf("a>>3=%d\n",a>>3); // 原码00010001->反码00010001->补码00010001->右移00000010->反码00000010->原码00000010->2
    printf("b>>3=%d\n",b>>3); // 原码10001100->反码11110011->补码11110100->右移11111110->反码11111101->原码10000010->-2
    return 0;
}
```

输出：

```tex
a<<2=68 
b<<2=-48
a>>3=2  
b>>3=-2 
```

### 6.7 三元运算符

条件表达式? 表达式1: 表达式2；

1）表达式最终取值

（1）如果条件表达式为非0（真），整个表达式的值是表达式1；

（2）如果条件表达式为0（假），整个表达式的值是表达式2；

```c
#include <stdio.h>

int main(){
    int a = 10;
    int b = 99;
    int res = a>b?a++:b--;
    int n2 = a>b?1.1:2.2;

    printf("a=%d\n",a);
    printf("b=%d\n",b);
    printf("res=%d\n",res);
    printf("n2=%d\n",n2);
    return 0;
}
```

输出：

```tex
a=10  
b=98  
res=99
n2=2 
```

求最大值

```c
#include <stdio.h>

int main(){
    int a = 10;
    int b = 100;
    int max = a > b ? a: b;
    printf("a和b中最大的数字:%d",max);
    return 0;
}
```

结果:

```tex
a和b中最大的数字:100
```

求三个数中的最大值

```c
#include <stdio.h>

int main(){
    int a = 10;
    int b = 100;
    int c = 199;

    int max = (a>b?a:b)>c?(a>b?a:b):c;
    printf("a、b、c中最大的数字:%d",max);
    return 0;
}
```

输出：

```tex
a、b、c中最大的数字:199
```

### 6.8 运算符优先级

| 优先级 | 运算符   | 名称或含义       | 结合方向 |
| ------ | -------- | ---------------- | -------- |
| 1      | []       | 数组下标         | 左到右   |
|        | ()       | 圆括号           |          |
|        | .        | 成员选择（对象） |          |
|        | ->       | 成员选择（指针） |          |
| 2      | -        | 负号运算符       | 右到左   |
|        | （类型） | 强制类型转换     |          |
|        | ++       | 自增运算符       |          |
|        | --       | 自减运算符       |          |
|        | *        | 取值运算符       |          |
|        | &        | 取地址运算符     |          |
|        | !        | 逻辑非运算符     |          |
|        | ~        | 按位取反运算符   |          |
|        | sizeof   | 长度运算符       |          |
| 3      | /        | 除               | 左到右   |
|        | *        | 乘               |          |
|        | %        | 余数（取模）     |          |
| 4      | +        | 加               | 左到右   |
|        | -        | 减               |          |
| 5      | <<       | 左移             | 左到右   |
|        | >>       | 右移             |          |
| 6      | >        | 大于             | 左到右   |
|        | >=       | 大于等于         |          |
|        | <        | 小于             |          |
|        | <=       | 小于等于         |          |
| 7      | ==       | 等于             | 左到右   |
|        | !=       | 不等于           |          |
| 8      | &        | 按位与           | 左到右   |
| 9      | ^        | 按位异或         | 左到右   |
| 10     | \|       | 按位或           | 左到右   |
| 11     | &&       | 逻辑与           | 左到右   |
| 12     | \|\|     | 逻辑或           | 左到右   |
| 13     | ?:       | 条件运算符       | 右到左   |
| 14     | =        | 赋值运算符       | 右到左   |
|        | /=       | 除后赋值         |          |
|        | *=       | 乘后赋值         |          |
|        | %=       | 取模后赋值       |          |
|        | +=       | 加后赋值         |          |
|        | -=       | 减后赋值         |          |
|        | <<=      | 左移后赋值       |          |
|        | >>=      | 右移后赋值       |          |
|        | &=       | 按位与后赋值     |          |
|        | ^=       | 按位异或后赋值   |          |
|        | \|=      | 按位或后赋值     |          |
| 15     | ,        | 逗号运算符       | 左到右   |

运算符优先级总结：

（1）不要过多的依赖运算的优先级来控制表达式的执行顺序，这样可读性太差，尽量`使用小括号来控制`表达式的执行顺序。

（2）不要把一个表达式写得过于复杂，如果一个表达式过于复杂，则把它`分成几步`来完成。

运算符优先级不用刻意地去记忆，总体上：一元运算符 > 算术运算符 > 关系运算符 > 逻辑运算符 > 三元运算符 > 赋值运算符。





## 程序控制

### 7 分支控制语句

#### 7.1 单向分支 

if (条件表达式)
{
    执行代码块；
}
说明：当条件表达式为真（非0时，就会执行代码块语句，条件表达式为假（0）时，不会执行代码块。



![image-20250606122139459](.\images\image-20250606122139459.png)

```c
#include <stdio.h>

int main(){
    int age = 0;
    printf("请输入年龄:");
    scanf("%d",&age);

    //根据年龄来判断处理
    if(age < 18)
    {
        printf("未成年人请在家长陪同下访问! ");
    }

    printf("欢迎继续访问");
    return 0;
}
```

输出：

```tex
请输入年龄:15
未成年人请在家长陪同下访问! 欢迎继续访问
```



#### 7.2 双向分支

if (条件表达式)
{
    执行代码块1；
}
else
{
    执行代码块2;
}
说明：当条件表达式成立（为真），执行代码块1，否则执行代码块2。

![image-20250606122336218](.\images\image-20250606122336218.png)

```c
#include <stdio.h>

int main(){
    int age = 0;
    printf("请输入年龄:");
    scanf("%d",&age);

    if(age > 18){
        printf("\n你年龄大于18岁，要对自己的行业负责!");
    }
    else{
        printf("\n你年龄还小，这次放过你了");
    }
    return 0;
}
```

输出:

```tex
请输入年龄:16

你年龄还小，这次放过你了

请输入年龄:22

你年龄大于18岁，要对自己的行业负责!
```



#### 7.3 多向分支-else if

1）基本语法
if (条件表达式1)
{
    执行代码块1;
}
else if (条件表达式2)
{
    执行代码块2;
}
... 
else
{
    执行代码块n;
}
说明：当条件表达式1成立时，即执行代码块1，如果表达式1不成立，才去判断表达式2是否成立，如果表达式2成立，就执行代码块2，以此类推如果所有的表达式都不成立，则执行else 的代码块。

![image-20250606122707827](.\images\image-20250606122707827.png)

张三参加考试，他和父亲达成协议，如果成绩不到 60 分没有任何奖励；如果成绩 60分（含）到80分，奖励一个肉夹馍；如果成绩80分（含）到90分，奖励一个 ipad；如果成绩90分及以上，奖励一部华为mate60 pro。 

```c
#include <stdio.h>

int main(){
    double score = 0;
    printf("请输入分数:");
    scanf("%lf",&score);

    if(score < 60){
        printf("你的成绩不及格，没有任何奖励！");
    }
    else if(score < 80){
        printf("奖励你一个肉夹馍");
    }
    else if(score < 90 )
    {
        printf("奖励你一个ipad!");
    }
    else if(score <= 100){
        printf("奖励你一个mate60 pro");
    }
    else{
        printf("分数无效!");
    }
    return 0;
}
```

输出：

```tex
请输入分数:70
奖励你一个肉夹馍


请输入分数:80
奖励你一个ipad!

请输入分数:90
奖励你一个mate60 pro

请输入分数:50
你的成绩不及格，没有任何奖励！
```



#### 7.4 多向分支-switch

```c
switch (表达式)
{
    case 常量值1:
       语句块1;
        break;
    case 常量值2:
       语句块2;
        break;
    case 常量值n:
        语句块n;
        break;
    default:
       语句块n + 1;
}
```

说明：

（1）switch后面表达式的值必须是一个整型（char、short, int, long等）或枚举类型。

（2）case后面的值必须是常量，而不能是变量。

（3）default是可选的，当没有匹配的case时，执行default。

（4）break语句可以使程序跳出switch语句块，如果没有break，会执行下一个case 语句块，直到遇到break或者执行到switch结尾，这个现象称为穿透。

![image-20250606221508705](.\images\image-20250606221508705.png)

案例：

编写一个程序，该程序可以接收一个字符，比如：a、b、c、d其中a表示星期一，b表示星期二…，根据用户的输入显示相应的信息，要求使用switch语句。

```c
#include <stdio.h>

int main(){

    char input;
    printf("输入一个字符(a,b,c,d):");
    scanf("%c",&input);

    switch (input)
    {
    case 'a':
        printf("星期一，穿衬衣!");
        break;
    case 'b':
        printf("星期二，穿短袖!");
        break;
    case 'c':
        printf("星期三, 穿T恤!");
        break;
    case 'd':
        printf("星期四，穿格式衫!");
        break;
    default:
        printf("没有相应的匹配字符!");
        break;
    }


    return 0;
}
```

输出：

```tex
输入一个字符(a,b,c,d):a
星期一，穿衬衣!

输入一个字符(a,b,c,d):B
没有相应的匹配字符!

输入一个字符(a,b,c,d):b
星期二，穿短袖!

输入一个字符(a,b,c,d):d
星期四，穿格式衫!
```



编写程序，输入月份，输出该月份有多少天。说明：1月、3月、5月、7月、8月、10月、12月有31天，4月、6月、9月、11月有30天，2月有28 天或 29天。

```c
#include <stdio.h>

int main(){
    int month;
    printf("请输入一个月份(1-12):");
    scanf("%d",&month);

    switch (month)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        printf("这个月有31天");
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        printf("这个月有30天！");
        break;
    case 2:
        printf("这个月有28天或者29天"); 
        break;  
    default:
        printf("输入的月份错误!");
        break;
    }
    return 0;
}
```

输出:

```tex
请输入一个月份(1-12):5
这个月有31天

请输入一个月份(1-12):8
这个月有31天

请输入一个月份(1-12):9
这个月有30天！

请输入一个月份(1-12):33
输入的月份错误!

请输入一个月份(1-12):2
这个月有28天或者29天
```



1）switch和if else if的比较

（1）如果判断条件是判等，而且符合整型、枚举类型，虽然两个语句都可以使用，建议使用swtich语句。

（2）如果判断条件是区间判断，大小判断等，使用if else if。



#### 7.5 嵌套分支

嵌套分支是指，在一个分支结构中又嵌套了另一个分支结构，里面的分支的结构称为内层分支，外面的分支结构称为外层分支。嵌套分支层数不宜过多，建议最多不要超过3层。

```c
if ()
{
    if ()
    {
    }
    else
    {
        switch ()
        {
        case …
        }
    }
}

```

输入油号（92或95）和油量，计算需要支付的金额。92号汽油每升4元，如果油量大于等于40升，每升3.5元；95号汽油每升5元，如果油量大于等于40升，每升4.5元。

```c
#include <stdio.h>

int main(){
    int oilNum,oilL;
    printf("请输入加油号(92或者95):");
    scanf("%d",&oilNum);
    printf("请输入加油量:");
    scanf("%d",&oilL);

    double price;

    if(oilNum == 92)
    {
        if(oilL >= 40)
        {
            price = 3.5;
        }
        else{
            price = 4;
        }
    }
    else if(oilNum == 95)
    {
        if(oilL >= 40)
        {
            price = 4.5;
        }
        else{
            price = 5;
        }
    }
    else
    {
        printf("加油号输入错误！");
    }
    
    double sum = oilL * price;
    printf("支付的金额为:%lf",sum);

    return 0;
}
```

结果：

```tex
请输入加油号(92或者95):92
请输入加油量:50
支付的金额为:175.000000


请输入加油号(92或者95):95
请输入加油量:20
支付的金额为:100.000000
```



### 8. 循环控制语句

#### 8.1 while循环

```c
while (循环条件表达式)
{
    循环体语句;
}
```

说明：

（1）当循环条件表达式成立，就执行循环体语句，直到条件不成立停止循环

（2）为避免死循环，循环条件表达式不能永远成立，且随着循环次数增加，应该越来越趋向于不成立。

![image-20250606224325981](.\images\image-20250606224325981.png)



案例：（1）输出10次 "我第n天吃了n个韭菜馅的包子"。

```c
#include <stdio.h>

int main(){
    //（1）输出10次 "我第n天吃了n个韭菜馅的包子"。
    int num = 10;
    int index = 0;
    while(index < num){
        printf("我第%d天吃了%d个韭菜馅的包子!\n",index,index);
        index++;
    }

    return 0;
}
```

输出：

```tex
我第0天吃了0个韭菜馅的包子!
我第1天吃了1个韭菜馅的包子!
我第2天吃了2个韭菜馅的包子!
我第3天吃了3个韭菜馅的包子!
我第4天吃了4个韭菜馅的包子!
我第5天吃了5个韭菜馅的包子!
我第6天吃了6个韭菜馅的包子!
我第7天吃了7个韭菜馅的包子!
我第8天吃了8个韭菜馅的包子!
我第9天吃了9个韭菜馅的包子!
```

（2）循环输出数字 7~15

```c
#include <stdio.h>

int main(){
    //（2）循环输出数字 7~15
    int start = 7;
    int end = 15;
    while(start <= end)
    {
        printf("当前数字是:%d \n",start);
        start++;
    }
    return 0;
}
```

输出：

```tex
当前数字是:7 
当前数字是:8 
当前数字是:9 
当前数字是:10 
当前数字是:11 
当前数字是:12 
当前数字是:13 
当前数字是:14 
当前数字是:15 
```



（3）倒序输出数字 56 ~ 43

```c
#include <stdio.h>

int main(){
    //（3）倒序输出数字 56 ~ 43
    int end = 56;
    int start = 43;
    while(end >= start)
    {
        printf("倒序数字:%d \n",end);
        end--;
    }
    return 0;
}
```

输出：

```tex
倒序数字:56 
倒序数字:55 
倒序数字:54
倒序数字:53
倒序数字:52
倒序数字:51
倒序数字:50
倒序数字:49
倒序数字:48
倒序数字:47
倒序数字:46
倒序数字:45
倒序数字:44
倒序数字:43
```



（4）输出10（包括10）以内所有的偶数

```c
#include <stdio.h>

int main(){
    //（4）输出10（包括10）以内所有的偶数
    int num = 10;
    while(num >= 0){
        if(num % 2 == 0)
        {
            printf("偶数:%d \n",num);
        }
        
        num--;
    }
    return 0;
}
```

输出:

```tex
偶数:10 
偶数:8 
偶数:6 
偶数:4 
偶数:2 
偶数:0 
```



（5）计算100以内（包括100）所有数字的和

```c
#include <stdio.h>

int main(){
    //（5）计算100以内（包括100）所有数字的和
    int end = 100;
    int index = 0;
    int sum = 0;
    while(index <= end){
        sum+=index;
        index++;
    }

    printf("100以内所有数字和:%d",sum);
    return 0;
}
```

输出:

```tex
100以内所有数字和:5050
```





(6) 计算10以内所有奇数的乘积

```c
#include <stdio.h>

int main(){
    
    int start = 1;
    int end = 10;

    int sum = 1;
    while(start < end)
    {
        if(start % 2 != 0)
        {
            sum *= start;
        }

        start++;
    }

    printf("10以内的奇数乘积是:%d",sum);

    return 0;
}
```

结果：

```tex
10以内的奇数乘积是:945
```



(7)案例 – 不确定循环

不确定循环直到循环表达式不成立之前，不确定循环次数，不需要循环变量。

实现需求：输入6位数密码，直到密码匹配成功结束循环。

```c
#include <stdio.h>

int main(){
    int passwrod = 654321;
    int input = 0;
    while(passwrod != input)
    {
        printf("请输入密码:");
        scanf("%d",&input);
    }
    printf("密码正常，欢迎回家！");

    return 0;
}
```

结果：

```tex
请输入密码:123456
请输入密码:654321
密码正常，欢迎回家！
```



#### 8.2 do-while循环

```c
do
{
    循环体语句；
} while (循环条件表达式);
```

说明：

（1）do…while循环是先执行再判断，但只有第一次循环语句的执行没有经过判断，以后的每一次仍然先经过判断。

（2）注意while后面的分号不能省略。

![image-20250607213953936](.\images\image-20250607213953936.png)



案例：（1）输出10次 "我第n天吃了n个韭菜馅的包子"。

```c
#include <stdio.h>

int main(){
    int start = 0;
    int end = 10;
    do{
        printf("第%d天我吃%d个饺子。\n",start,start);
        start++;
    } while(start <= end);

    printf("结束后start的值:%d",start);
    return 0;
}
```

输出：

```tex
第0天我吃0个饺子。
第1天我吃1个饺子。
第2天我吃2个饺子。
第3天我吃3个饺子。
第4天我吃4个饺子。
第5天我吃5个饺子。
第6天我吃6个饺子。
第7天我吃7个饺子。
第8天我吃8个饺子。
第9天我吃9个饺子。
第10天我吃10个饺子。
结束后start的值:11
```

（2）循环输出数字 7~15

```c
#include <stdio.h>

int main(){
    int start = 7;
    int end = 15;
    do{
        printf("输出当前数字:%d\n",start);
        start++;
    }while(start <= end);
    return 0;
}
```

输出：

```tex
输出当前数字:7 
输出当前数字:8 
输出当前数字:9 
输出当前数字:10
输出当前数字:11
输出当前数字:12
输出当前数字:13
输出当前数字:14
输出当前数字:15
```



（3）倒序输出数字 56 ~ 43

```c
#include <stdio.h>

int main(){
    int start = 56;
    int end = 43;
    do
    {
        printf("输出当前数字%d\n",start);
        start--;
    } while (start >= end);
    

    return 0;
}
```

输出：

```tex
输出当前数字56
输出当前数字55
输出当前数字54
输出当前数字53
输出当前数字52
输出当前数字51
输出当前数字50
输出当前数字49
输出当前数字48
输出当前数字47
输出当前数字46
输出当前数字45
输出当前数字44
输出当前数字43
```



（4）输出10（包括10）以内所有的偶数

```c
#include <stdio.h>

int main(){
    int start = 0;
    int end = 10;
    do
    {
        if(start % 2 == 0)
        {
            printf("当前偶数是:%d\n",start);
        }
        start++;
    } while (start <= end);
    return 0;
}
```

输出:

```tex
当前偶数是:0 
当前偶数是:2 
当前偶数是:4 
当前偶数是:6 
当前偶数是:8 
当前偶数是:10
```



（5）计算100以内（包括100）所有数字的和

```c
#include <stdio.h>

int main(){
    int start = 0;
    int end = 100;
    int sum = 0;
    do
    {
        sum += start;
        start++;
    } while (start <= end);
    
    printf("100以内所有数字的和是:%d",sum);

    return 0;
}
```

输出:

```tex
100以内所有数字的和是:5050
```





(6) 计算10以内所有奇数的乘积

```c
#include <stdio.h>

int main(){
    int start = 1;
    int end = 10;
    int sum = 1;
    do
    {
        if(start % 2 != 0)
        {
            sum*=start;
        }
        start++;
    } while (start <= end);
    
    printf("10以内的奇数乘积是:%d",sum);

    return 0;
}
```

结果：

```tex
10以内的奇数乘积是:945
```



(7)案例 – 不确定循环

不确定循环直到循环表达式不成立之前，不确定循环次数，不需要循环变量。

实现需求：输入6位数密码，直到密码匹配成功结束循环。

```c
#include <stdio.h>

int main(){
    int pwd = 23456;
    int input;
    while (input != pwd)
    {
        printf("请输入密码:");
        scanf("%d",&input);
    }

    printf("当前密码正常，欢迎回家！");
    
    return 0;
}
```

结果：

```tex
请输入密码:123456
请输入密码:231
请输入密码:23456
当前密码正常，欢迎回家！
```



#### 8.3 for循环

```c
for (循环变量初始化; 循环条件表达式; 循环变量变化)
{
    循环语句;
}
```

说明：

（1）循环变量初化只执行一次，且循环开始前就执行。

（2）循环条件表达式每次循环都执行，同while循环一样，每次先判断后执行循环体语句。

（3）循环标记变量变化每次循环都执行，在大括号中循环体语句之后执行。

![image-20250607225712036](.\images\image-20250607225712036.png)



案例：（1）输出10次 "我第n天吃了n个韭菜馅的包子"。

```c
#include <stdio.h>

int main(){
    
    for(int i = 0; i <= 10; i++)
    {
        printf("第%d天我吃了%d个饺子\n",i,i);
    }
    return 0;
}
```

输出：

```tex
第0天我吃了0个饺子
第1天我吃了1个饺子
第2天我吃了2个饺子
第3天我吃了3个饺子
第4天我吃了4个饺子
第5天我吃了5个饺子
第6天我吃了6个饺子
第7天我吃了7个饺子
第8天我吃了8个饺子  
第9天我吃了9个饺子  
第10天我吃了10个饺子
```

（2）循环输出数字 7~15

```c
#include <stdio.h>

int main(){
    for(int i = 7 ; i <= 15 ; i++){
        printf("输出数字%d\n",i);
    }
    return 0;
}
```

输出：

```tex
输出数字7 
输出数字8 
输出数字9 
输出数字10
输出数字11
输出数字12
输出数字13
输出数字14
输出数字15
```



（3）倒序输出数字 56 ~ 43

```c
#include <stdio.h>

int main(){
    for(int i = 56 ; i > 43 ; i--){
        printf("数字:%d \n",i);
    }
    return 0;
}
```

输出：

```tex
数字:56 
数字:55
数字:54
数字:53
数字:52
数字:51
数字:50
数字:49
数字:48
数字:47 
数字:46
数字:45
数字:44
```



（4）输出10（包括10）以内所有的偶数

```c
#include <stdio.h>

int main(){
    for(int i=0; i<=10;i++){
        if(i%2==0)
        {
            printf("当前偶数:%d\n",i);
        }
    }
    return 0;
}
```

输出:

```tex
当前偶数:0 
当前偶数:2 
当前偶数:4 
当前偶数:6 
当前偶数:8 
当前偶数:10
```



（5）计算100以内（包括100）所有数字的和

```c
#include <stdio.h>

int main(){
    int sum = 0;
    for(int i=0;i<=100;i++)
    {
        sum+=i;
    }
    printf("100以内的数字和是:%d",sum);
    return 0;
}
```

输出:

```tex
100以内的数字和是:5050
```





(6) 计算10以内所有奇数的乘积

```c
#include <stdio.h>

int main(){
    int sum = 1;
    for(int i=1;i<10;i++){
        if(i%2!=0)
        {
            sum*=i;
        }
    }
    printf("10以内所有奇数乘积:%d",sum);
    return 0;
}
```

结果：

```tex
10以内所有奇数乘积:945
```



(7)案例 – 不确定循环

不确定循环直到循环表达式不成立之前，不确定循环次数，不需要循环变量。

实现需求：输入6位数密码，直到密码匹配成功结束循环。

```c
#include <stdio.h>

int main(){
    int pwd = 123456;
    int input;
    for(;input != pwd;)
    {
        printf("请输入6位密码:");
        scanf("%d",&input);
    }

    printf("密码正确，欢迎回家!");

    return 0;
}
```

结果：

```tex
请输入6位密码:258741
请输入6位密码:369852
请输入6位密码:123456
密码正确，欢迎回家!
```





#### 8.4 多重循环

（1）将一个循环放在另一个循环体内，就形成了嵌套循环， 其中，for、while、do…while均可以作为外层循环和内层循环，建议一般使用两层，最多不要超过3层，如果嵌套循环过多，会造成可读性降低。

（2）如果外层循环次数为m次，内层为n次，则内层循环体实际上需要执行m*n次。



乘法表案例

```c
#include <stdio.h>

int main(){

    for(int i = 1 ; i <= 9 ;i++){
        for(int j = 1 ; j <= i; j++ )
        {
            printf("%d*%d=%d \t",i,j,i*j);
        }
        printf("\n");
    }


    return 0;
}
```

输出:

```tex
1*1=1 
2*1=2   2*2=4 
3*1=3   3*2=6   3*3=9 
4*1=4   4*2=8   4*3=12  4*4=16 
5*1=5   5*2=10  5*3=15  5*4=20  5*5=25         
6*1=6   6*2=12  6*3=18  6*4=24  6*5=30  6*6=36 
7*1=7   7*2=14  7*3=21  7*4=28  7*5=35  7*6=42  7*7=49         
8*1=8   8*2=16  8*3=24  8*4=32  8*5=40  8*6=48  8*7=56  8*8=64 
9*1=9   9*2=18  9*3=27  9*4=36  9*5=45  9*6=54  9*7=63  9*8=72  9*9=81
```

形式2：

```c
#include <stdio.h>

int main(){

    for(int i = 9 ; i > 0 ;i--){
        for(int j = 1 ; j <= i; j++ )
        {
            printf("%d*%d=%d \t",i,j,i*j);
        }
        printf("\n");
    }


    return 0;
}
```

输出：

```tex
9*1=9   9*2=18  9*3=27  9*4=36  9*5=45  9*6=54  9*7=63  9*8=72  9*9=81 
8*1=8   8*2=16  8*3=24  8*4=32  8*5=40  8*6=48  8*7=56  8*8=64 
7*1=7   7*2=14  7*3=21  7*4=28  7*5=35  7*6=42  7*7=49         
6*1=6   6*2=12  6*3=18  6*4=24  6*5=30  6*6=36 
5*1=5   5*2=10  5*3=15  5*4=20  5*5=25         
4*1=4   4*2=8   4*3=12  4*4=16 
3*1=3   3*2=6   3*3=9 
2*1=2   2*2=4         
1*1=1
```

形式3：

```c
#include <stdio.h>

int main(){
    for(int i = 1;i <= 9;i++)
    {
        for(int j=1 ; j <= 9-i;j++)
        {
            printf("\t");
        }
        for(int j = 1 ; j <= i ;j++)
        {
            printf("%d*%d=%d\t",i,j,i*j);
        }
        printf("\n");
    }

    return 0;
}
```

输出：

```tex
                                                                1*1=1 
                                                        2*1=2   2*2=4 
                                                3*1=3   3*2=6   3*3=9 
                                        4*1=4   4*2=8   4*3=12  4*4=16
                                5*1=5   5*2=10  5*3=15  5*4=20  5*5=25
                        6*1=6   6*2=12  6*3=18  6*4=24  6*5=30  6*6=36
                7*1=7   7*2=14  7*3=21  7*4=28  7*5=35  7*6=42  7*7=49
        8*1=8   8*2=16  8*3=24  8*4=32  8*5=40  8*6=48  8*7=56  8*8=64
9*1=9   9*2=18  9*3=27  9*4=36  9*5=45  9*6=54  9*7=63  9*8=72  9*9=81
```

形式4：

```c
#include <stdio.h>

int main(){
    for(int i=9;i>0;i--){
        for(int j=1;j<=9-i;j++)
        {
            printf("\t");
        }
        for(int j=1;j<=i;j++)
        {
            printf("%d*%d=%d\t",i,j,i*j);
        }
        
        printf("\n");
    }
    
    return 0;
}
```

输出：

```tex
9*1=9   9*2=18  9*3=27  9*4=36  9*5=45  9*6=54  9*7=63  9*8=72  9*9=81
        8*1=8   8*2=16  8*3=24  8*4=32  8*5=40  8*6=48  8*7=56  8*8=64
                7*1=7   7*2=14  7*3=21  7*4=28  7*5=35  7*6=42  7*7=49
                        6*1=6   6*2=12  6*3=18  6*4=24  6*5=30  6*6=36
                                5*1=5   5*2=10  5*3=15  5*4=20  5*5=25
                                        4*1=4   4*2=8   4*3=12  4*4=16
                                                3*1=3   3*2=6   3*3=9
                                                        2*1=2   2*2=4
                                                                1*1=1
```



#### 8.5 跳转控制break

break语句用于终止某个语句块的执行，用在switch语句或者循环语句中。

![image-20250608214459241](.\images\image-20250608214459241.png)

编写程序，要求输入一个数字，判断该数字是否是质数。

说明：质数指只能被1和自身整除且大于1的数字。

```c
#include <stdio.h>
#include <stdbool.h>


int main(){
    
    //要求输入一个数字，判断该数字是否是质数。
    int num;

    printf("请输入一个数字:");
    scanf("%d",&num);

    //标识当前是否为质数
    bool isPrime = num > 1 ? 1 : 0;

    for(int i = 2; i<num/2;i++)
    {
        if(num % i == 0)
        {
            isPrime = 0;
            break;
        }
    }

    if(isPrime)
    {
        printf("%d是质数！",num);
    }
    else
    {
        printf("%d 不是质数!",num);
    }

    return 0;
}
```

输出：

```tex
请输入一个数字:7
7是质数！

请输入一个数字:12
12 不是质数!
```

#### 8.6 跳转控制continue

continue语句用于结束本次循环，继续执行下一次循环。

![image-20250608215439682](.\images\image-20250608215439682.png)

输出100以内（包括100）的数字，跳过那些7的倍数或包含7的数字。

```c
#include <stdio.h>

int main(){
    for(int i=0;i<=100;i++)
    {
        //跳过那些7的倍数或包含7的数字。   

        //i % 7==0  7的倍数
        //i % 10==7 个位包含7的数字
        //i / 10=7  十位包含7的数字
        if(i % 7==0 
        || i % 10==7
        || i / 10==7){
            continue;
        }
        printf("数字：%d \t",i);
        if(i % 10 == 0)
        {
            printf("\n");
        }
    }
    return 0;
}
```

输出：

```tex
数字：1         数字：2         数字：3         数字：4         数字：5         数字：6         数字：8         数字：9         数字：10 
数字：11        数字：12        数字：13        数字：15        数字：16        数字：18        数字：19        数字：20 
数字：22        数字：23        数字：24        数字：25        数字：26        数字：29        数字：30 
数字：31        数字：32        数字：33        数字：34        数字：36        数字：38        数字：39        数字：40 
数字：41        数字：43        数字：44        数字：45        数字：46        数字：48        数字：50 
数字：51        数字：52        数字：53        数字：54        数字：55        数字：58        数字：59        数字：60 
数字：61        数字：62        数字：64        数字：65        数字：66        数字：68        数字：69        数字：80 
数字：81        数字：82        数字：83        数字：85        数字：86        数字：88        数字：89        数字：90 
数字：92        数字：93        数字：94        数字：95        数字：96        数字：99        数字：100 
```



#### 8.7 goto语句

`goto`语句是一种跳转语句，它允许程序控制流程在代码中跳转到带有标签（label）的语句处，标签（label）的名称可以自行设置，需要满足标识符规范。

注意，我们在开发中不建议使用goto语句，但我们需要掌握 goto 语句的执行流程，以能够看懂其他开发者的代码中可能出现的 goto 语句。

```c
goto 标签名  // 跳转到指定的标签（label）处
...
标签名:      // 定义一个标签（label）
语句;
```

注意：goto 后面如果引用了没有定义的标签，编译器会报错！

![image-20250608220556896](.\images\image-20250608220556896.png)



```c
#include <stdio.h>

int main(){
    printf("start \n");
    goto label1;

    printf("ok1 \n");
    printf("ok2 \n");
    label1:
    printf("ok2 \n");
    printf("ok4 \n");
    return 0;
}
```

输出：

```tex
start 
ok2   
ok4 
```

使用goto语句实现循环输出数字1 ~ 5。

```c
#include <stdio.h>

int main(){
    int num = 1;

loopStart:
    if(num<5){
        printf("%d\n",num);
        num++;
        goto loopStart;
    }

    return 0;
}
```

输出：

```tex
1
2
3
4
```



### 练习

（1）实现判断一个整数，属于哪个范围：大于0；小于0；等于0。

```c
#include <stdio.h>

int main(){
    int num;
    printf("请输入一个整数:");
    scanf("%d",&num);

    if(num > 0 )
    {
        printf("输入数字大于0");
    }

    if(num < 0)
    {
        printf("输入数字小于0");
    }

    if(num == 0)
    {
        printf("输入数字等于0");
    }

    return 0;
}
```

输出：

```tex
请输入一个整数:10
输入数字大于0

请输入一个整数:-15
输入数字小于0

请输入一个整数:0
输入数字等于0
```



（2）判断一个年份是否为闰年。

```c
#include <stdio.h>

int main(){
    int year;
    printf("请输入一个年份");
    scanf("%d",&year);

    if(year % 400 == 0  || year % 4 == 0)
    {
        printf("为一个闰年");
    }
    else{
        printf("不是一个闰年");
    }
    
    return 0;
}
```

输出：

```tex
请输入一个年份2024
为一个闰年

请输入一个年份2023
不是一个闰年
```

（3）判断一个整数是否是水仙花数，所谓水仙花数是指一个3位数，其各个位上数字立方和等于其本身，例如：153 = 1*1*1 + 5*5*5 + 3*3*3。

```c
#include <stdio.h>

int main(){
    int num;
    printf("请输入一个数字，以判断是否为水仙花数:");
    scanf("%d",&num);

    int num1 = num/100;
    int num2 = (num  - num1 * 100)/10;
    int num3 = num  - num1 * 100 - num2*10;

    int num1Sum = num1 * num1 * num1;
    int num2Sum = num2 * num2 * num2;
    int num3Sum = num3 * num3 * num3;
    int sum = num1Sum + num2Sum + num3Sum;

    if(num == sum)
    {
        printf("数字是一个水化花数");
    }
    else{
        printf("输入的数字不是一个水仙花数");
    }

    return 0;
}
```

输出：

```tex
请输入一个数字，以判断是否为水仙花数:152
输入的数字不是一个水仙花数

请输入一个数字，以判断是否为水仙花数:153
数字是一个水化花数
```

（4）编写程序，根据输入的月份和年份，求出该月的天数（1-12）, 就是需要考虑闰年（2月份 29）和平年（2月份 28）。

```c
#include <stdio.h>
#include <stdbool.h>

int main()
{
    int year, month;

    printf("请输入年份：");
    scanf("%d", &year);

    printf("请输入月份: ");
    scanf("%d", &month);

    bool isRun = false;

    // 判断是否为闰年
    if (year % 400 == 0 || (year % 4 == 0))
    {
        printf("%d年为闰年", year);
        isRun = true;
    }
    else
    {
        printf("%d年非闰年", year);
    }
    printf("\n");

    switch (month)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
        printf("%d月有31天",month);
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        printf("%d月有30天",month);
        break;
    case 2:
        if(isRun)
        {
            printf("2月为29天");
        }
        else{
            printf("2月为28天");
        }
        break;
    default:
        break;
    }

    return 0;
}
```

输出：

```tex
请输入年份：2024
请输入月份: 2
2024年为闰年
2月为29天

请输入年份：2023
请输入月份: 2
2023年非闰年
2月为28天 
```



（5）输入星期几，如果是星期一到星期三，打印：AAA，星期四到星期五，打印： BBB，星期六到星期日，打印CCC，如果都不是，提示“输入错误”。

```c
#include <stdio.h>

int main()
{
    int week = 0;
    printf("请输入星期几：");
    scanf("%d", &week);

    switch (week)
    {
    case 1:
    case 2:
    case 3:
        printf("AAA");
        break;
    case 4:
    case 5:
        printf("BBB");
        break;
    case 6:
    case 7:
        printf("CCC");
        break;
    default:
        printf("输入错误");
        break;
    }

    return 0;
}

```

输出:

```tex
请输入星期几：3
AAA

请输入星期几：6
CCC

请输入星期几：99
输入错误
```





（6）请使用for、while、do—while 三种形式打印1000——9999年内所有的闰年。

（7）输出小写的a-z以及大写的Z—A。

（8）求出1-1/2+1/3-1/4…..1/100的和。





## 9 数姐

数组相关概念

（1）**数组名**：本质上是一个标识符常量，命名需要符合标识符规范。

（2）**元素**：同一个数组中的元素必须是相同的数据类型。

（3）**下标（索引、角标）**：从0开始的连续数字。

（4）**数组的长度**：表示元素的个数

数组的特点

（1）创建数组时会在内存中开辟一整块连续的空间，占据的空间的大小，取决于数组的长度和数组中元素的类型。

（2）数组中的元素在内存中是依次紧密排列的且有序的。

（3）数组一旦初始化完成，其长度就是确定的，数组的长度一旦确定，就不能修改。

（4）我们可以直接通过索引（下标）获取指定位置的元素，速度很快。



### 9.1 数组的定义

```c
#include <stdio.h>

int main(){
    //方式1，先指定元素个数和类型，再进行初始化
    int arr1[3];
    //定义完成后再组元素赋值
    arr1[0]=10;
    arr1[1]=20;
    arr1[2]=30;

    //方式2，指定元素的类型和个数并同时进行初始化
    int arr[2]={4,5,6};

    //方式3，指定元素的类型，不指定元素的个数，同时进行初始化。
    int arr3[]={7,8,9,10};

    return 0;
}
```



### 9.2 数据的访问

```c
#include <stdio.h>

int main(){
    int nums[4]= {10,20,30,40};

    //修改元素的值
    nums[1]+=50;

    //读取元素
    printf("第1个元素的值%d\n",nums[0]);
    printf("第2个元素的值%d\n",nums[1]);
    printf("第3个元素的值%d\n",nums[2]);
    printf("第5个元素的值%d\n",nums[3]);
    return 0;
}

```

输出：

```tex
第1个元素的值10
第2个元素的值70
第3个元素的值30
第5个元素的值40
```





### 9.3 数组越界

```c
#include <stdio.h>

int main(){
    //定义数组
    int array[] = {10,20,30};
    
    printf("下标为0的元素:%d",array[0]); //10
    printf("下标为2的元素:%d",array[2]); //30
    printf("下标为-1的元素:%d",array[-1]); //得到不确定的结果
    printf("下标为5的元素:%d",array[5]); //得到不确定的结果

    return 0;
}
```

输出：

```tex
下标为0的元素:10        
下标为2的元素:30        
下标为-1的元素:197478344
下标为5的元素:1674908399
```



### 9.4 计算数组的长度

数组长度（元素个数）是在数组定义时明确指定且固定的，我们不能在运行时直接获取数组长度，但是，我们可以通过sizeof 运算符间接计算出数组长度，计算步骤如下：

（1）使用sizeof运算符计算出整个数组的字节长度。

（2）由于数组成员是同一类型，每个元素字节长度相等，用整个数组的字节长度除以单个元素的字节长度就可以得到数组的长度。



```c
#include <stdio.h>

int main(){
    //定义数组
    int array[] = {10,20,30,40,50,60};

    //计算数组总的字节长度
    int arrayByteLen = sizeof array;

    printf("数组的总字节长度:%d\n",arrayByteLen);

    //单个长度
    int itemByteLen = sizeof array[0];

    //计算数组的长度
    int arrayLen = arrayByteLen / itemByteLen;

    printf("数组的长度:%d",arrayLen);

    return 0;
}
```

输出：

```tex
数组的总字节长度:24
数组的长度:6    
```



### 9.5 数组的遍历

遍历数组是指按顺序访问数组中的每个元素，以便读取或修改它们，编程中一般使用循环结构对数组进行遍历。

```c
#include <stdio.h>

int main(){
    //定义数组
    int arr[5] = {1,2,3,4,5};

    //计算数组的长度
    int len = sizeof(arr)/sizeof(arr[0]);

    //遍历数组的元素
    printf("遍历数组中的元素:\n");
    for(int i = 0; i< len ; i++){
        printf("下标:%d,值:%d \n",i,arr[i]);
    }

    return 0;
}
```

输出：

```tex
遍历数组中的元素:
下标:0,值:1 
下标:1,值:2 
下标:2,值:3 
下标:3,值:4 
下标:4,值:5 
```

还可以通过数组的遍历完成对数组的初始化赋值

```c
#include <stdio.h>

int main(){
    //声明数组
    int arr[10];

    //计算数组的长度
    int len = sizeof(arr) / sizeof(arr[0]);

    //进行初始化赋值
    for(int i = 0; i< len ; i++){
        arr[i] = i+10;
    }

    //打印数组中的值
    for(int i = 0; i< len ; i++){
        printf("下标:%d->值:%d\n",i,arr[i]);
    }
    return 0;
}
```

输出:

```tex
下标:0->值:10
下标:1->值:11
下标:2->值:12
下标:3->值:13
下标:4->值:14
下标:5->值:15
下标:6->值:16
下标:7->值:17
下标:8->值:18
下标:9->值:19
```

### 9.6 计算数组中所有元素的和及平均数

```c
#include <stdio.h>

int main(){
    //定义数组
    int arr[10]={1,2,3,4,5,6,7,8,9,10};

    int len = sizeof(arr) / sizeof arr[0];

    int sum;

    for(int i = 0; i< len ; i++){
        sum += arr[i];
    }
    printf("所有元素的和为:%d \n",sum);


    //计算平均数
    int avg = sum / len;
    printf("平均数为：%d",avg);


    return 0;
}
```

输出:

```tex
所有元素的和为:565 
平均数为：56
```



### 9.7 求数组中的最大值和最小值

```c
#include <stdio.h>

int main()
{
    int arr[10] = {10,9,8,7,6,1,2,3,4,5};

    int min = arr[0];
    int max = arr[0];
    
    int len = sizeof arr / sizeof arr[0];

    for(int i = 1; i < len ; i++){
        //求最小值
        if(min > arr[i]){
            min = arr[i];
        }
        //求最大值
        if(max < arr[i]){
            max = arr[i];
        }    
    }

    printf("最大值：%d,最小值：%d",max,min);

    return 0;
}
```

输出：

```tex
最大值：10,最小值：1
```





### 9.8 字符串数组

用来存放字符的数组称为字符数组，也可以称为字符串。字符串的输入输出格式占位符是 %s。

字符串结尾，会自动添加一个 \0 作为字符串结束的标志，所以字符数组最后一个元素必须是 \0。
\0 是ASCII码表中的第0个字符，用NUL表示，称为空字符，该字符既不能显示，也不是控制字符，输出该字符不会有任何效果，它在C语言中仅作为字符串的结束标志。



**字符串定义**

在给某个字符数组赋值时，赋值的元素个数小于字符数组的长度，则会自动在后面加 '\0', 表示字符串结束； 赋值的元素的个数等于该数组的长度（或不指定数组长度），则不会自动添加 '\0'。

```c
#include <stdio.h>

int main(){
    //显示的设置\0，表示字符串已经结束
    char str1[12] = {'H','e','l','l','0',' ','W','o','r','l','d','\0'};
    char str2[4] = {'t','o','m'};
    char str3[] = {'j','a','c','k'};

    printf("str1=%s \n",str1); 
    printf("str2=%s \n",str2); 
    printf("str3=%s \n",str3); //由于没有显示的结束标识，会包括相邻内存数组，直接遇到结束标识！
    return 0;
}
```

输出：

```tex
str1=Hell0 World 
str2=tom 
str3=jacktom 
```

简化写法

```c
#include <stdio.h>

int main(){
    //定义字符串
    char chars1[]={"I am happy"};//后面自动添加\0
    char chars2[]="I am happy";//省略{}号，后面自动添加\0

    printf("\n str1=%s",chars1);
    printf("\n str2=%s",chars2);


    return 0;
}
```

输出:

```tex
 str1=I am happy
 str2=I am happy
```

### 9.10 字符串数组的遍历和访问

```c
#include <stdio.h>

int main()
{
    // 定义字符串
    char chars[] = "Hello";

    // 计算字符串长度
    int len = sizeof(chars) / sizeof(chars[0]);

    printf("%s \n", chars);
    printf("数组的长度:%d \n", len);
    printf("第三个元素是:%c \n", chars[2]);
    printf("\n");

    // 遍历字符串
    for (int i = 0; i < len; i++)
    {
        printf("%c \n",chars[i]);
    }

    return 0;
}
```

输出：

```tex
Hello 
数组的长度:6 
第三个元素是:l 

H 
e 
l 
l 
o 
```



### 9.11 多维数组

如果数组的元素还是数组，这样的数组就称为多维数组。这种多层次的结构允许我们以表格或矩阵的方式组织数据，其中每个维度都对应于不同的行、列或更多的维度，使数据更加结构化和有组织。
多维数组可以分为二维数组、三维数组、四维数组 …… 等，这里我们以二维数组为例进行演示。

```c
#include <stdio.h>

int main()
{
    //方式1： 定义一个2行3列的二维数组
    int intarray[2][3];

    intarray[0][0] = 10;
    intarray[0][1] = 20;
    intarray[0][2] = 30;
    intarray[1][0] = 40;
    intarray[1][1] = 50;
    intarray[1][2] = 60;

    //方式2，定义一个2行3列的二维数组
    int arrays2[2][3]= {
        {10,20,30},
        {40,50,60}
    };


    //方式3，定义一个2行3列的二维数组，会自动匹配到各行各列
    int arrays3[2][3] = {10,20,30,40,50,60};


    //方式4,如果所赋值的数量与元素数量对应，则第一个数组长度可以不给出
    int arrays4[][3] = {10,20,30,40,50,60};

    return 0;
}
```





**二维数组的访问和遍历**

访问二维数组的元素，需要使用两个下标（索引），一个用于访问行（第一维），另一个用于访问列（第二维），我们通常称为行下标（行索引）或列下标（列索引）。
遍历二维数组，需要使用双层循环结构。

```c
#include <stdio.h>

int main()
{
    int table[3][4] = {
        {10, 20, 30, 40},
        {50, 60, 70, 80},
        {90, 100, 110, 120}
    };

    // 计算行数
    int row = sizeof(table) / sizeof(table[0]);
    // 计算列数
    int cell = sizeof(table[0]) / sizeof(table[0][0]);

    // 遍历每个元素
    for (int i = 0; i < row; i++)
    {
        for (int j = 0; j < cell; j++)
        {
            printf("第%d行,第%d列，值：%d \t",i,j,table[i][j]);
        }
        printf("\n");
    }

    int sum = 0;
    for(int i=0; i< row;i++){
        for(int j=0;j<cell;j++){
            sum+=table[i][j];
        }
    }
    printf("所有元素和为:%d",sum);

    return 0;
}
```

输出：

```tex
第0行,第0列，值：10     第0行,第1列，值：20     第0行,第2列，值：30     第0行,第3列，值：40 
第1行,第0列，值：50     第1行,第1列，值：60     第1行,第2列，值：70     第1行,第3列，值：80
第2行,第0列，值：90     第2行,第1列，值：100    第2行,第2列，值：110    第2行,第3列，值：120
所有元素和为:780
```



### 9.12 二维数组的内存分析

用矩阵形式（如3行4列形式）表示二维数组，是逻辑上的概念，能形象地表示出行列关系。而在内存中，各元素是连续存放的，不是二维的，是线性的。
C语言中，二维数组中元素排列的顺序是按行存放的。即：先顺序存放第一行的元素，再存放第二行的元素。
比如，举例，数组a[3][4] 在内存中的存放：

![image-20250614095211898](.\images\image-20250614095211898.png)



**二维数组的案例**

现在有三个班，每个班五名同学，用二维数组保存他们的成绩，并求出每个班级平均分、以及所有班级平均分，数据要求从控制台输入。

```c
#include <stdio.h>

int main(){
    //现在有三个班，每个班五名同学，用二维数组保存他们的成绩，并求出每个班级平均分、以及所有班级平均分，数据要求从控制台输入。
    int arr[3][5];

    int row = sizeof(arr)/sizeof(arr[0]);
    int cell =sizeof(arr[0])/sizeof(arr[0][0]);


    for(int i=0;i<row;i++){
        for(int j=0;j<cell;j++)
        {
            printf("请输入第%d班第%d个学生的成绩:",i,j);
            scanf("%d",&arr[i][j]);
        }
    }

    //计算班级平均分
    int allSum = 0;
    for(int i=0;i<row;i++){
        int sum = 0;
        for(int j=0;j<cell;j++)
        {
           sum += arr[i][j];
           allSum+=arr[i][j];
        }
        printf("第%d班的平均分数是:%d\n",i,sum/cell);
    }

    printf("所有班的平均分数是:%d\n",allSum/(row * cell));


    return 0;
}
```

输出：

```tex
请输入第0班第0个学生的成绩:10
请输入第0班第1个学生的成绩:20
请输入第0班第2个学生的成绩:30
请输入第0班第3个学生的成绩:40
请输入第0班第4个学生的成绩:50
请输入第1班第0个学生的成绩:60
请输入第1班第1个学生的成绩:70
请输入第1班第2个学生的成绩:80
请输入第1班第3个学生的成绩:90
请输入第1班第4个学生的成绩:100
请输入第2班第0个学生的成绩:110
请输入第2班第1个学生的成绩:120
请输入第2班第2个学生的成绩:130
请输入第2班第3个学生的成绩:140
请输入第2班第4个学生的成绩:150
第0班的平均分数是:30
第1班的平均分数是:80
第2班的平均分数是:130
所有班的平均分数是:80
```




## 10. 函数

### 什么是函数？

函数是一种可重复使用的代码块，用于执行特定的任务或操作。
函数允许我们将代码逻辑组织成独立的单元，从而提高了代码的可读性、可维护性和重用性。
一个C程序可以由一个或多个源文件构成（C文件扩展名是“.c”），一个源文件是一个编译单位。一个源文件可以由若干个函数构成，函数之间可以相互调用。也就是说，函数是C程序基本的组成单位。

![image-20250614100709968](.\images\image-20250614100709968.png)

**函数的作用**

（1）封装功能，将一个完整的功能封装成函数，提高代码的结构化和复用性。

（2）代码模块化，将程序按照功能拆分成若干模块单元,有助于降低复杂度。

（3）增强可维护性，如果需要修改某项功能，只需要调整对应的函数代码。

（4）隔离细节，通过函数调用可以隐藏实现细节，只关心输入输出。

### **函数的语法**

```c
返回类型 函数名（参数列表）
{
    函数体语句1;
    函数体语句2;
    … 函数体语句n;
    return 返回值;
}
```

![image-20250615112354612](.\images\image-20250615112354612.png)

（1）函数名：函数被调用时使用的名字，函数名要符合标识符规范。

（2）函数体：函数中所包含的代码块，用于实现函数的具体功能和操作。

（3）参数：用于接收调用函数时传递进来的值。

返回值：函数执行完毕后，从函数传回到调用点的值，返回值的类型要与函数名

```c
#include <stdio.h>


//声明函数
void func(){
    printf("hello func\n");
}

//实现两个数字相加
int plus(int m,int n){
    return m + n;
}

//求最大值
int max(int a,int b)
{
    return a > b ? a : b;
}

int main(){

    return 0;
}
```

**函数不能嵌套声明**

C程序中的所有函数都是互相独立的，一个函数并不从属于另一个函数，即函数不能嵌套声明。

```c
//错误演示
int func1(int a,int b) //第1个函数的定义
{   
    ...
    int func2(int c,int d)  //第2个函数的定义
    {   
       ...
    }
    ...
}

```



### 调用函数

函数名后面加上圆括号即是函数的调用，参数写在小括号中，函数每调用一次，函数体语句都会执行一遍。

```c
#include <stdio.h>

//声明函数
void func(){
    printf("hello func\n");
}
//实现两个数字相减
int minus(int m,int n){
    return m -n;
}
//取两个字数中的最大值
int max(int a,int b){
    return a > b ? a : b;
}

int main(){
    func();
    func();
    printf("17-90的结果:%d\n",minus(17,90));
    printf("21-180的结果:%d\n",minus(21,180));
    printf("12和16之间较大的是:%d\n",max(12,16));
    printf("45和31之间较大的是:%d\n",max(45,31));
    return 0;
}
```

输出：

```c
hello func
hello func
17-90的结果:-73      
21-180的结果:-159    
12和16之间较大的是:16
45和31之间较大的是:45
```

### 函数的返回值

函数调用后数能得到一个确定的值，这就是函数的返回值，返回值常常是一个计算的结果，或是用来作为判断函数执行状态的标记。

函数返回值分为以下三种情况：

（1）无返回值类型：针对函数无返回值或明确不需返回值的情况，使用void（即空类型）表示。

（2）有返回值类型：指明具体的类型，比如，int、float、char等。需要在函数体内与return语句搭配使用。

（3）如果返回值类型不是void，但函数中没有return语句，则函数会返回一个不确定的值。

```c
#include <stdio.h>

//无返回值
void fun01(){
    printf("fun01\n");
}
//有明确的返回值
double fun02(){
    return 3.14;
}
//函数返回一个不确定的值
int fun03(){
    10 + 20;
}
//返回类型与return类型不一致，可能造成精度丢失
int fun04(){
    return 20.89;
}

int main(){
    fun01();
    printf("%f \n",fun02());
    printf("%d \n",fun03());
    printf("%d \n",fun04());
    return 0;
}
```

输出：

```tex
fun01    
3.140000 
10       
20  
```

### 函数的参数

函数的参数分为形参与实参：

Ø 形参：在定义函数时，函数名后面括号()中声明的变量称为形式参数，简称形参。

Ø 实参： 在调用函数时，函数名后面括号()中的使用的常量、变量、表达式称为实际参数，简称实参。

注意：实参的数量要与形参的数量一致，否则报错。

```c
#include <stdio.h>

//在定义函数时，函数括号里的声明变量称为形式参数，称为形参
int func(int x,int y){
    return x + y;
}

int main(){
    //在调用时传递的参数，称为实参
    int sum = func(3,8);
    printf("3+8=%d\n",sum);
    return 0;
}
```

输出：

```tex
3+8=11
```

### 主函数

**作用**

主函数是程序的入口函数，即所有的程序一定要包含一个主函数，程序总是从这个函数开始执行，如果没有该函数，程序就无法启动。

主函数中可以调用其它函数，但其它函数不能反过来调用主函数，主函数也不能调用自己。

**返回值**

C语言约定，主函数返回值0表示运行成功，如果返回其它非零整数，就表示运行失败。

默认情况下，如果主函数里面省略return 0 这一行，编译器会自动加上，即 main() 的默认返回值为0。但是为了保持统一的代码风格，不建议省略。

**主函数的参数**

```c
int main(int argc, char *argv[])
{
    // 函数体
}
```

案例：

```c
#include <stdio.h>

/**
 * argc 传递的参数大小
 * 
 * argv 传递的是参数的值
 */
int main(int argc, char *argv[])
{ 
    printf("param num:%d\n",argc);
    //打印参数
    for(int i =0 ;i < argc ;i++){
        printf("%d --> %s\n",i,argv[i]);
    }

    return 0;
}
```

编译

```sh
# 在命令行下执行：
gcc -o demo 05-function-main.c

demo.exe key1=value1 key2=value2
```

输出

```tex
D:\java\myself\learn\learn-md\c\chapter10>demo.exe key1=value1 key2=value2
param num:3
0 --> demo.exe
1 --> key1=value1
2 --> key2=value2
```

### 函数原型

默认情况下，函数必须先声明，后使用。由于程序总是先运行main() 函数，导致所有其它函数都必须在main() 函数之前声明。

如果想将函数声明写在后面，可以在程序开头处给出函数原型。函数原型，就是提前告诉编译器，每个函数的返回类型和参数类型。其它信息都不需要，也不用包括函数体，具体的函数实现可以后面再补上。

```c
#include <stdio.h>


int main(){
    printf("求最大数为:%d\n",max(10,20));
    return 0;
}


int max(int a,int b)
{
    return a > b ? a : b;
}
```

编译出现告警：

```sh
cmd /c chcp 65001>nul && D:\soft\c\mingw64\bin\gcc.exe -fdiagnostics-color=always -g D:\java\myself\learn\learn-md\c\chapter10\06-function-prototype.c -o D:\java\myself\learn\learn-md\c\chapter10\06-function-prototype.exe
D:\java\myself\learn\learn-md\c\chapter10\06-function-prototype.c: In function 'main':
D:\java\myself\learn\learn-md\c\chapter10\06-function-prototype.c:5:30: warning: implicit declaration of function 'max' [-Wimplicit-function-declaration]
    5 |     printf("求最大数为:%d\n",max(10,20));
      |
```

使用函数原型进行定义

```c
#include <stdio.h>

//使用函数原型进行声明
int max(int a,int b);

int main(){
    printf("求最大数为:%d\n",max(10,20));
    return 0;
}
//函数定义
int max(int a,int b)
{
    return a > b ? a : b;
}
```

结果：

```c
求最大数为:20
```



### 函数的作用域

作用域用于确定在代码中某个标识符（如变量、标识符常量、数组等）的可见性和访问范围，它决定了在程序的哪些部分可以引用或访问该标识符。

作用域可以分为全局作用域、局部作用域、块级作用域。

同一个作用域中不能声明同名的标识符。

#### 全局作用域

在函数和代码块（分支语句、循环语句等）以外定义的变量、标识符常量、数组等具有全局作用域，在程序的任何地方都可以被访问，通常称它们为全局变量、全局常量、全局数组等。

```c
#include <stdio.h>

// 全局变量
double money = 1.1;
// 全局常量
const double PI = 3.1415926;
// 全避数组
char msg[] = "Hello World";

// 全局函数
void func()
{
    printf("func 函数中使用全局数组:\n");
    printf("money=%.2f \n", money);
    printf("PI=%.2f \n", PI);
    printf("msg=%s \n", msg);
    printf("\n");
    money += 100; // 修改全局变量的值
}

int main()
{
    // 调用func函数
    func();

    printf("func 函数中使用全局数组:\n");
    printf("money=%.2f \n", money);
    printf("PI=%.2f \n", PI);
    printf("msg=%s \n", msg);
    printf("\n");

    // 调用func函数
    func();

    return 0;
}
```

输出：

```tex
func 函数中使用全局数组:
money=1.10 
PI=3.14 
msg=Hello World         

func 函数中使用全局数组:
money=101.10 
PI=3.14 
msg=Hello World         

func 函数中使用全局数组:
money=101.10 
PI=3.14 
msg=Hello World  
```



对于全局变量，如果没有显式指定初始值，它们将自动初始化为零值。

对于全局数组，如果没有显式初始化它们，它们的元素将自动初始化为零值，字符数组，将初始化为空字符 \0。

案例：

```c
#include <stdio.h>

//定义全局变量不进行初始化赋值
int a; //自动初始化为0
double b; //自动初始化为0.0
char c; //自动初始化为空字符\0

//定义全局数组不进行初始化
int arr[5]; //所有元素自动初始化为0
char msg[6]; //所有元素自动初始化为空字符符\0


int main(){
    printf("a=%d \n",a); // a =0
    printf("b=%f \n",b); // b = 0.000000
    printf("c=%c \n",c); // c= 
    printf("\n");

    //计算数组的长度
    int len = sizeof(arr) / sizeof(arr[0]);

    //遍历数组
    for(int i = 0;i<len; i++){
        printf("%d ",arr[0]);
    }

    printf("\n\n");

    printf("字符串数组:%s",msg);

    return 0;
}
```

输出：

```tex
a=0        
b=0.000000 
c=         

0 0 0 0 0  

字符串数组:
```

#### 局部作用域

在函数内定义的变量、标识符常量、数组等具有局部作用域，只有在该函数内部才能被访问，通常称它们为局部变量、局部常量、局部数组等。需要注意的是，函数的形参也是局部变量。

```c
#include <stdio.h>

//定义全局变量
int num01 = 10;
int num02 = 20;

void func(int count){
    //函数中定义变量、常量、数组，具有局部作用域
    int num01 = 100;
    const double PI01 = 3.14;
    char msg01[] = "Hello Beijing";
    //使用本作用域中的变量、常量、数组
    printf("func函数:\n");
    printf("本作用域变量:count=%d,num01=%d,PI01=%.2f,msg01=%s \n ",count,num01,PI01,msg01);
    printf("全局变量:num02=%d \n",num02);
    num01 +=1;
    num02 +=1;
    printf("----------------------------\n\n");
}

int main(){
    //定义变量，作用域，仅限于主函数
    int num02 = 100000;
    //局部变量没有初始化
    int num04;
    int arr[5];

    //调用函数
    func(220);

    //主函数中使用其他作用域中的变量
    printf("main函数:\n");
    printf("num02=%d \n ",num02);
    printf("本作用域变量:num04=%d,arr[0]=%d,arr[4]=%d \n ",num04,arr[0],arr[4]);
    return 0;
}
```

输出：

```tex
func函数:
本作用域变量:count=220,num01=100,PI01=3.14,msg01=Hello Beijing 
 全局变量:num02=20 
----------------------------

main函数:
num02=100000 
 本作用域变量:num04=1884243920,arr[0]=8,arr[4]=1884243928 
```

#### 块级作用域

块级作用域是C99标准引入的概念，在代码块（分支语句、循环语句等）中定义的变量、标识符常量、数组等具有块级作用域，只有在该代码块内部才能被访问，代码块通常具有花括号 `{}` 结构。 

这些被称为块级变量、块级常量、块级数组等，同时也可以被称为局部变量、局部常量、局部数组，且与函数中的局部变量、局部常量、局部数组具有相同的特性。

```c
#include <stdio.h>

int main()
{
    // 该代码块中具有块级作用域
    {
        // 块级变量
        int a = 20;
        // 块级常量
        const double PI = 3.14;

        printf("a*PI=%f \n", a * PI);
    }

    //分支语句具有块级作用域
    if(1){
        //局部数组
        int nums[] = {10,20,30};
        printf("%d %d %d \n",nums[0],nums[1],nums[2]);
    }

    //循环语句中的标识变量i是块级作用域
    for(int i = 0;i<5;i++){
        printf("%d ",i);
    }

    return 0;
}
```

输出：

```tex
a*PI=62.800000 
10 20 30       
0 1 2 3 4 
```

#### 作用域和内存

1）栈区域

局部变量、局部数组等通常存储在栈（Stack）区，这些局部数据的内存分配和释放是自动管理的，它们的生命周期受限于其定义的函数或块级作用域，当函数返回或块级作用域结束时，这些变量的内存会被自动释放。

函数每调用一次就创建一次局部数据，调用结束就销毁；下次调用再创建新的局部数据。

2）全局静态区域

全局变量、全局数组等存储在全局静态区，这些全局的数据在程序的整个生命周期内都存在，它们在程序启动时被分配，在程序结束时才被释放。



![image-20250615211556565](.\images\image-20250615211556565.png)









# 结束

