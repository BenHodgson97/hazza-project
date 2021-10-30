let menuContent = document.querySelector('.menu-content')
let menuButton = document.querySelector('.menu-button')

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