package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders") //order은 예약어라 다른 테이블명과 매치해주기 위함
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//여러개의 주문은 회원 한 명에 매핑될 수 있음
    @JoinColumn(name = "member_id") //매핑할 컬럼
    private Member member;

    //@OneToMany : 한 개의 주문은 여러개의 주문아이템을 가질 수 있음
    //@mappedBy : 연관관계의 주인이 아닌 쪽 [연관관계의 주인인 OrderItem 클래스의 order(필드명) 기재]
    //cascade :
    @OneToMany(mappedBy = "order" , cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //하나의 주문은 하나의 배송
    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //LocalDateTime : java 8부터 Date annotation없이도 Date형식지원
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 편의메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /*public static void main(String[] args) {
        Member member =  new Member();
        Order order = new Order();

        //member.getOrders().add(order); //이 라인이 없어도 됨
        order.setMember(member);
    }*/
    //==연관관계 편의 메서드==//

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        setStatus(OrderStatus.CANCEL);
        //한 주문에 orderItem 2개일 수 있기때문에 각각 cancel
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        //주문할 때 주문 개수 * 수량이 있기 때문에 getTotalPrice()을 += 함
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
