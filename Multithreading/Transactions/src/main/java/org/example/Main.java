package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bank bank = createBank();
        long sum1 = bank.getSumAllAccounts();
        int accCount = bank.getAccountsCount() - 1;
        System.out.println("Number of bank accounts: " + accCount);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<?>> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    try {
                        createTransaction(bank, accCount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("Ошибка {}", ex.getMessage(), ex);
                    }
                }
            }));
        }
        for (Future<?> task: tasks) {
            task.get();
        }
        executorService.shutdown();
        long sum2 = bank.getSumAllAccounts();
        System.out.println("Summary balance in bank accounts before transactions:    " + sum1);
        System.out.println("Summary balance in bank accounts after all transactions: " + sum2);
        System.out.println(sum2 - sum1);
    }

    private static Bank createBank() {
        Bank bank = new Bank();
        bank.createAccounts();
        return bank;
    }

    private static void createTransaction(Bank bank, int accCount) throws InterruptedException {
        String accFrom = Long.toString(Math.round(Math.random() * accCount));
        String accTo = Long.toString(Math.round(Math.random() * accCount));
        long amount;
        if (!accFrom.equals(accTo)) {
            if (100 * Math.random() < 5.0) {
                amount = (long) (50000 + 50000 * Math.random());
            }
            else {
                amount = (long) (50000 * Math.random());
            }
            bank.transfer(accFrom, accTo, amount);
        }
    }
}