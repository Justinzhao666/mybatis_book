package top.justin.ch02.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.justin.ch02.model.SysUser;

import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectByid() {
        //获取 sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取 UserMapper 接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectByid 方法，查询 id = 1 的用户
            SysUser user = userMapper.selectById(1L);
            //user 不为空
            Assert.assertNotNull(user);
            //userName = admin
            Assert.assertEquals("admin", user.getUserName());
        }
        //不要忘记关闭
    }


    @Test
    public void testSelectAll() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> sysUsers = userMapper.selectAll();
            Assert.assertNotNull(sysUsers);
            Assert.assertTrue(sysUsers.size() > 0);
        }
    }

}