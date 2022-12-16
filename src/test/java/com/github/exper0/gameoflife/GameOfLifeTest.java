package com.github.exper0.gameoflife;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {

    @ParameterizedTest
    @MethodSource("testData")
    void validateGameRules(final int [][] world, int[][] aliveCoordinates) {
        final GameOfLife gol = new GameOfLife(world);
        int aliveCells = gol.tick();
        assertEquals(aliveCoordinates.length, aliveCells, ()->buildErrorMessage("unexpected number of alive cells", world, gol));
        if (aliveCoordinates.length > 0) {
            for (int[] aliveCoordinate : aliveCoordinates) {
                int x = aliveCoordinate[0];
                int y = aliveCoordinate[1];
                assertTrue(gol.isAlive(x, y), ()->buildErrorMessage("unexpected dead cell", world, gol));
            }
        } else  {
            for (int x = 0; x < world.length; ++x) {
                for (int y = 0; y < world.length; ++y) {
                    assertFalse(gol.isAlive(x, y), ()->buildErrorMessage("unexpected live cell", world, gol));
                }
            }
        }
    }

    /**
     * Test data: the first argument (2d array) is pre-initialized world and the second argument
     * is list of coordinates of alive cells after tick (another 2d array)
     * TODO: it would be nice to move data to a file
     * @return data
     */
    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {1,0,0},
                        {0,1,0},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,1,0},
                        {0,1,0},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,1},
                        {0,1,0},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,1},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,0},
                        {0,0,1}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,0},
                        {0,1,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,0},
                        {1,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {1,1,0},
                        {0,0,0}
                }, (Object) new int[][] {}),
                Arguments.of((Object) new int[][] {
                        {1,0,0},
                        {1,1,0},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,0},
                        {0,1},
                        {1,0},
                        {1,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,1,0},
                        {1,1,0},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,0},
                        {0,1},
                        {1,0},
                        {1,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {1,1,0},
                        {0,1,0}
                }, (Object) new int[][] {
                        {2,0},
                        {2,1},
                        {1,0},
                        {1,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {1,1,0},
                        {1,0,0}
                }, (Object) new int[][] {
                        {2,0},
                        {2,1},
                        {1,0},
                        {1,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,1,0},
                        {0,1,1},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,1},
                        {0,2},
                        {1,1},
                        {1,2}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,1},
                        {0,1,1},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,1},
                        {0,2},
                        {1,1},
                        {1,2}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,1},
                        {0,1,0}
                }, (Object) new int[][] {
                        {1,1},
                        {1,2},
                        {2,1},
                        {2,2}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,1},
                        {0,0,1}
                }, (Object) new int[][] {
                        {1,1},
                        {1,2},
                        {2,1},
                        {2,2}
                }),
                Arguments.of((Object) new int[][] {
                        {1,1,0},
                        {1,1,0},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,0},
                        {0,1},
                        {1,0},
                        {1,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {1,1,0},
                        {1,1,0}
                }, (Object) new int[][] {
                        {1,0},
                        {1,1},
                        {2,0},
                        {2,1}
                }),
                Arguments.of((Object) new int[][] {
                        {0,1,1},
                        {0,1,1},
                        {0,0,0}
                }, (Object) new int[][] {
                        {0,1},
                        {0,2},
                        {1,1},
                        {1,2}
                }),
                Arguments.of((Object) new int[][] {
                        {0,0,0},
                        {0,1,1},
                        {0,1,1}
                }, (Object) new int[][] {
                        {1,1},
                        {1,2},
                        {2,1},
                        {2,2}
                }),
                Arguments.of((Object) new int[][] {
                        {1,1,1},
                        {1,1,1},
                        {1,1,1}
                }, (Object) new int[][] {
                        {0,0},
                        {0,2},
                        {2,0},
                        {2,2}
                })
        );
    }

    private static String buildErrorMessage(String msg, int [][] original, GameOfLife current) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg + "\n");
        sb.append("Original world:\n");
        sb.append(worldString(new GameOfLife(original)));
        sb.append("new world:\n");
        sb.append(worldString(current));
        return sb.toString();
    }

    private static String worldString(GameOfLife gol) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            gol.print(ps);
            return baos.toString(utf8);
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        }
    }
}
