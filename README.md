[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/AIO_Bc8D)

# Chaos game! 

## authors: 

**Ekern, Berggren Signe**

**Oksavik, Døving August**

## Introduction 
This application is a mandatory exam application made for the IDATA2003 2024 exam.\
It is a application for drawning fractals using Java and JavaFX with a host of other
auxiliary functionality.\
JavaFX was used in this application because it is what we are currently familiar with and  
have been using the last semester.
## Features 
This is a feature rich appliction that allows a lot freedom and manipulation of a fractals.  
Current implemented features include:
- Manipulating coordinates of a fractal.
- Manipulating transformation values of a fractal.
- changing the amount of steps to run (should not exceed 10 000 000 steps as it leads to bad performance).
- Opening a fractal from a file.
- Saving a fractal to a file.
- Creating a Julia or Affine fractal from scratch.
- Turning coordinates sliders on or off.
- turning zoom scrolling on or off.
- Choosing the color of the fractal or using default which is a heat map of the most commonly hit points.
- Creating a bunch of different pre-made fractal.
- Playing a few different pre defined animations.

## What we have learned 
In the course of this project we have gained extensive knowledge on how to use JavaFX, design patterns\
abstractions, polymorphism, interfaces and exceptions.

## Challenges we faced 
During the intial phases of the project we faced several issues related to the mathematics of fractals   
as well as how to draw them. While time consuming and frustrating, these types of issues helped us   
become better at finding other errors later on, most of them taking less than an hour to find and resolve.  

When creating the main class for our application we struggled keeping it to a reasonable size.  
It is our opinion that any class should not become overly large otherwise it risks compromissing  
the cohesion of our application. Due to this mindset we continously refactored parts of the main  
class out to their own classes, something we felt gave a better overview.  
In hindsight, it is debatable whether this is helpful or not for future expansion of the application.  
Our hope is that such refactoring is conducive for future features.