guytra2205, yuvaleyal
214987745, 326660610

the subimageshistory class exists to save brightness mementos 
the subimgcharmatcher handles the logic with the treemap that keeps the chars, it handels
the brightness and removeing and adding chars, it is used in the asciiartalgorithm as the algorithm
needs the brigthness of the chars, it is also used in the shell class to add and remove chars.
the asciiartalgorithm class is used in shell as its the main algorithm that we need to print.
the keyboardinput class is a class that reads the input of the user.
the shell class is the class that runs the interaction with the user
it uses image class, and the subimgcharmatcher class.
the image class saves the image, and keeps info on the image like width and height.
the imagesetter class uses the image classand the brightnessMemento class
 and using it it adds some more functionalyt to it like 
changing the resolution or getting the subimageresolution.
the brightness momento class can save the sub image and can get its resolution.

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