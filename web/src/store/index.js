import { createStore } from 'vuex';
import ModuleUser from './user';
import ModulePk from './pk';
import ModuleRecord from './record';

// Vuex 的核心概念是 store（仓库），它包含了应用的状态（state）、变更状态的方法（mutations）、处理异步操作的方法（actions）以及模块化的状态管理（modules）。
// 通过创建 store，可以集中管理应用的所有状态，方便调试和维护。

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
    pk: ModulePk,
    record: ModuleRecord
  }
})
