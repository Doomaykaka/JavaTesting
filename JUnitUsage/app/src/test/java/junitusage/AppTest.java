/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package junitusage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

class AppTest {
    @BeforeEach
    public void showUpTime() {
        System.out.println("Uptime " + LocalDateTime.now());
    }

    @AfterEach
    public void showDownTime() {
        System.out.println("Downtime " + LocalDateTime.now());
    }

    @Test
    public void appHasAMain() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getClass().getMethods()[0].getName(), "app should have a main");
    }

    @Test
    public void numNotEqualsZero() {
        assertNotEquals(0, App.generateRandFromTwo());
    }

    @Test
    public void methodThrowsException() {
        App classUnderTest = new App();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            classUnderTest.throwingMethod();
        });
    }

    @Test
    public void booleanTest() {
        App classUnderTest = new App();
        Assumptions.assumeTrue(classUnderTest.randBool());
    }

    @Nested
    public class AppFailTests {
        @Disabled
        @Test
        public void failTestDisabled() {
            fail("FirstFail");
        }

        @Test
        public void failTest() {
            fail("SecondFail");
        }
    }
}