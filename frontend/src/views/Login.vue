<template>
  <div class="w-100 h-max">
    <form>
      <div class="d-flex flex-column justify-content-center align-items-center">
        <label for="username">Username</label>
        <input
          type="text"
          placeholder="Username"
          name="username"
          v-model="username"
          required
        />
        <label for="password">Password</label>
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
