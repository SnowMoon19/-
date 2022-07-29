package com.myweb.fxj.service.impl.main.module.memory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myweb.fxj.common.vo.PhotographVo;
import com.myweb.fxj.mapper.module.memory.PhotoMapper;
import com.myweb.fxj.mapper.module.memory.PhotographMapper;
import com.myweb.fxj.pojo.memory.Photo;
import com.myweb.fxj.pojo.memory.Photograph;
import com.myweb.fxj.service.interfac.main.module.memory.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;
    @Autowired
    private PhotographMapper photographMapper;

    @Override
    public List<Photo> getPhotos(int photographId) {
        QueryWrapper<Photo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("photograph_id", photographId);
        queryWrapper.eq("is_deleted", 0);
        return photoMapper.selectList(queryWrapper);
    }

    @Override
    public List<Photograph> getPhotographs(int id) {
        QueryWrapper<Photograph> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_deleted", 0);
        return photographMapper.selectList(queryWrapper);
    }

    @Override
    public List<Photograph> getPhotographByQuery(PhotographVo photographVo) {
        return photographMapper.getPhotographByQuery(photographVo);
    }

    public int updatePhoto(Photo photo) {
        // 无num则是添加
        if(photo.getPhotoId() == null) return photoMapper.insert(photo);
        // 有num是更新
        else {
            UpdateWrapper<Photo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("photoId", photo.getPhotoId());
            return photoMapper.update(photo, updateWrapper);
        }
    }

    public int isYearExist(String name, int id) {
        QueryWrapper<Photograph> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("name", name);
        Photograph ph = photographMapper.selectOne(queryWrapper);
        if(ph == null) return -1;
        else return ph.getPhotographId(); // 年份存在则返回对应的photographId
    }

    @Override
    public int createPhotograph(String name, int id) {
        Date date = new Date();
        if(photographMapper.insert(new Photograph(id, null, name, date, 0)) == 1) {
            QueryWrapper<Photograph> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.eq("name", name);
            Photograph ph = photographMapper.selectOne(queryWrapper);
            return ph.getPhotographId(); // 插入成功则返回对应的photographId
        }
        else return -1;
    }

    @Override
    public int deletePhotograph(int photographId) {
        QueryWrapper<Photograph> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("photograph_id", photographId);
        return photographMapper.delete(queryWrapper);
    }
}
