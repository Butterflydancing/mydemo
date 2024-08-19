// /$$
//  $ @Author: lyndonarcmond Junjie wang && wangjunjie00007@163.com 
//  $ @Date: 2024-08-19 09:12:13
//  $ @LastEditors: lyndonarcmond Junjie wang && wangjunjie00007@163.com
//  $ @LastEditTime: 2024-08-19 09:17:20
//  $ @FilePath: /htmlworks/vue/mycrud/src/router/index.js
//  $ @Description: 
//  $ @Copyright (c) 2024 by ${Junjie Wang}, All Rights Reserved. 
//  $/
import { createRouter, createWebHistory } from 'vue-router'
// import IdentifierComponent from '../components/IdentifierCrud.vue'
// import RoleComponent from '../components/RoleCrud.vue'

import RoleCrud from '../components/RoleCrud.vue'

const routes = [

  { path: '/role', component: RoleCrud }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
