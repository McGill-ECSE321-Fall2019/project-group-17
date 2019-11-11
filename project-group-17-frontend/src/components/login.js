
import axios from 'axios'
import { runInThisContext } from 'vm'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
          })
          .catch(e => {
            this.errorPerson = e;
          });
      },

    methods: {

        login: function(username,password){

            if(username == "" || password == "") {
              this.errorPerson = 'Missing input fields.'
              return false
            } else {

                AXIOS.get(backendUrl+'/persons/getByUsername/?username='+username)

                .then(response => {
                    // JSON responses are automatically parsed.
                    this.people = response.data
                    this.username = ''
                    this.password = ''
                    this.errorPerson = ''

                    // Set logged in tutor to the username specified
                    this.$parent.logged_in_tutor = username
                    //window.location = 'Main.vue';

                  })
                  .catch(e => {
                    // Set logged in tutor to empty
                    this.$parent.logged_in_tutor = ""
                    this.errorPerson = e.response.data.message

                });

            }

        }
    }

}
