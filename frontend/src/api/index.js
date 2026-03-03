import axios from 'axios'

const api = axios.create({
  baseURL: '/api'
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export const auth = {
  login(username, password) {
    return api.post('/auth/login', { username, password })
  },
  register(username, password) {
    return api.post('/auth/register', { username, password })
  }
}

export const courses = {
  getList(page, size, keyword) {
    return api.get('/courses', { params: { page, size, keyword } })
  },
  getById(id) {
    return api.get(`/courses/${id}`)
  },
  create(data) {
    return api.post('/courses', data)
  },
  update(id, data) {
    return api.put(`/courses/${id}`, data)
  },
  delete(id) {
    return api.delete(`/courses/${id}`)
  }
}

export const selections = {
  select(courseId) {
    return api.post(`/selections/${courseId}`)
  },
  cancel(courseId) {
    return api.delete(`/selections/${courseId}`)
  },
  myCourses() {
    return api.get('/selections/my')
  },
  getSelectedIds() {
    return api.get('/selections/ids')
  }
}

export default api
