/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibmuser
 */
public class HueyStay implements IBot, Runnable {
    protected final int MAX_THINKING = 5; 
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Random ran = new Random();

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
        Hid hid = new Hid(seat);
        this.myHand = new Hand(hid);
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    @Override
    public void endGame(int shoeSize) {
        
    }

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        
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
        dealer.stay(this, myHand.getHid());
    }
    
}
