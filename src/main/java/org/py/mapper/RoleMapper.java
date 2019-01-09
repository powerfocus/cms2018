package org.py.mapper;

import org.apache.ibatis.annotations.Select;
import org.py.model.Role;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {
    @Select({"select * from role where name=#{name}"})
    Role findByName(String name);
}
