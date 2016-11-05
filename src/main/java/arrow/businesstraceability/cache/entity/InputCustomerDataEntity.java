package arrow.businesstraceability.cache.entity;

import arrow.businesstraceability.persistence.entity.Acc_com_relation;

public class InputCustomerDataEntity extends Acc_com_relation {


    public static final String CLASSIFY = "classify.";

    private boolean selected;

    private String classify;

    private Character status;

    private String dateSurvey;

    private int idComRelation;

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof InputCustomerDataEntity) {
            InputCustomerDataEntity entity = (InputCustomerDataEntity) obj;
            if (this.getName_com_relation().equals(entity.getName_com_relation())
                && (this.getEnum_com_relation() == entity.getEnum_com_relation())) {
                return true;
            }
        }
        return false;
    }

    public String getClassify() {
        return InputCustomerDataEntity.CLASSIFY + this.classify;
    }

    public void setClassify(final String classify) {
        this.classify = classify;
    }

    public Character getStatus() {
        return this.status;
    }

    public void setStatus(final Character status) {
        this.status = status;
    }

    public String getDateSurvey() {
        return this.dateSurvey;
    }

    public void setDateSurvey(final String dateSurvey) {
        this.dateSurvey = dateSurvey;
    }

    public int getIdComRelation() {
        return this.idComRelation;
    }

    public void setIdComRelation(final int idComRelation) {
        this.idComRelation = idComRelation;
    }


}
