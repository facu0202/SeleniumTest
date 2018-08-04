package com.facu.t24_selenium;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static final String ENQ_BOMB_NOFILE_SER_GEN = "ENQ BOMB.NOFILE.SER.GEN";
    private static final String TX_NUMER_TEST1 = "TF161551729601";
    final Logger logger = LogManager.getLogger(this.getClass());


    @Test
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
