package com.example.access.admin;

import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUsers() {
        for (int i = 0; i < 10; i++) {
            UserVO user = new UserVO();
            user.setUsername("test" + (i + 1));
            user.setPassword("12345678");
            user.setIsEnabled(true);
            user.setIsLocked(false);
            userService.saveOrUpdateById(user);
        }
    }
}
