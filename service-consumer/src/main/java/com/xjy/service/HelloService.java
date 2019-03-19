package com.xjy.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;

/**
 * 参考http://www.cnblogs.com/huangjuncong/p/9026949.html
 */
@Service
public class HelloService {
    private final RestTemplate restTemplate;

    @Autowired
    public HelloService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    //多请求结果会聚的注解写法，调用还是跟手写会聚一样调用
    //ObservableExecutionMode.EAGER热执行  ObservableExecutionMode.LAZY冷执行
    //还可以忽略某些异常避免出现服务降级，有时候某些异常出现，但是我们并不想服务降级，异常就异常吧。参数ignoreExceptions = XXX.class
    //groupKey ="" ,threadPoolKey = "",这是线程隔离，比如我需要根据groupKey划分，如果还要对groupKey内的任务进一步划分，就要threadPoolKey，比如对groupKey组内进行
    // 读取数据的时候，是从缓存读，还是数据库读
    // @CacheKey,缓存的注解方式
    @HystrixCommand(fallbackMethod = "helloFallBack", observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<String> helloService(String name) throws ExecutionException, InterruptedException {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        String result = restTemplate.getForObject("http://service-provider/hello/" + name, String.class);
                        subscriber.onNext(result);
                        //Thread.sleep(10000);
                        String result1 = restTemplate.getForObject("http://service-provider/hello/" + name, String.class);
                        subscriber.onNext(result1);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public String helloFallBack(String name) {
        return "error";
    }

}
