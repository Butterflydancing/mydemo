<template>
  <div>
    <h1>Identifier CRUD</h1>
    <div>
      <input v-model="newIdentifier.name" placeholder="Enter name">
      <button @click="createIdentifier">Add</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="identifier in identifiers" :key="identifier.id">
          <td>{{ identifier.id }}</td>
          <td v-if="!editMode || editIdentifierData.id !== identifier.id">{{ identifier.name }}</td>
          <td v-else>
            <input v-model="editIdentifierData.name" placeholder="Enter new name">
          </td>
          <td>
            <button v-if="editMode && editIdentifierData.id === identifier.id" @click="updateIdentifier">Save</button>
            <button v-else @click="editIdentifier(identifier)">Edit</button>
            <button @click="deleteIdentifier(identifier.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 分页按钮 -->
    <div v-if="totalPages > 1" class="pagination">
      <button @click="fetchIdentifiers(currentPage - 1)" :disabled="currentPage === 0">Previous</button>
      <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="fetchIdentifiers(currentPage + 1)" :disabled="currentPage === totalPages - 1">Next</button>
    </div>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      identifiers: [], // 用于存储从后端获取的 Identifier 数据
      newIdentifier: { name: '' }, // 新的 Identifier 数据
      editMode: false, // 编辑模式开关
      editIdentifierData: { id: null, name: '' }, // 用于编辑的 Identifier 数据
      currentPage: 0, // 当前页码
      totalPages: 1 // 总页数
    };
  },
  
  methods: {
    // 获取所有 Identifier 数据
    fetchIdentifiers(page = 0, size = 4) {
      axios.get(`http://localhost:8080/api/identifiers/page?page=${page}&size=${size}`)
        .then(response => {
          this.identifiers = response.data.content; // 使用响应中的分页数据
          this.currentPage = page; // 更新当前页
          this.totalPages = response.data.totalPages; // 更新总页数
        })
        .catch(error => {
          console.error('Error fetching identifiers:', error);
        });
    },
    // 创建新的 Identifier
    createIdentifier() {
      axios.post('http://localhost:8080/api/identifiers', this.newIdentifier)
        .then(() => {
          this.newIdentifier.name = ''; // 清空输入框
          this.fetchIdentifiers(); // 重新获取数据以更新列表
        });
    },
    // 进入编辑模式
    editIdentifier(identifier) {
      this.editMode = true;
      this.editIdentifierData = { ...identifier }; // 复制要编辑的 Identifier 数据
    },
    // 更新 Identifier
    updateIdentifier() {
      axios.put(`http://localhost:8080/api/identifiers/${this.editIdentifierData.id}`, this.editIdentifierData)
        .then(() => {
          this.editMode = false; // 关闭编辑模式
          this.editIdentifierData = { id: null, name: '' }; // 重置编辑数据
          this.fetchIdentifiers(); // 重新获取数据以更新列表
        });
    },
    // 删除 Identifier
    deleteIdentifier(id) {
      axios.delete(`http://localhost:8080/api/identifiers/${id}`)
        .then(() => {
          this.fetchIdentifiers(); // 重新获取数据以更新列表
        });
    }
  },
  // 在组件创建时获取所有 Identifier 数据
  created() {
    this.fetchIdentifiers();
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

input {
  margin-bottom: 10px;
}


.pagination {
  margin-top: 30px;
  text-align: center;
}

.pagination button {
  margin: 0 5px;
}

</style>
