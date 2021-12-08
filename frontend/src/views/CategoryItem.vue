<template>
  <div
    class="d-flex align-items-center justify-content-between categoryItem m-3"
  >
    <span class="px-1">{{ listItem }}</span>
    <input
      class="redButton"
      type="image"
      v-bind:src="require('../assets/' + 'red_close.svg')"
      v-on:click="deleteCategory"
    />
  </div>
</template>

<script>
export default {
  props: ["listItem"],
  methods: {
    deleteCategory() {
      console.log("Called deleteCategory with: " + this.listItem);
      this.$emit("deleteCategory", this.listItem);
    },
  },
};
</script>

<style>
.categoryItem {
  border: 1px solid black;
}
.redButton {
  border: 1px solid black;
}
</style>
