# Курс Java PRO
_Компания: «Иннотех»_

_Дата проведения: 26.03.2024_ - _28.05.2024_

_Поток: 2_

## Задание 1
Реализовать приложение, способное «прогонять тесты».

Создать набор аннотаций: _**@Test**_, _@**BeforeSuite**_, _**@AfterSuite**_, которые могут добавляться на методы, **_@Test_** на обычные методы, **_@BeforeSuite_** и **_@AfterSuite_** только на статические.

Для аннотации **_@Test_** добавить параметр _priority_ (_int_ в пределах от 1 до 10 включительно). Если параметр не задан явно, то он равен 5.

Создать класс с набором методов, и разметить их созданными аннотациями.

Создать класс **TestRunner** со статическим методом _**runTests(Class c)**_.

Выполнение **_runTests(Class c)_** приводит к следующему:
- указанный класс загружается в память;
- происходит проверка, что методов с аннотациями **_@BeforeSuite_** не больше одного;
- происходит проверка, что методов с аннотациями **_@AfterSuite_** не больше одного.
  
**Примечание:** можно выполнить дополнительные проверки, которые посчитаете нужными:
 
- выполняется метод с аннотацией **_@BeforeSuite_**, если такой есть;
- выполняются методы с аннотациями **_@Test_** в соответствии с их приоритетом (вначале выполняются те методы, у которых приоритет выше);
- выполняется метод с аннотацией **_@AfterSuite_**, если такой есть.

*Дополнительно добавить аннотации **_@BeforeTest_** и **_@AfterTest_**, методы с такими аннотациями должны выполняться перед каждым и после каждого теста соответственно.

## Задание 2
Все задачи должны быть выполнены в одну строку, используя Stream API.
1. Реализовать удаление из листа всех дубликатов.
2. Найти в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
3. Найти в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9. В отличие от прошлой задачи здесь разные 10 считаются за одно число).
4. Имеется список объектов типа Сотрудник (имя, возраст, должность). Необходимо получить список имен 3-х самых старших сотрудников с должностью «Инженер» в порядке убывания возраста.
5. Имеется список объектов типа Сотрудник (имя, возраст, должность). Посчитаеь средний возраст сотрудников с должностью «Инженер».
6. Найти в списке слов самое длинное.
7. Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Построить хеш-мапу, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке.
8. Вывести в консоль строки из списка в порядке увеличения длины слова. Если слова имеют одинаковую длину, то должен быть сохранен алфавитный порядок.
9. Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом. Найти среди всех слов самое длинное. Если таких слов несколько, получить любое из них.

## Задание 3
Реализовать собственный пул потоков.

В качестве аргументов конструктора пулу передается его емкость (количество рабочих потоков).

Как только пул создан, он сразу инициализирует и запускает потоки.

Внутри пула очередь задач на исполнение организуется через _**LinkedList<Runnable>**_.

При выполнении у пула потоков метода execute(_**Runnable**_), указанная задача должна попасть в очередь исполнения, и как только появится свободный поток – должна быть выполнена.

Также необходимо реализовать метод _**shutdown()**_, после выполнения которого новые задачи больше не принимаются пулом (при попытке добавить задачу можно бросать IllegalStateException), и все потоки, для которых больше нет задач, завершают свою работу.

Дополнительно добавить метод **_awaitTermination()_** без таймаута, работающий аналогично стандартным пулам потоков.

## Задание 4

- развернуть локально postgresql БД, создать таблицу **_users_** (**_id_** bigserial primary key, _**username**_ varchar(255) unique);
- создать Maven проект и подключить к нему: драйвер **_**postgresql**_**, **_hickaricp_**, **_spring context_**;
- создать пул соединений в виде Spring бина;
- создать класс **_User_** (Long **_id_**, String _**username**_);
- реализовать в виде бина класс **_UserDao_**, который позволяет выполнять CRUD операции над пользователями;
- реализовать в виде бина **_UserService_**, который позволяет создавать, удалять, получать одного, получать всех пользователей из базы данных;
- создать **_SpringContext_**, получить из него бин **_UserService_** и выполнить все возможные операции.

## Задание 5

- Создать сервис, который хранит продукты клиентов.
- Хранение продуктов необходимо организовать как в прошлом домашнем задании (пока не подключаем стартеры для работы с БД).
- Продукт клиента: **_id_**, номер счета (**_account_**), баланс (**_balance_**), тип продукта (**_productType_** - счет, карта).
- Сервис должен хранить продукты.
- Сервис должен давать возможность: создавать продукты, обновлять продукты, запрашивать все продукты, запрашивать все продукты по **_userId_**, запрашивать продукт по **_productId_**.
- Создать **_ProductController_** для вышеперечисленных запросов, используя **_Spring Boot Starter Web_**.

## Задание 6

- Создать сервис продуктов (product-service) в одном проекте с платежным ядром (payment-service) в виде отдельных модулей.
- Добавить интеграцию платежного сервиса с сервисом продуктов через RestTemplate.
- Добавить возможность запрашивать продукты у платежного сервиса (клиент кидает запрос в платежный сервис, платежный сервис запрашивает продукты клиента у сервиса продуктов и возвращает клиенту результат).
- Добавить в процесс исполнения платежа выбор продукта, проверку его существования и достаточности средств на нем.
- Добавить возврат ошибок клиенту о проблемах как на стороне платежного сервиса, так и на стороне сервиса продуктов.

## Задание 7

Работа с реляционными базами данных через Spring Data JPA:
- Реализовать миграцию баз данных с помощью Flyway.
- Перенести логику работы с БД на Spring Data JPA и в платежном сервисе, и в сервисе продуктов.