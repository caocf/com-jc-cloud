package com.jc.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.jc.tools.HttpClientUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 消息推送统一接口
 *
 * @author xikai
 * @version 1.0, 2013-9-17
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component("PushMsgClient")
public class PushMsgClient {
	@Value("#{sysConfig['pushMsg.url']}")
	private String url = "";
	@Resource(name="pushMsgTaskExecutor")
	private TaskExecutor task;
	private static Log LOG = LogFactory.getLog(PushMsgClient.class);
	/**
	 * 消息类型：客户消息
	 */
	private static final String MSG_TYPE_CUSTOMER = "CUSTOMER";
	/**
	 * 消息类型：市场消息
	 */
	private static final String MSG_TYPE_MARKET = "MARKET";
	/**
	 * 消息类型：系统消息
	 */
	private static final String MSG_TYPE_SYSTEM = "SYSTEM";
	
	/**
	 * 功能描述: <br>
	 * 推送消息统一接口
	 * @param msgType 消息类型 MessageType
	 * @param msgId 消息id
	 * @param companyId[可选：只有客户消息和系统消息必须] 接收消息企业id(该企业下的所有已登录用户都会收到消息)
	 * @return
	 * @throws
	 * @exception {说明}
	 * @since 1.0
	 * @auther xikai
	 */
	private boolean pushMsg(String msgType,Integer msgId, Integer companyId){
		final Map<String, String> params = new HashMap<String, String>();
		params.put("msgType", msgType);
		params.put("msgId", msgId == null ? null : msgId.toString());
		params.put("companyId", companyId == null ? null : companyId.toString());
		LOG.info("消息推送开始param:" + JSONObject.toJSONString(params));
		try {
			task.execute(new Runnable() {
				@Override
				public void run() {
					try {
						HttpClientUtils.postHTML(url, params);
					} catch (Exception e) {
						LOG.error("消息推送失败：" + e.getMessage());
					}
				}
			});
			return true;
		} catch (Exception e) {
			LOG.error("消息推送失败：" + e.getMessage());
			return false;
		}
	}
	/**
	 * 功能描述: <br>
	 * 推送市场消息
	 * @param msgId
	 *            消息id
	 * @since 1.0
	 * @auther xikai
	 */
	public boolean pushMarketMsg(Integer msgId){
		if(msgId == null){
			return false;
		}else{
			return pushMsg(MSG_TYPE_MARKET, msgId, null);
		}
	}
	/**
	 * 功能描述: <br>
	 * 推送系统消息
	 * @param msgId
	 *            消息id
	 * @param companyId
	 *            [系统消息必须] 接收消息企业id(该企业下的所有已登录用户都会收到消息)
	 * @since 1.0
	 * @auther xikai
	 */
	public boolean pushSystemMsg(Integer msgId, Integer companyId){
		if(msgId == null || companyId == null){
			return false;
		}else{
			return pushMsg(MSG_TYPE_SYSTEM, msgId, companyId);
		}
	}
	/**
	 * 功能描述: <br>
	 * 推送客户消息
	 * @param msgId
	 *            消息id
	 * @since 1.0
	 * @auther xikai
	 */
	public boolean pushCustomerMsg(Integer msgId){
		if(msgId == null){
			return false;
		}
		return pushMsg(MSG_TYPE_CUSTOMER, msgId, null);
	}
	public String getUrl() {
		return url;
	}
}
