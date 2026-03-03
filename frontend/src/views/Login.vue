<template>
  <div class="login-container">
    <div class="login-box">
      <h2>选课管理系统</h2>
      <div class="form-group">
        <input v-model="form.username" placeholder="用户名" />
      </div>
      <div class="form-group">
        <input v-model="form.password" type="password" placeholder="密码" @keyup.enter="login" />
      </div>
      <div class="btn-group">
        <button @click="login" class="btn-primary">登录</button>
        <button @click="register" class="btn-secondary">注册</button>
      </div>
      <p class="tip">测试账号: student1/123456, teacher1/123456, admin/123456</p>
    </div>
  </div>
</template>

<script>
import { auth } from '@/api'

export default {
  name: 'Login',
  data() {
    return {
      form: { username: '', password: '' }
    }
  },
  methods: {
    async login() {
      try {
        const res = await auth.login(this.form.username, this.form.password)
        if (res.data.code === 200) {
          localStorage.setItem('token', res.data.data.token)
          localStorage.setItem('userId', res.data.data.userId)
          localStorage.setItem('username', res.data.data.username)
          localStorage.setItem('role', res.data.data.role)
          localStorage.setItem('realName', res.data.data.realName)
          this.$router.push('/courses')
        } else {
          alert(res.data.message)
        }
      } catch (e) {
        alert('登录失败')
      }
    },
    async register() {
      try {
        const res = await auth.register(this.form.username, this.form.password)
        if (res.data.code === 200) {
          alert('注册成功，请登录')
        } else {
          alert(res.data.message)
        }
      } catch (e) {
        alert('注册失败')
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-box {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.2);
  width: 350px;
}
.login-box h2 { text-align: center; margin-bottom: 30px; color: #333; }
.form-group { margin-bottom: 20px; }
.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
}
.btn-group { display: flex; gap: 10px; }
.btn-primary, .btn-secondary {
  flex: 1;
  padding: 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}
.btn-primary { background: #409eff; color: white; }
.btn-secondary { background: #909399; color: white; }
.tip { margin-top: 20px; text-align: center; color: #999; font-size: 12px; }
</style>
