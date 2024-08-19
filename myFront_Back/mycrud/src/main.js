import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'; // 引入 Element Plus 样式
import router from './router'; // 引入路由配置

const app = createApp(App);

app.use(router); // 使用路由
app.use(ElementPlus); // 使用 Element Plus
app.mount('#app');
