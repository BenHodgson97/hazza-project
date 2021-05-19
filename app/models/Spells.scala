package models

case class Spells(
                   id: Int,
                   name: String,
                   rank: Int,
                   spellType: String, //TODO Enumerated
                   skill: String, //TODO Enumerated
                   abilityType: String, //TODO Enumerated
                   cost: Int,
                   description: String,
                   group: Int,
                   treePosition: Int
                 )
