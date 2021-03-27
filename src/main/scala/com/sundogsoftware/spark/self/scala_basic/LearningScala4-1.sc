// Data Structures

// Tuples
// Immutable lists


val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")
print(captainStuff)

/// refer to the individual fields with a ONE-BASED index
println(captainStuff._1)
println(captainStuff._2)
println(captainStuff._3)

//
val picardsShip = "Picard" -> "Enterprise-D"
println(picardsShip._2)

val aBunchOfStuff = ("Kirk", 1964, true)


// Lists
// Like a Tuple, but more functionality
// Must be of same type

val shiplist = List("Enterprise", "Defiant", "Voyager", "Deep Space Nine")

println(shiplist(1))
// zero-based
println(shiplist(0))

println(shiplist.head)
println(shiplist.tail)
println(shiplist.tail.head)

for (ship <- shiplist) {
  println(ship)
}


// function literal
//val backwardShips = shiplist.map((ship: String) => {
//  ship.reverse
//})
val backwardShips = shiplist.map((ship: String) => ship.reverse)

for (ship <- backwardShips) println(ship)
backwardShips.foreach(println(_))

// Reduce() to combine together all the items in a collection using some function
val numberList = List(1, 2, 3, 4, 5)
val sum = numberList.reduce((x: Int, y: Int) => x + y)
println(sum)

// filter() removes stuff
val iHateFives = numberList.filter((x: Int) => x != 5)

val iHateThrees = numberList.filter(_ != 3)

// Concatenate Lists
val moreNumbers = List(6,7,8)
val lotsOfNumbers = numberList ++ moreNumbers

val reversed = numberList.reverse
val sorted = reversed.sorted
val lotsOfDuplicates = numberList ++ numberList

val distinctValues = lotsOfDuplicates.distinct

val max = lotsOfDuplicates.max
val total = lotsOfDuplicates.sum

val hasThree = iHateThrees.contains(3)
val hasThree = lotsOfDuplicates.contains(3)

// Map (Dictionary)
val shipMap = Map("Kirk" -> "Enterprise", "Picard" -> "Enterprise-D", "Sisko" -> "Deep Space Nine", "Janeway" -> "Voyager")

println(shipMap("Janeway"))

val archersShip = util.Try(shipMap("Archer")) getOrElse "Unknown"
println(archersShip)

// Exercise
val listTo20 = 1 to 20 toList
val listExclude3 = listTo20.filter(_%3!=0)


def notThree(x:Int) = x%3!=0
def filter(x:Int, f: Int=>Boolean) = f(x)

val listExclude3Fun = listTo20.filter(filter(_, notThree))
println(listExclude3Fun)