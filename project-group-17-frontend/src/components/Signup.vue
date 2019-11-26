<template>

  <div id="signup" class="container">
    <div class="inner_container shadow">

    <!--<div class="img_container"></div>-->
    <div class="large_text center_text">Create your account.</div>

    <div class="input_container">

      <div class="small_text left_text brand_color_text">First and Last name:</div>
      <input type="text" class="input half" v-model="first" placeholder="First name">
      <input type="text" class="input half " v-model="last" placeholder="Last name">

      <hr>

      <div class="small_text left_text brand_color_text">Username:</div>
      <input type="text" class="input wide" v-model="username" placeholder="Username">
      <div class="small_text left_text brand_color_text">Email address:</div>
      <input type="email" class="input wide" v-model="email" placeholder="Email address">

      <hr>

      <!--
      <select v-model="personType" class="input half"><option value="Tutor" selected>Tutor</option><option value="Student">Student</option></select>

      <select v-model="sexe" class="input half"><option value="" selected disabled>Sexe (optional)</option><option value="Male">Male</option><option value="Female">Female</option><option value="Other">Other</option></select>

      <hr>
      -->

      <div class="small_text left_text brand_color_text">Password:</div>
      <input type="password" class="input wide" v-model="password" placeholder="Password">
      <div class="small_text left_text brand_color_text">Confirm Password:</div>
      <input type="password" class="input wide" v-model="password_confirm" placeholder="Confirm Password">
      <hr>
      <span v-if="errorPerson" style="color:red">{{errorPerson}} </span>
      <button class="button brand_color wide" @click="createPerson(first,last,username,email,password,password_confirm)">Signup</button>
    </div>

    <div class="small_text center_text">Already have an account?</div>
    <div class="small_text center_text brand_color_text"> <router-link to="/login">Log In</router-link>

    </div>

    </div>
  </div>

</template>

<script src='./signup.js'>
</script>

<style scoped>


</style>
