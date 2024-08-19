package com.example.mydemo.controller;

import com.example.mydemo.model.Identifier;
import com.example.mydemo.model.Role;
import com.example.mydemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:8081") // 允许跨域请求
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/search")
    public Role getRoleByName(@RequestParam String roleName) {
        return roleService.getRoleByName(roleName);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/page")
    public Page<Role> getRolesByPage(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "4") Integer size) {
        return roleService.getAllRolesPageable(page,size);
    }


    @GetMapping("/{id}")
    public Optional<Role> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @DeleteMapping("/batch-delete")
    public void deleteRoles(@RequestBody List<Long> ids) {
        roleService.deleteRoles(ids);
    }
}
