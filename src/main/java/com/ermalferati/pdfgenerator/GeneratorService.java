package com.ermalferati.pdfgenerator;

import com.ermalferati.pdfgenerator.manager.PdfTemplateManager;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class GeneratorService {

    private final PdfTemplateManager templateManager;

    public GeneratorService(PdfTemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    public byte[] downloadPdf(String templateName, Map<String, Object> variables) {
        final Context context = new Context();
        context.setVariables(variables);
        ByteArrayOutputStream pdfStream = templateManager.render(templateName, context);
        return pdfStream.toByteArray();
    }
}
