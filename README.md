# Система управления зоопарком

## Функциональные возможности

1. **Управление животными**
2. **Управление вольерами**
3. **Перемещение животных между вольерами**
4. **Управление расписанием кормления**
5. **Статистика зоопарка**

## Архитектура

1. **Domain Layer**
   - Модели: Animal, Enclosure, FeedingSchedule
   - Value Objects: AnimalType, EnclosureType, Gender, FoodType
   - События: AnimalMovedEvent, FeedingTimeEvent

2. **Application**
   - AnimalManagementService
   - EnclosureManagementService
   - AnimalTransferService
   - FeedingOrganizationService
   - ZooStatisticsService

3. **Infrastructure**
   - In-memory хранилища данных
   - Конфигурация Spring

4. **Presentation**
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
- AnimalTransferAndFeedingIntegrationTest - тестирует сценарий перемещения и кормления животного