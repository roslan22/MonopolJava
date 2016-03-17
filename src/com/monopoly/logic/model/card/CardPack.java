package com.monopoly.logic.model.card;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardPack<T extends Card>
{
    private List<T> cards;
    private int currentCardIndex = 0;

    public CardPack(List<T> cards) 
    {
        this.cards = cards;
        randomizePack();
    }
    
    public T getNext()
    {
        currentCardIndex = (currentCardIndex + 1) % cards.size();
        return cards.get(currentCardIndex);
    }

    public void randomizePack()
    {
        Collections.shuffle(cards, new Random(System.nanoTime()));
    }

    public void removeFromPack(T surpriseCard)
    {
        cards.remove(surpriseCard);
    }

    public void returnToPack(T card)
    {
        cards.add((currentCardIndex - 1) % cards.size() ,card);
    }

    public int getSize()
    {
        return cards.size();
    }
}
