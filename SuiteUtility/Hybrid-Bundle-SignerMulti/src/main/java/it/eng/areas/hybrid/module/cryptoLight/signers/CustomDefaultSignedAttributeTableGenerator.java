package it.eng.areas.hybrid.module.cryptoLight.signers;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAlgorithmProtection;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAttributeTableGenerator;

public class CustomDefaultSignedAttributeTableGenerator
  implements CMSAttributeTableGenerator
{
  
	public final static Logger logger = Logger.getLogger( CustomDefaultSignedAttributeTableGenerator.class );
	private final Hashtable table;

  public CustomDefaultSignedAttributeTableGenerator()
  {
    this.table = new Hashtable();
  }

  public CustomDefaultSignedAttributeTableGenerator(AttributeTable paramAttributeTable)
  {
    if (paramAttributeTable != null){
      logger.info("if");
    	this.table = paramAttributeTable.toHashtable();
    }else{
    	logger.info("else");
      this.table = new Hashtable();
    }
    
    logger.info("table " + table);
  }

  protected Hashtable createStandardAttributeTable(Map paramMap)
  {
    
	  logger.info("metodo createStandardAttributeTable");
	  Hashtable localHashtable = copyHashTable(this.table);
	  logger.info("localHashtable "+ localHashtable);
    Object localObject;
    Attribute localAttribute;
    if (!localHashtable.containsKey(CMSAttributes.contentType))
    {
      logger.info("contentType");
    	localObject = ASN1ObjectIdentifier.getInstance(paramMap.get("contentType"));
      if (localObject != null)
      {
        localAttribute = new Attribute(CMSAttributes.contentType, new DERSet((ASN1Encodable)localObject));
        localHashtable.put(localAttribute.getAttrType(), localAttribute);
      }
    }
    if (!localHashtable.containsKey(CMSAttributes.signingTime))
    {
    	logger.info("signingTime");
    	localObject = new Date();
      localAttribute = new Attribute(CMSAttributes.signingTime, new DERSet(new Time((Date)localObject)));
      localHashtable.put(localAttribute.getAttrType(), localAttribute);
    }
    if (!localHashtable.containsKey(CMSAttributes.messageDigest))
    {
    	logger.info("messageDigest");
    	localObject = (byte[])(byte[])paramMap.get("digest");
      localAttribute = new Attribute(CMSAttributes.messageDigest, new DERSet(new DEROctetString((byte[]) localObject)));
      localHashtable.put(localAttribute.getAttrType(), localAttribute);
    }
//    if (!localHashtable.contains(CMSAttributes.cmsAlgorithmProtect))
//    {
//    	logger.info("cmsAlgorithmProtect");
//    	localObject = new Attribute(CMSAttributes.cmsAlgorithmProtect, new DERSet(new CMSAlgorithmProtection((AlgorithmIdentifier)paramMap.get("digestAlgID"), 1, (AlgorithmIdentifier)paramMap.get("signatureAlgID"))));
//      localHashtable.put(((Attribute)localObject).getAttrType(), localObject);
//    }
    return (Hashtable)localHashtable;
  }

  public AttributeTable getAttributes(Map paramMap)
  {
    return new AttributeTable(createStandardAttributeTable(paramMap));
  }

  private static Hashtable copyHashTable(Hashtable paramHashtable)
  {
    Hashtable localHashtable = new Hashtable();
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      localHashtable.put(localObject, paramHashtable.get(localObject));
    }
    return localHashtable;
  }
}