package com.lab;

import org.apache.ibatis.io.Resources;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EnvironmentSmokeTest {
    @Test
    public void requiredDependenciesAreAvailable() throws Exception {
        assertNotNull(Resources.class);
        assertNotNull(Logger.getLogger(EnvironmentSmokeTest.class));
        assertNotNull(Class.forName("com.mysql.cj.jdbc.Driver"));
    }
}
