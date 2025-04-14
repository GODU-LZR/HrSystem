// 实现类
package com.example.test.demos.servicer.imp;


import com.example.test.demos.mappers.PositionInfoMapper;
import com.example.test.demos.pojo.PositionInfo;

import com.example.test.demos.servicer.PositionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionInfoServiceImpl implements PositionInfoService {

    @Autowired(required = false)
    private PositionInfoMapper positionInfoMapper;

    @Override
    public List<PositionInfo> getAllPositions() {
        return positionInfoMapper.selectAll();
    }

    @Override
    public List<PositionInfo> getPositionTypes() {
        return positionInfoMapper.selectPositionTypes();
    }

    @Override
    public PositionInfo getPositionById(Integer positionId) {
        return positionInfoMapper.selectById(positionId);
    }

    @Override
    public String addPosition(PositionInfo positionInfo) {
        try {
            positionInfoMapper.insert(positionInfo);
            return "添加成功";
        } catch (Exception e) {
            return "添加失败：" + e.getMessage();
        }
    }

    @Override
    public String addPositionType(PositionInfo positionInfo) {
        try {
            positionInfo.setParentId(0); // 设置为顶级类型
            positionInfoMapper.insert(positionInfo);
            return "添加成功";
        } catch (Exception e) {
            return "添加失败：" + e.getMessage();
        }
    }

    @Override
    public String updatePosition(PositionInfo positionInfo) {
        try {
            positionInfoMapper.update(positionInfo);
            return "更新成功";
        } catch (Exception e) {
            return "更新失败：" + e.getMessage();
        }
    }

    @Override
    public String deletePosition(Integer positionId) {
        try {
            positionInfoMapper.delete(positionId);
            return "删除成功";
        } catch (Exception e) {
            return "删除失败：" + e.getMessage();
        }
    }
    @Override
    public List<PositionInfo> getPositionsByParentId(Integer parentId) {
        return positionInfoMapper.selectByParentId(parentId);
    }

}