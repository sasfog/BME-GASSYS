<template>
  <div class="d-flex">
  <router-link v-bind:to="'/' + text">
    <img
      v-bind:src="require('../assets/' + imageSource)"
      width="16"
      height="16"
      class="my-1 mx-3"
    />
    </router-link>
    <router-link
      v-bind:to="'/' + text"
      v-bind:text="text"
      class="px-3"
    ></router-link>
  </div>
</template>

<script>
export default {
  props: ["text", "imageSource"],
};
</script>

<style>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1055;
  display: block;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: auto;
  outline: 0;
}
</style>
