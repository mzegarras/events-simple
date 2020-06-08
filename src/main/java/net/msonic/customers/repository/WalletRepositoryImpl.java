package net.msonic.customers.repository;

import lombok.AllArgsConstructor;
import net.msonic.customers.repository.entity.Customer;
import net.msonic.customers.repository.entity.Wallet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {

    private MongoTemplate mongoTemplate;

    public Customer save(Customer customer) {
        mongoTemplate.save(customer);
        return customer;
    }

    public Wallet save(Wallet wallet) {
        mongoTemplate.save(wallet);
        return wallet;
    }
}
