package ma.emsi.demospringang.services;

import jakarta.transaction.Transactional;
import ma.emsi.demospringang.entities.Payment;
import ma.emsi.demospringang.entities.PaymentStatus;
import ma.emsi.demospringang.entities.PaymentType;
import ma.emsi.demospringang.entities.Student;
import ma.emsi.demospringang.repositories.PaymentRepository;
import ma.emsi.demospringang.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository,PaymentRepository paymentRepository){
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, LocalDate date, double amount,
                               PaymentType type, String studentCode) throws IOException {

        Path folderPath = Paths.get(System.getProperty("user.home"),"emsi-data","payments");
        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);

        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"emsi-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date).type(type).student(student)
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentsStatus(PaymentStatus status, Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile( Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

}
