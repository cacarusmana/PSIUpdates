package com.global.imd.psiupdates;

import com.global.imd.psiupdates.network.RestfulHttpMethod;
import com.global.imd.psiupdates.util.Constant;
import com.global.imd.psiupdates.util.DateUtil;
import com.global.imd.psiupdates.util.Utility;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Caca Rusmana on 25/09/2017.
 */


public class MyUnitTest {

    @Test
    public void requestDataIsValid() throws Exception {
        String param = Constant.DATE_TIME_FIELD + DateUtil.formatDate(Constant.DATE_FORMAT, new Date());

        String result = RestfulHttpMethod.connect(Constant.PSI_URL + param);

        assertTrue(Utility.checkValidResult(result));
    }

    @Test
    public void requestDataIsInValid() throws Exception {
        String param = Constant.DATE_TIME_FIELD + null;

        String result = RestfulHttpMethod.connect(Constant.PSI_URL + param);

        assertFalse(Utility.checkValidResult(result));
    }
}
