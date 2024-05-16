package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class Bank {

    private static final Logger logger = LogManager.getLogger(Bank.class);

    private Map<String, Account> accounts;
    private volatile HashSet<String> blockedAccounts = new HashSet<>();
    private final Random random = new Random();
    final String mssNotMoneyEnough = "Недостаточно денег на счёте: ";
    final String mssAccFromIsBlocked = "Попытка перевода с заблокированного счёта: ";
    final String mssAccToIsBlocked = "Попытка перевода на заблокированный счёт: ";

    public Bank() {
    }

    public synchronized boolean isFraud(String fromAccount, String toAccount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        synchronized (accounts.get(fromAccountNum).compareTo(accounts.get(toAccountNum)) > 0 ? accounts.get(fromAccountNum) : accounts.get(toAccountNum)) {
            synchronized (accounts.get(fromAccountNum).compareTo(accounts.get(toAccountNum)) < 0 ? accounts.get(fromAccountNum) : accounts.get(toAccountNum)) {
                if (!blockedAccounts.contains(fromAccountNum) && !blockedAccounts.contains(toAccountNum)) {
                    if (getBalance(fromAccountNum) < amount) {
                        logger.info(mssNotMoneyEnough + "{}", fromAccountNum);
                    } else {
                        accounts.put(fromAccountNum, decreaseMoney(accounts.get(fromAccountNum), amount));
                        accounts.put(toAccountNum, increaseMoney(accounts.get(toAccountNum), amount));
                        logger.debug("Перевод - {} у.е. со счёта {} на счёт {}", amount, fromAccountNum, toAccountNum);
                        if (amount > 50000) {
                            if (isFraud(fromAccountNum, toAccountNum)) {
                                blockedAccounts.add(fromAccountNum);
                                blockedAccounts.add(toAccountNum);
                            }
                        }
                    }
                } else {
                    if (blockedAccounts.contains(fromAccountNum)) {
                        logger.warn(mssAccFromIsBlocked + "{}", fromAccountNum);
                    }
                    if (blockedAccounts.contains(toAccountNum)) {
                        logger.warn(mssAccToIsBlocked + "{}", toAccountNum);
                    }
                }
            }
        }
    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public synchronized long getSumAllAccounts() {
        long sum = 0L;
        for (Map.Entry<String, Account> acc : accounts.entrySet()) {
            sum = sum + getBalance(acc.getKey());
        }
        return sum;
    }

    public void createAccounts() {
        accounts = new HashMap<>();
        for (int i = 0; i < 100001; i++) {
            String number = Integer.toString(i);
            this.accounts.put(number, new Account(number, (long) (random.nextDouble() * 100000)));
        }
    }

    public int getAccountsCount() {
        return accounts.size();
    }

    private Account increaseMoney(Account account, long amount) {
        Account newAccount = new Account(account);
        long value = account.getMoney() + amount;
        newAccount.setMoney(value);
        return newAccount;
    }

    private Account decreaseMoney(Account account, long amount) {
        Account newAccount = new Account(account);
        long value = account.getMoney() - amount;
        newAccount.setMoney(value);
        return newAccount;
    }
}
