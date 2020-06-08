package net.msonic.customers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import net.msonic.customers.repository.WalletRepository;
import net.msonic.customers.repository.entity.Customer;
import net.msonic.customers.repository.entity.Wallet;
import net.msonic.customers.repository.entity.WalletStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class WalletServiceImpl implements  WalletService {

    private RabbitTemplate rabbitTemplate;
    private RestTemplate restTemplate;
    private WalletRepository walletRepository;

    private final String KEY_WALLET_NEW = "mybank.wallet.new.v1";
    private final String KEY_WALLET_CREATED = "mybank.wallet.created.v1.%s";
    private final String TOPIC = "wallet";


    @Override
    public Customer save(Customer customer) {
        return walletRepository.save(customer);
    }



    @Override
    public Wallet saveAsync(Wallet wallet) {

        wallet.setStatus(WalletStatus.INACTIVE);
        wallet.setAvailableBalance(0d);
        wallet.setBalance(0d);

        try {
            rabbitTemplate.convertAndSend(TOPIC, KEY_WALLET_NEW, new ObjectMapper().writeValueAsString(wallet));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return wallet;
    }


    @RabbitListener(queues = "wallet.new")
    @Override
    public void saveSync(@Payload String message) {

       Wallet wallet = null;

        try {
            wallet = new ObjectMapper().readValue(message, Wallet.class);
            walletRepository.save(wallet);
            rabbitTemplate.convertAndSend(TOPIC, String.format(KEY_WALLET_CREATED,wallet.getId()), new ObjectMapper().writeValueAsString(wallet));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = "wallet.hook")
    public void notifications(String message) {

        Wallet wallet = null;

        try {
            wallet = new ObjectMapper().readValue(message, Wallet.class);
            HttpEntity<Wallet> request = new HttpEntity<>(wallet);

            //Puedes crear tu hook en https://pipedream.com/

            ResponseEntity<Wallet> response = restTemplate
                    .exchange("https://25125965037e3e4b496550918b6e32fc.m.pipedream.net/wallet", HttpMethod.POST, request, Wallet.class);


        } catch (JsonProcessingException e) {
            log.error("",e);
        }
    }

}