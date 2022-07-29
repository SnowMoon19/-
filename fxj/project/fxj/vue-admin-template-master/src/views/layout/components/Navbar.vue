<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb />
    <!--    <div class="user-avatar">-->
    <!--      &lt;!&ndash;      <el-avatar src="@/assets/avatar/logo.png"/>&ndash;&gt;-->
    <!--      <img src="../../../assets/avatar/logo.jpg" width="60px" height="50px">-->
    <!--    </div>-->
    <!--名称-->
    <div v-if="model.type==='分手'" class="user-model" style="color:#908a8e">{{ model.type }}{{ time }}</div>
    <div v-else-if="model.type==='热恋'" class="user-model" style="color:crimson">{{ model.type }}{{ time }}</div>
    <div v-else-if="model.type==='稳定'" class="user-model" style="color: #f9a3ff">{{ model.type }}{{ time }}</div>
    <div v-else-if="model.type==='试用'" class="user-model" style="color: #ffb300">{{ model.type }}{{ time }}</div>
    <div class="user-text">
      {{ name }}
      <img src="../../../assets/heart/aa.png" width="25px">
      {{ anotherName }}
    </div>
    <el-dialog :visible.sync="modelVisible" width="600px">
      {{ time }}
      <div slot="footer">
        <el-button @click="modelVisible=false;">取 消</el-button>
        <el-button type="primary" @click="modelVisible=false">确 定</el-button>
      </div>
    </el-dialog>
    <!--通知-->
    <div class="notification" >
      <el-dropdown trigger="click" >
        <el-badge :value="notificationNum">
          <el-button type="primary" icon="el-icon-bell" circle/>
        </el-badge>
        <el-dropdown-menu slot="dropdown" class="infinite-list" >
          <el-dropdown-item style="font-size: 15px;text-align: center; color: darkgreen">全部标为已读</el-dropdown-item>
          <el-card style="height: auto;max-height: 600px; overflow-y: scroll">
            <!--遍历通知列表-->
            <el-dropdown-item v-for="notification in notificationList" :key="notification.num">
              <!--每一个通知的卡片-->
              <el-row :gutter="12">
                <el-card style="width: 300px; margin: 0; padding: 0">
                  <el-row>
                    <!--显示时间-->
                    <el-col :span="16" style="color: #1482f0; font-size: 15px; text-align: start; margin-top: 0; padding: 0">{{ notification.time }}</el-col>
                    <!--删除按键，绑定对应通知的num-->
                    <el-col :span="8"><index v-model="notification.num" class="el-icon-delete" style="float: right; font-size: 20px" @click="deleteNotification(notification.num)"/></el-col>
                  </el-row>
                  <el-row>
                    <!--通知的具体内容-->
                    <div style="font-size: 20px; color: blue; text-align: start; background: #97a8be">{{ notification.context_cp }}</div>
                  </el-row>
                </el-card>
              </el-row>
            </el-dropdown-item>
          </el-card>
          <el-dropdown-item style="font-size: 15px;text-align: center; color: red">全部清除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!--菜单按键与下拉-->
    <div class="menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-button type="success" icon="el-icon-menu" circle/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <!--路由链接-->
          <router-link class="inlineBlock" to="/">
            <el-dropdown-item>
              主页
            </el-dropdown-item>
          </router-link>

          <router-link class="inlineBlock" to="/userInfo">
            <el-dropdown-item>
              个人信息
            </el-dropdown-item>
          </router-link>

          <!--点击触发退出登录的事件-->
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">登出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import {
  getAnotherId,
  getAnotherUsername,
  getId,
  getName,
  getUsername,
  setAnotherUsername,
  setName
} from '../../../utils/auth'
import { getNotificationList, updateNotification } from '../../../api/status/notification'
import { getBaseInfo } from '../../../api/userInfo/baseInfo'
import { isNull } from '../../../utils/validate'
import {formatGap, getNowFormatTime} from "../../../utils/dateTime";
import {getModel} from "../../../api/userInfo/model";

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      username: getUsername(),
      id: getId(),
      anotherId: getAnotherId(),
      anotherName: getAnotherUsername(),
      name: getName(),
      notificationList: [],
      notificationNum: '',
      modelVisible: false,
      model: {},
      time: getNowFormatTime()
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
      // 'avatar',
    ])
  },
  mounted() {
    this.showTime()
  },
  created() {
    this.getNotificationList(this.id)
    this.getName()
    this.getModel()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      // 执行store里面user的LogOut方法
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    // 获取基本信息得到自己的名称和对象的名称
    getName() {
      getBaseInfo(this.id).then(response => {
        if (!isNull(response.data.name)) {
          setName(response.data.name)
          this.name = response.data.name
        }
      }).catch(error => {
        console.log(error)
      })
      if (!isNull(this.anotherId)) {
        getBaseInfo(this.anotherId).then(response => {
          if (!isNull(response.data.name)) {
            setAnotherUsername(response.data.name)
            this.anotherName = response.data.name
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    /**
     * 获取当前模式
     */
    getModel() {
      getModel(this.id).then(response => {
        this.model = response.data
      })
    },
    // 显示当前模式的时间
    showTime() {
      this.time = formatGap(this.model.startTime, getNowFormatTime())
      setTimeout(() => {
        this.showTime()
      }, 1000)
    },
    /**
     * 获取通知列表
     */
    getNotificationList() {
      const id = getId()
      getNotificationList(id).then(response => {
        // console.log(data)
        this.notificationList = response.data
        this.getNotificationNum()
      })
    },
    /**
     * 未查看的通知数量
     */
    getNotificationNum() {
      let ans = 0
      for (let i = 0; i < this.notificationList.length; i += 1) {
        if (this.notificationList[i].isClicked === 0) {
          ans += 1
        }
        // 将内容做一个换行文本的副本，方便显示
        this.notificationList[i].context_cp = this.getNewline(this.notificationList[i].context)
      }
      this.notificationNum = ans
    },
    // 显示内容换行
    getNewline(val) {
      const str = String(val)
      let bytesCount = 0
      let s = ''
      for (let i = 0, n = str.length; i < n; i++) {
        const c = str.charCodeAt(i)
        // 统计字符串的字符长度
        if ((c >= 0x0001 && c <= 0x007e) || (c >= 0xff60 && c <= 0xff9f)) {
          bytesCount += 1
        } else {
          bytesCount += 2
        }
        // 换行
        s += str.charAt(i)
        if (bytesCount >= 20) {
          s = s + '\n'
          // 重置
          bytesCount = 0
        }
      }
      return s
    },
    /**
     * 删除通知
     */
    deleteNotification(num) {
      const index = this.notificationList.findIndex(function(x) {
        return x.num === num
      })
      this.notificationList[index].isDeleted = 1
      updateNotification(this.notificationList[index]).then(response => {
        // 删除成功要把list和num减去
        this.notificationList.splice(index, 1)
        this.notificationNum -= 1
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

::-webkit-scrollbar {display:none} // 隐藏滚动条
.navbar {
  height: 60px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
  // 头像
  .user-avatar {
    height: 50px;
    display: inline-block;
    position: absolute;
    float: left;
    right: 1%;
    margin-right: 180px;
    margin-top: 8px;
  }
  // 相处模式时间
  .user-model {
    height: 50px;
    display: inline-block;
    position: absolute;
    width: auto;
    float: left;
    right: 10%;
    margin-right: 32%;
    font-size: 25px;
    margin-top: 8px;
    font-weight: bold;
  }
  // 用户名
  .user-text {
    height: 50px;
    display: inline-block;
    position: absolute;
    width: auto;
    float: left;
    right: 1%;
    margin-right: 150px;
    font-size: 30px;
    margin-top: 8px;
    cursor: pointer;
  }
  // 通知
  .notification {
    height: 50px;
    display: inline-block;
    float: left;
    position: absolute;
    right: 1%;
    margin-right: 90px;
    margin-top: 8px;
  }
  // 菜单
  .menu {
    float: left;
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 1%;
    margin-top: 8px;
  }
}
</style>

