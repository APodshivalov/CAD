package mappers;

import domain.Cut;

/**
 * Created by podsh on 08.05.2017.
 */
public class CutMapper {
    public static model.Cut map(Cut cut) {
        model.Cut clientCut = new model.Cut();
        clientCut.setId(cut.getId());
        clientCut.setFullName(cut.getFullName());
        clientCut.setShortName(cut.getShortName());
        clientCut.setType(cut.getType());
        clientCut.setT(cut.getT());
        clientCut.setS(cut.getS());
        clientCut.setB(cut.getB());
        clientCut.setH(cut.getH());
        clientCut.setR(cut.getR());
        return clientCut;
    }
}
