package com.tseong.learning.patterns._24_nullObject;

public class CustomerFactory {

    public static final String[] names = {"Rob", "Joe", "Julie"};

    public static AbstractCustomer getcustomer(String name) {

        for (int i=0; i<names.length; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                return new RealCustomer(name);
            }
        }

        return new NullCustomer();
    }
}
