package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.OrderTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderTableDao orderTableDao;
    @InjectMocks
    private TableService tableService;

    @Test
    void 주문테이블이_단체지정된_테이블이면_빈테이블로_변경_불가능() {
        //given
        OrderTable orderTable = new OrderTable();
        orderTable.setTableGroupId(1L);
        when(orderTableDao.findById(any()))
            .thenReturn(Optional.of(orderTable));

        //when
        assertThatThrownBy(() -> tableService.changeEmpty(1L, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블의_상태가_조리거나_식사면_빈테이블로_변경_불가능() {
        //given
        when(orderTableDao.findById(any())).thenReturn(Optional.of(new OrderTable()));
        when(orderDao.existsByOrderTableIdAndOrderStatusIn(any(), any()))
            .thenReturn(true);

        //when & then
        assertThatThrownBy(() -> tableService.changeEmpty(1L, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블의_방문한_손님_수_0미만으로_변경_불가능() {
        //given
        OrderTable orderTable = new OrderTable();
        orderTable.setNumberOfGuests(-1);

        //when & then
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(1L, orderTable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 방문한_손님수를_변경할_주문_테이블이_없으면_변경_불가능() {
        //given
        when(orderTableDao.findById(any())).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(1L, new OrderTable()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 방문한_손님수를_변경할_주문_테이블이_빈_테이블이면_변경_불가능() {
        //given
        OrderTable orderTable = new OrderTable();
        orderTable.setEmpty(true);
        when(orderTableDao.findById(any())).thenReturn(Optional.of(orderTable));

        //when
        assertThatThrownBy(() -> tableService.changeNumberOfGuests(1L, new OrderTable()))
            .isInstanceOf(IllegalArgumentException.class);
    }

}