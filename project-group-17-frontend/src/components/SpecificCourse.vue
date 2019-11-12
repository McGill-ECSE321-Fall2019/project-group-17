<template>

  <div id="specificCourse" class="container">
    <div class="inner_container shadow">

    <div class="large_text">Become a tutor for a course.</div>
    <hr>

    <!--
    <table id="courses" class="table">
      <th>CourseID</th>
      <th>Course Name</th>
      <th>Level</th>
      <th>Subject</th>
      <tr v-for="course in courses">
        <td>{{ course.courseID }}</td>
        <td>{{ course.courseName }}</td>
        <td>{{ course.level }}</td>
        <td>{{ course.subject }}</td>
      </tr>
    </table>
    -->

      <div class="input_container">

        <div class="small_text left_text brand_color_text">Choose course:</div>
        <select v-model="courseID" class="input wide">
          <option value="" selected disabled>Select a course</option>
          <option v-for="course in courses" :value="course.courseID">{{ course.courseID }} - {{ course.courseName }} - {{ course.level }}</option>
        </select>

        <div class="small_text left_text brand_color_text">Your hourly rate:</div>
        <input type="number" v-model="hourlyRate" class="input wide" placeholder="Hourly Rate (eg: 15.75)" maxlength="5">

        <hr>

        <span v-if="errorSpecificCourse" style="color:red">{{ errorSpecificCourse }} </span>

        <button @click="createSpecificCourse(courseID, hourlyRate)" class="button brand_color wide">Apply</button>

      </div>

      <hr>
      <div class="medium_text">Your courses:</div>
      <table class="table shadow">
        <th>CourseID</th>
        <th>Hourly Rate</th>
        <th>Edit</th>
        <tr v-for="specificCourse in specificCourses">
          <td>{{ specificCourse.courseID }}</td>
          <td>{{ (specificCourse.hourlyRate).toFixed(2) }}</td>
          <td><button @click="deleteSpecificCourse(specificCourse.specificCourseID)" class="button_small button_red wide">Delete</button></td>
        </tr>
      </table>

    </div>
  </div>

</template>

<script src="./specificCourse.js">

</script>

<style scoped>


</style>
