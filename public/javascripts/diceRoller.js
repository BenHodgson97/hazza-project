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
    let chosenClass = ".chosen-" + getColour(dice)
    let unchosenDice = chosenDice.querySelector(chosenClass).children[getIndex(dice)]

    if(isNotSelected(dice, "unselected-dice")) {
        lightUpDice(dice, 'unselected-dice');

        lightUpDice(unchosenDice, 'unchosen-dice')
    } else {
        clickSelected(dice, 'unselected-dice');
        clickSelected(unchosenDice, 'unchosen-dice')
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

function clickSelected(dice, diceClass) {
    let nextDice = dice.nextElementSibling
    if(nextDice != null && !isNotSelected(nextDice, diceClass)) {
        nextDice.classList.add(diceClass);
        if(nextDice.nextElementSibling != null && !isNotSelected(nextDice.nextElementSibling, diceClass)) {
            clickSelected(nextDice, diceClass);
        }

    } else {
        dice.classList.add(diceClass);
        let previousDice = dice.previousElementSibling
        if(previousDice != null && !isNotSelected(previousDice, diceClass)) {
            clickSelected(previousDice, diceClass);
        }
    }
}

function rollDice() {
    let unselectedDice = document.querySelectorAll('.unselected-dice')
    if(unselectedDice.length != 30) {
        connection.send('{"EventType": "Roll"}')
    }
}

let url = document.getElementById('diceRoller').getAttribute('dice-url');
let connection = new WebSocket(url);

connection.onmessage = function(event) {
    let object = JSON.parse(event.data);
    let eventType = object.EventType
    if(eventType == "DiceUpdate") {
        let die = object.die
        let amount = object.amount

        let diceColourDivClass = ".all-" + die + "-dice"
        let diceColourDiv = document.querySelector(diceColourDivClass)
        clickDice(diceColourDiv.children[amount])
    } else {
        Object.entries(object.rolledDice).forEach(([colour, value]) => {
            let backgrounds = document.querySelectorAll('.' + colour + '-background:not(.unchosen-dice)')
            for (let i = 0; i < value.length; i++) {
                let diceFace = value[i]
                let background = backgrounds[i]
                let isDouble = diceFace.length == 2
                background.classList.remove('double-symbol')
                if(isDouble) {
                    background.classList.add('double-symbol')
                }
                background.innerHTML = ""
                for (let i = 0; i < diceFace.length; i++) {
                    let symbol = diceFace[i]
                    let symbolDiv = document.createElement("div")
                    var symbolClass;
                    if(isDouble) {
                        symbolClass = "double-symbol"
                    } else {
                        symbolClass = "single-symbol"
                    }

                    symbolDiv.classList.add(symbol, symbolClass)
                    background.appendChild(symbolDiv)
                }
            }
        })

        let symbols = object.symbols
        let outcomeSymbols = document.querySelector('.outcome-symbols')
        outcomeSymbols.innerHTML = ""
        symbols.forEach(function(symbol) {
            let newSymbolElement = document.createElement("div")
            newSymbolElement.classList.add("outcome", symbol)
            outcomeSymbols.appendChild(newSymbolElement)
        })
    }
}

connection.onopen = function(event) {
    setInterval(function(){connection.send('{"EventType": "Ping"}')}, 54000)
}

