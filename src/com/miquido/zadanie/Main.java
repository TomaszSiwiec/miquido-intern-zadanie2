package com.miquido.zadanie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Sheet sheet = getSheet();
        int[][] board = new int[sheet.getY()][sheet.getX()];
        List<Element> elements = sortElements(getElements());

        elements.stream()
                .forEach(System.out::println);

        List<Element> bestSolutionElements = findBestSolution(elements, sheet, board);

        System.out.println("Best solution\nQuantity of elements: " + bestSolutionElements.size());
        bestSolutionElements.stream()
                .forEach(System.out::println);
        System.out.println("Points for all objects: " + calculateAllPointsForObjects(bestSolutionElements));
        System.out.println("All negative points for empty area: " + calculateAllNegativePoints(board));
        System.out.println("Overall result: " + calculateResultPoints(bestSolutionElements, board));

        drawResultBoard(board);

    }

    //TODO: to implement
    private static Sheet getSheet() {
        return new Sheet(11 , 11);
    }

    //TODO: to implement
    private static List<Element> getElements() {
        List<Element> elements = new ArrayList<>();
        elements.add(new Element(0, 6, 6, 9));
        elements.add(new Element(1, 7, 4, 3));
        elements.add(new Element(2, 2, 5, 5));
        elements.add(new Element(3, 2, 3, 7));
        elements.add(new Element(4, 5, 8, 4));
        elements.add(new Element(5, 3, 2, 1));
        elements.add(new Element(6, 4, 2, 1));
        elements.add(new Element(7, 4, 2, 1));
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
        try {
            result = findFirstAvailableCell(board);
        } catch (RuntimeException ex) {
            return false;
        }
        int x;
        int y;
        while (true) {
            x = result[1];
            y = result[0];
            if (x >= board[0].length - 1) {
                return false;
            }

            if (y >= board.length - 1) {
                return false;
            }

            if (checkPosibilityToDrawElement(board, element, result)) {
                drawElementOnBoard(board, element, x, y);
                return true;
            } else {
                element.swap();
                if (checkPosibilityToDrawElement(board, element, result)) {
                    drawElementOnBoard(board, element, x, y);
                    return true;
                }
            }
        }
    }

//    private static boolean findPositionAndDrawIfPossible(Element element, int[][] board) {
//        int[] result = findFirstAvailableCell(board);
//        int x = result[1];
//        int y = result[0];
//        while (true) {
//            if (x >= board[0].length - 1) {
//                return false;
//            }
//
//            if (y >= board.length - 1) {
//                return false;
//            }
//
//            if (checkPosibilityToDrawElement(board, element, result)) {
//                board = drawElementOnBoard(board, element, x, y);
//                return true;
//            } else {
//                System.out.println("Nie udało sie narysować elementu " + element.getId() + " w pozycji X=" + x + " Y=" + y + " i robimy swap!");
//                element.swap();
//                if (checkPosibilityToDrawElement(board, element, result)) {
//                    board = drawElementOnBoard(board, element, x, y);
//                    System.out.println("Udało sie narysować elementu  " + element.getId() + " w pozycji X=" + x + " Y=" + y + " i robimy swap!");
//                    return true;
//                }
//                System.out.println("Nie udało sie narysować elementu  " + element.getId() + " po swapie w pozycji X=" + x + " Y=" + y);
//            }
//
//            try {
//                result = findFirstAvailableCellFromCell(board, x, y);
//                x = result[1];
//                y = result[0];
//            } catch (RuntimeException ex) {
//                return false;
//            }
//
//            if (x == board[0].length - 1) {
//                y++;
//                x = 0;
//            } else {
//                x++;
//            }
//        }
//    }

//    private static boolean findPositionAndDrawIfPossible(Element element, int[][] board) {
//        boolean canDraw;
//        boolean loop = true;
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0 ; j < board[0].length; j++) {
//                int[] result = findFirstAvailableCellFromCell(board, j, i);
//                j = result[1];
//                i = result[0];
////                if (loop) {
////                    j = result[1];
////                    loop = false;
////                    continue;
////                }
////                System.out.println("Znaleziono pierwsza wolna komorke: " + result[0] + ":" + result[1]);
////                System.out.println("Sprawdzanie czy da się wpisać w kierunku " + element.getDirection());
//                canDraw = checkPosibilityToDrawElement(board, element, result);
////                System.out.println("Czy da sie narysowac: " + canDraw);
//                if (canDraw) {
//                    board = drawElementOnBoard(board, element, result[0], result[1]);
//
//                    return true;
//                }
//
//                element.swap();
////                System.out.println("Znaleziono pierwsza wolna komorke: " + result[0] + ":" + result[1]);
////                System.out.println("Sprawdzanie czy da się wpisać w kierunku " + element.getDirection());
//                canDraw = checkPosibilityToDrawElement(board, element, result);
////                System.out.println("Czy da sie narysowac: " + canDraw);
//                if (canDraw) {
//                    drawElementOnBoard(board, element, result[0], result[1]);
//                    return true;
//                } else {
//                    element.swap();
//                }
//            }
//        }
//
//        return false;
//    }

    public static int[] findFirstAvailableCellFromCell(int[][] board, int x, int y) throws RuntimeException{
        int startX = x;
        for (int i = y; i < board.length; i++) {
            for (int j = startX ; j < board[i].length; j++) {
                if (board[j][i] == -1) {
                    System.out.println("Znaleziona wolna komórka X: " + j + " Y: " + i);
                    return new int[] {i, j};
                }
            }
            startX = 0;
        }
        throw new RuntimeException();
    }

    public static int[] findFirstAvailableCell(int[][] board) throws RuntimeException {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0 ; j < board[i].length; j++) {
                if (board[i][j] == -1) {
                    System.out.println("Znaleziona wolna komórka X: " + j + " Y: " + i);
                    return new int[] {i, j};
                }
            }
        }
        throw new RuntimeException();
    }

    public static int[] findNextAvailableCell(int[][] board, int x, int y) throws RuntimeException {
        for (int i = y; i < board.length; i++) {
            for (int j = x + 1 ; j < board[i].length; j++) {
                if (board[i][j] == -1) {
                    System.out.println("[ZR]Znaleziona wolna komórka X: " + j + " Y: " + i);
                    return new int[] {i, j};
                }
            }
        }
        throw new RuntimeException();
    }

    public static int[][] drawElementOnBoard(int[][] board, Element element, int x, int y) {
        System.out.println("Rysujemy element " + element.getId() + " w miejscu X:" + x + " Y:" + y);
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
        boolean isPossible = false;

        if (result[1] + element.getX() >= board[0].length) {
            return isPossible;
        }

        if (result[0] + element.getY() >= board.length) {
            return isPossible;
        }

        int x = result[1];
        int y = result[0];
        while (!isPossible) {
            if (x >= board[0].length || x < 0) {
                return false;
            }

            if (y >= board.length || y < 0) {
                return false;
            }

            if (board[y][x] != -1) {
                return false;
            }

            if (x == result[1] + element.getX() && y == result[0] + element.getY()) {
                isPossible = true;
                return isPossible;
            }

            if (x >= result[1] + element.getX()) {
                y++;
                x = result[1];
            } else {
                x++;
            }
        }

//        int x = result[1];
//        int y = result[0];
//
//        if (x >= 0 || x <= board[0].length) {
//            return false;
//        }
//
//        if (y >= 0 || y <= board.length) {
//            return false;
//        }
//
//        boolean found = false;
//        while (!found) {
//            if (board[y][x] != -1) {
//                return false;
//            } else {
//                if (x == result[1] + element.getX()) {
//                    if (y == result[0] + element.getY()) {
//                        return true;
//                    }
//                }
//                if (x == board[0].length - 1) {
//                    x  = result[1];
//                    y++;
//                } else {
//                    x++;
//                }
//            }
//        }


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
