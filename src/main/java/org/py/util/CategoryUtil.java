package org.py.util;

import org.py.mapper.ColumntypeMapper;
import org.py.model.Columntype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public final class CategoryUtil {
    @Autowired
    private ColumntypeMapper mapper;
    public CategoryUtil() { }
    public void tree(long pid, int level, StringBuffer strbuffer) {
        Example example = Example.builder(Columntype.class)
                .where(Sqls.custom().andEqualTo("pid", pid)).build();
        mapper.selectByExample(example).forEach(col -> {
            if(pid == 0)
                strbuffer.append("|-");
            for(int i = 0; i < level; ++i)
                strbuffer.append("---");
            strbuffer.append(col.getTypename() + "\r\n");
            tree(col.getId(), level + 1, strbuffer);
        });
    }
    public void treeList(long pid, int level, List<Map<String, Columntype>> list) {
        Example example = Example.builder(Columntype.class)
                .where(Sqls.custom().andEqualTo("pid", pid)).build();
        StringBuffer strbuffer = new StringBuffer();
        mapper.selectByExample(example).forEach(col -> {
            if(pid == 0)
                strbuffer.append("|-");
            for(int i = 0; i < level; ++i)
                strbuffer.append("---");
            Map<String, Columntype> m = new HashMap<>();
            m.put(new String(strbuffer.toString()), col);
            list.add(m);
            strbuffer.delete(0, strbuffer.length());
            treeList(col.getId(), level + 1, list);
        });
    }
}
