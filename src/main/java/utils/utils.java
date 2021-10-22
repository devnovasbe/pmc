package utils;

import pojo.ObjSIGESToBID;

import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {

    // UTILITARIOS GERAIS
    //*************** CONVERTORES DE TIPOS ****************************************
    public int convToNumber (String valor){
        int num = -1;

        if(valor != null && valor !="") {
            num = Integer.parseInt(valor);
        }

        return num;

    }

    public String convToString (int num){
        String valor = "";
        valor = String.valueOf(num);
        return valor;
    }

    public String convToStringDouble(double db) {
        String valor = "0";
        valor = String.valueOf(db);
        return valor;
    }

    public double convToDouble(String valor) {
        double num = -1.0;

        if (valor != null && valor != "") {
            //num = Integer.parseInt(valor);
            num = Double.parseDouble(valor);
        }

        return num;

    }

    public String convBooleanTostring(boolean bl){

        String dado = "";
        if (bl) dado = "1";
        if (!bl) dado = "0";

        return dado;
    }

    public boolean  convStringToBoolean (String str){

        boolean bl = false;
        str = str.toLowerCase();
        if (str.equals("0")) bl = false;
        if (str.equals("1") ) bl = true;

        return bl;
    }

    public String convDoubleToStringDouble(double db) {
        String valor = "0";
        valor = String.valueOf(db);
        return valor;
    }


// FORMATOS DE DATAS **********************************************************

    public String getData (){
        //Devolve a Data do Sistema

        String strData = null;
        Date today = new Date();
        strData = formatOracleDate(today);
        return strData;
    }

    public String formatOracleDate (Date dataOr){

        String data ="";

        if (dataOr != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            data = sdf.format(dataOr).trim();
            // DEVOLVE A DATA EM FORMATO ORACLE
            return data;

        } else {
            return "NULL";
        }

    }

    public boolean temGrupo (ObjSIGESToBID objPla) {
        boolean temGrupo =false;
        int grp = objPla.getCdGrupo();

        if(grp > 0){
            temGrupo = true;
        }
        return temGrupo;

    }

    public String limpaPlica (String str){

        String linha = "";
        StringBuffer sbd = new StringBuffer();
        char ch ='0';
        int cmp = str.length();


        if (cmp > 0){

            for (int i=0; i <= cmp -1; i++){

                ch = str.charAt(i);
                if (ch == '\'' ){

                    ch = '´';

                }
                sbd.append(ch);

            }// fim for
            linha = sbd.toString();
        }


        return linha;

    }


} // fim classe
