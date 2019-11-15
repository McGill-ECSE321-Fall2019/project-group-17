
<template>
	<div id="viewStudentReviews" class="container">
		<div class="inner_container shadow">
			<div class="input_container">
				<div class="large_text">View a students reviews</div>
				<div class="small_text left_text brand_color_text">Student's Name:</div>
					<div>
					<select v-model="username" class="input wide">
						<option value="" selected disabled>Hopefully a list of students below</option>
						<option v-for"users in students">{{users}}</option>
					</select>
					</div>
									
					
					
					<!-- <table id="requested_appointments" class="table shadow">
						<thead>
							<tr>
								<th>Reviewer</th>
								<th>Time Created</th>
								<th>Date Created</th>
								<th>Appointment</th>
								<th>Review Text</th>
								<th>Rating</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>{{ rev.reviewee }}</td>
								<td>{{ rev.createdTime }}</td>
								<td>{{ rev.createdDate }}</td>
								<td>{{ rev.appointment }}</td>
								<td>{{ rev.reviewText }}</td>
								<td>{{ rev.rating }}</td>
							</tr>
						</tbody>
					</table> -->
				<button class="button brand_color wide" @click("getReviews(username)")>See Reviews</button>

			</div>
		</div>
	</div>
</template>

<script>
</script>

<style>

</style>


