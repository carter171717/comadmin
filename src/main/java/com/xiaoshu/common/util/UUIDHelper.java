package com.xiaoshu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class UUIDHelper {

	private static final Logger logger = LoggerFactory.getLogger(UUIDHelper.class);

	private static final char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };
	private static final int maxNum = 36;

	private UUIDHelper() {
	}

	public static void main(String[] args) {
		String uuid = UUIDHelper.getUUID32();
		System.out.println("长度：" + uuid.length() + "字符串为：" + uuid.toUpperCase());
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String getUUID32() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		uuidStr = uuidStr.replace("-", "");
		return uuidStr;
	}

	public static String getUUIDNumber() {
		return Integer.toOctalString(getUUID().hashCode());
	}

	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getUUID(String prefix, int length) {
		String dateSerialNo = DateUtil.getCurrentDateStr("yyyyMMddHHmmssSSS");

		String uuid = null;
		if (StringUtils.isBlank(prefix)) {
			int len = length - dateSerialNo.length();
			if (len > 0) {
				uuid = dateSerialNo + genRandomNum(len);
			} else {
				uuid = dateSerialNo;
			}
		} else {
			int len = length - dateSerialNo.length() - prefix.length();
			if (len > 0) {
				uuid = prefix + dateSerialNo + genRandomNum(len);
			} else {
				uuid = prefix + dateSerialNo;
			}
		}
		return uuid;
	}

	public static String genRandomNum(int pwd_len) {
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		StringBuilder pwd = new StringBuilder("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static String getDateSerialNo() {
		long now = System.currentTimeMillis();
		return now + getUUIDNumber().substring(0, 3);
	}

	public static void replacePropertiesValue(String configPath, Class<?> clz) {
		Resource resource = new ClassPathResource(configPath);
		if (resource.exists()) {
			Properties properties = new Properties();
			try {
				properties.load(resource.getInputStream());
				@SuppressWarnings("unchecked")
				Enumeration<String> proEnum = (Enumeration<String>) properties.propertyNames();
				while (proEnum.hasMoreElements()) {
					String key = proEnum.nextElement();
					String value = properties.getProperty(key);
					try {
						Field field = clz.getField(key);
						Class<?> fieldType = field.getType();
						if (fieldType == int.class || fieldType == Integer.class) {
							field.set(null, Integer.parseInt(value));
						} else if (fieldType == float.class || fieldType == Float.class) {
							field.set(null, Float.parseFloat(value));
						} else if (fieldType == double.class || fieldType == Double.class) {
							field.set(null, Double.parseDouble(value));
						} else if (fieldType == long.class || fieldType == Long.class) {
							field.set(null, Long.parseLong(value));
						} else if (fieldType == boolean.class || fieldType == Boolean.class) {
							Boolean tmpValue = value.toUpperCase().equals("TRUE") ? Boolean.TRUE : Boolean.FALSE;
							field.set(null, tmpValue);
						} else {
							field.set(null, value);
						}
						logger.info("加载配置项：name:{}，value:{}", key, value);
					} catch (NoSuchFieldException e) {
						logger.warn("属性文件[{}]中属性为：{} 在{}中不存在！", configPath, key, clz.getName());
					} catch (IllegalAccessException e) {
						logger.error(String.format("设置{}的属性时出错"), clz.getName());
					}
				}
			} catch (IOException e) {
				logger.error("加载配置属性文件[{}]失败，原因为{}", configPath, e.getMessage());
			}
		} else {
			logger.warn("配置属性文件[{}]不存在！", configPath);
		}
	}
}
