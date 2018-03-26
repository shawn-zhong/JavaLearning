package com.tseong.learning.text;

public class _07_UML {

    /*

    1.  依赖关系：表示"use a"关系。如A依赖于B，则B表现为A的局部变量、方法参数、静态方法调用等
        表示方法 虚线加箭头 .........>
        例子－1：
        public class Person {
            public void doSomething() {
                Card card = new card();             // 局部
            ｝
        ｝
        例子－2:
        public class Person {
            public void doSomething(Card card) {    // 参数

            }
        }


    2. 关联关系（Association） 是一种"has a"关系，A－>B则可以说A has a B，通常表现为全局变量
       表示方法：实线加箭头  ------>
       例子
       public Class Person {
            public Phone phone;
            public void setPhone(Phone a) {
                ....
            }
       }


    3. 聚合关系（Aggregation）：聚合关系与关联关系只是语义上的区别。关联关系－平等，聚合关系－不平等（整体与局部）
       表示方法：空心菱形加直线
       例子：比如Team和个人。Team解散了，但个人还是可以假如其它的team
       public Class Team {
            public Person person;

            public Team(Person person) {
                this.person = person;
            }
       }


     4. 组合关系
        表示方法：实心菱形加直线
        例子：
        public Class Person {
            public Head head;
            public Body body;

            public Person() {
                head = new Head();
                body = new Body();
            }
        }


     5. 实现接口：三角形加虚线
     6. 实现继承：三角形加实线


     */
}
