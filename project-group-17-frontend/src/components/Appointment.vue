

<template>
  <div id="appt" class="container">
    <div class="inner_container shadow">

      <div class="large_text">Your Appointments</div>
      <hr>

      <div class="medium_text left_text brand_color_text" >Requested Appointments:</div>
    <!--Requested Appoinments Table-->
    <table id="requested_appointments" class="table shadow">
    <thead>
      <tr>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Students</th>
        <th>Response</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(appt, i) in requestedAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td>{{ requestedStudents[i] }}</td>
        <td><button @click="editAppointment(appt.appointmentId, 'ACCEPTED')" class="button_small button_green wide">Accept</button>
              <button @click="editAppointment(appt.appointmentId, 'REFUSED')" class="button_small button_red wide">Refuse</button></td>
      </tr>
    </tbody>
  </table>
  <hr>
  <!--Upcoming Appoinments Table-->
      <div class="medium_text left_text brand_color_text">Upcoming Appointments:</div>

    <table id="upcoming_appointments" class="table shadow">
    <thead>
      <tr>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Students</th>
        <th>Cancel</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(appt, i) in acceptedAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td>{{ acceptedStudents[i] }}</td>
        <td><button @click="editAppointment(appt.appointmentId, 'CANCELLED')" class="button_small button_red wide">Cancel</button>
        </td>
      </tr>
    </tbody>
  </table>
  <hr>
<!--Previous Appoinments Table-->
      <div class="medium_text left_text brand_color_text">Previous Appointments:</div>

  <table id="requested_appointments" class="table shadow">
    <thead>
      <tr>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Students</th>
        <th>Paid</th>
        <th>Review</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(appt, i) in previousAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td>{{ previousStudents[i] }}</td>
        <td>{{ paid[i] }}</td>
        <td><button class="button_small brand_color wide" @click="makeReview(appt.appointmentId)">Make Review</button></td>
      </tr>
    </tbody>
  </table>
<hr>

    </div>


  </div>
</template>

<script src="./appointment.js">

}
</script>

<style scoped>


</style>
