package com.facu.t24_selenium;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

  
    public void testApp()
    {
        SeleniumT24 seleniumT24 = new SeleniumT24();
        assert(seleniumT24.runT24Example());
    }
}
