package net.msonic.customers.service;


import net.msonic.customers.repository.entity.Customer;
import net.msonic.customers.repository.entity.Wallet;

public interface WalletService {
    Customer save(Customer customer);
    Wallet saveAsync(Wallet walletDto);
    void saveSync(String message );

}
