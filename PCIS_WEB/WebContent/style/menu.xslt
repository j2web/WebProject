<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" version="4.0" indent="yes" />
	<xsl:template match="Menu">
		<div id="menu">
			<xsl:if test="count(MenuItem) > 0">
				<div class="subMenu">
					<table cellspacing="0" cellpadding="0">
						<xsl:for-each select="MenuItem">
							<xsl:sort select="@Sequence"></xsl:sort>
							<xsl:call-template name="SubMenu" />
						</xsl:for-each>
					</table>
				</div>
				<xsl:if test="count(MenuItem) > 0">
					<xsl:apply-templates select="MenuItem" />
				</xsl:if>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template name="SubMenu">
		<tr>
			<xsl:attribute name="class">outMenu</xsl:attribute>
			<xsl:attribute name="id">
				<xsl:value-of select="@ID" />
			</xsl:attribute>
			<td class="outMenuLeftCell">
				<div style="width:22px;display:block" />
			</td>
			<td noWrap="true" class="outMenuCenterCell">
				<nobr>
					<a hideFocus="true">
						<xsl:choose>
							<xsl:when test="@Url">
								<xsl:attribute name="href">
									<xsl:value-of select="@Url" />
								</xsl:attribute>
							</xsl:when>
						</xsl:choose>
						<xsl:value-of select="@Name" />
					</a>
				</nobr>
			</td>
			<td>
				<xsl:attribute name="class">
					<xsl:choose>
						<xsl:when test="count(./MenuItem) > 0">outMenuCenterCell_HasChild</xsl:when>
						<xsl:otherwise>outMenuCenterCell_NoChild</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<div style="width:5px;display:block" />
			</td>
			<td class="outMenuRightCell">
				<div style="width:5px;display:block" />
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="MenuItem">
		<xsl:if test="count(MenuItem) > 0">
			<div class="subMenu" style="display:none;">
				<xsl:attribute name="id">subMenu_<xsl:value-of
					select="@ID" /></xsl:attribute>
				<table cellspacing="0" cellpadding="0">
					<xsl:for-each select="MenuItem">
						<xsl:sort select="@Sequence"></xsl:sort>
						<xsl:call-template name="SubMenu" />
					</xsl:for-each>
				</table>
			</div>
			<xsl:if test="count(MenuItem) > 0">
				<xsl:apply-templates select="MenuItem" />
			</xsl:if>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
