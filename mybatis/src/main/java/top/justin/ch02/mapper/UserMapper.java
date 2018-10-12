package top.justin.ch02.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import top.justin.ch02.model.SysRole;
import top.justin.ch02.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id); // 这里就没有需要使用到@Param参数，为甚？什么时候可以不用？


    // 当执行的 SQL 返回多个结果时，必须使用 List<SysUser>或 SysUser[]作为返回值，否则会抛出异常
    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(Long id);

    int insert(SysUser user);

    int insert2(SysUser user);

    int updateById(SysUser user);

    int deleteById(SysUser user);

    int deleteById(Long id);


    List<SysRole> selectRolesByUserAndRole(@Param("userId") Long userId,
                                           @Param("enabled") Integer enabled);
}
