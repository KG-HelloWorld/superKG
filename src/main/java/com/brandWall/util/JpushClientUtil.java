package com.brandWall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushClientUtil {

	protected static final Logger LOG = LoggerFactory.getLogger(JpushClientUtil.class);

	/*public static JPushClient getJPushClient() {

		ClientConfig config = ClientConfig.getInstance();

		config.setGlobalPushSetting(false, 60 * 60 * 24); // development env,
															// one day

		JPushClient jPushClient = new JPushClient(Config.jpushSecret, Config.jpushAppKey, null, config); // JPush

		return jPushClient;
	}*/

	public static PushPayload buildPushObject_android_and_ios(String title, String alert, String tag) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.tag(tag))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
						.addPlatformNotification(
								IosNotification.newBuilder().incrBadge(1).addExtra("extra_key", "extra_value").build())
						.build())
				.build();
	}

	public static void sendPush(JPushClient jpushClient, String title, String alert, String tag) {

		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_android_and_ios(title, alert, tag);
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

}
