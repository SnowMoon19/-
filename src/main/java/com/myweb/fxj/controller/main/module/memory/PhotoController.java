package com.myweb.fxj.controller.main.module.memory;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.common.vo.PhotographVo;
import com.myweb.fxj.pojo.memory.Photo;
import com.myweb.fxj.pojo.memory.Photograph;
import com.myweb.fxj.service.interfac.main.module.memory.PhotoService;
import com.myweb.fxj.utils.createFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/memory")
public class PhotoController {

    @Autowired
    private PhotoService photoServiceImpl;
    private final String ROOT = "C:/data/photos/";

    @PostMapping("/photo")
    public Result upload(@RequestParam("file") MultipartFile[] file,
                         @RequestParam("photographId") Integer photographId,
                         @RequestParam("date") String date1,
                         @RequestParam("creator") int creator,
                         @RequestParam("comment") String comment) throws IOException, ParseException {
        if (file.length == 0) {
            Result.fail("上传失败");
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
        if(photographId == -1) {
            String year = Integer.toString(date.getYear() + 1900);
            if(photoServiceImpl.isYearExist(year, creator) == -1) {
                // 如果年份确实不存在
                // 如果photographId为-1说明该年的相册未创建，创建该年的相册
                // 先添加至数据库
                photographId = photoServiceImpl.createPhotograph(year, creator);
                if(photographId == -1) return Result.fail("导入数据库失败");
                else {
                    // 再在文件资源服务器创建目录
                    String dir = ROOT + photographId;
                    if(!createFileUtil.createDir(dir)) {
                        // 如果目录创建失败，数据库中将该相册记录删除
                        photoServiceImpl.deletePhotograph(photographId);
                        return Result.fail("目录创建失败");
                    }
                }
            }
        }
        // 上传照片到指定目录，同时插入数据库
        for (MultipartFile multipartFile : file) {
            String fileName = multipartFile.getOriginalFilename();
            String url = photographId + "/" +
                    UUID.randomUUID().toString().replace("-", "").toLowerCase() + fileName.substring(fileName.length() - 4);
            File dest = new File(ROOT + url);
            Photo photo = new Photo(photographId, null, url, date, creator, null, comment, 0);
            try {
                multipartFile.transferTo(dest);
                photoServiceImpl.updatePhoto(photo);
            } catch (IOException e) {
                return Result.fail("上传失败");
            }
        }
        return Result.ok("上传成功");
    }


    /**
     * 根据photographId获取照片
     */
    @GetMapping("/photo/{photographId}")
    public Result getPhotos(@PathVariable("photographId") int photographId) {
        List<Photo> photoList = photoServiceImpl.getPhotos(photographId);
        photoList.sort(new Comparator<Photo>() {
            @Override
            public int compare(Photo o1, Photo o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });// 对照片按时间序进行排序
        return Result.ok(photoList);
    }

    /**
     * 条件查询，返回的是根据年份与id获取到的相册id
     */
    @PostMapping("/photo/query/{id}/{anotherId}")
    public Result getPhotosVByQuery(@PathVariable("id") int id,
                                     @PathVariable("anotherId")int anotherId,
                                     @RequestBody PhotographVo photographVo) {
        photographVo.setId(id);
        List<Photograph> photographList = photoServiceImpl.getPhotographByQuery(photographVo);
        photographVo.setId(anotherId);
        photographList.addAll(photoServiceImpl.getPhotographByQuery(photographVo));
        photographList.sort(new Comparator<Photograph>() {
            @Override
            public int compare(Photograph o1, Photograph o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });// 对照片按时间序进行排序
        return Result.ok(photographList);
    }

    /**
     * 获取相册id
     */
    @GetMapping("/photograph/{id}/{anotherId}")
    public Result getPhotograph(@PathVariable("id") int id, @PathVariable("anotherId") int anotherId) {
        List<Photograph> photographList = photoServiceImpl.getPhotographs(id);
        photographList.addAll(photoServiceImpl.getPhotographs(anotherId));
        photographList.sort(new Comparator<Photograph>() {
            @Override
            public int compare(Photograph o1, Photograph o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });// 对照片按时间序进行排序
        return Result.ok(photographList);
    }

}
