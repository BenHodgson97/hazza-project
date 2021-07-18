let script = document.getElementById('chat');

function getScriptParamUrl() {
  return script.getAttribute('chat-url');
};

let user = script.getAttribute('username')


let url = getScriptParamUrl();
let connection = new WebSocket(url);

connection.onopen = function() {
  let chats = document.querySelectorAll('.chat input');
  setupEventListener(chats);
  console.log('Login user: ' + user);
  let json = '{ "EventType": "Login", "user": "' + user + '" }';
  connection.send(json);
};

connection.onerror = function(error) {
  console.log('WebSocket Error ', error);
};

connection.onmessage = function(event) {
  let obj = JSON.parse(event.data);
  let incomingChat = document.getElementById(obj.from + '-messages');
  incomingChat.insertAdjacentHTML('beforeend', '<p>' + obj.message + '</p>');
}

console.log( "chat app is running!");

function setupEventListener(elements) {
  elements.forEach(function(element) {

    element.addEventListener("click", sendMessage(element))
    element.addEventListener("keyup", function(event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
          sendMessage(element);
        }
      });
  });
}

function sendMessage(element) {
  let text = element.value;
  if(text != "") {
    element.value = "";
    let to = element.getAttribute('id').split('-')[0];
    json = '{ "EventType": "ChatMessage", "to": "' + to + '", "from": "' + user + '", "message":"' + text + '" }';
    connection.send(json);
  }
};