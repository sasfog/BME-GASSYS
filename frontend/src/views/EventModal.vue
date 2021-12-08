<template>
  <div class="modal show text-black" tabindex="-1" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header d-flex">
          <div class="d-flex justify-content-center w-100">
            <h1>{{ eventName }}</h1>
          </div>
          <button
            type="button"
            class="btn-close"
            v-on:click="close"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body d-flex flex-column justify-content-center align-items-center">
          <div class="d-flex w-50 align-items-center justify-content-around py-3">
            <b>Szervező:</b>
            <b>{{ eventOrganiser}}</b>
          </div>
          <p class="border border-secondary" >{{ eventDescription }}</p>
          <div class="d-flex flex-row flex-wrap">
            <div v-for="category in eventCategories" v-bind:key="category">
              {{ category }}
            </div>
          </div>
          <div class="d-flex w-50 align-items-center justify-content-around pe-2">
            <div class="d-flex my-2">
              <b>{{ eventStartDateTime }}</b>
            </div>
            <b>-</b>
            <div class="d-flex my-2">
              <b>{{ eventEndDateTime }}</b>
            </div>
          </div>
          <div class="d-flex w-50 align-items-center justify-content-around">
            <div class="d-flex justify-content-between my-2 ps-3">
              <b>Férőhelyek:</b>
            </div>
            <div class="d-flex justify-content-between my-2 ps-3">
              <b>{{ eventCapacity }}</b>
            </div>
          </div>
        </div>
        <div class="modal-footer d-flex justify-content-center">
          <button class="btn btn-primary" v-on:click="addEvent">
            Jelentkezés
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: ["event", "organiser", "appointment" ],
  methods: {
    close() {
      this.$emit("close");
    },
  },
  data() {
    return {};
  },
  computed: {
    eventName() {
      return this.event.name;
    },
    eventDescription() {
      return this.event.description;
    },
    eventCategories() {
      return this.event.categories;
    },
    eventStartDateTime() {
      return new Date(this.event.startDateTime).toLocaleDateString();
    },
    eventDuration() {
      return this.event.duration;
    },
    eventEndDateTime() {
      var endDateTime =
        new Date(this.event.startDateTime).getTime() + this.eventDuration * 60 * 1000;
      return new Date(endDateTime).toLocaleDateString();
    },
    eventCapacity() {
      return this.appointment.applicantIds.length + "/" + this.event.capacity;
    },
    eventOrganiser(){
      return this.organiser.firstName + " " + this.organiser.lastName;
    }
  },
};
</script>

<style>
#kezdes-blokk {
  border-top: 1px solid black;
  border-bottom: 1px solid black;
}

.w-60 {
  width: 60%;
}
</style>
