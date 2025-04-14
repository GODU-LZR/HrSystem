package com.example.test.demos.servicer.imp;

import com.example.test.demos.mappers.PermissionMapper;
import com.example.test.demos.mappers.RoleMapper;
import com.example.test.demos.mappers.UserRolePermissionMapper;
import com.example.test.demos.pojo.Permission;
import com.example.test.demos.pojo.Position;
import com.example.test.demos.pojo.Role;
import com.example.test.demos.pojo.UserRolePermission;
import com.example.test.demos.servicer.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PositionService 实现类
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Autowired(required = false)
    private UserRolePermissionMapper userRolePermissionMapper;

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    @Override
    public String insertPosition(Position position) {
        // 1. 创建 Role 对象
        Role role = new Role();
        role.setRoleName(position.getPositionName());
        role.setDescription(position.getDescription());

        // 2. 插入 Role 表，获取生成的 roleId
        roleMapper.insertRole(role);

        // 3. 创建 UserRolePermission 对象
        UserRolePermission urp = new UserRolePermission();
        urp.setUserId(0); // 假用户ID
        urp.setRoleId(role.getRoleId());
        urp.setPermissionIds(position.getPermissionIds());

        // 4. 插入 UserRolePermission 表
        userRolePermissionMapper.insertUserRolePermission(urp);

        return "插入成功";
    }

    @Override
    public String deletePosition(Integer positionId) {
        // 删除角色
        roleMapper.deleteRole(positionId);

        // 删除关联的权限记录
        userRolePermissionMapper.deleteByRoleId(positionId);

        return "删除成功";
    }

    @Override
    public String updatePosition(Position position) {
        // 更新 Role 信息
        Role role = new Role();
        role.setRoleId(position.getPositionId());
        role.setRoleName(position.getPositionName());
        role.setDescription(position.getDescription());
        roleMapper.updateRole(role);

        // 删除原有的权限关联
        userRolePermissionMapper.deleteByRoleId(role.getRoleId());

        // 重新插入新的权限关联
        UserRolePermission urp = new UserRolePermission();
        urp.setUserId(0);
        urp.setRoleId(role.getRoleId());
        urp.setPermissionIds(position.getPermissionIds());
        userRolePermissionMapper.insertUserRolePermission(urp);

        return "更新成功";
    }

    @Override
    public List<Position> selectPositions() {
        List<Role> roles = roleMapper.selectAllRoles();
        List<Position> positions = new ArrayList<>();

        for (Role role : roles) {
            Position position = new Position();
            position.setPositionId(role.getRoleId());
            position.setPositionName(role.getRoleName());
            position.setDescription(role.getDescription());


            // 获取权限ID列表
            List<Integer> permissionIds = userRolePermissionMapper.selectPermissionIdsByRoleIdAndUserId(role.getRoleId(),0);
            position.setPermissionIds(permissionIds);

            // 获取权限名称列表
            List<String> permissionNames = new ArrayList<>();
            for (Integer permissionId : permissionIds) {
                Permission permission = permissionMapper.selectById(permissionId);
                if (permission != null) {
                    permissionNames.add(permission.getPermissionName());
                }
            }
            position.setPermissionNames(permissionNames);

            positions.add(position);
        }

        return positions;
    }

    @Override
    public Position selectPositionById(Integer positionId) {
        Role role = roleMapper.selectRoleById(positionId);
        if (role == null) {
            return null;
        }

        Position position = new Position();
        position.setPositionId(role.getRoleId());
        position.setPositionName(role.getRoleName());
        position.setDescription(role.getDescription());

        // 获取权限ID列表
        List<Integer> permissionIds = userRolePermissionMapper.selectPermissionIdsByRoleIdAndUserId(role.getRoleId(),0);
        position.setPermissionIds(permissionIds);

        // 获取权限名称列表
        List<String> permissionNames = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            Permission permission = permissionMapper.selectById(permissionId);
            if (permission != null) {
                permissionNames.add(permission.getPermissionName());
            }
        }
        position.setPermissionNames(permissionNames);

        return position;
    }
}
