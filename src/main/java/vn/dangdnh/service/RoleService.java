package vn.dangdnh.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.role.RoleDto;

@Validated
public interface RoleService {

    RoleDto findRoleById(String id);

    RoleDto createRole(@Valid RoleCreateCommand command);

    void deleteRoleById(String id);
}
