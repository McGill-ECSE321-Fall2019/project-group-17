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

export default {
	name: 'viewStudentReviews',
	data() {
		return{
			reviews: [],
			allPeople: [],
			students: [],
			studentUsername: ''


		}
	},
	created: function(){

		var currently_logged_in = this.$parent.logged_in_tutor
    if(currently_logged_in == "") {
      this.$router.push("./login")
    }

		var allPeople
		AXIOS.get(backendUrl+'/persons')
      	.then(response => {
		// JSON responses are automatically parsed.
	
			allPeople = response.data
			for(var i=0; i< allPeople.length; i++){
				console.log(allPeople[i].username, allPeople[i].personType)
				if(allPeople[i].personType=="student"){
					console.log(allPeople[i].username)
					this.students.push(allPeople[i])
				}

			}
			
      	})
      	.catch(e => {
        	var errorMsg = e.message
			console.log(errorMsg)
      	});
	},
	methods: {
		getReviews: function(username) {
			this.reviews = []
			AXIOS.get(backendUrl+'/reviews/reviewsByReviewee?name_reviewee='+username)
			.then(response => {
			// JSON responses are automatically parsed.
		
				this.reviews = response.data
				this.reviews.sort(function (a,b) {   //function to sort availabilities by date
					console.log(a.createdDate)
					if(a.createdDate > b.createdDate) return 1;
					if(a.createdDate < b.createdDate) return -1;
					return 0;
				  });
			})
			.catch(e => {
				var errorMsg = e.message
				console.log(errorMsg)
			});			


    }

	}
}