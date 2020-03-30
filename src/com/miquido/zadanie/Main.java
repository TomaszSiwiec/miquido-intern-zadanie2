package com.miquido.zadanie;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Sheet sheet = getSheet();
        int[][] board = new int[sheet.getY()][sheet.getX()];
        List<Element> elements = sortElements(getElements());
        List<Element> bestSolutionElements = findBestSolution(elements, sheet, board);

        System.out.println("Best solution\nQuantity of elements: " + bestSolutionElements.size());
        bestSolutionElements.stream()
                .forEach(System.out::println);
        System.out.println("Points for all objects: " + calculateAllPointsForObjects(bestSolutionElements));
        System.out.println("All negative points for empty area: " + calculateAllNegativePoints(board));
        System.out.println("Overall result: " + calculateResultPoints(bestSolutionElements, board));

        //Draw result if size of sheet is smaller than 31x31 and amount of elements is less than 11
        if (elements.size() <= 10 && sheet.getX() <= 30 && sheet.getY() <= 30) {
            System.out.println("\nGenerated elements distribution:");
            drawResultBoard(board);
        }
    }

    private static Sheet getSheet() {
        Scanner scanner = new Scanner(System.in);
        int x, y;
        System.out.println("Enter dimension x of sheet: ");
        x = scanner.nextInt();
        System.out.println("Enter dimension y of sheet: ");
        y = scanner.nextInt();

        return new Sheet(x , y);
    }

    private static List<Element> getElements() throws IOException {
        List<Element> elements = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int x, y, p, q = 1;
        String ch;
        for (int i = 0; i < q; i++) {
            System.out.println("[ELEMENT " + i + "]Enter dimension x: ");
            x = scanner.nextInt();
            System.out.println("[ELEMENT " + i + "]Enter dimension y: ");
            y = scanner.nextInt();
            System.out.println("[ELEMENT " + i + "]Enter points: ");
            p = scanner.nextInt();
            elements.add(new Element(i, x, y, p));
            System.out.println("Created element with dimensions " + x + "x" + y + " with " + p + " points");
            System.out.println("Enter 'q' if you want to run algorithm or any letter to continue entering elements:");
            ch = scanner.next();
            if (!ch.equals("q") || ch.equals("")) {
                q++;
            }
        }

        return elements;
    }

    private static List<Element> sortElements(List<Element> elements) {
        List<Element> sortedElements = new ArrayList<>(elements);
        sortedElements.sort(Comparator.comparingDouble(Element::getProfitability));
        Collections.reverse(sortedElements);
        return sortedElements;
    }

    private static List<Element> findBestSolution(List<Element> elements, Sheet sheet, int[][] board) {
        resetTheArray(board, sheet);
        List<Element> bestSolutionList = new ArrayList<>();
        for (Element element : elements) {
            if (findPositionAndDrawIfPossible(element, board)) {
                bestSolutionList.add(element);
            }
        }

        return bestSolutionList;
    }

    private static boolean findPositionAndDrawIfPossible(Element element, int[][] board) {
        int[] result;

        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    result = new int[]{i, j};
                    if (checkPosibilityToDrawElement(board, element, result)) {
                        drawElementOnBoard(board, element, j, i);
                        return true;
                    } else {
                        element.swap();
                        if (checkPosibilityToDrawElement(board, element, result)) {
                            drawElementOnBoard(board, element, j, i);
                            return true;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static int[][] drawElementOnBoard(int[][] board, Element element, int x, int y) {
        for (int i = x ; i < x + element.getX(); i++) {
            for (int j = y ; j < y + element.getY(); j++) {
                board[j][i] = element.getId();
            }
        }
        return board;
    }

    private static void drawResultBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0 ; j < board[i].length; j++) {
                if (board[i][j] < 0) {
                    System.out.print(board[i][j]);
                } else {
                    System.out.print(" " + board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    private static boolean checkPosibilityToDrawElement(int[][] board, Element element, int[] result) {
        int startX = result[1];
        int startY = result[0];

        for (int i = startY; i < startY + element.getY(); i++) {
            for (int j = startX; j < startX + element.getX(); j++) {
                if (j > board[0].length - 1) {
                    return false;
                }

                if (i > board.length - 1) {
                    return false;
                }

                if (board[i][j] != -1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void resetTheArray(int[][] array, Sheet sheet) {
        for (int i = 0; i < sheet.getY(); i++) {
            for (int j = 0; j < sheet.getX(); j++) {
                array[i][j] = -1;
            }
        }
    }

    private static Integer calculateAllPointsForObjects(List<Element> elements) {
        return elements.stream()
                .mapToInt(Element::getPoints)
                .sum();
    }

    private static Integer calculateAllNegativePoints(int[][] board) {
        int points = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == -1) {
                    points++;
                }
            }
        }
        return points;
    }

    private static Integer calculateResultPoints(List<Element> elements, int[][] board) {
        return calculateAllPointsForObjects(elements) - calculateAllNegativePoints(board);
    }
}
