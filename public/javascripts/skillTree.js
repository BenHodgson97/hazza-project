let menuContent = document.querySelector('.menu-content')
let menuButton = document.querySelector('.menu-button')ls

function openCloseMenu() {
   if(menuContent.classList.contains('open')) {
        closeMenu()
   } else {
        openMenu()
   }
}

function closeMenu() {
    menuContent.classList.remove('open')
    menuButton.classList.remove('open')
}

function openMenu() {
    menuContent.classList.add('open')
    menuButton.classList.add('open')
}

let abilityToggles = document.querySelectorAll('.header')

abilityToggles.forEach(function(abilityToggle) {
    abilityToggle.addEventListener("click", function(event) {
        select(abilityToggle)
    })
})

function select(abilityToggle) {
    let selectBox = abilityToggle.querySelector('.selected-box')
    if(selectBox.classList.contains("unselected")) {
        selectBox.classList.remove('unselected')
        httpGetAdd(abilityToggle.getAttribute('abilityId'))
    } else {
        selectBox.classList.add('unselected')
        httpGetRemove(abilityToggle.getAttribute('abilityId'))
    }
}

function httpGetAdd(abilityId) {
    let connection = new XMLHttpRequest();
    connection.open("GET", "/skill-tree/add/" + user + "/" + abilityId)
    connection.send()
}

function httpGetRemove(abilityId) {
    let connection = new XMLHttpRequest();
    connection.open("GET", "/skill-tree/remove/" + user + "/" + abilityId)
    connection.send()
}
