package pt.novasbe.pmc.data;

import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.utils.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 *@author rui.spranger
 */

public class selectsMySql {

    // Objectos de acesso â BD.
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    //*****************************************************************************

    utils ut = new utils();

    // BÁSICAS =================================================================
    public String devolveChaveStr  (String sql, String item){

        String str = "";
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                str  = infoBidd.getString(item);

            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return str;
    }


    public int devolveChaveInt  (String sql, String item){

        int cod = -1;
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                cod  = infoBidd.getInt(item);

            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return cod;
    }

    public List devolveListaChavesInt  (String sql, String item){

        List chaves = new ArrayList();
        int cod = -1;
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                cod  = infoBidd.getInt(item);
                chaves.add(cod);
            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return chaves;
    }

    public List devolveListaChavesStr  (String sql, String item){

        List chaves = new ArrayList();
        String cod = "";
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                cod  = infoBidd.getString(item);
                chaves.add(cod);
            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return chaves;
    }


    //Nova Variante - Devolve uma chave int , mas o select passa STR
    public int devolveChaveStrSwitch  (String sql, String item){

        int key = 0;
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                key  = infoBidd.getInt(item);

            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return key;
    }

    public List devolveInfo2ArrayInt (String sql, String cp0, String cp1, String cp2, String cp3){

        List dados = new ArrayList();

        try {
            statement = getStatement();


            ResultSet info= statement.executeQuery(sql);

            while (info.next()){

                int[] linha = new int[4];

                linha[0] = info.getInt(cp0);
                linha[1] = info.getInt(cp1);
                linha[2] = info.getInt(cp2);
                linha[3] = info.getInt(cp3);

                dados.add(linha);
            }
        }   catch (Exception e) {
        } finally {
            close();
        }

        return dados;
    }

    public List coursesPlansMajorsSmy  (String sql){

        //Para a tabela ProgramsMajorsCourses
        utils ut = new utils();
        List infoPlans = new ArrayList();
        int key = 0;

        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){

                ObjSIGESToBID objs = new ObjSIGESToBID();
                objs.setKey(info.getInt("ID"));
                objs.setIdReq(info.getInt("ID_REQ"));

                //cdCurso = info.getInt("CD_CURSO");
                objs.setCdCurso(ut.convToNumber(info.getString("CD_CURSO")));
                //cdPlano = info.getInt("CD_PLANO");
                objs.setCdPlano(ut.convToNumber(info.getString("CD_PLANO")));
                // cdRamo = info.getInt("CD_RAMO");
                objs.setCdRamo(ut.convToNumber(info.getString("CD_RAMO")));
                //cdDiscip = info.getInt("CD_DISCIP");
                objs.setCdDiscip(ut.convToNumber(info.getString("CD_DISCIP")));
                //cdGrupo = info.getInt("CD_GRUPO");
                Object grupo = new Object();
                grupo = info.getObject("CD_GRUPO");
                if(grupo != null && !grupo.toString().equals("")){
                    objs.setCdGrupo(ut.convToNumber(grupo.toString()));
                }

                //objs.setCdGrupo(ut.convToNumber(info.getString("CD_GRUPO")));
                //cdDiscOp = info.getInt("CD_DISCIP_OPCIONAL");
                //objs.setCdDiscOp(ut.convToNumber(info.getString("CD_DISCIP_OPCIONAL")));
                Object opcDis = new Object();
                opcDis = info.getObject("CD_DISCIP_OPCIONAL");
                if(opcDis != null && !opcDis.toString().equals("")){
                    objs.setCdDiscOp(ut.convToNumber(opcDis.toString()));
                }

                //ctTipDis  = info.getInt("CD_TIPDIS");
                objs.setCtTipDis(ut.convToNumber(info.getString("CD_TIPDIS")));

                objs.setObrigatoria(info.getString("CD_OBRIGAT"));
                //activa = info.getString("CD_ACTIVA").trim();
                objs.setActiva(info.getString("CD_ACTIVA").trim());
                //publica = info.getString("PUBLICO").trim();
                objs.setPublica(info.getString("PUBLICO").trim());
                //nomeDis = ut.limpaPlica(info.getString("DS_DISCIP"));
                objs.setAvailable(info.getString("AVAILABLE").trim());

                objs.setNomeDis(ut.limpaPlica(info.getString("DS_DISCIP")));
                Object ctype = new Object();
                String courseType= "NA";
                ctype = info.getString("COURSETYPE");

                if(ctype != null && !ctype.equals("") ){
                    courseType = ctype.toString().trim();
                    objs.setCourseType(courseType);
                }

                objs.setDsGrupo(info.getString("DS_GRUPO"));
                objs.setTrack(info.getString("TRACK"));
                objs.setEcts(ut.convToDouble(info.getString("ECTS")));
                objs.setSemestres(info.getString("CD_DURACAO")); // ** ATENÇÂO

                // objs.setRamoBid(ut.convToNumber("CD_RAMO_BID"));
                Object crb = new Object();
                crb = info.getObject("CD_RAMO_BID");
                if(crb != null &&  !crb.toString().equals("")){

                    objs.setRamoBid(ut.convToNumber(crb.toString()));

                }


                // objs.setCdCourseBid(ut.convToNumber("CD_COURSE_BID"));
                Object courseBid = new Object() ;
                courseBid = info.getObject("CD_COURSE_BID");
                if(courseBid != null && !courseBid.equals("")){
                    objs.setCdCourseBid(ut.convToNumber(courseBid.toString()));
                }

                //objs.setCdCourseMother(ut.convToNumber("cd_course_mother_bid"));
                Object cm = new Object();
                cm = info.getObject("cd_course_mother_bid");
                if(cm !=null && !cm.toString().equals("")){
                    objs.setCdCourseMother(ut.convToNumber(cm.toString()));
                }


                // objs.setLectivoBid(ut.convToNumber("LECTIVO_BID"));
                Object lb = new Object();
                lb = info.getString("LECTIVO_BID");
                if(lb !=null && !lb.toString().equals("")){
                    objs.setLectivoBid(ut.convToNumber(lb.toString()));
                }


                objs.setProgramDescription(info.getString("PROGRAMDESCRIPTION"));
                objs.setPlanDescription(info.getString("PLANDESCRIPTION"));
                objs.setNomeTrk(info.getString("TRACKDESCRIPTION"));
                objs.setMajorDescription(info.getString("MAJORDESCRIPTION"));


                infoPlans.add(objs);

            }
        }  catch (Exception e) {
        } finally {
            close();
        }

        return infoPlans;
    }



    //==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==//

    //Acesso à BD MYSQL **********************************************************
    private Statement getStatement() throws SQLException {
        connection = ConnectionFactoryMySql.getConnection();
        return connection.createStatement();
    }


    void close() {
        ConnectionFactoryMySql.close(resultSet);
        ConnectionFactoryMySql.close(statement);
        ConnectionFactoryMySql.close(connection);
    }


} // fim classe
