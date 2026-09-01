package com.lab;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EnvironmentSmokeTest {
    @Test
    public void requiredDependenciesAreAvailable() throws Exception {
        assertNotNull(Resources.class);
        assertNotNull(LogManager.getLogger(EnvironmentSmokeTest.class));
        assertNotNull(Class.forName("com.mysql.cj.jdbc.Driver"));
    }
}
