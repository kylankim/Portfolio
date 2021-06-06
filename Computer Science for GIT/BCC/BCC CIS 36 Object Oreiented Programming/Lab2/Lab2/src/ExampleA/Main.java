package ExampleA;


/** /Library/Java/JavaVirtualMachines/jdk-10.0.2.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=51863:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/kylan/Desktop/Lab2/out/production/Lab2 ExampleA.Main
 32 Millisec was taken
 4 Millisec was taken
 1 Millisec was taken
 0 Millisec was taken
 0 Millisec was taken
 Overall:73 Millisec

 Process finished with exit code 0
 */

public class Main {
    public static void main(String[] args) {
        CustomerService a = new CustomerService();
        a.getList().add(100);
        a.getList().add(1000);
        a.getList().add(10000);
        a.getList().add(100000);
        a.getList().add(1000000);
        a.startWork();
        System.out.println("Overall:" + a.getTotalTime() + " Millisec");
        }
    }
