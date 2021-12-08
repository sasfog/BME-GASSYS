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
        />
        <label for="email"><b>Email</b></label>
        <input
          type="text"
          placeholder="gipszjakab@fakemail.com"
          name="email"
          v-model="email"
          required
        />
        <label for="firstName"><b>First Name</b></label>
        <input
          type="text"
          placeholder="Jakab"
          name="firstName"
          v-model="firstName"
          required
        />
        <label for="lastName"><b>Last Name</b></label>
        <input
          type="text"
          placeholder="Gipsz"
          name="lastName"
          v-model="lastName"
          required
        />
        <label for="password"><b>Password</b></label>
        <input
          type="password"
          placeholder="Password.."
          name="password"
          v-model="password"
          required
        />
        <label for="confirmPassword"><b>Confirm Password</b></label>
        <input
          type="password"
          placeholder="Confirm Password.."
          name="confirmPassword"
          v-model="confirmPassword"
          required
        />
        <button
          type="submit"
          class="btn-primary my-3"
          v-on:click="register($event)"
        >
          Register
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
      email: null,
      password: null,
      confirmPassword: null,
      firstName: null,
      lastName: null,
    };
  },
  methods: {
    register(event) {
      if (event) {
        event.preventDefault();
      }
      if (
        this.username &&
        this.email &&
        this.password &&
        this.confirmPassword &&
        this.password === this.confirmPassword
      ) {
        var registerData = {
          username: this.username,
          email: this.email,
          firstName: this.firstName,
          lastName: this.lastName,
        };
        this.$store.commit("register", registerData);
        this.$router.push("/appointments");
      }
    },
  },
};
</script>
