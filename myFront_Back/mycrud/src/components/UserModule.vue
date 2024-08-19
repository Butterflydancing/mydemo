<template>
  <div class="container">
    <h1>用户管理</h1>

    <!-- 搜索区域 -->
    <el-row class="search-row" gutter="20">
      <el-col :span="8">
        <el-input v-model="searchRoleName" placeholder="搜索角色名称" clearable></el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="searchRole">搜索</el-button>
      </el-col>
    </el-row>

    <!-- 搜索结果 -->
    <div v-if="searchedRole" class="search-result">
      <h3>搜索结果:</h3>
      <el-card>
        <p>{{ searchedRole.roleName }} - {{ searchedRole.description }}</p>
      </el-card>
    </div>

    <!-- 添加角色 -->
    <el-row class="add-role-row" gutter="20">
      <el-col :span="8">
        <el-input v-model="newRole.roleName" placeholder="输入角色名称" clearable></el-input>
      </el-col>
      <el-col :span="8">
        <el-input v-model="newRole.description" placeholder="输入描述" clearable></el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="success" @click="createRole">添加角色</el-button>
      </el-col>
    </el-row>

    <!-- 角色表格 -->
    <div class="table-container">
      <el-table :data="roles.content" stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column label="角色名称">
          <template v-slot="scope">
            <el-input v-if="editMode && editRoleData.id === scope.row.id" v-model="editRoleData.roleName" placeholder="输入新角色名称"></el-input>
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
    <el-button type="danger" @click="batchDeleteRoles" class="batch-delete-button">删除选中角色</el-button>

    <!-- 编辑角色 -->
    <div v-if="editMode" class="edit-role">
      <h3>编辑角色</h3>
      <el-input v-model="editRoleData.roleName" placeholder="输入新角色名称" clearable></el-input>
      <el-input v-model="editRoleData.description" placeholder="输入新描述" clearable></el-input>
      <el-button type="primary" @click="updateRole">保存</el-button>
    </div>

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
      newRole: { roleName: '', description: '' },
      editMode: false,
      editRoleData: { id: null, roleName: '', description: '' },
      searchRoleName: '',
      searchedRole: null,
      currentPage: 0, // 当前页码
      pageSize: 4 // 每页显示的条目数
    };
  },
  methods: {
    fetchRoles() {
      axios.get('http://localhost:8080/api/roles/page', {
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
      axios.get('http://localhost:8080/api/roles/search', {
        params: { roleName: this.searchRoleName }
      })
      .then(response => {
        this.searchedRole = response.data;
      });
    },
    createRole() {
      axios.post('http://localhost:8080/api/roles', this.newRole)
      .then(() => {
        this.newRole.roleName = '';
        this.newRole.description = '';
        this.fetchRoles();
      });
    },
    editRole(role) {
      this.editMode = true;
      this.editRoleData = { ...role };
    },
    updateRole() {
      axios.put(`http://localhost:8080/api/roles/${this.editRoleData.id}`, this.editRoleData)
      .then(() => {
        this.editMode = false;
        this.editRoleData = { id: null, roleName: '', description: '' };
        this.fetchRoles();
      });
    },
    deleteRole(id) {
      axios.delete(`http://localhost:8080/api/roles/${id}`)
      .then(() => {
        this.fetchRoles();
      });
    },
    batchDeleteRoles() {
      axios.delete('http://localhost:8080/api/roles/batch-delete', {
        data: this.selectedRoles
      })
      .then(() => {
        this.selectedRoles = [];
        this.fetchRoles();
      });
    },
    nextPage() {
      if (this.currentPage < this.roles.totalPages - 1) {
        this.currentPage++;
        this.fetchRoles();
      }
    },
    previousPage() {
      if (this.currentPage > 0) {
        this.currentPage--;
        this.fetchRoles();
      }
    },
    selectAllRoles(event) {
      this.selectedRoles = event.target.checked ? this.roles.content.map(role => role.id) : [];
    }
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
