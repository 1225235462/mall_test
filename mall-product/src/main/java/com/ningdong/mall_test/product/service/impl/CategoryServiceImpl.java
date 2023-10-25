package com.ningdong.mall_test.product.service.impl;

import com.ningdong.mall_test.product.service.CategoryBrandRelationService;
import com.ningdong.mall_test.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.product.dao.CategoryDao;
import com.ningdong.mall_test.product.entity.CategoryEntity;
import com.ningdong.mall_test.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    //菜单树方法实现
    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);

        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0).map(menu -> {
            menu.setChildren(getChildens(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());


        return level1Menus;
    }

    //获取父菜单的子菜单
    private List<CategoryEntity> getChildens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> collect = all.stream().filter(categoryEntity -> categoryEntity.getParentCid() == root.getCatId()).map(categoryEntity -> {
            categoryEntity.setChildren(getChildens(categoryEntity, all));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());
        return collect;
    }

    //根据菜单的删除方法
    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 需要检测当前菜单是否被其他地方引用

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId,paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[0]);
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());

    }

    @Override
    public List<CategoryEntity> getLevel1Category() {
        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        //优化sql查询，直接取出全部数据

        List<CategoryEntity> allEntities = baseMapper.selectList(null);


        List<CategoryEntity> level1Category = getParentCid(allEntities,0L);

        return level1Category.stream().collect(Collectors.toMap(k -> k.getCatId().toString(),v -> {
            List<CategoryEntity> entities2 = getParentCid(allEntities,v.getCatId());
            if (!ObjectUtils.isEmpty(entities2)){
                return entities2.stream().map(item -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, item.getCatId().toString(), item.getName());
                    List<CategoryEntity> entities3 = getParentCid(allEntities,item.getCatId());
                    List<Catelog2Vo.Catelog3Vo> catelog3Vos = Collections.emptyList();
                    if (!ObjectUtils.isEmpty(entities3)){
                        catelog3Vos = entities3.stream().map(e3 -> new Catelog2Vo.Catelog3Vo(e3.getParentCid().toString(), e3.getCatId().toString(), e3.getName())).collect(Collectors.toList());
                    }
                    catelog2Vo.setCatalog3List(catelog3Vos);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }else{
                return Collections.emptyList();
            }
        }));
    }

    private List<CategoryEntity> getParentCid(List<CategoryEntity> allEntities, Long parent_id) {
        return allEntities.stream().filter(item -> item.getParentCid() == parent_id).collect(Collectors.toList());
//        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
    }

    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid() != 0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }
}