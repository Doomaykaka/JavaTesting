/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package idioms;

import java.util.ArrayList;

import idioms.DependencyInjectionUsing.Dependency;

public class App {
    public static void main(String[] args) {
        EqualsUsageExample example1 = new EqualsUsageExample(0);
        EqualsUsageExample example2 = new EqualsUsageExample(1);
        System.out.println("Example 1 equals example 2: " + example1.equals(example2));

        separate();

        EntrySetUsing.generateEntrySet();

        separate();

        EnumSingletonUsing.INSTANCE.sayHello();
        EnumSingletonUsing.INSTANCE.sayHello();

        separate();

        ArraysAsListUsing.createArrayAsList();

        separate();

        WaitInCycleExample waiting = new WaitInCycleExample();
        waiting.useWait();

        separate();

        CloneNotSupportedExceptionAvoid obj1 = new CloneNotSupportedExceptionAvoid("First object");
        CloneNotSupportedExceptionAvoid obj2 = obj1.clone();

        System.out.println("Obj1 = " + obj1.toString());
        System.out.println("Obj2 = " + obj2.toString());

        separate();

        ListBuilder<Integer> lb = new ListBuilder<Integer>(new Integer[] { 1, 2, 3 }, new ArrayList<Integer>());
        lb.getList().stream().forEach(num -> System.out.print(num));
        System.out.print('\n');

        separate();

        IteratorUsing exampleIteratorUsing = new IteratorUsing(new String[] { "Hello", "from", "programm" });
        exampleIteratorUsing.print();

        separate();

        DependencyInjectionUsing.Dependency dep = new Dependency();
        dep.setId(10);

        DependencyInjectionUsing depUse = new DependencyInjectionUsing(dep);
        depUse.use();

        separate();

        TryWithResourcesUsing.use();
    }

    public static void separate() {
        System.out.println("==============");
    }
}
