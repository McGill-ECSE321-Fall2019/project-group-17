<template>
  <div id="app">
    <div class="login_container shadow">

      <div class="img_container"><!-- <img class="img_media" src=""> --></div>
      <div class="medium_text center_text brand_color_text">Online Tutoring System</div>

      <div class="input_container">
        <input type="text" class="input" placeholder="Username">
        <input type="password" class="input" placeholder="Password">

        <button class="button brand_color">Continue</button>
      </div>

      <div class="small_text center_text">Don't have an account?</div>
      <div class="small_text center_text brand_color_text"><a href="">Create an account</a></div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 1000px;
  margin: 0px 0px;
  padding: 0px;
  background: rgba(255, 140, 26, 0.3);
  font-family: 'Verdana';
}
.brand_color {
  background: rgba(255, 140, 26, 1);
}
.login_container {
  width: 65%;
  min-height: 100px;
  overflow-y: auto;
  margin: 0px auto;
  padding: 40px 75px;
  margin-top: 5%;
  background: white;
  border-radius: 10px;
}
.input_container {
  margin: 30px auto;
}
.input {
  width: 100%;
  padding: 10px 8px;
  margin: 5px auto;
  background: rgba(240, 240, 240, 0.3);
  font-size: 18px;
  font-weight: 500;
  border-radius: 0px;
  border: none;
  border-left: 2px transparent solid;
  -webkit-transition: all 0.2s ease;
	-moz-transition: all 0.2s ease;
	transition: all 0.2s ease;
}
.input:focus {
  border-left: 2px rgba(255, 140, 26, 1) solid;
}
.button {
  width: 100%;
  padding: 15px 10px;
  border-radius: 3px;
  border: none;
  font-size: 20px;
  font-weight: 600;
  margin: 20px auto;
  color: white;
}
.button_dark {
  background: rgba(0, 0, 0, 0.7);
}
.img_container {
  background: rgba(240, 240, 240, 0.7);
  width: 100px;
  height: 100px;
  margin: 10px auto;
  border: none;
  border-radius: 50%;
  cursor: pointer;
}
.img_media {
  width: 100%;
  height: 100%;
  margin: 0px auto;
}
.brand_color_text {
  color: rgb(255, 140, 26);
}
.small_text {
  font-size: 15px;
  font-weight: 400;
}
.medium_text {
  font-size: 25px;
  font-weight: 700;
  padding: 15px 0px;
}
.center_text {
  text-align: center;
}
.shadow {
	box-shadow: 1px 2px 10px -2px rgba(0,0,0,0.2);
}
input:focus {
   outline: none;
}
a {
    text-decoration: none;
}
a:link {
    text-decoration: none;
}
a:visited {
    text-decoration: none;
}
a:hover {
    text-decoration: none;
}
a:active {
    text-decoration: none;
}
</style>
