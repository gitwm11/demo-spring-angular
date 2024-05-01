package ma.emsi.demospringang;

import ma.emsi.demospringang.entities.Payment;
import ma.emsi.demospringang.entities.PaymentStatus;
import ma.emsi.demospringang.entities.PaymentType;
import ma.emsi.demospringang.entities.Student;
import ma.emsi.demospringang.repositories.PaymentRepository;
import ma.emsi.demospringang.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoSpringAngApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringAngApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        PaymentRepository paymentRepository){
        return args -> {
          studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                  .firstName("Yassine").code("1223456").programId("DSf4")
                  .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Amine").code("1225556").programId("DEf4")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Laila").code("4123456").programId("PSf4")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Fatima").code("1223498").programId("DTf4")
                    .build());

        PaymentType[] paymentTypes = PaymentType.values();
        Random random = new Random();
        studentRepository.findAll().forEach(st->{
            for (int i =0;i<40;i++){
                int index = random.nextInt(paymentTypes.length);
                Payment payment = Payment.builder()
                        .amount(1000+(int)(Math.random()*20000))
                        .type(paymentTypes[index])
                        .status(PaymentStatus.CREATED)
                        .date(LocalDate.now())
                        .student(st)
                        .build();
                paymentRepository.save(payment);

            }
        });
        };
        }

}
