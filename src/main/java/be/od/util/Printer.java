package be.od.util;

import be.od.model.Grid;

public class Printer {
    public static void printGrid(Grid grid) {

        for (int i = 0; i < 41; i++) {
            System.out.print("=");
        }
        System.out.println();
        for (int row = 0; row < 9; row++) {
            System.out.print("|| ");
            for (int col = 0; col < 9; col++) {

                int value = grid.getValueAt(row, col);
                if (value != 0) {
                    System.out.print(value);
                } else {
                    System.out.print(" ");
                }

                if ((col + 1) % 3 == 0) {
                    System.out.print(" || ");
                } else {
                    System.out.print(" | ");
                }
            }

            System.out.println();
            if ((row + 1) % 3 == 0) {
                for (int i = 0; i < 41; i++) {
                    System.out.print("=");
                }
            } else {
                for (int i = 0; i < 41; i++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printGridNumber(int number) {
        printTitle(" ***   Sudoku " + (number + 1) + "   ***  ");
    }

    public static void printTitle(String title) {
        System.out.println();
        for (int i = 0; i < title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.println(title);
        for (int i = 0; i < title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    public static void printResults(int size) {
        if(size == 0) {
            System.out.println("Something went wrong...");
        } else if (size == 1) {
            System.out.println("This is a good one!");
        } else {
            System.out.println("This grid has multiple solutions.");
        }
    }
}
