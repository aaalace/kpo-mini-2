# Система управления зоопарком

## Функциональные возможности

1. **Управление животными**
   - Добавление/удаление животных
   - Изменение информации о животных
   - Лечение животных

2. **Управление вольерами**
   - Добавление/удаление вольеров
   - Просмотр информации о вольерах
   - Очистка вольеров

3. **Перемещение животных между вольерами**
   - С проверкой совместимости типа животного и вольера

4. **Управление расписанием кормления**
   - Создание расписания кормления животных
   - Отметка о выполнении кормления
   - Просмотр расписания кормлений

5. **Статистика зоопарка**
   - Общее количество животных
   - Количество здоровых/больных животных
   - Информация о заполненности вольеров
   - Статистика по кормлениям

## Архитектура

1. **Domain Layer (Ядро)**
   - Модели: Animal, Enclosure, FeedingSchedule
   - Value Objects: AnimalType, EnclosureType, Gender, FoodType
   - События: AnimalMovedEvent, FeedingTimeEvent

2. **Application Layer (Сервисы)**
   - AnimalManagementService
   - EnclosureManagementService
   - AnimalTransferService
   - FeedingOrganizationService
   - ZooStatisticsService

3. **Infrastructure Layer (Инфраструктура)**
   - In-memory хранилища данных
   - Конфигурация Spring

4. **Presentation Layer (API)**
   - Контроллеры для обработки HTTP запросов
   - DTO для передачи данных

## Запуск приложения

```bash
./gradlew bootRun
```

Документация к API доступна по адресу: `http://localhost:8080/swagger-ui.html`

## DDD концепции

- **Агрегаты**: Animal, Enclosure, FeedingSchedule
- **Value Objects**: AnimalType, EnclosureType, Gender, FoodType
- **Domain Events**: AnimalMovedEvent, FeedingTimeEvent
- **Repositories**: Интерфейсы для доступа к хранилищам данных
- **Services**: Сервисы для реализации бизнес-логики
- **Entities**: Основные сущности системы с уникальными идентификаторами

## Юнит тесты:
- AnimalManagementServiceTest - тестирует создание и управление животными
- AnimalTransferServiceTest - проверяет процесс перемещения животных между вольерами
- AnimalTest - тестирует доменную логику модели животного
 -FeedingScheduleTest - тестирует функциональность расписания кормления
- InMemoryAnimalRepositoryTest - проверяет работу репозитория животных

## Тесты e2e:
- AnimalControllerIntegrationTest - тестирует полный жизненный цикл животного от создания до удаления
- AnimalTransferAndFeedingIntegrationTest - тестирует e2e сценарий перемещения и кормления животного