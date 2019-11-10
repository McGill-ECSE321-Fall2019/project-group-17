<template>
  <div id="login" class="container">
    <div class="inner_container shadow">

    <!--<div class="img_container"></div>-->
    <div class="large_text center_text">Log into your account</div>

      <div class="input_container">
        <input type="text" class="input wide" placeholder="Username">
        <input type="password" class="input wide" placeholder="Password">

        <button class="button brand_color">Log In</button>
      </div>

      <div class="small_text center_text">Don't have an account?</div>
      <div class="small_text center_text brand_color_text"> <router-link to="/signup">Signup</router-link> </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Login'
}
</script>

<style>

</style>
