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
