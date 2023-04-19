package com.vht.client.controller.role.impl;

import com.vht.client.controller.role.RoleController;
import com.vht.client.definition.URIs;
import com.vht.client.definition.message.response.ResponseMessages;
import com.vht.client.dto.role.RoleDto;
import com.vht.client.service.identity.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(URIs.ROLE)
public class RoleControllerImpl implements RoleController {

    private final RoleService service;

    @Autowired
    public RoleControllerImpl(RoleService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<RoleDto> findById(@PathVariable String id) {
        RoleDto response = service.findRoleById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto dto) {
        RoleDto response = service.createRole(dto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RoleDto> updateById(@PathVariable String id, @RequestBody @Valid RoleDto dto) {
        RoleDto response = service.updateRoleById(id, dto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        service.deleteRoleById(id);
        return ResponseEntity.ok(ResponseMessages.ENTITY_DELETED_SUCCESSFULLY);
    }
}
