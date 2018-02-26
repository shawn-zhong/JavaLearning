package com.tseong.learning.advance._02_classloading._02_Instancing;


class InstanceVariableInitializer {

    // 顺序-1. 先初始化这段代码
    private int i = getJValue();    // 当执行这句时，j还没有被赋值，所以是 i=0
    private int j = i + 1;          // j = 0+1 = 1


    // 顺序-3. 执行构造函数
    public InstanceVariableInitializer(int var) {
        System.out.println(i);      // i = 0
        System.out.println(j);      // j = 4
        this.i = var;               // i = var
        System.out.println(i);
        System.out.println(j);      // j = 4
    }

    // 顺序-2. 再运行这个实例块
    {
        j += 3;                 // j = 1+3 = 4
    }

    private int getJValue() {
        return j;
    }
}


public class _02_InstancingSequence {

    public static void main(String[] args ) {
        new InstanceVariableInitializer(8);
        // 上面代码输出：
           /*
            0
            4
            8
            4
            */


    }

}
