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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="webamilInputStream" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "webamilInputStream"
})
@XmlRootElement(name = "getMailAttachDownloadResponse", namespace = "http://webmail.com/Maildownload")
public class GetMailAttachDownloadResponse {

    @XmlElement(namespace = "http://webmail.com/Maildownload", required = true)
    protected Object webamilInputStream;

    /**
     * Gets the value of the webamilInputStream property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getWebamilInputStream() {
        return webamilInputStream;
    }

    /**
     * Sets the value of the webamilInputStream property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setWebamilInputStream(Object value) {
        this.webamilInputStream = value;
    }

}
