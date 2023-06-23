package main.groovy;

import com.sun.jna.Library
import com.sun.jna.Native
 
System.setProperty("jna.library.path","/cplusplus")
 
interface Algorithms extends Library {
    public int getMax(int[] numbers,int numbersLength);
}

instance = Native.loadLibrary('algo.dll',Algorithms)
numbers = [1,2,3,4] as int[]
max = instance.getMax(numbers,numbers.size())

println(max)

num = "B" * max
println(num + "A")
