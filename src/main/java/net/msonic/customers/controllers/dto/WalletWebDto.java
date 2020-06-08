package net.msonic.customers.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.msonic.customers.repository.entity.Wallet;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class WalletWebDto implements Serializable {
    private Wallet wallet;

}
