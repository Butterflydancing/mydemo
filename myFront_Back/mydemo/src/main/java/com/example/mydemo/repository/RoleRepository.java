package com.example.mydemo.repository;

import com.example.mydemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // 可以添加自定义查询方法，例如根据角色名称查找角色
    Role findByRoleName(String roleName);
}
