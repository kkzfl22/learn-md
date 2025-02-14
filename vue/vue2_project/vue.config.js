module.exports = {
  pages: {
    index: {
      //入口
      entry: 'src/main.js',
    },
  },
  //关闭语法检查
  lintOnSave: false,
  // //配制代理服务器
  // devServer: {
  //   proxy: {
  //     '/stu': {
  //       target: 'http://localhost:5000',
  //       //在请求服务器时去掉前缀
  //       pathRewrite:{'^/stu':''},
  //       ws: true,
  //       changeOrigin: true
  //     },
  //     '/cs': {
  //       target: 'http://localhost:5001',
  //       //在请求服务器时去掉前缀
  //       pathRewrite:{'^/cs':''},
  //       ws: true,
  //       changeOrigin: true
  //     }
  //   }
  // }

}