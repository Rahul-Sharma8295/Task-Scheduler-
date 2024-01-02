import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Task {
    private String name;
    private String description;
    private int priority;
    private String dueDate;

    public Task(String name, String description, int priority, String dueDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Task: " + name + "\nDescription: " + description + "\nPriority: " + priority + "\nDue Date: " + dueDate;
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully!");
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
                System.out.println("----------------------");
            }
        }
    }
}

class TaskScheduler {
    private Timer timer;
    private TaskManager taskManager;

    public TaskScheduler(TaskManager taskManager) {
        this.timer = new Timer();
        this.taskManager = taskManager;
    }

    public void scheduleTask(Task task) {
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Task Reminder: " + task.getName());
                    System.out.println("Description: " + task.getDescription());
                    System.out.println("Priority: " + task.getPriority());
                    System.out.println("Due Date: " + task.getDueDate());
                }
            }, TaskUtil.parseDueDate(task.getDueDate()));
            System.out.println("Task scheduled successfully!");
        } catch (Exception e) {
            System.out.println("Error scheduling task: " + e.getMessage());
        }
    }
}

class TaskUtil {
    public static long parseDueDate(String dueDate) throws Exception {
        // You can implement a more robust date parsing logic here
        return Long.parseLong(dueDate);
    }
}

public class TaskManagerApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskScheduler taskScheduler = new TaskScheduler(taskManager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. Display Tasks");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter priority (1-5): ");
                    int priority = scanner.nextInt();
                    System.out.print("Enter due date (in milliseconds): ");
                    String dueDate = scanner.next();

                    Task newTask = new Task(name, description, priority, dueDate);
                    taskManager.addTask(newTask);
                    taskScheduler.scheduleTask(newTask);
                    break;
                case 2:
                    taskManager.displayTasks();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
