<template>
  <div class="w-100 vh-100 d-flex justify-content-center align-items-center">
    <form class="w-25 border border-info border-2 rounded">
      <div
        class="d-flex flex-column justify-content-center align-items-center bg-light"
      >
        <h1>{{ userNameText }}</h1>
        <h3>{{ userEmailText }}</h3>
        <label for="password">Password</label>
        <input v-model="password" name="password" />
        <label for="newPassword">New Password</label>
        <input v-model="newPassword" name="newPassword" />
        <label for="confirmNewPassword">Confirm Password</label>
        <input
          v-model="confirmNewPassword"
          name="confirmNewPassword"
        />
        <button type="button" class="btn-primary my-3">Save</button>
      </div>
    </form>
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
      newPassword: "",
      confirmNewPassword: "",
    };
  },
  computed: {
    userNameText() {
      return this.$store.getters.StateUser;
    },
    userEmailText() {
      return this.$store.getters.StateEmail;
    },
  },
};
</script>
