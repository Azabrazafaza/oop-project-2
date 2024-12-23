package uni;

import enums.LessonType;
import lombok.Getter;
import lombok.Setter;
import users.Student;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class Journal {
    private List<LessonEntry> lessonEntries;
    private List<Student> students;
    public Journal() {
        this.lessonEntries = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    // Найти студента по имени
    public Student getStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    public void addLessonEntry(Lesson lesson, LocalDate date) {
        LessonEntry entry = new LessonEntry(lesson, date);
        lessonEntries.add(entry);
        System.out.println("Lesson entry added: " + entry);
    }

    public void markAttendance(int lessonId, String studentName) {
        for (LessonEntry entry : lessonEntries) {
            if (entry.getLesson().getLessonId() == lessonId) {
                entry.markAttendance(studentName);
                System.out.println("Attendance marked for " + studentName + " in lesson " + lessonId);
                return;
            }
        }
        System.err.println("Lesson with ID " + lessonId + " not found.");
    }

    // Добавить оценку студенту для конкретного урока
    public void addGradeToStudent(int lessonId, String studentName, int grade) {
        for (LessonEntry entry : lessonEntries) {
            if (entry.getLesson().getLessonId() == lessonId) {

                entry.addGrade(studentName, grade);
                System.out.println("Grade added for " + studentName + " in lesson " + lessonId + ": " + grade);
                return;
            }
        }
        System.err.println("Lesson with ID " + lessonId + " not found.");
    }



    // Получить список студентов, которые присутствовали на уроке
    public Set<String> getAttendanceForLesson(int lessonId) {
        for (LessonEntry entry : lessonEntries) {
            if (entry.getLesson().getLessonId() == lessonId) {
                return entry.getAttendance();
            }
        }
        System.err.println("Lesson with ID " + lessonId + " not found.");
        return Collections.emptySet();
    }



    public LessonEntry getLessonEntryById(int lessonId) {
        for (LessonEntry entry : lessonEntries) {
            if (entry.getLesson().getLessonId() == lessonId) {
                return entry;
            }
        }
        return null;
    }

    // Отобразить содержимое журнала
    public void displayJournal() {
        for (LessonEntry entry : lessonEntries) {
            System.out.println(entry);
        }
    }
}
