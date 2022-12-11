package com.example.access.admin;

import com.example.access.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUsers() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", "test" + i);
            userService.saveOrUpdate(map);
        }
    }
}
