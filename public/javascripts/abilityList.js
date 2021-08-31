let collapsibles = document.querySelectorAll('.collapsible');
console.log(collapsibles)

collapsibles.forEach(function(element){
    let abilityWrapper = element.parentElement
    let header = abilityWrapper.getElementsByClassName("header-1")[0];
    header.addEventListener("click", function(event){
        toggle(element);
    });
});

function toggle(collapsible) {
    if(collapsible.offsetParent === null) {
        expandForm(collapsible);
    } else {
        collapseForm(collapsible);
    }
}

function expandForm(collapsible) {
    collapsible.style.display = "block";
}

function collapseForm(collapsible) {
    collapsible.style.display = "none";
}
