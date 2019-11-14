import axios from 'axios'
import { runInThisContext } from 'vm'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'app',
    data() {
        return {
            errorTutorView: '',
            response: []
        }
    },
    created: function () {
      // If no user logged in, then should not be here, redicrect to Login vue
      var currently_logged_in = this.$parent.logged_in_tutor
      if(currently_logged_in == "") {
        this.$router.push("./login")
      }
    },

    methods: {
      // methods here
    }
}
