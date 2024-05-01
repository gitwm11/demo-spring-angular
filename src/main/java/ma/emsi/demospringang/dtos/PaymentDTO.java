package ma.emsi.demospringang.dtos;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.demospringang.entities.PaymentStatus;
import ma.emsi.demospringang.entities.PaymentType;
import ma.emsi.demospringang.entities.Student;

import java.time.LocalDate;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class PaymentDTO {

    private  Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;

}
