<template>
  <div class="courses-container">
    <div class="toolbar">
      <input v-model="keyword" placeholder="搜索课程..." @keyup.enter="search" />
      <button @click="search" class="btn-search">搜索</button>
      <button v-if="isTeacher" @click="showAddDialog = true" class="btn-add">创建课程</button>
    </div>
    
    <div class="course-list">
      <div v-for="course in courses" :key="course.id" class="course-card">
        <div class="course-info">
          <h3>{{ course.name }}</h3>
          <p class="teacher">教师: {{ course.teacherName }}</p>
          <p class="desc">{{ course.description }}</p>
          <p class="meta">学分: {{ course.credits }} | 容量: {{ course.selectedCount }}/{{ course.capacity }}</p>
        </div>
        <div class="course-actions">
          <button v-if="isStudent && !selectedIds.includes(course.id)" 
                  @click="selectCourse(course.id)" 
                  :disabled="course.selectedCount >= course.capacity">
            {{ course.selectedCount >= course.capacity ? '已满' : '选课' }}
          </button>
          <button v-if="isStudent && selectedIds.includes(course.id)" 
                  @click="cancelCourse(course.id)" 
                  class="btn-cancel">
            退课
          </button>
          <button v-if="isTeacher && course.teacherId === userId" @click="deleteCourse(course.id)" class="btn-delete">删除</button>
        </div>
      </div>
    </div>
    
    <div class="pagination">
      <button @click="prevPage" :disabled="page <= 1">上一页</button>
      <span>第 {{ page }} 页</span>
      <button @click="nextPage" :disabled="courses.length < size">下一页</button>
    </div>
  </div>
</template>

<script>
import { courses, selections } from '@/api'

export default {
  name: 'Courses',
  data() {
    return {
      courses: [],
      selectedIds: [],
      keyword: '',
      page: 1,
      size: 10,
      userId: null,
      userRole: '',
      showAddDialog: false
    }
  },
  computed: {
    isStudent() { return this.userRole === 'STUDENT' },
    isTeacher() { return this.userRole === 'TEACHER' || this.userRole === 'ADMIN' }
  },
  created() {
    this.userId = parseInt(localStorage.getItem('userId'))
    this.userRole = localStorage.getItem('role')
    this.loadCourses()
    if (this.isStudent) {
      this.loadSelectedIds()
    }
  },
  methods: {
    async loadCourses() {
      const res = await courses.getList(this.page, this.size, this.keyword)
      if (res.data.code === 200) {
        this.courses = res.data.data.records
      }
    },
    async loadSelectedIds() {
      const res = await selections.getSelectedIds()
      if (res.data.code === 200) {
        this.selectedIds = res.data.data
      }
    },
    async search() {
      this.page = 1
      this.loadCourses()
    },
    async prevPage() {
      if (this.page > 1) {
        this.page--
        this.loadCourses()
      }
    },
    async nextPage() {
      this.page++
      this.loadCourses()
    },
    async selectCourse(courseId) {
      const res = await selections.select(courseId)
      if (res.data.code === 200) {
        alert('选课成功')
        this.loadCourses()
        this.loadSelectedIds()
      } else {
        alert(res.data.message)
      }
    },
    async cancelCourse(courseId) {
      if (!confirm('确定要退课吗?')) return
      const res = await selections.cancel(courseId)
      if (res.data.code === 200) {
        alert('退课成功')
        this.loadCourses()
        this.loadSelectedIds()
      } else {
        alert(res.data.message)
      }
    },
    async deleteCourse(courseId) {
      if (!confirm('确定要删除课程吗?')) return
      const res = await courses.delete(courseId)
      if (res.data.code === 200) {
        alert('删除成功')
        this.loadCourses()
      } else {
        alert(res.data.message)
      }
    }
  }
}
</script>

<style scoped>
.toolbar { margin-bottom: 20px; display: flex; gap: 10px; }
.toolbar input { padding: 8px; border: 1px solid #ddd; border-radius: 4px; flex: 1; }
.toolbar button { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; }
.btn-search { background: #409eff; color: white; }
.btn-add { background: #67c23a; color: white; }
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
  background: #409eff;
  color: white;
  margin-left: 10px;
}
.course-actions button:disabled { background: #ccc; cursor: not-allowed; }
.course-actions .btn-cancel { background: #e6a23c; }
.course-actions .btn-delete { background: #f56c6c; }
.pagination { margin-top: 20px; text-align: center; }
.pagination button {
  padding: 8px 15px;
  margin: 0 10px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
}
</style>
