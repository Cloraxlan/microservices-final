package edu.msoe.microservicefinal.snowmanNotifyMicroservice.config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailjetConfig {
    @Value("${email.privateKey}")
    private String privateKey;

    @Value("${email.apiKey}")
    private String apiKey;

    @Bean
    public MailjetClient mailjetClient(){
        return new MailjetClient(apiKey, privateKey, new ClientOptions("v3.1"));
    }
}
