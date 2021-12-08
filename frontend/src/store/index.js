import { createStore } from "vuex";

export default createStore({
  state: {
    user: null,
    email: null,
  },
  mutations: {
    login(state, username) {
      state.user = username
    },
    logout(state) {
      state.user = null
    },
    register(state, registerData){
      state.user = registerData.username
      state.email = registerData.email
    }
  },
  actions: {},
  getters: {
    isAuthenticated: state => !!state.user,
    StateUser: state => state.user,
    StateEmail: state => state.email,
  },
  modules: {},
});
