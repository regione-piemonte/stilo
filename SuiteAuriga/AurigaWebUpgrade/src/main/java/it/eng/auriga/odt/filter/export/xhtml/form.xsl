<?xml version="1.0" encoding="UTF-8"?>
<!--

  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  
  Copyright 2008 by Sun Microsystems, Inc.
 
  OpenOffice.org - a multi-platform office productivity suite
 
  $RCSfile: form.xsl,v $
 
  $Revision: 1.1 $
 
  This file is part of OpenOffice.org.
 
  OpenOffice.org is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License version 3
  only, as published by the Free Software Foundation.
 
  OpenOffice.org is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License version 3 for more details
  (a copy is included in the LICENSE file that accompanied this code).
 
  You should have received a copy of the GNU Lesser General Public License
  version 3 along with OpenOffice.org.  If not, see
  <http://www.openoffice.org/license.html>
  for a copy of the LGPLv3 License.
 
-->
<!--
	For further documentation and updates visit http://xml.openoffice.org/odf2xhtml
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:config="urn:oasis:names:tc:opendocument:xmlns:config:1.0"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:ooo="http://openoffice.org/2004/office"
	xmlns:oooc="http://openoffice.org/2004/calc"
	xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:xforms="http://www.w3.org/2002/xforms"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="chart config dc dom dr3d draw fo form math meta number office ooo oooc ooow script style svg table text xforms xlink xsd xsi"
	xmlns="http://www.w3.org/1999/xhtml">



<!-- XForms -->

	<xsl:template match="office:forms[xforms:*]">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="xforms:*">
		<xsl:copy-of select="."/>
	</xsl:template>

	<xsl:template match="form:form"/>

	
	

	<xsl:template match="form:formatted-text">
		
		<xsl:element name="input">

				<xsl:attribute name="type">
					<xsl:text>text</xsl:text>
				</xsl:attribute>
		
				<xsl:attribute name="name">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>				
				
				<xsl:attribute name="id">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>	

				<xsl:attribute name="class">
					<xsl:call-template name="style-form-class"/>
				</xsl:attribute>
							
				<xsl:attribute name="style">
					<xsl:call-template name="style-form"/>
					<xsl:text>overflow:hidden;</xsl:text>
					<xsl:text>border:none;</xsl:text>
					<xsl:text>vertical-align:middle</xsl:text>
				</xsl:attribute>
		
				<xsl:attribute name="value">
					<xsl:call-template name="current-value-input"/>
				</xsl:attribute>
		</xsl:element>
			
	</xsl:template>

	<xsl:template match="form:text">
			<xsl:element name="input">

				<xsl:attribute name="type">
					<xsl:text>text</xsl:text>
				</xsl:attribute>
			
				<xsl:attribute name="name">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>				
				
				<xsl:attribute name="id">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>	
				
				<xsl:attribute name="style">
					<xsl:call-template name="style-form"/>
					<xsl:text>overflow:hidden;</xsl:text>
					<xsl:text>border:none;</xsl:text>
					<xsl:text>vertical-align:middle;</xsl:text>
					<xsl:text>border-bottom:1px solid black</xsl:text>
				</xsl:attribute>
				
				<xsl:attribute name="class">
					<xsl:call-template name="style-form-class"/>
				</xsl:attribute>
				
				<xsl:attribute name="value">	
					<xsl:if test="./@form:current-value">
						<xsl:value-of select="@form:current-value"/>
					</xsl:if>
				</xsl:attribute>
		
			</xsl:element>
	</xsl:template>

	<xsl:template name="current-value-input">
		<xsl:for-each select="./form:properties/form:property">
			<xsl:if test="./@form:property-name = 'Text'">
				<xsl:value-of select="@office:string-value"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>	
	
	
	
		<xsl:template match="form:textarea">
			
			<xsl:param name="idform" select="@form:name" />
			
			<xsl:for-each select="//draw:control[@draw:control = current()/@form:id]">
				<xsl:if test="@svg:height">
					<xsl:choose>
						<xsl:when test="number(substring(@svg:height,0,(string-length(@svg:height)-2)))>0.9">
							<xsl:element name="textarea">
								<xsl:attribute name="name">
									<xsl:value-of select="$idform" />
								</xsl:attribute>				
											
								<xsl:attribute name="id">
									<xsl:value-of select="$idform" />
								</xsl:attribute>	

								<xsl:attribute name="class">
									<xsl:call-template name="style-form-class"/>
								</xsl:attribute>
								
								<xsl:attribute name="style">
									<xsl:text>border:none;</xsl:text>
									<xsl:text>vertical-align:middle;</xsl:text>
									<xsl:text>border-bottom:1px solid black;</xsl:text>
									<xsl:text>height:</xsl:text>
									<xsl:value-of select="@svg:height" />
									<xsl:text>;</xsl:text>
									<xsl:text>width:</xsl:text>
									<xsl:value-of select="@svg:width" />
									<xsl:text>;</xsl:text>
									<xsl:for-each select="//style:style[@style:name = current()/@draw:text-style-name]">
										<xsl:text>font-size:</xsl:text>
											<xsl:value-of select="style:text-properties/@fo:font-size" />
										<xsl:text>;</xsl:text>				
									</xsl:for-each>
								</xsl:attribute>
																						
								<xsl:call-template name="current-value-textarea"/>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="input">

								<xsl:attribute name="type">
									<xsl:text>text</xsl:text>
								</xsl:attribute>
							
								<xsl:attribute name="name">
									<xsl:value-of select="$idform" />
								</xsl:attribute>				
								
								<xsl:attribute name="id">
									<xsl:value-of select="$idform" />
								</xsl:attribute>	
								
								<xsl:attribute name="style">
									<xsl:text>overflow:hidden;</xsl:text>
									<xsl:text>border:none;</xsl:text>
									<xsl:text>vertical-align:middle;</xsl:text>
									<xsl:text>border-bottom:1px solid black;</xsl:text>
									<xsl:text>height:</xsl:text>
									<xsl:value-of select="@svg:height" />
									<xsl:text>;</xsl:text>
									<xsl:text>width:</xsl:text>
									<xsl:value-of select="@svg:width" />
									<xsl:text>;</xsl:text>
									<xsl:for-each select="//style:style[@style:name = current()/@draw:text-style-name]">
										<xsl:text>font-size:</xsl:text>
											<xsl:value-of select="style:text-properties/@fo:font-size" />
										<xsl:text>;</xsl:text>			
									</xsl:for-each>
								</xsl:attribute>
								
								<xsl:attribute name="class">
									<xsl:call-template name="style-form-class"/>
								</xsl:attribute>
								
								<xsl:attribute name="value">	
									<xsl:if test="./@form:current-value">
										<xsl:value-of select="@form:current-value"/>
									</xsl:if>
								</xsl:attribute>
					
							</xsl:element>	
						</xsl:otherwise>
					
					</xsl:choose>		
				
				
				</xsl:if>
			</xsl:for-each>
						
			
	</xsl:template>

	<xsl:template name="current-value-textarea">
		<xsl:value-of select="current()/@form:current-value"/>
		<xsl:text> </xsl:text>
	</xsl:template>	
	
	
	
	<xsl:template name="style-form-class">
		<xsl:for-each select="//draw:control[@draw:control = current()/@form:id]">
			<xsl:if test="@draw:style-name">		
				<xsl:value-of select="@draw:style-name" />
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	
	
	<xsl:template name="style-form">
	
		<xsl:for-each select="//draw:control[@draw:control = current()/@form:id]">
		
			<xsl:if test="@svg:width">
				<xsl:text>width:</xsl:text>
					<xsl:value-of select="@svg:width" />
				<xsl:text>;</xsl:text>
			</xsl:if>
			
			<xsl:if test="@svg:height">
				<xsl:text>height:</xsl:text>
					<xsl:value-of select="@svg:height" />
				<xsl:text>;</xsl:text>
			</xsl:if>

			<xsl:if test="@draw:text-style-name">
				<xsl:for-each select="//style:style[@style:name = current()/@draw:text-style-name]">
					<xsl:text>font-size:</xsl:text>
					<xsl:value-of select="style:text-properties/@fo:font-size" />
					<xsl:text>;</xsl:text>
				</xsl:for-each>
			</xsl:if>
			
			
			
			
			<!-- xsl:for-each select="//style:style[@style:name = current()/@draw:text-style-name]/style:text-properties">
				<xsl:if test="@fo:color">
					<xsl:text>color:</xsl:text>
					<xsl:value-of select="@fo:color" />
					<xsl:text>;</xsl:text>
				</xsl:if>
				
				<xsl:if test="@fo:font-size">
					<xsl:text>font-size:</xsl:text>
					<xsl:value-of select="@fo:font-size" />
					<xsl:text>;</xsl:text>
				</xsl:if>
				
				<xsl:if test="@fo:font-weight">
					<xsl:text>font-weight:</xsl:text>
					<xsl:value-of select="@fo:font-weight" />
					<xsl:text>;</xsl:text>
				</xsl:if>
				
				<xsl:if test="@fo:font-style">
					<xsl:text>font-style:</xsl:text>
					<xsl:value-of select="@fo:font-style" />
					<xsl:text>;</xsl:text>
				</xsl:if>
			</xsl:for-each -->
		</xsl:for-each>
	</xsl:template>
		
	<xsl:template match="form:checkbox">
		<xsl:choose>
			<xsl:when test="@form:current-state">
				<input type="checkbox" value="1" name="{@form:name}" id="{@form:name}" checked="checked"/>
			</xsl:when>
			<xsl:otherwise>
				<input type="checkbox" value="1" name="{@form:name}" id="{@form:name}"/>
			</xsl:otherwise>
		</xsl:choose>	
		<xsl:if test="@form:label!=''">
			<label for="{@form:name}"> <xsl:value-of select="@form:label" /> </label>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="form:fixed-text" mode="forms-label">
		<xforms:label>
			<xsl:value-of select="@form:label"/>
		</xforms:label>
	</xsl:template>
	
	
	
	<xsl:template match="form:radio">
		<input type="radio" value="{@form:value}" name="{@form:name}" id="{@form:name}"/>
		<xsl:if test="@form:label!=''">
			<label for="{@form:name}"> <xsl:value-of select="@form:label" /> </label>
		</xsl:if>
	</xsl:template>

	
	<xsl:template match="form:button[@form:button-type='submit' ]">
		<xforms:submit submission="{@form:xforms-submission}">
			<xforms:label><xsl:value-of select="@form:label"/></xforms:label>
		</xforms:submit>
	</xsl:template>

	<xsl:template match="draw:g[draw:control]">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="draw:control">
		<!-- possibly better to use index here, but form usually not so huge -->
		<xsl:apply-templates select="//form:*[@form:id eq current()/@draw:control][1]"></xsl:apply-templates>
	</xsl:template>

	<xsl:template match="form:frame">
		<xforms:group>
			<xsl:apply-templates/>
		</xforms:group>
	</xsl:template>
	
	<xsl:template match="form:combobox">
			<xsl:element name="select">

				<xsl:attribute name="name">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>				
				
				<xsl:attribute name="id">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>	
			
				<xsl:attribute name="width">
					<xsl:value-of select="@svg:width" />
				</xsl:attribute>
			
				<xsl:attribute name="style">
					<xsl:call-template name="style-form"/>
				</xsl:attribute>
				
				<option value=""></option> 
				
				<xsl:for-each select="./form:option">
					<xsl:choose>
							<xsl:when test="current()/@form:current-selected='true'">
								 <option value="{current()/@form:label}" selected="selected"> 
								 	 <xsl:value-of select="current()/@form:label"/>
								 </option>
							</xsl:when>
							<xsl:otherwise>
								 <option value="{current()/@form:label}"> 
								  <xsl:value-of select="current()/@form:label"/>
								 </option>
							</xsl:otherwise>
					</xsl:choose>

				</xsl:for-each> 

				
			</xsl:element>
	</xsl:template>
	
	<xsl:template match="form:item">
		<option>
			<xsl:value-of select="@form:label"/>
			<xsl:value-of select="@form:label"/>
		</option>
	</xsl:template>

	<xsl:template match="form:option">
		<option>
			<xsl:value-of select="@form:label"/>
			<xsl:value-of select="@form:label"/>
		</option>
	</xsl:template>
	
	<xsl:template match="form:listbox">
		<xsl:element name="select">

				<xsl:attribute name="name">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>				
				
				<xsl:attribute name="id">
					<xsl:value-of select="@form:name" />
				</xsl:attribute>	
			
				<xsl:attribute name="width">
					<xsl:value-of select="@svg:width" />
				</xsl:attribute>
			
				<xsl:attribute name="style">
					<xsl:call-template name="style-form"/>
				</xsl:attribute>
				
				<option value=""> </option>
				
				<xsl:for-each select="./form:option">
					<xsl:choose>
							<xsl:when test="current()/@form:current-selected='true'">
								 <option value="{current()/@form:label}" selected="selected"> 
								 	 <xsl:value-of select="current()/@form:label"/>
								 </option>
							</xsl:when>
							<xsl:otherwise>
								 <option value="{current()/@form:label}"> 
								  <xsl:value-of select="current()/@form:label"/>
								 </option>
							</xsl:otherwise>
					</xsl:choose>

				</xsl:for-each> 

				
			</xsl:element>
	</xsl:template>

</xsl:stylesheet>

