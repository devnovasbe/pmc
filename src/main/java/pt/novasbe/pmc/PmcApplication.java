package pt.novasbe.pmc;

import bidding.pmcMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PmcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PmcApplication.class, args);
        pmcMain pm = new pmcMain();
        pm.loadPMC(academicos.AnoLectivo, academicos.semestreBidding);
        System.out.println("Fim !! Hello World! >:->");

    }

} // fim main
