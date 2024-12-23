package users;

import database.DatabaaseeActions;
import enums.FacultyType;
import enums.StudentCourse;
import interfaces.ResearchActions;
import lombok.Getter;
import lombok.Setter;
import service.ResearchPaper;
import service.ResearchProject;
import uni.Course;
import uni.Marks;
import uni.Organisation;
import decorator.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import uni.Journal;

    @Setter
    @Getter
public class Student extends User  implements ResearchActions {
    private String studentId;
    private StudentCourse studentCourse;
    private FacultyType faculty;
    private double gpa;
    private int credits;
    private Marks marks;
    private Vector<Course> courses;
    private boolean isAResearcher = false;
    private List<ResearchPaper> researchPapers;
    private Map<Course, Marks> marksByCourse;

    public Student(int id, String username, String password, String name, String studentId, StudentCourse studentCourse, FacultyType faculty, double gpa, int credits, Marks marks){
        super(id, username, password, name);
        this.studentId = studentId;
        this.studentCourse = studentCourse;
        this.faculty = faculty;
        this.gpa = gpa;
        this.credits = credits;
        this.marks = new Marks(0,0,0);
        this.courses = new Vector<>();
        this.marksByCourse = new HashMap<>();
    }
        //Kamila-----------------
        public Marks getMarksForCourse(Course course) {
            Marks marks = marksByCourse.get(course);
            if (marks != null) {
                System.out.println("Marks for course " + course.getCourseName() + ": " + marks);
            } else {
                System.out.println("No marks available for course " + course.getCourseName());
            }
            return marks;
        }

        // Метод для добавления курса и соответств Marks
        public void addCourse(Course course) {
            if (!marksByCourse.containsKey(course)) {
                marksByCourse.put(course, new Marks(0, 0, 0));
            }
        }

        public Map<Course, Marks> getAllMarks() {
            return marksByCourse;
        }


    public void registerCourse(Course course){
        courses.add(course);
//        course.addStudent(this);
        System.out.println("Successfully registered!");
    }


    public void viewStudentCourses() {
        for (Course course : courses) {
            System.out.println("- " + course.getCourseName());
        }
    }


    public String getDetails() {
        return "StudentId: " + studentId + ", Student: " + super.getName() + ", GPA: " + gpa + ", Credits: " + credits;
    }
    public Organisation createOrganisation(int organisationId, String organisationName, String description, String specialization) {
        Student leaderWithRole = new LeaderDecorator(this, organisationName);
        Organisation organisation = new Organisation(organisationId, organisationName, description, specialization, leaderWithRole);
        System.out.println("Organisation created: " + organisationName + " by leader: " + leaderWithRole.getDetails());
        DatabaaseeActions.update("leader_organization", leaderWithRole);
        return organisation;

        /*Organisation scienceClub = student1.createOrganisation(1, "Science Club", "A club for science enthusiasts.", "Science")*/
    }
    public void participateOrganisation(Organisation organisation) {
        Student memberWithRole = new MemberDecorator(this, organisation.getOrganisationName());
        System.out.println(memberWithRole.getDetails() + " is participating in " + organisation.getOrganisationName());
        DatabaaseeActions.update("member_organization", memberWithRole);
    }

        public void markAttendance(Journal journal, int lessonId) {
            journal.markAttendance(lessonId, this.name);
            System.out.println("Student " + name + " marked attendance for lesson " + lessonId);
        }
// ------------------


    public void rateTeacher(Teacher teacher, int rating){
        teacher.addRating(rating);
        System.out.println(name + " rated " + teacher.getName() + " with a rating of" + rating);
    }

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