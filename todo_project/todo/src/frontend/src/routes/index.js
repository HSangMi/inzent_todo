import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'

Vue.use(Router)

const requireAuth = (to,from,next) => {
    const loginPath = `/login?rPath=${encodeURIComponent(to.path)}`
    console.log(loginPath);
    store.getters.isAuth ? next() : next(loginPath)
}
const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        { path: '/', component : () => {import('@/views/BoardMain.vue')}, beforeEnter: requireAuth},
        { path: '/login', component : () => import('@/views/LoginPage.vue')}
    ]
})

//router.beforeEach(requireAuth);

export default router // 모듈로 export