package org.example;

public class Account implements Comparable<Account> {

    private long money;
    private String accNumber;

    public Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.money = money;
    }

    public Account(Account account) {
        this.money = account.getMoney();
        this.accNumber = account.getAccNumber();
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
