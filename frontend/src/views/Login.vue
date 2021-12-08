<template>
  <div class="w-100 vh-100 d-flex justify-content-center align-items-center">
    <form class="w-25 border border-info border-2 rounded">
      <div class="d-flex flex-column justify-content-center align-items-center bg-light">
        <label for="username"><b>Username</b></label>
        <input
          type="text"
          placeholder="Username"
          name="username"
          v-model="username"
          required
          class="mw-0"
        />
        <label for="password"><b>Password</b></label>
        <input
          type="password"
          placeholder="Password.."
          name="username"
          v-model="password"
          required
        />
        <button type="submit" class="btn-primary my-3" v-on:click="login($event)">
          Login
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    login(event) {
      if (event) {
        event.preventDefault()
      }
      if (this.password) {
        this.$store.commit("login", this.username);
        this.$router.push("/appointments");
      }
    },
  },
};
</script>
