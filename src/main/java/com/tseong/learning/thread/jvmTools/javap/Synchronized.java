package com.tseong.learning.thread.jvmTools.javap;

public class Synchronized {

    public static void main(String[] args) {
        // 对Synchronized class对象进行加锁
        synchronized (Synchronized.class) {
        }
        // 静态同步方法，对Synchronized Class对象
        m();
    }

    public static synchronized void m(){

    }
}


// 在Synchronized.class同级目录执行javap -v Synchronized.class，


/*

Tools/javap Shawn >> javap -v Synchronized.class
Classfile /Users/Shawn/Documents/workspace/JavaLearning/target/classes/com/tseong/learning/thread/jvmTools/javap/Synchronized.class
  Last modified Mar 3, 2018; size 636 bytes
  MD5 checksum 53773fc79d08da53e763a8daf01dcb98
  Compiled from "Synchronized.java"
public class com.tseong.learning.thread.jvmTools.javap.Synchronized
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#23         // java/lang/Object."<init>":()V
   #2 = Class              #24            // com/tseong/learning/thread/jvmTools/javap/Synchronized
   #3 = Methodref          #2.#25         // com/tseong/learning/thread/jvmTools/javap/Synchronized.m:()V
   #4 = Class              #26            // java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Utf8               LineNumberTable
   #9 = Utf8               LocalVariableTable
  #10 = Utf8               this
  #11 = Utf8               Lcom/tseong/learning/thread/jvmTools/javap/Synchronized;
  #12 = Utf8               main
  #13 = Utf8               ([Ljava/lang/String;)V
  #14 = Utf8               args
  #15 = Utf8               [Ljava/lang/String;
  #16 = Utf8               StackMapTable
  #17 = Class              #15            // "[Ljava/lang/String;"
  #18 = Class              #26            // java/lang/Object
  #19 = Class              #27            // java/lang/Throwable
  #20 = Utf8               m
  #21 = Utf8               SourceFile
  #22 = Utf8               Synchronized.java
  #23 = NameAndType        #5:#6          // "<init>":()V
  #24 = Utf8               com/tseong/learning/thread/jvmTools/javap/Synchronized
  #25 = NameAndType        #20:#6         // m:()V
  #26 = Utf8               java/lang/Object
  #27 = Utf8               java/lang/Throwable
{
  public com.tseong.learning.thread.jvmTools.javap.Synchronized();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/tseong/learning/thread/jvmTools/javap/Synchronized;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: ldc           #2                  // class com/tseong/learning/thread/jvmTools/javap/Synchronized
         2: dup
         3: astore_1
         4: monitorenter
         5: aload_1
         6: monitorexit
         7: goto          15
        10: astore_2
        11: aload_1
        12: monitorexit
        13: aload_2
        14: athrow
        15: invokestatic  #3                  // Method m:()V
        18: return
      Exception table:
         from    to  target type
             5     7    10   any
            10    13    10   any
      LineNumberTable:
        line 8: 0
        line 10: 5
        line 13: 15
        line 14: 18
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      19     0  args   [Ljava/lang/String;
      StackMapTable: number_of_entries = 2
        frame_type = 255
          offset_delta = 10
                  locals = [ class "[Ljava/lang/String;", class java/lang/Object ]
        stack = [ class java/lang/Throwable ]
        frame_type = 250
        offset_delta = 4

public static synchronized void m();
        descriptor: ()V
        flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
        Code:
        stack=0, locals=0, args_size=0
        0: return
        LineNumberTable:
        line 18: 0
        }
        SourceFile: "Synchronized.java"
        ~/Documents/workspace/JavaLearning/target/classes/com/tseong/learning/thread/jvmTools/javap Shawn >>


        */