package com.github.exper0.gameoflife;

import java.io.IOException;
import java.io.PrintStream;
import java.util.function.BiFunction;

public class GameOfLife {
    public static final int DEFAULT_WORLD_SIZE = 25;
    public static final int SEED_ZONE_SIZE = 5;

    // could be boolean[][] or even BitSet instead of int but int[][] is just a little more simple and convenient
    private int [][] world;
    private final int size;

    private static final int[][] DIRECTIONS = {{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    /**
     * Default constructor
     */
    public GameOfLife() {
        this(DEFAULT_WORLD_SIZE);
    }

    /**
     * Construct game with specified world size
     * @param worldSize World size
     */
    public GameOfLife(int worldSize) {
        this(new int[worldSize][worldSize]);
    }

    /**
     * Package-level visibility to use in Unit tests
     * @param world World to use in a game
     */
    GameOfLife(int[][] world) {
        this.world = world;
        this.size = world.length;
    }

    /**
     * Seed area of given size in the center of the world with given seeder.
     * Takes seed zone as a parameter, it has to be less than or equal to half of the world size
     * (in order to be effectively centered and provide enough free space for cells to grow)
     *
     * @param seedZoneSize Seed zone size
     * @param seeder Function(strategy) that determines if specific cell with given coordinates should be live or dead
     * @return number of live cells
     * @throws IllegalArgumentException if given seed zone or more than a half of the world
     */
    public int seed(int seedZoneSize, BiFunction<Integer, Integer, Integer> seeder) {
        if (seedZoneSize > (size / 2)) {
            throw new IllegalArgumentException("too large seed zone: seed zone should be less or equal to half of world size");
        }
        int offset = size / 2 - seedZoneSize / 2;
        int end = offset + seedZoneSize;
        int cells = 0;
        for (int row = offset; row < end; ++row) {
            for (int column = offset; column < end; ++column) {
                world[row][column] = seeder.apply(row, column);
                if (world[row][column] == 1) {
                    cells++;
                }
            }
        }
        return cells;
    }

    /**
     * Counts live neighbor cells by given coordinates
     * @param row Row
     * @param column Column
     * @return number of neighbor cells
     */
    private int countLiveNeighbors(int row, int column) {
        int cnt = 0;
        for (int[] dir: DIRECTIONS) {
            int r = row + dir[0];
            int c = column + dir[1];
            if (r >= 0 && c >= 0 && r < size && c < size && world[r][c] == 1) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Calculates next version of the world
     * @return number of live cells
     */
    public int tick() {
        int [][] newWorld = new int[size][size];
        int cells = 0;
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column) {
                int liveNeighbors = countLiveNeighbors(row, column);
                if (world[row][column] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newWorld[row][column] = 0;
                    } else {
                        newWorld[row][column] = 1;
                        cells++;
                    }
                } else if (liveNeighbors == 3) {
                    newWorld[row][column] = 1;
                    cells++;
                }
            }
        }
        this.world = newWorld;
        return cells;
    }

    /**
     * Prints the world state to given PrintStream
     * @param p print stream for the output
     */
    public void print(PrintStream p) {
        for (int r = 0; r < size; ++r) {
            for(int c = 0; c < size; ++c) {
                if (world[r][c] == 1) {
                    p.print("O");
                } else {
                    p.print("-");
                }
            }
            p.println();
        }
    }

    public boolean isAlive(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException("coordinates expected within range: 0-" + size);
        }
        return world[x][y] == 1;
    }

    public static void main(String[] args) throws IOException {
        GameOfLife gol = new GameOfLife();
        int liveCells = gol.seed(SEED_ZONE_SIZE, (row, column)->(int)Math.round(Math.random()));
        do {
            gol.print(System.out);
            System.out.println("(" + liveCells + " live cells) " +
                    "Press enter for next version of the world or 'q' followed by enter to exit");
        } while ((char)System.in.read() != 'q' && (liveCells = gol.tick()) != 0);
        if (liveCells == 0) {
            System.out.println("The world died, no live cells anymore!");
        }
    }
}
