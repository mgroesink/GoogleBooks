package nl.rocvantwente.rsk01.kittensearch;

import java.util.Date;

public class Book {


    private VolumeInfo volumeInfo = new VolumeInfo();
    private SaleInfo saleInfo = new SaleInfo();

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }


}

