<template>
  <div class="appointments w-100 p-1">
  <div v-if="loading" class="d-flex justify-content-center">
    <h1>Loading...</h1>
    </div>
    <table v-else class="table">
      <tr class="border border-dark">
        <th class="border border-dark">
        </th>
        <th
          class="border border-dark"
          v-for="(day, index) in datesOfWeek"
          v-bind:key="index"
          v-bind:value="day"
        >
          <div class="text-center">{{ daysOfWeek[index] }}</div>
          <div class="text-center">{{ day }}</div>
        </th>
        <th>
        </th>
      </tr>
      <tr>
        <!--
      <calendarEvent v-if="eventsOfTheWeek && eventsOfTheWeek[0]" v-bind:event="eventsOfTheWeek[0]" v-on:click="showEvent(1)"></calendarEvent>
      <eventModal v-bind:event="eventsOfTheWeek[0]" v-if="showEventModal" v-on:close-modal="hideEvent"></eventModal>
      --></tr>
      <!--<tr v-for="time in hours" :key="time.value">
        <td>{{ time }}</td>
        <template v-for="(day, index) in datesOfWeek" :key="index">
        <td>{{daysOfWeek[index]}}</td>
        </template>
        <td>{{ time }}</td>
      </tr>
      -->
      <template v-if="eventsForEveryHour && users && appointments">
        <template
          v-for="(eventList, index) in eventsForEveryHour"
          v-bind:key="index"
        >
          <calendar-row
            v-if="hours && eventsForEveryHour && appointments"
            v-bind:eventList="eventList"
            v-bind:time="hours[index]"
            v-bind:organiserList="users"
            v-bind:appointmentList="appointments"
          ></calendar-row>
        </template>
      </template>
    </table>
    <ul
      class="
        pagination
        position-relative
        bottom-0
        start-50
        end-50
        translate-middle-x
        d-flex
        justify-content-center
      "
      v-if="!loading"
    >
      <li class="page-item d-flex align-items-center">
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
          class="
            d-flex
            justify-content-center
            flex-nowrap flex-grow-1
            page-link
            bottom-border
          "
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
  </div>
  <div class="w-25 d-flex justify-content-center align-items-start">
    <button
      type="button"
      class="btn btn-primary mt-2"
      v-on:click="displayModal"
    >
      Új esemény
    </button>
    <newEventModal v-if="showModal" v-on:close-modal="hideModal">
    </newEventModal>
  </div>
</template>

<script>
import newEventModal from "./NewEventModal.vue";
import axios from "axios";
import * as config from "../scripts/constants.js";
import calendarRow from "./CalendarRow.vue";
export default {
  components: {
    newEventModal,
    calendarRow,
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
      eventsForEveryHour: [],
      eventsForFirstHour: [],
      users: {},
      appointments: {},
      loading: false
    };
  },
  mounted() {
    this.generateHours();
  },
  created() {
    this.updateTimeTable();
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
        this.hours.push(this.convertTimeToHourString(elem));
      }
    },
    convertTimeToHourString(time) {
      var hour = parseInt(time / 60);
      var min = time % 60;
      var hourString = "";
      var minString = "";
      if (hour < 10) hourString = "0" + hour;
      else hourString = hour;
      if (min == 0) minString = min + "0";
      else minString = min;
      return hourString + ":" + minString;
    },
    displayModal() {
      this.showModal = true;
    },
    hideModal() {
      this.showModal = false;
    },
    previousWeek() {
      this.selectedWeek -= 1;
      this.updateTimeTable();
    },
    nextWeek() {
      this.selectedWeek += 1;
      this.updateTimeTable();
    },
    updateTimeTable() {
      this.loading = true
      this.getEventsForWeek();
      this.getUsersForWeek();
      this.getAppointmentsForWeek();
    },
    getEventsForWeek() {
      var queryParams = {
        start_date: this.datesOfWeek[0],
        end_date: this.datesOfWeek[6],
      };
      axios
        .get(config.eventEndpoint, { params: queryParams })
        .then((response) => {
          this.eventsOfTheWeek = response.data["events"];
          this.getEventsForEachHour();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getUsersForWeek() {
      axios
        .get(config.userEndpoint)
        .then((response) => {
          var users = response.data["users"];
          users.sort((a, b) => a.id - b.id);
          for (const user of users) {
            if (this.isUserTheOrganiser(user.id, this.eventsOfTheWeek)) {
              this.users[user.id] = user;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    isUserTheOrganiser(userId, events) {
      for (const event of events) {
        if (parseInt(event.organiserId) == parseInt(userId)) return true;
      }
      return false;
    },
    getAppointmentsForWeek() {
      axios
        .get(config.appointmentEndpoint)
        .then((response) => {
          var appointments = response.data["appointments"];
          appointments.sort((a, b) => a.eventId - b.eventId);
          for (const appointment of appointments) {
            if (
              this.appointmentBelongsToEvent(
                appointment.eventId,
                this.eventsOfTheWeek
              )
            )
              this.appointments[appointment.eventId] = appointment;
              this.loading = false
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    appointmentBelongsToEvent(eventId, events) {
      for (const event of events) {
        if (parseInt(event.id) == parseInt(eventId)) return true;
      }
      return false;
    },
    getEvent(startDate, time) {
      var filterDateTime = new Date(startDate + " " + time);
      var eventEndTime = null;
      var eventStartTime = null;

      for (const event of this.eventsOfTheWeek) {
        eventStartTime = new Date(event["startDateTime"]);
        eventEndTime = new Date(event["startDateTime"]).setHours(time);
        if (filterDateTime >= eventStartTime && filterDateTime <= eventEndTime)
          return event;
      }
      return null;
    },
    getEventsForHour(hour, events) {
      var eventsForHour = [];
      for (const event of events) {
        var startDate = new Date(event["startDateTime"]);
        var startHour = startDate.getHours();
        var startMin = startDate.getMinutes();
        var startTime = startHour * 60 + startMin;
        //console.log(hour + " == " + this.convertTimeToHourString(startTime))
        //console.log(hour === this.convertTimeToHourString(startTime))
        if (hour === this.convertTimeToHourString(startTime))
          eventsForHour.push(event);
      }
      return eventsForHour;
    },
    getEventsForEachHour() {
      //console.log("Events of the week: ")
      //console.log(this.eventsOfTheWeek)
      var eventsForEveryHour = [];
      for (const hour of this.hours) {
        var eventsInHour = this.getEventsForHour(hour, this.eventsOfTheWeek);
        //console.log("Hour: " + hour)
        //console.log(eventsInHour)
        var listForHour = [];
        for (var i = 0; i < 7; i++) {
          var eventsInHourInDay = this.getEventsForDay(
            this.datesOfWeek[i],
            eventsInHour
          );
          if ((eventsInHourInDay, eventsInHourInDay[0]))
            listForHour.push(eventsInHourInDay[0]);
          else
            listForHour.push(
              this.getPlaceholderOrNull(
                hour,
                this.getEventsForDay(this.datesOfWeek[i], this.eventsOfTheWeek)
              )
            );
        }
        //console.log(listForHour)
        eventsForEveryHour.push(listForHour);
      }
      this.eventsForEveryHour = eventsForEveryHour;
    },
    getEventsForDay(localeDateString, events) {
      var eventsInDay = [];
      for (const event of events) {
        if (
          localeDateString ===
          new Date(event.startDateTime).toLocaleDateString()
        )
          eventsInDay.push(event);
      }
      return eventsInDay;
    },
    getPlaceholderOrNull(hourString, events) {
      for (const event of events) {
        var time = hourString.split(":");
        var hour = parseInt(time[0]);
        var minute = parseInt(time[1]);
        var currentTime = hour * 60 + minute;
        var startTime =
          new Date(event.startDateTime).getHours() * 60 +
          new Date(event.startDateTime).getMinutes();
        var endTime = startTime + parseInt(event.duration);
        if (startTime < currentTime && currentTime < endTime) return "X";
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
