package it.eng.core.business.export.trasformer;

import it.eng.core.business.converter.IBeanPopulate;

import org.apache.commons.beanutils.BeanUtilsBean2;


 
@Deprecated
public class BeanRowTrasformer<S,O> implements IRowTrasformer{
	public Class<S> sorg;
	public Class<O> dest;
	public IBeanPopulate<S, O>  populate;
	public BeanRowTrasformer(Class<S> sorg,Class<O> dest,IBeanPopulate<S, O> populate){
		 this.sorg=sorg;
		 this.dest=dest;
		 this.populate=populate;
	}
	
	@Override
	public Object trasform(Object[] row) throws Exception {
		
		
		if(sorg.isAssignableFrom(row[0].getClass())){
			S s=(S)row[0];
			O o = dest.newInstance();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(o, s);
			if(populate!=null){
				 populate.populate(s, o);
			} 
		    return o;
			 
		 }else{
			 throw new Exception("cannot trasform "+row[0].getClass() +" to " + sorg.getName());
		 }
	}

}
