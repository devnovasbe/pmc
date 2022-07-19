package pt.novasbe.pmc.bidding;

import pt.novasbe.pmc.data.selectsMySql;
import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.utils.utils;

public class validaIntegridadePMC {

    /*
    REGRA: Não pode existir fora do tronco comum (RAMO = 0) cadeiras iguais com tipos diferentes.
     */
    selectsMySql smy = new selectsMySql();
    utils ut = new utils();

    public boolean insideSharedArea(ObjSIGESToBID objCad){

        boolean flag = false;
        boolean grp = false;
        grp = ut.temGrupo(objCad);

        String sql ="";

        if(grp) {

            sql = "select id from  db_integrator.tbl_pmc where cd_ramo = 0 AND cd_curso = " + objCad.getCdCurso() + " " +
                    "AND CD_PLANO = " + objCad.getCdPlano() + " AND publico = 'S' " +
                    "AND  cd_discip_opcional = " + objCad.getCdDiscOp() + "";
        } else  {

            sql = "select id from  db_integrator.tbl_pmc where cd_ramo = 0 AND cd_curso = " + objCad.getCdCurso() + " " +
                    "AND CD_PLANO = " + objCad.getCdPlano() + " AND publico = 'S' " +
                    "AND cd_discip = " + objCad.getCdDiscip() + " ";
        }


        int key = smy.devolveChaveInt(sql,"ID");
        if (key > 0) flag = true;

        return flag;
    }

} // fim classe
