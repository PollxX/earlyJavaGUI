/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author James
 */
public class SimpleSearchTest {
    
    public SimpleSearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class SimpleSearch.
     */
    @Ignore
    @Test
    public void testFindSameCase() {
        System.out.println("find - same case:");
        String thisSearchTerm = "Bill";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Ignore
    @Test
    public void testFindDifferentCase() {
        System.out.println("find - different case:");
        String thisSearchTerm = "bill";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Ignore
    @Test
    public void testFindInAlbum() {
        System.out.println("find - album only:");
        String thisSearchTerm = "Amazing";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.budPowell1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Ignore
    @Test
    public void testFindInTitle() {
        System.out.println("find - title only:");
        String thisSearchTerm = "Ipanema";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.oscarPeterson1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Test
    public void testNullInputFind(){
        
    }
    @Test
    public void testEmptyInputFind() {
        
    }
    
}
