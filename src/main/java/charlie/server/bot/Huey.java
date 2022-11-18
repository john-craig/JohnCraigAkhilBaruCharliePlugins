/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.card.HoleCard;
import charlie.client.BotBasicStrategy;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibmuser
 */
public class Huey implements IBot, Runnable {
    protected final int MAX_THINKING = 5; 
    protected final int BET_AMT = 5;
    protected final int SIDE_BET_AMT = 0;
    
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Random ran = new Random();
    
    protected Card dealerUpCard = null;
    protected boolean requestedHit = false;

    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Override
    public void sit(Seat seat) {
        this.mine = seat;
        Hid hid = new Hid(seat, BET_AMT, SIDE_BET_AMT);
        this.myHand = new Hand(hid);
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    @Override
    public void endGame(int shoeSize) {
        // Set the dealer card to null
        this.dealerUpCard = null;
        
        // Create new hand
        //Hid hid = new Hid(this.mine);
        //this.myHand = new Hand(hid);
    }

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        //This is a card being dealt to the bot
        if(hid.getSeat() == this.mine){
            // We were dealt a card after requesting
            // a hit
            if(this.requestedHit){
                // We are expected to make
                // another play after requesting a hit
                this.requestedHit = false;
                
                // If this new card either caused us to
                // break or win, the dealer is not expecting
                // another play from us
                if(!myHand.isBroke()     &&
                   !myHand.isBlackjack() && 
                   !myHand.isCharlie()){
                    new Thread(this).start();
                }
            }
        }
        //This is a card being dealt to the dealer's hand
        else if(hid.getSeat() == Seat.DEALER){
            // Only update the upcard if there is not
            // already an upcard and this card is not the hole card
            if(this.dealerUpCard == null && !(card instanceof HoleCard)){
                this.dealerUpCard = card;
            }
        }
    }

    @Override
    public void insure() {
        
    }

    @Override
    public void bust(Hid hid) {
        
    }

    @Override
    public void win(Hid hid) {
        
    }

    @Override
    public void blackjack(Hid hid) {
        
    }

    @Override
    public void charlie(Hid hid) {
        
    }

    @Override
    public void lose(Hid hid) {
        
    }

    @Override
    public void push(Hid hid) {
        
    }

    @Override
    public void shuffling() {
        
    }

    @Override
    public void play(Hid hid) {
        if(hid.getSeat() != this.mine){
            return;
        }
        
        new Thread(this).start();
    }

    @Override
    public void split(Hid newHid, Hid origHid) {
        
    }

    @Override
    public void run() {
        int thinking = ran.nextInt(MAX_THINKING * 1000);
        try {
            Thread.sleep(thinking);
        } catch (InterruptedException ex) {
            Logger.getLogger(HueyStay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // BotBasic strategy is guaranteed to return
        // an actionable play which is valid for the bot
        // i.e. not SPLIT or NONE
        BotBasicStrategy strategy = new BotBasicStrategy();
        Play play = strategy.getPlay(this.myHand, this.dealerUpCard);
        
        // The bot's hand is always created with the same
        // hid so it will be correct for the dealer's purposes,
        // even though the hand instance itself is a separate copy
        switch(play){
            case HIT:
                this.requestedHit = true;
                dealer.hit(this, myHand.getHid());
                break;
            case STAY:
                dealer.stay(this, myHand.getHid());
                break;
            case DOUBLE_DOWN:
                dealer.doubleDown(this, myHand.getHid());
                break;
        }
    }
    
}
