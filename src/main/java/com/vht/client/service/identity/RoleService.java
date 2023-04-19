package com.vht.client.service.identity;

import com.vht.client.dto.role.RoleDto;

public interface RoleService {

    RoleDto findRoleById(String id);

    RoleDto createRole(RoleDto dto);

    RoleDto updateRoleById(String id, RoleDto dto);

    void deleteRoleById(String id);
}
