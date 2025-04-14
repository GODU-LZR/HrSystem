package com.example.test.demos.controller;

import com.example.test.demos.pojo.Position;
import com.example.test.demos.servicer.PositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired(required = false)
    private PositionService positionService;

    @ApiOperation(value = "添加职位")
    @PostMapping
    public String insertPosition(@RequestBody Position position) {
        if (position == null) return "插入失败";
        else return positionService.insertPosition(position);
    }

    @ApiOperation(value = "删除职位")
    @DeleteMapping("/{positionId}")
    public String deletePosition(@PathVariable Integer positionId) {
        return positionService.deletePosition(positionId);
    }

    @ApiOperation("更新职位信息")
    @PutMapping
    public String updatePosition(@RequestBody Position position) {
        return positionService.updatePosition(position);
    }

    @ApiOperation("获取所有职位")
    @GetMapping
    public List<Position> selectPositions() {
        return positionService.selectPositions();
    }

    @ApiOperation("根据ID获取职位信息")
    @GetMapping("/{positionId}")
    public Position selectPositionById(@PathVariable Integer positionId) {
        return positionService.selectPositionById(positionId);
    }
}
