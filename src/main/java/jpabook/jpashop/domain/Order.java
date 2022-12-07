package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") //order은 예약어라 다른 테이블명과 매치해주기 위함
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne //여러개의 주문은 회원 한 명에 매핑될 수 있음
    @JoinColumn(name = "member_id") //매핑할 컬럼
    private Member member;

    //@OneToMany : 한 개의 주문은 여러개의 주문아이템을 가질 수 있음
    //@mappedBy : 연관관계의 주인이 아닌 쪽 [연관관계의 주인인 OrderItem 클래스의 order(필드명) 기재]
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //하나의 주문은 하나의 배송
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //LocalDateTime : java 8부터 Date annotation없이도 Date형식지원
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

}
