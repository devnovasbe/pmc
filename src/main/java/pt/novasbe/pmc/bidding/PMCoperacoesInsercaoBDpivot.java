package pt.novasbe.pmc.bidding;

import pt.novasbe.pmc.data.selectsMySql;
import pt.novasbe.pmc.data.selectsSIGES;
import pt.novasbe.pmc.data.updateMySql;
import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.academicos;
import pt.novasbe.pmc.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class PMCoperacoesInsercaoBDpivot {

    utils ut = new utils();
    updateMySql upd = new updateMySql();
    selectsMySql smy = new selectsMySql();
    selectsSIGES scs = new selectsSIGES();
    validaIntegridadePMC vipmc = new validaIntegridadePMC();
    semestresDisciplina sem = new semestresDisciplina();
    boolean grp = false;

    public boolean insereObjectoInfoPla(ObjSIGESToBID objPla){

        boolean flag = false;
        boolean anual = AnualExcepcao(objPla);
        grp = ut.temGrupo(objPla); // tem grupo ?


        // complementos OBJ ==============================================
        // Nome do Curso
        objPla.setProgramDescription(scs.devolveProgramDesc(objPla));

        // Nome do Plano
        objPla.setPlanDescription(scs.devolvePlanDesc(objPla));
        // Nome do Major
        objPla.setMajorDescription(scs.devolveMajorDesc(objPla));
        // Nome do Grupo
        objPla.setDsGrupo(scs.nmGrupo(objPla));

        //specs pt.novasbe.pmc.bidding
        if(grp){
            objPla.setCdCourseBid(objPla.getCdDiscOp());
        } else {
            objPla.setCdCourseBid(objPla.getCdDiscip());
        }
        objPla.setRamoBid(objPla.getCdRamo());


        // 18/07/2022** Excepções se ANUAL
        // ECTS da cadeira
        //Semestres - Determinar os semestres em que a cadeira é oferecida
        if(!anual) {
            objPla.setEcts(scs.devolveECTS(objPla));
            String semestres = sem.devolveSemestres(objPla);
            objPla.setSemestres(semestres);
        } else {
            //Se for anual
            objPla = devolveSemestreECTSAnual(objPla);
        }


        boolean ext = existe(objPla);
        if (!ext){

            // Insere na BD intermédia em função dos semestres
            String[] oferta = objPla.getSemestres().split(",");

            //Não existe Mas pode ter 'NA' na Duracao
            // Validar se é a mesma turma mas agora com turma definida



            //if(oferta.length > 0 && !oferta[0].equalsIgnoreCase("NA")){
            // Só adicionar as cadeiras com semestre definido
            if(oferta.length > 0 ){
                objPla.setAvailable("1");

                for (int i = 0; i < oferta.length; i++) {
                    insereLinha(objPla, academicos.AnoLectivo, grp, oferta[i]);
                }
                //System.out.println("DEBUG");

            }


        } else {
            System.err.println("REPETIDA");

        }
        return flag;
    } // fim metodo


    private boolean validaAlteracaoSIGES(ObjSIGESToBID obj, String lectivo, boolean grp) {
        boolean flag = false;


        return flag;
    }


    // INSERE NA TABELA INTERMEDIA
    public void insereLinha(ObjSIGESToBID obj, String lectivo, boolean grp, String cdDuracao){
        String sql ="";

        if(grp){

            sql = "INSERT INTO db_integrator.TBL_PMC"
                    + " (id_req, cd_curso,cd_plano,cd_ramo,cd_discip,cd_grupo,cd_discip_opcional,"
                    + "cd_tipdis,cd_obrigat,cd_activa,publico,ds_discip,ds_grupo, coursetype,track,ects,cd_duracao,cd_ramo_bid,"
                    + "available,cd_course_bid, cd_course_mother_bid,lectivo_bid,programDescription,planDescription,trackDescription,majorDescription)"
                    + " VALUES ("+obj.getKey()+",'"+obj.getCdCurso()+"','"+obj.getCdPlano()+"','"+obj.getCdRamo()+"','"+obj.getCdDiscip()+"'"
                    + ",'"+obj.getCdGrupo()+"','"+obj.getCdDiscOp()+"','"+obj.getCtTipDis()+"','"+obj.getObrigatoria()+"','"+obj.getActiva()+"','"+obj.getPublica()+"','"+obj.getNomeDis()+"'"
                    + ",'"+obj.getDsGrupo()+"','"+obj.getCourseType()+"','"+obj.getTrack()+"','"+obj.getEcts()+"','"+cdDuracao+"','"+obj.getRamoBid()+"','"+obj.getAvailable()+"'"
                    + ",'"+obj.getCdDiscOp()+"','"+obj.getCdDiscip()+"','"+lectivo+"','"+obj.getNomeDis()+"','"+obj.getPlanDescription()+"','"+obj.getNomeTrk()+"','"+obj.getMajorDescription()+"')";

        } else{
            sql = "INSERT INTO db_integrator.TBL_PMC"
                    + " (id_req, cd_curso,cd_plano,cd_ramo,cd_discip,"
                    + "cd_tipdis,cd_obrigat,cd_activa,publico,ds_discip,coursetype,track,ects,cd_duracao,"
                    + "cd_ramo_bid,available,cd_course_bid,lectivo_bid,programDescription,planDescription,trackDescription,majorDescription)"
                    + " VALUES ("+obj.getKey()+",'"+obj.getCdCurso()+"','"+obj.getCdPlano()+"','"+obj.getCdRamo()+"','"+obj.getCdDiscip()+"'"
                    + ",'"+obj.getCtTipDis()+"','"+obj.getObrigatoria()+"','"+obj.getActiva()+"','"+obj.getPublica()+"','"+obj.getNomeDis()+"'"
                    + ",'"+obj.getCourseType()+"','"+obj.getTrack()+"','"+obj.getEcts()+"','"+cdDuracao+"','"+obj.getRamoBid()+"','"+obj.getAvailable()+"'"
                    + ",'"+obj.getCdCourseBid()+"','"+lectivo+"','"+obj.getNomeDis()+"','"+obj.getPlanDescription()+"','"+obj.getNomeTrk()+"','"+obj.getMajorDescription()+"')";

        }

        System.out.println(sql);
        upd.actualizaDados(sql);


    }

//=============================================================================//




    private boolean existe (ObjSIGESToBID objPla){
        boolean flag = false;
        grp = ut.temGrupo(objPla); // tem grupo ?


        //================================================================================================================//
        String sqlExiste = "";

        //if(!objPla.getSemestres().equalsIgnoreCase("NA")) {


        if (grp) {

            // valida se já existe
            sqlExiste = "Select id from db_integrator.tbl_pmc where cd_curso = '" + objPla.getCdCurso() + "' and cd_plano = '" + objPla.getCdPlano() + "' "
                    + " and cd_grupo = '" + objPla.getCdGrupo() + "' and cd_discip_opcional = '" + objPla.getCdDiscOp() + "' and track = '" + objPla.getTrack() + "'"
                    + " and cd_ramo_bid = '" + objPla.getRamoBid() + "' and cd_discip = '" + objPla.getCdDiscip() + "' and CD_DURACAO in ('" + objPla.getSemestres() + "') ";

        } else {
            // valida se já existe

            sqlExiste = "Select id from db_integrator.tbl_pmc where cd_curso = '" + objPla.getCdCurso() + "' and cd_plano = '" + objPla.getCdPlano() + "' "
                    + " and cd_ramo_bid = '" + objPla.getRamoBid() + "' and cd_discip = '" + objPla.getCdDiscip() + "' "
                    + " and coursetype = '" + objPla.getCourseType() + "' and CD_DURACAO in ('" + objPla.getSemestres() + "') ";

        }

        int id = smy.devolveChaveInt(sqlExiste, "ID");
        if(id > 0) flag = true;

       /* } else {
            System.out.println("Não existem semestres definidos " +
                    "para a cadeira -> OP:"+objPla.getCdDiscOp()+", -> "+objPla.getCdDiscip()+" , TIPO: "+objPla.getCourseType()+"");
            // forçar a FLAG ?
        } */

        return flag;
    }


    //VAlida se a Cadeira é uma Anual com tratamento de Execpção
    private boolean AnualExcepcao(ObjSIGESToBID objPla){

        boolean flag =false;
        if(objPla.getCdDiscip() == academicos.anual1 || objPla.getCdDiscip()==academicos.anual2
                || objPla.getCdDiscOp() == academicos.anual1 || objPla.getCdDiscOp()==academicos.anual2 ) {
           flag = true;
        }
      return flag;
    }

    private ObjSIGESToBID devolveSemestreECTSAnual (ObjSIGESToBID objPla){

        //String ectsAnuais = "2260;S1;3.5;S2;3.5#2742;S1;3.5;S2;0";
        String[] cadeirasAnuais = academicos.ectsAnuais.split("#");
        int indexMax = academicos.ttAnuais;

        //REVER PARA O CASO DE CICLO DINAMICO - Por agora MARTELADA nos indices
        if(objPla.getCdDiscip() == academicos.anual1 || objPla.getCdDiscOp() == academicos.anual1){

            //indice 0 - 2260
            String linha = (String) cadeirasAnuais[0];
            String[] infoCad = linha.split(";");
            objPla.setEcts(3.5);
            objPla.setSemestres("S1,S2");

        }

        if(objPla.getCdDiscip()==academicos.anual2 || objPla.getCdDiscOp() == academicos.anual2){
            //indice 0 - 2742
            String linha = (String) cadeirasAnuais[1];
            String[] infoCad = linha.split(";");
            objPla.setEcts(3.5);
            objPla.setSemestres("S1,S2");

        }



        return objPla;
    }



//======================================================================================================================//


} // fim classe
