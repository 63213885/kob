import { createStore } from 'vuex';
import ModuleUser from './user';
import ModulePk from './pk';

// createStore({
//     state,        // 根级别的状态
//     getters,      // 状态的计算属性
//     mutations,    // 同步修改状态的方法
//     actions,      // 异步操作
//     modules       // 模块化的状态管理
// })

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    pk: ModulePk
  }
})
