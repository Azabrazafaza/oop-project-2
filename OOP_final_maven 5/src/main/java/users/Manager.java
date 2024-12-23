package users;

import enums.ManagerType;
import interfaces.ManagerActions;
import uni.Course;
import service.Request;
import database.DatabaaseeActions;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import java.sql.ResultSet;
// kami interface interfaces.ManagerActions
public abstract class Manager extends Employee implements ManagerActions {
    private ManagerType type;
    private List<Student> students;
    private List<Course> courses;
    private List<Teacher> teachers;
    private List<String> news;
    private List<String> requestMessages;

    public Manager(int id,
                   String username,
                   String password,
                   String name,
                   ManagerType type) {
        super(id, username, password, name);
        this.type = type;
        this.students = new ArrayList<Student>();
        this.courses = new ArrayList<Course>();
        this.teachers = new ArrayList<Teacher>();
        this.news = new ArrayList<String>();
        this.requestMessages = new LinkedList<>();
    }

    @Override
    public void approveRegistration(Student student, Course course) {
        if (!course.getStudents().contains(student)) {
            course.getStudents().add(student);
            System.out.println("Student " + student.getName() + " registered to course " + course.getCourseName());
        } else {
            System.out.println("Student is already registered to this course.");
        }
    }
    //manage teachers to courses(adding, removing)
    @Override
    public void manageTeachers(Teacher teacher, String action,Course course) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter action to manage Teacher (add teacher/remove teacher): ");
        action = sc.nextLine().toLowerCase();
        switch (action.toLowerCase()) {
            case "add teacher":
                if (!course.getTeachers().contains(teacher)) {
                    course.getTeachers().add(teacher);// Нужно будет проверить работает ли оно так // так то работает но не с базой данных
                    System.out.println("Teacher " + teacher.getName() + " assigned to course " + course.getCourseName());
                } else {
                    System.out.println("Teacher is already assigned to this course.");
                }
                break;
            case "remove teacher":
                if (course.getTeachers().contains(teacher)) {
                    course.getTeachers().remove(teacher);
                    System.out.println("Teacher " + teacher.getName() + " removed from course " + course.getCourseName());
                } else {
                    System.out.println("Teacher is not found in this course.");
                }
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    //manage students to courses(adding, removing)
    @Override
    public void manageStudents(Student student, String action,Course course) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter action to manage Students (add student/remove student): ");
        action = sc.nextLine().toLowerCase();
        switch (action.toLowerCase()) {
            case "add student":
                if (!course.getStudents().contains(student)) {
                    course.getStudents().add(student);
                    System.out.println("Student " + student.getName() + " added to course " + course.getCourseName());
                } else {
                    System.out.println("Student is already part of this course..");
                }
                break;
            case "remove student":
                if (course.getStudents().contains(student)) {
                    course.getStudents().remove(student);
                    System.out.println("Student " + student.getName() + " removed from course " + course.getCourseName());
                } else {
                    System.out.println("Student is not found.");
                }
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    //Manage news(add,remove news)
    @Override
    public void manageNews(String newsItem,String action){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter action to manage News (add news /remove news): ");
        action = sc.nextLine().toLowerCase();

        switch (action.toLowerCase()) {
            case "add news":
                news.add(newsItem);
                System.out.println("News added: " + newsItem);
                break;
            case "remove news":
                if (news.remove(newsItem)) {
                    System.out.println("News removed: " + newsItem);
                } else {
                    System.out.println("News not found: " + newsItem);
                }
                break;
            default:
                System.out.println("Invalid action");
        }
    }


    // View student information
    @Override
    public void viewStudentInfo(Student student) {
        System.out.println("Student Info: " + student);
    }

    // View teacher information
    @Override
    public void viewTeacherInfo(Teacher teacher) {
        System.out.println("Teacher Info: " + teacher);
    }

    // Create reports
    @Override
    public void createReport() {
        System.out.println("Total Courses: " + courses.size());
        for (Course course : courses) {
            System.out.println("Course: " + course.getCourseName());
            System.out.println("Students: " + course.getStudents().size());
            System.out.println("Teachers: " + course.getTeachers().size());
        }
    }


    public void viewSignedRequest(List<Request> requests) throws SQLException {
        System.out.println("Signed Requests: ");
        List<ResultSet> res = DatabaaseeActions.findAll("requests");
        for (ResultSet i:res) {
            Map < String, String > reqData = new HashMap<>();
            reqData.put("signed",i.getString("TRUE"));
            
        }
        if (res.isEmpty()) {
            System.out.println("No signed requests");
        }
        else {
            for (ResultSet request : res) {
                System.out.println("- " + request);
            }
        }
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", sighned=" + type;
    }
    public Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    public Teacher findTeacherByName(String name) {
        for (Teacher teacher : teachers) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }

    public Course findCourseByName(String name) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        return null;
    }
}
