<!--
 * @Author: lyndonarcmond Junjie wang && wangjunjie00007@163.com 
 * @Date: 2024-08-16 23:54:06
 * @LastEditors: lyndonarcmond Junjie wang && wangjunjie00007@163.com
 * @LastEditTime: 2024-08-19 09:21:27
 * @FilePath: /htmlworks/vue/mycrud/src/App.vue
 * @Description: 
 * Copyright (c) 2024 by ${Junjie Wang}, All Rights Reserved. 
-->
<template>
  <div id="app">
    <!-- 导航链接 -->
    <nav>
      <router-link to="/identifier">Identifier Management</router-link>
      <router-link to="/role">Role Management</router-link>
    </nav>

    <!-- 动态加载组件 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

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
