<template>

    <div id="review" class="container">
      <div class="inner_container shadow">

      <div class="large_text left_text">Create a review.</div>

      <hr> 

      <div class="small_text left_text brand_color_text">Select the student under review</div>
      <div>
        <select  v-model="studentUsername" class="input half">
          <option value="" selected disabled>Select student</option>
          <option value=student.username v-for="student in students">{{ student.firstName }}</option>
        </select>
      </div>

      <hr>

      <div class="small_text left_text brand_color_text">Please share your tutoring experience:</div>

      <b-form-textarea
        id="textarea"
        class="input wide"
        type="text"
        v-model="reviewText"
        placeholder="Write your review here..."
        rows="6"
        max-rows="6"
        max-cols="10"
      ></b-form-textarea>

      <hr>

      <div class="small_text left_text brand_color_text">Please rate your tutoring experience:</div>

      <div id="rating">
        <star-rating :increment="0.5" v-model="rating" active-color="#FF8C1A"></star-rating>
      </div>

      <button class="button_small brand_color right" @click="">Create</button>



    </div>

  </div>
</template>

<script src="./review.js">

</script>

<style scoped>


</style>
