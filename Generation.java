package BlackJack;

import java.util.Arrays;

public class Generation {
    private boolean roundNew = true;

    String [] cards = {"6 черви", "7 черви", "8 черви", "9 черви", "10 черви", "В черви", "Д черви", "К черви", "Т черви",
                       "6 буби", "7 буби", "8 буби", "9 буби", "10 буби", "В буби", "Д буби", "К буби", "Т буби",
                       "6 крести", "7 крести", "8 крести", "9 крести", "10 крести", "В крести", "Д крести", "К крести", "Т крести",
                       "6 пики", "7 пики", "8 пики", "9 пики", "10 пики", "В пики", "Д пики", "К пики", "Т пики"};
    int [] intCards = {6, 7, 8, 9, 10, 2, 3, 4, 11,
                       6, 7, 8, 9, 10, 2, 3, 4, 11,
                       6, 7, 8, 9, 10, 2, 3, 4, 11,
                       6, 7, 8, 9, 10, 2, 3, 4, 11};

    private int [] intCardsCopy;

    public int getCard (){
        int i;
        if (roundNew){
            intCardsCopy = createIntCardsCopy();
            i = (int) (Math.random()*36);
            intCardsCopy [i] = 0;
            setRoundNew (false);
        } else {
            while (true) {
                i = (int) (Math.random()*36);
                if (intCardsCopy[i] != 0){
                    intCardsCopy[i] = 0;
                    break;
                }
            }
        }
        return i;
    }

    public int [] createIntCardsCopy() { return Arrays.copyOf(intCards, intCards.length); }

    public void setRoundNew (boolean roundNew){ this.roundNew = roundNew;}

}