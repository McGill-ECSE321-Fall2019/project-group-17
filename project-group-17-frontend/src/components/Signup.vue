<template>

  <div id="signup" class="container">
    <div class="inner_container shadow">

    <!--<div class="img_container"></div>-->
    <div class="large_text">Create an account</div>

    <div class="input_container">

      <input type="text" class="input half" v-model="first" placeholder="First name">
      <input type="text" class="input half " v-model="last" placeholder="Last name">

      <hr>

      <input type="text" class="input wide" v-model="username" placeholder="Username">
      <input type="email" class="input wide" v-model="email" placeholder="Email address">

      <hr>

      <!--
      <select class="input half"><option value="Tutor" selected>Tutor</option><option value="Student">Student</option></select>

      <select class="input half"><option value="" selected disabled>Sexe (optional)</option><option value="Male">Male</option><option value="Female">Female</option><option value="Other">Other</option></select>

      <hr>
      -->

      <input type="password" class="input wide" v-model="password" placeholder="Password">
      <input type="password" class="input wide" v-model="password_confirm" placeholder="Confirm Password">
      <hr>

      <button class="button brand_color" @click="createPerson(first,last,username,email,password)">Signup</button>
    </div>

    <div class="small_text center_text">Already have an account?</div>
    <div class="small_text center_text brand_color_text"> <router-link to="/login">Log In</router-link> 
          <p>
            <span v-if="errorPerson" style="color:red">{{errorPerson}} </span>
          </p>
    </div>

    </div>
  </div>

</template>

<script src='./signup.js'>
</script>

<style scoped>


</style>
