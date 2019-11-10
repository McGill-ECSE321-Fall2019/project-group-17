
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
            newPerson: '',
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
        createPerson: function (first,last,username,email,password) {

            AXIOS.post(backendUrl+`/persons/createPerson/?firstName=`+first+'&lastName='+last
            +'&username='+username+'&email='+email+'&password='+password+'&personType=Tutor', {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              this.people.push(response.data)
              this.newPerson = ''
              this.errorPerson = ''
              
            })
            .catch(e => {
              console.log(e.message)
              this.errorPerson = e.response
            });
        },
            
        
    }

}

