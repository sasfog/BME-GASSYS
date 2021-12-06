<template>
<nav class="navbar navbar-expand-lg navbar-light bg-light flex-column h-100">
      <a class="navbar-brand" href="#">BME-GASSYS</a>
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
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav flex-column align-items-center w-100">
          <li class="nav-item w-100">
          <div class="d-flex align-items-center w-100">
            <img v-bind:src="require('../assets/' + navUserImage)" width="16" height="16"  class="m-1 mx-3" />
            <div class="d-flex flex-column">
            <span v-for="name in navUserDisplayText" v-bind:key="name" v-bind:value="name" class="px-3">{{ name }}</span>
            </div>
            </div>
          </li>
          <li class="nav-item w-100">
            <navLink v-bind:text="navSettingsText" v-bind:imageSource="navSettingsImage"/>
          </li>
          <li class="nav-item w-100">
            <navLink v-bind:text="navAppointmentsText" v-bind:imageSource="navAppointmentsImage"/>
          </li>
          
        </ul>
      </div>
    </nav>
</template>

<script>
import navLink from "./NavLink.vue";
export default {
      components: {
    navLink
  },
    data() {
        return {
            navUserText: "FirstName LastName",
            navUserImage: "person-circle.svg",
            navSettingsText: "Settings",
            navSettingsImage: "gear-fill.svg",
            navAppointmentsText: "Appointments",
            navAppointmentsImage: "calendar-check.svg"
        }
    },
    mounted(){
    },
    computed: {
      navUserDisplayText(){
        console.log("test")
        return this.navUserText.split(" ")
      }
    }
}
</script>