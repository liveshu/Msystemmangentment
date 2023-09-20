import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/login',component: () => import('@/views/login/LoginPage.vue') },
    { 
      path: '/', 
      component: () => import('@/views/layout/LayoutContainer.vue'),
      redirect: '/building/floor',
      children: [
        { 
          path: '/building/dorm',
          component:() => import('@/views/building/DormManage.vue')
        },
        { 
          path: '/building/floor',
          component:() => import('@/views/building/FloorManage.vue')
        },
        { 
          path: '/building/student',
          component:() => import('@/views/building/StudentManage.vue')
        },
        {
          path: '/building/admin',
          component:() => import('@/views/building/AdminManage.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to) => {
  const useStore = useUserStore()
  if (!useStore.getToken() && to.path !== '/login') return '/login'
})
export default router
