package jp.co.seattle.library.commonutil;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import io.minio.MinioClient;
import jp.co.seattle.library.config.MinioConfig;
import jp.co.seattle.library.service.ThumbnailService;

@ContextConfiguration(classes = MinioConfig.class)
public class ThumbnailServiceTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private ThumbnailService thumbnailService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadThumbnail() throws Exception {
    	System.out.print("dete");
        // Mockの準備
        MultipartFile file = prepareTestFile(); // テスト用ファイル

        String thumbnailName = "test_thumbnail.jpg";

        // アップロードメソッドの実行
        String uploadedFileName = thumbnailService.uploadThumbnail(thumbnailName, file);

        assertNotNull(uploadedFileName);
        // ファイル名を確認するアサーションを追加する
    }

    @Test
    public void testGetURL() throws Exception {
        // Mockの準備
        String fileName = "test_thumbnail.jpg";


        // URL取得メソッドの実行
        String url = thumbnailService.getURL(fileName);

        assertNotNull(url);
        // 取得したURLを確認するアサーションを追加する
    }

    //テスト用ファイルを生成するメソッド
    private MultipartFile prepareTestFile() {
        return null;
    }
    
}