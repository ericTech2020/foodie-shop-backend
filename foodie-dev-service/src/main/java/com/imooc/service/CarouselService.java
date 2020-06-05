package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

public interface CarouselService {


    /**
     * 轮播图展示
     */

    public List<Carousel> queryAll(Integer isShow);

}
