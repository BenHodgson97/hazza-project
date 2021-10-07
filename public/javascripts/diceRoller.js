let dice = document.querySelectorAll('.dice');
let chosenDice = document.querySelector('.chosen-dice')

function getColour(dice) {
    return dice.className.substr(0, dice.className.indexOf("-"))
}

function getIndex(dice) {
    return Array.prototype.indexOf.call(dice.parentNode.children, dice)
}

dice.forEach(function(dice){
    dice.addEventListener("click", function(event){
        clickDice(dice)

        let dieColour = getColour(dice)
        let actualAmount = getIndex(dice)
        let json = '{"EventType": "DiceUpdate", "die": "' + dieColour + '", "amount": ' + actualAmount + '}'
        connection.send(json)
    });
});

function clickDice(dice) {
    if(isNotSelected(dice, "unselected-dice")) {
        lightUpDice(dice, 'unselected-dice');

        let chosenClass = ".chosen-" + getColour(dice)
        let unchosenDice = chosenDice.querySelector(chosenClass).children[getIndex(dice)]
        lightUpDice(unchosenDice, 'unchosen-dice')
    } else {
        clickSelected(dice);
    }
}

function lightUpDice(dice, classToGo) {
    dice.classList.remove(classToGo);

    if(dice.previousElementSibling != null && isNotSelected(dice.previousElementSibling, classToGo)) {
        lightUpDice(dice.previousElementSibling, classToGo);
    }
}

function isNotSelected(dice, classToGo) {
    return dice.classList.contains(classToGo);
}

function clickSelected(dice) {
    let nextDice = dice.nextElementSibling
    if(nextDice != null && !isNotSelected(nextDice, "unselected-dice")) {
        nextDice.classList.add("unselected-dice");
        if(nextDice.nextElementSibling != null && !isNotSelected(nextDice.nextElementSibling, "unselected-dice")) {
            clickSelected(nextDice);
        }

    } else {
        dice.classList.add("unselected-dice");
        let previousDice = dice.previousElementSibling
        if(previousDice != null && !isNotSelected(previousDice, "unselected-dice")) {
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

connection.onopen = function(event) {
    setInterval(function(){connection.send('{"EventType":"Ping"}')}, 60000)
}

