package com.ningdong.mall_test.member.service.impl;

import com.ningdong.mall_test.member.entity.GiteeUserInfoEntity;
import com.ningdong.mall_test.member.entity.MemberLevelEntity;
import com.ningdong.mall_test.member.exception.PhoneExistException;
import com.ningdong.mall_test.member.exception.UsernameExistException;
import com.ningdong.mall_test.member.service.MemberLevelService;
import com.ningdong.mall_test.member.vo.MemberLoginVo;
import com.ningdong.mall_test.member.vo.MemberRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.common.utils.Query;

import com.ningdong.mall_test.member.dao.MemberDao;
import com.ningdong.mall_test.member.entity.MemberEntity;
import com.ningdong.mall_test.member.service.MemberService;
import org.springframework.util.ObjectUtils;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberEntity entity = new MemberEntity();

        MemberLevelEntity default_status = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
        entity.setLevelId(default_status.getId());

        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());

        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(vo.getPassword());
        entity.setPassword(encodePassword);

        this.baseMapper.insert(entity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException{
        Long userConut = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (userConut > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException{
        Long userConut = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (userConut > 0) {
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        MemberEntity entity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct).or().eq("mobile", loginacct));
        if (ObjectUtils.isEmpty(entity)){
            return null;
        }else {
            String Md5Password = entity.getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(password, Md5Password);
            if (matches){
                return entity;
            }else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity login(GiteeUserInfoEntity vo) {
        long uid = vo.getId();

        MemberEntity entity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));
        if (!ObjectUtils.isEmpty(entity)){
            MemberEntity updateData = new MemberEntity();
            updateData.setId(entity.getId());
            updateData.setAccessToken(vo.getAccessToken());
            updateData.setExpiresIn(vo.getExpiresIn());

            this.baseMapper.updateById(updateData);

            entity.setAccessToken(vo.getAccessToken());
            entity.setExpiresIn(vo.getExpiresIn());

            return entity;
        }else {
            MemberEntity registData = new MemberEntity();
            registData.setUsername(vo.getLogin());
            registData.setEmail(vo.getEmail());
            registData.setNickname(vo.getName());

            MemberLevelEntity default_status = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
            registData.setLevelId(default_status.getId());

            registData.setSocialUid(String.valueOf(vo.getId()));
            registData.setAccessToken(vo.getAccessToken());
            registData.setExpiresIn(vo.getExpiresIn());

            this.baseMapper.insert(registData);
            return registData;
        }
    }
}