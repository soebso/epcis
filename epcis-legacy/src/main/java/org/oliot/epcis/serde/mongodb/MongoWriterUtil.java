package org.oliot.epcis.serde.mongodb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.oliot.gcp.core.AICodeParser;
import org.oliot.model.epcis.AggregationEventExtension2Type;
import org.oliot.model.epcis.AggregationEventExtensionType;
import org.oliot.model.epcis.BusinessLocationExtensionType;
import org.oliot.model.epcis.BusinessLocationType;
import org.oliot.model.epcis.BusinessTransactionType;
import org.oliot.model.epcis.DestinationListType;
import org.oliot.model.epcis.EPCISEventExtensionType;
import org.oliot.model.epcis.ILMDExtensionType;
import org.oliot.model.epcis.ObjectEventExtension2Type;
import org.oliot.model.epcis.ObjectEventExtensionType;
import org.oliot.model.epcis.QuantityElementType;
import org.oliot.model.epcis.QuantityEventExtensionType;
import org.oliot.model.epcis.QuantityListType;
import org.oliot.model.epcis.ReadPointType;
import org.oliot.model.epcis.SensorEventExtensionType;
import org.oliot.model.epcis.SourceDestType;
import org.oliot.model.epcis.SourceListType;
import org.oliot.model.epcis.TransactionEventExtension2Type;
import org.oliot.model.epcis.TransactionEventExtensionType;
import org.oliot.model.epcis.TransformationEventExtensionType;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Copyright (C) 2014 Jaewook Jack Byun
 *
 * This project is part of Oliot (oliot.org), pursuing the implementation of
 * Electronic Product Code Information Service(EPCIS) v1.1 specification in
 * EPCglobal.
 * [http://www.gs1.org/gsmp/kc/epcglobal/epcis/epcis_1_1-standard-20140520.pdf]
 * 
 *
 * @author Jaewook Jack Byun, Ph.D student
 * 
 *         Korea Advanced Institute of Science and Technology (KAIST)
 * 
 *         Real-time Embedded System Laboratory(RESL)
 * 
 *         bjw0829@kaist.ac.kr, bjw0829@gmail.com
 */

public class MongoWriterUtil {

	static String getInstanceEPC(String code, Integer gcpLength) {
		if (gcpLength == null) {
			return code;
		}
		AICodeParser codeParser = new AICodeParser();
		HashMap<String, String> collection = codeParser.parse(code, gcpLength.intValue());
		if (collection.containsKey("sgtin")) {
			return collection.get("sgtin");
		} else if (collection.containsKey("sscc")) {
			return collection.get("sscc");
		} else if (collection.containsKey("grai")) {
			return collection.get("grai");
		} else if (collection.containsKey("giai")) {
			return collection.get("giai");
		} else if (collection.containsKey("gsrn")) {
			return collection.get("gsrn");
		} else if (collection.containsKey("gdti")) {
			return collection.get("gdti");
		}
		return code;
	}

	static String getClassEPC(String code, Integer gcpLength) {
		if (gcpLength == null) {
			return code;
		}
		AICodeParser codeParser = new AICodeParser();
		HashMap<String, String> collection = codeParser.parse(code, gcpLength.intValue());

		// Priority LGTIN -> GTIN
		if (collection.containsKey("lgtin")) {
			return collection.get("lgtin");
		}
		if (collection.containsKey("gtin")) {
			return collection.get("gtin");
		}
		return code;
	}

	static String getLocationEPC(String code, Integer gcpLength) {
		if (gcpLength == null) {
			return code;
		}
		AICodeParser codeParser = new AICodeParser();
		HashMap<String, String> collection = codeParser.parse(code, gcpLength.intValue());
		if (collection.containsKey("sgln")) {
			return collection.get("sgln");
		}
		return code;
	}

	static String getSourceDestinationEPC(String code, Integer gcpLength) {
		if (gcpLength == null) {
			return code;
		}
		AICodeParser codeParser = new AICodeParser();
		HashMap<String, String> collection = codeParser.parse(code, gcpLength.intValue());
		if (collection.containsKey("sgln")) {
			return collection.get("sgln");
		} else if (collection.containsKey("gsrn")) {
			return collection.get("gsrn");
		}
		return code;
	}

	static String getVocabularyEPC(String vocType, String code, Integer gcpLength) {
		if (vocType == null) {
			return code;
		}
		if (gcpLength == null) {
			return code;
		}
		AICodeParser codeParser = new AICodeParser();
		HashMap<String, String> collection = codeParser.parse(code, gcpLength.intValue());

		if (vocType.equals("urn:epcglobal:epcis:vtype:BusinessLocation")) {
			if (collection.containsKey("sgln")) {
				return collection.get("sgln");
			}
		} else if (vocType.equals("urn:epcglobal:epcis:vtype:ReadPoint")) {
			if (collection.containsKey("sgln")) {
				return collection.get("sgln");
			}
		} else if (vocType.equals("urn:epcglobal:epcis:vtype:EPCClass")) {
			if (collection.containsKey("lgtin")) {
				return collection.get("lgtin");
			} else if (collection.containsKey("gtin")) {
				return collection.get("lgtin");
			}
		} else if (vocType.equals("urn:epcglobal:epcis:vtype:SourceDest")) {
			if (collection.containsKey("sgln")) {
				return collection.get("sgln");
			} else if (collection.containsKey("gsrn")) {
				return collection.get("gsrn");
			}
		} else if (vocType.equals("urn:epcglobal:epcis:vtype:EPCInstance")) {
			if (collection.containsKey("sgtin")) {
				return collection.get("sgtin");
			} else if (collection.containsKey("sscc")) {
				return collection.get("sscc");
			} else if (collection.containsKey("grai")) {
				return collection.get("grai");
			} else if (collection.containsKey("giai")) {
				return collection.get("giai");
			} else if (collection.containsKey("gsrn")) {
				return collection.get("gsrn");
			} else if (collection.containsKey("gdti")) {
				return collection.get("gdti");
			}
		}
		return code;
	}

	static BsonDocument getDBObjectFromMessageElement(MessageElement any) {
		NamedNodeMap attributes = any.getAttributes();
		BsonDocument attrObject = new BsonDocument();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);

			String attrName = attr.getNodeName();
			String attrValue = attr.getNodeValue();
			attrObject.put(attrName, new BsonString(attrValue));
		}
		return attrObject;
	}

	static BsonDocument getBaseExtensionObject(EPCISEventExtensionType baseExtensionType) {
		BsonDocument baseExtension = new BsonDocument();
		if (baseExtensionType.getAny() != null && baseExtensionType.getAny().isEmpty() == false) {
			List<Object> objList = baseExtensionType.getAny();
			BsonDocument map2Save = getAnyMap(objList);
			if (map2Save.isEmpty() == false)
				baseExtension.put("any", map2Save);
		}

		if (baseExtensionType.getOtherAttributes() != null
				&& baseExtensionType.getOtherAttributes().isEmpty() == false) {
			Map<QName, String> map = baseExtensionType.getOtherAttributes();
			BsonDocument map2Save = getOtherAttributesMap(map);
			if (map2Save.isEmpty() == false)
				baseExtension.put("otherAttributes", map2Save);
		}
		return baseExtension;
	}

	static BsonDocument getReadPointObject(ReadPointType readPointType, Integer gcpLength) {
		BsonDocument readPoint = new BsonDocument();
		if (readPointType.getId() != null)
			readPoint.put("id", new BsonString(getLocationEPC(readPointType.getId(), gcpLength)));
		// ReadPoint ExtensionType is not currently supported
		/*
		 * ReadPointExtensionType readPointExtensionType = readPointType
		 * .getExtension(); if (readPointExtensionType != null) { DBObject
		 * extension = new BasicDBObject(); if (readPointExtensionType.getAny()
		 * != null) { Map<String, String> map2Save = new HashMap<String,
		 * String>(); List<Object> objList = readPointExtensionType.getAny();
		 * for (int i = 0; i < objList.size(); i++) { Object obj =
		 * objList.get(i); if (obj instanceof Element) { Element element =
		 * (Element) obj; if (element.getFirstChild() != null) { String name =
		 * element.getLocalName(); String value = element.getFirstChild()
		 * .getTextContent(); map2Save.put(name, value); } } } if (map2Save !=
		 * null) extension.put("any", map2Save); }
		 * 
		 * if (readPointExtensionType.getOtherAttributes() != null) { Map<QName,
		 * String> map = readPointExtensionType .getOtherAttributes();
		 * Map<String, String> map2Save = new HashMap<String, String>();
		 * Iterator<QName> iter = map.keySet().iterator(); while
		 * (iter.hasNext()) { QName qName = iter.next(); String value =
		 * map.get(qName); map2Save.put(qName.toString(), value); }
		 * extension.put("otherAttributes", map2Save); }
		 * readPoint.put("extension", extension); }
		 */
		return readPoint;
	}

	static BsonDocument getBizLocationObject(BusinessLocationType bizLocationType, Integer gcpLength) {
		BsonDocument bizLocation = new BsonDocument();
		if (bizLocationType.getId() != null)
			bizLocation.put("id", new BsonString(getLocationEPC(bizLocationType.getId(), gcpLength)));

		BusinessLocationExtensionType bizLocationExtensionType = bizLocationType.getExtension();
		if (bizLocationExtensionType != null) {
			BsonDocument extension = new BsonDocument();
			if (bizLocationExtensionType.getAny() != null) {
				BsonDocument map2Save = new BsonDocument();
				List<Object> objList = bizLocationExtensionType.getAny();
				for (int i = 0; i < objList.size(); i++) {
					Object obj = objList.get(i);
					if (obj instanceof Element) {
						Element element = (Element) obj;
						if (element.getFirstChild() != null) {
							String name = element.getLocalName();
							String value = element.getFirstChild().getTextContent();
							map2Save.put(name, new BsonString(value));
						}
					}
				}
				if (map2Save != null)
					extension.put("any", map2Save);
			}

			if (bizLocationExtensionType.getOtherAttributes() != null) {
				Map<QName, String> map = bizLocationExtensionType.getOtherAttributes();
				BsonDocument map2Save = new BsonDocument();
				Iterator<QName> iter = map.keySet().iterator();
				while (iter.hasNext()) {
					QName qName = iter.next();
					String value = map.get(qName);
					map2Save.put(qName.toString(), new BsonString(value));
				}
				extension.put("otherAttributes", map2Save);
			}
			bizLocation.put("extension", extension);
		}

		return bizLocation;
	}

	static BsonArray getBizTransactionObjectList(List<BusinessTransactionType> bizList) {
		BsonArray bizTranList = new BsonArray();
		for (int i = 0; i < bizList.size(); i++) {
			BusinessTransactionType bizTranType = bizList.get(i);
			if (bizTranType.getType() != null && bizTranType.getValue() != null) {
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(bizTranType.getType(), new BsonString(bizTranType.getValue()));
				bizTranList.add(dbObj);
			}
		}
		return bizTranList;
	}

	static BsonDocument getAggregationEventExtensionObject(AggregationEventExtensionType oee, Integer gcpLength) {
		BsonDocument extension = new BsonDocument();
		if (oee.getChildQuantityList() != null) {
			QuantityListType qetl = oee.getChildQuantityList();
			List<QuantityElementType> qetList = qetl.getQuantityElement();
			BsonArray quantityList = new BsonArray();
			for (int i = 0; i < qetList.size(); i++) {
				BsonDocument quantity = new BsonDocument();
				QuantityElementType qet = qetList.get(i);
				if (qet.getEpcClass() != null)
					quantity.put("epcClass", new BsonString(getClassEPC(qet.getEpcClass().toString(), gcpLength)));
				if (qet.getQuantity() != 0) {
					quantity.put("quantity", new BsonDouble(qet.getQuantity()));
				}
				if (qet.getUom() != null)
					quantity.put("uom", new BsonString(qet.getUom().toString()));
				quantityList.add(quantity);
			}
			extension.put("childQuantityList", quantityList);
		}

		if (oee.getSourceList() != null) {
			SourceListType sdtl = oee.getSourceList();
			List<SourceDestType> sdtList = sdtl.getSource();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("sourceList", dbList);
		}
		if (oee.getDestinationList() != null) {
			DestinationListType sdtl = oee.getDestinationList();
			List<SourceDestType> sdtList = sdtl.getDestination();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("destinationList", dbList);
		}
		if (oee.getExtension() != null) {
			AggregationEventExtension2Type extension2Type = oee.getExtension();
			BsonDocument extension2 = new BsonDocument();
			if (extension2Type.getAny() != null) {
				List<Object> objList = extension2Type.getAny();
				BsonDocument map2Save = getAnyMap(objList);
				if (map2Save.isEmpty() == false)
					extension2.put("any", map2Save);
			}

			if (extension2Type.getOtherAttributes() != null) {
				Map<QName, String> map = extension2Type.getOtherAttributes();
				BsonDocument map2Save = getOtherAttributesMap(map);
				if (map2Save.isEmpty() == false)
					extension2.put("otherAttributes", map2Save);
			}
			extension.put("extension", extension2);
		}
		return extension;
	}

	static BsonDocument getILMDExtensionMap(ILMDExtensionType ilmdExtension) {
		List<Object> objList = ilmdExtension.getAny();
		BsonDocument map2Save = getAnyMap(objList);
		return map2Save;
	}

	static BsonDocument getObjectEventExtensionObject(ObjectEventExtensionType oee, Integer gcpLength) {
		BsonDocument extension = new BsonDocument();
		if (oee.getQuantityList() != null) {
			QuantityListType qetl = oee.getQuantityList();
			List<QuantityElementType> qetList = qetl.getQuantityElement();
			BsonArray quantityList = new BsonArray();
			for (int i = 0; i < qetList.size(); i++) {
				BsonDocument quantity = new BsonDocument();
				QuantityElementType qet = qetList.get(i);
				if (qet.getEpcClass() != null)
					quantity.put("epcClass", new BsonString(getClassEPC(qet.getEpcClass().toString(), gcpLength)));
				if (qet.getQuantity() != 0) {
					quantity.put("quantity", new BsonDouble(qet.getQuantity()));
				}
				if (qet.getUom() != null)
					quantity.put("uom", new BsonString(qet.getUom().toString()));
				quantityList.add(quantity);
			}
			extension.put("quantityList", quantityList);
		}
		if (oee.getSourceList() != null) {
			SourceListType sdtl = oee.getSourceList();
			List<SourceDestType> sdtList = sdtl.getSource();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("sourceList", dbList);
		}
		if (oee.getDestinationList() != null) {
			DestinationListType sdtl = oee.getDestinationList();
			List<SourceDestType> sdtList = sdtl.getDestination();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("destinationList", dbList);
		}
		if (oee.getExtension() != null) {
			ObjectEventExtension2Type extension2Type = oee.getExtension();
			BsonDocument extension2 = new BsonDocument();
			if (extension2Type.getAny() != null) {
				List<Object> objList = extension2Type.getAny();
				BsonDocument map2Save = getAnyMap(objList);
				if (map2Save != null)
					extension2.put("any", map2Save);
			}

			if (extension2Type.getOtherAttributes() != null) {
				Map<QName, String> map = extension2Type.getOtherAttributes();
				BsonDocument map2Save = getOtherAttributesMap(map);
				if (map2Save.isEmpty() == false)
					extension2.put("otherAttributes", map2Save);
			}
			extension.put("extension", extension2);
		}
		return extension;
	}

	static BsonDocument getQuantityEventExtensionObject(QuantityEventExtensionType oee) {
		BsonDocument extension = new BsonDocument();
		if (oee.getAny() != null) {
			List<Object> objList = oee.getAny();
			BsonDocument map2Save = getAnyMap(objList);
			if (map2Save != null)
				extension.put("any", map2Save);
		}

		if (oee.getOtherAttributes() != null) {
			Map<QName, String> map = oee.getOtherAttributes();
			BsonDocument map2Save = getOtherAttributesMap(map);
			if (map2Save.isEmpty() == false)
				extension.put("otherAttributes", map2Save);
		}
		return extension;
	}

	static BsonDocument getSensorEventExtensionObject(SensorEventExtensionType oee) {
		BsonDocument extension = new BsonDocument();
		if (oee.getAny() != null) {
			List<Object> objList = oee.getAny();
			BsonDocument map2Save = getAnyMap(objList);
			if (map2Save != null)
				extension.put("any", map2Save);
		}

		if (oee.getOtherAttributes() != null) {
			Map<QName, String> map = oee.getOtherAttributes();
			BsonDocument map2Save = getOtherAttributesMap(map);
			if (map2Save.isEmpty() == false)
				extension.put("otherAttributes", map2Save);
		}
		return extension;
	}

	static BsonDocument getTransactionEventExtensionObject(TransactionEventExtensionType oee, Integer gcpLength) {
		BsonDocument extension = new BsonDocument();
		if (oee.getQuantityList() != null) {
			QuantityListType qetl = oee.getQuantityList();
			List<QuantityElementType> qetList = qetl.getQuantityElement();
			BsonArray quantityList = new BsonArray();
			for (int i = 0; i < qetList.size(); i++) {
				BsonDocument quantity = new BsonDocument();
				QuantityElementType qet = qetList.get(i);
				if (qet.getEpcClass() != null)
					quantity.put("epcClass", new BsonString(getClassEPC(qet.getEpcClass().toString(), gcpLength)));
				if (qet.getQuantity() != 0) {
					quantity.put("quantity", new BsonDouble(qet.getQuantity()));
				}
				if (qet.getUom() != null)
					quantity.put("uom", new BsonString(qet.getUom().toString()));
				quantityList.add(quantity);
			}
			extension.put("quantityList", quantityList);
		}
		if (oee.getSourceList() != null) {
			SourceListType sdtl = oee.getSourceList();
			List<SourceDestType> sdtList = sdtl.getSource();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("sourceList", dbList);
		}
		if (oee.getDestinationList() != null) {
			DestinationListType sdtl = oee.getDestinationList();
			List<SourceDestType> sdtList = sdtl.getDestination();
			BsonArray dbList = new BsonArray();
			for (int i = 0; i < sdtList.size(); i++) {
				SourceDestType sdt = sdtList.get(i);
				BsonDocument dbObj = new BsonDocument();
				dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
				dbList.add(dbObj);
			}
			extension.put("destinationList", dbList);
		}
		if (oee.getExtension() != null) {
			TransactionEventExtension2Type extension2Type = oee.getExtension();
			BsonDocument extension2 = new BsonDocument();
			if (extension2Type.getAny() != null) {
				List<Object> objList = extension2Type.getAny();
				BsonDocument map2Save = getAnyMap(objList);
				if (map2Save != null)
					extension2.put("any", map2Save);
			}

			if (extension2Type.getOtherAttributes() != null) {
				Map<QName, String> map = extension2Type.getOtherAttributes();
				BsonDocument map2Save = getOtherAttributesMap(map);
				if (map2Save.isEmpty() == false)
					extension2.put("otherAttributes", map2Save);
			}
			extension.put("extension", extension2);
		}
		return extension;
	}

	static BsonArray getQuantityObjectList(List<QuantityElementType> qetList, Integer gcpLength) {
		BsonArray quantityList = new BsonArray();
		for (int i = 0; i < qetList.size(); i++) {
			BsonDocument quantity = new BsonDocument();
			QuantityElementType qet = qetList.get(i);
			if (qet.getEpcClass() != null)
				quantity.put("epcClass", new BsonString(getClassEPC(qet.getEpcClass().toString(), gcpLength)));
			if (qet.getQuantity() != 0) {
				quantity.put("quantity", new BsonDouble(qet.getQuantity()));
			}
			if (qet.getUom() != null)
				quantity.put("uom", new BsonString(qet.getUom().toString()));
			quantityList.add(quantity);
		}
		return quantityList;
	}

	static BsonArray getSourceDestObjectList(List<SourceDestType> sdtList, Integer gcpLength) {
		BsonArray dbList = new BsonArray();
		for (int i = 0; i < sdtList.size(); i++) {
			SourceDestType sdt = sdtList.get(i);
			BsonDocument dbObj = new BsonDocument();
			dbObj.put(sdt.getType(), new BsonString(getSourceDestinationEPC(sdt.getValue(), gcpLength)));
			dbList.add(dbObj);
		}
		return dbList;
	}

	static BsonDocument getTransformationEventExtensionObject(TransformationEventExtensionType oee) {
		BsonDocument extension = new BsonDocument();
		if (oee.getAny() != null) {
			List<Object> objList = oee.getAny();
			BsonDocument map2Save = getAnyMap(objList);
			if (map2Save != null)
				extension.put("any", map2Save);
		}

		if (oee.getOtherAttributes() != null) {
			Map<QName, String> map = oee.getOtherAttributes();
			BsonDocument map2Save = getOtherAttributesMap(map);
			if (map2Save != null)
				extension.put("otherAttributes", map2Save);
		}
		return extension;
	}

	static BsonDocument getAnyMap(List<Object> objList) {
		BsonDocument map2Save = new BsonDocument();
		for (int i = 0; i < objList.size(); i++) {
			Object obj = objList.get(i);
			if (obj instanceof Element) {
				Element element = (Element) obj;
				if (element.getFirstChild() != null) {
					String name = element.getNodeName();
					// Process Namespace
					String[] checkArr = name.split(":");
					if (checkArr.length == 2) {
						map2Save.put("@" + checkArr[0], new BsonString(element.getNamespaceURI()));
					}
					String value = element.getFirstChild().getTextContent();
					map2Save.put(name, converseType(value));
				}
			}
		}
		return map2Save;
	}

	static BsonDocument getOtherAttributesMap(Map<QName, String> map) {
		BsonDocument map2Save = new BsonDocument();
		Iterator<QName> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			QName qName = iter.next();
			String value = map.get(qName);
			map2Save.put(qName.toString(), new BsonString(value));
		}
		return map2Save;
	}

	static BsonValue converseType(String value) {
		String[] valArr = value.split("\\^");
		if (valArr.length != 2) {
			return new BsonString(value);
		}
		try {
			String type = valArr[1];
			if (type.equals("int")) {
				return new BsonInt32(Integer.parseInt(valArr[0]));
			} else if (type.equals("long")) {
				return new BsonInt64(Long.parseLong(valArr[0]));
			} else if (type.equals("double")) {
				return new BsonDouble(Double.parseDouble(valArr[0]));
			} else if (type.equals("boolean")) {
				return new BsonBoolean(Boolean.parseBoolean(valArr[0]));
			} else {
				return new BsonString(value);
			}
		} catch (NumberFormatException e) {
			return new BsonString(value);
		}
	}
}
