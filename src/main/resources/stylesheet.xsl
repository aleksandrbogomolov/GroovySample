<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <entries>
            <xsl:apply-templates/>
        </entries>
    </xsl:template>
    <xsl:template match="entry">
        <entry field="{field}"/>
    </xsl:template>
</xsl:stylesheet>
