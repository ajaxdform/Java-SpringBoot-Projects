package com.edigeest.journalentry.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("email", "shubham@gmail.com");  
        Object email = redisTemplate.opsForValue().get("email");
        assertEquals("shubham@gmail.com", email);
    }

}
