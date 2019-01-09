package org.py.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void findAll() {
        roleMapper.selectAll().forEach(System.out::println);
    }
    @Test
    public void findByName() {
        Role role = roleMapper.findByName("ADMIN");
        System.out.println(role);
    }
}