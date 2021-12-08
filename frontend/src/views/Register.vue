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
        <label for="email">Email</label>
        <input
          type="text"
          placeholder="gipszjakab@fakemail.com"
          name="email"
          v-model="email"
          required
        />
        <label for="password">Password</label>
        <input
          type="password"
          placeholder="Password.."
          name="password"
          v-model="password"
          required
        />
        <label for="confirmPassword">Confirm Password</label>
        <input
          type="password"
          placeholder="Confirm Password.."
          name="confirmPassword"
          v-model="confirmPassword"
          required
        />
        <button type="submit" class="btn-primary my-3" v-on:click="register($event)">Register</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: null,
      email: null,
      password: null,
      confirmPassword: null,
    };
  },
  methods: {
    register(event) {
      if (event) {
        event.preventDefault()
      }
      if(this.username && this.email && this.password && this.confirmPassword && this.password === this.confirmPassword){
        var registerData = {
          username: this.username,
          email: this.email
        }
      this.$store.commit("register", registerData);
      this.$router.push("/appointments");
      }
    },
  },
};
</script>
