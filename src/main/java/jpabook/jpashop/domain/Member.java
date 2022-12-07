package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //@OneToMany : 한 명의 회원이 여러개의 주문을 가질 수 있음
    //@mappedBy : 연관관계의 주인이 아닌 쪽 [연관관계의 주인인 Order 클래스의 member(필드명) 기재]
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
