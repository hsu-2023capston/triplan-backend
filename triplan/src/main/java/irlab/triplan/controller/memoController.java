package irlab.triplan.controller;

import irlab.triplan.DTO.memoDTO;
import irlab.triplan.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
public class memoController {
    private final memoService memoservice;

    @GetMapping
    public List<memoDTO> getClass(@RequestParam(name = "trip_id") Integer trip_id){
        return memoservice.getClass(trip_id);
    }

    @PostMapping(value = "/memo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createMemo(Integer trip_id, Integer category_id, Integer user_id, String content, MultipartFile image_path){
        return memoservice.createMemo(trip_id, category_id, user_id, content, image_path);
    }

    @PostMapping(value = "/url", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> classificationURL(Integer trip_id, Integer user_id, String content){
        return memoservice.classificationURL(trip_id, user_id, content);
    }

    @PutMapping(value = "/memo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> editMemo(Integer classification_id, Integer category_id, String content, MultipartFile image_path, String pre_path){
        return memoservice.editMemo(classification_id, category_id, content, image_path, pre_path);
    }

    @PostMapping(value = "/like", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> createLike(Integer classification_id, Integer user_id){
        return memoservice.createLike(classification_id, user_id);
    }

    @DeleteMapping(value = "/like", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> deleteLike(Integer classification_id, Integer user_id){
        return memoservice.deleteLike(classification_id, user_id);
    }
}
