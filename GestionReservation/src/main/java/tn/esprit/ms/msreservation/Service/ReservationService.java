package tn.esprit.ms.msreservation.Service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ms.msreservation.entities.Reservation;
import tn.esprit.ms.msreservation.repository.ReservationRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

  /*public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }*/
    public Reservation createReservation(Reservation reservation) throws WriterException {
        // Save the reservation to get the ID
        Reservation savedReservation = reservationRepository.save(reservation);

        // Generate QR code
        String qrContent = "Reservation Details:\n" +
                "ID: " + savedReservation.getId() + "\n" +
                "Stade ID: " + savedReservation.getIdStade() + "\n" +
                "Première équipe: " + savedReservation.getIdPremiereEquipe() + "\n" +
                "Deuxième équipe: " + savedReservation.getIdDeuxiemeEquipe() + "\n" +
                "Organisateur: " + savedReservation.getIdOrganisateur();
        String qrCodeBase64 = generateQRCode(qrContent);
        savedReservation.setQrCodeBase64(qrCodeBase64);

        // Save again with QR code
        return reservationRepository.save(savedReservation);
    }

   private String generateQRCode(String content) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            // Log the error and throw a runtime exception
            throw new RuntimeException("Failed to generate QR code image", e);
        }
        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existing = getReservationById(id);
        existing.setIdStade(reservation.getIdStade());
        existing.setDate(reservation.getDate());
        existing.setIdPremiereEquipe(reservation.getIdPremiereEquipe());
        existing.setIdDeuxiemeEquipe(reservation.getIdDeuxiemeEquipe());
        existing.setIdOrganisateur(reservation.getIdOrganisateur());
        return reservationRepository.save(existing);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
