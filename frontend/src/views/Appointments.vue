<template>
  <div class="appointments w-100">
    <table class="table">
      <tr>
        <th></th>
        <th
          v-for="(day, index) in datesOfWeek"
          v-bind:key="index"
          v-bind:value="day"
        >
          <div>{{ daysOfWeek[index] }}</div>
          <div>{{ day }}</div>
        </th>
        <th></th>
      </tr>
      <tr v-for="time in hours" :key="time.value">
        <td>{{ time }}</td>
        <template v-for="(day, index) in datesOfWeek" :key="index">
        <td v-if="true">{{daysOfWeek[index]}}</td>
        </template>
        <td>{{ time }}</td>
      </tr>
    </table>
  </div>
  <div class="w-25">
    <button type="button" class="btn btn-primary" v-on:click="displayModal">
      Új esemény
    </button>
    <newEventModal v-if="showModal" v-on:close-modal="hideModal">
    </newEventModal>
  </div>
  <ul
    class="pagination position-fixed bottom-0 start-50 end-50 translate-middle-x flex-grow-1"
  >
    <li class="page-item d-flex flex-grow-1 align-items-center">
      <button type="button" class="page-link" v-on:click="previousWeek">
        Previous
      </button>
    </li>
    <li class="page-item disabled d-flex flex-column" aria-current="page">
      <div class="d-flex justify-content-center page-link top-border">
        {{
          selectedWeek + currentWeek == 52
            ? selectedWeek + currentWeek
            : (selectedWeek + currentWeek) % 52
        }}. hét
      </div>
      <div
        class="d-flex justify-content-center flex-nowrap flex-grow-1 page-link bottom-border"
      >
        {{ datesOfWeek[0] }} - {{ datesOfWeek[6] }}
      </div>
    </li>
    <li class="page-item d-flex align-items-center">
      <button type="button" class="page-link" v-on:click="nextWeek">
        Next
      </button>
    </li>
  </ul>
</template>

<script>
import newEventModal from "./NewEventModal.vue";
import axios from "axios";
import * as config from "../scripts/constants.js";
//import calendarEvent from "./CalendarEvent.vue";
export default {
  components: {
    newEventModal,
   // calendarEvent,
  },
  data() {
    return {
      hours: [],
      maxHour: 24 * 60,
      minHour: 6 * 60,
      showModal: false,
      selectedWeek: 0,
      daysOfWeek: [
        "Hétfő",
        "Kedd",
        "Szerda",
        "Csütörtök",
        "Péntek",
        "Szombat",
        "Vasárnap",
      ],
      eventsOfTheWeek: null,
      timetable: new Array(7)
    };
  },
  mounted() {
    this.generateHours();
  },
  created() {
    this.getEventsForWeek();
    for (var i = 0; i < this.timetable.length; i++){
      this.timetable[i] = new Array(this.hours.length)
    }
  },
  methods: {
    generateHours() {
      var currentHour = this.minHour;
      var hoursIntegers = [];
      while (currentHour < this.maxHour) {
        hoursIntegers.push(currentHour);
        currentHour += 30;
      }

      for (const elem of hoursIntegers) {
        var hour = parseInt(elem / 60);
        var min = elem % 60;
        var hourString = "";
        var minString = "";
        if (hour < 10) hourString = "0" + hour;
        else hourString = hour;
        if (min == 0) minString = min + "0";
        else minString = min;
        this.hours.push(hourString + ":" + minString);
      }
    },
    displayModal() {
      this.showModal = true;
    },
    hideModal() {
      this.showModal = false;
    },
    previousWeek() {
      this.selectedWeek -= 1;
      this.getEventsForWeek();
    },
    nextWeek() {
      this.selectedWeek += 1;
      this.getEventsForWeek();
    },
    getEventsForWeek() {
      var startDate = this.datesOfWeek[0];
      var endDate = this.datesOfWeek[6];
      var queryParams = {
        start_date: this.datesOfWeek[0],
        end_date: this.datesOfWeek[6],
      };
      console.log(startDate);
      console.log(endDate);
      axios
        .get(config.eventEndpoint, { params: queryParams })
        .then((response) => {
          console.log(response.data);
          this.eventsOfTheWeek = response.data["events"];
          console.log(this.eventsOfTheWeek);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getEvent(startDate, time) {
      console.log("getEvent called with: " + "startDate: " + startDate + " time: " + time)
      var filterDateTime = new Date(startDate + " " + time);
      var eventEndTime = null;
      var eventStartTime = null;
      
      for (const event in this.eventsOfTheWeek) {
        eventStartTime = new Date(event['startDateTime']);
        eventEndTime = new Date(event['startDateTime']).setHours(time);
        if (filterDateTime >= eventStartTime && filterDateTime <= eventEndTime)
          return event;
      }
      return null;
    },
  },
  computed: {
    currentWeek() {
      var today = new Date();
      var thisYear = today.getFullYear();
      var yearDate = new Date(thisYear, 0, 1, 0, 0, 0, 0);
      var elapsedTime = today - yearDate;
      var week = Math.floor(elapsedTime / 1000 / 60 / 60 / 24 / 7) + 1;
      console.log("CurrentWeek: " + week);
      return week;
    },
    datesOfWeek() {
      var today = new Date();
      var dayOfWeek = (today.getDay() - 1) % 6;
      var day = 1000 * 60 * 60 * 24;
      var week = day * 7;
      var firstDayOfWeekInMillis =
        today.getTime() - dayOfWeek * day + this.selectedWeek * week;
      var firstDayDate;
      var datesOfWeek = [];
      for (var i = 0; i < 7; i++) {
        firstDayDate = new Date(firstDayOfWeekInMillis + i * day);
        datesOfWeek.push(firstDayDate.toLocaleDateString());
      }
      return datesOfWeek;
    },
  },
};
</script>

<style>
.page-link.top-border {
  border-bottom: 0px;
}
.page-link.bottom-border {
  border-top: 0px;
}
</style>
