package users;

import database.DatabaaseeActions;
import enums.Teacheros;
import enums.UrgencyLevel;
import enums.LessonType;
import interfaces.ResearchActions;
import lombok.Getter;
import service.Message;
import service.ResearchPaper;
import service.ResearchProject;
import uni.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Teacher extends Employee implements ResearchActions {
    @Getter
    private Teacheros title;
    private List<Course> coursesTaught;
    @Getter
    private double rating;
    private List<Integer> studentRating;
    private boolean isAResearcher = false;
    private List<ResearchPaper> researchPapers;
    public Teacher(int id,
                   String username,
                   String password,
                   String name,
                   Teacheros title,
                   List<Course> coursesTaught,
                   double rating
    ) {
        super(id,username,password,name);
        this.title = title;
        this.coursesTaught = coursesTaught;
        this.rating = rating;
        this.studentRating = new ArrayList<>();
    }

    public void viewTaughtCourses() {
        for (Course course : coursesTaught) {
            System.out.println("- " + course.getCourseName());
        }
    }

    public void viewMarksForLesson(Journal journal, int lessonId) {
        System.out.println("Grades for lesson ID " + lessonId + ":");
        LessonEntry lessonEntry = journal.getLessonEntryById(lessonId);
        if (lessonEntry == null) {
            System.err.println("Lesson with ID " + lessonId + " not found.");
            return;
        }
        Map<String, Integer> grades = lessonEntry.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No grades assigned for this lesson yet.");
            return;
        }
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            System.out.println("Student: " + entry.getKey() + ", Grade: " + entry.getValue());
        }
    }


    public void sendRequest(Dean dean, Student student, String content, UrgencyLevel urgency) {
        Message message = new Message(this , dean, content);
        dean.receiveMessage(message);
        DatabaaseeActions.save("Message",message);
        System.out.println(name  + " sent message to " + dean.getName());

    }


    public void viewStudents(Course course) {
        System.out.println("Students enrolled in " + course.getCourseName() + ":");
        for (Student student : course.getStudents()) {
            System.out.println("- " + student.getName());
        }
    }


    public void addRating(int rating) {
        DatabaaseeActions.save("TeachrsRatings", this);
    }



    public Lesson createLesson(int lessonId, String topic, LessonType lessonType, Course course, String room) {
        Lesson lesson = LessonFactory.createLesson(lessonId, topic, lessonType, course, room);
        System.out.println("Lesson created by Teacher " + name + ": " + lesson);
        return lesson;
    }
    //Учитель создаёт уроки через фабрику
    // Lesson mathLesson1 = LessonFactory.createLesson(1, "Algebra", LessonType.LECTURE, course, "Room 101");


    public void addGrade(Journal journal, int lessonId, String studentName, float grade) {
        Student student = journal.getStudentByName(studentName);
        if (student != null) {
            student.getMarks().addMark(grade);
            System.out.println("Teacher " + name + " added grade " + grade + " for " + studentName + " in lesson " + lessonId);
        } else {
            System.err.println("Student " + studentName + " not found in journal.");
        }

    }
//-----------

    @Override
    public void publishPaper(ResearchPaper paper) {
        DatabaaseeActions.save("Researches",paper);
    }

    @Override
    public void manageProject(ResearchProject project) {
        DatabaaseeActions.update("ResearchProject",project );
    }

    @Override
    public int calculateHIndex() {
        researchPapers.sort((p1, p2) -> Integer.compare(p2.getCitations(), p1.getCitations()));
        int h = 0;
        for (int i = 0; i < researchPapers.size(); i++) {
            if (researchPapers.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }

        return h;
    }

    @Override
    public void becomeAResearchr() {
        isAResearcher = true;
    }
}