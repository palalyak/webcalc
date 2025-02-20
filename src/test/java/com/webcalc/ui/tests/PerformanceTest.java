package com.webcalc.ui.tests;

import com.webcalc.performance.RestAssuredThread;
import com.webcalc.ui.core.utils.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

@Epic("Performance API test")
@Feature("send api requests")
public class PerformanceTest extends BaseTest {

    @Test
    public void testPerformOperation() throws InterruptedException {
        int threadNumber = 10;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            RestAssuredThread request = new RestAssuredThread(i);
            Thread thread = new Thread(request);
            thread.start();
            threads.add(thread);
        }

        for (Thread th : threads) {
            th.join();
        }
        assertEquals(String.format("There is %s unsuccessful responses",RestAssuredThread.failures),
                0, RestAssuredThread.failures);
    }
}
