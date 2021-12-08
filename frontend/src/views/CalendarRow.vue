<template>
  <tr class="border border-dark">
    <td class="border border-dark text-center"><b>{{ time }}</b></td>
    <template v-for="(event, index) in eventList" :key="index">
      <calendarEvent v-if="organiserList && appointmentList && event && event != 'X'" v-bind:event="event" v-bind:organiser="organiserList[event.organiserId]" v-bind:appointment="appointmentList[event.id]"></calendarEvent>
      <td class="border border-dark" v-else-if="!event"></td>

    </template>
    <td class="text-center"><b>{{ time }}</b></td>
  </tr>
</template>

<script>
import calendarEvent from './CalendarEvent.vue';
export default {
    components: { calendarEvent
    },
    props: [ 'eventList', 'time', 'organiserList', 'appointmentList' ],
    created(){
        console.log(this.appointmentList)
    }
};
</script>

