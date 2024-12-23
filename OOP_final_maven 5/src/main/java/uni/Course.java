package uni;

import enums.CoursePeriod;
import enums.CourseType;
import lombok.Getter;
import lombok.Setter;
import users.Student;
import users.Teacher;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Course {

    private String courseCode;
    private String courseName;
    private CourseType courseType;
    private int creditWeight;
    private String courseFormula;
    private CoursePeriod coursePeriod;
    private String courseDepartment;
    private List<Student> students;
    private List<Teacher> teachers;

    public Course(String courseCode, String courseName, CourseType courseType, int creditWeight, String courseFormula, CoursePeriod coursePeriod, String courseDepartment) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseType = courseType;
        this.creditWeight = creditWeight;
        this.courseFormula = courseFormula;
        this.coursePeriod = coursePeriod;
        this.courseDepartment = courseDepartment;
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    // Getters and setters for fields
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getCreditWeight() {
        return creditWeight;
    }

    public String getCourseFormula() {
        return courseFormula;
    }

    public CoursePeriod getCoursePeriod() {
        return coursePeriod;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

}
