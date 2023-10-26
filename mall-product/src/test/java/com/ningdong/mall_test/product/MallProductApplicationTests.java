package com.ningdong.mall_test.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.UUID;


@SpringBootTest
class MallProductApplicationTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("hello","world_"+ UUID.randomUUID().toString());

        System.out.println(operations.get("hello"));
    }

//    @Autowired
//    OSS ossClient;
//
//    @Test
//    public void testUpload() throws Exception {
////        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
////        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
////        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//////        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
////
////        // RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
////        // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
////        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
////
////        // 创建OSSClient实例。
////        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
//
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "mall-ningdong";
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "key.txt";
//        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        String filePath = "C:\\Users\\ningd\\Downloads\\key.txt";
//
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
//            // 创建PutObject请求。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }

}
