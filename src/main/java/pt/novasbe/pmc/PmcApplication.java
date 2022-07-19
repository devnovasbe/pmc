package pt.novasbe.pmc;

import pt.novasbe.pmc.bidding.pmcMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pt.novasbe.pmc.validacao.CruzaCursosPlanosPMC;

@SpringBootApplication
public class PmcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PmcApplication.class, args);

        //19/07/2022
        //validação da Estrutura PMC -> novos Ramos&Alterações
        CruzaCursosPlanosPMC crz = new CruzaCursosPlanosPMC();
        crz.cruzaInfo();

      pmcMain pm = new pmcMain();
        /*pm.loadPMC(academicos.AnoLectivo, academicos.semestreBidding);*/
        System.out.println("Fim !! Hello World! >:->");

    }

} // fim main
