package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "censusyear", schema = "canadacensusdb", catalog = "")
public class CensusYearEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "censusYearID", nullable = false)
    private int censusYearID;
    @Basic
    @Column(name = "censusYear", nullable = false)
    private int censusYear;
    
	public int getCensusYearID() {
		return censusYearID;
	}
	public void setCensusYearID(int censusYearID) {
		this.censusYearID = censusYearID;
	}
	public int getCensusYear() {
		return censusYear;
	}
	public void setCensusYear(int censusYear) {
		this.censusYear = censusYear;
	}
	@Override
	public String toString() {
		return "CensusYearEntity [censusYearID=" + censusYearID + ", censusYear=" + censusYear + "]";
	}
    
}
