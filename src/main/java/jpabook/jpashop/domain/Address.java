package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //어딘가에 내장이 될 수 있다.
@Getter //@Setter 제거
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //생성자에서 값을 모두 초기화해서 변경 불가능한 클래스로 만듬.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
