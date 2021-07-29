let abilities = document.querySelectorAll('div.ability');
console.log(abilities)
abilities.forEach(function(element){
    let header = element.getElementsByClassName("header-1")[0];
    let collapsible = element.getElementsByClassName("collapsible")[0];
    console.log(header)
    header.addEventListener("click", function(event){
        toggle(collapsible);
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
