package com.yourname.hexcrypt.service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EncryptionService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LambdaClient lambdaClient = LambdaClient.builder()
                                                         .region(Region.AP_NORTHEAST_1)
                                                         .build();

    public String encrypt(String input) {
        Map<String, String> payload = Map.of("action", "encrypt", "data", input);

        try {
            // MapからJSON文字列に変換
            String jsonPayload = objectMapper.writeValueAsString(payload);
            SdkBytes payloadBytes = SdkBytes.fromUtf8String(jsonPayload);

            InvokeRequest request = InvokeRequest.builder()
                                                 .functionName("HexCryptFunction")
                                                 .payload(payloadBytes)
                                                 .build();

            InvokeResponse response = lambdaClient.invoke(request);
            return new String(response.payload().asByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("JSONのシリアル化またはLambdaの呼び出し中にエラーが発生しました。", e);
        }
    }
}