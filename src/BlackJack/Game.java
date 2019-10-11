package BlackJack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    static Participant player;
    static Participant dealer;
    static int cardCost;
    boolean turn = true; // чья очередь (если false - то диллера, true - игрока)
    boolean wantOrCanContinue = true; // хочет ли (или может ли ввиду баланса) продолжать игрок
    private Generation generation = new Generation();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Начало игры
    public void startGame() {
        player = new Participant();
        dealer = new Participant();
        int l;
        // Выбор уровня сложности
        System.out.println("Выберете уровень сложности 0 или 1");
        while (true) {
            try {
                l = Integer.parseInt(reader.readLine());
                if (l == 0){
                    System.out.println("Вы выбрали легкий уровень");
                    setLevel(0);
                    break;
                } else if (l == 1){
                    System.out.println("Вы выбрали обычный уровень");
                    setLevel(1);
                    break;
                } else {
                    System.out.println("Пожалуйста введите либо 0 либо 1");
                }
            } catch (Exception e){
                System.out.println("Пожалуйста введите либо 0 либо 1");
            }
        }

        // Начинаем саму игру непосредственно
        while (wantOrCanContinue) {
            // Новый раунд
            round();

            // Идет проверка может ли продолжать игру Игрок ну или сам Диллер
            if (dealer.getBalance() < (2*cardCost)  ){
                System.out.println("Игра закончена, Вы Победили!");
                break;
            }
            if (player.getBalance() < (2*cardCost)){
                System.out.println("Игра закончена, Вы Проиграли =(");
                break;
            }

            // Спрашиваем у игрока будет ли он играть еще раунд
            wantNewRound();
        }

        System.out.println("Игра закончена \n");
        if (player.getBalance() > player.getOriginBalance()) {
            System.out.println("Вы подняли бабла аж на " + (player.getBalance() - player.getOriginBalance()));
        } else if (player.getBalance() < player.getOriginBalance()){
            System.out.println("Вам лучше не играть на 1 хбет, вы в минусе на " + (player.getOriginBalance() - player.getBalance()));
        } else {
            System.out.println("Похоже ничья!");
        }

    }

    public void round(){
        // В начале кажого раунда и дилер и игрок обязательно берут по одной карте
        player.clearAll();
        dealer.clearAll();
        takePlayerCard();
        takeDealerCard();
        if (turn){
            System.out.println("В этом раунде вы ходите первым");
            playerTurn();
            if (!player.getOverDone()) {
                dealerTurn();
            }
        } else {
            System.out.println("В этом раунде диллер ходит первым");
            dealerTurn();
            if (!dealer.getOverDone()) {
                playerTurn();
            }
        }
        roundResult();
        changeTurn();
        generation.setRoundNew(true);
    }

    // Результаты раунда
    public void roundResult (){
        if (player.getCurrentSum() > dealer.getCurrentSum()) {
            if (!player.getOverDone()) {
                System.out.println("Вы выиграли раунд, у вас " + player.getCurrentSum() + ", у диллера " + dealer.getCurrentSum());
                player.setBalance(player.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            } else {
                System.out.println("Вы проиграли раунд, у вас перебор: " + player.getCurrentSum() + ", у диллера " + dealer.getCurrentSum());
                dealer.setBalance(dealer.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            }
        } else if (player.getCurrentSum() < dealer.getCurrentSum()) {
            if (!dealer.getOverDone()) {
                System.out.println("Вы проиграли раунд, у вас " + player.getCurrentSum() + ", у диллера " + dealer.getCurrentSum());
                dealer.setBalance(dealer.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            } else {
                System.out.println("Вы выиграли раунд, у вас " + player.getCurrentSum() + ", у диллера перебор: " + dealer.getCurrentSum());
                player.setBalance(player.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            }
        } else {
            if (turn) {
                System.out.println("Вы проиграли раунд так как ходили первыми, у вас " + player.getCurrentSum() + ", у диллера " + dealer.getCurrentSum());
                dealer.setBalance(dealer.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            } else {
                System.out.println("Вы выиграли раунд так как ходили вторыми, у вас " + player.getCurrentSum() + ", у диллера " + dealer.getCurrentSum());
                player.setBalance(player.getBalance() + player.getCurrentBet() + dealer.getCurrentBet());
            }
        }
        System.out.println("Ваш баланс: " + player.getBalance() + " " + "Баланс диллера: " + dealer.getBalance());
    }

    public void wantNewRound (){
        System.out.println("Еще рануд?  y - да, n - нет");
        while (true) {
            try {
                String s = reader.readLine();
                if (s.equals("n")){
                    wantOrCanContinue = false;
                    break;
                } else if (s.equals("y")){
                    break;
                } else {
                    System.out.println("Пожалуйста введите y - чтобы продолжить, или n - чтобы закончить раунд");
                }
            } catch (IOException e) {
                System.out.println("Пожалуйста введите y - чтобы продолжить, или n - чтобы закончить раунд");
            }
        }
    }

    // Метод для выбора уровня сложности 0 или 1
    public void setLevel (int level){
        if (level == 0){
            player.setOriginBalance(5000);
            player.setBalance(5000);
            dealer.setBalance(4000);
            cardCost = 100;
        } else {
            player.setOriginBalance(10000);
            player.setBalance(10000);
            dealer.setBalance(10000);
            cardCost = 200;
        }
    }

    // Метод ход Игрока
    public void playerTurn (){
        String k;
        System.out.println("Ваша очередь");
        while (player.getBalance() >= cardCost) {
            try {
                System.out.println("Текущая сумма карт на руках: " + player.getCurrentSum() + ". Текущий баланс: " + player.getBalance());
                player.getSeeMyCards();
                System.out.println("Еще карту?  y - да, n - нет");
                k = reader.readLine();
                if (k.equals("y")){
                    takePlayerCard();
                    if (player.getCurrentSum() > 21){
                        System.out.println("Упс, у вас перебор");
                        player.getSeeMyCards();
                        player.setOverDone(true);
                        break;
                    }
                } else if (k.equals("n")) {
                    break;
                } else {
                    System.out.println("Пожалуйста введите y - чтобы продолжить, или n - чтобы закончить свой ход");
                }
            } catch (IOException e) {
                System.out.println("Пожалуйста введите y - чтобы продолжить, или n - чтобы закончить свой ход");
            }
        }
    }

    public void dealerTurn(){
        System.out.println("Диллер делает свой ход");
        while (dealer.getBalance() >= cardCost) {
            if (dealer.getCurrentSum() <= 15){
                takeDealerCard();
                if (dealer.getCurrentSum() > 21){
                    System.out.println("У диллера перебор");
                    dealer.setOverDone(true);
                    break;
                }
            } else {
                break;
            }
        }
    }

    public void takePlayerCard (){
        int p = generation.getCard();
        player.addCardToCurrentCards(p);
        player.calculateSum();
        player.setBalance(player.getBalance()-cardCost);
        player.setCurrentBet(cardCost);
    }

    public void takeDealerCard (){
        int d = generation.getCard();
        dealer.addCardToCurrentCards(d);
        dealer.calculateSum();
        dealer.setBalance(dealer.getBalance()-cardCost);
        dealer.setCurrentBet(cardCost);
    }

    public void changeTurn (){
        if (turn) {
            turn = false;
        } else {
            turn = true;
        }
    }


}
