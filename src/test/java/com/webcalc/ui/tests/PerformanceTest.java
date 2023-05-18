package com.webcalc.ui.tests;
import com.webcalc.performance.RestAssuredThread;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;

@Epic("Web calculator UI / Performance")
@Feature("send api requests")
public class PerformanceTest {

    @Test
    public void testPerformOperation() throws InterruptedException {
        int threadNumber = 100;
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

        System.out.println(RestAssuredThread.times);
        assertEquals(String.format("There is %s unsuccessful responses",RestAssuredThread.failures), 0, RestAssuredThread.failures);
    }
}
