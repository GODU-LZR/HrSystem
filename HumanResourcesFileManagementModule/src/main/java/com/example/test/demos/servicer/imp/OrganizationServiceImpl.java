package com.example.test.demos.servicer.imp;


import com.example.test.demos.mappers.OrganizationMapper;
import com.example.test.demos.pojo.Organization;
import com.example.test.demos.servicer.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired(required = false)
    private OrganizationMapper organizationMapper;

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationMapper.selectAllWithParentName();
    }

    @Override
    public List<Organization> getTopLevelOrganizations() {
        return organizationMapper.selectTopLevelOrganizations();
    }

    @Override
    public Organization getOrganizationById(Integer orgId) {
        return organizationMapper.selectByIdWithParentName(orgId);
    }

    @Override
    public String addOrganization(Organization organization) {
        try {
            organizationMapper.insert(organization);
            return "添加成功";
        } catch (Exception e) {
            return "添加失败：" + e.getMessage();
        }
    }

    @Override
    public String updateOrganization(Organization organization) {
        try {
            organizationMapper.update(organization);
            return "更新成功";
        } catch (Exception e) {
            return "更新失败：" + e.getMessage();
        }
    }

    @Override
    public String deleteOrganization(Integer orgId) {
        try {
            // 检查是否有子级组织，避免删除有子级的组织
            List<Organization> children = organizationMapper.selectAll();
            boolean hasChildren = children.stream().anyMatch(org -> org.getParentId().equals(orgId));
            if (hasChildren) {
                return "删除失败：该部门存在子部门，无法删除。";
            }
            organizationMapper.delete(orgId);
            return "删除成功";
        } catch (Exception e) {
            return "删除失败：" + e.getMessage();
        }
    }
    @Override
    public List<Organization> getOrganizationsByLevel(Integer orgLevel) {
        return organizationMapper.selectByLevel(orgLevel);
    }

    // 实现新增的方法：根据层级和父级ID获取组织列表
    @Override
    public List<Organization> getOrganizationsByLevelAndParentId(Integer orgLevel, Integer parentId) {
        return organizationMapper.selectByLevelAndParentId(orgLevel, parentId);
    }
}
