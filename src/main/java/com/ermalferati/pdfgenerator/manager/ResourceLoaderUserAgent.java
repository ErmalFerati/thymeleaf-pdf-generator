package com.ermalferati.pdfgenerator.manager;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import java.io.InputStream;

public class ResourceLoaderUserAgent extends ITextUserAgent {

    public ResourceLoaderUserAgent(ITextOutputDevice outputDevice) {
        super(outputDevice);
        String url = ResourceLoaderUserAgent.class.getResource(PdfTemplateManager.TEMPLATE_DIRECTORY).toString();
        setBaseURL(url);
    }

    @Override
    protected InputStream resolveAndOpenStream(String uri) {
        InputStream inputStream = super.resolveAndOpenStream(uri);
        if (inputStream == null) {
            inputStream = getFileInputStream(uri);
        }
        return inputStream;
    }

    private InputStream getFileInputStream(String uri) {
        String fileName = getFileName(uri);
        InputStream inputStream = null;
        try {
            inputStream = ResourceLoaderUserAgent.class.getResourceAsStream(PdfTemplateManager.TEMPLATE_DIRECTORY + fileName);
        } catch (Exception ignored) {
        }
        return inputStream;
    }

    private String getFileName(String uri) {
        String fileName;
        try {
            String[] split = uri.split("/");
            fileName = split[split.length - 1];
        } catch (Exception e) {
            fileName = null;
        }
        return fileName;
    }
}
