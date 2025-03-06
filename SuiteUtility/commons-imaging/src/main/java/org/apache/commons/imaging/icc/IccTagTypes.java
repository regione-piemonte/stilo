// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

public enum IccTagTypes implements IccTagType
{
    A_TO_B0_TAG("AToB0Tag", "lut8Type or lut16Type or lutAtoBType", 1093812784), 
    A_TO_B1_TAG("AToB1Tag", "lut8Type or lut16Type or lutAtoBType", 1093812785), 
    A_TO_B2_TAG("AToB2Tag", "lut8Type or lut16Type or lutAtoBType", 1093812786), 
    BLUE_MATRIX_COLUMN_TAG("blueMatrixColumnTag", "XYZType", 1649957210), 
    BLUE_TRC_TAG("blueTRCTag", "curveType or parametricCurveType", 1649693251), 
    B_TO_A0_TAG("BToA0Tag", "lut8Type or lut16Type or lutBtoAType", 1110589744), 
    B_TO_A1_TAG("BToA1Tag", "lut8Type or lut16Type or lutBtoAType", 1110589745), 
    B_TO_A2_TAG("BToA2Tag", "lut8Type or lut16Type or lutBtoAType", 1110589746), 
    CALIBRATION_DATE_TIME_TAG("calibrationDateTimeTag", "dateTimeType", 1667329140), 
    CHAR_TARGET_TAG("charTargetTag", "textType", 1952543335), 
    CHROMATIC_ADAPTATION_TAG("chromaticAdaptationTag", "s15Fixed16ArrayType", 1667785060), 
    CHROMATICITY_TAG("chromaticityTag", "chromaticityType", 1667789421), 
    COLORANT_ORDER_TAG("colorantOrderTag", "colorantOrderType", 1668051567), 
    COLORANT_TABLE_TAG("colorantTableTag", "colorantTableType", 1668051572), 
    COPYRIGHT_TAG("copyrightTag", "multiLocalizedUnicodeType", 1668313716), 
    DEVICE_MFG_DESC_TAG("deviceMfgDescTag", "multiLocalizedUnicodeType", 1684893284), 
    DEVICE_MODEL_DESC_TAG("deviceModelDescTag", "multiLocalizedUnicodeType", 1684890724), 
    GAMUT_TAG("gamutTag", "lut8Type or lut16Type or lutBtoAType", 1734438260), 
    GRAY_TRC_TAG("grayTRCTag", "curveType or parametricCurveType", 1800688195), 
    GREEN_MATRIX_COLUMN_TAG("greenMatrixColumnTag", "XYZType", 1733843290), 
    GREEN_TRC_TAG("greenTRCTag", "curveType or parametricCurveType", 1733579331), 
    LUMINANCE_TAG("luminanceTag", "XYZType", 1819635049), 
    MEASUREMENT_TAG("measurementTag", "measurementType", 1835360627), 
    MEDIA_BLACK_POINT_TAG("mediaBlackPointTag", "XYZType", 1651208308), 
    MEDIA_WHITE_POINT_TAG("mediaWhitePointTag", "XYZType", 2004119668), 
    NAMED_COLOR_2_TAG("namedColor2Tag", "namedColor2Type", 1852009522), 
    OUTPUT_RESPONSE_TAG("outputResponseTag", "responseCurveSet16Type", 1919251312), 
    PREVIEW_0_TAG("preview0Tag", "lut8Type or lut16Type or lutBtoAType", 1886545200), 
    PREVIEW_1_TAG("preview1Tag", "lut8Type or lut16Type or lutBtoAType", 1886545201), 
    PREVIEW_2_TAG("preview2Tag", "lut8Type or lut16Type or lutBtoAType", 1886545202), 
    PROFILE_DESCRIPTION_TAG("profileDescriptionTag", "multiLocalizedUnicodeType", 1684370275), 
    PROFILE_SEQUENCE_DESC_TAG("profileSequenceDescTag", "profileSequenceDescType", 1886610801), 
    RED_MATRIX_COLUMN_TAG("redMatrixColumnTag", "XYZType", 1918392666), 
    RED_TRC_TAG("redTRCTag", "curveType or parametricCurveType", 1918128707), 
    TECHNOLOGY_TAG("technologyTag", "signatureType", 1952801640), 
    VIEWING_COND_DESC_TAG("viewingCondDescTag", "multiLocalizedUnicodeType", 1987405156), 
    VIEWING_CONDITIONS_TAG("viewingConditionsTag", "viewingConditionsType", 1986618743);
    
    public final String name;
    public final String typeDescription;
    public final int signature;
    
    private IccTagTypes(final String name, final String typeDescription, final int signature) {
        this.name = name;
        this.typeDescription = typeDescription;
        this.signature = signature;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getTypeDescription() {
        return this.typeDescription;
    }
    
    @Override
    public int getSignature() {
        return this.signature;
    }
}
