package com.example.capstoneproject1.services;

import com.example.capstoneproject1.dto.request.payment.OrderRequestDTO;
import com.example.capstoneproject1.dto.request.payment.RefundRequestDTO;
import com.example.capstoneproject1.dto.request.payment.RefundStatusRequestDTO;
import com.example.capstoneproject1.dto.request.payment.StatusRequestDTO;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.user.UserService;
import com.example.capstoneproject1.util.HMACUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ZaloPayService {
    @Autowired
    UserService userService;
    @Autowired
    SpaceService spaceService;
    @Value("${zalopay.appId}")
    private Integer appId;

    @Value("${zalopay.key1}")
    private String key1;

    @Value("${zalopay.key2}")
    private String key2;

    public static final String ORDER_CREATE_ENDPOINT = "https://sandbox.zalopay.com.vn/v001/tpe/createorder";

    public static final String ORDER_STATUS_ENDPOINT = "https://sb-openapi.zalopay.vn/v2/query";

    public static final String REFUND_PAYMENT_ENDPOINT = "https://sb-openapi.zalopay.vn/v2/refund";

    public static final String REFUND_STATUS_PAYMENT_ENDPOINT = "https://sb-openapi.zalopay.vn/v2/query_refund";

    private String getCurrentTimeString(String format) {

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }
    //create order
    public Map<String, Object> createOrder(OrderRequestDTO orderRequestDTO) throws IOException, JSONException {
        User user = userService.findByUserId(orderRequestDTO.getUserId());
        Space space = spaceService.findSpaceById(orderRequestDTO.getId());
        Map<String,Object> spa = new HashMap<String, Object>(){{
            put("id", space.getId());
            put("price",space.getPrice());
            put("address",space.getAddress());
        }};
        Map<String, Object> order = new HashMap<String, Object>(){{
            put("app_id", appId);
            put("app_trans_id", getCurrentTimeString("yyMMdd") +"_"+ new Date().getTime()); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
            put("app_time", System.currentTimeMillis()); // miliseconds
            put("app_user", user.getId()+" "+user.getName());
            put("amount", space.getPrice());
            put("description", "Shared Space Finder - Payment for the booking #" + getCurrentTimeString("yyMMdd") +"_"+ new Date().getTime());
            put("item", new JSONObject(spa));
            put("embed_data", "");
            put("callback_url", "http://localhost:8080/api/v1/callback");
        }};
        String data = order.get("app_id") +"|"+ order.get("app_trans_id") +"|"+ order.get("app_user") +"|"+ order.get("amount")
                +"|"+ order.get("app_time") +"|"+ order.get("embed_data") +"|"+ order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(ORDER_CREATE_ENDPOINT);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {

            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {

            resultJsonStr.append(line);
        }

        JSONObject jsonResult = new JSONObject(resultJsonStr.toString());
        Map<String, Object> finalResult = new HashMap<>();
        for (Iterator it = jsonResult.keys(); it.hasNext(); ) {

            String key = (String) it.next();
            finalResult.put(key, jsonResult.get(key));
        }

        return finalResult;
    }
//    public Map<String, Object> createOrder(Integer userId, Integer id) throws IOException, JSONException {
//        Map<String, String> config = new HashMap<String, String>(){{
//            put("app_id", "554");
//            put("key1", "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn");
//            put("key2", "uUfsWgfLkRLzq6W2uNXTCxrfxs51auny");
//            put("endpoint", "https://sandbox.zalopay.com.vn/v001/tpe/createorder");
//
//        }};
//        Random rand = new Random();
//        int random_id = rand.nextInt(1000000);
//        final Map embed_data = new HashMap(){{}};
//
//        final Map[] item = {
//                new HashMap(){{}}
//        };
//
//        Map<String, Object> order = new HashMap<String, Object>(){{
//            put("app_id", config.get("app_id"));
//            put("app_trans_id", getCurrentTimeString("yyMMdd") +"_"+ random_id); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
//            put("app_time", System.currentTimeMillis()); // miliseconds
//            put("app_user", "user123");
//            put("amount", 50000);
//            put("description", "Lazada - Payment for the order #"+random_id);
//            put("bank_code", "zalopayapp");
//            put("item", new JSONObject(item).toString());
//            put("embed_data", new JSONObject(embed_data).toString());
//        }};
//
//        // app_id +”|”+ app_trans_id +”|”+ appuser +”|”+ amount +"|" + app_time +”|”+ embed_data +"|" +item
//        String data = order.get("app_id") +"|"+ order.get("app_trans_id") +"|"+ order.get("app_user") +"|"+ order.get("amount")
//                +"|"+ order.get("app_time") +"|"+ order.get("embed_data") +"|"+ order.get("item");
//        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(config.get("endpoint"));
//
//        List<NameValuePair> params = new ArrayList<>();
//        for (Map.Entry<String, Object> e : order.entrySet()) {
//            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
//        }
//
//        // Content-Type: application/x-www-form-urlencoded
//        post.setEntity(new UrlEncodedFormEntity(params));
//
//        CloseableHttpResponse res = client.execute(post);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
//        StringBuilder resultJsonStr = new StringBuilder();
//        String line;
//
//        while ((line = rd.readLine()) != null) {
//            resultJsonStr.append(line);
//        }
//
//        JSONObject result = new JSONObject(resultJsonStr.toString());
//        for (String key : result.keySet()) {
//            System.out.format("%s = %s\n", key, result.get(key));
//        }
//        Map<String, Object> finalResult = new HashMap<>();
//        for (Iterator it = result.keys(); it.hasNext(); ) {
//
//            String key = (String) it.next();
//            finalResult.put(key, result.get(key));
//        }
//
//        return finalResult;
//    }
//    public Map<String, Object> createOrder(Integer userId, Integer id) throws IOException {
//        User user = userService.findByUserId(userId);
//        Space space = spaceService.findSpaceById(id);
//        Map<String, Object> spa = new HashMap<String, Object>() {{
//            put("id", space.getId());
//            put("price", space.getPrice());
//            put("address", space.getAddress());
//        }};
//        Map<String, Object> order = new HashMap<String, Object>() {{
//            put("app_id", appId);
//            put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + new Date().getTime());
//            put("app_time", System.currentTimeMillis());
//            put("app_user", user.getName());
//            put("amount", space.getPrice().longValue());
//            put("description", "Shared Space Finder - Payment for the booking #" + getCurrentTimeString("yyMMdd") + "_" + new Date().getTime());
//            put("item", new Gson().toJson(spa));
//            put("bank_code", "zalopayapp");
//            put("embed_data", "");
//            put("callback_url", "http://localhost:8080/api/v1/callback");
//        }};
//        System.out.println(order.get("item"));
//        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|" + order.get("amount")
//                + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|" + order.get("item");
//        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data));
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(ORDER_CREATE_ENDPOINT);
//        System.out.println("post" + post);
//        post.setHeader("Content-Type", "application/json");
//
//        // Chuyển đổi order thành chuỗi JSON
//        String json = new Gson().toJson(order);
//        System.out.println(json);
//        StringEntity requestEntity = new StringEntity(json);
//        post.setEntity(requestEntity);
//
//        CloseableHttpResponse res = client.execute(post);
//        System.out.println(res.getEntity().getContent());
//
//        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
//        System.out.println(rd);
//        StringBuilder resultJsonStr = new StringBuilder();
//        String line;
//
//        while ((line = rd.readLine()) != null) {
//            System.out.println(line);
//
//            resultJsonStr.append(line);
//        }
//        JSONObject jsonResult = new JSONObject(resultJsonStr.toString());
//        System.out.println(jsonResult);
//        Map<String, Object> finalResult = new HashMap<>();
//        for (Iterator<String> it = jsonResult.keys(); it.hasNext(); ) {
//            String key = it.next();
//            finalResult.put(key, jsonResult.get(key));
//        }
//        System.out.println(finalResult);
//        return finalResult;
//    }
    //--------------------------------------------------------------------------------------------------------------------
    //call back
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Object doCallBack(JSONObject result, String jsonStr) throws JSONException, NoSuchAlgorithmException, InvalidKeyException {

        Mac HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(key2.getBytes(), "HmacSHA256"));

        try {
            JSONObject cbdata = new JSONObject(jsonStr);
            String dataStr = cbdata.getString("data");
            String reqMac = cbdata.getString("mac");

            byte[] hashBytes = HmacSHA256.doFinal(dataStr.getBytes());
            String mac = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();

            // check if the callback is valid (from ZaloPay server)
            if (!reqMac.equals(mac)) {
                // callback is invalid
                result.put("returncode", -1);
                result.put("returnmessage", "mac not equal");
            } else {
                // payment success
                // merchant update status for order's status
                JSONObject data = new JSONObject(dataStr);
                logger.info("update order's status = success where app_trans_id = " + data.getString("app_trans_id"));

                result.put("return_code", 1);
                result.put("return_message", "success");
            }
        } catch (Exception ex) {
            result.put("return_code", 0); // callback again (up to 3 times)
            result.put("return_message", ex.getMessage());
        }

        return result;
    }
    //--------------------------------------------------------------------------------------------------------------------
    //get status
//    public Map<String, Object> statusOrder(StatusRequestDTO statusRequestDTO) throws URISyntaxException, IOException, JSONException {
//
////        String appTranId = "210608_2553_1623145380738";  // Input your app_trans_id
////        String data = appId +"|"+ statusRequestDTO.getAppTransId()  +"|"+ key1; // appid|app_trans_id|key1
//        String data = appId +"|"+ "abc"  +"|"+ key1; // appid|app_trans_id|key1
//        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("appid", appId));
////        params.add(new BasicNameValuePair("apptransid", statusRequestDTO.getAppTransId()));
//        params.add(new BasicNameValuePair("apptransid", "abc"));
//        params.add(new BasicNameValuePair("mac", mac));
//
//        URIBuilder uri = new URIBuilder(ORDER_STATUS_ENDPOINT);
//        uri.addParameters(params);
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(uri.build());
//        post.setEntity(new UrlEncodedFormEntity(params));
//
//        CloseableHttpResponse res = client.execute(post);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
//        StringBuilder resultJsonStr = new StringBuilder();
//        String line;
//
//        while ((line = rd.readLine()) != null) {
//
//            resultJsonStr.append(line);
//        }
//
//        JSONObject result = new JSONObject(resultJsonStr.toString());
//        Map<String, Object> finalResult = new HashMap<>();
//        finalResult.put("returncode", result.get("returncode"));
//        finalResult.put("returnmessage", result.get("returnmessage"));
//        finalResult.put("isprocessing", result.get("isprocessing"));
//        finalResult.put("amount", result.get("amount"));
//        finalResult.put("discountamount", result.get("discountamount"));
//        finalResult.put("zptransid", result.get("zptransid"));
//        return finalResult;
//    }
    //---------------------------------------------------------------------------------------------------------------------
    //sendRefund
    public Map<String, Object> sendRefund(RefundRequestDTO refundRequestDTO) throws JSONException, IOException {

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("app_id", appId);
            put("zp_trans_id", refundRequestDTO.getZpTransId());
            put("m_refund_id", getCurrentTimeString("yyMMdd") +"_"+ appId +"_"+
                    System.currentTimeMillis() + "" + (111 + new Random().nextInt(888)));
            put("timestamp", System.currentTimeMillis());
            put("amount", refundRequestDTO.getAmount());
            put("description", refundRequestDTO.getDescription());
        }};

        String data = order.get("app_id") +"|"+ order.get("zp_trans_id") +"|"+ order.get("amount")
                +"|"+ order.get("description") +"|"+ order.get("timestamp");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(REFUND_PAYMENT_ENDPOINT);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject jsonResult = new JSONObject(resultJsonStr.toString());
        Map<String, Object> finalResult = new HashMap<>();
        for (Iterator it = jsonResult.keys(); it.hasNext(); ) {

            String key = (String) it.next();
            finalResult.put(key, jsonResult.get(key));
        }

        return finalResult;
    }
    //--------------------------------------------------------------------------------------------------------------------
    //refund status
//    public Map<String, Object> getStatusRefund(RefundStatusRequestDTO refundStatusDTO) throws IOException, URISyntaxException, JSONException {
//
////        String mRefundId = "190308_2553_123456";
//        String timestamp = Long.toString(System.currentTimeMillis()); // miliseconds
//        String data = appId +"|"+ refundStatusDTO.getRefundId()  +"|"+ timestamp; // app_id|m_refund_id|timestamp
//        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("app_id", appId));
//        params.add(new BasicNameValuePair("m_refund_id", refundStatusDTO.getRefundId()));
//        params.add(new BasicNameValuePair("timestamp", timestamp));
//        params.add(new BasicNameValuePair("mac", mac));
//
//        URIBuilder uri = new URIBuilder(REFUND_STATUS_PAYMENT_ENDPOINT);
//        uri.addParameters(params);
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost post = new HttpPost(uri.build());
//        post.setEntity(new UrlEncodedFormEntity(params));
//
//        CloseableHttpResponse res = client.execute(post);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
//        StringBuilder resultJsonStr = new StringBuilder();
//        String line;
//
//        while ((line = rd.readLine()) != null) {
//
//            resultJsonStr.append(line);
//        }
//
//        JSONObject jsonResult = new JSONObject(resultJsonStr.toString());
//        Map<String, Object> finalResult = new HashMap<>();
//        finalResult.put("return_code", jsonResult.get("return_code"));
//        finalResult.put("return_message", jsonResult.get("return_message"));
//        finalResult.put("sub_return_code", jsonResult.get("sub_return_code"));
//        finalResult.put("sub_return_message", jsonResult.get("sub_return_message"));
//        return finalResult;
//    }
}
