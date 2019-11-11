<template>
  <div id="login" class="container">
    <div class="inner_container shadow">

    <!--<div class="img_container"></div>-->
    <div class="large_text center_text">Log into your account</div>

      <div class="input_container">
        <input type="text" class="input wide" v-model='username' placeholder="Username">
        <input type="password" class="input wide" v-model='password' placeholder="Password">

        <button class="button brand_color" @click="login(username,password)">Log In</button>
      </div>

      <div class="small_text center_text">Don't have an account?</div>
      <div class="small_text center_text brand_color_text"> <router-link to="/signup">Signup</router-link> 
          <p>
            <span v-if="errorPerson" style="color:red">{{errorPerson}} </span>
          </p>
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
