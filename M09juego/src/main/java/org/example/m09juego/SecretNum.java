package org.example.m09juego;


import java.util.Random;

public class SecretNum {

    private int number;

    public SecretNum(int max) {
        generate(max);
    }

    public void generate(int max) {
        Random random = new Random();
        number = random.nextInt(max) + 1;
    }

    public boolean check(String guess) {
        try {
            int n = Integer.parseInt(guess);
            return number == n;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String hint(String guess) {
        int difference = Math.abs(number - Integer.parseInt(guess));
        if (difference <= 5) {
            return "¡Muy cerca! La diferencia es <= 5 ";
        } else if (difference <= 10) {
            return "¡Acercándote! La diferencia es  > 5 y <= 10";
        } else {
            return "Lejos, sigue intentando!La diferencia es >10";
        }
    }

    public int getNumber() {
        return number;
    }
}
