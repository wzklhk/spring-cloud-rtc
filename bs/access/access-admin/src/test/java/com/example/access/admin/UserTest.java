package com.example.access.admin;

import com.example.access.admin.pojo.user.UserDO;
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
            UserDO user = new UserDO();
            user.setUsername("test" + (i + 1));
            user.setPassword("12345678");
            user.setIsEnabled(true);
            user.setIsLocked(false);
            user.setIsDeleted(false);
            userService.saveOrUpdateById(user);
        }
    }
}
