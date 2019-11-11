import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

console.log(frontendUrl);
console.log(backendUrl);

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

created: function() {
  // Initializing courses from backend
    AXIOS.get(`/courses`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.courses = response.data
    })
    .catch(e => {
      this.errorCourse = e;
    });

}

methods: {

  createSpecificCourse: function (courseID, hourlyRate, tutorUsername) {
    var c = new SpecificCourseDto(courseID, hourlyRate, tutorUsername);
    this.courses.push(c);
    this.hourlyRate = '';
    this.courseID = '';
    this.tutorUsername = '';
  }

}
