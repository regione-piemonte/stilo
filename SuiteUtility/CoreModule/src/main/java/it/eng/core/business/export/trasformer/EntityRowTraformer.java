package it.eng.core.business.export.trasformer;


 

public class EntityRowTraformer implements IRowTrasformer{
	public Class clazz;//expected class
	
	public EntityRowTraformer(Class clazz){
		this.clazz=clazz;
	}
	
	@Override
	public Object trasform(Object[] row) throws Exception {
		 if(clazz.isAssignableFrom(row[0].getClass())){
			 return row[0];
		 }else{
			 throw new Exception("cannot trasform "+row[0].getClass() +" to " + clazz.getName());
		 }
	}

}
