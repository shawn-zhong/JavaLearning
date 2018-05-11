package com.tseong.learning.patterns._24_nullObject;

public class NullPatternDemo {

    public static void main(String[] args) {
        AbstractCustomer customer1 = CustomerFactory.getcustomer("Rob");
        AbstractCustomer customer2 = CustomerFactory.getcustomer("Bob");
        AbstractCustomer customer3 = CustomerFactory.getcustomer("Julie");
        AbstractCustomer customer4 = CustomerFactory.getcustomer("Laura");

        System.out.println("Customers");
        System.out.println(customer1.getName());
        System.out.println(customer2.getName());
        System.out.println(customer3.getName());
        System.out.println(customer4.getName());
    }
}


/*

In Null Object pattern, a null object replaces check of NULL object instance. Instead of putting if check for a null value,
Null Object reflects a do nothing relationship. Such Null object can also be used to provide default behaviour in case data is not available.

 */