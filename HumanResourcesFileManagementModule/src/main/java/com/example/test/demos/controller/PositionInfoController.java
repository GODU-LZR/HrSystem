package com.example.test.demos.controller;

import com.example.test.demos.annotations.RequiresPermissions;
import com.example.test.demos.pojo.PositionInfo;

import com.example.test.demos.servicer.PositionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "职位管理接口")
@RestController
@RequestMapping("/positions")
public class PositionInfoController {

    @Autowired(required = false)
    private PositionInfoService positionInfoService;

    @ApiOperation("获取所有职位信息")
    @GetMapping     // 添加这个注解
    public List<PositionInfo> getAllPositions() {
        return positionInfoService.getAllPositions();
    }

    @ApiOperation("获取职位类型列表")
    @GetMapping("/position-types")
    public List<PositionInfo> getPositionTypes() {
        return positionInfoService.getPositionTypes();
    }

    @ApiOperation("根据ID获取职位信息")
    @GetMapping("/{positionId}")
    public PositionInfo getPositionById(@PathVariable Integer positionId) {
        return positionInfoService.getPositionById(positionId);
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("添加职位信息")
    @PostMapping    // 添加这个注解
    public String addPosition(@RequestBody PositionInfo positionInfo) {
        String result = positionInfoService.addPosition(positionInfo);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("添加职位类型")
    @PostMapping("/position-types")
    public String addPositionType(@RequestBody PositionInfo positionInfo) {
        String result = positionInfoService.addPositionType(positionInfo);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("更新职位信息")
    @PutMapping("/{positionId}")
    public String updatePosition(@PathVariable Integer positionId, @RequestBody PositionInfo positionInfo) {
        positionInfo.setPositionId(positionId);
        String result = positionInfoService.updatePosition(positionInfo);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("删除职位信息")
    @DeleteMapping("/{positionId}")
    public String deletePosition(@PathVariable Integer positionId) {
        String result = positionInfoService.deletePosition(positionId);
        return "{\"status\": \"" + result + "\"}";
    }

    @ApiOperation("根据父级ID获取职位名称列表")
    @GetMapping("/by-parent/{parentId}")
    public List<PositionInfo> getPositionsByParentId(@PathVariable Integer parentId) {
        return positionInfoService.getPositionsByParentId(parentId);
    }
}
