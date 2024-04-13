package ru.freeomsk.task3;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleThreadPool {
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private final Thread[] workers;
    private volatile boolean isShutdownInitiated = false;

    public SimpleThreadPool(int numberOfThreads) {
        workers = new Thread[numberOfThreads];
        if (numberOfThreads < 1) {
            throw new IllegalArgumentException("Количество потоков должно быть больше нуля.");
        }
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i] = new Thread(() -> {
                while (!isShutdownInitiated || !taskQueue.isEmpty()) {
                    Runnable task;
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty() && !isShutdownInitiated) {
                            try {
                                taskQueue.wait();
                            } catch (InterruptedException ignored) {}
                        }
                        if (isShutdownInitiated && taskQueue.isEmpty()) {
                            break;
                        }
                        task = taskQueue.poll();
                    }
                    try {
                        if (task != null) {
                            task.run();
                        }
                    } catch (RuntimeException e) {
                        System.err.println("Произошла ошибка во время выполнения задачи: " + e.getMessage());
                    }
                }
            });
            workers[i].start();
        }
    }
    public synchronized void shutdown() {
        isShutdownInitiated = true;
        synchronized (taskQueue) {
            taskQueue.notifyAll(); // Уведомляем все ожидающие потоки
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdownInitiated) {
            throw new IllegalStateException("Пул потоков уже был остановлен. Новые задачи не принимаются.");
        }
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify(); // Уведомляем один из ожидающих потоков
        }
    }

    // Метод для ожидания завершения всех потоков
    public void awaitTermination() {
        for (Thread worker : workers) {
            try {
                worker.join(); // Ожидаем завершения каждого потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}