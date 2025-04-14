package com.example.test.demos.servicer;

import com.example.test.demos.pojo.Position;

import java.util.List;

public interface PositionService {
    String insertPosition(Position position);

    String deletePosition(Integer positionId);

    String updatePosition(Position position);

    List<Position> selectPositions();

    Position selectPositionById(Integer positionId);
}
