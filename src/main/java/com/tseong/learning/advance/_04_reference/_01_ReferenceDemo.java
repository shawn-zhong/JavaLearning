package com.tseong.learning.advance._04_reference;

import java.lang.ref.*;

public class _01_ReferenceDemo {

    private static Object _objectStrong = new Object();
    private static Object _objectSoft = new Object();

    private static Object _objWeak = new Object();
    private static String _strWeak = "This is text";

    private static Object _objPhanton = new Object();
    private static String _strPhanton = "Another text";

    public static void main(String[] args) throws InterruptedException {

        System.out.println(_objectStrong + " " + _objectSoft + " " + _objWeak + " " + _strWeak);

        // Strong reference:
        Object objStrong = _objectStrong;
        _objectStrong = null;
        System.gc();
        System.out.println("After system.gc - strongReference = " + objStrong);
        // 输出 :
        // After system.gc - strongReference = java.lang.Object@2503dbd3
        // 在该demo中objStrong就是使用的默认的强引用，虽然obj所指向的对象被置为null,但gc不会回收该强引用对象


        // Soft Reference:
        SoftReference<Object> objSoft = new SoftReference<>(_objectSoft);
        _objectSoft = null;
        System.gc();
        System.out.println("after system.gc - softReference = " + objSoft.get());
        // 输出 :
        // after system.gc - softReference = java.lang.ref.SoftReference@4b67cf4d
        // 由控制台的输出结果可以看到，虽然软引用引用的对象被清空，但是由于内存充足，就算是执行了gc也不会被回收 内存不足时软引用才会被及时回收避免oom异常


        // Weak Reference: （ThreadLocalMap的key使用到了）
        WeakReference<Object> weakReference = new WeakReference<>(_objWeak);
        WeakReference<String> weakReferenceStr = new WeakReference<>(_strWeak);
        System.gc();
        System.out.println("after system.gc - weakReference = " + weakReference.get());
        System.out.println("after system.gc - weakReference = " + weakReferenceStr.get());
        // 输出:
        // after system.gc - weakReference = java.lang.Object@7ea987ac
        // after system.gc - weakReference = This is text
        // 可以看到，此时所引用的对象不为null，所以在进行gc回收时不会回收弱引用对象 -> 因为还有引用
        _objWeak = null;
        _strWeak = null;
        System.out.println("after system.gc - weakReference = " + weakReference.get());
        System.out.println("after system.gc - weakReference = " + weakReferenceStr.get());
        // 输出：
        // after system.gc - weakReference = java.lang.Object@7ea987ac
        // after system.gc - weakReference = This is text
        // 可以看到如果不进行gc，弱引用对象在内存充足的情况下是不会回收的
        System.gc();
        System.out.println("after system.gc - weakReference = " + weakReference.get());
        System.out.println("after system.gc - weakReference = " + weakReferenceStr.get());
        // 输出：
        // after system.gc - weakReference = null
        // after system.gc - weakReference = This is text   --> 这是因为gc不清理常量池里的垃圾，所以所引用的内容不为null

        // （应用）ThreadLocal的Key是弱引用类型的，但Value并非弱引用。

        // Phanton Reference
        // 应用：NIO直接内存的释放
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(_objPhanton, referenceQueue);
        PhantomReference<String> phantomReferenceStr = new PhantomReference<>(_strPhanton, referenceQueue);
        System.gc();
        System.out.println(phantomReference.isEnqueued());      // false 是否加入了队列referenceQueue
        System.out.println(phantomReferenceStr.isEnqueued());   // false 是否加入了队列referenceQueue
        _strPhanton = null;
        _objPhanton = null;
        System.out.println(phantomReference.isEnqueued());      // false 是否加入了队列referenceQueue
        System.out.println(phantomReferenceStr.isEnqueued());   // false 是否加入了队列referenceQueue
        System.gc();
        System.out.println(phantomReference.isEnqueued());      // false 是否加入了队列referenceQueue
        System.out.println(phantomReferenceStr.isEnqueued());   // false 是否加入了队列referenceQueue

        try {
            Thread.sleep(1000);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(phantomReference.isEnqueued());      // true 是否加入了队列referenceQueue   -> 虚引用在系统垃圾回收器开始回收对象时但不会立即将其加入回收队列 . 只有在真正对象被 GC 清除时 , 才会将其加入 Reference 队列中去
        System.out.println(phantomReferenceStr.isEnqueued());   // false 是否加入了队列referenceQueue  -> 这是因为gc不清理常量池里的垃圾，所以所引用的内容不为null

        System.out.println("after system.gc - phantomReference = " + referenceQueue.remove(100));       // -> after system.gc - phantomReference = java.lang.ref.PhantomReference@12a3a380
        System.out.println("after system.gc - phantomReferenceStr = " + referenceQueue.remove(100));    // -> after system.gc - phantomReferenceStr = null


        // 关于队列：可以用softreference或weakreference包装后塞入队列，当外围置为null时，队列可以自己清空
    }

}

