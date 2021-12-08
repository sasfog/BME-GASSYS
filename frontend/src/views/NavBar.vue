<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light flex-column">
    <div class="navbar-brand mx-1" href="#"><b>BME-GASSYS</b></div>
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
      aria-orientation="vertical"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="h-50 mh-50 d-flex column justify-content-center"> 
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul
        class="navbar-nav flex-column align-items-center w-100"
        v-if="isLoggedIn"
      >
        <li class="nav-item w-100">
          <div class="d-flex align-items-center w-100">
            <img
              v-bind:src="require('../assets/' + navUserImage)"
              width="16"
              height="16"
              class="m-1 mx-3"
            />
            <div class="d-flex flex-column flex-wrap">
              <div class="px-3"><b>{{ navUserText }}</b></div>
            </div>
          </div>
        </li>
        <li class="nav-item w-100">
          <navLink
            v-bind:text="navSettingsText"
            v-bind:imageSource="navSettingsImage"
          />
        </li>
        <li class="nav-item w-100">
          <navLink
            v-bind:text="navAppointmentsText"
            v-bind:imageSource="navAppointmentsImage"
          />
        </li>
        <li class="nav-item w-100">
          <navLink
            v-bind:text="navLogoutText"
            v-bind:imageSource="navLogoutImage"
          />
        </li>
      </ul>
      <ul v-else class="navbar-nav flex-column align-items-center w-100">
        <li class="nav-item w-100">
          <navLink
            v-bind:text="navRegisterText"
            v-bind:imageSource="navRegisterImage"
          />
        </li>
        <li class="nav-item w-100">
          <navLink v-bind:text="navLoginText" v-bind:imageSource="navLoginImage" />
        </li>
      </ul>
    </div>
    </div>
    <div class="h-50 mh-50"></div>
  </nav>
</template>

<script>
import navLink from "./NavLink.vue";
export default {
  components: {
    navLink,
  },
  data() {
    return {
      navUserImage: "person-circle.svg",
      navSettingsText: "Settings",
      navSettingsImage: "gear-fill.svg",
      navAppointmentsText: "Appointments",
      navAppointmentsImage: "calendar-check.svg",
      navLoginText: "Login",
      navLoginImage: "box-arrow-in-left.svg",
      navRegisterText: "Register",
      navRegisterImage: "box-arrow-in-left.svg",
      navLogoutText: "Logout",
      navLogoutImage: "box-arrow-in-left.svg",
    };
  },
  mounted() {},
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },
    navUserDisplayText() {
      console.log("test");
      return this.navUserText.split(" ");
    },
    navUserText() {
      return this.$store.getters.StateName;
    },
  },
};
</script>
