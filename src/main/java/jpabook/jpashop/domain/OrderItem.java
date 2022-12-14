package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 protected 와 같은 효과
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //한 아이템이 여러개의 주문에 속할 수 있기 때문
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //한 주문에 여러개의 아이템이 속할 수 있기 때문
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //new 인스턴스 생성을 막아줌 = 서비스단에서 생성해 필드를 set하면 사용성이 떨어짐 (필드 추가, 유지보수 등)
    //@NoArgsConstructor(access = AccessLevel.PROTECTED)와 같은 효과
    /*protected OrderItem() {

    }*/

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //재고 수량 감소
        item.removeStock(count);

        return orderItem;
    }
    //==비즈니스 로직==//
    public void cancel() {
        //재고 수량 원복
        getItem().addStock(count);
    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
