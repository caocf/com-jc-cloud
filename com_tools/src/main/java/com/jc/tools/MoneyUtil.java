package com.jc.tools;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {
	/**
	 * @author chenzhao
	 * @param money
	 *            待处理的美金
	 ** @param isNeed
	 *            是否需要添加$符号
	 * @return
	 */
	public static String getFormatMoney(double money, boolean isNeed) {
		if (isNeed) {
			return getAccountantMoney(money, 2);
		} else {
			return getAccountantMoney(money, 2).replace("$", "");
		}
	}

	/**
	 * @author chenzhao
	 * @param money
	 *            待处理的美金
	 * @return
	 */
	public static String getFormatMoney(double money) {
		return getAccountantMoney(money, 2).replace("$", "");
	}

	/**
	 * 格式化金钱
	 * 
	 * @param money
	 *            待处理的美金
	 * @param num
	 *            小数位数
	 * @return User lukejia
	 */
	public static String getAccountantMoney(double money, int num) {
		NumberFormat currencyFormatB = NumberFormat
				.getCurrencyInstance(Locale.US);
		currencyFormatB.setMinimumFractionDigits(num);
		return currencyFormatB.format(money);
	}

	private final static String[] CN_Digits = { "零", "壹", "貳", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖", };

	/**
	 * @title 将数字型货币转换为中文型货币
	 * @author chenzhao
	 * @param moneyValue
	 *            字符串形式的金额，小数部分，将多于3位部分舍去，不做四舍五入
	 * @return
	 */
	public static String CNValueOf(String moneyValue) {
		// 使用正则表达式，去除前面的零及数字中的逗号
		String value = moneyValue.replaceFirst("^0+", "");
		value = value.replaceAll(",", "");
		// 分割小数部分与整数部分
		int dot_pos = value.indexOf('.');
		String int_value;
		String fraction_value;
		if (dot_pos == -1) {
			int_value = value;
			fraction_value = "00";
		} else {
			int_value = value.substring(0, dot_pos);
			fraction_value = value.substring(dot_pos + 1, value.length())
					+ "00".substring(0, 2);// 也加两个0，便于后面统一处理
		}

		int len = int_value.length();
		if (len > 16)
			return "值过大";
		StringBuffer cn_currency = new StringBuffer();
		String[] CN_Carry = new String[] { "", "万", "亿", "万" };
		// 数字分组处理，计数组数
		int cnt = len / 4 + (len % 4 == 0 ? 0 : 1);
		// 左边第一组的长度
		int partLen = len - (cnt - 1) * 4;
		String partValue = null;
		boolean bZero = false;// 有过零
		String curCN = null;
		for (int i = 0; i < cnt; i++) {
			partValue = int_value.substring(0, partLen);
			int_value = int_value.substring(partLen);
			curCN = Part2CN(partValue, i != 0 && !"零".equals(curCN));
			// System.out.println(partValue+":"+curCN);
			// 若上次为零，这次不为零，则加入零
			if (bZero && !"零".equals(curCN)) {
				cn_currency.append("零");
				bZero = false;
			}
			if ("零".equals(curCN))
				bZero = true;
			// 若数字不是零，加入中文数字及单位
			if (!"零".equals(curCN)) {
				cn_currency.append(curCN);
				cn_currency.append(CN_Carry[cnt - 1 - i]);
			}
			// 除最左边一组长度不定外，其它长度都为4
			partLen = 4;
			partValue = null;
		}
		cn_currency.append("元");
		// 处理小数部分
		int fv1 = Integer.parseInt(fraction_value.substring(0, 1));
		int fv2 = Integer.parseInt(fraction_value.substring(1, 2));
		if (fv1 + fv2 == 0) {
			cn_currency.append("整");
		} else {
			cn_currency.append(CN_Digits[fv1]).append("角");
			cn_currency.append(CN_Digits[fv2]).append("分");
		}
		return cn_currency.toString();
	}

	/**
	 * @title 将一组数字（不多于四个）转化成中文表示
	 * @author chenzhao
	 * @param partValue
	 *            字符串形式的数字
	 * @param bInsertZero
	 *            是否在前面添加零
	 * @return
	 */
	public static String Part2CN(String partValue, boolean bInsertZero) {
		// 使用正则表达式，去除前面的0
		partValue = partValue.replaceFirst("^0+", "");
		int len = partValue.length();
		if (len == 0)
			return "零";
		StringBuffer sbResult = new StringBuffer();
		int digit;
		String[] CN_Carry = new String[] { "", "拾", "佰", "仟" };
		for (int i = 0; i < len; i++) {
			digit = Integer.parseInt(partValue.substring(i, i + 1));
			if (digit != 0) {
				sbResult.append(CN_Digits[digit]);
				sbResult.append(CN_Carry[len - 1 - i]);
			} else {
				// 若不是最后一位，且下不位不为零，追加零
				if (i != len - 1
						&& Integer.parseInt(partValue.substring(i + 1, i + 2)) != 0)
					sbResult.append("零");
			}
		}
		if (bInsertZero && len != 4)
			sbResult.insert(0, "零");
		return sbResult.toString();
	}
	
	public static BigDecimal getNullBig(BigDecimal o){
		BigDecimal bigDecimal = new BigDecimal(0);
		if(null == o){
			return bigDecimal;
		}
		return o;
	}
	
	public static Double getNullDouble(Double o){
		Double doub = new Double(0);
		if(null == o){
			return doub;
		}
		return o;
	}

	public static void main(String[] args) {
		System.out.println(getFormatMoney(121212.34));
	}

}
