<template>
  <td v-bind:rowspan="cellRowSpan">
    <div class="d-flex flex-column justify-content-center align-items-center">
      <div>{{ organiserName }}</div>
      <div>{{ eventName }}</div>
      <div>{{ capacity }}</div>
    </div>
  </td>
</template>

<script>
export default {
  props: ["eventName", "organiserName", "capacity", "duration"],
  computed: {
      cellRowSpan() {
          console.log(this.duration)
          console.log(this.eventName)
          console.log(this.organiserName)
          console.log(this.capacity)

          return Math.floor(parseInt(this.duration) / 30) + 1
      }
  },
  data() {
      return {

      }
  }
};
</script>
