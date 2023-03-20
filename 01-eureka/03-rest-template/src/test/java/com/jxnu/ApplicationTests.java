package com.jxnu;

import com.jxnu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ApplicationTests {




    @Test
    void contextLoads() {
        //在JAVA代码中发送一个请求  请求一个页面
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.baidu.com";
        //请求一个页面 返回它的 HTML代码
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);
    }


    @Test
    void testGet(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=zzz";
//        String result = restTemplate.getForObject(url, String.class); //ok，只返回结果
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        //请求头 请求参数 .. 报文 ...
        System.out.println(result);
    }

    @Test
    void testPost1(){
        RestTemplate restTemplate = new RestTemplate();
        User user = new User("Tom",20,8000D);
        String url = "http://localhost:8080/testPost1";
        //发送psot请求 json 参数 web默认使用 jackon 把对象转成 json字符串
        String postForObject = restTemplate.postForObject(url, user, String.class);
        System.out.println(postForObject);
    }

    @Test
    void testPost2(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost2";
        //构建表单参数
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name","Tom");
        map.add("age",22);
        map.add("price",9999D);
        String postForObject = restTemplate.postForObject(url,map,String.class);
        System.out.println(postForObject);
    }

}
