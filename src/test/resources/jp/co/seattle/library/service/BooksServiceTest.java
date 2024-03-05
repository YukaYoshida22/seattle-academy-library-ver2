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

package jp.co.seattle.library.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

public class BooksServiceTest {

    @Mock 
    private JdbcTemplate jdbcTemplate;

    @InjectMocks 
    private BooksService booksService;

    @Before 
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test // 書籍リストを取得するテスト
    public void testGetBookList() {
        List<BookInfo> mockedList = new ArrayList<>();
        // jdbcTemplate.queryが呼ばれた際に、mockedListを返すように設定
        when(jdbcTemplate.query(anyString(), any(BookInfoRowMapper.class))).thenReturn(mockedList);

        List<BookInfo> result = booksService.getBookList();
        assertNotNull(result); // nullでないことを確認
        verify(jdbcTemplate).query(anyString(), any(BookInfoRowMapper.class)); 
    }

    @Test // 書籍の詳細情報を取得するテスト
    public void testGetBookInfo() {
        BookDetailsInfo mockedBookDetailsInfo = new BookDetailsInfo();
 

        when(jdbcTemplate.queryForObject(anyString(), any(), anyInt())).thenReturn(mockedBookDetailsInfo);

        BookDetailsInfo result = booksService.getBookInfo(1);
        assertNotNull(result); // 結果がnullでないことを確認
        verify(jdbcTemplate).queryForObject(anyString(), any(), anyInt()); 
    }
    @Test // 書籍を登録するテスト
    public void testRegistBook() {
        when(jdbcTemplate.queryForObject(anyString(), eq(int.class), any())).thenReturn(1);

        BookDetailsInfo bookInfo = new BookDetailsInfo();
        int bookId = booksService.registBook(bookInfo);

        assertTrue(bookId > 0); // bookIdが0より大きいことを確認
        verify(jdbcTemplate).queryForObject(anyString(), eq(int.class), any()); 
    }

    @Test // 書籍を削除するテスト
    public void testDeleteBook() {
        booksService.deleteBook(1);

        verify(jdbcTemplate).update(anyString(), eq(1)); 
    }

    @Test // 書籍情報を更新するテスト
    public void testUpdateBook() {
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setBookId(1);

        booksService.updateBook(bookInfo);

        verify(jdbcTemplate).update(anyString(), any()); 
    }
} 

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