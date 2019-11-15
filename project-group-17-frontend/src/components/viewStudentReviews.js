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
			students: []

		}
	},
	created: function(){

		AXIOS.get(backendUrl+'/persons')
      	.then(response => {
		// JSON responses are automatically parsed.
	
			this.allPeople = response.data
			for(var i=0; i< this.allPeople.length; i++){
				if(allPeople[i].personType==Student){
					students.push(allPeople[i].username)
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
			AXIOS.get(backendUrl+'/reviews/reviewsByReviewee?name_reviewer='+username)
			.then(response => {
			// JSON responses are automatically parsed.
		
				this.reviews = response.data

			})
			.catch(e => {
				var errorMsg = e.message
				console.log(errorMsg)
			});			


    }

	}
}