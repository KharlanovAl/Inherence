import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodosTest {
    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
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

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchTasks() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить Хлеб");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
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

        // Поиск по "Хлеб" - должны найтись все три задачи
        Task[] result = todos.search("Хлеб");
        assertEquals(3, result.length);
        assertArrayEquals(new Task[]{simpleTask, epic, meeting}, result);

        // Поиск по "Молоко" - только Epic
        result = todos.search("Молоко");
        assertEquals(1, result.length);
        assertArrayEquals(new Task[]{epic}, result);

        // Поиск по несуществующему запросу
        result = todos.search("Сыр");
        assertEquals(0, result.length);
    }

    @Test
    public void shouldSearchEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("Хлеб");
        assertEquals(0, result.length);
    }

    @Test
    public void shouldSearchCaseSensitive() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить Хлеб");
        Todos todos = new Todos();
        todos.add(simpleTask);

        // Поиск с учетом регистра
        Task[] result = todos.search("хлеб");
        assertEquals(0, result.length); // Не найдено из-за разного регистра

        result = todos.search("Хлеб");
        assertEquals(1, result.length);
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

        assertEquals(3, result.length);
        assertArrayEquals(new Task[]{task1, task2, task3}, result);
    }

    @Test
    public void testFindAllEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.findAll();

        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithSimpleTask() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Buy milk and bread");
        todos.add(task);

        Task[] result = todos.search("milk");
        assertEquals(1, result.length);
        assertEquals(task, result[0]);

        result = todos.search("bread");
        assertEquals(1, result.length);
        assertEquals(task, result[0]);

        result = todos.search("cheese");
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithEpic() {
        Todos todos = new Todos();
        String[] subtasks = {"Buy milk", "Buy bread", "Cook dinner"};
        Epic epic = new Epic(1, subtasks);
        todos.add(epic);

        Task[] result = todos.search("milk");
        assertEquals(1, result.length);
        assertEquals(epic, result[0]);

        result = todos.search("bread");
        assertEquals(1, result.length);
        assertEquals(epic, result[0]);

        result = todos.search("cheese");
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithMeeting() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(1, "Project discussion", "Important project", "Tomorrow");
        todos.add(meeting);

        Task[] result = todos.search("discussion");
        assertEquals(1, result.length);
        assertEquals(meeting, result[0]);

        result = todos.search("Important");
        assertEquals(1, result.length);
        assertEquals(meeting, result[0]);

        result = todos.search("random");
        assertEquals(0, result.length);
    }
    @Test
    public void testSearchWithNullQuery() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Test task");
        todos.add(task);

        Task[] result = todos.search(null);
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchCaseSensitive() {
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Important Project");
        todos.add(task);

        Task[] result = todos.search("important");
        assertEquals(0, result.length); // Регистрозависимый поиск

        result = todos.search("Important");
        assertEquals(1, result.length);

        result = todos.search("PROJECT");
        assertEquals(0, result.length);

        result = todos.search("Project");
        assertEquals(1, result.length);
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
        assertEquals(0, result.length);

        result = todos.search(null);
        assertEquals(0, result.length);
    }

    @Test
    public void testMultipleAdditionsAndSearches() {
        Todos todos = new Todos();

        // Добавляем несколько задач
        for (int i = 0; i < 10; i++) {
            SimpleTask task = new SimpleTask(i, "Task " + i);
            todos.add(task);
        }

        assertEquals(10, todos.findAll().length);

        // Поиск существующих задач
        Task[] result = todos.search("Task 5");
        assertEquals(1, result.length);

        // Поиск несуществующей задачи
        result = todos.search("Non-existent");
        assertEquals(0, result.length);
    }
}
