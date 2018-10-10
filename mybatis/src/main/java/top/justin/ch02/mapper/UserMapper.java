package top.justin.ch02.mapper;

import top.justin.ch02.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);

    // 当执行的 SQL 返回多个结果时，必须使用 List<SysUser>或 SysUser[]作为返回值，否则会抛出异常
    List<SysUser> selectAll();
}
