import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Upload from '../views/Upload.vue'
const routes = [
 {
   path: '/',
   name: 'Home',
   component: Home
 },
 {
   path: '/login',
   name: 'Login',
   component: Login
 },
 {
   path: '/upload',
   name: 'Upload',
   component: Upload
 }
]
const router = createRouter({
 history: createWebHistory(),
 routes
})
export default router