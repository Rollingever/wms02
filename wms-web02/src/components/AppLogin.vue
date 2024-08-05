<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">用户登录</h1>
        <el-form :model="loginForm" label-width="100px" :rules="rules" ref="loginForm">
          <el-form-item label="账号" prop="no">
            <el-input style="width: 200px" type="text" v-model="loginForm.no" autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input style="width: 200px" type="password" v-model="loginForm.password" show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="confirm" :disabled="confirm_disabled">确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppLogin",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: ''
      },
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          console.log("Form validated successfully, sending login request...");
          // 去后台验证用户名密码
          this.$axios.post(this.$httpUrl + '/user/login', this.loginForm)
              .then(res => {
                console.log("Login response:", res);
                if (res.data.code === 200) {
                  // 从 res.data.data 中提取用户数据和菜单数据
                  const data = res.data.data;
                  const user = data.user;
                  const menu = data.menu;

                  // 存储用户数据
                  sessionStorage.setItem("CurUser", JSON.stringify(user));
                  // 存储菜单数据
                  console.log("Before committing setMenu");
                  this.$store.commit("setMenu", menu);
                  console.log("After committing setMenu");
                  this.$router.push('/IndexPage').then(() => {
                    console.log("Navigation to /IndexPage/AppHome successful");
                  }).catch((err) => {
                    console.error("Navigation error: ", err);
                  });
                } else {
                  this.confirm_disabled = false;
                  alert('校验失败，用户名或密码错误！');
                }
              })
              .catch(error => {
                console.error('请求出错', error);
                this.confirm_disabled = false;
                alert('请求出错，请稍后重试');
              });
        } else {
          this.confirm_disabled = false;
          console.log('校验失败');
        }
      });
    }
  }
}
</script>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}

.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: #fff;
  border-radius: 5%;
}

.login-title {
  margin: 20px 0;
  text-align: center;
}

.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>
