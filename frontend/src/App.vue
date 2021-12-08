<template>
  <div class="d-flex flex-row">
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
