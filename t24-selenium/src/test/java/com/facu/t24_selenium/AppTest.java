package com.facu.t24_selenium;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private static final String ENQ_BOMB_NOFILE_SER_GEN = "ENQ BOMB.NOFILE.SER.GEN";
    private static final String TX_NUMER_TEST1 = "TF161551729601";
    final Logger logger = LogManager.getLogger(this.getClass());

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
    	
        Generico seleniumT24 = new Generico(ENQ_BOMB_NOFILE_SER_GEN);
        Map<String,String> map = seleniumT24.runT24Example(TX_NUMER_TEST1);
        for (String key : map.keySet()) {
        	logger.info(key + " = "+ map.get(key));
		}
        assert(!StringUtils.isEmpty(map.get("USUARIO")));
    	
    }
}
