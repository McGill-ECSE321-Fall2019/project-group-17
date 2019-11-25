
import axios from 'axios'
import { runInThisContext } from 'vm'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PersonDto(first,last,username,email,
    password,sexe,age,personType,) {
    this.first = first
    this.last = last
    this.username = username
    this.email = email
    this.password = password
    this.sexe = sexe
    this.age = age
    this.personType = personType
}

export default {
    name: 'app',
    data() {
        return {
            people: [],
            username: '',
            password: '',
            errorMessage: '',
            response: []
        }
    },
    created: function () {

          // Initializing people from backend
          AXIOS.get(`/persons`)
          .then(response => {
            // JSON responses are automatically parsed.
            this.people = response.data

            // If a user is already logged in, redirect them to their user page
            var currently_logged_in = this.$parent.logged_in_tutor
            if(currently_logged_in != "") {
              this.$router.push("./tutorView")
            }
          })
          .catch(e => {
            this.errorMessage = e;
          });
      },

    methods: {

        login: function(username,password){

            if(username == "" || password == "") {
              this.errorMessage = 'Missing input fields.'
              return false
            } else {

                AXIOS.get(backendUrl+'/persons/getByUsername/?username='+username)

                .then(response => {
                    // JSON responses are automatically parsed.
                    this.people = response.data
                    this.username = ''
                    this.password = ''
                    this.errorMessage = ''

                    // Set logged in tutor to the username specified
                    
                    if (password == response.data.password){
                        this.$parent.logged_in_tutor = username
                        this.$router.push("./tutorView")
                    }else{
                        this.errorMessage = "Password is incorrect"
                    }
                    

                  })
                  .catch(e => {
                    // Set logged in tutor to empty
                    this.$parent.logged_in_tutor = ""
                    this.errorMessage = e.response.data.message

                });

            }

        }
    }

}
