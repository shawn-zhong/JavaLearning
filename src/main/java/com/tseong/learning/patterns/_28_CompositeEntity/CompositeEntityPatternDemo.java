package com.tseong.learning.patterns._28_CompositeEntity;

public class CompositeEntityPatternDemo {

    public static void main(String[] args) {
        Client client = new Client();
        client.setData("Test", "Data");
        client.printData();
        client.setData("Second Test", "Data1");
        client.printData();;
    }
}


/*
Composite Entity pattern is used in EJB persistence mechanism. A Composite entity is an EJB entity bean which represents
a graph of objects. When a composite entity is updated, internally dependent objects beans get updated automatically as being
managed by EJB entity bean. Following are the participants in Composite Entity Bean.

 */