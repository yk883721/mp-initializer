package com.yk;

import com.yk.mapper.UserMapper;
import com.yk.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        //selectList()根据MP内置的条件构造器查询一个list集合，null表示没有条件，即查询所有
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());

        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert(){

        User user = new User(null, "张三", 23, "zhangsan@atguigu.com", 0);

        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        int result = userMapper.insert(user);

        System.out.println("受影响行数："+result);
        // 雪花ID：1555968054443737089
        System.out.println("id自动获取："+user.getId());
    }


    @Test
    public void testDeleteById(){
        //通过id删除用户信息
        // DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(1555968054443737089L);
        System.out.println("受影响行数："+result);
    }

    @Test
    public void testDeleteBatchIds(){
        //通过多个id批量删除
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响行数："+result);
    }

    @Test
    public void testDeleteByMap(){
        //根据map集合中所设置的条件删除记录
        // DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 24);
        map.put("name", "Billie");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响行数："+result);
    }

    @Test
    public void testUpdateById(){
        User user = new User(4L, "admin", 22, null, 0);
        //UPDATE user SET name=?, age=? WHERE id=?
        int result = userMapper.updateById(user);
        System.out.println("受影响行数："+result);
    }

    @Test
    public void testSelectById(){
        //根据id查询用户信息
        //SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds(){
        //根据多个id查询多个用户信息
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> list = userMapper.selectBatchIds(idList);
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        //通过map条件查询用户信息
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 18);
        map.put("name", "Jone");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectAll(){
        //查询所有用户信息
        // SELECT id,name,age,email FROM user
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }


}