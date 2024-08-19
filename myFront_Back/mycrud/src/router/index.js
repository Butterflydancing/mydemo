import { createRouter, createWebHistory } from 'vue-router';
import AppLayout from '../components/AppLayout.vue'; // 引入布局组件
import UserModule from '../components/UserModule.vue'; // 用户模块组件


const routes = [
  {
    path: '/',
    component: AppLayout, // 默认布局
    children: [
      {
        path: 'role',
        component: UserModule, // 用户模块组件
      },
      // 其他子路由
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
