/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;
import charlie.audio.SoundFactory;
import charlie.sidebet.rule.SideBetRule;
import static charlie.view.AMoneyManager.PLACE_HOME_X;
import static charlie.view.AMoneyManager.PLACE_HOME_Y;
import charlie.view.sprite.Chip;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet view
 * @author Ron.Coleman
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = Logger.getLogger(SideBetView.class);

    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);

    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    
    protected List<Chip> chips = new ArrayList<>();
    protected Random ran = new Random();
    protected int width;

    protected Color bjFgColor = Color.BLACK;
    protected Color bjBgColor = new Color(116,255,4);
    protected Color bustFgColor = Color.WHITE;
    protected Color bustBgColor = new Color(250,58,5);
    
    private boolean didWin;
    private boolean isPlaying = true;
    
    public SideBetView() {
        LOG.info("side bet view constructed");
    }

    /**
     * Sets the money manager.
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        
        this.buttons = moneyManager.getButtons();
        this.width = moneyManager.getWidth();
    }

    /**
     * Registers a click for the side bet.
     * This method gets invoked on right mouse click.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;

        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
                
                //Add chips
                int n = chips.size();

                int placeX = X + n * width / 3 + ran.nextInt(10) - 10;
                int placeY = Y + ran.nextInt(5) - 5;
                
                Chip chip = new Chip(button.getImage(), placeX, placeY, amt);
                chips.add(chip);
                
                //Play the sound
                SoundFactory.play(Effect.CHIPS_IN);
            }
        }
        
        // If the click collides with the Side Bet
        // stake area's diameter
        if(x > X - (DIAMETER / 2) &&
           x < X + (DIAMETER / 2) &&
           y > Y - (DIAMETER / 2) &&
           y > Y - (DIAMETER / 2)){
            //Clear the bet
            amt = 0;
            
            //Clear the chips
            chips.clear();
            
            //Play the sound
            SoundFactory.play(Effect.CHIPS_OUT);
        }

        if(oldAmt == amt) {
            amt = 0;
            LOG.info("B. side bet amount cleared");
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        isPlaying = false;
        double bet = hid.getSideAmt();
        
        if(bet == 0){
            didWin = false;
            return;
        } else {
            didWin = true;
        }

        LOG.info("side bet outcome = "+bet);

        // Update the bankroll
        moneyManager.increase(bet);

        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting.
     */
    @Override
    public void starting() {
        isPlaying = true;
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view.
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view.
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);

        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+amt, X-5, Y+5);
        
        // Draw the side bet labels
        g.drawString("SUPER 7 pays 3:1", X+DIAMETER, Y-DIAMETER/2);
        g.drawString("ROYAL MATCH pays 25:1", X+DIAMETER, Y);
        g.drawString("EXACTLY 13 pays 1:1", X+DIAMETER, Y+DIAMETER/2);
        
        // Draw the chips
        for(Chip chip: chips) {
            chip.render(g);
        }
        
        //Draw the win status
        if(!isPlaying){
            if(didWin){
                g.setColor(Color.GREEN);
                g.drawString("WIN", X, Y);
            } else {
                g.setColor(Color.RED);
                g.drawString("LOSE", X, Y);
            }
        }
    }
}