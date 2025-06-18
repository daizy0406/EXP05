package ynu.edu.controller;

import com.netflix.appinfo.InstanceInfo;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.Resource;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ynu.edu.entity.Cart;
import ynu.edu.entity.CommonResult;
import ynu.edu.entity.User;
import ynu.edu.feign.ServiceProviderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private RestTemplate restTemplate;



    @Resource
    private ServiceProviderService serviceInstance;

//    @Retry(name = "backendA", fallbackMethod = "getCartByIdDown")
//    @RateLimiter(name = "rate1", fallbackMethod = "getCartByIdDown")
//    @CircuitBreaker(name = "backendA",fallbackMethod = "getCartByIdDown")

    @GetMapping("/getCartById/{userId}")
    @LoadBalanced
    @Bulkhead(name="bulkheadA",type = Bulkhead.Type.THREADPOOL,fallbackMethod = "getCartByIdDown")
    public CompletableFuture<User> getCartById(@PathVariable("userId") Integer userId) {
        CompletableFuture<User> user = CompletableFuture.supplyAsync(()->{return serviceInstance.GetUserById(userId);});
        System.out.println("正常执行！");
        return user;
    }

    public CompletableFuture<User> getCartByIdDown(Integer userId, Exception e) {
        e.printStackTrace();
        String message = "该功能当前异常火爆，请稍后再试！";
        System.out.println(message);
        CompletableFuture<User> result = CompletableFuture.supplyAsync(()->{return new CommonResult<>(440,"fallback",new User()).getResult();});
        return result;
    }
}
