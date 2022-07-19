package pt.novasbe.pmc.bidding;

import pt.novasbe.pmc.data.selectsSIGES;
import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.academicos;
import pt.novasbe.pmc.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class semestresDisciplina {

    utils ut = new utils();

    public String devolveSemestres(ObjSIGESToBID objPla){

        String semsConcat ="NA";
        List periodos = new ArrayList();

        selectsSIGES scs = new selectsSIGES();

        String sqlTerm = "";

        // Só processa as disciplinas diferentes de WP
        if (!objPla.getCourseType().equalsIgnoreCase( "W")){

            boolean grupo = ut.temGrupo(objPla);

            if(!grupo) {
                sqlTerm = "select cd_duracao from CSE.T_TURMA where cd_lectivo = '" + academicos.AnoLectivo + "' and cd_discip = " + objPla.getCdDiscip() + "";

            } else {

                sqlTerm = "select cd_duracao from CSE.T_TURMA where cd_lectivo = '" + academicos.AnoLectivo + "' and cd_discip = " + objPla.getCdDiscOp() + "";
            }
            periodos = scs.devolvePeriodos(sqlTerm, "CD_DURACAO");
            //======================================================================================================//
            if (periodos.size() == 0) {


                // Nesta situação vai devolver apenas um periodo NOTA*** não resolve a questão do plano 120
                /*periodos = scs.devolvePeriodosNull(sqlTerm);*/
                periodos.add("NA"); // flag para avisar que não temos periodos
            }


        } // fim if#1


        if (objPla.getCourseType().equalsIgnoreCase("W")){
            periodos.add("S1"); // para "remediar Work Projects senão não vinha nenhum"
        }

        //**************************************************************************
        // INJECTA A SUBSTITUIÇÂO DA ANUAL 15/12/2015 -> anulada 18/07/2022
       /* if(periodos.contains("A")){
            periodos.clear();
            periodos.add("S1");
        }*/

        //devolve os periodos em que a cadeira é oferecida
        // nova alteração 13-12-2016
        if(periodos.size() > 1 ){
            semsConcat = concatenaLista(periodos);
        }

        if(periodos.size() == 1 ){
            semsConcat = (String)periodos.get(0);
        }

        return semsConcat;
    }

    //*******************************************************************************************//

    // NOVA VERSÂO - SEMESTRES QUANDO NÂO HA DIST. SERVIÇOS MAS ESTÀ NO PLANO (explo - modulos)

    public String devolveSemestresDoPlano (String[] linha, String lectivoBid, String semestre){

        String semsConcat ="NA";
        List semestres = new ArrayList();
        String tipo = linha[13].trim();
        selectsSIGES scs = new selectsSIGES();
        boolean grp = false;

        if (!linha[5].equals("") && !linha[5].equals("0")){
            grp = true;
        }

        // OS TIPO W São martelados depois
        if (tipo != "W"){

            String sqlTerm = "";

            if (!grp){

                sqlTerm = "select CD_DURACAO  from BIDDING_TAB_A where cd_curso = "+linha[1]+" and  cd_plano = "+linha[2]+" and cd_ramo = "+linha[3]+""
                        + "and cd_discip = "+linha[4]+"";
                semestres = scs.devolvePeriodosDoPLANO(sqlTerm,"CD_DURACAO");


            } else{

                sqlTerm = "select CD_DURACAO_TAB_B  from bidding_TAB_B where cd_curso = "+linha[1]+" and  cd_plano = "+linha[2]+" "
                        + "and cd_ramo = "+linha[3]+" and cd_discip = "+linha[6]+" and CD_GRUPO = "+linha[5]+"";

                semestres = scs.devolvePeriodosDoPLANO(sqlTerm,"CD_DURACAO_TAB_B");

            }

        }


        // nova alteração 13-12-2016
        if(semestres.size() > 1 ){
            semsConcat = concatenaLista(semestres);
        }

        if(semestres.size() == 1 ){
            semsConcat = (String)semestres.get(0);
        }

        return semsConcat;
    }


// AUXILIAR ********************************************************************

    private String concatenaLista(List sem){

        String coc = "";
        StringBuilder sbt = new StringBuilder();

        for(int i = 0; i < sem.size(); i ++){

            String info = (String) sem.get(i);

            sbt.append(info);
            if(i < sem.size()-1){
                sbt.append(",");
            }


        } // FIM FOR

        coc = sbt.toString();

        return coc;
    }

} // fim classe
