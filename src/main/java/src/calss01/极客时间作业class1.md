# 第一课时
###### 作业1
```
public class ByteAnalyze {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
        int e = a - b;
        int f = a * b;
        double g = a / b;
        byte k =9;
        float m = 2f;
        long cc = 22l;
        char cha = 'a';
        boolean x = false;
        if (g == 0.0)
            x = true;
        for (int i = 0; i < 2; i++) {
            a = a + i;
        }
    }
}
```

```
public class calss01.ByteAnalyze {
  public calss01.ByteAnalyze();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_1          //将int类型常量1压入栈
       1: istore_1          //将int类型值存入局部变量1
       2: iconst_2
       3: istore_2
       4: iload_1           从局部变量1中装载int类型值
       5: iload_2
       6: iadd              //执行int类型的加法
       7: istore_3
       8: iload_1
       9: iload_2
      10: isub              //执行int类型的减法
      11: istore        4
      13: iload_1
      14: iload_2
      15: imul              //执行int类型的乘法
      16: istore        5
      18: iload_1
      19: iload_2
      20: idiv              //执行int类型的除法
      21: i2d               //把int类型的数据转化为double类型
      22: dstore        6   //将double类型值存入局部变量
      24: bipush        9   //将一个8位带符号整数压入栈
      26: istore        8
      28: fconst_2
      29: fstore        9
      31: ldc2_w        #7   // long 22l   把常量池中long类型或者double类型的项压入栈（使用宽索引）
      34: lstore        10   //将long类型值存入局部变量
      36: bipush        97   //将一个8位带符号整数压入栈
      38: istore        12
      40: iconst_0
      41: istore        13
      43: dload         6
      45: dconst_0
      46: dcmpl             //比较double类型值（当遇到NaN时，返回-1）
      47: ifne          53  //如果不等于0，则跳转
      50: iconst_1
      51: istore        13
      53: iconst_0
      54: istore        14
      56: iload         14
      58: iconst_2
      59: if_icmpge     73  //如果一个int类型值大于或者等于另外一个int类型值，则跳转
      62: iload_1
      63: iload         14
      65: iadd
      66: istore_1
      67: iinc          14, 1   //把一个常量值加到一个int类型的局部变量上
      70: goto          56  //无条件跳转
      73: return
}

```

##### 作业2

```
package class01_homework;

import java.io.*;
import java.lang.reflect.Method;

class MyClassLoader extends ClassLoader{
    private static void xor(InputStream in, OutputStream out) throws Exception {
        int ch;
        while (-1 != (ch = in.read())) {
            ch = 255 -  ch ;
            out.write(ch);
        }
    }

    public static byte[] decrypt(File src) throws Exception {
        InputStream in = new FileInputStream(src);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        xor(in, bos);
        byte[] data = bos.toByteArray();

        in.close();
        bos.close();
        return data;
    }
    protected Class<?> findClass(String name,byte[] bytes) throws ClassNotFoundException {
        Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
        return aClass;
    }

    public static void main(String[] args) throws Exception {
        File src = new File("/Users/apple/calu/work/calu_java/src/class01_homework/Hello.xlass");
        byte[] bytes = decrypt(src);
        Class<?> helloclass = new MyClassLoader().findClass("Hello", bytes);
        Object o = helloclass.newInstance();
        Method hello = helloclass.getMethod("hello");
        hello.invoke(o);
    }
}
```
###### 执行结果

```
Hello, classLoader!
```

##### 作业三


```
命令 :jamp -heap pid
```

```
Attaching to process ID 1319, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.66-b17

using parallel threads in the new generation.
using thread-local object allocation.
Concurrent Mark-Sweep GC

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 3556769792 (3392.0MB)
   NewSize                  = 1275068416 (1216.0MB)
   MaxNewSize               = 1275068416 (1216.0MB)
   OldSize                  = 2281701376 (2176.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 536870912 (512.0MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 536870912 (512.0MB)
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
New Generation (Eden + 1 Survivor Space):
   capacity = 1147600896 (1094.4375MB)
   used     = 131517096 (125.42447662353516MB)
   free     = 1016083800 (969.0130233764648MB)
   11.460177179924404% used
Eden Space:
   capacity = 1020133376 (972.875MB)
   used     = 123134216 (117.42993927001953MB)
   free     = 896999160 (855.4450607299805MB)
   12.070403625339281% used
From Space:
   capacity = 127467520 (121.5625MB)
   used     = 8382880 (7.994537353515625MB)
   free     = 119084640 (113.56796264648438MB)
   6.576483169987147% used
To Space:
   capacity = 127467520 (121.5625MB)
   used     = 0 (0.0MB)
   free     = 127467520 (121.5625MB)
   0.0% used
concurrent mark-sweep generation:
   capacity = 2281701376 (2176.0MB)
   used     = 1489309176 (1420.3159103393555MB)
   free     = 792392200 (755.6840896606445MB)
   65.27187087956597% used

53639 interned Strings occupying 5743208 bytes.
```
分析
1. 老年代使用的率较高但fullgc次数非常少，ygc次数较多，但ygc时间较短所以还是比较健康的。
2. 机器内存大小为5G，young：old=  1:2,edn:s0:s1=8:1:1,新生代内存占用率也不是特别高。

