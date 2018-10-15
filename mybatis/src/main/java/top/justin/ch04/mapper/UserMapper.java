package top.justin.ch04.mapper;

import top.justin.ch04.model.SysUser;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<SysUser> selectByUser(SysUser user);

    int updateByIdSelective(SysUser user);

    int insert(SysUser user);

    SysUser selectByIdOrUserName(SysUser query);

    int insertList(List<SysUser> userList);

    void updateByMap(Map<String, Object> map);
}
