package com.example.test.demos.controller;

import com.example.test.demos.annotations.RequiresPermissions;
import com.example.test.demos.pojo.Organization;

import com.example.test.demos.servicer.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "组织管理接口")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired(required = false)
    private OrganizationService organizationService;

    @ApiOperation("获取所有组织信息")
    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @ApiOperation("获取顶级组织列表")
    @GetMapping("/top")
    public List<Organization> getTopLevelOrganizations() {
        return organizationService.getTopLevelOrganizations();
    }

    @ApiOperation("根据ID获取组织信息")
    @GetMapping("/{orgId}")
    public Organization getOrganizationById(@PathVariable Integer orgId) {
        return organizationService.getOrganizationById(orgId);
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("添加组织信息")
    @PostMapping
    public String addOrganization(@RequestBody Organization organization) {
        String result = organizationService.addOrganization(organization);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("更新组织信息")
    @PutMapping("/{orgId}")
    public String updateOrganization(@PathVariable Integer orgId, @RequestBody Organization organization) {
        organization.setOrgId(orgId);
        String result = organizationService.updateOrganization(organization);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({18}) // 需要权限ID 18
    @ApiOperation("删除组织信息")
    @DeleteMapping("/{orgId}")
    public String deleteOrganization(@PathVariable Integer orgId) {
        String result = organizationService.deleteOrganization(orgId);
        return "{\"status\": \"" + result + "\"}";
    }

    @ApiOperation("根据层级和父级ID获取组织列表")
    @GetMapping("/level/{orgLevel}")
    public List<Organization> getOrganizationsByLevel(
            @PathVariable Integer orgLevel,
            @RequestParam(value = "parentId", required = false) Integer parentId) {
        if (parentId != null) {
            return organizationService.getOrganizationsByLevelAndParentId(orgLevel, parentId);
        } else {
            return organizationService.getOrganizationsByLevel(orgLevel);
        }
    }
}
