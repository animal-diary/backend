package animal.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
@Slf4j
@Service
@RequiredArgsConstructor
public class CloudFrontUrlService {

    @Value("${cloud.aws.cloudfront.key-pair-id}")
    private String keyPairId;

    @Value("${cloud.aws.cloudfront.private-key}")
    private String privateKeyPem;

    @Value("${cloud.aws.cloudfront.domain}")
    private  String cloudFrontDomain;

    public PrivateKey loadPrivateKey(String pem) throws Exception {
        // -----BEGIN PRIVATE KEY----- / -----END PRIVATE KEY----- 제거
        String privateKeyPEM = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public String generateSignedUrl(String objectKey) {
        log.info("Generating signed URL for object key: {}", objectKey);
        try {
            // PEM → PrivateKey
            PrivateKey privateKey = loadPrivateKey(privateKeyPem);

            // CloudFront 리소스 경로
            String resourceUrl = "https://" + cloudFrontDomain + "/" +
                    URLEncoder.encode(objectKey, StandardCharsets.UTF_8);

            // 만료 시간 (Epoch seconds) - 지금부터 5분 후
            long expires = (System.currentTimeMillis() / 1000L) + (5 * 60);

            // "Canned Policy" 문자열
            String policy = String.format(
                    "{\"Statement\":[{\"Resource\":\"%s\",\"Condition\":{\"DateLessThan\":{\"AWS:EpochTime\":%d}}}]}",
                    resourceUrl, expires
            );

            // 정책에 서명 (SHA1withRSA)
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(policy.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();

            // 서명 Base64 → CloudFront URL-safe 인코딩
            String signatureEncoded = makeUrlSafe(Base64.getEncoder().encodeToString(signedBytes));

            // 최종 URL 조합
            String signedUrl = String.format("%s?Expires=%d&Signature=%s&Key-Pair-Id=%s",
                    resourceUrl, expires, signatureEncoded, keyPairId);
            log.info("Successfully generated signed URL for object key: {}", objectKey);
            return signedUrl;

        } catch (Exception e) {
            log.error("Failed to generate signed URL for object key: {}", objectKey, e);
            throw new RuntimeException("CloudFront Signed URL 생성 실패", e);
        }
    }

    private String makeUrlSafe(String base64) {
        return base64.replace('+', '-')
                .replace('=', '_')
                .replace('/', '~');
    }
}