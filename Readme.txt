HOW TO OPEN THE GAME:

1. (Recommended) JAR version: 

    We strongly recommend you to enter the game by double clicking on "WikiJump.JAR". 
    The game will start immediately.
    It can run as long as JRE is installed, without installing anything else.

    This JAR file is not dependent on any other file we submitted.
    It creates a "load" folder containing the high scores.

2. (Not Recommended) Development version: 

    This is how we test the game while developing so its less recommended.
    Opening the game in this way may take some several minutes for the first time as some development utilities and dependency packages need to be loaded.

    Go into the "Development" folder. 
    - If you are on a windows machine, run "run.bat" and wait till the game starts.
    - If you are on a mac/linux machine, 
        1. Open console
        2. change directory to this directory
        3. use "gradlew desktop:run" command
    There could be warnings about JRE version but actually there's nothing wrong.

No matter opened in which way, the game is identical, except for they do not share the same high score archive.



WHERE TO FIND THE SOURCE CODE:

    Originally, all the source code is in "Development" folder, but they are separated in different sub-folders.
    Therefore, I have copied all the source code into the "Source" folder.
    If you insist on finding the source code in the "Development" folder, then they are in "core/src/" and "desktop/src/com/isom/desktop".
    Note that simply performing a "javac" on these source code does not work because I did not copy the dependency packages here for clarity.

