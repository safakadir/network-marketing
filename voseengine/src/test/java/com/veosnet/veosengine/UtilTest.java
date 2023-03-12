package com.vose.voseengine;

import com.vose.voseengine.controller.HelperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void testBayiIdFromPath() {
        String path1 = "/foo/bayi/111";
        String path2 = "/bayi/foo";
        String path3 = "/foo/bayi/111/bar";
        String path4 = "foo/222/bayi/111/bar";
        String path5 = "foo/222";

        Assertions.assertEquals(111, HelperUtil.bayiIdFromPath(path1));
        Assertions.assertNull(HelperUtil.bayiIdFromPath(path2));
        Assertions.assertEquals(111, HelperUtil.bayiIdFromPath(path3));
        Assertions.assertEquals(111, HelperUtil.bayiIdFromPath(path4));
        Assertions.assertNull(HelperUtil.bayiIdFromPath(path5));
    }
}
