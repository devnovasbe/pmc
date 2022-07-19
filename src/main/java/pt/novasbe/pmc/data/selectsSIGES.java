package pt.novasbe.pmc.data;

/*
 * selects ao Oracle-SIGES
 * @author rui.spranger
 */

import pt.novasbe.pmc.academicos;
import pt.novasbe.pmc.pojo.ObjSIGESToBID;
import pt.novasbe.pmc.pojo.ObjVal;
import pt.novasbe.pmc.utils.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class selectsSIGES {

    // Objectos de acesso â BD.
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    //**************************************************************************

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

    public double  devolveChaveDouble  (String sql, String item){

        double cod = -1;
        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                cod  = infoBidd.getDouble(item);

            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return cod;
    }

    public List devolveListaChavesInt  (String sql, String item) {

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

    public List devolveListaChavesStrDois  (String sql, String item1, String item2){

        List chaves = new ArrayList();

        try {
            statement = getStatement();
            ResultSet infoBidd = statement.executeQuery(sql);

            while (infoBidd.next()){

                String[] linha = new String[2];
                linha[0]  = infoBidd.getString(item1);
                linha[1] = infoBidd.getString(item2);
                chaves.add(linha);
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

    public List devolvePeriodos (String sql, String item){



        List periodos = new ArrayList();

        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){

                String sem = info.getString(item);
                //Adiciona apenas periodos diferentes
                if (!periodos.contains(sem)){
                    periodos.add(sem);
                }

            } // fim while
        }  catch (Exception e) {
        } finally {
            close();
        }
        return periodos ;
    } // fim metodo

    public List devolvePeriodosDoPLANO (String sql, String campoSemestre){

        // Devolve os terms do plano (podem vir separados com virgulas)

        List periodos = new ArrayList();
        List terms = new ArrayList();

        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){

                String sem = info.getString(campoSemestre).toUpperCase();

                //Adiciona apenas periodos diferentes
                if (!periodos.contains(sem)){
                    periodos.add(sem);
                }

            } // fim while
        }  catch (Exception e) {
        } finally {
            close();
        }

        // DECOMPOR OS SEMESTRE(SE VIRGULA) **********************************

        for ( int i = 0; i < periodos.size(); i++){
            // para cada linha
            String sem = periodos.get(i).toString();


            //OS TERMS VEEM SEPARADOS POR VIRGULAS
            if(sem.contains(",")){

                String [] novos = sem.split(",");

                for(int j = 0; j < novos.length; j++){
                    terms.add(novos[j].trim());
                }


            } else {
                terms.add(sem.trim());
            }


        } // fim for

        return terms ;
    }
    //Devolve nome do Program
    public String devolveProgramDesc (ObjSIGESToBID objPla){
        String pDesc = "";

        String sql = "select nm_curso from cse.t_cursos where cd_curso = "+objPla.getCdCurso()+"";
        pDesc = devolveChaveStr(sql, "NM_CURSO");

        return pDesc;
    }

    //Devolve nome do RAMO (MAJOR)
    public String devolveMajorDesc (ObjSIGESToBID objPla){
        String plDesc = "";

        String sql = "select nm_ramo from cse.t_ramos where "
                + "cd_curso = "+objPla.getCdCurso()+" and cd_plano = "+objPla.getCdPlano()+" and cd_ramo = "+objPla.getCdRamo()+"";
        plDesc = devolveChaveStr(sql, "NM_RAMO");

        return plDesc;
    }

    public String nmGrupo (ObjSIGESToBID objPla){


        String nmGrupo = "";

        String sqlGrp = "select * from cse.t_tbgrupos  where "
                + "cd_grupo = "+objPla.getCdGrupo()+"";
        nmGrupo = devolveChaveStr(sqlGrp,"DS_GRUPO");

        return nmGrupo;

    }

    public String devolvePlanDesc (ObjSIGESToBID objPla){
        String plDesc = "";

        String sql = "select nm_plano from cse.t_planos where cd_curso = "+objPla.getCdCurso()+" and cd_plano = "+objPla.getCdPlano()+"";
        plDesc = devolveChaveStr(sql, "NM_PLANO");

        return plDesc;
    }


    public double devolveECTS (ObjSIGESToBID objPla){

        double ects = 0;
        boolean grupo = ut.temGrupo(objPla);


        String sqlEcts ="";
        if (!grupo){

            // "normal"
            sqlEcts = "select NR_CRE_EUR from BIDDING_COURSE_ECTS t WHERE cd_curso = "+objPla.getCdCurso()+" AND "
                    + "cd_discip = "+objPla.getCdDiscip()+" and cd_plano = "+objPla.getCdPlano()+" and cd_ramo ="+objPla.getCdRamo()+"" ;
        } else{

            // Tem Grupo -> optativa

            sqlEcts = "select NR_CRE_EUR from BIDDING_COURSE_ECTS t WHERE cd_curso = "+objPla.getCdCurso()+" AND "
                    + " cd_discip_opcional = "+objPla.getCdDiscOp()+" and cd_plano = "+objPla.getCdPlano()+" and cd_ramo ="+objPla.getCdRamo()+" " +
                    " and cd_grupo = "+objPla.getCdGrupo()+"" ;

        }
        ects = EctsDiscip(sqlEcts);

        return ects;
    }


    public double EctsDiscip  (String sqlEcts) {

        // Acesso a BD
        double ECTS =0;
        try {
            statement =  getStatement();


            ResultSet dadosAluno = statement.executeQuery(sqlEcts);

            while (dadosAluno.next()) {
                ECTS  = dadosAluno.getDouble("NR_CRE_EUR");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }

        return ECTS;
    }

    // Devolve resposta à VIEW BIDDING_RAMOS_GRUPOS
    public List coursesPlansMajors  (String sql){

        //controlo debug
        int contador = 0;
        int eleitas = 0;

        //Para a tabela ProgramsMajorsCourses
        utils ut = new utils();
        List infoPlans = new ArrayList();
        int key = 0;

        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){
                contador++;
                //nova alteração TAGS - FILTRO LIVRE
                String fl = info.getString("FILTRO_LIVRE");
                String[] tags = fl.split("#");
                ObjSIGESToBID objs = new ObjSIGESToBID();

                //objs.setPublica(info.getString("PUBLICO").trim());
                if(tags.length > 1) {
                    if (tags[1].equals("P")) {
                        objs.setPublica("P");
                    } else {
                        objs.setPublica("N");
                    }
                }

                if(tags.length == 1) {
                    objs.setPublica("N");
                }
                //cdCurso = info.getInt("CD_CURSO");
                objs.setCdCurso(info.getInt("CD_CURSO"));
                //cdPlano = info.getInt("CD_PLANO");
                objs.setCdPlano(info.getInt("CD_PLANO"));
                // cdRamo = info.getInt("CD_RAMO");
                objs.setCdRamo(info.getInt("CD_RAMO"));
                //cdDiscip = info.getInt("CD_DISCIP");
                objs.setCdDiscip(info.getInt("CD_DISCIP"));
                //cdGrupo = info.getInt("CD_GRUPO");
                objs.setCdGrupo(info.getInt("CD_GRUPO"));
                //cdDiscOp = info.getInt("CD_DISCIP_OPCIONAL");
                objs.setCdDiscOp(info.getInt("CD_DISCIP_OPCIONAL"));
                //ctTipDis  = info.getInt("CD_TIPDIS");
                objs.setCtTipDis(info.getInt("CD_TIPDIS"));
                //obrigatoria = info.getString("CD_OBRIGAT");
                objs.setObrigatoria(info.getString("CD_OBRIGAT"));
                //activa = info.getString("CD_ACTIVA").trim();
                objs.setActiva(info.getString("CD_ACTIVA").trim());
                //publica = info.getString("PUBLICO").trim();

                //nomeDis = ut.limpaPlica(info.getString("DS_DISCIP"));
                objs.setNomeDis(ut.limpaPlica(info.getString("DS_DISCIP")));

                Object ctype = new Object();
                String courseType= "NA";

                ctype = tags[0].toUpperCase();
                if(ctype != null ){
                    courseType = ctype.toString().trim();
                    objs.setCourseType(courseType);
                }


                // Track
                // NOTA: Estou a carregar o TRACK por defeito - pode ter que ser alterado posteriormente
                objs.setTrack("65");
                objs.setNomeTrk("Standard Track");
                // infoPlans.add(objs);

                // 29/06/2020 ALTERAÇÂO IMPORTANTE - PUBLICO É TRATADO NESTE PONTO
                if (objs.getPublica().equalsIgnoreCase("P")) {
                    infoPlans.add(objs);
                    eleitas++;
                }

            }
        }  catch (Exception e) {
            System.out.println(e);
        } finally {
            close();
        }

        System.out.println("Processadas -> "+contador+"");
        System.err.println("APURADAS -> "+eleitas+"");

        return infoPlans;
    }


    //VALIDACOES

    public List<ObjVal> devolveInfoGrupo  (String sql){

        List<ObjVal> grps = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){
                ObjVal val  = new ObjVal();

                val.setCd_grupo(info.getInt("CD_GRUPO"));
                val.setCd_curso(info.getInt("CD_CURSO"));
                val.setCd_plano(info.getInt("CD_PLANO"));
                val.setDsGrupo(info.getString("DS_GRUPO"));
                grps.add(val);


            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return grps ;
    }

    public List<ObjVal> devolveInfoMajor  (String sql){

        List<ObjVal> grps = new ArrayList<>();
        try {
            statement = getStatement();
            ResultSet info = statement.executeQuery(sql);

            while (info.next()){
                ObjVal val  = new ObjVal();

                val.setCd_grupo(info.getInt("CD_GRUPO"));
                val.setCd_curso(info.getInt("CD_CURSO"));
                val.setCd_plano(info.getInt("CD_PLANO"));
                val.setCd_ramo(info.getInt("CD_RAMO"));
                val.setNm_ramo(info.getString("NM_RAMO"));

                grps.add(val);


            } // fim while
        }   catch (Exception e) {
        } finally {
            close();
        }

        return grps ;
    }



    //==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_//
    //Acesso à BD SIGES ********************************************************

    private Statement getStatement() throws SQLException {
        connection = ConnectionFactorySIGES.getConnection();
        return connection.createStatement();
    }

    void close() {
        ConnectionFactorySIGES.close(resultSet);
        ConnectionFactorySIGES.close(statement);
        ConnectionFactorySIGES.close(connection);
    }



} // fim classe

