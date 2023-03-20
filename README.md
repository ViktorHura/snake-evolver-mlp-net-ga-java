# Snake Evolver

![snake gif](https://github.com/ViktorHura/snake-evolver-mlp-net-ga-java/raw/master/snake.gif)

This is a java program that uses my [simple multilayer perceptron neural network](https://github.com/ViktorHura/mlpnet-ga-java), together with a genetic algorithm to learn to play the game of snake.

The game runs on OpenGl using bindings provided by [lwjgl](https://www.lwjgl.org/) library.

The network has 12 input nodes, 13 hidden nodes and 4 outputs.

The first 4 inputs tell the snake if there is a wall or a tail(1 blocked, 0 clear) in the 4 blocks next to the snake's head.

The second 4 inputs tell the snake which direction it is moving. Only one of them can be 1, the others will be 0.

The final 4 inputs tell the snake the the direction in which the apple is: 
above? under? left? right?
Usually 2 of them are 1 at once.

The 4 outputs correspond to the 4 arrow keys that you would use in a normal game of snake.

The genetic algorithm is identical to the one I used in my previous projects but with a slight change.

I decided to have the first 4 apples in the game spawn in fixed position as to aid the snakes to get on their feet. This also means that the best fitness score doesn't drop after each generation.

But when the snakes have learned to eat the first 4 apples, then the apples spawn random like in the real game. This makes it so that the same snake might get different fitness scores in different generations.

I couldn't cope with the random nature of this game at first. But I have learned that even tho the snakes might be leapfrogging each other in terms of highest fitness, I have observed that the average fitness still rises from one generation to the next.

I am happy with the results I got considering the simple architecture of my neural network and the limited inputs that my snakes get.

In the future I might try to give the snakes a more complex vision and use a more complex neural network.

![evolver screenshot](https://github.com/ViktorHura/snake-evolver-mlp-net-ga-java/raw/master/evolver.png)

## Binaries

I have compiled a fat jar for windows together with the jre which you can find [here](https://github.com/ViktorHura/snake-evolver-mlp-net-ga-java/releases)

If you wish to compile for a different operating system, don't forget to include the native libraries for that os. For more information take a look at the [lwjgl wiki](http://wiki.lwjgl.org/wiki/Distributing_Your_LWJGL_Application.html)

## Usage

The program starts with a console where you can input the variables for the genetic algorithm if you select the Train command.

The network saves the best snake from each generation to the nets/ folder, you can use the Replay command to see them in action.
