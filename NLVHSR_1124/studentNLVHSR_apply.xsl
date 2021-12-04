<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<h1>Hallgatok apply-template</h1>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="student">
		<p>
			ID: <xsl:value-of select="@id"/>
			<xsl:apply-templates select="vezeteknev"/>
			<xsl:apply-templates select="keresztnev"/>
			<xsl:apply-templates select="becenev"/>
			<xsl:apply-templates select="kor"/>
		</p>
	</xsl:template>
	
	<xsl:template match="vezeteknev">
		<span>Vezeteknev: <span style="color: ff0000"><xsl:value-of select="."/></span> <br/></span>
	</xsl:template>
	
	<xsl:template match="keresztnev">
		<span>Keresztnev: <span style="color: 00ff00"><xsl:value-of select="."/></span> <br/></span>
	</xsl:template>
	
	<xsl:template match="becenev">
		<span>Becenev: <span style="color: 0000ff"><xsl:value-of select="."/></span> <br/></span>
	</xsl:template>
	
	<xsl:template match="kor">
		<span>Kor: <span style="color: 00ffff"><xsl:value-of select="."/></span> <br/></span>
	</xsl:template>
</xsl:stylesheet>