package pt.novasbe.pmc.pojo;

public class ObjSIGESToBID {
    private int key = 0; // Controlo
    private int idReq = 0; // serie
    private int cdCurso = 0;
    private int cdPlano = 0;
    private int cdRamo = 0;
    private int cdDiscip = 0;
    private int cdGrupo = -1;
    private int cdDiscOp = -1;
    private int ctTipDis = -1;

    private String  obrigatoria = "";
    private String  activa="";
    private String publica = "";
    private String nomeDis = "";
    private String courseType= "";
    private String programDescription = "";
    private String planDescription	= "";

    //***********************************************//


    public int getIdReq() {
        return idReq;
    }

    public void setIdReq(int idReq) {
        this.idReq = idReq;
    }

    // TRACK
    private String track="";
    // Nome TRACK
    private String nomeTrk;

    //ECTS
    private double ects;
    //CD_RAMO_BID
    private int ramoBid=0;

    //CD_COURSE_BID
    private int cdCourseBid =0;
    //CD_COURSE_MOTHER_BID
    private int cdCourseMother = 0;
    //LECTIVO_BID
    private int lectivoBid =0;
    //MAJORDESCRIPTION
    private String majorDescription = "";
    //AVAILABLE
    private String available = "";

    //DS_GRUPO
    private String dsGrupo ="";
    //SEMESTRES - CD_DURACAO
    private String semestres = "";

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public int getCdCurso() {
        return cdCurso;
    }

    public void setCdCurso(int cdCurso) {
        this.cdCurso = cdCurso;
    }

    public int getCdPlano() {
        return cdPlano;
    }

    public void setCdPlano(int cdPlano) {
        this.cdPlano = cdPlano;
    }

    public int getCdRamo() {
        return cdRamo;
    }

    public void setCdRamo(int cdRamo) {
        this.cdRamo = cdRamo;
    }

    public int getCdDiscip() {
        return cdDiscip;
    }

    public void setCdDiscip(int cdDiscip) {
        this.cdDiscip = cdDiscip;
    }

    public int getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(int cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    public int getCdDiscOp() {
        return cdDiscOp;
    }

    public void setCdDiscOp(int cdDiscOp) {
        this.cdDiscOp = cdDiscOp;
    }

    public int getCtTipDis() {
        return ctTipDis;
    }

    public void setCtTipDis(int ctTipDis) {
        this.ctTipDis = ctTipDis;
    }

    public String getObrigatoria() {
        return obrigatoria;
    }

    public void setObrigatoria(String obrigatoria) {
        this.obrigatoria = obrigatoria;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getPublica() {
        return publica;
    }

    public void setPublica(String publica) {
        this.publica = publica;
    }

    public String getNomeDis() {
        return nomeDis;
    }

    public void setNomeDis(String nomeDis) {
        this.nomeDis = nomeDis;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    //*************************************************//


    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public double getEcts() {
        return ects;
    }

    public void setEcts(double ects) {
        this.ects = ects;
    }

    public int getRamoBid() {
        return ramoBid;
    }

    public void setRamoBid(int ramoBid) {
        this.ramoBid = ramoBid;
    }

    public int getCdCourseBid() {
        return cdCourseBid;
    }

    public void setCdCourseBid(int cdCourseBid) {
        this.cdCourseBid = cdCourseBid;
    }

    public int getCdCourseMother() {
        return cdCourseMother;
    }

    public void setCdCourseMother(int cdCourseMother) {
        this.cdCourseMother = cdCourseMother;
    }

    public int getLectivoBid() {
        return lectivoBid;
    }

    public void setLectivoBid(int lectivoBid) {
        this.lectivoBid = lectivoBid;
    }

    public String getMajorDescription() {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        this.majorDescription = majorDescription;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDsGrupo() {
        return dsGrupo;
    }

    public void setDsGrupo(String dsGrupo) {
        this.dsGrupo = dsGrupo;
    }

    public String getSemestres() {
        return semestres;
    }

    public void setSemestres(String semestres) {
        this.semestres = semestres;
    }

    public String getNomeTrk() {
        return nomeTrk;
    }

    public void setNomeTrk(String nomeTrk) {
        this.nomeTrk = nomeTrk;
    }

}
