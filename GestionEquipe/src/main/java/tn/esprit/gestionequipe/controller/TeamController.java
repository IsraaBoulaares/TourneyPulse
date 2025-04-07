package tn.esprit.gestionequipe.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionequipe.entities.ChatRequest;
import tn.esprit.gestionequipe.entities.Team;
import tn.esprit.gestionequipe.entities.Users;
import tn.esprit.gestionequipe.service.ChatbotService;
import tn.esprit.gestionequipe.service.TeamService;
import java.util.List;
import com.itextpdf.text.Document;              // For the PDF document
import com.itextpdf.text.DocumentException;     // For handling exceptions from iText
import com.itextpdf.text.Font;                 // For font styling
import com.itextpdf.text.Paragraph;            // For adding paragraphs to the PDF
import com.itextpdf.text.Phrase;               // For cell content in the table
import com.itextpdf.text.pdf.PdfPCell;          // For table cells
import com.itextpdf.text.pdf.PdfPTable;         // For creating tables
import com.itextpdf.text.pdf.PdfWriter;         // For writing the PDF content
import org.springframework.http.HttpHeaders;    // For setting HTTP headers
import org.springframework.http.MediaType;      // For setting the content type (APPLICATION_PDF)
import org.springframework.web.bind.annotation.GetMapping; // For mapping the endpoint
import org.springframework.web.bind.annotation.RestController; // If this is a REST controller
import java.io.ByteArrayOutputStream;          // For storing the PDF in memory
import java.io.IOException;                    // For handling I/O exceptions
import com.itextpdf.text.*;
import org.springframework.http.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> addPlayerToTeam(
            @PathVariable Long teamId,
            @PathVariable Long playerId) {
        return ResponseEntity.ok(teamService.addPlayerToTeam(teamId, playerId));
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> removePlayerFromTeam(
            @PathVariable Long teamId,
            @PathVariable Long playerId) {
        return ResponseEntity.ok(teamService.removePlayerFromTeam(teamId, playerId));
    }

    @GetMapping("/{teamId}/players")
    public ResponseEntity<List<Users>> getTeamPlayers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamPlayers(teamId));
    }



    @PostMapping("/chat")
    public String getChatResponse(@RequestBody ChatRequest request) {
        return chatbotService.getChatResponse(request.getPrompt()).block();
    }

    @PostMapping("/user")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        return ResponseEntity.ok(teamService.createUser(users));
    }


    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportToPDF() throws DocumentException, IOException {
        List<Team> teams = teamService.getAllTeams();

        // Initialize PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        // Add header with styling
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GRAY);

        Paragraph title = new Paragraph("Team Directory Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        Paragraph generatedDate = new Paragraph(
                "Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")),
                subtitleFont
        );
        generatedDate.setAlignment(Element.ALIGN_CENTER);
        generatedDate.setSpacingAfter(20f);
        document.add(generatedDate);

        PdfPTable table = new PdfPTable(6); // 6 columns: ID, Name, Stadium, Founded, League, Status
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 2f, 2f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(10f);

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
        BaseColor headerColor = new BaseColor(0, 102, 204); // Blue header

        String[] headers = {"ID", "Team Name", "Home Stadium", "Founded", "League", "Status"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(headerColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(8f);
            table.addCell(cell);
        }

        Font cellFont = new Font(Font.FontFamily.HELVETICA, 10);
        BaseColor oddRowColor = new BaseColor(240, 240, 240); // Light gray for alternating rows

        int rowCount = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Team team : teams) {
            PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(team.getId()), cellFont));
            PdfPCell nameCell = new PdfPCell(new Phrase(team.getTeamName(), cellFont));
            PdfPCell stadiumCell = new PdfPCell(new Phrase(team.getHomeStadium() != null ? team.getHomeStadium() : "N/A", cellFont));
            PdfPCell foundedCell = new PdfPCell(new Phrase(team.getFoundedDate().format(dateFormatter), cellFont));
            PdfPCell leagueCell = new PdfPCell(new Phrase(team.getLeague() != null ? team.getLeague() : "N/A", cellFont));
            PdfPCell statusCell = new PdfPCell(new Phrase(team.getStatus().toString(), cellFont));

            PdfPCell[] cells = {idCell, nameCell, stadiumCell, foundedCell, leagueCell, statusCell};

            for (PdfPCell cell : cells) {
                cell.setPadding(6f);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (rowCount % 2 == 1) {
                    cell.setBackgroundColor(oddRowColor);
                }
                table.addCell(cell);
            }
            rowCount++;
        }

        document.add(table);

        Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Paragraph footer = new Paragraph(
                "Total Teams: " + teams.size() + " | Page " + writer.getPageNumber(),
                footerFont
        );
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(20f);
        document.add(footer);

        document.close();


        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentDispositionFormData("attachment", "teams_report.pdf");
        header.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(baos.toByteArray(), header, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<String> searchTeam(@RequestParam String name) {
        String response = teamService.searchTeam(name);
        return ResponseEntity.ok(response);
    }


}