/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */


import Datepicker from 'vuejs-datepicker'
import { networkInterfaces } from 'os';

function Time(time) {
  var splitTime = time.split(":");
    this.hour = splitTime[0];
    this.minutes = splitTime[1];
}

function AvailabilityDto (name) {
  this.name = name
  this.events = []
}
import axios from 'axios'
import { runInThisContext } from 'vm'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
     
export default {
  name: 'availability',
  data() {
    return {
        availabilities: [],
        errorAvailability: '',
        response: []
    }
},
  components: {
    Datepicker
    
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
    createAvailability: function (createDate, createStartTime, createEndTime) {
      var date = new Date(createDate);
      var startTime = new Time(createStartTime);
      var currentDate = new Date().getTime();
      var endTime = new Time(createEndTime);
      var realStartTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), startTime.hour, startTime.minutes, 0, 0).getTime();
      var realEndTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), endTime.hour, endTime.minutes, 0, 0).getTime();
      var tutor = "timtom67";
      console.log(realStartTime, realEndTime);
      AXIOS.post(backendUrl+'/availabilities/createAvailability?tutorUsername='+tutor+'&date='+date.getTime()+'&createdDate='+currentDate
            +'&startTime='+realStartTime+'&endTime='+realEndTime, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              this.availabilities.push(response.data)
              this.availabilities.sort(function (a,b) {
                if(a.date > b.date) return 1;
                if(a.date < b.date) return -1;
                return 0;
              });
              this.errorPerson = ''
              
            })
            .catch(e => {
              console.log(e.message)
              this.errorPerson = e.response
            });
    },

    // removeAvailability: function (availability)
  }
  
}


