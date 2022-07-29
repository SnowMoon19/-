<template>
  <div class="dashboard-container">
    <div class="dashboard-text" style="font-weight: bold">V1.0.0</div>
    <div class="dashboard-text">username:{{ username }}</div>
    <div class="dashboard-text"> 欢迎访问本网站，本网站仅供个人学习与交流，服务器响应可能比较慢。<br>为了防止服务器崩溃，目前只开放两个测试账号tes1、test2,密码均为123456<br>许多模块仍在建设中，其中任务板可以拖拽，mode仍在开发中<br>网站仍在建设中，问题还有跟多，如有bug和任何建议，欢迎在下方留言，谢谢。</div>
    <div class="question" style="width: 95%; height: 300px; margin-top: 30px;margin-bottom: 20px">
      <el-input v-model="question" type="textarea" style="font-size: 30px; line-height: 46px;"/>
      <el-button type="primary" @click="submitQuestion">提交</el-button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getId, getUsername } from '../../utils/auth'
import { submitQuestion } from '../../api/userInfo/baseInfo'

export default {
  name: 'Dashboard',
  // 直接通过获取Cookie中保存的用户名和id作为data
  // computed还有待学习
  data() {
    return {
      username: getUsername(),
      id: getId(),
      question: ''
    }
  },
  computed: {
    // 在别的地方已经保存了对应的映射
    ...mapGetters([
    ])
  },
  methods: {
    submitQuestion() {
      if (this.question !== '') {
        submitQuestion(this.question, this.username).then(function(index) {
          alert('提交成功')
        })
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
  &-question {
    font-size: 30px;
    height: 200px;
    width: 1000px;
  }
}
::-webkit-scrollbar {display:none} // 隐藏滚动条
</style>
