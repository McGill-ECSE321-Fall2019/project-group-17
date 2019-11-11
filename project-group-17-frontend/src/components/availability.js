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
    // Initializing availabilities from backend
      AXIOS.get(`/availabilities`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.availabilities = response.data
        
      })
      .catch(e => {
        this.errorAvailability = e;
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
              this.errorAvailability = ''
              
            })
            .catch(e => {
              console.log(e.message)
              this.errorAvailability = e.response
            });
    },

    deleteAvailability: function (availId) {
      console.log(availId);
      AXIOS.post(backendUrl+'/availabilities/deleteById?availabilityId='+availId, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              for (var i = 0; i < this.availabilities.length; i++) {
                if (this.availabilities[i].availabilityId==availId) this.availabilities.splice(i, 1); 
              }
              this.availabilities.sort(function (a,b) {
                if(a.date > b.date) return 1;
                if(a.date < b.date) return -1;
                return 0;
              });
              this.errorAvailability = ''
              
            })
            .catch(e => {
              console.log(e.message)
              this.errorAvailability = e.response
            });
    }
  }
  
}


