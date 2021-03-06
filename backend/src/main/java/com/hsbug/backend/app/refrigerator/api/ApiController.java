package com.hsbug.backend.app.refrigerator.api;

import com.hsbug.backend.app.refrigerator.manage_product.ManageProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user/barcode")        // 기본 url /user/...
@RequiredArgsConstructor
public class ApiController {

    @GetMapping("/call")
    public JSONObject callApi(HttpServletRequest request, @RequestParam String bar_code) throws ParseException {
        String apikey = "433bea5199ba464ab499";     // 맥심
        log.info("bar-code = {}",bar_code);
        //예비 값
        //String bar_code = "8801037022315";
        StringBuffer result = new StringBuffer();
        try {
            String urlStr = "http://openapi.foodsafetykorea.go.kr/api/" +
                    apikey + "/" +                 // api 토큰 키
                    "I2570/json/1/5/" +             // I2570 = 바코드 인식 api, json, 시작위치, 종료위치
                    "BRCD_NO=" + bar_code;              //바코드 번호

            // url 연결 후 한줄씩 버퍼에 담아 result에 저장

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

            String returnLine;
            while((returnLine = br.readLine()) != null){
                result.append(returnLine + "\n");
            }
            br.close();
            urlConnection.disconnect();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        {
            // String to Json parsing
            JSONParser parser = new JSONParser();
            JSONObject product_parse = (JSONObject) parser.parse(String.valueOf(result)); // json 파싱
            JSONObject obj_msg = new JSONObject();
            try {
                JSONObject product_obj = (JSONObject) product_parse.get("I2570");        // I2570 {} 추출
                JSONArray product_array = (JSONArray) product_obj.get("row");           // row [] 추출
                JSONObject product = (JSONObject) product_array.get(0);


                String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                ManageProductDto addProductDto = new ManageProductDto();

                log.info("X-AUTH-TYPE = {}",request.getHeader("X-AUTH-TYPE"));


                addProductDto.setBarcode((String) product.get("BRCD_NO"));
                addProductDto.setItemName((String) product.get("PRDT_NM"));
                addProductDto.setProduct_type((String) product.get("PRDLST_NM"));
                addProductDto.setItemAmount(1);            // 초깃값 : 1
                addProductDto.setItemExp(today);           // local date
                addProductDto.setItemReg(today);         // local date

                obj_msg.put("message","바코드 인식 성공");
                obj_msg.put("status",200);
                obj_msg.put("information",addProductDto);
            /*
                바코드 정보 ->
                PRDLST_REPORT_NO : 품목보고(신고)번호  (197806140099)
                HTRK_PRDLST_NM : 식품 종류           (가공식품)
                LAST_UPDT_DTM : 최근 업데이트 일       (2015-01-22 11:12:12)
                HRNK_PRDLST_NM : 식품 상세 종류       (초콜릿류)
                BRCD_NO : 바코드 번호                (8801062518210)
                PRDLST_NM : 식품 품목 종류            (초콜릿 가공품)
                PRDT_NM : 제품 명                    (롯데 칸쵸 57g)
                CMPNY_NM : 회사 명                   (롯데제과(주))
             */
                System.out.println(obj_msg);
                return obj_msg;
            }catch(Exception e){
                System.out.println(e);
                obj_msg.put("message","바코드 인식 실패");
                obj_msg.put("status",200);
                return obj_msg;
            }
        }
    }
}
