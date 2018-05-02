package com.tseong.learning.patterns.Demo;

import java.util.Arrays;
import java.util.List;

public class SettleModel {
    public int getType() {return 0;}
    public List<Integer> getUsers() { return Arrays.asList(1,2 ,3 );};
    public int getDepositEachTime() {return 100;}

    public boolean isSponsorUser(int uid) { return false; }
    public int getSponsorAmount(int uid) { return 1; }
}
