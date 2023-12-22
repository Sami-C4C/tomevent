package de.thb.dim.eventTom.valueObjects.eventManagement;

import java.time.LocalDateTime;

public class PartyVO extends EventVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String catering;
    private String performer;

    public PartyVO(int id, String name, String[] equipment, String location, LocalDateTime date, String catering, String performer){
        super(id, name, equipment, location, date, 1);
        setCatering(catering);
        setPerformer(performer);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((catering == null) ? 0 : catering.hashCode());
        result = prime * result + ((performer == null) ? 0 : performer.hashCode());
        return result;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof PartyVO)) {
			return false;
		}
		PartyVO other = (PartyVO) obj;
		if (catering == null) {
			if (other.catering != null) {
				return false;
			}
		} else if (!catering.equals(other.catering)) {
			return false;
		}
		if (performer == null) {
			if (other.performer != null) {
				return false;
			}
		} else if (!performer.equals(other.performer)) {
			return false;
		}
		return true;
	}




    public String getCatering() {
        return catering;
    }

    public String getPerformer() {
        return performer;
    }

    /**
     *
     * @param catering
     */
    public void setCatering(String catering) {
        this.catering = catering;
    }

    /**
     *
     * @param perfomer
     */
    public void setPerformer(String perfomer) {
        this.performer = perfomer;
    }


}
