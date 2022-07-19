package pt.novasbe.pmc.bidding;

import pt.novasbe.pmc.data.selectsMySql;
import pt.novasbe.pmc.data.selectsSIGES;
import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class pmcMain {

    PMCoperacoesInsercaoBDpivot ins = new PMCoperacoesInsercaoBDpivot();
    validaIntegridadePMC vip = new validaIntegridadePMC();
    mapToBidding mtb = new mapToBidding();
    utils ut = new utils();



    public void loadPMC(String lectivo, String semestres){

        //==============================================================================/
        iniMst("202223","S1,T1,T2,S2,T3,T4","14","13");
        iniMst("202223","S1,T1,T2,S2,T3,T4","14","14");

        iniMst("202223","S1,T1,T2,S2,T3,T4","15","13");
        iniMst("202223","S1,T1,T2,S2,T3,T4","15","14");

        iniMst("202223","S1,T1,T2,S2,T3,T4","16","13");
        iniMst("202223","S1,T1,T2,S2,T3,T4","16","14");

        iniMst("202223","S1,T1,T2,S2,T3,T4","35","13");
        iniMst("202223","S1,T1,T2,S2,T3,T4","35","14");

        iniMst("202223","S1,T1,T2,S2,T3,T4","36","13");
        iniMst("202223","S1,T1,T2,S2,T3,T4","36","14");
        iniMst("202223","S1,T1,T2,S2,T3,T4","37","14");
        iniMst("202223","S1,T1,T2,S2,T3,T4","38","14");
        iniMst("202223","S1,T1,T2,S2,T3,T4","39","14");


        //======================================================================================/
        iniMst("202223","S1,T1,T2,S2,T3,T4","22","2");
        iniMst("202223","S1,T1,T2,S2,T3,T4","8","5");
        iniMst("202223","S1,T1,T2,S2,T3,T4","8","7");
        iniMst("202223","S1,T1,T2,S2,T3,T4","8","8");
        iniMst("202223","S1,T1,T2,S2,T3,T4","8","10");
        //======================================================================================//
    }

    private void iniMst(String lectivo, String semestre, String curso, String plano){

        System.out.println("A iniciar a inserção do PMC na tabela PIVOT");
        List dadosToPMC = new ArrayList();
        String dis = "*ALL"; // colocar "*ALL" para todos
        // String dis = "9652";

        // 0 - Cadeiras avulsas ;21680

        //1 - PREPARA OS DADOS PARA INSERCAO  //curso,plano 21650
       dadosToPMC  = devolveLista(lectivo, semestre, curso, plano, dis);

        // chave da OP deve ser um aleatório
        int idReq =   (int) (Math.random() * 3500) ;

        // carrega a tabela com dados apurados SIGES
        for (int i = 0; i < dadosToPMC.size(); i++){

            System.out.println("ciclo => "+i+" de "+dadosToPMC.size()+"");

            ObjSIGESToBID infoPla = (ObjSIGESToBID) dadosToPMC.get(i);
            infoPla.setKey(idReq);

            //** Muito importante - Esta Flag vai controlar se a cadeira está repetida
            boolean flagOkIns =false;
            //Cadeiras na shared area ou nos cursos 8/22 não precisam de validação
            if(infoPla.getCdRamo() == 0 || infoPla.getCdCurso() == 8 || infoPla.getCdCurso() == 22 ) {
                ins.insereObjectoInfoPla(infoPla);
            } else {

                flagOkIns = vip.insideSharedArea(infoPla);
                if (!flagOkIns) {
                    ins.insereObjectoInfoPla(infoPla);
                } else {
                    System.err.println("TEMOS REPETIDA -> " + infoPla.getCdDiscOp() + " => " + infoPla.getCdDiscip() + " ," +
                            " " + infoPla.getCdCurso() + " => " + infoPla.getCdPlano() + " => " + infoPla.getCdRamo() + "");
                }
            }


        } // fim for

//===================================================================================================================//
//===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===//
        // fase de mapeamento siges - pt.novasbe.pmc.bidding
        // Para cada disciplina criar a entrada no PMC
        List dadosSmy = new ArrayList();
      dadosSmy = devolveListaSmY(lectivo, semestre, curso, plano, dis);

        for (int k = 0; k < dadosSmy.size(); k++){

            ObjSIGESToBID uCurr = (ObjSIGESToBID) dadosSmy.get(k);
            System.out.println("A INSERIR NO PMCBIDDING -> "+uCurr.getCdDiscip()+" ou /"+uCurr.getCdDiscOp()+" -> "+k+" de "+dadosSmy.size()+" ");
            mtb.iniciaSyncPMC(uCurr);

        } // FIM FOR


    } //fim método

    public  List devolveLista(String lectivo, String semestre, String curso, String plano, String dis) {


        List pmc = new ArrayList();


        selectsSIGES scs = new selectsSIGES();
        String sql ="";


        if(dis.equalsIgnoreCase("*ALL")) {
            sql = "select * from BIDDING_RAMOS_GRUPOS  where (cd_curso in(" + curso + ") and cd_plano in (" + plano + "))"
                    + " and  FILTRO_LIVRE IS NOT NULL   "
                   // + " and ((cd_discip >= 9500 and cd_discip <= 9999 ) or (cd_discip_opcional >= 9500 and cd_discip_opcional <= 9999))  "
                    + " order by cd_curso, cd_plano,cd_ramo";
        } else  {

            sql = "select * from BIDDING_RAMOS_GRUPOS  where (cd_curso in(" + curso + ") and cd_plano in (" + plano + "))"
                    + " and (cd_discip in ("+dis+") or cd_discip_opcional in ("+dis+"))"
                    + " and  FILTRO_LIVRE IS NOT NULL "
                    //+ " and cd_discip not in (4500,4501,4502,4503,4504,4505,4506,4507)"
                    + " order by cd_curso, cd_plano,cd_ramo";
        }



        pmc = scs.coursesPlansMajors(sql);
        return pmc;
    }

    //===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===_===//
    private List devolveListaSmY(String lectivo, String semestre, String curso, String plano, String dis) {

        List pmcY = new ArrayList();

        selectsMySql smy = new selectsMySql();
        String sql ="";
        sql = "select * from db_integrator.tbl_pmc where" +
                " importada not in  ('S','R') and cd_duracao != 'NA'" ;

        if(!dis.equalsIgnoreCase("NA")) {
            sql = "select * from db_integrator.tbl_pmc  where (cd_curso in(" + curso + ") and cd_plano in (" + plano + ") " +
                    "and cd_duracao != 'NA' and coursetype != 'NA')  and importada in ('N','F') " +
                    "order by cd_curso, cd_plano,cd_ramo";

        } else {

            sql = "select * from db_integrator.tbl_pmc  where (cd_curso in(" + curso + ") and cd_plano in (" + plano + ") )" +
                    "and (cd_discip in ("+dis+") or cd_discip_opcional in ("+dis+"))" +
                    "and cd_duracao != 'NA' and coursetype != 'NA'  and importada in ('N','F') " +
                    "order by cd_curso, cd_plano,cd_ramo";
        }

        pmcY = smy.coursesPlansMajorsSmy(sql);
        if(pmcY.size() > 0 ){
            System.out.println("DEBUG");
        }
        return pmcY;
    }


} // fim classe


