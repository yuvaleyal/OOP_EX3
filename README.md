guytra2205, yuvaleyal
214987745, 326660610

we used the treemap data structure for it has a log(n) time complexity
for searching inserting and deleting and it sorts the items based on the keys
they have, the keys we used are the brightness of the chars making it
easy to go from char to char that are close in brightness to each other.

we used the error handling by always trying to read the input of the user in a while
loop and if the input the user gave is problomatic we throw an error
and in the func run in shell we catch the error and respond properly.

we added 3 functions to the api of SubImgCharMatcher, the first is:
public TreeSet<Character> getAllChars(): this function returns a treeset with all the charecters,
we added this function so that when we need to print all the chars to the screen 
so the user knows what are the chars he is working with it will be able to be done very easly 
with this function.
public int getSize(): this function returns the amount of chars that we are currently working with 
it is usfull in the shell class as well as we somthimes need to check things
about the chars we are working with so we will know what to do,
for example if its empty we need to print somthing to the screen and not run the algorithm.
public void removeAllChars(): lastly we have this function,
it removes all the chars and makes the char list completly empty,
it is usful if the users input is remove all.

we didnt change the api of any other classes.