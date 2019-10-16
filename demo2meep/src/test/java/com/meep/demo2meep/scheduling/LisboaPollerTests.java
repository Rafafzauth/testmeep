
package com.meep.demo2meep.scheduling;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LisboaPollerTests {

    @Autowired
    private LisboaPoller lisboaPoller;

    @Test
    public void nullsJSONs() throws IOException {
        lisboaPoller.report();
    }
}