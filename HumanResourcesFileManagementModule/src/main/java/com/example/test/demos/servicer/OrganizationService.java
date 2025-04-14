package com.example.test.demos.servicer;



import com.example.test.demos.pojo.Organization;

import java.util.List;

public interface OrganizationService {
    List<Organization> getAllOrganizations();
    List<Organization> getTopLevelOrganizations();
    Organization getOrganizationById(Integer orgId);
    String addOrganization(Organization organization);
    String updateOrganization(Organization organization);
    String deleteOrganization(Integer orgId);
    List<Organization> getOrganizationsByLevel(Integer orgLevel); // 新增方法
    // 新增的方法：根据层级和父级ID获取组织列表
    List<Organization> getOrganizationsByLevelAndParentId(Integer orgLevel, Integer parentId);
}
