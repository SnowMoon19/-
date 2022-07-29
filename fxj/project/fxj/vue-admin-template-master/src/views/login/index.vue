<template>
  <div class="login-container">
    <div class="logo"> <!--图标-->
      <img src="../../assets/avatar/logo.jpg" alt="logo" width="250px">
    </div>
    <el-card class="loginCard">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
        <h3 class="title">Login</h3>
        <el-form-item prop="username">
          <span class="svg-container">
            <svg-icon icon-class="user" />
          </span>
          <el-input v-model="loginForm.username" class="input_box" name="username" type="text" auto-complete="on" placeholder="username" />
        </el-form-item>
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :type="pwdType"
            v-model="loginForm.password"
            name="password"
            auto-complete="on"
            placeholder="password"
            class="input_box"
            @keyup.enter.native="handleLogin" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon icon-class="eye" />
          </span>
        </el-form-item>
        <el-form-item style="margin-top: 20px">
          <el-button :loading="loading" type="primary" style="width:100%; opacity: 70%" @click.native.prevent="handleLogin">
            Sign in
          </el-button>
        </el-form-item>
        <div class="tips">
          <span v-if="errorMsg===true" style="margin-right:20px; color: #f16c92; font-size: 18px">Wrong username or password</span>
        </div>
      </el-form>
    </el-card>
    <div style="font-size:18px; color:#534f50; margin-left:44%">
      公开账号:test1 密码:123456
    </div>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    // const validateUsername = (rule, value, callback) => {
    //   if (!isvalidUsername(value)) {
    //     callback(new Error('请输入正确的用户名'))
    //   } else {
    //     callback()
    //   }
    // }
    // const validatePass = (rule, value, callback) => {
    //   if (value.length < 5) {
    //     callback(new Error('密码不能小于5位'))
    //   } else {
    //     callback()
    //   }
    // }
    // return {
    //   loginForm: {
    //     username: 'admin',
    //     password: 'admin'
    //   },
    //   loginRules: {
    //     username: [{ required: true, trigger: 'blur', validator: validateUsername }],
    //     password: [{ required: true, trigger: 'blur', validator: validatePass }]
    //   },
    //   loading: false,
    //   pwdType: 'password',
    //   redirect: undefined
    // }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur' }],
        password: [{ required: true, trigger: 'blur' }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined,
      errorMsg: false
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true // 正在等待服务器反馈
          this.$store.dispatch('Login', this.loginForm).then(() => { // 调用store里面的登录方法
            this.errorMsg = false
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            // 账号或密码错误
            this.loading = false
            this.errorMsg = true
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.login-container {
  height: 100%;
  background-image: url('../../assets/login/bg-02.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center 0;
  background-attachment: fixed;
  -webkit-background-size: cover;
  -o-background-size: cover;
  -moz-background-size: cover;
  -ms-background-size: cover;
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    //input {
    //  background: transparent;
    //  border: 0px;
    //  -webkit-appearance: none;
    //  border-radius: 0px;
    //  padding: 12px 5px 12px 15px;
    //  //color: red;
    //  height: 47px;
    //  &:-webkit-autofill {
    //    //-webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
    //    //-webkit-text-fill-color: #fff !important;
    //  }
    //}
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    //color: #454545;
  }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .loginCard {
    height: 600px;
    width: 600px;
    margin: 30px auto;
    background: -webkit-linear-gradient(90deg, #a0e6e3, #f4cedd); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(90deg, #a0e6e3, #f4cedd); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(90deg, #a0e6e3, #f4cedd); /* Firefox 3.6 - 15 */
    background: linear-gradient(90deg, #a0e6e3, #f4cedd); /* 标准的语法（必须放在最后） */
    border-radius: 4%;
    //border: 50px #1482f0;
    border-inline-width: 4px;
    border-top-width: 4px;
    border-bottom-width: 4px;
    box-shadow: 0 2px 8px 0 rgba(255, 255, 255, 10);
    transition: all .3s ease-in;
    .login-form {
      position: absolute;
      left: 0;
      right: 0;
      width: 520px;
      max-width: 100%;
      padding: 35px 35px 15px 45px;
      margin: 50px auto;
      display: inline-block;
    }
    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }
    .svg-container {
      padding: 6px 5px 6px 15px;
      color: #454545;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }
    .title {
      font-size: 36px;
      font-weight: 400;
      color: white;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: #454545;
      cursor: pointer;
      user-select: none;
    }
  }
}
.input_box {
  padding-left: 10px;
}

.logo {
  margin: 30px auto;
  width: 250px;
}
</style>
