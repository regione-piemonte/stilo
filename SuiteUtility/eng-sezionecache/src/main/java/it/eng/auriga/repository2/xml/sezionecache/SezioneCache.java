// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.sezionecache;

import org.exolab.castor.xml.XMLClassDescriptorResolver;
import java.io.StringReader;
import org.exolab.castor.xml.util.XMLClassDescriptorResolverImpl;
import java.io.StringWriter;
import org.exolab.castor.xml.Validator;
import org.exolab.castor.xml.Unmarshaller;
import java.io.Reader;
import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import java.io.Writer;
import org.exolab.castor.xml.ValidationException;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

public class SezioneCache implements Serializable
{
    private Vector _variabileList;
    
    public SezioneCache() {
        this._variabileList = new Vector();
    }
    
    public void addVariabile(final Variabile vVariabile) throws IndexOutOfBoundsException {
        this._variabileList.addElement(vVariabile);
    }
    
    public void addVariabile(final int index, final Variabile vVariabile) throws IndexOutOfBoundsException {
        this._variabileList.add(index, vVariabile);
    }
    
    public Enumeration enumerateVariabile() {
        return this._variabileList.elements();
    }
    
    public Variabile getVariabile(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._variabileList.size()) {
            throw new IndexOutOfBoundsException("getVariabile: Index value '" + index + "' not in range [0.." + (this._variabileList.size() - 1) + "]");
        }
        return this._variabileList.get(index);
    }
    
    public Variabile[] getVariabile() {
        final int size = this._variabileList.size();
        final Variabile[] array = new Variabile[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._variabileList.get(index);
        }
        return array;
    }
    
    public int getVariabileCount() {
        return this._variabileList.size();
    }
    
    public boolean isValid() {
        try {
            this.validate();
        }
        catch (ValidationException vex) {
            return false;
        }
        return true;
    }
    
    public void marshal(final Writer out) throws MarshalException, ValidationException {
        Marshaller.marshal((Object)this, out);
    }
    
    public void marshal(final ContentHandler handler) throws IOException, MarshalException, ValidationException {
        Marshaller.marshal((Object)this, handler);
    }
    
    public void removeAllVariabile() {
        this._variabileList.clear();
    }
    
    public boolean removeVariabile(final Variabile vVariabile) {
        final boolean removed = this._variabileList.remove(vVariabile);
        return removed;
    }
    
    public Variabile removeVariabileAt(final int index) {
        final Object obj = this._variabileList.remove(index);
        return (Variabile)obj;
    }
    
    public void setVariabile(final int index, final Variabile vVariabile) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._variabileList.size()) {
            throw new IndexOutOfBoundsException("setVariabile: Index value '" + index + "' not in range [0.." + (this._variabileList.size() - 1) + "]");
        }
        this._variabileList.set(index, vVariabile);
    }
    
    public void setVariabile(final Variabile[] vVariabileArray) {
        this._variabileList.clear();
        for (int i = 0; i < vVariabileArray.length; ++i) {
            this._variabileList.add(vVariabileArray[i]);
        }
    }
    
    public static SezioneCache unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (SezioneCache)Unmarshaller.unmarshal(SezioneCache.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
    
    public static final void main(final String[] args) {
        try {
            SezioneCache sc = null;
            Variabile vab = null;
            VariabileChoice vch = null;
            Lista lst = null;
            Riga row = null;
            Colonna col = null;
            sc = new SezioneCache();
            for (int i = 0; i < 10; ++i) {
                vab = new Variabile();
                vab.setNome("attributo_semplice_" + i);
                vch = new VariabileChoice();
                vch.setValoreSemplice("valore_semplice_" + i);
                vab.setVariabileChoice(vch);
                sc.addVariabile(vab);
            }
            vab = new Variabile();
            vab.setNome("nome_attrib_2");
            vch = new VariabileChoice();
            vch.setValoreSemplice("val_2\u00e8\u00f2\u00e0\u00f9\u00ec&");
            vab.setVariabileChoice(vch);
            sc.addVariabile(vab);
            lst = new Lista();
            row = new Riga();
            col = new Colonna();
            col.setNro(1);
            col.setContent("R1 Colonna 1");
            row.addColonna(col);
            col = new Colonna();
            col.setNro(2);
            col.setContent("R1 Colonna 2");
            row.addColonna(col);
            lst.addRiga(row);
            row = new Riga();
            col = new Colonna();
            col.setNro(1);
            col.setContent("R2 Colonna aaaaaaaaaaaaaaaaaaaa");
            row.addColonna(col);
            col = new Colonna();
            col.setNro(2);
            col.setContent("R2 Colonna \u00e8\u00e9\u00f2\u00e7\u00e0°\u00f9§@&");
            row.addColonna(col);
            lst.addRiga(row);
            vab = new Variabile();
            vab.setNome("super_lista");
            vch = new VariabileChoice();
            vch.setLista(lst);
            vab.setVariabileChoice(vch);
            sc.addVariabile(vab);
            final StringWriter sw = new StringWriter();
            final Marshaller mrsh = new Marshaller((Writer)sw);
            mrsh.setEncoding("ISO-8859-1");
            mrsh.marshal((Object)sc);
            System.out.println(sw.toString());
            final XMLClassDescriptorResolver cdr = (XMLClassDescriptorResolver)new XMLClassDescriptorResolverImpl();
            final Unmarshaller umrsh = new Unmarshaller(SezioneCache.class);
            umrsh.setDebug(false);
            umrsh.setValidation(false);
            umrsh.setResolver(cdr);
            StringReader sr = new StringReader(sw.toString());
            SezioneCache sc2 = (SezioneCache)umrsh.unmarshal((Reader)sr);
            sr = new StringReader(sw.toString());
            sc2 = (SezioneCache)Unmarshaller.unmarshal(SezioneCache.class, (Reader)sr);
            final Enumeration enu = sc2.enumerateVariabile();
            Enumeration enu2 = null;
            Enumeration enu3 = null;
            String simple = null;
            while (enu != null && enu.hasMoreElements()) {
                vab = enu.nextElement();
                System.out.print("NOME=" + vab.getNome());
                vch = vab.getVariabileChoice();
                simple = vch.getValoreSemplice();
                lst = vch.getLista();
                System.out.print(" - TIPO=");
                if (lst == null) {
                    System.out.println("semplice - VAL=" + simple);
                }
                else {
                    System.out.println("lista");
                    enu2 = lst.enumerateRiga();
                    while (enu2 != null && enu2.hasMoreElements()) {
                        row = enu2.nextElement();
                        System.out.println("    RIGA");
                        enu3 = row.enumerateColonna();
                        while (enu3 != null && enu3.hasMoreElements()) {
                            col = enu3.nextElement();
                            System.out.println("        - COL[" + col.getNro() + "]=" + col.getContent());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
