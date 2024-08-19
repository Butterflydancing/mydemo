// /$$
//  $ @Author: lyndonarcmond Junjie wang && wangjunjie00007@163.com 
//  $ @Date: 2024-08-16 23:54:06
//  $ @LastEditors: lyndonarcmond Junjie wang && wangjunjie00007@163.com
//  $ @LastEditTime: 2024-08-17 11:37:08
//  $ @FilePath: /htmlworks/vue/mycrud/vue.config.js
//  $ @Description: 
//  $ @Copyright (c) 2024 by ${Junjie Wang}, All Rights Reserved. 
//  $/
const { defineConfig } = require('@vue/cli-service')
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端服务的地址
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  },
};
