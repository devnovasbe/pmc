package pojo;

public class ObjPMC {

    private int idProgramPlanTrack;
    private int idMajor;
    private int idCourse;
    private double ects;
    private int idCourseType;
    private int idLective;
    private int idTerm;
    private int courseGroup;
    private int courseMother;
    private int idcoursesgroup;
    private String available;
    private String repetidoForaTroncoComum;

    //=====================================================================================//


    public int getIdProgramPlanTrack() {
        return idProgramPlanTrack;
    }

    public void setIdProgramPlanTrack(int idProgramPlanTrack) {
        this.idProgramPlanTrack = idProgramPlanTrack;
    }

    public int getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(int idMajor) {
        this.idMajor = idMajor;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public double getEcts() {
        return ects;
    }

    public void setEcts(double ects) {
        this.ects = ects;
    }

    public int getIdCourseType() {
        return idCourseType;
    }

    public void setIdCourseType(int idCourseType) {
        this.idCourseType = idCourseType;
    }

    public int getIdLective() {
        return idLective;
    }

    public void setIdLective(int idLective) {
        this.idLective = idLective;
    }

    public int getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(int idTerm) {
        this.idTerm = idTerm;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public int getCourseGroup() {
        return courseGroup;
    }

    public void setCourseGroup(int courseGroup) {
        this.courseGroup = courseGroup;
    }

    public int getCourseMother() {
        return courseMother;
    }

    public void setCourseMother(int courseMother) {
        this.courseMother = courseMother;
    }

    public int getIdcoursesgroup() {
        return idcoursesgroup;
    }

    public void setIdcoursesgroup(int idcoursesgroup) {
        this.idcoursesgroup = idcoursesgroup;
    }

    public String getRepetidoForaTroncoComum() {
        return repetidoForaTroncoComum;
    }

    public void setRepetidoForaTroncoComum(String repetidoForaTroncoComum) {
        this.repetidoForaTroncoComum = repetidoForaTroncoComum;
    }

}
