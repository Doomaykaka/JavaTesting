/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class App {
    public static void main(String[] args) {
        int threadsNum = 8;
        ExecutorService service = Executors.newFixedThreadPool(threadsNum);
        
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            return Thread.currentThread().getName();
        };
        
        StringBuilder resultInLine = new StringBuilder();
        List<Future<String>> results = new ArrayList<Future<String>>();
        Future<String> result = null;
        
        for(int i = 0; i < threadsNum; i++) {
            result = service.submit(task);
            results.add(result);
        }
        
        for(Future<String> threadResult:results) {
            try {
                resultInLine.append(threadResult.get(1, TimeUnit.SECONDS));
                resultInLine.append(" | ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        
        service.shutdown();
        
        System.out.println("-------------");
        System.out.println(resultInLine.toString()); 
    }
}
