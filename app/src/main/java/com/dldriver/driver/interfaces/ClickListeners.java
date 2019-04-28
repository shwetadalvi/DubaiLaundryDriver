package com.dldriver.driver.interfaces;

import com.dldriver.driver.models.Remarks;

import java.util.List;


public interface ClickListeners {
    interface CategoryItemEvents<T> {
        void onClickedEdit(T items);

        void onDeletedItem(T items);
    }

    interface getRemarkList {
        void onClickedRemarkItem(List<Remarks> remarksList);
    }
   /* interface ItemTimeClick<T> {
        void onClickedTimeItem(T item);
    }*/
}
