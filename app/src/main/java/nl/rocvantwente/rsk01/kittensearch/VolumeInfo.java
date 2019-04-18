package nl.rocvantwente.rsk01.kittensearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VolumeInfo {
    private String mSubtitle;
    private String mSmallThumbnail;
    private String mThumbnail;
    private String mPublisher;
    private List<String> mAuthors = new ArrayList<String>();
    private Date mPublishedDate;
    private int mPageCount;
    private String mTitle;
    private String mDescription;
    private String mIsbn10 = "";
    private String mIsbn13 = "";

    public String getmIsbn10() {
        return mIsbn10;
    }

    public void setmIsbn10(String mIsbn10) {
        this.mIsbn10 = mIsbn10;
    }

    public String getmIsbn13() {
        return mIsbn13;
    }

    public void setmIsbn13(String mIsbn13) {
        this.mIsbn13 = mIsbn13;
    }

    public Date getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(Date mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public List<String> getmAuthors() {
        return mAuthors;
    }

    public void addAuthor(String name) {
        mAuthors.add(name);
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public void setmSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    public String getmSmallThumbnail() {
        return mSmallThumbnail;
    }

    public void setmSmallThumbnail(String mSmallThumbnail) {
        this.mSmallThumbnail = mSmallThumbnail;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmPageCount() {
        return mPageCount;
    }

    public void setmPageCount(int mPageCount) {
        this.mPageCount = mPageCount;
    }
}
