package top.justin.ch04.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.justin.ch02.mapper.BaseMapperTest;
import top.justin.ch04.model.SysUser;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectByUser() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("ad");
            List<SysUser> userList = userMapper.selectByUser(user);
            Assert.assertTrue(userList.size() > 0);

            user = new SysUser();
            user.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(user);
            Assert.assertTrue(userList.size() > 0);

            user = new SysUser();
            user.setUserName("ad");
            user.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(user);
            Assert.assertEquals(0, userList.size());
        }
    }

    @Test
    public void testUpdateByIdSelective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询 1 个 user 对象
            SysUser user = new SysUser();
            //更新 id = 1 的用户
            user.setId(1L);
            //修改邮箱
            user.setUserEmail("test@mybatis.tk");
            //将新建的对象插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
            int result = userMapper.updateByIdSelective(user);
            //只更新 1 条数据
            Assert.assertEquals(1, result);
        } finally {
            //为了不影响数据库中的数据导致其他测试失败，这里选择回滚
            sqlSession.rollback();
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }

    /**
     * 测试插入 if 标签
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test2");
            user.setUserPassword("123456");
            user.setUserEmail("test@email.com");
            user.setUserInfo("halo");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert(user);
            Assert.assertEquals(1, result);
            System.out.println("自增主键id值为: " + user.getId());
//            sqlSession.commit(); 默认的 sqlSessionFactory.openSession()是不自动提交的
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * choose标签的使用
     * */
    @Test
    public void testSelectByIdOrUserName(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("admin");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            //当没有 id 时
            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            //当 id 和 name 都为空时
            query.setUserName(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNull(user);
        } finally {
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testInsertList(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个 user 对象
            List<SysUser> userList = new ArrayList<>();
            for(int i = 0; i < 2; i++){
                SysUser user = new SysUser();
                user.setUserName("test" + i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                userList.add(user);
            }
            //将新建的对象批量插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
            int result = userMapper.insertList(userList);
            Assert.assertEquals(2, result);
            for(SysUser user : userList){
                System.out.println(user.getId());
            }
        } finally {
            //为了不影响数据库中的数据导致其他测试失败，这里选择回滚
            sqlSession.rollback();
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }


    @Test
    public void testUpdateByMap(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询 1 个 user 对象
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1L);
            map.put("user_email", "test@mybatis.tk");
            map.put("user_password", "12345678");
            //更新数据
            userMapper.updateByMap(map);
        } finally {
            //为了不影响数据库中的数据导致其他测试失败，这里选择回滚
            sqlSession.rollback();
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }


}