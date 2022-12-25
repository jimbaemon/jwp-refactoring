package kitchenpos.order.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import kitchenpos.common.Price;

@Embeddable
public class OrderMenu {

    @Column(nullable = false)
    private Long menuId;
    @Column(nullable = false)
    private String name;
    @Embedded
    @Column(nullable = false)
    private Price price = new Price(BigDecimal.ZERO);

    protected OrderMenu() {
    }

    public OrderMenu(Long menuId, String name, Price price) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
    }

    public Long getMenuId() {
        return menuId;
    }
}
