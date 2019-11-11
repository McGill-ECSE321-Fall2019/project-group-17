
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


function CourseDto(courseID, courseName, level, subject) {
  this.courseID = courseID;
  this.coursename = courseName;
  this.level = level;
  this.subjects = subjects;
  this.specificCourses = [];
}

function SpecificCourseDto(courseID, hourlyRate, tutorUsername) {
  this.courseID = courseID;
  this.hourlyRate = hourlyRate;
  this.tutorUsername = tutorUsername;
}

export default {
  name: 'sCourse',
  data() {
    return {
      courses: [],
      courseName: '',
      courseID: '',
      level: '',
      subject: '',

      specificCourses: [],
      hourlyRate: '',
      errorSpecificCourse: '',
      errorCourse: '',
      response: []
    }
  },
  created: function() {
    // Initializing courses from backend
      AXIOS.get(`/courses`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.courses = response.data
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

    createSpecificCourse: function (courseID, hourlyRate) {
      // CREATE a Specific Course
      if(hourlyRate < 12.50) {
        this.errorSpecificCourse = 'The minimum wage in the Province of Quebec is 12.50$'
        return false
      } else {
        //var tutor = "felixsimard"

        // Below we use the logged in tutor
        var tutor = this.$parent.logged_in_tutor

        AXIOS.post(backendUrl+'/specificCourses/create?hourlyRate='+hourlyRate+'&tutorUsername='+tutor+'&courseID='+courseID, {}, {})
        .then(response => {

          this.errorSpecificCourse = ''
          this.specificCourses.push(response.data);
          this.hourlyRate = '';
          this.courseID = '';

        })
        .catch(e => {
          this.errorSpecificCourse = e.response.data.message
        })

      }


    },

    deleteSpecificCourse: function (scID) {
      // DELETE a Specific Course by ID
      AXIOS.post(backendUrl+'/specificCourses/delete?specificCourseID='+scID, {}, {})
            .then(response => {
              // JSON responses are automatically parsed.
              for (var i = 0; i < this.specificCourses.length; i++) {
                if (this.specificCourses[i].specificCourseID==scID) this.specificCourses.splice(i, 1);
              }
              this.errorSpecificCourse = ''

            })
            .catch(e => {
              this.errorSpecificCourse = e.response.data.message
            });
    },

  } // end of methods

} // end of export default
