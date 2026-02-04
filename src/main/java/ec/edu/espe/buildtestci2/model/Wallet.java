package ec.edu.espe.buildtestci2.model;

import java.util.UUID;

public class Wallet {

    private final String id;
    private final String ownerEmail;
    private double balance;

    public Wallet(String ownerEmail,double balance) {
        this.balance = balance;
        this.ownerEmail = ownerEmail;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount){
        if(amount <0){
            throw new IllegalStateException("Invalid Amount");

        }

        this.balance += amount;
    }

    public void withDraw(double amount){
        if(amount <=0 || balance < amount){
            throw new IllegalStateException("Insufficient Balance");
        }

        this.balance -= amount;
    }


}
