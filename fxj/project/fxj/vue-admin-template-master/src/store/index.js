import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import getters from './getters'

Vue.use(Vuex)

/**
 * 前端保存信息分为两大模块，但是注意这里是保存在Vuex中的，只要页面刷新就没了
 * 1：app，通用信息
 * 2：user， 用户登录后的信息
 */
const store = new Vuex.Store({
  modules: {
    app,
    user
  },
  getters
})

export default store
