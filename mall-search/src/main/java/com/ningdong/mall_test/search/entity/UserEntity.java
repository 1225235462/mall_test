package com.ningdong.mall_test.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "users")
public class UserEntity implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    @Id
    private Long id;

    @Field(index=true,store=true,analyzer="ik_max_word",searchAnalyzer="ik_max_word",type= FieldType.Text)
    private String name;
}
