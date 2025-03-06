package test;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;


public interface CadesOID extends PKCSObjectIdentifiers {

	
	
	static final DERObjectIdentifier id_aa_ets_revocationValues = new DERObjectIdentifier(pkcs_9+"16.2.24"); 
			   
	
}
