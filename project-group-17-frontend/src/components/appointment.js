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
        students: [],
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
    // created: function () {
    //   // Updating appointments from backend
    //     var tutor = this.$parent.logged_in_tutor;
        
    //       this.requestedAppointments = []
    //       this.acceptedAppointments = []
    //       this.previousAppointments = []
    //       for (var i = 0; i < this.appointments.length; i++) {
    //         if (this.appointments[i].status=="REQUESTED") {
    //           this.requestedAppointments.push(this.appointments[i])
    //         }
    //       }
    //       for (var i = 0; i < this.appointments.length; i++) {
    //         if (this.appointments[i].status=="ACCEPTED") this.acceptedAppointments.push(this.appointments[i])
    //       }
    //       for (var i = 0; i < this.appointments.length; i++) {
    //         if (this.appointments[i].status=="COMPLETED") this.previousAppointments.push(this.appointments[i])
    //       }
    // },
    editAppointment: function (apptId, status) {

      // Below we use the logged in tutor
      var tutor = this.$parent.logged_in_tutor

      AXIOS.post(backendUrl+'/appointments/changeStatus?appointmentId='+apptId+'&newStatus='+status, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              console.log(status)
              if(status == "REFUSED"){
                for (var i = 0; i < this.requestedAppointments.length; i++) {
                    
                    if (this.appointments[i].appointmentId==apptId) {
                      alert()
                      this.requestedAppointments.splice(i, 1);
                    }
                }
              }else if (status == "ACCEPTED") {
                for (var i = 0; i < this.requestedAppointments.length; i++) {
                  if (this.appointments[i].appointmentId==apptId){
                    this.acceptedAppointments.push(this.appointments[i]);
                    this.requestedAppointments.splice(i,1);
                  } 
                }
              } else if (status == "CANCELLED"){
                for (var i = 0; i < this.acceptedAppointments.length; i++) {
                  if (this.appointments[i].appointmentId==apptId){
                    console.log("CANCELLEDDEDD")
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
            })
            .catch(e => {
              this.errorAppointment = e.response.data.message
            });
    },
    getStudents: function(appointmentId){
      // for (var i = 0; i < this.appointments.length; i++) {
      //   if (this.appointments[i].appointmentId==appointmentId){
      //     for (var j = 0 ; this.appointments[i].student.length; j++){
      //       this.students.push(this.appointments[i].student[j]);
      //     }
      //   }
        
      // }
      var name;
      AXIOS.get(backendUrl+'/appointments/getAppointmentById?appointmentId='+appointmentId, {}, {})
            .then(response => {
              console.log(response.data);
              // JSON responses are automatically parsed.
              console.log("HERE")
              console.log(response.data.student[1].username)
              console.log(response.data.student.length)
              for(var i=0; i<response.data.student.length; i++){
                this.students.push(response.data.student[i].username);
                this.errorAppointment = '';
              
              }
              console.log("HELLO")
            })
            .catch(e => {
              this.errorAppointment = e.response.data.message
            });
    },

  }

}
