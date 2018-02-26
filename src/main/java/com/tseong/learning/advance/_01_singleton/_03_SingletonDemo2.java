package com.tseong.learning.advance._01_singleton;

import java.io.*;

// 使用静态内置类实现单例模式
// 静态内部类虽然保证了单例在多线程并发下的线程安全性，但是在遇到序列化对象时，默认的方式运行得到的结果就是多例的。
// 解决办法就是在反序列化的过程中使用readResolve()方法，单例实现的代码如下：
class Singleton03_2 implements Serializable {

    // 静态私有内部类
    private static class InnerClass {
        private final static Singleton03_2 instance = new Singleton03_2();
    }

    private Singleton03_2() { }

    public static Singleton03_2 getInstance() {
        return InnerClass.instance;
    }

    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
    protected Object readResolve() throws ObjectStreamException {
        System.out.println("调用了readReolve方法!");
        return InnerClass.instance;
    }
}

public class _03_SingletonDemo2 {

    public static void main(String[] args) {

        Singleton03_2 singleton = Singleton03_2.getInstance();

        File file = new File("object.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(singleton);
            fos.close();
            oos.close();
            System.out.println(singleton.hashCode());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Singleton03_2 singleton2 = (Singleton03_2) ois.readObject();
            fis.close();
            ois.close();

            System.out.println(singleton2.hashCode());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
