package charlie.basicstrategy.invalid;

import charlie.client.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my hand with too many cards, which should be NONE
 * @author ibmuser
 */
public class TooLargeHandTest {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 2+10
        Card card1 = new Card(2, Card.Suit.CLUBS);
        Card card2 = new Card(10, Card.Suit.DIAMONDS);
        Card card3 = new Card(4, Card.Suit.SPADES);
        
        myHand.hit(card1);
        myHand.hit(card2);
        myHand.hit(card3);
        
        // Create dealer up card: 2
        Card upCard = new Card(2, Card.Suit.HEARTS);
        
        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();
  
        Play advice = advisor.advise(myHand, upCard);
        // Validate the advice.
        assertEquals(advice, Play.NONE);
    }
}