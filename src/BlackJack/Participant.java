package BlackJack;

import java.util.HashMap;

public class Participant {
    private int balance;
    private int currentSum;
    private int currentBet;
    private int originBalance;
    private HashMap<String, Integer> currentCards = new HashMap<>();
    private boolean overDone; // переменная отвечающая за перебор игрока В РАУНДЕ
    private static Generation g;
    static {
       g = new Generation();
    }

    // Геттеры для игрока
    public int getOriginBalance() { return originBalance;}

    public int getCurrentBet () { return currentBet;}

    public int getBalance (){ return balance;}

    public int getCurrentSum() { return currentSum;}

    public boolean getOverDone() {return overDone;}


    // Сеттеры для игрока
    public void setBalance (int balance) {
        this.balance = balance;
    }

    public void setCurrentBet (int cardCost){
        currentBet += cardCost;
    }

    public void setOriginBalance (int originBalance){
        this.originBalance = originBalance;
    }

    public void setOverDone (boolean overDone) { this.overDone = overDone; }


    // Другие методы

    // Метод для просмотра текщих карт на руках
    public void getSeeMyCards () {
        System.out.print("Мои карты: ");
        for (String x: currentCards.keySet()){
            System.out.print(x + ", ");
        }
    }

    // Метод для добавления карт к текщим картам на руках
    public void addCardToCurrentCards (int n) {
        currentCards.put(g.cards[n], g.intCards[n]);
    }

    // Метод для сброса текщих карт, текущей ставки и текущей суммы для начала раунда
    public void clearAll(){
        currentCards.clear();
        currentSum = 0;
        currentBet = 0;
        setOverDone(false);
    }

    // Метод для подсчета текущей суммы
    public void calculateSum (){
        currentSum = 0;
        for (int value : currentCards.values()) {
            currentSum += value;
        }
        while (currentSum > 21 && currentCards.containsValue(11)){
            for (HashMap.Entry<String, Integer> pair : currentCards.entrySet()){
                if (pair.getValue() == 11){
                    String t = pair.getKey();
                    currentCards.remove(pair.getKey());
                    currentCards.put(t, 1);
                    break;
                }
            }
            currentSum = 0;
            for (int value : currentCards.values()) {
                currentSum += value;
            }
        }
    }
}
