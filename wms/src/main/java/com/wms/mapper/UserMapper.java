package com.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2024-07-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("<script>" +
            "SELECT * FROM (" +
            "  SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS row_num " +
            "  FROM [user] " +
            "  <where>" +
            "    <if test='name != null and name != \"\"'> " +
            "      AND name LIKE CONCAT('%', #{name}, '%') " +
            "    </if>" +
            "    <if test='sex != null and sex != \"\"'> " +
            "      AND sex = #{sex} " +
            "    </if>" +
            "    <if test='roleId != null and roleId != \"\"'> " +
            "      AND role_id = #{roleId} " +
            "    </if>" +
            "  </where>" +
            ") AS temp_table " +
            "WHERE row_num BETWEEN #{start} AND #{end} " +
            "</script>")
    List<User> selectPage(@Param("name") String name, @Param("sex") String sex, @Param("roleId") String roleId, @Param("start") int start, @Param("end") int end);

    @Select("<script>" +
            "SELECT COUNT(*) FROM [user] " +
            "  <where>" +
            "    <if test='name != null and name != \"\"'> " +
            "      AND name LIKE CONCAT('%', #{name}, '%') " +
            "    </if>" +
            "    <if test='sex != null and sex != \"\"'> " +
            "      AND sex = #{sex} " +
            "    </if>" +
            "    <if test='roleId != null and roleId != \"\"'> " +
            "      AND role_id = #{roleId} " +
            "    </if>" +
            "  </where>" +
            "</script>")
    int selectCount(@Param("name") String name, @Param("sex") String sex, @Param("roleId") String roleId);

    void insertUser(User insertUsers);

    List<User> getAllUsers();
}
