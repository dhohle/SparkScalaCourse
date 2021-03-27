// Flow Control

// If / Else
if(1>3) println("Impossible!") else println("The world makes sense")

if(1>3){
  println("Impossible!")
  println("Huh")
}else{
  println("The world")
  println("makes sense still")
}

// Matching
val number =3
number match {
  case 1 => println("One")
  case 2 => println("Two")
  case 3 => println("Three")
  case _ => println("Something else")
}

for (x <- 1 to 4){
  val squared = x*x
  println(squared)
}

var x = 10
while (x>=0){
  println(x)
  x -=1
}

x = 0
do{
  println(x);x+=1
}while(x<=10)


// Expressions

val q = {val x = 10;x+20}
println(q)

// test fibonacci

var previous:Long = 0l
var current:Long = 1l

for ( x <- 1 to 90){
  val next = previous + current
  println(f"Fibonacci's $x th sequence is $next")
//  println(f"Fibonacci's $x th sequence is ${current + previous}")
  previous = current
  current = next
}
