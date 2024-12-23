package uni;
import enums.LessonType;


public class LessonFactory {
    public static Lesson createLesson(int lessonId, String topic, LessonType lessonType, Course course, String room) {
        if (lessonId <= 0) {
            throw new IllegalArgumentException("Lesson ID must be positive.");
        }
        if (topic == null || topic.isEmpty()) {
            throw new IllegalArgumentException("Topic cannot be null or empty.");
        }
        if (lessonType == null) {
            throw new IllegalArgumentException("Lesson sighned cannot be null.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (room == null || room.isEmpty()) {
            throw new IllegalArgumentException("Room cannot be null or empty.");
        }
        return new Lesson(lessonId, topic, lessonType, course, room);
    }
}


