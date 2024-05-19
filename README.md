[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/AIO_Bc8D)

# Chaos game! 

## Authors: 

**Ekern, Berggren Signe**

**Oksavik, Døving August**

## Introduction
##### Welcome to chaos game!
Chaos game is one of many established methods for generating fractals online.
A fractal can be defined as complex pattern that are self-similar across different scales. In other word, beautiful 
figure that will repeat the same pattern regardless of how much you zoom and your viewpoint.

The application creates fractals choosing a initial starting point, and so transforming that point using a predefined 
mathematical operation. Iteratively creating a long sequence of points later on drawn on a canvas to create a fractal.
The most known fractals includes the Julia set and the Sierpinski triangle.

This particular Chaos Game was created by students as NTNU as a part of their exam for the course IDATx2024. The main 
objective was to show competence in newly learned knowledge, such as
design patterns, Java inheritance and polymorphism, and creating a GUI using JavaFX. 
JavaFX was used in this application because it is what we are currently familiar with and  
have been using the last semester.

## How to run the application
The application uses Maven as a build automation tool to ensure that the user can run it across integrated development
environments. Therefore, anyone with Maven can run it by either using “mvn javafx:run” or running the starter
application “ChaosGameMainApp”.

## Features 
This is a feature rich application that allows a lot of freedom and manipulation of a fractals. Current implemented 
features include:
- Manipulating coordinates of a fractal.
- Manipulating transformation values of a fractal.
- Changing the amount of steps to run (should not exceed 10 000 000 steps as it leads to bad performance).
- Opening a fractal from a file.
- Saving a fractal to a file.
- Creating a Julia or Affine fractal from scratch.
- Turning coordinates sliders on or off.
- Turning zoom scrolling on or off.
- Choosing the color of the fractal or using default which is a heat map of the most commonly hit points.
- Creating a bunch of different pre-made fractal.
- Playing a few different pre-defined animations.

## Architecture of the application
A desktop application was chosen as the main architecture, with the code itself structured in an MVC architecture. It 
was chosen because it provides an easier setup and insight into the structure of the application, as well as makes it 
easier to expand the application in the future by dividing the code base into discrete packages. MCV was also chosen to
naturally reduce unnecessary coupling between classes, so that the code becomes more robust and forgivable for future
extension. The architecture of the application was also chosen for the application since it is relatively easy to 
develop, and does not need a network connection or databases.

## What we have learned 
In the course of this project we have gained extensive knowledge on how to use JavaFX, design patterns
abstractions, polymorphism, interfaces and exceptions.

## Challenges we faced 
During the initial phases of the project we faced several issues related to the mathematics of fractals 
as well as how to draw them. While time-consuming and frustrating, these types of issues helped us   
become better at finding other errors later on, most of them taking less than an hour to find and resolve.  

When creating the main class for our application we struggled keeping it to a reasonable size. It is our opinion 
that any class should not become overly large otherwise it risks compromising the cohesion of our application. 
Due to this mindset we continuously refactored parts of the main class out to their own classes, something we felt 
gave a better overview. In hindsight, it is debatable whether this is helpful or not for future expansion of the 
application. Our hope is that such refactoring is conducive for future features.
## Instalation and execution
To run our "chaos Game" application, Maven is required.  
Explaning how to install Maven would be outside the scope of this application,  
please refer to this link [!install Maven](https://maven.apache.org/install.html) 
to find out how to install maven.  
Once maven is installed you can run the "runApp" file or use the command "mvn javafx:run" in a command shell.