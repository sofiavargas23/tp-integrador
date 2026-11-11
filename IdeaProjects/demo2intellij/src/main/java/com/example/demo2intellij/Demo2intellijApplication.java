package ar.edu.undec.demo2intellij;

import com.example.demo2intellij.Turno;
import com.example.demo2intellij.TurnoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo2intellijApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo2intellijApplication.class, args);

    }
    @Bean
    CommandLineRunner demo() {
        return args -> {
        // todavia a mano, sin spring
        TurnoService turnoService = new TurnoService();
        Turno turno = turnoService.consultarTurno(1L);
        System.out.println("El turno es de : " + turno.getNombre() + " a las " + turno.getHorario() + " *** ");

        };
    }
}


