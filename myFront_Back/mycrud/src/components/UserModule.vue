<template>
  <div class="container">
    <h1>用户管理</h1>

    <!-- 搜索区域 -->
    <el-row class="search-row" gutter="20">
      <el-col :span="8">
        <el-input v-model="searchRoleName" placeholder="搜索用户名称" clearable></el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="searchRole">搜索</el-button>
        <el-button type="warning" @click="resetSearch" style="margin-left: 10px;">重置</el-button>
      </el-col>
    </el-row>

    <!-- 搜索结果 -->
    <div v-if="searchedRole" class="search-result">
      <h3>搜索结果:</h3>
      <el-table :data="[searchedRole]" style="width: 100%">
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column label="头像">
          <template v-slot="scope">
            <div class="avatar-container">
              <img :src="`http://localhost:8080${scope.row.avatarPath}`" alt="头像" style="width: 50px; height: 50px; border-radius: 50%;" />
              
            </div>
          </template>
        </el-table-column>
    <el-table-column prop="roleName" label="角色名称" width="180">
    </el-table-column>
    <el-table-column prop="description" label="描述" width="300">
    </el-table-column>
  </el-table>
    </div>

    <!-- 添加用户 -->
    <el-row class="add-role-row" gutter="20">
      <el-col :span="7">
        <el-input v-model="newRole.roleName" placeholder="输入用户名称" clearable></el-input>
      </el-col>
      <el-col :span="7">
        <el-input v-model="newRole.description" placeholder="输入描述" clearable></el-input>
      </el-col>
      <el-col :span="6">
        <el-upload
          class="avatar-uploader"
          action=""
          :show-file-list="false"
          :on-change="handleAvatarChange"
          :before-upload="beforeAvatarUpload"
        >
          <el-button size="small" type="primary">选择头像</el-button>
        </el-upload>
      </el-col>
      <el-col :span="4">
        <el-button type="success" @click="createRole">添加用户</el-button>
      </el-col>
    </el-row>

    <!-- 用户表格 -->
    <div class="table-container">
      <el-table :data="roles.content" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID"></el-table-column>

        <!-- 头像列 -->
        <el-table-column label="头像">
          <template v-slot="scope">
            <div class="avatar-container">
              <img :src="`http://localhost:8080${scope.row.avatarPath}`" alt="头像" style="width: 50px; height: 50px; border-radius: 50%;" />
              <!-- 编辑模式下显示的上传头像按钮 -->
              <el-upload
                class="avatar-uploader"
                action=""
                :show-file-list="false"
                :on-change="handleAvatarChange"
                :before-upload="beforeAvatarUpload"
                v-if="editMode && editRoleData.id === scope.row.id"
              >
                <el-button size="small" type="primary">修改头像</el-button>
              </el-upload>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="用户名称">
          <template v-slot="scope">
            <el-input v-if="editMode && editRoleData.id === scope.row.id" v-model="editRoleData.roleName" placeholder="输入新用户名称"></el-input>
            <span v-else>{{ scope.row.roleName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="描述">
          <template v-slot="scope">
            <el-input v-if="editMode && editRoleData.id === scope.row.id" v-model="editRoleData.description" placeholder="输入新描述"></el-input>
            <span v-else>{{ scope.row.description }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template v-slot="scope">
            <el-button v-if="editMode && editRoleData.id === scope.row.id" type="primary" @click="updateRole">保存</el-button>
            <el-button v-else @click="editRole(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteRole(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 批量删除按钮 -->
    <el-button type="danger" @click="batchDeleteRoles" class="batch-delete-button">删除选中用户</el-button>

    <!-- 分页控件 -->
    <el-pagination
      @current-change="handlePageChange"
      :current-page="currentPage + 1"
      :page-size="pageSize"
      :total="roles.totalElements"
      layout="prev, pager, next, jumper"
      class="pagination">
    </el-pagination>
    
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      roles: {}, // 用于存储分页数据
      selectedRoles: [],
      newRole: { roleName: '', description: '', avatarPath: '' },
      editMode: false,
      editRoleData: { id: null, roleName: '', description: '', avatarPath: '' },
      searchRoleName: '',
      searchedRole: null,
      currentPage: 0, // 当前页码
      pageSize: 4, // 每页显示的条目数
      avatarFile: null // 用于存储选择的头像文件
    };
  },
  methods: {
    fetchRoles() {
      axios.get('http://localhost:8080/api/users/page', {
        params: {
          page: this.currentPage,
          size: this.pageSize
        }
      })
      .then(response => {
        this.roles = response.data;
      });
    },
    searchRole() {
      axios.get('http://localhost:8080/api/users/search', {
        params: { roleName: this.searchRoleName }
      })
      .then(response => {
        this.searchedRole = response.data;
      });
    },
    resetSearch() {
    this.searchRoleName = ''; // 清空搜索输入框
    this.searchedRole = null; // 清空搜索结果
  },
    // 处理头像选择
    handleAvatarChange(file) {
      this.avatarFile = file.raw; // 保存选中的文件
    },
    // 检查头像上传前的操作
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    createRole() {
      if (this.avatarFile) {
        const formData = new FormData();
        formData.append('file', this.avatarFile);
        console.log('Selected file:', this.avatarFile);

        // 先上传头像
        axios.post('http://localhost:8080/api/users/upload-avatar', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(response => {
          this.newRole.avatarPath = response.data; // 获取头像路径
          this.avatarFile = null; // 重置头像文件
          this.saveUser(); // 保存用户信息
        }).catch(() => {
          this.$message.error('头像上传失败');
        });
      } else {
        this.saveUser(); // 如果没有选择头像，直接保存用户信息
      }
    },
    saveUser() {
      axios.post('http://localhost:8080/api/users', this.newRole)
        .then(() => {
          this.newRole.roleName = '';
          this.newRole.description = '';
          this.newRole.avatarPath = ''; // 清空头像路径
          this.fetchRoles(); // 刷新用户列表
        });
    },
    editRole(role) {
      this.editMode = true;
      this.editRoleData = { ...role };
    },
    updateRole() {
      // 保留当前头像路径，如果未选择新头像，则保持不变
      const currentAvatarPath = this.editRoleData.avatarPath;

      if (this.avatarFile) {
        const formData = new FormData();
        formData.append('file', this.avatarFile);
        console.log('Selected file for update:', this.avatarFile);

        // 先上传头像
        axios.post('http://localhost:8080/api/users/upload-avatar', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(response => {
          this.editRoleData.avatarPath = response.data; // 获取新的头像路径
          this.avatarFile = null; // 重置头像文件
          return this.saveUpdatedRole();
        }).catch(() => {
          this.$message.error('头像上传失败');
        });
      } else {
        // 如果没有选择新头像，则保留原有的头像路径
        this.editRoleData.avatarPath = currentAvatarPath;
        this.saveUpdatedRole(); // 直接保存用户信息
      }
    },
    saveUpdatedRole() {
      axios.put(`http://localhost:8080/api/users/${this.editRoleData.id}`, this.editRoleData)
        .then(() => {
          this.editMode = false;
          this.editRoleData = { id: null, roleName: '', description: '', avatarPath: '' };
          this.fetchRoles();
        });
    },
    deleteRole(id) {
      axios.delete(`http://localhost:8080/api/users/${id}`)
      .then(() => {
        this.fetchRoles();
      });
    },
    batchDeleteRoles() {
      if (this.selectedRoles.length === 0) {
        alert('请先选择用户');
        return;
      }

      axios.delete('http://localhost:8080/api/users/batch-delete', {
        data: this.selectedRoles 
      })
      .then(() => {
        this.selectedRoles = [];
        this.fetchRoles();
      })
      .catch(error => {
        console.error("批量删除失败:", error);
      });
    },
    handleSelectionChange(selection) {
      this.selectedRoles = selection.map(role => role.id);
    },
    handlePageChange(page) {
      this.currentPage = page - 1;  // Vue 和 Element UI 的分页索引不一致，所以减1
      this.fetchRoles(); // 获取新的页数据
    },
  },
  created() {
    this.fetchRoles(); // 初始化时获取第一页数据
  }
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.search-row,
.add-role-row {
  margin-bottom: 20px;
}

.search-result,
.edit-role {
  margin-bottom: 20px;
}

.table-container {
  margin-top: 20px;
}

.el-table {
  width: 100%;
}

.batch-delete-button {
  margin-top: 20px;
}

.pagination {
  margin-top: 20px;
}
</style>