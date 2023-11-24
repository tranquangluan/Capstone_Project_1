package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.payment.*;
import com.example.capstoneproject1.services.ZaloPayService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    private ZaloPayService zaloPayService;

    @PostMapping("/api/v1/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequestDTO request) throws JSONException, IOException {

        Map<String, Object> resultOrder = zaloPayService.createOrder(request);
        return new ResponseEntity<>(resultOrder, HttpStatus.OK);
    }
    @PostMapping("/api/v1/callback")
    public ResponseEntity<String> callback(@RequestBody CallBackPaymentDTO paymentDTO)
            throws JSONException, NoSuchAlgorithmException, InvalidKeyException {

        JSONObject result = new JSONObject();
        return new ResponseEntity<>(zaloPayService.doCallBack(result, paymentDTO.getJsonString()).toString(), HttpStatus.OK);

    }
    @PostMapping("/api/v1/order-status")
    public Map<String, Object> getStatusOrder(@RequestBody StatusRequestDTO statusRequestDTO) throws JSONException, URISyntaxException, IOException {
        return zaloPayService.statusOrder(statusRequestDTO);
    }
    @PostMapping("/api/v1/refund-payment")
    public ResponseEntity<Map<String, Object>> sendRefundRequest(@RequestBody RefundRequestDTO refundRequestDTO) throws JSONException, IOException {

        Map<String, Object> result = zaloPayService.sendRefund(refundRequestDTO);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }
    @PostMapping("/api/v1/get-refund-status")
    public ResponseEntity<Map<String, Object>> getStatusRefund(@RequestBody RefundStatusRequestDTO refundStatusDTO) throws JSONException, IOException, URISyntaxException {

        Map<String, Object> result =  zaloPayService.getStatusRefund(refundStatusDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
