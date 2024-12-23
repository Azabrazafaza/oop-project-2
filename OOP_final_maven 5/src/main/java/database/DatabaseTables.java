package database;

public enum DatabaseTables {

    //
    //
    //
    //

    COURSE("course"),
    MESSAGES("messages"),
    NEWS("news"),
    ORGANIZATION_STUDENTS("organization_students"),
    ORGANIZATIONS("organizations"),
    REQUESTS("requests"),
    STUDENT_INFO("student_info"),
    USERS("users")










    ;

    String tableName;

    DatabaseTables(String name){
        tableName = name;
    }

    public String toString() {
        return this.tableName;
    }

}
