package vn.dangdnh.service.identity;

import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.role.RoleDto;

import javax.validation.Valid;

@Validated
public interface RoleService {

    RoleDto findRoleById(String id);

    RoleDto createRole(@Valid RoleCreateCommand command);

    void deleteRoleById(String id);
}
