<template>

  <div id="createCourse" class="container">
    <div class="inner_container shadow">

    <div class="large_text">Create a course</div>

      <div class="input_container">

        <input type="text" class="input wide" placeholder="Course ID (ECSE321)" maxlength="10">

        <input type="text" class="input wide" placeholder="Course Name (Intro to the Engineering Profession)">

        <hr>

        <select class="input half">
          <option value="UNIVERSITY" selected> University</option>
          <option value="CEGEP"> CEGEP</option>
          <option value="HIGHSCHOOL"> High school</option>
        </select>

        <select class="input half">
          <option value=""> Select subject (optional)</option>
          <option value="Science"> Science</option>
          <option value="Math"> Math</option>
          <option value="Linguistics"> Linguistics</option>
          <option value="Arts"> Arts</option>
          <option value="Music"> Music</option>
          <option value="Religion"> Religion</option>
          <option value="History"> History</option>
          <option value="Sociology"> Sociology</option>
          <option value="Economics"> Economics</option>
          <option value="Finance"> Finance</option>
        </select>

        <hr>



        <button class="button brand_color">Create Course</button>

      </div>

    </div>
  </div>

</template>

<script>
export default {
  name: 'CreateCourse'
}
</script>

<style scoped>


</style>
