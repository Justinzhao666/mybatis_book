package top.justin.ch02.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.justin.ch02.model.SysRole;
import top.justin.ch02.model.SysUser;

import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
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
        //不要忘记关闭 - 这里使用了try(xxx)不需要再close了
    }

    /**
     * 注解方式 查询
     */
    @Test
    public void testSelectById2() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理员", role.getRoleName());
        } finally {
            sqlSession.close();
        }
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


    /**
     * 测试多表查询，结果插入组合对象中
     */
    @Test
    public void testSelectRolesByUserid() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> sysRoles = userMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size() > 0);
        }
    }


    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@email.com");
            user.setUserInfo("halo");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert(user);
            Assert.assertEquals(1, result);
            Assert.assertNull(user.getId());
//            sqlSession.commit(); 默认的 sqlSessionFactory.openSession()是不自动提交的
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }


    /**
     * 测试插入,并获取插入后的mysql自动生成的主键值
     */
    @Test
    public void testInsert2() {
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
            int result = userMapper.insert2(user);
            Assert.assertEquals(1, result);
            System.out.println("自增主键id值为: " + user.getId());
//            sqlSession.commit(); 默认的 sqlSessionFactory.openSession()是不自动提交的
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 测试插入
     */
    @Test
    public void testUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = userMapper.selectById(1L);
            sysUser.setUserName("name_update");
            int res = userMapper.updateById(sysUser);
            Assert.assertEquals(1, res);
            System.out.println(userMapper.selectById(1L));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }


    /**
     * 测试的删除
     */
    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询 1 个 user 对象，根据 id = 1 查询
            SysUser user1 = userMapper.selectById(1L);
            //现在还能查询出 user 对象
            Assert.assertNotNull(user1);
            //调用方法删除
            Assert.assertEquals(1, userMapper.deleteById(1L));
            //再次查询，这时应该没有值，为 null
            Assert.assertNull(userMapper.selectById(1L));
            //使用 SysUser 参数再做一遍测试，根据 id = 1001  查询
            SysUser user2 = userMapper.selectById(1001L);
            //现在还能查询出 user 对象
            Assert.assertNotNull(user2);
            //调用方法删除，注意这里使用参数为 user2
            Assert.assertEquals(1, userMapper.deleteById(user2));
            //再次查询，这时应该没有值，为 null
            Assert.assertNull(userMapper.selectById(1001L));
            //使用 SysUser 参数再做一遍测试
        } finally {
            //为了不影响数据库中的数据导致其他测试失败，这里选择回滚
            //由于默认的 sqlSessionFactory.openSession() 是不自动提交的，
            //因此不手动执行 commit 也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }


    /**
     * 测试查询多个参数
     */
    @Test
    public void testSelectRolesByUserAndRole() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> userList = userMapper.selectRolesByUserAndRole(1L, 1);
            Assert.assertNotNull(userList);
            //角色数量大于 0 个
            Assert.assertTrue(userList.size() > 0);
        } finally {
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }


}