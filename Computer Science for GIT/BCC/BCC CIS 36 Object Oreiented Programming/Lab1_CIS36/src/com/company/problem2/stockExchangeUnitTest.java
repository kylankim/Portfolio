package com.company.problem2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class stockExchangeUnitTest {

    /**
     * /Library/Java/JavaVirtualMachines/jdk-10.0.2.jdk/Contents/Home/bin/java -Didea.test.cyclic.buffer.size=1048576 "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60738:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA CE.app/Contents/plugins/junit/lib/junit-rt.jar:/Applications/IntelliJ IDEA CE.app/Contents/plugins/junit/lib/junit5-rt.jar:/Users/kylan/Downloads/Lab1_CIS36/out/production/Lab1_CIS36:/Users/kylan/Desktop/java/lib/gjdb.jar:/Users/kylan/Desktop/java/lib/hamcrest-core-1.3.jar:/Users/kylan/Desktop/java/lib/jpt.jar:/Users/kylan/Desktop/java/lib/junit-4.12.jar:/Users/kylan/Desktop/java/lib/tools.jar:/Users/kylan/Desktop/java/lib/ucb-checkstyle.jar:/Users/kylan/Desktop/java/lib/ucb-f2007.jar:/Users/kylan/Desktop/java/lib/ucb.jar" com.intellij.rt.execution.junit.JUnitStarter -ideVersion5 -junit4 com.company.problem2.stockExchangeUnitTest,ThreadTest
     * is working 2
     * is working 3
     * It took 1 steps to reach the target amount of price unit(1)
     *
     * java.util.ConcurrentModificationException
     * 	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:937)
     * 	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:891)
     * 	at com.company.problem2.StockExchange.run(StockExchange.java:81)
     * 	at com.company.problem2.stockExchangeUnitTest.ThreadTest(stockExchangeUnitTest.java:22)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
     * 	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
     * 	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
     * 	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
     * 	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
     * 	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
     * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
     * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
     * 	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
     * 	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
     * 	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
     * 	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
     * 	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
     * 	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
     * 	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
     * 	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
     * 	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
     * 	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
     * 	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
     */

    @Test
    public void ThreadTest(){
        StockMonitor a = new StockMonitor(1);
        StockMonitor b = new StockMonitor(2);
        StockMonitor c = new StockMonitor(3);
        StockMonitor d = new StockMonitor(4);
        StockMonitor e = new StockMonitor(5);

        StockPricePrinter f = new StockPricePrinter();
        StockExchange tmp = new StockExchange(30);
        tmp.getObserverList().addAll(List.of(a, b, c, d, e, f));
        tmp.run();




//        StockExchange ASD = new StockExchange(5);
//        PriceChangeEvent A = new PriceChangeEvent(3, ASD);
//        ASD.pricedChanged(A);
//        StockMonitor M1 = new StockMonitor(ASD.getCurrentPrice(), A.getChangeInPrice(), ASD.getCurrentPrice());
//        A.getReference().AddObserver();
//        ASD.run();
    }
}
