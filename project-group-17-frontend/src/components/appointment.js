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
      
        appointments: [],
        students: '',
        requestedAppointments: [],
        acceptedAppointments: [],
        previousAppointments: [],
        createDate: '',
        createStartTime: '',
        createEndTime: '',
        errorAppointment: '',
        response: []
    }
  },
  created: function () {
    // Initializing appointments from backend
      var tutor = this.$parent.logged_in_tutor;
      AXIOS.get(`/appointments/tutor/?username=`+ tutor)
      .then(response => {
        // JSON responses are automatically parsed.
        this.appointments = response.data
        for (var i = 0; i < this.appointments.length; i++) {
          if (this.appointments[i].status=="REQUESTED") {
            this.requestedAppointments.push(this.appointments[i])
          }
        }
        for (var i = 0; i < this.appointments.length; i++) {
          if (this.appointments[i].status=="ACCEPTED") this.acceptedAppointments.push(this.appointments[i])
        }
        for (var i = 0; i < this.appointments.length; i++) {
          if (this.appointments[i].status=="COMPLETED") this.previousAppointments.push(this.appointments[i])
        }
      })
      .catch(e => {
        this.errorAppointment = e;
      });
  },

  methods: {
    refresh: function () {
      // Updating appointments from backend
        var tutor = this.$parent.logged_in_tutor;
        
          this.requestedAppointments = []
          this.acceptedAppointments = []
          this.previousAppointments = []
          for (var i = 0; i < this.appointments.length; i++) {
            if (this.appointments[i].status=="REQUESTED") {
              this.requestedAppointments.push(this.appointments[i])
            }
          }
          for (var i = 0; i < this.appointments.length; i++) {
            if (this.appointments[i].status=="ACCEPTED") this.acceptedAppointments.push(this.appointments[i])
          }
          for (var i = 0; i < this.appointments.length; i++) {
            if (this.appointments[i].status=="COMPLETED") this.previousAppointments.push(this.appointments[i])
          }
    },
    editAppointment: function (availId, status) {

      // Below we use the logged in tutor
      var tutor = this.$parent.logged_in_tutor

      AXIOS.post(backendUrl+'/appointments/changeStatus?n='+availId+'&newStatus='+status, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              if(status == "REFUSED"){
                for (var i = 0; i < this.requestedAppointments.length; i++) {
                    
                    if (this.appointments[i].appointmentId==availId) {
                      alert()
                      this.requestedAppointments.splice(i, 1);
                    }
                }
              }else if (status == "ACCEPTED") {
                for (var i = 0; i < this.requestedAppointments.length; i++) {
                  if (this.appointments[i].appointmentId==availId){
                    this.acceptedAppointments.push(this.appointments[i]);
                    this.requestedAppointments.splice(i, 1);
                  } 
                }
              } else if (status == "CANCELLED"){
                for (var i = 0; i < this.acceptedAppointments.length; i++) {
                  if (this.appointments[i].appointmentId==availId){
                    this.acceptedAppointments.splice(i, 1);
                  } 
                }
              }
              this.appointments.sort(function (a,b) {
                if(a.date > b.date) return 1;
                if(a.date < b.date) return -1;
                return 0;
              });
              this.errorAppointment = ''
              this.refresh();
            })
            .catch(e => {
              this.errorAppointment = e.response.data.message
            });
    },
    getStudentUsername: function(availId){
      this.students = "";
      for (var i = 0; i < this.appointments.length; i++) {
        if (this.appointments[i].appointmentId==availId){
          for (var j =0 ; this.appointments[i].student.length; j++){
            this.students.concat(this.students, this.appointments[i].student[j], ",")
          }
        }
        
      }
    },

  }

}
