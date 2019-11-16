import axios from 'axios'
import Vue from 'vue'
import StarRating from 'vue-star-rating'

//constructors


var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

Vue.component('star-rating', StarRating);


export default {
	name: 'review',
	data() {
		return{
			reviews: [],
			reviewText: '',
			rating: 0,
			reviewee: [],
			reviewer: [],
			appointment:[],
			reviewID: '',
			createdDate: '',
			createdTime: '',
			errorReview: [],
			studentUsername: '',
			testStudents: [],
			students: []

		}
	},
	created: function(){

		var appt_id = this.$parent.appt_id_review

    // ********************************************************************
    //if(appt_id == 0) {
      //this.$router.push("./appointment")
    //}
    // ********************************************************************

		//we have the appointemnt id in app, so we can get the students from that
		AXIOS.get(backendUrl+'/persons/getStudentsByAppointmentID?appointmentID='+appt_id)
      	.then(response => {
        // JSON responses are automatically parsed.
        	this.students = response.data
        	this.errorReview = ''
      	})
      	.catch(e => {
        	var errorMsg = e.message
			    console.log(errorMsg)
			    this.errorReview = errorMsg
      	});
	},
	methods: {

    createReview : function(studentUsername, reviewText, rating){
      if(studentUsername == "" || reviewText == "" || rating == 0){
        this.errorMessage = 'Missing input fields.'
        return false
      }else{
        //we need the student username and the tutor username
        var appt_id = this.$parent.appt_id_review
        //get the appointment:
        AXIOS.get(backendUrl+'/appointments/getAppointmentById?appointmentId='+appt_id)
        .then(response => {
        // JSON responses are automatically parsed.
          this.appointment = response.data
          this.errorReview = ''
          return 0
        })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
          this.errorReview = errorMsg
        });


        var tutor = this.$parent.logged_in_tutor
        var appointmentID = this.appointment.appointmentID

        AXIOS.post(backendUrl+"/reviews/createReview?reviewText=" + reviewText +"&rating=" + rating +
            "&name_reviewee=" + studentUsername + "&name_reviewer=" + tutor + "&appontmentID=" + appointmentID)
        .then(response => {
        // JSON responses are automatically parsed.
            return 0
            })
        .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorReview = errorMsg
        });
      }
    }

	}
}
