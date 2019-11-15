<template>
  <div id="tutorView" class="container">

    <div class="inner_container shadow">

      <div class="profile_info">
        <table class="profile_info_table">
        <tr>
          <td><div class="img_container"></div></td>
          <td>
            <div class="large_text">{{ this.firstName }} {{ this.lastName }}</div>
            <div class="small_text">{{ this.$parent.logged_in_tutor }}</div>
            <div class="small_text">{{ this.email }}</div>
          </td>
          <td><div class="img_container" style="opacity:0;"></div></td>
        </tr>
        </table>


      </div>

      <hr>
      <table>
        <tr>
          <td><button class="button_small brand_color"><router-link to="/specificCourse" class="styled_router_link">View Courses</router-link></button></td>
          <td>&nbsp;</td>
          <td><button  class="button_small brand_color"><router-link to="/availability" class="styled_router_link">Modify Availabilities</router-link></button></td>
          <td>&nbsp;</td>
          <td><button  class="button_small brand_color"><router-link to="/viewStudentReviews" class="styled_router_link">See Student Reviews</router-link></button></td>
          <td>&nbsp;</td>
          <td><button  class="button_small brand_color"><router-link to="/appointment" class="styled_router_link">Manage Appointments</router-link></button></td>
        </tr>
      </table>
      <hr>

    </div>



  </div>
</template>


<script src='./tutorView.js'>
</script>



<style scoped>

</style>
