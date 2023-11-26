package vn.dangdnh.controller.role.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.role.RoleController;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.role.RoleDto;
import vn.dangdnh.service.RoleService;

@RestController
public class RoleControllerImpl implements RoleController {

    private final RoleService service;

    @Autowired
    public RoleControllerImpl(final RoleService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<RoleDto> findById(String id) {
        var response = service.findRoleById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RoleDto> create(RoleCreateCommand command) {
        var response = service.createRole(command);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> deleteById(String id) {
        var response = service.deleteRoleById(id);
        return ResponseEntity.ok(response);
    }
}
