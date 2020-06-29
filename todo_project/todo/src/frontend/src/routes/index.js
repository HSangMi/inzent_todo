import Vue from "vue";
import Router from "vue-router";
import store from "../store";

Vue.use(Router);

const requireAuth = (from, to, next) => {
  const loginPath = `/login?returnPath=${from.path}`;
  store.getters.isAuth ? next() : next(loginPath);
};
const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      component: () => import("@/views/BoardMain.vue"),
      beforeEnter: requireAuth,
    },
    {
      path: "/dashboard",
      component: () => import("@/views/BoardMain.vue"),
      beforeEnter: requireAuth,
    },
    {
      path: "/projects",
      name: "Project List",
      component: () => import("@/views/ProjectMain.vue"),
    },
    {
      path: "/projects/:pid",
      component: () => import("@/views/ProjectBoard.vue"),
    },
    {
      path: "/schedule",
      component: () => import("@/views/Schedule.vue"),
      beforeEnter: requireAuth,
    },
    {
      path: "/search",
      component: () => import("@/views/BoardMain.vue"),
      beforeEnter: requireAuth,
    },
    {
      path: "/archive",
      component: () => import("@/views/BoardMain.vue"),
      beforeEnter: requireAuth,
    },
    { path: "/login", component: () => import("@/views/LoginPage.vue") },
  ],
});

//router.beforeEach(requireAuth);

export default router; // 모듈로 export
