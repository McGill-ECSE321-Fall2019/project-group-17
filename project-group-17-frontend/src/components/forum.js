import axios from 'axios'

var config = require('../../config')

// var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
// var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// var AXIOS = axios.create({
//   baseURL: backendUrl,
//   headers: { 'Access-Control-Allow-Origin': frontendUrl }
// })


function Message (text, author, id) {
    this.text = text
    this.author = author
    this.id = id
    this.replies = []
  }

function Reply (text, author) {
    this.text = text
    this.author = author
  }


export default {
  name: 'forum',
  data() {
    return {
        messages: [],
        messageCount: 0,
        messageText : '',
        replyText: ''

    }
  },
  created: function () {

    var currently_logged_in = this.$parent.logged_in_tutor
    // if(currently_logged_in == "") {
    //   this.$router.push("./login")
    // }
  },

  methods: {
    createNewMessage: function (text) {
      var currently_logged_in = this.$parent.logged_in_tutor
      var message = new Message(text, currently_logged_in, this.messageCount)
      this.messages.push(message)
      this.messageCount += 1;
      this.messageText = "";
     
    },

    replyToMessage: function (messageId, replyText) {
      var currently_logged_in = this.$parent.logged_in_tutor
      var reply = new Reply(replyText, currently_logged_in)
      this.messages[messageId].replies.push(reply)
      
    }
  }

}
