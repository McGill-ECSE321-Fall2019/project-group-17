/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */

import Datepicker from 'vuejs-datepicker'

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

}
// methods {
//   createAvailability: function (createDate, createStartTime, createEndTime) {
//     var date = new Date(createDate);
//     var startTime = new Time(createStartTime);
//     var endTime = new Time(createEndTime);
//
//     console.log(date);
//     console.log(startTime);
//     console.log(endTime);
//     AXIOS.post(`/createAvailability/`+, {}, {})
//     .then(response => {
//       // JSON responses are automatically parsed.
//       this.people.push(response.data)
//       this.newPerson = ''
//       this.errorPerson = ''
//     })
//     .catch(e => {
//       var errorMsg = e.message
//       console.log(errorMsg)
//       this.errorPerson = errorMsg
//     });
//   }
// }
