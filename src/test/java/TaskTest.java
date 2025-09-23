import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void shouldSimpleTask() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        assertTrue(simpleTask.matches("Позвонить"));
        assertTrue(simpleTask.matches("родителям"));
        assertFalse(simpleTask.matches("написать"));
        assertTrue(simpleTask.matches(" "));

    }

    @Test
    public void shouldMatchEpic() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        assertTrue(epic.matches("Молоко"));
        assertTrue(epic.matches("Яйца"));
        assertTrue(epic.matches("Хлеб"));
        assertFalse(epic.matches("Сыр"));
        assertFalse(epic.matches(" "));
    }

    @Test
    public void shouldMatchMeeting() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        assertTrue(meeting.matches("Выкатка"));
        assertTrue(meeting.matches("НетоБанка"));
        assertTrue(meeting.matches("приложения"));
        assertFalse(meeting.matches("среда"));
        assertTrue(meeting.matches(" "));
    }

    // Для полного покрытия
    @Test
    public void testTaskConstructorAndGetter() {
        Task task = new Task(1);
        assertEquals(1, task.getId());
    }

    @Test
    public void testTaskEqualsSameObject() {
        Task task1 = new Task(1);
        assertTrue(task1.equals(task1));
    }

    @Test
    public void testTaskEqualsDifferentObjectsSameId() {
        Task task1 = new Task(1);
        Task task2 = new Task(1);
        assertTrue(task1.equals(task2));
        assertTrue(task2.equals(task1));
    }

    @Test
    public void testTaskEqualsDifferentIds() {
        Task task1 = new Task(1);
        Task task2 = new Task(2);
        assertFalse(task1.equals(task2));
        assertFalse(task2.equals(task1));
    }

    @Test
    public void testTaskEqualsWithNull() {
        Task task = new Task(1);
        assertFalse(task.equals(null));
    }

    @Test
    public void testTaskEqualsDifferentClass() {
        Task task = new Task(1);
        String notATask = "not a task";
        assertFalse(task.equals(notATask));
    }

    @Test
    public void testTaskHashCode() {
        Task task1 = new Task(1);
        Task task2 = new Task(1);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void testTaskHashCodeDifferentIds() {
        Task task1 = new Task(1);
        Task task2 = new Task(2);
        assertNotEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void testTaskMatchesBaseImplementation() {
        Task task = new Task(1);
        assertFalse(task.matches("any query"));
        assertFalse(task.matches(null));
        assertFalse(task.matches(""));
    }
}
