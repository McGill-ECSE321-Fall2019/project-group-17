import axios from 'axios'

var config = require('../../config')

// var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
// var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


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
        replyText: '',
        errorForum: '',

    }
  },
  created: function () {

    var currently_logged_in = this.$parent.logged_in_tutor
    if(currently_logged_in == "") {
      this.$router.push("./login")
    }
    AXIOS.get(`/messages/`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.messages = response.data
      })
      .catch(e => {
        this.errorForum = e;
      });
  },

  methods: {
    createNewMessage: function (text) {
      var currently_logged_in = this.$parent.logged_in_tutor
      // var message = new Message(text, currently_logged_in, this.messageCount)
      // this.messages.push(message)
      // this.messageCount += 1

      AXIOS.post('/messages/createMessage?author='+currently_logged_in+'&text='+text)
        .then(response => {
          this.messages.push(response.data)
          this.messages.sort(function (a,b) {   //function to sort availabilities by date
            if(a.createdDate > b.createdDate) return 1;
            if(a.createdDate < b.createdDate) return -1;
            return 0;
          });
          console.log(response.data.createdDate)
        })
        .catch(e => {
          this.errorForum = e
        })

      this.messageText = "";

    },

    replyToMessage: function (messageId, replyText) {
      var currently_logged_in = this.$parent.logged_in_tutor
      // var reply = new Reply(replyText, currently_logged_in)
      // this.messages[messageId].replies.push(reply)
      AXIOS.post('/replies/createReply?messageId='+messageId+'&author='+currently_logged_in+'&text='+replyText)
        .then(response => {
          for(var i=0; i<this.messages.length; i++) {
            if(this.messages[i].messageId == messageId) this.messages[i].replies.push(response.data)
          }
        })
        .catch(e => {
          this.errorForum = e
        })

        this.replyText = ''

    }
  }

}
