package ynu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ynu.edu.rule.CustomThreeTimeLoadBalanceConfig;

@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(name = "provider-service",configuration = CustomThreeTimeLoadBalanceConfig.class)
public class ConsumerApplication16000 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication16000.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
