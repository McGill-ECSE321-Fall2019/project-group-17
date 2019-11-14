

<template>
  <div id="appt" class="container">
    <div class="inner_container shadow">

      <div class="large_text">Your Appointments</div>
      <hr>

      <div class="medium_text left_text brand_color_text" >Requested Appointments with you:</div>
    <!--Requested Appoinments Table-->
    <table id="requested_appointments" class="table shadow">
    <thead>
      <tr>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Student(s)</th>
        <th>Response</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="appt in requestedAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td><button@click="getStudents(appt.appointmentId)">See students</button>{{ students }}</td>
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
        <th>Student</th>
        <th>Cancel</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="appt in acceptedAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td><button@click="getStudents(appt.appointmentId)">See students</button>{{ students }}</td>
        <td><button @click="editAppointment(appt.appointmentId, 'CANCELLED')" class="button_small button_red wide">Cancel</button>
        </td>
      </tr>
    </tbody>
  </table>
  <hr>
<!--Previous Appoinments Table-->
      <div class="medium_text left_text brand_color_text">Your Previous Appointments:</div>

  <table id="requested_appointments" class="table shadow">
    <thead>
      <tr>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Student</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="appt in previousAppointments">
        <td>{{ appt.date }}</td>
        <td>{{ appt.startTime }}</td>
        <td>{{ appt.endTime }}</td>
        <td><button@click="getStudents(appt.appointmentId)">Students</button>{{ this.students }}</td>
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
