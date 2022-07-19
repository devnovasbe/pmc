package pt.novasbe.pmc;

public interface academicos {
    // controlo - Lectivo Corrente
    //============ ATENCAO ==============================//
    String AnoLectivo ="202223";
    String semestreLectivo ="S1";
    String trimestresLectivos ="'T1','T2'";
    String trimestresLectivosS2 ="'T3','T4'";
    int anoLectivoInt = 202223;
    int  idHeader = 244;

    //** IMPORTANTE - VALIDAR SEMPRE - TRATAMENTO DE EXCEPÇÕES
    int ttAnuais = 2;
    int anual1 = 2260;
    int anual2 = 2742;
    String ectsAnuais = "2260;S1;3.5;S2;3.5#2742;S1;3.5;S2;0";

    //String tipoMassivo = "WPS";
    String tipoMassivo = "BIDDING";
    boolean listaFromFile = false;

    //BIDDING
    //==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==//
    int idrequesHeader = 1; // MASSIVO - ATENçÃO

    String cursosEmBidding = "(8,14,15,16,22,35,36,37,38,39)";
    String AnoLectivoBidding ="202223";
    String semestreBidding = "(S2,'T3','T4')";
    String MSTBidding = "(14,15,16,35,36,37,38,39)";
    String EXCHBidding = "8";
    String MIMBidding = "22";
    String planosMSTBidding = "(13,14)";
    String planosMIMBidding = "(2)";
    String planosEXCHBidding = "(4,5,7,8,9,10)";
    //controle do Range da QUERY pt.novasbe.pmc.bidding service1 para a listagem dos alunos
    String dataIngressoMST ="01/01/2015";
    String dataIngressoEXH ="01/01/2019";
    String dataIngressoMIM ="01/01/2019";
    String restricaoCadeiras = "9538,9539,9541";

    //==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==//
    //WorkProjects
    int cdWs = 2866;
    int tagWP_1  = 22;
    int tagWP_2  = 57;
    int tagWP_3  = 58;

    // INFO DEVOLVIDA NO CAMPO INTAKE*******************************************
    //FreeMover
    int fmSitAluS1 = 91;  // Se S1 e situaca = 91 -> FREE_MOVER
    int fmSitAluS2 = 92;  // Se S2 e situaca = 92 -> FREE_MOVER

    //Double Degree
    int ddIN = 85; // Se cd_situa_fin = 85 -> Double Degree_IN
    int ddOUT = 93; // Se cd_situa_fin = 93 -> Double Degree_OUT

    int eroutS1 = 81; // Se cd_situa_fin = 81 -> Exchange_S1
    int eroutS2 = 82; // Se cd_situa_fin = 82 -> Exchange_S2

    // Interrupção de estudos
    int  intuS1 = 23; // Se cd_situa_fin = 23 -> Interrupção
    int  intuS2 = 24; // Se cd_situa_fin = 24 -> Interrupção

    int  intuS1MIM = 46; // Se cd_situa_fin = 46 -> MIM OUT S1
    int  intuS2MIM = 47; // Se cd_situa_fin = 47 -> MIM OUT S2

    // FIM  CAMPO INTAKE********************************************************
    int StuPartTime = 79; // Se CD_TIP_ALU = 79
    //****************************************************************************
    // ELEGIVEL
    // Planos
    String planosMST ="13,14";
    String planosExh = "4,5,7,8,9,10";
    String planosMIM = "2";

    //**************************************************************************

    // TIPOS DE CADEIRAS FIXO

    String courseTypeMIM = "E";
    String courseTypeEXCH = "E";


    // WORK PROJECT  - CD_TIP_ALUno

    String wp1 = "22";
    String wp2 = "57";
    String wp3 = "58";
    String wpS2 = "(57)";

    // =======================================================================================================//

    //cadeiras  a tratar
    String wp = "2866";

    //MIM - 22
    String codeMIM = "22";
    //public static final String courseTypeMIM = "E";
    String planoMIM =  "2";
    String ramoMIM =  "0";
    String grupoMIM = "26";
    String maeMIM = "220200";

    // - 8
    String codeEXCH = "8";
    //public static final String courseTypeEXCH = "E";
    String ramoEXCH = "0";
    String grupoEXCH = "null";

    String maeEXCH = "null";

    // Track Standar Bidding
    int track = 65;
    //==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_==_//



} // fim interface
