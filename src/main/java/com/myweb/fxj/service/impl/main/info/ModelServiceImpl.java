package com.myweb.fxj.service.impl.main.info;

import com.myweb.fxj.mapper.info.ModelMapper;
import com.myweb.fxj.pojo.info.Model;
import com.myweb.fxj.service.interfac.main.info.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Model getModel(int id) {
        return modelMapper.getModel(id);
    }
}
