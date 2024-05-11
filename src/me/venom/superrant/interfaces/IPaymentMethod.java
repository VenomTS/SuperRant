package me.venom.superrant.interfaces;

public interface IPaymentMethod
{
    double getBalance();
    boolean hasEnoughBalance(double amount);
    void withdraw(double amount);
    void deposit(double amount);
}
