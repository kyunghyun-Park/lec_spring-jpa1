package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",   //다대다를 풀어주는 중간 테이블 매핑
                joinColumns = @JoinColumn(name = "category_id"),    //중간 테이블의 category쪽 컬럼(FK)
                inverseJoinColumns = @JoinColumn(name = "item_id")) //중간 테이블의 item쪽 컬럼(FK)
    private List<Item> items = new ArrayList<>();

    //내 부모니까 ManyToOne
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //자식
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
