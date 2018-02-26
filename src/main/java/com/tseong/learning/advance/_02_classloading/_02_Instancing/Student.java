package com.tseong.learning.advance._02_classloading._02_Instancing;

import java.io.Serializable;

public class Student implements Cloneable, Serializable {
    private String name;
    private int uid;

    public Student() {
        name = "shawn";
        uid = 48100;
    }

    private Student(String name, int uid) {
        this.name = name;
        this.uid = uid;
    }

    private Student(String name) {
        this.name = name;
        this.uid = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }

    // 需要重写clone方法，即使只是调用suepr.clone, 否则由于protected的关系，调用不了，因为和Object不在同一个包下面，除非是派生类自己调用
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
