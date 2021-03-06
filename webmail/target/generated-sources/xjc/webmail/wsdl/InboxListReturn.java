//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.11 at 12:09:55 PM IST 
//


package webmail.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InboxListReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InboxListReturn"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="MailCount" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="InboxListReturn" type="{http://webmail.com/Inbox}ArrayOfInboxMail" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InboxListReturn", namespace = "http://webmail.com/Inbox", propOrder = {
    "success",
    "mailCount",
    "inboxListReturn"
})
public class InboxListReturn {

    @XmlElement(name = "Success")
    protected boolean success;
    @XmlElement(name = "MailCount")
    protected boolean mailCount;
    @XmlElement(name = "InboxListReturn")
    protected ArrayOfInboxMail inboxListReturn;

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the mailCount property.
     * 
     */
    public boolean isMailCount() {
        return mailCount;
    }

    /**
     * Sets the value of the mailCount property.
     * 
     */
    public void setMailCount(boolean value) {
        this.mailCount = value;
    }

    /**
     * Gets the value of the inboxListReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInboxMail }
     *     
     */
    public ArrayOfInboxMail getInboxListReturn() {
        return inboxListReturn;
    }

    /**
     * Sets the value of the inboxListReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInboxMail }
     *     
     */
    public void setInboxListReturn(ArrayOfInboxMail value) {
        this.inboxListReturn = value;
    }

}
