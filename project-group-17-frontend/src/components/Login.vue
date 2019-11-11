<template>
  <div id="login" class="container">
    <div class="inner_container shadow">

    <!--<div class="img_container"></div>-->
    <div class="large_text">Log into your account</div>

      <div class="input_container">
        <div class="small_text left_text brand_color_text">Username:</div>
        <input type="text" class="input wide" v-model='username' placeholder="Username">
        <div class="small_text left_text brand_color_text">Password:</div>
        <input type="password" class="input wide" v-model='password' placeholder="Password">

        <span v-if="errorPerson" style="color:red">{{errorPerson}} </span>
        <button class="button brand_color wide" @click="login(username,password)">Log In</button>
      </div>

      <div class="small_text center_text">Don't have an account?</div>
      <div class="small_text center_text brand_color_text"> <router-link to="/signup">Signup</router-link>

      </div>
    </div>

  </div>
</template>

<script src='./login.js'>
export default {
  name: 'Login'
}
</script>

<style>

</style>
