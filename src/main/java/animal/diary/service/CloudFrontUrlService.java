package animal.diary.service;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
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
        try {
            // PEM 문자열 → PrivateKey 객체 변환
            PrivateKey privateKey = loadPrivateKey(privateKeyPem);

            // CloudFront 리소스 경로
            String resourceUrl = "https://" + cloudFrontDomain + "/" + objectKey;

            // Signed URL 생성 (5분 유효)
            return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                    resourceUrl,
                    keyPairId,
                    privateKey,
                    new Date(System.currentTimeMillis() + 5 * 60 * 1000)
            );

        } catch (Exception e) {
            throw new RuntimeException("CloudFront Signed URL 생성 실패", e);
        }
    }
}