<template>
  <td v-bind:rowspan="cellRowSpan" class="text-white " v-bind:class="{ 'bg-dark': !isFull, 'bg-secondary': isFull}">
    <div
      class="
        d-flex
        flex-column
        justify-content-center
        align-items-center
      "
      v-on:click="showEvent"
    >
      <div v-if="organiser" >{{ fullName }}</div>
      <div>{{ eventName }}</div>
      <div v-if="appointment">{{ capacityString }}</div>
    </div>
    <eventModal
      v-bind:event="event"
      v-if="showModal && event && organiser && appointment"
      v-on:close="hideEvent"
      v-bind:organiser="organiser"
      v-bind:appointment="appointment"
    ></eventModal>
  </td>
</template>

<script>
import eventModal from "./EventModal.vue";
export default {
  components: {
    eventModal,
  },
  props: ["event", 'organiser', 'appointment' ],
  data() {
    return {
      showModal: false,
    };
  },
  created(){
    console.log(this.appointment)
  },
  methods: {
    showEvent() {
      if(!this.isFull)
      this.showModal = true;
    },
    hideEvent() {
      if(!this.isFull)
      this.showModal = false;
    },
  },
  computed: {
    cellRowSpan() {
      return Math.floor(parseInt(this.duration) / 30);
    },
    capacity() {
      return this.event["capacity"];
    },
    eventName() {
      return this.event["name"];
    },
    organiserName() {
      return this.event["organiserId"];
    },
    duration() {
      return this.event["duration"];
    },
    isFull() {
      if(this.appointment)
        return parseInt(this.appointment.applicantIds.length) == parseInt(this.capacity)
      return false
    },
    fullName() {
      return this.organiser.firstName + " " + this.organiser.lastName
    },
    capacityString(){
      return this.appointment.applicantIds.length + "/" + this.capacity
    }
  },
};
</script>
