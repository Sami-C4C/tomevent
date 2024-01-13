package de.thb.dim.eventTom.valueObjects.eventManagement;

import java.time.Duration;
import java.time.LocalDateTime;

public class ShowVO extends EventVO {


    private static final long serialVersionUID = 1L;
    private Duration runtime;


    public ShowVO(int id, String name, String[] equipment, String location, LocalDateTime date, Duration runtime, int anzCategory)throws NullPointerException,IllegalArgumentException {
        super(id, name, equipment, location, date, anzCategory);
        setRuntime(runtime);


    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((runtime == null) ? 0 : runtime.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShowVO other = (ShowVO) obj;
        if (runtime == null) {
            if (other.runtime != null) {
                return false;
            }
        } else if (!runtime.equals(other.runtime)) {
            return false;
        }
        return true;
    }

    public Duration getRuntime() {
        return runtime;
    }


    /**
     * corrected
     * @param runtime
     */
    public void setRuntime(Duration runtime) {
        if (runtime != null && runtime.isNegative()) {
            throw new IllegalArgumentException("Runtime must not be negative");
        }
        this.runtime = runtime;
    }

    /*	public void setRuntime(Duration runtime) {
            this.runtime = runtime;
        }*/


    public void setNrAvailableTickets(int nrAvailableTickets) {
        this.nrAvailableTickets = nrAvailableTickets;
    }


}