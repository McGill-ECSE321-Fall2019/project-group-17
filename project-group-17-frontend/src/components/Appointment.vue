

<template>
  <div id="avail" class="container">
    <div class="inner_container shadow">

      <div class="large_text left_text">Define your availabilities.</div>
      <hr>
      <div class="small_text left_text brand_color_text">Date:</div>
      <datepicker name="createDatePicker" v-model="createDate" placeholder="Select date"></datepicker>
      <div class="small_text left_text brand_color_text">Start Time:</div>
      
  <hr>

  <div class="medium_text left_text">Your availabilities:</div>
  <table class="table shadow">
  <thead>
    <tr>
      <th>Date</th>
      <th>Start Time</th>
      <th>End Time</th>
      <th>Edit</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="appt in availabilities">
      <td>{{ appt.date }}</td>
      <td>{{ appt.startTime }}</td>
      <td>{{ appt.endTime }}</td>
      <td><button @click="editAppintment(appt.appointmentId, "REFUSED")" class="button_small button_red wide">Accept</button>
            <button @click="editAppointment(avail.appointmentId, "REFUSED")" class="button_small button_red wide">Refuse</button></td>
    </tr>
  </tbody>
</table>

<table class="table shadow">
  <thead>
    <tr>
      <th>Date</th>
      <th>Start Time</th>
      <th>End Time</th>
      <th>Edit</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="appt in availabilities">
      <td>{{ appt.date }}</td>
      <td>{{ appt.startTime }}</td>
      <td>{{ appt.endTime }}</td>
      <td><button @click="editAppintment(appt.appointmentId, "CANCELLED")" class="button_small button_red wide">Cancel</button></td>
    </tr>
  </tbody>
</table>

    </div>


  </div>
</template>

<script src="./appointment.js">
export default {
  el: '#appt',
  name: 'Appointment',
  data() {
    return {
        appointments: [],
        errorAppointment: '',
        response: [],
        createDate: '',
        createStartTime: '',
        createEndTime: '',

    }
},

}
</script>

<style scoped>


</style>
