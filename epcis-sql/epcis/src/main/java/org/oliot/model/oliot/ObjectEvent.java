//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.23 at 08:16:54 PM KST 
//

package org.oliot.model.oliot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;

//import org.w3c.dom.Element;

@Entity
//@PrimaryKeyJoinColumn (name="EPCISEvent_id")
public class ObjectEvent{// extends EPCISEvent {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CUSTOMER_SEQ")
	//@SequenceGenerator(name="TAB_CUSTOMER_SEQ", sequenceName="TAB_CUSTOMER_SEQ", allocationSize=1)
	private int id;
	
	protected String userID;
	protected String accessModifier;
	
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected Date eventTime;
    @XmlSchemaType(name = "dateTime")
    protected Date recordTime;
    @XmlElement(required = true)
    protected String eventTimeZoneOffset;
    
    @OneToOne
	@JoinColumn(name="baseExtension_id")
    protected EPCISEventExtension baseExtension;
	
	@OneToOne
	@JoinColumn(name="epcList_id")
	protected EPCList epcList;
	
	@Enumerated (EnumType.STRING)
	protected Action action;
	
	protected String bizStep;
	protected String disposition;
	
    @OneToOne
	@JoinColumn(name="readPoint_id")
	protected ReadPoint readPoint;
	
    @OneToOne
	@JoinColumn(name="bizLocation_id")
	protected BusinessLocation bizLocation;
    
    @OneToOne
	@JoinColumn(name="bizTransactionList_id")
	protected BusinessTransactionList bizTransactionList;
    @OneToOne
	@JoinColumn(name="ilmd_id")
	protected ILMD ilmd;

    
    @OneToOne
    @JoinColumn(name="objectEventExtension_id")
	protected ObjectEventExtension extension;
	
    @OneToOne
    @JoinColumn(name="baseExtensionMaps_id")
    protected ExtensionMaps extensionMaps;
    
	@Transient
	@XmlAnyElement(lax = true)
	protected List<Object> any;

	
	
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccessModifier() {
		return accessModifier;
	}

	public void setAccessModifier(String accessModifier) {
		this.accessModifier = accessModifier;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getEventTimeZoneOffset() {
		return eventTimeZoneOffset;
	}

	public void setEventTimeZoneOffset(String eventTimeZoneOffset) {
		this.eventTimeZoneOffset = eventTimeZoneOffset;
	}

	public EPCISEventExtension getBaseExtension() {
		return baseExtension;
	}

	public void setBaseExtension(EPCISEventExtension baseExtension) {
		this.baseExtension = baseExtension;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	/**
	 * Gets the value of the epcList property.
	 * 
	 * @return possible object is {@link EPCList }
	 * 
	 */
	public EPCList getEpcList() {
		return epcList;
	}

	/**
	 * Sets the value of the epcList property.
	 * 
	 * @param value
	 *            allowed object is {@link EPCList }
	 * 
	 */
	public void setEpcList(EPCList value) {
		this.epcList = value;
	}

	/**
	 * Gets the value of the action property.
	 * 
	 * @return possible object is {@link Action }
	 * 
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the value of the action property.
	 * 
	 * @param value
	 *            allowed object is {@link Action }
	 * 
	 */
	public void setAction(Action value) {
		this.action = value;
	}

	/**
	 * Gets the value of the bizStep property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBizStep() {
		return bizStep;
	}

	/**
	 * Sets the value of the bizStep property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBizStep(String value) {
		this.bizStep = value;
	}

	/**
	 * Gets the value of the disposition property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDisposition() {
		return disposition;
	}

	/**
	 * Sets the value of the disposition property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDisposition(String value) {
		this.disposition = value;
	}

	/**
	 * Gets the value of the readPoint property.
	 * 
	 * @return possible object is {@link ReadPoint }
	 * 
	 */
	public ReadPoint getReadPoint() {
		return readPoint;
	}

	/**
	 * Sets the value of the readPoint property.
	 * 
	 * @param value
	 *            allowed object is {@link ReadPoint }
	 * 
	 */
	public void setReadPoint(ReadPoint value) {
		this.readPoint = value;
	}

	/**
	 * Gets the value of the bizLocation property.
	 * 
	 * @return possible object is {@link BusinessLocation }
	 * 
	 */
	public BusinessLocation getBizLocation() {
		return bizLocation;
	}

	/**
	 * Sets the value of the bizLocation property.
	 * 
	 * @param value
	 *            allowed object is {@link BusinessLocation }
	 * 
	 */
	public void setBizLocation(BusinessLocation value) {
		this.bizLocation = value;
	}

	/**
	 * Gets the value of the bizTransactionList property.
	 * 
	 * @return possible object is {@link BusinessTransactionList }
	 * 
	 */
	public BusinessTransactionList getBizTransactionList() {
		return bizTransactionList;
	}

	/**
	 * Sets the value of the bizTransactionList property.
	 * 
	 * @param value
	 *            allowed object is {@link BusinessTransactionList }
	 * 
	 */
	public void setBizTransactionList(BusinessTransactionList value) {
		this.bizTransactionList = value;
	}

	/**
	 * Gets the value of the ilmd property.
	 * 
	 * @return possible object is {@link ILMD }
	 * 
	 */
	public ILMD getIlmd() {
		return ilmd;
	}



	/**
	 * Sets the value of the ilmd property.
	 * 
	 * @param value
	 *            allowed object is {@link ILMD }
	 * 
	 */
	public void setIlmd(ILMD value) {
		this.ilmd = value;
	}

	/**
	 * Gets the value of the extension property.
	 * 
	 * @return possible object is {@link ObjectEventExtension }
	 * 
	 */
	public ObjectEventExtension getExtension() {
		return extension;
	}

	/**
	 * Sets the value of the extension property.
	 * 
	 * @param value
	 *            allowed object is {@link ObjectEventExtension }
	 * 
	 */
	public void setExtension(ObjectEventExtension value) {
		this.extension = value;
	}

	
	
	
	public ExtensionMaps getExtensionMaps() {
		return extensionMaps;
	}

	public void setExtensionMaps(ExtensionMaps extensionMaps) {
		this.extensionMaps = extensionMaps;
	}

	/**
	 * Gets the value of the any property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the any property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAny().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Object }
	 * {@link Element }
	 * 
	 * 
	 */
	public List<Object> getAny() {
		if (any == null) {
			any = new ArrayList<Object>();
		}
		return this.any;
	}

}