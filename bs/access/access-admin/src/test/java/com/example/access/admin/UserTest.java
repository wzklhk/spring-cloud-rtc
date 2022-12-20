package com.example.access.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.access.admin.api.repository.UserRepository;
import com.example.access.admin.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void addUsers() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", "test" + i);
            userService.saveOrUpdate(map);
        }
    }

    @Test
    public void count() {
        Map<String, Object> map = new HashMap<>();
        map.put("password", "88888888");
        Long count = userService.count(map);
        System.out.println(count);
    }

    @Test
    public void emTest() {
        String sql = "select " +
                "au.id as 'id', " +
                "au.username as 'username', " +
                "au.password as 'password', " +
                "au.is_locked as 'isLocked', " +
                "au.is_enabled as 'isEnabled', " +
                "au.is_deleted as 'isDeleted', " +
                "au.create_time as 'createTime', " +
                "au.update_time as 'updateTime' " +
                "from access_user au";
        Query query = em.createNativeQuery(sql);
        List resultList = query.getResultList();
        for (Object o : resultList) {
            String s = JSONObject.toJSONString(o);
            System.out.println(s);
        }

    }
}
