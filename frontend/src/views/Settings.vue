<template>
  <div class="w-100 mh-100">
    <div class="d-flex flex-column align-items-center">
      <h1>{{ userNameText }}</h1>
      <h3>{{ userEmailText }}</h3>
      <label for="password">Password</label>
      <input v-model="password" placeholder="Password.." name="password"/>
      <label for ="confirmPassword" >Confirm Password</label>
      <input v-model="confirmPassword" placeholder="Password.." name="confirmPassword" />
      <button type="button" class="btn-primary my-3">Save</button>
    </div>
  </div>
</template>

<script>
//import axios from "axios";
export default {
  //components: {
  //},
  data() {
    return {
      password: "",
      confirmPassword: "",
    };
  },
  computed: {
    userNameText() {
      return this.$store.getters.StateUser
    },
    userEmailText(){
      return this.$store.getters.StateEmail
    }
  }
};
</script>
