let script = document.getElementById('chat');

let user = script.getAttribute('username')

let chatUrl = script.getAttribute('chat-url');
let chatConnection = new WebSocket(chatUrl);

function keepChatAlive() {
  chatConnection.send('{ "EventType": "Ping"}');
}

chatConnection.onopen = function() {
  let chats = document.querySelectorAll('.chat-popup');
  setupEventListener(chats);
  console.log('Login user: ' + user);
  let json = '{ "EventType": "Login", "user": "' + user + '" }';
  chatConnection.send(json);
  setInterval(keepChatAlive, 54000);
};

chatConnection.onerror = function(error) {
  console.log('WebSocket Error ', error);
};

chatConnection.onmessage = function(event) {
  let obj = JSON.parse(event.data);
  if(obj.EventType == "OutgoingMessage") {
    insertMessage(obj.from, obj.message)
  } else {
    console.log(obj.EventType);
  }
}

function insertMessage(name, message) {
  let chat = document.getElementById(name + '-messages');
  if(chat != null) {
    let container = chat.parentElement.parentElement
    if(container.classList.contains("closed-chat-container")){
      container.getElementsByTagName("span")[0].classList.remove("read")
      container.getElementsByClassName("open-button")[0].classList.add("unread")
    }

    chat.insertAdjacentHTML('beforeend', '<p class="received-message">' + message + '</p>');
    chat.scrollTop = chat.scrollHeight;
  } else {
    let popupId = name + '-chat'
    let html = '<div class="closed-chat-container"><button class="open-button" onclick=toggleForm("'+ name + '-chat")>'+ name + '</button><div id="' + popupId + '" class="chat-popup"><div id="' + name + '-messages" class="messages"></div><input id="' + name +'-input" placeholder="Type message.." required></div></div>';
    document.getElementById("chats").insertAdjacentHTML('afterbegin', html);
    setupEventListener([document.getElementById(popupId)]);
    insertMessage(name, message);
  }
}

console.log( "chat app is running!");

function setupEventListener(elements) {
  elements.forEach(function(element) {
    let input = element.getElementsByTagName("input")[0];
    let messages = element.getElementsByClassName("messages")[0];
    element.addEventListener("keyup", function(event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
          sendMessage(input, messages);
        }
      });
  });
}

function sendMessage(element, messages) {
  let text = element.value;
  if(text != "") {
    element.value = "";
    messages.insertAdjacentHTML('beforeend', '<p class="sent-message">' + text + '</p>');
    messages.scrollTop = messages.scrollHeight;
    let to = element.getAttribute('id').split('-')[0];
    json = '{ "EventType": "ChatMessage", "to": "' + to + '", "from": "' + user + '", "message":"' + text + '" }';
    chatConnection.send(json);
  }
};

function toggleForm(id) {
  let element = document.getElementById(id)
  if(element.offsetParent === null) {
    openForm(element);
  } else {
    closeForm(element);
  }
}

function openForm(element) {
  let container = element.parentElement
  container.getElementsByTagName("span")[0].classList.add("read")
  container.getElementsByClassName("open-button")[0].classList.remove("unread")
  element.style.display = "block";
  element.parentElement.className = "open-chat-container";
}

function closeForm(element) {
  element.style.display = "none";
  element.parentElement.className = "closed-chat-container";
}
