/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 *
 * @author ibmuser
 */
public class BotBasicStrategy extends BasicStrategy {
    @Override
    public Play getPlay(Hand hand, Card upCard) {
        Play play = super.getPlay(hand, upCard);
        
        // IBot does not support splits, so change
        // any splits to hits
        if(play == Play.SPLIT){
            play = Play.HIT;
        } 
        // Bots have to do *some* action, so 
        // if get a NONE for some reason, just STAY
        else if(play == Play.NONE){
            play = Play.STAY;
        }
        
        return play;
    }
}
