package BlackJack;

import java.util.ArrayList;

public class Player {
    private int balance;
    private int currentSum;
    private int currentBet;
    private int originBalance;
    private ArrayList<String> currentCards = new ArrayList<>();
    Generation g = new Generation();

    public int getOriginBalance() { return originBalance;}

    public int getCurrentBet () { return currentBet;}

    public int getBalance (){ return balance;}

    public int getCurrentSum() { return currentSum;}

    public void setBalance (int balance) {
        this.balance = balance;
    }

    public void getSeeMyCards () {
        System.out.print("Мои карты: ");
        for (String x: currentCards){
            System.out.print(x + ", ");
        }
    }

    public void addCardToCurrentCards (int n) {
        currentCards.add(g.cards[n]);
    }

    public void clearAll(){
        currentCards.clear();
        currentSum = 0;
        currentBet = 0;
    }

    public void calculateSum (int k){
        int s = g.intCards[k];
        if (currentSum <= 10) {
            currentSum += s;
        } else if (s == 11){
            currentSum += 1;
        } else {
            currentSum += s;
        }
    }

    public void setCurrentBet (int cardCost){
        currentBet += cardCost;
    }

    public void setOriginBalance (int originBalance){
        this.originBalance = originBalance;
    }

}
