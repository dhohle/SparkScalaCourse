// Functions

// format def <function name> (parameter name: type, ...) return type = { }

def squareIt(x: Int): Int = {
  x * x
}

//def cubeIt(x:Int):Int = {x*x*x}
def cubeIt(x:Int):Int = x*x*x

print(squareIt(4))
print(cubeIt(4))


//
def transformInt(x:Int, f: Int => Int) : Int = {
  f(x)
}

print(transformInt(4, cubeIt))


// lambda function
transformInt(3, x => x*x*x*x)

transformInt(10, x=> x/2)

transformInt(2, x => {val y = x*2; y*y})


// Assignment

def upperCaseIt(x:String):String = x.toUpperCase()

def stringTransform(x: String, f: String => String) = f(x)

print(upperCaseIt("sdlsdlksd"))

print(stringTransform("sdfsdfsdf", upperCaseIt))




