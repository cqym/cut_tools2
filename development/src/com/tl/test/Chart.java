package com.tl.test;

import java.text.DecimalFormat;
import java.util.Arrays;

import za.co.connext.web.components.DefaultOFCGraphDataModel;
import za.co.connext.web.components.OFCFilledBarSeriesType;
import za.co.connext.web.components.OFCGraphController;
import za.co.connext.web.components.OFCLineDotSeriesType;
import za.co.connext.web.components.OFCLineHollowSeriesType;
import za.co.connext.web.components.OFCLineSeriesType;

public class Chart {
	 private static Double data01[] = {
	        new Double(1.3999999999999999D), new Double(6.2999999999999998D), new Double(7.2000000000000002D), new Double(4.0999999999999996D), new Double(5.4000000000000004D), new Double(3.7999999999999998D), new Double(1.5D), new Double(4.4000000000000004D), new Double(8.9000000000000004D), new Double(8.5999999999999996D), 
	        new Double(8.6999999999999993D), new Double(2.3999999999999999D), new Double(3.5D)
	    };
	    private static Double data02[] = {
	        new Double(3.5D), new Double(1.3999999999999999D), new Double(2.2999999999999998D), new Double(8.4000000000000004D), new Double(9.5999999999999996D), new Double(4.4000000000000004D), new Double(3.7999999999999998D), new Double(1.5D), new Double(2.7999999999999998D), new Double(8.3000000000000007D), 
	        new Double(5.4000000000000004D), new Double(8.6999999999999993D), new Double(1.3999999999999999D)
	    };
	    private static Double data03[] = {
	        new Double(13.5D), new Double(11.4D), new Double(12.300000000000001D), new Double(18.399999999999999D), new Double(19.600000000000001D), new Double(14.4D), new Double(13.800000000000001D), new Double(11.5D), new Double(12.800000000000001D), new Double(18.300000000000001D), 
	        new Double(15.4D), new Double(18.699999999999999D), new Double(11.4D)
	    };
	    private static Double data04[] = {
	        new Double(13.5D), new Double(11.4D), new Double(12.300000000000001D), new Double(18.399999999999999D), new Double(19.600000000000001D), new Double(14.4D), new Double(13.800000000000001D), new Double(11.5D), new Double(12.800000000000001D), new Double(18.300000000000001D), 
	        new Double(15.4D), new Double(18.699999999999999D), new Double(11.4D)
	    };
	    private static String labels[] = {
	        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", 
	        "Nov", "Dec", "Jan"
	    };
public static void main(String[] args) {
	OFCGraphController controller = new OFCGraphController();
    controller.getTitle().setText("Example 01");
    controller.getTitle().setSize(12);
    controller.getLabels().setLabels(Arrays.asList(labels));
    controller.getYLegend().setText("No. of tasks");
    controller.getYLegend().setColor("#8b0000");
    controller.getYLegend().setSize(12);
    controller.getXLegend().setText("Months");
    controller.getXLegend().setColor("#8b0000");
    controller.getXLegend().setSize(12);
    controller.getColor().getBgColor().setColor("#FFFFFF");
    controller.getColor().getXAxisColor().setColor("#e3e3e3");
    controller.getColor().getYAxisColor().setColor("#e3e3e3");
    controller.getColor().getXGridColor().setColor("#e3e3e3");
    controller.getColor().getYGridColor().setColor("#e3e3e3");
    DefaultOFCGraphDataModel model = new DefaultOFCGraphDataModel();
    model.setData(Arrays.asList(data01));
    model.setFormat(new DecimalFormat("###0.000"));
    model.setSeriesType(new OFCLineHollowSeriesType(3, "#8b0000", "Test", 10, 6));
    controller.add(model);
    model = new DefaultOFCGraphDataModel();
    model.setData(Arrays.asList(data02));
    model.setFormat(new DecimalFormat("###0.000"));
    model.setSeriesType(new OFCLineDotSeriesType(3, "#8b0000", "Test 2", 10, 6));
    controller.add(model);
    model = new DefaultOFCGraphDataModel();
    model.setData(Arrays.asList(data03));
    model.setFormat(new DecimalFormat("###0.000"));
    model.setSeriesType(new OFCLineSeriesType(2, "#8b0000", "Example", 4));
    controller.add(model);
    model = new DefaultOFCGraphDataModel();
    model.setData(Arrays.asList(data04));
    model.setFormat(new DecimalFormat("###0.000"));
    model.setSeriesType(new OFCFilledBarSeriesType(50, "#8b0000", "#8b0000", "Filled Bar"));
    controller.add(model);
    String value = controller.render();
    System.out.println(value);
}
}
