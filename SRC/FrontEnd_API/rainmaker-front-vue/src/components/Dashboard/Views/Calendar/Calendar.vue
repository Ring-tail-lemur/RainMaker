<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-10 ml-auto mr-auto">
        <div class="card card-calendar">
          <div class="card-body">
            <fullCalendar ref="calendar" :options="calendarOptions" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { INITIAL_EVENTS, createEventId } from "./event-utils";
import Swal from "sweetalert2";

  export default {
    components: {
      FullCalendar
    },
    data() {
      return {
        calendarOptions: {
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          center: "dayGridMonth,timeGridWeek,timeGridDay",
          right: "prev,next,today",
        },
        initialView: "dayGridMonth",
        initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed
        editable: true,
        selectable: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
      },
      }
    },
    methods: {
      handleWeekendsToggle() {
        this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
      },
      handleDateSelect(selectInfo) {
        // on select we show the Sweet Alert modal with an input
        const swalWithBootstrapButtons = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons
          .fire({
            title: "Create an Event",
            html: `<input type="text" id="md-input" class="form-control">`,
            showCancelButton: true,
          })
          .then(() => {
            var title = document.getElementById("md-input").value;
            let calendarApi = selectInfo.view.calendar;
            calendarApi.unselect(); // clear date selection
            if (title) {
              calendarApi.addEvent({
                id: createEventId(),
                title,
                start: selectInfo.startStr,
                end: selectInfo.endStr,
                allDay: selectInfo.allDay,
              });
            }
          });
      },
      handleEventClick(clickInfo) {
        if (
          confirm(
            `Are you sure you want to delete the event '${clickInfo.event.title}'`
          )
        ) {
          clickInfo.event.remove();
        }
      },
      handleEvents(events) {
        this.currentEvents = events;
      },
    }
  }
</script>
<style>
  #fullCalendar {
    min-height: 300px;
  }

  .el-loading-spinner .path {
    stroke: #66615B !important;
  }
</style>
