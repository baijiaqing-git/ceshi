package cn.kgc.itrip.utils;


import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class SMSSendUtil {
    public static boolean sendSms(String phonenum, String b, String str[]) {
        HashMap<String, Object> result = null;
        boolean ok = false;
//初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");

        restAPI.setAccount("8a216da871bf71a10171f237421f10a2", "7240235d4f2047308e64f98e4adaa57d");


        restAPI.setAppId("8a216da871bf71a10171f23743b110a8");

        result = restAPI.sendTemplateSMS(phonenum, b, str);

        System.out.println("SDKTestGetSubAccounts result=" + result);
        String resultstate = (String) result.get("statusCode");

        if ("000000".equals(resultstate)) {
// //正常返回输出data包体信息（map）
// HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
// Set<String> keySet = data.keySet();
// for(String key:keySet){
// Object object = data.get(key);
// System.out.println(key +" = "+object);
// }
            ok = true;
        } else {
//异常返回输出错误码和错误信息
            //log.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            ok = false;
        }
        return ok;
    }

    public static void main(String[] args) {
        SMSSendUtil smsSendUtil = new SMSSendUtil();
        String[] str = {"999999", "5"};
        smsSendUtil.sendSms("15829804071", "1", str);
    }
}
