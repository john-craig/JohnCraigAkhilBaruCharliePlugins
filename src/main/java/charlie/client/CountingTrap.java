/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.client;

import charlie.card.Card;
import charlie.message.Message;
import charlie.message.view.to.Deal;
import charlie.message.view.to.GameStart;
import charlie.message.view.to.Shuffle;
import charlie.plugin.ITrap;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;

/**
 *
 * @author John Craig, Akhil Sai Baru
 */
public class CountingTrap implements ITrap {
    private static final int minimumBet = 1;
    private static int shoeSize = -1;
    private static int runningCount = 0 ;
    private static int cardsPlayed = 0;
    private static boolean shufflePending = false;
    Logger LOG = null;

    public CountingTrap(){
        LOG = Logger.getLogger(CountingTrap.class);
    }
    
    @Override
    public void onSend(Message msg) {
        
    }

    @Override
    public void onReceive(Message msg) {
        if (msg instanceof GameStart){
            if(shufflePending){
                //Reset count
                runningCount = 0;
                cardsPlayed = 0;
                shufflePending = false;
            }
            
            GameStart start = (GameStart)msg;
            shoeSize = start.shoeSize();
        }
        
        if (msg instanceof Deal){
            if(shoeSize == -1){
                //Throw an error
                LOG.info("shoeSize was unitialized.");
            }
            
            Deal deal = (Deal)msg;
            
            // Count cards
            Card card = deal.getCard();
            
            if(card != null){
                int rank = card.getRank();
            
                /*
                  If the card is A, 10, J, Q, K subtract
                  if it is less than 7 add
                  otherwise do nothing
                */
                if(rank >= 10 || card.isAce() ){
                    runningCount -= 1;
                } else if (rank <= 6){
                    runningCount += 1;
                }

                //Regardless of the card, increment the cards played
                cardsPlayed++;

                //Calculate results
                double shoeDecks = (shoeSize - cardsPlayed) / 52;
                double trueCount = (double)runningCount / shoeDecks;
                int betAmount = (int)Math.max(minimumBet, minimumBet + Math.round(trueCount));

                //Formatters
                DecimalFormat shoeFmt = new DecimalFormat("#.##");
                DecimalFormat rnCntFmt = new DecimalFormat("###");
                DecimalFormat trCntFmt = new DecimalFormat("###.#");
                DecimalFormat betFmt = new DecimalFormat("###");

                String logString = String.format(
                    "shoe: %s running count: %s true count: %s bet: %s",
                    shoeFmt.format(shoeDecks),
                    rnCntFmt.format(runningCount),
                    trCntFmt.format(trueCount),
                    betFmt.format(betAmount)
                );
                LOG.info(logString);
            }
        }
        
        if(msg instanceof Shuffle){
            shufflePending = true;
        }
    }
    
}
