<template>

    <div id="review" class="container">
      <div class="inner_container shadow">

      <div class="large_text left_text">Create a review.</div>
   
      <hr>

      <div class="small_text left_text brand_color_text">Select the student under review</div>
      <div>
        <select  v-model="name_reviewee" class="input half">
          <option value="" selected disabled>Select student</option>
          <option v-for="student in students"  :value="student.username">{{ student.username }}</option>
        </select>
      </div>

      <hr>

      <div class="small_text left_text brand_color_text">Please share your tutoring experience:</div>
      <div>
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

      <hr>
      </div>
      <button class="button brand_color wide" @click="createReview(name_reviewee, reviewText, rating)">Submit Review</button>


      <span style="color:red">{{ errorMessage }}</span>
    </div>

  </div>
</template>

<script src="./review.js">

</script>

<style scoped>


</style>
