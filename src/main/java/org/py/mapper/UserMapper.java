package org.py.mapper;

import org.apache.ibatis.annotations.Select;
import org.py.model.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    @Select({"select * from user where username=#{username}"})
    User findByUsername(String username);
}
