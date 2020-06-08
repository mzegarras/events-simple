package net.msonic.customers.controllers;


import lombok.AllArgsConstructor;
import net.msonic.customers.controllers.dto.WalletWebDto;
import net.msonic.customers.repository.entity.Customer;
import net.msonic.customers.repository.entity.Wallet;
import net.msonic.customers.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CustomerController {

    private WalletService customerService;

    @PostMapping
    @RequestMapping(value = "/customers")
    public Customer create(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @PostMapping
    @RequestMapping(value = "/wallets")
    public Wallet create(@RequestBody WalletWebDto walletWebDto){

        //RandomStringUtils.randomAlphanumeric(20)
        return customerService.saveAsync(walletWebDto.getWallet());
    }


}

