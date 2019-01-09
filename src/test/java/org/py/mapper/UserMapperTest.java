package org.py.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test() {
        userMapper.selectAll().forEach(System.out::println);
    }
    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("admin");
        System.out.println(user);
    }
}