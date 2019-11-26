
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
    password,sexe,age,personType) {
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
            first: '',
            last: '',
            username: '',
            email: '',
            password: '',
            password_confirm: '',
            errorPerson: '',
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
            this.errorPerson = e.response.data.message
          });
      },

    methods: {
        createPerson: function (first,last,username,email,password,password_confirm) {
            if(first == "" || last == "" || username == "" || email == "" || password == "" || password_confirm == "") {
              this.errorPerson = 'Missing input fields.'
              return false
            } else if(password != password_confirm) {
              this.errorPerson = 'Passwords do not match.'
              return false
            } else {

                AXIOS.post(backendUrl+`/persons/createPerson/?firstName=`+first+'&lastName='+last
                +'&username='+username+'&email='+email+'&password='+password+'&personType=Tutor', {}, {})
                .then(response => {
                  // JSON responses are automatically parsed.
                  this.people.push(response.data)
                  this.first =  ''
                  this.last = ''
                  this.username = ''
                  this.email = ''
                  this.password = ''
                  this.password_confirm = ''
                  this.errorPerson = ''

                  // The user has been created successfully
                  // Set logged in tutor to the username specified
                  this.$parent.logged_in_tutor = username
                  this.$router.push("./tutorView")

                })
                .catch(e => {
                  this.errorPerson = e.response.data.message
                });
            }
        },
    }
}
