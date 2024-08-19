package com.example.mydemo.service;

import com.example.mydemo.model.User;
import com.example.mydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getRoleByName(String roleName) {
        return userRepository.findByRoleName(roleName);
    }

    public List<User> getAllRoles() {
        return userRepository.findAll();
    }

    // 修改后的分页查询方法
//    public Page<Identifier> getIdentifiersPage(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return identifierRepository.findAll(pageable);
//    }
    public Page<User> getAllRolesPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable);
    }

    public User saveRole(User user) {
        return userRepository.save(user);
    }

    public User updateRole(Long id, User newUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setRoleName(newUser.getRoleName());
                    existingUser.setDescription(newUser.getDescription());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + id + " not found"));
    }

    public void deleteRole(Long id) {
        userRepository.deleteById(id);
    }

    // 批量删除方法
    public void deleteRoles(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public Optional<User> getRoleById(Long id) {
        return userRepository.findById(id);
    }


}
