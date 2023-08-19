package vn.dangdnh.service.identity;

import vn.dangdnh.dto.role.RoleDto;

public interface RoleService {

    RoleDto findRoleById(String id);

    RoleDto createRole(RoleDto dto);

    RoleDto updateRoleById(String id, RoleDto dto);

    void deleteRoleById(String id);
}
