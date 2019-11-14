import axios from 'axios'
var config = require('../../config')
var listStudents = []

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
        requestedStudents: [],
        acceptedStudents: [],
        previousStudents: [],
        requestedAppointments: [],
        acceptedAppointments: [],
        previousAppointments: [],
        createDate: '',
        createStartTime: '',
        createEndTime: '',
        errorAppointment: '',
        response: [],
        paid: [],
    }
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
    getStudents: function(){
      this.students = [];
      this.paid = []
      var specificStudents = [];
      var name;
      for(var i=0; i<this.appointments.length; i++) {
        specificStudents = []
        for(var j=0; j<this.appointments[i].student.length; j++) {
          specificStudents.push(this.appointments[i].student[j].username)
        }
        this.students.push(specificStudents)
      }
    },
    makeReview: function(appointmentId) {
      this.$parent.appt_id_review = appointmentId
      this.$router.push("./review")
    }

  },

  created: function () {
    // Initializing appointments from backend
      var tutor = this.$parent.logged_in_tutor;
      var specificStudents = []
      AXIOS.get(`/appointments/tutor/?username=`+ tutor)
      .then(response => {
        // JSON responses are automatically parsed.
        this.appointments = response.data
        for (var i = 0; i < this.appointments.length; i++) {
          if (this.appointments[i].status=="REQUESTED") {
            specificStudents = []
            for(var j=0; j<this.appointments[i].student.length; j++) {
              specificStudents.push(this.appointments[i].student[j].username)
            }
          this.requestedStudents.push(specificStudents)
            this.requestedAppointments.push(this.appointments[i])
          }
        
          if (this.appointments[i].status=="ACCEPTED") {
            specificStudents = []
            for(var j=0; j<this.appointments[i].student.length; j++) {
              specificStudents.push(this.appointments[i].student[j].username)
            }
            this.acceptedStudents.push(specificStudents)
            this.acceptedAppointments.push(this.appointments[i])
          }
        
          if (this.appointments[i].status=="COMPLETED") {
            specificStudents = []
            var paid = "NO"
            for(var j=0; j<this.appointments[i].student.length; j++) {
              specificStudents.push(this.appointments[i].student[j].username)
            }
            this.previousStudents.push(specificStudents)

            this.paid.push("NO")
            this.previousAppointments.push(this.appointments[i])
         }
      
          if(this.appointments[i].status=="PAID") {
            specificStudents = []
            for(var j=0; j<this.appointments[i].student.length; j++) {
              specificStudents.push(this.appointments[i].student[j].username)
            }
            this.previousStudents.push(specificStudents)
            this.paid.push("YES")
            this.previousAppointments.push(this.appointments[i])
          }

          
        }
      }
      )
      .catch(e => {
        this.errorAppointment = e;
      });
      //this.getStudents()
  }

}
