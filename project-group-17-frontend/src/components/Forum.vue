
<template>
	<div id="viewStudentReviews" class="container">
		<div class="inner_container shadow">
			<div class="input_container">
				<div class="large_text">Forum</div>


                <div class="small_text left_text brand_color_text">Message:</div>
                <div>
                <b-form-textarea
                    id="textarea"
                    class="input wide"
                    type="text"
                    v-model="messageText"
                    placeholder="Write your message here..."
                    rows="6"
                    max-rows="6"
                    max-cols="10">
                </b-form-textarea>
                </div>
                <button class="button brand_color " @click="createNewMessage(messageText)">Post</button>


		
			<div>
				<div v-for="(message, i) in messages" class="shadow review_box">
					<div class="medium_text">{{ message.text }}</div>
					<div class="grey_text">Posted by: {{ message.author }}</div>
                    <input type="text" class="input wide" v-model='replyText' placeholder="Reply...">
                    <button class="button brand_color " @click="replyToMessage(replyText, i)">Reply</button>
                    <div v-for="reply in message.replies">
                        <div>{{reply.author}} - {{reply.text}}</div>
                    </div>
				</div>
			</div>

			</div>
		</div>
	</div>
</template>

<script src='./forum.js'>
</script>

<style>

</style>


