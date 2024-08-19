package com.example.mydemo.service;

import com.example.mydemo.model.Identifier;
import com.example.mydemo.model.Role;
import com.example.mydemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // 修改后的分页查询方法
//    public Page<Identifier> getIdentifiersPage(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return identifierRepository.findAll(pageable);
//    }
    public Page<Role> getAllRolesPageable(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return roleRepository.findAll(pageable);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role newRole) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setRoleName(newRole.getRoleName());
                    existingRole.setDescription(newRole.getDescription());
                    return roleRepository.save(existingRole);
                })
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + id + " not found"));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    // 批量删除方法
    public void deleteRoles(List<Long> ids) {
        roleRepository.deleteAllById(ids);
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
