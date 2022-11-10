/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4Suite.java to edit this template
 */
package charlie.basicstrategy;

import charlie.basicstrategy.invalid.BlackjackHandTest;
import charlie.basicstrategy.invalid.InvalidHandCardTest;
import charlie.basicstrategy.invalid.InvalidUpCardTest;
import charlie.basicstrategy.invalid.NullUpCardTest;
import charlie.basicstrategy.invalid.TooLargeHandTest;
import charlie.basicstrategy.section1.Test00_12_2;
import charlie.basicstrategy.section1.Test00_12_7;
import charlie.basicstrategy.section1.Test01_12_2;
import charlie.basicstrategy.section1.Test01_12_7;
import charlie.basicstrategy.section2.Test00_5_2;
import charlie.basicstrategy.section2.Test00_5_7;
import charlie.basicstrategy.section2.Test01_5_2;
import charlie.basicstrategy.section2.Test01_5_7;
import charlie.basicstrategy.section3.Test00_A2_2;
import charlie.basicstrategy.section3.Test00_A2_7;
import charlie.basicstrategy.section3.Test01_A2_2;
import charlie.basicstrategy.section3.Test01_A2_7;
import charlie.basicstrategy.section4.Test00_22_2;
import charlie.basicstrategy.section4.Test00_22_7;
import charlie.basicstrategy.section4.Test01_22_2;
import charlie.basicstrategy.section4.Test01_22_7;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author ibmuser
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    Test00_12_2.class, Test01_12_2.class, Test00_12_7.class, Test01_12_7.class,
    Test00_5_2.class, Test01_5_2.class, Test00_5_7.class, Test01_5_7.class,
    Test00_A2_2.class, Test01_A2_2.class, Test00_A2_7.class, Test01_A2_7.class,
    Test00_22_2.class, Test01_22_2.class, Test00_22_7.class, Test01_22_7.class,
    BlackjackHandTest.class, InvalidHandCardTest.class, InvalidUpCardTest.class, 
    TooLargeHandTest.class, NullUpCardTest.class
})
public class TestSuite00 {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
