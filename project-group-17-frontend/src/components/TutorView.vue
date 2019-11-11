<template>
  <div id="main">
    <h1>Tutor Homepage</h1>
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


<script>
</script>



<style scoped>

</style>
