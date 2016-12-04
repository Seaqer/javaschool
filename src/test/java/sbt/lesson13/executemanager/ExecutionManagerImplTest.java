package sbt.lesson13.executemanager;

import sbt.lesson13.executemanager.interfaces.Context;
import sbt.lesson13.executemanager.interfaces.ExecutionManager;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by Артём on 30.10.2016.
 */
public class ExecutionManagerImplTest {
    private ExecutionManager executionManager;
    private Context contex;

    @Before
    public void setUp() throws Exception {
        executionManager = new ExecutionManagerImpl();
    }

    @Test
    public void execute() throws Exception {
        contex = executionManager.execute(() -> {
                    System.out.println("Finish");
                }, () -> {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                , () -> {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                , () -> {
                    try {
                        Thread.sleep(5);
                        throw new RuntimeException();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        sleep(100);
        assertEquals("failed completed", contex.getCompletedTaskCount(), 2);
        assertEquals("failed exception", contex.getFailedTaskCount(), 1);
    }
}