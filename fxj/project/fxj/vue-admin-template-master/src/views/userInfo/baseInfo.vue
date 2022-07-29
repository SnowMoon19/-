<template>
  <div class="app-container">
    <div class="info" >
      <el-col class="self-info">
        <div class="tittle_self"> Self </div>
        <el-form ref="form" :model="baseInfo" label-width="120px">
          <el-form-item label="账号">
            {{ baseInfo.username }}
          </el-form-item>
          <el-form-item label="用户名">
            {{ baseInfo.name }}
          </el-form-item>
          <el-form-item label="性别">
            {{ baseInfo.gender }}
          </el-form-item>
          <el-form-item label="生日">
            {{ dealBirth(baseInfo.birth) }}
          </el-form-item>
          <el-form-item label="年龄">
            {{ baseInfo.age }}
          </el-form-item>
          <el-form-item label="手机">
            {{ baseInfo.phone }}
          </el-form-item>
          <el-form-item label="邮箱">
            {{ baseInfo.email }}
          </el-form-item>
          <el-form-item label="星座">
            {{ baseInfo.sign }}
          </el-form-item>
          <el-form-item label="注册日期">
            {{ dealBirth(baseInfo.registerDate) }}
          </el-form-item>
          <el-form-item>
            <router-link class="inlineBlock" to="/userInfo/changeBaseInfo">
              <el-button type="primary" class="update_btn">修 改</el-button>
            </router-link>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col class="another-info">
        <div class="tittle_another"> Another </div>
        <el-form ref="form" :model="anotherInfo" label-width="120px">
          <el-form-item label="账号">
            {{ anotherInfo.username }}
          </el-form-item>
          <el-form-item label="用户名">
            {{ anotherInfo.name }}
          </el-form-item>
          <el-form-item label="性别">
            {{ anotherInfo.gender }}
          </el-form-item>
          <el-form-item label="生日">
            {{ dealBirth(anotherInfo.birth) }}
          </el-form-item>
          <el-form-item label="年龄">
            {{ anotherInfo.age }}
          </el-form-item>
          <el-form-item label="手机">
            {{ anotherInfo.phone }}
          </el-form-item>
          <el-form-item label="邮箱">
            {{ anotherInfo.email }}
          </el-form-item>
          <el-form-item label="星座">
            {{ anotherInfo.sign }}
          </el-form-item>
        </el-form>
      </el-col>
    </div>
  </div>
</template>

<script>
import { getBaseInfo } from '../../api/userInfo/baseInfo'
import { getAnotherId, getId } from '../../utils/auth'
import { formatDay } from '../../utils/dateTime'
import { isNull } from '../../utils/validate'

export default {
  data() {
    return {
      baseInfo: {
        username: '',
        name: '',
        gender: '',
        birth: '',
        age: '',
        sign: '',
        phone: 0,
        registerDate: '',
        email: ''
      },
      anotherInfo: {
        username: '',
        name: '',
        gender: '',
        birth: '',
        age: '',
        sign: '',
        phone: 0,
        registerDate: '',
        email: ''
      },
      id: getId(),
      anotherId: getAnotherId()
    }
  },
  // computed: {
  //   ...mapGetters([
  //     'username',
  //     'id'
  //   ])
  // },
  created() {
    this.getBaseInfo()
    this.getAnotherInfo()
  },
  methods: {
    getBaseInfo() {
      /**
       * 获取基础信息
       */
      getBaseInfo(this.id).then(response => {
        if (!isNull(response.data.username)) this.baseInfo.username = response.data.username
        if (!isNull(response.data.name)) this.baseInfo.name = response.data.name
        if (!isNull(response.data.gender)) this.baseInfo.gender = response.data.gender
        if (!isNull(response.data.birth)) this.baseInfo.birth = response.data.birth
        if (!isNull(response.data.age)) this.baseInfo.age = response.data.age
        if (!isNull(response.data.phone)) this.baseInfo.phone = response.data.phone
        if (!isNull(response.data.email)) this.baseInfo.email = response.data.email
        if (!isNull(response.data.sign)) this.baseInfo.sign = response.data.sign
        if (!isNull(response.data.registerDate)) this.baseInfo.registerDate = response.data.registerDate
      }).catch(error => {
        console.log(error)
      })
    },
    getAnotherInfo() {
      getBaseInfo(this.anotherId).then(response => {
        if (!isNull(response.data.username)) this.anotherInfo.username = response.data.username
        if (!isNull(response.data.name)) this.anotherInfo.name = response.data.name
        if (!isNull(response.data.gender)) this.anotherInfo.gender = response.data.gender
        if (!isNull(response.data.birth)) this.anotherInfo.birth = response.data.birth
        if (!isNull(response.data.age)) this.anotherInfo.age = response.data.age
        if (!isNull(response.data.phone)) this.anotherInfo.phone = response.data.phone
        if (!isNull(response.data.email)) this.anotherInfo.email = response.data.email
        if (!isNull(response.data.sign)) this.anotherInfo.sign = response.data.sign
      }).catch(error => {
        console.log(error)
      })
    },
    dealBirth(birth) {
      return formatDay(birth)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.info {
  width:1200px;
  margin:80px auto;
}

.self-info{
  background: #52aeff;
  box-shadow:
    0px 1px 0px #f4cedd,
    0px 2px 0px #e6bbd9,
    0px 3px 0px #ffb3e0,
    0px 6px 0px #ff8edd,
    0px 8px 10px #ff6bde;
  border-radius: 30px;
  width: 45%;
  height: 700px;
}

.another-info{
  background: rgba(230,187,217,0.95);
  box-shadow:
    0px 1px 0px #cafff7,
    0px 2px 0px #a2ffff,
    0px 3px 0px #55e9ff,
    0px 6px 0px #28daff,
    0px 8px 10px #009dff;
  margin-left: 100px;
  border-radius: 30px;
  width: 45%;
  height: 700px;
}
.tittle_self {
  font-size: 50px;
  margin-left: 20px;
  margin-top: 10px;
  color:white;
  font-family: "Arial";
  text-shadow:
    0px 1px 0px #f4cedd,
    0px 2px 0px #e6bbd9,
    0px 3px 0px #ffb3e0,
    0px 4px 0px #ff8edd,
    0px 5px 6px #ff6bde;
}
.tittle_another {
  font-size: 50px;
  margin-left: 20px;
  margin-top: 10px;
  color:white;
  font-family: "Arial";
  text-shadow:
    0px 1px 0px #cafff7,
    0px 2px 0px #a2ffff,
    0px 3px 0px #55e9ff,
    0px 4px 0px #28daff,
    0px 5px 6px #009dff
}
.el-form {
  width: 80%;
  margin: auto;
  height: 82%;
  border-radius: 30px;
  box-shadow:
    0px 1px 0px #c0c0c0,
    0px 2px 0px #b0b0b0,
    0px 3px 0px #a0a0a0,
    0px 4px 0px #909090,
    0px 5px 10px rgba(0, 0, 0, 0.6);
  background: white;
  padding-top: 10px;
  padding-bottom: 5px;
}
.el-form-item {
  /*background: #52aeff;*/
  margin: 15px;
  border-radius: 10px;
  font-weight: bold;
  font-family: 微软雅黑;
  color:black;
  text-shadow:
    0px 1px 0px #c0c0c0,
    0px 2px 0px #b0b0b0;
  &:hover {
   background-color: #ecf5ff !important; // 子模块的hover背景色
 }
}
.update_btn {
  margin-left: 40px;
  box-shadow:
    0px 1px 0px #c0c0c0,
    0px 2px 0px #b0b0b0,
    0px 3px 0px #a0a0a0,
    0px 4px 8px #909090;
}
</style>

