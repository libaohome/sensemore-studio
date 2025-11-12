import { createRouter, createWebHistory } from 'vue-router'
import H5WithNav from '../pages/H5WithNav.vue'
import H5Plain from '../pages/H5Plain.vue'

const routes = [
  { path: '/', redirect: '/h5-with-nav' },
  { path: '/h5-with-nav', component: H5WithNav },
  { path: '/h5-plain', component: H5Plain },
]

export default createRouter({
  history: createWebHistory(),
  routes,
})
