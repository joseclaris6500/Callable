package Callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyCallable implements Callable<String> {

    //Static Integer Variable
    static int value = 0;
    //Instance variable called increment=
    public int increment;

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        value = increment + value;
        return Thread.currentThread().getName()
                + " The value =" + value;
    }

    public static void main(String[] args) {

        //Get ExcutorSrvice from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //Create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Creating the 5 Callable Ojbects
        Callable<String> callable1 = new MyCallable(5);
        Callable<String> callable2 = new MyCallable(3);
        Callable<String> callable3 = new MyCallable(-4);
        Callable<String> callable4 = new MyCallable(6);
        Callable<String> callable5 = new MyCallable(-2);

        //Submit Callable tasks to be executed by thread pool
        Future<String> future1 = executor.submit(callable1);
        Future<String> future2 = executor.submit(callable2);
        Future<String> future3 = executor.submit(callable3);
        Future<String> future4 = executor.submit(callable4);
        Future<String> future5 = executor.submit(callable5);
        //Add Future to the list, we can get return value using Future
        list.add(future1);
        list.add(future2);
        list.add(future3);
        list.add(future4);
        list.add(future5);

        for (Future<String> fut : list) {
            try {
                //Print the return value of Future, notice the output delay in console
                //because Future.get() waits for task to get completed
                System.out.println(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            executor.shutdown();
        }
//        System.out.println(value);
    }

    public MyCallable(int increment) {
        this.increment = increment;
    }

}
