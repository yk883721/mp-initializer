package com.yk;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yk.mapper.UserMapper;
import com.yk.model.User;
import com.yk.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        // Parameters: %a%(String), 20(Integer), 30(Integer)
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .like("name", "a")
                .between("age", 20, 30).isNotNull("email");

        userMapper.selectList(wrapper);
    }

    @Test
    public void test02() {
        //按年龄降序查询用户，如果年龄相同则按id升序排列
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .orderByDesc("age")
                .orderByAsc("id");
        userMapper.selectList(wrapper);
    }

    @Test
    public void test03() {
        //删除email为空的用户
        // 保持逻辑删除
        // UPDATE user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .isNull("email");
        userMapper.delete(wrapper);
    }

    @Test
    public void test04() {
        // 将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
        // UPDATE user SET age=?, email=? WHERE is_deleted=0 AND (age > ? AND name LIKE ? OR email IS NULL)
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .gt("age", 20)
                .like("name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        userMapper.update(user, wrapper);
    }

    @Test
    public void test05() {
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        // UPDATE user SET age=?, email=? WHERE is_deleted=0 AND (name LIKE ? AND (age > ? OR email IS NULL))
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .like("name", "a")
                .and(t -> t.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        userMapper.update(user, wrapper);
    }

    @Test
    public void test06() {
        // 查询用户信息的username和age字段
        // SELECT name,age FROM user WHERE is_deleted=0
        QueryWrapper<User> wrapper = Wrappers
                .<User>query()
                .select("name", "age");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
    }

    @Test
    public void test07() {
        // /将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        // UPDATE user SET age=?,email=? WHERE is_deleted=0 AND (name LIKE ? AND (age > ? OR email IS NULL))
        UpdateWrapper<User> wrapper = Wrappers.<User>update()
                .like("name", "a")
                .and(t -> t.gt("age", 20).or().isNull("email"))

                .set("age", 18)
                .set("email", "user@atguigu.com");

        int result = userMapper.update(null, wrapper);
        System.out.println(result);
    }

    @Test
    public void test09() {
        // 定义查询条件，有可能为null（用户未输入）
        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (name LIKE ? AND age >= ? AND age <= ?)
        String name = "a";
        Integer ageBegin = 10;
        Integer ageEnd = 24;

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotBlank(name), User::getName, name)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test10() {
        // 定义查询条件，有可能为null（用户未输入）
        // UPDATE user SET age=?,email=? WHERE is_deleted=0 AND (name LIKE ? AND (age < ? OR email IS NULL))
        LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate()
                .like(User::getName, "a")
                .and(t -> t.lt(User::getAge, 24).or().isNull(User::getEmail))

                .set(User::getAge, 18)
                .set(User::getEmail, "user@atguigu.com");

        int rows = userMapper.update(null, wrapper);
        System.out.println(rows);
    }

}
