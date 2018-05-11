package com.infinite.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间的工具类
 * 
 * @author Stone
 *
 */
public class DateUtil {

	public static final String yrMonDayHrMinSec_ = "yyyy-MM-dd HH:mm:ss";
	public static final String yrMonDayHrMinSec = "yyyyMMddHHmmss";
	public static final String yrMonDayHrMinSecShortYr = "yyMMddHHmmss";
	public static final String yrMonDayHrMin_ = "yyyy-MM-dd HH:mm";
	public static final String yrMonDayHrMin = "yyyyMMddHHmm";
	public static final String yrMonDay_ = "yyyy-MM-dd";
	public static final String yrMonDay = "yyyyMMdd";
	public static final String yrMonDay2 = "yyMMdd";
	public static final String yrMon_ = "yyyy-MM";
	public static final String yrMon = "yyyyMM";
	public static final String hrMinSec_ = "HH:mm:ss";
	public static final String hrMinSec = "HHmmss";
	public static final String hrMin_ = "HH:mm";
	public static final String hrMin = "HHmm";
	public static final String year = "yyyy";
	public static final String month = "MM";
	public static final String day = "dd";
	public static final String hour = "HH";
	public static final String monDay = "MMdd";

	public static final SimpleDateFormat yrMonDayHrMinSecSDF_ = new SimpleDateFormat(yrMonDayHrMinSec_);
	public static final SimpleDateFormat yrMonDayHrMinSecSDF = new SimpleDateFormat(yrMonDayHrMinSec);
	public static final SimpleDateFormat yrMonDayHrMinSecShortYrSDF = new SimpleDateFormat(yrMonDayHrMinSecShortYr);
	public static final SimpleDateFormat yrMonDayHrMinSDF_ = new SimpleDateFormat(yrMonDayHrMin_);
	public static final SimpleDateFormat yrMonDayHrMinSDF = new SimpleDateFormat(yrMonDayHrMin);
	public static final SimpleDateFormat yrMonDaySDF_ = new SimpleDateFormat(yrMonDay_);
	public static final SimpleDateFormat yrMonDaySDF = new SimpleDateFormat(yrMonDay);
	public static final SimpleDateFormat yrMonDaySDF2 = new SimpleDateFormat(yrMonDay2);
	public static final SimpleDateFormat yrMonSDF_ = new SimpleDateFormat(yrMon_);
	public static final SimpleDateFormat yrMonSDF = new SimpleDateFormat(yrMon);
	public static final SimpleDateFormat hrMinSecSDF_ = new SimpleDateFormat(hrMinSec_);
	public static final SimpleDateFormat hrMinSecSDF = new SimpleDateFormat(hrMinSec);
	public static final SimpleDateFormat hrMinSDF_ = new SimpleDateFormat(hrMin_);
	public static final SimpleDateFormat hrMinSDF = new SimpleDateFormat(hrMin);
	public static final SimpleDateFormat yearSDF = new SimpleDateFormat(year);
	public static final SimpleDateFormat monthSDF = new SimpleDateFormat(month);
	public static final SimpleDateFormat daySDF = new SimpleDateFormat(day);
	public static final SimpleDateFormat hourSDF = new SimpleDateFormat(hour);
	public static final SimpleDateFormat monDaySDF = new SimpleDateFormat(monDay);

	// *********** 转换类的函数 ***********
	/**
	 * String To Date---TESTED
	 * 
	 * @param dateStr
	 *            待转换的字符串型日期；
	 * @param format
	 *            转化的日期格式
	 * @return 返回该字符串的日期型数据；
	 */
	public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 将日期串或日期时间串转换成日期,例如2015-08-29或2015-08-29 11:46:03转成Sat Aug 29 00:00:00 CST
	 * 2015---TESTED
	 * 
	 * @param dateStr
	 *            传入2015-08-29或2015-08-29 11:46:40的字符串,亲测 2015-08-29 aaa 的字符串也能转
	 * @return
	 */
	public static Date parseDate(String yr_mon_dayStr) {
		try {
			synchronized (yrMonDaySDF_) {
				return yrMonDaySDF_.parse(yr_mon_dayStr);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 将时期时间串转成日期 Sat Aug 29 12:00:14 CST 2015---TESTED
	 * 
	 * @param datetimeStr
	 *            传入2015-08-29 11:59:13, 不能是别的格式
	 * @return
	 */
	public static Date parseDateTime(String yr_mon_day_day_hr_min_secStr) {
		try {
			synchronized (yrMonDayHrMinSecSDF_) {
				return yrMonDayHrMinSecSDF_.parse(yr_mon_day_day_hr_min_secStr);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Date parseDateTimeNoSec(String yr_mon_day_day_hr_minStr) {
		try {
			synchronized (yrMonDayHrMinSDF_) {
				return yrMonDayHrMinSDF_.parse(yr_mon_day_day_hr_minStr);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 传入Unix时间戳,转换成日期.
	 * 
	 * 注意Unix时间戳精度到秒,所以传入的unixTime其实是个int,还没到达long, Unix时间戳和 new
	 * java.util.Date().getTime()得到的数字会少三位,即毫秒的三位.
	 * 
	 * @param unixTime
	 * @return
	 * @author Stone
	 */
	public static Date parseUnixTime(long unixTime) {
		long timestamp = unixTime * 1000;
		Date date = new Date(timestamp);
		return date;
	}

	public static Date parseUnixTime(int unixTime) {
		long timestamp = unixTime * 1000;
		Date date = new Date(timestamp);
		return date;
	}

	/**
	 * 时间转字符串格式---TESTED
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 转换成2015-08的字符串---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToYrMon_(Date date) {
		synchronized (yrMonSDF_) {
			return yrMonSDF_.format(date);
		}
	}

	/**
	 * 转换成2015-08-29的字符串格式---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToYrMonDay_(Date date) {
		synchronized (yrMonDaySDF_) {
			return yrMonDaySDF_.format(date);
		}
	}

	/**
	 * 把时间的时间部分去除, 只留取日期部分---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static Date trimTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);// 必须设置这个，否则毫秒数不同会导致compareDate方法在毫秒数上有差异
		return cal.getTime();
	}

	// *********** 获得当前日期、时间类的方法 ***********
	/**
	 * 获取当前日期时间 格式:年月日时分秒---TESTED 【应该改成now()，比较好使用】
	 * 
	 * @return
	 */
	// @Deprecated//命名不直观,后续优化会干掉
	public static Date getCurDateTime() {
		return new Date();
	}

	/**
	 * 优化的时候把上面的刚发干掉
	 * 
	 * @return
	 * @author Stone
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 获取当前日期 格式:年月日---TESTED
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurDateTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);// 尽量设置这个,
											// 否则返回的Date类型实际上还是保存着毫秒的，例如今天是2015-09-01，用此方法获得的Date和parseDate("2015-09-01")会有所不同，后者毫秒数是0
		return cal.getTime();
	}

	// *********** 获得当前日期、时间类的字符串 ***********
	/**
	 * 获得年月字符串 格式201508
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonStr(Date date) {
		synchronized (yrMonSDF) {
			return yrMonSDF.format(date);
		}
	}

	/**
	 * 获得年月字符串 格式2015-08
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonStr_(Date date) {
		synchronized (yrMonSDF_) {
			return yrMonSDF_.format(date);
		}
	}

	/**
	 * 获得年月字符串 格式20150805
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonDayStr(Date date) {
		synchronized (yrMonDaySDF) {
			return yrMonDaySDF.format(date);
		}
	}

	/**
	 * 获得年月字符串 格式150805
	 * 
	 * @param date
	 * @return
	 * @author Stone
	 */
	public static String getShortYearMonDayStr(Date date) {
		synchronized (yrMonDaySDF2) {
			return yrMonDaySDF2.format(date);
		}
	}

	/**
	 * 获得年月字符串 格式2015-08-01
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonDayStr_(Date date) {
		synchronized (yrMonDaySDF_) {
			return yrMonDaySDF_.format(date);
		}
	}
	
	public static String getHrMinSecStr(Date date) {
		synchronized (hrMinSecSDF) {
			return hrMinSecSDF.format(date);
		}
	}

	/**
	 * 获得年月日时分秒字符串 格式2015-11-11 19:52:35
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonDayHrMinSecStr_(Date date) {
		synchronized (yrMonDayHrMinSecSDF_) {
			return yrMonDayHrMinSecSDF_.format(date);
		}
	}

	/**
	 * 获得年月日时分秒字符串 格式2015-11-11 19:52
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonDayHrMinStr_(Date date) {
		synchronized (yrMonDayHrMinSDF_) {
			return yrMonDayHrMinSDF_.format(date);
		}
	}

	/**
	 * 获得当前时间 格式：20141202103853---TESTED
	 * 
	 * @return String
	 */
	public static String getCurTimestampStr() {
		synchronized (yrMonDayHrMinSecSDF) {
			return yrMonDayHrMinSecSDF.format(getCurDateTime());
		}
	}

	/**
	 * 获得当前时间的年月日时分秒，2位的年
	 * 
	 * @return
	 * @author Stone
	 */
	public static String getCurTimestampShortYrStr() {
		synchronized (yrMonDayHrMinSecShortYrSDF) {
			return yrMonDayHrMinSecShortYrSDF.format(now());
		}
	}

	/**
	 * 获取今天的日期 格式：20141202---TESTED
	 * 
	 * @return String
	 */
	public static String getCurDatestampStr() {
		synchronized (yrMonDaySDF) {
			return yrMonDaySDF.format(new Date());
		}
	}

	/**
	 * 获得当前时间 格式：2014-12-02 10:38:53---TESTED
	 * 
	 * @return String
	 */
	public static String getCurDateTimeStr() {
		synchronized (yrMonDayHrMinSecSDF_) {
			return yrMonDayHrMinSecSDF_.format(getCurDateTime());
		}
	}

	/**
	 * 获取年月日 也就是当前时间 格式：2014-12-02---TESTED
	 * 
	 * @return String
	 */
	public static String getCurDateStr() {
		synchronized (yrMonDaySDF_) {
			return yrMonDaySDF_.format(getCurDateTime());
		}
	}

	/**
	 * 获取年月日 也就是当前时间 格式：2014-12---TESTED
	 * 
	 * @return String
	 */
	public static String getCurYearMonStr() {
		synchronized (yrMonSDF_) {
			return yrMonSDF_.format(getCurDateTime());
		}
	}

	/**
	 * 获取当前的年---TESTED
	 * 
	 * @return String
	 */
	public static String getCurYear() {
		synchronized (yearSDF) {
			return yearSDF.format(getCurDateTime());
		}
	}

	/**
	 * 获取当前的月---TESTED
	 * 
	 * @return String
	 */
	public static String getCurMonth() {
		synchronized (monthSDF) {
			return monthSDF.format(getCurDateTime());
		}
	}

	/**
	 * 获取当前的日---TESTED
	 * 
	 * @return String
	 */
	public static String getCurDay() {
		synchronized (daySDF) {
			return daySDF.format(getCurDateTime());
		}
	}

	// *********** 功能性的方法 ***********_*************************************

	/**
	 * 返回1~7分别代表周一到周日
	 * 
	 * @param date
	 * @return
	 * @author Stone
	 */
	public static int getDayIndexOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int index = cal.get(Calendar.DAY_OF_WEEK);
		switch (index) {
		case 1:
			return 7;// 周日
		case 2:
			return 1;// 周一
		case 3:
			return 2;// 周二
		case 4:
			return 3;// 周三
		case 5:
			return 4;// 周四
		case 6:
			return 5;// 周五
		default:
			return 6;// 周六
		}
	}

	/**
	 * 返回周一到周日
	 * 
	 * @param date
	 * @return
	 * @author Stone
	 */
	public static String getDayStrOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int index = cal.get(Calendar.DAY_OF_WEEK);
		switch (index) {
		case 1:
			return "周日";// 周日
		case 2:
			return "周一";// 周一
		case 3:
			return "周二";// 周二
		case 4:
			return "周三";// 周三
		case 5:
			return "周四";// 周四
		case 6:
			return "周五";// 周五
		default:
			return "周六";// 周六
		}
	}

	/**
	 * 获得日期是当月第几天,从1开始
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayIndexOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int index = cal.get(Calendar.DAY_OF_MONTH);
		return index;
	}

	/**
	 * 获得某个日期所在月份一共有多少天---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static int getHowManyDaysInMon(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.getActualMaximum(Calendar.DATE);
		return day;
	}

	/**
	 * 获得指定日期所在当月第一天(时间部分会保留)---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 获得指定日期所在当月最后一天(时间部分会保留), 能正确识别月份不同天数---TESTED
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 判断两个时间是否相等, 必须年月日时分秒都相等 ---TESTED <br>
	 * 相等: 2015-08-19 0:0:0 和 2015-08-19 <br>
	 * 不等: 2015-08-19 14:28:47 和 2015-08-19 14:28:51 <br>
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqualDateTime(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.equals(cal2);
	}

	/**
	 * 仅仅比较日期是否相等, 不比较时间 ---TESTED <br>
	 * 相等: 2015-08-19 14:28:47 和 2015-08-19 14:28:51、2015-08-18 和 2015-08-18<br>
	 * 不等: 2015-08-18 和 2015-08-19、2015-08-19 14:32:51和2015-08-18 14:32:57
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqualDate(Date date1, Date date2) {
		date1 = trimTimeFromDate(date1);
		date2 = trimTimeFromDate(date2);
		return isEqualDateTime(date1, date2);
	}

	/**
	 * 比较日期时间先后顺序 ---TESTED<br>
	 * 辅助理解: 1.所谓时间大于,未来的时间比现在大
	 * 2.看着入参列出等式,如date1-date2,结果是1就表示大于0,即date1-date2>0亦即date1>date2
	 * 
	 * @param date1
	 * @param date2
	 * @return date1>date2返回1,date1<date2返回-1,date1=date2返回0 <br>
	 */
	public static int compareDateTime(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (isEqualDateTime(date1, date2)) {
			return 0;
		}
		if (cal1.after(cal2)) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * 比较日期的先后顺序---TESTED<br>
	 * 
	 * @param date1
	 * @param date2
	 * @return date1>date2返回1,date1<date2返回-1,date1=date2返回0 <br>
	 */
	public static int compareDate(Date date1, Date date2) {
		date1 = trimTimeFromDate(date1);
		date2 = trimTimeFromDate(date2);
		return compareDateTime(date1, date2);
	}

	/**
	 * 增加或减少天数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);
		return cal.getTime();
	}

	/**
	 * 增加或减少月份数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addMonths(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}

	/**
	 * 增加或减少年份数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addYears(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}

	/**
	 * 增加或减少小时数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, amount);// 用HOUR和HOUR_OF_DAY结果一样
		return cal.getTime();
	}

	/**
	 * 增加或减少分钟数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, amount);
		return cal.getTime();
	}

	/**
	 * 增加或减少秒数,注意入参本身是不改变的---TESTED
	 * 
	 * @param date
	 * @param amount
	 *            可以传负整数
	 * @return
	 */
	public static Date addSecounds(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, amount);
		return cal.getTime();
	}

	/**
	 * 1. 计算两个日期之间有多少天<br>
	 * 2. 只考虑日期,不考虑时间 <br>
	 * 3. 包括开始日期,不包括结束 <br>
	 * 4. 举例: 2015-08-19 和 2015-08-18 结果是1 2015-08-19和2015-08-19结果是0 <br>
	 * 
	 * @param endDate
	 *            第一个时间
	 * @param beginDate
	 *            第二个时间
	 * @return 天数
	 */
	public static long getTwoDateInterval(Date endDate, Date beginDate) {
		endDate = trimTimeFromDate(endDate);
		beginDate = trimTimeFromDate(beginDate);
		long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return Math.abs(day);
	}

	/**
	 * 查询两个时间之间相差的天数,不足一天按一天计算<br>
	 * 两个日期可以随便传按照任意顺序传入
	 * 
	 * @param endDateTime
	 *            具体到时间,如2015-11-17 12:29:52
	 * @param beginDateTime
	 *            具体到时间,如2015-11-17 12:29:52
	 * @return
	 */
	public static long getTwoDateTimeInterval(Date endDateTime, Date beginDateTime) {
		long l = endDateTime.getTime() - beginDateTime.getTime();
		long dayMillSec = 24 * 60 * 60 * 1000;
		double d = ((double) l) / ((double) dayMillSec);
		d = Math.abs(d);// 免得入参传反了导致问题
		Double day = Math.ceil(d);
		return Math.abs(day.longValue());
	}

	
	public static int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			int yearNow = now.get(Calendar.YEAR);
			int yearBirth = birth.get(Calendar.YEAR);
			age = yearNow - yearBirth;
			if (age <= 0) {
				return 0;
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}

	/**
	 * 1. 计算两个日期之间有多少天<br>
	 * 2. 只考虑日期,不考虑时间 <br>
	 * 3. 既包括开始日期,也包括结束 <br>
	 * 4. 举例: 2015-08-19 和 2015-08-18 结果是2 2015-08-19和2015-08-19结果是1 <br>
	 * 
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static long getTwoDateIntervalContainEnd(Date endDate, Date beginDate) {
		return getTwoDateInterval(endDate, beginDate) + 1;
	}

	/**
	 * 获得一个月有多少天 可以传入日期或日期时间2015-09-15 或 2015-09-15 10:51:02
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMon(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		return dateOfMonth;
	}

	/**
	 * 断是否在当天12:00:00前(包含)
	 * 
	 * @param datetime
	 * @param containBolder
	 *            是否包含边界
	 * @return
	 */
	public static boolean isBefore120000(Date datetime, boolean containBolder) {
		Date datetime120000 = DateUtil.parseDateTime(DateUtil.getYearMonDayStr_(datetime) + " 12:00:00");
		int comp = DateUtil.compareDateTime(datetime, datetime120000);
		if (containBolder) {
			return comp <= 0 ? true : false;
		} else {
			return comp < 0 ? true : false;
		}
	}

	/**
	 * 判断是否在当天14:00:00后(不包含)
	 * 
	 * @param datetime
	 * @return
	 */
	public static boolean isAfter140000(Date datetime, boolean containBolder) {
		Date datetime140000 = DateUtil.parseDateTime(DateUtil.getYearMonDayStr_(datetime) + " 14:00:00");
		int comp = DateUtil.compareDateTime(datetime, datetime140000);
		if (containBolder) {
			return comp >= 0 ? true : false;
		} else {
			return comp > 0 ? true : false;
		}
	}

	// 距离当天24:00:00的分钟数---TESTED
	public static int todayRemainMinutes(Date datetime) {
		Date today24 = parseDateTime(getYearMonDayStr_(datetime) + " 24:00:00");
		return (int) ((today24.getTime() - datetime.getTime()) / 1000 * 60);
	}

	// 距离当天24:00:00的秒数---TESTED
	public static int todayRemainSecs(Date datetime) {
		Date today24 = parseDateTime(getYearMonDayStr_(datetime) + " 24:00:00");
		return (int) ((today24.getTime() - datetime.getTime()) / 1000);
	}

	public static long toSeconds(long date) {
		return date / 1000L;
	}

	public static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	public static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	public static long toDays(long date) {
		return toHours(date) / 24L;
	}

	public static long toWeeks(long date) {
		return toDays(date) / 7L;
	}

	public static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	@SuppressWarnings("unused")
	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}
}