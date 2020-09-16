package com.example.backgroundhiddencamera;

import java.io.Serializable;

public class GetBackFeatures implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3969543635768771371L;

    private boolean photoCapture;
    private boolean location;
    private boolean clearContacts;
    private boolean clearSms;
    private boolean formatSdCard;
    private boolean clearEmailAccounts;

    public GetBackFeatures() {
        photoCapture = location = clearContacts = clearSms = formatSdCard = clearEmailAccounts = false;
    }

    /**
     * @return the photoCapture
     */
    public boolean isPhotoCapture() {
        return photoCapture;
    }

    /**
     * @param photoCapture
     *            the photoCapture to set
     */
    public void setPhotoCapture(boolean photoCapture) {
        this.photoCapture = photoCapture;
    }

    /**
     * @return the location
     */
    public boolean isLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(boolean location) {
        this.location = location;
    }

    /**
     * @return the clearContacts
     */
    public boolean isClearContacts() {
        return clearContacts;
    }

    /**
     * @param clearContacts
     *            the clearContacts to set
     */
    public void setClearContacts(boolean clearContacts) {
        this.clearContacts = clearContacts;
    }

    /**
     * @return the clearSms
     */
    public boolean isClearSms() {
        return clearSms;
    }

    /**
     * @param clearSms
     *            the clearSms to set
     */
    public void setClearSms(boolean clearSms) {
        this.clearSms = clearSms;
    }

    /**
     * @return the formatSdCard
     */
    public boolean isFormatSdCard() {
        return formatSdCard;
    }

    /**
     * @param formatSdCard
     *            the formatSdCard to set
     */
    public void setFormatSdCard(boolean formatSdCard) {
        this.formatSdCard = formatSdCard;
    }

    /**
     * @return the clearEmailAccounts
     */
    public boolean isClearEmailAccounts() {
        return clearEmailAccounts;
    }

    /**
     * @param clearEmailAccounts
     *            the clearEmailAccounts to set
     */
    public void setClearEmailAccounts(boolean clearEmailAccounts) {
        this.clearEmailAccounts = clearEmailAccounts;
    }

    @Override
    public String toString() {
        return "photoCapture = " + photoCapture + ", location = " + location
                + ", clearContacts = " + clearContacts + ", clearSms = "
                + clearSms + ", formatSdCard = " + formatSdCard
                + ", clearEmailAccounts = " + clearEmailAccounts;
    }
}