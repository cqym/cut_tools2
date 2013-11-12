package com.tl.common.util;

import java.util.Comparator;

import com.tl.resource.business.dto.QuotationDetailDto;

public class DateComparator implements Comparator<QuotationDetailDto> {

	@Override
	public int compare(QuotationDetailDto arg0, QuotationDetailDto arg1) {
		if(arg0.getDeliveryDate() != null && arg1.getDeliveryDate() != null) {
			int res = arg0.getDeliveryDate().compareTo(arg1.getDeliveryDate());
			if (res > 0) {
				return -1;
			} else {
				if (res == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else {
			return 0;
		}
	}

}
