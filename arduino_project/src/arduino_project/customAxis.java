/*
 * 
 * 
 * 
 */
package arduino_project;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.TickType;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author Ryan Rusli 2201832446
 */
public class customAxis extends NumberAxis{
    
    @Override
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge){
        
        List allTicks = super.refreshTicks(g2, state, dataArea, edge);
        List myTicks = new ArrayList();
        
        int dayCount = 1;
        String dayText = "";
        for (int i = 0; i < allTicks.size(); i++){
            
            NumberTick numtick = (NumberTick) allTicks.get(i);
            
            if (TickType.MAJOR.equals(numtick.getTickType()) && (i % 23 == 0) && (i >=23)){
                dayText = Integer.toString(dayCount);
                myTicks.add(new NumberTick(TickType.MINOR, numtick.getValue(), dayText,
                            numtick.getTextAnchor(), numtick.getRotationAnchor(),
                            numtick .getAngle()));
                dayCount++;
                continue;
            }
            else if (TickType.MAJOR.equals(numtick.getTickType()) && ((i < 23 ) || (i % 23 != 0))){
                myTicks.add(new NumberTick(TickType.MINOR, numtick.getValue(), "",
                            numtick.getTextAnchor(), numtick.getRotationAnchor(),
                            numtick .getAngle()));
                continue;
            }
            myTicks.add(allTicks.get(i));
        }
        return myTicks;
    }
    
}
