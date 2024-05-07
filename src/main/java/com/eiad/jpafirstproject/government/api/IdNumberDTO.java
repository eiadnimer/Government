package com.eiad.jpafirstproject.government.api;

import lombok.Getter;

import java.util.Objects;

@Getter
public class IdNumberDTO {
    private Long idNumber;

    public IdNumberDTO(long idNumber){
        this.idNumber = idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdNumberDTO that = (IdNumberDTO) o;
        return Objects.equals(idNumber, that.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber);
    }

    @Override
    public String toString() {
        return "IdNumberDTO{" +
                "idNumber=" + idNumber +
                '}';
    }
}
