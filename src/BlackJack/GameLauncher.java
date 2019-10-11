package BlackJack;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameLauncher {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Хотите начать игру? y - да, n - нет");
        String s = null;
        while (true) {
            try {
                s = reader.readLine();
                if (s.equals("y") || s.equals("n")) {
                    break;
                } else {
                    System.out.println("Пожалуйста введите либо y либо n");
                }
            } catch (IOException e) {
                System.out.println("Пожалуйста введите либо y либо n");
            }
        }
        if (s.equals("y")){
            Game game = new Game();
            game.startGame();
        } else {
            System.out.println("До встречи");
        }

    }

}
