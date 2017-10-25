package com.boil.hospitalorder.utils;


/**
 * 
 * 常量工具类。
 * 
 * @author ChenYong
 * @date 2016-01-26
 * 
 */
public interface Constants {
	/*------------------------------[接口地址]------------------------------*/
	/** 通用接口地址的命名空间 */
	public static final String WS_NAMESPACE_1 = "http://webser.data.zlyy";
	/** 通用接口地址 */
	public static final String WS_URL_1 = "http://58.42.232.110:8085/ZlyyData/services/ZlyyData";
	/** 特殊接口地址的命名空间 */
	public static final String WS_NAMESPACE_2 = "http://port.zlyy.com/";
	/** 特殊接口地址 */
	public static final String WS_URL_2 = "http://yyghw.gzszlyy.cn:8086/ZlyyWebSer/ZlyySerPort";
	/** 通用接口地址的命名空间 */
	public static final String WS_NAMESPACE_3 = "http://appointment.com.zlyy";
	/** 通用接口地址 */
	public static final String WS_URL_3 = "http://58.42.232.110:8085/ZlyyAppointment/services/ZlyyAppointment";
	
	public static final String WEB_DINNER_URL = "http://58.42.232.110:8086/hsptapp-web/mobile";
	
	public static final String WEB_URL_4 = "http://58.42.232.110:8086";
	/*------------------------------[/接口地址]------------------------------*/

	/*------------------------------[正则表达式]------------------------------*/
	/** 姓名正则表达式 */
	public static final String NAME_RE = "^[\u4e00-\u9fa5]{2,5}$";
	public static final String NAME_RE_MSG = "姓名由2至5个汉字组成";
	/** 手机号正则表达式 */
	public static final String PHONE_RE = "^1[3-9][0-9]{9}$";
	public static final String PHONE_RE_MSG = "手机号格式错误";
	/** 密码正则表达式 */
	public static final String PASSWD_RE = "^[0-9A-Za-z`~!@#%&_:;=',<>/\"\\+\\-\\*\\.\\?\\$\\|\\(\\)\\{\\}\\[\\]\\\\]{6,16}$";
	public static final String PASSWD_RE_MSG = "密码由6至16个字符、数字组成";
	
	public static final String PASSWD_RE2 = "^[0-9A-Za-z]{8,18}$";
	public static final String PASSWD_RE_MSG2 = "密码由6至16个字符、数字组成";
	
	public static final String PASSWD_RE3 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
	public static final String PASSWD_RE_MSG3 = "密码由8至16个字符和数字组成";
	
	/** 验证码正则表达式 */
	public static final String CAPTCHA_RE = "^[0-9]{6}$";
	public static final String CAPTCHA_RE_MSG = "验证码由6个数字组成";
	/** 银行卡正则表达式 */
	public static final String BANK_CARD_NO_RE = "^([0-9]{16})|([0-9]{19})$";
	public static final String BANK_CARD_NO_RE_MSG = "银行卡号格式错误";
	/** 银行卡 CVN2 正则表达式 */
	public static final String BANK_CARD_CVN2_RE = "^([0-9]{3})|([0-9]{4})$";
	public static final String BANK_CARD_CVN2_RE_MSG = "CVN2由3或4位数字组成";
	/** 银行卡有效期正则表达式 */
	public static final String BANK_CARD_VALID_DATE_RE = "^[0-9]{2}[0-9]{2}$";
	public static final String BANK_CARD_VALID_DATE_RE_MSG = "有效期格式错误";
	/*------------------------------[/正则表达式]------------------------------*/


	/*------------------------------[常量]------------------------------*/
	/** 用户是否登录的标志 */
	public static final String LOGIN_FLAG = "login_flag";
//	/** 注册用户的手机号 */
//	public static final String LOGIN_PHONE = "login_phone";
//	/** 注册用户的密码 */
//	public static final String LOGIN_PASSWD = "login_password";
//	/** 注册用户 ID */
//	public static final String LOGIN_USER_ID = "login_user_id";
//	/** 注册用户的姓名 */
//	public static final String LOGIN_NAME = "login_name";
//	/** 注册用户的身份证号 */
//	public static final String LOGIN_ID_NUMBER = "login_id_number";
//	/** 黑名单用户标志 */
//	public static final String BLACK_FLAG = "black_flag";
	
	
	
	public static final int PATIENT_REQ = 22;
	public static final int PATIENT_RESP = 23;
	/*------------------------------[/常量]------------------------------*/

	/*------------------------------[变量常量]------------------------------*/
	/** 56 个名族 */
	public static final String[] NATION_56 = new String[] { "汉族", "壮族", "满族",
			"回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族",
			"朝鲜族", "白族", "哈尼族", "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族",
			"高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族", "柯尔克孜族",
			"达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族",
			"怒族", "京族", "基诺族", "德昂族", "保安族", "俄罗斯族", "裕固族", "乌孜别克族", "门巴族",
			"鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族" };
	/*------------------------------[/变量常量]------------------------------*/
	
	//http://yyghw.gzszlyy.cn:8086/doctors/imgs/   http://58.42.232.110:8086/zlyydoctor/zlyypic/http://58.42.232.110:8086/zlyydoctor/zlyypic/1015.jpg 
	public static final String IMAGE_STR = "http://58.42.232.110:8086/zlyydoctor/zlyypic/";
	
	public static final String apkName = "yiyi";
	
	/** 图片缓冲区目录 */
	public static final String IMAGE_CACHE_DIR = "bitmap";
	/**二期开发*/
	public static final String SUBSCRIBE_ITEM_SELECT = "com.boil.hospitalsecond.subscribe.spar.CopyOfSubscribeTabFragment"; 

	
	//医生端
	
	/*------------------------------[登录信息]------------------------------*/
	/**登录的id*/
	public static final String LOGIN_INFO_ID = "login_info_id";
	/**登录的密码*/
	public static final String LOGIN_INFO_PWD = "login_info_pwd";
	/**登录医生hisid*/
	public static final String LOGIN_INFO_HISID = "login_info_hisid";
	/**登录医生isdefault标识*/
	public static final String LOGIN_INFO_ISDEFAULT = "login_info_isdefault";
	/**登录医生科室名称*/
	public static final String LOGIN_INFO_DEP_NAME = "login_info_dep_name";
	public static final String LOGIN_INFO_SUPERVISE = "login_info_supervise";
	
	/**该医生所属的医院id*/
	public static final String LOGIN_INFO_HID="login_info_hid";
	/**该医生所属的医院名称*/
	public static final String LOGIN_INFO_HOS_NAME="login_info_hos_name";
	/**登录医生科室列表json*/
	public static final String LOGIN_INFO_DEPT_JSON="login_info_dept_json";
	
	public static final String HOSPITAL_LOGIN_ADD="hos_login_address";
	
	/*------------------------------[登录信息]------------------------------*/
	/*------------------------------[用户信息]------------------------------*/
	/**用户id*/
	public static final String USER_ID="user_id";
	/**用户身份证*/
	public static final String USER_ID_NUMBER="user_id_number";
	/**用户姓名*/
	public static final String USER_NAME="user_name";
	/**用户电话*/
	public static final String USER_PHONE="user_phone";
	
	public static final String USER_HISID="user_hisid";
	
	
	/*------------------------------[用户信息]------------------------------*/
	
	/*------------------------------[广播 Action]------------------------------*/
	public static final String REFRESH_ONE_TOPIC_BROADCAST_ACTION = "com.boil.pay.view.ChooseIssuingBankActivity";
	public static final String MESSAGE_PLATFORM_DETAIL_BROADCAST_ACTION = "com.boil.hospitalsecond.docpatientcircle.spar.MessagePlatformDetailActivity";
	public static final String MESSAGE_PLATFORM_BROADCAST_ACTION = "com.boil.hospitalsecond.docpatientcircle.spar.MessagePlatformActivity";
	public static final String TOPIC_SEARCH_BROADCAST_ACTION = "com.boil.hospitalsecond.docpatientcircle.spar.TopicSearchActivity";
	/*------------------------------[/广播 Action]------------------------------*/

	
	
	public static final String SUBSCRIBE_SPID = "2";
	public static final String ANNOUNCEMENT_SPID = "3";//公告
	public static final String NOTICE_SPID = "4"; //通知
	/**最近联系人json*/
	public static final String LATEST_VISIT_PATIENT_JSON = "latest_visit_patient_json"; 
	
}