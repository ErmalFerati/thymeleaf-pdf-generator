package com.ermalferati.pdfgenerator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/generate-template")
public class GeneratorController {

    private final GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping("/{templateName}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String templateName,
                                              @RequestBody Map<String, Object> variables) {
        byte[] bytes = generatorService.downloadPdf(templateName, variables);
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
