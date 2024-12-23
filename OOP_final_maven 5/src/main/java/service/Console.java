package service;

import database.DatabaaseeActions;
import enums.UrgencyLevel;
import users.*;
import uni.Course;
import users.Student;
import enums.UserType;
import java.util.Scanner;
import singleton.UserRegistry;

public class Console {
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the University System!");

        User loggedInUser = login();
        if (loggedInUser != null) {
            handleUserFunctions(loggedInUser);
        } else {
            System.out.println("Login failed. Exiting the system.");
        }
    }

    private User login() {
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        User user = UserRegistry.findUserByUsername(username);
        if (user != null) {
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            if (user.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return user;
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    private void handleUserFunctions(User user) {
        if (user instanceof Admin) {
            handleAdminFunctions((Admin) user);
        } else if (user instanceof Dean) {
            handleDeanFunctions((Dean) user);
        } else if (user instanceof Manager) {
            handleManagerFunctions((Manager) user);
        } else if (user instanceof Teacher) {
            handleTeacherFunctions((Teacher) user);
        } else if (user instanceof Student) {
            handleStudentFunctions((Student) user);
        } else if (user instanceof TechSupporter) {
            handleTechSupporterFunctions((TechSupporter) user);
        } else {
            System.out.println("Unknown user role. Exiting.");
        }
    }

    private void handleAdminFunctions(Admin admin) {
        boolean running = true;
        while (running) {
            System.out.println("Admin Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Remove User");
            System.out.println("4. See All Users");
            System.out.println("5. See Log Files");
            System.out.println("6. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    admin.createUser();
                    System.out.println("User created successfully.");
                    break;
                case "2":
                    System.out.print("Enter user ID to update: ");
                    int updateUserId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    User updatedUser = new Admin(updateUserId,
                            newUsername,
                            newPassword,
                            newName);
                    admin.updateUser(updatedUser);
                    System.out.println("User updated successfully.");
                    break;
                case "3":
                    System.out.print("Enter user ID to remove: ");
                    int removeUserId = Integer.parseInt(scanner.nextLine());
                    User userToRemove = new Admin(removeUserId, null, null, null);
                    admin.removeUser(userToRemove);
                    System.out.println("User removed successfully.");
                    break;
                case "4":
                    System.out.println("All Users:");
                    admin.seeAllUsers();
                    break;
                case "5":
                    System.out.println("Log Files:");
                    admin.seeLogFiles();
                    break;
                case "6":
                    System.out.println("Exiting admin menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleTeacherFunctions(Teacher teacher) {
        boolean running = true;
        while (running) {
            System.out.println("Teacher Menu:");
            System.out.println("1. View Courses");
            System.out.println("2. View Marks");
            System.out.println("3. Send Request");
            System.out.println("4. View Students: ");
            System.out.println("5. Grade Student");
            System.out.println("6. Exit");
            System.out.println("7. Exit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    teacher.viewTaughtCourses();
                    System.out.println("Courses viewed.");
                    break;
                case "2":
                    teacher.viewMarksForLesson(journal, lessonId);// добавьте сканнер
                    System.out.println("Marks viewed.");
                    break;

                case "3":
                    System.out.print("Enter dean ID: ");
                    int deanId = Integer.parseInt(scanner.nextLine());
                    Dean dean = Dean.getById(deanId);

                    System.out.print("Enter student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter content text: ");
                    String content = scanner.nextLine();
                    System.out.print("Enter urgency level(LOW, MEDIUM, HIGH): ");
                    UrgencyLevel urgency = UrgencyLevel.valueOf(scanner.nextLine().toUpperCase());
                    teacher.sendRequest(deanId, studentId, content,  urgency);
                    break;

                case "4":
                    System.out.print("Enter course ID: ");
                    int courseId = Integer.parseInt(scanner.nextLine());
                    viewStudents(Course course);
                    System.out.println("Student graded.");
                    break;
                case "5":
                    System.out.print("Enter student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter course ID: ");
                    int courseId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter grade: ");
                    double grade = Double.parseDouble(scanner.nextLine());
                    teacher.addGrade(journal, lessonId, studentName, grade); // изменить сканнеры
                    System.out.println("Student graded.");
                    break;
                case "7":
                    System.out.println("Exiting teacher menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleStudentFunctions(Student student) {
        boolean running = true;
        while (running) {
            System.out.println("Student Menu:");
            System.out.println("1. View Marks");
            System.out.println("2. View Courses");
            System.out.println("3. Add Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    student.getMarksForCourse();
                    System.out.println("Marks viewed.");
                    break;
                case "2":
                    student.viewStudentCourses();
                    break;
                case "3":
                    System.out.print("Enter course name to add: ");
                    Course courseName = scanner.nextLine();
                    student.addCourse(courseName);
                    System.out.println("Course added.");
                    break;
                case "4":
                    System.out.print("Enter course name to drop: ");
                    String dropCourseName = scanner.nextLine();
                    student.dropCourse(dropCourseName);
                    System.out.println("Course dropped.");
                    break;
                case "5":
                    System.out.println("Exiting student menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleTechSupporterFunctions(TechSupporter techSupporter) {
        boolean running = true;
        while (running) {
            System.out.println("Tech Support Menu:");
            System.out.println("1. Accept Ticket");
            System.out.println("2. Reject Ticket");
            System.out.println("3. View Tickets by Status");
            System.out.println("4. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    techSupporter.acceptTicket();
                    System.out.println("Ticket accepted.");
                    break;
                case "2":
                    techSupporter.rejectTicket();
                    System.out.println("Ticket rejected.");
                    break;
                case "3":
                    System.out.println("Viewing tickets by status.");
                    techSupporter.viewTicketsByStatus();
                    break;
                case "4":
                    System.out.println("Exiting tech support menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        Console console = new Console();
        console.run();
    }
}

