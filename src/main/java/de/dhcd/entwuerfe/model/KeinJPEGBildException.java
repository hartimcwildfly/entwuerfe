package de.dhcd.entwuerfe.model;


public class KeinJPEGBildException extends RuntimeException {
    
    public KeinJPEGBildException() {
        super("Kein gültiges JPEG-Bild");
    }
}
