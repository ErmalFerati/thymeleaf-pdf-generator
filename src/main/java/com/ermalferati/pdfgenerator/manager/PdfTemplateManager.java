package com.ermalferati.pdfgenerator.manager;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PdfTemplateManager {

    public static final String TEMPLATE_DIRECTORY = "/templates/";
    private static final String TEMPLATE_NAME_FORMAT = "%s.html";

    private final TemplateEngine templateEngine;

    public PdfTemplateManager() {
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML");
        resolver.setPrefix(TEMPLATE_DIRECTORY);

        templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(resolver);
    }

    public ByteArrayOutputStream render(String templateName, Context context) {
        ITextRenderer renderer = buildPdfRenderer(templateName, context);

        renderer.layout();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);
        renderer.finishPDF();
        return outputStream;
    }

    private ITextRenderer buildPdfRenderer(String templateName, Context context) {
        ITextRenderer renderer = new ITextRenderer();
        ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(renderer.getOutputDevice());
        callback.setSharedContext(renderer.getSharedContext());
        renderer.getSharedContext().setUserAgentCallback(callback);
        renderer.getSharedContext().setReplacedElementFactory(new ImgReplacedElementFactory());

        String html = processTemplate(templateName, context);
        renderer.setDocumentFromString(html);

        return renderer;

    }

    private String processTemplate(String templateName, Context context) {
        String templateNameWithExtension = String.format(TEMPLATE_NAME_FORMAT, templateName);
        return templateEngine.process(templateNameWithExtension, context);
    }

}
