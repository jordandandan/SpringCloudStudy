package com.xjy.controller;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.xjy.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author bjxujiayi
 * @create 2019-03-07 20:31
 **/
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private TestFeign testFeign;
    @Resource
    private HelloService helloService;

    @GetMapping("/hello/{name}")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "helloFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
    })
    public String hello(@PathVariable String name) throws Exception {

        String url = "http://service-provider/hello/";
        String result = "";
        Future<String> future = new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject(url + name, String.class);
            }

            @Override
            public String get() throws UnsupportedOperationException {
                return invoke();
            }
        };

        result = future.get();


        return result;
    }

    //fall back 方法需要和原方法参数一致
    public String helloFallback(String name) {
        return "hello-fallback" + name;
    }


    @GetMapping("/feign/{name}")
    @ResponseBody
    public String feiggn(@PathVariable String name) {
        return testFeign.hello(name);
    }

    @GetMapping("/merge/{name}")
    @ResponseBody
    public String mergeResult(@PathVariable String name) throws ExecutionException, InterruptedException {
        List<String> mergedResult = Lists.newArrayList();
        Observable<String> observable = helloService.helloService(name);

        //订阅
        observable.subscribe(new Observer<String>() {
            //请求完成的方法
            @Override
            public void onCompleted() {
                log.info("会聚完了所有查询请求");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error:", throwable.getCause());
            }

            //订阅调用事件，结果会聚的地方，用集合去装返回的结果会聚起来。
            @Override
            public void onNext(String s) {
                log.info("结果来了.....");
                mergedResult.add(s);
            }
        });

        return mergedResult.toString();

    }
    @GetMapping("/zuul/{name}")
    @ResponseBody
    public String zuulRequest(@PathVariable String name){
        String url = "http://service-provider/hello/";
        return restTemplate.getForObject(url + name, String.class);
    }
}
