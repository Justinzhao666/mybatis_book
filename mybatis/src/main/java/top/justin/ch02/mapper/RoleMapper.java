package top.justin.ch02.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import top.justin.ch02.model.SysRole;

public interface RoleMapper {


    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select id,role_name, enabled, create_by, create_time from sys_role where id = #{id}")
    SysRole selectById(Long id);


}
