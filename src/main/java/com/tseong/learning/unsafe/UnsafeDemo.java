package com.tseong.learning.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

class Player {
    private int age;
    private String name;
    private Player() {} // 私有化，不可以被实例化

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

public class UnsafeDemo {


    /**
     * demo通过unsafe来实例化一个私有构造函数的类
     */
    void instanceClassByUnsafe() throws Exception {

        // Unsafe类使用了单例模式，需要通过一个静态方法getUnsafe()方法来获取，但Unsafe类做了限制，如果是普通的调用的话，
        // 它会抛出一个SecurityException异常，只有有主加载器加载的类才能调用这个方法。所以我们通过反射来实例化Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // 实例化Player
        Player player = (Player) unsafe.allocateInstance(Player.class);
        player.setName("Shawn from unsafe.allocateInstance");
        System.out.println(player.getName());
    }

    void instanceClassByReflect() throws Exception {
        Class<Player> cls = Player.class;

        Constructor<Player> constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        Player p = constructor.newInstance();

        p.setName("Shawn from Refelct");
        System.out.println(p.getName());

    }

    void demoCAS() throws Exception {
        // 通过反射实例化Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe)f.get(null);

        // 实例化Player
        Player player = (Player) unsafe.allocateInstance(Player.class);
        player.setAge(18);
        player.setName("Shawn from LockinteruptiblyDemo CAS");
        for (Field field:Player.class.getDeclaredFields()) {
            System.out.println(field.getName() + ":对应内存偏移地址 " + unsafe.objectFieldOffset(field));
        }

        long ageOffset = unsafe.objectFieldOffset(Player.class.getDeclaredField("age"));

        // 修改内存偏移地址为12的值（age）；返回true，说明修改成功 （原子性）
        System.out.println(unsafe.compareAndSwapInt(player, ageOffset, 18, 20));
        System.out.println("age修改之后的值: " + player.getAge());

        // 修改内存偏移地址为12的值（age），但是修改后不保证立马能被其它的线程看到
        unsafe.putOrderedInt(player, ageOffset, 33);
        System.out.println("age修改之后的值: " + player.getAge());

        // 修改内存偏移地址为12的值（age），volatile修饰，修改能立马对其它线程可见
        unsafe.putIntVolatile(player, ageOffset, 44);
        System.out.println("age修改之后的值: " + player.getAge());

        long nameOffset = unsafe.objectFieldOffset(Player.class.getDeclaredField("name"));

        // 修改name的值，volatile修饰，修改能立马对其它线程可见
        unsafe.putObjectVolatile(player, nameOffset, "new name");
        System.out.println("name修改之后的值: " + player.getName());
    }

    void allocateMemory() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // allocate
        long size = 10240;
        long addr = unsafe.allocateMemory(size);
        System.out.println("unsafe address : " + addr );

        for (int i=0; i < 10240; i++) {
            unsafe.putByte(addr+i, (byte)6);
            if (unsafe.getByte(addr+i) != 6) {
                System.out.println("failed at offset");
            }
        }

        unsafe.freeMemory(addr);
    }

    public static void main(String[] args) throws Exception {
        UnsafeDemo instance = new UnsafeDemo();

        instance.instanceClassByUnsafe();
        instance.instanceClassByReflect();
        instance.demoCAS();
        instance.allocateMemory();

    }
}
