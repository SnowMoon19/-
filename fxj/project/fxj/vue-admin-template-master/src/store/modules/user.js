import { login } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import {
  removeAnotherId, removeAnotherUsername,
  removeId,
  removeName,
  removeUsername,
  setAnotherId, setAnotherUsername,
  setId,
  setName,
  setUsername
} from '../../utils/auth'
import { getBaseInfo } from '../../api/userInfo/baseInfo';
import { isNull } from '../../utils/validate';

const user = {
  state: {
    token: getToken(),
    username: '',
    // avatar: '',
    id: ''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_UNAME: (state, username) => {
      state.username = username
    },
    // SET_AVATAR: (state, avatar) => {
    //   state.avatar = avatar
    // },
    SET_ID: (state, id) => {
      state.id = id
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      /**
       * Promise处理异步登录
       * 登录信息保存在token中，存在Cookie里，通过这样的方式判别账号是否已登录
       */
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          // 根据是否返回id验证账号密码是否正确
          if (!isNull(data.anotherId)) {
            setToken(data.id)
            setUsername(data.username)
            setId(data.id)
            setAnotherId(data.anotherId)
            // todo 这里服务器没做token，暂时将id作为token值
            commit('SET_TOKEN', data.id)
            commit('SET_ID', data.id)
            commit('SET_UNAME', data.username)
            resolve()
          }
        }).catch(error => {
          // console.log(error)
          reject(error)
        })
      })
    },

    // // 获取用户信息
    // GetInfo({ commit, state }) {
    //   if(state.id !== '') {
    //     commit('SET_ID', data.id)
    //   }
    //   return new Promise((resolve, reject) => {
    //     getInfo(state.token).then(response => {
    //       const data = response.data
    //       if (data.id && data.id.length > 0) { // 验证是否返回id
    //         commit('SET_ID', data.id)
    //       } else {
    //         reject('getInfo: id未获取到')
    //       }
    //       commit('SET_UNAME', data.username)
    //       // commit('SET_AVATAR', data.avatar)
    //       resolve(response)
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        console.log(123)
        commit('SET_TOKEN', '')
        commit('SET_ID', '')
        commit('SET_UNAME', '')
        removeToken()
        removeUsername()
        removeId()
        removeName()
        removeAnotherId()
        removeAnotherUsername()
        resolve()
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_ID', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
