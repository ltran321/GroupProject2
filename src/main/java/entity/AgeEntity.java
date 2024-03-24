package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "age", schema = "canadacensusdb", catalog = "")
public class AgeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ageId", nullable = false)
    private int ageId;
    @Basic
    @Column(name = "ageGroup", nullable = false)
    private int ageGroup;
    @Basic
    @Column(name = "censusYear", nullable = false)
    private int censusYear;
    @Basic
    @Column(name = "geographicArea", nullable = false)
    private int geographicArea;
    @Basic
    @Column(name = "combined", nullable = false)
    private int combined;
    @Basic
    @Column(name = "male", nullable = false)
    private int male;
    @Basic
    @Column(name = "female", nullable = false)
    private int female;
    
	public int getAgeId() {
		return ageId;
	}
	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}
	public int getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(int ageGroup) {
		this.ageGroup = ageGroup;
	}
	public int getCensusYear() {
		return censusYear;
	}
	public void setCensusYear(int censusYear) {
		this.censusYear = censusYear;
	}
	public int getGeographicArea() {
		return geographicArea;
	}
	public void setGeographicArea(int geographicArea) {
		this.geographicArea = geographicArea;
	}
	public int getCombined() {
		return combined;
	}
	public void setCombined(int combined) {
		this.combined = combined;
	}
	public int getFemale() {
		return female;
	}
	public void setFemale(int female) {
		this.female = female;
	}
    
}
