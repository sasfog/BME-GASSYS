import { createStore } from "vuex";

export default createStore({
  state: {
    user: null,
    email: null,
    name: null,
    fakeEmail: "jakab.gipsz@fakemail.com",
    fakeName: "Jakab Gipsz"
  },
  mutations: {
    login(state, username) {
      state.user = username
      state.email = state.fakeEmail
      state.name = state.fakeName
    },
    logout(state) {
      state.user = null
    },
    register(state, registerData){
      state.user = registerData.username
      state.email = registerData.email
      state.name = registerData.firstName + " " + registerData.lastName
    }
  },
  actions: {},
  getters: {
    isAuthenticated: state => !!state.user,
    StateUser: state => state.user,
    StateEmail: state => state.email,
    StateName: state => state.name,
  },
  modules: {},
});
