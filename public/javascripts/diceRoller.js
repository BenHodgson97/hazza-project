let dice = document.querySelectorAll('.dice');

dice.forEach(function(dice){
    dice.addEventListener("click", function(event){
        clickDice(dice)
        let diceClassNames = dice.className

        let dieColour = diceClassNames.substr(0, diceClassNames.indexOf("-"))
        let actualAmount = Array.prototype.indexOf.call(dice.parentNode.children, dice)
        let json = '{"die": "' + dieColour + '", "amount": ' + actualAmount + '}'
        connection.send(json)
    });
});

function clickDice(dice) {
    if(isNotSelected(dice)) {
        lightUpDice(dice);
    } else {
        clickSelected(dice);
    }
}

function lightUpDice(dice) {
    dice.classList.remove('unselected-dice');

    if(dice.previousElementSibling != null && isNotSelected(dice.previousElementSibling)) {
        lightUpDice(dice.previousElementSibling);
    }
}

function isNotSelected(dice) {
    return dice.classList.contains("unselected-dice");
}

function clickSelected(dice) {
    let nextDice = dice.nextElementSibling
    if(nextDice != null && !isNotSelected(nextDice)) {
        nextDice.classList.add("unselected-dice");
        if(nextDice.nextElementSibling != null && !isNotSelected(nextDice.nextElementSibling)) {
            clickSelected(nextDice);
        }

    } else {
        dice.classList.add("unselected-dice");
        let previousDice = dice.previousElementSibling
        if(previousDice != null && !isNotSelected(previousDice)) {
            clickSelected(previousDice);
        }
    }
}

let url = "ws://localhost:9000/dice-roller-socket";
let connection = new WebSocket(url);

connection.onmessage = function(event) {
    let object = JSON.parse(event.data);
    let die = object.die
    let amount = object.amount

    let diceColourDivClass = ".all-" + die + "-dice"
    let diceColourDiv = document.querySelector(diceColourDivClass)
    clickDice(diceColourDiv.children[amount])
}
