package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //하나의 주문은 하나의 배송
    //@mappedBy : 연관관계의 주인이 아닌 쪽 [연관관계의 주인인 Order 클래스의 delivery(필드명) 기재]
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    //무조건 EnumType.STRING 사용, EnumType.ORDINAL은 숫자로 쓰기 때문에 중간에 옵션 추가되면 순서 밀림
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY(배송준비), COMP(배송)

}
