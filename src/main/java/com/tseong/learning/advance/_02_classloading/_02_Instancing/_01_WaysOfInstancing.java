package com.tseong.learning.advance._02_classloading._02_Instancing;

import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class _01_WaysOfInstancing {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {

        // 1 by new
        Student student = new Student();
        System.out.println("by new : " + student.toString() + " " + student.hashCode());

        // 2 by forname (reflects)
        student = (Student)Class.forName("com.tseong.learning.advance._02_classloading._02_Instancing.Student").newInstance();  // 这种方式必须要有默认构造方法？因为没看到构造参数
        System.out.println("by forname " + student.toString() + " " + student.hashCode());

        // 3 by xx.class.newInstance (reflects)
        // 使用newInstance方法的这两种方式创建对象使用的就是Java的反射机制，事实上Class的newInstance方法内部调用的也是Constructor的newInstance方法。见4
        student = Student.class.newInstance();
        System.out.println("by xx.class.newInstance " + student.toString() + " " + student.hashCode());

        // 4 by constructor.newInstance (reflects)
        //Constructor<Student> constructor = Student.class.getConstructor(String.class, int.class);
        //constructor.setAccessible(true);    // 事实证明这种想法错误:因为该构造函数是private的， 这里可以设置可达性绕开; 即使设置了true还是会抛出异常, 使用4.1的方法，获取private方法需要加入..Declare..
        //student = constructor.newInstance("zhong", 12);
        //System.out.println("by reflect constructor " + student.toString() + " " + student.hashCode());

        Constructor<Student> constructor = Student.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Student student1 = (Student)constructor.newInstance("getDeclaredConstructor");
        System.out.println("by reflect private getDeclaredConstructor " + student1.toString() + " " + student1.hashCode());

        // 4.1 by call private constructor
        Constructor constructor1 = Class.forName("com.tseong.learning.advance._02_classloading._02_Instancing.Student")
                .getDeclaredConstructor(String.class);
        constructor1.setAccessible(true);
        Student privateStu = (Student)constructor1.newInstance("privatedemo");
        System.out.println("by reflect private constructor " + privateStu.toString() + " " + privateStu.hashCode());

        // 5 使用Clone方法创建对象
        // 无论何时我们调用一个对象的clone方法，JVM都会帮我们创建一个新的、一样的对象，特别需要说明的是，用clone方法创建对象的过程中并不会调用任何构造函数
        Student studentClone = (Student) student.clone();
        System.out.println("by clone " + studentClone.toString() + " " + studentClone.hashCode());

        studentClone.setName("shawn-zhong");

        // 6 使用序列化反序列化
        Student studentBySerialize = getObjectBySerialize(studentClone);
        System.out.println("by serialize " + studentBySerialize.toString() + " " + studentBySerialize.hashCode());

        // 7 使用unsafe创建实例
        Student studentUnsafe = getObjectByUnsafe();
        System.out.println("by serialize " + studentUnsafe.toString() + " " + studentUnsafe.hashCode());

    }

    public static Student getObjectBySerialize(Student student) {
        try {
            // 序列化
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("/Users/Shawn/Documents/workspace/JavaLearning/target/student.bin"));
            output.writeObject(student);
            output.close();

            // 读对象 (不会调用构造函数)
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("/Users/Shawn/Documents/workspace/JavaLearning/target/student.bin"));
            Student newStudent = (Student) input.readObject();
            return newStudent;

        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Student getObjectByUnsafe() {
        // Unsafe类使用了单例模式，需要通过一个静态方法getUnsafe()方法来获取，但Unsafe类做了限制(特定loader才能调用)，如果是普通的调用的话，
        // 它会抛出一个SecurityException异常，只有有主加载器加载的类才能调用这个方法。所以我们通过反射来实例化Unsafe
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);

            Student student = (Student) unsafe.allocateInstance(Student.class);
            student.setName("UNSAFE");
            return student;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
