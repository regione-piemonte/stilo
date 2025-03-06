<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/page">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="Etichetta" reference-orientation="{@reference-orientation}" page-height="{@page-height}" page-width="{@page-width}" margin="{@margin}">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="Etichetta">
				<fo:flow flow-name="xsl-region-body" font-family="{labels/@font-family}">
					<xsl:for-each select="labels/label">
						<xsl:for-each select="riga">
						<fo:block margin-top="{@margin-top}" font-size="{@font-size}" font-weight="{@font-weight}" text-align="{@text-align}" margin-bottom="{@margin-bottom}">
							<xsl:value-of select="."/>
						</fo:block>
						</xsl:for-each>
						<xsl:if test="barCode">
						<fo:block text-align="{barCode/@text-align}" margin-bottom="{barCode/@margin-bottom}">
							<fo:instream-foreign-object>
								<barcode:barcode xmlns:barcode="http://barcode4j.krysalis.org/ns" message="{barCode}" orientation="{barCode/@orientation}">
									<barcode:code128>
										<barcode:height><xsl:value-of select="barcodeProperty/height"/></barcode:height>
										<barcode:module-width><xsl:value-of select="barcodeProperty/module-width"/></barcode:module-width>
										<barcode:wide-factor><xsl:value-of select="barcodeProperty/wide-factor"/></barcode:wide-factor>
										<barcode:quiet-zone enabled="{barcodeProperty/quiet-zone/@enabled}"><xsl:value-of select="barcodeProperty/quiet-zone"/></barcode:quiet-zone>
										<barcode:human-readable>
											<barcode:placement><xsl:value-of select="barcodeProperty/human-readable/placement"/></barcode:placement>
										</barcode:human-readable>
									</barcode:code128>
								</barcode:barcode>
							</fo:instream-foreign-object>
						</fo:block>
						</xsl:if>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>
