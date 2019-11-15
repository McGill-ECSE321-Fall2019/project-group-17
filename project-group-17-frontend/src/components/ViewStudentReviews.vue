
<template>
	<div id="viewStudentReviews" class="container">
		<div class="inner_container shadow">
			<div class="input_container">
				<div class="large_text">View a students reviews</div>
				<div class="small_text left_text brand_color_text">Student's Username:</div>
					<div>
					<select v-model="studentUsername" class="input wide" @change="getReviews(studentUsername)">
						<option value="" selected disabled>Select Student</option>
						<option v-for="student in students">{{ student.username }}</option>
					</select>
					</div>
			<div>
				<div v-for="review in reviews" class="shadow review_box">
					<star-rating :inline="true" :star-size="30" :read-only="true" :rating="review.rating" active-color="#FF8C1A"></star-rating>
				
					<div class="medium_text">{{ review.reviewText }}</div>
					<div class="grey_text">{{ review.reviewer }} &middot; {{ review.createdDate }}</div>
				
				</div>
			</div>

			</div>
		</div>
	</div>
</template>

<script src='./viewStudentReviews.js'>
</script>

<style>

</style>


