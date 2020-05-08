package com.example.barcodereader.model;

import com.google.zxing.BarcodeFormat;

public enum BarcodeType {

    ALL("All formats"),

    /**
     * Aztec 2D barcode format.
     */
    AZTEC("Aztec 2D"),

    /**
     * CODABAR 1D format.
     */
    CODABAR("CODABAR 1D"),

    /**
     * Code 39 1D format.
     */
    CODE_39("Code 39 1D"),

    /**
     * Code 93 1D format.
     */
    CODE_93("Code 93 1D"),

    /**
     * Code 128 1D format.
     */
    CODE_128("Code 128 1D"),

    /**
     * Data Matrix 2D barcode format.
     */
    DATA_MATRIX("Data Matrix 2D"),

    /**
     * EAN-8 1D format.
     */
    EAN_8("EAN-8 1D"),

    /**
     * EAN-13 1D format.
     */
    EAN_13("EAN-13 1D"),

    /**
     * ITF (Interleaved Two of Five) 1D format.
     */
    ITF("ITF (Interleaved Two of Five) 1D"),

    /**
     * MaxiCode 2D barcode format.
     */
    MAXICODE("MaxiCode 2D"),

    /**
     * PDF417 format.
     */
    PDF_417("PDF417"),

    /**
     * QR Code 2D barcode format.
     */
    QR_CODE("QR Code 2D"),

    /**
     * RSS 14
     */
    RSS_14("RSS 14"),

    /**
     * RSS EXPANDED
     */
    RSS_EXPANDED("RSS EXPANDED"),

    /**
     * UPC-A 1D format.
     */
    UPC_A("UPC-A 1D"),

    /**
     * UPC-E 1D format.
     */
    UPC_E("UPC-E 1D"),

    /**
     * UPC/EAN extension format. Not a stand-alone format.
     */
    UPC_EAN_EXTENSION("UPC/EAN extension");

    private final String name;

    private BarcodeType(String s) {
        name = s;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static BarcodeType getTypeFromFormat(BarcodeFormat format){
        BarcodeType type = BarcodeType.valueOf(format.name());
        return type;
    }

}
