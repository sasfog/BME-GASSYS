import { createRouter, createWebHistory } from "vue-router";
import User from "../views/User.vue";
import Settings from "../views/Settings.vue";
import Appointments from "../views/Appointments.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import store from "../store";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Login,
  },
  {
    path: "/settings",
    name: "Settings",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //component: () =>
    //  import(/* webpackChunkName: "about" */ "../views/About.vue"),
    component: Settings,
    meta: { requiresAuth: true },
  },
  {
    path: "/user",
    name: "User",
    component: User,
    meta: { requiresAuth: true },
  },
  {
    path: "/appointments",
    name: "Appointments",
    component: Appointments,
    meta: { requiresAuth: true },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: { guest: true },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: { guest: true },
  },
  {
    path: "/logout",
    name: "Logout",
    component: Login,
    meta: { requiresAuth: true },
    beforeEnter: (to, from, next) => {
      store.commit('logout')
      next('/login')
    }
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isAuthenticated) {
      next();
    } 
    else {
      next('/login');
    }
  } else if (to.matched.some((record) => record.meta.guest)) {
    if (!store.getters.isAuthenticated) {
      next();
    } 
    else {
      next('/')
    }
  }
});

export default router;
