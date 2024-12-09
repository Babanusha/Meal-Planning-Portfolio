[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/RyiBKJgD)
# Portfolio project --Redacted--
This file uses Mark Down syntax. For more information see [here](https://www.markdownguide.org/basic-syntax/).

[//]: # (TODO: Fill inn your name and student ID)

STUDENT NAME = "Babanusha"  
STUDENT ID = "--Redacted--"

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
An application to help manage Items in a collection and hold a constant overview over expiration dates, and quantities.
  
Some of the applications features are:
- Add new items to the fridge
- Removing items from the fridge
- Updating parameters of the item.
- Displaying all items in the fridge
- Displaying items that are about to expire - by given date.
- An easy tool to create new recipes and add them to the recipe book.
- Keeping recipes and displaying recipes that can be made with the items in the fridge.
## Project structure

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)
Package structure is inspired by the MVC pattern.

Packages:
- edu.ntnu.idi.bidata - Holds the main class of the application, and the rest of the packages.
- Controller - Holds the controller classes for the application.
- CookBook - A module package, holds the classes for the cookbook.
- Fridge - A module package, holds the classes for the fridge.
- Settings - Holds a static final class for the application settings, and to increase readability.
- userInterface - Holds both User_interface class, and the classes for the TUI.


- test - Holds the JUnit test classes for the application.

## Link to repository

[//]: # (TODO: Include a link to your GitHub repository here.)
__PLACEHOLDER__
## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)

To run the project, you can run the main method in the Main class. 
The main method will start the application and run the application until the user exits the application.

The input of the program is the user input from the terminal,
and the output is the response from the application. (Items in fridge, recipes, etc.)


## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)

To run the tests, you can run the test classes in the test package.
Location: src/test/java.
## References
https://www.codecademy.com/article/mvc.

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)