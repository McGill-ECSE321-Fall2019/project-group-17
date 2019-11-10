<template>
  <div id="main">
    <h>Tutor Homepage</h>
    <h2>People</h2>
    <table>
    <tr v-for="person in people" >
        <td>{{ "Name: " + person.name }}</td>

    </tr>
    <tr v-for="person in people" >
        <td>{{ "Username: " + person.username }}</td>
        <td>{{ "Password: " + person.password }}</td>

    </tr>
    </table>
  </div>
</template>


<!-- Link Java Script -->
<script>
</script>


<!-- CSS code -->
<style>
  #main {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  td{
      
  }
</style>