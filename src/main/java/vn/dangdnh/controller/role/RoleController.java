package vn.dangdnh.controller.role;

import vn.dangdnh.definition.URIs;
import vn.dangdnh.dto.role.RoleDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(URIs.ROLE)
public interface RoleController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDto> findById(@PathVariable String id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto dto);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDto> updateById(@PathVariable String id, @RequestBody @Valid RoleDto dto);

    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> deleteById(@PathVariable String id);
}
