package com.atguigu.test;

/**
 * @ClassName TestFile
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/7 20:12
 * @Version 1.0
 */
public class TestFile {

    //@Test
    //public void uploadFile(){
    //    //构造一个带指定Zone对象的配置类
    //    Configuration cfg = new Configuration(Region.region1());
    //    //...其他参数参考类注释
    //    UploadManager uploadManager = new UploadManager(cfg);
    //    //...生成上传凭证，然后准备上传
    //    String accessKey = "Rk-mGUK5Y7rCRTSeORp__2KXHc7gDazsHajN_2N8";
    //    String secretKey = "j1d3WykshGv3KHZ49W_cvCrlBp_bg_utF0czBnku";
    //    String bucket = "atguigu0330";
    //    //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
    //    String localFilePath = "D:/壁纸/1.jpg";
    //    //默认不指定key的情况下，以文件内容的hash值作为文件名
    //    String key = null;
    //    Auth auth = Auth.create(accessKey, secretKey);
    //    String upToken = auth.uploadToken(bucket);
    //    try {
    //        Response response = uploadManager.put(localFilePath, null, upToken);
    //        //解析上传成功的结果
    //        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    //        System.out.println(putRet.key);
    //        System.out.println(putRet.hash);
    //    } catch (QiniuException ex) {
    //        Response r = ex.response;
    //        System.err.println(r.toString());
    //        try {
    //            System.err.println(r.bodyString());
    //        } catch (QiniuException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //}
    //
    //// 删除空间中的文件
    //@Test
    //public void deleteFile(){
    //    //构造一个带指定Zone对象的配置类
    //    Configuration cfg = new Configuration(Region.region1());
    //    //...其他参数参考类注释
    //    String accessKey = "Rk-mGUK5Y7rCRTSeORp__2KXHc7gDazsHajN_2N8";
    //    String secretKey = "j1d3WykshGv3KHZ49W_cvCrlBp_bg_utF0czBnku";
    //    String bucket = "atguigu0330";
    //    String key = "FiWwUV3cAh6CXc9gkgOITH7pibaa";//文件名称
    //    Auth auth = Auth.create(accessKey, secretKey);
    //    BucketManager bucketManager = new BucketManager(auth, cfg);
    //    try {
    //        bucketManager.delete(bucket, key);
    //    } catch (QiniuException ex) {
    //        //如果遇到异常，说明删除失败
    //        System.err.println(ex.code());
    //        System.err.println(ex.response.toString());
    //    }
    //}


}