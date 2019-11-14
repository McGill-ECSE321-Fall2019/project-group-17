<template>
  <div id="tutorView" class="container">

    <div class="inner_container shadow">

      <div class="large_text">Hey, {{ this.$parent.logged_in_tutor }}</div>

      <hr>
      <table>
        <tr>
          <td><button class="button_small brand_color"><router-link to="/specificCourse" class="styled_router_link">View Courses</router-link></button></td>
          <td>&nbsp;</td>
          <td><button  class="button_small brand_color"><router-link to="/availability" class="styled_router_link">Modify Availabilities</router-link></button></td>
          <td>&nbsp;</td>
          <td><button  class="button_small brand_color"><router-link to="/review" class="styled_router_link">See Reviews</router-link></button></td>
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
