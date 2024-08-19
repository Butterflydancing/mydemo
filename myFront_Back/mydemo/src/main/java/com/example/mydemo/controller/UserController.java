package com.example.mydemo.controller;

import com.example.mydemo.model.User;
import com.example.mydemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:8081") // 允许跨域请求
@Api(tags = "用户管理")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation(value = "根据角色名称查询角色")
    @GetMapping("/search")
    public User getRoleByName(@RequestParam @ApiParam(value = "角色名称", required = true) String roleName) {
        return userService.getRoleByName(roleName);
    }

    @GetMapping
    @ApiOperation(value = "获取所有角色")
    public List<User> getAllRoles() {
        return userService.getAllRoles();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页获取所有角色")
    public Page<User> getRolesByPage(@RequestParam(defaultValue = "0") @ApiParam(value = "页码", required = true) Integer page,
                                     @RequestParam(defaultValue = "5") @ApiParam(value = "每页记录数", required = true) Integer size) {
        return userService.getAllRolesPageable(page,size);
    }



    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取角色")
    public Optional<User> getRoleById(@PathVariable @ApiParam(value = "角色ID", required = true) Long id) {
        return userService.getRoleById(id);
    }

    @PostMapping
    @ApiOperation(value = "创建角色")
    public User createRole(@RequestBody @ApiParam(value = "角色信息", required = true) User user) {
        return userService.saveRole(user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新角色")
    public User updateRole(@PathVariable @ApiParam(value = "角色ID", required = true) Long id, @RequestBody @ApiParam(value = "更新后的角色信息", required = true) User user) {
        return userService.updateRole(id, user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色")
    public void deleteRole(@PathVariable @ApiParam(value = "角色ID", required = true) Long id) {
        userService.deleteRole(id);
    }

    @DeleteMapping("/batch-delete")
    @ApiOperation(value = "批量删除角色")
    public void deleteRoles(@RequestBody @ApiParam(value = "角色ID列表", required = true) List<Long> ids) {
        userService.deleteRoles(ids);
    }


}
