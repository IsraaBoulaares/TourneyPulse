package tn.esprit.satduimmanagment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.esprit.satduimmanagment.entities.Stade;
import tn.esprit.satduimmanagment.service.FootballDataService;
import tn.esprit.satduimmanagment.service.serviceStaduim;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


import java.util.List;

@RestController
@RequestMapping("/stades")
@RequiredArgsConstructor
public class StadeController {

    private final serviceStaduim stadeService;

    @PostMapping
    public Stade createStade(@RequestBody Stade stade) {
        return stadeService.createStade(stade);
    }

    @GetMapping
    public List<Stade> getAllStades() {
        return stadeService.getAllStades();
    }

    @GetMapping("/{id}")
    public Stade getStadeById(@PathVariable Long id) {
        return stadeService.getStadeById(id);
    }

    @PutMapping("/{id}")
    public Stade updateStade(@PathVariable Long id, @RequestBody Stade stade) {
        return stadeService.updateStade(id, stade);
    }

    @DeleteMapping("/{id}")
    public void deleteStade(@PathVariable Long id) {
        stadeService.deleteStade(id);
    }


    @Autowired
    private FootballDataService footballDataService;

    @GetMapping("/matches")
    public ResponseEntity<String> getMatches() {
        String data = footballDataService.getMatches();
        return ResponseEntity.ok(data);
    }
    @GetMapping("/competitions")
    public ResponseEntity<String> getCompetitions() {
        return ResponseEntity.ok(footballDataService.getCompetitions());
    }
    @GetMapping("/areas")
    public ResponseEntity<String> getAreas() {
        return ResponseEntity.ok(footballDataService.getAreas());
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        // Fetch list of Stades
        List<Stade> stades = stadeService.getAllStades();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Stades");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] excelHeaders = {"ID", "Name", "Location", "Capacity"};
        for (int i = 0; i < excelHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelHeaders[i]);
            cell.setCellStyle(getHeaderStyle(workbook));
        }

        // Data rows
        int rowNum = 1;
        for (Stade stade : stades) {
            Row row = sheet.createRow(rowNum++);

            // Fill in data for each Stade
            row.createCell(0).setCellValue(stade.getId() != null ? stade.getId().toString() : "N/A");
            row.createCell(1).setCellValue(stade.getName());
            row.createCell(2).setCellValue(stade.getLocation());
            row.createCell(3).setCellValue(stade.getCapacity());
        }

        // Auto-size columns
        for (int i = 0; i < excelHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to a byte array output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "stades.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        // Return the response with the Excel file
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    // Method to style the header cells in Excel
    private CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setBold(true); // Make header text bold
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }




}
