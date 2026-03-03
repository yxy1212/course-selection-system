<template>
  <div id="app">
    <div class="header">
      <div class="logo">🎓 选课管理系统</div>
      <div class="nav" v-if="isLoggedIn">
        <router-link to="/courses">课程列表</router-link>
        <router-link to="/my-courses" v-if="userRole === 'STUDENT'">我的选课</router-link>
        <span class="username">{{ username }}</span>
        <button @click="logout">退出</button>
      </div>
    </div>
    <div class="main">
      <router-view />
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
      userRole: ''
    }
  },
  created() {
    this.checkLogin()
  },
  methods: {
    checkLogin() {
      const token = localStorage.getItem('token')
      this.isLoggedIn = !!token
      if (this.isLoggedIn) {
        this.username = localStorage.getItem('username') || ''
        this.userRole = localStorage.getItem('role') || ''
      }
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
.nav button {
  background: #fff;
  color: #409eff;
  border: none;
  padding: 5px 15px;
  margin-left: 15px;
  cursor: pointer;
  border-radius: 3px;
}
.main { padding: 20px; }
</style>
