import Datepicker from 'vuejs-datepicker'
import axios from 'axios'

function Time(time) {
  var splitTime = time.split(":");
    this.hour = splitTime[0];
    this.minutes = splitTime[1];
}

function AvailabilityDto (name) {
  this.name = name
  this.events = []
}


var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
  name: 'availability',
  data() {
    return {
        availabilities: [],
        createDate: '',
        createStartTime: '',
        createEndTime: '',
        errorAvailability: '',
        response: []
    }
  },
  components: {
    Datepicker
  },
  created: function () {

    var currently_logged_in = this.$parent.logged_in_tutor
    if(currently_logged_in == "") {
      this.$router.push("./login")
    }

    // Initializing availabilities from backend
      AXIOS.get(`/availabilities/`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.people = response.data
        this.availabilities = response.data
      })
      .catch(e => {
        this.errorAvailability = e;
      });
  },

  methods: {
    createAvailability: function (createDate, createStartTime, createEndTime) {
      console.log(createDate)
      var date = new Date(createDate);
      console.log(date.getTime() +date.getTimezoneOffset())
      var startTime = new Time(createStartTime);
      var currentDate = new Date().getTime();
      var endTime = new Time(createEndTime);
      var realStartTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), startTime.hour, startTime.minutes, 0, 0).getTime()-18000*1000;
      var realEndTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), endTime.hour, endTime.minutes, 0, 0).getTime()-18000*1000;
      //var tutor = "timtom67";

      // Below we use the logged in tutor
      var tutor = this.$parent.logged_in_tutor

      console.log(realStartTime, realEndTime);
      AXIOS.post(backendUrl+'/availabilities/createAvailability?tutorUsername='+tutor+'&date='+(date.getTime()-18000*1000)+'&createdDate='+currentDate
            +'&startTime='+realStartTime+'&endTime='+realEndTime, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              this.availabilities.push(response.data)
              this.availabilities.sort(function (a,b) {   //function to sort availabilities by date
                if(a.date > b.date) return 1;
                if(a.date < b.date) return -1;
                return 0;
              });
              this.errorAvailability = ''

            })
            .catch(e => {
              this.errorAvailability = e.response.data.message
            });
    },

    deleteAvailability: function (availId) {

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
              this.errorAvailability = e.response.data.message
            });
    }
  }

}
