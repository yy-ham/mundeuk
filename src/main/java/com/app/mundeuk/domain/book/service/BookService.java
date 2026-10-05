package com.app.mundeuk.domain.book.service;

import com.app.mundeuk.domain.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {

    @Value("${book.api.key}")
    private String apiKey;

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 책 검색
    public String searchBooks(String keyword, int page, int size) {
        String text = null;
        try {
            text = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://dapi.kakao.com/v3/search/book?query=" + text
                        + "&page=" + page
                        + "&size=" + size;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", "KakaoAK " + apiKey);
        String responseBody = executeGetRequest(apiURL,requestHeaders);

        return responseBody;
    }

    // API 연결 요청
    private static String executeGetRequest(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = createConnection(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readResponseBody(con.getInputStream());
            } else { // 오류 발생
                return readResponseBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    // API 통신을 위한 연결 객체 생성
    private static HttpURLConnection createConnection(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    // API 응답 데이터를 반환
    private static String readResponseBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
