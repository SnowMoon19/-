package com.myweb.fxj.service.interfac.main.module.memory;

import com.myweb.fxj.common.vo.PhotographVo;
import com.myweb.fxj.pojo.memory.Photo;
import com.myweb.fxj.pojo.memory.Photograph;

import java.util.List;

public interface PhotoService {
    /**
     * 通过相册id获取照片列表
     */
    List<Photo> getPhotos(int photographId);

    /**
     * 通过id获取相册列表
     */
    List<Photograph> getPhotographs(int id);

    /**
     * 条件查询相册列表
     */
    List<Photograph> getPhotographByQuery(PhotographVo photographVo);

    /**
     * 更新照片
     */
    int updatePhoto(Photo photo);

    /**
     * 创建新的相册
     */
    int createPhotograph(String name, int id);

    /**
     * 删除相册，为了应对文件目录创建失败
     */
    int deletePhotograph(int photographId);

    int isYearExist(String name, int id);
}
