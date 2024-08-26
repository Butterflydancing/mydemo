<template>
  <div id="app">
    <UserModule />
  </div>
</template>

<script>
import UserModule from './components/UserModule.vue'; // 引入 UserModule 组件
export default {
  name: 'App',
  components: {
    UserModule
  }
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
