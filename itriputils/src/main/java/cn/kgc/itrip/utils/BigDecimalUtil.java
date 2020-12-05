package cn.kgc.itrip.utils;

import java.math.BigDecimal;

public class BigDecimalUtil{
    private BigDecimalUtil() {

    }

    /**
     * 两数相加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 两数相减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 两数相乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 两数相除 在做除法运算的时候可能会出现除不尽的问题
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        //四舍五入 ，保留两位小数
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * BigDecimal操作枚举定义
     */
    public enum BigDecimalOprations{
        add,subtract,multiply,divide
    }

    /**
     * Bigdecimal加减乘除运算
     * @param numOne [String Integer Long Double Bigdecimal]
     * @param numTwo [String Integer Long Double Bigdecimal]
     * @param bigDecimalOpration
     * @param scale
     * @param roundingMode
     * @return
     * @throws Exception
     */
    public static BigDecimal OperationASMD(Object numOne,Object numTwo,BigDecimalOprations bigDecimalOpration,int scale,int roundingMode) throws Exception{
        BigDecimal num1 = new BigDecimal(String.valueOf(numOne)).setScale(scale,roundingMode);
        BigDecimal num2 = new BigDecimal(String.valueOf(numTwo)).setScale(scale,roundingMode);
        switch (bigDecimalOpration){
            case add: return num1.add(num2).setScale(scale,roundingMode);
            case subtract: return num1.subtract(num2).setScale(scale,roundingMode);
            case multiply: return num1.multiply(num2).setScale(scale,roundingMode);
            case divide: return num1.divide(num2, scale, roundingMode);
        }
        return null;
    }

}
