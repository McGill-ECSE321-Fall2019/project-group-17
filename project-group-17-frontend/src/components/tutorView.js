import axios from 'axios'
import { runInThisContext } from 'vm'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'app',
    data() {
        return {
            errorTutorView: '',
            email: '',
            firstName: '',
            lastName: '',
            personType: '',
            response: []
        }
    },
    created: function() {

      // If no user logged in, then should not be here, redicrect to Login vue
      var currently_logged_in = this.$parent.logged_in_tutor
      if(currently_logged_in == "") {
        this.$router.push("./login")
      }

      // Initializing courses from backend
        AXIOS.get(`/persons/getByUsername?username=`+currently_logged_in)
        .then(response => {
          // JSON responses are automatically parsed.
          var person = response.data
          this.email = person.email
          this.firstName = person.firstName
          this.lastName = person.lastName
          this.personType = person.personType
        })
        .catch(e => {
          this.errorCourse = e.reponse.data.message
        })

        AXIOS.get(`/specificCourses/tutor?username=felixsimard`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.specificCourses = response.data
        })
        .catch(e => {
          this.errorSpecificCourse = e.reponse.data.message
        })

    },

    methods: {
      // methods here
    }
}
