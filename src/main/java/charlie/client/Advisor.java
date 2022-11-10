/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 *
 * @author ibmuser
 */
public class Advisor implements IAdvisor {
    protected BasicStrategy bs = new BasicStrategy();

    /**
     * Gets advice using the Basic Strategy.
     * @param myHand Player hand
     * @param upCard Dealer up-card
     * @return Play advice
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        return bs.getPlay(myHand, upCard);
    }
    
}
