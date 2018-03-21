<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
    <html>
        <head>
            <title>
            <!-- 获取xml文件标题 -->
            <xsl:value-of select="view/header/title" />
            </title>
        </head>
        <body>
            <!-- 生成form元素 -->
            <xsl:element name="form">
                <xsl:attribute name="name">
                    <xsl:value-of select="view/body/form/name" />
                </xsl:attribute>
                <xsl:attribute name="action">
                    <xsl:value-of select="view/body/form/action" />
                </xsl:attribute>
                <xsl:attribute name="method">
                    <xsl:value-of select="view/body/form/method" />
                </xsl:attribute>
                <table border="0" bordercolor="#000000" style="border-collapse:collapse"
                       align="center">
                    <xsl:for-each select="view/body/form/textView">
                        <tr>
                            <td>
                                <xsl:value-of select="label" />
                            </td>
                            <td>
                                <!-- 生成input元素-->
                                <xsl:element name="input">
                                    <xsl:attribute name="name">
                                        <xsl:value-of select="name" />
                                    </xsl:attribute>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="value" />
                                    </xsl:attribute>
                                </xsl:element>
                            </td>
                        </tr>
                    </xsl:for-each>
                    <xsl:for-each select="view/body/form/buttonView">
                        <tr>
                            <td>
                                <xsl:value-of select="label" />
                            </td>
                            <td align="right">
                                <!-- 生成input元素 提交按钮 -->
                                <xsl:element name="input">
                                    <xsl:attribute name="type">
                                        <xsl:value-of select="'substring-before(name,'View')'"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="name">
                                        <xsl:value-of select="name" />
                                    </xsl:attribute>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="'登出'" />
                                    </xsl:attribute>
                                    <xsl:attribute name="style">
                                        <xsl:value-of select="'width:50px; height:22px; border:#1f0000 1px solid; background:#3d69ff; color:#FFF'" />
                                    </xsl:attribute>
                                </xsl:element>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </xsl:element>
        </body>
    </html>
    </xsl:template>
</xsl:stylesheet>