package org.example;

public class App {
    public static void main(String[] args) {
        System.out.println("Learn");
        String line = "\"671\",\"rs_aJgLTw7S9g\",\"xxx,xxxx,xxxx\",\"0\",\"road_2Hc134MVBP\",,\"0\",,,\"cp_y6cdnqMLw1\",\"cp_3xO4ltdPDP\"";

        System.out.println(line.split(",?:()"));
    }
}
