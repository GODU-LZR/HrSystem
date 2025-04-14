package com.example.test.demos.mappers;


import com.example.test.demos.pojo.Organization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationMapper {

    @Select("SELECT * FROM organization")
    List<Organization> selectAll();

    @Select("SELECT * FROM organization WHERE parentId = 0")
    List<Organization> selectTopLevelOrganizations();

    @Select("SELECT o1.*, o2.orgName AS parentName " +
            "FROM organization o1 LEFT JOIN organization o2 ON o1.parentId = o2.orgId")
    List<Organization> selectAllWithParentName();

    @Select("SELECT o1.*, o2.orgName AS parentName " +
            "FROM organization o1 LEFT JOIN organization o2 ON o1.parentId = o2.orgId " +
            "WHERE o1.orgId = #{orgId}")
    Organization selectByIdWithParentName(Integer orgId);

    @Insert("INSERT INTO organization(orgName, orgLevel, parentId, orgSort, status) " +
            "VALUES(#{orgName}, #{orgLevel}, #{parentId}, #{orgSort}, #{status})")
    void insert(Organization organization);

    @Update("UPDATE organization SET orgName = #{orgName}, orgLevel = #{orgLevel}, " +
            "parentId = #{parentId}, orgSort = #{orgSort}, status = #{status} " +
            "WHERE orgId = #{orgId}")
    void update(Organization organization);

    @Delete("DELETE FROM organization WHERE orgId = #{orgId}")
    void delete(Integer orgId);

    @Select("SELECT * FROM organization WHERE orgLevel = #{orgLevel}")
    List<Organization> selectByLevel(@Param("orgLevel") Integer orgLevel);

    // 新增的方法：根据层级和父级ID获取组织列表
    @Select("SELECT * FROM organization WHERE orgLevel = #{orgLevel} AND parentId = #{parentId}")
    List<Organization> selectByLevelAndParentId(@Param("orgLevel") Integer orgLevel, @Param("parentId") Integer parentId);
}
