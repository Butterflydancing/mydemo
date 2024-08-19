<template>
  <div>
    <h1>Role Management</h1>
    <div>
      <input v-model="searchRoleName" placeholder="Search role name">
      <button @click="searchRole">Search</button>
    </div>
    <div v-if="searchedRole">
      <h3>Search Result:</h3>
      <p>{{ searchedRole.roleName }} - {{ searchedRole.description }}</p>
    </div>
    <div>
      <input v-model="newRole.roleName" placeholder="Enter role name">
      <input v-model="newRole.description" placeholder="Enter description">
      <button @click="createRole">Add Role</button>
    </div>
    <table>
      <thead>
        <tr>
          <th><input type="checkbox" @click="selectAllRoles($event)" /></th>
          <th>ID</th>
          <th>Role Name</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="role in roles.content" :key="role.id">
          <td><input type="checkbox" v-model="selectedRoles" :value="role.id"></td>
          <td>{{ role.id }}</td>
          <td v-if="!editMode || editRoleData.id !== role.id">{{ role.roleName }}</td>
          <td v-if="!editMode || editRoleData.id !== role.id">{{ role.description }}</td>
          <td v-else>
            <input v-model="editRoleData.roleName" placeholder="Enter new role name">
            <input v-model="editRoleData.description" placeholder="Enter new description">
          </td>
          <td>
            <button v-if="editMode && editRoleData.id === role.id" @click="updateRole">Save</button>
            <button v-else @click="editRole(role)">Edit</button>
            <button @click="deleteRole(role.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
    <button @click="batchDeleteRoles">Delete Selected Roles</button>
    <div v-if="editMode">
      <h3>Edit Role</h3>
      <input v-model="editRoleData.roleName" placeholder="Enter new role name">
      <input v-model="editRoleData.description" placeholder="Enter new description">
      <button @click="updateRole">Save</button>
    </div>
    <!-- Pagination Controls -->
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 0">Previous</button>
      <span>Page {{ currentPage + 1 }} of {{ roles.totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= roles.totalPages - 1">Next</button>
    </div>
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
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: center;
}

td {
  text-align: center;
}

button {
  margin-left: 10px;
}

.pagination {
  margin-top: 20px;
}

.pagination button {
  margin: 0 5px;
}
</style>
