package com.example.mydemo.controller;

import com.example.mydemo.model.User;
import com.example.mydemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/users")
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

    @Value("${upload.path}")
    private String uploadPath;


    @ApiOperation(value = "上传头像")
    @PostMapping("/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传失败: 文件为空");
        }

        File folder = new File(uploadPath);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                return ResponseEntity.badRequest().body("上传失败: 文件夹创建失败");
            }
        }

        // 生成唯一文件名
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        File dest = new File(folder.getAbsolutePath() + File.separator + fileName);

        try {
            // 保存文件
            file.transferTo(dest);
            // 返回文件的相对路径
            return ResponseEntity.ok("/images/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败: " + e.getMessage());
        }
    }




}
