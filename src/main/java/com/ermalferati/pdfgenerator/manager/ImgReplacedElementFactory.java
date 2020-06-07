package com.ermalferati.pdfgenerator.manager;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

public class ImgReplacedElementFactory implements ReplacedElementFactory {

    private static final String IMAGE_NODE_NAME = "img";
    private static final String IMAGE_SOURCE_ATTRIBUTE_NAME = "src";

    private static final int CSS_PROPERTY_ALLOWED_VALUE = -1;

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {
        Element element = box.getElement();
        if (element == null) {
            return null;
        }

        return getTextImageFromNode(element, uac, cssWidth, cssHeight);
    }

    private ITextImageElement getTextImageFromNode(Element element, UserAgentCallback uac, int cssWidth, int cssHeight) {
        if (!IMAGE_NODE_NAME.equals(element.getNodeName())) {
            return null;
        }

        String attribute = element.getAttribute(IMAGE_SOURCE_ATTRIBUTE_NAME);
        FSImage fsImage = buildImage(attribute, uac);
        if (fsImage == null) {
            return null;
        }

        return buildTextImageElement(fsImage, cssWidth, cssHeight);
    }

    private ITextImageElement buildTextImageElement(FSImage fsImage, int cssWidth, int cssHeight) {
        if (cssWidth != CSS_PROPERTY_ALLOWED_VALUE || cssHeight != CSS_PROPERTY_ALLOWED_VALUE) {
            fsImage.scale(cssWidth, cssHeight);
        }
        return new ITextImageElement(fsImage);
    }

    private FSImage buildImage(String srcAttr, UserAgentCallback uac) {
        return uac.getImageResource(srcAttr).getImage();
    }

    @Override
    public void remove(Element e) {
    }

    @Override
    public void reset() {
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
    }
}
