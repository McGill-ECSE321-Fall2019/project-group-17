
<template>
	<div id="viewStudentReviews" class="container">
		<div class="inner_container shadow">
			<div class="input_container">
				<div class="large_text">Question Forum</div>


                <div class="medium_text left_text brand_color_text">Post New Message:</div>
                <div>
                <b-form-textarea
                    v-bind:key ="`reply-${messageCount}`"
                    class="input wide"
                    type="text"
                    v-model="messageText"
                    placeholder="Write your message here..."
                    rows="6"
                    max-rows="6"
                    max-cols="10">
                </b-form-textarea>
                </div>
                <button class="button brand_color wide" @click="createNewMessage(messageText)">Post New Message</button>



			<div>
				<div v-for="message in messages" class="shadow review_box">
					<div class="medium_text">{{ message.text }}</div>
					<div class="grey_text">Posted by: {{ message.author }} - {{message.createdDate }}</div>
                    <input type="text" class="input half" v-model='replyText' placeholder="Reply...">
                    <button class="button_small brand_color" @click="replyToMessage(message.messageId, replyText)">Reply</button>
                    <div class="small_text left_text brand_color_text" v-if="message.replies.length > 0">Replies:</div>
                    <div class="review_box" v-for="reply in message.replies">
                        <div>{{reply.author}} : <b>{{reply.text}}</b></div>
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
