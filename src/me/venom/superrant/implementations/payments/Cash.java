package me.venom.superrant.implementations.payments;

import me.venom.superrant.interfaces.IPaymentMethod;

public class Cash implements IPaymentMethod
{
    private double balance;

    public Cash()
    {
        balance = 0d;
    }

    @Override
    public double getBalance() { return balance; }

    @Override
    public boolean hasEnoughBalance(double amount)
    {
        return balance >= amount;
    }

    @Override
    public void withdraw(double amount)
    {
        if(!hasEnoughBalance(amount)) return; // This is just fail-safe
        balance -= amount;
    }

    @Override
    public void deposit(double amount)
    {
        if(amount <= 0) return;
        balance += amount;
    }
}
