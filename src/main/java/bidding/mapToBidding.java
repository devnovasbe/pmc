package bidding;

import data.selectsMySql;
import data.selectsSIGES;
import data.updateMySql;
import pojo.ObjPMC;
import pojo.ObjSIGESToBID;
import utils.utils;

public class mapToBidding {

    utils ut = new utils();
    updateMySql upd = new updateMySql();
    selectsMySql smy = new selectsMySql();
    selectsSIGES scs = new selectsSIGES();



    public void iniciaSyncPMC (ObjSIGESToBID objBid){

        // 1 - Validar se está em condições de Mapear
        //boolean ok = validaIntegridade(objBid);

        String upStatusPMC ="";
        boolean mapeada = preparaLinha(objBid);




    } // Fim método


    private boolean preparaLinha  (ObjSIGESToBID objBid){

        boolean mapeada = false;
        ObjPMC pmc = new ObjPMC();
        boolean grp = ut.temGrupo(objBid); // tem grupo ?
        int idLinha = objBid.getKey();

        //db_integrator.

        // PROGRAM - CURSO
        int programBD = -1;
        String progBid = "select id from db_integrator.programs where code = "+objBid.getCdCurso()+"";
        programBD = smy.devolveChaveInt(progBid,"ID");

        // COURSE - DISCIPLINA
        int courseBD = 0;
        int codDiscplina = objBid.getCdCourseBid(); // ** ATENÇÃO tem o codigo Bidding da cadeira
        String sqlCourse ="select ID from db_integrator.courses where code = "+codDiscplina+"";
        courseBD = smy.devolveChaveInt(sqlCourse,"ID");

        if(courseBD == -1){
            System.out.println("DEBUG - CADEIRA NAO EXISTE -> "+codDiscplina+"" );
            System.out.println(sqlCourse);
        }

        //PLANO
        int planoBD = -1;
        String planoBid = "select id from db_integrator.plans where idProgram = "+programBD+" and code = "+objBid.getCdPlano()+"";
        planoBD = smy.devolveChaveInt(planoBid,"id");


        //IDProgPlanTrack
        int idProgPlanTrak = -1;
        String programPlanTrack = "select id from db_integrator.tracks where idPlan ="+planoBD+" and code = "+objBid.getTrack()+"";
        idProgPlanTrak = smy.devolveChaveInt(programPlanTrack,"ID");


        //ECTS
        String ects =ut.convToStringDouble(objBid.getEcts());

        //Lectivo
        int idLectivoBD =0;
        String anoLectivo = "select ID from db_integrator.lectives where code = "+objBid.getLectivoBid()+"";
        idLectivoBD = smy.devolveChaveInt(anoLectivo, "ID");

        // TERM -> atencao as linhas
        int termBD = 0;
        String term = objBid.getSemestres();
        String sqlTermBD ="select ID from db_integrator.terms where code = '"+term+"'";
        termBD = smy.devolveChaveInt(sqlTermBD,"ID");

        if(termBD == -1){
            System.out.println("DEBUG - CADEIRA SEM SEMESTRE DEFINIDO -> "+term+"" );
            System.out.println(sqlTermBD);
        }

        // MAJOR
        int major = 0;
        int ramoBid = objBid.getRamoBid();
        String majorBid = "select id from db_integrator.majors where idProgramPlan = "+planoBD+" and code = "+ramoBid+"";
        major = smy.devolveChaveInt(majorBid, "ID");

        // NOVA ENTRADA
        // IDCOURSEGROUP

        int idCrGrp = 0;
        if(grp) {
            String courseGroup = "select id from db_integrator.coursesgroups where idPlan ="+planoBD+" and code = "+objBid.getCdGrupo()+"";
            idCrGrp = smy.devolveChaveInt(courseGroup, "ID");

            if(idCrGrp <= 0) {
                System.out.println("DEBUG");
            }

        }

        pmc.setIdProgramPlanTrack(idProgPlanTrak);
        pmc.setIdMajor(major);
        pmc.setIdCourse(courseBD);
        pmc.setEcts(objBid.getEcts());
        pmc.setIdCourseType(tipoBid(objBid));
        pmc.setIdLective(idLectivoBD);
        pmc.setIdTerm(termBD);
        pmc.setAvailable(objBid.getAvailable());

        if(grp){
            pmc.setCourseGroup(objBid.getCdGrupo());
            pmc.setCourseMother(objBid.getCdCourseMother());
            pmc.setIdcoursesgroup(idCrGrp);
        }


        mapeada = insereLinhaPMC(pmc, grp, idLinha,objBid);
        return mapeada;
    }

    //================================================================================================================//

    public boolean insereLinhaPMC (ObjPMC pmc, boolean grp, int idLinha, ObjSIGESToBID objBid){

        boolean op = false;
        boolean preTeste = false;

        String sqlIn = "";
        if(!grp){

            sqlIn = "Insert into db_integrator.programsmajorscourses "
                    + "(idProgramPlanTrack,idMajor,idCourse,ects,idCourseType,idLective,idTerm,available) VALUES "
                    + "("+pmc.getIdProgramPlanTrack()+","+pmc.getIdMajor()+","+pmc.getIdCourse()+","+pmc.getEcts()+"," +
                    "" +pmc.getIdCourseType()+","+pmc.getIdLective()+","+pmc.getIdTerm()+","+pmc.getAvailable()+")";

        } else {


            sqlIn = "Insert into db_integrator.programsmajorscourses "
                    + "(idProgramPlanTrack,idMajor,idCourse,ects,idCourseType,idLective,idTerm,available,courseGroup,courseMother,idcoursesgroup) VALUES "
                    + "("+pmc.getIdProgramPlanTrack()+","+pmc.getIdMajor()+","+pmc.getIdCourse()+","+pmc.getEcts()+","+pmc.getIdCourseType()+","
                    + ""+pmc.getIdLective()+","+pmc.getIdTerm()+","+pmc.getAvailable()+"," +
                    " "+pmc.getCourseGroup()+","+pmc.getCourseMother()+","+pmc.getIdcoursesgroup()+")";

        }

        int idPMC =  jaInserido(pmc,grp);
        boolean flag = false;
        preTeste = detectaPreProblemaInsercao(sqlIn);

        // insere na PMC
        if(idPMC < 1 && !preTeste) {
            //System.out.println(sqlIn);
            //Nao existe -> insere
            atraso();
            flag = upd.actualizaDados(sqlIn);

            if (flag) {
                flag = validaControloMid(idLinha,"S");
            }

        }
        if(idPMC > 0 ) {
            //Ja existe - Marca como repetida
            op = validaControloMid(idLinha,"R");

        }

        if(preTeste) {

            atraso();
            op = validaControloMid(idLinha, "F");
            //há problema existe um (-1) no sql
            System.out.println(sqlIn);
            System.err.println("ERRO- "+objBid.getCdCurso()+","+objBid.getCdPlano()+","+objBid.getCdDiscip()+"," +
                    ""+objBid.getCdDiscOp()+","+objBid.getCourseType()+","+objBid.getCdRamo()+","+objBid.getCdGrupo()+"");

        }
        op = flag;

        return op;
    }


    //================================================================================================================//

    //verifica se o tem semestre e coursetype definidos.
    private boolean  validaIntegridade (ObjSIGESToBID objPla) {

        boolean flag = false;
        boolean grp  =  ut.temGrupo(objPla); // tem grupo ?
        String sqlIntg = "";

        if (grp) {

            // valida se já existe
            sqlIntg = "Select id from db_integrator.tbl_pmc where cd_curso = '" + objPla.getCdCurso() + "' and cd_plano = '" + objPla.getCdPlano() + "' "
                    + " and cd_grupo = '" + objPla.getCdGrupo() + "' and cd_discip_opcional = '" + objPla.getCdDiscOp() + "' and track = '" + objPla.getTrack() + "'"
                    + " and cd_ramo_bid = '" + objPla.getRamoBid() + "' and cd_discip = '" + objPla.getCdDiscip() + "' " +
                    " and CD_DURACAO != ('NA') and courseType != ('NA')";

        } else {
            // valida se já existe

            sqlIntg = "Select id from db_integrator.tbl_pmc where cd_curso = '" + objPla.getCdCurso() + "' and cd_plano = '" + objPla.getCdPlano() + "' "
                    + " and cd_ramo_bid = '" + objPla.getRamoBid() + "' and cd_discip = '" + objPla.getCdDiscip() + "' "
                    + " and coursetype = '" + objPla.getCourseType() + "' and CD_DURACAO in ('" + objPla.getSemestres() + "') " +
                    " and CD_DURACAO != ('NA') and courseType != ('NA') ";

        }

        int id = smy.devolveChaveInt(sqlIntg, "ID");
        if(id > 0) flag = true;
        return flag;
    }


    //================================================================================================================//
    private int tipoBid (ObjSIGESToBID objPla){

        int tipo = 0;

        String tpSTr = objPla.getCourseType().toUpperCase();

        switch (tpSTr){

            case "M" : tipo = 1;break;
            case "E" : tipo = 2;break;
            case "C" : tipo = 3;break;
            case "O" : tipo = 4;break;
            case "P" : tipo = 5;break;
            case "W" : tipo = 6;break;
            case "D" : tipo = 7;break;
            case "S" : tipo = 8;break;

            default: tipo = 0;

        }

        return tipo;
    }
    //================================================================================================================//
    public int jaInserido(ObjPMC pmc, boolean grp){

        int id = 0;
        String sql ="";

        if(grp){
            sql = "select ID from db_integrator.programsmajorscourses  " +
                    "  WHERE idProgramPlanTrack ="+pmc.getIdProgramPlanTrack()+" AND "
                    + "idMajor = "+pmc.getIdMajor()+" AND idCourse= "+pmc.getIdCourse()+" AND idTerm = "+pmc.getIdTerm()+" " +
                    "  and idLective= "+pmc.getIdLective()+" and idcoursesgroup =  "+pmc.getIdcoursesgroup()+"";

        } else{
            sql = "select ID from db_integrator.programsmajorscourses  WHERE idProgramPlanTrack ="+pmc.getIdProgramPlanTrack()+" " +
                    "AND  idMajor = "+pmc.getIdMajor()+" AND idCourse= "+pmc.getIdCourse()+" " +
                    "AND idTerm = "+pmc.getIdTerm()+" and idLective= "+pmc.getIdLective()+"";

        }

        id = smy.devolveChaveInt(sql, "ID");


        return id;
    }// fim metodo

    private boolean validaControloMid (int idLinha, String status){

        boolean op = false;

        String upStatusPMC = "update db_integrator.tbl_pmc set importada = '"+status+"' where id = " + idLinha + " ";
        op = upd.actualizaDados(upStatusPMC);
        return op;
    }


    private boolean detectaPreProblemaInsercao (String linha){

        boolean flag = false;
        if(linha.contains("-1")) {
            flag = true;

        }
        return flag;
    }


    private void atraso (){
        // provoca um atraso aletorio

        // DELAY ALEATORIO
        //int delay = (int) (Math.random() * 6500) ;
        int delay = 700;

        try
        {
            Thread.sleep(delay);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    } // fim metodo

} // fim classe
