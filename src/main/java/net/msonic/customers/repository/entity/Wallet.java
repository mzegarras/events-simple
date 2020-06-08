package net.msonic.customers.repository.entity;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString(exclude = {"id"})
@Document(collection = "wallets")
@Data
public class Wallet implements Serializable {

    @Id
    public String id;
    private Customer customer;
    private WalletType type;
    private WalletStatus status;
    private CurrencyCode currency;
    private String description;
    private double availableBalance;
    private double balance;
}
