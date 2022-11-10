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
public class BasicStrategy {
    // These help make table formatting compact to look like the pocket card.
    public final static Play P = Play.SPLIT;
    public final static Play H = Play.HIT;
    public final static Play S = Play.STAY;
    public final static Play D = Play.DOUBLE_DOWN;
    
    /** Rules for section 1; see Instructional Services (2000) pocket card */
    Play[][] section1Rules = {
        /*               2  3  4  5  6  7  8  9  T  A  */
        /* 17+      */ { S, S, S, S, S, S, S, S, S, S },
        /* 16       */ { S, S, S, S, S, H, H, H, H, H },
        /* 15       */ { S, S, S, S, S, H, H, H, H, H },
        /* 14       */ { S, S, S, S, S, H, H, H, H, H },
        /* 13       */ { S, S, S, S, S, H, H, H, H, H },
        /* 12       */ { H, H, S, S, S, H, H, H, H, H }
    };
    Play[][] section2Rules = {
        /*               2  3  4  5  6  7  8  9  T  A  */
        /* 11       */ { D, D, D, D, D, D, D, D, D, H },
        /* 10       */ { D, D, D, D, D, D, D, D, H, H },
        /* 9        */ { H, D, D, D, D, H, H, H, H, H },
        /* 5-8      */ { H, H, H, H, H, H, H, H, H, H }
    };
    Play[][] section3Rules = {
        /*               2  3  4  5  6  7  8  9  T  A  */
        /* A, 8-10  */ { S, S, S, S, S, S, S, S, S, S },
        /* A, 7     */ { S, D, D, D, D, S, S, H, H, H },
        /* A, 6     */ { H, D, D, D, D, H, H, H, H, H },
        /* A, 5     */ { H, H, D, D, D, H, H, H, H, H },
        /* A, 4     */ { H, H, D, D, D, H, H, H, H, H },
        /* A, 3     */ { H, H, H, D, D, H, H, H, H, H },
        /* A, 2     */ { H, H, H, D, D, H, H, H, H, H }
    };
    Play[][] section4Rules = {
        /*               2  3  4  5  6  7  8  9  T  A  */
        /* A,A 8,8  */ { P, P, P, P, P, P, P, P, P, P },
        /* 10, 10   */ { S, S, S, S, S, S, S, S, S, S },
        /* 9, 9     */ { P, P, P, P, P, S, P, P, S, S },
        /* 7, 7     */ { P, P, P, P, P, P, H, H, H, H },
        /* 6, 6     */ { P, P, P, P, P, H, H, H, H, H },
        /* 5, 5     */ { D, D, D, D, D, D, D, D, H, H },
        /* 4, 4     */ { H, H, H, P, P, H, H, H, H, H },
        /* 3, 3     */ { P, P, P, P, P, P, H, H, H, H },
        /* 2, 2     */ { P, P, P, P, P, P, H, H, H, H }
    };
    
    // Constants for section rules corresponding to columns
    private final static int S1_17_PLUS         = 0;
    private final static int S1_16              = 1;
    private final static int S1_15              = 2;
    private final static int S1_14              = 3;
    private final static int S1_13              = 4;
    private final static int S1_12              = 5;

    private final static int S2_11              = 0;
    private final static int S2_10              = 1;
    private final static int S2_9               = 2;
    private final static int S2_5_THRU_8        = 3;

    private final static int S3_8_THRU_10       = 0;
    private final static int S3_7               = 1;
    private final static int S3_6               = 2;
    private final static int S3_5               = 3;
    private final static int S3_4               = 4;
    private final static int S3_3               = 5;
    private final static int S3_2               = 6;

    private final static int S4_A_A_OR_8_8      = 0;
    private final static int S4_10_10           = 1;
    private final static int S4_9_9             = 2;
    private final static int S4_7_7             = 3;
    private final static int S4_6_6             = 4;
    private final static int S4_5_5             = 5;
    private final static int S4_4_4             = 6;
    private final static int S4_3_3             = 7;
    private final static int S4_2_2             = 8;
    
    /**
     * Gets the play for player's hand vs. dealer up-card.
     * @param hand Hand player hand
     * @param upCard Dealer up-card
     * @return Play based on basic strategy
     */
    public Play getPlay(Hand hand, Card upCard) {
        //Validate hand and upcard
        if(upCard == null || hand == null){
            return Play.NONE;
        }
        
        if(!isValidCard(upCard)){
            return Play.NONE;
        }
        
        if(hand.size() != 2){
            return Play.NONE;
        } else if(
                hand.isBlackjack() ||
                hand.isCharlie() ||
                hand.isBroke()
            ){
            return Play.NONE;
        }
        
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        
        if(card1 == null || card2 == null){
            return Play.NONE;
        }
        
        if(!isValidCard(card1) ||
           !isValidCard(card2)){
            return Play.NONE;
        } else if(hand.getValue() < 2 ||
                hand.getValue() > 21){
            return Play.NONE;
        }
        
        //Process different sections
        if(hand.isPair()) {
            return doSection4(hand,upCard);
        }
        else if(hand.size() == 2 && (card1.getRank() == Card.ACE || card2.getRank() == Card.ACE)) {
            return doSection3(hand,upCard);
        }
        else if(hand.getValue() >=5 && hand.getValue() < 12) {
            return doSection2(hand,upCard);
        }
        else if(hand.getValue() >= 12)
            return doSection1(hand,upCard);
        
        return Play.NONE;
    }
    
    /**
     * 
     * @param card
     * @return isValid
     */
    private boolean isValidCard(Card card){
        boolean isValid = true;
        
        int cardValue = card.value();
        String cardName = card.getName();
        
        // Ensure the card's name is valid
        if(cardName.equals("?")){
            isValid = false;
        }
        
        // Ensure the card's value is within the appropriate range
        if(cardValue > 10 || cardValue < 1){
            isValid = false;
        } else {
            // For aces, ensure their value is 1
            if(card.isAce() && cardValue != 1){
                isValid = false;
            }
            
            // For face cards, ensure their value is 10
            if(card.isFace() && cardValue != 10){
                isValid = false;
            }
        }
        
        return isValid;
    }
    
    /**
     * 
     * @param upCard the Dealer's up-card
     * @return dealerIdx the rule index for the dealer's up-card
     */
    private int getDealerIndex(Card upCard){
        int dealerIdx = -1;
        
        if(upCard.isAce()){
            dealerIdx = 9;
        } else {
            dealerIdx = upCard.value() - 2;
        }
        
        return dealerIdx;
    }
    
    /**
     * Does section 1 processing of the basic strategy, 12-21 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection1(Hand hand, Card upCard) {
        int dealerIdx = getDealerIndex(upCard);
        int ruleRow = -1;
        
        int value = hand.getValue();
        
        // Determine the row in the Section 1 rule table
        // which we should use for this hand
        switch(value){
            case 16:
                ruleRow = S1_16;
                break;
            case 15:
                ruleRow = S1_15;
                break;
            case 14:
                ruleRow = S1_14;
                break;
            case 13:
                ruleRow = S1_13;
                break;
            case 12:
                ruleRow = S1_12;
                break;
            default:
                ruleRow = S1_17_PLUS;
                break;
        }
        
        return section1Rules[ruleRow][dealerIdx];
    }

    protected Play doSection2(Hand hand, Card upCard) {
        int dealerIdx = getDealerIndex(upCard);
        int ruleRow = -1;
        
        int value = hand.getValue();
        
        // Determine the row in the Section 2 rule table
        // which we should use for this hand
        switch(value){
            case 11:
                ruleRow = S2_11;
                break;
            case 10:
                ruleRow = S2_10;
                break;
            case 9:
                ruleRow = S2_9;
                break;
            default:
                ruleRow = S2_5_THRU_8;
                break;
        }
        
        return section2Rules[ruleRow][dealerIdx];
    }
    
    protected Play doSection3(Hand hand, Card upCard) {
        int dealerIdx = getDealerIndex(upCard);
        int ruleRow = -1;
        
        // Get the value of the non-ace card
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        int value;
        
        if(card1.isAce()){
            value = card2.value();
        } else {
            value = card1.value();
        }
        
        // Determine the row in the Section 2 rule table
        // which we should use for this hand
        switch(value){
            case 7:
                ruleRow = S3_7;
                break;
            case 6:
                ruleRow = S3_6;
                break;
            case 5:
                ruleRow = S3_5;
                break;
            case 4:
                ruleRow = S3_4;
                break;
            case 3:
                ruleRow = S3_3;
                break;
            case 2:
                ruleRow = S3_2;
                break;
            default:
                ruleRow = S3_8_THRU_10;
                break;
        }
        
        return section3Rules[ruleRow][dealerIdx];
    }

    protected Play doSection4(Hand hand, Card upCard) {
        int dealerIdx = getDealerIndex(upCard);
        int ruleRow = -1;
        
        // Get the value of either of the cards
        Card card1 = hand.getCard(0);
        int value = card1.value();
        
        // Determine the row in the Section 2 rule table
        // which we should use for this hand
        switch(value){
            case 10:
                ruleRow = S4_10_10;
                break;
            case 9:
                ruleRow = S4_9_9;
                break;
            case 7:
                ruleRow = S4_7_7;
                break;
            case 6:
                ruleRow = S4_6_6;
                break;
            case 5:
                ruleRow = S4_5_5;
                break;
            case 4:
                ruleRow = S4_4_4;
                break;
            case 3:
                ruleRow = S4_3_3;
                break;
            case 2:
                ruleRow = S4_2_2;
                break;
            default:
                ruleRow = S4_A_A_OR_8_8;
                break;
        }
        
        return section4Rules[ruleRow][dealerIdx];
    }
}