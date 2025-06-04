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



# 结束

