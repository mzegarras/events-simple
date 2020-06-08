package net.msonic.customers.repository;


import net.msonic.customers.repository.entity.Customer;
import net.msonic.customers.repository.entity.Wallet;

public interface WalletRepository {
    Customer save(Customer customer);
    Wallet save(Wallet wallet);

}