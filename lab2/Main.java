package org.example;
import java.util.*;

import static java.lang.Math.sqrt;

class TaskQueue {
    private final Queue<Integer> tasks = new LinkedList<>();
    private boolean closed = false;

    public synchronized void addTask(int task) {
        tasks.add(task);
        notify(); // powiadamia oczekujące wątki
    }

    public synchronized int getTask() throws InterruptedException {
        while (tasks.isEmpty() && !closed) {
            wait(); // czeka na pojawienie się nowego zadania lub zamknięcie kolejki
        }
        if (closed && tasks.isEmpty()) {
            throw new InterruptedException("Queue closed");
        }
        return tasks.poll(); //pop_front i zwroc tego wartosc
    }

    public synchronized void close() {
        closed = true;
        notifyAll(); // powiadamia wszystkie oczekujące wątki
    }
}

class TaskHandler implements Runnable {
    private final Map<Integer, Boolean> results = new LinkedHashMap<>();
    private final TaskQueue tasks;

    TaskHandler(TaskQueue tasks) {
        this.tasks = tasks;
    }

    public synchronized void addResult(int taskId, boolean result) {
        results.put(taskId, result);
    }

    public synchronized void printResults() {
        System.out.println("Results:");
        for (Map.Entry<Integer, Boolean> entry : results.entrySet()) {
            System.out.println("Task " + entry.getKey() + " result: " + entry.getValue());
        }
    }

    public boolean isPrime(int value) throws InterruptedException {
        Thread.sleep((long) value/100);
        if (value <= 1)
            return false;
        for (int i = 2; i <= sqrt(value); i++) {
            if (value % i == 0)
                return false;
        }
        return true;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                int number = tasks.getTask();
                if(number == 0) //zakoncz
                    break;
                //System.out.println("Thread name: " + Thread.currentThread().getName());
                boolean prime = isPrime(number);
                addResult(number, prime);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        //w konfiguracji (3)
        int numberOfThreads = Integer.parseInt(args[0]);
        TaskQueue tasks = new TaskQueue();
        TaskHandler taskHandler = new TaskHandler(tasks);

        Scanner scan = new Scanner(System.in);
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(taskHandler);
            //threads[i].setName("Thread-" + (i + 1));
            threads[i].start();
        }
        // Read numbers from the user
        while (true) {
            int number = scan.nextInt();
            if (number == 0) {
                System.out.println("\nexit");
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                break; // Exit loop if user enters 0
            }

            tasks.addTask(number); // Add the number to the task queue
            //taskHandler.printResults();
        }

        // Close the task queue
        tasks.close();

        /*
        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for each thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

        // Wyświetlanie wyników
        taskHandler.printResults();

        // Zamykanie skanera
        scan.close();
    }
}

/*
100001
120000
2

123456
154608
129132
2
 */