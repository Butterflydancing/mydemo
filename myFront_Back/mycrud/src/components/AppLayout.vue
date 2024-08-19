<template>
  <div>
    <!-- 导航链接 -->
    <nav v-if="showNav">
      <router-link to="/role">用户模块</router-link>
      <!-- 其他导航链接 -->
    </nav>

    <!-- 动态加载组件 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'AppLayout',
  computed: {
    showNav() {
      // 判断当前路由是否需要显示导航栏
      return this.$route.path !== '/role'; // 示例：如果当前路由是/role，则不显示导航栏
    }
  }
};
</script>

<style>
nav {
  margin-bottom: 20px;
}

nav a {
  margin: 0 15px;
  text-decoration: none;
  color: #42b983;
}

nav a.router-link-exact-active {
  font-weight: bold;
}
</style>
