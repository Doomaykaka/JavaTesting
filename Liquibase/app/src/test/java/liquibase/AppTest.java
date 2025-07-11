package liquibase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

class AppTest {
    @Test
    void appHasAMain() {
        App classUnderTest = new App();
        Method[] methods = classUnderTest.getClass().getMethods();
        List<String> methodsNames = Arrays.stream(methods).map(method -> method.getName()).toList();
        assertTrue(methodsNames.contains("main"), "app should have a main");
    }
}
