<template>
  <div class="app-container">
    <el-form ref="form" :model="baseInfo" label-width="120px">
      <el-form-item label="账号">
        {{ username }}
      </el-form-item>
      <el-form-item label="用户名">
        <el-col :span="8">
          <el-input v-model="baseInfo.name"/>
        </el-col>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="baseInfo.gender" placeholder="please select your zone">
          <el-option label="男" value="男"/>
          <el-option label="女" value="女"/>
        </el-select>
      </el-form-item>
      <el-form-item label="生日">
        <el-col :span="8">
          <el-date-picker v-model="baseInfo.birth" type="date" placeholder="Pick a date" style="width: 100%;"/>
        </el-col>
      </el-form-item>
      <el-form-item label="手机">
        <el-col :span="8">
          <el-input v-model="baseInfo.phone" type="number"/>
        </el-col>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-col :span="8">
          <el-input v-model="baseInfo.email" type="email"/>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="confirm_btn" @click="changeBaseInfoConfirm">确 认</el-button>
        <el-button @click="returnToBaseInfo">取 消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { changeBaseInfo, getBaseInfo } from '../../api/userInfo/baseInfo'
import { getId, getUsername, setName } from '../../utils/auth'
import { calAge, calSign } from '../../utils/baseInfo'
import {isNull} from "../../utils/validate";

export default {
  data() {
    return {
      username: getUsername(),
      id: getId(),
      baseInfo: {
        name: '',
        gender: '',
        birth: '',
        phone: 0,
        email: ''
      }
    }
  },
  created() {
    this.getBaseInfo()
  },
  methods: {
    getBaseInfo() {
      /**
       * 获取基础信息
       */
      getBaseInfo(this.id).then(response => {
        if (!isNull(response.data.name)) this.baseInfo.name = response.data.name
        if (!isNull(response.data.gender)) this.baseInfo.gender = response.data.gender
        if (!isNull(response.data.birth)) this.baseInfo.birth = response.data.birth
        if (!isNull(response.data.phone)) this.baseInfo.phone = response.data.phone
        if (!isNull(response.data.email)) this.baseInfo.email = response.data.email
      })
    },
    changeBaseInfoConfirm() {
      this.baseInfo.age = calAge(this.baseInfo.birth) // 直接根据生日计算年龄与星座
      this.baseInfo.sign = calSign(this.baseInfo.birth)
      this.baseInfo.id = this.id
      console.log(this.baseInfo.birth)
      changeBaseInfo(this.baseInfo).then(response => {
        setName(this.baseInfo.name) // Cookie中的用户名信息也要更改
        this.returnToBaseInfo()
      }).catch(error => {
        console.log(error)
      })
    },
    // 回到基本信息页
    returnToBaseInfo() {
      this.$router.push({ path: '/userInfo' })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.el-form {
  width: 50%;
  margin: auto;
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
  font-family: 微软雅黑;
  color:black;
  &:hover {
   background-color: #ecf5ff !important; // 子模块的hover背景色
 }
}
.confirm_btn {
  margin-left: 200px;
}
</style>

