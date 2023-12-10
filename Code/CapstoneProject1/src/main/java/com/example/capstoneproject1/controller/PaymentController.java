package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.payment.OrderRequestDTO;
import com.example.capstoneproject1.dto.request.payment.RefundRequestDTO;
import com.example.capstoneproject1.services.ZaloPayService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    private ZaloPayService zaloPayService;

    @PostMapping("/api/v1/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws JSONException, IOException {

        Map<String, Object> resultOrder = zaloPayService.createOrder(orderRequestDTO);
        return new ResponseEntity<>(resultOrder, HttpStatus.OK);
    }

    @PostMapping(value = "/api/v1/callback", produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<String> callback(@RequestBody String string)
            throws JSONException, NoSuchAlgorithmException, InvalidKeyException {
        JSONObject result = new JSONObject();
        if (string != null) {
            return new ResponseEntity<>(zaloPayService.doCallBack(result, string).toString(), HttpStatus.OK);
        } else {
            result.put("return_code", 0);
            result.put("return_message", "Json string is null");
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping(value = "/api/v1/callback")
//    public ResponseEntity<String> callback(@RequestParam("string") String string)
//            throws JSONException, NoSuchAlgorithmException, InvalidKeyException {
//        JSONObject result = new JSONObject();
//        if (string != null) {
//            return new ResponseEntity<>(zaloPayService.doCallBack(result, string).toString(), HttpStatus.OK);
//        } else {
//            result.put("return_code", 0);
//            result.put("return_message", "Json string is null");
//            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/api/v1/refund-payment")
    public ResponseEntity<Map<String, Object>> sendRefundRequest(@RequestBody RefundRequestDTO refundRequestDTO)
            throws JSONException, IOException {
        Map<String, Object> result = zaloPayService.sendRefund(refundRequestDTO);
        return new ResponseEntity<>(new HashMap<>(result), HttpStatus.OK);
    }
//    @PostMapping("/api/v1/get-refund-status")
//    public ResponseEntity<Map<String, Object>> getStatusRefund(@RequestBody RefundStatusRequestDTO refundStatusDTO)
//            throws JSONException, IOException, URISyntaxException {
//        Map<String, Object> result =  zaloPayService.getStatusRefund(refundStatusDTO);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
