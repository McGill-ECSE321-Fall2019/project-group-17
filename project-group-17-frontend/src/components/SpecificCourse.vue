<template>

  <div id="specificCourse" class="container">
    <div class="inner_container shadow">

    <div class="large_text">Become a tutor for a course.</div>

    <table>
      <tr v-for="course in courses">
        <td>{{ course.courseID }}</td>
        <td>{{ course.courseName }}</td>
        <td>{{ course.level }}</td>
        <td>{{ course.subject }}</td>
      </tr>

    </table>


      <div class="input_container">

      <!--
        <select v-model="courseID" class="input wide">
          <option value="" selected disabled>Select course</option>
          <option v-for="course in course" value="course.courseID">{{ course.courseID }} - {{ course.courseName }}</option>
        </select>
      -->

        <input type="text" v-model="tutorUsername" class="input wide" placeholder="Your username" maxlength="10">

        <input type="text" v-model="courseID" class="input half" placeholder="Course ID (ECSE321)" maxlength="10">

        <input type="number" v-model="hourlyRate" class="input half" placeholder="Hourly Rate (eg: 15.75)" maxlength="5">

        <hr>

        <span v-if="errorSpecificCourse" style="color:red">Error: {{errorSpecificCourse}} </span>

        <button v-on:click="createSpecificCourse()" class="button brand_color">Apply</button>

      </div>

    </div>
  </div>

</template>

<script src="./specificCourse.js"></script>
<script>

export default {
  name: 'SpecificCourse',
  data () {
    return {
      courses: [],

      courseID: '',
      hourlyRate: '',
      tutorUsername: '',
      errorSpecificCourse: '',
      errorSpecificCourse: '',
      response: []
    }
  }
}


</script>

<style scoped>


</style>
