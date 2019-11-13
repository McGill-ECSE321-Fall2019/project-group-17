import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
  name: 'appointment',
  data() {
    return {
        requestedAppointments: [],
        acceptedAppointments: [],
        createDate: '',
        createStartTime: '',
        createEndTime: '',
        errorAppointment: '',
        response: []
    }
  },
  created: function () {
    // Initializing appointments from backend
      AXIOS.get(`/appointment`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.people = response.data
        this.appointments = response.data
      })
      .catch(e => {
        this.errorAppointment = e;
      });
  },

  methods: {
    editAppointment: function (appointmentId, status) {

      // Below we use the logged in tutor
      var tutor = this.$parent.logged_in_tutor

      console.log(realStartTime, realEndTime);
      AXIOS.post(backendUrl+'/appointments/deleteById?AppointmentId='+availId, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              if(status == "CANCELLED"){
                for (var i = 0; i < this.appointments.length; i++) {
                    if (this.appointments[i].AppointmentId==availId) this.appointments.splice(i, 1);
                }
              }
              this.appointments.sort(function (a,b) {
                if(a.date > b.date) return 1;
                if(a.date < b.date) return -1;
                return 0;
              });
              this.errorAppointment = ''

            })
            .catch(e => {
              this.errorAppointment = e.response.data.message
            });
    }
  }

}
