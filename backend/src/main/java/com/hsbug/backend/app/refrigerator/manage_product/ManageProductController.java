package com.hsbug.backend.app.refrigerator.manage_product;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/refrig")
@RequiredArgsConstructor
public class ManageProductController {

    private final ManageProductService manageProductService;

    @PostMapping("/addProduct")
    public JSONObject AddProduct(@RequestBody ManageProductDto addProductDto) throws ParseException {
        JSONObject obj = new JSONObject();
        String email = findEmail();
        int remain_date = remainDate(addProductDto.getItemExp());
        addProductDto.setItemRemainingDate(remain_date);
        addProductDto.setEmail(email);
        obj.put("obj",addProductDto);
        obj.put("status",200);
        manageProductService.save(addProductDto);

        System.out.println(addProductDto.getItemRemainingDate());
        return obj;
    }

    @GetMapping("/readProduct")
    public JSONObject ReadProduct() throws ParseException {
        String email = findEmail();
        System.out.println(email);
        List<ManageProductDto> productDtoList = manageProductService.findProduct(email);
        JSONObject obj = new JSONObject();
        ArrayList list = new ArrayList<>();

        obj.put("message","read 완료");
        obj.put("status",200);
        for (int i = 0; i< productDtoList.size(); i++){
            productDtoList.get(i).setItemRemainingDate(remainDate(productDtoList.get(i).getItemExp()));
            list.add(productDtoList.get(i));
        }
        obj.put("refrigeratorItem",list);
        return obj;
    }

    @PostMapping("/deleteProduct")
    public JSONObject DeleteProduct(@RequestParam Long id) {
        JSONObject obj = new JSONObject();
        manageProductService.deleteProduct(id);
        obj.put("message",id+" 삭제 완료");
        obj.put("status",200);
        return obj;
    }

      // dto를 read하여 수정후에 dto를 수정한다

    @PostMapping("/updateProduct")
    public JSONObject UpdateProduct(@RequestBody ManageProductDto dto) throws ParseException {
        JSONObject obj = new JSONObject();
        dto.setEmail(findEmail());
        int remain_date = remainDate(dto.getItemExp());
        dto.setItemRemainingDate(remain_date);
        manageProductService.updateProduct(dto);
        obj.put("message"," 업데이트 완료");
        obj.put("status",200);
        return obj;
    }

    @PostMapping("/readProduct/detail")
    public ManageProductDto readProductDetail(@RequestParam Long id) {
        ManageProductDto findProductDetail = manageProductService.findById(id);
        return findProductDetail;
    }

    private String findEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email;
    }

    private Integer remainDate(String date) throws ParseException {
        String todayFm =  new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date exp_date = new Date(dateFormat.parse(date).getTime());
        Date today = new Date(dateFormat.parse(todayFm).getTime());

        long cal = exp_date.getTime() - today.getTime();
        int Dday = (int) (cal / (24*60*60*1000));
        return Dday;
    }
}
