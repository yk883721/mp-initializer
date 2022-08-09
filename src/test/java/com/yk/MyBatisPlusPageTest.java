package com.yk;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yk.mapper.UserMapper;
import com.yk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusPageTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 LIMIT ?
        Page<User> page = new Page<>(1, 5);
        Page<User> userPage = userMapper.selectPage(page, null);

        // 1. 入参与返回 相等
        System.out.println(page == userPage);

        // 2. 获取分页数据
        List<User> list = page.getRecords();
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页:" + page.hasPrevious());
        System.out.println("是否有下一页:" + page.hasNext());

    }

}
