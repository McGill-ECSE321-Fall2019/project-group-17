

<template>
  <div id="avail">
    <div class="inner_container shadow">

      <div class="large_text left_text">Create Availability</div>
      <div class="small_text left_text brand_color_text">Date: </div>
      <datepicker name="createDatePicker" v-model="createDate" font-family="Helvetica" placeholder="Click to select date" :highlighted="highlighted"></datepicker>
      <div class="small_text left_text brand_color_text">Start Time: </div>

      <div>
      <select v-model="createStartTime" class="input half">
        <option value="08:00">08:00 AM</option>
        <option value="08:30">08:30 AM</option>
        <option value="09:00">09:00 AM</option>
        <option value="09:30">09:30 AM</option>
        <option value="10:00">10:00 AM</option>
        <option value="10:30">10:30 AM</option>
        <option value="11:00">11:00 AM</option>
        <option value="11:30">11:30 AM</option>
        <option value="12:00">12:00 PM</option>
        <option value="12:30">12:30 PM</option>
        <option value="13:00">01:00 PM</option>
        <option value="13:30">01:30 PM</option>
        <option value="14:00">02:00 PM</option>
        <option value="14:30">02:30 PM</option>
        <option value="15:00">03:00 PM</option>
        <option value="15:30">03:30 PM</option>
        <option value="16:00">04:00 PM</option>
        <option value="16:30">04:30 PM</option>
        <option value="17:00">05:00 PM</option>
        <option value="17:30">05:30 PM</option>
        <option value="18:00">06:00 PM</option>
        <option value="18:30">06:30 PM</option>
        <option value="19:00">07:00 PM</option>
        <option value="19:30">07:30 PM</option>
        <option value="20:00">08:00 PM</option>
        <option value="20:30">08:30 PM</option>
    </select>
    </div>
      <div class="small_text left_text brand_color_text">End Time: </div>

  <div>
  <select v-model="createEndTime" class="input half">
    <option value="08:30">08:30 AM</option>
    <option value="09:00">09:00 AM</option>
    <option value="09:30">09:30 AM</option>
    <option value="10:00">10:00 AM</option>
    <option value="10:30">10:30 AM</option>
    <option value="11:00">11:00 AM</option>
    <option value="11:30">11:30 AM</option>
    <option value="12:00">12:00 PM</option>
    <option value="12:30">12:30 PM</option>
    <option value="13:00">01:00 PM</option>
    <option value="13:30">01:30 PM</option>
    <option value="14:00">02:00 PM</option>
    <option value="14:30">02:30 PM</option>
    <option value="15:00">03:00 PM</option>
    <option value="15:30">03:30 PM</option>
    <option value="16:00">04:00 PM</option>
    <option value="16:30">04:30 PM</option>
    <option value="17:00">05:00 PM</option>
    <option value="17:30">05:30 PM</option>
    <option value="18:00">06:00 PM</option>
    <option value="18:30">06:30 PM</option>
    <option value="19:00">07:00 PM</option>
    <option value="19:30">07:30 PM</option>
    <option value="20:00">08:00 PM</option>
    <option value="20:30">08:30 PM</option>
    <option value="21:00">09:00 PM</option>
    </select>
    </div>
  <button @click="createAvailability(createDate, createStartTime, createEndTime)" class="button brand_color">Create</button>



    <div class="medium_text left_text">Remove Availability</div>
    <div class="small_text left_text brand_color_text">Choose Date: </div>
    <datepicker font-family="Helvetica" placeholder="Click to select date" :highlighted="highlighted"></datepicker>
    <div class="small_text left_text brand_color_text">Start Time: </div>

    <table id="firstTable">
  <thead>
    <tr>
      <th>Date</th>
      <th>Start Time</th>
      <th>End Time</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="avail in availabilities">
      <td>{{avail.date}}</td>
      <td>{{avail.startTime}}</td>
      <td>{{avail.endTime}}</td>
    </tr>
  </tbody>
</table>

    <button class="button brand_color">Remove</button>
    </div>


  </div>
</template>

<script src="./availability.js">
export default {
  el: '#avail',
  name: 'Availability',
  data: {
    createDate: '',
    createStartTime: '',
    createEndTime: '',
  }

}
</script>

<style>


</style>
