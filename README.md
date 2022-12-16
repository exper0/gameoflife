# Game of life
It's a simple game implementation. Rules are [here](Rules.md). 
## Requirements
Java 8 or above, optionally gradle (gradle wrapper provided)

## How to run
In project directory:
1. (Preferred) Run `./gradlew build` and then `java -jar build/libs/game-of-life.jar`. This works a little better than running directly from gradle due to interactive nature of the program
2. (Additional) Simply run `./gradlew run`

## How it works
The world 25 x 25 with dead/empty cells marked as `-` and live cells `O`. The area 5x5 in the center of the world is going to be randomly seeded. In order to proceed to next generation hit `Enter`. Hit `q` followed by `Enter` to exit. On each `Enter` new generation will be shown, no screen cleanup will be done so it's easy to trace before/after state.
