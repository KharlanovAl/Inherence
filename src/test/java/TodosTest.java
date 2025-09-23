import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodosTest {
    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchTasks() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить Хлеб");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Обсуждение проекта",
                "Проект Хлебозавод",
                "Завтра утром"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        // Поиск по "Хлеб"
        Task[] result = todos.search("Хлеб");
        Assertions.assertArrayEquals(new Task[]{simpleTask, epic, meeting}, result);

        // Поиск по "Молоко" - только Epic
        result = todos.search("Молоко");
        Assertions.assertArrayEquals(new Task[]{epic}, result);

        // Поиск по несуществующему запросу
        result = todos.search("Сыр");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void shouldSearchEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("Хлеб");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void shouldSearchCaseSensitive() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить Хлеб");
        Todos todos = new Todos();
        todos.add(simpleTask);

        // Поиск с учетом регистра
        Task[] result = todos.search("хлеб");
        Assertions.assertArrayEquals(new Task[]{}, result);

        result = todos.search("Хлеб");
        Assertions.assertArrayEquals(new Task[]{simpleTask}, result);
    }

    // Для полного покрытия

    @Test
    public void testAddSingleTask() {
        Todos todos = new Todos();
        Task task = new SimpleTask(1, "Test Task");

        todos.add(task);
        Task[] result = todos.findAll();

        assertEquals(1, result.length);
        assertEquals(task, result[0]);
    }

    @Test
    public void testAddMultipleTasks() {
        Todos todos = new Todos();
        Task task1 = new SimpleTask(1, "Task 1");
        Task task2 = new SimpleTask(2, "Task 2");
        Task task3 = new SimpleTask(3, "Task 3");

        todos.add(task1);
        todos.add(task2);
        todos.add(task3);
        Task[] result = todos.findAll();

        Assertions.assertArrayEquals(new Task[]{task1, task2, task3}, result);
    }

    @Test
    public void testFindAllEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.findAll();

        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testSearchWithSimpleTask() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Buy milk and bread");
        todos.add(task);

        Task[] result = todos.search("milk");
        Assertions.assertArrayEquals(new Task[]{task}, result);

        result = todos.search("bread");
        Assertions.assertArrayEquals(new Task[]{task}, result);

        result = todos.search("cheese");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testSearchWithEpic() {
        Todos todos = new Todos();
        String[] subtasks = {"Buy milk", "Buy bread", "Cook dinner"};
        Epic epic = new Epic(1, subtasks);
        todos.add(epic);

        Task[] result = todos.search("milk");
        Assertions.assertArrayEquals(new Task[]{epic}, result);

        result = todos.search("bread");
        Assertions.assertArrayEquals(new Task[]{epic}, result);

        result = todos.search("cheese");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testSearchWithMeeting() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(1, "Project discussion", "Important project", "Tomorrow");
        todos.add(meeting);

        Task[] result = todos.search("discussion");
        Assertions.assertArrayEquals(new Task[]{meeting}, result);

        result = todos.search("Important");
        Assertions.assertArrayEquals(new Task[]{meeting}, result);

        result = todos.search("random");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testSearchWithNullQuery() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Test task");
        todos.add(task);

        Task[] result = todos.search(null);
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testSearchCaseSensitive() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Important Project");
        todos.add(task);

        Task[] result = todos.search("important");
        Assertions.assertArrayEquals(new Task[]{}, result); // Регистрозависимый поиск

        result = todos.search("Important");
        Assertions.assertArrayEquals(new Task[]{task}, result);

        result = todos.search("PROJECT");
        Assertions.assertArrayEquals(new Task[]{}, result);

        result = todos.search("Project");
        Assertions.assertArrayEquals(new Task[]{task}, result);
    }

    @Test
    public void testSearchWithTasksHavingNullFields() {
        Todos todos = new Todos();

        // Задача с null полями
        SimpleTask taskWithNullTitle = new SimpleTask(1, null);
        Meeting meetingWithNullFields = new Meeting(2, null, null, "time");

        todos.add(taskWithNullTitle);
        todos.add(meetingWithNullFields);

        Task[] result = todos.search("test");
        Assertions.assertArrayEquals(new Task[]{}, result);

        result = todos.search(null);
        Assertions.assertArrayEquals(new Task[]{}, result);
    }

    @Test
    public void testMultipleAdditionsAndSearches() {
        Todos todos = new Todos();

        // Добавляем несколько задач
        Task[] expectedTasks = new Task[10];
        for (int i = 0; i < 10; i++) {
            SimpleTask task = new SimpleTask(i, "Task " + i);
            todos.add(task);
            expectedTasks[i] = task;
        }

        Task[] allTasks = todos.findAll();
        Assertions.assertArrayEquals(expectedTasks, allTasks);

        // Поиск существующих задач
        Task[] result = todos.search("Task 5");
        Assertions.assertArrayEquals(new Task[]{expectedTasks[5]}, result);

        // Поиск несуществующей задачи
        result = todos.search("Non-existent");
        Assertions.assertArrayEquals(new Task[]{}, result);
    }
}