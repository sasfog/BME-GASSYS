<template>
  <div class="d-flex flex-row mh-100 h-100">
    <navBar> </navBar>
    <router-view />
  </div>
</template>

<script>
import navBar from "./views/NavBar.vue";
export default {
  components: {
    navBar,
  },
};
</script>
