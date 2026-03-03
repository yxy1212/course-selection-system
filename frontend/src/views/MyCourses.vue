<template>
  <div class="my-courses-container">
    <h2>我的选课</h2>
    <div v-if="courses.length === 0" class="empty">暂无选课记录</div>
    <div class="course-list">
      <div v-for="course in courses" :key="course.id" class="course-card">
        <div class="course-info">
          <h3>{{ course.name }}</h3>
          <p class="teacher">教师: {{ course.teacherName }}</p>
          <p class="desc">{{ course.description }}</p>
          <p class="meta">学分: {{ course.credits }}</p>
        </div>
        <div class="course-actions">
          <button @click="cancelCourse(course.id)" class="btn-cancel">退课</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { selections } from '@/api'

export default {
  name: 'MyCourses',
  data() {
    return {
      courses: []
    }
  },
  created() {
    this.loadMyCourses()
  },
  methods: {
    async loadMyCourses() {
      const res = await selections.myCourses()
      if (res.data.code === 200) {
        this.courses = res.data.data
      }
    },
    async cancelCourse(courseId) {
      if (!confirm('确定要退课吗?')) return
      const res = await selections.cancel(courseId)
      if (res.data.code === 200) {
        alert('退课成功')
        this.loadMyCourses()
      } else {
        alert(res.data.message)
      }
    }
  }
}
</script>

<style scoped>
.my-courses-container { max-width: 800px; margin: 0 auto; }
.my-courses-container h2 { margin-bottom: 20px; color: #333; }
.empty { text-align: center; color: #999; padding: 40px; }
.course-list { display: flex; flex-direction: column; gap: 15px; }
.course-card {
  background: white;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.course-info h3 { margin-bottom: 8px; color: #333; }
.course-info .teacher { color: #666; margin-bottom: 5px; }
.course-info .desc { color: #999; font-size: 14px; margin-bottom: 5px; }
.course-info .meta { color: #409eff; font-size: 14px; }
.course-actions button {
  padding: 8px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background: #e6a23c;
  color: white;
}
</style>
