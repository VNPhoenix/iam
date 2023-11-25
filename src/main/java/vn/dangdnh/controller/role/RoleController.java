package vn.dangdnh.controller.role;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.role.RoleDto;

@RequestMapping("${app.context-path}")
public interface RoleController {

    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDto> findById(@PathVariable String id);

    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDto> create(@RequestBody RoleCreateCommand command);

    @DeleteMapping(value = "/roles/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> deleteById(@PathVariable String id);
}
