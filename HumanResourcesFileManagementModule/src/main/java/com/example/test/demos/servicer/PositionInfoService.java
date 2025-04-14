// 接口
package com.example.test.demos.servicer;

import com.example.test.demos.pojo.PositionInfo;
import java.util.List;

public interface PositionInfoService {
    List<PositionInfo> getAllPositions();
    List<PositionInfo> getPositionTypes();
    PositionInfo getPositionById(Integer positionId);
    String addPosition(PositionInfo positionInfo);
    String addPositionType(PositionInfo positionInfo);
    String updatePosition(PositionInfo positionInfo);
    String deletePosition(Integer positionId);
    List<PositionInfo> getPositionsByParentId(Integer parentId);

}