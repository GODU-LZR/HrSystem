package com.example.test.demos.mappers;

import com.example.test.demos.pojo.PositionInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PositionInfoMapper {

    @Select("SELECT * FROM positionInfo")
    List<PositionInfo> selectAll();

    @Select("SELECT * FROM positionInfo WHERE parentId = 0")
    List<PositionInfo> selectPositionTypes();

    @Select("SELECT * FROM positionInfo WHERE positionId = #{positionId}")
    PositionInfo selectById(Integer positionId);

    @Insert("INSERT INTO positionInfo(positionType, positionName, parentId, positionSort, isEnabled) " +
            "VALUES(#{positionType}, #{positionName}, #{parentId}, #{positionSort}, #{isEnabled})")
    void insert(PositionInfo positionInfo);

    @Update("UPDATE positionInfo SET positionType = #{positionType}, positionName = #{positionName}, " +
            "parentId = #{parentId}, positionSort = #{positionSort}, isEnabled = #{isEnabled} " +
            "WHERE positionId = #{positionId}")
    void update(PositionInfo positionInfo);

    @Delete("DELETE FROM positionInfo WHERE positionId = #{positionId}")
    void delete(Integer positionId);

    @Select("SELECT * FROM positionInfo WHERE parentId = #{parentId}")
    List<PositionInfo> selectByParentId(@Param("parentId") Integer parentId);

}
