<template>
  <div id="app">
    <div class="header">
      <div class="logo">🎓 选课管理系统</div>
      <div class="nav" v-if="isLoggedIn">
        <router-link to="/courses">课程列表</router-link>
        <router-link to="/my-courses" v-if="userRole === 'STUDENT'">我的选课</router-link>
        <span class="username">{{ realName }} ({{ userRole }})</span>
        <button @click="logout" class="btn-logout">退出</button>
      </div>
    </div>
    <div class="main">
      <router-view @login-success="onLoginSuccess" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isLoggedIn: false,
      username: '',
      realName: '',
      userRole: ''
    }
  },
  created() {
    this.checkLogin()
  },
  watch: {
    '$route': 'checkLogin'
  },
  methods: {
    checkLogin() {
      const token = localStorage.getItem('token')
      this.isLoggedIn = !!token
      if (this.isLoggedIn) {
        this.username = localStorage.getItem('username') || ''
        this.realName = localStorage.getItem('realName') || ''
        this.userRole = localStorage.getItem('role') || ''
      }
    },
    onLoginSuccess() {
      this.checkLogin()
    },
    logout() {
      localStorage.clear()
      this.isLoggedIn = false
      this.$router.push('/login')
    }
  }
}
</script>

<style>
.header {
  background: #409eff;
  color: white;
  padding: 15px 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo { font-size: 20px; font-weight: bold; }
.nav a { color: white; margin: 0 15px; text-decoration: none; }
.nav a:hover { text-decoration: underline; }
.nav .username { margin-left: 15px; color: #fff; }
.nav button {
  background: #fff;
  color: #409eff;
  border: none;
  padding: 5px 15px;
  margin-left: 15px;
  cursor: pointer;
  border-radius: 3px;
}
.btn-logout { background: #f56c6c !important; color: white !important; }
.main { padding: 20px; }
</style>
