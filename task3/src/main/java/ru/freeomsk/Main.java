package ru.freeomsk;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Создаем пул потоков с некоторым количеством рабочих потоков
        SimpleThreadPool pool = new SimpleThreadPool(4);

        // Добавляем задачи для выполнения в пул потоков
        for (int i = 0; i < 10; i++) {
            int taskNumber = i;
            pool.execute(() -> {
                System.out.println("Выполняется задача " + taskNumber + " в потоке " + Thread.currentThread().getName());
                try {
                    // Имитация продолжительной работы
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Даем некоторое время на выполнение задач
        Thread.sleep(5000);

        // Инициируем остановку пула потоков. Это заставит пул перестать принимать новые задачи и корректно завершить текущие.
        pool.shutdown();

        // Ожидаем завершения всех задач в пуле перед выходом из программы
        pool.awaitTermination();
    }
}